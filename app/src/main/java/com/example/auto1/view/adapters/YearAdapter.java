package com.example.auto1.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.auto1.R;
import com.example.auto1.callBacks.OnItemClickListener;
import com.example.auto1.constants.ActivityVariables;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LinkedHashMap<String, String> years;
    private OnItemClickListener onItemClickListener;

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.listItem1_yearName)
        TextView listItem1YearName;


        public MyViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(String year, String key) {
            listItem1YearName.setText(year);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClicked(year, key));
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.listItem2_yearName)
        TextView listItem2YearName;

        public MyViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(String year, String key) {
            listItem2YearName.setText(year);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClicked(year, key));
        }

    }

    public YearAdapter(LinkedHashMap<String, String> years, Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.years = years;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ActivityVariables.Adapters.ITEM_TYPE_1:
                View v1 = inflater.inflate(R.layout.year_list_item_1, parent, false);
                viewHolder = new MyViewHolder1(v1);
                break;
            case ActivityVariables.Adapters.ITEM_TYPE_2:
                View v2 = inflater.inflate(R.layout.year_list_item_2, parent, false);
                viewHolder = new MyViewHolder2(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return ActivityVariables.Adapters.ITEM_TYPE_1;
        else
            return ActivityVariables.Adapters.ITEM_TYPE_2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ActivityVariables.Adapters.ITEM_TYPE_1:
                YearAdapter.MyViewHolder1 myViewHolder1 = (YearAdapter.MyViewHolder1) holder;
                String year1 = (String) getElementNameByIndex(years, position);
                String yearKey1 = (String) getElementKeyByIndex(years, position);
                myViewHolder1.bindViews(year1,yearKey1);
                break;
            case ActivityVariables.Adapters.ITEM_TYPE_2:
                YearAdapter.MyViewHolder2 myViewHolder2 = (YearAdapter.MyViewHolder2) holder;
                String year2 = (String) getElementNameByIndex(years, position);
                String yearKey2 = (String) getElementKeyByIndex(years, position);
                myViewHolder2.bindViews(year2,yearKey2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    private Object getElementNameByIndex(LinkedHashMap map, int index) {
        return map.get((map.keySet().toArray())[index]);
    }

    private Object getElementKeyByIndex(LinkedHashMap map, int index) {
        return (map.keySet().toArray())[index];
    }

}
