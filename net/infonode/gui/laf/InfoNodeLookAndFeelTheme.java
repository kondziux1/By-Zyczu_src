package net.infonode.gui.laf;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.InsetsUIResource;
import net.infonode.gui.border.EdgeBorder;
import net.infonode.gui.border.EtchedLineBorder;
import net.infonode.gui.border.HighlightBorder;
import net.infonode.gui.border.PopupMenuBorder;
import net.infonode.gui.icon.EmptyIcon;
import net.infonode.gui.icon.button.BorderIcon;
import net.infonode.gui.icon.button.CloseIcon;
import net.infonode.gui.icon.button.MaximizeIcon;
import net.infonode.gui.icon.button.MinimizeIcon;
import net.infonode.gui.icon.button.RestoreIcon;
import net.infonode.gui.icon.button.WindowIcon;
import net.infonode.gui.laf.value.BorderValue;
import net.infonode.gui.laf.value.ColorValue;
import net.infonode.gui.laf.value.FontValue;
import net.infonode.util.ArrayUtil;
import net.infonode.util.ColorUtil;

public class InfoNodeLookAndFeelTheme {
   private static final float PRIMARY_HUE = 0.61F;
   private static final float PRIMARY_SATURATION = 0.6F;
   private static final float PRIMARY_BRIGHTNESS = 0.67F;
   public static final Color DEFAULT_CONTROL_COLOR = Color.getHSBColor(0.12F, 0.058F, 0.89F);
   public static final Color DEFAULT_PRIMARY_CONTROL_COLOR = Color.getHSBColor(0.61F, 0.6F, 1.0F);
   public static final Color DEFAULT_BACKGROUND_COLOR = new Color(250, 250, 247);
   public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
   public static final Color DEFAULT_SELECTED_BACKGROUND_COLOR = Color.getHSBColor(0.61F, 0.70000005F, 0.67F);
   public static final Color DEFAULT_SELECTED_TEXT_COLOR = Color.WHITE;
   public static final Color DEFAULT_TOOLTIP_BACKGROUND_COLOR = new Color(255, 255, 180);
   public static final Color DEFAULT_TOOLTIP_FOREGROUND_COLOR = Color.BLACK;
   public static final Color DEFAULT_DESKTOP_COLOR = Color.getHSBColor(0.59000003F, 0.6F, 0.67F);
   public static final int DEFAULT_FONT_SIZE = 11;
   private static final String[] FONT_NAMES = new String[]{"Dialog"};
   private FontUIResource font = new FontUIResource("Dialog", 0, 11);
   private FontUIResource boldFont;
   private ColorValue controlColor = new ColorValue();
   private ColorValue primaryControlColor = new ColorValue();
   private ColorValue backgroundColor = new ColorValue();
   private ColorValue textColor = new ColorValue();
   private ColorValue selectedTextBackgroundColor = new ColorValue();
   private ColorValue focusColor = new ColorValue();
   private ColorValue selectedTextColor = new ColorValue();
   private ColorValue tooltipBackgroundColor = new ColorValue(DEFAULT_TOOLTIP_BACKGROUND_COLOR);
   private ColorValue tooltipForegroundColor = new ColorValue(DEFAULT_TOOLTIP_FOREGROUND_COLOR);
   private ColorValue desktopColor = new ColorValue(DEFAULT_DESKTOP_COLOR);
   private ColorValue treeIconBackgroundColor = new ColorValue();
   private ColorValue selectedMenuBackgroundColor = new ColorValue();
   private ColorValue selectedMenuForegroundColor = new ColorValue();
   private ColorValue inactiveTextColor = new ColorValue();
   private ColorUIResource controlHighlightColor;
   private ColorUIResource controlLightShadowColor;
   private ColorUIResource controlShadowColor;
   private ColorUIResource controlDarkShadowColor;
   private ColorUIResource primaryControlHighlightColor;
   private ColorUIResource primaryControlShadowColor;
   private ColorUIResource primaryControlDarkShadowColor;
   private ColorValue scrollBarBackgroundColor = new ColorValue();
   private ColorUIResource scrollBarBackgroundShadowColor;
   private ColorValue activeInternalFrameTitleBackgroundColor = new ColorValue();
   private ColorValue activeInternalFrameTitleGradientColor = new ColorValue();
   private ColorValue activeInternalFrameTitleForegroundColor = new ColorValue();
   private ColorValue inactiveInternalFrameTitleBackgroundColor = new ColorValue();
   private ColorValue inactiveInternalFrameTitleGradientColor = new ColorValue();
   private ColorValue inactiveInternalFrameTitleForegroundColor = new ColorValue();
   private IconUIResource internalFrameIcon = new IconUIResource(new BorderIcon(new WindowIcon(Color.BLACK, 12), 2));
   private IconUIResource internalFrameIconifyIcon = new IconUIResource(new MinimizeIcon());
   private IconUIResource internalFrameMinimizeIcon = new IconUIResource(new RestoreIcon());
   private IconUIResource internalFrameMaximizeIcon = new IconUIResource(new MaximizeIcon());
   private IconUIResource internalFrameCloseIcon = new IconUIResource(new CloseIcon());
   private BorderUIResource internalFrameBorder = new BorderUIResource(new LineBorder(Color.BLACK, 2));
   private FontValue internalFrameTitleFont = new FontValue();
   private FontValue optionPaneButtonFont = new FontValue();
   private IconUIResource treeOpenIcon = new IconUIResource(EmptyIcon.INSTANCE);
   private IconUIResource treeClosedIcon = new IconUIResource(EmptyIcon.INSTANCE);
   private IconUIResource treeLeafIcon = new IconUIResource(EmptyIcon.INSTANCE);
   private BorderValue menuBarBorder = new BorderValue();
   private BorderValue popupMenuBorder = new BorderValue();
   private BorderValue tableHeaderCellBorder = new BorderValue();
   private BorderValue textFieldBorder = new BorderValue();
   private BorderValue listItemBorder = new BorderValue(new EmptyBorder(1, 4, 1, 4));
   private BorderValue listFocusedItemBorder = new BorderValue();
   private int splitPaneDividerSize = 7;
   private int scrollBarWidth = 17;
   private InsetsUIResource buttonMargin = new InsetsUIResource(1, 6, 1, 6);
   private double shadingFactor = 1.6;
   private String name;

