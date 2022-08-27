package net.infonode.gui.draggable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.ScrollButtonBox;
import net.infonode.gui.ScrollableBox;
import net.infonode.gui.layout.DirectionLayout;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.util.Direction;

public class DraggableComponentBox extends SimplePanel {
   private final boolean componentBoxEnabled = true;
   private final JComponent componentBox;
   private JComponent componentContainer;
   private JComponent outerParentArea = this;
   private Direction componentDirection = Direction.UP;
   private boolean scrollEnabled = false;
   private boolean ensureSelectedVisible;
   private boolean autoSelect = true;
   private boolean descendingSortOrder = true;
   private boolean doReverseSort = false;
   private boolean mustSort = false;
   private int scrollOffset;
   private final int iconSize;
   private DraggableComponent selectedComponent;
   private DraggableComponent topComponent;
   private ArrayList listeners;
   private final ArrayList draggableComponentList = new ArrayList(10);
   private final ArrayList layoutOrderList = new ArrayList(10);
   private ScrollButtonBox scrollButtonBox;
   private boolean useDefaultScrollButtons = true;
   private final DraggableComponentListener draggableComponentListener = new DraggableComponentBox$1(this);

   public DraggableComponentBox(int var1) {
      this(var1, true);
   }

   public DraggableComponentBox(int var1, boolean var2) {
      super();
      this.iconSize = var1;
      this.useDefaultScrollButtons = var2;
      DraggableComponentBox$2 var3 = new DraggableComponentBox$2(
         this,
         this.componentDirection == Direction.UP
            ? Direction.RIGHT
            : (this.componentDirection == Direction.LEFT ? Direction.DOWN : (this.componentDirection == Direction.DOWN ? Direction.RIGHT : Direction.DOWN))
      );
      var3.setLayoutOrderList(this.layoutOrderList);
      this.componentBox = new DraggableComponentBox$3(this, var3);
      this.componentBox.addComponentListener(new DraggableComponentBox$4(this));
      this.initialize();
   }

   public void addListener(DraggableComponentBoxListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList(2);
      }

