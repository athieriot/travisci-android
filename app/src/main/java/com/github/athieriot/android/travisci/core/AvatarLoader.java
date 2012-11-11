package com.github.athieriot.android.travisci.core;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.view.View.VISIBLE;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.github.athieriot.android.travisci.R;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.inject.Inject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import roboguice.util.RoboAsyncTask;

/**
 * Avatar utilities
 */
public class AvatarLoader {

    private static final String TAG = "AvatarLoader";

    private static final float CORNER_RADIUS_IN_DIP = 3;

    private static final int CACHE_SIZE = 75;

    private static abstract class FetchAvatarTask extends
            RoboAsyncTask<BitmapDrawable> {

        private static final Executor EXECUTOR = Executors
                .newFixedThreadPool(1);

        private FetchAvatarTask(Context context) {
            super(context, EXECUTOR);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Log.d(TAG, "Avatar load failed", e);
        }
    }

    private final float cornerRadius;

    private final Map<Object, BitmapDrawable> loaded = new LinkedHashMap<Object, BitmapDrawable>(
            CACHE_SIZE, 1.0F) {

        private static final long serialVersionUID = -4191624209581976720L;

        @Override
        protected boolean removeEldestEntry(
                Map.Entry<Object, BitmapDrawable> eldest) {
            return size() >= CACHE_SIZE;
        }
    };

    private final Context context;

    private final File avatarDir;

    private final Drawable loadingAvatar;

    private final BitmapFactory.Options options;

    /**
     * Create avatar helper
     *
     * @param context
     */
    @Inject
    public AvatarLoader(final Context context) {
        this.context = context;

        loadingAvatar = context.getResources().getDrawable(R.drawable.gravatar_icon);

        avatarDir = new File(context.getCacheDir(), "avatars/" + context.getPackageName());
        if (!avatarDir.isDirectory())
            avatarDir.mkdirs();

        float density = context.getResources().getDisplayMetrics().density;
        cornerRadius = CORNER_RADIUS_IN_DIP * density;

        options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = ARGB_8888;
    }

    /**
     * Get image for user
     *
     * @param user
     * @return image
     */
    protected BitmapDrawable getImage(final User user) {
        File avatarFile = new File(avatarDir, user.getObjectId());

        if (!avatarFile.exists() || avatarFile.length() == 0)
            return null;

        Bitmap bitmap = decode(avatarFile);
        if (bitmap != null)
            return new BitmapDrawable(context.getResources(), bitmap);
        else {
            avatarFile.delete();
            return null;
        }
    }

//    /**
//     * Get image for user
//     *
//     * @param user
//     * @return image
//     */
//    protected BitmapDrawable getImage(final CommitUser user) {
//        File avatarFile = new File(avatarDir, user.getEmail());
//
//        if (!avatarFile.exists() || avatarFile.length() == 0)
//            return null;
//
//        Bitmap bitmap = decode(avatarFile);
//        if (bitmap != null)
//            return new BitmapDrawable(context.getResources(), bitmap);
//        else {
//            avatarFile.delete();
//            return null;
//        }
//    }

