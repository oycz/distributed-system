package sys.clock;

import sys.setting.Settings;

import java.lang.reflect.InvocationTargetException;

public class ClockFactory {

    public static Clock newClock(String clockType) {
        if(!Settings.CLOCK_TYPES.containsKey(clockType) || clockType.equals("none")) {
            return null;
        }
        Clock clock = null;
        try {
//            clock = Settings.CLOCK_TYPES.get(clockType).getConstructor(new Class[] {String.class}).newInstance(nodeId);
            clock = (Clock) Settings.CLOCK_TYPES.get(clockType).getConstructor(new Class[0]).newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return clock;
    }
}
