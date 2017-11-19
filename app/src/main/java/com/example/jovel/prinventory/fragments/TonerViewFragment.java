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
import com.example.jovel.prinventory.models.Toner;

/**
 * Created by Jovel on 7/5/2017.
 */

public class TonerViewFragment extends AppCompatDialogFragment {

    private static final String ARG_KEY ="id";

    /*
    The received ID/Position from the adapter
     */
    public static TonerViewFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt(ARG_KEY, id);

        TonerViewFragment frag = new TonerViewFragment();
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view_toner, null);

        Database mDatabase = new Database(getActivity());

        Bundle args = getArguments();
        int id = args.getInt(ARG_KEY);

        //mToner will be used to populate the textviews
        Toner mToner = mDatabase.getToner(id);

        ImageView mIcon = (ImageView) v.findViewById(R.id.fragment_view_toner_image);
        mIcon.setImageResource(R.drawable.ic_toner);

        TextView mMake = (TextView) v.findViewById(R.id.fragment_view_toner_make);
        String format = "Make:    " + mToner.getMake();
        mMake.setText(format);

        TextView mModel = (TextView) v.findViewById(R.id.fragment_view_toner_model);
        format = "Model:   " + mToner.getModel();
        mModel.setText(format);

        TextView mColor = (TextView) v.findViewById(R.id.fragment_view_toner_color);
        if (mToner.getColor() == 0){
            format = "Color:    B/W";
        }else {
            format = "Color:    Color";
        }

        mColor.setText(format);

        TextView mTModel = (TextView) v.findViewById(R.id.fragment_view_toner_tmodel);
        format = "TModel:   " + mToner.getTonerModel();
        mTModel.setText(format);

        TextView mBlack = (TextView) v.findViewById(R.id.fragment_view_toner_black);
        format = "Black:  " + mToner.getBlack();
        mBlack.setText(format);

        TextView mCyan = (TextView) v.findViewById(R.id.fragment_view_toner_cyan);
        format = "Cyan:  " + mToner.getCyan();
        mCyan.setText(format);

        TextView mYellow = (TextView) v.findViewById(R.id.fragment_view_toner_yellow);
        format = "Yellow:  " + mToner.getYellow();
        mYellow.setText(format);

        TextView mMagenta = (TextView) v.findViewById(R.id.fragment_view_toner_magenta);
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