package com.vinay.phonepequiz.util;

import android.app.Activity;

import java.io.InputStream;

public class Utility {
    public static char[] user_submit_answer;
    public static int count=1;
    public static String [] alphabest_character={
      "a","b","c","d","e","f","g","h","i","j","k","l","m","n",
            "o","p","q","r","s","t","u","v","w","x","y","z"
    };


    public static String getJsonConfig(Activity activity) {
        String json = "";
        try {
            InputStream inputStream = activity.getAssets().open("config_quiz.json"); // for complete test
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
