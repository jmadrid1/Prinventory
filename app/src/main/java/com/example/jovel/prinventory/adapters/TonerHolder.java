package com.example.jovel.prinventory.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.models.Toner;
import com.example.jovel.prinventory.utils.OnTonerItemClickListener;

/**
 * Created by Jovel on 11/16/2017.
 */

public class TonerHolder extends RecyclerView.ViewHolder {

    private TextView mMake, mModel, mTModel;
    private ImageView mIcon, mColor, mSettings;

    public TonerHolder(View view){
        super(view);

        mMake = (TextView)view.findViewById(R.id.row_toner_make);
        mModel = (TextView)view.findViewById(R.id.row_toner_model);
        mTModel = (TextView)view.findViewById(R.id.row_toner_tmodel);
        mIcon = (ImageView)view.findViewById(R.id.row_toner_icon);
        mColor = (ImageView)view.findViewById(R.id.row_toner_color);
        mSettings = (ImageView) view.findViewById(R.id.row_toner_setting);
    }

    public void bindToner(final Toner toner, final OnTonerItemClickListener onTonerItemClickListener){

        mMake.setText("Make:      " + toner.getMake());
        mModel.setText("Model:    " + toner.getModel());
        mTModel.setText("TModel:  " + toner.getTonerModel());
        mIcon.setImageResource(R.drawable.ic_toner);

        if(toner.getColor()==0){
            mColor.setImageResource(R.drawable.ic_toner_row_bw); // black and white
        }else{
            mColor.setImageResource(R.drawable.ic_toner_row_color); //color
        }

        mSettings.setImageResource(R.drawable.ic_settings);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTonerItemClickListener.onClick(toner, v);
            }
        });

    }
}
