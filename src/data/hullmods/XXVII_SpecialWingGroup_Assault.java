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

public class XXVII_SpecialWingGroup_Assault extends BaseHullMod {

	public static final String XXVII_SpecialWingGroup_Assault = "XXVII_SpecialWingGroup_Assault";
	private static final String id = "XXVII_SpecialWingGroup_Assault";
	private  static final Float Sensors_TimeSpeed_Percent = 10f;
	private  static final Float Overload_SpeedAdd_Percent = 30f;
	private  static final Float Overload_Flux_Percent = 25f;
	private  static final Float Overload_HealthLess_Percent = 35f;
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
		text = tooltip.beginImageWithText("graphics/hullmods/XXVII_SpecialWingGroup_Assault.png", 32);
		text.addPara(txt("HullMods_XXVII_SpecialWingGroup_Above"), 0, Misc.getPositiveHighlightColor(),txt("HullMods_XXVII_SpecialWingGroup_Assault"));
		tooltip.addImageWithText(pad);

/*
		if (ship.getVariant().hasHullMod("hiressensors")) {
			text = tooltip.beginImageWithText("graphics/hullmods/high_res_sensors.png", 32);
			text.addPara("母舰改装-高精尖传感器", new Color(141, 237, 246, 255), 4f);
			text.addPara("[%s]", 0, Misc.getPositiveHighlightColor(), "已安装");
			text.addPara("战机更换协同传感系统，以此随时和母舰同步数据，增加舰载机{%s}反应能力 - 即时间流速加快对应倍率", 0, Misc.getHighlightColor(),  Sensors_TimeSpeed_Percent+"%");
			tooltip.addImageWithText(pad);
		} else {
			text = tooltip.beginImageWithText("graphics/hullmods/high_res_sensors.png", 32);
			text.addPara("母舰改装-高精尖传感器", new Color(141, 237, 246, 255), 4f);
			text.addPara("[%s]", 0, Misc.getNegativeHighlightColor(), "未安装");
			text.addPara("战机更换协同传感系统，以此随时和母舰同步数据，增加舰载机{%s}反应能力 - 即时间流速加快对应倍率", 0, Misc.getHighlightColor(),  Sensors_TimeSpeed_Percent+"%");
			tooltip.addImageWithText(pad);
		}

		if (ship.getVariant().hasHullMod("safetyoverrides")) {
			text = tooltip.beginImageWithText("graphics/hullmods/safety_overrides.png", 32);
			text.addPara("战机改装-战机系统超限", new Color(246, 141, 141, 255), 4f);
			text.addPara("[%s]", 0, Misc.getPositiveHighlightColor(), "已安装");
			text.addPara("战机解除全部安全系统，增加战机极限速度{%s}，提高战机{%s}幅能和散幅效益，但降低战机{%s}结构", 0, Misc.getHighlightColor(),
					Overload_SpeedAdd_Percent+"%",Overload_Flux_Percent+"%",Overload_HealthLess_Percent+"%");
			tooltip.addImageWithText(pad);
		} else {
			text = tooltip.beginImageWithText("graphics/hullmods/safety_overrides.png", 32);
			text.addPara("战机改装-战机系统超限", new Color(246, 141, 141, 255), 4f);
			text.addPara("[%s]", 0, Misc.getNegativeHighlightColor(), "未安装");
			text.addPara("战机解除全部安全系统，增加战机极限速度{%s}，提高战机{%s}幅能和散幅效益，但降低战机{%s}结构", 0, Misc.getHighlightColor(),
					Overload_SpeedAdd_Percent+"%",Overload_Flux_Percent+"%",Overload_HealthLess_Percent+"%");
			tooltip.addImageWithText(pad);
		}*/
	}
	@Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		CombatEngineAPI engine = Global.getCombatEngine();
		if (!ship.isAlive() || engine.isPaused()) return;
		Color color4 = new Color(255, 171, 171, 255);
		Color color5 = new Color(255, 171, 228, 35);
		for (ShipAPI fighter : getFighters(ship)) {
			float time = fighter.getFullTimeDeployed(); // 存活秒数
			if (!fighter.getWing().getSpec().getId().startsWith("SGB_")) return;
			if (time < 1f) {//舰载机数据改动
				/*
				//插件可选升级改动
				if (ship.getVariant().hasHullMod("hiressensors")) {
					fighter.getMutableStats().getTimeMult().modifyPercent(id, Sensors_TimeSpeed_Percent);
				}
				if (ship.getVariant().hasHullMod("safetyoverrides")) {
					fighter.getMutableStats().getMaxSpeed().modifyPercent(id, Overload_SpeedAdd_Percent);
					fighter.getMutableStats().getAcceleration().modifyPercent(id, Overload_SpeedAdd_Percent);
					fighter.getMutableStats().getMaxTurnRate().modifyPercent(id, -10f);

					fighter.getMutableStats().getFluxCapacity().modifyPercent(id, Overload_Flux_Percent);
					fighter.getMutableStats().getFluxDissipation().modifyPercent(id, Overload_Flux_Percent);

					fighter.getMutableStats().getHullBonus().modifyPercent(id, -Overload_HealthLess_Percent);
				}*/
			} else {//舰载机视觉效果
				/*
				if (ship.getVariant().hasHullMod("safetyoverrides")) {
					fighter.getEngineController().extendFlame(this, 1.25f, 0.5f, 0.5f);
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




