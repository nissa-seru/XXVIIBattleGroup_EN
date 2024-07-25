package data.hullmods;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;

import static data.utils.sgb.SGB_stringsManager.txt;
public class SGB_Austenite_Weapon_Blast_L extends BaseHullMod {    
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
        tooltip.addPara(txt("HullMods_Austenite_LeftWeapon"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Weapon_Blast1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Weapon_Blast2"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Weapon_Blast3"), SGB_Color.SGBOMGWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Weapon_Blast4"), SGB_Color.SGBhardWord, 4f);
    }
}
