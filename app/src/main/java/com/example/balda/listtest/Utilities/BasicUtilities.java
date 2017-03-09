package com.example.balda.listtest.Utilities;

import java.util.List;

/**
 * Created by balda on 19.02.2017.
 */

public class BasicUtilities {
    public static final String[] BEER_TYPES = new String[] { "n/a", "Helles", "Dunkles", "Pils", "Weizen"};

    public static String getNameForBeerTypeID(long id) {
        if (id >= BEER_TYPES.length || id < 0)
            return "n/a";

        return BEER_TYPES[(int)id];
    }

    public static float calculateAvgRating(List<String> ratings) {
        float avgRating = 0.0f;
        int nRatings = 0;
        if (ratings != null) {
            for (String ratingStr : ratings) {
                float rating = (float)Integer.parseInt(ratingStr);
                if (rating == 0.0f) {
                    continue;
                }
                nRatings++;
                avgRating += rating;
            }
            if (nRatings > 0)
                avgRating /= nRatings;
            else
                avgRating = 0.0f;
        }
        return avgRating;
    }
}
