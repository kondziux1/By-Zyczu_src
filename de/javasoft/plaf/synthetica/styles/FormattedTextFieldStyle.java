package de.javasoft.plaf.synthetica.styles;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;

public class FormattedTextFieldStyle extends StyleWrapper {
   private static FormattedTextFieldStyle instance = new FormattedTextFieldStyle();

   private FormattedTextFieldStyle() {
      super();
   }

   public static SynthStyle getStyle(SynthStyle var0, JComponent var1, Region var2) {
      if (SyntheticaLookAndFeel.getStyleName(var1) == null) {
         instance.setStyle(var0);
         return instance;
      } else {
         FormattedTextFieldStyle var3 = new FormattedTextFieldStyle();
         var3.setStyle(var0);
         return var3;
      }
   }

   @Override
   public Insets getInsets(SynthContext var1, Insets var2) {
      Insets var3 = this.synthStyle.getInsets(var1, var2);
      String var4 = var1.getComponent().getName();
      if ("Spinner.formattedTextField".equals(var4)) {
         return var1.getComponent().getComponentOrientation().isLeftToRight() ? new Insets(0, 0, 0, var3.right) : new Insets(0, var3.right, 0, 0);
      } else {
         return "ComboBox.textField".equals(var4) ? new Insets(0, 1, 0, 1) : var3;
      }
   }
}
