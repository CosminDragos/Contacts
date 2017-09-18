package ro.project.contacts.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ro.project.contacts.R;

/**
 * Created by cosmin on 18.09.2017.
 */
public class CustomRatioFrameLayout extends FrameLayout{

    /**
     * @param ratioType choose what to adjust {@link #WIDTH_RATIO} or {@link #HEIGHT_RATIO}
     * @param mHeightRatio float value for height/width default 0f
     * @param mWidthRatio float value for width/height default 0f*/

    private final int WIDTH_RATIO = 0;
    private final int HEIGHT_RATIO = 1;

    private float mHeightRatio;
    private float mWidthRatio;
    private int ratioType;


    public CustomRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RatioView, 0, 0);
        try {
            mHeightRatio = ta.getFloat(R.styleable.RatioView_height_ratio, 0.0f);
            mWidthRatio = ta.getFloat(R.styleable.RatioView_width_ratio, 0.0f);
            ratioType = ta.getInt(R.styleable.RatioView_ratioType, WIDTH_RATIO);
        } finally {
            ta.recycle();
        }
    }

    public CustomRatioFrameLayout(Context context) {
        super(context);
    }

    public void setHeightRatio(float ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public void setWidthRatio(float ratio) {
        if (ratio != mWidthRatio) {
            mWidthRatio = ratio;
            requestLayout();
        }
    }

    public void setRatioType(int type) {
        ratioType = type;
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        switch (ratioType) {
            case WIDTH_RATIO:
                if(mWidthRatio > 0.0f ) {
                    width = (int) (height * mWidthRatio);
                }
                break;
            case HEIGHT_RATIO:
                if(mHeightRatio > 0.f) {
                    height = (int) (width * mHeightRatio);
                }
                break;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }
}
