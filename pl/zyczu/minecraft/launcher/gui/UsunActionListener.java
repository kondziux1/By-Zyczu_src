package pl.zyczu.minecraft.launcher.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.UserModPacks;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.ModPack;

public class UsunActionListener implements ActionListener {
   private Mod mod = null;

   public UsunActionListener(Mod m) {
      super();
      this.mod = m;
   }

   public void actionPerformed(ActionEvent arg0) {
      LauncherFrame.getInstance().enableGlassPane();
      boolean dalej = true;
      ModPack paczka = MultiPanelStub.getInstance().getCurrentModPack();
      List<Mod> mody = paczka.realRemovalResolver(this.mod);
      if (mody.size() > 1) {
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, 1));
         JLabel m = new JLabel("Potwierdź usunięcie modów");
         m.setFont(Minecraft.getInstance().getFont(16));
         panel.add(m);
         StringBuilder mesydżBuilder = new StringBuilder();
         mesydżBuilder.append("<html><table width=600><p style=\"font-family:MinecraftZyczu\">");
         mesydżBuilder.append("Następujący mod zostanie usunięty:");
         mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu;color:red\">");
         mesydżBuilder.append(this.mod.getName());
         mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu\">");
         mesydżBuilder.append("W wyniku tego następujące mody będą miały niespełnione zależności i także zostaną usunięte:");

         for(Mod modek : mody) {
            if (!modek.equals(this.mod)) {
               mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu;color:red\">");
               mesydżBuilder.append(modek.getName());
            }
         }

         mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu\">");
         mesydżBuilder.append("Czy na pewno chcesz to zrobić?");
         mesydżBuilder.append("</p>");
         m = new JLabel(mesydżBuilder.toString());
         m.setFont(Minecraft.getInstance().getFont(12));
         panel.add(m);
         int rezultat = JOptionPane.showOptionDialog(
            LauncherFrame.getInstance(), panel, "Potwierdź usunięcie modów", 2, 2, null, new String[]{"Tak", "Nie"}, "default"
         );
         if (rezultat == 1) {
            dalej = false;
         }
      }

      if (dalej) {
         paczka.commitRemoval(mody);
         UserModPacks.getInstance().save();
         MultiPanelStub.getInstance().refreshInThisThread();
      }

      LauncherFrame.getInstance().disableGlassPane();
   }
}
