package factory.abfactory.phone;

public class HuaWeiPhone implements IPhoneProduct{
    @Override
    public void open() {
        System.out.println("华为手机开机");
    }
}
