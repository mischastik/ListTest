package com.example.balda.listtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.nio.ByteBuffer;

/**
 * Created by balda on 15.02.2017.
 */

public class BeerListDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "beerlist.db";
    private static final int DATABASE_VERSION = 1;

    public BeerListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_BEERLIST_TABLE =

                "CREATE TABLE " + BeerListContract.ListEntry.TABLE_NAME + " (" +

                /*
                 * WeatherEntry did not explicitly declare a column called "_ID". However,
                 * WeatherEntry implements the interface, "BaseColumns", which does have a field
                 * named "_ID". We use that here to designate our table's primary key.
                 */
                BeerListContract.ListEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                BeerListContract.ListEntry.COLUMN_NAME       + " STRING NOT NULL, " +
                BeerListContract.ListEntry.COLUMN_BREWERY       + " STRING NOT NULL, "  +

                BeerListContract.ListEntry.COLUMN_TYPE + " INTEGER NOT NULL," +

                BeerListContract.ListEntry.COLUMN_LOCATION_BREWERY   + " STRING, " +
                BeerListContract.ListEntry.COLUMN_LOGO_IMAGE   + " BLOB, " +

                BeerListContract.ListEntry.COLUMN_BEER_IMAGE   + " BLOB, " +
                BeerListContract.ListEntry.COLUMN_TASTERS   + " REAL NOT NULL, " +

                BeerListContract.ListEntry.COLUMN_TASTING_DATES + " INTEGER NOT NULL, " +

                /*
                 * To ensure this table can only contain one weather entry per date, we declare
                 * the date column to be unique. We also specify "ON CONFLICT REPLACE". This tells
                 * SQLite that if we have a weather entry for a certain date and we attempt to
                 * insert another weather entry with that date, we replace the old weather entry.
                 */
                        " UNIQUE (" + BeerListContract.ListEntry.COLUMN_NAME + ") ON CONFLICT REPLACE);";

        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        sqLiteDatabase.execSQL(SQL_CREATE_BEERLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BeerListContract.ListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    protected long saveEntry(SQLiteDatabase database, String name, String brewery, int type, String locationBrewery, Bitmap logoImage, Bitmap beerImage, int[] tasters, long[] tastingDates)
    {
        ContentValues cv = new ContentValues();
        if (beerImage != null) {
            PutImage(cv, BeerListContract.ListEntry.COLUMN_BEER_IMAGE, beerImage);
        }
        if (logoImage != null) {
            PutImage(cv, BeerListContract.ListEntry.COLUMN_LOGO_IMAGE, logoImage);
        }
        cv.put(BeerListContract.ListEntry.COLUMN_NAME, name);
        cv.put(BeerListContract.ListEntry.COLUMN_BREWERY, brewery);
        cv.put(BeerListContract.ListEntry.COLUMN_LOCATION_BREWERY, locationBrewery);
        return database.insert(BeerListContract.ListEntry.TABLE_NAME, null, cv);
    }
    private void PutImage(ContentValues cv, String key, Bitmap bmp) {
        int size = bmp.getRowBytes() * bmp.getHeight();
        ByteBuffer b = ByteBuffer.allocate(size);
        bmp.copyPixelsToBuffer(b);
        byte[] bytes = new byte[size];
        b.get(bytes, 0, bytes.length);
        cv.put(key, bytes);
    }
}
