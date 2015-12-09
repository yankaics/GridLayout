package me.originqiu.grildlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.originqiu.library.GridLayout;

/**
 * GridLayout use as single
 * Created by OriginQiu on 2015/11/27.
 */
public class SingleActivity extends AppCompatActivity {
    private GridLayout mGridLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        mGridLayout = (GridLayout) findViewById(R.id.grid_box);
        mGridLayout.setChildCount(7);
        mGridLayout.setChildViewClickCallback(new GridLayout.ChildViewClick() {
            @Override
            public void onChildClick(int position) {
                // do click event here
            }
        });
        ArrayList<View> views = mGridLayout.getAllChildView();
        for (View view : views) {
            Glide.with(this).load("https://www.baidu.com/img/baidu_jgylogo3.gif").into((ImageView)(view));
        }
        ImageView imageView = (ImageView) views.get(2);
        imageView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

    }
}
