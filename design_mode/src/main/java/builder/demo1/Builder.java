package builder.demo1;
//抽象建造者
public abstract class Builder {

    //铺地基
    abstract void buildA();
    //铺水泥
    abstract void buildB();
    //建墙
    abstract void buildC();
    //粉刷
    abstract void buildD();

    // 得到房子
    abstract Product getProduct();
}
