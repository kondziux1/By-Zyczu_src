package net.infonode.gui.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import net.infonode.gui.UIManagerUtil;

public class FocusBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private static final Insets INSETS = new Insets(1, 1, 1, 1);
   private final Component component;
   private boolean enabled = true;

   public FocusBorder(Component var1) {
      super();
      this.component = var1;
      var1.addFocusListener(new FocusBorder$1(this, var1));
   }

   public Insets getBorderInsets(Component var1) {
      return INSETS;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean var1) {
      if (var1 != this.enabled) {
         this.enabled = var1;
      }

   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      if (this.enabled && this.component.hasFocus()) {
         var2.setColor(UIManagerUtil.getColor("Button.focus", "TabbedPane.focus"));
         if (UIManager.getLookAndFeel().getClass().getName().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
            BasicGraphicsUtils.drawDashedRect(var2, var3, var4, var5, var6);
         } else {
            var2.drawRect(var3, var4, var5 - 1, var6 - 1);
         }
      }

   }
}
