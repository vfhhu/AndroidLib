package xyz.vfhhu.lib.android.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leo3x on 2018/11/26.
 */

public class StringUtil {
    public static List<String[]> matchTextAll(String inputStr, String patternStr){
        List<String[]> list = new ArrayList<>();

        if(patternStr == null || inputStr == null) {
            return list;
        }
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);
        while (matcher.find()){
            String []fds=new String[matcher.groupCount()+1];
            if(matcher.groupCount()>0)fds[0]=matcher.group(0);
            for (int i = 0; i < matcher.groupCount(); i++){
                fds[i+1]=matcher.group(i+1);
            }
            list.add(fds);
        }
        return list;
    }
}
