import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
public class UpdateProfileHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange){
        if("POST".equals(exchange.getRequestMethod())){
            
        }
    }
}
