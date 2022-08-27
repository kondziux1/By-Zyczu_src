package net.infonode.tabbedpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.UIManager;
import net.infonode.gui.UIManagerUtil;
import net.infonode.util.ColorUtil;

public class TabbedUIDefaults {
   private static final int BUTTON_ICON_SIZE = 11;

   private TabbedUIDefaults() {
      super();
   }

   public static Color getContentAreaBackground() {
      return UIManagerUtil.getColor("Panel.background", "control", Color.LIGHT_GRAY);
   }

   public static Color getNormalStateForeground() {
      return UIManagerUtil.getColor("TabbedPane.foreground", "controlText", Color.BLACK);
   }

   public static Color getNormalStateBackground() {
      return UIManagerUtil.getColor("TabbedPane.background", "control", Color.LIGHT_GRAY);
   }

   public static Color getHighlightedStateForeground() {
      return getNormalStateForeground();
   }

   public static Color getHighlightedStateBackground() {
      return UIManagerUtil.getColor("Panel.background", "control", Color.LIGHT_GRAY);
   }

   public static Color getDisabledForeground() {
      return UIManagerUtil.getColor("inactiveCaptionText", "controlText", Color.WHITE);
   }

   public static Color getDisabledBackground() {
      return ColorUtil.mult(getNormalStateBackground(), 0.9);
   }

   public static Color getDarkShadow() {
      return UIManagerUtil.getColor("TabbedPane.darkShadow", "controlDkShadow", Color.BLACK);
   }

   public static Color getHighlight() {
      return UIManagerUtil.getColor("TabbedPane.highlight", "controlHighlight", Color.WHITE);
   }

   public static Font getFont() {
      return UIManagerUtil.getFont("TabbedPane.font");
   }

   public static int getIconTextGap() {
      return UIManager.getInt("TabbedPane.textIconGap");
   }

   public static Insets getTabInsets() {
      return UIManagerUtil.getInsets("TabbedPane.tabInsets", new Insets(2, 2, 0, 2));
   }

   public static Insets getContentAreaInsets() {
      return UIManagerUtil.getInsets("TabbedPane.contentBorderInsets", new Insets(2, 2, 2, 2));
   }

   public static int getButtonIconSize() {
      return 11;
   }
}
