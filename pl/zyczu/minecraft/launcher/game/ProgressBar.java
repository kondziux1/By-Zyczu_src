package pl.zyczu.minecraft.launcher.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import pl.zyczu.minecraft.launcher.Minecraft;

public class ProgressBar extends JPanel {
   private static final long serialVersionUID = 5380182587202078479L;
   private short start = 0;
   private short koniec = 100;
   private short progress = 0;
   private Color[] kolorki = null;
   private short height = 0;

   public ProgressBar() {
      super();
   }

   public void setStart(short nowy) {
      this.start = nowy;
   }

   public void setKoniec(short nowy) {
      this.koniec = nowy;
   }

   public void setProgress(short nowy) {
      this.progress = nowy;
   }

   public void setPercentageProgress(short procent) {
      this.setProgress((short)(this.koniec * procent / 100));
   }

   public void updateProgress(short nowy) {
      this.setProgress(nowy);
      this.update();
   }

   public void updatePercentageProgress(short procent) {
      this.updateProgress((short)(this.koniec * procent / 100));
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               ProgressBar.this.update(ProgressBar.this.getGraphics());
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
            }

         }
      });
   }

   public void setHeight(short wysokosc) {
      this.height = wysokosc;
   }

   public int getHeight() {
      return this.height;
   }

   public void setColors(Color pierwszy, Color drugi) {
      short height = (short)this.getHeight();
      this.kolorki = new Color[height];
      short redStep = (short)(Math.abs(drugi.getRed() - pierwszy.getRed()) / height);
      short greenStep = (short)(Math.abs(drugi.getGreen() - pierwszy.getGreen()) / height);
      short blueStep = (short)(Math.abs(drugi.getBlue() - pierwszy.getBlue()) / height);

      for(short i = 0; i < height; ++i) {
         this.kolorki[i] = new Color(pierwszy.getRed() + redStep * i, pierwszy.getGreen() + greenStep * i, pierwszy.getBlue() + blueStep * i);
      }

   }

   private AlphaComposite makeComposite(float alpha) {
      int type = 3;
      return AlphaComposite.getInstance(type, alpha);
   }

   public void paintComponent(Graphics g) {
      short height = (short)this.getHeight();
      short width = (short)this.getWidth();
      Graphics2D g2 = (Graphics2D)g;
      Composite originalComposite = g2.getComposite();
      g2.setPaint(Color.BLACK);
      g2.setComposite(this.makeComposite(0.3F));
      g2.fillRect(0, 0, width + 1, height);
      g2.setComposite(originalComposite);
      short progressWidth = (short)(this.progress * (width - 1) / this.koniec);

      for(int i = 0; i < height; ++i) {
         g2.setColor(this.kolorki[i]);
         g2.drawLine(0, i, progressWidth, i);
      }

   }
}
