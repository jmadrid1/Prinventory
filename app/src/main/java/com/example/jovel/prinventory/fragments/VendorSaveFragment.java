package com.example.jovel.prinventory.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.jovel.prinventory.models.Vendor;

/**
 * Created by Jovel on 6/30/2017.
 */

public class VendorSaveFragment extends AppCompatDialogFragment {

    private Vendor mVendor;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mStreet;
    private EditText mCity;
    private Spinner mState;
    private EditText mZipcode;
    private Database db;

    @Override
    public Dialog onCreateDialog(Bundle adddInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_vendor, null);
        mVendor = new Vendor();
        db = new Database(getActivity());

        mName = (EditText) v.findViewById(R.id.fragment_add_vendor_name);
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPhone = (EditText) v.findViewById(R.id.fragment_add_vendor_phone);
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEmail = (EditText) v.findViewById(R.id.fragment_add_vendor_email);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mStreet = (EditText) v.findViewById(R.id.fragment_add_vendor_street);
        mStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setStreet(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mCity = (EditText) v.findViewById(R.id.fragment_add_vendor_city);
        mCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setCity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*
        This spinner is for selecting the state of where the vendor is located
         */
        mState = (Spinner) v.findViewById(R.id.fragment_add_vendor_state);
        ArrayAdapter<CharSequence> statesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.states_array, R.layout.support_simple_spinner_dropdown_item);
        statesAdapter.setDropDownViewResource(R.layout.spinner_layout);
        mState.setAdapter(statesAdapter);

        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String state = mState.getSelectedItem().toString();
                mVendor.setState(state);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mZipcode = (EditText) v.findViewById(R.id.fragment_add_vendor_zipcode);
        mZipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setZipcode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_save_vendor)
                .setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        isTextEmpty(mName);
                        isTextEmpty(mEmail);
                        isTextEmpty(mStreet);
                        isTextEmpty(mCity);

                        db.addVendor(mVendor);

                    }
                })
                .setNegativeButton("Cancel", null)
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
