package net.infonode.gui.colorprovider;

import java.awt.Color;
import javax.swing.UIManager;

public class UIManagerColorProvider extends AbstractColorProvider {
   private static final long serialVersionUID = 1L;
   public static final UIManagerColorProvider CONTROL_COLOR = new UIManagerColorProvider("control", Color.LIGHT_GRAY);
   public static final UIManagerColorProvider CONTROL_DARK_SHADOW = new UIManagerColorProvider("controlDkShadow", Color.BLACK);
   public static final UIManagerColorProvider TABBED_PANE_HIGHLIGHT = new UIManagerColorProvider("TabbedPane.highlight", Color.WHITE);
   public static final UIManagerColorProvider TABBED_PANE_SHADOW = new UIManagerColorProvider("TabbedPane.shadow", Color.BLACK);
   public static final UIManagerColorProvider TABBED_PANE_DARK_SHADOW = new UIManagerColorProvider("TabbedPane.darkShadow", Color.BLACK);
   public static final UIManagerColorProvider TABBED_PANE_BACKGROUND = new UIManagerColorProvider("TabbedPane.background", Color.LIGHT_GRAY);
   public static final UIManagerColorProvider DESKTOP_BACKGROUND = new UIManagerColorProvider("Desktop.background", Color.BLUE);
   private final String propertyName;
   private Color defaultColor;

   public UIManagerColorProvider(String var1) {
      super();
      this.propertyName = var1;
   }

   public UIManagerColorProvider(String var1, Color var2) {
      super();
      this.propertyName = var1;
      this.defaultColor = var2;
   }

   public Color getColor() {
      Color var1 = UIManager.getColor(this.propertyName);
      return var1 == null ? this.defaultColor : var1;
   }
}
