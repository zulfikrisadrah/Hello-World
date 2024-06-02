package com.example.helloworld.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.helloworld.R;
//import com.example.helloworld.RedeemCode;
import com.example.helloworld.Model.User;
import com.example.helloworld.UserPreferences;

import java.util.ArrayList;
import java.util.List;

public class CodeFragment extends Fragment {
    private EditText et_code;
    private TextView okBtn;
    private UserPreferences userPreferences;

    public CodeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_code, container, false);

        et_code = view.findViewById(R.id.et_code);
        okBtn = view.findViewById(R.id.ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_code.getText().toString().trim();
                et_code.setText("");
                if (!code.isEmpty()) {
                    redeemCode(code);
                } else {
                    showErrorMessage("Please enter a redeem code.");
                }
            }
        });

        userPreferences = new UserPreferences(requireContext());

        return view;
    }

    private void redeemCode(String inputCode) {
        List<RedeemCode> redeemCodes = new ArrayList<>();
        redeemCodes.add(new RedeemCode("hello", "coin", 5));
        redeemCodes.add(new RedeemCode("world", "life", 5));
        redeemCodes.add(new RedeemCode("zul", "coin", 3));
        redeemCodes.add(new RedeemCode("fikri", "life", 3));

        boolean codeFound = false;
        for (RedeemCode redeemCode : redeemCodes) {
            if (redeemCode.getCode().equalsIgnoreCase(inputCode)) {
                codeFound = true;
                String rewardType = redeemCode.getRewardType();
                int rewardAmount = redeemCode.getRewardAmount();
                applyReward(rewardType, rewardAmount);
                break;
            }
        }

        if (!codeFound) {
            showRedeemFailedDialog("The code you entered is invalid.");
        }
    }

    private void applyReward(String rewardType, int rewardAmount) {
        User user = userPreferences.getUser();
        switch (rewardType) {
            case "coin":
                int currentCoins = user.getCoin();
                user.setCoin(currentCoins + rewardAmount);
                showRedeemSuccessDialog("You have successfully redeemed your reward. You received " + rewardAmount + " coins.");
                break;
            case "life":
                int currentLife = user.getLife();
                user.setLife(currentLife + rewardAmount);
                showRedeemSuccessDialog("You have successfully redeemed your reward. You received " + rewardAmount + " life.");
                break;
        }

        userPreferences.saveUser(user);
    }

    private void showRedeemSuccessDialog(String message) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.redeem_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvRedeem = dialog.findViewById(R.id.tv_redeem);
        tvRedeem.setText(message);

        TextView btnOK = dialog.findViewById(R.id.yes_btn);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showRedeemFailedDialog(String message) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.redeem_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvRedeem = dialog.findViewById(R.id.tv_redeem);
        tvRedeem.setText(message);

        TextView btnOK = dialog.findViewById(R.id.yes_btn);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showErrorMessage(String message) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.redeem_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvRedeem = dialog.findViewById(R.id.tv_redeem);
        tvRedeem.setText(message);

        TextView btnOK = dialog.findViewById(R.id.yes_btn);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private class RedeemCode {
        private String code;
        private String rewardType;
        private int rewardAmount;

        public RedeemCode(String code, String rewardType, int rewardAmount) {
            this.code = code;
            this.rewardType = rewardType;
            this.rewardAmount = rewardAmount;
        }

        public String getCode() {
            return code;
        }

        public String getRewardType() {
            return rewardType;
        }

        public int getRewardAmount() {
            return rewardAmount;
        }
    }
}
