package kevin.example.com.httpwifi;

/**
 * Created by kevin on 2016/8/29.
 */
public class WebConfiguration {

    private int port; //端口
    private int maxParalels;// 最大监听


    public int getMaxParalels() {
        return maxParalels;
    }

    public void setMaxParalels(int maxParalels) {
        this.maxParalels = maxParalels;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }



}
