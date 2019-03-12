package xyz.vfhhu.lib.android.socket_connect;

import java.net.InetAddress;

/**
 * Created by leo3x on 2018/5/7.
 */

public abstract class OnUdpClientListener {
    public void onSend(String address, int port, String src, UdpClient client){};
    public void onSend(String address, int port, byte[] src, UdpClient client){};
    public abstract void onData(String src, UdpClient client, InetAddress recever_address, int recever_port);
    public void onData(byte[] src, UdpClient client, InetAddress recever_address, int recever_port){};
    public void onClosed(UdpClient udpClient){};
    public void onOpen(UdpClient udpClient){};
    public void onError(String message, UdpClient udpClient){};
}
