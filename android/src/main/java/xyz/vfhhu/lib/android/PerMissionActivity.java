package xyz.vfhhu.lib.android;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerMissionActivity extends AppCompatActivity {
    public static final String TAG_PERMISSION="TAG_PERMISSION";

    public static final String TAG_DILOAG_TITLE="TAG_DILOAG_TITLE";
    public static final String TAG_DILOAG_MSG="TAG_DILOAG_MSG";
    public static final String TAG_DILOAG_BTN_RETRY="TAG_DILOAG_BTN_RETRY";
    public static final String TAG_DILOAG_BTN_EXIT="TAG_DILOAG_BTN_EXIT";

    private static final int REQUEST_PERMISSION_CODE = 1;
    public static final String TAG_RESULT_TYPE="TAG_RESULT_TYPE";
    public static final String TAG_RESULT_MSG="TAG_RESULT_MSG";

    public static final int VALUE_RESULT_SUCCESS=-1;
    public static final int VALUE_RESULT_ERROR=10;
    public static final int VALUE_RESULT_REJECT=0;


    private static String diloag_title="permission";
    private static String diloag_msg="need to authorization  all permission";
    private static String diloag_btn_retry="repermission";
    private static String diloag_btn_exit="exit";

    private String permissionA[];
    private String PermissionDangerous[]=new String[]{
            android.Manifest.permission.READ_CALENDAR,
            android.Manifest.permission.WRITE_CALENDAR,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.GET_ACCOUNTS,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.WRITE_CALL_LOG,
            android.Manifest.permission.ADD_VOICEMAIL,
            android.Manifest.permission.USE_SIP,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
            android.Manifest.permission.BODY_SENSORS,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_WAP_PUSH,
            android.Manifest.permission.RECEIVE_MMS,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_per_mission);
        try{
            List<String> listP=new ArrayList<String>();
            List listD=Arrays.asList(PermissionDangerous);
            String permissionTmp[]=this.getIntent().getStringArrayExtra(TAG_PERMISSION);
            for(String str:permissionTmp){
                if(listD.contains(str))listP.add(str);
            }

            if(listP.size()>0){
                permissionA=listP.toArray(new String[listP.size()]);
            }else{
                onError("no dangerous permission need to authorization ");
                return;
            }

        }catch(Exception e){
            e.printStackTrace();
            onError("no permission need to authorization ");
            return;
        }
        try{
            if(permissionA==null || permissionA.length<=0){
                onError("no permission need to authorization");
                return;
            }
        }catch (Exception e){e.printStackTrace();}

        try{diloag_title= (getIntent().getStringExtra(TAG_DILOAG_TITLE)==null)?diloag_title:getIntent().getStringExtra(TAG_DILOAG_TITLE);}catch(Exception e){e.printStackTrace();}
        try{diloag_msg= (getIntent().getStringExtra(TAG_DILOAG_MSG)==null)?diloag_msg:getIntent().getStringExtra(TAG_DILOAG_MSG);}catch(Exception e){e.printStackTrace();}
        try{diloag_btn_retry= (getIntent().getStringExtra(TAG_DILOAG_BTN_RETRY)==null)?diloag_btn_retry:getIntent().getStringExtra(TAG_DILOAG_BTN_RETRY);}catch(Exception e){e.printStackTrace();}
        try{diloag_btn_exit= (getIntent().getStringExtra(TAG_DILOAG_BTN_EXIT)==null)?diloag_btn_exit:getIntent().getStringExtra(TAG_DILOAG_BTN_EXIT);}catch(Exception e){e.printStackTrace();}

        if(checkPermissionsReq(permissionA)){
            Intent i=new Intent();
            i.putExtra(TAG_RESULT_TYPE,VALUE_RESULT_SUCCESS);
            setResult(RESULT_OK,i);
            finish();
        }
    }
    public static boolean checkPermissions(Activity at, String []ps) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : ps) {
                if (at.checkSelfPermission(permission)!= PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    private boolean checkPermissionsReq(String []ps) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isReq=false;
            ArrayList<String> ar=new ArrayList<>();
            for (String permission : ps) {
                if (checkSelfPermission(permission)!= PackageManager.PERMISSION_GRANTED) {
                    isReq=true;
                    ar.add(permission);
                }
            }
            if(isReq){
                requestPermissions(ar.toArray(new String[ar.size()]),REQUEST_PERMISSION_CODE);
                return false;
            }else{
                return true;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE){
            boolean isAllReq=true;
            for(int grantResult : grantResults){
                if (grantResult!= PackageManager.PERMISSION_GRANTED) {
                    isAllReq=false;
                }
            }
            if(isAllReq){
                Intent i=new Intent();
                i.putExtra(TAG_RESULT_TYPE,VALUE_RESULT_SUCCESS);
                setResult(RESULT_OK,i);
                finish();
            }else{
                alert();
            }
        }
    }
    private void alert(){
        new AlertDialog.Builder(this)
                .setTitle(diloag_title)
                .setMessage(diloag_msg)
                .setCancelable(false)
                .setPositiveButton(diloag_btn_retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checkPermissionsReq(permissionA)){
                            finish();
                        }
                    }
                })
                .setNeutralButton(diloag_btn_exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent();
                        i.putExtra(TAG_RESULT_TYPE,VALUE_RESULT_REJECT);
                        setResult(RESULT_OK,i);
                        finish();
                    }
                })
                .show();
    }
    public void onError(String msg){
        Intent i=new Intent();
        i.putExtra(TAG_RESULT_TYPE,VALUE_RESULT_ERROR);
        i.putExtra(TAG_RESULT_MSG,msg);
        setResult(RESULT_OK,i);
        finish();
    }
    public static void setDiloag_title(String diloag_title) {
        PerMissionActivity.diloag_title = diloag_title;
    }
    public static void setDiloag_msg(String diloag_msg) {
        PerMissionActivity.diloag_msg = diloag_msg;
    }
    public static void setDiloag_btn_retry(String diloag_btn_retry) {
        PerMissionActivity.diloag_btn_retry = diloag_btn_retry;
    }
    public static void setDiloag_btn_exit(String diloag_btn_exit) {
        PerMissionActivity.diloag_btn_exit = diloag_btn_exit;
    }
    public static void setDiloag_title(Context ctx, int res) {
        PerMissionActivity.diloag_title =ctx.getResources().getString(res);
    }
    public static void setDiloag_msg(Context ctx,int res) {
        PerMissionActivity.diloag_msg = ctx.getResources().getString(res);
    }
    public static void setDiloag_btn_retry(Context ctx,int res) {
        PerMissionActivity.diloag_btn_retry = ctx.getResources().getString(res);
    }
    public static void setDiloag_btn_exit(Context ctx,int res) {
        PerMissionActivity.diloag_btn_exit = ctx.getResources().getString(res);
    }

}
