package com.byy.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author biyuyang
 *
 * UDP 用户数据报协议
 *  无连接,通信协议
 *  消耗资源小,通信效率高
 *  常用于音频,视频和普通数据的传输
 *
 * DatagramPacket 数据报包
 *  相当于集装箱,用于封装UDP通信中发送或者接收的数据
 *  构造方法
 *    DatagramPacket(byte[] byte,int length) --> 接收端
 *    DatagramPacket(byte[] byte,int length,InetAddress inetAddress,int port) --> 发送端(需要接收端的ip和port)
 *  常用方法
 *    InetAddress getAddress(): 返回机器的IP地址
 *    byte[] getData(): 返回数据缓冲区
 *    int getLength(): 返回将要发送或接收到的数据的长度
 *    int getPort(): 返回某台远程主机的端口号
 *
 * DatagramSocket 数据报套接字
 *  相当于码头,用于发送和接收DatagramPacket数据包
 *  构造方法
 *    DatagramSocket() --> 只可以是发送端(系统会分配一个没有被其他网络程序所使用的端口号)
 *    DatagramSocket(int port) --> 发送端和接收端都可以(接收端必须指定端口号)
 *  常用方法
 *    void receive(DatagramPacket p): 从此套接字接收数据报包
 *    void send(DatagramPacket p): 从此套接字发送数据报包
 *    void close(): 关闭此数据报套接字
 *
 *
 *
 *
 */
class DatagramTest {

  //接收后,发送反馈信息给发送端
  @Test
  public void testReceiveThenSend(){
    try {
      DatagramSocket socket = new DatagramSocket(8080);

      byte[] buf = new byte[1024];
      DatagramPacket packet = new DatagramPacket(buf,buf.length);

      socket.receive(packet);

      byte[] data = packet.getData();
      InetAddress address = packet.getAddress();
      int port = packet.getPort();
      int length = packet.getLength();
      String ip = address.getHostAddress();
      System.out.println("data:" + Arrays.toString(data) + "\nlen:" + length + "\nport:" + port + "\nip:" + ip);
      String str = new String(data, 0, length);
      System.out.println("数据: " + str);

      String ss = "接收成功";
      DatagramPacket packet1 = new DatagramPacket(ss.getBytes(),ss.length(),address,port);
      socket.send(packet1);

      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSendThenReceive(){
    try {
      DatagramSocket socket = new DatagramSocket();
      String str = "我是发送方!";
      DatagramPacket packet = new DatagramPacket(str.getBytes(),str.getBytes().length,InetAddress.getByName("localhost"),8080);
      socket.send(packet);


      // 4. 创建一个集装箱对象, 接收返回的数据, 并解析查看
      byte[] buf2 = new byte[1024];
      DatagramPacket dp2 = new DatagramPacket(buf2, buf2.length);
      socket.receive(dp2);
      String data = new String(dp2.getData(), 0, dp2.getLength());
      System.out.println("接收到的数据为: " + data);
      socket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  //接收
  @Test
  private void testReceive(){
    try {
      //1.创建一个'接收端'的码头对象(8080码头)
      DatagramSocket socket = new DatagramSocket(8080);
      //2.创建一个'数据包'的集装箱对象,用于接收数据
      byte[] buf = new byte[1024];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      //3.码头在指定端口等待接收集装箱数据,注意:接收数据时,如果没有数据则会阻塞
      System.out.println("接收端等待接收数据:");
      socket.receive(packet);

      //4.从集装箱中取出数据,包括数据内容,长度,发送方IP和端口号
      //4.1 数据内容
      byte[] data = packet.getData();
      //4.2 数据长度
      int length = packet.getLength();
      //4.3 端口号
      int port = packet.getPort();
      //4.4 IP对象
      InetAddress address = packet.getAddress();
      //4.5 IP地址
      String ip = address.getHostAddress();		// IP地址
      System.out.println("data:" + Arrays.toString(data) + "\nlen:" + length + "\nport:" + port + "\nip:" + ip);
      String str = new String(data, 0, length);
      System.out.println("数据: " + str);
      //5.释放资源
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //发送
  @Test
  private void testSend(){
    try {
      //1.创建一个'发送端'的码头对象
      DatagramSocket socket = new DatagramSocket();
      //2.创建一个'数据包'的集装箱对象,用于发送数据
      String str = "hello world!";
      DatagramPacket packet = new DatagramPacket(str.getBytes(),str.length(), InetAddress.getByName("localhost"),8080);
      //3.向指定的码头发送集装箱数据
      System.out.println("发送数据:");
      socket.send(packet);
      //4.释放资源
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
