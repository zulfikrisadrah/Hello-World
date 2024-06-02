package com.example.helloworld.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helloworld.R;
import com.example.helloworld.Model.User;
import com.example.helloworld.UserPreferences;

public class StoreFragment extends Fragment {
    private TextView tv_coin, tv_life;
    private FrameLayout life1, life3, life5, life10;
    private UserPreferences userPreferences;

    public StoreFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPreferences = new UserPreferences(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        life1 = view.findViewById(R.id.life1);
        life3 = view.findViewById(R.id.life3);
        life5 = view.findViewById(R.id.life5);
        life10 = view.findViewById(R.id.life10);

        tv_coin = view.findViewById(R.id.tv_coin);
        tv_life = view.findViewById(R.id.tv_life);

        User user = userPreferences.getUser();

        tv_coin.setText(String.valueOf(user.getCoin()));
        tv_life.setText(String.valueOf(user.getLife()));

        life1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(1, 1);
            }
        });

        life3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(3, 3);
            }
        });

        life5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(5, 4);
            }
        });

        life10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(10, 7);
            }
        });

        return view;
    }

    private void showConfirmationDialog(final int life, final int coin) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.confirm_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView noBtn = dialog.findViewById(R.id.no_btn);
        TextView yesBtn = dialog.findViewById(R.id.yes_btn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                performPurchase(life, coin);
            }
        });

        dialog.show();
    }

    private void performPurchase(int life, int coin) {
        User user = userPreferences.getUser();

        if (user.getCoin() >= coin) {
            int remainingCoins = user.getCoin() - coin;
            user.setCoin(remainingCoins);

            int newLife = user.getLife() + life;
            user.setLife(newLife);

            userPreferences.saveUser(user);

            updateUI();

            Toast.makeText(requireContext(), "You purchased " + life + " life with " + coin + " coins!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "You don't have enough coins!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        User user = userPreferences.getUser();
        tv_coin.setText(String.valueOf(user.getCoin()));
        tv_life.setText(String.valueOf(user.getLife()));
    }

}
