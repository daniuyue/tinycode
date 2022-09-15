package bridge;

import bridge.brand.HuaWei;
import bridge.product.PingBan;
import bridge.product.Product;

public class Entrance {
    public static void main(String[] args) {
        Product product = new PingBan(new HuaWei());
        product.info();
    }
}
