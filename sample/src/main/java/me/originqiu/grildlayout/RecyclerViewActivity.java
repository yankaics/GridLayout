package me.originqiu.grildlayout;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import me.originqiu.library.GridLayout;

/**
 * Created by OriginQiu on 2015/11/27.
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    
    private ArrayList<String> mData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("hello" + i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                                                                    LinearLayoutManager.VERTICAL,
                                                                    false));
        mRecyclerView.setAdapter(new SimpleAdapter());
        
    }
    
    class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerViewActivity.this)
                                      .inflate(R.layout.layout_item,
                                               parent,
                                               false);
                                               
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            
            if (position % 2 == 0) {
                holder.mGridLayout.setChildCount(4);
            }
            else if (position % 3 == 0) {
                holder.mGridLayout.setChildCount(7);
            }
            else {
                holder.mGridLayout.setChildCount(9);
            }
            
            holder.mGridLayout.setChildClickCallback(new GridLayout.ChildClick() {
                @Override
                public void onChildClick(int position) {
                    Toast.makeText(RecyclerViewActivity.this,
                                   "" + position,
                                   Toast.LENGTH_SHORT)
                         .show();
                }
            });
        }
        
        @Override
        public int getItemCount() {
            return mData.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            public GridLayout mGridLayout;
            
            public ViewHolder(View itemView) {
                super(itemView);
                mGridLayout = (GridLayout) itemView.findViewById(R.id.grid_box);
            }
        }
        
    }
}
