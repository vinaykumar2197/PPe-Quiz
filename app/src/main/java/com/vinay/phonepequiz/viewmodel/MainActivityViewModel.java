package com.vinay.phonepequiz.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.Random;

public class MainActivityViewModel extends AndroidViewModel {
    public int totalPoints = 0;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }


}
