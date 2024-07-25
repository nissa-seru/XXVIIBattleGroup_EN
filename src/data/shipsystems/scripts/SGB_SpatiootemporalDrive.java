package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipEngineControllerAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import data.scripts.util.MagicAnim;
import data.scripts.util.MagicUI;
import data.utils.sgb.SGB_Color;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.List;

import static data.utils.sgb.SGB_stringsManager.txt;

public class SGB_SpatiootemporalDrive extends BaseShipSystemScript {

	public static float SPEED_BONUS = 275f;
	public static float TURN_BONUS = 25f;
	
	private Color color = new Color(187, 60, 49,255);
	private Color UIcolor = new Color(187, 155, 49,255);
	private Color UILcolor = new Color(79, 187, 49,255);
	public static final float PLAYER_TIME_MULT = 300f;

	@Override
	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
		//引入ShipAPI & CombatEngineAPI
		ShipAPI ship = (ShipAPI) stats.getEntity();
		CombatEngineAPI engine = Global.getCombatEngine();
		//Global.getSoundPlayer().playSound("SGB_Downer_time", 1, 1, ship.getLocation(), ship.getVelocity());
		float effect = Math.min(
				1,
				Math.max(0, MagicAnim.smoothReturnNormalizeRange(
						effectLevel,
						0,
						1)/2 + MagicAnim.smoothReturnNormalizeRange(
						effectLevel*1.5f, 0, 1)/2 +
						MagicAnim.smoothReturnNormalizeRange(effectLevel*2, 0, 1)/2));

		if(ship!=null && engine!=null){
			if(Math.random()>0.9f){
				//拖影效果
				//ship.addAfterimage(SGB_Color.SGBpurple, 0f, 0f, -ship.getVelocity().x, -ship.getVelocity().y, 0f, 0f, 0f, 1f, true, false, false);
				ship.setJitter(ship, SGB_Color.SGBcoreDanger_Word, 1f, 25, 0, 0);
				//引擎电弧
				List<ShipEngineControllerAPI.ShipEngineAPI> engines = ship.getEngineController().getShipEngines();
				for (ShipEngineControllerAPI.ShipEngineAPI enginea : engines) {
					float EmpThickness = MathUtils.getRandomNumberInRange(10f,30f);
					Vector2f to = MathUtils.getRandomPointOnCircumference(enginea.getLocation(),90f);
					if(Math.random()>0.99f) {
						engine.spawnEmpArcVisual(enginea.getLocation(), ship, to, ship, EmpThickness, SGB_Color.SGBcoreDanger_Word, SGB_Color.SGBpurple);
						//engine.addHitParticle(ship.getLocation(), ship.getLocation(), 50f, 0.5f, 0.25f, SGB_Color.SGBcoreDanger_Word);

						if(!stats.getTimeMult().getPercentMods().containsKey(id)){
							ship.setPhased(true);
						} else if(ship.isPhased()){
							ship.setPhased(false);
						}
					}
				}
			}
		}

			stats.getTurnAcceleration().modifyFlat(id, 300f);
			stats.getMaxTurnRate().modifyFlat(id, 1000f);

			stats.getMaxSpeed().modifyPercent(id, 300 * effect);
			stats.getAcceleration().modifyPercent(id, 250);
			stats.getDeceleration().modifyPercent(id, 350);

			stats.getPeakCRDuration().modifyPercent(id, 1000 * effect);

		if (stats.getEntity() instanceof ShipAPI) {
			if(ship!=null && engine!=null){
				ship.getEngineController().fadeToOtherColor(this, color, new Color(0,0,0,0), effectLevel, 0.67f);
				ship.getEngineController().extendFlame(this, 2f * effectLevel, 0f * effectLevel, 0f * effectLevel);
			}
		}


		effectLevel *= effectLevel;
		float TIME_MULT = 1000f;
		float shipTimeMult = 1f + (TIME_MULT - 1f) * effectLevel;
		float shipTimeMultNF = shipTimeMult;
		stats.getTimeMult().modifyPercent(id, shipTimeMult);
		//MagicUI.drawSystemBar(ship, new Color(255, 196, 0), shieldTime/MAX_SHIELD_TIME, 0);

		//Percent
		float TimeMultForTime_F=MagicAnim.smoothReturnNormalizeRange(
				shipTimeMult,
				0,
				950);
		boolean player = false;

		// Are you the player?
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
			player = ship == Global.getCombatEngine().getPlayerShip();
			id = id + "_" + ship.getId();
		} else {
			return;
		}
		// So I might give ya more time
		if (player) {
			Global.getCombatEngine().getTimeMult().modifyMult(id, Math.max(0.1f,1f / shipTimeMult));
				//1f / shipTimeMult * effectLevel
				//Global.getCombatEngine().getTimeMult().modifyPercent(id, Math.max(0.001f,Math.min(1f,1f-0.9f*TimeMultForTime_F)));
			//else{Global.getCombatEngine().getTimeMult().unmodify(id);}
			if(ship!=null) {
				MagicUI.drawHUDStatusBar(ship, Math.min(1f, Math.max(0, 1f - shipTimeMult / 1000f)), UIcolor, UILcolor,
						Math.min(1f, Math.max(0, shipTimeMultNF / 1000f)),
						txt("Austenite_System_temporalDrive_1"),
						txt("Austenite_System_temporalDrive_2"),
						false);
			}
		} else {
			Global.getCombatEngine().getTimeMult().unmodify(id);
		}
	}

	@Override
	public void unapply(MutableShipStatsAPI stats, String id) {
		stats.getMaxSpeed().unmodify(id);
		stats.getMaxTurnRate().unmodify(id);
		stats.getTurnAcceleration().unmodify(id);
		stats.getAcceleration().unmodify(id);
		stats.getDeceleration().unmodify(id);
		stats.getTimeMult().unmodify(id);
		stats.getPeakCRDuration().unmodify(id);

		ShipAPI ship = null;
		boolean player = false;
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
			player = ship == Global.getCombatEngine().getPlayerShip();
			id = id + "_" + ship.getId();
		} else {
			return;
		}

		Global.getCombatEngine().getTimeMult().unmodify(id);

		//ShipAPI ship = null;
		//boolean player = false;
		//if (stats.getEntity() instanceof ShipAPI) {
		//	ship = (ShipAPI) stats.getEntity();
		//	player = ship == Global.getCombatEngine().getPlayerShip();
		//	id = id + "_" + ship.getId();
		//	Global.getCombatEngine().getTimeMult().unmodify(id);
		//} else {
		//	return;
		//}

	}

	@Override
	public StatusData getStatusData(int index, State state, float effectLevel) {
		if (index == 0) {
			return new StatusData(txt("Austenite_System_temporalDrive_3"), true);
		} else if (index == 1) {
			return new StatusData(txt("Austenite_System_temporalDrive_4"), false);
		}
		return null;
	}
}
