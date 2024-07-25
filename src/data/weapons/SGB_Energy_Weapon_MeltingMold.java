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

public class SGB_Energy_Weapon_MeltingMold implements OnHitEffectPlugin {
	public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target,
					  Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {

		if(target instanceof MissileAPI || target instanceof AsteroidAPI)return;
		Vector2f Speedo =projectile.getVelocity();
		Vector2f Speedo2 = new Vector2f(Speedo);
		Speedo2.setX(Speedo.getX()/8);
		Speedo2.setY(Speedo.getY()/8);
		float Angle =projectile.getFacing();

		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_firestreak_1"),
				point,
				Speedo2,
				new Vector2f(102,304),
				new Vector2f(12,36),
				//angle,
				Angle+2f*(float)Math.random()+90f,
				0,
				new Color(116, 144, 236,255),
				true,
				0.1f,
				0.15f,
				0.2f
		);

		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_Spark01A"),
				point,
				Speedo2,
				new Vector2f(64,96),
				new Vector2f(202,604),
				//angle,
				Angle+2f*(float)Math.random()+90f,
				0,
				new Color(173, 137, 236, 255),
				true,
				0,
				0.1f,
				0.15f
		);

		MagicRender.battlespace(
				Global.getSettings().getSprite("fx","SGB_smoke_2"),
				point,
				Speedo2,
				new Vector2f(132,132),
				new Vector2f(404,404),
				//angle,
				Angle+2*(float)Math.random(),
				0,
				new Color(150, 165, 185, 255),
				true,
				0.1f,
				0.25f,
				0.5f
		);

		if (!shieldHit && target instanceof ShipAPI) {

			float FluxAddingNub = MathUtils.getRandomNumberInRange(500, 2000);

			MagicRender.battlespace(
					Global.getSettings().getSprite("fx","SGB_firestreak_1"),
					point,
					Speedo2,
					new Vector2f(32,32),
					new Vector2f(102,102),
					//angle,
					Angle+2*(float)Math.random(),
					0,
					new Color(150, 165, 185, 255),
					true,
					0.2f,
					0.0f,
					0.4f
			);
			MagicRender.battlespace(
					Global.getSettings().getSprite("fx","SGB_Spark17A"),
					point,
					Speedo2,
					new Vector2f(32,32),
					new Vector2f(102,102),
					//angle,
					Angle+2*(float)Math.random(),
					0,
					new Color(234, 219, 138, 255),
					true,
					0.1f,
					0.0f,
					0.25f
			);

			engine.addSmoothParticle(point, new Vector2f(), 100, 1f, 0.1f, Color.orange);
			if(projectile.getWeapon().getId().equals("SGB_MeltingMold_Launcher_fighter")){
				FluxAddingNub = FluxAddingNub*1.5f;
			}
			((ShipAPI) target).getFluxTracker().increaseFlux(FluxAddingNub,true);
			engine.addFloatingDamageText(point,FluxAddingNub,Color.cyan,target,target);
		}

		if(MagicRender.screenCheck(0.1f, point)){
			for (float i = 0; i <= 5; i++) {
				float particleSize = MathUtils.getRandomNumberInRange(1, 5);
				engine.addSmoothParticle(point, new Vector2f(), particleSize * 10, 2f, 0.1f, new Color(109,126,212,200) );
			}


		}
	}
}
