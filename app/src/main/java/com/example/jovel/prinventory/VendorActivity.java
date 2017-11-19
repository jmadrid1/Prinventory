package com.example.jovel.prinventory;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.adapters.VendorAdapter;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.VendorSaveFragment;
import com.example.jovel.prinventory.fragments.VendorUpdateFragment;
import com.example.jovel.prinventory.fragments.VendorViewFragment;
import com.example.jovel.prinventory.models.Vendor;
import com.example.jovel.prinventory.utils.OnVendorItemClickListener;

import java.util.List;

/**
 * Created by Jovel on 9/2/2017.
 */

public class VendorActivity extends AppCompatActivity implements OnVendorItemClickListener {

    private TextView mEmptyText;
    private ImageView mEmptyImage;
    private RecyclerView mRecyclerView;
    private VendorAdapter mVendorAdapter;
    private Database mDatabase;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_printer:

                    Intent intent = new Intent(VendorActivity.this, PrinterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                    break;

                case R.id.navigation_toner:

                    intent = new Intent(VendorActivity.this, TonerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                    break;

                case R.id.navigation_vendor:

                    setupAdapter();

                    break;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        mDatabase = new Database(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_vendor).setChecked(true);

        //Drawable & text is set whenever there are no list items
        mEmptyImage = (ImageView)findViewById(R.id.empty_img_vendor);
        mEmptyImage.setImageResource(R.drawable.ic_list_empty);
        mEmptyText = (TextView)findViewById(R.id.empty_text_vendor);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_vendor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mVendorAdapter);

        setupAdapter();
        showListStatus();

        FloatingActionButton mFabBtn = (FloatingActionButton) findViewById(R.id.fab_add_vendor);
        mFabBtn.setImageResource(R.drawable.ic_plus);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                FragmentManager fm = getSupportFragmentManager();
                VendorSaveFragment dialog = new VendorSaveFragment();
                dialog.show(fm, null);
            }
        });

    }

    /*
    Updates/Refreshes when list becomes focused (Adding, deleting, etc)
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus){
            setupAdapter();
            showListStatus();
        }
    }

    /*
    Sets the adapter and keeps list updated
     */
    private void setupAdapter(){

        List<Vendor> vendors = mDatabase.getAllVendors();

        if(mVendorAdapter == null){
            mVendorAdapter = new VendorAdapter(this, vendors);
            mRecyclerView.setAdapter(mVendorAdapter);
        } else {
            mVendorAdapter.setList(vendors);
            mVendorAdapter.notifyDataSetChanged();
        }
    }

    /*
    Toggles empty list drawable & text when the size of list changes
     */
    private void showListStatus(){

        if(mVendorAdapter.getItemCount() == 0){
            mRecyclerView.setVisibility(View.INVISIBLE);
            mEmptyImage.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyImage.setVisibility(View.INVISIBLE);
            mEmptyText.setVisibility(View.INVISIBLE);
        }
    }

    /*
    Navigates user back to Printers Tab while clearing history
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, PrinterActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    private boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void displayPopup(final Vendor vendor, final View view){

        PopupMenu pop = new PopupMenu(this, view);
        MenuInflater inflater = pop.getMenuInflater();
        inflater.inflate(R.menu.context_menu_vendor , pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){

                    case R.id.menu_view_vendor:

                        FragmentManager fm = getSupportFragmentManager();
                        VendorViewFragment vfrag = VendorViewFragment.newInstance(vendor.getId());
                        vfrag.show(fm, null);

                        break;


                    case R.id.menu_call_vendor:

                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                        phoneIntent.setData(Uri.parse("tel:"+ vendor.getPhone()));

                        try {
                            startActivity(phoneIntent);
                        }catch (Error e){
                            Log.e("Vendor Phone Intent", "Call didn't work");
                        }

                        break;

                    case R.id.menu_msg_vendor:

                        if(!hasConnection()){

                            AlertDialog.Builder dialog = new AlertDialog.Builder(VendorActivity.this);
                            dialog.setTitle(R.string.dialog_title_network_status_vendor)
                                    .setMessage(R.string.dialog_message_network_connection)
                                    .setNeutralButton(R.string.btn_close, null);
                            dialog.show();

                        }else {

                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                            emailIntent.setData(Uri.parse("mailto:" + vendor.getEmail()));

                            try {
                                startActivity(emailIntent);
                            } catch (ActivityNotFoundException e) {

                                AlertDialog.Builder dialog = new AlertDialog.Builder(VendorActivity.this);

                                dialog.setTitle(R.string.dialog_title_email_vendor)
                                        .setMessage(R.string.dialog_message_email_vendor)
                                        .setPositiveButton(R.string.btn_close, null)
                                        .create()
                                        .show();
                            }
                        }

                        break;

                    case R.id.menu_edit_vendor:

                        fm = getSupportFragmentManager();
                        VendorUpdateFragment frag = VendorUpdateFragment.newInstance(vendor.getId());
                        frag.show(fm, null);

                        break;

                    case R.id.menu_delete_vendor:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(VendorActivity.this);
                        dialog.setTitle(R.string.dialog_title_delete_vendor)
                                .setMessage(R.string.dialog_message_delete_vendor)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        mDatabase.deleteVendor(vendor.getId());
                                    }
                                })
                                .setNegativeButton(R.string.btn_no, null)
                                .create()
                                .show();

                        break;
                }
                return false;
            }
        });
        pop.show();

    }

    @Override
    public void onClick(Vendor vendor, View view) {
        displayPopup(vendor, view);
    }
}
