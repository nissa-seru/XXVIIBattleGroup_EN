package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;

import java.awt.*;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_Engine_Wing_BackLoad extends BaseHullMod {

    private static final float SYSTEM_REGEN_AEWB = 20f;
    private static final float SYSTEM_AMMO_AEWB = 1f;
    private static final float COMBAT_READ_AEWB = 5f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipAPI ship = (ShipAPI) stats.getEntity();
        if (ship != null && ship.getSystem()!=null) {
            if (ship.getSystem().getMaxAmmo() >1) {
            stats.getSystemRegenBonus().modifyPercent(id, SYSTEM_REGEN_AEWB);
            }
        }
        else{
            stats.getSystemUsesBonus().modifyFlat(id, +SYSTEM_AMMO_AEWB);
        }

        stats.getMaxCombatReadiness().modifyFlat(id, +0.01f*COMBAT_READ_AEWB);
    }
    @Override
    public int getDisplaySortOrder() {
        return 2000;
    }
    @Override
    public int getDisplayCategoryIndex() {
        return 3;
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) SYSTEM_REGEN_AEWB + "%";
        if (index == 1) return "" + (int) SYSTEM_AMMO_AEWB + txt("HullMods_time");
        if (index == 2) return "" + (int) COMBAT_READ_AEWB + txt("HullMods_point");
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_BackLoad1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_BackLoad2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addSectionHeading(txt("HullMods_Tips"), Alignment.MID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_BackLoad3"), SGB_Color.SGBcoreIntersting_Word, 4f);
    }
}
