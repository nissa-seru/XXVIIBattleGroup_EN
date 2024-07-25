package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;

import java.util.List;

import static data.utils.sgb.SGB_stringsManager.txt;
import static data.hullmods.SGB_Austenite_System_Wing.IM_ONE_SASW;

public class SGB_Austenite_System_Wing_Support extends BaseHullMod {
    public static final float IM_ONE_SASWS = 1f;
    public static final float COMBAT_FIGHTER_RANGE_SASW = -3000f;
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if (!stats.getVariant().getHullMods().contains("SGB_Austenite_System_Wing")) {
            stats.getVariant().removeMod("SGB_Austenite_System_Wing_Support");
        }
        stats.getWeaponRangeThreshold().modifyPercent(id,-25f);
    }
    public void advanceInCombat(ShipAPI ship, float amount) {
        String id = ship.getId();
        MutableShipStatsAPI stats =ship.getMutableStats();
        if(ship != null) {
            float MaxFlux = ship.getMaxFlux();
            float NowFlux = 0;
            float FluxScale = 0;
            NowFlux = ship.getCurrFlux();
            if(NowFlux == 0f){
                FluxScale = 1f;
            }
            else {
                FluxScale = 1f-(NowFlux / MaxFlux);
            }
            stats.getFighterWingRange().modifyFlat(id,FluxScale*COMBAT_FIGHTER_RANGE_SASW);
        }
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
        if (index == 0) return "" + (int) IM_ONE_SASWS*25f + "%";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_wings"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_wings_Spt1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_wings_Spt2"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_wings_Spt3"), SGB_Color.SGBcoreDanger_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_wings_Spt4"), SGB_Color.SGBcoreDanger_Word, 5f);
    }
}
