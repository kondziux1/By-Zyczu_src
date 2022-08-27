package pl.zyczu.minecraft.launcher.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import pl.zyczu.minecraft.launcher.LoginPanel;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.Properties;
import pl.zyczu.minecraft.launcher.UserModPacks;
import pl.zyczu.minecraft.launcher.repo.ModPack;

public class MultiPanel extends JPanel {
   private static final long serialVersionUID = -4238593815696697352L;
   private final int paddingTop = 40;
   private final int szerokosc = 834;
   private final int wysokosc = 390;
   private final int margines = 0;
   private final int lewo = 10;
   private final int gora = 80;
   private final int maxszer = 834;
   private final int tloLewo = 10;
   private final int tloGora = 70;
   private final int tloSzerokosc = 834;
   private final int tloWysokosc = 50;
   private final int comboSzerokosc = 360;
   public JComboBox modSelect = null;
   public MultiPanelStub sharedComponent = null;
   private JTabbedPane tabbedPane = null;
   private static MultiPanel instance = null;

   private MultiPanel() {
      super(true);
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(null);
      this.sharedComponent = new MultiPanelStub(new JLabel());
      this.sharedComponent.setReference(this);
      this.tabbedPane = new JTabbedPane();
      this.tabbedPane.setFont(Minecraft.getInstance().getFont(12));
      this.tabbedPane.addTab("Paczki modów", null, this.sharedComponent, "Zarządzaj paczkami modów, twórz nowe, dodawaj i usuwaj mody");
      this.tabbedPane.addTab("Polecane", null, null, "Zobacz mody wybrane dla ciebie przez Zyczu");
      this.tabbedPane.addTab("Kategorie", null, null, "Przeglądaj wszystkie mody w kategoriach");
      this.tabbedPane.addTab("Szukaj", null, null, "Znajdź interesujące cię mody");
      this.tabbedPane.setBounds(10, 80, 834, 390);
      this.tabbedPane.addChangeListener(this.sharedComponent.getChangeListenerInstance());
      this.sharedComponent.openView(Polecane.getInstance());
      this.modSelect = new JComboBox();
      this.modSelect.setFont(Minecraft.getInstance().getFont(12));
      this.modSelect.setToolTipText("Wybierz zestaw modów, który chcesz edytować");
      this.modSelect.setBounds(444, 72, 360, 28);
      this.modSelect.addActionListener(this.sharedComponent.getComboBoxListenerInstance());
      this.updateComboBox();
      JButton closebtn = new JButton();
      closebtn.setIcon(new ImageIcon(Minecraft.getInstance().closeIcon));
      closebtn.setToolTipText("Powrót do menu");
      closebtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            Properties properties = Properties.getInstance();
            properties.set("current_modpack", "user" + MultiPanel.this.modSelect.getSelectedIndex());
            LoginPanel loginPanel = LoginPanel.getInstance();
            loginPanel.updateComboBox();
            Minecraft.switchPanel(MultiPanel.this, loginPanel);
         }
      });
      closebtn.setBounds(809, 72, 30, 28);
      this.add(this.modSelect);
      this.add(closebtn);
      this.add(this.tabbedPane);
   }

   public void updateComboBox() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            MultiPanel.this.refresh();

            try {
               String cm = Properties.getInstance().get("current_modpack");
               if (!cm.contains("user")) {
                  return;
               }

               int x = new Integer(cm.replace("user", ""));
               MultiPanel.this.modSelect.setSelectedIndex(x);
            } catch (IllegalArgumentException var3) {
               MultiPanel.this.modSelect.setSelectedIndex(0);
               Properties.getInstance().set("current_modpack", "default");
            } catch (ArrayIndexOutOfBoundsException var4) {
               MultiPanel.this.modSelect.setSelectedIndex(0);
               Properties.getInstance().set("current_modpack", "default");
            } catch (NullPointerException var5) {
               MultiPanel.this.modSelect.setSelectedIndex(0);
               Properties.getInstance().set("current_modpack", "default");
            }

         }
      });
   }

   public void refresh() {
      this.modSelect.removeAllItems();

      for(ModPack paczka : UserModPacks.getInstance().getModPacks()) {
         this.modSelect.addItem(paczka.getName());
      }

   }

   public ModPack getCurrentModPack() {
      return (ModPack)UserModPacks.getInstance().getModPacks().get(this.modSelect.getSelectedIndex());
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               MultiPanel.this.update(MultiPanel.this.getGraphics());
            } catch (NullPointerException var2) {
               Minecraft.log.warning("Bład: " + var2.toString());
            }

         }
      });
   }

   private AlphaComposite makeComposite(float alpha) {
      int type = 3;
      return AlphaComposite.getInstance(type, alpha);
   }

   public void paintComponent(Graphics g) {
      g.drawImage(Minecraft.getInstance().backgroundGeneric, 0, 0, null);
      Graphics2D g2 = (Graphics2D)g;
      Composite originalComposite = g2.getComposite();
      g2.setPaint(Color.WHITE);
      g2.setComposite(this.makeComposite(0.6F));
      g2.fillRect(10, 70, 834, 50);
      g2.setComposite(originalComposite);
   }

   public static MultiPanel getInstance() {
      if (instance == null) {
         instance = new MultiPanel();
      }

      return instance;
   }

   public void setSelectedTab(int newTab) {
      this.tabbedPane.setSelectedIndex(newTab);
   }

   public void shutdownNow() {
      this.modSelect = null;
      this.sharedComponent = null;
      this.tabbedPane = null;
   }

   public static void shutdownInstance() {
      if (instance != null) {
         instance.shutdownNow();
      }

      instance = null;
   }
}
