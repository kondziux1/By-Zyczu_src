package pl.zyczu.minecraft.launcher.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.repo.ModPack;

public class MultiPanelStub extends JScrollPane {
   private static final long serialVersionUID = 5793643444505498759L;
   public static final int PACZKI_MODOW = 0;
   public static final int POLECANE = 1;
   public static final int KATEGORIE = 2;
   public static final int SZUKAJ = 3;
   public static final int WLASNE_MODY = 4;
   public static final int TRZY_KROPKI = 4;
   public static final int NONE = -1;
   private Loadable currentContent = null;
   private MultiPanel multiPanel = null;
   ActionListener comboBoxListener = null;
   ChangeListener changeListener = null;
   private static MultiPanelStub instance = null;

   public MultiPanelStub(JComponent childPanel) {
      super(childPanel);
      this.setOpaque(false);
      this.setWheelScrollingEnabled(true);
      this.getVerticalScrollBar().setUnitIncrement(16);
      instance = this;
   }

   private AlphaComposite makeComposite(float alpha) {
      int type = 3;
      return AlphaComposite.getInstance(type, alpha);
   }

   public void paintComponent(Graphics g) {
      g.drawImage(Minecraft.getInstance().backgroundGeneric, -12, -106, null);
      Graphics2D g2 = (Graphics2D)g;
      Composite originalComposite = g2.getComposite();
      g2.setPaint(Color.WHITE);
      g2.setComposite(this.makeComposite(0.7F));
      g2.fillRect(0, 0, this.getWidth(), this.getHeight());
      g2.setComposite(originalComposite);
   }

   public void openView(Loadable content) {
      content.onLoad(this);
      if (this.currentContent != null) {
         this.getViewport().remove(this.currentContent);
      }

      this.getViewport().add(content);
      this.currentContent = content;
   }

   public void setReference(MultiPanel ref) {
      this.multiPanel = ref;
   }

   public void refreshComboBox() {
      this.multiPanel.refresh();
   }

   public ModPack getCurrentModPack() {
      return this.multiPanel.getCurrentModPack();
   }

   public void setSelectedTab(int newTab) {
      this.multiPanel.setSelectedTab(newTab);
   }

   public void refreshAndWait() {
      if (this.currentContent instanceof Refreshable) {
         try {
            EventQueue.invokeAndWait(new Runnable() {
               public void run() {
                  ((Refreshable)MultiPanelStub.this.currentContent).refresh();
               }
            });
         } catch (InterruptedException var2) {
            Minecraft.log.severe("Wystąpił bład " + var2.toString());
            var2.printStackTrace();
         } catch (InvocationTargetException var3) {
            Minecraft.log.severe("Wystąpił bład " + var3.toString());
            var3.printStackTrace();
         }
      }

   }

   public void refreshInThisThread() {
      if (this.currentContent instanceof Refreshable) {
         ((Refreshable)this.currentContent).refresh();
      }

   }

   public ActionListener getComboBoxListenerInstance() {
      if (this.comboBoxListener == null) {
         this.comboBoxListener = new MultiPanelStub.ZyczuComboListener(null);
      }

      return this.comboBoxListener;
   }

   public ChangeListener getChangeListenerInstance() {
      if (this.changeListener == null) {
         this.changeListener = new MultiPanelStub.ZyczuChangeListener(null);
      }

      return this.changeListener;
   }

   public static MultiPanelStub getInstance() {
      return instance;
   }

   private class ZyczuChangeListener implements ChangeListener {
      private ZyczuChangeListener() {
         super();
      }

      public void stateChanged(ChangeEvent e) {
         switch(((JTabbedPane)e.getSource()).getSelectedIndex()) {
            case 0:
               LauncherFrame.getInstance().enableGlassPane();
               LauncherFrame.getInstance().validate();
               ModList ml = ModList.getInstance();
               ml.loadModsByModPack(MultiPanelStub.this.getCurrentModPack());
               ml.refresh();
               MultiPanelStub.this.openView(ml);
               LauncherFrame.getInstance().disableGlassPane();
               break;
            case 1:
               MultiPanelStub.this.openView(Polecane.getInstance());
               break;
            case 2:
               MultiPanelStub.this.openView(CatView.getInstance());
               break;
            case 3:
               MultiPanelStub.this.openView(Search.getInstance());
               break;
            case 4:
               MultiPanelStub.this.openView(MoreView.getInstance());
         }

      }
   }

   private class ZyczuComboListener implements ActionListener {
      private ZyczuComboListener() {
         super();
      }

      public void actionPerformed(ActionEvent arg0) {
         JComboBox kombo = (JComboBox)arg0.getSource();
         if (kombo.getSelectedIndex() >= 0) {
            MultiPanelStub.this.currentContent.onModPackChanged();
         }

      }
   }
}
