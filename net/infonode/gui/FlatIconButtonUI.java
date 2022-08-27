package net.infonode.gui;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class FlatIconButtonUI extends BasicButtonUI {
   private static final FlatIconButtonUI buttonUI = new FlatIconButtonUI();

   public FlatIconButtonUI() {
      super();
   }

   public static ComponentUI createUI(JComponent var0) {
      return buttonUI;
   }

   protected void installDefaults(AbstractButton var1) {
   }
}
