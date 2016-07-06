package com.lapism.searchview;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


class SearchAnimator {
    private static final String LOG_TAG = SearchAnimator.class.getSimpleName();

    static void fadeIn(View view, int duration) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        view.setAnimation(anim);
        view.setVisibility(View.VISIBLE);
    }

    static void fadeOut(View view, int duration) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        view.setAnimation(anim);
        view.setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void revealOpen(View view, int duration, Context context, final SearchEditText editText, final boolean shouldClearOnOpen, final SearchView.OnOpenCloseListener listener) {
        int padding = context.getResources().getDimensionPixelSize(R.dimen.search_reveal);

        int cx = view.getWidth() - padding;
        int cy = context.getResources().getDimensionPixelSize(R.dimen.search_height) / 2;

        if (cx != 0 && cy != 0) {
            float finalRadius = (float) Math.hypot(cx, cy);

            if (SearchUtils.isRtlLayout(context)) {
                cx = padding;
            }

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0.0f, finalRadius);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            anim.addListener(new Animator.AnimatorListener() { // new AnimatorListenerAdapter()
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(LOG_TAG, "revealOpen() - onAnimationStart() callback invoked.");
                    Log.d(LOG_TAG, String.format("revealOpen() - is OnOpenCloseListener set? %s", listener != null));

                    if (listener != null) {
                        listener.onOpen();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(LOG_TAG, "revealOpen() - onAnimationEnd() callback invoked.");

                    if (shouldClearOnOpen && editText.length() > 0) {
                        editText.getText().clear();
                    }
                    editText.requestFocus();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(LOG_TAG, "revealOpen() - onAnimationCancel() callback invoked.");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(LOG_TAG, "revealOpen() - onAnimationRepeat() callback invoked.");
                }
            });

            view.setVisibility(View.VISIBLE);
            anim.start();

            Log.d(LOG_TAG, "revealOpen() - anim.start() called");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void revealClose(final View view, int duration, Context context, final SearchEditText editText, final boolean shouldClearOnClose, final SearchView searchView, final SearchView.OnOpenCloseListener listener) {
        int padding = context.getResources().getDimensionPixelSize(R.dimen.search_reveal);

        int cx = view.getWidth() - padding;
        int cy = context.getResources().getDimensionPixelSize(R.dimen.search_height) / 2;

        if (cx != 0 && cy != 0) {
            float initialRadius = (float) Math.hypot(cx, cy);

            if (SearchUtils.isRtlLayout(context)) {
                cx = padding;
            }

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0.0f);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(LOG_TAG, "revealClose() - onAnimationStart() callback invoked.");

                    if (shouldClearOnClose && editText.length() > 0) {
                        editText.getText().clear();
                    }
                    editText.clearFocus();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(LOG_TAG, "revealClose() - onAnimationEnd() callback invoked.");
                    Log.d(LOG_TAG, String.format("revealClose() - is OnOpenCloseListener set? %s", listener != null));

                    view.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    if (listener != null) {
                        listener.onClose();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(LOG_TAG, "revealClose() - onAnimationCancel() callback invoked.");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(LOG_TAG, "revealClose() - onAnimationRepeat() callback invoked.");
                }
            });
            anim.start();

            Log.d(LOG_TAG, "revealClose() - anim.start() called");
        }
    }

    static void fadeOpen(final View view, int duration, final SearchEditText editText, final boolean shouldClearOnOpen, final SearchView.OnOpenCloseListener listener) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(LOG_TAG, "fadeOpen() - onAnimationStart() callback invoked.");
                Log.d(LOG_TAG, String.format("fadeOpen() - is OnOpenCloseListener set? %s", listener != null));

                if (listener != null) {
                    listener.onOpen();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(LOG_TAG, "fadeOpen() - onAnimationEnd() callback invoked.");

                view.setVisibility(View.VISIBLE);

                if (shouldClearOnOpen && editText.length() > 0) {
                    editText.getText().clear();
                }
                editText.requestFocus();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

//        view.setAnimation(anim);
        Log.d(LOG_TAG, "fadeOpen() - view.setAnimation() called");

//        view.setVisibility(View.VISIBLE);
        Log.d(LOG_TAG, "fadeOpen() - view visibility set to VISIBLE");
        view.startAnimation(anim);
    }

    static void fadeClose(final View view, int duration, final SearchEditText editText, final boolean shouldClearOnClose, final SearchView searchView, final SearchView.OnOpenCloseListener listener) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(LOG_TAG, "fadeClose() - onAnimationStart() callback invoked.");

                if (shouldClearOnClose && editText.length() > 0) {
                    editText.getText().clear();
                }
                editText.clearFocus();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(LOG_TAG, "fadeClose() - onAnimationEnd() callback invoked.");
                Log.d(LOG_TAG, String.format("fadeClose() - is OnOpenCloseListener set? %s", listener != null));

                view.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                if (listener != null) {
                    listener.onClose();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

//        view.setAnimation(anim);
        Log.d(LOG_TAG, "fadeClose() - view.setAnimation() called");

//        view.setVisibility(View.GONE);
        Log.d(LOG_TAG, "fadeClose() - view visibility set to GONE");
        view.startAnimation(anim);
    }
}
