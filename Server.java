import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;
import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException{
        // Creating a static server instance running at port 5000
        // The queue list is 10
        HttpServer server = HttpServer.create(new InetSocketAddress(5000),10);
        serverContextHandler(server);
        startServer(server);
        
    }

    public static void startServer(HttpServer server){
        //null the default executor will be used
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at port 5000");
    }

    public static void serverContextHandler(HttpServer server){
        server.createContext("/login",new SignInHandler());
        server.createContext("/update_profile",new UpdateProfileHandler());}
    
}


