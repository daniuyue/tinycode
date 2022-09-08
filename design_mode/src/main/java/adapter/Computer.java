package adapter;

// 电脑类
public class Computer {
    private Computer computer;

    //电脑连接适配器
    public void getWifi(NetUsb netUsb) {
        //上网的具体实现，找一个转接头
        netUsb.handleRequest();
    }
}
