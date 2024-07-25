package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.fleet.FleetMember;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static data.hullmods.XXVII_SpecialWingGroups_Base.SpecialWingType;
import static data.hullmods.XXVII_SpecialWingGroups_Base.XXVII_SpecialWingGroup_Coop_List;
import static data.utils.sgb.SGB_stringsManager.txt;

public class XXVII_SpecialWingGroup_Coop extends BaseHullMod {

	public static final String XXVII_SpecialWingGroup_Coop = "XXVII_SpecialWingGroup_Coop";
	private static final String id = "XXVII_SpecialWingGroup_Coop";
	private  static final Float DetectTargetCore_Damage_Add_Percent = 25f;

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
		text = tooltip.beginImageWithText("graphics/hullmods/XXVII_SpecialWingGroup_Coop.png", 32);
		text.addPara(txt("HullMods_XXVII_SpecialWingGroup_Above"), 0, Misc.getPositiveHighlightColor(),txt("HullMods_XXVII_SpecialWingGroup_Coop"));
		tooltip.addImageWithText(pad);
/*
		tooltip.addSectionHeading("安装相应插件进行强化", Alignment.MID, 5f);
		if (ship.getVariant().hasHullMod("dedicated_targeting_core")) {
			text = tooltip.beginImageWithText("graphics/hullmods/targeting_core.png", 32);
			text.addPara("母舰改装-舰载索敌系统", new Color(59, 123, 220, 255), 4f);
			text.addPara("[%s]", 0, Misc.getPositiveHighlightColor(), "已安装");
			text.addPara("母舰辅助定位目标要害，从而增加{%s}舰载机武器伤害效益", 0, Misc.getHighlightColor(),  DetectTargetCore_Damage_Add_Percent+"%");
			tooltip.addImageWithText(pad);
		} else {
			text = tooltip.beginImageWithText("graphics/hullmods/targeting_core.png", 32);
			text.addPara("母舰改装-舰载索敌系统", new Color(59, 123, 220, 255), 4f);
			text.addPara("[%s]", 0, Misc.getNegativeHighlightColor(), "未安装");
			text.addPara("母舰辅助定位目标要害，从而增加{%s}舰载机武器伤害效益", 0, Misc.getHighlightColor(),  DetectTargetCore_Damage_Add_Percent+"%");
			tooltip.addImageWithText(pad);
		}

 */



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
				//插件可选升级改动
				/*
				if (ship.getVariant().hasHullMod("dedicated_targeting_core")) {
					fighter.getMutableStats().getBallisticWeaponDamageMult().modifyPercent(id, DetectTargetCore_Damage_Add_Percent);
					fighter.getMutableStats().getEnergyWeaponDamageMult().modifyPercent(id, DetectTargetCore_Damage_Add_Percent);
					fighter.getMutableStats().getMissileWeaponDamageMult().modifyPercent(id, DetectTargetCore_Damage_Add_Percent);
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




