package de.javasoft.plaf.synthetica.styles;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;

public class PopupMenuStyle extends StyleWrapper {
   private static PopupMenuStyle instance = new PopupMenuStyle();

   private PopupMenuStyle() {
      super();
   }

   public static SynthStyle getStyle(SynthStyle var0, JComponent var1, Region var2) {
      JPopupMenu var3 = (JPopupMenu)var1;
      var3.setOpaque(false);
      if (!SyntheticaLookAndFeel.isWindowOpacityEnabled(null)) {
         var3.setDoubleBuffered(false);
      }

      var3.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            String var2 = var1.getPropertyName();
            if ("visible".equals(var2)) {
               JPopupMenu var3 = (JPopupMenu)var1.getSource();
               JPanel var4 = (JPanel)var3.getParent();
               if (var4 != null && var4.isOpaque()) {
                  var4.setOpaque(false);
                  if (!SyntheticaLookAndFeel.isWindowOpacityEnabled(null)) {
                     var4.setDoubleBuffered(false);
                  }
               }
            }

         }
      });
      if (SyntheticaLookAndFeel.getStyleName(var1) == null) {
         instance.setStyle(var0);
         return instance;
      } else {
         PopupMenuStyle var4 = new PopupMenuStyle();
         var4.setStyle(var0);
         return var4;
      }
   }

   @Override
   public Insets getInsets(SynthContext var1, Insets var2) {
      JPopupMenu var3 = (JPopupMenu)var1.getComponent();
      Insets var4 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.toplevel.insets", var3, false);
      return SyntheticaLookAndFeel.isToplevelPopupMenu(var3) ? var4 : super.getInsets(var1, var2);
   }
}
