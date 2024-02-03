package com.mkvsk.warehousewizard.ui.util;

import java.util.regex.Pattern;

final public class Constants {
    public final static String SP_TAG = "shared_preferences";
    public final static String SP_TAG_PASSWORD = "password";
    public final static String SP_TAG_USERNAME = "username";
    public final static String USERNAME_REGEX = "([a-zA-Z0-9-_]{5,32})+([-]|[_]{0,2})+([a-z]|[A-Z]{0,26})+([0-9]{0,10})";
    public final static Pattern PASSWORD_REGEX = Pattern.compile("([a-zA-Z0-9!@#$%^&*]{6,20})");

}
