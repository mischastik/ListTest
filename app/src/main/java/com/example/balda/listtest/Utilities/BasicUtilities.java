package com.example.balda.listtest.Utilities;

import java.util.List;

/**
 * Created by balda on 19.02.2017.
 */

public class BasicUtilities {
    public static String getNameForBeerTypeID(long id) {
        switch ((int)id) {
            case 1:
                return "Helles";
            case 2:
                return "Dunkles";
            case 3:
                return "Pils";
            case 4:
                return "Weizen";
            default:
                return "n/a";
        }
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
