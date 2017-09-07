package com.example.jovel.prinventory.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.jovel.prinventory.PrinterActivity;
import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.database.Database;
import com.example.jovel.prinventory.fragments.PrinterUpdateFragment;
import com.example.jovel.prinventory.fragments.PrinterViewFragment;
import com.example.jovel.prinventory.models.Printer;

import java.util.List;

/**
 * Created by Jovel on 6/29/2017.
 */

public class PrinterAdapter extends RecyclerView.Adapter<PrinterAdapter.PrinterHolder> {

    private List<Printer> mList;
    private Context mContext;

    public PrinterAdapter(Context context, List<Printer> list){
        mContext = context;
        mList = list;
    }

    @Override
    public PrinterAdapter.PrinterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_printer, parent, false);

        return new PrinterHolder(v);
    }

    @Override
    public void onBindViewHolder(PrinterAdapter.PrinterHolder holder, int position) {
        Printer printer = mList.get(position);

        holder.bindPrinter(printer);
    }


    @Override
    public int getItemCount() {
        Log.i("Adapter List", String.valueOf(mList.size()));
        return mList.size();
    }

    public void setList(List<Printer> printers){
        mList = printers;
    }


    private void displayPopup(final View view, final int position){
        View mv = view.findViewById(R.id.row_printer_setting);
        PopupMenu pop = new PopupMenu(mContext, mv);
        MenuInflater inflate = pop.getMenuInflater();
        inflate.inflate(R.menu.context_menu_printer , pop.getMenu());
        Log.e("POP!", "Popup position on list: " + position);

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){

                    case R.id.menu_view_printer:

                        Database db = new Database(mContext);
                        PrinterActivity ma = (PrinterActivity)mContext;
                        FragmentManager fm = ma.getSupportFragmentManager();
                        PrinterViewFragment vfrag = PrinterViewFragment.newInstance(position);
                        vfrag.show(fm, "FRAG_View");
                        break;

                    case R.id.menu_edit_printer:

                        ma = (PrinterActivity)mContext;
                        fm = ma.getSupportFragmentManager();
                        PrinterUpdateFragment ufrag = PrinterUpdateFragment.newInstance(position);
                        ufrag.show(fm, "FRAG_UPDATE");

                        Log.e("Edit", "You are editing printer# " + position);
                        break;

                    case R.id.menu_delete_printer:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                        dialog.setTitle(R.string.dialog_title_delete_printer)
                                .setMessage(R.string.dialog_message_delete_printer)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Database db = new Database(mContext);
                                        db.deletePrinter(mList.get(position));

                                    }
                                })
                                .setNegativeButton(R.string.btn_no, null)
                                .create()
                                .show();

                        Log.e("Details", "You are deleting printer# " + position);
                        break;
                }
                return false;
            }
        });
        pop.show();

    }


    public class PrinterHolder extends RecyclerView.ViewHolder {
        private TextView make, model, dept, ip;
        private ImageView listImg, colorImg, statusImg, settingsImg;
        private Printer mPrinter;

        public PrinterHolder(View view){
            super(view);

            make = (TextView)view.findViewById(R.id.row_printer_make);
            model = (TextView)view.findViewById(R.id.row_printer_model);
            dept = (TextView)view.findViewById(R.id.row_printer_dept);
            ip = (TextView)view.findViewById(R.id.row_printer_ip);
            listImg = (ImageView)view.findViewById(R.id.row_printer_icon);
            colorImg = (ImageView)view.findViewById(R.id.row_printer_color);
            statusImg = (ImageView)view.findViewById(R.id.row_printer_status);
            settingsImg = (ImageView) view.findViewById(R.id.row_printer_setting);
        }

        public void bindPrinter(final Printer printer){
            mPrinter = printer;
            make.setText("Make:    " + mPrinter.getMake());
            model.setText("Model:   " + mPrinter.getModel());
            dept.setText("Department:   " + mPrinter.getDepartment());
            ip.setText("IP:    " + String.valueOf(mPrinter.getIp()));
            listImg.setImageResource(R.drawable.ic_printer);

            if (mPrinter.getColor() == 0){
                colorImg.setImageResource(R.drawable.ic_toner_row_bw);
            }else{
                colorImg.setImageResource(R.drawable.ic_toner_row_color);
            }

            if (mPrinter.getStatus() == 0){
                statusImg.setImageResource(R.drawable.ic_status_inactive);
            }else{
                statusImg.setImageResource(R.drawable.ic_status_active);
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

