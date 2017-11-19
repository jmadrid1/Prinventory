package com.example.jovel.prinventory.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.models.Printer;
import com.example.jovel.prinventory.utils.OnPrinterItemClickListener;

/**
 * Created by Jovel on 11/15/2017.
 */

public class PrinterHolder extends RecyclerView.ViewHolder {

    private TextView mMake, mModel, mDept, mIp;
    private ImageView mIcon, mColor, mStatus, mSettings;

    public PrinterHolder(View view){
        super(view);

        mMake = (TextView)view.findViewById(R.id.row_printer_make);
        mModel = (TextView)view.findViewById(R.id.row_printer_model);
        mDept = (TextView)view.findViewById(R.id.row_printer_dept);
        mIp = (TextView)view.findViewById(R.id.row_printer_ip);
        mIcon = (ImageView)view.findViewById(R.id.row_printer_icon);
        mColor = (ImageView)view.findViewById(R.id.row_printer_color);
        mStatus = (ImageView)view.findViewById(R.id.row_printer_status);
        mSettings = (ImageView)view.findViewById(R.id.row_printer_setting);
    }

    public void bindPrinter(final Printer printer, final OnPrinterItemClickListener onPrinterItemClickListener){

        mMake.setText("Make:    " + printer.getMake());
        mModel.setText("Model:   " + printer.getModel());
        mDept.setText("Department:   " + printer.getDepartment());
        mIp.setText("IP:    " + String.valueOf(printer.getIp()));
        mIcon.setImageResource(R.drawable.ic_printer);

        if (printer.getColor() == 0){
            mColor.setImageResource(R.drawable.ic_toner_row_bw);
        }else{
            mColor.setImageResource(R.drawable.ic_toner_row_color);
        }

        if (printer.getStatus() == 0){
            mStatus.setImageResource(R.drawable.ic_status_inactive);
        }else{
            mStatus.setImageResource(R.drawable.ic_status_active);
        }

        mSettings.setImageResource(R.drawable.ic_settings);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPrinterItemClickListener.onClick(printer, view);
            }
        });

    }

}
