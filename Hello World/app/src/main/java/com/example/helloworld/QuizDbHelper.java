package com.example.helloworld;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.helloworld.Model.Country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HelloWorld.db";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_COUNTRIES = "countries";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POPULATION = "population";
    private static final String COLUMN_LAND_AREA = "land_area";
    private static final String COLUMN_DENSITY = "density";
    private static final String COLUMN_CAPITAL = "capital";
    private static final String COLUMN_CURRENCY = "currency";
    private static final String COLUMN_FLAG = "flag";
    private static final String COLUMN_LOCKED = "locked";

    private Context context;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_COUNTRIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_POPULATION + " INTEGER, " +
                COLUMN_LAND_AREA + " INTEGER, " +
                COLUMN_DENSITY + " REAL, " +
                COLUMN_CAPITAL + " TEXT, " +
                COLUMN_CURRENCY + " TEXT, " +
                COLUMN_FLAG + " TEXT, " +
                COLUMN_LOCKED + " INTEGER DEFAULT 1" +
                ")";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        onCreate(db);
    }

    public void insertCountry(Country country) {
        SQLiteDatabase db = getWritableDatabase();

        if (countryExists(country.getId())) {
            updateCountry(country);
        } else {
            db.insert(TABLE_COUNTRIES, null, countryToContentValues(country));
        }

        db.close();
    }

    private boolean countryExists(int countryId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_COUNTRIES + " WHERE " + COLUMN_ID + "=?", new String[]{String.valueOf(countryId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    private void updateCountry(Country country) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_COUNTRIES, countryToContentValues(country), COLUMN_ID + "=?", new String[]{String.valueOf(country.getId())});
        db.close();
    }

    private ContentValues countryToContentValues(Country country) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, country.getId());
        values.put(COLUMN_NAME, country.getName());
        values.put(COLUMN_POPULATION, country.getPopulation());
        values.put(COLUMN_LAND_AREA, country.getLandArea());
        values.put(COLUMN_DENSITY, country.getDensity());
        values.put(COLUMN_CAPITAL, country.getCapital());
        values.put(COLUMN_CURRENCY, country.getCurrency());
        values.put(COLUMN_FLAG, country.getFlag());
        values.put(COLUMN_LOCKED, country.isLocked() ? 1 : 0);
        return values;
    }

    public List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COUNTRIES, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Country country = new Country(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POPULATION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LAND_AREA)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DENSITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPITAL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURRENCY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLAG)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOCKED)) == 1
                );

                countryList.add(country);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return countryList;
    }

    public Country getRandomCountryExcept(List<Integer> askedCountryIds) {
        List<Country> availableCountries = getAvailableCountries(askedCountryIds);

        if (!availableCountries.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableCountries.size());
            return availableCountries.get(randomIndex);
        }
        return null;
    }


    private List<Country> getAvailableCountries(List<Integer> askedCountryIds) {
        List<Country> availableCountries = new ArrayList<>();
        List<Country> allCountries = getAllCountries();

        for (Country country : allCountries) {
            if (!askedCountryIds.contains(country.getId())) {
                availableCountries.add(country);
            }
        }
        return availableCountries;
    }

    public Country getRandomCountry() {
        List<Country> allCountries = getAllCountries();
        Random random = new Random();
        int randomIndex = random.nextInt(allCountries.size());
        return allCountries.get(randomIndex);
    }
}
