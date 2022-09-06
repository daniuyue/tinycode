package builder.demo2;

public class Worker extends Builder{

    private Product product;

    public Worker() {
        this.product = new Product();
    }

    @Override
    Builder buildA(String msg) {
        product.setNameA(msg);
        return this;
    }

    @Override
    Builder buildB(String msg) {
        product.setNameB(msg);
        return this;
    }

    @Override
    Builder buildC(String msg) {
        product.setNameC(msg);
        return this;
    }

    @Override
    Builder buildD(String msg) {
        product.setNameD(msg);
        return this;
    }

    @Override
    Product getProduct() {
        return product;
    }
}
