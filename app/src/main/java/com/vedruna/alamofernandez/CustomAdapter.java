package com.vedruna.alamofernandez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mMainItems;
    private ArrayList<String> mSubItems;

    public CustomAdapter(Context context, ArrayList<String> mainItems, ArrayList<String> subItems) {
        super(context, 0, mainItems);
        mContext = context;
        mMainItems = mainItems;
        mSubItems = subItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = convertView.findViewById(R.id.main_text);
            viewHolder.subText = convertView.findViewById(R.id.sub_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mainText.setText(mMainItems.get(position));
        viewHolder.subText.setText(mSubItems.get(position));

        return convertView;
    }

    private static class ViewHolder {
        TextView mainText;
        TextView subText;
    }
}
