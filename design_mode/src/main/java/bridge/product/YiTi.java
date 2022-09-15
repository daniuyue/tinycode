package bridge.product;

import bridge.brand.Brand;

public class YiTi extends Product {

    public YiTi(Brand brand) {
        super(brand);
    }
    @Override
    public void info() {
        super.info();
        System.out.println("一体机");
    }

}
