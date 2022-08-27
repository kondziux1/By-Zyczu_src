package net.infonode.gui.laf.ui;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class SlimInternalFrameUI extends BasicInternalFrameUI {
   public static ComponentUI createUI(JComponent var0) {
      return new SlimInternalFrameUI((JInternalFrame)var0);
   }

   public SlimInternalFrameUI(JInternalFrame var1) {
      super(var1);
   }

   protected JComponent createNorthPane(JInternalFrame var1) {
      return new SlimInternalFrameTitlePane(var1);
   }
}
