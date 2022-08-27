package de.javasoft.plaf.synthetica.styles;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;

public class TextFieldStyle extends StyleWrapper {
   private static TextFieldStyle instance = new TextFieldStyle();

   private TextFieldStyle() {
      super();
   }

   public static SynthStyle getStyle(SynthStyle var0, JComponent var1, Region var2) {
      if (SyntheticaLookAndFeel.getStyleName(var1) == null) {
         instance.setStyle(var0);
         return instance;
      } else {
         TextFieldStyle var3 = new TextFieldStyle();
         var3.setStyle(var0);
         return var3;
      }
   }

   @Override
   public Insets getInsets(SynthContext var1, Insets var2) {
      String var3 = var1.getComponent().getName();
      return "ComboBox.textField".equals(var3) ? new Insets(0, 1, 0, 1) : this.synthStyle.getInsets(var1, var2);
   }
}
