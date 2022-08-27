package net.infonode.gui.hover.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.hover.hoverable.HoverManager;
import net.infonode.gui.hover.hoverable.Hoverable;
import net.infonode.gui.shaped.panel.ShapedPanel;

public class HoverableShapedPanel extends ShapedPanel implements Hoverable {
   private HoverListener hoverListener;
   private Component hoveredComponent;
   private boolean hovered = false;

   public HoverableShapedPanel(HoverListener var1) {
      this(new BorderLayout(), var1, null);
   }

   public HoverableShapedPanel(LayoutManager var1, HoverListener var2) {
      this(var1, var2, null);
   }

   public HoverableShapedPanel(LayoutManager var1, HoverListener var2, Component var3) {
      super(var1);
      this.hoveredComponent = (Component)(var3 != null ? var3 : this);
      HoverManager.getInstance().addHoverable(this);
      this.setHoverListener(var2);
   }

   public HoverListener getHoverListener() {
      return this.hoverListener;
   }

   public void setHoverListener(HoverListener var1) {
      if (this.hoverListener != var1) {
         HoverListener var2 = this.hoverListener;
         this.hoverListener = var1;
         if (var2 != null && var1 != null && this.hovered) {
            HoverEvent var3 = new HoverEvent(this.hoveredComponent);
            var2.mouseExited(var3);
            var1.mouseEntered(var3);
         }
      }

   }

   public void hoverEnter() {
      if (this.hoverListener != null) {
         this.hovered = true;
         this.hoverListener.mouseEntered(new HoverEvent(this.hoveredComponent));
      }

   }

   public void hoverExit() {
      if (this.hoverListener != null) {
         this.hovered = false;
         this.hoverListener.mouseExited(new HoverEvent(this.hoveredComponent));
      }

   }

   public Component getHoveredComponent() {
      return this.hoveredComponent;
   }

   public boolean isHovered() {
      return this.hovered;
   }

   public boolean acceptHover(ArrayList var1) {
      return true;
   }
}
