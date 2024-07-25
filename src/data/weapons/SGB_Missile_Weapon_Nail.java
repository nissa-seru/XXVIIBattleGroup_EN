package data.weapons;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.AsteroidAPI;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.loading.DamagingExplosionSpec;
import data.scripts.util.MagicRender;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;

public class SGB_Missile_Weapon_Nail implements OnHitEffectPlugin {

	private final Color PARTICLE_COLOR = new Color(213, 218, 217);
	private final float PARTICLE_SIZE = 3f;
	private final float PARTICLE_BRIGHTNESS = 1f;
	private final float PARTICLE_DURATION = 0.75f;

	private final float EXPLOSION_SIZE = 10f;

	public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target,
					  Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {

		if(target instanceof MissileAPI || target instanceof AsteroidAPI)return;
		Vector2f Speedo =projectile.getVelocity();
		Vector2f Speedo2 = Speedo;
		Speedo2.setX(Speedo.getX()/8);
		Speedo2.setY(Speedo.getY()/8);
		Vector2f Speedo3 = Speedo2;
		Speedo3.setX(Speedo.getX()/4);
		Speedo3.setY(Speedo.getY()/4);
		float Angle =projectile.getFacing();

		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_Spark01A"),
				point,
				Speedo3,
				new Vector2f(16,64),
				new Vector2f(202,304),
				//angle,
				Angle+2f*(float)Math.random()+90f,
				0,
				new Color(139, 213, 224,255),
				true,
				0,
				0.1f,
				0.25f
		);

		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_Spark01B"),
				point,
				Speedo2,
				new Vector2f(64,96),
				new Vector2f(202,404),
				//angle,
				Angle+2f*(float)Math.random()+90f,
				0,
				new Color(236, 137, 137, 255),
				true,
				0,
				0.1f,
				0.15f
		);

		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_smoke_2"),
				point,
				Speedo2,
				new Vector2f(32,32),
				new Vector2f(454,454),
				//angle,
				Angle+2*(float)Math.random(),
				0,
				Color.white,
				true,
				0.1f,
				0.0f,
				0.25f
		);

		DamagingExplosionSpec boom = new DamagingExplosionSpec(
				0.1f,
				60,
				50,
				projectile.getDamageAmount(),
				50,
				CollisionClass.PROJECTILE_NO_FF,
				CollisionClass.PROJECTILE_FIGHTER,
				2,
				5,
				5,
				25,
				new Color(218, 189, 174),
				new Color(178, 122, 101)
		);
		boom.setDamageType(DamageType.FRAGMENTATION);
		boom.setShowGraphic(false);
		boom.setSoundSetId("explosion_flak");

		float RandomNum = (float)Math.random();
		if (RandomNum > 0.75f && !shieldHit && target instanceof ShipAPI) {
			engine.spawnDamagingExplosion(boom, projectile.getSource(), point);

			Float HitPoints = target.getHitpoints();
			Float DamageToHull = 200f * RandomNum;
			if (HitPoints > 200) {
				target.setHitpoints(HitPoints - DamageToHull);
			}
			Vector2f EnemyPlaced = target.getLocation();
			Vector2f PrjtPlaced = point;
			Vector2f NowPlace = point;
			float HalfPlacex = (EnemyPlaced.getX()+PrjtPlaced.getX())/2;
			float HalfPlacey = (EnemyPlaced.getY()+PrjtPlaced.getY())/2;
			NowPlace.setX(HalfPlacex);
			NowPlace.setY(HalfPlacey);
			engine.addFloatingDamageText(NowPlace,DamageToHull,Color.red,target,target);

			MagicRender.battlespace(
					Global.getSettings().getSprite("fx","SGB_firestreak_1"),
					NowPlace,
					Speedo2,
					new Vector2f(32,32),
					new Vector2f(482,482),
					//angle,
					Angle+2*(float)Math.random(),
					0,
					Color.white,
					true,
					0.1f,
					0.0f,
					0.25f
			);
			engine.addSmoothParticle(point, new Vector2f(), 100, 1f, 0.1f, Color.orange);
		}

		if(MagicRender.screenCheck(0.1f, point)){
			for (float i = 0; i <= 5; i++) {
				float particleSize = MathUtils.getRandomNumberInRange(PARTICLE_SIZE-2, PARTICLE_SIZE+2);
				Vector2f randSpawnPoint = MathUtils.getRandomPointOnCircumference(point, EXPLOSION_SIZE);
				Vector2f randExitVector = VectorUtils.getDirectionalVector(point, randSpawnPoint);
				randExitVector.scale(EXPLOSION_SIZE*2);
				engine.addHitParticle(randSpawnPoint, randExitVector, particleSize, PARTICLE_BRIGHTNESS, PARTICLE_DURATION, PARTICLE_COLOR);
			}

			engine.addSmoothParticle(point, new Vector2f(), 60, 2f, 0.1f, Color.orange);
			engine.spawnExplosion(point, new Vector2f(), new Color(238, 232, 228), 10 + (float)Math.random()*40, 0.25f);
		}
	}
}
