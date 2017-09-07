package com.example.jovel.prinventory.adapters;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.jovel.prinventory.TonerActivity;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.TonerUpdateFragment;
import com.example.jovel.prinventory.fragments.TonerViewFragment;
import com.example.jovel.prinventory.models.Toner;

import java.util.List;

/**
 * Created by Jovel on 6/28/2017.
 */

public class TonerAdapter extends RecyclerView.Adapter<TonerAdapter.TonerHolder> {

    private List<Toner> mList;
    private Context mContext;

    public TonerAdapter(Context context, List<Toner> list){
        mContext = context;
        mList = list;
    }

    @Override
    public TonerAdapter.TonerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_toner, parent, false);

        return new TonerHolder(v);
    }

    @Override
    public void onBindViewHolder(TonerAdapter.TonerHolder holder, int position) {
        Toner toner = mList.get(position);

        holder.bindToner(toner);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Toner> toners){
        mList = toners;
    }

    private void displayPopup(final View view, final int position){
        View mv = view.findViewById(R.id.row_toner_setting);
        PopupMenu pop = new PopupMenu(mContext, mv);
        MenuInflater inflate = pop.getMenuInflater();
        inflate.inflate(R.menu.context_menu_toner , pop.getMenu());
        Log.e("POP!", "Popup position on list: " + position);

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){

                    case R.id.menu_view_toner:

                        Database db = new Database(mContext);
                        TonerActivity ta = (TonerActivity) mContext;
                        FragmentManager fm = ta.getSupportFragmentManager();
                        TonerViewFragment vfrag = TonerViewFragment.newInstance(position);
                        vfrag.show(fm, "FRAG_View");
                        break;

                    case R.id.menu_edit_toner:

                        ta = (TonerActivity) mContext;
                        fm = ta.getSupportFragmentManager();
                        TonerUpdateFragment frag = TonerUpdateFragment.newInstance(position);
                        frag.show(fm, "FRAG_UPDATE");

                        Log.e("Edit", "You are editing printer# " + position);
                        break;

                    case R.id.menu_delete_toner:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                        dialog.setTitle(R.string.dialog_title_delete_toner)
                                .setMessage(R.string.dialog_message_delete_toner)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Database db = new Database(mContext);
                                        db.deleteToner(mList.get(position));

                                    }
                                })
                                .setNegativeButton(R.string.btn_no, null)
                                .create()
                                .show();

                        Log.e("Details", "You are deleting toner# " + position);
                        break;
                }
                return false;
            }
        });
        pop.show();

    }


    public class TonerHolder extends RecyclerView.ViewHolder {
        private TextView make, model, tmodel, black, cyan, yellow, magenta;
        private ImageView listImg, colorImg, settingsImg;
        private Toner mToner;

        public TonerHolder(View view){
            super(view);

            make = (TextView)view.findViewById(R.id.row_toner_make);
            model = (TextView)view.findViewById(R.id.row_toner_model);
            tmodel = (TextView)view.findViewById(R.id.row_toner_tmodel);
            listImg = (ImageView)view.findViewById(R.id.row_toner_icon);
            colorImg = (ImageView)view.findViewById(R.id.row_toner_color);
            settingsImg = (ImageView) view.findViewById(R.id.row_toner_setting);

        }

        public void bindToner(final Toner toner){
            mToner = toner;
            make.setText("Make:      " + mToner.getMake());
            model.setText("Model:    " + mToner.getModel());
            tmodel.setText("TModel:  " + mToner.getTonerModel());
            listImg.setImageResource(R.drawable.ic_toner);

            if(mList.get(getAdapterPosition()).getColor()==0){
                colorImg.setImageResource(R.drawable.ic_toner_row_bw); // black and white
            }else{
                colorImg.setImageResource(R.drawable.ic_toner_row_color); //color
            }

            settingsImg.setImageResource(R.drawable.ic_settings);
            settingsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayPopup(v, getAdapterPosition());
                }
            });

        }
    }

}
