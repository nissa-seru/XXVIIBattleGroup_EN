package data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import data.scripts.util.MagicLensFlare;
import data.scripts.util.MagicRender;
import data.utils.sgb.SGB_Color;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

import static com.fs.starfarer.api.combat.CombatEngineLayers.ABOVE_SHIPS_AND_MISSILES_LAYER;

public class SGB_Quench implements MissileAIPlugin, GuidedMissileAI {
    private static CombatEngineAPI engine;
    private final MissileAPI missile;
    private final IntervalUtil intervalUtil = new IntervalUtil(0.5f, 0.5f);
    private static final IntervalUtil intervalUtil2 = new IntervalUtil(0f, 2f);
    private float thickness = 30;
    private float length = 800;

    public SGB_Quench(MissileAPI missile) {
        this.missile = missile;
    }

    @Override
    public void advance(float amount) {
        if (engine != Global.getCombatEngine()) {
            engine = Global.getCombatEngine();
        }
        //cancelling IF: skip the AI if the game is paused, the missile is engineless or fading
        if (Global.getCombatEngine().isPaused()) {
            return;
        }
        thickness = Math.max(thickness - 1f ,10);
        length = Math.max(length - 5f ,100);
        Vector2f nv1 = new Vector2f(150,20);
        SpriteAPI l = Global.getSettings().getSprite("campaignEntities","fusion_lamp_glow");
        MagicRender.singleframe(l,missile.getLocation(),nv1,0, SGB_Color.SGBpurple,true,ABOVE_SHIPS_AND_MISSILES_LAYER);
        MagicLensFlare.createSharpFlare(engine, missile.getSource(), missile.getLocation(), thickness, length, 0, SGB_Color.SGBcoreWord, SGB_Color.SGBhardWord);
        missile.giveCommand(ShipCommand.ACCELERATE);
        intervalUtil.advance(amount);
        if (intervalUtil.intervalElapsed()) {
            //mirv(missile);
            spawnShip(missile.getSource(), missile);

        }
    }

    private static void spawnShip(ShipAPI source, MissileAPI missile) {
        CombatFleetManagerAPI manager = engine.getFleetManager(source.getOwner());
        float amount = 0;
        boolean orig = manager.isSuppressDeploymentMessages();
        manager.setSuppressDeploymentMessages(true);
        Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+15, null);
        Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-15, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+18, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-18, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+21, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-21, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+27, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-27, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+30, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-30, null);
        Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+32, null);
        Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-32, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()+36, null);
        //Global.getCombatEngine().spawnProjectile(missile.getSource(), missile.getWeapon(), "SGB_Quench_Rocket_Onec", missile.getLocation(), missile.getFacing()-36, null);
        ShipAPI newShip1 = manager.spawnShipOrWing("SGB_Quench_Anvil_wing", missile.getLocation(), missile.getFacing()+40f,1.8f);
        ShipAPI newShip2 = manager.spawnShipOrWing("SGB_Quench_Anvil_wing", missile.getLocation(), missile.getFacing()-40f,1.8f);
        //ShipAPI newShip3 = manager.spawnShipOrWing("SGB_Quench_Assault_wing", missile.getLocation(), missile.getFacing()+0f,2f);
        Global.getCombatEngine().removeEntity(missile);
        manager.setSuppressDeploymentMessages(orig);
        Global.getCombatEngine().spawnExplosion(missile.getLocation(), new Vector2f(0, 0), Color.darkGray, 200f, 2f);
        if(newShip1.isAlive()) {
            intervalUtil2.advance(amount);
            if (intervalUtil2.intervalElapsed()) {
                newShip1.setJitter(newShip1, SGB_Color.SGBcoreWord, 1f, 25, 0, 0);
                newShip1.addAfterimage(SGB_Color.SGBcoreWord, 0f, 0f, -newShip1.getVelocity().x, -newShip1.getVelocity().y, 0f, 0f, 0f, 1f, true, false, false);
            }
        }
        if(newShip2.isAlive()) {
            intervalUtil2.advance(amount);
            if (intervalUtil2.intervalElapsed()) {
                newShip2.setJitter(newShip2, SGB_Color.SGBcoreWord, 1f, 25, 0, 0);
                newShip2.addAfterimage(SGB_Color.SGBcoreWord, 0f, 0f, -newShip2.getVelocity().x, -newShip2.getVelocity().y, 0f, 0f, 0f, 1f, true, false, false);
            }
        }
        /*
        if(newShip3.isAlive()) {
            intervalUtil2.advance(amount);
            if (intervalUtil2.intervalElapsed()) {
                newShip2.setJitter(newShip2, SGB_Color.SGBcoreWord, 1f, 25, 0, 0);
                newShip2.addAfterimage(SGB_Color.SGBcoreWord, 0f, 0f, -newShip2.getVelocity().x, -newShip2.getVelocity().y, 0f, 0f, 0f, 1f, true, false, false);
            }
        }*/
    }

    @Override
    public CombatEntityAPI getTarget() {
        return null;
    }

    @Override
    public void setTarget(CombatEntityAPI target) {
        //this.target = target;

    }

}
