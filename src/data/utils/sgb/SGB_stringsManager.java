package data.utils.sgb;

import com.fs.starfarer.api.Global;

public class SGB_stringsManager {   
    private static final String ML="SGB";    
    
    public static String txt(String id){
        return Global.getSettings().getString(ML, id);
    }
}