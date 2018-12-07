package ru.itlab.ufob.SpecialClasses;


import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Net {
    public void Server(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket socket = new ServerSocket(1010);
                    while (true) {
                        Socket client = socket.accept();
                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        String message = (String) ois.readObject();
                        Gdx.app.log("get", message);
                        if (message.equals("Damir")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Client(){
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 1010);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Damir");
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}