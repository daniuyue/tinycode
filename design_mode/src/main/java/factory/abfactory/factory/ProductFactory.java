package factory.abfactory.factory;

import factory.abfactory.diannao.DianNaoProduct;
import factory.abfactory.phone.IPhoneProduct;

public interface ProductFactory {
    //手机抽象工厂
    IPhoneProduct getPhone();
    //电脑抽象工厂
    DianNaoProduct getDianNao();
}
