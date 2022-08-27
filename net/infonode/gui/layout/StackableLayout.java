package net.infonode.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager2;
import net.infonode.gui.ComponentUtil;

public class StackableLayout implements LayoutManager2 {
   private Container container;
   private Component component;
   private boolean autoShowFirstComponent = true;
   private boolean useSelectedComponentSize;

   public StackableLayout(Container var1) {
      super();
      this.container = var1;
   }

   public boolean usesSelectedComponentSize() {
      return this.useSelectedComponentSize;
   }

   public boolean isAutoShowFirstComponent() {
      return this.autoShowFirstComponent;
   }

   public void setAutoShowFirstComponent(boolean var1) {
      this.autoShowFirstComponent = var1;
   }

   public void setUseSelectedComponentSize(boolean var1) {
      if (this.useSelectedComponentSize != var1) {
         this.useSelectedComponentSize = var1;
         ComponentUtil.validate(this.container);
      }

   }

   public Dimension maximumLayoutSize(Container var1) {
      return LayoutUtil.add(LayoutUtil.getMinMaximumSize(var1.getComponents()), var1.getInsets());
   }

   public void invalidateLayout(Container var1) {
   }

   public float getLayoutAlignmentY(Container var1) {
      return 0.0F;
   }

   public float getLayoutAlignmentX(Container var1) {
      return 0.0F;
   }

   public void addLayoutComponent(Component var1, Object var2) {
      var1.setVisible(this.autoShowFirstComponent && var1.getParent().getComponentCount() == 1);
      if (var1.isVisible()) {
         this.component = var1;
      }

   }

   public void addLayoutComponent(String var1, Component var2) {
      this.addLayoutComponent(var2, null);
   }

   public void removeLayoutComponent(Component var1) {
      if (var1 == this.component) {
         this.component = null;
      }

      var1.setVisible(true);
   }

   public Dimension preferredLayoutSize(Container var1) {
      return LayoutUtil.add(
         this.useSelectedComponentSize
            ? (this.component == null ? new Dimension(0, 0) : this.component.getPreferredSize())
            : LayoutUtil.getMaxPreferredSize(var1.getComponents()),
         var1.getInsets()
      );
   }

   public Dimension minimumLayoutSize(Container var1) {
      return LayoutUtil.add(LayoutUtil.getMaxMinimumSize(var1.getComponents()), var1.getInsets());
   }

   public void layoutContainer(Container var1) {
      Component[] var2 = var1.getComponents();
      Insets var3 = var1.getInsets();
      Dimension var4 = LayoutUtil.getInteriorSize(var1);

      for(int var5 = 0; var5 < var2.length; ++var5) {
         var2[var5].setBounds(var3.left, var3.top, var4.width, var4.height);
      }

   }

   public Component getVisibleComponent() {
      return this.component;
   }

   public void showComponent(Component var1) {
      Component var2 = this.component;
      if (var2 != var1) {
         this.component = var1;
         boolean var3 = var2 != null && LayoutUtil.isDescendingFrom(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner(), var2);
         if (var2 != null) {
            var2.setVisible(false);
         }

         if (this.component != null) {
            this.component.setVisible(true);
            if (var3) {
               ComponentUtil.smartRequestFocus(this.component);
            }
         }

      }
   }
}
