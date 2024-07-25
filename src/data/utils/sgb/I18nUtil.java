//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package data.utils.sgb;

import com.fs.starfarer.api.Global;
import org.dark.shaders.distortion.WaveDistortion;
import org.lwjgl.util.vector.Vector2f;
import org.dark.shaders.distortion.DistortionShader;
import org.dark.shaders.distortion.RippleDistortion;

public class I18nUtil {

    public I18nUtil() {
    }

    public static String getString(String category, String id) {
        return Global.getSettings().getString(category, id);
    }

    public static String getShipSystemString(String id) {
        return getString("shipSystem", id);
    }

    public static String getStarSystemsString(String id) {
        return getString("starSystems", id);
    }

    public static String getHullModString(String id) {
        return getString("hullMod", id);
    }

    public static void easyRippleOut(Vector2f location, Vector2f velocity, float size, float intensity, float fadesize, float frameRate) {
        if (intensity == -1f) {
            intensity = size / 3f;
        }
        if (velocity == null) {
            velocity = nv;
        }
        RippleDistortion ripple = new RippleDistortion(location, velocity);
        ripple.setSize(size);
        ripple.setIntensity(intensity);
        ripple.setFrameRate(frameRate);
        ripple.fadeInSize(fadesize);
        ripple.fadeOutIntensity(fadesize);

        DistortionShader.addDistortion(ripple);
    }
    public static void easyWaving(Vector2f location, Vector2f velocity, float size, float intensity, float fadesize) {
        if (intensity == -1f) {
            intensity = size / 3f;
        }
        if (velocity == null) {
            velocity = nv;
        }
        WaveDistortion wave = new WaveDistortion(location, velocity);
        wave.setSize(size);
        wave.setIntensity(intensity);
        wave.fadeInSize(fadesize);
        wave.fadeOutIntensity(fadesize);

        DistortionShader.addDistortion(wave);
    }

    public static final Vector2f nv = new Vector2f();

    //engine.getCombatUI().addMessage(10, new Object[]{TDB_ColorData.TDBblue3, parent, txt("JYY_2"), "(", parent.getHullSpec().getHullName(), ")", parent.getName()});
}
