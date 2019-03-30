//package com.terry.view.swipeanimationbutton;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ValueAnimator;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Vibrator;
//import android.support.v4.content.ContextCompat;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import org.w3c.dom.Text;
//
//public class SwipeAnimationButton extends RelativeLayout {
//
//    private static final String TAG = "SwipeButton";
//    private static final boolean RIGHT = true;
//    private static final boolean LEFT = false;
//    SwipeAnimationListener mSwipeAnimationListener;
//    RelativeLayout mBackground;
//    private ImageView slidingButton;
//    private float initialX;
//    private boolean active;
//    private int initialButtonWidth;
//
//    ImageView swipeButton;
//    TypedArray ta;
//    Integer drawable = R.drawable.num1;
//
//    private Drawable defaultDrawable;
//    private Drawable defaultBackground;
//
//
//    private Drawable rightSwipeDrawable;
//    private Drawable rightSwipeBackground;
//
//    private Drawable leftSwipeDrawable;
//    private Drawable leftSwipeBackground;
//
//    private Drawable basketballIcon;
//
//    private long mDuration;
//    long[] mVibratePattern = new long[]{0, 300};
//    Vibrator mVibrate;
//
//    public SwipeAnimationButton(Context context) {
//        super(context);
//        init(context, null, -1, -1);
//    }
//
//    public SwipeAnimationButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs, -1, -1);
//    }
//
//    public SwipeAnimationButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs, defStyleAttr, -1);
//    }
//
//    public void setOnSwipeAnimationListener(SwipeAnimationListener swipeAnimationListener) {
//        this.mSwipeAnimationListener = swipeAnimationListener;
//    }
//
//    public void setPicture(Integer picture) {
//        Drawable newDrawable = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, picture));
//
////        Bitmap bitmap = ((BitmapDrawable) newDrawable).getBitmap();
////        Drawable scaledDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));
//
//        swipeButton.setImageDrawable(newDrawable);
//    }
//
//    @SuppressLint("ResourceAsColor")
//    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        ta = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeAnimationButton);
//
//        basketballIcon = (Drawable) context.getDrawable(R.drawable.ic_basketball);
//
////        defaultBackground = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, R.drawable.shape_rounded));
////        defaultDrawable = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultDrawable, R.drawable.sentimental_neutral));
//        defaultBackground = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, R.drawable.shape_rounded));
//        defaultDrawable = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultDrawable, this.drawable));
//
//        mVibrate = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        mBackground = new RelativeLayout(context);
//
//        LayoutParams layoutParamsView = new LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        layoutParamsView.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//
//        mBackground.setBackground(ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_backgroundColor, R.drawable.shape_rounded)));
////        mBackground.setPadding(40, 40, 40, 40);
//        addView(mBackground, layoutParamsView);
//
//        swipeButton = new ImageView(context);
//        this.slidingButton = swipeButton;
//
//        rightSwipeDrawable = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_rightSwipeDrawable, R.drawable.ic_num1));
//        rightSwipeBackground = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, R.drawable.shape_button));
//        leftSwipeDrawable = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_leftSwipeDrawable, R.drawable.ic_basketball));
//        leftSwipeBackground = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_leftSwipeBackground, R.drawable.gradient_radius_grey));
//
//
//        mDuration = ta.getInteger(R.styleable.SwipeAnimationButton_duration, 200);
//        slidingButton.setImageDrawable(defaultDrawable);
////        slidingButton.setPadding(60, 60, 60, 60);
//
//        LayoutParams layoutParamsButton = new LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        layoutParamsButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        layoutParamsButton.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//
//        swipeButton.setImageDrawable(defaultDrawable);
//        swipeButton.setBackground(defaultBackground);
//
//        addView(swipeButton, layoutParamsButton);
//        setOnTouchListener(getButtonTouchListener());
//    }
//
//    public OnTouchListener getButtonTouchListener() {
//        return new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        if (initialX == 0) {
//                            initialX = slidingButton.getX();
//                        }
//                        if (event.getX() > initialX + slidingButton.getWidth() / 2 &&
//                                event.getX() + slidingButton.getWidth() / 2 < getWidth()) {
//                            slidingButton.setX(event.getX() - slidingButton.getWidth() / 2);
//                            Log.d(TAG, "sliding Right");
//                        }
//
//                        if (event.getX() < initialX + slidingButton.getWidth() / 2 &&
//                                event.getX() + slidingButton.getWidth() / 2 < getWidth()) {
//                            slidingButton.setX(event.getX() - slidingButton.getWidth() / 2);
//                            Log.d(TAG, "sliding left");
//                        }
//
//                        if (event.getX() + slidingButton.getWidth() / 2 > getWidth() &&
//                                slidingButton.getX() + slidingButton.getWidth() / 2 < getWidth() + 100) {
//                            Log.d(TAG, "stop at right");
//                            slidingButton.setX(getWidth() - slidingButton.getWidth());
//                        }
//
//
//                        if (event.getX() + slidingButton.getWidth() / 2 < getWidth() && slidingButton.getX() < 4) {
//                            slidingButton.setX(0);
//                        }
//
//                        if (event.getX() < slidingButton.getWidth() / 2 &&
//                                slidingButton.getX() > 0) {
//                            slidingButton.setX(initialX);
//                        }
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        if (active) {
//                            collapseButton();
//                        } else {
//                            initialButtonWidth = slidingButton.getWidth();
//
//                            if (slidingButton.getX() + slidingButton.getWidth() > getWidth() * 0.75) {
//                                expandButton(RIGHT);
//                            } else if (slidingButton.getX() - slidingButton.getWidth() < getWidth() * 0.25) {
//                                expandButton(LEFT);
//                            } else {
//                                moveToCenter();
//                            }
//                        }
//
//                        return true;
//
//                }
//
//                return false;
//            }
//        };
//
//    }
//
//    public void expandButton(final boolean isUp) {
//        final ValueAnimator positionAnimator =
//                ValueAnimator.ofFloat(slidingButton.getX(), 0);
//        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float x = (Float) positionAnimator.getAnimatedValue();
//                slidingButton.setX(x);
//            }
//        });
//
//
//        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
//                slidingButton.getWidth(),
//                getWidth());
//
//        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
//                params.width = (Integer) widthAnimator.getAnimatedValue();
//                slidingButton.setLayoutParams(params);
//                if (isUp) {
//                    slidingButton.setImageDrawable(rightSwipeDrawable);
//                    slidingButton.setBackground(rightSwipeBackground);
//                } else {
//                    slidingButton.setImageDrawable(leftSwipeDrawable);
//                    slidingButton.setBackground(leftSwipeBackground);
//                }
//                slidingButton.setPadding(66, 70, 66, 70);
//            }
//        });
//
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                active = true;
//            }
//        });
//
//        animatorSet.playTogether(positionAnimator, widthAnimator);
//        animatorSet.start();
//        mSwipeAnimationListener.onSwiped(isUp);
//    }
//
//    public void collapseButton() {
//        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
//                slidingButton.getWidth(),
//                initialButtonWidth);
//
//        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
//                params.width = (Integer) widthAnimator.getAnimatedValue();
//                slidingButton.setLayoutParams(params);
//            }
//        });
//
//        widthAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                active = false;
////                slidingButton.setPadding(66, 60, 66, 60);
//
//                slidingButton.setImageDrawable(defaultDrawable);
//                slidingButton.setBackground(defaultBackground);
//            }
//        });
//
//        AnimatorSet animatorSet = new AnimatorSet();
//
//        animatorSet.play(widthAnimator);
//        animatorSet.start();
//    }
//
//    public void moveToCenter() {
//        final ValueAnimator positionAnimator =
//                ValueAnimator.ofFloat(slidingButton.getX(), 0);
//        positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float x = (Float) positionAnimator.getAnimatedValue();
//                slidingButton.setX(initialX);
//            }
//        });
//        positionAnimator.setDuration(mDuration);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(positionAnimator);
//        animatorSet.start();
//    }
//
//}


package com.terry.view.swipeanimationbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SwipeAnimationButton extends RelativeLayout {

    private static final String TAG = "SwipeButton";
    private static final boolean RIGHT = true;
    private static final boolean LEFT = false;
    SwipeAnimationListener mSwipeAnimationListener;
    RelativeLayout mBackground;
    private ImageView slidingButton;
    private float initialX;
    private boolean active;
    private int initialButtonWidth;

    private Drawable defaultDrawable;
    private Drawable defaultBackground;

    private Drawable rightSwipeDrawable;
    private Drawable rightSwipeBackground;

    private Drawable leftSwipeDrawable;
    private Drawable leftSwipeBackground;

    private long mDuration;
    long[] mVibratePattern = new long[]{0, 300};
    Vibrator mVibrate;

    public SwipeAnimationButton(Context context) {
        super(context);
        init(context, null, -1, -1);
    }

    public SwipeAnimationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public SwipeAnimationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }

    public void setOnSwipeAnimationListener(SwipeAnimationListener swipeAnimationListener) {
        this.mSwipeAnimationListener = swipeAnimationListener;
    }

    public void setPicture(Integer picture) {
//        Drawable newDrawable = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, picture));

//        Bitmap bitmap = ((BitmapDrawable) newDrawable).getBitmap();
//        Drawable scaledDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));

//        swipeButton.setImageDrawable(newDrawable);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeAnimationButton);

        defaultBackground = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, R.drawable.shape_button_neutral));
        defaultDrawable = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultDrawable, R.drawable.ic_num1));

        mVibrate = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mBackground = new RelativeLayout(context);

        LayoutParams layoutParamsView = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsView.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        mBackground.setBackground(ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_backgroundColor, R.drawable.shape_rounded)));
        mBackground.setPadding(40, 40, 40, 40);
        addView(mBackground, layoutParamsView);

        final ImageView swipeButton = new ImageView(context);
        this.slidingButton = swipeButton;

        rightSwipeDrawable = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_rightSwipeDrawable, R.drawable.ic_basketball));
        rightSwipeBackground = ContextCompat.getDrawable(context, ta.getInteger(R.styleable.SwipeAnimationButton_defaultBackground, R.drawable.shape_button));
        leftSwipeDrawable = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_leftSwipeDrawable, R.drawable.ic_basketball));
        leftSwipeBackground = ContextCompat.getDrawable(getContext(), ta.getInteger(R.styleable.SwipeAnimationButton_leftSwipeBackground, R.drawable.gradient_radius_grey));

        mDuration = ta.getInteger(R.styleable.SwipeAnimationButton_duration, 200);
        slidingButton.setImageDrawable(defaultDrawable);
        slidingButton.setPadding(20, 50, 20, 50);

        LayoutParams layoutParamsButton = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParamsButton.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        swipeButton.setImageDrawable(defaultDrawable);
        swipeButton.setBackground(defaultBackground);

        addView(swipeButton, layoutParamsButton);
        setOnTouchListener(getButtonTouchListener());
    }

    public OnTouchListener getButtonTouchListener() {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (initialX == 0) {
                            initialX = slidingButton.getX();
                        }
                        if (event.getX() > initialX + slidingButton.getWidth() / 2 &&
                                event.getX() + slidingButton.getWidth() / 2 < getWidth()) {
                            slidingButton.setX(event.getX() - slidingButton.getWidth() / 2);
                            Log.d(TAG, "sliding Right");
                        }

                        if (event.getX() < initialX + slidingButton.getWidth() / 2 &&
                                event.getX() + slidingButton.getWidth() / 2 < getWidth()) {
                            slidingButton.setX(event.getX() - slidingButton.getWidth() / 2);
                            Log.d(TAG, "sliding left");
                        }

                        if (event.getX() + slidingButton.getWidth() / 2 > getWidth() &&
                                slidingButton.getX() + slidingButton.getWidth() / 2 < getWidth() + 100) {
                            Log.d(TAG, "stop at right");
                            slidingButton.setX(getWidth() - slidingButton.getWidth());
                        }


                        if (event.getX() + slidingButton.getWidth() / 2 < getWidth() && slidingButton.getX() < 4) {
                            slidingButton.setX(0);
                        }

                        if (event.getX() < slidingButton.getWidth() / 2 &&
                                slidingButton.getX() > 0) {
                            slidingButton.setX(initialX);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (active) {
                            collapseButton();
                        } else {
                            initialButtonWidth = slidingButton.getWidth();

                            if (slidingButton.getX() + slidingButton.getWidth() > getWidth() * 0.75) {
                                expandButton(RIGHT);
                            } else if (slidingButton.getX() - slidingButton.getWidth() < getWidth() * 0.25) {
                                expandButton(LEFT);
                            } else {
                                moveToCenter();
                            }
                        }

                        return true;

                }

                return false;
            }
        };

    }

    public void expandButton(final boolean isUp) {
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(slidingButton.getX(), 0);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (Float) positionAnimator.getAnimatedValue();
                slidingButton.setX(x);
            }
        });


        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                slidingButton.getWidth(),
                getWidth());

        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
                params.width = (Integer) widthAnimator.getAnimatedValue();
                slidingButton.setLayoutParams(params);
                if (isUp) {
                    slidingButton.setImageDrawable(rightSwipeDrawable);
                    slidingButton.setBackground(rightSwipeBackground);
                } else {
                    slidingButton.setImageDrawable(leftSwipeDrawable);
                    slidingButton.setBackground(leftSwipeBackground);
                }
                slidingButton.setPadding(50, 50, 50, 50);
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                active = true;
            }
        });

        animatorSet.playTogether(positionAnimator, widthAnimator);
        animatorSet.start();
        mSwipeAnimationListener.onSwiped(isUp);
    }

    public void collapseButton() {
        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                slidingButton.getWidth(),
                initialButtonWidth);

        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
                params.width = (Integer) widthAnimator.getAnimatedValue();
                slidingButton.setLayoutParams(params);
            }
        });

        widthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                active = false;
                slidingButton.setPadding(50, 50, 50, 50);

                slidingButton.setImageDrawable(defaultDrawable);
                slidingButton.setBackground(defaultBackground);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(widthAnimator);
        animatorSet.start();
    }

    public void moveToCenter() {
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(slidingButton.getX(), 0);
        positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (Float) positionAnimator.getAnimatedValue();
                slidingButton.setX(initialX);
            }
        });
        positionAnimator.setDuration(mDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(positionAnimator);
        animatorSet.start();
    }

}
