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

public class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context context;
    private LinkedHashMap<String, String> models;
    private OnItemClickListener onItemClickListener;

    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.listItem1_modelName)
        TextView listItem1ModelName;

        public MyViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(String model, String key) {
            listItem1ModelName.setText(model);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClicked(model,key));
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.listItem2_modelName)
        TextView listItem2ModelName;
        public MyViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(String model,String key) {
            listItem2ModelName.setText(model);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClicked(model,key));
        }

    }

    public ModelAdapter(LinkedHashMap<String, String> models, Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.models = models;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ActivityVariables.Adapters.ITEM_TYPE_1:
                View v1 = inflater.inflate(R.layout.model_list_item_1, parent, false);
                viewHolder = new MyViewHolder1(v1);
                break;
            case ActivityVariables.Adapters.ITEM_TYPE_2:
                View v2 = inflater.inflate(R.layout.model_list_item_2, parent, false);
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
                ModelAdapter.MyViewHolder1 myViewHolder1 = (ModelAdapter.MyViewHolder1) holder;
                String model1 = (String) getElementNameByIndex(models, position);
                String modelKey1 = (String) getElementKeyByIndex(models, position);
                myViewHolder1.bindViews(model1,modelKey1);
                break;
            case ActivityVariables.Adapters.ITEM_TYPE_2:
                ModelAdapter.MyViewHolder2 myViewHolder2 = (ModelAdapter.MyViewHolder2) holder;
                String model2 = (String) getElementNameByIndex(models, position);
                String modelKey2 = (String) getElementKeyByIndex(models, position);
                myViewHolder2.bindViews(model2,modelKey2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private Object getElementNameByIndex(LinkedHashMap map, int index) {
        return map.get((map.keySet().toArray())[index]);
    }

    private Object getElementKeyByIndex(LinkedHashMap map, int index) {
        return (map.keySet().toArray())[index];
    }

}

