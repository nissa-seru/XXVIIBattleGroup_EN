package data.weapons;

import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import data.scripts.util.MagicRender;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.WeakHashMap;

public class SGB_Repair_IonEffect implements OnHitEffectPlugin {
	// No Need To Look. This thing isn't finish yet
	private float maxRadius = 2f;
	private float innerRadius = 1f;
	private float repairRate = 1f;

	private static final float ARMOR_HEAL_PER_SHOT = 100f;
	private static final float HULL_HEAL_PER_SHOT = 100f;
	private static final float RANGE_FACTOR = 800f;
	private static final Random REPAIR_ZONE = new Random();

	private Map<Point, Float> cellMap;

	private void Repairing(ShipAPI ship) {
		ArmorGridAPI armorGrid = ship.getArmorGrid();
		float maxZone = armorGrid.getMaxArmorInCell();
		int gridWidth = armorGrid.getGrid().length;
		int gridHeight = armorGrid.getGrid()[0].length;
		java.awt.Point cellToFix = new java.awt.Point(REPAIR_ZONE.nextInt(gridWidth), REPAIR_ZONE.nextInt(gridHeight));

		ship.setHitpoints(Math.min(ship.getMaxHitpoints(), ship.getHitpoints() + HULL_HEAL_PER_SHOT ));

		for (int x = cellToFix.x - 1; x <= cellToFix.x + 1; ++x) {
			if (x < 0 || x >= gridWidth) {
				continue;
			}
			for (int y = cellToFix.y - 1; y <= cellToFix.y + 1; ++y) {
				if (y < 0 || y >= gridHeight) {
					continue;
				}

				float mult = (3 - Math.abs(x - cellToFix.x) - Math.abs(y - cellToFix.y)) / 3f;
				armorGrid.setArmorValue(x, y, Math.min(maxZone, armorGrid.getArmorValue(x, y) + ARMOR_HEAL_PER_SHOT * mult));
			}
		}
	}

	@Override
	public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
/*
		//MagicLensFlare.createSharpFlare(engine, projectile.getSource(), projectile.getLocation(), 10, 400, 0, IIRT_Omega_Color.IIRT_Omega_Bright, IIRT_Omega_Color.IIRT_Omega_Lab_Word);

		if(MagicRender.screenCheck(0.1f, point)){
			for (float i = 0; i <= 5; i++) {
				float particleSize = MathUtils.getRandomNumberInRange(PARTICLE_SIZE-2, PARTICLE_SIZE+2);
				Vector2f randSpawnPoint = MathUtils.getRandomPointOnCircumference(point, EXPLOSION_SIZE);
				Vector2f randExitVector = VectorUtils.getDirectionalVector(point, randSpawnPoint);
				randExitVector.scale(EXPLOSION_SIZE*2);
				engine.addHitParticle(randSpawnPoint, randExitVector, particleSize, PARTICLE_BRIGHTNESS, PARTICLE_DURATION, PARTICLE_COLOR);
			}

			//void spawnExplosion(Vector2f loc, Vector2f vel, Color color, float size, float maxDuration);
			engine.spawnExplosion(point, new Vector2f(), EXPLOSION_COLOR, EXPLOSION_SIZE + (float)Math.random()*5, 0.5f);
			engine.spawnExplosion(point, new Vector2f(), FLASH_COLOR, FLASH_SIZE + (float)Math.random()*5, 0.25f);
			engine.addHitParticle(point, new Vector2f(), GLOW_SIZE + (float)Math.random()*5, 1, 0.05f, IIRT_Omega_Color.IIRT_Omega_Lab_Weapon);
		}*/
	}
			//setHealStats(beam.getWeapon().getId());
			//buildCellMap(beam);

	/*private void buildCellMap(BeamAPI beam) {
		cellMap = new WeakHashMap<>();
		ArmorGridAPI grid = beam.getSource().getArmorGrid();
		int[] center = grid.getCellAtLocation(beam.getWeapon().getLocation());
		if (center == null || center.length <= 0) {
			return;
		}

		Vector2f relLoc = new Vector2f(center[0] * grid.getCellSize(), center[1] * grid.getCellSize());

		for (int x = 0; x < grid.getGrid().length; ++x) {
			for (int y = 0; y < grid.getGrid()[0].length; ++y) {
				Vector2f loc = new Vector2f(x * grid.getCellSize(), y * grid.getCellSize());
				float dist = MathUtils.getDistance(relLoc, loc);

				if (dist > maxRadius) {
					continue;
				}
				float effect = (dist <= innerRadius) ? 1f : 1f - (dist - innerRadius) / (maxRadius - innerRadius);
				cellMap.put(new Point(x, y), effect);
			}
		}
	}*/
}