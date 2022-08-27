package net.infonode.gui.draggable;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.EventUtil;

public class DraggableComponent {
   private final JComponent component;
   private JComponent[] eventComponents;
   private boolean reorderEnabled = true;
   private boolean enabled = true;
   private boolean reorderRestoreOnDrag;
   private boolean detectOuterAreaAsLine = true;
   private boolean enableInsideDrag;
   private boolean selectOnMousePress;
   private boolean mousePressed;
   private boolean dragEventFired;
   private boolean dragStarted;
   private boolean ignoreAddNotify = false;
   private int dragIndex;
   private int dragFromIndex;
   private int abortDragKeyCode = 27;
   private ArrayList layoutOrderList;
   private ArrayList listeners;
   private JComponent outerParentArea;
   private final KeyEventDispatcher abortDragKeyDispatcher = new DraggableComponent$1(this);
   private final MouseInputListener mouseInputListener = new DraggableComponent$2(this);

   public DraggableComponent(JComponent var1) {
      this(var1, var1);
   }

   public DraggableComponent(JComponent var1, JComponent var2) {
      this(var1, new JComponent[]{var2});
   }

   public DraggableComponent(JComponent var1, JComponent[] var2) {
      super();
      this.component = var1;
      var1.addComponentListener(new DraggableComponent$3(this));
      this.setEventComponents(var2);
   }

   public void addListener(DraggableComponentListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList(2);
      }

