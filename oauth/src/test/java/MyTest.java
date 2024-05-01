/**
 * @Author 杨森森
 * @Data 2024/4/10  20:42
 */
public class MyTest {
    public static void  oauth(String... ss){
        String[] mm = ss;
        System.out.println(mm[0]);
        System.out.println(ss[0]);
    }
    public static void main(String[] args) {
        String s = "1,2,3";
        String[] split = s.split(",");
        System.out.println(split);
        oauth(s.split(","));
        oauth(s);
    }
}
