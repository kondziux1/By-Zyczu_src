package pl.zyczu.minecraft.launcher;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
   private static final long serialVersionUID = -4238593885696697352L;
   private final int paddingTop = 40;
   private final int szerokosc = 820;
   private final int wysokosc = 405;
   private final int margines = 10;
   private final int lewo = 27;
   private final int gora = 77;
   private final int maxszer = 800;
   private int lastSelectedIndex = -1;
   private JComboBox ramCombo;
   private JCheckBox wyjdzCheck;
   private JCheckBox nickCheck;
   private static SettingsPanel instance = null;

   public SettingsPanel() {
      super();
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(null);
      JLabel nagl = new JLabel("Ustawienia", 0);
      nagl.setFont(Minecraft.getInstance().getFont(18));
      nagl.setVerticalAlignment(0);
      nagl.setBounds(27, 77, 800, 30);
      this.add(nagl);
      JLabel outOfMemoryFixHeader = new JLabel("Fix na OutOfMemory");
      outOfMemoryFixHeader.setFont(Minecraft.getInstance().getFont(14));
      outOfMemoryFixHeader.setBounds(27, 107, 800, 25);
      this.add(outOfMemoryFixHeader);
      JLabel outOfMemoryFixOpis = new JLabel(
         "<html><p style=\"font-family:MinecraftZyczu\">Bład OutOfMemory występuje, ponieważ domyślnie java nie wykorzystuje całej dostępnej pamięci RAM, a jedynie jej część. Fix na OutOfMemory wymusza zaalokowanie konkretnej ilości pamięci RAM na potrzeby Minecrafta. Wybranie zbyt dużej wartości spowoduje, że gra się nie uruchomi. W takim wypadku domyślne ustawienia zostaną przywrócone.</p></html>"
      );
      outOfMemoryFixOpis.setBounds(57, 127, 770, 70);
      outOfMemoryFixOpis.setFont(Minecraft.getInstance().getFont(12));
      this.add(outOfMemoryFixOpis);
      JLabel ram1 = new JLabel("Ilość pamięci RAM:");
      ram1.setFont(Minecraft.getInstance().getFont(12));
      ram1.setBounds(57, 202, 150, 20);
      this.add(ram1);
      this.ramCombo = new JComboBox(OutOfMemoryFix.nazwy);
      this.ramCombo.setFont(Minecraft.getInstance().getFont(12));
      this.ramCombo.setBounds(217, 198, 100, 28);
      this.add(this.ramCombo);
      JLabel wyjdzHeader = new JLabel("Przycisk Wyjdź z Gry");
      wyjdzHeader.setFont(Minecraft.getInstance().getFont(14));
      wyjdzHeader.setBounds(27, 227, 800, 25);
      this.add(wyjdzHeader);
      JLabel wyjdzOpis = new JLabel(
         "<html><p style=\"font-family:MinecraftZyczu\">W minecraftcie wprowadzono zabezpieczenie, polegające na tym, że przycisk Wyjdź z Gry nie jest wyświetlany na wersji nopremium. Launcher by Zyczu potrafi obejść to zabezpieczenie i wymusić obecność tego przycisku w menu.</p></html>"
      );
      wyjdzOpis.setBounds(57, 252, 770, 50);
      wyjdzOpis.setFont(Minecraft.getInstance().getFont(12));
      this.add(wyjdzOpis);
      this.wyjdzCheck = new JCheckBox("Pokazuj przycisk Wyjdź z Gry w menu");
      this.wyjdzCheck.setFont(Minecraft.getInstance().getFont(12));
      this.wyjdzCheck.setBounds(57, 302, 770, 20);
      this.add(this.wyjdzCheck);
      JLabel nickHeader = new JLabel("Sprawdzanie nicku");
      nickHeader.setFont(Minecraft.getInstance().getFont(14));
      nickHeader.setBounds(27, 322, 800, 25);
      this.add(nickHeader);
      JLabel nickOpis = new JLabel(
         "<html><p style=\"font-family:MinecraftZyczu\">Domyślnie w minecraftcie nick nie może zawierać dowolnych znaków. Użycie nieprawidłowego nicku może skutkować brakiem możliwości wejścia na większość serwerów, lub nawet banem. Launcher by Zyczu nie pozwoli na ustawienie takiego nicku. Jeżeli wiesz co robisz, możesz wyłączyć to zabezpiecznie.</p></html>"
      );
      nickOpis.setFont(Minecraft.getInstance().getFont(12));
      nickOpis.setBounds(57, 347, 770, 50);
      this.add(nickOpis);
      this.nickCheck = new JCheckBox("Zezwalaj na użycie nieprawidłowych nicków");
      this.nickCheck.setFont(Minecraft.getInstance().getFont(12));
      this.nickCheck.setBounds(57, 397, 770, 20);
      this.add(this.nickCheck);
      JButton przycisk1 = new JButton("Zapisz zmiany");
      przycisk1.setFont(Minecraft.getInstance().getFont(12));
      przycisk1.setBounds(272, 427, 150, 38);
      przycisk1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            SettingsPanel.this.saveSettings();
         }
      });
      this.add(przycisk1);
      JButton przycisk2 = new JButton("Anuluj");
      przycisk2.setFont(Minecraft.getInstance().getFont(12));
      przycisk2.setBounds(432, 432, 150, 28);
      przycisk2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Minecraft.switchPanel(SettingsPanel.getInstance(), LoginPanel.getInstance());
         }
      });
      this.add(przycisk2);
   }

   public void loadSettings() {
      Properties p = Properties.getInstance();
      this.lastSelectedIndex = OutOfMemoryFix.defaultSelectedIndex;

      for(int i = 0; i < OutOfMemoryFix.wartosci.length; ++i) {
         if (OutOfMemoryFix.wartosci[i] == p.getNumber("ram")) {
            this.lastSelectedIndex = i;
            break;
         }
      }

      this.ramCombo.setSelectedIndex(this.lastSelectedIndex);
      if (p.get("wyjdz", "nie").equalsIgnoreCase("tak")) {
         this.wyjdzCheck.setSelected(true);
      } else {
         this.wyjdzCheck.setSelected(false);
      }

      if (p.get("verifyUsername", "true").equals("false")) {
         this.nickCheck.setSelected(true);
      } else {
         this.nickCheck.setSelected(false);
      }

   }

   public void saveSettings() {
      Properties p = Properties.getInstance();
      if (this.wyjdzCheck.isSelected()) {
         p.set("wyjdz", "tak");
      } else {
         p.set("wyjdz", "nie");
      }

      if (this.nickCheck.isSelected()) {
         p.set("verifyUsername", "false");
      } else {
         p.set("verifyUsername", "true");
      }

      if (this.ramCombo.getSelectedIndex() != this.lastSelectedIndex) {
         p.set("ram", (long)OutOfMemoryFix.wartosci[this.ramCombo.getSelectedIndex()]);
         JOptionPane.showMessageDialog(
            Minecraft.frame, "Launcher zostanie uruchomiony ponownie z nową ilością RAM\n\nJeżeli launcher się nie uruchomi, uruchom go ręcznie."
         );
         Minecraft.getInstance().fork(OutOfMemoryFix.wartosci[this.ramCombo.getSelectedIndex()]);
         System.exit(1);
      }

      Minecraft.switchPanel(this, LoginPanel.getInstance());
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               SettingsPanel.this.update(SettingsPanel.this.getGraphics());
            } catch (NullPointerException var2) {
               Minecraft.log.warning("Bład: " + var2.toString());
            }

         }
      });
   }

   private AlphaComposite makeComposite(float alpha) {
      int type = 3;
      return AlphaComposite.getInstance(type, alpha);
   }

   public void paintComponent(Graphics g) {
      g.drawImage(Minecraft.getInstance().backgroundGeneric, 0, 0, null);
      Graphics2D g2 = (Graphics2D)g;
      Composite originalComposite = g2.getComposite();
      g2.setPaint(Color.WHITE);
      g2.setComposite(this.makeComposite(0.6F));
      g2.fillRect(17, 67, 820, 405);
      g2.setComposite(originalComposite);
   }

   public static SettingsPanel getInstance() {
      if (instance == null) {
         instance = new SettingsPanel();
      }

      return instance;
   }
}
