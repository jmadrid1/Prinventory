package com.example.jovel.prinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.adapters.TonerAdapter;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.TonerSaveFragment;
import com.example.jovel.prinventory.models.Toner;

import java.util.List;

/**
 * Created by Jovel on 9/2/2017.
 */

public class TonerActivity extends FragmentActivity {

    private static final String DIALOG_SAVE ="DialogSave";

    private FloatingActionButton mFabBtn;
    private TextView mEmptyText;
    private ImageView mEmptyImage;
    private RecyclerView mRecyclerView;
    private TonerAdapter mTonerAdapter;
    private Database db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_printer:
                    Intent intent = new Intent(TonerActivity.this, PrinterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.navigation_toner:

                    updateList();
                    break;

                case R.id.navigation_vendor:

                    intent = new Intent(TonerActivity.this, VendorActivity.class);
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
        setContentView(R.layout.activity_toner);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_toner).setChecked(true);

        /*
        Drawable & text is set whenever there are no list items
         */
        mEmptyImage = (ImageView)findViewById(R.id.empty_img_toner);
        mEmptyImage.setImageResource(R.drawable.ic_list_empty);
        mEmptyText = (TextView)findViewById(R.id.empty_text_toner);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_toner);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mTonerAdapter);

        updateList();
        isListEmpty();

        mFabBtn = (FloatingActionButton) findViewById(R.id.fab_add_toner);
        mFabBtn.setImageResource(R.drawable.ic_plus);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                TonerSaveFragment dialog = new TonerSaveFragment();
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
        Database db = new Database(this);
        List<Toner> toners = db.getAllToners();

        if(mTonerAdapter == null){
            mTonerAdapter = new TonerAdapter(this, toners);
            mRecyclerView.setAdapter(mTonerAdapter);
        } else {
            mTonerAdapter.setList(toners);
            mTonerAdapter.notifyDataSetChanged();
        }
    }

    /*
    Toggles empty list drawable & text when the size of list changes
     */
    private Boolean isListEmpty(){
        if(mTonerAdapter.getItemCount() == 0){
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

}
