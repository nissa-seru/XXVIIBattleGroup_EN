package data.weapons;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.AsteroidAPI;
import com.fs.starfarer.api.combat.*;
import data.scripts.util.MagicRender;
import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;

public class SGB_Austenite_Weapon_GunHit implements OnHitEffectPlugin {


	public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target,
					  Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {

		if(target instanceof MissileAPI || target instanceof AsteroidAPI)return;
		Vector2f Speedo =projectile.getVelocity();
		Vector2f Speedo2 = new Vector2f(Speedo);
		Speedo2.setX(Speedo.getX()/8);;
		Speedo2.setY(Speedo.getY()/8);
		float Angle =projectile.getFacing();
		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_Spark01A"),
				point,
				Speedo2,
				new Vector2f(32,64),
				new Vector2f(64,96),
				//angle,
				Angle+2f*(float)Math.random()+90f,
				0,
				new Color(239, 227, 186,255),
				true,
				0,
				0.1f,
				0.25f
		);
		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_Spark01A"),
				point,
				Speedo2,
				new Vector2f(32,64),
				new Vector2f(64,96),
				//angle,
				Angle+2*(float)Math.random()+90f,
				0,
				Color.white,
				true,
				0.2f,
				0.0f,
				0.3f
		);

		engine.addHitParticle(point, new Vector2f(), 50, 2f, 0.25f, Color.white);
		engine.addSmoothParticle(point, new Vector2f(), 60, 2f, 0.1f, Color.white);
		engine.spawnExplosion(point, new Vector2f(), new Color(255, 222, 202), 5 + (float)Math.random()*5, 0.25f);
	}
}
