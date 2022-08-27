package net.minecraft;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.VolatileImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Launcher extends Applet implements Runnable, AppletStub {
   private static final long serialVersionUID = 1L;
   public Map<String, String> customParameters = new HashMap();
   private GameUpdater gameUpdater;
   private boolean gameUpdaterStarted = false;
   private Applet applet;
   private Image bgImage;
   private boolean active = false;
   private int context = 0;
   private VolatileImage img;

   public Launcher() {
      super();
   }

   public boolean isActive() {
      if (this.context == 0) {
         this.context = -1;

         try {
            if (this.getAppletContext() != null) {
               this.context = 1;
            }
         } catch (Exception var2) {
         }
      }

      return this.context == -1 ? this.active : super.isActive();
   }

   public void init(Image tlo, String userName, String sessionId, boolean wyjdzZgry, File mcroot, File mcjar) {
      try {
         this.bgImage = tlo;
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      this.customParameters.put("username", userName);
      this.customParameters.put("sessionid", sessionId);
      if (wyjdzZgry) {
         this.customParameters.put("stand-alone", "true");
      }

      this.gameUpdater = new GameUpdater(mcroot, mcjar);
   }

   public boolean canPlayOffline() {
      return true;
   }

   public void init() {
      if (this.applet != null) {
         this.applet.init();
      }
   }

   public void start() {
      if (this.applet != null) {
         this.applet.start();
      } else if (!this.gameUpdaterStarted) {
         Thread t = new Thread() {
            public void run() {
               Launcher.this.gameUpdater.run();

               try {
                  if (!Launcher.this.gameUpdater.fatalError) {
                     Launcher.this.replace(Launcher.this.gameUpdater.createApplet());
                  }
               } catch (ClassNotFoundException var2) {
                  var2.printStackTrace();
               } catch (InstantiationException var3) {
                  var3.printStackTrace();
               } catch (IllegalAccessException var4) {
                  var4.printStackTrace();
               }

            }
         };
         t.setDaemon(true);
         t.start();
         t = new Thread() {
            public void run() {
               while(Launcher.this.applet == null) {
                  Launcher.this.repaint();

                  try {
                     Thread.sleep(10L);
                  } catch (InterruptedException var2) {
                     var2.printStackTrace();
                  }
               }

            }
         };
         t.setDaemon(true);
         t.start();
         this.gameUpdaterStarted = true;
      }
   }

   public void stop() {
      if (this.applet != null) {
         this.active = false;
         this.applet.stop();
      }
   }

   public void destroy() {
      if (this.applet != null) {
         this.applet.destroy();
      }
   }

   public void replace(Applet applet) {
      this.applet = applet;
      applet.setStub(this);
      applet.setSize(this.getWidth(), this.getHeight());
      this.setLayout(new BorderLayout());
      this.add(applet, "Center");
      applet.init();
      this.active = true;
      applet.start();
      this.validate();
   }

   public void update(Graphics g) {
      this.paint(g);
   }

   public void paint(Graphics g2) {
      if (this.applet == null) {
         int w = this.getWidth();
         int h = this.getHeight();
         if (this.img == null || this.img.getWidth() != w || this.img.getHeight() != h) {
            this.img = this.createVolatileImage(w, h);
         }

         Graphics g = this.img.getGraphics();
         g.drawImage(this.bgImage, 0, 0, null);
         g.setColor(Color.BLACK);
         g.dispose();
         g2.drawImage(this.img, 0, 0, w, h, null);
      }
   }

   public void run() {
   }

   public String getParameter(String name) {
      String custom = (String)this.customParameters.get(name);
      if (custom != null) {
         return custom;
      } else {
         try {
            return super.getParameter(name);
         } catch (Exception var4) {
            this.customParameters.put(name, null);
            return null;
         }
      }
   }

   public void appletResize(int width, int height) {
   }

   public URL getDocumentBase() {
      try {
         return new URL("http://minecraft.zyczu.pl/");
      } catch (MalformedURLException var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
