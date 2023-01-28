package com.example.seolanjin.seolanjinproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Record> recordArrayList;
    Context context;
    View view;
    ViewHolder viewHolder;

    public MyAdapter(Context context1, ArrayList<Record> SubjectValues1){

        recordArrayList = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewIdL;
        public TextView textViewDateL;
        public TextView textViewtimeL;
        public TextView textViewNameL;

        public ViewHolder(View v){
            super(v);

            textViewIdL = (TextView)v.findViewById(R.id.textViewIdL);
            textViewDateL = (TextView)v.findViewById(R.id.textViewDateL);
            textViewtimeL = (TextView)v.findViewById(R.id.textViewtimeL);
            textViewNameL = (TextView)v.findViewById(R.id.textViewNameL);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout,parent,false);
        viewHolder = new ViewHolder(view);
        viewHolder.itemView.getLayoutParams().height=70;

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textViewIdL.setText(String.valueOf(recordArrayList.get(position).getId()));
        holder.textViewDateL.setText(String.valueOf(recordArrayList.get(position).getsDate()));
        holder.textViewtimeL.setText(recordArrayList.get(position).getTime());
        holder.textViewNameL.setText(recordArrayList.get(position).getDogName());
    }

    @Override
    public int getItemCount(){ return recordArrayList.size(); }
}
