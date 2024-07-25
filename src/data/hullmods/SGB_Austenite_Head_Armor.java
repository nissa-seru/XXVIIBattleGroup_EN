package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_Head_Armor extends BaseHullMod {
    private static final float HULL_AHA = 10f;
    private static final float EMP_AHA = 50f;
    private static final float WEAPON_REP_AHA = 30f;
    private static final float DAM_TAKE_AHA = 25f;
    private static final float FLUX_DIS_AHA = 25f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {

        stats.getHullBonus().modifyPercent(id, +HULL_AHA);
        stats.getEmpDamageTakenMult().modifyPercent(id, -EMP_AHA);
        stats.getCombatWeaponRepairTimeMult().modifyPercent(id, -WEAPON_REP_AHA);
        stats.getHullDamageTakenMult().modifyPercent(id, -DAM_TAKE_AHA);
        stats.getArmorDamageTakenMult().modifyPercent(id, -DAM_TAKE_AHA);
        stats.getFluxDissipation().modifyPercent(id, -FLUX_DIS_AHA);
    }
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) HULL_AHA + "%";
        if (index == 1) return "" + (int) EMP_AHA + "%";
        if (index == 2) return "" + (int) WEAPON_REP_AHA + "%";
        if (index == 3) return "" + (int) DAM_TAKE_AHA + "%";
        if (index == 4) return "" + (int) FLUX_DIS_AHA + "%";
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
        tooltip.addPara(txt("HullMods_Austenite_Head_Armor1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Armor2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Armor3"), SGB_Color.SGBcoreDanger_Word, 4f);
    }
}
