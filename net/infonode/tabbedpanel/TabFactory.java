package net.infonode.tabbedpanel;

import javax.swing.Icon;
import javax.swing.JComponent;
import net.infonode.tabbedpanel.titledtab.TitledTab;

public class TabFactory {
   private TabFactory() {
      super();
   }

   public static TitledTab createTitledTab(String var0, Icon var1) {
      return new TitledTab(var0, var1, null, null);
   }

   public static TitledTab createTitledTab(String var0, Icon var1, JComponent var2) {
      return new TitledTab(var0, var1, var2, null);
   }

   public static TitledTab createTitledTab(String var0, Icon var1, JComponent var2, JComponent var3) {
      return new TitledTab(var0, var1, var2, var3);
   }

   public static TitledTab createTitledTab(String var0, Icon var1, Icon var2, Icon var3, JComponent var4, JComponent var5) {
      TitledTab var6 = new TitledTab(var0, var1, var4, var5);
      var6.getProperties().getHighlightedProperties().setIcon(var2);
      var6.getProperties().getDisabledProperties().setIcon(var3);
      return var6;
   }
}
