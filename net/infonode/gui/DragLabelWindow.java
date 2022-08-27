package net.infonode.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JLabel;

public class DragLabelWindow extends Dialog {
   private JLabel label = new DragLabelWindow$1(this);

   public DragLabelWindow(Dialog var1) {
      super(var1);
      this.init();
   }

   public DragLabelWindow(Frame var1) {
      super(var1);
      this.init();
   }

   private void init() {
      this.setLayout(new BorderLayout());
      this.add(this.label, "North");
      this.setUndecorated(true);
      this.label.setOpaque(true);
   }

   public JLabel getLabel() {
      return this.label;
   }

   public void setCursor(Cursor var1) {
      this.label.setCursor(var1);
   }

   public void setVisible(boolean var1) {
      if (var1 != this.isVisible() && var1) {
         this.setSize(0, 0);
         this.pack();
         this.setSize(this.getPreferredSize());
      }

      super.setVisible(var1);
   }
}
