package data.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipEngineControllerAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import org.lazywizard.lazylib.MathUtils;

import java.util.List;

public class SGB_Quench_Engine implements EveryFrameWeaponEffectPlugin {
    public final String leftEngineID = "WING_LEFT";
    public final String rightEngineID = "WING_RIGHT";
    private WeaponAPI rEngine; private WeaponAPI lEngine;
    private boolean runOnce=false;
    private float engineWidth, engineHeight;

    private float currentRotateL=0, currentRotateR=0;
    private final float maxRotate=22.5f;
    private ShipAPI ship; private ShipSystemAPI system; private ShipEngineControllerAPI engines;

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
                    case leftEngineID:
                        lEngine=w;
                        engineHeight=w.getSprite().getHeight();
                        engineWidth=w.getSprite().getWidth();
                        break;
                    case rightEngineID:
                        rEngine=w;
                        break; 
                }                
            }
            runOnce=true;
            //return to avoid a null error on the ship
            return;
        }
        //------------
        
        float ltarget=0;
        float rtarget=0;
        
        if(engines.isAccelerating()){
            ltarget-=maxRotate/2;
            rtarget+=maxRotate/2;
        } else if (engines.isDecelerating()|| engines.isAcceleratingBackwards()){            
            ltarget+=maxRotate;
            rtarget-=maxRotate;
        }
        if(engines.isStrafingLeft()){            
            ltarget+=maxRotate/3;
            rtarget+=maxRotate/1.5f;
        } else if (engines.isStrafingRight()){            
            ltarget-=maxRotate/1.5f;
            rtarget-=maxRotate/3;
        }
        if(engines.isTurningLeft()){          
            ltarget-=maxRotate/2;
            rtarget-=maxRotate/2;            
        } else if (engines.isTurningRight()){                      
            ltarget+=maxRotate/2;
            rtarget+=maxRotate/2;
        }

        float rtl = MathUtils.getShortestRotation(currentRotateL, ltarget);
        if (Math.abs(rtl)<0.5f){
            currentRotateL=ltarget;
        } else if (rtl>0) {
            currentRotateL+=0.5f;
        } else {
            currentRotateL-=0.5f;
        }

        float rtr = MathUtils.getShortestRotation(currentRotateR, rtarget);
        if (Math.abs(rtr)<0.5f){
            currentRotateR=rtarget;
        } else if (rtr>0) {
            currentRotateR+=0.5f;
        } else {
            currentRotateR-=0.5f;
        }

        float FACING=ship.getFacing();
        lEngine.setCurrAngle(FACING-currentRotateL);
        rEngine.setCurrAngle(FACING-currentRotateR);

    }
}
