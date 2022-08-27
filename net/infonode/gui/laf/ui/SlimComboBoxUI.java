package net.infonode.gui.laf.ui;

import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class SlimComboBoxUI extends MetalComboBoxUI {
   public static Border FOCUS_BORDER = new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(0, 3, 0, 3));
   public static Border NORMAL_BORDER = new EmptyBorder(1, 4, 1, 4);

   public SlimComboBoxUI() {
      super();
   }

   public static ComponentUI createUI(JComponent var0) {
      return new SlimComboBoxUI();
   }

   protected ListCellRenderer createRenderer() {
      return new SlimComboBoxUI$1(this);
   }
}
