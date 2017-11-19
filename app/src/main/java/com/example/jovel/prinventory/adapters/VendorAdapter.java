package com.example.jovel.prinventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.models.Vendor;
import com.example.jovel.prinventory.utils.OnVendorItemClickListener;

import java.util.List;

/**
 * Created by Jovel on 9/2/2017.
 */

public class VendorAdapter extends RecyclerView.Adapter<VendorHolder> {

    private Context mContext;
    private List<Vendor> mVendors;
    private OnVendorItemClickListener mOnVendorItemClickListener;

    public VendorAdapter(Context context, List<Vendor> vendors){
        mContext = context;
        mVendors = vendors;
        mOnVendorItemClickListener = (OnVendorItemClickListener)context;
    }

    @Override
    public VendorHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vendor, parent, false);

        return new VendorHolder(v);
    }

    @Override
    public void onBindViewHolder(VendorHolder holder, int position) {

        Vendor vendor = mVendors.get(position);

        holder.bindVendor(vendor, mOnVendorItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mVendors.size();
    }

    public void setList(List<Vendor> vendors){
        mVendors = vendors;
    }

}

