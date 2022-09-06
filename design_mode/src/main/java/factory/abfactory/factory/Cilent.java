package factory.abfactory.factory;

import factory.abfactory.diannao.DianNaoProduct;
import factory.abfactory.phone.IPhoneProduct;

public class Cilent {
    public static void main(String[] args) {
        //小米工厂
        ProductFactory factory = new XiaoMiFactory();
        IPhoneProduct phone = factory.getPhone();
        phone.open();
        DianNaoProduct dianNao = factory.getDianNao();
        dianNao.open();

    }
}
