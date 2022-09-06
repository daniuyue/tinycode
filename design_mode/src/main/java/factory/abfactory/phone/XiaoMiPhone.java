package factory.abfactory.phone;

public class XiaoMiPhone  implements IPhoneProduct{
    @Override
    public void open() {
        System.out.println("小米手机开机");
    }
}
