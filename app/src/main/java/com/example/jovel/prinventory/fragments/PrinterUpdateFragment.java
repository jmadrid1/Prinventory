package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class PrinterUpdateFragment extends AppCompatDialogFragment {

    private static final String ARG_KEY ="id";

    private Printer mPrinter;
    private EditText mMake, mModel, mSerial, mOwner, mDept, mLocation, mFloor;
    private Database mDatabase;

    /*
    The received ID/Position from the adapter
     */
    public static PrinterUpdateFragment newInstance(int id){

        Bundle args = new Bundle();
        args.putInt(ARG_KEY, id);

        PrinterUpdateFragment frag = new PrinterUpdateFragment();
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_printer, null);

        mDatabase = new Database(getActivity());

        Bundle args = getArguments();
        int id = args.getInt(ARG_KEY);

        // mPrinter will be used to populate the fields for editing/updating
        mPrinter = mDatabase.getPrinter(id);

        mMake = (EditText) v.findViewById(R.id.fragment_update_printer_make);
        mMake.setText(mPrinter.getMake());
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

        mModel = (EditText) v.findViewById(R.id.fragment_update_printer_model);
        mModel.setText(mPrinter.getModel());
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

        mSerial = (EditText) v.findViewById(R.id.fragment_update_printer_serial);
        mSerial.setText(mPrinter.getSerial());
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

        // This spinner is for specifying if a printer utilizes colored toner or only black & white toner
        Spinner mColor = (Spinner)v.findViewById(R.id.fragment_update_printer_color);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.toner_array_color, R.layout.support_simple_spinner_dropdown_item);
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mColor.setAdapter(colorAdapter);

        mColor.setSelection(mPrinter.getColor());
        mColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrinter.setColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // This spinner is for identifying a newly added printer as active or inactive
        Spinner mStatus = (Spinner)v.findViewById(R.id.fragment_update_printer_status);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_array, R.layout.support_simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mStatus.setAdapter(statusAdapter);

        mStatus.setSelection(mPrinter.getStatus());
        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrinter.setStatus(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mOwner = (EditText) v.findViewById(R.id.fragment_update_printer_owner);
        mOwner.setText(mPrinter.getOwnership());
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

        mDept = (EditText) v.findViewById(R.id.fragment_update_printer_dept);
        mDept.setText(mPrinter.getDepartment());
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

        mLocation = (EditText) v.findViewById(R.id.fragment_update_printer_location);
        mLocation.setText(mPrinter.getLocation());
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

        mFloor = (EditText) v.findViewById(R.id.fragment_update_printer_floor);
        mFloor.setText(mPrinter.getFloor());
        mFloor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrinter.setFloor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EditText mIp = (EditText) v.findViewById(R.id.fragment_update_printer_ip);
        mIp.setText(String.valueOf(mPrinter.getIp())); // double check this if any errors
        mIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    mPrinter.setIp(Integer.parseInt(s.toString()));
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
                .setTitle(R.string.dialog_title_update_printer)
                .setPositiveButton(R.string.btn_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        checkTextField(mMake);
                        checkTextField(mModel);
                        checkTextField(mSerial);
                        checkTextField(mOwner);
                        checkTextField(mDept);
                        checkTextField(mLocation);
                        checkTextField(mFloor);

                        mDatabase.updatePrinter(mPrinter);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .create();
    }

    /*
    Checks to see if important fields were left
     empty/unanswered and sets text if not addressed
     */
    private void checkTextField(EditText et){
        String text = et.getText().toString();
        if(TextUtils.isEmpty(text)){
            et.setText("Not Specified");
        }
    }
}

