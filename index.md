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
  compile 'com.kocomojo:kocomojo-sdk:0.9.824'
}
```

Then simply run `gradle` to install KocomojoSDK into your project.  

&nbsp;

### 32-Bit 

Currently KocomojoSDK only supports 32-bit libraries.  To ensure compatibility, you'll need to ensure it only uses 32-bit libraries: 

```ruby
android {
  ...
  defaultConfig {
    ...
    ndk {
        abiFilters "armeabi-v7a", "x86"
    }
  }
}
```

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

```java
KocomojoSDK.getInstance().validateApiKey(new KocomojoSDK.ValidateApiKeyHandler() {
    @Override
    public void onValidated() {
        Log.d("ApiKey", "Is valid!");
    }

    @Override
    public void onInvalidated() {
        Log.d("ApiKey", "Is invalid!");
    }

    @Override
    public void onError(String message) {
        Log.d("ApiKey", "Validation error: " + message);
    }
});
```

&nbsp;

# Experience Or Button

&nbsp;

The user can enter KocomojoSDK's interface either via an Experience or a Button.  Only one of the two can exist per app. 

If the entire app is based on KocmomojoSDK (i.e. the first thing the user would see would be a screen designed in the editor), then you'd want to use `KocomojoExperience`.  

If the app has existing functionality outside of KocomojoSDK, then your app will use `KocomojoButton` as it's entry point.

&nbsp;

<a href="/experience.html" style="font-size: 2em;">Go to Experience Docs</a> <span style="font-size: 2em;">&nbsp;|&nbsp;</span> 
<a href="/button.html" style="font-size: 2em;">Go to Button Docs</a>

&nbsp;