package pl.zyczu.minecraft.launcher.gui;

import java.awt.EventQueue;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.Properties;
import pl.zyczu.minecraft.launcher.UserModPacks;
import pl.zyczu.minecraft.launcher.repo.Repository;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;

public class WelcomeTutorialScreen implements TutorialScreen {
   private ModPackTutorialPanel panel = null;
   private JPanel innerPanel = null;
   private JTextField nazwaField;
   private JComboBox repoCombo;

   public WelcomeTutorialScreen() {
      super();
   }

   @Override
   public void createControls() {
      this.innerPanel = this.panel.getInnerPanel();
      int szerokosc = this.innerPanel.getWidth();
      JLabel nagl = new JLabel("Tworzenie własnej paczki modów", 0);
      if (!this.panel.firstTime) {
         nagl.setText("Tworzenie nowej paczki modów");
      }

      nagl.setFont(Minecraft.getInstance().getFont(18));
      nagl.setVerticalAlignment(0);
      nagl.setBounds(0, 0, szerokosc, 30);
      this.add(nagl);
      JLabel pierwszyOpis = new JLabel(
         "<html><p style=\"font-family:MinecraftZyczu\">Minecraft by Zyczu umożliwia łatwą instalację modów do Minecrafta.<br>Możesz samemu dobrać mody, które mają być zainstalowane.<br>Mody są grupowane w paczkach modów. Możesz mieć kilka własnych paczek modów. Narazie jednak nie masz żadnej, dlatego musisz ją utworzyć.<br><br>Na początek, musisz wybrać na jaką wersję Minecrafta ma być twoja paczka. Pamiętaj, że nie wszystkie mody są dostępne na każdą wersję gry.</p></html>"
      );
      if (!this.panel.firstTime) {
         pierwszyOpis.setText(
            "<html><p style=\"font-family:MinecraftZyczu\">Minecraft by Zyczu umożliwia łatwą instalację modów do Minecrafta.<br>Możesz samemu dobrać mody, które mają być zainstalowane.<br>Mody są grupowane w paczkach modów. Możesz mieć kilka własnych paczek modów. W każdej paczce mogą znajdować się różne mody.<br><br>Na początek, musisz wybrać na jaką wersję Minecrafta ma być twoja paczka. Pamiętaj, że nie wszystkie mody są dostępne na każdą wersję gry.</p></html>"
         );
      }

      pierwszyOpis.setBounds(0, 30, szerokosc, 120);
      pierwszyOpis.setFont(Minecraft.getInstance().getFont(12));
      this.add(pierwszyOpis);
      JLabel wersja = new JLabel("Wybierz wersję gry:", 4);
      wersja.setBounds(0, 160, 200, 30);
      wersja.setFont(Minecraft.getInstance().getFont(14));
      wersja.setVerticalAlignment(0);
      this.add(wersja);
      this.repoCombo = new JComboBox();
      Iterator<Repository> it = RepositoryManager.getInstance().getRepositoriesInOrder().iterator();

      while(it.hasNext()) {
         this.repoCombo.addItem(((Repository)it.next()).getName());
      }

      this.repoCombo.setFont(Minecraft.getInstance().getFont(12));
      this.repoCombo.setBounds(210, 161, 350, 28);
      this.add(this.repoCombo);
      JLabel drugiOpis = new JLabel(
         "<html><p style=\"font-family:MinecraftZyczu\">Musisz jeszcze wybrać nazwę dla swojej paczki modów. Nazwa ta będzie się wyświetlać na liście modów do wyboru, przy uruchamianiu gry.</p></html>"
      );
      drugiOpis.setBounds(0, 200, szerokosc, 34);
      drugiOpis.setFont(Minecraft.getInstance().getFont(12));
      this.add(drugiOpis);
      JLabel nazwa = new JLabel("Nazwa paczki:", 4);
      nazwa.setBounds(0, 250, 200, 30);
      nazwa.setFont(Minecraft.getInstance().getFont(14));
      nazwa.setVerticalAlignment(0);
      this.add(nazwa);
      this.nazwaField = new JTextField();
      this.nazwaField.setFont(Minecraft.getInstance().getFont(12));
      this.nazwaField.setBounds(210, 251, 350, 28);
      if (this.panel.firstTime) {
         this.nazwaField.setText("Moja pierwsza paczka modów");
      } else {
         this.nazwaField.setText("Moja paczka modów");
      }

      this.add(this.nazwaField);
      JLabel trzeciOpis = new JLabel(
         "<html><p style=\"font-family:MinecraftZyczu\">Teraz możesz już zacząć wybierać mody. W tym celu kliknij DALEJ!</p></html>"
      );
      trzeciOpis.setBounds(0, 290, szerokosc, 34);
      trzeciOpis.setFont(Minecraft.getInstance().getFont(12));
      this.add(trzeciOpis);
   }

   @Override
   public void onDalejClicked() {
      short dlugosc = (short)this.nazwaField.getText().trim().length();
      short innadlugosc = (short)this.nazwaField.getText().trim().replace(" ", "").replace("_", "").length();
      if (innadlugosc < 2) {
         MessageBox.showErrorMessage("Nazwa jest za krótka!", "Nazwa paczki modów musi mieć conajmniej 3 znaki!");
      } else if (dlugosc > 50) {
         MessageBox.showErrorMessage("Nazwa jest za długa!", "Nazwa paczki modów nie może mieć więcej niż 50 znaków!");
      } else {
         RepositoryManager repositoryManager = RepositoryManager.getInstance();
         UserModPacks userModPacks = UserModPacks.getInstance();
         userModPacks.addNewPack(this.nazwaField.getText().trim(), repositoryManager.getRepositoryByOrdialNumber(this.repoCombo.getSelectedIndex()));
         userModPacks.save();
         MultiPanel mp = MultiPanel.getInstance();
         if (!this.panel.firstTime) {
            Properties.getInstance().set("current_modpack", "user" + (UserModPacks.getInstance().getUserModPacksCount() - 1));
            EventQueue.invokeLater(new Runnable() {
               public void run() {
                  MultiPanel.getInstance().updateComboBox();
                  MultiPanelStub.getInstance().openView(Polecane.getInstance());
               }
            });
         }

         Minecraft.switchPanel(this.panel, mp);
      }

   }

   @Override
   public void removeControls() {
   }

   private void add(JComponent jComponent) {
      this.innerPanel.add(jComponent);
   }

   @Override
   public void setPanel(ModPackTutorialPanel p) {
      this.panel = p;
   }
}
