package net.infonode.docking.internal;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.infonode.docking.View;
import net.infonode.docking.internalutil.ButtonInfo;
import net.infonode.docking.internalutil.CloseButtonInfo;
import net.infonode.docking.internalutil.DockButtonInfo;
import net.infonode.docking.internalutil.InternalDockingUtil;
import net.infonode.docking.internalutil.MaximizeButtonInfo;
import net.infonode.docking.internalutil.MinimizeButtonInfo;
import net.infonode.docking.internalutil.RestoreButtonInfo;
import net.infonode.docking.internalutil.UndockButtonInfo;
import net.infonode.docking.properties.ViewTitleBarProperties;
import net.infonode.docking.properties.ViewTitleBarStateProperties;
import net.infonode.gui.ContentTitleBar;
import net.infonode.gui.DimensionProvider;
import net.infonode.gui.hover.hoverable.HoverManager;
import net.infonode.properties.gui.InternalPropertiesUtil;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMapListener;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;

public class ViewTitleBar extends ContentTitleBar {
   private static final ButtonInfo[] buttonInfos = new ButtonInfo[]{
      new UndockButtonInfo(ViewTitleBarStateProperties.UNDOCK_BUTTON_PROPERTIES),
      new DockButtonInfo(ViewTitleBarStateProperties.DOCK_BUTTON_PROPERTIES),
      new MinimizeButtonInfo(ViewTitleBarStateProperties.MINIMIZE_BUTTON_PROPERTIES),
      new MaximizeButtonInfo(ViewTitleBarStateProperties.MAXIMIZE_BUTTON_PROPERTIES),
      new RestoreButtonInfo(ViewTitleBarStateProperties.RESTORE_BUTTON_PROPERTIES),
      new CloseButtonInfo(ViewTitleBarStateProperties.CLOSE_BUTTON_PROPERTIES)
   };
   private DimensionProvider minimumSizeProvider;
   private View view;
   private AbstractButton[] buttons = new AbstractButton[buttonInfos.length];
   private List customBarComponents;
   private PropertyMapTreeListener propertiesListener = new ViewTitleBar$1(this);
   private PropertyMapListener buttonsListener = new ViewTitleBar$2(this);
   private int pressedCount = 0;
   private boolean dragOutside = false;

   public ViewTitleBar(View var1) {
      super(var1);
      this.view = var1;
      PropertyMapWeakListenerManager.addWeakTreeListener(var1.getViewProperties().getViewTitleBarProperties().getMap(), this.propertiesListener);
      PropertyMapWeakListenerManager.addWeakListener(var1.getWindowProperties().getMap(), this.buttonsListener);
      this.updateTitleBar(null);
      this.updateTitle();
      this.updateViewButtons(null);
      HoverManager.getInstance().addHoverable(this);
   }

   private void updateTitle() {
      ViewTitleBarStateProperties var1 = this.view.getViewProperties().getViewTitleBarProperties().getNormalProperties();
      this.getLabel().setText(var1.getTitleVisible() ? var1.getTitle() : null);
      this.getLabel().setIcon(var1.getIconVisible() ? var1.getIcon() : null);
   }

   private void updateTitleBar(Map var1) {
      this.updateTitle();
      JLabel var2 = this.getLabel();
      ViewTitleBarProperties var3 = this.view.getViewProperties().getViewTitleBarProperties();
      ShapedPanelProperties var4 = var3.getNormalProperties().getShapedPanelProperties();
      ComponentProperties var5 = var3.getNormalProperties().getComponentProperties();
      this.updateViewButtons(var1);
      if (var1 == null) {
         this.minimumSizeProvider = var3.getMinimumSizeProvider();
         var5.applyTo(this);

         for(int var6 = 0; var6 < this.buttons.length; ++var6) {
            if (this.buttons[var6] != null) {
               this.buttons[var6].setForeground(var5.getForegroundColor());
               this.buttons[var6].setBackground(var4.getComponentPainter() != null ? null : var5.getBackgroundColor());
            }
         }

         var2.setForeground(var5.getForegroundColor());
         var2.setFont(var5.getFont());
         var2.setIconTextGap(var3.getNormalProperties().getIconTextGap());
         InternalPropertiesUtil.applyTo(var4, this);
         this.setLayoutDirection(var3.getDirection());
         this.setHoverListener(var3.getHoverListener());
         this.setLabelAlignment(var3.getNormalProperties().getIconTextHorizontalAlignment());
      } else {
         Map var8 = (Map)var1.get(var3.getMap());
         if (var8 != null) {
            if (var8.containsKey(ViewTitleBarProperties.MINIMUM_SIZE_PROVIDER)) {
               this.minimumSizeProvider = var3.getMinimumSizeProvider();
               this.revalidate();
            }

            if (var8.containsKey(ViewTitleBarProperties.DIRECTION)) {
               this.setLayoutDirection(var3.getDirection());
            }

            if (var8.containsKey(ViewTitleBarProperties.HOVER_LISTENER)) {
               this.setHoverListener(var3.getHoverListener());
            }
         }

         var8 = (Map)var1.get(var3.getNormalProperties().getMap());
         if (var8 != null) {
            if (var8.containsKey(ViewTitleBarStateProperties.ICON_TEXT_GAP)) {
               var2.setIconTextGap(var3.getNormalProperties().getIconTextGap());
            }

            if (var8.containsKey(ViewTitleBarStateProperties.ICON_TEXT_HORIZONTAL_ALIGNMENT)) {
               this.setLabelAlignment(var3.getNormalProperties().getIconTextHorizontalAlignment());
            }
         }

         var8 = (Map)var1.get(var3.getNormalProperties().getComponentProperties().getMap());
         if (var8 != null) {
            var5.applyTo(this);
            var2.setForeground(var5.getForegroundColor());
            var2.setFont(var5.getFont());

            for(int var7 = 0; var7 < this.buttons.length; ++var7) {
               if (this.buttons[var7] != null) {
                  this.buttons[var7].setForeground(var5.getForegroundColor());
                  this.buttons[var7].setBackground(var4.getComponentPainter() != null ? null : var5.getBackgroundColor());
               }
            }
         }

         var8 = (Map)var1.get(var3.getNormalProperties().getShapedPanelProperties().getMap());
         if (var8 != null) {
            InternalPropertiesUtil.applyTo(var4, this);
         }
      }

   }

