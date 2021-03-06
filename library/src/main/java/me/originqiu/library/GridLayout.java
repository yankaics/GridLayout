/**
 * The MIT License (MIT) Copyright (c) 2015 OriginQiu Permission is hereby
 * granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/
package me.originqiu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by OriginQiu on 2015/11/27.
 */
public class GridLayout extends ViewGroup {
    private static final int DEFAULT_COUNT = 3;
    
    private Paint mGridPaint;
    
    private int mColumnCount;
    
    private int mMaxChildren;
    
    private int childCount;
    
    /**
     * child view click callback
     */
    private ChildViewClick mChildClick;
    
    /**
     * when the view is just one ,set the special bounds
     */
    private boolean isOneView = false;
    
    private int oneViewWith = 120;
    
    private int oneViewHeight = 100;
    
    public GridLayout(Context context) {
        this(context, null);
    }
    
    public GridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public GridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                                                      R.styleable.GridLayout,
                                                      0,
                                                      defStyle);
                                                      
        int strokeWidth = a.getDimensionPixelSize(R.styleable.GridLayout_separatorWidth,
                                                  0);
        int strokeColor = a.getColor(R.styleable.GridLayout_separatorColor,
                                     Color.BLACK);
                                     
        int viewWidth = a.getDimensionPixelSize(R.styleable.GridLayout_oneViewHeight,
                                                0);
        int viewHeight = a.getDimensionPixelSize(R.styleable.GridLayout_oneViewHeight,
                                                 0);
                                                 
        if (viewHeight != 0 && viewWidth != 0) {
            isOneView = true;
            oneViewWith = viewWidth;
            oneViewHeight = viewHeight;
        }
        
        mColumnCount = a.getInteger(R.styleable.GridLayout_numColumns,
                                    DEFAULT_COUNT);
        mMaxChildren = mColumnCount * mColumnCount;
        childCount = mMaxChildren;
        a.recycle();
        
        // init paint
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(strokeColor);
        mGridPaint.setStrokeWidth(strokeWidth);
        Log.d("init view", "init view");
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // when there is just one view and you custom the view bounds
        if (childCount == 1 && isOneView) {
            int widthSpec = MeasureSpec.makeMeasureSpec(oneViewWith,
                                                        MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec.makeMeasureSpec(oneViewHeight,
                                                         MeasureSpec.EXACTLY);
            measureChildren(widthSpec, heightSpec);
            setMeasuredDimension(oneViewWith, oneViewHeight);
        }
        else {
            int widthSize, heightSize;
            // Get the width based on the measure specs
            widthSize = getDefaultSize(0, widthMeasureSpec);
            
            // Get the height based on measure specs
            heightSize = getDefaultSize(0, heightMeasureSpec);
            int majorDimension = widthSize;
            // Measure all child views
            int blockDimension = majorDimension / mColumnCount;
            int blockSpec = MeasureSpec.makeMeasureSpec(blockDimension,
                                                        MeasureSpec.EXACTLY);
            measureChildren(blockSpec, blockSpec);
            
            int singleHeight = majorDimension / mColumnCount;
            setMeasuredDimension(widthSize,
                                 childCount % mColumnCount == 0 ? singleHeight
                                                                  * (childCount
                                                                     / mColumnCount)
                                                                : singleHeight
                                                                  * (childCount
                                                                     / mColumnCount
                                                                     + 1));
        }
        
        Log.d("view onMeasure", "view onMeasure");
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (childCount > 1) {
            int row, col, left, top;
            for (int i = 0; i < getChildCount(); i++) {
                row = i / mColumnCount;
                col = i % mColumnCount;
                View child = getChildAt(i);
                left = col * child.getMeasuredWidth();
                top = row * child.getMeasuredHeight();
                
                child.layout(left,
                             top,
                             left + child.getMeasuredWidth(),
                             top + child.getMeasuredHeight());
            }
        }
        else if (childCount == 1) {
            View child = getChildAt(0);
            child.layout(0,
                         0,
                         child.getMeasuredWidth(),
                         child.getMeasuredHeight());
        }
        Log.d("view onLayout", "view onLayout");
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (childCount > 1) {
            // draw grid separator
            for (int i = getWidth()
                         / mColumnCount; i < getWidth(); i += (getWidth()
                                                               / mColumnCount)) {
                canvas.drawLine(i, 1, i, getHeight(), mGridPaint);
            }
            int count = childCount
                        % mColumnCount == 0 ? childCount / mColumnCount
                                            : childCount / mColumnCount + 1;
            for (int i = getHeight() / count; i < getHeight(); i += (getHeight()
                                                                     / count)) {
                canvas.drawLine(1, i, getWidth(), i, mGridPaint);
            }
        }
    }
    
    @Override
    public void addView(View child) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than "
                                            + mMaxChildren
                                            + " direct children");
        }
        Log.d("add view", "add view");
        super.addView(child);
    }
    
    @Override
    public void addView(View child, int index) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than "
                                            + mMaxChildren
                                            + " direct children");
        }
        Log.d("add view", "add view");
        super.addView(child, index);
    }
    
    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than "
                                            + mMaxChildren
                                            + " direct children");
        }
        Log.d("add view", "add view");
        super.addView(child, index, params);
    }
    
    @Override
    public void addView(View child, LayoutParams params) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than "
                                            + mMaxChildren
                                            + " direct children");
        }
        Log.d("add view", "add view");
        super.addView(child, params);
    }
    
    @Override
    public void addView(View child, int width, int height) {
        if (getChildCount() > mMaxChildren - 1) {
            throw new IllegalStateException("BoxGridLayout cannot have more than "
                                            + mMaxChildren
                                            + " direct children");
        }
        Log.d("add view", "add view");
        super.addView(child, width, height);
    }
    
    /**
     * child view click listener
     */
    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mChildClick != null)
                mChildClick.onChildClick((Integer) view.getTag());
        }
    };
    
    /**
     * set the view child, hide the other child
     *
     * @param count
     */
    public void setChildCount(int count) {
        childCount = count;
        if (count > mMaxChildren) {
            throw new IllegalStateException("BoxGridLayout cannot have more than "
                                            + mMaxChildren
                                            + " direct children");
        }
        else {
            for (int i = 0; i < mMaxChildren; i++) {
                if (i < count) {
                    getChildAt(i).setVisibility(VISIBLE);
                    getChildAt(i).setTag(i);
                    getChildAt(i).setOnClickListener(mOnClickListener);
                }
                else
                    getChildAt(i).setVisibility(GONE);
            }
            invalidate();
        }
    }
    
    /**
     * the interface for child view click
     */
    public interface ChildViewClick {
        void onChildClick(int position);
    }
    
    /**
     * set child view click call back
     *
     * @param childViewClickCallback
     */
    public void setChildViewClickCallback(ChildViewClick childViewClickCallback) {
        mChildClick = childViewClickCallback;
    }
    
    /**
     * get all child view in gridlayout
     */
    public ArrayList<View> getAllChildView() {
        ArrayList<View> childViews = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            childViews.add(i, getChildAt(i));
        }
        return childViews;
    }
    
}
