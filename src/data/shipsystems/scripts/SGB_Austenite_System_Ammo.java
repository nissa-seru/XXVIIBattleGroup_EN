package data.shipsystems.scripts;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_System_Ammo extends BaseShipSystemScript {

	public static final float ROFN_PERCENT = 75f;
	public static final float REGEN_PERCENT = 50f;
	public static final float FLUX_REDUCTION = 20f;
	
	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
		stats.getBallisticRoFMult().modifyPercent(id, ROFN_PERCENT);
		stats.getBallisticAmmoRegenMult().modifyPercent(id, REGEN_PERCENT);
		stats.getBallisticAmmoRegenMult().modifyPercent(id, REGEN_PERCENT);
		stats.getMissileAmmoRegenMult().modifyPercent(id, REGEN_PERCENT);
		stats.getBallisticWeaponFluxCostMod().modifyPercent(id, -FLUX_REDUCTION);

	}
	public void unapply(MutableShipStatsAPI stats, String id) {
		stats.getBallisticRoFMult().unmodify(id);
		stats.getBallisticAmmoRegenMult().unmodify(id);
		stats.getMissileAmmoRegenMult().unmodify(id);
		stats.getBallisticWeaponFluxCostMod().unmodify(id);
	}
	
	public StatusData getStatusData(int index, State state, float effectLevel) {
		if (index == 0) {
			return new StatusData(txt("Austenite_System_ammo_1") + (int) ROFN_PERCENT + "%", false);
		}
		if (index == 1) {
			return new StatusData(txt("Austenite_System_ammo_2")+ (int) REGEN_PERCENT + "%", false);
		}
		if (index == 2) {
			return new StatusData(txt("Austenite_System_ammo_3")+ (int) FLUX_REDUCTION + "%", false);
		}
		return null;
	}
}
