package data.shipsystems.scripts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.FighterLaunchBayAPI;
import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

public class SGB_RepairWings extends BaseShipSystemScript {

	private List<ShipAPI> ftrs = new ArrayList<>();
	public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
		ShipAPI ship = null;
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
		} else {
			return;
		}
		
		/*
		if (effectLevel > 0) {
			final String fightersKey = ship.getId() + "_sgb_repairwings_target";
			List<ShipAPI> fighters = null;
			if (!Global.getCombatEngine().getCustomData().containsKey(fightersKey)) {
				fighters = getFighters(ship);
				Global.getCombatEngine().getCustomData().put(fightersKey, fighters);
			} else {
				return;
			}

			if (fighters == null) { // shouldn't be possible, but still
				fighters = new ArrayList<ShipAPI>();
			}*/

			if (state == State.IN) {
				for (FighterWingAPI wing:ship.getAllWings()){
					ftrs.addAll(wing.getWingMembers());
				}
			}

			if (effectLevel == 1) {
				for (FighterLaunchBayAPI bay : ship.getLaunchBaysCopy()) {
					if (bay.getWing() == null) {
						continue;
					}
					bay.makeCurrentIntervalFast();
				}
						/*if (fighter.getWing() != null && fighter.getWing().getSource() != null) {
							//fighter.getWing().getSource().makeCurrentIntervalFast();
							fighter.getWing().getSourceShip().getMutableStats().getMaxCombatHullRepairFraction().modifyFlat(
									id,
									1750f/Math.max(1750f,ship.getHitpoints()));}*/
				for(ShipAPI ftr:ftrs){
						ftr.getMutableStats().getHullCombatRepairRatePercentPerSecond().modifyFlat(id,100f);
				}

			}

	}
	
	public static List<ShipAPI> getFighters(ShipAPI carrier) {
		List<ShipAPI> result = new ArrayList<ShipAPI>();
		
		for (ShipAPI ship : Global.getCombatEngine().getShips()) {
			if (!ship.isFighter()) continue;
			if (ship.getWing() == null) continue;
			if (ship.getWing().getSourceShip() == carrier) {
				result.add(ship);
			}
		}
		
		return result;
	}
	
	
	public void unapply(MutableShipStatsAPI stats, String id) {
		ShipAPI ship = null;
		if (stats.getEntity() instanceof ShipAPI) {
			ship = (ShipAPI) stats.getEntity();
		} else {
			return;
		}

		for (FighterLaunchBayAPI bay : ship.getLaunchBaysCopy()) {
			if (bay.getWing() == null) {
				continue;}
			for(ShipAPI ftr:ftrs){
				ftr.getMutableStats().getHullCombatRepairRatePercentPerSecond().unmodify();
			}
		}
		//final String fightersKey = ship.getId() + "_sgb_repairwings_target";
		//Global.getCombatEngine().getCustomData().remove(fightersKey);
		ftrs.clear();
	}
	
	
	public StatusData getStatusData(int index, State state, float effectLevel) {
		return null;
	}

	
}








