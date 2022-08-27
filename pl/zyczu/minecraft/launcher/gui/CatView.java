package pl.zyczu.minecraft.launcher.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.repo.Cat;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;

public class CatView extends Loadable {
   private static final long serialVersionUID = -5445253622656217216L;
   private static CatView instance = null;

   private CatView(boolean theValue1) {
      super(theValue1);
      this.setOpaque(false);
      this.setLayout(null);
      JLabel naglowek = new JLabel("Kategorie", 0);
      naglowek.setFont(Minecraft.getInstance().getFont(18));
      naglowek.setVerticalAlignment(0);
      naglowek.setBounds(0, 0, this.getWidth(), 30);
      this.add(naglowek);
      RepositoryManager repoManager = RepositoryManager.getInstance();
      Iterator<Cat> it = repoManager.getOrderedCatList().iterator();
      int i = 0;
      int przyciskSzerokosc = this.getWidth() / 2 - 30;

      int przyciskWysokosc;
      for(przyciskWysokosc = 100; it.hasNext(); ++i) {
         Cat kategoria = (Cat)it.next();
         JLabel tytul = new JLabel(kategoria.getName() + " (" + kategoria.getModCount() + ")");
         tytul.setFont(Minecraft.getInstance().getFont(14));
         JLabel opis = new JLabel("<html><p style=\"font-family:MinecraftZyczu\">" + kategoria.getText() + "</p></html>");
         opis.setFont(Minecraft.getInstance().getFont(12));
         ButtonPanel panel = new ButtonPanel();
         panel.add(tytul);
         panel.add(opis);
         JButton przycisk = new JButton();
         przycisk.add(panel);
         przycisk.addActionListener(new CatView.ButtonActionListener(kategoria));
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

   public void paintComponent(Graphics g) {
   }

   public int getWidth() {
      return 840;
   }

   @Override
   public void onLoad(MultiPanelStub ref) {
      ref.setSelectedTab(2);
   }

   @Override
   public void onModPackChanged() {
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               CatView.this.update(CatView.this.getGraphics());
            } catch (Exception var2) {
               Minecraft.log.warning("NPE przy rysowaniu interfejsu!");
            }

         }
      });
   }

   public static CatView getInstance() {
      if (instance == null) {
         instance = new CatView(true);
      }

      return instance;
   }

   private class ButtonActionListener implements ActionListener {
      private Cat kategoria = null;

      public ButtonActionListener(Cat k) {
         super();
         this.kategoria = k;
      }

      public void actionPerformed(ActionEvent arg0) {
         ModList instance = ModList.getInstance();
         instance.loadModsByCat(this.kategoria);
         instance.setTitle("Mody w kategorii: " + this.kategoria.getName());
         instance.refresh();
         CatView.this.openView(instance);
      }
   }
}
