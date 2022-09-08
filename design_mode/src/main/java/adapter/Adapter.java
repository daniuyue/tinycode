package adapter;

// 适配器
// 继承 类适配器
// 2. 组合  对象适配器（常用）
public class Adapter implements NetUsb {
private Reticle reticle;

    public Adapter(Reticle reticle) {
        this.reticle = reticle;
    }


    @Override
    public void handleRequest() {
        reticle.request();
    }
}
