package com.example.codetribe1.sizanani.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codetribe1.sizanani.dto.CategoryDTO;
import com.example.codetribe1.sizanani.dto.NewsDTO;
import com.example.codetribe1.unischool.R;

import java.util.List;

/**
 * Created by geoffrey on 1/17/16.
 */
public class CategoriesAdaptor extends BaseAdapter {

    Context mCtx;
    List<String> mList;
    List<CategoryDTO>categoryList;

    public CategoriesAdaptor(Context mCtx, List<CategoryDTO> newsList) {
        this.mCtx = mCtx;
        this.categoryList = newsList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder h;
        if(convertView==null){
            h= new holder();
            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.category_item, parent, false);

            h.CAT_name = (TextView) convertView.findViewById(R.id.CAT_name);
            h.CAT_description = (TextView) convertView.findViewById(R.id.CAT_description);

            convertView.setTag(h);
        }else{
            h = (holder) convertView.getTag();
        }
        String name = categoryList.get(position).getCat_name();
        String description = categoryList.get(position).getDescription();

        h.CAT_name.setText(name);
        h.CAT_description.setText(description);

        animateView(convertView);

        return convertView;
    }
    class holder{
        TextView CAT_name;
        TextView CAT_description;
    }

    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(mCtx, R.anim.grow_fade_in_center);
        a.setDuration(500);
        if (view == null)
            return;
        view.startAnimation(a);
    }
}

