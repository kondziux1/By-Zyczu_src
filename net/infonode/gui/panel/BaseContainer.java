package net.infonode.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import javax.swing.plaf.PanelUI;

public class BaseContainer extends JPanel {
   private Color foreground;
   private Color background;
   private Font font;
   private Color overridedBackground;
   private Color overridedForeground;
   private Font overridedFont;
   private boolean forcedOpaque = true;
   private boolean opaque = true;
   private static PanelUI UI = new BaseContainer$1();

   public BaseContainer() {
      this(true);
   }

   public BaseContainer(boolean var1) {
      this(var1, new BorderLayout());
   }

   public BaseContainer(LayoutManager var1) {
      this(true, var1);
   }

   public BaseContainer(boolean var1, LayoutManager var2) {
      super(var2);
      this.forcedOpaque = var1;
      this.updateOpaque();
   }

   public void setUI(PanelUI var1) {
      Color var2 = this.overridedBackground;
      Color var3 = this.overridedForeground;
      Font var4 = this.overridedFont;
      Object var5 = null;
      Object var6 = null;
      Object var7 = null;
      this.setBackground(null);
      this.setForeground(null);
      this.setFont(null);
      super.setUI(var1);
      this.background = this.getBackground();
      this.foreground = this.getForeground();
      this.font = this.getFont();
      this.overridedBackground = (Color)var5;
      this.overridedForeground = (Color)var6;
      this.overridedFont = (Font)var7;
      if (!this.forcedOpaque) {
         super.setUI(UI);
      }

      this.updateBackground();
      this.updateForeground();
      this.updateFont();
   }

   void setForcedOpaque(boolean var1) {
      if (this.forcedOpaque != var1) {
         this.forcedOpaque = var1;
         this.updateUI();
         this.updateOpaque();
      }

   }

   public void setOpaque(boolean var1) {
      this.opaque = var1;
      this.updateOpaque();
   }

   protected void paintComponent(Graphics var1) {
      if (this.forcedOpaque) {
         super.paintComponent(var1);
      }

   }

   public void setForeground(Color var1) {
      this.foreground = var1;
      this.updateForeground();
   }

   public void setBackground(Color var1) {
      this.background = var1;
      this.updateBackground();
   }

   public void setFont(Font var1) {
      this.font = var1;
      this.updateFont();
   }

   void setOverridedForeround(Color var1) {
      this.overridedForeground = var1;
      this.updateForeground();
   }

   void setOverridedBackground(Color var1) {
      this.overridedBackground = var1;
      this.updateBackground();
   }

   void setOverrideFont(Font var1) {
      this.overridedFont = var1;
      this.updateFont();
   }

   private void updateBackground() {
      super.setBackground(this.overridedBackground == null ? this.background : this.overridedBackground);
   }

   private void updateForeground() {
      super.setForeground(this.overridedForeground == null ? this.foreground : this.overridedForeground);
   }

   private void updateFont() {
      super.setFont(this.overridedFont == null ? this.font : this.overridedFont);
   }

   private void updateOpaque() {
      super.setOpaque(this.forcedOpaque ? this.opaque : this.forcedOpaque);
   }
}
