<a href="#" onClick="javascript:window.history.back();">&lt; Back</a>

# KocomojoSDK for iOS

## Using KocomojoExperience

### Simple Approach

There are two ways you can implement an experience-based app. The easiest is declaring `com.kocomojo.SdkExperienceActivity` as the main activity in your application's `AndroidManifest.xml`:

```xml
  <activity android:name="com.kocomojo.SdkExperienceActivity">
      <intent-filter>
          <action android:name="android.intent.action.MAIN" />

          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
  </activity>
```

Your app will now launch with this activity and show the main experience from the web once it has loaded.

### Complex Approach

The other more complex way of implementing an experience-based app comes in handy if you want a splash screen to show when loading the data from the server. Since data is cached from the previous load of the app, this comes in handy on subsequent launches when you want to make sure the user is viewing the most updated data.  

To do this:


#### Step 1

In your main activity's `onCreate` start the `com.kocomojo.LoadDataService`:

```java
Intent loadDataServiceIntent = new Intent(this, com.kocomojo.LoadDataService.class);
startService(loadDataServiceIntent);
```

#### Step 2

Add a `NotificationListener` (which is discussed in more detail later on in the docs):

```java
KocomjoSDK.getInstance()
  .setNotificationListener(new KocomojoSDK.ExperienceNotificationListener() {
      @Override
      public void onMainExperienceReady(Boolean isCached) {
          if(sdkExperienceActivityIntent == null && isCached == false) { // freshly loaded
            runOnUiThread(new Runnable() {
              public void run() {
                sdkExperienceActivityIntent = new Intent(MainActivity.this, com.kocomojo.SdkExperienceActivity.class);
                MainActivity.this.startActivity(sdkExperienceActivityIntent);

                MainActivity.this.finish();
              }
            });
          }

      }

      @Override
      public void onExperiencesInRange(ArrayList experiencesList) {
      }

      @Override
      public void onBluetoothDisabled() {
      }
});
```

We're telling the activity that when the Main experience has loaded from the server (`isCached == false`) AND we haven't yet created the Intent to start `com.kocomojo.SdkExperienceActivity`, create it, start it, and finish the MainActivity. Ensure that this only happens once in your MainActivity, as `onMainExperienceReady` may get called more than once due to data being loaded in both `LoadDataService` and `SdkExperienceActivity`.

This way we can ensure that the `SdkExperienceActivity` does not show until data has loaded from the server.

#### Step 3

Lastly, when the app goes into the background and the user touches the app icon again, to ensure it doesn't unnecessarily show `MainActivity` put this in your `onCreate`:


```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // from https://stackoverflow.com/a/10598619/169335
    if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
        // Activity was brought to front and not created,
        // Thus finishing this will get us to the last viewed activity
        finish();
        return;
    }
    ...
}
```

&nbsp;

## Back Button

Add this to forward key presses to KocomojoSDK -- this is used mainly for the Back Button:

```java
@Override
public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
    return KocomojoSDK.getInstance().onKeyDown(keyCode, keyEvent);
}
```

&nbsp;

## Location Permissions

Add this line to let KocomjoSDK know the results of it asking for permission to access location in the activity that contains the button:

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
  KocomojoSDK.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

&nbsp;

### Title

To set the title of Push Notifications, use `setPushNotificationTitle`:

```java
KocomojoSDK.getInstance()
  .setPushNotificationTitle("Your Title");
```

&nbsp;

### Small and Large Icons

To set the small icon of Push Notifications, use `setPushSmallIcon`:

```java
KocomojoSDK.getInstance()
  .setPushSmallIcon(R.mipmap.ic_small);
```

&nbsp;

To set the large icon of Push Notifications, use `setPushLargeIcon`.  This takes a `Bitmap`, for example:

```java
Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

KocomojoSDK.getInstance()
  .setPushLargeIcon(icon)
```
&nbsp;


### Frequency

You change the frequency of push notifications - how many seconds will need to pass after a push notification for another one to occur.  (Any notifications that would occur during this time is ignored as to not overwhelm the client.)  This defaults to 60 &#42; 60 &#42; 2 = 3600, or 2 hours.

```java
KocomojoSDK.getInstance()
  .setMinimumSecondsBetweenPushNotifications(60 * 60 * 2);
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
  .setNotificationListener(new KocomojoSDK.ExperienceNotificationListener() {
      @Override
      public void onMainExperienceReady(Boolean isCached) {
          Log.d("MainApplication", "Main experience ready " + isCached);

          if(isCached) {  // it's loaded, but not from server -- from cache

          } else { // freshly loaded from server

          }
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

### onMainExperienceReady

This will be called once on initial app launch, and twice on every other launch.  One the first launch it will be called with `isCached` as false, as it would have just loaded the main experience from the server.

On every other launch it will be called once with `isCached` as true.  After it receives data from the server, it will be called again with `isCached` as false.

When coming back to the app from the background it will also be called with `isCached` as false when it retrieves data from the server again.  

This can be useful if you have an overlay on top of the Fragment that you want to show until data is ready.

### onExperiencesInRange

When experiences come in range (or go out of range) this will be posted.

### onBluetoothDisabled

This is called if the user launches the app with Bluetooth disabled, or if they disable Bluetooth while in the app.
