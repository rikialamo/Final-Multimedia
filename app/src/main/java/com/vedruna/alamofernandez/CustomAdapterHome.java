package com.vedruna.alamofernandez;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class CustomAdapterHome extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mMainItems;
    private ArrayList<String> mSubItems;
    private ArrayList<String> mAdditionalItems;
    private ArrayList<String> mDrawables;

    public CustomAdapterHome(Context context, ArrayList<String> mainItems, ArrayList<String> subItems, ArrayList<String> additionalItems, ArrayList<String> drawables) {
        super(context, 0, mainItems);
        mContext = context;
        mMainItems = mainItems;
        mSubItems = subItems;
        mAdditionalItems = additionalItems;
        mDrawables = drawables;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_home, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = convertView.findViewById(R.id.main_text);
            viewHolder.subText = convertView.findViewById(R.id.sub_text);
            viewHolder.additionalText = convertView.findViewById(R.id.additional_text);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mainText.setText(mMainItems.get(position));
        viewHolder.subText.setText(mSubItems.get(position));
        viewHolder.additionalText.setText(mAdditionalItems.get(position));
        loadImage(this.getContext(), mDrawables.get(position), viewHolder.imageView);

        return convertView;
    }

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // Cache la imagen para su uso futuro

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    private static class ViewHolder {
        TextView mainText;
        TextView subText;
        TextView additionalText;
        ImageView imageView;
    }
}
