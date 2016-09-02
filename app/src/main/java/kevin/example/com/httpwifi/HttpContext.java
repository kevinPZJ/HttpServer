package kevin.example.com.httpwifi;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by kevin on 2016/8/30.
 */
public class HttpContext {
    private Socket  underlySocket;

    private final HashMap<String,String> requestHeaders;


    public HttpContext(){
        requestHeaders=new HashMap<String, String>();

    }

    public void setUnderSocket(Socket socket) {
        this.underlySocket=socket;
    }

    public Socket getUnderlySocket(){
        return  this.underlySocket;
    }

    public void addRequestHeader(String headerName, String headerValue) {

        requestHeaders.put(headerName,headerValue);

    }
    public  String getRequestHeaderValue(String headerName){
      return   requestHeaders.get(headerName);

    }
}

