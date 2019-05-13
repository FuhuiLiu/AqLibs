# AqLibs

## 使用说明

1. 使用权限申请模块

1.1 作为submodule引入工程
```
git submodule add git@github.com:FuhuiLiu/AqLibs.git
```
1.2 根目录settings.gradle新入模块(这里原只有app模块)
```
include ':app', ':AqLibs'
```
1.3 app module中引用
```
implementation project(":AqLibs")
```
1.4 AndroidManifest.xml中修改主activity为目标activity
```
<activity android:name="com.aqLibs.permission.PermissionActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```
1.5 app模块string.xml添加原始启动activity回调
```
<string name="org_launchActivity_path">org_launch_activity_path</string>
```

** 注意 ** 
PermissionActivity中申请的permissions权限必须在AndroidManifest.xml中声明，否则GrantPermissionsActivity会java.lang.NullPointerException