   public InfoNodeLookAndFeelTheme() {
      this(
         "Default Theme",
         DEFAULT_CONTROL_COLOR,
         DEFAULT_PRIMARY_CONTROL_COLOR,
         DEFAULT_BACKGROUND_COLOR,
         DEFAULT_TEXT_COLOR,
         DEFAULT_SELECTED_BACKGROUND_COLOR,
         DEFAULT_SELECTED_TEXT_COLOR
      );
   }

   public InfoNodeLookAndFeelTheme(String var1, Color var2, Color var3, Color var4, Color var5) {
      this(var1, var2, var3, var4, var5, var3, ColorUtil.getOpposite(var3));
   }

   public InfoNodeLookAndFeelTheme(String var1, Color var2, Color var3, Color var4, Color var5, Color var6, Color var7) {
      this(var1, var2, var3, var4, var5, var6, var7, 1.3);
   }

   public InfoNodeLookAndFeelTheme(String var1, Color var2, Color var3, Color var4, Color var5, Color var6, Color var7, double var8) {
      super();
      this.name = var1;
      String[] var10 = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

      for(int var11 = 0; var11 < FONT_NAMES.length; ++var11) {
         if (ArrayUtil.containsEqual(var10, FONT_NAMES[var11])) {
            this.font = new FontUIResource(new Font(FONT_NAMES[var11], 0, 11));
            break;
         }
      }

      this.updateFonts();
      this.controlColor.setColor(var2);
      this.primaryControlColor.setColor(var3);
      this.backgroundColor.setColor(var4);
      this.selectedTextBackgroundColor.setColor(var6);
      this.selectedTextColor.setColor(var7);
      this.textColor.setColor(var5);
      this.shadingFactor = var8;
      this.updateColors();
   }

