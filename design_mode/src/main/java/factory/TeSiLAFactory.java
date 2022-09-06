package factory;

public class TeSiLAFactory implements CarFactory {
    @Override
    public Car getCar() {
       return new WuLinCar();
    }
}
