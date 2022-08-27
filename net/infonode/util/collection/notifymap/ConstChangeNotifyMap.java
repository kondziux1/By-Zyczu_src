package net.infonode.util.collection.notifymap;

import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.signal.SignalHook;

public interface ConstChangeNotifyMap extends ConstMap {
   SignalHook getChangeSignal();
}
