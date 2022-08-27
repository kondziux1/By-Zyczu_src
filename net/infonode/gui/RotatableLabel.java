package net.infonode.gui;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.plaf.LabelUI;
import net.infonode.util.Direction;

public class RotatableLabel extends JLabel {
   private RotatableLabelUI ui = new RotatableLabelUI(Direction.RIGHT);

   public RotatableLabel(String var1) {
      super(var1);
      this.init();
   }

   public RotatableLabel(String var1, Icon var2) {
      super(var1, var2, 2);
      this.init();
   }

   private void init() {
      super.setUI(this.ui);
      super.setOpaque(false);
   }

   public Direction getDirection() {
      return this.ui.getDirection();
   }

   public void setDirection(Direction var1) {
      if (this.ui.getDirection() != var1) {
         this.ui.setDirection(var1);
         this.revalidate();
      }

   }

   public void setMirror(boolean var1) {
      this.ui.setMirror(var1);
      this.revalidate();
   }

   public boolean isMirror() {
      return this.ui.isMirror();
   }

   public void setUI(LabelUI var1) {
   }

   private boolean isVertical() {
      return !this.ui.getDirection().isHorizontal();
   }

   private Dimension rotateDimension(Dimension var1) {
      return var1 == null ? null : (this.isVertical() ? new Dimension(var1.height, var1.width) : var1);
   }

   public Dimension getPreferredSize() {
      return this.rotateDimension(super.getPreferredSize());
   }

   public Dimension getMinimumSize() {
      return this.rotateDimension(super.getMinimumSize());
   }

   public Dimension getMaximumSize() {
      return this.rotateDimension(super.getMaximumSize());
   }

   public void setMinimumSize(Dimension var1) {
      super.setMinimumSize(this.rotateDimension(var1));
   }

   public void setMaximumSize(Dimension var1) {
      super.setMaximumSize(this.rotateDimension(var1));
   }

   public void setPreferredSize(Dimension var1) {
      super.setPreferredSize(this.rotateDimension(var1));
   }

   public void setOpaque(boolean var1) {
   }
}
