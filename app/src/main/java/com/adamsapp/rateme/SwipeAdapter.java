package com.adamsapp.rateme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 10-Jul-17.
 */

public class SwipeAdapter extends BaseAdapter {

    private String[] mData={"Adam","John","Fils","Gates","Queen","Adam","John","Fils","Gates","Queen"};
    private int[] pics={R.drawable.queen,R.drawable.cindy,R.drawable.mil,R.drawable.prosperline,R.drawable.queen2,
            R.drawable.queen,R.drawable.cindy,R.drawable.mil,R.drawable.prosperline,R.drawable.queen2, };
    Context c;

    public SwipeAdapter(){

    }

    public SwipeAdapter(Context c) {
        this.c = c;
    }

    public SwipeAdapter(String[] mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       // LayoutInflater inflater = getL
        view =LayoutInflater.from(c).inflate(R.layout.single_card_layout,viewGroup,false);
        TextView textView = (TextView)view.findViewById(R.id.hot_user_name_age);
        textView.setText(mData[i]);
        ImageView image = (ImageView)view.findViewById(R.id.hot_image);
        image.setImageResource(pics[i]);
        return view;
    }
}
