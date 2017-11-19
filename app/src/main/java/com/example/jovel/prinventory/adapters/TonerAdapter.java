package com.example.jovel.prinventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.models.Toner;
import com.example.jovel.prinventory.utils.OnTonerItemClickListener;

import java.util.List;

/**
 * Created by Jovel on 6/28/2017.
 */

public class TonerAdapter extends RecyclerView.Adapter<TonerHolder> {

    private Context mContext;
    private List<Toner> mToners;
    private OnTonerItemClickListener mOnTonerItemClickListener;

    public TonerAdapter(Context context, List<Toner> toners){
        mContext = context;
        mToners = toners;
        mOnTonerItemClickListener = (OnTonerItemClickListener)context;
    }

    @Override
    public TonerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_toner, parent, false);

        return new TonerHolder(v);
    }

    @Override
    public void onBindViewHolder(TonerHolder holder, int position) {

        Toner toner = mToners.get(position);

        holder.bindToner(toner, mOnTonerItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mToners.size();
    }

    public void setList(List<Toner> toners){
        mToners = toners;
    }

}
