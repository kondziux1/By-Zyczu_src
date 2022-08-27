package net.infonode.tabbedpanel;

import net.infonode.util.AntUtils;
import net.infonode.util.ReleaseInfo;

public class TabbedPanelReleaseInfo {
   private static ReleaseInfo productInfo = new ReleaseInfo(
      "InfoNode Tabbed Panel GPL",
      "NNL Technology AB",
      AntUtils.getBuildTime(1235486906703L),
      AntUtils.createProductVersion(1, 6, 1),
      "GNU General Public License, Version 2",
      "http://www.infonode.net"
   );

   private TabbedPanelReleaseInfo() {
      super();
   }

   public static ReleaseInfo getReleaseInfo() {
      return productInfo;
   }
}
