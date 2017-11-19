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
import com.example.jovel.prinventory.models.Vendor;

/**
 * Created by Jovel on 6/30/2017.
 */

public class VendorUpdateFragment extends AppCompatDialogFragment {

    private static final String ARG_KEY ="id";

    private Vendor mVendor;
    private EditText mName, mPhone, mEmail, mStreet, mCity, mZipcode;
    private Spinner mState;

    private Database db;

    /*
    The received ID/Position from the adapter
     */
    public static VendorUpdateFragment newInstance(int id){

        Bundle args = new Bundle();
        args.putInt(ARG_KEY, id);

        VendorUpdateFragment frag = new VendorUpdateFragment();
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_vendor, null);

        db = new Database(getActivity());

        Bundle args = getArguments();
        int id = args.getInt(ARG_KEY);

        //mVendor will be used to populate the fields for editing/updating
        mVendor = db.getVendor(id); // ID for printer will go here and populate textviews

        mName = (EditText) v.findViewById(R.id.fragment_update_vendor_name);
        mName.setText(mVendor.getName());
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

        mPhone = (EditText) v.findViewById(R.id.fragment_update_vendor_phone);
        mPhone.setText(String.valueOf(mVendor.getPhone()));
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

        mEmail = (EditText) v.findViewById(R.id.fragment_update_vendor_email);
        mEmail.setText(mVendor.getEmail());
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

        mStreet = (EditText) v.findViewById(R.id.fragment_update_vendor_street);
        mStreet.setText(mVendor.getStreet());
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

        mCity = (EditText) v.findViewById(R.id.fragment_update_vendor_city);
        mCity.setText(mVendor.getCity());
        mCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setCity((s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // This spinner is for selecting the state of where the vendor is located

        mState = (Spinner) v.findViewById(R.id.fragment_update_vendor_state);
        ArrayAdapter<CharSequence> statesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.states_array, R.layout.support_simple_spinner_dropdown_item);
        statesAdapter.setDropDownViewResource(R.layout.spinner_layout);
        mState.setAdapter(statesAdapter);

        mState.setSelection(getSpinnerValue(mState, mVendor.getState()));
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

        mZipcode = (EditText) v.findViewById(R.id.fragment_update_vendor_zipcode);
        mZipcode.setText(String.valueOf(mVendor.getZipcode()));
        mZipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mVendor.setZipcode(s.toString()); //double check this
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title_update_vendor)
                .setPositiveButton(R.string.btn_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        checkTextField(mName);
                        checkTextField(mPhone);
                        checkTextField(mEmail);
                        checkTextField(mStreet);
                        checkTextField(mCity);
                        checkTextField(mZipcode);

                        db.updateVendor(mVendor);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .create();
    }

    /*
    Used for presenting the correct selected item on the
    spinner from the database's data for the vendor
     */
    private int getSpinnerValue(Spinner spinner, String target){

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(target)){
                index = i;
                break;
            }
        }
        return index;
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

