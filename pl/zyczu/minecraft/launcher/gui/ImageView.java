package pl.zyczu.minecraft.launcher.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pl.zyczu.minecraft.launcher.Minecraft;

public class ImageView extends JPanel implements Runnable {
   private static final long serialVersionUID = -435421477994615977L;
   private static Map<String, Image> cache = new HashMap();
   private Thread watek = null;
   private static boolean dloading = false;
   private String url = null;

   public ImageView() {
      super(true);
      this.setOpaque(false);
      this.setPreferredSize(new Dimension(160, 120));
   }

   public void setURL(URL u) {
      this.url = u.toString();
      if (!cache.containsKey(this.url)) {
         cache.put(this.url, null);
         this.watek = new Thread(this);
         this.watek.start();
      }

   }

   public void paintComponent(Graphics g) {
      Image i = (Image)cache.get(this.url);
      if (i != null) {
         g.drawImage(i, 0, 0, null);
      }

      g.setColor(Color.BLACK);
      g.drawRect(0, 0, 159, 119);
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               ImageView.this.update(ImageView.this.getGraphics());
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
            }

         }
      });
   }

   public void run() {
      while(true) {
         try {
            if (dloading) {
               Thread.sleep(10L);
               continue;
            }

            dloading = true;
            Minecraft.log.debug("Pobieranie obrazka " + this.url);
            Image obrazek = ImageIO.read(new URL(this.url));
            dloading = false;
            cache.put(this.url, obrazek);
            this.update();
         } catch (Exception var2) {
            dloading = false;
            Minecraft.log.warning("Wyjatek w watku: " + var2.toString());
         }

         return;
      }
   }
}
