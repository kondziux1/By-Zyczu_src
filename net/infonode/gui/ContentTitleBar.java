package net.infonode.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.infonode.gui.hover.panel.HoverableShapedPanel;
import net.infonode.util.Alignment;
import net.infonode.util.Direction;

public class ContentTitleBar extends HoverableShapedPanel {
   private final ComponentPaintChecker repaintChecker;
   private JComponent[] leftTitleComponents;
   private JComponent[] rightTitleComponents;
   private Insets[] leftTitleComponentsInsets;
   private Insets[] rightTitleComponentsInsets;
   private boolean flipTitleComponents = false;
   private GridBagConstraints constraints = new GridBagConstraints();
   private Insets labelInsets = InsetsUtil.EMPTY_INSETS;
   private Alignment labelAlignment = Alignment.LEFT;
   private final RotatableLabel label = new ContentTitleBar$1(this, "");

   public ContentTitleBar() {
      this(null);
   }

   public ContentTitleBar(Component var1) {
      super(new GridBagLayout(), null, var1);
      this.repaintChecker = new ComponentPaintChecker(this);
      this.add(this.label);
      this.updateLayout();
   }

   public JLabel getLabel() {
      return this.label;
   }

   public String getText() {
      return this.label.getText();
   }

   public Icon getIcon() {
      return this.label.getIcon();
   }

   public void setIcon(Icon var1) {
      if (this.label.getIcon() != var1) {
         this.label.setIcon(var1);
         this.doUpdate();
      }

   }

   public Alignment getLabelAlignment() {
      return this.labelAlignment;
   }

   public void setLabelAlignment(Alignment var1) {
      if (this.labelAlignment != var1) {
         this.labelAlignment = var1;
         this.updateLabelAlignment();
      }

   }

   public void setLayoutDirection(Direction var1) {
      if (this.label.getDirection() != var1) {
         this.label.setDirection(var1);
         this.updateLayout();
      }

   }

   public Insets getLabelInsets() {
      return this.labelInsets;
   }

   public void setLabelInsets(Insets var1) {
      this.labelInsets = var1;
      GridBagConstraints var2 = ((GridBagLayout)this.getLayout()).getConstraints(this.label);
      var2.insets = InsetsUtil.rotate(this.getDirection(), var1);
      ((GridBagLayout)this.getLayout()).setConstraints(this.label, var2);
      this.doUpdate();
   }

   public boolean isFlipTitleComponents() {
      return this.flipTitleComponents;
   }

   public void setFlipTitleComponents(boolean var1) {
      if (this.flipTitleComponents != var1) {
         this.flipTitleComponents = var1;
         this.updateLayout();
      }

   }

   public JComponent[] getLeftTitleComponents() {
      return this.leftTitleComponents;
   }

   public void setLeftTitleComponents(JComponent[] var1) {
      this.setLeftTitleComponents(var1, var1 == null ? null : this.createEmptyInsets(var1.length));
   }

   public void setLeftTitleComponents(JComponent[] var1, Insets[] var2) {
      JComponent[] var3 = this.leftTitleComponents;
      this.leftTitleComponents = var1;
      this.leftTitleComponentsInsets = var2;
      this.updateTitleComponents(var3, var1);
   }

   public JComponent[] getRightTitleComponents() {
      return this.rightTitleComponents;
   }

   public void setRightTitleComponents(JComponent[] var1) {
      this.setRightTitleComponents(var1, var1 == null ? null : this.createEmptyInsets(var1.length));
   }

   public void setRightTitleComponents(JComponent[] var1, Insets[] var2) {
      JComponent[] var3 = this.rightTitleComponents;
      this.rightTitleComponents = var1;
      this.rightTitleComponentsInsets = var2;
      this.updateTitleComponents(var3, var1);
   }

   private Insets[] createEmptyInsets(int var1) {
      Insets[] var2 = new Insets[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = InsetsUtil.EMPTY_INSETS;
      }

      return var2;
   }

