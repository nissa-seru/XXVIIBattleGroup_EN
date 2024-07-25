package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.util.MagicAnim;
import data.utils.sgb.SGB_Color;
import static data.utils.sgb.SGB_stringsManager.txt;

import java.util.List;

import static data.shipsystems.scripts.SGB_Austenite_System_Ammo.*;

public class SGB_Austenite_System_Repair extends BaseHullMod {
    public static final float IM_ONE_SASR = 1f;
    public static final float HULL_REPAIR_PERCENT_SASR = 35f;
    public static final float COMBAT_REPAIR_SASR = 50f;
    public static final float HULL_PERCENT_SASR = 10f;
    public static final float ARMOR_PERCENT_SASR = 90f;
    public static final float DAMPER_PERCENT_SASR = 85f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipAPI ship = null;
        stats.getArmorBonus().modifyPercent(id, -ARMOR_PERCENT_SASR);
        stats.getHullBonus().modifyPercent(id, +HULL_PERCENT_SASR);
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
            if(ship.getHitpoints() > 5000f){
                stats.getHullBonus().modifyFlat(id, Math.min(0,-(ship.getHitpoints()-5000f)));
            }
            //if(ship.arm() > 5000f){
            //    stats.getHullBonus().modifyFlat(id, Math.min(0,-(ship.getHitpoints()-5000f)));
            //}
        }
    }
    public void advanceInCombat(ShipAPI ship, float amount) {
        String id = ship.getId();
        MutableShipStatsAPI stats =ship.getMutableStats();
        boolean isFireing = false;
        if(ship != null) {
            List<WeaponAPI> weapons = ship.getAllWeapons();
            for (WeaponAPI w : weapons){
                if (w.isFiring()){
                    stats.getHullCombatRepairRatePercentPerSecond().unmodify();
                    stats.getMaxCombatHullRepairFraction().unmodify();
                    stats.getCombatEngineRepairTimeMult().unmodify();
                    stats.getCombatWeaponRepairTimeMult().unmodify();
                    isFireing = true;
                }
            }
            if (!isFireing) {
                stats.getHullCombatRepairRatePercentPerSecond().modifyFlat(id, HULL_REPAIR_PERCENT_SASR);
                stats.getMaxCombatHullRepairFraction().modifyFlat(id, 3000f/Math.max(3000f,ship.getHitpoints()));
                stats.getCombatEngineRepairTimeMult().modifyPercent(id, -COMBAT_REPAIR_SASR);
                stats.getCombatWeaponRepairTimeMult().modifyPercent(id, -COMBAT_REPAIR_SASR);
            }
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
        if (index == 0) return "" + (int) HULL_PERCENT_SASR + "%";
        if (index == 1) return "" + (int) ARMOR_PERCENT_SASR + "%";
        if (index == 2) return "" + (int) HULL_REPAIR_PERCENT_SASR + "%";
        if (index == 3) return "" + (int) COMBAT_REPAIR_SASR + "%";
        if (index == 4) return "" + (int) IM_ONE_SASR*5000f + "";
        if (index == 5) return "" + (int) IM_ONE_SASR*3000f + "";
        if (index == 6) return "" + (int) DAMPER_PERCENT_SASR + "%";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_System"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Repair_Dec1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Repair_Dec2"), SGB_Color.SGBOMGWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Repair_Dec3"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Repair_Dec4"), SGB_Color.SGBcoreDanger_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Repair_Dec5"), SGB_Color.SGBcoreDanger_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Repair_Dec6"), SGB_Color.SGBOMGWord2, 2f);
    }
}
