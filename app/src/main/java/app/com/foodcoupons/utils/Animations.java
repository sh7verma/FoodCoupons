package app.com.foodcoupons.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import app.com.foodcoupons.R;

/**
 * Created by dev on 27/9/18.
 */


public class Animations {

    private static Animation anim, anim2, aplha_anim;
    private static View dotView, circleView;

    public static void scaleUpView(final View v, final float startScale, final float endScale, final int duration) {
        circleView = v;
        anim2 = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim2.setFillAfter(true); // Needed to keep the result of the animation
        anim2.setDuration(duration);
        v.startAnimation(anim2);
        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                alphaHide(v, startScale, endScale, 500);
//                scaleDownView(v, endScale, startScale, duration);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void scaleUpViewDot(final View v, final float startScale, final float endScale, final int duration) {
        dotView = v;
        anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(duration);
        v.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                alphaShow(v, startScale, endScale, duration);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                alphaHide(v, startScale, endScale, 200);
                scaleDownViewDot(v, endScale, startScale, duration);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private static void alphaHide(final View v, final float startScale, final float endScale, int duration) {
        aplha_anim = new AlphaAnimation(1f, 0f);
        aplha_anim.setDuration(duration);
        v.startAnimation(aplha_anim);
        aplha_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                alphaShow(v, startScale, endScale, 300);
//                scaleUpView(v, startScale, endScale, 900);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private static void alphaShow(final View v, final float startScale, final float endScale, int duration) {
//        v.setScaleX(0.3f);
//        v.setScaleY(0.3f);
//        v.setAlpha(0f);
        aplha_anim = new AlphaAnimation(0f, 1f);
        aplha_anim.setDuration(duration);
        aplha_anim.setStartOffset(100);
        v.startAnimation(aplha_anim);
        aplha_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                scaleUpView(v, startScale, endScale, 900);
                Log.e("dot animation", "alphaShow ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static Animation getAnimationInstance() {
        return anim;
    }

    public static void clearAnimation() {
        if (anim2 != null) {
            anim2.cancel();
            anim2 = null;
            dotView.clearAnimation();
            circleView.clearAnimation();
        }
        if (anim != null) {
            anim.cancel();
            anim = null;
            dotView.clearAnimation();
            circleView.clearAnimation();
        }
        if (aplha_anim != null) {
            aplha_anim.cancel();
            aplha_anim = null;
            dotView.clearAnimation();
            circleView.clearAnimation();
        }

    }

    public static void scaleDownViewDot(final View v, final float startScale, final float endScale, final int duration) {
        anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(duration);
        v.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e("dot animation", "scaleDownViewDot ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                scaleUpViewDot(v, endScale, startScale, duration);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void slideLeftin(View view, int mWidth) {
        view.setVisibility(View.VISIBLE);

        TranslateAnimation animate = new TranslateAnimation(
                mWidth,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public static void slideLeftout(View view, int mWidth) {
        view.setVisibility(View.GONE);

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                -mWidth,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public static void slideRightin(View view, int mWidth) {
        view.setVisibility(View.VISIBLE);

        TranslateAnimation animate = new TranslateAnimation(
                -mWidth,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public static void slideRightout(View view, int mWidth) {
        view.setVisibility(View.GONE);

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                mWidth,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(300);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(300);
        v.startAnimation(a);
    }


    public static void AnimatedClick(Context context, View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click));
    }

    public static void ShakeAnimation(Context context, View v) {

        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        v.startAnimation(shake);
    }
}