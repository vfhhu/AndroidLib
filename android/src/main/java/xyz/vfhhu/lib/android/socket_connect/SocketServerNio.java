package xyz.vfhhu.lib.android.socket_connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leo on 2017/6/23.
 */

public class SocketServerNio {
    private static final String TAG = "SocketServerNio";
    private int port = 5168;// 連線的port
    private boolean isClose=true;
    private OnSocketServerListener listener;
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private InetSocketAddress listenAddress;
    private ConcurrentHashMap<SocketChannel,List> dataMapper;
    private Thread readT;
    private String endChar= DataEndChar.N;
    public SocketServerNio( int port) {
        this.port = port;
        listenAddress = new InetSocketAddress( port);
        dataMapper = new ConcurrentHashMap<SocketChannel,List>();
    }
    public SocketServerNio( InetSocketAddress is) {
        this.port = is.getPort();
        listenAddress = is;
        dataMapper = new ConcurrentHashMap<SocketChannel,List>();
    }
    public void setListener(OnSocketServerListener l){
        listener=l;
    }

    public void start() {
        if(readT!=null)stop();
        readT=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    selector = Selector.open();
                    serverChannel = ServerSocketChannel.open();
                    serverChannel.configureBlocking(false);
                    serverChannel.socket().bind(listenAddress);
                    serverChannel.register(SocketServerNio.this.selector, SelectionKey.OP_ACCEPT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isClose = false;
                if(listener!=null)listener.onStart();
                while (!isClose) {
                    try {
                        SocketServerNio.this.selector.select();
                        Iterator keys = SocketServerNio.this.selector.selectedKeys().iterator();
                        while (keys.hasNext()) {
                            SelectionKey key = (SelectionKey) keys.next();
                            keys.remove();
                            if (!key.isValid()) {
                                continue;
                            }
                            try{
                                if (key.isAcceptable()) {
                                    SocketServerNio.this.accept(key);
                                }
                                else if (key.isReadable()) {
                                    SocketServerNio.this.read(key);
                                }
                            }catch(IOException ex){
                                ex.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(listener!=null)listener.onStop();
            }
        });
        readT.start();
    }
    public void stop() {
        isClose = true;
        if(readT!=null )readT.interrupt();
        readT=null;
        try {
            serverChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        dataMapper.put(channel, new ArrayList());
        channel.register(this.selector, SelectionKey.OP_READ);
        if(listener!=null)listener.onConnected(channel);
    }
    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        int numRead = -1;
        numRead = channel.read(buffer);
        if (numRead == -1) {
            this.dataMapper.remove(channel);
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            channel.close();
            key.cancel();
            if(listener!=null)listener.onClosed(channel);
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        if(listener!=null)listener.onData(new String(data),channel,data);
    }
    private void readLine(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        int numRead = -1;
        numRead = channel.read(buffer);
        if (numRead == -1) {
            this.dataMapper.remove(channel);
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            channel.close();
            key.cancel();
            if(listener!=null)listener.onClosed(channel);
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        Byte[] dataB = new Byte[numRead];
        int i=0;
        for(byte b: data)dataB[i++] = b;
        ArrayList<Byte> arr=(ArrayList<Byte>)this.dataMapper.get(channel);
        arr.addAll(Arrays.asList(dataB));
        try{
            int line_rn=indexOf(arr,"\r\n".getBytes());
            int count=0;
            while(line_rn>0){
                count++;
                if(count>20)break;
                byte[] retData=sublistNL("\r\n",arr);
                if(listener!=null)listener.onData(new String(retData),channel,retData);
                line_rn=indexOf(arr,"\r\n".getBytes());
            }
            int line_n=indexOf(arr,"\n".getBytes());
            while(line_n>0){
                count++;
                if(count>20)break;
                byte[] retData=sublistNL("\n",arr);
                if(listener!=null)listener.onData(new String(retData),channel,retData);
                line_n=indexOf(arr,"\n".getBytes());
            }
        }catch(Exception e){e.printStackTrace();}

    }
    private int indexOf(ArrayList<Byte> arr, byte[] b){
        int i=0;
        int cnt=0;
        for(Byte d: arr){
            i++;
            if(d==b[cnt]){
                cnt++;
                if(cnt>=b.length)return (i-cnt);
            }else{cnt=0;}
        }
        return -1;
    }
    private byte[] sublistNL(String newLine, ArrayList<Byte> arr){
        int newLineLength=newLine.getBytes().length;
        int newLineIndex=indexOf(arr,newLine.getBytes());
        int lastIndex=newLineIndex+newLineLength;
        List<Byte> subList = Collections.synchronizedList(new ArrayList<Byte>(arr.subList(0,lastIndex)));
        arr.removeAll(subList);
        Byte[] sub=subList.toArray(new Byte[subList.size()]);
        byte retB[]=new byte[sub.length-newLineLength];
        for(int i=0;i<retB.length;i++){
            retB[i]=sub[i];
        }
        return retB;
    }
    public void broadcast(String msg) throws IOException {
        ByteBuffer msgBuf= ByteBuffer.wrap(msg.getBytes());
        for(SelectionKey key : selector.keys()) {
            if(key.isValid() && key.channel() instanceof SocketChannel) {
                SocketChannel sch=(SocketChannel) key.channel();
                sch.write(msgBuf);
                msgBuf.rewind();
                if(listener!=null)listener.onSend(msg,sch);
            }
        }
    }
    public boolean send(SocketChannel channel, String msg) throws IOException {
        ByteBuffer msgBuf= ByteBuffer.wrap(msg.getBytes());
        try {
            channel.write(msgBuf);
            if(listener!=null)listener.onSend(msg,channel);
            return true;
        } catch(ClosedChannelException e) {
            e.printStackTrace();
            if(listener!=null)listener.onClosed(channel);
            channel.close();
        }
        return false;
    }
    public boolean send(SocketChannel channel, byte[] msg) throws IOException {
        ByteBuffer msgBuf= ByteBuffer.wrap(msg);
        try {
            channel.write(msgBuf);
            if(listener!=null)listener.onSend(msg,channel);
            return true;
        } catch(ClosedChannelException e) {
            e.printStackTrace();
            if(listener!=null)listener.onClosed(channel);
            channel.close();
        }
        return false;
    }
    public String getEndChar() {
        return endChar;
    }

    public void setEndChar(String endChar) {
        this.endChar = endChar;
    }
}
