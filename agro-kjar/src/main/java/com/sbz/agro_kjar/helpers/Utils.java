package com.sbz.agro_kjar.helpers;

import java.util.Map;

public class Utils {
    
    public static int openedValvesOfArray(Long arrayId, Map<DeviceKey, Boolean> valvesMap)
    {
        int result = 0;
        
        for(DeviceKey dk : valvesMap.keySet())
        {
            if(dk.arrayId == arrayId)
            {
                if (valvesMap.get(dk) == true)
                {
                    result++;
                }
            }
        }
        return result;
    }
}
