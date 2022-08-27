package net.infonode.gui;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultButtonModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import net.infonode.gui.panel.SimplePanel;

public class PopupList extends SimplePanel {
   private PopupList.Popup popup = new PopupList.Popup();
   private ArrayList listeners = new ArrayList(1);

   public PopupList(AbstractButton var1) {
      super();
      this.setLayout(new BoxLayout(this, 1));
      this.setButton(var1);
      this.popup.addPopupMenuListener(new PopupList$4(this));
   }

   public JList getList() {
      return this.popup.getList();
   }

   public void setButton(AbstractButton var1) {
      if (this.getComponentCount() > 0) {
         AbstractButton var2 = (AbstractButton)this.getComponent(0);
         var2.removeMouseListener(this.popup.getMouseListener());
         var2.removeMouseMotionListener(this.popup.getMouseMotionListener());
         this.remove(var2);
      }

      this.add(var1);
      var1.setModel(new PopupList.PopupButtonModel(null));
      var1.setAutoscrolls(true);
      var1.setFocusable(false);
      var1.addMouseListener(this.popup.getMouseListener());
      var1.addMouseMotionListener(this.popup.getMouseMotionListener());
   }

   public AbstractButton getButton() {
      return this.getComponentCount() == 0 ? null : (AbstractButton)this.getComponent(0);
   }

   public void updateUI() {
      super.updateUI();
      if (this.popup != null) {
         SwingUtilities.updateComponentTreeUI(this.popup);
      }

   }

   public void addPopupListListener(PopupListListener var1) {
      this.listeners.add(var1);
   }

   public void removePopupListListener(PopupListListener var1) {
      this.listeners.remove(var1);
   }

   public void addListSelectionListener(ListSelectionListener var1) {
      this.getList().addListSelectionListener(var1);
   }

   public void removeListSelectionListener(ListSelectionListener var1) {
      this.getList().removeListSelectionListener(var1);
   }

   private void fireWillBecomeVisible() {
      Object[] var1 = this.listeners.toArray();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         ((PopupListListener)var1[var2]).willBecomeVisible(this);
      }

   }

   private class Popup extends JPopupMenu {
      private JList list = new JList();
      private JScrollPane scrollPane = new JScrollPane(this.list, 20, 31);
      private int oldIndex;

      Popup() {
         super();
         this.setLayout(new BoxLayout(this, 1));
         this.scrollPane.setBorder(null);
         this.setBorderPainted(true);
         this.setBorder(new LineBorder(UIManagerUtil.getColor("controlDkShadow", Color.BLACK), 1));
         this.add(this.scrollPane);
         this.scrollPane.getViewport().setOpaque(false);
         this.list.addListSelectionListener(new PopupList$1(this));
         this.update();
      }

      public MouseMotionListener getMouseMotionListener() {
         return new PopupList$2(this);
      }

      public MouseListener getMouseListener() {
         return new PopupList$3(this);
      }

      public JList getList() {
         return this.list;
      }

      public void updateUI() {
         super.updateUI();
         this.setBorder(new LineBorder(UIManagerUtil.getColor("controlDkShadow", Color.BLACK), 1));
         if (this.list != null) {
            this.update();
         }

      }

      private void update() {
         this.scrollPane.getViewport().setOpaque(false);
         this.scrollPane.setBorder(null);
      }
   }

   private class PopupButtonModel extends DefaultButtonModel {
      private boolean pressed;

      private PopupButtonModel() {
         super();
      }

      public boolean isPressed() {
         return super.isPressed() || this.pressed;
      }

      public boolean isArmed() {
         return super.isArmed() || this.pressed;
      }

      public void setPressedInternal(boolean var1) {
         this.pressed = var1;
         this.fireStateChanged();
      }
   }
}
