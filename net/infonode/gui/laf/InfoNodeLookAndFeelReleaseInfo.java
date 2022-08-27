package net.infonode.gui.laf;

import net.infonode.util.AntUtils;
import net.infonode.util.ReleaseInfo;

public class InfoNodeLookAndFeelReleaseInfo {
   private static ReleaseInfo productInfo = new ReleaseInfo(
      "InfoNode Look and Feel GPL",
      "NNL Technology AB",
      AntUtils.getBuildTime(1235486816015L),
      AntUtils.createProductVersion(1, 6, 1),
      "GNU General Public License, Version 2",
      "http://www.infonode.net"
   );

   public InfoNodeLookAndFeelReleaseInfo() {
      super();
   }

   public static ReleaseInfo getReleaseInfo() {
      return productInfo;
   }
}
