package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.jovel.prinventory.models.Toner;

/**
 * Created by Jovel on 7/5/2017.
 */

public class TonerViewFragment extends AppCompatDialogFragment {

    private Toner mToner;
    private ImageView mImage;
    private TextView mMake;
    private TextView mModel;
    private TextView mColor;
    private TextView mTModel;
    private TextView mBlack;
    private TextView mCyan;
    private TextView mYellow;
    private TextView mMagenta;
    private Database db;

    /*
    The received ID/Position from the adapter
     */
    public static TonerViewFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt("ID", id);

        TonerViewFragment frag = new TonerViewFragment();
        frag.setArguments(args);

        Log.i("newInstance ID: ", "This is the passed ID --> " + id);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view_toner, null);

        db = new Database(getActivity());

        Bundle args = getArguments();
        int mId = args.getInt("ID");

        /*
        mToner will be used to populate the fields for editing/updating
         */
        mToner = db.getToner(mId+1); // ID for toner will go here and populate textviews

        mImage = (ImageView)v.findViewById(R.id.fragment_view_toner_image);
        mImage.setImageResource(R.drawable.ic_toner);

        mMake = (TextView) v.findViewById(R.id.fragment_view_toner_make);
        String format = "Make:    " + mToner.getMake();
        mMake.setText(format);

        mModel = (TextView) v.findViewById(R.id.fragment_view_toner_model);
        format = "Model:   " + mToner.getModel();
        mModel.setText(format);

        mColor = (TextView) v.findViewById(R.id.fragment_view_toner_color);
        if (mToner.getColor() == 0){
            format = "Color:    B/W";
        }else {
            format = "Color:    Color";
        }

        mColor.setText(format);

        mTModel = (TextView) v.findViewById(R.id.fragment_view_toner_tmodel);
        format = "TModel:   " + mToner.getTonerModel();
        mTModel.setText(format);

        mBlack = (TextView) v.findViewById(R.id.fragment_view_toner_black);
        format = "Black:  " + mToner.getBlack();
        mBlack.setText(format);

        mCyan = (TextView) v.findViewById(R.id.fragment_view_toner_cyan);
        format = "Cyan:  " + mToner.getCyan();
        mCyan.setText(format);

        mYellow = (TextView) v.findViewById(R.id.fragment_view_toner_yellow);
        format = "Yellow:  " + mToner.getYellow();
        mYellow.setText(format);

        mMagenta = (TextView) v.findViewById(R.id.fragment_view_toner_magenta);
        format = "Magenta:  " + mToner.getMagenta();
        mMagenta.setText(format);

        if(mToner.getColor() == 0){
            mCyan.setVisibility(View.INVISIBLE);
            mYellow.setVisibility(View.INVISIBLE);
            mMagenta.setVisibility(View.INVISIBLE);
        }else{
            mCyan.setVisibility(View.VISIBLE);
            mYellow.setVisibility(View.VISIBLE);
            mMagenta.setVisibility(View.VISIBLE);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_view_toner)
                .setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
    }
}