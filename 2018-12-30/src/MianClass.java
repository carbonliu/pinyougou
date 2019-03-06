/**
 * @author LiuK
 * @date 2018/12/30 17:08
 * @description:
 */
public class MianClass {
    public static void main(String[] args) {
        Fruit fruit = FruitFactory.getFruit("Apple");
        fruit.get();
    }
}
