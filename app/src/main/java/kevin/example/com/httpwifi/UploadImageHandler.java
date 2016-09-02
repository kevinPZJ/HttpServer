package kevin.example.com.httpwifi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by kevin on 2016/8/31.
 */
public class UploadImageHandler implements IResourceUriHandler {

    private  String acceptPreFix= "/upload_image/";

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceptPreFix);
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {

        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintWriter writer=new PrintWriter(nos);
        writer.println("HTTP/1.1 200 OK  ");
        writer.println();
        writer.println("from resource in image handler");
        writer.flush();
//        writer.close();

    }
}
