package com.example.jovel.prinventory.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.VendorActivity;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.VendorUpdateFragment;
import com.example.jovel.prinventory.fragments.VendorViewFragment;
import com.example.jovel.prinventory.models.Vendor;

import java.util.List;

/**
 * Created by Jovel on 9/2/2017.
 */

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorHolder> {

    private List<Vendor> mList;
    private Context mContext;


    public VendorAdapter(Context context, List<Vendor> list){
        mContext = context;
        mList = list;
    }


    @Override
    public VendorAdapter.VendorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vendor, parent, false);

        return new VendorHolder(v);
    }

    @Override
    public void onBindViewHolder(VendorAdapter.VendorHolder holder, int position) {
        Vendor vendor = mList.get(position);

        holder.bindVendor(vendor);

    }

    @Override
    public int getItemCount() {
        Log.i("Adapter List", String.valueOf(mList.size()));
        return mList.size();
    }

    public void setList(List<Vendor> vendors){
        mList = vendors;
    }



    private void displayPopup(final View view, final int position){
        View mv = view.findViewById(R.id.row_vendor_setting);
        PopupMenu pop = new PopupMenu(mContext, mv);
        MenuInflater inflate = pop.getMenuInflater();
        inflate.inflate(R.menu.context_menu_vendor , pop.getMenu());
        Log.e("POP!", "Popup position on list: " + position);

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){

                    case R.id.menu_view_vendor:
                        Database db = new Database(mContext);
                        VendorActivity va = (VendorActivity) mContext;
                        FragmentManager fm = va.getSupportFragmentManager();
                        VendorViewFragment vfrag = VendorViewFragment.newInstance(position);
                        vfrag.show(fm, "FRAG_View");
                        break;


                    case R.id.menu_call_vendor:
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                        phoneIntent.setData(Uri.parse("tel:"+ mList.get(position).getPhone()));
                        try {
                            va = (VendorActivity) mContext;
                            va.startActivity(phoneIntent);
                        }catch (Error e){
                            Log.e("Adapter", "Call didn't work");
                        }
                        break;

                    case R.id.menu_msg_vendor:

                        if(!hasConnection()){

                            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                            dialog.setTitle(R.string.dialog_title_network_status_vendor)
                                    .setMessage(R.string.dialog_message_network_connection)
                                    .setNeutralButton(R.string.btn_close, null);
                            dialog.show();

                        }else {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                            emailIntent.setData(Uri.parse("mailto:" + mList.get(position).getEmail()));

                            va = (VendorActivity) mContext;

                            try {
                                va.startActivity(emailIntent);
                            } catch (ActivityNotFoundException e) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                                dialog
                                        .setTitle(R.string.dialog_title_email_vendor)
                                        .setMessage(R.string.dialog_message_email_vendor)
                                        .setPositiveButton(R.string.btn_close, null)

                                        .create()
                                        .show();

                            }
                        }
                        break;

                    case R.id.menu_edit_vendor:

                        va = (VendorActivity) mContext;
                        fm = va.getSupportFragmentManager();
                        VendorUpdateFragment frag = VendorUpdateFragment.newInstance(position);
                        frag.show(fm, "FRAG_UPDATE");

                        Log.e("Edit", "You are editing printer# " + position);
                        break;

                    case R.id.menu_delete_vendor:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                        dialog.setTitle(R.string.dialog_title_delete_vendor)
                                .setMessage(R.string.dialog_message_delete_vendor)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        Database db = new Database(mContext);
                                        db.deleteVendor(mList.get(position));
                                    }
                                })
                                .setNegativeButton(R.string.btn_no, null)
                                .create()
                                .show();

                        Log.e("Details", "You are deleting vendor# " + position);
                        break;
                }
                return false;
            }
        });
        pop.show();

    }


    public class VendorHolder extends RecyclerView.ViewHolder {
        private TextView name, phone, email;
        private ImageView listImg, emailImg, phoneImg, settingsImg;
        private Vendor mVendor;

        public VendorHolder(View view){
            super(view);


            //check the imageviews emailImg and the other ones not instantiated
            name = (TextView)view.findViewById(R.id.row_vendor_name);
            phone = (TextView)view.findViewById(R.id.row_vendor_phone);
            email = (TextView)view.findViewById(R.id.row_vendor_email);
            listImg = (ImageView)view.findViewById(R.id.row_vendor_icon);
            settingsImg = (ImageView) view.findViewById(R.id.row_vendor_setting);

        }

        public void bindVendor(final Vendor vendor){
            mVendor = vendor;
            name.setText("Name:    " + mVendor.getName());
            phone.setText("Phone:    " + mVendor.getPhone());
            email.setText("Email:      " + mVendor.getEmail());
            listImg.setImageResource(R.drawable.ic_vendor);
            settingsImg.setImageResource(R.drawable.ic_settings);
            settingsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayPopup(v, getAdapterPosition());
                }
            });

        }
    }

    private boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

