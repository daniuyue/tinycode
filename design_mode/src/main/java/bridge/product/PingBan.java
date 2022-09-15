package bridge.product;

import bridge.brand.Brand;
// 产品
public class PingBan extends Product {

    public PingBan(Brand brand) {
        super(brand);
    }

    @Override
    public void info() {
        super.info();
        System.out.println("平板");
    }
}
