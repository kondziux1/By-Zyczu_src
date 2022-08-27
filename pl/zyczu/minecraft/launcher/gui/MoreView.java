package pl.zyczu.minecraft.launcher.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import pl.zyczu.minecraft.launcher.Minecraft;

public class MoreView extends Loadable {
   private static final long serialVersionUID = -6078485733193395069L;
   private List<MoreViewOption> moreViewOptions = null;
   private static MoreView instance = null;

   private MoreView(boolean b) {
      super(b);
      this.setOpaque(false);
      this.setLayout(null);
      this.setupMoreViewOptions();
      JLabel naglowek = new JLabel("Więcej opcji", 0);
      naglowek.setFont(Minecraft.getInstance().getFont(18));
      naglowek.setVerticalAlignment(0);
      naglowek.setBounds(0, 0, this.getWidth(), 30);
      this.add(naglowek);
      Iterator<MoreViewOption> it = this.moreViewOptions.iterator();
      int i = 0;
      int przyciskSzerokosc = this.getWidth() / 2 - 30;

      int przyciskWysokosc;
      for(przyciskWysokosc = 100; it.hasNext(); ++i) {
         MoreViewOption opcja = (MoreViewOption)it.next();
         JLabel tytul = new JLabel(opcja.getName());
         tytul.setFont(Minecraft.getInstance().getFont(14));
         JLabel opis = new JLabel("<html><p style=\"font-family:MinecraftZyczu\">" + opcja.getText() + "</p></html>");
         opis.setFont(Minecraft.getInstance().getFont(12));
         ButtonPanel panel = new ButtonPanel();
         panel.add(tytul);
         panel.add(opis);
         JButton przycisk = new JButton();
         przycisk.add(panel);
         przycisk.addActionListener(new MoreView.ButtonActionListener(opcja));
         int przyciskLewe = 10;
         if (i % 2 == 1) {
            przyciskLewe += przyciskSzerokosc + 10;
         }

         int przyciskGora = 50 + przyciskWysokosc * (i / 2);
         przycisk.setBounds(przyciskLewe, przyciskGora, przyciskSzerokosc, przyciskWysokosc);
         this.add(przycisk);
      }

      this.setPreferredSize(new Dimension(800, 60 + przyciskWysokosc * ((i + 1) / 2)));
   }

   private void setupMoreViewOptions() {
      this.moreViewOptions = new ArrayList();
      this.moreViewOptions.add(new MoreViewOption("Własne mody", "Dodaj mody z poza repozytorium, zapisane na dysku w komputerze.", null));
      this.moreViewOptions
         .add(
            new MoreViewOption(
               "Wymuszanie ustawień", "Zapisz konkretną konfigurację modów dla tej paczki. Przydatne gdy gramy na kilku różnych serwerach z modami.", null
            )
         );
      this.moreViewOptions
         .add(new MoreViewOption("Exportuj paczkę modów", "Zapisz aktualną paczkę modów do pliku, wraz z ustawieniami oraz własnymi modami.", null));
      this.moreViewOptions
         .add(
            new MoreViewOption(
               "Importuj paczkę modów", "Wgraj paczkę modów stworzoną na innym komputerze lub dostarczoną przez administracje serwera z modami.", null
            )
         );
   }

   @Override
   public void onLoad(MultiPanelStub ref) {
   }

   @Override
   public void onModPackChanged() {
   }

   public int getWidth() {
      return 840;
   }

   public static MoreView getInstance() {
      if (instance == null) {
         instance = new MoreView(true);
      }

      return instance;
   }

   private class ButtonActionListener implements ActionListener {
      private MoreViewOption option = null;

      public ButtonActionListener(MoreViewOption localVariable1) {
         super();
         this.option = localVariable1;
      }

      public void actionPerformed(ActionEvent arg0) {
         Loadable loadable = this.option.getViewToLoad();
         if (loadable != null) {
            MoreView.this.openView(loadable);
         }

      }
   }
}
