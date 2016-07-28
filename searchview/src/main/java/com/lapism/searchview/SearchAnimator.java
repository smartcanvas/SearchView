package com.lapism.searchview;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


class SearchAnimator {

    static void fadeIn(final View view, int duration) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(anim);
    }

    static void fadeOut(final View view, int duration) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(anim);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void revealOpen(final View view, int duration, Context context, final SearchEditText editText, final boolean shouldClearOnOpen, final SearchView.OnOpenCloseListener listener) {

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
                    if (listener != null) {
                        listener.onOpen();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (shouldClearOnOpen && editText.length() > 0) {
                        editText.getText().clear();
                    }
                    editText.requestFocus();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            view.setVisibility(View.VISIBLE);
            anim.start();
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
                    if (shouldClearOnClose && editText.length() > 0) {
                        editText.getText().clear();
                    }
                    editText.clearFocus();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);

                    if (listener != null) {
                        listener.onClose();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    }

    static void fadeOpen(final View view, int duration, final SearchEditText editText, final boolean shouldClearOnOpen, final SearchView.OnOpenCloseListener listener) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (listener != null) {
                    listener.onOpen();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
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

        view.startAnimation(anim);
    }

    static void fadeClose(final View view, int duration, final SearchEditText editText, final boolean shouldClearOnClose, final SearchView searchView, final SearchView.OnOpenCloseListener listener) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (shouldClearOnClose && editText.length() > 0) {
                    editText.getText().clear();
                }
                editText.clearFocus();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
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

        view.startAnimation(anim);
    }
}
