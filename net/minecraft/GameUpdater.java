package net.minecraft;

import java.applet.Applet;
import java.io.File;
import java.io.FilePermission;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureClassLoader;
import java.util.Vector;

public class GameUpdater implements Runnable {
   public static final int STATE_INIT = 1;
   public static final int STATE_DETERMINING_PACKAGES = 2;
   public static final int STATE_CHECKING_CACHE = 3;
   public static final int STATE_DOWNLOADING = 4;
   public static final int STATE_EXTRACTING_PACKAGES = 5;
   public static final int STATE_UPDATING_CLASSPATH = 6;
   public static final int STATE_SWITCHING_APPLET = 7;
   public static final int STATE_INITIALIZE_REAL_APPLET = 8;
   public static final int STATE_START_REAL_APPLET = 9;
   public static final int STATE_DONE = 10;
   public long percentage;
   public long currentSizeDownload;
   public long totalSizeDownload;
   public long currentSizeExtract;
   public long totalSizeExtract;
   protected long versionNumber = 0L;
   protected File classpathFile;
   protected static ClassLoader classLoader;
   protected Thread loaderThread;
   protected Thread animationThread;
   public boolean fatalError;
   public String fatalErrorDescription;
   protected String subtaskMessage = "";
   protected int state = 1;
   protected boolean lzmaSupported = false;
   protected boolean pack200Supported = false;
   protected String[] genericErrorMessage = new String[]{
      "An error occured while loading the applet.", "Please contact support to resolve this issue.", "<placeholder for error message>"
   };
   protected boolean certificateRefused;
   protected String[] certificateRefusedMessage = new String[]{
      "Permissions for Applet Refused.", "Please accept the permissions dialog to allow", "the applet to continue the loading process."
   };
   protected static boolean natives_loaded = false;
   public boolean forceUpdate = false;
   public static final String[] gameFiles = new String[]{"lwjgl.jar", "jinput.jar", "lwjgl_util.jar", "minecraft.jar"};
   protected File mcroot = null;
   protected File bin = null;
   protected File mcjar = null;

   public GameUpdater(File f, File g) {
      super();
      this.mcroot = f;
      this.bin = new File(this.mcroot, "bin");
      this.mcjar = g;
   }

   public void init() {
      this.state = 1;

      try {
         Class.forName("LZMA.LzmaInputStream");
         this.lzmaSupported = true;
      } catch (Throwable var3) {
      }

      try {
         Class.forName("java.util.jar.Pack200").getSimpleName();
         this.pack200Supported = true;
      } catch (Throwable var2) {
      }

   }

   protected String generateStacktrace(Exception exception) {
      Writer result = new StringWriter();
      PrintWriter printWriter = new PrintWriter(result);
      exception.printStackTrace(printWriter);
      return result.toString();
   }

   protected String getDescriptionForState() {
      switch(this.state) {
         case 1:
            return "Inicjowanie minecrafta";
         case 2:
            return "Sprawdzanie plikow do pobrania";
         case 3:
            return "Sprawdzanie cache";
         case 4:
            return "Pobieranie plikow";
         case 5:
            return "Wypakowywanie";
         case 6:
            return "Aktualizowanie classpath";
         case 7:
            return "Switching applet";
         case 8:
            return "Initializing real applet";
         case 9:
            return "Starting real applet";
         case 10:
            return "Ukończono";
         default:
            return "unknown state";
      }
   }

   protected void updateClassPath() throws Exception {
      File dir = this.bin;
      String[] nejmz = new String[]{"lwjgl.jar", "lwjgl_util.jar", "jinput.jar", "minecraft.jar"};
      URL[] urls = new URL[nejmz.length];

      for(int i = 0; i < nejmz.length; ++i) {
         urls[i] = new File(this.bin, nejmz[i]).toURI().toURL();
      }

      if (classLoader == null) {
         classLoader = new URLClassLoader(urls) {
            protected PermissionCollection getPermissions(CodeSource codesource) {
               PermissionCollection perms = null;

               try {
                  Method method = SecureClassLoader.class.getDeclaredMethod("getPermissions", CodeSource.class);
                  method.setAccessible(true);
                  perms = (PermissionCollection)method.invoke(this.getClass().getClassLoader(), codesource);
                  String host = "minecraft.zyczu.pl";
                  if (host != null && host.length() > 0) {
                     perms.add(new SocketPermission(host, "connect,accept"));
                  } else {
                     codesource.getLocation().getProtocol().equals("file");
                  }

                  perms.add(new FilePermission("<<ALL FILES>>", "read"));
               } catch (Exception var5) {
                  var5.printStackTrace();
               }

               return perms;
            }
         };
      }

      String path = dir.getAbsolutePath();
      if (!path.endsWith(File.separator)) {
         path = path + File.separator;
      }

      this.unloadNatives(path);
      System.setProperty("org.lwjgl.librarypath", path + "natives");
      System.setProperty("net.java.games.input.librarypath", path + "natives");
      natives_loaded = true;
   }

   protected void unloadNatives(String nativePath) {
      if (natives_loaded) {
         try {
            Field field = ClassLoader.class.getDeclaredField("loadedLibraryNames");
            field.setAccessible(true);
            Vector libs = (Vector)field.get(this.getClass().getClassLoader());
            String path = new File(nativePath).getCanonicalPath();

            for(int i = 0; i < libs.size(); ++i) {
               String s = (String)libs.get(i);
               if (s.startsWith(path)) {
                  libs.remove(i);
                  --i;
               }
            }
         } catch (Exception var7) {
            var7.printStackTrace();
         }

      }
   }

   public void run() {
      this.init();

      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
            public Object run() throws Exception {
               GameUpdater.this.zyczuUpdater();
               return null;
            }
         });
      } catch (PrivilegedActionException var2) {
         System.out.println("PrivilegedExceptionAction failed!");
         this.zyczuUpdater();
      }

   }

   public void zyczuUpdater() {
      try {
         this.updateClassPath();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public Applet createApplet() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      Class<?> appletClass = classLoader.loadClass("net.minecraft.client.MinecraftApplet");
      return (Applet)appletClass.newInstance();
   }

   public boolean canPlayOffline() {
      return true;
   }
}
