package com.kocomojo.simpleexample;

import android.app.Application;

/**
 * Created by elijahwindsor on 1/27/17.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        KocomojoSDK.setApplication(this);

        KocomojoSDK
                .getInstance()
                .setApiKey("9lKZWgRmmYn3DmzYjnpVrkvFP396uXnFWIdp5vK27w~DEV")
                .setRadiusInMiles(17.0)
                .setMinimumSecondsBetweenPushNotifications(100)
                .setAutomaticallyShowExperiences(false)
                .setShowPushNotifications(true)
                .setPushNotificationTitle("Kocomojo")
                .setPushLaunchActivityClass(MainActivity.class)
                .setPushSmallIcon(R.mipmap.ic_launcher)
                .setDisabledImageName("button_disabled")
                .setEnabledImageName("button_enabled");
    }

}
