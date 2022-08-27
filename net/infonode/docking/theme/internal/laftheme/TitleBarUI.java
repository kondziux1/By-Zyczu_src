package net.infonode.docking.theme.internal.laftheme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.beans.PropertyVetoException;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.infonode.docking.View;
import net.infonode.docking.internal.ViewTitleBar;
import net.infonode.gui.DimensionProvider;
import net.infonode.gui.DynamicUIManager;
import net.infonode.gui.DynamicUIManagerListener;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.tabbedpanel.theme.internal.laftheme.SizeIcon;
import net.infonode.util.ColorUtil;
import net.infonode.util.Direction;

public class TitleBarUI {
   private static final int NUM_FADE_COLORS = 25;
   private static final int BUTTON_OFFSET = 2;
   private static final int RIGHT_INSET = 4;
   private final boolean showing = true;
   private boolean enabled;
   private final DynamicUIManagerListener uiListener = new TitleBarUI$1(this);
   private final Color[] fadeSelectedColors = new Color[25];
   private final Color[] fadeNormalColors = new Color[25];
   private final TitleBarUI.IFrame iFrame = new TitleBarUI.IFrame();
   private JFrame frame;
   private Dimension reportedMinimumSize;
   private Dimension minimumSize;
   private Insets iFrameInsets;
   private Color inactiveBackgroundColor;
   private Color activeBackgroundColor;
   private Color foundBackgroundColor;
   private boolean skipIFrame = false;
   private final ComponentPainter activeComponentPainter = new TitleBarUI$2(this);
   private final ComponentPainter inactiveComponentPainter = new TitleBarUI$3(this);
   private final TitleBarUIListener listener;

   public TitleBarUI(TitleBarUIListener var1, boolean var2) {
      super();
      this.enabled = var2;
      this.listener = var1;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public void dispose() {
      DynamicUIManager.getInstance().removePrioritizedListener(this.uiListener);
      this.frame.removeAll();
      this.frame.dispose();
   }

   public void init() {
      DynamicUIManager.getInstance().addPrioritizedListener(this.uiListener);
      this.frame = new JFrame();
      this.frame.getContentPane().setLayout(null);
      this.frame.getContentPane().add(this.iFrame);
      this.frame.pack();
      this.listener.updating();
      this.update();
   }

   private void doUpdate() {
      this.setEnabled(false);
      SwingUtilities.updateComponentTreeUI(this.frame);
      this.update();
   }

   private void update() {
      SwingUtilities.invokeLater(new TitleBarUI$4(this));
   }

   private void estimateBackgroundColors() {
      this.activeBackgroundColor = this.estimateBackgroundColor(true);
      this.inactiveBackgroundColor = this.estimateBackgroundColor(false);
      double var1 = (double)(255 / this.fadeSelectedColors.length);

      for(int var3 = 0; var3 < this.fadeSelectedColors.length; ++var3) {
         if (this.activeBackgroundColor != null) {
            this.fadeSelectedColors[var3] = new Color(
               this.activeBackgroundColor.getRed(),
               this.activeBackgroundColor.getGreen(),
               this.activeBackgroundColor.getBlue(),
               (int)((double)(var3 + 1) * var1)
            );
         }

         if (this.inactiveBackgroundColor != null) {
            this.fadeNormalColors[var3] = new Color(
               this.inactiveBackgroundColor.getRed(),
               this.inactiveBackgroundColor.getGreen(),
               this.inactiveBackgroundColor.getBlue(),
               (int)((double)(var3 + 1) * var1)
            );
         }
      }

   }

   private Color estimateBackgroundColor(boolean var1) {
      this.setSize(400);
      this.iFrame.setSelectedActivated(var1);
      BufferedImage var2 = new BufferedImage(this.iFrame.getWidth(), this.iFrame.getHeight(), 2);
      int var3 = this.iFrame.getWidth() - this.iFrameInsets.right - 3;
      int var4 = this.iFrameInsets.top + 3;
      TitleBarUI$5 var7 = new TitleBarUI$5(this, var3, var4);
      FilteredImageSource var8 = new FilteredImageSource(var2.getSource(), var7);
      this.iFrame.paint(var2.getGraphics());
      BufferedImage var9 = new BufferedImage(var2.getWidth(), var2.getHeight(), 2);
      var9.getGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(var8), 0, 0, null);
      return this.foundBackgroundColor;
   }

   public DimensionProvider getSizeDimensionProvider() {
      return this.skipIFrame ? null : new TitleBarUI$6(this);
   }

