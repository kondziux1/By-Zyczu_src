package net.infonode.gui.laf.ui;

import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import net.infonode.gui.ButtonFactory;
import net.infonode.gui.border.EdgeBorder;

public class SlimInternalFrameTitlePane extends BasicInternalFrameTitlePane {
   public SlimInternalFrameTitlePane(JInternalFrame var1) {
      super(var1);
      this.setBorder(new EdgeBorder(UIManager.getColor("controlDkShadow"), false, true, false, false));
   }

   protected void createButtons() {
      this.iconButton = ButtonFactory.createFlatHighlightButton(this.iconIcon, UIManager.getString("InternalFrame.iconButtonToolTip"), 0, this.iconifyAction);
      this.iconButton.setFocusable(false);
      this.closeButton = ButtonFactory.createFlatHighlightButton(this.closeIcon, UIManager.getString("InternalFrame.closeButtonToolTip"), 0, this.closeAction);
      this.closeButton.setFocusable(false);
      this.maxButton = ButtonFactory.createFlatHighlightButton(this.maxIcon, UIManager.getString("InternalFrame.maxButtonToolTip"), 0, this.maximizeAction);
      this.maxButton.setFocusable(false);
   }
}
