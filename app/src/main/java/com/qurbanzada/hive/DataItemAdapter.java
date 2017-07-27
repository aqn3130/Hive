package com.qurbanzada.hive;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import com.qurbanzada.hive.MainActivity;

/**
 * Created by aqn3130 on 25/07/2017.
 */


public class DataItemAdapter extends ArrayAdapter {
    List<String> mDataItems;
    LayoutInflater mInflater;
    public static TextView tvName;
    public static ImageView imageView;

    public DataItemAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.list_item, objects);
        mDataItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item,parent,false);
        }


        tvName = convertView.findViewById(R.id.itemNameText);

        imageView = convertView.findViewById(R.id.imageView);

        String item =  mDataItems.get(position);
//        List list = new ArrayList<>();

//        tvName.setText((CharSequence) new MainActivity().getCi());
        tvName.setText(item);
        imageView.setImageResource(R.drawable.ic_shortcut_subject);
        return convertView;
    }
}
