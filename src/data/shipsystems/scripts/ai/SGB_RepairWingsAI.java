//
// Source code recreated from a .class file by IntelliJ IDEA
// By Siren(or we should say ajimu)
//

package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lwjgl.util.vector.Vector2f;

public class SGB_RepairWingsAI implements ShipSystemAIScript {
    private CombatEngineAPI engine;
    private ShipSystemAPI system;
    private ShipAPI ship;
    private final IntervalUtil tracker = new IntervalUtil(0.2F, 0.3F);

    public SGB_RepairWingsAI() {
    }

    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {
        //boolean NeedRepair = false;
        if(engine.isPaused()) return;
        if(system.isOn()||system.getCooldownRemaining()>0) return;
        if(system.getAmmo()<=0) return;
        this.tracker.advance(amount);
            if (this.tracker.intervalElapsed()) {

                float replacementTimeTotal = 0f;
                float replacementTimeTotalMax = 0f;
                float replacementTimeBaysHasLost = 0f;
                float replacementTimeBaysMax = 0f;
                for(FighterLaunchBayAPI bay:ship.getLaunchBaysCopy()){
                    if(bay.getWing()!=null){
                        if(bay.getNumLost()>0){
                            replacementTimeTotal+=(bay.getNumLost()*
                                    ship.getMutableStats().getFighterRefitTimeMult().getModifiedValue()*
                                    bay.getWing().getSpec().getRefitTime());
                            if(replacementTimeTotalMax<replacementTimeTotal) {
                                replacementTimeTotalMax=replacementTimeTotal;}
                            replacementTimeBaysHasLost=ship.getMutableStats().getFighterRefitTimeMult().getModifiedValue()*bay.getWing().getSpec().getRefitTime();
                            if(replacementTimeBaysMax<replacementTimeBaysHasLost) {
                                replacementTimeBaysMax=replacementTimeBaysHasLost;}
                        }
                    }
                }
                if(replacementTimeBaysHasLost<=0) return;
                float frac = replacementTimeTotal/replacementTimeBaysHasLost;
                float fracm = replacementTimeTotalMax/replacementTimeBaysMax;
                //if(frac>0.8f && fracm>=1f*ship.getLaunchBaysCopy().size() && replacementTimeTotalMax >= 12f && replacementTimeBaysMax != 0f){
                if(frac>0.8f && fracm>=0.75f*ship.getLaunchBaysCopy().size() && replacementTimeTotalMax >= 18f && replacementTimeBaysMax != 0f){
                    ship.useSystem();
                    replacementTimeTotal = 0;
                    replacementTimeTotalMax = 0;
                    replacementTimeBaysHasLost = 0;
                    replacementTimeBaysMax = 0;
                }
            }
                /*
                if (this.ship.getWing().isDestroyed()) {
                    if (!AIUtils.canUseSystemThisFrame(this.ship)) {
                        return;
                    }

                    boolean pointedAtTarget = false;
                    if (target == null) {
                        target = this.ship.getShipTarget();
                    }

                    if (target == null && this.ship.getWing() != null && this.ship.getWing().getLeader() != null) {
                        target = this.ship.getWing().getLeader().getShipTarget();
                    }

                    if (target != null) {
                        this.ship.useSystem();
                    }
                }*/

    }

    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, CombatEngineAPI engine) {
        this.ship = ship;
        this.engine = engine;
        this.system = system;
    }
}
