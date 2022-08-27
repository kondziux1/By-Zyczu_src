package de.javasoft.plaf.synthetica;

import de.javasoft.util.JavaVersion;
import de.javasoft.util.OS;
import java.awt.Insets;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.synth.SynthContext;
import sun.swing.DefaultLookup;
import sun.swing.plaf.synth.SynthUI;

public class SyntheticaDefaultLookup extends DefaultLookup {
   private static Class<?> synthUI;

   public SyntheticaDefaultLookup() {
      super();

      try {
         if (!JavaVersion.JAVA5 && !JavaVersion.JAVA6) {
            synthUI = Class.forName("javax.swing.plaf.synth.SynthUI");
         } else {
            synthUI = Class.forName("sun.swing.plaf.synth.SynthUI");
         }
      } catch (ClassNotFoundException var2) {
         new RuntimeException(var2);
      }

   }

   public Object getDefault(JComponent var1, ComponentUI var2, String var3) {
      if (!"ToggleButton.focusInputMap".equals(var3) && !"RadioButton.focusInputMap".equals(var3) && !"CheckBox.focusInputMap".equals(var3)) {
         if (OS.getCurrentOS() == OS.Mac
            && this.isSynthUI(var2)
            && SyntheticaLookAndFeel.getBoolean("Synthetica.metaKeySupportOnMacEnabled", var1, true)
            && (
               "TextField.focusInputMap".equals(var3)
                  || "FormattedTextField.focusInputMap".equals(var3)
                  || "PasswordField.focusInputMap".equals(var3)
                  || "TextArea.focusInputMap".equals(var3)
                  || "TextPane.focusInputMap".equals(var3)
                  || "EditorPane.focusInputMap".equals(var3)
                  || "List.focusInputMap".equals(var3)
                  || "Tree.focusInputMap".equals(var3)
                  || "Spinner.focusInputMap".equals(var3)
            )) {
            SynthContext var11 = this.getContext(var2, var1);
            InputMap var13 = (InputMap)var11.getStyle().get(var11, var3);
            LookAndFeel.loadKeyBindings(
               var13, new Object[]{"meta X", "cut-to-clipboard", "meta C", "copy-to-clipboard", "meta V", "paste-from-clipboard", "meta A", "select-all"}
            );
            if ("TextField.focusInputMap".equals(var3)
               || "FormattedTextField.focusInputMap".equals(var3)
               || "PasswordField.focusInputMap".equals(var3)
               || "TextArea.focusInputMap".equals(var3)
               || "TextPane.focusInputMap".equals(var3)
               || "EditorPane.focusInputMap".equals(var3)) {
               Object[] var15 = this.asArray(this.getMacMap4JTextComponent());
               LookAndFeel.loadKeyBindings(var13, var15);
            }

            return var13;
         } else if ("Button.defaultButtonFollowsFocus".equals(var3)) {
            return UIManager.getBoolean("Button.defaultButtonFollowsFocus");
         } else if ("SplitPane.oneTouchButtonOffset".equals(var3) && UIManager.getBoolean("Syntetica.splitPane.centerOneTouchButtons") && this.isSynthUI(var2)
            )
          {
            JSplitPane var10 = (JSplitPane)var1;
            Insets var12 = var10.getInsets();
            int var6 = 0;
            if (var10.getOrientation() == 0) {
               var6 = var10.getWidth() - var12.left - var12.right;
            } else {
               var6 = var10.getHeight() - var12.top - var12.bottom;
            }

            SynthContext var7 = this.getContext(var2, var1);
            int var8 = var7.getStyle().get(var7, "SplitPane.oneTouchButtonSize");
            int var9 = var10.getDividerSize();
            return var6 / 2 - var8 - var9;
         } else if ("OptionPane.buttonPadding".equals(var3)) {
            return UIManager.get("OptionPane.buttonPadding");
         } else if ("OptionPane.buttonOrientation".equals(var3)) {
            return UIManager.get("OptionPane.buttonOrientation");
         } else if ("ProgressBar.horizontalSize".equals(var3)) {
            return UIManager.get("ProgressBar.horizontalSize");
         } else if ("ProgressBar.vertictalSize".equals(var3) || "ProgressBar.verticalSize".equals(var3)) {
            return UIManager.get("ProgressBar.verticalSize");
         } else if ("Slider.tickColor".equals(var3) && !var1.isEnabled() && SyntheticaLookAndFeel.get("Slider.tickColor.disabled", var1) != null) {
            return SyntheticaLookAndFeel.get("Slider.tickColor.disabled", var1);
         } else if ("Slider.tickColor".equals(var3) && SyntheticaLookAndFeel.get("Slider.tickColor", var1) != null) {
            return SyntheticaLookAndFeel.get("Slider.tickColor", var1);
         } else if ("TabbedPane.labelShift".equals(var3)) {
            return SyntheticaLookAndFeel.getInt("TabbedPane.labelShift", var1, 1);
         } else if ("TabbedPane.selectedLabelShift".equals(var3)) {
            return SyntheticaLookAndFeel.getInt("TabbedPane.selectedLabelShift", var1, -1);
         } else {
            Object var4 = SyntheticaLookAndFeel.get(var3, var1);
            if (var4 != null) {
               return var4;
            } else {
               if (this.isSynthUI(var2)) {
                  SynthContext var5 = this.getContext(var2, var1);
                  if (var5 != null && var5.getStyle() != null) {
                     return var5.getStyle().get(var5, var3);
                  }
               }

               return super.getDefault(var1, var2, var3);
            }
         }
      } else {
         return LookAndFeel.makeInputMap(new Object[]{"SPACE", "pressed", "released SPACE", "released"});
      }
   }

