package com.whackyard.flipmapapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Nazila on 07/01/2017.
 */

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Contact> contactItems;

    public CustomListAdapter(Activity activity, List<Contact> contactItems) {
        this.activity = activity;
        this.contactItems = contactItems;
    }

    @Override
    public int getCount() {
        return contactItems.size();
    }

    @Override
    public Object getItem(int location) {
        return contactItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView name = (TextView) convertView.findViewById(R.id.title);
        TextView icon = (TextView) convertView.findViewById(R.id.thumbText);
        de.hdodenhof.circleimageview.CircleImageView iconThumb = (de.hdodenhof.circleimageview.CircleImageView) convertView.findViewById(R.id.thumbImg);

        int[] androidColors = activity.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        iconThumb.setColorFilter(randomAndroidColor);

        Contact m = contactItems.get(position);

        name.setText(m.getName());
        icon.setText(m.getIcon());

        return convertView;
    }

}
