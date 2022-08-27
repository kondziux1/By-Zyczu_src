package net.infonode.gui.laf.info;

import net.infonode.gui.ReleaseInfoDialog;
import net.infonode.gui.laf.InfoNodeLookAndFeelReleaseInfo;

public class Info {
   public Info() {
      super();
   }

   public static final void main(String[] var0) {
      ReleaseInfoDialog.showDialog(InfoNodeLookAndFeelReleaseInfo.getReleaseInfo(), null);
      System.exit(0);
   }
}
