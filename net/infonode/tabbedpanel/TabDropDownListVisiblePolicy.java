package net.infonode.tabbedpanel;

import net.infonode.util.Enum;

public final class TabDropDownListVisiblePolicy extends Enum {
   private static final long serialVersionUID = 1L;
   public static final TabDropDownListVisiblePolicy NEVER = new TabDropDownListVisiblePolicy(0, "Never");
   public static final TabDropDownListVisiblePolicy MORE_THAN_ONE_TAB = new TabDropDownListVisiblePolicy(1, "More than One Tab");
   public static final TabDropDownListVisiblePolicy TABS_NOT_VISIBLE = new TabDropDownListVisiblePolicy(1, "Some Tabs Not Visible");
   private static final TabDropDownListVisiblePolicy[] DROP_DOWN_LIST_VISIBLE_POLICIES = new TabDropDownListVisiblePolicy[]{
      NEVER, MORE_THAN_ONE_TAB, TABS_NOT_VISIBLE
   };

   private TabDropDownListVisiblePolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TabDropDownListVisiblePolicy[] getDropDownListVisiblePolicies() {
      return (TabDropDownListVisiblePolicy[])DROP_DOWN_LIST_VISIBLE_POLICIES.clone();
   }
}
