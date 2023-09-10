package com.example.projetov2.methodModule;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetov2.model.Investiment;
import com.example.projetov2.view.MyReactActivity;
import com.example.projetov2.viewmodel.ReactIntegrationViewModel;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class NativeFunctions extends ReactContextBaseJavaModule {
    public NativeFunctions(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "NativeFunctions";
    }

    @ReactMethod
    public void navigateToFlutter(String investmentData) {
        MyReactActivity currentActivity = (MyReactActivity) getCurrentActivity();
        assert currentActivity != null;
        ReactIntegrationViewModel viewModelReact = new ViewModelProvider(currentActivity).get(ReactIntegrationViewModel.class);
        viewModelReact.setModelMessage(investmentData);
        viewModelReact.renderFlutterInsideReact("/teste", currentActivity);
    }

    @ReactMethod
    public void goBackNative() {
        MyReactActivity currentActivity = (MyReactActivity) getCurrentActivity();
        currentActivity.finish();
    }

    @ReactMethod
    public void getAPIData(Promise promise){
        try {
            List<Investiment> investimentList = new ArrayList<>();
            investimentList.add(new Investiment("1", "Investimento 01"));
            investimentList.add(new Investiment("2", "Investimento 02"));
            investimentList.add(new Investiment("3", "Investimento 03"));
            investimentList.add(new Investiment("4", "Investimento 04"));
            investimentList.add(new Investiment("5", "Investimento 05"));
            investimentList.add(new Investiment("6", "Investimento 06"));
            investimentList.add(new Investiment("7", "Investimento 07"));
            Gson gson = new Gson();
            promise.resolve(gson.toJson(investimentList));
        } catch (Exception e){
            promise.reject(e.toString());
        }
    }

    @ReactMethod
    public void validateBiometric() {
        MyReactActivity currentActivity = (MyReactActivity) getCurrentActivity();
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BiometricPrompt biometricPrompt;
                BiometricPrompt.PromptInfo promptInfo;
                BiometricManager biometricManager = BiometricManager.from(currentActivity);
                switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                        Toast.makeText(currentActivity.getApplicationContext(), "Device doesn`t have fingerprint", Toast.LENGTH_LONG).show();
                        break;

                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        Toast.makeText(currentActivity.getApplicationContext(), "Not Working", Toast.LENGTH_LONG).show();
                        break;

                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                        Toast.makeText(currentActivity.getApplicationContext(), "No Fingerprint assigned", Toast.LENGTH_LONG).show();
                        break;
                }

                Executor executor = ContextCompat.getMainExecutor(currentActivity);

                biometricPrompt = new BiometricPrompt(currentActivity, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        Toast.makeText(currentActivity.getApplicationContext(), "Pagamento efetuado", Toast.LENGTH_LONG).show();
                        ReactIntegrationViewModel viewModelReact = new ViewModelProvider(currentActivity).get(ReactIntegrationViewModel.class);
                        viewModelReact.renderNativeScreen(currentActivity);
                        super.onAuthenticationSucceeded(result);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });

                promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Confirme o pagamento").setDescription("Confirme o pagamento usando sua digital").setDeviceCredentialAllowed(true).build();
                biometricPrompt.authenticate(promptInfo);
            }
        });


    }
}

