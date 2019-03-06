/**
 * @author LiuK
 * @date 2018/12/30 12:13
 * @description:
 */
public interface interfaceDemo {
    void show();
    default void  show01(){
        System.out.println("this is default method");
    }
    static void show02(){
        System.out.println("this is static method ");
    }

}
 class interfaceimpl implements interfaceDemo{

    public  void show01(){
        show01();
        System.out.println("this method belong the son");
    }

     @Override
    public void show() {
        interfaceDemo.show02();
    }

     public static void main(String[] args) {
         interfaceimpl in=new interfaceimpl();
         in.show();
     }
}


