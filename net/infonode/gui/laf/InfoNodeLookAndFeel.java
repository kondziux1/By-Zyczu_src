package net.infonode.gui.laf;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import net.infonode.gui.icon.button.TreeIcon;
import net.infonode.gui.laf.ui.SlimComboBoxUI;
import net.infonode.util.ArrayUtil;
import net.infonode.util.ColorUtil;

public class InfoNodeLookAndFeel extends MetalLookAndFeel {
   public static final LookAndFeelInfo LOOK_AND_FEEL_INFO = new LookAndFeelInfo(
      "InfoNode",
      (InfoNodeLookAndFeel.class$net$infonode$gui$laf$InfoNodeLookAndFeel == null
            ? (InfoNodeLookAndFeel.class$net$infonode$gui$laf$InfoNodeLookAndFeel = class$("net.infonode.gui.laf.InfoNodeLookAndFeel"))
            : InfoNodeLookAndFeel.class$net$infonode$gui$laf$InfoNodeLookAndFeel)
         .getName()
   );
   private static MetalTheme oldMetalTheme;
   private transient InfoNodeLookAndFeelTheme theme;
   private transient DefaultMetalTheme defaultTheme = new InfoNodeLookAndFeel$1(this);

   public InfoNodeLookAndFeel() {
      this(new InfoNodeLookAndFeelTheme());
   }

   public InfoNodeLookAndFeel(InfoNodeLookAndFeelTheme var1) {
      super();
      this.theme = var1;
   }

   public InfoNodeLookAndFeelTheme getTheme() {
      return this.theme;
   }

   public void initialize() {
      super.initialize();
      if (oldMetalTheme == null) {
         try {
            oldMetalTheme = (MetalTheme)(class$javax$swing$plaf$metal$MetalLookAndFeel == null
                  ? (class$javax$swing$plaf$metal$MetalLookAndFeel = class$("javax.swing.plaf.metal.MetalLookAndFeel"))
                  : class$javax$swing$plaf$metal$MetalLookAndFeel)
               .getMethod("getCurrentTheme", null)
               .invoke(null, null);
         } catch (NoSuchMethodException var2) {
         } catch (IllegalAccessException var3) {
         } catch (InvocationTargetException var4) {
         }
      }

      setCurrentTheme(this.defaultTheme);
   }

   public void uninitialize() {
      setCurrentTheme((MetalTheme)(oldMetalTheme == null ? new DefaultMetalTheme() : oldMetalTheme));
      oldMetalTheme = null;
   }

   public String getName() {
      return LOOK_AND_FEEL_INFO.getName();
   }

   public String getDescription() {
      return "A slim look and feel based on Metal.";
   }

