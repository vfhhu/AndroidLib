# AndroidLib

```gradle
dependencies {
	implementation 'xyz.vfhhu.lib:android:7.0'
	implementation 'com.squareup.okhttp3:okhttp:3.10.0'
	implementation 'com.orhanobut:logger:2.2.0'
}
```
# How to use
## init
In LAUNCHER Activity like MainActivity
```java
VfhhuLib.setDebug(BuildConfig.DEBUG);//run one time
```
## BaseActivity
```java
public class MainActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		VfhhuLib.setDebug(BuildConfig.DEBUG);//run one time

        //Android log show in debug mod
        log("message");
        loggger("message");//orhanobut logger

        //auto controll view on ui thread
        showView(view);
        hideView(view);
        setTextView(textview,"string");

        //startActivityForResult and callback
        startActRet(intent, code,new ActivityResultCallback() {
             @Override
             public void onActivityResult(int requestCode, int resultCode, Intent data) {

             }
         });
	}
}
```
## FileUtil
simple way to controll file<br>
FileUtil will use ctx.getExternalFilesDir creat folder in app package<br>
and controll file at the same folder
```java
//new
FileUtil fu=FileUtil(context,"dirname");

//save
fu.saveBytes(String filename, byte[] data);
fu.saveBytes(File f, byte[] data);

//read
byte[] data=fu.readBytes(File f);
byte[] data=fu.readBytes(String filename);

//delete
fu.delete(String filename);
fu.delete(File f);
fu.clearAllFile();

//list
File[] lsitf=fu.listFiles(FilenameFilter ff);
String[] lsitf=fu.listFiles(FilenameFilter ff);

//file name to file
fu.getFile(String filename);
```
## SharedPreferences
simple way to controll SharedPreferences<br>
### save
```java
VfhhuLib.setSaveString(Context ct, String tag, String data);
VfhhuLib.setSaveInt(Context ct, String tag, int data);
VfhhuLib.setSaveLong(Context ct, String tag, long data);
VfhhuLib.setSaveFloat(Context ct, String tag, float data);
```
### get
```java
VfhhuLib.getSaveString(Context ct, String tag, String defultV);
VfhhuLib.getSaveInt(Context ct, String tag, int defultV);
VfhhuLib.getSaveLong(Context ct, String tag, long defultV);
VfhhuLib.getSaveFloat(Context ct, String tag, float defultV);
```
### remove
```java
VfhhuLib.remove(Context ct,String tag);
VfhhuLib.clear(Context ct);
```