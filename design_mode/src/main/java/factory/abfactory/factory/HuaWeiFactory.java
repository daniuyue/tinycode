package factory.abfactory.factory;

import factory.abfactory.diannao.DianNaoProduct;
import factory.abfactory.diannao.HuaWeiDianNao;
import factory.abfactory.phone.HuaWeiPhone;
import factory.abfactory.phone.IPhoneProduct;

public class HuaWeiFactory implements ProductFactory{

    @Override
    public IPhoneProduct getPhone() {
        return new HuaWeiPhone();
    }

    @Override
    public DianNaoProduct getDianNao() {
        return new HuaWeiDianNao();
    }
}
