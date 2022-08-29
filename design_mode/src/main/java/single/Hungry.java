package single;

/**
 * @author daniuyue
 */
public class Hungry {
    //饿汉式单例模式有可能会造成空降浪费
    //第一步，私有化构造函数
    private Hungry(){

    }
    //第二部，直接创建对象
    private static final Hungry HUNGRY = new Hungry();
    //第三步，提供获取对象方法
    public static Hungry getInstance() {
        return HUNGRY;
    }

}
