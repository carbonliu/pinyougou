/**
 * @author LiuK
 * @date 2018/12/30 17:06
 * @description:
 */
public class FruitFactory {
    public static Fruit getFruit(String type){
        try {
            //return (Fruit)clazz.newInstance();
            Class clazz = Class.forName(type);
            return (Fruit) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
