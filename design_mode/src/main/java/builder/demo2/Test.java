package builder.demo2;

public class Test {

    public static void main(String[] args) {
        Worker worker =new Worker();
        worker.buildA("大号全家桶").buildB("二号全家桶");
        System.out.println(worker.getProduct());
    }
}