   private boolean isSynthUI(Object var1) {
      return synthUI.isAssignableFrom(var1.getClass());
   }

   private SynthContext getContext(final Object var1, final JComponent var2) {
      Object var3 = null;
      SynthContext var4;
      if (!JavaVersion.JAVA5 && !JavaVersion.JAVA6) {
         var4 = (SynthContext)AccessController.doPrivileged(new PrivilegedAction<SynthContext>() {
            public SynthContext run() {
               try {
                  Method var1x = var1.getClass().getMethod("getContext", JComponent.class);
                  var1x.setAccessible(true);
                  return (SynthContext)var1x.invoke(var1, var2);
               } catch (Exception var2x) {
                  throw new RuntimeException(var2x);
               }
            }
         });
      } else {
         var4 = ((SynthUI)var1).getContext(var2);
      }

      return var4;
   }

   private Object[] asArray(Map<Object, Object> var1) {
      Object[] var2 = new Object[var1.size() * 2];
      int var3 = 0;

      for(Entry var4 : var1.entrySet()) {
         var2[var3++] = var4.getKey();
         var2[var3++] = var4.getValue();
      }

      return var2;
   }

   private Map<Object, Object> getMacMap4JTextComponent() {
      HashMap var1 = new HashMap();
      var1.put("meta A", "select-all");
      var1.put("meta LEFT", "caret-begin-line");
      var1.put("meta KP_LEFT", "caret-begin-line");
      var1.put("meta RIGHT", "caret-end-line");
      var1.put("meta KP_RIGHT", "caret-end-line");
      var1.put("meta UP", "caret-begin");
      var1.put("meta KP_UP", "caret-begin");
      var1.put("meta DOWN", "caret-end");
      var1.put("meta KP_DOWN", "caret-end");
      var1.put("meta shift LEFT", "selection-begin-line");
      var1.put("meta shift KP_LEFT", "selection-begin-line");
      var1.put("meta shift RIGHT", "selection-end-line");
      var1.put("meta shift KP_RIGHT", "selection-end-line");
      var1.put("meta shift UP", "selection-begin");
      var1.put("meta shift KP_UP", "selection-begin");
      var1.put("meta shift DOWN", "selection-end");
      var1.put("meta shift KP_DOWN", "selection-end");
      var1.put("meta shift PAGE_UP", "selection-page-up");
      var1.put("meta shift PAGE_DOWN", "selection-page-down");
      var1.put("meta BACK_SLASH", "unselect");
      var1.put("meta T", "next-link-action");
      var1.put("meta shift T", "previous-link-action");
      var1.put("meta SPACE", "activate-link-action");
      var1.put("ctrl H", "delete-previous");
      var1.put("ctrl D", "delete-next");
      var1.put("ctrl W", "delete-previous-word");
      var1.put("ctrl F", "caret-forward");
      var1.put("ctrl B", "caret-backward");
      var1.put("alt RIGHT", "caret-next-word");
      var1.put("alt KP_RIGHT", "caret-next-word");
      var1.put("alt LEFT", "caret-previous-word");
      var1.put("alt KP_LEFT", "caret-previous-word");
      var1.put("alt DELETE", "delete-next-word");
      var1.put("alt BACK_SPACE", "delete-previous-word");
      var1.put("shift alt RIGHT", "selection-next-word");
      var1.put("shift alt KP_RIGHT", "selection-next-word");
      var1.put("shift alt LEFT", "selection-previous-word");
      var1.put("shift alt KP_LEFT", "selection-previous-word");
      Map var2 = (Map)UIManager.get("Synthetica.textComponent.macFocusInputMap");
      if (var2 != null) {
         var1.putAll(var2);
      }

      return var1;
   }
}
