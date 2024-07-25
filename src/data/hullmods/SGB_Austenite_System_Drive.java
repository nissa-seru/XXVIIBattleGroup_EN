package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_System_Drive extends BaseHullMod {
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
        tooltip.addPara(txt("HullMods_Austenite_System"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Drive_Dec1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Drive_Dec2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        //tooltip.addPara("每次使用都会超驰系统，并直接扣除舰船 1点 的CR！", SGB_Color.SGBcoreDanger_Word, 4f);
        tooltip.addSectionHeading(txt("HullMods_Tips"), Alignment.MID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_System_Drive_Dec3"), SGB_Color.SGBcoreIntersting_Word, 4f);
    }
}
