package data.hullmods;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;

import static data.utils.sgb.SGB_stringsManager.txt;
public class SGB_Austenite_System_Wingman_CORED extends BaseHullMod {    
    @Override
    public int getDisplaySortOrder() {
        return 2000;
    }

    @Override
    public int getDisplayCategoryIndex() {
        return 3;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara("让战机内置系统能够运作。", SGB_Color.SGBhardWord, 4f);
        tooltip.addPara("你不应该看见这个玩意；", SGB_Color.SGBhardWord, 4f);
        tooltip.addPara("这是个中介插件。", SGB_Color.SGBhardWord, 4f);
    }
}
