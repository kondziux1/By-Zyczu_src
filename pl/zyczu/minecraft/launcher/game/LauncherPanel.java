package pl.zyczu.minecraft.launcher.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.ads.NetworkService;

public class LauncherPanel extends JPanel {
   private static final long serialVersionUID = 130077564603509211L;
   private final int margines = 10;
   private final int szerokosc = 834;
   private final int wysokosc = 80;
   private final int gora = 70;
   private final int panelSzerokosc = 814;
   private boolean adEnabled = false;
   private JPanel holder = null;
   private ProgressBar progressBar = null;
   private JLabel naglowek = null;
   private JLabel linia1 = null;
   private JLabel linia2 = null;
   private static LauncherPanel instance = null;

   public LauncherPanel() {
      super(true);
      this.setOpaque(false);
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(null);
      this.adEnabled = NetworkService.getInstance().isBigAdPresent();
      this.addMouseListener(new LauncherPanel.Myszka(null));
      this.holder = new JPanel();
      this.holder.setOpaque(false);
      this.holder.setLayout(null);
      if (this.adEnabled) {
         this.holder.setBounds(20, 70, 814, 80);
      } else {
         this.holder.setBounds(20, 200, 814, 80);
      }

      this.naglowek = new JLabel("Aktualizacja gry", 0);
      this.naglowek.setFont(Minecraft.getInstance().getFont(18));
      this.naglowek.setVerticalAlignment(0);
      this.naglowek.setBounds(0, 0, 814, 30);
      this.holder.add(this.naglowek);
      this.linia1 = new JLabel("Czekaj...", 0);
      this.linia1.setFont(Minecraft.getInstance().getFont(12));
      this.linia1.setVerticalAlignment(0);
      this.linia1.setBounds(0, 30, 814, 20);
      this.holder.add(this.linia1);
      this.linia2 = new JLabel("...", 0);
      this.linia2.setFont(Minecraft.getInstance().getFont(12));
      this.linia2.setVerticalAlignment(0);
      this.linia2.setBounds(0, 50, 814, 20);
      this.holder.add(this.linia2);
      this.progressBar = new ProgressBar();
      this.progressBar.setBounds(0, 70, 814, 6);
      this.holder.add(this.progressBar);
      this.progressBar.setHeight((short)6);
      this.progressBar.setColors(new Color(0, 168, 255), new Color(0, 70, 255));
      this.progressBar.setPercentageProgress((short)1);
      this.add(this.holder);
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               LauncherPanel.this.update(LauncherPanel.this.getGraphics());
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
      if (this.adEnabled) {
         this.paintComponent(g, (short)0);
      } else {
         this.paintComponent(g, 0L);
      }

   }

   private void paintComponent(Graphics g, short ążćłęńś) {
      g.drawImage(Minecraft.getInstance().backgroundGeneric, 0, 0, null);
      Graphics2D g2 = (Graphics2D)g;
      g2.setPaint(Color.BLACK);
      g2.setFont(Minecraft.getInstance().getFont(12));
      g2.drawString("Reklama:", 30, 150);
      Composite originalComposite = g2.getComposite();
      g2.setPaint(Color.WHITE);
      g2.setComposite(this.makeComposite(0.6F));
      g2.fillRect(10, 70, 834, 80);
      g2.fillRect(10, 150, 834, 320);
      g2.setComposite(originalComposite);
      Image obrazek = NetworkService.getInstance().getBigImage();
      if (obrazek != null) {
         g2.drawImage(obrazek, 27, 154, null);
      }

   }

   private void paintComponent(Graphics g, long ńęśźłćęż) {
      g.drawImage(Minecraft.getInstance().backgroundGeneric, 0, 0, null);
      Graphics2D g2 = (Graphics2D)g;
      Composite originalComposite = g2.getComposite();
      g2.setPaint(Color.WHITE);
      g2.setComposite(this.makeComposite(0.6F));
      g2.fillRect(10, 200, 834, 80);
      g2.setComposite(originalComposite);
   }

   public static void updateIfExists() {
      if (instance != null) {
         instance.update();
      }

   }

   public static LauncherPanel getInstance() {
      if (instance == null) {
         instance = new LauncherPanel();
      }

      return instance;
   }

   public void setFirstLine(String liniaPierwsza) {
      this.linia1.setText(liniaPierwsza);
   }

   public void setSecondLine(String liniaDruga) {
      this.linia2.setText(liniaDruga);
   }

   public void setHeader(String nagl) {
      this.naglowek.setText(nagl);
   }

   public void processMessage(Message msg, String mesydż) {
      switch(msg) {
         case SECOND_LINE:
            this.setSecondLine(mesydż);
            break;
         case FIRST_LINE:
            this.setFirstLine(mesydż);
            break;
         case PROGRESS_SETVALUE:
            this.progressBar.updateProgress(new Short(mesydż));
            break;
         case PROGRESS_SETUP:
            this.progressBar.setStart(new Short("0"));
            this.progressBar.setKoniec(new Short("800"));
            break;
         case STRASZNY_BLAD:
            this.setHeader("Straszny bład!");
            this.setFirstLine("Wystąpił bład przy próbie uruchomienia gry:");
            this.setSecondLine(mesydż);
            break;
         case HEADER:
            this.setHeader(mesydż);
      }

   }

   private class Myszka implements MouseListener {
      private Point punkt = null;

      private Myszka() {
         super();
      }

      public void mouseClicked(MouseEvent arg0) {
      }

      public void mouseEntered(MouseEvent arg0) {
      }

      public void mouseExited(MouseEvent arg0) {
      }

      public void mousePressed(MouseEvent arg0) {
         if (LauncherPanel.this.adEnabled) {
            Point p = arg0.getPoint();
            if (p.getX() > 0.0 && p.getY() > 170.0 && p.getX() < 834.0 && p.getY() < 460.0) {
               this.punkt = p;
            } else {
               this.punkt = null;
            }
         }

      }

      public void mouseReleased(MouseEvent arg0) {
         if (LauncherPanel.this.adEnabled && this.punkt != null) {
            Point p = arg0.getPoint();
            if (p.getX() > 0.0 && p.getY() > 170.0 && p.getX() < 834.0 && p.getY() < 460.0) {
               LauncherPanel.this.adEnabled = false;
               Minecraft.log.debug("Kliknieto w reklame");

               try {
                  Desktop.getDesktop().browse(NetworkService.getInstance().getBigDestination().toURI());
               } catch (IOException var4) {
                  Minecraft.log.severe("Nie moge otworzyć reklamy! Bład " + var4.toString());
                  var4.printStackTrace();
               } catch (URISyntaxException var5) {
                  Minecraft.log.severe("Nie moge otworzyć reklamy! Bład " + var5.toString());
                  var5.printStackTrace();
               }

               LauncherPanel.this.holder.setBounds(20, 200, 814, 80);
               LauncherPanel.this.update();
            }
         }

      }
   }
}
