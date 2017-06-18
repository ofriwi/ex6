import oop.ex6.main.scopes.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ofri Wienner on 13/06/2017.
 */
public class test {
    public static void main(String[] args){
        int[][] x=new int[10][2];
        System.out.println(x.length);
        Map<String, Method> methods = new HashMap<>();
        methods.put("h", new Method(null, null, ""));
        System.out.println(methods.get("s"));
    }
}
