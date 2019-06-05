package com.byy.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;

/**
 * @author biyuyang
 */
public class SocketTest {

  /**
   * InetAddress类的基本演示
   *  1.没有明显的构造方法,需要通过工厂方法获取该类实例
   *      InetAddress.getLocalHost(): 返回本地主机
   *      InetAddress.getByName(String host): 给定主机名,确定主机的IP地址
   *      InetAddress.getAllByName(String host): 给定主机名,确定其IP地址所组成的数组
   *  2.
   */
  @Test
  public void test(){
    try {
      //1.getLocalHost
      InetAddress localHost1 = InetAddress.getLocalHost();
      System.out.println("本地的IP地址:"+localHost1.getHostAddress());
      System.out.println("本地主机名称:"+localHost1.getHostName());

      //2.getByName
      InetAddress localHost2 = InetAddress.getByName("localhost");
      System.out.println(localHost2);

      //3.getAllByName
      InetAddress[] baidus = InetAddress.getAllByName("www.baidu.com");
      for (InetAddress baidu : baidus){
        System.out.println(baidu);
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

  }

}
