package net.infonode.tabbedpanel;

import javax.swing.JComponent;
import javax.swing.JPanel;
import net.infonode.gui.layout.StackableLayout;

public class TabContentPanel extends JPanel {
   private TabbedPanel tabbedPanel;
   private StackableLayout layout = new StackableLayout(this);
   private TabListener listener = new TabContentPanel$1(this);

   public TabContentPanel() {
      super();
      this.setLayout(this.layout);
      this.setOpaque(false);
      this.layout.setAutoShowFirstComponent(false);
   }

   public TabContentPanel(TabbedPanel var1) {
      this();
      this.setTabbedPanel(var1);
   }

   public TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }

   public void setTabbedPanel(TabbedPanel var1) {
      if (this.tabbedPanel != var1) {
         if (this.tabbedPanel != null) {
            this.tabbedPanel.removeTabListener(this.listener);
            this.removeAll();
         }

         this.tabbedPanel = var1;
         if (this.tabbedPanel != null) {
            var1.addTabListener(this.listener);

            for(int var2 = 0; var2 < var1.getTabCount(); ++var2) {
               JComponent var3 = var1.getTabAt(var2).getContentComponent();
               if (var3 != null) {
                  this.add(var1.getTabAt(var2).getContentComponent());
               }
            }
         }
      }

   }
}
