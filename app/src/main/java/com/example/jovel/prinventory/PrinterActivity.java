package com.example.jovel.prinventory;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.adapters.PrinterAdapter;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.PrinterSaveFragment;
import com.example.jovel.prinventory.fragments.PrinterUpdateFragment;
import com.example.jovel.prinventory.fragments.PrinterViewFragment;
import com.example.jovel.prinventory.models.Printer;
import com.example.jovel.prinventory.utils.OnPrinterItemClickListener;

import java.util.List;

public class PrinterActivity extends AppCompatActivity implements OnPrinterItemClickListener{

    private ImageView mEmptyImage;
    private TextView mEmptyText;
    private RecyclerView mRecyclerView;
    private PrinterAdapter mPrinterAdapter;
    private Database mDatabase;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_printer:

                    setupAdapter();
                    break;

                case R.id.navigation_toner:

                    Intent intent = new Intent(PrinterActivity.this, TonerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                    break;

                case R.id.navigation_vendor:

                    intent = new Intent(PrinterActivity.this, VendorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                    break;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_printer).setChecked(true);

        mDatabase = new Database(this);

        //Drawable & text is set whenever there are no list items
        mEmptyImage = (ImageView)findViewById(R.id.empty_img_printer);
        mEmptyImage.setImageResource(R.drawable.ic_list_empty);
        mEmptyText = (TextView)findViewById(R.id.empty_text_printer);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_printer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupAdapter();
        showListStatus();

        FloatingActionButton mFabBtn = (FloatingActionButton) findViewById(R.id.fab_add_printer);
        mFabBtn.setImageResource(R.drawable.ic_plus);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                PrinterSaveFragment dialog = new PrinterSaveFragment();
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

        List<Printer> printers = mDatabase.getAllPrinters();

        if(mPrinterAdapter == null){
            mPrinterAdapter = new PrinterAdapter(this, printers);
            mRecyclerView.setAdapter(mPrinterAdapter);
        } else {
            mPrinterAdapter.setList(printers);
            mPrinterAdapter.notifyDataSetChanged();
        }
    }

    /*
    Toggles empty list drawable & text when the size of list changes
     */
    private void showListStatus(){

        if(mPrinterAdapter.getItemCount() == 0){
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
    Exits the application when on Printers Tab
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void displayPopup(final Printer printer, View v){

        PopupMenu pop = new PopupMenu(PrinterActivity.this, v);
        MenuInflater inflater = pop.getMenuInflater();
        inflater.inflate(R.menu.context_menu_printer , pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){

                    case R.id.menu_view_printer:

                        FragmentManager fm = getSupportFragmentManager();
                        PrinterViewFragment vfrag = PrinterViewFragment.newInstance(printer.getId());
                        vfrag.show(fm, null);
                        break;

                    case R.id.menu_edit_printer:

                        fm = getSupportFragmentManager();
                        PrinterUpdateFragment ufrag = PrinterUpdateFragment.newInstance(printer.getId());
                        ufrag.show(fm, null);

                        break;

                    case R.id.menu_delete_printer:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(PrinterActivity.this);
                        dialog.setTitle(R.string.dialog_title_delete_printer)
                                .setMessage(R.string.dialog_message_delete_printer)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        mDatabase.deletePrinter(printer.getId());
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
    public void onClick(final Printer printer, View view){
        displayPopup(printer, view);
    }

}
