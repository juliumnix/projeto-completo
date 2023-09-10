package com.example.projetov2.view;

import static com.example.projetov2.model.Informations.getChannelFlutter;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.projetov2.model.Informations;
import com.example.projetov2.viewmodel.FlutterIntegrationViewModel;


import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

public class CustomFlutterActivity extends FlutterActivity{
    private FlutterIntegrationViewModel viewModelFlutter;

    private final ViewModelStore viewModelStore = new ViewModelStore();

    @Override
    protected void onResume() {
        super.onResume();
        viewModelFlutter.initFramework(this);
    }

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        viewModelFlutter = new ViewModelProvider(new ViewModelStoreOwner() {
            @NonNull
            @Override
            public ViewModelStore getViewModelStore() {
                return viewModelStore;
            }
        }).get(FlutterIntegrationViewModel.class);
        viewModelFlutter.initFramework(this);
        viewModelFlutter.initializeListenerFromFlutterToNative(flutterEngine);
    }


    @Override
    protected void onPause() {
        super.onPause();
        viewModelFlutter.destroyInstance();
    }
}
