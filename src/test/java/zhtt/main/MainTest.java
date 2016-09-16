package zhtt.main;

/**
 * Created by zhtt on 2016/9/14.
 */
public class MainTest {

    public static void main(String[] args){
        String str="children.0.children.1.children.0.children";
        System.out.println( str.substring(0,str.lastIndexOf("children"))+"show");
    }
}
