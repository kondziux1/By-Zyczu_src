package pl.zyczu.minecraft.launcher.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;

public class MessageBox {
   public MessageBox() {
      super();
   }

   public static boolean showLaunchGameMessage(String naglowek, String komunikat) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, 1));
      JLabel nagl = new JLabel(naglowek, 0);
      nagl.setFont(Minecraft.getInstance().getFont(18));
      nagl.setVerticalAlignment(0);
      panel.add(nagl);
      nagl = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + komunikat + "</p></table></html>");
      nagl.setFont(Minecraft.getInstance().getFont(12));
      panel.add(nagl);
      int rezultat = JOptionPane.showOptionDialog(LauncherFrame.getInstance(), panel, naglowek, 2, 2, null, new String[]{"OK", "Zmień ustawienia"}, null);
      return rezultat == 1;
   }

   public static void showErrorMessage(String nagłówek, String mesydż) {
      JLabel l = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + mesydż + "</p></table></html>");
      l.setFont(Minecraft.getInstance().getFont(12));
      JOptionPane.showMessageDialog(LauncherFrame.getInstance(), l, nagłówek, 0);
   }

   public static boolean showConfirm(String string, String string2) {
      JLabel l = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + string2 + "</p></table></html>");
      l.setFont(Minecraft.getInstance().getFont(12));
      int rezultat = JOptionPane.showOptionDialog(LauncherFrame.getInstance(), l, string, 2, 2, null, new String[]{"Tak, usuń", "Nie usuwaj"}, null);
      return rezultat == 0;
   }

   public static boolean showYesNoMessage(String nagłówek, String mesydż) {
      JLabel l = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + mesydż + "</p></table></html>");
      l.setFont(Minecraft.getInstance().getFont(12));
      int rezultat = JOptionPane.showOptionDialog(LauncherFrame.getInstance(), l, nagłówek, 2, 2, null, new String[]{"Tak", "Nie"}, "default");
      return rezultat == 0;
   }
}