   private void updateFonts() {
      this.boldFont = new FontUIResource(this.font.deriveFont(1));
      this.internalFrameTitleFont.setDefaultFont(this.boldFont);
      this.optionPaneButtonFont.setDefaultFont(this.boldFont);
   }

   private void updateColors() {
      this.focusColor.setDefaultColor(ColorUtil.blend(this.controlColor.getColor(), this.textColor.getColor(), 0.5));
      this.inactiveTextColor.setDefaultColor(this.focusColor);
      double var1 = 1.0 / (1.0 + this.shadingFactor * 1.2);
      double var3 = 1.0 / (1.0 + this.shadingFactor / 2.0);
      double var5 = 1.0 / (1.0 + this.shadingFactor / 7.0);
      this.controlHighlightColor = new ColorUIResource(ColorUtil.mult(this.controlColor.getColor(), 1.0 + this.shadingFactor));
      this.controlLightShadowColor = new ColorUIResource(ColorUtil.mult(this.controlColor.getColor(), var5));
      this.controlShadowColor = new ColorUIResource(ColorUtil.mult(this.controlColor.getColor(), var3));
      this.controlDarkShadowColor = new ColorUIResource(ColorUtil.mult(this.controlColor.getColor(), var1));
      this.primaryControlHighlightColor = this.controlHighlightColor;
      this.primaryControlShadowColor = new ColorUIResource(ColorUtil.mult(this.primaryControlColor.getColor(), var3));
      this.primaryControlDarkShadowColor = new ColorUIResource(ColorUtil.mult(this.primaryControlColor.getColor(), var1));
      this.scrollBarBackgroundColor.setDefaultColor(this.controlLightShadowColor);
      this.scrollBarBackgroundShadowColor = new ColorUIResource(ColorUtil.mult(this.scrollBarBackgroundColor.getColor(), var1));
      this.selectedMenuBackgroundColor.setDefaultColor(this.selectedTextBackgroundColor);
      this.selectedMenuForegroundColor.setDefaultColor(this.selectedTextColor);
      this.treeIconBackgroundColor.setDefaultColor(ColorUtil.blend(this.backgroundColor.getColor(), this.primaryControlColor.getColor(), 0.15F));
      this.activeInternalFrameTitleBackgroundColor
         .setDefaultColor(ColorUtil.blend(this.primaryControlColor.getColor(), ColorUtil.getOpposite(this.getTextColor()), 0.5));
      this.activeInternalFrameTitleForegroundColor.setDefaultColor(this.getTextColor());
      this.activeInternalFrameTitleGradientColor.setDefaultColor(ColorUtil.mult(this.activeInternalFrameTitleBackgroundColor.getColor(), 1.2));
      this.inactiveInternalFrameTitleBackgroundColor.setDefaultColor(this.controlLightShadowColor);
      this.inactiveInternalFrameTitleForegroundColor.setDefaultColor(this.getTextColor());
      this.inactiveInternalFrameTitleGradientColor.setDefaultColor(ColorUtil.mult(this.inactiveInternalFrameTitleBackgroundColor.getColor(), 1.2));
      this.menuBarBorder
         .setDefaultBorder(new BorderUIResource(new EtchedLineBorder(false, false, true, false, this.controlHighlightColor, this.controlDarkShadowColor)));
      this.popupMenuBorder.setDefaultBorder(new BorderUIResource(new PopupMenuBorder(this.controlHighlightColor, this.controlDarkShadowColor)));
      this.textFieldBorder
         .setDefaultBorder(new BorderUIResource(new CompoundBorder(new LineBorder(this.controlDarkShadowColor), new EmptyBorder(1, 2, 1, 2))));
      this.tableHeaderCellBorder
         .setDefaultBorder(
            new BorderUIResource(
               new CompoundBorder(
                  new CompoundBorder(
                     new EdgeBorder(this.controlDarkShadowColor, false, true, false, true), new HighlightBorder(false, this.controlHighlightColor)
                  ),
                  new EmptyBorder(1, 4, 1, 4)
               )
            )
         );
      this.listFocusedItemBorder.setDefaultBorder(new CompoundBorder(new LineBorder(this.focusColor.getColor()), new EmptyBorder(0, 3, 0, 3)));
   }

