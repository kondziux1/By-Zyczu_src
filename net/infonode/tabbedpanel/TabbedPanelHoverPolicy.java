package net.infonode.tabbedpanel;

import net.infonode.util.Enum;

public class TabbedPanelHoverPolicy extends Enum {
   private static final long serialVersionUID = 1L;
   public static final TabbedPanelHoverPolicy NEVER = new TabbedPanelHoverPolicy(0, "Never Hover");
   public static final TabbedPanelHoverPolicy ALWAYS = new TabbedPanelHoverPolicy(1, "Always Hover");
   public static final TabbedPanelHoverPolicy NO_HOVERED_CHILD = new TabbedPanelHoverPolicy(2, "No Hovered Child Tabbed Panel");
   public static final TabbedPanelHoverPolicy ONLY_WHEN_DEEPEST = new TabbedPanelHoverPolicy(3, "Only when Deepest Tabbed Panel");
   public static final TabbedPanelHoverPolicy ALWAYS_AND_EXCLUDE = new TabbedPanelHoverPolicy(4, "Always Hover and be Excluded by Others");

   private TabbedPanelHoverPolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TabbedPanelHoverPolicy[] getHoverPolicies() {
      return new TabbedPanelHoverPolicy[]{NEVER, ALWAYS, NO_HOVERED_CHILD, ONLY_WHEN_DEEPEST, ALWAYS_AND_EXCLUDE};
   }
}
