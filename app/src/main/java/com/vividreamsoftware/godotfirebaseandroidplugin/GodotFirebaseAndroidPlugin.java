package com.vividreamsoftware.godotfirebaseandroidplugin;

import android.os.Bundle;
import android.util.ArraySet;

import androidx.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.Dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        return Arrays.asList("setAnalyticsEnabled", "logEvent", "analyticsEnabledSetSignal", "eventLoggedSignal");
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();
        signals.add(new SignalInfo("analyticsEnabledSet", Boolean.class));
        signals.add(new SignalInfo("eventLogged", String.class));
        return signals;
    }

    public void setAnalyticsEnabled(boolean consented){

        mFirebaseAnalytics.setAnalyticsCollectionEnabled(consented);
        analyticsEnabledSetSignal(consented);
    }

    public void analyticsEnabledSetSignal(boolean consented){
        emitSignal("analyticsEnabledSet", consented);
    }

    public void logEvent(String eventName, Dictionary eventObject){
        Bundle bundle = new Bundle();
        for(Map.Entry<String, Object> entry : eventObject.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            bundle.putString(key, value.toString());
        }

        mFirebaseAnalytics.logEvent(eventName, bundle);
        eventLoggedSignal(eventName);
    }

    public void eventLoggedSignal(String eventName){
        emitSignal("eventLogged", eventName);
    }
}
