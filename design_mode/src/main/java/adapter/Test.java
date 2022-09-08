package adapter;

public class Test {
    public static void main(String[] args) {
        Computer computer = new Computer();
        Reticle reticle = new Reticle();
        Adapter adapter = new Adapter(reticle);
        computer.getWifi(adapter);
    }
}
