package net.infonode.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.infonode.gui.draggable.DraggableComponent;

public class Tab extends JPanel {
   private TabbedPanel tabbedPanel;
   private JComponent contentComponent;
   private JComponent focusableComponent;
   private ArrayList listeners;
   private DraggableComponent draggableComponent;
   private KeyListener focusableKeyListener = new Tab$1(this);
   private TabListener tabbedPanelListener = new Tab$2(this);

   public Tab() {
      this(null);
   }

   public Tab(JComponent var1) {
      super(new BorderLayout());
      this.setOpaque(false);
      this.contentComponent = var1;
      this.draggableComponent = new DraggableComponent(this);
   }

   public void addTabListener(TabListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList(2);
      }

      this.listeners.add(var1);
   }

   public void removeTabListener(TabListener var1) {
      if (this.listeners != null) {
         this.listeners.remove(var1);
         if (this.listeners.size() == 0) {
            this.listeners = null;
         }
      }

   }

   public JComponent getContentComponent() {
      return this.contentComponent;
   }

   public TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }

   public void setEnabled(boolean var1) {
      this.getDraggableComponent().setEnabled(var1);
      super.setEnabled(var1);
   }

   public void setSelected(boolean var1) {
      if (var1) {
         this.draggableComponent.select();
      } else if (this.tabbedPanel != null && this.tabbedPanel.getSelectedTab() == this) {
         this.tabbedPanel.setSelectedTab(null);
      }

   }

   public boolean isSelected() {
      return this.tabbedPanel != null ? this.tabbedPanel.getSelectedTab() == this : false;
   }

   public void setHighlighted(boolean var1) {
      if (this.tabbedPanel != null) {
         if (var1) {
            this.tabbedPanel.setHighlightedTab(this);
         } else if (this.tabbedPanel.getHighlightedTab() == this) {
            this.tabbedPanel.setHighlightedTab(null);
         }
      }

   }

   public boolean isHighlighted() {
      return this.tabbedPanel != null ? this.tabbedPanel.getHighlightedTab() == this : false;
   }

   public void setEventComponent(JComponent var1) {
      this.setEventComponents(new JComponent[]{var1});
   }

   public void setEventComponents(JComponent[] var1) {
      this.draggableComponent.setEventComponents(var1);
   }

   public JComponent[] getEventComponents() {
      return this.draggableComponent.getEventComponents();
   }

   public int getIndex() {
      return this.tabbedPanel == null ? -1 : this.tabbedPanel.getTabIndex(this);
   }

   public JComponent getFocusableComponent() {
      return this.focusableComponent;
   }

   public void setFocusableComponent(JComponent var1) {
      if (this.focusableComponent != var1) {
         boolean var2 = false;
         if (this.focusableComponent != null) {
            this.focusableComponent.removeKeyListener(this.focusableKeyListener);
            var2 = this.focusableComponent.hasFocus();
         }

         this.focusableComponent = var1;
         if (this.focusableComponent != null) {
            this.focusableComponent.setFocusable(this.isSelected());
            this.focusableComponent.addKeyListener(this.focusableKeyListener);
            if (var2) {
               this.focusableComponent.requestFocusInWindow();
            }
         }
      }

   }

   public Shape getShape() {
      return null;
   }

   protected void setTabbedPanel(TabbedPanel var1) {
      this.tabbedPanel = var1;
      if (this.tabbedPanel != null) {
         this.tabbedPanel.addTabListener(this.tabbedPanelListener);
      }

   }

   DraggableComponent getDraggableComponent() {
      return this.draggableComponent;
   }

   private void fireHighlightedEvent(TabStateChangedEvent var1) {
      if (this.listeners != null) {
         TabStateChangedEvent var2 = new TabStateChangedEvent(this, var1.getTabbedPanel(), this, var1.getPreviousTab(), var1.getCurrentTab());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabHighlighted(var2);
         }
      }

   }

   private void fireDehighlightedEvent(TabStateChangedEvent var1) {
      if (this.listeners != null) {
         TabStateChangedEvent var2 = new TabStateChangedEvent(this, var1.getTabbedPanel(), this, var1.getPreviousTab(), var1.getCurrentTab());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabDehighlighted(var2);
         }
      }

   }

   private void fireSelectedEvent(TabStateChangedEvent var1) {
      if (this.listeners != null) {
         TabStateChangedEvent var2 = new TabStateChangedEvent(this, var1.getTabbedPanel(), this, var1.getPreviousTab(), var1.getCurrentTab());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabSelected(var2);
         }
      }

   }

   private void fireDeselectedEvent(TabStateChangedEvent var1) {
      if (this.listeners != null) {
         TabStateChangedEvent var2 = new TabStateChangedEvent(this, var1.getTabbedPanel(), this, var1.getPreviousTab(), var1.getCurrentTab());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabDeselected(var2);
         }
      }

   }

   private void fireDraggedEvent(TabDragEvent var1) {
      if (this.listeners != null) {
         TabDragEvent var2 = new TabDragEvent(this, var1.getMouseEvent());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabDragged(var2);
         }
      }

   }

   private void fireDroppedEvent(TabDragEvent var1) {
      if (this.listeners != null) {
         TabDragEvent var2 = new TabDragEvent(this, this, var1.getPoint());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabDropped(var2);
         }
      }

   }

   private void fireNotDroppedEvent() {
      if (this.listeners != null) {
         TabEvent var1 = new TabEvent(this, this);
         Object[] var2 = this.listeners.toArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            ((TabListener)var2[var3]).tabDragAborted(var1);
         }
      }

   }

   private void fireMovedEvent() {
      if (this.listeners != null) {
         TabEvent var1 = new TabEvent(this, this);
         Object[] var2 = this.listeners.toArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            ((TabListener)var2[var3]).tabMoved(var1);
         }
      }

   }

   private void fireAddedEvent() {
      if (this.listeners != null) {
         TabEvent var1 = new TabEvent(this, this);
         Object[] var2 = this.listeners.toArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            ((TabListener)var2[var3]).tabAdded(var1);
         }
      }

   }

   private void fireRemovedEvent(TabRemovedEvent var1) {
      if (this.listeners != null) {
         TabRemovedEvent var2 = new TabRemovedEvent(this, this, var1.getTabbedPanel());
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabRemoved(var2);
         }
      }

   }

   public void addNotify() {
      if (!this.draggableComponent.isIgnoreAddNotify()) {
         super.addNotify();
      }

   }

   public void removeNotify() {
      if (!this.draggableComponent.isIgnoreAddNotify()) {
         super.removeNotify();
      }

   }
}
