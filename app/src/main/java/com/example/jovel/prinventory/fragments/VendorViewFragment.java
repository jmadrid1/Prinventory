package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.models.Vendor;

/**
 * Created by Jovel on 7/5/2017.
 */

public class VendorViewFragment extends AppCompatDialogFragment {

    private Vendor mVendor;
    private ImageView mImage;
    private ImageView mCall;
    private ImageView mMessage;
    private TextView mName;
    private TextView mPhone;
    private TextView mEmail;
    private TextView mStreet;
    private TextView mCity;
    private TextView mState;
    private TextView mZipcode;
    private Database db;

    /*
    The received ID/Position from the adapter
     */
    public static VendorViewFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt("ID", id);

        VendorViewFragment frag = new VendorViewFragment();
        frag.setArguments(args);

        Log.i("newInstance ID: ", "This is the passed ID --> " + id);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view_vendor, null);

        db = new Database(getActivity());

        Bundle args = getArguments();
        int mId = args.getInt("ID");

        /*
        mVendor will be used to populate the fields for editing/updating
         */
        mVendor = db.getVendor(mId+1); // ID for vendor will go here and populate textviews

        mImage = (ImageView)v.findViewById(R.id.fragment_view_vendor_image);
        mImage.setImageResource(R.drawable.ic_vendor);

        mCall =(ImageView)v.findViewById(R.id.fragment_view_vendor_call);
        mCall.setImageResource(R.drawable.ic_phone);
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:"+ mVendor.getPhone()));
                try {
                    startActivity(phoneIntent);
                }catch (Error e){
                    Log.e("Adapter", "Call didn't work");
                }

            }
        });

        mMessage = (ImageView)v.findViewById(R.id.fragment_view_vendor_message);
        mMessage.setImageResource(R.drawable.ic_message);
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + mVendor.getEmail()));

                try{
                    startActivity(emailIntent);
                }catch (ActivityNotFoundException e){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog
                            .setTitle(R.string.dialog_title_email_vendor)
                            .setMessage(R.string.dialog_message_email_vendor)
                            .setPositiveButton(R.string.btn_close, null)

                            .create()
                            .show();
                }
            }
        });

        mName = (TextView) v.findViewById(R.id.fragment_view_vendor_name);
        String format = "Name:    " + mVendor.getName();
        mName.setText(format);

        mPhone = (TextView) v.findViewById(R.id.fragment_view_vendor_phone);
        format = "Phone:    " + mVendor.getPhone();
        mPhone.setText(String.valueOf(format));

        mEmail = (TextView) v.findViewById(R.id.fragment_view_vendor_email);
        format = "Email:      " + mVendor.getEmail();
        mEmail.setText(format);

        mStreet = (TextView) v.findViewById(R.id.fragment_view_vendor_street);
        format = "Street:     " + mVendor.getStreet();
        mStreet.setText(format);

        mCity = (TextView) v.findViewById(R.id.fragment_view_vendor_city);
        format = "City:         " + mVendor.getCity();
        mCity.setText(format);

        mState = (TextView) v.findViewById(R.id.fragment_view_vendor_state);
        format = "State:       " + mVendor.getState();
        mState.setText(format);

        mZipcode = (TextView) v.findViewById(R.id.fragment_view_vendor_zipcode);
        format = "Zipcode:  " + mVendor.getZipcode();
        mZipcode.setText(String.valueOf(format));

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_view_vendor)
                .setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

}