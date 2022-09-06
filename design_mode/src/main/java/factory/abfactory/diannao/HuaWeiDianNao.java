package factory.abfactory.diannao;

public class HuaWeiDianNao implements DianNaoProduct{
    @Override
    public void open() {
        System.out.println("华为电脑开机");
    }
}
