package com.example.jovel.prinventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jovel.prinventory.adapters.TonerAdapter;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.TonerSaveFragment;
import com.example.jovel.prinventory.fragments.TonerUpdateFragment;
import com.example.jovel.prinventory.fragments.TonerViewFragment;
import com.example.jovel.prinventory.models.Toner;
import com.example.jovel.prinventory.utils.OnTonerItemClickListener;

import java.util.List;

/**
 * Created by Jovel on 9/2/2017.
 */

public class TonerActivity extends AppCompatActivity implements OnTonerItemClickListener {

    private TextView mEmptyText;
    private ImageView mEmptyImage;
    private RecyclerView mRecyclerView;
    private TonerAdapter mTonerAdapter;
    private Database mDatabase;

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

                    setupAdapter();
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

        mDatabase = new Database(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_toner).setChecked(true);

        //Drawable & text is set whenever there are no list items
        mEmptyImage = (ImageView)findViewById(R.id.empty_img_toner);
        mEmptyImage.setImageResource(R.drawable.ic_list_empty);
        mEmptyText = (TextView)findViewById(R.id.empty_text_toner);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_toner);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupAdapter();
        showListStatus();

        FloatingActionButton mFabBtn = (FloatingActionButton) findViewById(R.id.fab_add_toner);
        mFabBtn.setImageResource(R.drawable.ic_plus);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                TonerSaveFragment dialog = new TonerSaveFragment();
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

        List<Toner> toners = mDatabase.getAllToners();

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
    private void showListStatus(){

        if(mTonerAdapter.getItemCount() == 0){
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

    private void displayPopup(final Toner toner, View v){

        PopupMenu pop = new PopupMenu(TonerActivity.this, v);
        MenuInflater inflater = pop.getMenuInflater();
        inflater.inflate(R.menu.context_menu_toner , pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){

                    case R.id.menu_view_toner:

                        FragmentManager fm = getSupportFragmentManager();
                        TonerViewFragment vfrag = TonerViewFragment.newInstance(toner.getId());
                        vfrag.show(fm, null);
                        break;

                    case R.id.menu_edit_toner:

                        fm = getSupportFragmentManager();
                        TonerUpdateFragment ufrag = TonerUpdateFragment.newInstance(toner.getId());
                        ufrag.show(fm, null);

                        break;

                    case R.id.menu_delete_toner:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(TonerActivity.this);
                        dialog.setTitle(R.string.dialog_title_delete_toner)
                                .setMessage(R.string.dialog_message_delete_toner)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        mDatabase.deleteToner(toner.getId());
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
    public void onClick(final Toner toner, View view){
        displayPopup(toner, view);
    }

}
