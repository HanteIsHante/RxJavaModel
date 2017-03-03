# RxJavaModel


#####RxJava
**compile 'io.reactivex.rxjava2:rxjava:2.0.4'**

**compile 'io.reactivex.rxjava2:rxandroid:2.0.1'**
_

##### Lambda AS 添加示例

`主要添加 jackOptions 代码块
及 compileOptions 代码块`

```
android {
    compileSdkVersion 25
    buildToolsVersion "25.0.0"
    defaultConfig {
        applicationId "com.example.hante.rxjavamodel"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

        jackOptions {
            enabled true
        }
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}


```