   public String getName() {
      return this.name;
   }

   public double getShadingFactor() {
      return this.shadingFactor;
   }

   public void setShadingFactor(double var1) {
      this.shadingFactor = var1;
      this.updateColors();
   }

   public FontUIResource getFont() {
      return this.font;
   }

   public void setFont(FontUIResource var1) {
      this.font = var1;
      this.updateFonts();
   }

   public ColorUIResource getControlColor() {
      return this.controlColor.getColor();
   }

   public ColorUIResource getPrimaryControlColor() {
      return this.primaryControlColor.getColor();
   }

   public ColorUIResource getBackgroundColor() {
      return this.backgroundColor.getColor();
   }

   public ColorUIResource getTextColor() {
      return this.textColor.getColor();
   }

   public ColorUIResource getSelectedTextBackgroundColor() {
      return this.selectedTextBackgroundColor.getColor();
   }

   public ColorUIResource getFocusColor() {
      return this.focusColor.getColor();
   }

   public ColorUIResource getSelectedTextColor() {
      return this.selectedTextColor.getColor();
   }

   public ColorUIResource getTooltipBackgroundColor() {
      return this.tooltipBackgroundColor.getColor();
   }

   public ColorUIResource getDesktopColor() {
      return this.desktopColor.getColor();
   }

   public ColorUIResource getTreeIconBackgroundColor() {
      return this.treeIconBackgroundColor.getColor();
   }

   public ColorUIResource getSelectedMenuBackgroundColor() {
      return this.selectedMenuBackgroundColor.getColor();
   }

   public ColorUIResource getSelectedMenuForegroundColor() {
      return this.selectedMenuForegroundColor.getColor();
   }

   public ColorUIResource getInactiveTextColor() {
      return this.inactiveTextColor.getColor();
   }

   public ColorUIResource getControlHighlightColor() {
      return this.controlHighlightColor;
   }

   public ColorUIResource getControlLightShadowColor() {
      return this.controlLightShadowColor;
   }

   public ColorUIResource getControlShadowColor() {
      return this.controlShadowColor;
   }

   public ColorUIResource getControlDarkShadowColor() {
      return this.controlDarkShadowColor;
   }

   public ColorUIResource getPrimaryControlHighlightColor() {
      return this.primaryControlHighlightColor;
   }

   public ColorUIResource getPrimaryControlShadowColor() {
      return this.primaryControlShadowColor;
   }

   public ColorUIResource getPrimaryControlDarkShadowColor() {
      return this.primaryControlDarkShadowColor;
   }

   public ColorUIResource getScrollBarBackgroundColor() {
      return this.scrollBarBackgroundColor.getColor();
   }

   public ColorUIResource getScrollBarBackgroundShadowColor() {
      return this.scrollBarBackgroundShadowColor;
   }

   public ColorUIResource getActiveInternalFrameTitleBackgroundColor() {
      return this.activeInternalFrameTitleBackgroundColor.getColor();
   }

   public ColorUIResource getActiveInternalFrameTitleForegroundColor() {
      return this.activeInternalFrameTitleForegroundColor.getColor();
   }

   public ColorUIResource getActiveInternalFrameTitleGradientColor() {
      return this.activeInternalFrameTitleGradientColor.getColor();
   }

   public ColorUIResource getInactiveInternalFrameTitleBackgroundColor() {
      return this.inactiveInternalFrameTitleBackgroundColor.getColor();
   }

   public ColorUIResource getInactiveInternalFrameTitleForegroundColor() {
      return this.inactiveInternalFrameTitleForegroundColor.getColor();
   }

