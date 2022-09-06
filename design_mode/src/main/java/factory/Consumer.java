package factory;

public class Consumer {
    public static void main(String[] args) {
        Car car = new WuLinCarFactory().getCar();
        Car car2 = new TeSiLAFactory().getCar();
    }
}
