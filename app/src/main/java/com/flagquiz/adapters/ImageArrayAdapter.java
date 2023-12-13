package com.flagquiz.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class ImageArrayAdapter extends ArrayAdapter<Integer> {

    public ImageArrayAdapter(Context context, int resource, Integer[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(getItem(position));
        imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100)); // Ajusta el tamaño según tus necesidades
        return imageView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}