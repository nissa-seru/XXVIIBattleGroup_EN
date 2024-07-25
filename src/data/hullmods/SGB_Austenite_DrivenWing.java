package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;

import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_DrivenWing extends BaseHullMod {

    private static final float SPEED_FLAT_ADW = 40f;
    private static final float MOBILITY_MULT_ADW = 75f;
    private static final float EXPLOSIVE_TAKEN_MULT_ADW = 30f;
    private static final float ARMOR_FLAT_ADW = 100f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().modifyFlat(id, SPEED_FLAT_ADW);

        stats.getAcceleration().modifyPercent(id, MOBILITY_MULT_ADW);
        stats.getDeceleration().modifyPercent(id, MOBILITY_MULT_ADW);
        stats.getTurnAcceleration().modifyPercent(id, MOBILITY_MULT_ADW);
        stats.getMaxTurnRate().modifyPercent(id, MOBILITY_MULT_ADW);

        stats.getHighExplosiveDamageTakenMult().modifyPercent(id, EXPLOSIVE_TAKEN_MULT_ADW);
        stats.getArmorBonus().modifyFlat(id, -ARMOR_FLAT_ADW);
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
        if (index == 0) return "" + (int) SPEED_FLAT_ADW + "su/s";
        if (index == 1) return "" + (int) MOBILITY_MULT_ADW + "%";
        if (index == 2) return "" + (int) EXPLOSIVE_TAKEN_MULT_ADW + "%";
        if (index == 3) return "" + (int) ARMOR_FLAT_ADW + "";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Wings"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_DrivenWing1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_DrivenWing2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_DrivenWing3"), SGB_Color.SGBcoreDanger_Word, 4f);
    }
}
