package com.haoke.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Auspice Tian
 * @time 2021-05-02 17:15
 * @current haoke-manage-com.haoke.api.utils
 */
public class ID {
    private static long tmpID = 0;

    //锁
    private static boolean tmpIDlocked = false;

    public static long getId()
    {
        long ltime = 0;
        while (true)
        {
            if(tmpIDlocked == false)
            {
                tmpIDlocked = true;
                //当前：（年、月、日、时、分、秒、毫秒）*10000
                ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
                if(tmpID < ltime)
                {
                    tmpID = ltime;
                }
                else
                {
                    tmpID = tmpID + 1;
                    ltime = tmpID;
                }
                tmpIDlocked = false;
                return ltime;
            }
        }
    }
}
