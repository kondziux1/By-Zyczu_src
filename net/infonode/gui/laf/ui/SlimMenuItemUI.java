package net.infonode.gui.laf.ui;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class SlimMenuItemUI extends BasicMenuItemUI {
   public SlimMenuItemUI() {
      super();
   }

   public static ComponentUI createUI(JComponent var0) {
      return new SlimMenuItemUI();
   }

   protected void installDefaults() {
      super.installDefaults();
   }
}
