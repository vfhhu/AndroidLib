# AndroidLib

dependencies {<br>
    implementation 'xyz.vfhhu.lib:android:5.0'<br>
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'<br>
    implementation 'com.orhanobut:logger:2.2.0'<br>
}<br>

#How to use
public class MainActivity extends BaseActivity{<br>
    @Override<br>
    protected void onCreate(Bundle savedInstanceState) {<br>
        super.onCreate(savedInstanceState);<br>
        VfhhuLib.setDebug(BuildConfig.DEBUG);<br>
    }<br>
}<br>


