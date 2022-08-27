package net.infonode.gui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import net.infonode.util.ReleaseInfo;

public class ReleaseInfoDialog {
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");

   public ReleaseInfoDialog() {
      super();
   }

   public static void showDialog(ReleaseInfo var0, String var1) {
      showDialog(new ReleaseInfo[]{var0}, var1 == null ? null : new String[]{var1});
   }

   public static void showDialog(ReleaseInfo[] var0, String[] var1) {
      JComponent var2 = constructMessage(var0, var1);
      ReleaseInfoDialog$1 var3 = new ReleaseInfoDialog$1(var2, 20, 31, var2);
      var2.setBorder(new EmptyBorder(10, 20, 10, 20));
      var3.getViewport().setBackground(Color.white);
      JOptionPane.showMessageDialog(null, var3, "Product Release Information", 1);
   }

   private static JComponent constructMessage(ReleaseInfo[] var0, String[] var1) {
      Box var2 = new Box(1);

      for(int var3 = 0; var3 < var0.length; ++var3) {
         JLabel var4 = new JLabel(
            "<html><body>"
               + (var1 != null && var1[var3] != null ? var1[var3] + "<br>" : "")
               + "<table>"
               + "<tr><td style='font-weight: bold;'>Product Name:</td><td>"
               + var0[var3].getProductName()
               + "</td></tr>"
               + "<tr><td style='font-weight: bold;'>Version:</td><td>"
               + var0[var3].getProductVersion().toString()
               + "</td></tr>"
               + "<tr><td style='font-weight: bold;'>Build Time:</td><td>"
               + DATE_FORMAT.format(new Date(var0[var3].getBuildTime()))
               + "</td></tr>"
               + "<tr><td style='font-weight: bold;'>License:</td><td>"
               + var0[var3].getLicense()
               + "</td></tr>"
               + "<tr><td style='font-weight: bold;'>Vendor:</td><td>"
               + var0[var3].getProductVendor()
               + "</td></tr>"
               + "<tr><td style='font-weight: bold;'>Homepage:</td><td>"
               + var0[var3].getHomepage()
               + "</td></tr>"
               + "</table></body></html>"
         );
         var4.setFont(var4.getFont().deriveFont(0));
         var4.setBorder(
            new CompoundBorder(new EmptyBorder(0, 0, var3 == var0.length - 1 ? 0 : 10, 0), new TitledBorder(" " + var0[var3].getProductName() + " "))
         );
         var2.add(var4);
      }

      return var2;
   }

   static {
      DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
   }
}
