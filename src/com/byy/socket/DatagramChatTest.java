package com.byy.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @author biyuyang
 */
public class DatagramChatTest {

  public static void main(String[] args) throws SocketException {
    //1.创建一个(接收,发送)的码头
    DatagramSocket socket = new DatagramSocket(8888);
    //2.创建一个发送任务
    String ip = "127.0.0.1";
    int port = 8888;
    SendTask sendTask = new SendTask(socket,ip,port);
    Thread sendThread = new Thread(sendTask,"发送任务");
    sendThread.start();
    //3.创建一个接收任务
    ReceiveTask receiveTask = new ReceiveTask(socket);
    Thread receiveThread = new Thread(receiveTask,"接收任务");
    receiveThread.start();
  }
}

class ReceiveTask implements Runnable{

  private DatagramSocket socket;

  public ReceiveTask(DatagramSocket socket){
    this.socket = socket;
  }
  @Override
  public void run() {
    //循环接收
    byte[] buf = new byte[1024];
    while(true){
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try {
        socket.receive(packet);

        byte[] data = packet.getData();
        int length = packet.getLength();
        String str = new String(data,0,length);
        String ip = packet.getAddress().getHostAddress();
        System.out.println(ip+" : "+str);

      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}

class SendTask implements Runnable{

  private DatagramSocket socket;
  private String ip;
  private int port;

  public SendTask(DatagramSocket socket, String ip, int port) {
    this.socket = socket;
    this.ip = ip;
    this.port = port;
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(System.in);
    String line = null;
    while ((line = sc.nextLine()) != null){
      //约定结束条件
      if("exit".equals(line)){
        break;
      }
      try {
        InetAddress address = InetAddress.getByName(ip);
        byte[] buf = line.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    sc.close();
  }
}
