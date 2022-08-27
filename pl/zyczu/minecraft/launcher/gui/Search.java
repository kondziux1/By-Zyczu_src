package pl.zyczu.minecraft.launcher.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.SearchParameter;
import pl.zyczu.minecraft.launcher.repo.SearchProvider;

public class Search extends Loadable {
   private static final long serialVersionUID = -5445253622656217216L;
   private final short margines = 30;
   private final short szerokoscPrzyciskuSzukaj = 150;
   private JTextField pole = null;
   private JRadioButton titleOnly = null;
   private JRadioButton fullSearch = null;
   private static Search instance = null;

   private Search(boolean theValue1) {
      super(theValue1);
      this.setOpaque(false);
      this.setLayout(null);
      Search.SearchActionListener searchActionListener = new Search.SearchActionListener(null);
      JLabel naglowek = new JLabel("Wyszukiwarka modów", 0);
      naglowek.setFont(Minecraft.getInstance().getFont(18));
      naglowek.setVerticalAlignment(0);
      naglowek.setBounds(0, 0, this.getWidth(), 30);
      this.add(naglowek);
      this.pole = new JTextField();
      this.pole.setFont(Minecraft.getInstance().getFont(12));
      this.pole.setBounds(30, 51, this.getWidth() - 60, 28);
      this.pole.addActionListener(searchActionListener);
      this.add(this.pole);
      this.titleOnly = new JRadioButton("Szukaj tylko w nazwach modów");
      this.titleOnly.setFont(Minecraft.getInstance().getFont(12));
      this.titleOnly.setMnemonic(78);
      this.titleOnly.setActionCommand("A");
      this.titleOnly.setBounds(30, 81, (this.getWidth() - 90) / 2, 28);
      this.add(this.titleOnly);
      this.fullSearch = new JRadioButton("Szukaj w nazwach i opisach");
      this.fullSearch.setFont(Minecraft.getInstance().getFont(12));
      this.fullSearch.setMnemonic(79);
      this.fullSearch.setActionCommand("B");
      this.fullSearch.setSelected(true);
      this.fullSearch.setBounds(60 + (this.getWidth() - 90) / 2, 81, (this.getWidth() - 90) / 2, 28);
      this.add(this.fullSearch);
      ButtonGroup grupa = new ButtonGroup();
      grupa.add(this.titleOnly);
      grupa.add(this.fullSearch);
      JButton szukaj = new JButton("Szukaj modów");
      szukaj.setFont(Minecraft.getInstance().getFont(12));
      szukaj.setBounds((this.getWidth() - 150 - 60) / 2, 111, 150, 36);
      szukaj.addActionListener(searchActionListener);
      this.add(szukaj);
      this.setPreferredSize(new Dimension(800, 300));
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Search.this.pole.grabFocus();
               Search.this.pole.requestFocus();
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy dawaniu focusa na pole wyszukiwania!");
            }

         }
      });
   }

   public void paintComponent(Graphics g) {
   }

   public int getWidth() {
      return 830;
   }

   @Override
   public void onLoad(MultiPanelStub ref) {
      ref.setSelectedTab(3);
      this.pole.requestFocus();
   }

   @Override
   public void onModPackChanged() {
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Search.this.update(Search.this.getGraphics());
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
            }

         }
      });
   }

   public static Search getInstance() {
      if (instance == null) {
         instance = new Search(true);
      }

      return instance;
   }

   private class SearchActionListener implements ActionListener {
      private SearchActionListener() {
         super();
      }

      public void actionPerformed(ActionEvent arg0) {
         LauncherFrame.getInstance().enableGlassPane();
         SearchProvider sp = SearchProvider.getInstance();
         sp.setSearchParameter(SearchParameter.NO_REMOVE_SPECIAL_CHARS);
         if (Search.this.fullSearch.isSelected()) {
            sp.setSearchParameter(SearchParameter.FULL_SEARCH);
         } else {
            sp.setSearchParameter(SearchParameter.TITLE_ONLY);
         }

         sp.setSearchParameter(SearchParameter.MULTIWORD_SEPARATE);
         List<Mod> lista = sp.searchFor(Search.this.pole.getText());
         if (lista.size() <= 0) {
            MessageBox.showErrorMessage("Nic nie znaleziono", "Nie znaleziono żadnych modów dla zapytania \"" + Search.this.pole.getText() + "\"");
            EventQueue.invokeLater(new Runnable() {
               public void run() {
                  try {
                     Search.this.pole.grabFocus();
                     Search.this.pole.requestFocus();
                  } catch (Exception var2) {
                     Minecraft.log.warning("NPE przy dawaniu focusa na pole wyszukiwania!");
                  }

               }
            });
         } else {
            ModList wyniki = ModList.getInstance();
            wyniki.loadMods(lista);
            wyniki.setSearch(true);
            wyniki.setSearchProvider(sp);
            wyniki.setTitle(Search.this.pole.getText());
            wyniki.refresh();
            Search.this.openView(wyniki);
         }

         LauncherFrame.getInstance().disableGlassPane();
      }
   }
}
