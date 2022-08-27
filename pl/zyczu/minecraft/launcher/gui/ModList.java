package pl.zyczu.minecraft.launcher.gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.Properties;
import pl.zyczu.minecraft.launcher.UserModPacks;
import pl.zyczu.minecraft.launcher.repo.Cat;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.ModPack;
import pl.zyczu.minecraft.launcher.repo.Repository;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;
import pl.zyczu.minecraft.launcher.repo.SearchProvider;
import pl.zyczu.util.WrapLayout;

public class ModList extends Loadable implements Refreshable {
   private static final long serialVersionUID = -1681385194901696099L;
   private JLabel naglowek = null;
   private List<Mod> mody;
   private boolean szukaj = false;
   private boolean modpack = false;
   private SearchProvider searchProvider = null;
   private ModPack paczka = null;
   private static ModList instance = null;

   private ModList(boolean b) {
      super(b);
      this.setOpaque(false);
      this.mody = new LinkedList();
      this.setLayout(new WrapLayout());
      this.naglowek = new JLabel("mody", 0);
      this.naglowek.setFont(Minecraft.getInstance().getFont(18));
      this.naglowek.setVerticalAlignment(0);
   }

   @Override
   public void onLoad(MultiPanelStub ref) {
   }

   @Override
   public void onModPackChanged() {
      if (this.modpack) {
         this.loadModsByModPack(this.getCurrentModPack());
      }

      this.refresh();
   }

   public void paintComponent(Graphics g) {
   }

   private void setDefaults() {
      this.szukaj = false;
      this.modpack = false;
   }

   public void loadModsByCat(Cat kategoria) {
      this.setDefaults();
      this.mody = new ArrayList();
      Iterator<Mod> it = kategoria.getMods().iterator();

      while(it.hasNext()) {
         this.mody.add((Mod)it.next());
      }

   }

   public void loadModsByModPack(ModPack pczk) {
      this.setDefaults();
      this.paczka = pczk;
      this.mody = this.paczka.getMods();
      this.modpack = true;
   }

   public void loadMods(List<Mod> lista) {
      this.setDefaults();
      this.mody = lista;
   }

   public void setSearch(boolean b) {
      this.szukaj = true;
   }

   public void setSearchProvider(SearchProvider sp) {
      this.searchProvider = sp;
   }

   public void setTitle(String theValue1) {
      this.naglowek.setText(theValue1);
   }

   @Override
   public void refresh() {
      this.removeAll();
      if (this.szukaj) {
         ModList.SearchEntry se = new ModList.SearchEntry();
         se.setQuery(this.naglowek.getText());
         this.add(se);
      } else if (this.modpack) {
         ModList.ModpackEntry mpe = new ModList.ModpackEntry();
         this.add(mpe);
      } else {
         this.add(this.naglowek);
      }

      for(Mod ble : this.mody) {
         if (!this.modpack || !ble.getId().equalsIgnoreCase("minecraft")) {
            ModList.ModEntry me = new ModList.ModEntry(0.05F);
            me.loadModInfo(ble);
            this.add(me);
         }
      }

   }

