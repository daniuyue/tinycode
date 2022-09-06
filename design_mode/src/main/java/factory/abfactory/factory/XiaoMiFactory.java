package factory.abfactory.factory;

import factory.abfactory.diannao.DianNaoProduct;
import factory.abfactory.diannao.XiaoMiDianNao;
import factory.abfactory.phone.IPhoneProduct;
import factory.abfactory.phone.XiaoMiPhone;

public class XiaoMiFactory implements ProductFactory{

    @Override
    public IPhoneProduct getPhone() {
        return new XiaoMiPhone();
    }

    @Override
    public DianNaoProduct getDianNao() {
        return new XiaoMiDianNao();
    }
}


