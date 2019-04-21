package lottery.script.custom;

import com.superywd.library.script.metadata.*;

public class CustomDemo1{

    @OnClassLoad
    public static void startMethod(){
        System.out.println("类加载成功！");
    }

    public static void main(String[] args){
        System.out.println("一通打印！");
    }

    @OnClassUnLoad
    public static void endMethod(){
        System.out.println("类卸载成功！");
    }

}