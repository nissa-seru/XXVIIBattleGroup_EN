package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.util.MagicAnim;
import data.utils.sgb.SGB_Color;

import java.util.List;

import static data.utils.sgb.SGB_stringsManager.txt;
import static data.shipsystems.scripts.SGB_Austenite_System_Ammo.*;

public class SGB_Austenite_System_Wing extends BaseHullMod {
    public static final float IM_ONE_SASW = 1f;
    public static final float HULL_FLAT_SASW = 50f;
    public static final float FIGHTER_REMAKE_TIME = 50f;
    public static final float COMBAT_FIGHTER_RANGE_SASW = 5000f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipAPI ship = null;

        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
            if(ship.getHitpoints() > 100f){
                stats.getHullBonus().modifyMult(id, -IM_ONE_SASW);
            }
            else{
                stats.getHullBonus().modifyPercent(id, -50f);
            }
            stats.getDynamic().getStat(Stats.REPLACEMENT_RATE_DECREASE_MULT).modifyPercent(id, -FIGHTER_REMAKE_TIME);
        }
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


    public boolean isApplicableToShip(ShipAPI ship) {
        int bays = (int) ship.getMutableStats().getNumFighterBays().getBaseValue();
        return ship != null && bays > 0;
    }

    public String getUnapplicableReason(ShipAPI ship) {
        return txt("HullMods_Austenite_System_Wingman_ERROR");
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
        if (index == 0) return "" + (int) HULL_FLAT_SASW + "%";
        if (index == 1) return "" + (int) FIGHTER_REMAKE_TIME + "%";
        if (index == 2) return "" + (int) COMBAT_FIGHTER_RANGE_SASW + "";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_System"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_Dec1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_Dec2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_Dec3"), SGB_Color.SGBcoreDanger_Word, 5f);
        tooltip.addPara(txt("HullMods_Austenite_System_Wingman_Dec4"), SGB_Color.SGBOMGWord2, 5f);
    }
}
