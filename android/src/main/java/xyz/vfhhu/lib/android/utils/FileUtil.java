package xyz.vfhhu.lib.android.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by leo3x on 2018/8/13.
 */

public class FileUtil {
    Context ctx;
    File dir;
    private int delete_cnt=0;
    public FileUtil(Context ctx){
        this.ctx=ctx;
        dir=ctx.getExternalFilesDir(null);
        if(!dir.exists())dir.mkdir();
    }
    public FileUtil(Context ctx,String dirname){
        this.ctx=ctx;
        dir=new File(ctx.getExternalFilesDir(null), dirname+File.separator);
        if(!dir.exists())dir.mkdirs();
    }

    public String getDirPath(){
        return dir.getAbsolutePath();
    }
    public boolean saveInput(String fname,InputStream input){
        File f=new File(dir,fname);
        return saveInput(f, input);
    }
    public boolean saveInput(File f,InputStream input){
        try {
            OutputStream output = new FileOutputStream(f.getAbsolutePath());
            byte[] buffer=new byte[1024];
            int n;
            while((n=input.read(buffer))>-1){
                output.write(buffer,0,n);
            }
//
//            int byteValue;
//            while ((byteValue = input.read()) != -1) {
//                output.write(byteValue);
//            }
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public OutputStream getOutputStream(String fname){
        File f=new File(dir,fname);
        return getOutputStream(f);
    }
    public OutputStream getOutputStream(File f){
        try {
            return  new FileOutputStream(f.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean saveBytes(String fname,byte[] data){
        File f=new File(dir,fname);
        return saveBytes(f,data);
    }
    public boolean saveBytes(File f,byte[] data){
        try {
            if(f.exists())delete(f);
            f.createNewFile();
            FileOutputStream fop = new FileOutputStream(f);
            fop.write(data);
            fop.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean appendBytes(String fname,byte[] data){
        File f=new File(dir,fname);
        return appendBytes(f,data);
    }
    public boolean appendBytes(File f,byte[] data){
        try {
            if(!f.exists())f.createNewFile();
            FileOutputStream fop = new FileOutputStream(f,true);
            fop.write(data);
            fop.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public byte[] readBytes(String fname){
        File f=new File(dir,fname);
        return readBytes(f);
    }
    public byte[] readBytes(File f){
        byte[] ret=new byte[0];
        if(f==null || !f.exists())return ret;
        try {
            FileInputStream fin=new FileInputStream(f);
            byte[] buffer = new byte[4096];
            int length = -1;
            // 從來源檔案讀取資料至緩衝區
            while((length = fin.read(buffer)) != -1) {
                byte[] tmp=new byte[ret.length+length];
                System.arraycopy(ret,0,tmp,0,ret.length);
                System.arraycopy(buffer,0,tmp,ret.length,length);
                ret=tmp;
            }
            fin.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public String readString(String fname){
        File f=new File(dir,fname);
        return readString(f);
    }
    public String readString(File f){
        String ret="";
        if(f==null || !f.exists())return ret;
        try {
            FileInputStream fin=new FileInputStream(f);
            ret = convertStreamToString(fin);
            fin.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean delete(String fname){
        File f=new File(dir,fname);
        return delete(f);
    }
    public boolean delete(File f){
        boolean isDelete=false;
        if(f!=null && f.exists()){
            File nf=new File(f.getPath()+"_"+System.currentTimeMillis()+"_"+delete_cnt);
            f.renameTo(nf);
            nf.delete();
            delete_cnt++;
            isDelete=true;
        }
        return isDelete;
    }
    public void clearAllFile(){
        String fnames[]=list(null);
        for(String fname:fnames){
            delete(fname);
        }
    }



    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
    public File[] listFiles(FilenameFilter ff){
        if(ff!=null)return dir.listFiles(ff);
        return dir.listFiles();
    }
    public String[] list(FilenameFilter ff){
        if(ff!=null)return dir.list(ff);
        return dir.list();
    }
    public File getFile(String fname){
        return new File(dir,fname);
    }
    public String getPathName(String fname){
        return new File(dir,fname).getAbsolutePath();
    }
    public static String basename(String path){
        if(path==null)return "";
        if(path.indexOf("/")>=0)  return path.substring(path.lastIndexOf("/")+1);
        return path;
    }
    public static String[] basename_explode(String path){
        String base=basename(path);
        if(base.indexOf(".")>0)return base.split("\\.");
        return new String[]{path};
    }
    public static String fname_base(String path){
        return basename_explode(path)[0];
    }
    public static String fname_extension(String path){
        String[] arr=basename_explode(path);
        return arr[arr.length-1];
    }

}
