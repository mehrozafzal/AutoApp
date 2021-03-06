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

public class ManufacturerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LinkedHashMap<String, String> manufacturers;
    private OnItemClickListener onItemClickListener;

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.listItem1_manufacturerName)
        TextView listItem1ManufacturerName;

        public MyViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(String manufacturer, String key) {
            listItem1ManufacturerName.setText(manufacturer);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClicked(manufacturer, key));
        }

    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.listItem2_manufacturerName)
        TextView listItem2ManufacturerName;

        public MyViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(String manufacturer, String key) {
            listItem2ManufacturerName.setText(manufacturer);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClicked(manufacturer, key));

        }

    }

    public ManufacturerAdapter(LinkedHashMap<String, String> manufacturers, Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.manufacturers = manufacturers;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ActivityVariables.Adapters.ITEM_TYPE_1:
                View v1 = inflater.inflate(R.layout.manufacturer_list_item_1, parent, false);
                viewHolder = new MyViewHolder1(v1);
                break;
            case ActivityVariables.Adapters.ITEM_TYPE_2:
                View v2 = inflater.inflate(R.layout.manufacturer_list_item_2, parent, false);
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
                MyViewHolder1 myViewHolder1 = (MyViewHolder1) holder;
                String manufacturer1 = (String) getElementNameByIndex(manufacturers, position);
                String manufacturerKey1 = (String) getElementKeyByIndex(manufacturers, position);
                myViewHolder1.bindViews(manufacturer1, manufacturerKey1);
                break;
            case ActivityVariables.Adapters.ITEM_TYPE_2:
                MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
                String manufacturer2 = (String) getElementNameByIndex(manufacturers, position);
                String manufacturerKey2 = (String) getElementKeyByIndex(manufacturers, position);
                myViewHolder2.bindViews(manufacturer2, manufacturerKey2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return manufacturers.size();
    }

    private Object getElementNameByIndex(LinkedHashMap map, int index) {
        return map.get((map.keySet().toArray())[index]);
    }

    private Object getElementKeyByIndex(LinkedHashMap map, int index) {
        return (map.keySet().toArray())[index];
    }

    public void notifyManufacturerAdapter(LinkedHashMap<String, String> manufacturers) {
        this.manufacturers = manufacturers;
        notifyDataSetChanged();
    }
}
