package com.example.helloworld.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helloworld.Activity.CapitalGameActivity;
import com.example.helloworld.Activity.CurrencyGameActivity;
import com.example.helloworld.R;
import com.example.helloworld.Model.User;
import com.example.helloworld.UserPreferences;

public class HomeFragment extends Fragment {
    private ImageView btn_play_capital, btn_play_currency;
    private TextView tv_life, tv_coin, tv_level_kota, tv_level_uang;
    private UserPreferences userPreferences;

    public HomeFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPreferences = new UserPreferences(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_play_capital = view.findViewById(R.id.play_kota);
        btn_play_currency = view.findViewById(R.id.play_uang);
        tv_coin = view.findViewById(R.id.tv_coin);
        tv_life = view.findViewById(R.id.tv_life);
        tv_level_kota = view.findViewById(R.id.tv_level_kota);
        tv_level_uang = view.findViewById(R.id.tv_level_uang);

        User user = userPreferences.getUser();

        tv_coin.setText(String.valueOf(user.getCoin()));
        tv_life.setText(String.valueOf(user.getLife()));
        tv_level_kota.setText("Level " + user.getLevelCapital() + "/199");
        tv_level_uang.setText("Level " + user.getLevelCurrency() + "/199");

        btn_play_capital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), CapitalGameActivity.class);
                startActivity(intent);
            }
        });

        btn_play_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), CurrencyGameActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = userPreferences.getUser();
        tv_coin.setText(String.valueOf(user.getCoin()));
        tv_life.setText(String.valueOf(user.getLife()));
        tv_level_kota.setText("Level " + user.getLevelCapital() + "/199");
        tv_level_uang.setText("Level " + user.getLevelCurrency() + "/199");
    }

}
