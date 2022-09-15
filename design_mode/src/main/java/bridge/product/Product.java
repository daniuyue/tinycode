package bridge.product;

import bridge.brand.Brand;
//抽象的产品  建议使用组合（抽象类）  不要继承  不要接口
public abstract class Product {
protected Brand brand;

    public Product(Brand brand) {
        this.brand = brand;
    }

    public void info(){
        brand.getInfo();
    }

}
