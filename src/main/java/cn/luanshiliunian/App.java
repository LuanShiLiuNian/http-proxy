package cn.luanshiliunian;

import cn.luanshiliunian.core.GlobalContext;
import cn.luanshiliunian.net.TcpServer;

public class App {
    public static void main(String[] args) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println();
//                    System.out.println(GlobalContext.SESSION.size());
//                    System.out.println();
//                }
//            }
//        }).start();

        new TcpServer().starter();
    }
}
