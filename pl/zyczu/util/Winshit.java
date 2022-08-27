package pl.zyczu.util;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.PointerByReference;
import net.minecraft.LauncherFrame;

public class Winshit {
   public Winshit() {
      super();
   }

   public static boolean isWinshit() {
      String os = java.lang.System.getProperty("os.name").toLowerCase();
      return os.contains("win");
   }

   public static void win7pinToTaskbar() {
      if (isWinshit()) {
         try {
            Native.register("shell32");
            setCurrentProcessExplicitAppUserModelID(LauncherFrame.class.getName());
            java.lang.System.out.println(getCurrentProcessExplicitAppUserModelID());
         } catch (UnsatisfiedLinkError var1) {
            java.lang.System.out.println("Masz stara wersje windowsa!");
         } catch (Exception var2) {
            java.lang.System.out.println("Masz Windows 7 lub nowszy!");
         }
      }

   }

   public static String getCurrentProcessExplicitAppUserModelID() {
      PointerByReference r = new PointerByReference();
      if (GetCurrentProcessExplicitAppUserModelID(r).longValue() == 0L) {
         Pointer p = r.getValue();
         return p.getString(0L, true);
      } else {
         return "N/A";
      }
   }

   public static void setCurrentProcessExplicitAppUserModelID(String appID) {
      if (SetCurrentProcessExplicitAppUserModelID(new WString(appID)).longValue() != 0L) {
         throw new RuntimeException("unable to set current process explicit AppUserModelID to: " + appID);
      }
   }

   private static native NativeLong GetCurrentProcessExplicitAppUserModelID(PointerByReference var0);

   private static native NativeLong SetCurrentProcessExplicitAppUserModelID(WString var0);
}
