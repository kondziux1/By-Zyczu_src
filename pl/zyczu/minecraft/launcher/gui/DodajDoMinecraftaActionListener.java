package pl.zyczu.minecraft.launcher.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.UserModPacks;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.ModConflictException;
import pl.zyczu.minecraft.launcher.repo.ModPack;

public class DodajDoMinecraftaActionListener implements ActionListener {
   private Mod mod = null;

   public DodajDoMinecraftaActionListener(Mod theValue1) {
      super();
      this.mod = theValue1;
   }

   public void actionPerformed(ActionEvent arg0) {
      LauncherFrame.getInstance().enableGlassPane();
      Minecraft.log.info("Dodawanie nowego moda " + this.mod.getName());
      ModPack userPack = MultiPanelStub.getInstance().getCurrentModPack();
      if (this.mod.isStandalone() && userPack.getModCount() > 1) {
         if (MessageBox.showYesNoMessage(
            "Wykryto konflikt pomiędzy modami",
            "Mod <span style=\"color:red\">"
               + this.mod.getName()
               + "</span> nie jest kompatybilny z żadnym innym modem! Aby dodać ten mod, wszystkie mody z obecnej paczki modów zostaną usunięte! Czy na pewno chcesz to zrobić?"
         )) {
            userPack.removeAllMods();
            userPack.addMod(this.mod);
            MultiPanelStub.getInstance().refreshInThisThread();
            UserModPacks.getInstance().save();
         }

         LauncherFrame.getInstance().disableGlassPane();
      } else {
         for(Mod md : userPack.getMods()) {
            if (md.replaces(this.mod)) {
               JLabel l = new JLabel(
                  "<html><table width=600><p style=\"font-family:MinecraftZyczu\">Mod <span style=\"color:blue\">"
                     + this.mod.getName()
                     + "</span> nie jest potrzebny, ponieważ zastępuje go <span style=\"color:blue\">"
                     + md.getName()
                     + "</span>.</p><p style=\"font-family:MinecraftZyczu\">Wszystkie mody, które wymagają <span style=\"color:blue\">"
                     + this.mod.getName()
                     + "</span> będą działać prawidłowo.</p></table></html>"
               );
               l.setFont(Minecraft.getInstance().getFont(12));
               JOptionPane.showMessageDialog(LauncherFrame.getInstance(), l, "Informacja", 1);
               LauncherFrame.getInstance().disableGlassPane();
               return;
            }

            if (md.isStandalone()) {
               MessageBox.showErrorMessage(
                  "Nie można dodać moda!",
                  "Nie możesz dodawać modów do tej paczki, ponieważ znajduje się w niej mod <span style=\"color:red\">"
                     + md.getName()
                     + "</span>, który nie jest kompatybilny z żadnym innym modem!"
               );
               LauncherFrame.getInstance().disableGlassPane();
               return;
            }
         }

         ModPack newPack = new ModPack();
         newPack.attachRepository(userPack.getRepository());
         Iterator<Mod> it = userPack.getMods().iterator();

         while(it.hasNext()) {
            newPack.addMod((Mod)it.next());
         }

         boolean dalej = true;
         boolean anuluj = false;

         while(dalej) {
            try {
               newPack.dependencyResolver(this.mod);
               dalej = false;
            } catch (ModConflictException var18) {
               ModConflictException e = var18;
               JPanel panel = new JPanel();
               panel.setLayout(new BoxLayout(panel, 1));
               JLabel n = new JLabel("Wykryto konflikt pomiędzy modami");
               n.setFont(Minecraft.getInstance().getFont(12));
               panel.add(n);
               n = new JLabel(var18.getFirst().getName());
               n.setFont(Minecraft.getInstance().getFont(14));
               panel.add(n);
               n = new JLabel("oraz");
               n.setFont(Minecraft.getInstance().getFont(12));
               panel.add(n);
               n = new JLabel(var18.getSecond().getName());
               n.setFont(Minecraft.getInstance().getFont(14));
               panel.add(n);
               n = new JLabel(
                  "<html><table width=600><p style=\"font-family:MinecraftZyczu\">Aby dodać mod <span style=\"color:blue\">"
                     + this.mod.getName()
                     + "</span> do wybranej paczki modów, konieczne jest usunięcie moda <span style=\"color:red\">"
                     + var18.getSecond().getName()
                     + "</span>. Czy na pewno chcesz to zrobić?</p></table></html>"
               );
               n.setFont(Minecraft.getInstance().getFont(12));
               panel.add(n);
               int rezultat = JOptionPane.showOptionDialog(
                  LauncherFrame.getInstance(), panel, "Wykryto konflikt pomiędzy modami", 2, 2, null, new String[]{"Tak", "Nie"}, "default"
               );
               if (rezultat == 1) {
                  anuluj = true;
                  dalej = false;
               } else {
                  List<Mod> mody = newPack.realRemovalResolver(var18.getSecond());
                  if (mody.size() > 1) {
                     JPanel panel2 = new JPanel();
                     panel2.setLayout(new BoxLayout(panel2, 1));
                     JLabel m = new JLabel("Potwierdź usunięcie modów");
                     m.setFont(Minecraft.getInstance().getFont(16));
                     panel2.add(m);
                     StringBuilder mesydżBuilder = new StringBuilder();
                     mesydżBuilder.append("<html><table width=600><p style=\"font-family:MinecraftZyczu\">");
                     mesydżBuilder.append("Następujący mod zostanie usunięty:");
                     mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu;color:red\">");
                     mesydżBuilder.append(var18.getSecond().getName());
                     mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu\">");
                     mesydżBuilder.append("W wyniku tego następujące mody będą miały niespełnione zależności i także zostaną usunięte:");

                     for(Mod modek : mody) {
                        if (!modek.equals(e.getSecond())) {
                           mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu;color:red\">");
                           mesydżBuilder.append(modek.getName());
                        }
                     }

                     mesydżBuilder.append("</p><p style=\"font-family:MinecraftZyczu\">");
                     mesydżBuilder.append("Czy na pewno chcesz to zrobić?");
                     mesydżBuilder.append("</p>");
                     m = new JLabel(mesydżBuilder.toString());
                     m.setFont(Minecraft.getInstance().getFont(12));
                     panel2.add(m);
                     rezultat = JOptionPane.showOptionDialog(
                        LauncherFrame.getInstance(), panel2, "Potwierdź usunięcie modów", 2, 2, null, new String[]{"Tak", "Nie"}, "default"
                     );
                     if (rezultat == 1) {
                        anuluj = true;
                        dalej = false;
                     }
                  }

                  if (dalej) {
                     newPack.commitRemoval(mody);
                  }
               }
            }
         }

         if (!anuluj) {
            newPack.removeReplacedMods(this.mod);
            userPack.loadMods(newPack);
            MultiPanelStub.getInstance().refreshInThisThread();
            UserModPacks.getInstance().save();
         } else {
            JLabel l = new JLabel(
               "<html><table width=600><p style=\"font-family:MinecraftZyczu\">Mod "
                  + this.mod.getName()
                  + " NIE ZOSTAŁ dodany do minecrafta ze względu na problemy z zależnościami.</p><p style=\"font-family:MinecraftZyczu\">Pamiętaj, że nie wszystkie mody są ze sobą kompatybilne, i koniecze jest usunięcie niektórych modów, by móc zainstalować inne.</p></table></html>"
            );
            l.setFont(Minecraft.getInstance().getFont(12));
            JOptionPane.showMessageDialog(LauncherFrame.getInstance(), l, "Bład podczas dodawania moda!", 0);
         }

         LauncherFrame.getInstance().disableGlassPane();
      }
   }
}
