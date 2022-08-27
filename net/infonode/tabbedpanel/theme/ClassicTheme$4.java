package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Insets;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.tabbedpanel.internal.TwoColoredLineBorder;

class ClassicTheme$4 extends TwoColoredLineBorder {
   ClassicTheme$4(ClassicTheme var1, ColorProvider var2, ColorProvider var3, boolean var4, boolean var5, boolean var6) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
      this.val$equalInset = var6;
   }

   protected Insets getShapedBorderInsets(Component var1) {
      return this.val$equalInset ? new Insets(1, 1, 1, 1) : super.getShapedBorderInsets(var1);
   }
}
