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
import com.example.jovel.prinventory.models.Printer;

/**
 * Created by Jovel on 6/28/2017.
 */

public class PrinterSaveFragment extends AppCompatDialogFragment {

    private Printer mPrinter;
    private EditText mMake;
    private EditText mModel;
    private EditText mSerial;
    private Spinner mColor;
    private Spinner mStatus;
    private EditText mOwner;
    private EditText mDept;
    private EditText mLocation;
    private EditText mFloor;
    private EditText mIp;
    private Database db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_printer, null);
        mPrinter = new Printer();
        db = new Database(getActivity());

        mMake = (EditText)v.findViewById(R.id.fragment_add_printer_make);
        mMake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setMake(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mModel = (EditText)v.findViewById(R.id.fragment_add_printer_model);
        mModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setModel(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSerial = (EditText)v.findViewById(R.id.fragment_add_printer_serial);
        mSerial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setSerial(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*
        This spinner is for specifying if a printer utilizes colored toner or only black & white toner
         */
        mColor = (Spinner)v.findViewById(R.id.fragment_add_printer_color);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.toner_array_color, R.layout.support_simple_spinner_dropdown_item);
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mColor.setAdapter(colorAdapter);

        mColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrinter.setColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
        This spinner is for identifying a newly added printer as active or inactive
         */
        mStatus = (Spinner)v.findViewById(R.id.fragment_add_printer_status);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_array, R.layout.support_simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mStatus.setAdapter(statusAdapter);

        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrinter.setStatus(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mOwner = (EditText)v.findViewById(R.id.fragment_add_printer_owner);
        mOwner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setOwnership(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mDept = (EditText)v.findViewById(R.id.fragment_add_printer_dept);
        mDept.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setDepartment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mLocation =(EditText)v.findViewById(R.id.fragment_add_printer_location);
        mLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mFloor = (EditText)v.findViewById(R.id.fragment_add_printer_floor);
        mFloor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setFloor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {  }
        });

        mIp = (EditText)v.findViewById(R.id.fragment_add_printer_ip);

        /*
         * Checks to see if IP field is empty and is so assigns a zero alternatively
         */
        String text = mIp.getText().toString();
        if(text.length()== 0){
            text = "0";
        }

        mIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0) {
                    mPrinter.setIp(Integer.parseInt(s.toString()));
                }else{
                    s = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_save_printer)
                .setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        isTextEmpty(mMake);
                        isTextEmpty(mModel);
                        isTextEmpty(mSerial);
                        isTextEmpty(mOwner);
                        isTextEmpty(mDept);
                        isTextEmpty(mLocation);
                        isTextEmpty(mFloor);

                        db.addPrinter(mPrinter);
                        Log.d("Saving Frag", mMake.getText().toString());
                        Log.i("Saving", "Printer was added.");

                    }
                })

                .setNegativeButton(R.string.btn_cancel ,null)
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

