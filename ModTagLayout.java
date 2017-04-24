package com.ekant.justbiz;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
/**
 * Created by ekant on 16/12/15.
 */
public class ModTagLayout extends ViewGroup
{
    int deviceWidth;
    public ModTagLayout(Context context)
    {
        super(context);
        init(context);
    }
    public ModTagLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }
    public ModTagLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;
        //get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;
        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                return;
            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();
            //wrap is reach to the end
            if (curLeft + curWidth >= childRight)
            {
                curLeft = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //do the layout
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            //store the max height
            if (maxHeight < curHeight)
                maxHeight = curHeight;
            curLeft += curWidth;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int count = getChildCount();
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        int mLeftWidth = 0;
        int rowCount = 0;
        for (int i = 0; i < count; i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth +=  child.getMeasuredWidth()+10;
            if(maxHeight == 0)
               maxHeight += child.getMeasuredHeight();
            mLeftWidth += child.getMeasuredWidth()+10;
            if(maxWidth > deviceWidth ||(deviceWidth - mLeftWidth)< child.getMeasuredWidth())
            {
                maxWidth = 0;
                mLeftWidth = 0;
                maxHeight += child.getMeasuredHeight()+30;
                rowCount ++;
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState), resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
    }
}