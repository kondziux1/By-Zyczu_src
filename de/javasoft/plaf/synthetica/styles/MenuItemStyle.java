package de.javasoft.plaf.synthetica.styles;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;
import javax.swing.text.View;

public class MenuItemStyle extends StyleWrapper {
   private static MenuItemStyle instance = new MenuItemStyle();

   private MenuItemStyle() {
      super();
   }

   public static SynthStyle getStyle(SynthStyle var0, JComponent var1, Region var2) {
      var1.setOpaque(false);
      if (SyntheticaLookAndFeel.getStyleName(var1) == null) {
         instance.setStyle(var0);
         return instance;
      } else {
         MenuItemStyle var3 = new MenuItemStyle();
         var3.setStyle(var0);
         return var3;
      }
   }

   @Override
   public Font getFont(SynthContext var1) {
      menuItemHtmlColorCorrection(var1);
      return this.synthStyle.getFont(var1);
   }

   @Override
   public Icon getIcon(SynthContext var1, Object var2) {
      JMenuItem var3 = (JMenuItem)var1.getComponent();
      if ((var3 instanceof JRadioButtonMenuItem || var3 instanceof JCheckBoxMenuItem) && var3.isSelected() && !var3.isEnabled()) {
         SynthContext var4 = new SynthContext(var3, var1.getRegion(), var1.getStyle(), 520);
         return this.synthStyle.getIcon(var4, var2);
      } else {
         return this.synthStyle.getIcon(var1, var2);
      }
   }

   @Override
   public String getString(SynthContext var1, Object var2, String var3) {
      if ("MenuItem.acceleratorDelimiter".equals(var2)) {
         String var4 = UIManager.getString("MenuItem.acceleratorDelimiter");
         if (var4 != null) {
            return var4;
         }
      }

      return super.getString(var1, var2, var3);
   }

   static void menuItemHtmlColorCorrection(SynthContext var0) {
      JMenuItem var1 = (JMenuItem)var0.getComponent();
      String var2 = var1.getText();
      if (var2 != null && var1.getClientProperty("html") != null) {
         SynthStyle var3 = var0.getStyle();
         int var4 = var0.getComponentState();
         boolean var5 = var1 instanceof JMenu ? (var4 & 512) > 0 : (var4 & 2) > 0;
         boolean var6 = (var4 & 8) > 0;
         Boolean var7 = (Boolean)var1.getClientProperty("Synthetica.MOUSE_OVER");
         boolean var8 = var7 == null ? false : var7;
         boolean var9 = (var4 & 512) > 0;
         if (var5 || var6) {
            int var10 = var2.toLowerCase().indexOf("<html>");
            if (var1 instanceof JMenu && var0.getRegion() != Region.MENU) {
               var3 = SynthLookAndFeel.getStyle(var1, Region.MENU);
            }

            String var11 = Integer.toHexString(var3.getColor(var0, ColorType.TEXT_FOREGROUND).getRGB());
            String var12 = "color=#" + var11.substring(2);
            String var13 = var2.substring(var10, var10 + 6);
            var2 = var2.replaceFirst(var13, "<html><font " + var12 + ">");
         }

         if (!var1.getParent().isVisible()) {
            var1.putClientProperty("Synthetica.oldText", "");
            if (var1 instanceof JMenu) {
               var1.putClientProperty("Synthetica.oldHover", Boolean.FALSE);
            }
         }

         String var14 = (String)var1.getClientProperty("Synthetica.oldText");
         var1.putClientProperty("Synthetica.oldText", var2);
         boolean var15 = false;
         if (var1 instanceof JMenu) {
            var15 = var1.getClientProperty("Synthetica.oldHover");
            var1.putClientProperty("Synthetica.oldHover", var8);
         }

         View var16 = (View)var1.getClientProperty("html");
         if (!var2.equals(var14) && var16 != var1.getClientProperty("Synthetica.oldView") || var1 instanceof JMenu && var9 && !var8 && var15 != var8) {
            var1.putClientProperty("Synthetica.oldView", var16);
            updateHTMLRenderer(var1);
         }

      }
   }

   private static void updateHTMLRenderer(JMenuItem var0) {
      BasicHTML.updateRenderer(var0, (String)var0.getClientProperty("Synthetica.oldText"));
   }
}
