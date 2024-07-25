//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package data.utils.sgb;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import java.util.Map;

public class HullModUtil {
    public HullModUtil() {
    }

    public static String getHullSizeFlatString(Map<ShipAPI.HullSize, Float> map) {
        return "" + ((Float)map.get(HullSize.FRIGATE)).intValue() + "/" + ((Float)map.get(HullSize.DESTROYER)).intValue() + "/" + ((Float)map.get(HullSize.CRUISER)).intValue() + "/" + ((Float)map.get(HullSize.CAPITAL_SHIP)).intValue() + "";
    }

    public static String getHullSizePercentString(Map<ShipAPI.HullSize, Float> map) {
        return data.utils.sgb.BlgMisc.getDigitValue((Float)map.get(HullSize.FRIGATE)) + "%/" + data.utils.sgb.BlgMisc.getDigitValue((Float)map.get(HullSize.DESTROYER)) + "%/" + data.utils.sgb.BlgMisc.getDigitValue((Float)map.get(HullSize.CRUISER)) + "%/" + data.utils.sgb.BlgMisc.getDigitValue((Float)map.get(HullSize.CAPITAL_SHIP)) + "%";
    }
}
