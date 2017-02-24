package twice.pbdtest.AddFriend;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by MaximaXL on 2/24/2017.
 */

public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private final String TAG = "BluetoothSendRecv";
    private final UUID MY_UUID = UUID.fromString("f07e64de-fa97-11e6-bc64-92361f002671");
    private OnConnectedListener listener;

    public AcceptThread(BluetoothAdapter mBluetoothAdapter) {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("GoblinAttack", MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                Log.v(TAG,"Trying to Accept");
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                manageMyConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "Socket's close() method failed", e);
                }
                break;
            }
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket socket) {
        this.listener.onConnected(socket);
    }

    public void setOnConnectedListener(OnConnectedListener listener){
        this.listener = listener;
    }


    public interface OnConnectedListener {
        public void onConnected(BluetoothSocket bs);
    }


    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}