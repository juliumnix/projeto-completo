package com.example.projetov2.view;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetov2.viewmodel.ReactIntegrationViewModel;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

public class MyReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private ReactIntegrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        viewModel = new ViewModelProvider(this).get(ReactIntegrationViewModel.class);
        viewModel.initFramework(this);
        setContentView(viewModel.getmReactRootView());
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (viewModel.getmReactInstanceManager() != null) {
            viewModel.getmReactInstanceManager().onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel.getmReactInstanceManager() != null) {
            viewModel.getmReactInstanceManager().onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (viewModel.getmReactInstanceManager() != null) {
            viewModel.getmReactInstanceManager().onHostDestroy(this);
        }
        if (viewModel.getmReactRootView() != null) {
            viewModel.getmReactRootView().unmountReactApplication();
        }
    }

    @Override
    public void onBackPressed() {
        if (viewModel.getmReactInstanceManager() != null) {
            viewModel.getmReactInstanceManager().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && viewModel.getmReactInstanceManager() != null) {
            viewModel.getmReactInstanceManager().showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