   public ColorUIResource getInactiveInternalFrameTitleGradientColor() {
      return this.inactiveInternalFrameTitleGradientColor.getColor();
   }

   public BorderUIResource getTableHeaderCellBorder() {
      return this.tableHeaderCellBorder.getBorder();
   }

   public IconUIResource getInternalFrameIcon() {
      return this.internalFrameIcon;
   }

   public void setInternalFrameIcon(IconUIResource var1) {
      this.internalFrameIcon = var1;
   }

   public IconUIResource getInternalFrameMinimizeIcon() {
      return this.internalFrameMinimizeIcon;
   }

   public void setInternalFrameMinimizeIcon(IconUIResource var1) {
      this.internalFrameMinimizeIcon = var1;
   }

   public IconUIResource getInternalFrameMaximizeIcon() {
      return this.internalFrameMaximizeIcon;
   }

   public void setInternalFrameMaximizeIcon(IconUIResource var1) {
      this.internalFrameMaximizeIcon = var1;
   }

   public IconUIResource getInternalFrameCloseIcon() {
      return this.internalFrameCloseIcon;
   }

   public void setInternalFrameCloseIcon(IconUIResource var1) {
      this.internalFrameCloseIcon = var1;
   }

   public BorderUIResource getInternalFrameBorder() {
      return this.internalFrameBorder;
   }

   public void setInternalFrameBorder(BorderUIResource var1) {
      this.internalFrameBorder = var1;
   }

   public FontUIResource getInternalFrameTitleFont() {
      return this.internalFrameTitleFont.getFont();
   }

   public void setInternalFrameTitleFont(FontUIResource var1) {
      this.internalFrameTitleFont.setFont(var1);
   }

   public void setControlColor(Color var1) {
      this.controlColor.setColor(var1);
      this.updateColors();
   }

   public void setPrimaryControlColor(Color var1) {
      this.primaryControlColor.setColor(var1);
      this.updateColors();
   }

   public void setBackgroundColor(Color var1) {
      this.backgroundColor.setColor(var1);
      this.updateColors();
   }

   public void setTextColor(Color var1) {
      this.textColor.setColor(var1);
      this.updateColors();
   }

   public FontUIResource getOptionPaneButtonFont() {
      return this.optionPaneButtonFont.getFont();
   }

   public void setOptionPaneButtonFont(FontUIResource var1) {
      this.optionPaneButtonFont.setFont(var1);
   }

   public int getSplitPaneDividerSize() {
      return this.splitPaneDividerSize;
   }

   public void setSplitPaneDividerSize(int var1) {
      this.splitPaneDividerSize = var1;
   }

   public BorderUIResource getTextFieldBorder() {
      return this.textFieldBorder.getBorder();
   }

   public void setTextFieldBorder(BorderUIResource var1) {
      this.textFieldBorder.setBorder(var1);
   }

   public IconUIResource getTreeOpenIcon() {
      return this.treeOpenIcon;
   }

   public void setTreeOpenIcon(IconUIResource var1) {
      this.treeOpenIcon = var1;
   }

   public IconUIResource getTreeClosedIcon() {
      return this.treeClosedIcon;
   }

   public void setTreeClosedIcon(IconUIResource var1) {
      this.treeClosedIcon = var1;
   }

   public IconUIResource getTreeLeafIcon() {
      return this.treeLeafIcon;
   }

   public void setTreeLeafIcon(IconUIResource var1) {
      this.treeLeafIcon = var1;
   }

   public BorderUIResource getMenuBarBorder() {
      return this.menuBarBorder.getBorder();
   }

   public void setMenuBarBorder(BorderUIResource var1) {
      this.menuBarBorder.setBorder(var1);
   }

   public void setSelectedTextBackgroundColor(Color var1) {
      this.selectedTextBackgroundColor.setColor(var1);
      this.updateColors();
   }

   public void setFocusColor(Color var1) {
      this.focusColor.setColor(var1);
   }

   public void setSelectedTextColor(Color var1) {
      this.selectedTextColor.setColor(var1);
   }

