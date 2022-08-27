package net.infonode.tabbedpanel;

import net.infonode.util.Enum;

public class TabDepthOrderPolicy extends Enum {
   private static final long serialVersionUID = 1L;
   public static final TabDepthOrderPolicy DESCENDING = new TabDepthOrderPolicy(0, "Descending");
   public static final TabDepthOrderPolicy ASCENDING = new TabDepthOrderPolicy(1, "Ascending");

   private TabDepthOrderPolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TabDepthOrderPolicy[] getDepthOrderPolicies() {
      return new TabDepthOrderPolicy[]{DESCENDING, ASCENDING};
   }
}
