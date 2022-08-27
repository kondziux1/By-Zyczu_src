package de.javasoft.plaf.synthetica.aluoxide;

import de.javasoft.plaf.synthetica.SyntheticaWindowShape;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

public class WindowShape implements SyntheticaWindowShape {
   public WindowShape() {
      super();
   }

   @Override
   public Shape getShape(int width, int height) {
      return createShape(0.0F, 0.0F, (float)width, (float)height, 7.0F);
   }

   private static Shape createShape(float x, float y, float w, float h, float arc) {
      GeneralPath path = new GeneralPath();
      path.moveTo(x, y + h);
      path.lineTo(x, y + arc);
      path.quadTo(x, y, x + arc, y);
      path.lineTo(x + w - arc, y);
      path.quadTo(x + w, y, x + w, y + arc);
      path.lineTo(x + w, y + h);
      return path;
   }
}
