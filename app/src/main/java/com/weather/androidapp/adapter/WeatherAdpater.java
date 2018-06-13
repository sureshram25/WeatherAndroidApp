package com.weather.androidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.weather.androidapp.Activity_WeatherDetails;
import com.weather.androidapp.R;
import com.weather.androidapp.model.List;
import com.weather.androidapp.model.MainObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class WeatherAdpater extends RecyclerView.Adapter<WeatherAdpater.ViewHolder> {
    private Context mContex;
    private ArrayList<List> lists;
    String units, units_of_temp;

    public WeatherAdpater(Context applicationContext, ArrayList<List> lists, String units, String units_of_temp) {
        this.mContex = applicationContext;
        this.lists = lists;
        this.units = units;
        this.units_of_temp = units_of_temp;
    }

    @NonNull
    @Override
    public WeatherAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.weather_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherAdpater.ViewHolder holder, final int position) {

        holder.txt_placename.setText("" + lists.get(position).getName());
        if (units_of_temp.equals("C")) {
            holder.txt_temp.setText("" + String.format("%.0f", lists.get(position).getMain().getTemp()) + (char) 0x00B0 + " C");
        } else if (units_of_temp.equals("F")) {
            holder.txt_temp.setText("" + String.format("%.0f", lists.get(position).getMain().getTemp()) + (char) 0x00B0 + " F");
        } else if (units_of_temp.equals("K")) {
            holder.txt_temp.setText("" + String.format("%.0f", lists.get(position).getMain().getTemp()) + " K");
        }

        loadForcast(lists.get(position).getId(), holder.recyclerViewforcost, units);


        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Today Weather Details :\nCity Name : " + lists.get(position).getName() + "\nTemparature : " + lists.get(position).getMain().getTemp() + "\nHumidity : " + lists.get(position).getMain().getHumidity() + "\nPressure : " + lists.get(position).getMain().getPressure() + "");
                sendIntent.setType("text/plain");
                mContex.startActivity(sendIntent);
            }
        });

        holder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContex, Activity_WeatherDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("humidity", lists.get(position).getMain().getHumidity());
                intent.putExtra("wind", lists.get(position).getWind().getSpeed());
                intent.putExtra("pressure", lists.get(position).getMain().getPressure());
                intent.putExtra("temp", holder.txt_temp.getText().toString());
                intent.putExtra("name", holder.txt_placename.getText().toString());
                mContex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_placename, txt_temp;
        RecyclerView recyclerViewforcost;
        RelativeLayout relative_layout;
        ImageView iv_share;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_placename = itemView.findViewById(R.id.txt_placename);
            txt_temp = itemView.findViewById(R.id.txt_temp);
            recyclerViewforcost = itemView.findViewById(R.id.recyclerViewforcost);
            relative_layout = itemView.findViewById(R.id.relative_layout);
            iv_share = itemView.findViewById(R.id.iv_share);
        }
    }

    private void loadForcast(final String id, final RecyclerView recyclerViewforcost, String units) {
        final Map<String, List> resut = new HashMap<>();
        resut.clear();
        Ion.with(this.mContex).load("http://api.openweathermap.org/data/2.5/forecast?id=" + id + "&APPID=899dbeb438c2a3451acd594f022407aa" + units)

                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {

                        if (result != null) {
                            Gson gson = new Gson();
                            MainObject rootObject = gson.fromJson(result.getResult().toString(), MainObject.class);
                            ArrayList<List> listArrayList = new ArrayList<>();
                            listArrayList = rootObject.getList();
                            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            for (int i = 0; i < listArrayList.size(); i++) {
                                try {
                                    Date date = fmt.parse(listArrayList.get(i).getDt_txt());
                                    SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd");
                                    try {

                                        Calendar calendar1 = Calendar.getInstance();
                                        TimeZone tz = calendar1.getTimeZone();
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                        sdf.setTimeZone(tz);
                                        String currentDateandTime = sdf.format(new Date());


                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                        simpleDateFormat.setTimeZone(tz);
                                        Date time1 = simpleDateFormat.parse(currentDateandTime);
                                        calendar1.setTime(time1);
                                        calendar1.setTimeZone(tz);
                                        calendar1.add(Calendar.HOUR, 1);


                                        SimpleDateFormat simpleDateForma = new SimpleDateFormat("HH:mm:ss");
                                        simpleDateForma.setTimeZone(tz);
                                        Date time2 = simpleDateForma.parse(currentDateandTime);
                                        Calendar calendar2 = Calendar.getInstance();
                                        calendar2.setTime(time2);
                                        calendar2.add(Calendar.HOUR, -2);


                                        SimpleDateFormat simpleDateForm = new SimpleDateFormat("HH:mm:ss");
                                        simpleDateForm.setTimeZone(tz);
                                        Date d = simpleDateForm.parse(sdf.format(date));
                                        Calendar calendar3 = Calendar.getInstance();
                                        calendar3.setTime(d);
                                        Date x = calendar3.getTime();
                                        //based on current time getting the next 5 days fore coast
                                        if (x.after(calendar1.getTime()) || x.before(calendar2.getTime())) {

                                        } else {
                                            resut.put(fmtOut.format(date), listArrayList.get(i));

                                        }
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }

                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                            }

                            ArrayList<List> listArrayList2 = new ArrayList<>(resut.values());
                            Collections.sort(listArrayList2, new Comparator<List>() {
                                public int compare(List m1, List m2) {
                                    return m1.getDt_txt().compareTo(m2.getDt_txt());
                                }
                            });
                            recyclerViewforcost.setHasFixedSize(false);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContex, LinearLayoutManager.VERTICAL, false);
                            recyclerViewforcost.setLayoutManager(mLayoutManager);
                            recyclerViewforcost.setItemAnimator(new DefaultItemAnimator());
                            WeekListWeatherAdapter weekListWeatherAdapter = new WeekListWeatherAdapter(WeatherAdpater.this.mContex, listArrayList2, units_of_temp);
                            recyclerViewforcost.setAdapter(weekListWeatherAdapter);
                            weekListWeatherAdapter.notifyDataSetChanged();
                        }
                    }

                });

    }

}
