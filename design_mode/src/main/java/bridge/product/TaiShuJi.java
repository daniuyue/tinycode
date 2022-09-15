package bridge.product;

import bridge.brand.Brand;

public class TaiShuJi extends Product {

    public TaiShuJi(Brand brand) {
        super(brand);
    }

    @Override
    public void info() {
        super.info();
        System.out.println("台式机");
    }
}
