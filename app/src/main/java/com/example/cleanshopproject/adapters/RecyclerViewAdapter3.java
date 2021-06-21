package com.example.cleanshopproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanshopproject.DataAdapter;
import com.example.cleanshopproject.R;

import java.util.List;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder>{

    Context context;
    List<DataAdapter> dataAdapters;


    public RecyclerViewAdapter3(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bell, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);

        Viewholder.ImageNumberTextView.setText(dataAdapterOBJ.getImageNumber());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ImageNumberTextView;


        public ViewHolder(View itemView) {

            super(itemView);
            ImageNumberTextView = (TextView) itemView.findViewById(R.id.notificationNumber) ;

        }
    }
}