   public void setTooltipBackgroundColor(Color var1) {
      this.tooltipBackgroundColor.setColor(var1);
   }

   public void setDesktopColor(Color var1) {
      this.desktopColor.setColor(var1);
      this.updateColors();
   }

   public void setTreeIconBackgroundColor(Color var1) {
      this.treeIconBackgroundColor.setColor(var1);
   }

   public void setSelectedMenuBackgroundColor(Color var1) {
      this.selectedMenuBackgroundColor.setColor(var1);
      this.updateColors();
   }

   public void setSelectedMenuForegroundColor(Color var1) {
      this.selectedMenuForegroundColor.setColor(var1);
   }

   public void setInactiveTextColor(Color var1) {
      this.inactiveTextColor.setColor(var1);
   }

   public void setScrollBarBackgroundColor(Color var1) {
      this.scrollBarBackgroundColor.setColor(var1);
      this.updateColors();
   }

   public void setActiveInternalFrameTitleBackgroundColor(Color var1) {
      this.activeInternalFrameTitleBackgroundColor.setColor(var1);
      this.updateColors();
   }

   public void setActiveInternalFrameTitleForegroundColor(Color var1) {
      this.activeInternalFrameTitleForegroundColor.setColor(var1);
      this.updateColors();
   }

   public void setActiveInternalFrameTitleGradientColor(Color var1) {
      this.activeInternalFrameTitleGradientColor.setColor(var1);
      this.updateColors();
   }

   public void setInactiveInternalFrameTitleBackgroundColor(Color var1) {
      this.inactiveInternalFrameTitleBackgroundColor.setColor(var1);
      this.updateColors();
   }

   public void setInactiveInternalFrameTitleForegroundColor(Color var1) {
      this.inactiveInternalFrameTitleForegroundColor.setColor(var1);
      this.updateColors();
   }

   public void setInactiveInternalFrameTitleGradientColor(Color var1) {
      this.inactiveInternalFrameTitleGradientColor.setColor(var1);
      this.updateColors();
   }

   public void setInternalFrameTitleFont(Font var1) {
      this.internalFrameTitleFont.setFont(var1);
   }

   public void setOptionPaneButtonFont(Font var1) {
      this.optionPaneButtonFont.setFont(var1);
   }

   public void setTableHeaderCellBorder(BorderUIResource var1) {
      this.tableHeaderCellBorder.setBorder(var1);
   }

   public int getScrollBarWidth() {
      return this.scrollBarWidth;
   }

   public void setScrollBarWidth(int var1) {
      this.scrollBarWidth = var1;
   }

   public InsetsUIResource getButtonMargin() {
      return this.buttonMargin;
   }

   public void setButtonMargin(InsetsUIResource var1) {
      this.buttonMargin = var1;
   }

   public BorderUIResource getPopupMenuBorder() {
      return this.popupMenuBorder.getBorder();
   }

   public void setPopupMenuBorder(BorderUIResource var1) {
      this.popupMenuBorder.setBorder(var1);
   }

   public IconUIResource getInternalFrameIconifyIcon() {
      return this.internalFrameIconifyIcon;
   }

   public void setInternalFrameIconifyIcon(IconUIResource var1) {
      this.internalFrameIconifyIcon = var1;
   }

   public ColorUIResource getTooltipForegroundColor() {
      return this.tooltipForegroundColor.getColor();
   }

   public void setTooltipForegroundColor(ColorUIResource var1) {
      this.tooltipForegroundColor.setColor(var1);
      this.updateColors();
   }

   public BorderUIResource getListItemBorder() {
      return this.listItemBorder.getBorder();
   }

   public void setListItemBorder(BorderUIResource var1) {
      this.listItemBorder.setBorder(var1);
   }

   public BorderUIResource getListFocusedItemBorder() {
      return this.listFocusedItemBorder.getBorder();
   }

   public void setListFocusedItemBorder(BorderUIResource var1) {
      this.listFocusedItemBorder.setBorder(var1);
   }
}
