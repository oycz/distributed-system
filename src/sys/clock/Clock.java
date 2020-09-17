package sys.clock;

import java.io.Serializable;

public abstract class Clock<T> implements Comparable<T>, Serializable {

    private static String clockType;
    private static Clock instance;
    private static String nodeId;

//    public static Clock getInstance() {
//        if(clockType == null || nodeId == null) {
//            return null;
//        }
//        if(instance == null) {
//            synchronized (MatrixClock.class) {
//                if(instance == null) {
//                    if(clockType == null) {
//                        throw new RuntimeException("Try to get clock before initialization. ");
//                    } else {
//                        try {
//                            instance = (Clock) CLOCK_TYPES.get(clockType).getConstructor(new Class[] {String.class}).newInstance(nodeId);
//                        } catch (InstantiationException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return instance;
//    }
//
//    public static void setClockType(String clockType) {
//        if(!CLOCK_TYPES.keySet().contains(clockType)) {
//            throw new IllegalArgumentException("No such clock type: " + clockType + ". ");
//        }
//        Clock.clockType = clockType;
//    }
//
//    public static void setNodeId(String nodeId) {
//        Clock.nodeId = nodeId;
//    }
}
