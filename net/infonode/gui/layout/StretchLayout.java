package net.infonode.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class StretchLayout implements LayoutManager {
   public static final StretchLayout BOTH = new StretchLayout();
   private boolean horizontal;
   private boolean vertical;

   public StretchLayout() {
      this(true, true);
   }

   public StretchLayout(boolean var1, boolean var2) {
      super();
      this.horizontal = var1;
      this.vertical = var2;
   }

   public void addLayoutComponent(String var1, Component var2) {
   }

   public void layoutContainer(Container var1) {
      Dimension var2 = LayoutUtil.getInteriorSize(var1);
      Insets var3 = var1.getInsets();
      Component[] var4 = LayoutUtil.getVisibleChildren(var1);

      for(int var5 = 0; var5 < var4.length; ++var5) {
         Dimension var6 = new Dimension(
            this.horizontal ? var2.width : var4[var5].getPreferredSize().width, this.vertical ? var2.height : var4[var5].getPreferredSize().height
         );
         var4[var5]
            .setBounds(
               (int)((float)var3.left + (float)(var2.width - var6.width) * var4[var5].getAlignmentX()),
               (int)((float)var3.top + (float)(var2.height - var6.height) * var4[var5].getAlignmentY()),
               var6.width,
               var6.height
            );
      }

   }

   public Dimension minimumLayoutSize(Container var1) {
      return LayoutUtil.add(LayoutUtil.getMaxMinimumSize(LayoutUtil.getVisibleChildren(var1)), var1.getInsets());
   }

   public Dimension preferredLayoutSize(Container var1) {
      return LayoutUtil.add(LayoutUtil.getMaxPreferredSize(LayoutUtil.getVisibleChildren(var1)), var1.getInsets());
   }

   public void removeLayoutComponent(Component var1) {
   }
}
