package de.javasoft.plaf.synthetica.aluoxide;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;

public class GraphicsUtils extends de.javasoft.plaf.synthetica.GraphicsUtils {
   private boolean textShadowEnabled = SyntheticaLookAndFeel.getBoolean("SyntheticaAluOxide.textShadowEnabled", null, true);
   private boolean useSimpleTextShadow = SyntheticaLookAndFeel.getBoolean("SyntheticaAluOxide.useSimpleTextShadow", null, true);
   private Color optionalForeground = new Color(15198183);
   private Color optionalForeground_hover = new Color(16777215);
   private Color optionalForeground_disabled = new Color(7897224);

   public GraphicsUtils() {
      super();
   }

   @Override
   public void paintText(SynthContext sc, Graphics g, String text, int x, int y, int mnemonicIndex) {
      JComponent c = sc.getComponent();
      String name = c.getName();
      Region region = sc.getRegion();
      boolean selected = (sc.getComponentState() & 512) > 0;
      boolean disabled = (sc.getComponentState() & 8) > 0;
      boolean hover = (sc.getComponentState() & 2) > 0;
      boolean pressed = (sc.getComponentState() & 4) > 0;
      Component cParent = c.getParent();
      Component grandParent = cParent == null ? null : cParent.getParent();
      JComponent parent = cParent instanceof JComponent ? (JComponent)cParent : null;
      JToolBar opaqueToolBar = null;
      boolean isMenuBarChild = parent instanceof JMenuBar;
      boolean isToolBarChild = parent instanceof JToolBar;
      boolean isStatusBarChild = false;
      Color paintColor = g.getColor();
      if ((isMenuBarChild || isToolBarChild || isStatusBarChild) && paintColor instanceof UIResource && parent.isOpaque()) {
         g.setColor(this.optionalForeground);
      } else if (isMenuBarChild && !(c instanceof JMenuItem) && paintColor instanceof UIResource) {
         g.setColor(this.optionalForeground);
      } else if (isToolBarChild) {
         opaqueToolBar = SyntheticaLookAndFeel.findOpaqueParentOfClass(JToolBar.class, c, false);
         if (opaqueToolBar != null && paintColor instanceof UIResource) {
            g.setColor(this.optionalForeground);
         }
      }

      if (region == Region.PROGRESS_BAR) {
         this.paintTextShadow(c, g, x, y, text, false, 5, -5, 1.0F, Color.WHITE, -2, -2, false, mnemonicIndex);
      } else if (region == Region.BUTTON || region == Region.TOGGLE_BUTTON) {
         if (pressed && !selected) {
            ++y;
         }

         opaqueToolBar = SyntheticaLookAndFeel.findOpaqueParentOfClass(JToolBar.class, c, false);
         if (opaqueToolBar != null && paintColor instanceof UIResource) {
            Color foreground = hover ? this.optionalForeground_hover : (disabled ? this.optionalForeground_disabled : this.optionalForeground);
            g.setColor(foreground);
         }
      }

      if (this.textShadowEnabled) {
         if (region == Region.MENU && ((JMenu)c).isTopLevelMenu()) {
            Window w = SwingUtilities.getWindowAncestor(c);
            if (w != null && w.isActive()) {
               this.paintDarkShadow(c, g, x, y, text, mnemonicIndex);
            }
         } else if (region == Region.MENU && selected) {
            this.paintBrightShadow(c, g, x, y, text, mnemonicIndex);
         } else if (c instanceof JMenuItem && hover) {
            this.paintBrightShadow(c, g, x, y, text, mnemonicIndex);
         } else if (region == Region.LABEL && "TableHeader.renderer".equals(name)) {
            this.paintBrightShadow(c, g, x, y, text, mnemonicIndex);
         } else if (region == Region.LABEL
            && -13421773 == c.getForeground().getRGB()
            && (
               "List.cellRenderer".equals(name)
                  || "ComboBox.listRenderer".equals(name)
                  || "Table.cellRenderer".equals(name)
                  || "Tree.cellRenderer".equals(name)
            )) {
            boolean respectFocus = SyntheticaLookAndFeel.getBoolean("Synthetica.cellRenderer.respectFocus", c, false);
            boolean focused = respectFocus ? (grandParent != null ? grandParent.hasFocus() : false) : false;
            if ("Table.cellRenderer".equals(name) && respectFocus && grandParent instanceof JTable) {
               JTable table = (JTable)grandParent;
               if (table.getCellEditor() != null) {
                  focused = true;
               }
            }

            if (focused || "ComboBox.listRenderer".equals(name)) {
               this.paintBrightShadow(c, g, x, y, text, mnemonicIndex);
            }
         } else if ((region == Region.BUTTON || region == Region.TOGGLE_BUTTON) && opaqueToolBar == null) {
            this.paintBrightShadow2(c, g, x, y, text, mnemonicIndex);
         } else if (region == Region.TABBED_PANE_TAB) {
            this.paintBrightShadow2(c, g, x, y, text, mnemonicIndex);
         }
      }

      super.paintText(sc, g, text, x, y, mnemonicIndex);
   }

   private void paintBrightShadow(JComponent c, Graphics g, int x, int y, String text, int mnemonicIndex) {
      this.paintShadow(c, g, x, y, text, false, 1, -2, 0.75F, Color.WHITE, 0, 1, mnemonicIndex);
   }

   private void paintBrightShadow2(JComponent c, Graphics g, int x, int y, String text, int mnemonicIndex) {
      this.paintShadow(c, g, x, y, text, false, 1, -2, 0.5F, Color.WHITE, 0, 1, mnemonicIndex);
   }

   private void paintDarkShadow(JComponent c, Graphics g, int x, int y, String text, int mnemonicIndex) {
      this.paintShadow(c, g, x, y, text, false, 1, -2, 1.0F, Color.BLACK, 0, 1, mnemonicIndex);
   }

   private void paintShadow(
      JComponent c,
      Graphics g,
      int x,
      int y,
      String text,
      boolean highQuality,
      int size,
      int distance,
      float opacity,
      Color color,
      int xOffset,
      int yOffset,
      int mnemonicIndex
   ) {
      if (this.useSimpleTextShadow) {
         FontMetrics fm = g.getFontMetrics(c.getFont());
         Color col = g.getColor();
         g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255.0F * opacity / 1.25F)));
         g.drawString(text, x + xOffset, y + yOffset + fm.getAscent());
         g.setColor(col);
      } else {
         this.paintTextShadow(c, g, x, y, text, highQuality, size, distance, opacity, color, xOffset, yOffset, false, mnemonicIndex);
      }

   }
}