   public void paintTitleBar(Component var1, Graphics var2, boolean var3, int var4, int var5, Direction var6) {
      if (this.enabled) {
         View var7 = this.findView(var1);
         if (var7 == null) {
            return;
         }

         this.setTitleAndIcon(
            var7.getViewProperties().getViewTitleBarProperties().getNormalProperties().getTitle(),
            var7.getViewProperties().getViewTitleBarProperties().getNormalProperties().getIcon()
         );
         this.iFrame.setSelectedActivated(var3);
         this.setSize(var4);
         Shape var8 = var2.getClip();
         var2.clipRect(0, 0, var4, this.reportedMinimumSize.height - this.iFrameInsets.top - this.iFrameInsets.bottom);
         var2.translate(-this.iFrameInsets.left, -this.iFrameInsets.top);
         this.iFrame.paint(var2);
         var2.translate(this.iFrameInsets.left, this.iFrameInsets.top);
         var2.setClip(var8);
         this.paintSolidButtonBackground(var1, var2, var3);
      }

   }

   private void paintSolidButtonBackground(Component var1, Graphics var2, boolean var3) {
      ViewTitleBar var4 = (ViewTitleBar)var1;
      JComponent[] var5 = var4.getRightTitleComponents();
      if (var5.length > 0) {
         int var6 = 0;

         for(int var7 = 0; var7 < var5.length; ++var7) {
            if (var5[var7].isVisible()) {
               var6 += var5[var7].getWidth();
            }
         }

         Color var11 = var3 ? this.activeBackgroundColor : this.inactiveBackgroundColor;
         Color[] var8 = var3 ? this.fadeSelectedColors : this.fadeNormalColors;

         for(int var9 = 0; var9 < var8.length; ++var9) {
            var2.setColor(var8[var9]);
            int var10 = var1.getWidth() - var6 - (var8.length - var9) - 4;
            var2.drawLine(var10, 2, var10, var1.getHeight() - 4);
         }

         var2.setColor(var11);
         var2.fillRect(var1.getWidth() - var6 - 4, 2, var6 + 4, var1.getHeight() - 4);
      }

   }

   private void setTitleAndIcon(String var1, Icon var2) {
      this.iFrame.setTitle(var1);
      this.iFrame.setFrameIcon((Icon)(var2 == null ? SizeIcon.EMPTY : var2));
   }

   private View findView(Component var1) {
      return var1 != null && !(var1 instanceof View) ? this.findView(var1.getParent()) : (View)var1;
   }

   private void setSize(int var1) {
      this.iFrame.setSize(var1 + this.iFrameInsets.left + this.iFrameInsets.right, this.reportedMinimumSize.height);
      this.iFrame.invalidate();
      this.iFrame.validate();
   }

   public boolean isRenderingIcon() {
      return !this.skipIFrame;
   }

   public boolean isRenderingTitle() {
      return !this.skipIFrame;
   }

   public Direction getRenderingDirection() {
      return Direction.RIGHT;
   }

   public ComponentPainter getInactiveComponentPainter() {
      if (!this.skipIFrame) {
         return this.inactiveComponentPainter;
      } else {
         Color var1 = UIManager.getColor("InternalFrame.inactiveTitleBackground");
         if (var1 == null) {
            var1 = this.inactiveBackgroundColor;
         }

         return this.createComponentPainter(var1, UIManager.getColor("InternalFrame.inactiveTitleGradient"));
      }
   }

   public ComponentPainter getActiveComponentPainter() {
      if (!this.skipIFrame) {
         return this.activeComponentPainter;
      } else {
         Color var1 = UIManager.getColor("InternalFrame.activeTitleBackground");
         if (var1 == null) {
            var1 = this.activeBackgroundColor;
         }

         return this.createComponentPainter(var1, UIManager.getColor("InternalFrame.activeTitleGradient"));
      }
   }

   public Insets getInsets() {
      return this.skipIFrame ? new Insets(2, 2, 2, 2) : new Insets(0, 0, 0, 4);
   }

   public Color getInactiveBackgroundColor() {
      return this.inactiveBackgroundColor;
   }

   public Color getActiveBackgroundColor() {
      return this.activeBackgroundColor;
   }

   private ComponentPainter createComponentPainter(Color var1, Color var2) {
      Color var3 = ColorUtil.blend(var1, var2, 0.5);
      ComponentPainter var4 = this.createGradientSegmentPainter(var1, var2, true);
      return new TitleBarUI$7(this, var2, var3, var4, var1);
   }

   private ComponentPainter createGradientSegmentPainter(Color var1, Color var2, boolean var3) {
      if (var1 != null) {
         var2 = var2 == null ? var1 : var2;
         var1 = ColorUtil.mult(var1, var3 ? 1.05 : 0.9);
         var2 = ColorUtil.mult(var2, var3 ? 0.9 : 1.05);
         return new GradientComponentPainter(var1, var1, var2, var2);
      } else {
         return null;
      }
   }

   private class IFrame extends JInternalFrame {
      public IFrame() {
         super();
      }

      public void updateUI() {
         super.updateUI();
         this.setClosable(false);
         this.setIconifiable(false);
         this.setMaximizable(false);
         this.setFocusable(false);
      }

      public void setSelectedActivated(boolean var1) {
         try {
            this.setSelected(var1);
         } catch (PropertyVetoException var3) {
            var3.printStackTrace();
         }

      }

      public boolean isShowing() {
         return true;
      }
   }
}
