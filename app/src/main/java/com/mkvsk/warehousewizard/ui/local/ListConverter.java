package com.mkvsk.warehousewizard.ui.local;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListConverter {
    @TypeConverter
    public static String toString(List<String> list) {
        return list != null ? String.join("|", list) : "";
    }

    @TypeConverter
    public static List<String> toList(String string) {
        if (string == null || string.isEmpty()) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(string.split("\\|")));
        }
    }

}
