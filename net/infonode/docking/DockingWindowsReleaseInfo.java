package net.infonode.docking;

import net.infonode.util.AntUtils;
import net.infonode.util.ReleaseInfo;

public class DockingWindowsReleaseInfo {
   private static ReleaseInfo productInfo = new ReleaseInfo(
      "InfoNode Docking Windows GPL",
      "NNL Technology AB",
      AntUtils.getBuildTime(1235487189453L),
      AntUtils.createProductVersion(1, 6, 1),
      "GNU General Public License, Version 2",
      "http://www.infonode.net"
   );

   private DockingWindowsReleaseInfo() {
      super();
   }

   public static ReleaseInfo getReleaseInfo() {
      return productInfo;
   }
}
