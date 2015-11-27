package me.originqiu.grildlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.originqiu.library.GridLayout;

/**
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
        
    }
}
