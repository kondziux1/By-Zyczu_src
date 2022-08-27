package pl.zyczu.minecraft.launcher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;

public class Polecane extends Loadable implements Runnable {
   private static final long serialVersionUID = -809697836864891063L;
   private String htmlCode = null;
   private JLabel napis = null;
   private LinkedList<Polecane.Element> elementy = null;
   private static Polecane instance = null;

   private Polecane() {
      super(true);
      this.addMouseListener(new Polecane.Myszka(null));
      this.elementy = new LinkedList();
      this.napis = new JLabel("Ładowanie...");
      this.napis.setFont(Minecraft.getInstance().getFont(12));
      this.setLayout(new GridBagLayout());
      this.add(this.napis, new GridBagConstraints());
      Thread downloadThread = new Thread(this);
      downloadThread.start();
   }

   public void run() {
      try {
         StringBuilder htmlBuilder = new StringBuilder();
         URL url = new URL("http://l7.minecraft.zyczu.pl/polecane.html");
         URLConnection conn = url.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String linia = null;

         while((linia = in.readLine()) != null) {
            if (linia.length() >= 3 && !linia.startsWith("#")) {
               String[] polecenia = linia.split("\\|");
               Polecane.Element e = new Polecane.Element(polecenia);
               e.setModToClick(polecenia[5]);
               this.elementy.add(e);
            }
         }

         in.close();
         this.ustawTresc(htmlBuilder.toString());
      } catch (NumberFormatException var8) {
         this.komunikat("Niepoprawny numer pozycji grafiki");
      } catch (MalformedURLException var9) {
         this.komunikat("Niepoprawny adres URL grafiki");
      } catch (IOException var10) {
         this.komunikat("Brak połączenia internetowego");
      } catch (ArrayIndexOutOfBoundsException var11) {
         this.komunikat("Błąd serwera");
      }

      this.pobierzObrazki();
   }

   private void pobierzObrazki() {
      for(Polecane.Element element : this.elementy) {
         try {
            Image obrazek = ImageIO.read(element.getUrlToLoad());
            if (obrazek == null) {
               throw new IOException("!");
            }

            element.setImage(obrazek);
            this.update();
         } catch (IOException var4) {
            Minecraft.log.log("Polecane", "Nie moge pobrac: " + element.getName());
         }
      }

   }

   private void komunikat(final String tresc) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            Polecane.this.napis.setText(tresc);
         }
      });
   }

   private void ustawTresc(final String tresc) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            Polecane.this.napis.setLayout(new BorderLayout());
            Polecane.this.remove(Polecane.this.napis);
            Polecane.this.napis.setText(tresc);
            Polecane.this.add(Polecane.this.napis, "North");
         }
      });
   }

   public String getHtmlCode() {
      return this.htmlCode;
   }

   public void setHtmlCode(String theValue1) {
      this.htmlCode = theValue1;
   }

   private void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Polecane.this.update(Polecane.this.getGraphics());
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu");
            }

         }
      });
   }

   public void paintComponent(Graphics g) {
      Graphics2D graphics2D = (Graphics2D)g;
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

      for(Polecane.Element e : this.elementy) {
         if (e.getImage() == null) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(e.getLeft(), e.getTop(), e.getWidth(), e.getHeight());
         } else {
            graphics2D.drawImage(e.getImage(), e.getLeft(), e.getTop(), e.getWidth(), e.getHeight(), null);
         }
      }

   }

   @Override
   public void onLoad(MultiPanelStub ref) {
      ref.setSelectedTab(1);
   }

   @Override
   public void onModPackChanged() {
   }

   public static Polecane getInstance() {
      if (instance == null) {
         instance = new Polecane();
      }

      return instance;
   }

   private class Element {
      protected static final int margin = 10;
      protected static final int widthMultipler = 200;
      protected static final int heightMultipler = 100;
      protected static final int tileSpacing = 2;
      private short x;
      private short y;
      private short w;
      private short h;
      private Image img = null;
      private URL urlToLoad = null;
      private String modToClick = null;

      public Element(short theValue1, short theValue2, short theValue3, short theValue4, URL theValue5) {
         super();
         this.x = theValue1;
         this.y = theValue2;
         this.w = theValue3;
         this.h = theValue4;
         this.urlToLoad = theValue5;
      }

      public Element(String[] theValue1) throws NumberFormatException, MalformedURLException {
         this(new Short(theValue1[0]), new Short(theValue1[1]), new Short(theValue1[2]), new Short(theValue1[3]), new URL(theValue1[4]));
      }

      public int getLeft() {
         return 10 + this.x * 202;
      }

      public int getTop() {
         return 10 + this.y * 102;
      }

      public int getWidth() {
         return this.w * 200 + (this.w - 1) * 2;
      }

      public int getHeight() {
         return this.h * 100 + (this.h - 1) * 2;
      }

      public void setImage(Image loadedImage) {
         synchronized(this) {
            this.img = loadedImage;
         }
      }

      public Image getImage() {
         synchronized(this) {
            return this.img;
         }
      }

      public String getModToClick() {
         return this.modToClick;
      }

      public void setModToClick(String theValue1) {
         this.modToClick = theValue1;
      }

      public String getName() {
         return this.urlToLoad.toString();
      }

      public URL getUrlToLoad() {
         return this.urlToLoad;
      }

      public boolean sprawdzPunkt(Point p) {
         return p.x > this.getLeft() && p.y > this.getTop() && p.x < this.getLeft() + this.getWidth() && p.y < this.getTop() + this.getHeight();
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
         if (arg0.getButton() == 1) {
            this.punkt = arg0.getPoint();
         }

      }

      public void mouseReleased(MouseEvent arg0) {
         if (arg0.getButton() == 1) {
            Point drugi = arg0.getPoint();

            for(Polecane.Element e : Polecane.this.elementy) {
               if (e.sprawdzPunkt(this.punkt)) {
                  if (e.sprawdzPunkt(drugi)) {
                     Minecraft.log.debug("Wykryto poprawne kliknięcie");
                     ModView mv = ModView.getInstance();
                     mv.loadModInfo(RepositoryManager.getInstance().getModById(e.modToClick));
                     Polecane.this.openView(mv);
                  }
                  break;
               }
            }
         }

      }
   }
}
