package net.infonode.docking;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

class RectangleBorderComponent extends JComponent {
   private int lineWidth;

   RectangleBorderComponent(int var1) {
      super();
      this.lineWidth = var1;
      this.setOpaque(false);
   }

   void setLineWidth(int var1) {
      this.lineWidth = var1;
   }

   public void paint(Graphics var1) {
      var1.setColor(Color.BLACK);
      var1.setXORMode(Color.WHITE);
      var1.fillRect(this.lineWidth, 0, this.getWidth() - 2 * this.lineWidth, this.lineWidth);
      var1.fillRect(this.lineWidth, this.getHeight() - 1 - this.lineWidth, this.getWidth() - 2 * this.lineWidth, this.lineWidth);
      var1.fillRect(0, 0, this.lineWidth, this.getHeight() - 1);
      var1.fillRect(this.getWidth() - this.lineWidth, 0, this.lineWidth, this.getHeight() - 1);
      var1.setPaintMode();
   }
}