      this.listeners.add(var1);
   }

   public void removeListener(DraggableComponentListener var1) {
      if (this.listeners != null) {
         this.listeners.remove(var1);
         if (this.listeners.size() == 0) {
            this.listeners = null;
         }
      }

   }

   public JComponent getComponent() {
      return this.component;
   }

   public JComponent[] getEventComponents() {
      return this.eventComponents;
   }

   public void setEventComponents(JComponent[] var1) {
      if (this.eventComponents != null) {
         for(int var2 = 0; var2 < this.eventComponents.length; ++var2) {
            this.eventComponents[var2].removeMouseListener(this.mouseInputListener);
            this.eventComponents[var2].removeMouseMotionListener(this.mouseInputListener);
         }
      }

      this.eventComponents = var1;
      if (this.eventComponents != null) {
         for(int var3 = 0; var3 < this.eventComponents.length; ++var3) {
            this.eventComponents[var3].addMouseListener(this.mouseInputListener);
            this.eventComponents[var3].addMouseMotionListener(this.mouseInputListener);
         }
      }

   }

   public int getAbortDragKeyCode() {
      return this.abortDragKeyCode;
   }

   public void setAbortDragKeyCode(int var1) {
      this.abortDragKeyCode = var1;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean var1) {
      if (this.enabled != var1) {
         this.enabled = var1;
         this.fireChangedEvent(var1 ? 3 : 4);
      }

   }

   public boolean isReorderEnabled() {
      return this.reorderEnabled;
   }

   public void setReorderEnabled(boolean var1) {
      this.reorderEnabled = var1;
   }

   public boolean isReorderRestoreOnDrag() {
      return this.reorderRestoreOnDrag;
   }

   public void setReorderRestoreOnDrag(boolean var1) {
      this.reorderRestoreOnDrag = var1;
   }

   public boolean isDetectOuterAreaAsLine() {
      return this.detectOuterAreaAsLine;
   }

   public void setDetectOuterAreaAsLine(boolean var1) {
      this.detectOuterAreaAsLine = var1;
   }

   public boolean isEnableInsideDrag() {
      return this.enableInsideDrag;
   }

   public void setEnableInsideDrag(boolean var1) {
      this.enableInsideDrag = var1;
   }

   public boolean isSelectOnMousePress() {
      return this.selectOnMousePress;
   }

   public void setSelectOnMousePress(boolean var1) {
      this.selectOnMousePress = var1;
   }

   public void drag(Point var1) {
      if (this.enabled) {
         this.dragIndex = this.getComponentIndex(this.component);
         this.dragFromIndex = this.dragIndex;
         this.doDrag(var1);
      }

   }

   public void abortDrag() {
      if (this.dragStarted) {
         this.dragCompleted(null);
      }

   }

   public void setLayoutOrderList(ArrayList var1) {
      this.layoutOrderList = var1;
   }

   public void select() {
      if (this.enabled) {
         this.fireSelectedEvent();
      }

   }

   public void setOuterParentArea(JComponent var1) {
      this.outerParentArea = var1;
   }

   public boolean isIgnoreAddNotify() {
      return this.ignoreAddNotify;
   }

   public void setIgnoreAddNotify(boolean var1) {
      this.ignoreAddNotify = var1;
   }

   private void pressed(MouseEvent var1) {
      if (this.enabled && var1.getButton() == 1) {
         if (this.selectOnMousePress && !var1.isShiftDown()) {
            this.select();
         }

         this.dragStarted = false;
         KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this.abortDragKeyDispatcher);
         this.mousePressed = true;
         this.dragIndex = this.getComponentIndex(this.component);
         this.dragFromIndex = this.dragIndex;
         this.fireChangedEvent(1);
      }

   }

   private void released(MouseEvent var1) {
      if (this.mousePressed) {
         if (var1.getButton() == 1) {
            this.dragCompleted(var1);
         } else {
            this.dragCompleted(null);
            var1.consume();
         }
      }

   }

   private void dragged(MouseEvent var1) {
      if (this.enabled && this.mousePressed) {
         Point var2 = SwingUtilities.convertPoint((JComponent)var1.getSource(), var1.getPoint(), this.component);
         if (this.dragStarted || this.enableInsideDrag || !this.component.contains(var2)) {
            if (this.reorderEnabled) {
               this.doDrag(var2);
            } else {
               this.dragStarted = true;
            }

            this.fireDraggedEvent(EventUtil.convert(var1, this.component, var2));
         }
      }

   }

   private void dragCompleted(MouseEvent var1) {
      this.mousePressed = false;
      this.dragStarted = false;
      KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this.abortDragKeyDispatcher);
      if (var1 == null) {
         this.restoreComponentOrder();
         this.fireNotDroppedEvent();
      } else if (!this.checkParentContains(SwingUtilities.convertPoint((JComponent)var1.getSource(), var1.getPoint(), this.component.getParent()))) {
         this.restoreComponentOrder();
         this.fireDroppedEvent(EventUtil.convert(var1, this.component));
      } else {
         this.fireDroppedEvent(EventUtil.convert(var1, this.component));
         if (!this.selectOnMousePress && !var1.isShiftDown()) {
            this.fireSelectedEvent();
         }
      }

      this.fireChangedEvent(2);
   }

   private void updateParent() {
      if (this.component.getParent() != null) {
         ComponentUtil.validate(this.component.getParent());
      }

   }

   private void doDrag(Point var1) {
      this.dragStarted = true;
      JComponent var2 = (JComponent)this.component.getParent();
      if (var2.getComponentCount() != 1) {
         Point var3 = SwingUtilities.convertPoint(this.component, var1, var2);
         int var4 = this.getMoveComponentIndex(var3);
         if (var4 != -1) {
            var4 = Math.min(var4, var2.getComponentCount() - 1);
            JComponent var5 = this.getComponent(var2, this.dragIndex);
            int var6;
            int var7;
            int var8;
            if (this.isVerticalDrag()) {
               var6 = var5.getHeight();
               var7 = (int)SwingUtilities.convertPoint(var2, var3, this.getComponent(var2, var4)).getY();
               var8 = this.getComponent(var2, var4).getHeight();
            } else {
               var6 = var5.getWidth();
               var7 = (int)SwingUtilities.convertPoint(var2, var3, this.getComponent(var2, var4)).getX();
               var8 = this.getComponent(var2, var4).getWidth();
            }

            if (var4 > this.dragIndex && var8 - var7 > var6 || (this.dragIndex == -1 || var4 < this.dragIndex) && var7 > var6) {
               return;
            }

            if (this.dragIndex != -1 && this.dragIndex != var4) {
               this.removeComponent(var2, var5, this.dragIndex);
               this.addComponent(var2, var5, var4);
               this.fireChangedEvent(0);
            }
         }

         if (var4 < 0) {
            this.restoreComponentOrder();
         } else {
            this.dragIndex = var4;
         }

      }
   }

   private boolean isVerticalDrag() {
      JComponent var1 = (JComponent)this.component.getParent();
      if (var1.getComponentCount() > 1) {
         return this.getComponent(var1, 0).getY() < this.getComponent(var1, 1).getY();
      } else {
         return false;
      }
   }

   private boolean checkParentContains(Point var1) {
      if (this.outerParentArea == null) {
         return this.component.getParent().contains(var1);
      } else {
         Point var2 = SwingUtilities.convertPoint(this.component.getParent(), var1, this.outerParentArea);
         if (this.detectOuterAreaAsLine) {
            Insets var3 = new Insets(0, 0, 0, 0);
            if (!this.component.getParent().contains(var1)) {
               if (this.outerParentArea.contains(var2)) {
                  if (this.isVerticalDrag()) {
                     if (var2.getX() >= (double)var3.left && var2.getX() < (double)(this.outerParentArea.getWidth() - var3.right)) {
                        return true;
                     }
                  } else if (var2.getY() >= (double)var3.top && var2.getY() < (double)(this.outerParentArea.getHeight() - var3.bottom)) {
                     return true;
                  }
               }

               return false;
            } else {
               return true;
            }
         } else {
            return this.component.getParent().contains(var1) || this.outerParentArea.contains(var2);
         }
      }
   }

   private int getMoveComponentIndex(Point var1) {
      JComponent var2 = (JComponent)this.component.getParent();
      if (this.checkParentContains(var1)) {
         boolean var3 = this.isVerticalDrag();

         for(int var4 = 0; var4 < var2.getComponentCount() - 1; ++var4) {
            Point var5 = this.getComponent(var2, var4 + 1).getLocation();
            if (var3) {
               if (var1.getY() >= 0.0 && var1.getY() < var5.getY()) {
                  return var4;
               }
            } else if (var1.getX() >= 0.0 && var1.getX() < var5.getX()) {
               return var4;
            }
         }

         if (this.dragIndex == -1) {
            return var2.getComponentCount();
         } else if (var3) {
            return var1.getY() < 0.0 ? 0 : var2.getComponentCount() - 1;
         } else {
            return var1.getX() < 0.0 ? 0 : var2.getComponentCount() - 1;
         }
      } else {
         return -1;
      }
   }

   private JComponent getComponent(Container var1, int var2) {
      return this.layoutOrderList != null ? (JComponent)this.layoutOrderList.get(var2) : (JComponent)var1.getComponent(var2);
   }

   private int getComponentIndex(Component var1) {
      return this.layoutOrderList != null ? this.layoutOrderList.indexOf(var1) : ComponentUtil.getComponentIndex(var1);
   }

   private void addComponent(Container var1, Component var2, int var3) {
      if (this.layoutOrderList != null) {
         this.layoutOrderList.add(var3, var2);
         var1.add(var2, var3);
      } else {
         var1.add(var2, var3);
      }

      this.revalidateComponentTree((JComponent)var2);
   }

   private void removeComponent(Container var1, Component var2, int var3) {
      this.revalidateComponentTree((JComponent)var2);
      if (this.layoutOrderList != null) {
         if (var3 < 0) {
            this.layoutOrderList.remove(var2);
            var1.remove(var2);
         } else {
            Component var4 = (Component)this.layoutOrderList.get(var3);
            this.layoutOrderList.remove(var3);
            var1.remove(var4);
         }
      } else if (var3 < 0) {
         var1.remove(var2);
      } else {
         var1.remove(var3);
      }

   }

   private void revalidateComponentTree(JComponent var1) {
      Container var2 = var1.getParent();
      int var3 = ComponentUtil.getComponentIndex(var1);
      if (var3 > 0) {
         this.doRevalidateComponentTree((JComponent)var2.getComponent(var3 - 1));
      }

      this.doRevalidateComponentTree(var1);
      if (var3 < var2.getComponentCount() - 1) {
         this.doRevalidateComponentTree((JComponent)var2.getComponent(var3 + 1));
      }

   }

   private void doRevalidateComponentTree(JComponent var1) {
      var1.revalidate();
      int var2 = var1.getComponentCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.doRevalidateComponentTree((JComponent)var1.getComponent(var3));
      }

   }

   private void restoreComponentOrder() {
      if (this.reorderEnabled && this.dragIndex != -1 && this.dragFromIndex != -1 && this.dragIndex != this.dragFromIndex) {
         Container var1 = this.component.getParent();
         JComponent var2 = this.getComponent(var1, this.dragIndex);
         this.removeComponent(var1, var2, -1);
         this.dragIndex = this.dragFromIndex;
         this.addComponent(var1, var2, this.dragIndex);
         this.fireChangedEvent(0);
      }

   }

   private void fireChangedEvent(int var1) {
      this.updateParent();
      if (this.listeners != null) {
         DraggableComponentEvent var2 = new DraggableComponentEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentListener)var3[var4]).changed(var2);
         }
      }

   }

   private void fireSelectedEvent() {
      this.updateParent();
      if (this.listeners != null) {
         DraggableComponentEvent var1 = new DraggableComponentEvent(this);
         Object[] var2 = this.listeners.toArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            ((DraggableComponentListener)var2[var3]).selected(var1);
         }
      }

   }

   private void fireDraggedEvent(MouseEvent var1) {
      this.dragEventFired = true;
      if (this.listeners != null) {
         DraggableComponentEvent var2 = new DraggableComponentEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentListener)var3[var4]).dragged(var2);
         }
      }

   }

   private void fireDroppedEvent(MouseEvent var1) {
      this.updateParent();
      if (this.dragEventFired) {
         this.dragEventFired = false;
         if (this.listeners != null) {
            DraggableComponentEvent var2 = new DraggableComponentEvent(this, var1);
            Object[] var3 = this.listeners.toArray();

            for(int var4 = 0; var4 < var3.length; ++var4) {
               ((DraggableComponentListener)var3[var4]).dropped(var2);
            }
         }
      }

   }

   private void fireNotDroppedEvent() {
      this.updateParent();
      if (this.dragEventFired) {
         this.dragEventFired = false;
         if (this.listeners != null) {
            DraggableComponentEvent var1 = new DraggableComponentEvent(this);
            Object[] var2 = this.listeners.toArray();

            for(int var3 = 0; var3 < var2.length; ++var3) {
               ((DraggableComponentListener)var2[var3]).dragAborted(var1);
            }
         }
      }

   }
}
