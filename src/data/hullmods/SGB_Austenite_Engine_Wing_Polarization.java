package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.utils.sgb.SGB_Color;
import org.magiclib.util.MagicAnim;

import java.awt.*;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_Engine_Wing_Polarization extends BaseHullMod {

    private static final float SHIP_MOVEMENT_AEWP = 50f;
    private static final float REPAIR_TIME_AEWP = 20f;
    private Color color = new Color(255, 158, 78,255);
    private Color colorTrail = new Color(192, 161, 93, 50);

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
    }
    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        String id = ship.getId();
        MutableShipStatsAPI stats =ship.getMutableStats();
        if(ship != null) {
            //made Engine UnBreakable
            float MaxCR = ship.getCRAtDeployment();
            float CurCR = ship.getCurrentCR();
            //get speed
            float MaxVelo = 0f;
            float VeloX = 0f;
            float VeloY = 0f;
            float Speed2 = 0f;
            float CurSpeed = 0f;
            float NormSpeed = 0f;
            float AnmiSpeed = 0f;
            MaxVelo = ship.getMaxSpeed();
            VeloX = ship.getVelocity().getX();
            VeloY = ship.getVelocity().getY();
            if (VeloX == 0f && VeloY == 0f) {
                CurSpeed = 0;
            } else {
                Speed2 = (VeloX * VeloX) + (VeloY * VeloY);
                CurSpeed = (float) Math.sqrt(Speed2);
            }
            NormSpeed = 4f * MagicAnim.smoothNormalizeRange(CurSpeed, 0f, MaxVelo);
            AnmiSpeed = MagicAnim.cycle(NormSpeed, 0, 1f);
            if (MaxCR == CurCR) {
                stats.getEngineDamageTakenMult().modifyMult(id, 1f / REPAIR_TIME_AEWP);
                stats.getEngineHealthBonus().modifyMult(id, REPAIR_TIME_AEWP);
                stats.getCombatEngineRepairTimeMult().modifyMult(id, 1f / REPAIR_TIME_AEWP);
                ship.getEngineController().fadeToOtherColor(this, color, colorTrail, AnmiSpeed, 1.0f);
                ship.getEngineController().extendFlame(this, 1f * AnmiSpeed, 1.25f * AnmiSpeed, 0.5f * CurCR / 70f);
            } else {
                stats.getEngineDamageTakenMult().unmodify();
                stats.getEngineHealthBonus().unmodify();
                stats.getCombatEngineRepairTimeMult().unmodify();
            }

            //use CR peak time for Speed
            float noLossTime = ship.getMutableStats().getPeakCRDuration().computeEffective(ship.getHullSpec().getNoCRLossTime());
            float LossTimeCR = ship.getTimeDeployedForCRReduction();
            float ThisTimeCR = noLossTime - LossTimeCR;

            float CRTimetoSpeed = MagicAnim.smoothNormalizeRange(ThisTimeCR, 0f, noLossTime);
            stats.getAcceleration().modifyPercent(id, CRTimetoSpeed * SHIP_MOVEMENT_AEWP);
            stats.getDeceleration().modifyPercent(id, CRTimetoSpeed * SHIP_MOVEMENT_AEWP);
            stats.getMaxTurnRate().modifyPercent(id, CRTimetoSpeed * SHIP_MOVEMENT_AEWP);
            stats.getTurnAcceleration().modifyPercent(id, CRTimetoSpeed * SHIP_MOVEMENT_AEWP);

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
        if (index == 0) return "" + (int) SHIP_MOVEMENT_AEWP + "%";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Polarization_1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Polarization_2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Polarization_3"), SGB_Color.SGBcoreDanger_Word, 4f);
        tooltip.addSectionHeading(txt("HullMods_Tips"), Alignment.MID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Polarization_4"), SGB_Color.SGBcoreIntersting_Word, 4f);
    }
}
