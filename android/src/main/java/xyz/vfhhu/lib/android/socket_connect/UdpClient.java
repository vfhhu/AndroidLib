package xyz.vfhhu.lib.android.socket_connect;

import android.os.Looper;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


/**
 * Created by leo3x on 2018/5/7.
 */

public class UdpClient {
    final private String TAG="UdpClient";
    private UdpClient client ;// 連線的port
    private String server_address = "127.0.0.1";// 連線的ip
    private int server_port = 5168;// 連線的port
    private int local_port = 5168;// 連線的port
    private OnUdpClientListener listener;
    DatagramSocket socket = null;
    private boolean isClose = false;
    private static Thread udpT;


    public UdpClient( String address, int port) {
        server_address = address;
        local_port=server_port = port;
        client=this;
    }
    public UdpClient( String address, int server_port,int local_port) {
        server_address = address;
        this.server_port = server_port;
        this.local_port = local_port;
        client=this;
    }
    public void setListener(OnUdpClientListener l){
        listener=l;
    }
    public void start() {
        release();
        try {
            socket = new DatagramSocket(local_port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if(listener!=null)listener.onOpen(this);
        udpT=new Thread(new Runnable() {
            @Override
            public void run() {
                isClose=false;
                while (!isClose){
                    byte[] recbuf = new byte[1024];
                    DatagramPacket recpacket = new DatagramPacket(recbuf,recbuf.length);
                    if(socket!=null){
                        try {
                            socket.receive(recpacket);
                            if(listener!=null){
                                listener.onData(recpacket.getData(),client,recpacket.getAddress(),recpacket.getPort());
                                listener.onData(new String(recpacket.getData()).trim() ,client,recpacket.getAddress(),recpacket.getPort());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if(socket==null){
                                Thread.sleep(1000);
                                close();
                            }else Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        close();
                    }
                }
            }
        }, "UdpClient.init");
        udpT.start();
    }
    public boolean send(final String s) {
        boolean bl=send(s.getBytes());
        if(bl && listener!=null)listener.onSend(server_address,server_port,s,this);
        return bl;
    }
    public boolean send(final byte buf[]) {
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    send(buf);

                }
            }).start();
            return true;
        }
        try {
            DatagramPacket packet;
            packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(server_address), server_port);
            if(socket!=null){
                socket.send(packet);
                if(listener!=null)listener.onSend(server_address,server_port,buf,this);
                return true;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            listener.onError(e.getMessage(),this);
        }
        return false;
    }
    public boolean send(final String address,final int port,final String s) {
        boolean bl=send(address,port,s.getBytes());
        if(bl && listener!=null)listener.onSend(address,port,s,this);
        return bl;
    }
    public boolean send(final String address,final int port,final byte buf[]) {
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    send(address,port,buf);

                }
            }).start();
            return true;
        }
        try {
            DatagramPacket packet;
            packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(address), port);
            if(socket!=null){
                socket.send(packet);
                if(listener!=null)listener.onSend(address,port,buf,this);
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            listener.onError(e.getMessage(),this);
        }
        return false;
    }
    public void stop() {
        close();
    }
    public void close() {
        release();
        if(listener!=null)listener.onClosed(this);
    }
    private void release(){
        isClose = true;
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if(udpT!=null)udpT.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket = null;
    }
}
