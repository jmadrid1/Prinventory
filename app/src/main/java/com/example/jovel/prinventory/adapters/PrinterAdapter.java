package com.example.jovel.prinventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jovel.prinventory.R;
import com.example.jovel.prinventory.models.Printer;
import com.example.jovel.prinventory.utils.OnPrinterItemClickListener;

import java.util.List;

/**
 * Created by Jovel on 6/29/2017.
 */

public class PrinterAdapter extends RecyclerView.Adapter<PrinterHolder> {

    private Context mContext;
    private List<Printer> mPrinters;
    private OnPrinterItemClickListener mOnPrinterItemClickListener;

    public PrinterAdapter(Context context, List<Printer> printers){
        mContext = context;
        mPrinters = printers;
        mOnPrinterItemClickListener = (OnPrinterItemClickListener) context;
    }

    @Override
    public PrinterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_row_printer, parent, false);

        return new PrinterHolder(v);
    }

    @Override
    public void onBindViewHolder(PrinterHolder holder, int position) {

        Printer printer = mPrinters.get(position);

        holder.bindPrinter(printer, mOnPrinterItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mPrinters.size();
    }

    public void setList(List<Printer> printers){
        mPrinters = printers;
    }
    
}

