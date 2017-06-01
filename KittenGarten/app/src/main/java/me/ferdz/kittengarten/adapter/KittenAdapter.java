package me.ferdz.kittengarten.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.ferdz.kittengarten.R;
import me.ferdz.kittengarten.model.MinKitten;
import me.ferdz.kittengarten.util.Utils;

/**
 * Created by 1452284 on 2016-08-25.
 */
public class KittenAdapter extends ArrayAdapter<MinKitten> {
    public KittenAdapter(Context context, int resource, int textViewResourceId, List<MinKitten> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View row = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        final MinKitten kitten = getItem(position);
        ((TextView) row.findViewById(R.id.list_item_name)).setText(kitten.getName());

        Bitmap image = kitten.getImage();
        if(image == null)
            ((ImageView)row.findViewById(R.id.list_item_image)).setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.garfielf));
        else
            ((ImageView)row.findViewById(R.id.list_item_image)).setImageBitmap(image);

        if(kitten.getRenterId() == Utils.loggedUser.getId())
            row.setBackgroundColor(Color.argb(65, 0, 255, 0));
        else if (kitten.getRenterId() > 0)
            row.setBackgroundColor(Color.argb(65, 255, 0, 0));
        return row;
    }
}
