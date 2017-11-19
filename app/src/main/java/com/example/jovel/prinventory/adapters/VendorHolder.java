package com.example.jovel.prinventory.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.models.Vendor;
import com.example.jovel.prinventory.utils.OnVendorItemClickListener;

/**
 * Created by Jovel on 11/16/2017.
 */

public class VendorHolder extends RecyclerView.ViewHolder {

    private TextView mName, mPhone, mEmail;
    private ImageView mIcon, mSettings;

    public VendorHolder(View view) {
        super(view);

        mName = (TextView) view.findViewById(R.id.row_vendor_name);
        mPhone = (TextView) view.findViewById(R.id.row_vendor_phone);
        mEmail = (TextView) view.findViewById(R.id.row_vendor_email);
        mIcon = (ImageView) view.findViewById(R.id.row_vendor_icon);
        mSettings = (ImageView) view.findViewById(R.id.row_vendor_setting);
    }

    public void bindVendor(final Vendor vendor, final OnVendorItemClickListener onVendorItemClickListener) {

        mName.setText("Name:    " + vendor.getName());
        mPhone.setText("Phone:    " + vendor.getPhone());
        mEmail.setText("Email:      " + vendor.getEmail());
        mIcon.setImageResource(R.drawable.ic_vendor);
        mSettings.setImageResource(R.drawable.ic_settings);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVendorItemClickListener.onClick(vendor, view);
            }
        });

    }

}
