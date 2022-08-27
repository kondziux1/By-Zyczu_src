package net.infonode.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;

class SimpleSplitPane$1 implements LayoutManager {
   SimpleSplitPane$1(SimpleSplitPane var1) {
      super();
      this.this$0 = var1;
   }

   public void addLayoutComponent(String var1, Component var2) {
   }

   public void layoutContainer(Container var1) {
      if (SimpleSplitPane.access$000(this.this$0) == null || !SimpleSplitPane.access$000(this.this$0).isVisible()) {
         this.maximize(SimpleSplitPane.access$100(this.this$0));
      } else if (SimpleSplitPane.access$100(this.this$0) != null && SimpleSplitPane.access$100(this.this$0).isVisible()) {
         float var2 = SimpleSplitPane.access$200(this.this$0, this.this$0.getDividerLocation());
         int var3 = SimpleSplitPane.access$300(this.this$0);
         int var4 = (int)((float)var3 * var2);
         int var5 = SimpleSplitPane.access$400(this.this$0);
         int var6 = this.this$0.getInsets().left;
         int var7 = this.this$0.getInsets().top;
         Dimension var8 = SimpleSplitPane.access$500(this.this$0, var4, var5);
         SimpleSplitPane.access$000(this.this$0).setBounds(var6, var7, (int)var8.getWidth(), (int)var8.getHeight());
         Point var9 = SimpleSplitPane.access$600(this.this$0, var4, 0);
         var8 = SimpleSplitPane.access$500(this.this$0, SimpleSplitPane.access$700(this.this$0), var5);
         SimpleSplitPane.access$800(this.this$0).setBounds(var9.x + var6, var9.y + var7, (int)var8.getWidth(), (int)var8.getHeight());
         var9 = SimpleSplitPane.access$600(this.this$0, var4 + SimpleSplitPane.access$700(this.this$0), 0);
         var8 = SimpleSplitPane.access$500(this.this$0, var3 - var4, var5);
         SimpleSplitPane.access$100(this.this$0).setBounds(var9.x + var6, var9.y + var7, (int)var8.getWidth(), (int)var8.getHeight());
      } else {
         this.maximize(SimpleSplitPane.access$000(this.this$0));
      }

   }

   private void maximize(Component var1) {
      if (var1 != null && var1.isVisible()) {
         Insets var2 = this.this$0.getInsets();
         Dimension var3 = this.this$0.getSize();
         var1.setBounds(var2.left, var2.top, var3.width - var2.left - var2.right, var3.height - var2.top - var2.bottom);
      }

      SimpleSplitPane.access$800(this.this$0).setBounds(0, 0, 0, 0);
   }

   public Dimension minimumLayoutSize(Container var1) {
      Dimension var2 = SimpleSplitPane.access$500(
         this.this$0,
         (
               SimpleSplitPane.access$000(this.this$0) == null
                  ? 0
                  : SimpleSplitPane.access$900(this.this$0, SimpleSplitPane.access$000(this.this$0).getMinimumSize())
            )
            + SimpleSplitPane.access$700(this.this$0)
            + (
               SimpleSplitPane.access$100(this.this$0) == null
                  ? 0
                  : SimpleSplitPane.access$900(this.this$0, SimpleSplitPane.access$100(this.this$0).getMinimumSize())
            ),
         Math.max(
            SimpleSplitPane.access$000(this.this$0) == null
               ? 0
               : SimpleSplitPane.access$1000(this.this$0, SimpleSplitPane.access$000(this.this$0).getMinimumSize()),
            SimpleSplitPane.access$100(this.this$0) == null
               ? 0
               : SimpleSplitPane.access$1000(this.this$0, SimpleSplitPane.access$100(this.this$0).getMinimumSize())
         )
      );
      return new Dimension(
         var2.width + this.this$0.getInsets().left + this.this$0.getInsets().right, var2.height + this.this$0.getInsets().top + this.this$0.getInsets().bottom
      );
   }

   public Dimension preferredLayoutSize(Container var1) {
      boolean var2 = SimpleSplitPane.access$000(this.this$0) != null && SimpleSplitPane.access$000(this.this$0).isVisible();
      boolean var3 = SimpleSplitPane.access$100(this.this$0) != null && SimpleSplitPane.access$100(this.this$0).isVisible();
      Dimension var4 = SimpleSplitPane.access$500(
         this.this$0,
         (var2 ? SimpleSplitPane.access$900(this.this$0, SimpleSplitPane.access$000(this.this$0).getPreferredSize()) : 0)
            + (var2 && var3 ? SimpleSplitPane.access$700(this.this$0) : 0)
            + (var3 ? SimpleSplitPane.access$900(this.this$0, SimpleSplitPane.access$100(this.this$0).getPreferredSize()) : 0),
         Math.max(
            var2 ? SimpleSplitPane.access$1000(this.this$0, SimpleSplitPane.access$000(this.this$0).getPreferredSize()) : 0,
            var3 ? SimpleSplitPane.access$1000(this.this$0, SimpleSplitPane.access$100(this.this$0).getPreferredSize()) : 0
         )
      );
      return new Dimension(
         var4.width + this.this$0.getInsets().left + this.this$0.getInsets().right, var4.height + this.this$0.getInsets().top + this.this$0.getInsets().bottom
      );
   }

   public void removeLayoutComponent(Component var1) {
   }
}
