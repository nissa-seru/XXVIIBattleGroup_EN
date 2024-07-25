package data.shipsystems.scripts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import com.fs.starfarer.api.combat.listeners.DamageListener;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.util.Misc;
import data.scripts.util.MagicUI;

import static com.fs.starfarer.api.impl.combat.DamperFieldStats.getDamper;
import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_Austenite_System_Repair extends BaseShipSystemScript {
	public static Color JITTER_COLOR = new Color(255, 210, 100, 50);
	private Color UIcolor = new Color(187, 155, 49,255);
	private Color UILcolor = new Color(79, 187, 49,255);
	public static final Color JITTER_UNDER_COLOR = Misc.setAlpha(JITTER_COLOR, 155);

	private static Map mag = new HashMap();

	static {
		mag.put(HullSize.FIGHTER, 0.15f);
		mag.put(HullSize.FRIGATE, 0.15f);
		mag.put(HullSize.DESTROYER, 0.20f);
		mag.put(HullSize.CRUISER, 0.3f);
		mag.put(HullSize.CAPITAL_SHIP, 0.3f);
	}

	float recentHits = 0f;
	public class SGB_DamageListener implements DamageListener {
		public SGB_Austenite_System_Repair plugin;

		public SGB_DamageListener(SGB_Austenite_System_Repair plugin) {
			this.plugin = plugin;
		}

		public void reportDamageApplied(Object source, CombatEntityAPI target, ApplyDamageResultAPI result) {
			float totalDamage = result.getDamageToHull() + result.getDamageToShields() + result.getTotalDamageToArmor();
			//plugin.recentHits += totalDamage / JITTER_PER_DAMAGE_DIVISOR;
			float max = 1f;
			if (result.isDps()) max = 0.1f;

			float recentHits = 0f;
			recentHits += Math.min(max, totalDamage / 10f);
		}
	}
	protected Object STATUSKEY1 = new Object();


	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
		ShipAPI ship = null;
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
			float oldEL = effectLevel;
			effectLevel = 1f;
			ship.addListener(new SGB_DamageListener(this));

			float jitterLevel = effectLevel;
			if (state == State.OUT) {
				jitterLevel *= jitterLevel;
			}
			float maxRangeBonus = 25f;
			float jitterRangeBonus = jitterLevel * maxRangeBonus;
			if (state == State.OUT) {
			}
			ship.setJitterUnder(this, JITTER_UNDER_COLOR, jitterLevel, 11, 0f, 3f + jitterRangeBonus);
			ship.setJitter(this, JITTER_COLOR, jitterLevel, 4, 0f, 0 + jitterRangeBonus);

			float mult = (Float) mag.get(HullSize.CRUISER);
			if (stats.getVariant() != null) {
				mult = (Float) mag.get(stats.getVariant().getHullSize());
			}
			stats.getHullDamageTakenMult().modifyMult(id, 1f - (1f - mult) * effectLevel);
			stats.getArmorDamageTakenMult().modifyMult(id, 1f - (1f - mult) * effectLevel);
			stats.getEmpDamageTakenMult().modifyMult(id, 1f - (1f - mult) * effectLevel);

			ship = null;
			boolean player = false;
			if (stats.getEntity() instanceof ShipAPI) {
				ship = (ShipAPI) stats.getEntity();
				player = ship == Global.getCombatEngine().getPlayerShip();
			}
			if (player) {
				ShipSystemAPI system = getDamper(ship);
				if (system != null) {
					float percent = (1f - mult) * effectLevel * 100;
					Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY1,
							system.getSpecAPI().getIconSpriteName(), system.getDisplayName(),
							(int) Math.round(percent) + txt("Austenite_System_repair_1"), false);

					MagicUI.drawHUDStatusBar(ship, Math.min(1f,oldEL), UIcolor, UILcolor,
							Math.min(1f,effectLevel),
							txt("Austenite_System_repair_2"),
							txt("Austenite_System_repair_3"),
							false);
				}
			}

			float maxAlpha = 0.67f;
			ship.setAlphaMult(maxAlpha);
			ship.blockCommandForOneFrame(ShipCommand.USE_SYSTEM);
			ship.blockCommandForOneFrame(ShipCommand.TOGGLE_SHIELD_OR_PHASE_CLOAK);


			//float minus = recentHits * amount / JITTER_PER_DAMAGE_DECAY_SECONDS;
			float minus = recentHits * effectLevel * 2f;
			minus += 0.1f * effectLevel;
			recentHits -= minus;
			if (recentHits < 0) recentHits = 0;
			if (recentHits > 5f) recentHits = 5f;

			float HitjitterLevel = 1f;
			float jitterRange = 0.5f;
			float HitmaxRangeBonus = 50f;
			HitmaxRangeBonus = 20f;
			HitmaxRangeBonus += recentHits * 40f;
			float HitjitterRangeBonus = jitterRange * HitmaxRangeBonus;
			Color c = JITTER_COLOR;

			float numCopies = 7f;
			numCopies += recentHits * 2f;

			ship.setJitter(this, c, HitjitterLevel, (int) Math.round(numCopies), 0f, HitjitterRangeBonus);

		}
	}
		public void unapply (MutableShipStatsAPI stats, String id){ShipAPI ship = null;
			if (stats.getEntity() instanceof ShipAPI) {
				ship = (ShipAPI) stats.getEntity();
				ship.setAlphaMult(1f);
			}
			stats.getHullDamageTakenMult().unmodify(id);
			stats.getArmorDamageTakenMult().unmodify(id);
			stats.getEmpDamageTakenMult().unmodify(id);
		}


//	public StatusData getStatusData(int index, State state, float effectLevel) {
//		float mult = (Float) mag.get(HullSize.CRUISER);
//		if (stats.getVariant() != null) {
//			mult = (Float) mag.get(stats.getVariant().getHullSize());
//		}
//		effectLevel = 1f;
//		float percent = (1f - INCOMING_DAMAGE_MULT) * effectLevel * 100;
//		if (index == 0) {
//			return new StatusData((int) percent + "% less damage taken", false);
//		}
//		return null;
//	}
	}