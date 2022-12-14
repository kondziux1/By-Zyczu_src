package de.javasoft.plaf.synthetica.painter;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.synth.SynthContext;
import sun.swing.plaf.synth.SynthIcon;

abstract class SyntheticaIconPainter extends SynthIcon implements UIResource {
   SyntheticaIconPainter() {
      super();
   }

   public abstract int getIconHeight();

   public abstract int getIconWidth();

   public abstract void paintIcon(Component var1, Graphics var2, int var3, int var4);

   public int getIconWidth(SynthContext var1) {
      return this.getIconWidth();
   }

   public int getIconHeight(SynthContext var1) {
      return this.getIconHeight();
   }

   public void paintIcon(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paintIcon(var1.getComponent(), var2, var3, var4);
   }
}
