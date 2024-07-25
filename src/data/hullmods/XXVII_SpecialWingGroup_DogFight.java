package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static data.hullmods.XXVII_SpecialWingGroups_Base.SpecialWingType;
import static data.hullmods.XXVII_SpecialWingGroups_Base.XXVII_SpecialWingGroup_Coop_List;
import static data.utils.sgb.SGB_stringsManager.txt;

public class XXVII_SpecialWingGroup_DogFight extends BaseHullMod {

	public static final String XXVII_SpecialWingGroup_DogFight = "XXVII_SpecialWingGroup_DogFight";
	private static final String id = "XXVII_SpecialWingGroup_DogFight";
	private  static final Float NavRelay_TimeLess_Percent = 25f;
	private  static final Float Thruster_SpeedAdd_Percent = 30f;
	private  static final Float Thruster_HealthAdd_Percent = 100f;

	@Override
	public String getUnapplicableReason(ShipAPI ship) {
		return XXVII_SpecialWingGroups_Base.XXVII_SpecialWingGroups_InapplicableReason(ship,null,1,SpecialWingType);
	}
	@Override
	public boolean isApplicableToShip(ShipAPI ship) {
		return XXVII_SpecialWingGroups_Base.XXVII_SpecialWingGroups_isApToShip(ship,1,spec,SpecialWingType);
	}

	@Override
	public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
		if (ship == null) return;
		float pad = 10f;
		TooltipMakerAPI text;

		tooltip.addSectionHeading("HullMods_Remarks", Alignment.TMID, 5f);
		text = tooltip.beginImageWithText("graphics/hullmods/XXVII_SpecialWingGroup_DogFight.png", 32);
		text.addPara(txt("HullMods_XXVII_SpecialWingGroup_Above"), 0, Misc.getPositiveHighlightColor(),txt("HullMods_XXVII_SpecialWingGroup_DogFight"));
		tooltip.addImageWithText(pad);
/*
		if (ship.getVariant().hasHullMod("nav_relay")) {
			text = tooltip.beginImageWithText("graphics/hullmods/nav_relay.png", 32);
			text.addPara("母舰改装-宏观战机调控", new Color(246, 235, 141, 255), 4f);
			text.addPara("[%s]", 0, Misc.getPositiveHighlightColor(), "已安装");
			text.addPara("母舰增设大量额外架次战机，从而让母舰能快速整备编队，增加{%s}舰载机整备速度", 0, Misc.getHighlightColor(),  NavRelay_TimeLess_Percent+"%");
			tooltip.addImageWithText(pad);
		} else {
			text = tooltip.beginImageWithText("graphics/hullmods/nav_relay.png", 32);
			text.addPara("母舰改装-宏观战机调控", new Color(246, 235, 141, 255), 4f);
			text.addPara("[%s]", 0, Misc.getNegativeHighlightColor(), "未安装");
			text.addPara("母舰增设大量额外架次战机，从而让母舰能快速整备编队，增加{%s}舰载机整备速度", 0, Misc.getHighlightColor(),  NavRelay_TimeLess_Percent+"%");
			tooltip.addImageWithText(pad);
		}

		if (ship.getVariant().hasHullMod("auxiliarythrusters")) {
			text = tooltip.beginImageWithText("graphics/hullmods/auxilliary_thrusters.png", 32);
			text.addPara("战机改装-高稳离子引擎", new Color(168, 200, 25, 255), 4f);
			text.addPara("[%s]", 0, Misc.getPositiveHighlightColor(), "已安装");
			text.addPara("大幅度额外优化舰载机引擎，增加{%s}引擎血量，并略微提升机动能力", 0, Misc.getHighlightColor(),  Thruster_HealthAdd_Percent+"%");
			tooltip.addImageWithText(pad);
		} else {
			text = tooltip.beginImageWithText("graphics/hullmods/auxilliary_thrusters.png", 32);
			text.addPara("战机改装-高稳离子引擎", new Color(168, 200, 25, 255), 4f);
			text.addPara("[%s]", 0, Misc.getNegativeHighlightColor(), "未安装");
			text.addPara("大幅度额外优化舰载机引擎，增加{%s}引擎血量，并略微提升机动能力", 0, Misc.getHighlightColor(),  Thruster_HealthAdd_Percent+"%");
			tooltip.addImageWithText(pad);
		}

 */
	}
	@Override
	public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
		if (stats.getVariant().hasHullMod("nav_relay")) {
	//		stats.getFighterRefitTimeMult().modifyPercent(id, -NavRelay_TimeLess_Percent);
		}
	}
	@Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		CombatEngineAPI engine = Global.getCombatEngine();
		if (!ship.isAlive() || engine.isPaused()) return;
		Color color4 = new Color(221, 255, 171, 255);
		Color color5 = new Color(171, 255, 195, 35);
		for (ShipAPI fighter : getFighters(ship)) {
			float time = fighter.getFullTimeDeployed(); // 存活秒数
			if (!fighter.getWing().getSpec().getId().startsWith("SGB_")) return;
			if (time < 1f) {//舰载机数据改动
				/*
				//插件可选升级改动
				if (ship.getVariant().hasHullMod("auxiliarythrusters")) {
					fighter.getMutableStats().getEngineHealthBonus().modifyPercent(id, Thruster_HealthAdd_Percent);
					fighter.getMutableStats().getMaxSpeed().modifyPercent(id, Thruster_SpeedAdd_Percent);
					fighter.getMutableStats().getTurnAcceleration().modifyPercent(id, Thruster_SpeedAdd_Percent);
					fighter.getMutableStats().getAcceleration().modifyPercent(id, Thruster_SpeedAdd_Percent/1.5f);
					fighter.getMutableStats().getDeceleration().modifyPercent(id, Thruster_SpeedAdd_Percent);
				}
			} else {//舰载机视觉效果
				if (ship.getVariant().hasHullMod("auxiliarythrusters")) {
					fighter.getEngineController().extendFlame(this, 1.25f, 0.65f, 0.5f);
					fighter.getEngineController().fadeToOtherColor(this, color4, color5, 1f, 0.9f);
				}

				 */
			}
		}
	}

	private static java.util.List<ShipAPI> getFighters(ShipAPI carrier) {
		java.util.List<ShipAPI> result = new ArrayList<>();
		for (FighterWingAPI wing : carrier.getAllWings()) {
			List<ShipAPI> members = wing.getWingMembers();
			if (!members.isEmpty()) {
				result.addAll(members);
			}
		}
		return result;
	}
}




