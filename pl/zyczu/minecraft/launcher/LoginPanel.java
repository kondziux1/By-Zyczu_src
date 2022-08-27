package pl.zyczu.minecraft.launcher;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pl.zyczu.minecraft.launcher.game.LauncherPanel;
import pl.zyczu.minecraft.launcher.game.LauncherService;
import pl.zyczu.minecraft.launcher.game.UsernameCheck;
import pl.zyczu.minecraft.launcher.gui.MessageBox;
import pl.zyczu.minecraft.launcher.gui.ModPackTutorialPanel;
import pl.zyczu.minecraft.launcher.gui.MultiPanel;
import pl.zyczu.minecraft.launcher.repo.ModPack;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;

public class LoginPanel extends JPanel {
   private static final long serialVersionUID = 8136273811436433393L;
   private static LoginPanel instance = null;
   private final int szerokosc = 360;
   private final int wysokosc = 150;
   private final int odstep = 20;
   private final int margines = 10;
   private final int lewo = 484;
   private final int gora = 320;
   private JTextField nazwa = null;
   private JComboBox combo = null;
   private JButton przycisk0 = null;
   private JButton przycisk1 = null;
   private JButton przycisk2 = null;

   public LoginPanel() {
      super(true);
      instance = this;
      JLabel wersja = new JLabel("3.3.3", 4);
      wersja.setBounds(750, 455, 80, 25);
      wersja.setForeground(Color.WHITE);
      wersja.setFont(Minecraft.getInstance().getFont(10));
      this.add(wersja);
      Minecraft.log.debug("Wykonuje sie konstruktor");
      Properties p = Properties.getInstance();
      this.addMouseListener(new LoginPanel.Myszka());
      RepositoryManager repositoryManager = RepositoryManager.getInstance();
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(null);
      JLabel lanczer = new JLabel("Minecraft Launcher by Zyczu");
      lanczer.setBounds(484, 320, 280, 20);
      lanczer.setFont(Minecraft.getInstance().getFont(12));
      lanczer.setForeground(Color.BLACK);
      this.add(lanczer);
      JLabel nick = new JLabel("Twój Nick:");
      nick.setBounds(484, 350, 80, 20);
      nick.setFont(Minecraft.getInstance().getFont(12));
      nick.setForeground(Color.BLACK);
      this.add(nick);
      this.nazwa = new JTextField();
      this.nazwa.setBounds(564, 346, 260, 28);
      this.nazwa.setFont(Minecraft.getInstance().getFont(12));
      this.nazwa.setText(p.get("nick"));
      this.add(this.nazwa);
      JLabel paczki = new JLabel("Mody:");
      paczki.setBounds(484, 386, 50, 20);
      paczki.setFont(Minecraft.getInstance().getFont(12));
      paczki.setForeground(Color.BLACK);
      this.add(paczki);
      this.combo = new JComboBox();
      this.combo.setBounds(534, 380, 290, 28);
      this.combo.setFont(Minecraft.getInstance().getFont(12));
      this.updateComboBox();
      this.add(this.combo);
      this.przycisk0 = new JButton();
      this.przycisk0.setIcon(new ImageIcon(Minecraft.getInstance().settingsIcon));
      this.przycisk0.setBounds(484, 418, 36, 36);
      this.przycisk0.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            LoginPanel.this.switchToSettingsPanel();
         }
      });
      this.add(this.przycisk0);
      this.przycisk1 = new JButton("Własne mody");
      this.przycisk1.setBounds(527, 418, 150, 36);
      this.przycisk1.setFont(Minecraft.getInstance().getFont(12));
      this.przycisk1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (UserModPacks.getInstance().getUserModPacksCount() < 1) {
               ModPackTutorialPanel modPackTutorialPanel = ModPackTutorialPanel.getInstance();
               Minecraft.switchPanel(LoginPanel.this, modPackTutorialPanel);
            } else {
               MultiPanel multiPanel = MultiPanel.getInstance();
               Minecraft.switchPanel(LoginPanel.this, multiPanel);
            }

         }
      });
      this.add(this.przycisk1);
      this.przycisk2 = new JButton("Uruchom grę");
      this.przycisk2.setBounds(684, 416, 140, 40);
      this.przycisk2.setFont(Minecraft.getInstance().getFont(12));
      this.przycisk2
         .addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent arg0) {
                  if (LoginPanel.this.nazwa.getText().length() < 1) {
                     MessageBox.showErrorMessage("Błąd podczas uruchamiania gry!", "Twój nick w grze nie może być pusty!");
                  } else if (LoginPanel.this.nazwa.getText().equalsIgnoreCase("twoj_nick")) {
                     MessageBox.showErrorMessage("Błąd podczas uruchamiania gry!", "Proszę wpisać nazwę, jaką chcesz mieć w grze, zamiast \"twoj_nick\"");
                  } else if (!UsernameCheck.check(LoginPanel.this.nazwa.getText())) {
                     if (MessageBox.showLaunchGameMessage(
                        "Twój nick zawiera niedozwolone znaki!",
                        "Twój nick zawiera znaki, które nie są akceptowane przy rejestracji kont premium.<br><br>Nie będziesz mógł wejść na większość serwerów, zwłaszcza tych z pluginami AuthMe czy też xAuth.<br><br>Nick w minecraftcie powinien składać się wyłącznie z liter, cyfr oraz znaku _<br><br>Nick nie może zawierać polskich znaków, spacji, kropek, przecinków, itp.<br><br>Jeżeli mimo wszystko chcesz uruchomić grę z takim nickiem, możesz wyłączyć sprawdzanie nicku w ustawieniach."
                     )) {
                        LoginPanel.this.switchToSettingsPanel();
                     }
                  } else if (UsernameCheck.isBlacklisted(LoginPanel.this.nazwa.getText())) {
                     if (MessageBox.showLaunchGameMessage(
                        "Wykryto próbę wejścia na cudzy nick!",
                        "Twój nick jest identyczny z nickiem znanej osobistości z Minecrafta.<br><br>Granie na takim nicku nie jest zalecane i może zakończyć się banem na większości serwerów!<br><br>Jeżeli chcesz zmienić skina, NIE MUSISZ GRAĆ NA CUDZYN NICKU, Minecraft by Zyczu umożliwia ci zmianę skina na Minecraftcie nopremium. Skina zmieniamy na stronie <a href=\"\">minecraft.zyczu.pl</a>!<br><br>Jeżeli mimo wszystko chcesz uruchomić grę z takim nickiem, możesz wyłączyć sprawdzanie nicku w ustawieniach."
                     )) {
                        LoginPanel.this.switchToSettingsPanel();
                     }
                  } else {
                     LauncherPanel launcherPanel = LauncherPanel.getInstance();
                     Minecraft.switchPanel(LoginPanel.this, launcherPanel);
                     LauncherService service = LauncherService.getInstance();
                     service.setUsername(LoginPanel.this.nazwa.getText());
                     int index = LoginPanel.this.combo.getSelectedIndex();
                     int rozm = RepositoryManager.getInstance().getModPacks().size();
                     if (index < rozm) {
                        service.setModPack((ModPack)RepositoryManager.getInstance().getModPacks().get(index));
                     } else {
                        service.setModPack((ModPack)UserModPacks.getInstance().getModPacks().get(index - rozm));
                        service.setUserModPackId(index - rozm);
                     }
      
                     service.start();
                  }
      
               }
            }
         );
      this.add(this.przycisk2);
   }

   private void switchToSettingsPanel() {
      SettingsPanel settingsPanel = SettingsPanel.getInstance();
      settingsPanel.loadSettings();
      Minecraft.switchPanel(this, settingsPanel);
   }

   public void updateComboBox() {
      while(true) {
         try {
            String cm = Properties.getInstance().get("current_modpack");
            this.combo.removeAllItems();

            for(ModPack pak : RepositoryManager.getInstance().getModPacks()) {
               this.combo.addItem(pak.getName());
               if (pak.getId().equals(cm)) {
                  this.combo.setSelectedIndex(this.combo.getItemCount() - 1);
               }
            }

            UserModPacks userModPacks = UserModPacks.getInstance();
            Iterator<ModPack> var10 = userModPacks.getModPacks().iterator();

            while(var10.hasNext()) {
               this.combo.addItem(((ModPack)var10.next()).getName());
            }

            if (cm.contains("user")) {
               try {
                  int x = new Integer(cm.replace("user", ""));
                  this.combo.setSelectedIndex(RepositoryManager.getInstance().getModPacks().size() + x);
               } catch (IllegalArgumentException var6) {
                  this.combo.setSelectedIndex(0);
                  Properties.getInstance().set("current_modpack", "default");
               } catch (ArrayIndexOutOfBoundsException var7) {
                  this.combo.setSelectedIndex(0);
                  Properties.getInstance().set("current_modpack", "default");
               } catch (NullPointerException var8) {
                  this.combo.setSelectedIndex(0);
                  Properties.getInstance().set("current_modpack", "default");
               }
            }

            return;
         } catch (NullPointerException var9) {
            try {
               Minecraft.log.warning("NPE przy dodawaniu modów do comboboxa!");
               Thread.sleep(30L);
            } catch (InterruptedException var5) {
               var5.printStackTrace();
            }
         }
      }
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               LoginPanel.this.update(LoginPanel.this.getGraphics());
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
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
      g2.fillRect(474, 310, 360, 150);
      if (pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance().getX() > 0) {
         int xAd = 10;
         int yAd = 450 - pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance().getY();
         int wAd = 20 + pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance().getX();
         int hAd = 20 + pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance().getY();
         g2.fillRect(xAd, yAd - 20, wAd, hAd + 20);
         g2.setComposite(originalComposite);
         g2.setPaint(Color.BLACK);
         g2.setFont(Minecraft.getInstance().getFont(12));
         g2.drawString("Reklama:", xAd + 20, yAd);
         g2.setPaint(Color.WHITE);
         if (pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance().getImage() == null) {
            g2.drawRect(xAd + 10, yAd + 10, wAd - 20, hAd - 20);
         } else {
            g2.drawImage(pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance().getImage(), xAd + 10, yAd + 10, null);
         }
      } else {
         g2.setComposite(originalComposite);
      }

   }

   public static LoginPanel getInstance() {
      return instance;
   }

   public static void updateIfExists() {
      if (instance != null) {
         instance.update();
      }

   }

   public static void shutdownInstace() {
      instance = null;
   }

   private class Myszka implements MouseListener {
      private pl.zyczu.minecraft.launcher.ads.NetworkService ns = null;
      private Point punkt = null;
      private short xAd;
      private short yAd;
      private short wAd;
      private short hAd;

      public Myszka() {
         super();
         this.ns = pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance();
         this.xAd = 10;
         this.yAd = (short)(450 - this.ns.getY());
         this.wAd = (short)(20 + this.ns.getX());
         this.hAd = (short)(20 + this.ns.getY());
      }

      public void mouseClicked(MouseEvent arg0) {
      }

      public void mouseEntered(MouseEvent arg0) {
      }

      public void mouseExited(MouseEvent arg0) {
      }

      public void mousePressed(MouseEvent arg0) {
         if (this.ns.getX() > 0) {
            if (arg0.getPoint().x > this.xAd
               && arg0.getPoint().y > this.yAd
               && arg0.getPoint().x < this.xAd + this.wAd
               && arg0.getPoint().y < this.yAd + this.hAd) {
               this.punkt = arg0.getPoint();
            } else {
               this.punkt = null;
            }
         }

      }

      public void mouseReleased(MouseEvent arg0) {
         if (this.punkt != null
            && arg0.getPoint().x > this.xAd
            && arg0.getPoint().y > this.yAd
            && arg0.getPoint().x < this.xAd + this.wAd
            && arg0.getPoint().y < this.yAd + this.hAd) {
            try {
               Minecraft.log.debug("Kliknieto w reklame");
               Desktop.getDesktop().browse(this.ns.getDestination().toURI());
               this.ns.setX((short)0);
               LoginPanel.this.update();
            } catch (IOException var3) {
               var3.printStackTrace();
            } catch (URISyntaxException var4) {
               var4.printStackTrace();
            }
         }

      }
   }
}
