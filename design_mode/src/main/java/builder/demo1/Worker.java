package builder.demo1;
//具体建造者
public class Worker extends Builder{

    private final Product product;

    public Worker() {
        this.product = new Product();
    }

    @Override
    void buildA() {
        product.setBuildA("地基");
        System.out.println("地基");
    }


    @Override
    void buildB() {
        product.setBuildB("钢筋");
        System.out.println("钢筋");
    }

    @Override
    void buildC() {
        product.setBuildC("墙");
        System.out.println("墙");
    }

    @Override
    void buildD() {
        product.setBuildD("粉刷");
        System.out.println("粉刷");
    }

    @Override
    Product getProduct() {
        return product;
    }
}
