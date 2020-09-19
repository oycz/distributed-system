package sys.factory;

import sys.clock.Clock;
import sys.setting.Setting;

import java.lang.reflect.InvocationTargetException;

public class ClockFactory {

    public static Clock newClock(String clockType) {
        if(!Setting.CLOCK_TYPES.containsKey(clockType) || clockType.equals("none")) {
            return null;
        }
        Clock clock = null;
        try {
            clock = (Clock) Setting.CLOCK_TYPES.get(clockType).getConstructor(new Class[0]).newInstance();
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
