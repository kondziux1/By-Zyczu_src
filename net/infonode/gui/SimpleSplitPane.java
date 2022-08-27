package net.infonode.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JComponent;
import net.infonode.gui.panel.BaseContainer;
import net.infonode.gui.panel.SimplePanel;

public class SimpleSplitPane extends BaseContainer {
   private LayoutManager splitLayout = new SimpleSplitPane$1(this);
   private Component leftComponent;
   private Component rightComponent;
   private SimplePanel dividerPanel = new SimplePanel();
   private Component dragIndicator;
   private boolean dividerDraggable = true;
   private boolean continuousLayout = true;
   private float dragLocation;
   private boolean horizontal;
   private float dividerLocation = 0.5F;
   private int dividerSize = 6;
   private ArrayList listeners = new ArrayList(0);
   private Color dragIndicatorColor = Color.DARK_GRAY;

   public SimpleSplitPane(boolean var1) {
      this(var1, false);
   }

   public SimpleSplitPane(boolean var1, boolean var2) {
      super();
      this.setLayout(this.splitLayout);
      this.add(this.dividerPanel);
      this.setHorizontal(var1);
      this.setHeavyWeightDragIndicator(var2);
      this.dividerPanel.addMouseListener(new SimpleSplitPane$2(this));
      this.dividerPanel.addMouseMotionListener(new SimpleSplitPane$3(this));
   }

   public SimpleSplitPane(boolean var1, Component var2, Component var3) {
      this(var1);
      this.setLeftComponent(var2);
      this.setRightComponent(var3);
   }

   public void addListener(SimpleSplitPaneListener var1) {
      ArrayList var2 = new ArrayList(this.listeners.size() + 1);
      var2.addAll(this.listeners);
      this.listeners = var2;
      this.listeners.add(var1);
   }

   public JComponent getDividerPanel() {
      return this.dividerPanel;
   }

   public boolean isDividerDraggable() {
      return this.dividerDraggable;
   }

   public void setDividerDraggable(boolean var1) {
      this.dividerDraggable = var1;
      this.updateDividerCursor();
   }

   public void setHeavyWeightDragIndicator(boolean var1) {
      this.initDragIndicatior(var1);
   }

   public Color getDragIndicatorColor() {
      return this.dragIndicatorColor;
   }

   public void setDragIndicatorColor(Color var1) {
      this.dragIndicatorColor = var1;
      this.dragIndicator.setBackground(var1);
   }

   private void setDragIndicator(float var1) {
      this.dragLocation = this.fixDividerLocation(var1);
      this.dragIndicator.setVisible(true);
      Point var2 = this.createPoint((int)((float)this.getViewSize() * this.dragLocation), 0);
      Dimension var3 = this.createSize(this.dividerSize, this.getOtherSize());
      this.dragIndicator
         .setBounds(
            (int)(var2.getX() + (double)this.getInsets().left), (int)(var2.getY() + (double)this.getInsets().top), (int)var3.getWidth(), (int)var3.getHeight()
         );
   }

   private void initDragIndicatior(boolean var1) {
      if (this.dragIndicator != null) {
         this.remove(this.dragIndicator);
      }

      if (var1) {
         this.dragIndicator = new Canvas();
      } else {
         this.dragIndicator = new BaseContainer();
      }

      this.add(this.dragIndicator, 0);
      this.dragIndicator.setBackground(this.dragIndicatorColor);
      this.dragIndicator.setVisible(false);
   }

   private float fixDividerLocation(float var1) {
      int var2 = this.getViewSize();
      if (var2 <= 0) {
         return 0.5F;
      } else {
         int var3 = Math.max(
            (int)((float)var2 * var1),
            this.leftComponent != null && this.leftComponent.isVisible() ? this.getDimensionSize(this.leftComponent.getMinimumSize()) : 0
         );
         var3 = Math.min(
            var3, var2 - (this.rightComponent != null && this.rightComponent.isVisible() ? this.getDimensionSize(this.rightComponent.getMinimumSize()) : 0)
         );
         return (float)var3 / (float)var2;
      }
   }

   public void setContinuousLayout(boolean var1) {
      this.continuousLayout = var1;
   }

   public boolean isContinuousLayout() {
      return this.continuousLayout;
   }

   public int getDividerSize() {
      return this.dividerSize;
   }

   public void setDividerSize(int var1) {
      this.dividerSize = var1;
      this.revalidate();
   }

   private int getOffset() {
      return this.horizontal ? this.getInsets().left : this.getInsets().top;
   }

   private int getOtherSize() {
      return this.horizontal
         ? this.getHeight() - this.getInsets().top - this.getInsets().bottom
         : this.getWidth() - this.getInsets().left - this.getInsets().right;
   }

   private int getViewSize() {
      return this.getDimensionSize(this.getSize())
         - this.dividerSize
         - (this.horizontal ? this.getInsets().left + this.getInsets().right : this.getInsets().top + this.getInsets().bottom);
   }

   private int getDimensionSize(Dimension var1) {
      return (int)(this.horizontal ? var1.getWidth() : var1.getHeight());
   }

   private int getOtherSize(Dimension var1) {
      return (int)(this.horizontal ? var1.getHeight() : var1.getWidth());
   }

   private int getPos(Point var1) {
      return (int)(this.horizontal ? var1.getX() : var1.getY());
   }

   private Dimension createSize(int var1, int var2) {
      return this.horizontal ? new Dimension(var1, var2) : new Dimension(var2, var1);
   }

   private Point createPoint(int var1, int var2) {
      return this.horizontal ? new Point(var1, var2) : new Point(var2, var1);
   }

   public boolean isHorizontal() {
      return this.horizontal;
   }

   public void setHorizontal(boolean var1) {
      this.horizontal = var1;
      this.updateDividerCursor();
      this.revalidate();
   }

   public float getDividerLocation() {
      return this.dividerLocation;
   }

   public void setDividerLocation(float var1) {
      this.dividerLocation = var1;
      this.revalidate();

      for(int var2 = 0; var2 < this.listeners.size(); ++var2) {
         ((SimpleSplitPaneListener)this.listeners.get(var2)).dividerLocationChanged(this);
      }

   }

   public Component getLeftComponent() {
      return this.leftComponent;
   }

   public void setLeftComponent(Component var1) {
      if (this.leftComponent != null) {
         this.remove(this.leftComponent);
      }

      this.leftComponent = var1;
      if (var1 != null) {
         this.add(var1);
      }

      this.revalidate();
   }

   public Component getRightComponent() {
      return this.rightComponent;
   }

   public void setRightComponent(Component var1) {
      if (this.rightComponent != null) {
         this.remove(this.rightComponent);
      }

      this.rightComponent = var1;
      if (var1 != null) {
         this.add(var1);
      }

      this.revalidate();
   }

   private void updateDividerCursor() {
      this.dividerPanel.setCursor(this.dividerDraggable ? new Cursor(this.horizontal ? 10 : 8) : Cursor.getDefaultCursor());
   }

   public void setComponents(Component var1, Component var2) {
      this.setLeftComponent(var1);
      this.setRightComponent(var2);
   }
}
