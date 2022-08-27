package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;

public class ToolBarHandle extends SyntheticaIconPainter {
   public ToolBarHandle() {
      super();
   }

   @Override
   public int getIconHeight() {
      return UIManager.getInt("Synthetica.toolBar.handle.size");
   }

   @Override
   public int getIconWidth() {
      return UIManager.getInt("Synthetica.toolBar.handle.size");
   }

   @Override
   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      JToolBar var5 = (JToolBar)var1;
      int var6 = var5.getOrientation();
      SynthStyle var7 = SynthLookAndFeel.getStyle(var5, Region.TOOL_BAR);
      SynthContext var8 = new SynthContext(var5, Region.TOOL_BAR, var7, 0);
      SyntheticaPainterState var9 = new SyntheticaPainterState(var8);
      int var10 = SyntheticaLookAndFeel.getInt("Synthetica.toolBar.handle.size", var1);
      int var11 = var10;
      if (var10 > 0) {
         Object var12 = null;
         Insets var15;
         if (var6 == 0) {
            var15 = SyntheticaLookAndFeel.getInsets("Synthetica.toolBar.margin.horizontal", var1);
            var11 = var1.getHeight() - var15.top - var15.bottom;
         } else {
            var3 = var5.getComponentOrientation().isLeftToRight() ? var3 : 0;
            var15 = SyntheticaLookAndFeel.getInsets("Synthetica.toolBar.margin.vertical", var1);
            var10 = var1.getWidth() - var15.left - var15.right;
         }

         var3 += var15.left;
         var4 += var15.top;
         ToolBarHandlePainter.getInstance(var8).paintHandle(var5, var9, var6, var2, var3, var4, var10, var11);
      }
   }
}
