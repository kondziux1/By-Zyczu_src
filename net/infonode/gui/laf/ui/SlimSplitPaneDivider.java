package net.infonode.gui.laf.ui;

import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import net.infonode.gui.icon.button.ArrowIcon;
import net.infonode.util.Direction;

public class SlimSplitPaneDivider extends BasicSplitPaneDivider {
   public SlimSplitPaneDivider(BasicSplitPaneUI var1) {
      super(var1);
   }

   protected JButton createLeftOneTouchButton() {
      ArrowIcon var1 = new ArrowIcon(8, Direction.LEFT);
      var1.setShadowEnabled(false);
      JButton var2 = new JButton(var1);
      var2.setCursor(Cursor.getPredefinedCursor(0));
      var2.setFocusPainted(false);
      var2.setBorderPainted(false);
      var2.setRequestFocusEnabled(false);
      return var2;
   }

   protected JButton createRightOneTouchButton() {
      ArrowIcon var1 = new ArrowIcon(8, Direction.RIGHT);
      var1.setShadowEnabled(false);
      JButton var2 = new JButton(var1);
      var2.setCursor(Cursor.getPredefinedCursor(0));
      var2.setFocusPainted(false);
      var2.setBorderPainted(false);
      var2.setRequestFocusEnabled(false);
      return var2;
   }
}
