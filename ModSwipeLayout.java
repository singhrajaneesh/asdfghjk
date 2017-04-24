package com.ekant.justbiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModSwipeLayout extends FrameLayout
{
    @Deprecated
    public static final int EMPTY_LAYOUT = -1;
    private static final int DRAG_LEFT = 1;
    private static final int DRAG_RIGHT = 2;
    private static final int DRAG_TOP = 4;
    private static final int DRAG_BOTTOM = 8;
    private static final DragEdge DefaultDragEdge = DragEdge.Right;

    private int mTouchSlop;

    private DragEdge mCurrentDragEdge = DefaultDragEdge;
    private ViewDragHelper mDragHelper;

    private int mDragDistance = 0;
    private LinkedHashMap<DragEdge, View> mDragEdges = new LinkedHashMap<>();
    private ShowMode mShowMode;

    private float[] mEdgeSwipesOffset = new float[4];

    private List<SwipeListener> mSwipeListeners = new ArrayList<>();
    private List<SwipeDenier> mSwipeDeniers = new ArrayList<>();
    private Map<View, ArrayList<OnRevealListener>> mRevealListeners = new HashMap<>();
    private Map<View, Boolean> mShowEntirely = new HashMap<>();
    private Map<View, Rect> mViewBoundCache = new HashMap<>();//save all children's bound, restore in onLayout


    private boolean mSwipeEnabled = true;
    private boolean[] mSwipesEnabled = new boolean[]{true, true, true, true};
    private boolean mClickToClose = false;
    private float mWillOpenPercentAfterOpen=0.75f;
    private float mWillOpenPercentAfterClose=0.25f;

    public enum DragEdge {
        Left,
        Top,
        Right,
        Bottom
    }

    public enum ShowMode
    {
        LayDown,
        PullOut
    }

    public ModSwipeLayout(Context context) {
        this(context, null);
    }

    public ModSwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ModSwipeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, mDragHelperCallback);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ModSwipeLayout);
        int dragEdgeChoices = a.getInt(R.styleable.ModSwipeLayout_drag_edge, DRAG_RIGHT);
        mEdgeSwipesOffset[DragEdge.Left.ordinal()] = a.getDimension(R.styleable.ModSwipeLayout_leftEdgeSwipeOffset, 0);
        mEdgeSwipesOffset[DragEdge.Right.ordinal()] = a.getDimension(R.styleable.ModSwipeLayout_rightEdgeSwipeOffset, 0);
        mEdgeSwipesOffset[DragEdge.Top.ordinal()] = a.getDimension(R.styleable.ModSwipeLayout_topEdgeSwipeOffset, 0);
        mEdgeSwipesOffset[DragEdge.Bottom.ordinal()] = a.getDimension(R.styleable.ModSwipeLayout_bottomEdgeSwipeOffset, 0);
        setClickToClose(a.getBoolean(R.styleable.ModSwipeLayout_clickToClose, mClickToClose));

        if ((dragEdgeChoices & DRAG_LEFT) == DRAG_LEFT) {
            mDragEdges.put(DragEdge.Left, null);
        }
        if ((dragEdgeChoices & DRAG_TOP) == DRAG_TOP) {
            mDragEdges.put(DragEdge.Top, null);
        }
        if ((dragEdgeChoices & DRAG_RIGHT) == DRAG_RIGHT) {
            mDragEdges.put(DragEdge.Right, null);
        }
        if ((dragEdgeChoices & DRAG_BOTTOM) == DRAG_BOTTOM) {
            mDragEdges.put(DragEdge.Bottom, null);
        }
        int ordinal = a.getInt(R.styleable.ModSwipeLayout_show_mode, ShowMode.PullOut.ordinal());
        mShowMode = ShowMode.values()[ordinal];
        a.recycle();

    }

    public interface SwipeListener
    {
        void onStartOpen(ModSwipeLayout layout);

        void onOpen(ModSwipeLayout layout);

        void onStartClose(ModSwipeLayout layout);

        void onClose(ModSwipeLayout layout);

        void onUpdate(ModSwipeLayout layout, int leftOffset, int topOffset);

        void onHandRelease(ModSwipeLayout layout, float xvel, float yvel);
    }



    public interface SwipeDenier {

        boolean shouldDenySwipe(MotionEvent ev);
    }


    public interface OnRevealListener {
        void onReveal(View child, DragEdge edge, float fraction, int distance);
    }
    private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback()
    {

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == getSurfaceView()) {
                switch (mCurrentDragEdge) {
                    case Top:
                    case Bottom:
                        return getPaddingLeft();
                    case Left:
                        if (left < getPaddingLeft()) return getPaddingLeft();
                        if (left > getPaddingLeft() + mDragDistance)
                            return getPaddingLeft() + mDragDistance;
                        break;
                    case Right:
                        if (left > getPaddingLeft()) return getPaddingLeft();
                        if (left < getPaddingLeft() - mDragDistance)
                            return getPaddingLeft() - mDragDistance;
                        break;
                }
            } else if (getCurrentBottomView() == child) {

                switch (mCurrentDragEdge) {
                    case Top:
                    case Bottom:
                        return getPaddingLeft();
                    case Left:
                        if (mShowMode == ShowMode.PullOut) {
                            if (left > getPaddingLeft()) return getPaddingLeft();
                        }
                        break;
                    case Right:
                        if (mShowMode == ShowMode.PullOut) {
                            if (left < getMeasuredWidth() - mDragDistance) {
                                return getMeasuredWidth() - mDragDistance;
                            }
                        }
                        break;
                }
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy)
        {
            if (child == getSurfaceView()) {
                switch (mCurrentDragEdge) {
                    case Left:
                    case Right:
                        return getPaddingTop();
                    case Top:
                        if (top < getPaddingTop()) return getPaddingTop();
                        if (top > getPaddingTop() + mDragDistance)
                            return getPaddingTop() + mDragDistance;
                        break;
                    case Bottom:
                        if (top < getPaddingTop() - mDragDistance) {
                            return getPaddingTop() - mDragDistance;
                        }
                        if (top > getPaddingTop()) {
                            return getPaddingTop();
                        }
                }
            } else {
                View surfaceView = getSurfaceView();
                int surfaceViewTop = surfaceView == null ? 0 : surfaceView.getTop();
                switch (mCurrentDragEdge) {
                    case Left:
                    case Right:
                        return getPaddingTop();
                    case Top:
                        if (mShowMode == ShowMode.PullOut) {
                            if (top > getPaddingTop()) return getPaddingTop();
                        } else {
                            if (surfaceViewTop + dy < getPaddingTop())
                                return getPaddingTop();
                            if (surfaceViewTop + dy > getPaddingTop() + mDragDistance)
                                return getPaddingTop() + mDragDistance;
                        }
                        break;
                    case Bottom:
                        if (mShowMode == ShowMode.PullOut) {
                            if (top < getMeasuredHeight() - mDragDistance)
                                return getMeasuredHeight() - mDragDistance;
                        } else {
                            if (surfaceViewTop + dy >= getPaddingTop())
                                return getPaddingTop();
                            if (surfaceViewTop + dy <= getPaddingTop() - mDragDistance)
                                return getPaddingTop() - mDragDistance;
                        }
                }
            }
            return top;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId)
        {
            boolean result = child == getSurfaceView() || getBottomViews().contains(child);
            if (result) {
                isCloseBeforeDrag = getOpenStatus() == Status.Close;
            }
            return result;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDragDistance;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragDistance;
        }

        boolean isCloseBeforeDrag = true;

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //processHandRelease(xvel, yvel, isCloseBeforeDrag);
            for (SwipeListener l : mSwipeListeners) {
                l.onHandRelease(ModSwipeLayout.this, xvel, yvel);
            }

            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            View surfaceView = getSurfaceView();
            if (surfaceView == null) return;
            View currentBottomView = getCurrentBottomView();
            int evLeft = surfaceView.getLeft(),
                    evRight = surfaceView.getRight(),
                    evTop = surfaceView.getTop(),
                    evBottom = surfaceView.getBottom();
            if (changedView == surfaceView) {

                if (mShowMode == ShowMode.PullOut && currentBottomView != null) {
                    if (mCurrentDragEdge == DragEdge.Left || mCurrentDragEdge == DragEdge.Right) {
                        currentBottomView.offsetLeftAndRight(dx);
                    } else {
                        currentBottomView.offsetTopAndBottom(dy);
                    }
                }

            } else if (getBottomViews().contains(changedView)) {

                if (mShowMode == ShowMode.PullOut) {
                    surfaceView.offsetLeftAndRight(dx);
                    surfaceView.offsetTopAndBottom(dy);
                }
                else
                {

                }
            }

            dispatchSwipeEvent(evLeft, evTop, dx, dy);

            invalidate();

            captureChildrenBound();
        }
    };


    private void captureChildrenBound(){
        View currentBottomView = getCurrentBottomView();
        if(getOpenStatus()==Status.Close){
            mViewBoundCache.remove(currentBottomView);
            return;
        }

        View[] views = new View[]{getSurfaceView(), currentBottomView};
        for (View child : views) {
            Rect rect = mViewBoundCache.get(child);
            if(rect==null){
                rect = new Rect();
                mViewBoundCache.put(child, rect);
            }
            rect.left = child.getLeft();
            rect.top = child.getTop();
            rect.right = child.getRight();
            rect.bottom = child.getBottom();
        }
    }


    private int mEventCounter = 0;

    protected void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, int dx, int dy) {
        DragEdge edge = getDragEdge();
        boolean open = true;
        if (edge == DragEdge.Left) {
            if (dx < 0) open = false;
        } else if (edge == DragEdge.Right) {
            if (dx > 0) open = false;
        } else if (edge == DragEdge.Top) {
            if (dy < 0) open = false;
        } else if (edge == DragEdge.Bottom) {
            if (dy > 0) open = false;
        }

        dispatchSwipeEvent(surfaceLeft, surfaceTop, open);
    }

    protected void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, boolean open) {
        safeBottomView();
        Status status = getOpenStatus();

        if (!mSwipeListeners.isEmpty()) {
            mEventCounter++;
            for (SwipeListener l : mSwipeListeners) {
                if (mEventCounter == 1) {
                    if (open) {
                        l.onStartOpen(this);
                    } else {
                        l.onStartClose(this);
                    }
                }
                l.onUpdate(ModSwipeLayout.this, surfaceLeft - getPaddingLeft(), surfaceTop - getPaddingTop());
            }

            if (status == Status.Close) {
                for (SwipeListener l : mSwipeListeners) {
                    l.onClose(ModSwipeLayout.this);
                }
                mEventCounter = 0;
            }

            if (status == Status.Open) {
                View currentBottomView = getCurrentBottomView();
                if (currentBottomView != null) {
                    currentBottomView.setEnabled(true);
                }
                for (SwipeListener l : mSwipeListeners) {
                    l.onOpen(ModSwipeLayout.this);
                }
                mEventCounter = 0;
            }
        }
    }

    private void safeBottomView() {
        Status status = getOpenStatus();
        List<View> bottoms = getBottomViews();

        if (status == Status.Close) {
            for (View bottom : bottoms) {
                if (bottom != null && bottom.getVisibility() != INVISIBLE) {
                    bottom.setVisibility(INVISIBLE);
                }
            }
        } else {
            View currentBottomView = getCurrentBottomView();
            if (currentBottomView != null && currentBottomView.getVisibility() != VISIBLE) {
                currentBottomView.setVisibility(VISIBLE);
            }
        }
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public interface OnLayout
    {
        void onLayout(ModSwipeLayout v);
    }

    private List<OnLayout> mOnLayoutListeners;


    public void addDrag(DragEdge dragEdge, View child) {
        addDrag(dragEdge, child, null);
    }

    public void addDrag(DragEdge dragEdge, View child, ViewGroup.LayoutParams params) {
        if (child == null) return;

        if (params == null) {
            params = generateDefaultLayoutParams();
        }
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        int gravity = -1;
        switch (dragEdge) {
            case Left:
                gravity = Gravity.LEFT;
                break;
            case Right:
                gravity = Gravity.RIGHT;
                break;
            case Top:
                gravity = Gravity.TOP;
                break;
            case Bottom:
                gravity = Gravity.BOTTOM;
                break;
        }
        if (params instanceof FrameLayout.LayoutParams) {
            ((LayoutParams) params).gravity = gravity;
        }
        addView(child, 0, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child == null) return;
        int gravity = Gravity.NO_GRAVITY;
        try {
            gravity = (Integer) params.getClass().getField("gravity").get(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gravity > 0) {
            gravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));

            if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
                mDragEdges.put(DragEdge.Left, child);
            }
            if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
                mDragEdges.put(DragEdge.Right, child);
            }
            if ((gravity & Gravity.TOP) == Gravity.TOP) {
                mDragEdges.put(DragEdge.Top, child);
            }
            if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                mDragEdges.put(DragEdge.Bottom, child);
            }
        } else {
            for (Map.Entry<DragEdge, View> entry : mDragEdges.entrySet()) {
                if (entry.getValue() == null) {
                    //means used the drag_edge attr, the no gravity child should be use set
                    mDragEdges.put(entry.getKey(), child);
                    break;
                }
            }
        }
        if (child.getParent() == this) {
            return;
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        updateBottomViews();

        if (mOnLayoutListeners != null) for (int i = 0; i < mOnLayoutListeners.size(); i++) {
            mOnLayoutListeners.get(i).onLayout(this);
        }
    }

    void layoutPullOut()
    {
        View surfaceView = getSurfaceView();
        Rect surfaceRect = mViewBoundCache.get(surfaceView);
        if(surfaceRect == null) surfaceRect = computeSurfaceLayoutArea(false);
        if (surfaceView != null) {
            surfaceView.layout(surfaceRect.left, surfaceRect.top, surfaceRect.right, surfaceRect.bottom);
            bringChildToFront(surfaceView);
        }
        View currentBottomView = getCurrentBottomView();
        Rect bottomViewRect = mViewBoundCache.get(currentBottomView);
        if(bottomViewRect == null) bottomViewRect = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, surfaceRect);
        if (currentBottomView != null) {
            currentBottomView.layout(bottomViewRect.left, bottomViewRect.top, bottomViewRect.right, bottomViewRect.bottom);
        }
    }

    void layoutLayDown() {
        View surfaceView = getSurfaceView();
        Rect surfaceRect = mViewBoundCache.get(surfaceView);
        if(surfaceRect == null) surfaceRect = computeSurfaceLayoutArea(false);
        if (surfaceView != null) {
            surfaceView.layout(surfaceRect.left, surfaceRect.top, surfaceRect.right, surfaceRect.bottom);
            bringChildToFront(surfaceView);
        }
        View currentBottomView = getCurrentBottomView();
        Rect bottomViewRect = mViewBoundCache.get(currentBottomView);
        if(bottomViewRect == null) bottomViewRect = computeBottomLayoutAreaViaSurface(ShowMode.LayDown, surfaceRect);
        if (currentBottomView != null) {
            currentBottomView.layout(bottomViewRect.left, bottomViewRect.top, bottomViewRect.right, bottomViewRect.bottom);
        }
    }

    private boolean mIsBeingDragged;

    private void checkCanDrag(MotionEvent ev)
    {
        if (mIsBeingDragged) return;
        if (getOpenStatus() == Status.Middle) {
            mIsBeingDragged = true;
            return;
        }
        Status status = getOpenStatus();
        float distanceX = ev.getRawX() - sX;
        float distanceY = ev.getRawY() - sY;
        float angle = Math.abs(distanceY / distanceX);
        angle = (float) Math.toDegrees(Math.atan(angle));
        if (getOpenStatus() == Status.Close) {
            DragEdge dragEdge;
            if (angle < 45) {
                if (distanceX > 0 && isLeftSwipeEnabled()) {
                    dragEdge = DragEdge.Left;
                } else if (distanceX < 0 && isRightSwipeEnabled()) {
                    dragEdge = DragEdge.Right;
                } else return;

            } else {
                if (distanceY > 0 && isTopSwipeEnabled()) {
                    dragEdge = DragEdge.Top;
                } else if (distanceY < 0 && isBottomSwipeEnabled()) {
                    dragEdge = DragEdge.Bottom;
                } else return;
            }
            setCurrentDragEdge(dragEdge);
        }

        boolean doNothing = false;
        if (mCurrentDragEdge == DragEdge.Right) {
            boolean suitable = (status == Status.Open && distanceX > mTouchSlop)
                    || (status == Status.Close && distanceX < -mTouchSlop);
            suitable = suitable || (status == Status.Middle);

            if (angle > 30 || !suitable) {
                doNothing = true;
            }
        }

        if (mCurrentDragEdge == DragEdge.Left) {
            boolean suitable = (status == Status.Open && distanceX < -mTouchSlop)
                    || (status == Status.Close && distanceX > mTouchSlop);
            suitable = suitable || status == Status.Middle;

            if (angle > 30 || !suitable) {
                doNothing = true;
            }
        }

        if (mCurrentDragEdge == DragEdge.Top) {
            boolean suitable = (status == Status.Open && distanceY < -mTouchSlop)
                    || (status == Status.Close && distanceY > mTouchSlop);
            suitable = suitable || status == Status.Middle;

            if (angle < 60 || !suitable) {
                doNothing = true;
            }
        }

        if (mCurrentDragEdge == DragEdge.Bottom) {
            boolean suitable = (status == Status.Open && distanceY > mTouchSlop)
                    || (status == Status.Close && distanceY < -mTouchSlop);
            suitable = suitable || status == Status.Middle;

            if (angle < 60 || !suitable) {
                doNothing = true;
            }
        }
        mIsBeingDragged = !doNothing;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {

        if (!isSwipeEnabled()) {
            return false;
        }
        if (mClickToClose && getOpenStatus() == Status.Open && isTouchOnSurface(ev))
        {

            close();
            return true;
        }
        for (SwipeDenier denier : mSwipeDeniers) {
            if (denier != null && denier.shouldDenySwipe(ev)) {
                return false;
            }
        }

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDragHelper.processTouchEvent(ev);
                mIsBeingDragged = false;
                sX = ev.getRawX();
                sY = ev.getRawY();
                //if the swipe is in middle state(scrolling), should intercept the touch
                if (getOpenStatus() == Status.Middle) {
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                boolean beforeCheck = mIsBeingDragged;
                checkCanDrag(ev);
                if (mIsBeingDragged) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (!beforeCheck && mIsBeingDragged) {
                    return false;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mDragHelper.processTouchEvent(ev);
                break;
            default://handle other action, such as ACTION_POINTER_DOWN/UP
                mDragHelper.processTouchEvent(ev);
        }
        return mIsBeingDragged;
    }

    private float sX = -1, sY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (!isSwipeEnabled()) return super.onTouchEvent(event);

        int action = event.getActionMasked();
        gestureDetector.onTouchEvent(event);

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mDragHelper.processTouchEvent(event);
                sX = event.getRawX();
                sY = event.getRawY();


            case MotionEvent.ACTION_MOVE: {
                //the drag state and the direction are already judged at onInterceptTouchEvent
                checkCanDrag(event);
                if (mIsBeingDragged) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    mDragHelper.processTouchEvent(event);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mDragHelper.processTouchEvent(event);
                break;

            default:
                close();
            //handle other action, such as ACTION_POINTER_DOWN/UP
                mDragHelper.processTouchEvent(event);

        }

        return super.onTouchEvent(event) || mIsBeingDragged || action == MotionEvent.ACTION_DOWN;
    }


    public void setClickToClose(boolean mClickToClose) {
        this.mClickToClose = mClickToClose;
    }



    public boolean isSwipeEnabled() {
        return mSwipeEnabled;
    }

    public boolean isLeftSwipeEnabled() {
        View bottomView = mDragEdges.get(DragEdge.Left);
        return bottomView != null && bottomView.getParent() == this
                && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Left.ordinal()];
    }
    public boolean isRightSwipeEnabled()
    {
        View bottomView = mDragEdges.get(DragEdge.Right);
        return bottomView != null && bottomView.getParent() == this && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Right.ordinal()];
    }

    public boolean isTopSwipeEnabled() {
        View bottomView = mDragEdges.get(DragEdge.Top);
        return bottomView != null && bottomView.getParent() == this && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Top.ordinal()];
    }



    public boolean isBottomSwipeEnabled() {
        View bottomView = mDragEdges.get(DragEdge.Bottom);
        return bottomView != null && bottomView.getParent() == this
                && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Bottom.ordinal()];
    }



    private AdapterView getAdapterView() {
        ViewParent t = getParent();
        if (t instanceof AdapterView) {
            return (AdapterView) t;
        }
        return null;
    }



    OnClickListener clickListener;

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        clickListener = l;
    }

    OnLongClickListener longClickListener;

    @Override
    public void setOnLongClickListener(OnLongClickListener l)
    {
        super.setOnLongClickListener(l);
        longClickListener = l;
    }

    private Rect hitSurfaceRect;



    public boolean isTouchOnSurface(MotionEvent ev)
    {
        View surfaceView = getSurfaceView();

        if (surfaceView == null)
        {
            return false;
        }
        if (hitSurfaceRect == null)
        {
            hitSurfaceRect = new Rect();
        }
        surfaceView.getHitRect(hitSurfaceRect);
        return hitSurfaceRect.contains((int) ev.getX(), (int) ev.getY());

    }

    private GestureDetector gestureDetector = new GestureDetector(getContext(), new SwipeDetector());

    class SwipeDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            if (mClickToClose && isTouchOnSurface(e))
            {
                close();
            }

            return super.onSingleTapUp(e);
        }

    }

    public void setShowMode(ShowMode mode) {
        mShowMode = mode;
        requestLayout();
    }

    public DragEdge getDragEdge() {
        return mCurrentDragEdge;
    }


    public ShowMode getShowMode() {
        return mShowMode;
    }


    public View getSurfaceView() {
        if (getChildCount() == 0) return null;
        return getChildAt(getChildCount() - 1);
    }


    @Nullable
    public View getCurrentBottomView() {
        List<View> bottoms = getBottomViews();
        if (mCurrentDragEdge.ordinal() < bottoms.size()) {
            return bottoms.get(mCurrentDragEdge.ordinal());
        }
        return null;
    }


    public List<View> getBottomViews() {
        ArrayList<View> bottoms = new ArrayList<View>();
        for (DragEdge dragEdge : DragEdge.values()) {
            bottoms.add(mDragEdges.get(dragEdge));
        }
        return bottoms;
    }

    public enum Status {
        Middle,
        Open,
        Close
    }


    public Status getOpenStatus()
    {
        View surfaceView = getSurfaceView();
        if (surfaceView == null)
        {
            return Status.Close;
        }
        int surfaceLeft = surfaceView.getLeft();
        int surfaceTop = surfaceView.getTop();
        if (surfaceLeft == getPaddingLeft() && surfaceTop == getPaddingTop()) return Status.Close;

        if (surfaceLeft == (getPaddingLeft() - mDragDistance) || surfaceLeft == (getPaddingLeft() + mDragDistance)
                || surfaceTop == (getPaddingTop() - mDragDistance) || surfaceTop == (getPaddingTop() + mDragDistance))
            return Status.Open;

        return Status.Middle;
    }

    public void open() {
        open(true, true);
    }

    public void open(boolean smooth) {
        open(smooth, true);
    }

    public void open(boolean smooth, boolean notify)
    {
        View surface = getSurfaceView(), bottom = getCurrentBottomView();
        if (surface == null)
        {
            return;
        }
        int dx, dy;
        Rect rect = computeSurfaceLayoutArea(true);
        if (smooth)
        {
            mDragHelper.smoothSlideViewTo(surface, rect.left, rect.top);
        } else
        {
            dx = rect.left - surface.getLeft();
            dy = rect.top - surface.getTop();
            surface.layout(rect.left, rect.top, rect.right, rect.bottom);
            if (getShowMode() == ShowMode.PullOut) {
                Rect bRect = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rect);
                if (bottom != null) {
                    bottom.layout(bRect.left, bRect.top, bRect.right, bRect.bottom);
                }
            }
            if (notify)
            {
                dispatchSwipeEvent(rect.left, rect.top, dx, dy);
            } else
            {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void open(DragEdge edge) {
        setCurrentDragEdge(edge);
        open(true, true);
    }

    public void open(boolean smooth, DragEdge edge) {
        setCurrentDragEdge(edge);
        open(smooth, true);
    }

    public void open(boolean smooth, boolean notify, DragEdge edge) {
        setCurrentDragEdge(edge);
        open(smooth, notify);
    }

    public void close()
    {
        close(true, true);
    }

    public void close(boolean smooth) {
        close(smooth, true);
    }


    public void close(boolean smooth, boolean notify)
    {

        View surface = getSurfaceView();

        if (surface == null) {
            return;
        }

        if (smooth) {

        }
            //mDragHelper.smoothSlideViewTo(getSurfaceView(), getPaddingLeft(), getPaddingTop());
        else
        {

        }
        invalidate();
    }


    private Rect computeSurfaceLayoutArea(boolean open)
    {
        int l = getPaddingLeft(), t = getPaddingTop();
        if (open)
        {
            if (mCurrentDragEdge == DragEdge.Left)
                l = getPaddingLeft() + mDragDistance;
            else if (mCurrentDragEdge == DragEdge.Right)
                l = getPaddingLeft() - mDragDistance;
            else if (mCurrentDragEdge == DragEdge.Top)
                t = getPaddingTop() + mDragDistance;
            else t = getPaddingTop() - mDragDistance;

        }
        return new Rect(l, t, l + getMeasuredWidth(), t + getMeasuredHeight());
    }

    private Rect computeBottomLayoutAreaViaSurface(ShowMode mode, Rect surfaceArea)
    {
        Rect rect = surfaceArea;
        View bottomView = getCurrentBottomView();

        int bl = rect.left, bt = rect.top, br = rect.right, bb = rect.bottom;
        if (mode == ShowMode.PullOut) {
            if (mCurrentDragEdge == DragEdge.Left)
                bl = rect.left - mDragDistance;
            else if (mCurrentDragEdge == DragEdge.Right)
                bl = rect.right;
            else if (mCurrentDragEdge == DragEdge.Top)
                bt = rect.top - mDragDistance;
            else bt = rect.bottom;

            if (mCurrentDragEdge == DragEdge.Left || mCurrentDragEdge == DragEdge.Right) {
                bb = rect.bottom;
                br = bl + (bottomView == null ? 0 : bottomView.getMeasuredWidth());
            } else {
                bb = bt + (bottomView == null ? 0 : bottomView.getMeasuredHeight());
                br = rect.right;
            }
        }
        else if (mode == ShowMode.LayDown)
        {
            if (mCurrentDragEdge == DragEdge.Left)
                br = bl + mDragDistance;
            else if (mCurrentDragEdge == DragEdge.Right)
                bl = br - mDragDistance;
            else if (mCurrentDragEdge == DragEdge.Top)
                bb = bt + mDragDistance;
            else bt = bb - mDragDistance;

        }
        return new Rect(bl, bt, br, bb);

    }




    private int dp2px(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }



    private float getCurrentOffset() {
        if (mCurrentDragEdge == null) return 0;
        return mEdgeSwipesOffset[mCurrentDragEdge.ordinal()];
    }

    private void setCurrentDragEdge(DragEdge dragEdge) {
        mCurrentDragEdge = dragEdge;
        updateBottomViews();
    }

    private void updateBottomViews()
    {
        View currentBottomView = getCurrentBottomView();
        if (currentBottomView != null) {
            if (mCurrentDragEdge == DragEdge.Left || mCurrentDragEdge == DragEdge.Right) {
                mDragDistance = currentBottomView.getMeasuredWidth() - dp2px(getCurrentOffset());
            } else {
                mDragDistance = currentBottomView.getMeasuredHeight() - dp2px(getCurrentOffset());
            }
        }

        if (mShowMode == ShowMode.PullOut) {
            layoutPullOut();
        } else if (mShowMode == ShowMode.LayDown) {
            layoutLayDown();
        }

        safeBottomView();
    }
}