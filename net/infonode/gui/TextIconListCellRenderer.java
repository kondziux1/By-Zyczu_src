package net.infonode.gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import net.infonode.gui.icon.IconUtil;

public class TextIconListCellRenderer extends DefaultListCellRenderer {
   private ListCellRenderer renderer;
   private Icon emptyIcon;
   private int width;
   private int gap = -1;

   public TextIconListCellRenderer(ListCellRenderer var1) {
      super();
      this.renderer = var1;
   }

   public void calculateMaximumIconWidth(Object[] var1) {
      this.width = IconUtil.getMaxIconWidth(var1);
      this.emptyIcon = this.width == 0 ? null : new TextIconListCellRenderer$1(this);
   }

   public void setRenderer(ListCellRenderer var1) {
      this.renderer = var1;
   }

   public Component getListCellRendererComponent(JList var1, Object var2, int var3, boolean var4, boolean var5) {
      if (var3 == -1) {
         return null;
      } else {
         JLabel var6 = (JLabel)this.renderer.getListCellRendererComponent(var1, var2, var3, var4, var5);
         if (this.gap < 0) {
            this.gap = var6.getIconTextGap();
         }

         Icon var7 = IconUtil.getIcon(var2);
         if (var7 == null) {
            var6.setIcon(this.emptyIcon);
         } else {
            var6.setIcon(var7);
            var6.setIconTextGap(this.gap + this.width - var7.getIconWidth());
         }

         return var6;
      }
   }
}
