package com.example.balda.listtest.Utilities;

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
}
