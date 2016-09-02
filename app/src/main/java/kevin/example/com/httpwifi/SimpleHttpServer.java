package kevin.example.com.httpwifi;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 2016/8/29.
 */
public class SimpleHttpServer {

    private final WebConfiguration webConfig;
    private final ExecutorService threadPool;
    private boolean isEnable;
    private ServerSocket socket;


    private Set<IResourceUriHandler> resourceHandlers;

    public SimpleHttpServer(WebConfiguration webConfig) {
        this.webConfig = webConfig;

        threadPool = Executors.newCachedThreadPool();
        resourceHandlers = new HashSet<IResourceUriHandler>();
    }

    //    启动server
    public void startAsync() {
        isEnable = true;

        new Thread(new Runnable() {

            @Override
            public void run() {
                doProcSync();

            }
        }).start();
    }


    public void stopAsync() throws IOException {

        if (!isEnable) {
            return;
        }
        isEnable = false;
        socket.close();
        socket = null;

    }

    private void doProcSync() {


        try {
            InetSocketAddress socketAddr = new InetSocketAddress(webConfig.getPort());
            socket = new ServerSocket();
            socket.bind(socketAddr);
            while (isEnable) {
                final Socket remotePeer = socket.accept();
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("tag", "a remote peer accepted" + remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            Log.e("spy", e.toString());
//            e.printStackTrace();
        }


    }


    public void registerResourceHandler(IResourceUriHandler handler) {

        resourceHandlers.add(handler);


    }

    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
            HttpContext httpContext = new HttpContext();
            httpContext.setUnderSocket(remotePeer);
//            remotePeer.getOutputStream().write("congratulation,connected successful".getBytes());
            InputStream nis = remotePeer.getInputStream();
            String headerLine = null;
            String resourceUrl  =headerLine= StreamToolkit.readLine(nis).split(" ")[1];
            Log.d("URL", resourceUrl);
            while ((headerLine = StreamToolkit.readLine(nis)) != null) {
                Log.d("TAg", "++++" + headerLine);
                if (headerLine.equals(" ")) {
                    break;
                }
                String[ ] pair = headerLine.split(": ");
                if (pair.length > 1)

                {
                    Log.d("pair","pair>1");
                    httpContext.addRequestHeader(pair[0], pair[1]);
                }

            for (IResourceUriHandler handler: resourceHandlers){
                if (!handler.accept(resourceUrl)){

                    continue;
                }
                handler.handle(resourceUrl,httpContext);

                }
            }

        } catch (IOException e) {
            Log.e("spy", e.toString());
        }finally {
            try {
                remotePeer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}