   protected void initClassDefaults(UIDefaults var1) {
      super.initClassDefaults(var1);

      try {
         Class var2 = class$net$infonode$gui$laf$ui$SlimSplitPaneUI == null
            ? (class$net$infonode$gui$laf$ui$SlimSplitPaneUI = class$("net.infonode.gui.laf.ui.SlimSplitPaneUI"))
            : class$net$infonode$gui$laf$ui$SlimSplitPaneUI;
         var1.put("SplitPaneUI", var2.getName());
         var1.put(var2.getName(), var2);
         var2 = class$net$infonode$gui$laf$ui$SlimInternalFrameUI == null
            ? (class$net$infonode$gui$laf$ui$SlimInternalFrameUI = class$("net.infonode.gui.laf.ui.SlimInternalFrameUI"))
            : class$net$infonode$gui$laf$ui$SlimInternalFrameUI;
         var1.put("InternalFrameUI", var2.getName());
         var1.put(var2.getName(), var2);
         var2 = class$net$infonode$gui$laf$ui$SlimComboBoxUI == null
            ? (class$net$infonode$gui$laf$ui$SlimComboBoxUI = class$("net.infonode.gui.laf.ui.SlimComboBoxUI"))
            : class$net$infonode$gui$laf$ui$SlimComboBoxUI;
         SlimComboBoxUI.NORMAL_BORDER = this.theme.getListItemBorder();
         SlimComboBoxUI.FOCUS_BORDER = this.theme.getListFocusedItemBorder();
         var1.put("ComboBoxUI", var2.getName());
         var1.put(var2.getName(), var2);
         var2 = class$net$infonode$gui$laf$ui$SlimMenuItemUI == null
            ? (class$net$infonode$gui$laf$ui$SlimMenuItemUI = class$("net.infonode.gui.laf.ui.SlimMenuItemUI"))
            : class$net$infonode$gui$laf$ui$SlimMenuItemUI;
         var1.put("MenuItemUI", var2.getName());
         var1.put(var2.getName(), var2);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   protected void initComponentDefaults(UIDefaults var1) {
      super.initComponentDefaults(var1);
      Class var2 = class$javax$swing$plaf$metal$MetalLookAndFeel == null
         ? (class$javax$swing$plaf$metal$MetalLookAndFeel = class$("javax.swing.plaf.metal.MetalLookAndFeel"))
         : class$javax$swing$plaf$metal$MetalLookAndFeel;
      InfoNodeLookAndFeel$2 var3 = new InfoNodeLookAndFeel$2(this);
      Object[] var4 = new Object[]{
         "SplitPane.dividerSize",
         new Integer(this.theme.getSplitPaneDividerSize()),
         "TabbedPane.background",
         this.theme.getControlLightShadowColor(),
         "ComboBox.selectionBackground",
         this.theme.getSelectedMenuBackgroundColor(),
         "ComboBox.selectionForeground",
         this.theme.getSelectedMenuForegroundColor(),
         "List.cellRenderer",
         new InfoNodeLookAndFeel$3(this),
         "ToolTip.foreground",
         this.theme.getTooltipForegroundColor(),
         "ToolTip.background",
         this.theme.getTooltipBackgroundColor(),
         "Viewport.background",
         this.theme.getBackgroundColor(),
         "ScrollBar.background",
         this.theme.getScrollBarBackgroundColor(),
         "ScrollBar.shadow",
         this.theme.getScrollBarBackgroundShadowColor(),
         "ScrollBar.width",
         new Integer(this.theme.getScrollBarWidth()),
         "Table.focusCellBackground",
         new ColorUIResource(ColorUtil.mult(this.theme.getSelectedMenuBackgroundColor(), 1.4F)),
         "Table.focusCellForeground",
         this.theme.getSelectedMenuForegroundColor(),
         "TableHeader.cellBorder",
         this.theme.getTableHeaderCellBorder(),
         "InternalFrame.activeTitleBackground",
         this.theme.getActiveInternalFrameTitleBackgroundColor(),
         "InternalFrame.activeTitleForeground",
         this.theme.getActiveInternalFrameTitleForegroundColor(),
         "InternalFrame.activeTitleGradient",
         this.theme.getActiveInternalFrameTitleGradientColor(),
         "InternalFrame.inactiveTitleBackground",
         this.theme.getInactiveInternalFrameTitleBackgroundColor(),
         "InternalFrame.inactiveTitleForeground",
         this.theme.getInactiveInternalFrameTitleForegroundColor(),
         "InternalFrame.inactiveTitleGradient",
         this.theme.getInactiveInternalFrameTitleGradientColor(),
         "InternalFrame.icon",
         this.theme.getInternalFrameIcon(),
         "InternalFrame.iconifyIcon",
         this.theme.getInternalFrameIconifyIcon(),
         "InternalFrame.minimizeIcon",
         this.theme.getInternalFrameMinimizeIcon(),
         "InternalFrame.maximizeIcon",
         this.theme.getInternalFrameMaximizeIcon(),
         "InternalFrame.closeIcon",
         this.theme.getInternalFrameCloseIcon(),
         "InternalFrame.border",
         this.theme.getInternalFrameBorder(),
         "InternalFrame.titleFont",
         this.theme.getInternalFrameTitleFont(),
         "MenuBar.border",
         this.theme.getMenuBarBorder(),
         "MenuItem.border",
         var3,
         "Menu.border",
         var3,
         "Spinner.border",
         this.theme.getTextFieldBorder(),
         "Spinner.background",
         new ColorUIResource(this.theme.getBackgroundColor()),
         "PopupMenu.border",
         this.theme.getPopupMenuBorder(),
         "TextField.border",
         this.theme.getTextFieldBorder(),
         "FormattedTextField.border",
         this.theme.getTextFieldBorder(),
         "Button.textShiftOffset",
         new Integer(2),
         "Button.select",
         this.theme.getControlLightShadowColor(),
         "Button.margin",
         this.theme.getButtonMargin(),
         "Button.disabledText",
         this.theme.getInactiveTextColor(),
         "ToggleButton.margin",
         this.theme.getButtonMargin(),
         "ToggleButton.select",
         this.theme.getControlLightShadowColor(),
         "ToggleButton.textShiftOffset",
         new Integer(2),
         "Tree.openIcon",
         this.theme.getTreeOpenIcon(),
         "Tree.closedIcon",
         this.theme.getTreeClosedIcon(),
         "Tree.leafIcon",
         this.theme.getTreeLeafIcon(),
         "Tree.collapsedIcon",
         new IconUIResource(new TreeIcon(1, 10, 10, true, this.theme.getTextColor(), this.theme.getTreeIconBackgroundColor())),
         "Tree.expandedIcon",
         new IconUIResource(new TreeIcon(2, 10, 10, true, this.theme.getTextColor(), this.theme.getTreeIconBackgroundColor())),
         "Tree.leftChildIndent",
         new Integer(5),
         "Tree.rightChildIndent",
         new Integer(11),
         "OptionPane.errorIcon",
         LookAndFeel.makeIcon(var2, "icons/Error.gif"),
         "OptionPane.informationIcon",
         LookAndFeel.makeIcon(var2, "icons/Inform.gif"),
         "OptionPane.warningIcon",
         LookAndFeel.makeIcon(var2, "icons/Warn.gif"),
         "OptionPane.questionIcon",
         LookAndFeel.makeIcon(var2, "icons/Question.gif"),
         "OptionPane.buttonFont",
         this.theme.getOptionPaneButtonFont()
      };
      var1.putDefaults(var4);
   }

   public static void install() {
      if (!ArrayUtil.contains(UIManager.getInstalledLookAndFeels(), LOOK_AND_FEEL_INFO)) {
         UIManager.installLookAndFeel(LOOK_AND_FEEL_INFO);
      }

   }

   private static class MyListCellRenderer extends DefaultListCellRenderer {
      private Border normalBorder;
      private Border focusBorder;

      MyListCellRenderer(Border var1, Border var2) {
         super();
         this.normalBorder = var1;
         this.focusBorder = var2;
      }

      public Component getListCellRendererComponent(JList var1, Object var2, int var3, boolean var4, boolean var5) {
         JLabel var6 = (JLabel)super.getListCellRendererComponent(var1, var2, var3, var4, var5);
         var6.setBorder(var5 ? this.focusBorder : this.normalBorder);
         return var6;
      }

      public static class UIResource extends InfoNodeLookAndFeel.MyListCellRenderer implements javax.swing.plaf.UIResource {
         public UIResource(Border var1, Border var2) {
            super(var1, var2);
         }
      }
   }
}