   private void updateLabelAlignment() {
      this.label.setHorizontalAlignment(this.labelAlignment == Alignment.LEFT ? 2 : (this.labelAlignment == Alignment.RIGHT ? 4 : 0));
   }

   private void updateTitleComponents(JComponent[] var1, JComponent[] var2) {
      if (var1 != null) {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            this.remove(var1[var3]);
         }
      }

      if (var2 != null) {
         for(int var4 = 0; var4 < var2.length; ++var4) {
            this.add(var2[var4]);
         }
      }

      this.updateLayout();
   }

   private void updateLayout() {
      Direction var1 = this.label.getDirection();
      this.constraints = new GridBagConstraints();
      JComponent[] var2 = this.flipTitleComponents ? this.rightTitleComponents : this.leftTitleComponents;
      JComponent[] var3 = this.flipTitleComponents ? this.leftTitleComponents : this.rightTitleComponents;
      Insets[] var4 = this.flipTitleComponents ? this.rightTitleComponentsInsets : this.leftTitleComponentsInsets;
      Insets[] var5 = this.flipTitleComponents ? this.leftTitleComponentsInsets : this.rightTitleComponentsInsets;
      if (var1 == Direction.LEFT || var1 == Direction.UP) {
         JComponent[] var6 = var2;
         var2 = var3;
         var3 = var6;
         Insets[] var7 = var4;
         var4 = var5;
         var5 = var7;
      }

      if (var1.isHorizontal()) {
         int var9 = 0;
         if (var2 != null) {
            for(int var13 = 0; var13 < var2.length; ++var13) {
               int var8 = var1 == Direction.RIGHT ? var13 : var2.length - var13 - 1;
               this.setConstraints(var2[var8], var4[var8], var9++, 0, 1, 1, 0, 0.0, 0.0, 10);
            }
         }

         this.setConstraints(this.label, this.labelInsets, var9++, 0, 1, 1, 1, 1.0, 1.0, 10);
         if (var3 != null) {
            for(int var14 = 0; var14 < var3.length; ++var14) {
               int var17 = var1 == Direction.RIGHT ? var14 : var3.length - var14 - 1;
               this.setConstraints(var3[var17], var5[var17], var9++, 0, 1, 1, 0, 0.0, 0.0, 10);
            }
         }
      } else {
         int var11 = 0;
         if (var2 != null) {
            for(int var15 = 0; var15 < var2.length; ++var15) {
               int var18 = var1 == Direction.DOWN ? var15 : var2.length - var15 - 1;
               this.setConstraints(var2[var18], var4[var18], 0, var11++, 1, 1, 0, 0.0, 0.0, 10);
            }
         }

         this.setConstraints(this.label, this.labelInsets, 0, var11++, 1, 1, 1, 1.0, 1.0, 10);
         if (var3 != null) {
            for(int var16 = 0; var16 < var3.length; ++var16) {
               int var19 = var1 == Direction.DOWN ? var16 : var3.length - var16 - 1;
               this.setConstraints(var3[var19], var5[var19], 0, var11++, 1, 1, 0, 0.0, 0.0, 10);
            }
         }
      }

      this.doUpdate();
   }

   private void setConstraints(Component var1, Insets var2, int var3, int var4, int var5, int var6, int var7, double var8, double var10, int var12) {
      this.constraints.insets = InsetsUtil.rotate(this.getDirection(), var2);
      this.constraints.gridx = var3;
      this.constraints.gridy = var4;
      this.constraints.fill = var7;
      this.constraints.weightx = var8;
      this.constraints.weighty = var10;
      this.constraints.gridwidth = var5;
      this.constraints.gridheight = var6;
      this.constraints.anchor = var12;
      ((GridBagLayout)this.getLayout()).setConstraints(var1, this.constraints);
   }

   private void doUpdate() {
      this.revalidate();
      if (this.repaintChecker.isPaintingOk()) {
         this.repaint();
      }

   }
}
