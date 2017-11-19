package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.models.Printer;

/**
 * Created by Jovel on 7/5/2017.
 */

public class PrinterViewFragment extends AppCompatDialogFragment {

    private static final String ARG_KEY ="id";

    /*
    The received ID/Position from the adapter
     */
    public static PrinterViewFragment newInstance(int id){

        Bundle args = new Bundle();
        args.putInt(ARG_KEY, id);

        PrinterViewFragment frag = new PrinterViewFragment();
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view_printer, null);

        Database mDatabase = new Database(getActivity());

        Bundle args = getArguments();
        int id = args.getInt(ARG_KEY);

        //mPrinter will be used to populate the textviews
        Printer mPrinter = mDatabase.getPrinter(id);

        ImageView mIcon = (ImageView) v.findViewById(R.id.fragment_view_printer_image);
        mIcon.setImageResource(R.drawable.ic_printer);

        TextView mMake = (TextView) v.findViewById(R.id.fragment_view_printer_make);
        String format = "Make:   " + mPrinter.getMake();
        mMake.setText(format);

        TextView mModel = (TextView) v.findViewById(R.id.fragment_view_printer_model);
        format = "Model:  " + mPrinter.getModel();
        mModel.setText(format);

        TextView mSerial = (TextView) v.findViewById(R.id.fragment_view_printer_serial);
        format = "Serial:  " + mPrinter.getSerial();
        mSerial.setText(format);

        TextView mColor = (TextView) v.findViewById(R.id.fragment_view_printer_color);
        if(mPrinter.getColor()== 0){
            format = "Color:    B/W";
            mColor.setText(format);
        }else{
            format = "Color:    Color";
            mColor.setText(format);
        }

        TextView mStatus = (TextView) v.findViewById(R.id.fragment_view_printer_status);
        if(mPrinter.getStatus()== 0){
            format = "Status:  Inactive";
            mStatus.setText(format);
        }else{
            format = "Status:  Active";
            mStatus.setText(format);
        }

        TextView mOwner = (TextView) v.findViewById(R.id.fragment_view_printer_owner);
        format = "Owner:  " + mPrinter.getOwnership();
        mOwner.setText(format);

        TextView mDept = (TextView) v.findViewById(R.id.fragment_view_printer_dept);
        format = "Department:   " + mPrinter.getDepartment();
        mDept.setText(format);

        TextView mLocation = (TextView) v.findViewById(R.id.fragment_view_printer_location);
        format = "Location:  " + mPrinter.getLocation();
        mLocation.setText(format);

        TextView mFloor = (TextView) v.findViewById(R.id.fragment_view_printer_floor);
        format = "Floor:   " + mPrinter.getFloor();
        mFloor.setText(format);

        TextView mIp = (TextView) v.findViewById(R.id.fragment_view_printer_ip);
        format = "IP:  " + mPrinter.getIp();
        mIp.setText(format);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_view_printer)
                .setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
    }

}

