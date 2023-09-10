package com.example.projetov2.viewmodel;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.example.projetov2.BuildConfig;
import com.example.projetov2.view.CustomFlutterActivity;
import com.example.projetov2.view.MainActivity;
import com.example.projetov2.view.MyReactActivity;
import com.example.projetov2.adapter.NavigateAdapter;
import com.example.projetov2.model.Informations;
import com.example.projetov2.packages.ReactPackageNative;
import com.facebook.hermes.reactexecutor.HermesExecutorFactory;
import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.soloader.SoLoader;

import java.util.List;

public class ReactIntegrationViewModel extends ViewModel implements NavigateAdapter {
    private final Informations model;
    private ReactRootView mReactRootView;

    private ReactInstanceManager mReactInstanceManager;

    public ReactIntegrationViewModel() {
        this.model = Informations.getInstance();
    }

    public ReactInstanceManager getmReactInstanceManager() {
        return mReactInstanceManager;
    }

    @Override
    public void navigateTo(AppCompatActivity activity, String route) {
        Intent intent = new Intent(activity, MyReactActivity.class);
        intent.putExtra("message_from_native", route);
        activity.startActivity(intent);
    }

    public void setModelMessage(String data){
        model.setMessage_From_Native(data);
    }

    public void renderFlutterInsideReact(String route, AppCompatActivity activity) {
        model.setRoute(route);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity, CustomFlutterActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    public void renderNativeScreen(AppCompatActivity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void initFramework(Activity appCompatActivity) {
        SoLoader.init(appCompatActivity, false);
        mReactRootView = new ReactRootView(appCompatActivity);
        List<ReactPackage> packages = new PackageList(appCompatActivity.getApplication()).getPackages();
        // Packages that cannot be autolinked yet can be added manually here, for example:
        // packages.add(new MyReactNativePackage());
        packages.add(new ReactPackageNative());
        // Remember to include them in `settings.gradle` and `app/build.gradle` too.
        SoLoader.init(appCompatActivity, false);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(appCompatActivity.getApplication())
                .setCurrentActivity(appCompatActivity)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .setJavaScriptExecutorFactory(new HermesExecutorFactory())
                .build();
        // The string here (e.g. "MyReactNativeApp") has to match
        // the string in AppRegistry.registerComponent() in index.js
        Bundle initialProperties = new Bundle();
        String messageFromNative = appCompatActivity.getIntent().getStringExtra("message_from_native");
        initialProperties.putString("message_from_native", messageFromNative);
        mReactRootView.startReactApplication(mReactInstanceManager, "MyReactNativeApp", initialProperties);
    }

    public ReactRootView getmReactRootView() {
        return mReactRootView;
    }
}
