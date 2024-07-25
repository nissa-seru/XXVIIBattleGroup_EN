package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_Head_Assault extends BaseHullMod {
    private static final float DAM_AHAS = 10f;
    private static final float RPS_AHAS = 50f;
    private static final float SHIELD_RAD_AHAS = 10f;
    private static final float ROUND_AHAS = 15f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {

        stats.getBallisticWeaponDamageMult().modifyPercent(id, +DAM_AHAS);
        stats.getBallisticRoFMult().modifyPercent(id, +RPS_AHAS);
        stats.getShieldDamageTakenMult().modifyPercent(id, -SHIELD_RAD_AHAS);
        stats.getBallisticWeaponRangeBonus().modifyPercent(id, -ROUND_AHAS);
        stats.getMissileWeaponRangeBonus().modifyPercent(id, -ROUND_AHAS);
    }
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) DAM_AHAS + "%";
        if (index == 1) return "" + (int) RPS_AHAS + "%";
        if (index == 2) return "" + (int) SHIELD_RAD_AHAS + "%";
        if (index == 3) return "" + (int) ROUND_AHAS + "%";
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
        tooltip.addPara(txt("HullMods_Austenite_Head_Assault1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Assault2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Head_Assault3"), SGB_Color.SGBcoreDanger_Word, 4f);
    }
}
