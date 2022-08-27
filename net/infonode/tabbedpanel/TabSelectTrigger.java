package net.infonode.tabbedpanel;

import net.infonode.util.Enum;

public final class TabSelectTrigger extends Enum {
   private static final long serialVersionUID = 1L;
   public static final TabSelectTrigger MOUSE_PRESS = new TabSelectTrigger(0, "Mouse Press");
   public static final TabSelectTrigger MOUSE_RELEASE = new TabSelectTrigger(1, "Mouse Release");
   private static final TabSelectTrigger[] SELECT_TRIGGERS = new TabSelectTrigger[]{MOUSE_PRESS, MOUSE_RELEASE};

   private TabSelectTrigger(int var1, String var2) {
      super(var1, var2);
   }

   public static TabSelectTrigger[] getSelectTriggers() {
      return (TabSelectTrigger[])SELECT_TRIGGERS.clone();
   }
}