      this.listeners.add(var1);
   }

   public void removeListener(DraggableComponentBoxListener var1) {
      if (this.listeners != null) {
         this.listeners.remove(var1);
         if (this.listeners.size() == 0) {
            this.listeners = null;
         }
      }

   }

   public void addDraggableComponent(DraggableComponent var1) {
      this.insertDraggableComponent(var1, -1);
   }

   public void insertDraggableComponent(DraggableComponent var1, int var2) {
      var1.setLayoutOrderList(this.layoutOrderList);
      var1.addListener(this.draggableComponentListener);
      if (var2 < 0) {
         this.layoutOrderList.add(var1.getComponent());
         this.componentBox.add(var1.getComponent());
      } else {
         this.layoutOrderList.add(var2, var1.getComponent());
         this.componentBox.add(var1.getComponent(), var2);
      }

      this.sortComponentList(!this.descendingSortOrder);
      this.draggableComponentList.add(var1);
      var1.setOuterParentArea(this.outerParentArea);
      this.componentBox.revalidate();
      this.fireAddedEvent(var1);
      if (this.autoSelect && this.layoutOrderList.size() == 1 && this.selectedComponent == null && var1.isEnabled()) {
         this.doSelectComponent(var1);
      }

      this.updateScrollButtons();
   }

   private void updateScrollButtons() {
      if (this.scrollButtonBox != null) {
         ScrollableBox var1 = (ScrollableBox)this.componentContainer;
         this.scrollButtonBox.setButton1Enabled(!var1.isLeftEnd());
         this.scrollButtonBox.setButton2Enabled(!var1.isRightEnd());
      }

   }

   public void insertDraggableComponent(DraggableComponent var1, Point var2) {
      int var3 = this.getComponentIndexAtPoint(var2);
      if (var3 != -1 && this.layoutOrderList.size() > 0) {
         this.insertDraggableComponent(var1, var3);
      } else {
         this.insertDraggableComponent(var1, -1);
      }

   }

   public void selectDraggableComponent(DraggableComponent var1) {
      if (var1 == null) {
         if (this.selectedComponent != null) {
            DraggableComponent var2 = this.selectedComponent;
            this.selectedComponent = null;
            this.fireSelectedEvent(this.selectedComponent, var2);
         }
      } else {
         var1.select();
      }

   }

   public void removeDraggableComponent(DraggableComponent var1) {
      if (var1 != null && this.draggableComponentList.contains(var1)) {
         int var2 = this.layoutOrderList.indexOf(var1.getComponent());
         var1.removeListener(this.draggableComponentListener);
         if (var1 == this.topComponent) {
            this.topComponent = null;
         }

         if (this.layoutOrderList.size() > 1 && this.selectedComponent != null) {
            if (this.selectedComponent == var1) {
               if (this.autoSelect) {
                  int var4 = this.findSelectableComponentIndex(var2);
                  if (var4 > -1) {
                     this.selectDraggableComponent(this.findDraggableComponent((Component)this.layoutOrderList.get(var4)));
                  } else {
                     this.selectedComponent = null;
                  }
               } else {
                  this.selectDraggableComponent(null);
               }
            }
         } else if (this.selectedComponent != null) {
            DraggableComponent var3 = this.selectedComponent;
            this.selectedComponent = null;
            this.fireSelectedEvent(this.selectedComponent, var3);
         }

         this.draggableComponentList.remove(var1);
         this.layoutOrderList.remove(var1.getComponent());
         this.componentBox.remove(var1.getComponent());
         this.componentBox.revalidate();
         var1.setLayoutOrderList(null);
         this.sortComponentList(!this.descendingSortOrder);
         this.updateScrollButtons();
         this.fireRemovedEvent(var1);
      }

   }

   public boolean containsDraggableComponent(DraggableComponent var1) {
      return this.draggableComponentList.contains(var1);
   }

   public DraggableComponent getSelectedDraggableComponent() {
      return this.selectedComponent;
   }

   public int getDraggableComponentCount() {
      return this.layoutOrderList.size();
   }

   public DraggableComponent getDraggableComponentAt(int var1) {
      return var1 < this.layoutOrderList.size() ? this.findDraggableComponent((Component)this.layoutOrderList.get(var1)) : null;
   }

   public int getDraggableComponentIndex(DraggableComponent var1) {
      return this.layoutOrderList.indexOf(var1.getComponent());
   }

   public Object[] getDraggableComponents() {
      return this.draggableComponentList.toArray();
   }

   public Component[] getBoxComponents() {
      return this.componentBox.getComponents();
   }

   public boolean getDepthSortOrder() {
      return this.descendingSortOrder;
   }

   public void setDepthSortOrder(boolean var1) {
      if (var1 != this.descendingSortOrder) {
         this.descendingSortOrder = var1;
         this.sortComponentList(!var1);
         this.doSort();
      }

   }

   public boolean isScrollEnabled() {
      return this.scrollEnabled;
   }

   public void setScrollEnabled(boolean var1) {
      if (var1 != this.scrollEnabled) {
         this.scrollEnabled = var1;
         this.initialize();
      }

   }

   public int getScrollOffset() {
      return this.scrollOffset;
   }

   public void setScrollOffset(int var1) {
      if (var1 != this.scrollOffset) {
         this.scrollOffset = var1;
         if (this.scrollEnabled) {
            ((ScrollableBox)this.componentContainer).setScrollOffset(var1);
         }
      }

   }

   public int getComponentSpacing() {
      return this.getDirectionLayout().getComponentSpacing();
   }

   public void setComponentSpacing(int var1) {
      if (var1 != this.getDirectionLayout().getComponentSpacing()) {
         if (this.getComponentSpacing() < 0 && var1 >= 0) {
            DraggableComponent var2 = this.topComponent;
            this.sortComponentList(false);
            this.topComponent = var2;
         }

         this.getDirectionLayout().setComponentSpacing(var1);
         this.sortComponentList(!this.descendingSortOrder);
         this.componentBox.revalidate();
      }

   }

   public boolean isEnsureSelectedVisible() {
      return this.ensureSelectedVisible;
   }

   public void setEnsureSelectedVisible(boolean var1) {
      this.ensureSelectedVisible = var1;
   }

   public boolean isAutoSelect() {
      return this.autoSelect;
   }

   public void setAutoSelect(boolean var1) {
      this.autoSelect = var1;
   }

   public Direction getComponentDirection() {
      return this.componentDirection;
   }

   public void setComponentDirection(Direction var1) {
      if (var1 != this.componentDirection) {
         this.componentDirection = var1;
         this.getDirectionLayout()
            .setDirection(
               var1 == Direction.UP
                  ? Direction.RIGHT
                  : (var1 == Direction.LEFT ? Direction.DOWN : (var1 == Direction.DOWN ? Direction.RIGHT : Direction.DOWN))
            );
         if (this.scrollEnabled) {
            this.scrollButtonBox.setVertical(var1.isHorizontal());
            ((ScrollableBox)this.componentContainer).setVertical(var1.isHorizontal());
         }
      }

   }

   public void setTopComponent(DraggableComponent var1) {
      if (var1 != this.topComponent) {
         this.topComponent = var1;
         this.sortComponentList(!this.descendingSortOrder);
      }

   }

   public ScrollButtonBox getScrollButtonBox() {
      return this.scrollButtonBox;
   }

   public JComponent getOuterParentArea() {
      return this.outerParentArea;
   }

   public void setOuterParentArea(JComponent var1) {
      this.outerParentArea = var1;
   }

   public void dragDraggableComponent(DraggableComponent var1, Point var2) {
      if (this.draggableComponentList.contains(var1)) {
         var1.drag(SwingUtilities.convertPoint(this, var2, var1.getComponent()));
      }

   }

   public Dimension getMaximumSize() {
      if (this.scrollEnabled) {
         return this.getPreferredSize();
      } else {
         return this.componentDirection != Direction.LEFT && this.componentDirection != Direction.RIGHT
            ? new Dimension((int)super.getMaximumSize().getWidth(), (int)this.getPreferredSize().getHeight())
            : new Dimension((int)this.getPreferredSize().getWidth(), (int)super.getMaximumSize().getHeight());
      }
   }

   public Dimension getInnerSize() {
      boolean var1 = this.mustSort;
      this.mustSort = false;
      Dimension var2 = this.scrollEnabled ? this.componentBox.getPreferredSize() : this.componentBox.getSize();
      this.mustSort = var1;
      return var2;
   }

   public void scrollToVisible(DraggableComponent var1) {
      SwingUtilities.invokeLater(new DraggableComponentBox$5(this, var1));
   }

   private void setIgnoreAddRemoveNotify(boolean var1) {
      for(int var2 = 0; var2 < this.draggableComponentList.size(); ++var2) {
         ((DraggableComponent)this.draggableComponentList.get(var2)).setIgnoreAddNotify(var1);
      }

   }

   private void doSort() {
      if (this.mustSort && this.getComponentSpacing() < 0 && this.componentBox.getComponentCount() > 0) {
         this.setIgnoreAddRemoveNotify(true);
         this.mustSort = false;
         JComponent var2 = this.topComponent != null ? this.topComponent.getComponent() : null;
         int var3 = 0;
         if (var2 != null) {
            if (this.componentBox.getComponent(0) != var2) {
               this.componentBox.remove(var2);
               this.componentBox.add(var2, var3);
            }

            ++var3;
         }

         int var4 = 0;
         int var5 = this.layoutOrderList.size();

         for(int var6 = 0; var6 < var5; ++var6) {
            Component var1 = (Component)this.layoutOrderList.get(this.doReverseSort ? var5 - var6 - 1 : var6);
            if (var1 != var2) {
               if (this.componentBox.getComponent(var3) != var1) {
                  ++var4;
                  this.componentBox.remove(var1);
                  this.componentBox.add(var1, var3);
               }

               ++var3;
            }
         }

         this.setIgnoreAddRemoveNotify(false);
      }

   }

   private void sortComponentList(boolean var1) {
      this.doReverseSort = var1;
      this.mustSort = true;
   }

   private int getComponentIndexAtPoint(Point var1) {
      JComponent var2 = null;
      Point var3 = SwingUtilities.convertPoint(this, var1, this.componentBox);
      Point var4 = SwingUtilities.convertPoint(this.componentBox, var1, this.outerParentArea);
      if (this.outerParentArea.contains(var4)) {
         var2 = (JComponent)ComponentUtil.getChildAtLine(this.componentBox, var3, this.getDirectionLayout().getDirection().isHorizontal());
      }

      return this.layoutOrderList.indexOf(var2);
   }

   private void doSelectComponent(DraggableComponent var1) {
      if (this.selectedComponent != null) {
         DraggableComponent var2 = this.selectedComponent;
         this.selectedComponent = var1;
         this.ensureSelectedVisible();
         this.fireSelectedEvent(this.selectedComponent, var2);
      } else {
         this.selectedComponent = var1;
         this.ensureSelectedVisible();
         this.fireSelectedEvent(this.selectedComponent, null);
      }

   }

   private int findSelectableComponentIndex(int var1) {
      int var2 = -1;

      for(int var4 = 0; var4 < this.layoutOrderList.size(); ++var4) {
         if (this.findDraggableComponent((Component)this.layoutOrderList.get(var4)).isEnabled() && var4 != var1) {
            int var3 = var2;
            var2 = var4;
            if (var3 < var1 && var4 > var1) {
               return var4;
            }

            if (var3 > var1 && var4 > var1) {
               return var3;
            }
         }
      }

      return var2;
   }

   private DraggableComponent findDraggableComponent(Component var1) {
      for(int var2 = 0; var2 < this.draggableComponentList.size(); ++var2) {
         if (((DraggableComponent)this.draggableComponentList.get(var2)).getComponent() == var1) {
            return (DraggableComponent)this.draggableComponentList.get(var2);
         }
      }

      return null;
   }

   private DirectionLayout getDirectionLayout() {
      return (DirectionLayout)this.componentBox.getLayout();
   }

   private void initialize() {
      if (this.componentContainer != null) {
         this.remove(this.componentContainer);
      }

      DirectionLayout var1 = this.getDirectionLayout();
      var1.setCompressing(!this.scrollEnabled);
      if (this.scrollEnabled) {
         if (this.useDefaultScrollButtons) {
            this.scrollButtonBox = new ScrollButtonBox(this.componentDirection.isHorizontal(), this.iconSize);
         } else {
            this.scrollButtonBox = new ScrollButtonBox(this.componentDirection.isHorizontal(), null, null, null, null);
         }

         ScrollableBox var2 = new ScrollableBox(this.componentBox, this.componentDirection.isHorizontal(), this.scrollOffset);
         var2.setLayoutOrderList(this.layoutOrderList);
         this.scrollButtonBox.addListener(new DraggableComponentBox$6(this, var2));
         var2.addComponentListener(new DraggableComponentBox$7(this, var2));
         this.scrollButtonBox.setButton1Enabled(!var2.isLeftEnd());
         this.scrollButtonBox.setButton2Enabled(!var2.isRightEnd());
         var2.addScrollableBoxListener(new DraggableComponentBox$8(this));
         this.componentContainer = var2;
      } else {
         this.scrollButtonBox = null;
         this.componentContainer = this.componentBox;
      }

      this.componentContainer.setAlignmentY(0.0F);
      this.add(this.componentContainer, "Center");
      this.revalidate();
   }

   private void ensureSelectedVisible() {
      SwingUtilities.invokeLater(new DraggableComponentBox$9(this));
   }

   private void fireDraggedEvent(DraggableComponentEvent var1) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var2 = new DraggableComponentBoxEvent(
            this, var1.getSource(), var1, SwingUtilities.convertPoint(var1.getSource().getComponent(), var1.getMouseEvent().getPoint(), this)
         );
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentBoxListener)var3[var4]).componentDragged(var2);
         }
      }

   }

   private void fireDroppedEvent(DraggableComponentEvent var1) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var2 = new DraggableComponentBoxEvent(
            this, var1.getSource(), var1, SwingUtilities.convertPoint(var1.getSource().getComponent(), var1.getMouseEvent().getPoint(), this)
         );
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentBoxListener)var3[var4]).componentDropped(var2);
         }
      }

   }

   private void fireNotDroppedEvent(DraggableComponentEvent var1) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var2 = new DraggableComponentBoxEvent(this, var1.getSource(), var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentBoxListener)var3[var4]).componentDragAborted(var2);
         }
      }

   }

   private void fireSelectedEvent(DraggableComponent var1, DraggableComponent var2) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var3 = new DraggableComponentBoxEvent(this, var1, var2);
         Object[] var4 = this.listeners.toArray();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            ((DraggableComponentBoxListener)var4[var5]).componentSelected(var3);
         }
      }

   }

   private void fireAddedEvent(DraggableComponent var1) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var2 = new DraggableComponentBoxEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentBoxListener)var3[var4]).componentAdded(var2);
         }
      }

   }

   private void fireRemovedEvent(DraggableComponent var1) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var2 = new DraggableComponentBoxEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentBoxListener)var3[var4]).componentRemoved(var2);
         }
      }

   }

   private void fireChangedEvent(DraggableComponentEvent var1) {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var2 = new DraggableComponentBoxEvent(this, var1.getSource(), var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((DraggableComponentBoxListener)var3[var4]).changed(var2);
         }
      }

   }

   private void fireChangedEvent() {
      if (this.listeners != null) {
         DraggableComponentBoxEvent var1 = new DraggableComponentBoxEvent(this);
         Object[] var2 = this.listeners.toArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            ((DraggableComponentBoxListener)var2[var3]).changed(var1);
         }
      }

   }
}
