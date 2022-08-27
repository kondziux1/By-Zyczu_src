package net.infonode.tabbedpanel;

import net.infonode.util.Enum;

public final class TabLayoutPolicy extends Enum {
   private static final long serialVersionUID = -1345037155950998515L;
   public static final TabLayoutPolicy SCROLLING = new TabLayoutPolicy(0, "Scrolling");
   public static final TabLayoutPolicy COMPRESSION = new TabLayoutPolicy(1, "Compression");
   public static final TabLayoutPolicy[] LAYOUT_POLICIES = new TabLayoutPolicy[]{SCROLLING, COMPRESSION};

   private TabLayoutPolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TabLayoutPolicy[] getLayoutPolicies() {
      return (TabLayoutPolicy[])LAYOUT_POLICIES.clone();
   }
}
