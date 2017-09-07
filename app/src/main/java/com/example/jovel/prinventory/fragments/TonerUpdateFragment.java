package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.models.Toner;

/**
 * Created by Jovel on 6/29/2017.
 */

public class TonerUpdateFragment extends AppCompatDialogFragment {

    private Toner mToner;
    private EditText mMake;
    private EditText mModel;
    private Spinner mColor;
    private EditText mTModel;
    private EditText mBlack;
    private EditText mCyan;
    private EditText mYellow;
    private EditText mMagenta;
    private Database db;

    /*
    The received ID/Position from the adapter
     */
    public static TonerUpdateFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt("ID", id);

        TonerUpdateFragment frag = new TonerUpdateFragment();
        frag.setArguments(args);

        Log.i("newInstance ID: ", "This is the passed ID --> " + id);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_toner, null);

        db = new Database(getActivity());

        Bundle args = getArguments();
        int mId = args.getInt("ID");

        /*
        mToner will be used to populate the fields for editing/updating
         */
        mToner = db.getToner(mId+1);

        mMake = (EditText) v.findViewById(R.id.fragment_update_toner_make);
        mMake.setText(mToner.getMake());
        mMake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToner.setMake(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mModel = (EditText) v.findViewById(R.id.fragment_update_toner_model);
        mModel.setText(mToner.getModel());
        mModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToner.setModel(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*
        Spinner for toggling between the type of toner being registered
         */
        mColor = (Spinner)v.findViewById(R.id.fragment_update_toner_color);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.toner_array_color, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mColor.setAdapter(adapter);

        mColor.setSelection(mToner.getColor());
        mColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mToner.setColor(position);

                if(mToner.getColor()== 0){
                    mCyan.setVisibility(View.INVISIBLE);
                    mYellow.setVisibility(View.INVISIBLE);
                    mMagenta.setVisibility(View.INVISIBLE);
                }else{
                    mCyan.setVisibility(View.VISIBLE);
                    mYellow.setVisibility(View.VISIBLE);
                    mMagenta.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mTModel = (EditText) v.findViewById(R.id.fragment_update_toner_tmodel);
        mTModel.setText(mToner.getTonerModel());
        mTModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToner.setTonerModel(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mBlack = (EditText) v.findViewById(R.id.fragment_update_toner_black);
        mBlack.setText(String.valueOf(mToner.getBlack()));
        mBlack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    mToner.setBlack(Integer.parseInt(s.toString()));
                }else{
                    s = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mCyan = (EditText) v.findViewById(R.id.fragment_update_toner_cyan);
        mCyan.setText(String.valueOf(mToner.getCyan()));
        mCyan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    mToner.setCyan(Integer.parseInt(s.toString()));
                }else{
                    s = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mYellow = (EditText) v.findViewById(R.id.fragment_update_toner_yellow);
        mYellow.setText(String.valueOf(mToner.getYellow()));
        mYellow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    mToner.setYellow(Integer.parseInt(s.toString()));
                }else{
                    s = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mMagenta = (EditText) v.findViewById(R.id.fragment_update_toner_magenta);
        mMagenta.setText(String.valueOf(mToner.getMagenta())); // double check this if any errors
        mMagenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    mToner.setMagenta(Integer.parseInt(s.toString()));
                }else{
                    s = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_update_toner)
                .setPositiveButton(R.string.btn_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        isTextEmpty(mMake);
                        isTextEmpty(mMake);
                        isTextEmpty(mTModel);
                        db.updateToner(mToner);

                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .create();
    }

    /*
    Checks to see if important fields were left
     empty/unanswered and sets text if not addressed
     */
    private boolean isTextEmpty(EditText et){
        String text = et.getText().toString();
        if(TextUtils.isEmpty(text)){
            et.setText("Not Specified");
        }
        return false;
    }
}

