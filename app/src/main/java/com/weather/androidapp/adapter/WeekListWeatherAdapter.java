package com.weather.androidapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weather.androidapp.R;
import com.weather.androidapp.model.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeekListWeatherAdapter extends RecyclerView.Adapter<WeekListWeatherAdapter.VHolder> {
    private Context mContext;
    private ArrayList<List> listArrayList;
    String units_of_temp;


    public WeekListWeatherAdapter(Context mContex, ArrayList<List> listArrayList, String units_of_temp) {
        this.listArrayList = listArrayList;
        this.mContext = mContex;
        this.units_of_temp = units_of_temp;
    }

    @NonNull
    @Override
    public WeekListWeatherAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_details_adapter, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekListWeatherAdapter.VHolder holder, int position) {
        if (position % 2 == 0) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#11FFFFFF"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = fmt.parse(listArrayList.get(position).getDt_txt());
            SimpleDateFormat fmtOut = new SimpleDateFormat("EEEE");
            holder.day.setText(fmtOut.format(date));
            Glide.with(mContext)
                    .load("http://openweathermap.org/img/w/"+listArrayList.get(position).getWeather().get(0).getIcon()+".png")
                    .asBitmap()
                    .into(holder.weather_img);
            if (units_of_temp.equals("C")) {
                holder.maxTemp.setText("" + String.format("%.0f", listArrayList.get(position).getMain().getTemp_max()) + (char) 0x00B0+" C");
                holder.minTemp.setText("" + String.format("%.0f", listArrayList.get(position).getMain().getTemp_min()) + (char) 0x00B0+" C");
            } else if (units_of_temp.equals("F")) {
                holder.maxTemp.setText("" + String.format("%.0f", listArrayList.get(position).getMain().getTemp_max()) + (char) 0x00B0+" F");
                holder.minTemp.setText("" + String.format("%.0f", listArrayList.get(position).getMain().getTemp_min()) + (char) 0x00B0+" F");
            } else if (units_of_temp.equals("K")) {
                holder.maxTemp.setText("" + String.format("%.0f", listArrayList.get(position).getMain().getTemp_max())+" K");
                holder.minTemp.setText("" + String.format("%.0f", listArrayList.get(position).getMain().getTemp_min())+" K");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listArrayList.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView day, maxTemp, minTemp;
        ImageView weather_img;

        public VHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
            day = (TextView) itemView.findViewById(R.id.day);
            maxTemp = (TextView) itemView.findViewById(R.id.maxTemp);
            minTemp = (TextView) itemView.findViewById(R.id.minTemp);
            weather_img = (ImageView) itemView.findViewById(R.id.weather_img);
        }
    }
}
