package com.example.projetov2.adapter;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

public interface NavigateAdapter {
    void navigateTo (AppCompatActivity activity, String route);
    void initFramework(Activity appCompatActivity);
}
