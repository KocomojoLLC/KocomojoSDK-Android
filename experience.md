<a href="#" onClick="javascript:window.history.back();">&lt; Back</a>

# KocomojoSDK for iOS

## Using KocomojoExperience

Inside of the layout for the *Activity* add this fragment -- it will need to take up the whole viewing area: 

```xml
    <fragment
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.kocomojo.SdkExperience"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_marginLeft="0dp"
        android:layout_marginStart="0dp"
        android:layout_marginTop="0dp"
        android:id="@+id/fragment" />
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
