package data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import data.scripts.util.MagicRender;
import java.awt.Color;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

public class SGB_Austenite_Weapon_Gun_Fire implements EveryFrameWeaponEffectPlugin {
        
    private boolean firing=false;
    private boolean needReload=false;
    private float  recoil=0;


    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        
        if(engine.isPaused() || weapon.getShip().getOriginalOwner()==-1){return;}
        if(weapon.getChargeLevel()==1) {

            firing = true;
            //Vector2f muzzle;

            //recoil
            recoil = Math.min(1, recoil + 0.33f);

            //muzzle non hidden weapon
            if (MagicRender.screenCheck(0.1f, weapon.getLocation())) {

                for (DamagingProjectileAPI p : CombatUtils.getProjectilesWithinRange(weapon.getLocation(), 150)) {
                    if (p.getWeapon() != weapon) continue;
                    engine.addHitParticle(
                            p.getLocation(),
                            weapon.getShip().getVelocity(),
                            75f,
                            0.25f,
                            0.33f,
                            new Color(200, 96, 0, 32));
                }
                //muzzle = MathUtils.getPoint(weapon.getLocation(), 40 - (recoil), weapon.getCurrAngle());
                /*
                engine.addHitParticle(
                        muzzle,
                        weapon.getShip().getVelocity(),
                        30,
                        0.5f,
                        1,
                        Color.green
                );
                engine.addHitParticle(
                        muzzle,
                        weapon.getShip().getVelocity(),
                        30,
                        0.5f,
                        0.3f,
                        Color.red
                );*/
            }
        }
        if(firing && weapon.getChargeLevel()<1) {
            needReload = true;
            firing = false;
        }
        if(needReload){
            Vector2f muzzle;
            muzzle = MathUtils.getPoint(weapon.getLocation(), 40 - (recoil), weapon.getCurrAngle());
            Global.getSoundPlayer().playSound("SGB_Assault_Gun_reload_Only", 1, 1, weapon.getLocation(), weapon.getShip().getVelocity());
            engine.addHitParticle(
                    muzzle,
                    weapon.getShip().getVelocity(),
                    5,
                    0.05f,
                    1,
                    new Color(239, 189, 139)
            );

            needReload=false;
        }
    }
}