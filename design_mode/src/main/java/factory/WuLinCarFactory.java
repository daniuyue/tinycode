package factory;

public class WuLinCarFactory implements CarFactory{
    @Override
    public Car getCar() {
        return new WuLinCar();
    }
}
