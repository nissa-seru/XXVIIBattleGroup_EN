package data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipEngineControllerAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import data.scripts.util.MagicAnim;
import org.lazywizard.lazylib.MathUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SGB_Austenite_Engine implements EveryFrameWeaponEffectPlugin {
    //String Weapons which might change them self.
    private final Map<Integer,String> LEFT_CORE_WEAPON = new HashMap<>();
    {
        LEFT_CORE_WEAPON.put(0, "SGB_Austenite_Weapon_Rocket_L");
        LEFT_CORE_WEAPON.put(1, "SGB_Austenite_Weapon_Gun_L");
        LEFT_CORE_WEAPON.put(2, "SGB_Austenite_Weapon_Blast_L");
    }
    private final Map<Integer,String> RIGHT_CORE_WEAPON = new HashMap<>();
    {
        RIGHT_CORE_WEAPON.put(0, "SGB_Austenite_Weapon_Rocket_R");
        RIGHT_CORE_WEAPON.put(1, "SGB_Austenite_Weapon_Gun_R");
        RIGHT_CORE_WEAPON.put(2, "SGB_Austenite_Weapon_Blast_R");
    }
    //String IDs.
    public final String leftWingID = "WING_LEFT";
    public final String rightWingID = "WING_RIGHT";
    public final String leftEngineID = "WING_ENGINE_LEFT";
    public final String rightEngineID = "WING_ENGINE_RIGHT";

    public final String leftRocketID = "ROCKET_LEFT";
    public final String rightRocketID = "ROCKET_RIGHT";
    public final String leftMainWeaponID = "WEAPON_LEFT";
    public final String rightMainWeaponID = "WEAPON_RIGHT";

    public final String topID = "TOP";
    public final String headID = "HEAD";
    //Private some API & some boolean to use.
    private WeaponAPI rEngine; private WeaponAPI lEngine;
    private WeaponAPI rWing; private WeaponAPI lWing;
    private WeaponAPI rRocket; private WeaponAPI lRocket;
    private WeaponAPI rWeapon; private WeaponAPI lWeapon;
    private WeaponAPI topHull;
    private ShipAPI ship; private ShipSystemAPI system; private ShipEngineControllerAPI engines;
    private boolean isVenting = false;
    private boolean runOnce=false;
    private float rate=1;
    private float engineWidth, engineHeight, mainWeaponHeight,mainWeaponWidth;

    //Private some math work.
    private float currentRotateL=0, currentRotateR=0;
    private final float maxRotate=22.5f;
    private final float engineOffsetX=2f;
    private final float engineOffsetY=-5f;

    //------------
    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        
        if (Global.getCombatEngine().isPaused()) {
            return;
        }
        
        //initialise the variables
        if (!runOnce || ship==null || system==null){
            ship=weapon.getShip();
            
            system = ship.getSystem();
            engines = ship.getEngineController();
            List <WeaponAPI> weapons = ship.getAllWeapons();
            for (WeaponAPI w : weapons){
                switch(w.getSlot().getId()){
                    case leftMainWeaponID:
                        lWeapon=w;
                        mainWeaponHeight=w.getSprite().getHeight();
                        mainWeaponWidth=w.getSprite().getWidth();
                        break;
                    case rightMainWeaponID:
                        rWeapon=w;
                        break;
                    case leftWingID:
                        lWing=w;
                        break;
                    case rightWingID:
                        rWing=w;
                        break;
                    case leftRocketID:
                        lRocket=w;
                        break;
                    case rightRocketID:
                        rRocket=w;
                        break;
                    case leftEngineID:
                        lEngine=w;
                        engineHeight=w.getSprite().getHeight();
                        engineWidth=w.getSprite().getWidth();
                        break;
                    case rightEngineID:
                        rEngine=w;
                        break;
                    case topID:
                        topHull=w;
                        break;
                }                
            }

            if(lWeapon==null || rWeapon==null){
                return;
            }

            runOnce=true;
            //return to avoid a null error on the ship
            return;
        }
        //------------
        if (!(ship ==null)) {
            float MaxVelo = 0;
            float VeloX = 0;
            float VeloY = 0;
            float rateY = 0;
            float rateX = 0;

            float ltarget = 0;
            float rtarget = 0;
            float ltargetNoTurn = 0;
            float rtargetNoTurn = 0;
            float lmwpX = 0;
            float rmwpX = 0;
            float mwpY = 0;
            float legwX = 0;
            float regwX = 0;
            float egwY = 0;
            MaxVelo = ship.getMaxSpeed();
            VeloX = ship.getVelocity().getX();
            VeloY = ship.getVelocity().getY();
            if (MaxVelo == 0) {
                rateY = 0;
                rateX = 0;
            } else {
                rateY = VeloY / MaxVelo;
                rateX = VeloX / MaxVelo;
            }

            float speedX = MagicAnim.smoothNormalizeRange(rateX, 0f, 0.2f);
            float speedY = MagicAnim.smoothNormalizeRange(rateY, 0.15f, 1f);

            //float slideMove = MagicAnim.smoothNormalizeRange(rate,0f,1f);
            //float recessMove = MagicAnim.smoothNormalizeRange(rate,0f,1f);


            if (engines.isAccelerating()) {
                ltarget -= maxRotate / 2;
                rtarget += maxRotate / 2;
            } else if (engines.isDecelerating() || engines.isAcceleratingBackwards()) {
                ltarget += maxRotate;
                rtarget -= maxRotate;
            }
            ltargetNoTurn = ltarget;
            rtargetNoTurn = rtarget;

            if (engines.isStrafingLeft()) {
                ltarget += maxRotate / 3;
                rtarget += maxRotate / 1.5f;
            } else if (engines.isStrafingRight()) {
                ltarget -= maxRotate / 1.5f;
                rtarget -= maxRotate / 3;
            }
            if (engines.isTurningLeft()) {
                ltarget -= maxRotate / 2;
                rtarget -= maxRotate / 2;
            } else if (engines.isTurningRight()) {
                ltarget += maxRotate / 2;
                rtarget += maxRotate / 2;
            }

            float rtl = MathUtils.getShortestRotation(currentRotateL, ltarget);
            if (Math.abs(rtl) < 0.5f) {
                currentRotateL = ltarget;
            } else if (rtl > 0) {
                currentRotateL += 0.5f;
            } else {
                currentRotateL -= 0.5f;
            }

            float rtr = MathUtils.getShortestRotation(currentRotateR, rtarget);
            if (Math.abs(rtr) < 0.5f) {
                currentRotateR = rtarget;
            } else if (rtr > 0) {
                currentRotateR += 0.5f;
            } else {
                currentRotateR -= 0.5f;
            }

            float FACING = ship.getFacing();

            //if(ship.getFluxTracker().isVenting()){
            //    rate = Math.min(1,rate+amount);
            //    system.setCooldownRemaining();
            //}

            //Main weapons
            lmwpX = mainWeaponWidth / 2 + engineOffsetX * speedX;
            rmwpX = mainWeaponWidth / 2 - engineOffsetX * speedX;
            mwpY = mainWeaponHeight / 2 + engineOffsetY * speedY;
            //Engine wings
            legwX = engineWidth / 2 + engineOffsetX * speedX;
            regwX = engineWidth / 2 - engineOffsetX * speedX;
            egwY = engineHeight / 2 + engineOffsetY * speedY;
            //Anim
            if(!(lWing == null && rWing == null)) {
                lWing.setCurrAngle(FACING + currentRotateL /5*3 + ltargetNoTurn / 4*2 * speedY);
                rWing.setCurrAngle(FACING + currentRotateR /5*3 + rtargetNoTurn / 4*2 * speedY);
            }
            if(lWeapon != null || rWeapon != null) {
            lWeapon.getSprite().setCenter(lmwpX, mwpY);
            rWeapon.getSprite().setCenter(rmwpX, mwpY);
            }

            if(!(lEngine == null && rEngine == null)) {
            lEngine.setCurrAngle(FACING - currentRotateL);
            rEngine.setCurrAngle(FACING - currentRotateR);
            lEngine.getSprite().setCenter(legwX, egwY);
            rEngine.getSprite().setCenter(regwX, egwY);
            }
        }
    }
}
