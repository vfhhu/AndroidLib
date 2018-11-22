# AndroidLib

```gradle
dependencies {
	implementation 'xyz.vfhhu.lib:android:5.0'
	implementation 'com.squareup.okhttp3:okhttp:3.10.0'
	implementation 'com.orhanobut:logger:2.2.0'
}
```
# How to use
```java
public class MainActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VfhhuLib.setDebug(BuildConfig.DEBUG);
	}
}
```
