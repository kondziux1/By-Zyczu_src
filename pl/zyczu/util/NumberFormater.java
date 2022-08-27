package pl.zyczu.util;

import java.text.DecimalFormat;

public class NumberFormater {
   public NumberFormater() {
      super();
   }

   public static String formatBytes(double bajty) {
      int level = 0;

      String[] nazwy;
      for(nazwy = new String[]{"B", "KB", "MB", "GB"}; bajty > 1024.0; ++level) {
         bajty /= 1024.0;
      }

      DecimalFormat twoDForm;
      if (bajty < 10.0) {
         twoDForm = new DecimalFormat("#.##");
      } else if (bajty < 100.0) {
         twoDForm = new DecimalFormat("##.#");
      } else if (bajty < 1000.0) {
         twoDForm = new DecimalFormat("###");
      } else {
         twoDForm = new DecimalFormat("####");
      }

      StringBuilder respBuilder = new StringBuilder();
      respBuilder.append(twoDForm.format(bajty)).append(" ").append(nazwy[level]);
      return respBuilder.toString();
   }

   public static String formatSpeed(double bajty) {
      int level = 0;

      String[] nazwy;
      for(nazwy = new String[]{"B/s", "KB/s", "MB/s", "GB/s"}; bajty > 1024.0; ++level) {
         bajty /= 1024.0;
      }

      DecimalFormat twoDForm;
      if (bajty < 10.0) {
         twoDForm = new DecimalFormat("#.##");
      } else if (bajty < 100.0) {
         twoDForm = new DecimalFormat("##.#");
      } else if (bajty < 1000.0) {
         twoDForm = new DecimalFormat("###");
      } else {
         twoDForm = new DecimalFormat("####");
      }

      StringBuilder respBuilder = new StringBuilder();
      respBuilder.append(twoDForm.format(bajty)).append(" ").append(nazwy[level]);
      return respBuilder.toString();
   }

   public static String formatTime(long czas) {
      int godz = (int)(czas / 3600L);
      czas %= 3600L;
      int min = (int)(czas / 60L);
      int sek = (int)(czas % 60L);
      return timeZeroFill(godz) + ":" + timeZeroFill(min) + ":" + timeZeroFill(sek);
   }

   private static String timeZeroFill(int l) {
      return l < 10 ? "0" + l : "" + l;
   }
}
