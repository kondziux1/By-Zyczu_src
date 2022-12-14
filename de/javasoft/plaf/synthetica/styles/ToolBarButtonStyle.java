package de.javasoft.plaf.synthetica.styles;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;

public class ToolBarButtonStyle extends StyleWrapper {
   private static ToolBarButtonStyle instance = new ToolBarButtonStyle();

   private ToolBarButtonStyle() {
      super();
   }

   public static SynthStyle getStyle(SynthStyle var0, JComponent var1, Region var2) {
      if (SyntheticaLookAndFeel.getStyleName(var1) == null) {
         instance.setStyle(var0);
         return instance;
      } else {
         ToolBarButtonStyle var3 = new ToolBarButtonStyle();
         var3.setStyle(var0);
         return var3;
      }
   }

   @Override
   public Font getFont(SynthContext var1) {
      Object var2 = this.synthStyle.getFont(var1);
      JComponent var3 = var1.getComponent();
      if (SyntheticaLookAndFeel.get("Synthetica.toolBar.button.font.style", var3) != null) {
         int var4 = 0;
         String var5 = SyntheticaLookAndFeel.getString("Synthetica.toolBar.button.font.style", var3);
         if (var5.contains("BOLD")) {
            var4 |= 1;
         }

         if (var5.contains("ITALIC")) {
            var4 |= 2;
         }

         float var6 = SyntheticaLookAndFeel.scaleFontSize((float)SyntheticaLookAndFeel.getInt("Synthetica.toolBar.button.font.size", var3, 0));
         if (var6 == 0.0F) {
            var6 = ((Font)var2).getSize2D();
         }

         var2 = new FontUIResource(((Font)var2).deriveFont(var4, var6));
      } else if (SyntheticaLookAndFeel.get("Synthetica.toolBar.toggleButton.font.style", var3) != null) {
         int var7 = 0;
         String var8 = SyntheticaLookAndFeel.getString("Synthetica.toolBar.toggleButton.font.style", var3);
         if (var8.contains("BOLD")) {
            var7 |= 1;
         }

         if (var8.contains("ITALIC")) {
            var7 |= 2;
         }

         float var9 = SyntheticaLookAndFeel.scaleFontSize((float)SyntheticaLookAndFeel.getInt("Synthetica.toolBar.toggleButton.font.size", var3, 0));
         if (var9 == 0.0F) {
            var9 = ((Font)var2).getSize2D();
         }

         var2 = new FontUIResource(((Font)var2).deriveFont(var7, var9));
      }

      return (Font)var2;
   }
}
