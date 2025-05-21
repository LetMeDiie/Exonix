package kz.amihady.exonix.license.service;

import java.util.function.Consumer;

public class UpdateHelper {
    public static <T> void updateIfNotNull(T newValue, Consumer<T> updater){
        if(newValue != null) {
            updater.accept(newValue);
        }
    }
}
