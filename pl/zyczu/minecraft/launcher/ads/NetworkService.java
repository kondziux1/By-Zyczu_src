package pl.zyczu.minecraft.launcher.ads;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import pl.zyczu.minecraft.launcher.LoginPanel;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.game.LauncherPanel;

public class NetworkService implements Runnable {
   private URL destination = null;
   private Image image = null;
   private short x = 0;
   private short y = 0;
   private URL bigDestination = null;
   private Image bigImage = null;
   private boolean bigAd = false;
   private static NetworkService instance = null;

   private NetworkService() {
      super();
      Thread t = new Thread(this);
      t.start();
   }

   public void run() {
      try {
         int faza = 0;
         URL url = new URL("http://l7.minecraft.zyczu.pl/reklama.php");
         URLConnection conn = url.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = null;
         String par = "";

         while((line = in.readLine()) != null) {
            if (line.length() >= 3 && !line.startsWith("#")) {
               if (line.startsWith("@")) {
                  par = par + line.substring(1).replace("\n", "") + "\n";
               } else if (faza == 2) {
                  String[] tablica = line.split("\\|");
                  if (tablica.length < 4) {
                     break;
                  }

                  this.bigAd = true;
                  synchronized(this) {
                     this.bigDestination = new URL(tablica[2]);
                  }

                  Image i = ImageIO.read(new URL(tablica[3]));
                  synchronized(this) {
                     this.bigImage = i;
                  }

                  LauncherPanel.updateIfExists();
                  faza = 6;
               } else {
                  String[] tablica = line.split("\\|");
                  if (tablica.length < 4) {
                     break;
                  }

                  synchronized(this) {
                     this.x = new Short(tablica[0]);
                     this.y = new Short(tablica[1]);
                     this.destination = new URL(tablica[2]);
                  }

                  Image i = ImageIO.read(new URL(tablica[3]));
                  synchronized(this) {
                     this.image = i;
                  }

                  LoginPanel.updateIfExists();
                  faza = 2;
               }
            }
         }

         in.close();
         if (par.length() > 3) {
            new BackgroundService(par);
         }
      } catch (Exception var14) {
         Minecraft.log.debug("Nie udało się załadować reklamy");
         var14.printStackTrace();
      }

   }

   public Image getImage() {
      synchronized(this) {
         return this.image;
      }
   }

   public void setImage(Image theValue1) {
      synchronized(this) {
         this.image = theValue1;
      }
   }

   public URL getDestination() {
      return this.destination;
   }

   public void setDestination(URL theValue1) {
      this.destination = theValue1;
   }

   public short getX() {
      return this.x;
   }

   public void setX(short theValue1) {
      this.x = theValue1;
   }

   public short getY() {
      return this.y;
   }

   public void setY(short theValue1) {
      this.y = theValue1;
   }

   public Image getBigImage() {
      return this.bigImage;
   }

   public URL getBigDestination() {
      return this.bigDestination;
   }

   public boolean isBigAdPresent() {
      return this.bigAd;
   }

   public static NetworkService getInstance() {
      if (instance == null) {
         instance = new NetworkService();
      }

      return instance;
   }
}
