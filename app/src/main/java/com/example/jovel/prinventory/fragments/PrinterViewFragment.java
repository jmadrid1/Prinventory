package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
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

    private Printer mPrinter;
    private ImageView mImage;
    private TextView mMake;
    private TextView mModel;
    private TextView mSerial;
    private TextView mColor;
    private TextView mStatus;
    private TextView mOwner;
    private TextView mDept;
    private TextView mLocation;
    private TextView mFloor;
    private TextView mIp;
    private Database db;

    /*
    The received ID/Position from the adapter
     */
    public static PrinterViewFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt("ID", id);

        PrinterViewFragment frag = new PrinterViewFragment();
        frag.setArguments(args);

        Log.i("newInstance ID: ", "This is the passed ID --> " + id);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view_printer, null);

        db = new Database(getActivity());

        Bundle args = getArguments();
        int mId = args.getInt("ID");

        /*
        mPrinter will be used to populate the fields for editing/updating
         */
        mPrinter = db.getPrinter(mId+1);

        mImage = (ImageView)v.findViewById(R.id.fragment_view_printer_image);
        mImage.setImageResource(R.drawable.ic_printer);

        mMake = (TextView) v.findViewById(R.id.fragment_view_printer_make);
        String format = "Make:   " + mPrinter.getMake();
        mMake.setText(format);

        mModel = (TextView) v.findViewById(R.id.fragment_view_printer_model);
        format = "Model:  " + mPrinter.getModel();
        mModel.setText(format);

        mSerial = (TextView) v.findViewById(R.id.fragment_view_printer_serial);
        format = "Serial:  " + mPrinter.getSerial();
        mSerial.setText(format);

        mColor = (TextView)v.findViewById(R.id.fragment_view_printer_color);
        if(mPrinter.getColor()== 0){
            format = "Color:    B/W";
            mColor.setText(format);
        }else{
            format = "Color:    Color";
            mColor.setText(format);
        }

        mStatus = (TextView)v.findViewById(R.id.fragment_view_printer_status);
        if(mPrinter.getStatus()== 0){
            format = "Status:  Inactive";
            mStatus.setText(format);
        }else{
            format = "Status:  Active";
            mStatus.setText(format);
        }

        mOwner = (TextView) v.findViewById(R.id.fragment_view_printer_owner);
        format = "Owner:  " + mPrinter.getOwnership();
        mOwner.setText(format);

        mDept = (TextView) v.findViewById(R.id.fragment_view_printer_dept);
        format = "Department:   " + mPrinter.getDepartment();
        mDept.setText(format);

        mLocation = (TextView) v.findViewById(R.id.fragment_view_printer_location);
        format = "Location:  " + mPrinter.getLocation();
        mLocation.setText(format);

        mFloor = (TextView) v.findViewById(R.id.fragment_view_printer_floor);
        format = "Floor:   " + mPrinter.getFloor();
        mFloor.setText(format);

        mIp = (TextView) v.findViewById(R.id.fragment_view_printer_ip);
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

