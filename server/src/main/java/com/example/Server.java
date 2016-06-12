package com.example;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Created by Gary on 16/5/28.
 */
public class Server implements Runnable{
    private Thread thread;
    private ServerSocket servSock;

    private JFrame frame;
    private JTextArea textArea;


    public Server(){

        frame = new JFrame();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setBackground(SystemColor.textHighlight);

        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.setVisible(true);

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            textArea.setText("IP of my system is :"+IP.getHostAddress() + "\n" + "Waitting to connect......");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                //System.out.println("I am here~~");
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();

                System.out.println("Connected");
                textArea.setText("Connected!!");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                length = in.read(b);
                String s = new String(b);
                System.out.println("[Server Said]" + s);
                textArea.setText(s);

            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
