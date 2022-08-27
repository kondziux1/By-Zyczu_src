package net.infonode.docking;

import net.infonode.util.Enum;

final class WindowTabState extends Enum {
   private static final long serialVersionUID = 1L;
   static final WindowTabState NORMAL = new WindowTabState(0, "Normal");
   static final WindowTabState HIGHLIGHTED = new WindowTabState(1, "Highlighted");
   static final WindowTabState FOCUSED = new WindowTabState(2, "Focused");
   private static final WindowTabState[] STATES = new WindowTabState[]{NORMAL, HIGHLIGHTED, FOCUSED};

   WindowTabState(int var1, String var2) {
      super(var1, var2);
   }

   static WindowTabState[] getStates() {
      return (WindowTabState[])STATES.clone();
   }

   static int getStateCount() {
      return STATES.length;
   }
}