    /**
     * Decode file to bitmap
     *
     * @param file
     * @return bitmap
     */
    protected Bitmap decode(final File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * Fetch avatar from URL
     *
     * @param url
     * @param userId
     * @return bitmap
     */
    protected BitmapDrawable fetchAvatar(final String url, final String userId) {
        File rawAvatar = new File(avatarDir, userId + "-raw");
        HttpRequest request = HttpRequest.get(url);
        if (request.ok())
            request.receive(rawAvatar);

        if (!rawAvatar.exists() || rawAvatar.length() == 0)
            return null;

        Bitmap bitmap = decode(rawAvatar);
        if (bitmap == null) {
            rawAvatar.delete();
            return null;
        }

        bitmap = ImageUtils.roundCorners(bitmap, cornerRadius);
        if (bitmap == null) {
            rawAvatar.delete();
            return null;
        }

        File roundedAvatar = new File(avatarDir, userId.toString());
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(roundedAvatar);
            if (bitmap.compress(PNG, 100, output))
                return new BitmapDrawable(context.getResources(), bitmap);
            else
                return null;
        } catch (IOException e) {
            Log.d(TAG, "Exception writing rounded avatar", e);
            return null;
        } finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException e) {
                    // Ignored
                }
            rawAvatar.delete();
        }
    }

    /**
     * Sets the logo on the {@link com.actionbarsherlock.app.ActionBar} to the user's avatar.
     *
     * @param actionBar
     * @param user
     * @return this helper
     */
    public AvatarLoader bind(final ActionBar actionBar, final User user) {
        return bind(actionBar, new AtomicReference<User>(user));
    }

    /**
     * Sets the logo on the {@link ActionBar} to the user's avatar.
     *
     * @param actionBar
     * @param userReference
     * @return this helper
     */
    public AvatarLoader bind(final ActionBar actionBar,
                             final AtomicReference<User> userReference) {
        if (userReference == null)
            return this;

        final User user = userReference.get();
        if (user == null)
            return this;

        final String avatarUrl = user.getAvatarUrl();
        if (TextUtils.isEmpty(avatarUrl))
            return this;

        final String userId = user.getObjectId();

        BitmapDrawable loadedImage = loaded.get(userId);
        if (loadedImage != null) {
            actionBar.setLogo(loadedImage);
            return this;
        }

        new FetchAvatarTask(context) {

            @Override
            public BitmapDrawable call() throws Exception {
                final BitmapDrawable image = getImage(user);
                if (image != null)
                    return image;
                else
                    return fetchAvatar(avatarUrl, userId.toString());
            }

            @Override
            protected void onSuccess(BitmapDrawable image) throws Exception {
                final User current = userReference.get();
                if (current != null && userId.equals(current.getObjectId()))
                    actionBar.setLogo(image);
            }
        }.execute();

        return this;
    }

    private AvatarLoader setImage(final Drawable image, final ImageView view) {
        return setImage(image, view, null);
    }

    private AvatarLoader setImage(final Drawable image, final ImageView view,
                                  Object tag) {
        view.setImageDrawable(image);
        view.setTag(R.id.iv_avatar, tag);
        view.setVisibility(VISIBLE);
        return this;
    }

    private String getAvatarUrl(String id) {
        if (!TextUtils.isEmpty(id))
            return "https://secure.gravatar.com/avatar/" + id + "?d=404";
        else
            return null;
    }

    private String getAvatarUrl(User user) {
        String avatarUrl = user.getAvatarUrl();
        if (TextUtils.isEmpty(avatarUrl)) {
            String gravatarId = user.getGravatarId();
            if (TextUtils.isEmpty(gravatarId))
                gravatarId = GravatarUtils.getHash(user.getUsername());
            avatarUrl = getAvatarUrl(gravatarId);
        }
        return avatarUrl;
    }

//    private String getAvatarUrl(CommitUser user) {
//        return getAvatarUrl(GravatarUtils.getHash(user.getEmail()));
//    }

    /**
     * Bind view to image at URL
     *
     * @param view
     * @param user
     * @return this helper
     */
    public AvatarLoader bind(final ImageView view, final User user) {
        if (user == null)
            return setImage(loadingAvatar, view);

        String avatarUrl = getAvatarUrl(user);

        if (TextUtils.isEmpty(avatarUrl))
            return setImage(loadingAvatar, view);

        final String userId = user.getObjectId();

        BitmapDrawable loadedImage = loaded.get(userId);
        if (loadedImage != null)
            return setImage(loadedImage, view);

        setImage(loadingAvatar, view, userId);

        final String loadUrl = avatarUrl;
        new FetchAvatarTask(context) {

            @Override
            public BitmapDrawable call() throws Exception {
                if (!userId.equals(view.getTag(R.id.iv_avatar)))
                    return null;

                final BitmapDrawable image = getImage(user);
                if (image != null)
                    return image;
                else
                    return fetchAvatar(loadUrl, userId.toString());
            }

            @Override
            protected void onSuccess(final BitmapDrawable image)
                    throws Exception {
                if (image == null)
                    return;
                loaded.put(userId, image);
                if (userId.equals(view.getTag(R.id.iv_avatar)))
                    setImage(image, view);
            }

        }.execute();

        return this;
    }

//    /**
//     * Bind view to image at URL
//     *
//     * @param view
//     * @param user
//     * @return this helper
//     */
//    public AvatarLoader bind(final ImageView view, final CommitUser user) {
//        if (user == null)
//            return setImage(loadingAvatar, view);
//
//        String avatarUrl = getAvatarUrl(user);
//
//        if (TextUtils.isEmpty(avatarUrl))
//            return setImage(loadingAvatar, view);
//
//        final String userId = user.getEmail();
//
//        BitmapDrawable loadedImage = loaded.get(userId);
//        if (loadedImage != null)
//            return setImage(loadedImage, view);
//
//        setImage(loadingAvatar, view, userId);
//
//        final String loadUrl = avatarUrl;
//        new FetchAvatarTask(context) {
//
//            @Override
//            public BitmapDrawable call() throws Exception {
//                if (!userId.equals(view.getTag(id.iv_avatar)))
//                    return null;
//
//                final BitmapDrawable image = getImage(user);
//                if (image != null)
//                    return image;
//                else
//                    return fetchAvatar(loadUrl, userId);
//            }
//
//            @Override
//            protected void onSuccess(final BitmapDrawable image)
//                    throws Exception {
//                if (image == null)
//                    return;
//                loaded.put(userId, image);
//                if (userId.equals(view.getTag(id.iv_avatar)))
//                    setImage(image, view);
//            }
//
//        }.execute();
//
//        return this;
//    }
}

