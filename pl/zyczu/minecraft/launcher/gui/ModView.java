package pl.zyczu.minecraft.launcher.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.Repository;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;
import pl.zyczu.util.WrapLayout;

public class ModView extends Loadable implements Refreshable {
   private static final long serialVersionUID = 800L;
   private Mod modek = null;
   private static ModView instance = null;

   private ModView(boolean b) {
      super(b);
      this.setOpaque(false);
      this.setLayout(new WrapLayout());
   }

   public static ModView getInstance() {
      if (instance == null) {
         instance = new ModView(true);
      }

      return instance;
   }

   public void loadModInfo(Mod mod) {
      this.removeAll();
      this.modek = mod;
      boolean zainstalowany = this.getCurrentModPack().containsMod(mod);
      StringBuilder bld = new StringBuilder();
      boolean pierwsze = true;
      boolean dostepny = false;

      for(Repository repo : RepositoryManager.getInstance().getRepositoriesInOrder()) {
         if (repo.containsMod(mod)) {
            if (this.getCurrentModPack().getRepository().equals(repo)) {
               dostepny = true;
            }

            if (pierwsze) {
               bld.append(repo.getName());
               pierwsze = false;
            } else {
               bld.append("<br>" + repo.getName());
            }
         }
      }

      String repozytoria = bld.toString();
      JPanel zbiorczy = new JPanel() {
         private final Color kolorek = Color.WHITE;
         private static final long serialVersionUID = -5951374986891143962L;

         public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Composite originalComposite = g2.getComposite();
            g2.setPaint(this.kolorek);
            g2.setComposite(this.makeComposite());
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setComposite(originalComposite);
         }

         private AlphaComposite makeComposite() {
            int type = 3;
            return AlphaComposite.getInstance(type, 0.2F);
         }
      };
      zbiorczy.setOpaque(false);
      JLabel nagl = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + mod.getName() + "</p></table></html>");
      nagl.setFont(Minecraft.getInstance().getFont(18));
      zbiorczy.add(nagl, "West");
      if (!mod.getId().equals("minecraft")) {
         JButton btn = null;
         if (zainstalowany) {
            btn = new JButton("Usuń z minecrafta");
            btn.addActionListener(new UsunActionListener(mod));
         } else if (dostepny) {
            btn = new JButton("Dodaj do minecrafta");
            btn.addActionListener(new DodajDoMinecraftaActionListener(mod));
         } else {
            btn = new JButton("Niedostępny na " + this.getCurrentModPack().getRepository().getShortName());
            btn.setEnabled(false);
         }

         btn.setFont(Minecraft.getInstance().getFont(12));
         zbiorczy.add(btn, "East");
      }

      this.add(zbiorczy);
      JPanel lewe = new JPanel();
      lewe.setLayout(new BoxLayout(lewe, 1));
      lewe.setOpaque(false);
      final String łebsajt = mod.getWebsite();
      JLabel aut = new JLabel("<html><table width=800><p style=\"font-family:MinecraftZyczu\">Autor: " + mod.getAuthor() + "</p></table></html>");
      aut.setFont(Minecraft.getInstance().getFont(11));
      lewe.add(aut);
      JLabel www = new JLabel(
         "<html><table width=800><p style=\"font-family:MinecraftZyczu\">Strona: <a href=\""
            + mod.getWebsite()
            + "\">"
            + mod.getWebsite()
            + "</a> </p></table></html>"
      );
      www.setFont(Minecraft.getInstance().getFont(11));
      www.addMouseListener(new MouseAdapter() {
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
      lewe.add(www);
      JLabel rep = new JLabel("<html><table width=800><p style=\"font-family:MinecraftZyczu\">Dostępny na: " + repozytoria + "</p></table></html>");
      rep.setFont(Minecraft.getInstance().getFont(11));
      lewe.add(rep);
      JLabel opis = new JLabel("<html><table width=800><p style=\"font-family:MinecraftZyczu\">" + mod.getText().replace("\n", "<br>") + "</p></table></html>");
      opis.setFont(Minecraft.getInstance().getFont(12));
      lewe.add(opis);
      this.add(lewe, "West");
      if (mod.getDependencies().size() > 0) {
         JLabel n = new JLabel("<html><table width=800><p style=\"font-family:MinecraftZyczu\">Wymaga:</p></table></html>");
         n.setFont(Minecraft.getInstance().getFont(14));
         this.add(n);
         Iterator<String> it2 = mod.getDependencies().iterator();

         while(it2.hasNext()) {
            final Mod inny = RepositoryManager.getInstance().getModById((String)it2.next());
            zbiorczy = new JPanel() {
               private final Color kolorek = Color.WHITE;
               private static final long serialVersionUID = -5951374986891143962L;

               public void paintComponent(Graphics g) {
                  Graphics2D g2 = (Graphics2D)g;
                  Composite originalComposite = g2.getComposite();
                  g2.setPaint(this.kolorek);
                  g2.setComposite(this.makeComposite());
                  g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                  g2.setComposite(originalComposite);
               }

               private AlphaComposite makeComposite() {
                  int type = 3;
                  return AlphaComposite.getInstance(type, 0.2F);
               }
            };
            zbiorczy.setOpaque(false);
            JLabel ngl2 = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + inny.getName() + "</p></table></html>");
            ngl2.setFont(Minecraft.getInstance().getFont(16));
            zbiorczy.add(ngl2, "West");
            JButton btn = new JButton("Informacje o modzie");
            btn.setFont(Minecraft.getInstance().getFont(12));
            btn.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent arg0) {
                  LauncherFrame.getInstance().enableGlassPane();
                  ModView.this.loadModInfo(inny);
                  ModView.this.update();
                  LauncherFrame.getInstance().disableGlassPane();
               }
            });
            zbiorczy.add(btn, "East");
            this.add(zbiorczy);
         }
      }

      if (mod.getReplacements().size() > 0) {
         JLabel n = new JLabel("<html><table width=800><p style=\"font-family:MinecraftZyczu\">Zastępuje:</p></table></html>");
         n.setFont(Minecraft.getInstance().getFont(14));
         this.add(n);
         Iterator<String> it2 = mod.getReplacements().iterator();

         while(it2.hasNext()) {
            final Mod inny = RepositoryManager.getInstance().getModById((String)it2.next());
            zbiorczy = new JPanel() {
               private final Color kolorek = Color.WHITE;
               private static final long serialVersionUID = -5951374986891143962L;

               public void paintComponent(Graphics g) {
                  Graphics2D g2 = (Graphics2D)g;
                  Composite originalComposite = g2.getComposite();
                  g2.setPaint(this.kolorek);
                  g2.setComposite(this.makeComposite());
                  g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                  g2.setComposite(originalComposite);
               }

               private AlphaComposite makeComposite() {
                  int type = 3;
                  return AlphaComposite.getInstance(type, 0.2F);
               }
            };
            zbiorczy.setOpaque(false);
            JLabel ngl2 = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + inny.getName() + "</p></table></html>");
            ngl2.setFont(Minecraft.getInstance().getFont(16));
            zbiorczy.add(ngl2, "West");
            JButton btn = new JButton("Informacje o modzie");
            btn.setFont(Minecraft.getInstance().getFont(12));
            btn.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent arg0) {
                  ModView.this.loadModInfo(inny);
               }
            });
            zbiorczy.add(btn, "East");
            this.add(zbiorczy);
         }
      }

      if (mod.getConflicts().size() > 0) {
         JLabel n = new JLabel("<html><table width=800><p style=\"font-family:MinecraftZyczu\">Nie działa z:</p></table></html>");
         n.setFont(Minecraft.getInstance().getFont(14));
         this.add(n);
         Iterator<String> it2 = mod.getConflicts().iterator();

         while(it2.hasNext()) {
            final Mod inny = RepositoryManager.getInstance().getModById((String)it2.next());
            zbiorczy = new JPanel() {
               private final Color kolorek = Color.WHITE;
               private static final long serialVersionUID = -5951374986891143962L;

               public void paintComponent(Graphics g) {
                  Graphics2D g2 = (Graphics2D)g;
                  Composite originalComposite = g2.getComposite();
                  g2.setPaint(this.kolorek);
                  g2.setComposite(this.makeComposite());
                  g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                  g2.setComposite(originalComposite);
               }

               private AlphaComposite makeComposite() {
                  int type = 3;
                  return AlphaComposite.getInstance(type, 0.2F);
               }
            };
            zbiorczy.setOpaque(false);
            JLabel ngl2 = new JLabel("<html><table width=600><p style=\"font-family:MinecraftZyczu\">" + inny.getName() + "</p></table></html>");
            ngl2.setFont(Minecraft.getInstance().getFont(16));
            zbiorczy.add(ngl2, "West");
            JButton btn = new JButton("Informacje o modzie");
            btn.setFont(Minecraft.getInstance().getFont(12));
            btn.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent arg0) {
                  ModView.this.loadModInfo(inny);
               }
            });
            zbiorczy.add(btn, "East");
            this.add(zbiorczy);
         }
      }

   }

   @Override
   public void refresh() {
      this.loadModInfo(this.modek);
   }

   private void refreshInEventQueue() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            ModView.this.refresh();
         }
      });
   }

   private void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               ModView.this.update(ModView.this.getGraphics());
            } catch (NullPointerException var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
            }

         }
      });
   }

   @Override
   public void onLoad(MultiPanelStub ref) {
   }

   @Override
   public void onModPackChanged() {
      this.refresh();
   }
}
