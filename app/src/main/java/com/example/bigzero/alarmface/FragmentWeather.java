package com.example.bigzero.alarmface;


import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigzero.alarmface.weather.data.Channel;
import com.example.bigzero.alarmface.weather.data.Item;
import com.example.bigzero.alarmface.weather.service.WeatherServiceCallback;
import com.example.bigzero.alarmface.weather.service.YahooWeatherService;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWeather extends Fragment implements WeatherServiceCallback {
    private ImageView imgIconWeather, imgSearch;
    private TextView tvTemperature, tvCondition, tvLocation;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    private EditText edtViTri;


    public FragmentWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_weather, container, false);

        imgIconWeather = (ImageView)view.findViewById(R.id.imgIcon);
        imgSearch = (ImageView)view.findViewById(R.id.imgSearch);
        tvCondition = (TextView)view.findViewById(R.id.tvCondition);
        tvLocation = (TextView)view.findViewById(R.id.tvViTri);
        tvTemperature = (TextView)view.findViewById(R.id.tvNhietDo);
        edtViTri = (EditText)view.findViewById(R.id.edtViTri);


        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        service.refreshWeather("Ho Chi Minh, VN");

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtViTri.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "You have not entered a location yet!", Toast.LENGTH_SHORT).show();
                }else{
                    service.refreshWeather(edtViTri.getText().toString());
                }
            }
        });

        return  view;
    }

    @Override
    public void serviceSuccess(Channel channel) {

        dialog.hide();

        final Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(), null, getActivity().getPackageName());

        @SuppressWarnings("deprecation")
        Drawable drawableIconWeather = getResources().getDrawable(resourceId);
        //chuyển độ F sang độ C
        double c = Math.round((item.getCondition().getTemp()-32)/1.8);

        imgIconWeather.setVisibility(View.VISIBLE);
        tvCondition.setVisibility(View.VISIBLE);
        tvLocation.setVisibility(View.VISIBLE);
        tvTemperature.setVisibility(View.VISIBLE);

        imgIconWeather.setImageDrawable(drawableIconWeather);
        tvLocation.setText(service.getLocation());
        tvTemperature.setText((int)c + "\u00B0" + "C");
        tvCondition.setText(item.getCondition().getDescription());

    }

    @Override
    public void serviceFailure(Exception exception) {

        imgIconWeather.setVisibility(View.INVISIBLE);
        tvCondition.setVisibility(View.INVISIBLE);
        tvLocation.setVisibility(View.INVISIBLE);
        tvTemperature.setVisibility(View.INVISIBLE);

        dialog.hide();
        Toast.makeText(getActivity(), "No data for location!", Toast.LENGTH_SHORT).show();

    }
}
