package com.example.helloworld.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView tv_negara;
    private TextView tv_population;
    private TextView tv_land;
    private TextView tv_density;
    private TextView tv_capital;
    private TextView tv_currency;
//    private ImageView iv_flag;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_negara = findViewById(R.id.tv_negara);
        tv_population = findViewById(R.id.tv_population);
        tv_land = findViewById(R.id.tv_land);
        tv_density = findViewById(R.id.tv_density);
        tv_capital = findViewById(R.id.tv_capital);
        tv_currency = findViewById(R.id.tv_currency);
//        iv_flag = findViewById(R.id.iv_flag);
        back = findViewById(R.id.back);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int population = intent.getIntExtra("population", 0);
        double landArea = intent.getDoubleExtra("land_area", 0.0);
        double density = intent.getDoubleExtra("density", 0.0);
        String capital = intent.getStringExtra("capital");
        String currency = intent.getStringExtra("currency");
//        String flagUrl = intent.getStringExtra("flag");
//        Picasso.get().load(flagUrl).into(iv_flag);

        tv_negara.setText(name);
        tv_population.setText(String.format("%,d", population));
        tv_land.setText(String.format("%.2f km²", landArea));
        tv_density.setText(String.format("%.2f per km²", density));
        tv_capital.setText(capital);
        tv_currency.setText(currency);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}