package org.openmrs.module.freeshr.terminology.utils;

import java.util.ArrayList;
import java.util.List;

public class Lambda {

    public static <T> List<T> filter(List<T> xs, Fn<T, Boolean> fn) {
        List<T> result = new ArrayList<T>();
        for (T x : xs) {
            if (fn.call(x)) {
                result.add(x);
            }
        }
        return result;
    }

    public static <T> T first(List<T> xs){
        return xs.get(0);
    }

    public static boolean isNotEmpty(Object[] xs){
        return xs != null && xs.length > 0;
    }
    public static interface Fn<I, O> {
        public O call(I input);
    }

}
