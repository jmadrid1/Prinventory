package com.example.jovel.prinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.adapters.PrinterAdapter;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.PrinterSaveFragment;
import com.example.jovel.prinventory.models.Printer;

import java.util.List;

public class PrinterActivity extends FragmentActivity {

    private static final String DIALOG_SAVE ="DialogSave";

    private FloatingActionButton mFabBtn;
    private ImageView mEmptyImage;
    private TextView mEmptyText;
    private RecyclerView mRecyclerView;
    private PrinterAdapter mPrinterAdapter;
    private Database db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_printer:

                    updateList();
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

        /*
        Drawable & text is set whenever there are no list items
         */
        mEmptyImage = (ImageView)findViewById(R.id.empty_img_printer);
        mEmptyImage.setImageResource(R.drawable.ic_list_empty);
        mEmptyText = (TextView)findViewById(R.id.empty_text_printer);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_printer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mPrinterAdapter);

        updateList();
        isListEmpty();

        mFabBtn = (FloatingActionButton) findViewById(R.id.fab_add_printer);
        mFabBtn.setImageResource(R.drawable.ic_plus);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                PrinterSaveFragment dialog = new PrinterSaveFragment();
                dialog.show(fm, DIALOG_SAVE);

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
            updateList();
            isListEmpty();
        }
    }

    /*
    Retrieves data from DB to populate list
     */
    private void updateList(){
        db = new Database(this);
        List<Printer> printers = db.getAllPrinters();

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
    private Boolean isListEmpty(){
        if(mPrinterAdapter.getItemCount() == 0){
            mRecyclerView.setVisibility(View.INVISIBLE);
            mEmptyImage.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyImage.setVisibility(View.INVISIBLE);
            mEmptyText.setVisibility(View.INVISIBLE);
        }
        return false;
    }

    /*
    Exits the application when on Printers Tab
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
