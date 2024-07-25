package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.CombatReadinessPlugin;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.utils.sgb.SGB_Color;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_AllForHuman extends BaseHullMod {
	public SGB_AllForHuman() {
	}
	public static final String SGB = "SGB";
	private static Map Speed = new HashMap(); //不同船型
	private static Map ZERO_FLUX_SGB = new HashMap();
	/**
	 * This is the center hullmod for SGB.
	 */

	//private static final float SPEED_BONUS = 20f;
	static {
		Speed.put(ShipAPI.HullSize.FIGHTER, 10f);
		Speed.put(ShipAPI.HullSize.FRIGATE, 20f);
		Speed.put(ShipAPI.HullSize.DESTROYER, 25f);
		Speed.put(ShipAPI.HullSize.CRUISER, 15f);
		Speed.put(ShipAPI.HullSize.CAPITAL_SHIP, 10f);

		ZERO_FLUX_SGB.put(ShipAPI.HullSize.FIGHTER, 1f);
		ZERO_FLUX_SGB.put(ShipAPI.HullSize.FRIGATE, 1f);
		ZERO_FLUX_SGB.put(ShipAPI.HullSize.DESTROYER, 2f);
		ZERO_FLUX_SGB.put(ShipAPI.HullSize.CRUISER, 3f);
		ZERO_FLUX_SGB.put(ShipAPI.HullSize.CAPITAL_SHIP, 4f);
	}
	private static float CAPACITY_MULT = 1.03f;
	private static float DISSIPATION_MULT = 1.03f;
	private static float MISSILE_MULT = 30f;
	private static float MISSILE_MULT_NOT_SHOW = 20f;
	private static float MAX_SPEED_TIME_PERCENT = 70f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		//ShipAPI ship = (ShipAPI) stats.getEntity();
		stats.getZeroFluxSpeedBoost().modifyFlat(id, (Float) Speed.get(hullSize));

		stats.getMissileGuidance().modifyPercent(id, MISSILE_MULT);
		stats.getMissileTurnAccelerationBonus().modifyPercent(id, MISSILE_MULT_NOT_SHOW);

	}
	@Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		MutableShipStatsAPI stats = ship.getMutableStats();
		HullSize hullSize = ship.getHullSize();
		String id =ship.getId();
		if (ship != null && !ship.getVariant().getHullMods().contains("safetyoverrides")) {
			//Normal
			//Float NormalTimeCR =ship.getHullSpec().getNoCRLossTime();
			//Modify
			float noLossTime = ship.getMutableStats().getPeakCRDuration().computeEffective(ship.getHullSpec().getNoCRLossTime());
			//Only Workable in 0.96a//Float ThisTimeCR=ship.getPeakTimeRemaining;
			//So lets use this
			float LossTimeCR =ship.getTimeDeployedForCRReduction();
			float ThisTimeCR =noLossTime-LossTimeCR;
			//Yep
			float maxFlux= ship.getMaxFlux();
			float needFlux= (Float) ZERO_FLUX_SGB.get(hullSize)/100f * maxFlux;
			if(maxFlux!=0 && (ThisTimeCR/noLossTime)>(MAX_SPEED_TIME_PERCENT/100f)) {
				stats.getZeroFluxMinimumFluxLevel().modifyFlat(id, needFlux / maxFlux);
			}
			if(maxFlux!=0 && (ThisTimeCR/noLossTime)<=(MAX_SPEED_TIME_PERCENT/100f)) {
				stats.getZeroFluxMinimumFluxLevel().unmodify();
			}
		}
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		//if (index == 1) return "" + (int) Math.round((CAPACITY_MULT - 1f) * 100f) + "%";
		if (index == 0) return "" + ((Float) Speed.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) Speed.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) Speed.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) Speed.get(HullSize.CAPITAL_SHIP)).intValue();
		if (index == 4) return "" + (int) MISSILE_MULT + "%";

		if (index == 5) return "" + (int) (100f-MAX_SPEED_TIME_PERCENT) + "%";
		if (index == 6) return "" + ((Float) ZERO_FLUX_SGB.get(HullSize.FRIGATE)).intValue() + "%";
		if (index == 7) return "" + ((Float) ZERO_FLUX_SGB.get(HullSize.DESTROYER)).intValue() + "%";
		if (index == 8) return "" + ((Float) ZERO_FLUX_SGB.get(HullSize.CRUISER)).intValue() + "%";
		if (index == 9) return "" + ((Float) ZERO_FLUX_SGB.get(HullSize.CAPITAL_SHIP)).intValue() + "%";
		return null;
	}

	//更多的描述拓展
	public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {

		float pad = 10f;
		TooltipMakerAPI text;


		tooltip.addSectionHeading(txt("HullMods_Remarks"), Alignment.TMID, 5f);
		text = tooltip.beginImageWithText("graphics/hullmods/SGB_AllForHuman.png", 32);
		tooltip.addImageWithText(pad);
		tooltip.addPara(txt("HullMods_AllForHuman1"), SGB_Color.SGBhardWord, 4f);
		tooltip.addPara(txt("HullMods_AllForHuman2"), SGB_Color.SGBhardWord, 4f);
		tooltip.addPara(txt("HullMods_AllForHuman3"), SGB_Color.SGBhardWord, 4f);
		//tooltip.addPara("其加速效果必须在零赋能状态下！", SGB_Color.SGBcoreDanger_Word, 4f);
		tooltip.addSectionHeading(txt("HullMods_Tips"), Alignment.MID, 5f);
		tooltip.addPara(txt("HullMods_AllForHuman4"), SGB_Color.SGBcoreIntersting_Word, 4f);
	}


	@Override
	public Color getBorderColor() {
		return new Color(255, 202, 12, 255);
	}

	@Override
	public Color getNameColor() {
		return new Color(213, 194, 65, 255);
	}
}
