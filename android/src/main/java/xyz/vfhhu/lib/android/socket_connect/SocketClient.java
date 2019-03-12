package xyz.vfhhu.lib.android.socket_connect;

//import android.os.Looper;

import android.os.Looper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;


/**
 * Created by leo on 2017/6/22.
 */

public class SocketClient implements Runnable {
    final private String TAG="SocketClient";
    private String address = "127.0.0.1";// 連線的ip
    private int port = 5168;// 連線的port
    private InputStream in;
    private InputStreamReader reader;
    private BufferedReader readerB;
    private Socket socket;
    private boolean isClose = false;
    private OutputStream outputstream;
    private OutputStreamWriter wt;
    private BufferedWriter out;
    private OnSocketClientListener listener;



    private String endChar= DataEndChar.N;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }
    public void setListener(OnSocketClientListener l){
        listener=l;
    }

    public void init() {
        new Thread(this, "SocketClient.init").start();
    }
    public void start() {
        new Thread(this, "SocketClient.init").start();
    }
    @Override
    public void run() {
        close();
        socket = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        try {
            socket.connect(isa, 10000);
            in = socket.getInputStream();
            reader = new InputStreamReader(in, "UTF-8");
            readerB = new BufferedReader(reader);
            outputstream=socket.getOutputStream();
            wt = new OutputStreamWriter(outputstream, "UTF-8");
            out = new BufferedWriter(wt);
        } catch (Exception e) {
            e.printStackTrace();
            close();
            return;
        }

        if(listener!=null)listener.onConnected(this);
        isClose = false;
        while (!isClose) {
            try {
                String src = receive();
                if (src == null) {
                    if (!isClose) {
                        close();
                    }
                    break;
                }
                onData(src);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public boolean send(final String s, final String end) {
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    send(s,end);
                }
            }).start();
            return true;
        }
        try {
            if(out!=null){
                out.write(s + end);
                out.flush();
                if(listener!=null)listener.onSend(s,this);
            }else{
            }
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean send(final byte[] s) {
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    send(s);
                }
            }).start();
            return true;
        }
        try {
            if(outputstream!=null){
                outputstream.write(s);
                outputstream.flush();
                if(listener!=null)listener.onSend(s,this);
            }else{
            }
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean send(final String s) {
        return send(s,endChar);
    }
    public boolean send_rn(final String s) {
        return send(s, DataEndChar.RN);
    }

    protected String receive() throws IOException {
        String src = null;
        if(endChar.equals(DataEndChar.N) || endChar.equals(DataEndChar.RN) || endChar.equals(DataEndChar.ZERO) ){
            src = readerB.readLine();
        }else {
            byte[] buffer = new byte[4096];
            int len = -1;
            len = in.read(buffer);
            if(len!=-1){
                if(listener!=null)listener.onData(buffer,this);
                src=new String(buffer,"UTF-8").trim();
            }
        }
        //src = readerB.readLine();
        return src;
    }
    private void onData(String str){
        if(listener!=null)listener.onData(str,this);
    }

    public void close() {
        isClose = true;
        if (socket != null) {
            try {
                socket.close();
                if(listener!=null)listener.onClosed(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        socket = null;
        reader = null;
        readerB = null;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEndChar() {
        return endChar;
    }

    public void setEndChar(String endChar) {
        this.endChar = endChar;
    }
}
