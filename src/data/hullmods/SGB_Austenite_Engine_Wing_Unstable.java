package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.util.MagicAnim;
import data.utils.sgb.SGB_Color;

import java.awt.*;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_Engine_Wing_Unstable extends BaseHullMod {
    private Color color = new Color(74, 187, 49,255);

    private static final float FLUX_DEC_AEWU = 25f;
    private static final float SPEED_ADD_AEWU = 50f;
    private static final float SPEED_ACC_DEC_AEWU = 60f;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
    }
    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        String id = ship.getId();
        MutableShipStatsAPI stats =ship.getMutableStats();
        CombatEngineAPI engine = Global.getCombatEngine();
        if(ship != null) {
            //get FluxScale
            float FluxScale = 1f;
            float MaxFlux = ship.getMaxFlux();
            float CurFlux = ship.getCurrFlux();
            FluxScale = MagicAnim.smoothToRange(CurFlux, MaxFlux / 2f, MaxFlux, 0f, 1f);
            FluxScale = MagicAnim.smoothToRange(CurFlux, MaxFlux / 2f, MaxFlux, 1f, 2f);

            //get speed
            float MaxVelo = 0f;
            float VeloX = 0f;
            float VeloY = 0f;
            float Speed2 = 0f;
            float CurSpeed = 0f;
            float SpeedScale = 0f;
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
            if (MaxVelo == 0) {
                SpeedScale = 0;
            } else {
                SpeedScale = CurSpeed / MaxVelo;
            }


            if (ship != null && engine != null && MaxFlux != 0f) {
                ship.getEngineController().fadeToOtherColor(this, color, new Color(0, 0, 0, 0), FluxScale, 0.67f);
                ship.getEngineController().extendFlame(this, 2f * FluxScale, 0.5f * AnmiSpeed, 0.5f * AnmiSpeed);

                stats.getMaxSpeed().modifyPercent(id, +(SPEED_ADD_AEWU * CurFlux/MaxFlux));
                stats.getFluxDissipation().modifyPercent(id, +(FLUX_DEC_AEWU * SpeedScale));
                stats.getAcceleration().modifyFlat(id, +(SPEED_ACC_DEC_AEWU * CurFlux/MaxFlux));
                stats.getDeceleration().modifyFlat(id, +(SPEED_ACC_DEC_AEWU / 2f * CurFlux/MaxFlux));
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
        if (index == 0) return "" + (int) FLUX_DEC_AEWU + "%";
        if (index == 1) return "" + (int) SPEED_ADD_AEWU + "%";
        return null;
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Unstable1"), SGB_Color.SGBhardWord, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Unstable2"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Unstable3"), SGB_Color.SGBcoreIntersting_Word, 4f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Unstable4"), SGB_Color.SGBcoreDanger_Word, 4f);
        tooltip.addSectionHeading(txt("HullMods_Tips"), Alignment.MID, 5f);
        tooltip.addPara(txt("HullMods_Austenite_Engine_Wing_Unstable5"), SGB_Color.SGBcoreIntersting_Word, 4f);
    }
}