   public void updateViewButtons(Map var1) {
      InternalDockingUtil.updateButtons(
         buttonInfos, this.buttons, null, this.view, this.view.getViewProperties().getViewTitleBarProperties().getNormalProperties().getMap(), var1
      );
      if (this.shouldUpdateButtons()) {
         this.updateCustomBarComponents(this.customBarComponents);
      }

   }

   private boolean shouldUpdateButtons() {
      JComponent[] var1 = this.getRightTitleComponents();
      if (var1 != null && var1.length >= this.buttons.length) {
         for(int var2 = 0; var2 < this.buttons.length; ++var2) {
            if (var1[var1.length - var2 - 1] != this.buttons[var2]) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public void updateCustomBarComponents(List var1) {
      this.customBarComponents = var1;
      int var2 = var1 == null ? 0 : var1.size();
      JComponent[] var3 = new JComponent[var2 + this.buttons.length];
      Insets[] var4 = new Insets[var3.length];
      if (var1 != null) {
         for(int var5 = 0; var5 < var1.size(); ++var5) {
            var3[var5] = (JComponent)var1.get(var5);
            var4[var5] = var5 == 0
               ? new Insets(0, 0, 0, 0)
               : new Insets(0, this.view.getViewProperties().getViewTitleBarProperties().getNormalProperties().getButtonSpacing(), 0, 0);
         }
      }

      for(int var7 = 0; var7 < this.buttons.length; ++var7) {
         int var6 = var2 + var7;
         var3[var6] = this.buttons[var7];
         var4[var6] = var6 == 0
            ? new Insets(0, 0, 0, 0)
            : new Insets(0, this.view.getViewProperties().getViewTitleBarProperties().getNormalProperties().getButtonSpacing(), 0, 0);
      }

      this.setRightTitleComponents(var3, var4);
   }

   public void dispose() {
      HoverManager.getInstance().removeHoverable(this);
   }

   protected void processMouseEvent(MouseEvent var1) {
      if (var1.getID() == 501) {
         ++this.pressedCount;
      }

      if (var1.getID() == 502) {
         --this.pressedCount;
         if (this.pressedCount <= 0) {
            this.dragOutside = false;
         }
      }

      super.processMouseEvent(var1);
   }

   protected void processMouseMotionEvent(MouseEvent var1) {
      if (var1.getID() == 506 && !this.dragOutside) {
         this.dragOutside = !this.contains(var1.getPoint());
         if (!this.dragOutside) {
            return;
         }
      }

      super.processMouseMotionEvent(var1);
   }

   public Dimension getMinimumSize() {
      if (this.minimumSizeProvider == null) {
         return super.getMinimumSize();
      } else {
         Dimension var1 = this.minimumSizeProvider.getDimension(this);
         return var1 == null ? super.getMinimumSize() : var1;
      }
   }

   public Dimension getPreferredSize() {
      Dimension var1 = this.minimumSizeProvider == null ? null : this.minimumSizeProvider.getDimension(this);
      Dimension var2 = super.getPreferredSize();
      return var1 == null ? var2 : new Dimension(Math.max(var1.width, var2.width), Math.max(var1.height, var2.height));
   }
}
