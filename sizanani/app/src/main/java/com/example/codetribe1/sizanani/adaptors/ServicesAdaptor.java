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
import com.example.codetribe1.sizanani.dto.ServicesDTO;
import com.example.codetribe1.unischool.R;

import java.util.List;

/**
 * Created by geoffrey on 1/17/16.
 */
public class ServicesAdaptor extends BaseAdapter {

    Context mCtx;
    List<String> mList;
    List<ServicesDTO>serviceList;

    public ServicesAdaptor(Context mCtx, List<ServicesDTO> newsList) {
        this.mCtx = mCtx;
        this.serviceList = newsList;
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceList.get(position);
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

            convertView = inflater.inflate(R.layout.service_item, parent, false);

            h.SEV_title = (TextView) convertView.findViewById(R.id.SEV_title);
            h.SEV_discription = (TextView) convertView.findViewById(R.id.SEV_discription);
            h.SEV_location = (TextView) convertView.findViewById(R.id.SEV_location);

            convertView.setTag(h);
        }else{
            h = (holder) convertView.getTag();
        }
        String title = serviceList.get(position).getFirstname()+ " " + serviceList.get(position).getSurname();
        String description = serviceList.get(position).getDescription();
        String location = serviceList.get(position).getLocation();

        h.SEV_title.setText(title);
        h.SEV_discription.setText(description);
        h.SEV_location.setText(location);

        animateView(convertView);

        return convertView;
    }
    class holder{
        TextView SEV_title;
        TextView SEV_discription;
        TextView SEV_location;
    }

    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(mCtx, R.anim.grow_fade_in_center);
        a.setDuration(500);
        if (view == null)
            return;
        view.startAnimation(a);
    }
}

