package com.example.sophiataskova.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sophiataskova.gridimagesearch.R;
import com.example.sophiataskova.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sophiataskova on 2/14/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {


    public ImageResultsAdapter(Context context, List<ImageResult> objects) {
        super(context, R.layout.item_image_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageResult = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView)convertView.findViewById(R.id.iv_result_image);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_result_title);

        ivImage.setImageResource(0);
        tvTitle.setText(Html.fromHtml(imageResult.title));
        Picasso.with(getContext()).load(imageResult.thumbUrl).into(ivImage);
        return convertView;
    }
}
