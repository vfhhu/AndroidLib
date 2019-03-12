package xyz.vfhhu.lib.android.socket_connect;

import java.nio.channels.SocketChannel;

/**
 * Created by leo on 2017/6/23.
 */

public interface OnSocketServerListener {
    void onStart();
    void onStop();
    void onSend(String src, SocketChannel client);
    void onSend(byte[] src, SocketChannel client);
    void onData(String src, SocketChannel client, byte[] data);
    void onConnected(SocketChannel client);
    void onClosed(SocketChannel client);
    void onError(String err, SocketChannel client);

}
