package net.infonode.tabbedpanel;

import net.infonode.util.Enum;

public class TabAreaVisiblePolicy extends Enum {
   private static final long serialVersionUID = 1L;
   public static final TabAreaVisiblePolicy ALWAYS = new TabAreaVisiblePolicy(0, "Always");
   public static final TabAreaVisiblePolicy NEVER = new TabAreaVisiblePolicy(1, "Never");
   public static final TabAreaVisiblePolicy TABS_EXIST = new TabAreaVisiblePolicy(2, "Tabs Exist in Tab Area");
   public static final TabAreaVisiblePolicy MORE_THAN_ONE_TAB = new TabAreaVisiblePolicy(3, "More than One Tab");
   private static final TabAreaVisiblePolicy[] VISIBLE_POLICIES = new TabAreaVisiblePolicy[]{ALWAYS, NEVER, TABS_EXIST, MORE_THAN_ONE_TAB};

   private TabAreaVisiblePolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TabAreaVisiblePolicy[] getVisiblePolicies() {
      return (TabAreaVisiblePolicy[])VISIBLE_POLICIES.clone();
   }
}
