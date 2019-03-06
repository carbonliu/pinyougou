/**
 * @author LiuK
 * @date 2018/12/30 18:35
 * @description:
 */
public class TimeFormatStringUtil {
    public static  String convertTimeString(long second){
        String str="还剩下";
        long day = second / 86400;
        long temp=second%86400;
        long hour=temp/3600;
        temp=temp%3600;
        long mint=temp/60;
        temp=temp%60;
        str=str+day+"天"+hour+"小时"+mint+"分钟"+temp+"秒结束";
        return str;
    }

    public static void main(String[] args) {
        System.out.println(TimeFormatStringUtil.convertTimeString(166437));
        Object o=new Object();
        o=null;
        System.out.println(o);
    }
}
