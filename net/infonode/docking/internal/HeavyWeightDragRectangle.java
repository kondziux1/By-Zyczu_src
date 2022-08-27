package net.infonode.docking.internal;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JPanel;

public class HeavyWeightDragRectangle extends JPanel {
   private int width = 4;
   private Canvas northCanvas = new HeavyWeightDragRectangle$1(this);
   private Canvas southCanvas = new HeavyWeightDragRectangle$2(this);
   private Canvas eastCanvas = new HeavyWeightDragRectangle$3(this);
   private Canvas westCanvas = new HeavyWeightDragRectangle$4(this);

   public HeavyWeightDragRectangle() {
      super(new BorderLayout());
      this.setOpaque(false);
      this.add(this.northCanvas, "North");
      this.add(this.southCanvas, "South");
      this.add(this.westCanvas, "West");
      this.add(this.eastCanvas, "East");
      this.setColor(Color.BLACK);
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      super.setBounds(var1, var2, var3, var4);
      this.revalidate();
   }

   public void setBorderWidth(int var1) {
      this.width = var1;
      this.revalidate();
   }

   public void setColor(Color var1) {
      this.northCanvas.setBackground(var1);
      this.southCanvas.setBackground(var1);
      this.eastCanvas.setBackground(var1);
      this.westCanvas.setBackground(var1);
   }
}
