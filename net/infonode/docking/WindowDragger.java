package net.infonode.docking;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.internalutil.DropAction;

class WindowDragger implements DockingWindowDragger {
   private DockingWindow dragWindow;
   private DropAction dropAction;
   private RootWindow rootWindow;

   WindowDragger(DockingWindow var1) {
      this(var1, var1.getRootWindow());
   }

   WindowDragger(DockingWindow var1, RootWindow var2) {
      super();
      this.dragWindow = var1;
      this.rootWindow = var2;
      var2.internalStartDrag(var1);
   }

   public DockingWindow getDragWindow() {
      return this.dragWindow;
   }

   public RootWindow getDropTarget() {
      return this.rootWindow;
   }

   void undoDrag(DropAction var1) {
      if (this.dropAction != null) {
         this.dropAction.clear(this.dragWindow, var1);
         this.dropAction = null;
      }

   }

   private void stopDrag() {
      this.rootWindow.stopDrag();
   }

   public void abortDrag() {
      this.stopDrag();
      this.undoDrag(null);
   }

   public void dropWindow(MouseEvent var1) {
      this.stopDrag();
      if (this.dropAction == null) {
         this.dropAction = this.dragWindow.getDefaultDropAction();
      }

      if (this.dragWindow != null && this.dropAction != null) {
         this.dropAction.execute(this.dragWindow, var1);
         Container var2 = this.dragWindow.getTopLevelAncestor();
         if (var2 != null && var2 instanceof Window) {
            ((Window)var2).toFront();
         }

         FocusManager.focusWindow(this.dragWindow);
      }

   }

   public void dragWindow(MouseEvent var1) {
      JRootPane var2 = this.rootWindow.getCurrentDragRootPane();
      Point var3 = SwingUtilities.convertPoint((Component)var1.getSource(), var1.getPoint(), var2);
      if (var2 != this.rootWindow.getRootPane() && !var2.contains(var3)) {
         this.rootWindow.setCurrentDragRootPane(null);
         var2 = this.rootWindow.getCurrentDragRootPane();
         var3 = SwingUtilities.convertPoint((Component)var1.getSource(), var1.getPoint(), var2);
      }

      DockingWindow var4;
      for(var4 = this.getDeepestWindowAt(var2, var3.x, var3.y);
         var4 != null && var4.getWindowParent() != null && !(var4 instanceof FloatingWindow);
         var4 = var4.getWindowParent()
      ) {
         Point var5 = SwingUtilities.convertPoint(var2, var3, var4.getWindowParent());
         if (!var4.getWindowParent().contains(var5)) {
            break;
         }
      }

      DropAction var7 = var4 != null ? var4.acceptDrop(SwingUtilities.convertPoint(var2, var3, var4), this.dragWindow) : null;
      this.undoDrag(var7);
      Cursor var6 = DragSource.DefaultMoveDrop;
      if (var7 == null) {
         var6 = DragSource.DefaultMoveNoDrop;
      }

      if (var4 == null && this.dragWindow.getWindowProperties().getUndockOnDropEnabled()) {
         var6 = DragSource.DefaultMoveDrop;
      }

      this.rootWindow.setDragCursor(var6);
      this.rootWindow.setDragText(var7 != null && !var7.showTitle() ? null : var3, this.dragWindow.getTitle());
      this.dropAction = var7;
      if (this.dropAction == null) {
         this.rootWindow.setDragRectangle(null);
      }

   }

   private DockingWindow getDeepestWindowAt(Component var1, int var2, int var3) {
      if (var1 != null && var1.isVisible() && var1.contains(var2, var3)) {
         if (var1 instanceof Container) {
            Component[] var4 = ((Container)var1).getComponents();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               DockingWindow var6 = this.getDeepestWindowAt(var4[var5], var2 - var4[var5].getX(), var3 - var4[var5].getY());
               if (var6 != null) {
                  return var6;
               }
            }
         }

         if (var1 instanceof DockingWindow) {
            DockingWindow var7 = (DockingWindow)var1;
            return var7.getRootWindow() == this.rootWindow ? var7 : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
