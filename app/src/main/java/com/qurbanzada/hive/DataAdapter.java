package com.qurbanzada.hive;

/**
 * Created by aqn3130 on 26/07/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    public static final String DATA = "Doc Detail";
    private List<String> mItems;
    private Context mContext;

    public DataAdapter(Context context, List<String> items) {
        this.mContext = context;
        this.mItems = items;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        final String item = mItems.get(position);


//        try {
            holder.tvName.setText(item);
//            String imageFile = setImageResource(R.drawable.ic_shortcut_subject);
//            InputStream inputStream = mContext.getAssets().open(imageFile);
//            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageResource(R.drawable.ic_shortcut_subject);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"You have selected "+item, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,DocumentDetail.class);
                intent.putExtra(DATA,item);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mView = itemView;
        }
    }
}