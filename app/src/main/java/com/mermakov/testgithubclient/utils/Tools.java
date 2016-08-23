package com.mermakov.testgithubclient.utils;

import java.util.Collection;

/**
 * Created by max_ermakov on 8/22/16.
 */
public class Tools {
    /**
     * Method check is collection null or empty
     *
     * @param collection - Collection to check
     * @return true if Collection null or not null but empty
     */
    //TODO: synchronized?? Weird. Seems like workaround for bad threading code
    public static synchronized boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Method check is String null or empty
     *
     * @param string - String to check
     * @return true if string null or not null but empty
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
