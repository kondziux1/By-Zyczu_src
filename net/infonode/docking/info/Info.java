package net.infonode.docking.info;

import net.infonode.docking.DockingWindowsReleaseInfo;
import net.infonode.gui.ReleaseInfoDialog;
import net.infonode.gui.laf.InfoNodeLookAndFeelReleaseInfo;
import net.infonode.tabbedpanel.TabbedPanelReleaseInfo;
import net.infonode.util.ReleaseInfo;

public class Info {
   private Info() {
      super();
   }

   public static final void main(String[] var0) {
      ReleaseInfoDialog.showDialog(
         new ReleaseInfo[]{
            DockingWindowsReleaseInfo.getReleaseInfo(), TabbedPanelReleaseInfo.getReleaseInfo(), InfoNodeLookAndFeelReleaseInfo.getReleaseInfo()
         },
         null
      );
      System.exit(0);
   }
}
