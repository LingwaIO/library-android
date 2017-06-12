# LingwaIO Android

#### Installation

1.  Add to build.gradle
```java
compile 'io.lingwa.android:lingwaio:0.1.3'
```

2.  Add permission in Manifest
```java
<uses-permission android:name="android.permission.INTERNET"/>
```

3.  Add in the Manifest application tag 
```java
<meta-data
  android:name="LingwaProjectCode"
  android:value="*PROJECT CODE FROM SITE*"/>
```

4.  Initialize and download labels in splash/where appropriate
```java
Lingwa.init(this, new Lingwa.OnInitialize() {
    @Override
    public void onInitializeSuccess() {
        //labels are now available
        //close splash screen or get label value
    }

    @Override
    public void onInitializeFail(String error) {
        Log.d(TAG, error);
    }
});
```

5.  Get label in TextView
```java
Lingwa.with(this)
  .label("welcome") //label from site
  .into(textView);
```

Optional.  In th Application class' onCreate method add 
```java
Lingwa.getConfiguration(this)
  .setExpiryMinutes(1)
  .setDebug(true);
```

### Todos

License
----

MIT
