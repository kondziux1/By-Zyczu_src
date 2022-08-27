package de.javasoft.plaf.synthetica.aluoxide;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;

public class TableHeaderCellBorder implements Border {
   public TableHeaderCellBorder() {
      super();
   }

   public Insets getBorderInsets(Component c) {
      JComponent comp = (JComponent)c;
      SynthStyle ss = SynthLookAndFeel.getStyle(comp, Region.TABLE_HEADER);
      SynthContext sc = new SynthContext(comp, Region.TABLE_HEADER, ss, 0);
      Insets insets = ss.getInsets(sc, null);
      insets.bottom += 2;
      return insets;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
   }
}
