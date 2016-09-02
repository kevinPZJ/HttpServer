package kevin.example.com.httpwifi;

import java.io.IOException;

/**
 * Created by kevin on 2016/8/30.
 */
public interface IResourceUriHandler {

    boolean accept(String uri );

    void handle(String uri,HttpContext httpContext) throws IOException;
}
