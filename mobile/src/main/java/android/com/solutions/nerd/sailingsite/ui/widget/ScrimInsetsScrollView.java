package android.com.solutions.nerd.sailingsite.ui.widget;



import android.com.solutions.nerd.sailingsite.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.ScrollView;

/**
 * A layout that draws something in the insets passed to {@link #fitSystemWindows(android.graphics.Rect)}, i.e. the area above UI chrome
 * (status and navigation bars, overlay action bars).
 */
public class ScrimInsetsScrollView extends ScrollView {
    private Drawable mInsetForecolor;

    private WindowInsets mInsets;
    private Rect mTempRect = new Rect();
    private OnInsetsCallback mOnInsetsCallback;


    public ScrimInsetsScrollView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScrimInsetsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ScrimInsetsScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);

    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ScrimInsetsView, defStyle, 0);
        if (a == null) {
            return;
        }
        mInsetForecolor = a.getDrawable(R.styleable.ScrimInsetsView_insetForeColor);
        a.recycle();

        setWillNotDraw(true);
    }


    @Override
    public WindowInsets dispatchApplyWindowInsets (WindowInsets insets)
    {
        mInsets = insets;
            setWillNotDraw(mInsetForecolor==null);
            if(mOnInsetsCallback!=null)
               mOnInsetsCallback.onInsetsChanged(insets);


        return super.dispatchApplyWindowInsets(insets);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (mInsets != null && mInsetForecolor != null) {
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());


            // Top
            mTempRect.set(0, 0, width, mInsets.getStableInsetTop());
            mInsetForecolor.setBounds(mTempRect);
            mInsetForecolor.draw(canvas);

            // Bottom
            mTempRect.set(0, height - mInsets.getStableInsetBottom(), width, height);
            mInsetForecolor.setBounds(mTempRect);
            mInsetForecolor.draw(canvas);

            // Left
            mTempRect.set(0, mInsets.getStableInsetTop(), mInsets.getStableInsetLeft(), height - mInsets.getStableInsetBottom());
            mInsetForecolor.setBounds(mTempRect);
            mInsetForecolor.draw(canvas);

            // Right
            mTempRect.set(width - mInsets.getStableInsetRight(), mInsets.getStableInsetTop(), width, height - mInsets.getStableInsetBottom());
            mInsetForecolor.setBounds(mTempRect);
            mInsetForecolor.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInsetForecolor != null) {
            mInsetForecolor.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInsetForecolor != null) {
            mInsetForecolor.setCallback(null);
        }
    }

    /**
     * Allows the calling container to specify a callback for custom processing when insets change (i.e. when
     * {@link #fitSystemWindows(android.graphics.Rect)} is called. This is useful for setting padding on UI elements based on
     * UI chrome insets (e.g. a Google Map or a ListView). When using with ListView or GridView, remember to set
     * clipToPadding to false.
     */
    public void setOnInsetsCallback(OnInsetsCallback onInsetsCallback) {
        mOnInsetsCallback = onInsetsCallback;
    }

    public static interface OnInsetsCallback {
        public void onInsetsChanged(WindowInsets insets);

    }
}