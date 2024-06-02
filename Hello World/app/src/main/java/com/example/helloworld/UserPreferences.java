package com.example.helloworld;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.helloworld.Model.User;

import java.util.HashSet;
import java.util.Set;

public class UserPreferences {

    private static final String PREF_NAME = "UserPreferences";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_LIFE = "user_life";
    private static final String KEY_USER_COIN = "user_coin";
    private static final String KEY_USER_LEVEL_CAPITAL = "user_level_capital";
    private static final String KEY_USER_LEVEL_CURRENCY = "user_level_currency";
    private static final String KEY_UNLOCKED_COUNTRIES = "unlocked_countries";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUser(User user) {
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putInt(KEY_USER_LIFE, user.getLife());
        editor.putInt(KEY_USER_COIN, user.getCoin());
        editor.putInt(KEY_USER_LEVEL_CAPITAL, user.getLevelCapital());
        editor.putInt(KEY_USER_LEVEL_CURRENCY, user.getLevelCurrency());
        editor.apply();
    }

    public User getUser() {
        int id = sharedPreferences.getInt(KEY_USER_ID, 1);
        int life = sharedPreferences.getInt(KEY_USER_LIFE, 5);
        int coin = sharedPreferences.getInt(KEY_USER_COIN, 5);
        int levelCapital = sharedPreferences.getInt(KEY_USER_LEVEL_CAPITAL, 1);
        int levelCurrency = sharedPreferences.getInt(KEY_USER_LEVEL_CURRENCY, 1);

        return new User(id, life, coin, levelCapital, levelCurrency);
    }

    public void addUnlockedCountryId(int countryId) {
        Set<String> unlockedCountries = getUnlockedCountries();
        unlockedCountries.add(String.valueOf(countryId));
        editor.putStringSet(KEY_UNLOCKED_COUNTRIES, unlockedCountries);
        editor.apply();
    }

    private Set<String> getUnlockedCountries() {
        return sharedPreferences.getStringSet(KEY_UNLOCKED_COUNTRIES, new HashSet<>());
    }

    public boolean isCountryUnlocked(int countryId) {
        Set<String> unlockedCountries = getUnlockedCountries();
        return unlockedCountries.contains(String.valueOf(countryId));
    }

    public void addLife(int amount) {
        int currentLife = sharedPreferences.getInt(KEY_USER_LIFE, 0);
        if (currentLife < 5) {
            currentLife += amount;
            editor.putInt(KEY_USER_LIFE, currentLife);
            editor.apply();
        }
    }

    public int getLife() {
        return sharedPreferences.getInt(KEY_USER_LIFE, 0);
    }

}
