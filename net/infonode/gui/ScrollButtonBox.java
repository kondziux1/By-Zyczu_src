package net.infonode.gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import net.infonode.gui.icon.button.ArrowIcon;
import net.infonode.gui.layout.DirectionLayout;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.util.Direction;

public class ScrollButtonBox extends SimplePanel {
   private AbstractButton upButton;
   private AbstractButton downButton;
   private AbstractButton leftButton;
   private AbstractButton rightButton;
   private boolean button1Enabled;
   private boolean button2Enabled;
   private boolean vertical;
   private ArrayList listeners;
   private ActionListener button1Listener = new ScrollButtonBox$1(this);
   private ActionListener button2Listener = new ScrollButtonBox$2(this);

   public ScrollButtonBox(boolean var1, int var2) {
      this(
         var1,
         ButtonFactory.createFlatHighlightButton(new ArrowIcon(var2, Direction.UP), "", 0, null),
         ButtonFactory.createFlatHighlightButton(new ArrowIcon(var2, Direction.DOWN), "", 0, null),
         ButtonFactory.createFlatHighlightButton(new ArrowIcon(var2, Direction.LEFT), "", 0, null),
         ButtonFactory.createFlatHighlightButton(new ArrowIcon(var2, Direction.RIGHT), "", 0, null)
      );
   }

   public ScrollButtonBox(boolean var1, AbstractButton var2, AbstractButton var3, AbstractButton var4, AbstractButton var5) {
      super();
      this.vertical = var1;
      this.setLayout(new DirectionLayout(var1 ? Direction.DOWN : Direction.RIGHT));
      this.addMouseWheelListener(new ScrollButtonBox$3(this));
      this.setButtons(var2, var3, var4, var5);
   }

   public void setButton1Enabled(boolean var1) {
      this.button1Enabled = var1;
      if (this.getComponentCount() > 0) {
         ((AbstractButton)this.getComponent(0)).setEnabled(var1);
      }

   }

   public void setButton2Enabled(boolean var1) {
      this.button2Enabled = var1;
      if (this.getComponentCount() > 0) {
         ((AbstractButton)this.getComponent(1)).setEnabled(var1);
      }

   }

   public boolean isButton1Enabled() {
      return this.button1Enabled;
   }

   public boolean isButton2Enabled() {
      return this.button2Enabled;
   }

   public void addListener(ScrollButtonBoxListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList(2);
      }

      this.listeners.add(var1);
   }

   public void removeListener(ScrollButtonBoxListener var1) {
      if (this.listeners != null) {
         this.listeners.remove(var1);
         if (this.listeners.size() == 0) {
            this.listeners = null;
         }
      }

   }

   public boolean isVertical() {
      return this.vertical;
   }

   public void setVertical(boolean var1) {
      if (var1 != this.vertical) {
         this.vertical = var1;
         this.initialize();
      }

   }

   public void setButtons(AbstractButton var1, AbstractButton var2, AbstractButton var3, AbstractButton var4) {
      if (var1 != this.upButton || var2 != this.downButton || var3 != this.leftButton || var4 != this.rightButton) {
         this.upButton = var1;
         this.downButton = var2;
         this.leftButton = var3;
         this.rightButton = var4;
         this.initialize();
      }

   }

   public AbstractButton getUpButton() {
      return this.upButton;
   }

   public AbstractButton getDownButton() {
      return this.downButton;
   }

   public AbstractButton getLeftButton() {
      return this.leftButton;
   }

   public AbstractButton getRightButton() {
      return this.rightButton;
   }

   private void fireButton1() {
      if (this.listeners != null) {
         Object[] var1 = this.listeners.toArray();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            ((ScrollButtonBoxListener)var1[var2]).scrollButton1();
         }
      }

   }

   private void fireButton2() {
      if (this.listeners != null) {
         Object[] var1 = this.listeners.toArray();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            ((ScrollButtonBoxListener)var1[var2]).scrollButton2();
         }
      }

   }

   private void initialize() {
      if (this.getComponentCount() > 0) {
         ((AbstractButton)this.getComponent(0)).removeActionListener(this.button1Listener);
         ((AbstractButton)this.getComponent(1)).removeActionListener(this.button2Listener);
         this.removeAll();
      }

      ((DirectionLayout)this.getLayout()).setDirection(this.vertical ? Direction.DOWN : Direction.RIGHT);
      AbstractButton var1;
      AbstractButton var2;
      if (this.vertical) {
         var1 = this.upButton;
         var2 = this.downButton;
      } else {
         var1 = this.leftButton;
         var2 = this.rightButton;
      }

      if (var1 != null && var2 != null) {
         this.add(var1);
         this.add(var2);
         var1.setFocusable(false);
         var2.setFocusable(false);
         var1.setEnabled(this.button1Enabled);
         var2.setEnabled(this.button2Enabled);
         var1.addActionListener(this.button1Listener);
         var2.addActionListener(this.button2Listener);
      }

      if (this.getParent() != null) {
         ComponentUtil.validate(this.getParent());
      }

   }
}
