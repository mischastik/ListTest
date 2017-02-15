package com.example.balda.listtest;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.BaseColumns;

import java.nio.ByteBuffer;

/**
 * Created by balda on 15.02.2017.
 */

public class BeerListContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.balda.listtest";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_TASKS = "beerlist";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class ListEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();


        // Task table and column names
        public static final String TABLE_NAME = "beerlist";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BREWERY = "brewery";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_LOCATION_BREWERY = "location";
        public static final String COLUMN_TASTERS = "tasters";
        public static final String COLUMN_TASTING_DATES = "tasting_dates";
        public static final String COLUMN_LOGO_IMAGE = "logo";
        public static final String COLUMN_BEER_IMAGE = "beer_image";
    }
}
