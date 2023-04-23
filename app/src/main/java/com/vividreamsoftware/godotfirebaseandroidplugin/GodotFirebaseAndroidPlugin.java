package com.vividreamsoftware.godotfirebaseandroidplugin;

import android.content.Context;
import android.util.ArraySet;

import androidx.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GodotFirebaseAndroidPlugin extends GodotPlugin{
    private FirebaseAnalytics mFirebaseAnalytics;

    public GodotFirebaseAndroidPlugin(Godot godot) {
        super(godot);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.getActivity());
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodotFirebaseAndroidPlugin";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("getHello", "getHelloSignal", "initializeFirebase");
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();
        signals.add(new SignalInfo("testSignal", String.class));
        return signals;
    }

    public String getHello(){
        getHelloSignal("this is our test signal");
        return "Hello World";
    }

    public void provideAnalyticsConsent(){
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void getHelloSignal(String s){
        emitSignal("testSignal", s);
    }
}
