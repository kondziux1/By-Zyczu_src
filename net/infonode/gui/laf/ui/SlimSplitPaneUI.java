package net.infonode.gui.laf.ui;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class SlimSplitPaneUI extends BasicSplitPaneUI {
   public SlimSplitPaneUI() {
      super();
   }

   public static ComponentUI createUI(JComponent var0) {
      return new SlimSplitPaneUI();
   }

   public BasicSplitPaneDivider createDefaultDivider() {
      return new SlimSplitPaneDivider(this);
   }
}
