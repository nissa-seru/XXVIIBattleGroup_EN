package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_HeavyWing extends BaseHullMod {

    private static final float SPEED_FLAT_AHW = 35f;
    private static final float HULL_FLAT_AHW = 500f;
    private static final float MOBILITY_MULT_AHW = 30f;
    private static final float ARMOR_TAKEN_MULT_AHW = 20f;
    private static final float ARMOR_FLAT_AHW = 150f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().modifyFlat(id, -SPEED_FLAT_AHW);

        stats.getAcceleration().modifyPercent(id, -MOBILITY_MULT_AHW);
        stats.getDeceleration().modifyPercent(id, -MOBILITY_MULT_AHW);
        stats.getTurnAcceleration().modifyPercent(id, -MOBILITY_MULT_AHW);
        stats.getMaxTurnRate().modifyPercent(id, -MOBILITY_MULT_AHW);

        stats.getEffectiveArmorBonus().modifyPercent(id, +ARMOR_TAKEN_MULT_AHW);
        stats.getArmorBonus().modifyFlat(id, +ARMOR_FLAT_AHW);

        stats.getHullBonus().modifyFlat(id, +HULL_FLAT_AHW);
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
        if (index == 0) return "" + (int) ARMOR_FLAT_AHW + "";
        if (index == 1) return "" + (int) ARMOR_TAKEN_MULT_AHW + "%";
        if (index == 2) return "" + (int) HULL_FLAT_AHW + "";
        if (index == 3) return "" + (int) SPEED_FLAT_AHW + "su/s";
        if (index == 4) return "" + (int) MOBILITY_MULT_AHW + "%";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Wings"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_HeavyWing1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_HeavyWing2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_HeavyWing3"), SGB_Color.SGBcoreDanger_Word, 4f);
    }
}
