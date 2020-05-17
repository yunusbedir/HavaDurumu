# HavaDurumu
MVVM architecture, LiveData, Rxjava, Navigation Component, Retrofit2 örnek projesi


### Kullanılan kütüphaneler
build.gradle(app level)
```kotlin
apply plugin: "androidx.navigation.safeargs.kotlin"
apply plugin: 'kotlin-kapt'
...
dependencies {
    ...
    //Navigation Components
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

    //Retrofit
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"

    //RxJava
    implementation "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
    implementation "io.reactivex.rxjava2:rxandroid:$rxJavaVersion"

    //Room
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"

    //Google Places
    implementation 'com.google.android.libraries.places:places:2.2.0'
}
```
build.gradle(project level)
```kotlin
buildscript{
    ...
    dependencies {
        ...
        //Navigation Components
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
    }
}
```

## HavaDurumu Ekranı

![](https://github.com/yunusbedir/HavaDurumu/blob/master/ss/current_fragment.png)

## Ayarlar Sayfası 

![](https://github.com/yunusbedir/HavaDurumu/blob/master/ss/settings_fragment_1.png)

## Ayarlar Sayfası Konum Arama

![](https://github.com/yunusbedir/HavaDurumu/blob/master/ss/settings_fragment_2.png)

