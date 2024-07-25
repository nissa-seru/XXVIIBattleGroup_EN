//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package data.utils.sgb;

public class BlgMisc {
    public BlgMisc() {
    }

    public static String getDigitValue(float value) {
        return getDigitValue(value, 1);
    }

    public static String getDigitValue(float value, int digit) {
        return Math.abs((float)Math.round(value) - value) < 1.0E-4F ? String.format("%d", Math.round(value)) : String.format("%." + digit + "f", value);
    }
}
