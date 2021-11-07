package server;

import annotations.Controller;
import engine.Engine;
import framework.MiniWebFramework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) throws IOException {

//        for (Class cl: Engine.getClassesFromPackage("")) {
//            System.out.println(cl.getName());
//        }

        List<Class> allClasses = Engine.getClassesFromPackage("");
        for (Class cl: allClasses) {
            MiniWebFramework.scanControllerMethods(cl);
        }

        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server is running at http://localhost:"+TCP_PORT);
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
