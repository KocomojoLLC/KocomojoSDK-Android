# KocomojoSDK for Android

## Installation

&nbsp;

### Repositories

Add KocomojoSDK Android Maven repository to your `build.gradle` file: 

```ruby
repositories {
  maven {
      url 'https://raw.githubusercontent.com/KocomojoLLC/KocomojoSDK-Android/master'
  }
}
```

### Dependencies 

Add KocomojoSDK as a dependency: 

```ruby
dependencies {
  compile 'com.kocomojo:kocomojo-sdk:0.9.822'
}
```

Then simply run `gradle` to install KocomojoSDK into your project.  

&nbsp;

## Implementation 

Import `KocomojoSDK` into your `MainApplication.java`:

`import com.kocomojo.KocomojoSDK;`

Add these lines to `onCreate()`: 

```java
KocomojoSDK.setApplication(this);

KocomojoSDK
  .getInstance()
  .setApiKey("YOUR_API_KEY_HERE")
  .setRadiusInMiles(17.0)
  .setShowPushNotifications(true);
```


### API Key validation

You can validate your api key directly by calling 

TODO 

Alternatively you can also look for an error message in logs.

&nbsp;

### Auto Show Experiences 

You can have experiences automatically pop up once the user is within range. 

To do this: 

```java
KocomojoSDK.getInstance().setAutomaticallyShowExperiences(true);
```


## Adding the Button

Inside of the layout for the *Activity* where you'd like the button to be add a fragment: 

```xml
<fragment
    android:layout_width="250dp"
    android:layout_height="250dp"
    android:name="com.kocomojo.SdkButton"
    android:id="@+id/fragment"
    android:layout_gravity="center"
    />
```

You can use the fragment as-is (blank) if you want to use it as an overlay, or you can also assign 
an enabled and disabled image to it: 

```java
KocomojoSDK.getInstance()
  .setDisabledImageName("button_disabled")
  .setEnabledImageName("button_enabled");
```

## Location Permissions 

Add this line to let KocomjoSDK know the results of it asking for permission to access location in the activity that contains the button: 

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
  KocomojoSDK.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

&nbsp;

## Local Push Notifications 

You can have KocomojoSDK send Push Notifications whenever an Experiences comes in range. 

To enable this:

```java
KocomojoSDK.getInstance()
  .setPushLaunchActivityClass(MainActivity.class)  // set it to the class of the Activity that contains the button 
  .setShowPushNotifications(true);
```

&nbsp;


### Frequency

You change the frequency of push notifications - how many seconds will need to pass after a push notification for another one to occur.  (Any notifications that would occur during this time is ignored as to not overwhelm the client.)  This defaults to 60 &#42; 60 &#42; 2 = 3600, or 2 hours.

```java 
KocomojoSDK.getInstance()
  .setMinimumSecondsBetweenPushNotifications();
```

&nbsp;

### Push Templates

There are 2 templates that can be customized by the client.  One is for `pushTemplateSingular` (when there's one Experience in range) and the other is `pushTemplatePlural` (when there's more than one Experience in range.)

They can be assigned like:

```java 
KocomojoSDK.getInstance()
  .setPushTemplateSingular("An awesome experience __NAME__ has just entered your sphere")
  .setPushTemplatePlural("__NAMES__ are all available!");
```

&nbsp;

## Notification Listener 

```java
KocomjoSDK.getInstance()
  .setNotificationListener(new KocomojoSDK.NotificationListener() {
      @Override
      public void onButtonEnabled(Boolean isEnabled) {
          Log.d("MainApplication", "Button enabled? " + isEnabled.toString());
      }

      @Override
      public void onButtonTouched() {
          Log.d("MainApplication", "Button Touched!");
      }

      @Override
      public void onExperiencesInRange(ArrayList experiencesList) {
          Log.d("MainApplication", "Experiences in range " + experiencesList.toString());
      }

      @Override
      public void onBluetoothDisabled() {
          Log.d("MainApplication", "Bluetooth Disabled!");
      }
})
```

 &nbsp;

### onButtonTouched

This simply tells when the button is touched, whether or not it is enabled.  

### onButtonEnabled

This is called when the button is either enabled or disabled.

### onExperiencesInRange

When experiences come in range (or go out of range) this will be posted.

### onBluetoothDisabled

This is called if the user launches the app with Bluetooth disabled, or if they disable Bluetooth while in the app.