   private void refreshInEventQueue() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            ModList.this.refresh();
         }
      });
   }

   private void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               ModList.this.update(ModList.this.getGraphics());
            } catch (NullPointerException var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
            }

         }
      });
   }

   public static ModList getInstance() {
      if (instance == null) {
         instance = new ModList(true);
      }

      return instance;
   }

   private class ModEntry extends JPanel {
      private static final long serialVersionUID = 446L;
      private float alfa = 0.1F;
      private Color kolorek = Color.BLACK;

      public ModEntry(float a) {
         super(true);
         this.setOpaque(false);
         this.alfa = a;
      }

      public void loadModInfo(final Mod mod) {
         boolean zainstalowany = ModList.this.getCurrentModPack().containsMod(mod);
         if (zainstalowany) {
            this.kolorek = new Color(0, 168, 255);
         }

         JPanel lewe = new JPanel();
         lewe.setOpaque(false);
         lewe.setLayout(new BorderLayout());
         JPanel prawe = new JPanel();
         prawe.setOpaque(false);
         prawe.setLayout(new BoxLayout(prawe, 1));
         JPanel zbiorczy = new JPanel();
         zbiorczy.setOpaque(false);
         zbiorczy.setLayout(new BoxLayout(zbiorczy, 1));
         ImageView obrazek = new ImageView();
         obrazek.setURL(mod.getImage());
         JLabel nagl = new JLabel("<html><table width=446><p style=\"font-family:MinecraftZyczu\">" + mod.getName() + "</p></table></html>");
         nagl.setFont(Minecraft.getInstance().getFont(14));
         zbiorczy.add(nagl);
         final String łebsajt = mod.getWebsite();
         JLabel aut = new JLabel(
            "<html><table width=446><p style=\"font-family:MinecraftZyczu\">Autor: "
               + mod.getAuthor()
               + " &nbsp; Strona: <a href=\""
               + mod.getWebsite()
               + "\">"
               + mod.getWebsite().replace("http://", "")
               + "</a> </p></table></html>"
         );
         aut.setFont(Minecraft.getInstance().getFont(10));
         aut.addMouseListener(new MouseAdapter() {
            private boolean bylo = false;

            public void mousePressed(MouseEvent arg0) {
               this.bylo = true;
            }

            public void mouseReleased(MouseEvent arg0) {
               if (this.bylo) {
                  Minecraft.log.debug("Przekierowywanie na " + łebsajt);

                  try {
                     Desktop.getDesktop().browse(new URL(łebsajt).toURI());
                  } catch (MalformedURLException var3) {
                     var3.printStackTrace();
                  } catch (IOException var4) {
                     var4.printStackTrace();
                  } catch (URISyntaxException var5) {
                     var5.printStackTrace();
                  }
               }

               this.bylo = false;
            }
         });
         zbiorczy.add(aut);
         short ktory = 0;
         StringBuilder bld = new StringBuilder();
         boolean pierwsze = true;
         boolean dostepny = false;

         for(Repository repo : RepositoryManager.getInstance().getRepositoriesInOrder()) {
            if (repo.containsMod(mod)) {
               if (ModList.this.getCurrentModPack().getRepository().equals(repo)) {
                  dostepny = true;
               }

               if (pierwsze) {
                  bld.append(repo.getName());
                  pierwsze = false;
                  ktory = 2;
               } else {
                  if (ktory > 3) {
                     bld.append(" i inne");
                     break;
                  }

                  bld.append(", " + repo.getName());
                  ++ktory;
               }
            }
         }

         if (!dostepny) {
            this.kolorek = new Color(255, 0, 168);
         }

         JLabel wer = new JLabel(
            "<html><table width=446><p style=\"font-family:MinecraftZyczu\">Dostępny na wersje: " + bld.toString() + "</p></table></html>"
         );
         wer.setFont(Minecraft.getInstance().getFont(10));
         zbiorczy.add(wer);
         String[] opisy = mod.getText().split("\n");
         JLabel opis = new JLabel("<html><table width=446><p style=\"font-family:MinecraftZyczu\">" + opisy[0] + "</p></table></html>");
         opis.setFont(Minecraft.getInstance().getFont(12));
         zbiorczy.add(opis);
         lewe.add(zbiorczy, "East");
         lewe.add(obrazek, "West");
         if (!ModList.this.modpack) {
            JButton mtn = new JButton("Informacje o modzie");
            mtn.setFont(Minecraft.getInstance().getFont(12));
            mtn.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent arg0) {
                  LauncherFrame.getInstance().enableGlassPane();
                  ModView modView = ModView.getInstance();
                  modView.loadModInfo(mod);
                  ModList.this.openView(modView);
                  LauncherFrame.getInstance().disableGlassPane();
               }
            });
            prawe.add(mtn);
         }

         if (!mod.getId().equals("minecraft")) {
            JButton btn = null;
            if (zainstalowany) {
               btn = new JButton("Usuń z minecrafta");
               btn.addActionListener(new UsunActionListener(mod));
            } else if (dostepny) {
               btn = new JButton("Dodaj do minecrafta");
               btn.addActionListener(new DodajDoMinecraftaActionListener(mod));
            } else {
               btn = new JButton("Niedostępny na " + ModList.this.getCurrentModPack().getRepository().getShortName());
               btn.setEnabled(false);
            }

            btn.setFont(Minecraft.getInstance().getFont(12));
            prawe.add(btn);
         }

         this.add(lewe, "West");
         this.add(prawe, "East");
      }

      private AlphaComposite makeComposite() {
         int type = 3;
         return AlphaComposite.getInstance(type, this.alfa);
      }

      public void paintComponent(Graphics g) {
         Graphics2D g2 = (Graphics2D)g;
         Composite originalComposite = g2.getComposite();
         g2.setPaint(this.kolorek);
         g2.setComposite(this.makeComposite());
         g2.fillRect(0, 0, this.getWidth(), this.getHeight());
         g2.setComposite(originalComposite);
      }
   }

   private class ModpackEntry extends JPanel {
      private static final long serialVersionUID = 1699824401827787313L;
      private float alfa = 0.2F;
      private Color kolorek = Color.WHITE;
      private JTextField pole = null;

      public ModpackEntry() {
         super(true);
         this.setOpaque(false);
         Minecraft.log.debug("new ModPackEntry()");
         JTextField nazwa = new JTextField();
         nazwa.setFont(Minecraft.getInstance().getFont(12));
         nazwa.setText(" " + ModList.this.paczka.getRepository().getName() + " ");
         nazwa.setEditable(false);
         nazwa.setFocusable(false);
         nazwa.setMinimumSize(new Dimension(150, 28));
         JLabel kreska = new JLabel("-");
         kreska.setFont(Minecraft.getInstance().getFont(18));
         kreska.setVerticalAlignment(0);
         this.pole = new JTextField();
         this.pole.setFont(Minecraft.getInstance().getFont(12));
         this.pole.setText(ModList.this.paczka.getUserName());
         JButton przycisk = new JButton("Zmień nazwę");
         przycisk.setFont(Minecraft.getInstance().getFont(12));
         przycisk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               short dlugosc = (short)ModpackEntry.this.pole.getText().trim().length();
               short innadlugosc = (short)ModpackEntry.this.pole.getText().trim().replace(" ", "").replace("_", "").length();
               if (innadlugosc < 2) {
                  MessageBox.showErrorMessage("Nazwa jest za krótka!", "Nazwa paczki modów musi mieć conajmniej 3 znaki!");
               } else if (dlugosc > 50) {
                  MessageBox.showErrorMessage("Nazwa jest za długa!", "Nazwa paczki modów nie może mieć więcej niż 50 znaków!");
               } else {
                  ModList.this.getCurrentModPack().setName(ModpackEntry.this.pole.getText().trim());
                  UserModPacks.getInstance().save();
                  ModList.this.updateComboBox();
                  ModList.this.refreshInEventQueue();
               }

            }
         });
         JButton przycisk2 = new JButton("Usuń");
         przycisk2.setFont(Minecraft.getInstance().getFont(12));
         przycisk2.addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  if (UserModPacks.getInstance().getUserModPacksCount() <= 1) {
                     MessageBox.showErrorMessage(
                        "Nie można usunąć paczki modów!",
                        "Nie możesz usunąć tej paczki modów, ponieważ jest to jedyna paczka. Będziesz mógł ją usunąć po utworzeniu innej paczki modów."
                     );
                  } else if (MessageBox.showConfirm(
                     "Potwierdź usunięcie paczki modów",
                     "Czy napewno chcesz usunąć paczkę modów:<br><br>" + ModList.this.paczka.getName() + "<br><br>Tej operacji nie można cofnąć!"
                  )) {
                     UserModPacks userModPacks = UserModPacks.getInstance();
                     userModPacks.remove(ModList.this.getCurrentModPack());
                     userModPacks.save();
                     Properties p = Properties.getInstance();
                     String cm = p.get("current_modpack");
                     if (cm.contains("user")) {
                        int x = new Integer(cm.replace("user", ""));
                        System.out.println("x=" + x);
                        if (x >= userModPacks.getUserModPacksCount()) {
                           p.set("current_modpack", "user" + (userModPacks.getUserModPacksCount() - 1));
                        }
                     }
   
                     ModList.this.updateComboBox();
                     EventQueue.invokeLater(new Runnable() {
                        public void run() {
                           ModList.this.paczka = ModList.this.getCurrentModPack();
                           ModList.this.refresh();
                        }
                     });
                  }
   
               }
            }
         );
         JButton przycisk3 = new JButton("Nowa paczka");
         przycisk3.setFont(Minecraft.getInstance().getFont(12));
         przycisk3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               ModPackTutorialPanel modPackTutorialPanel = ModPackTutorialPanel.getInstance();
               modPackTutorialPanel.setFirstTime(false);
               Minecraft.switchPanel(MultiPanel.getInstance(), modPackTutorialPanel);
            }
         });
         GridBagLayout gbl = new GridBagLayout();
         this.setLayout(gbl);
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.anchor = 10;
         gbc.fill = 0;
         gbc.gridheight = 1;
         gbc.gridwidth = 6;
         gbc.gridx = -1;
         gbc.gridy = 0;
         gbc.insets = new Insets(5, 5, 5, 0);
         gbc.ipadx = 0;
         gbc.ipady = 0;
         gbc.weightx = 0.0;
         gbc.weighty = 0.0;
         this.add(nazwa);
         gbl.setConstraints(nazwa, gbc);
         gbc.insets = new Insets(0, 0, 0, 0);
         this.add(kreska);
         gbl.setConstraints(kreska, gbc);
         JComponent component = this.pole;
         gbc.fill = 2;
         gbc.insets = new Insets(5, 0, 5, 5);
         gbc.weightx = 1.0;
         this.add(component);
         gbl.setConstraints(component, gbc);
         gbc.fill = 0;
         gbc.insets = new Insets(5, 5, 5, 5);
         gbc.weightx = 0.0;
         this.add(przycisk);
         gbl.setConstraints(przycisk, gbc);
         this.add(przycisk2);
         gbl.setConstraints(przycisk2, gbc);
         this.add(przycisk3);
         gbl.setConstraints(przycisk3, gbc);
         this.setPreferredSize(new Dimension(800, 40));
      }
   }

   private class SearchEntry extends JPanel {
      private static final long serialVersionUID = 1699824401827787313L;
      private float alfa = 0.2F;
      private Color kolorek = Color.WHITE;
      private JTextField pole = null;

      public SearchEntry() {
         super(true);
         this.setOpaque(false);
         ModList.SearchEntry.SearchActionListener searchActionListener = new ModList.SearchEntry.SearchActionListener(null);
         JLabel nazwa = new JLabel("Szukaj", 0);
         nazwa.setFont(Minecraft.getInstance().getFont(18));
         nazwa.setVerticalAlignment(0);
         this.pole = new JTextField();
         this.pole.setFont(Minecraft.getInstance().getFont(12));
         this.pole.addActionListener(searchActionListener);
         JButton przycisk = new JButton("Szukaj");
         przycisk.setFont(Minecraft.getInstance().getFont(12));
         przycisk.addActionListener(searchActionListener);
         GridBagLayout gbl = new GridBagLayout();
         this.setLayout(gbl);
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.anchor = 10;
         gbc.fill = 0;
         gbc.gridheight = 1;
         gbc.gridwidth = 3;
         gbc.gridx = -1;
         gbc.gridy = 0;
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.ipadx = 0;
         gbc.ipady = 0;
         gbc.weightx = 0.0;
         gbc.weighty = 0.0;
         this.add(nazwa);
         gbl.setConstraints(nazwa, gbc);
         JComponent component = this.pole;
         gbc.fill = 2;
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.weightx = 1.0;
         this.add(component);
         gbl.setConstraints(component, gbc);
         gbc.fill = 0;
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.weightx = 0.0;
         this.add(przycisk);
         gbl.setConstraints(przycisk, gbc);
         this.setPreferredSize(new Dimension(800, 40));
         EventQueue.invokeLater(new Runnable() {
            public void run() {
               try {
                  SearchEntry.this.pole.grabFocus();
                  SearchEntry.this.pole.requestFocus();
               } catch (Exception var2) {
                  Minecraft.log.warning("NPE przy dawaniu focusa na pole wyszukiwania!");
               }

            }
         });
      }

      public void setQuery(String nq) {
         this.pole.setText(nq);
      }

      private class SearchActionListener implements ActionListener {
         private SearchActionListener() {
            super();
         }

         public void actionPerformed(ActionEvent arg0) {
            LauncherFrame.getInstance().enableGlassPane();
            List<Mod> lista = ModList.this.searchProvider.searchFor(SearchEntry.this.pole.getText());
            if (lista.size() <= 0) {
               MessageBox.showErrorMessage("Nic nie znaleziono", "Nie znaleziono żadnych modów dla zapytania \"" + SearchEntry.this.pole.getText() + "\"");
               EventQueue.invokeLater(new Runnable() {
                  public void run() {
                     try {
                        SearchEntry.this.pole.grabFocus();
                        SearchEntry.this.pole.requestFocus();
                     } catch (Exception var2) {
                        Minecraft.log.warning("NPE przy dawaniu focusa na pole wyszukiwania!");
                     }

                  }
               });
            } else {
               ModList.this.loadMods(lista);
               ModList.this.setSearch(true);
               ModList.this.setTitle(SearchEntry.this.pole.getText());
               ModList.this.refresh();
               ModList.this.update();
            }

            LauncherFrame.getInstance().disableGlassPane();
         }
      }
   }
}
