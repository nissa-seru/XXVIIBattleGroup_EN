package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_Head_Empty extends BaseHullMod {
    private static final float HULL_AHE = 45f;
    private static final float ARMOR_FLAT_AHE = 50f;
    private static final float FLUX_CAP_AHE = 20f;
    private static final float FLUX_DIS_AHE = 20f;
    private static final float ZERO_FLUX_AHE = 1f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {

        stats.getArmorBonus().modifyFlat(id, -ARMOR_FLAT_AHE);
        stats.getHullBonus().modifyPercent(id, -HULL_AHE);
        stats.getFluxCapacity().modifyPercent(id, +FLUX_CAP_AHE);
        stats.getFluxDissipation().modifyPercent(id, +FLUX_DIS_AHE);
        stats.getZeroFluxMinimumFluxLevel().modifyFlat(id, +ZERO_FLUX_AHE);
    }
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) ARMOR_FLAT_AHE + "";
        if (index == 1) return "" + (int) HULL_AHE + "%";
        if (index == 2) return "" + (int) FLUX_CAP_AHE + "%";
        if (index == 3) return "" + (int) FLUX_DIS_AHE + "%";
        if (index == 4) return "" + (int) (ZERO_FLUX_AHE*100f) + "%";
        return null;
    }
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
        tooltip.addPara(txt("HullMods_Austenite_Head"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Empty1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Empty2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Empty3"), SGB_Color.SGBcoreDanger_Word, 4f);
    }
}
