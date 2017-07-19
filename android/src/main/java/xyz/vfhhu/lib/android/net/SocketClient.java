package xyz.vfhhu.lib.android.net;

import android.os.Looper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private InputStreamReader reader;
    private BufferedReader readerB;
    private Socket socket;
    private boolean isClose = false;
    private OutputStreamWriter wt;
    private BufferedWriter out;
    private OnSocketClientListener listener;

    public SocketClient( String address, int port) {
        this.address = address;
        this.port = port;
    }
    public void setListener(OnSocketClientListener l){
        listener=l;
    }

    public void init() {
        close();
        socket = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        try {
            socket.connect(isa, 10000);
            InputStream in = socket.getInputStream();
            reader = new InputStreamReader(in, "UTF-8");
            readerB = new BufferedReader(reader);
            wt = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            out = new BufferedWriter(wt);
            new Thread(this, "SocketClient.init").start();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }
    @Override
    public void run() {
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
    public boolean send(final String s) {
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
            if(out!=null){
                out.write(s + "\n");
                out.flush();
                if(listener!=null)listener.onSend(s,this);
            }else{
            }
            return true;
        } catch (SocketException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean send_rn(final String s) {
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    send_rn(s);

                }
            }).start();
            return true;
        }
        try {
            if(out!=null){
                out.write(s + "\r\n");
                out.flush();
                if(listener!=null)listener.onSend(s,this);
            }else{
            }
            return true;
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected String receive() throws IOException {
        String src = null;
        src = readerB.readLine();
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
}
