package de.javasoft.plaf.synthetica.aluoxide;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D.Float;

public class TitledBorderPainter extends de.javasoft.plaf.synthetica.painter.TitledBorderPainter {
   private static final float ARC = 12.0F;

   public TitledBorderPainter() {
      super();
   }

   @Override
   public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      if (h >= 2) {
         Graphics2D g2 = this.prepareGraphics2D(null, g, x, y, true);
         float arc = this.scaleArc(12.0F);
         float y_ = Math.min(arc / 2.0F / (float)h, 0.999F);
         Shape shape = this.createShape(0.0F, 0.0F, calcRelativeLength(g2, (float)w, 0.0F), calcRelativeLength(g2, (float)h, 0.0F), arc);
         g2.setPaint(
            this.createLinearGradientPaint(
               0.0F,
               this.calcRelativeGradientPos(g2, 0.0F, 0.0F),
               0.0F,
               this.calcRelativeGradientPos(g2, (float)(h - 1), 0.0F),
               new float[]{0.0F, y_, Math.max(1.0F - y_, y_) + 1.0E-4F, 1.0F},
               new Color[]{new Color(1073741824, true), new Color(419430400, true), new Color(419430400, true), new Color(-1275068417, true)}
            )
         );
         g2.draw(shape);
         g2.setPaint(new Color(251658240, true));
         g2.fill(subtractStroke(g2, shape));
         this.restoreGraphics2D(g2);
      }
   }

   private Shape createShape(float x, float y, float w, float h, float arc) {
      return new Float(x, y, w, h, arc, arc);
   }
}
