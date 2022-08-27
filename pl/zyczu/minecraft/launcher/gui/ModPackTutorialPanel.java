package pl.zyczu.minecraft.launcher.gui;

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
import javax.swing.JPanel;
import pl.zyczu.minecraft.launcher.LoginPanel;
import pl.zyczu.minecraft.launcher.Minecraft;

public class ModPackTutorialPanel extends JPanel {
   private static final long serialVersionUID = 7219993324949059060L;
   private final int paddingTop = 40;
   private final int szerokosc = 820;
   private final int wysokosc = 405;
   private final int margines = 10;
   private final int lewo = 27;
   private final int gora = 77;
   private final int maxszer = 800;
   private JPanel innerPanel = null;
   private TutorialScreen currentScreen = null;
   protected boolean firstTime = true;
   private static ModPackTutorialPanel instance = null;

   private ModPackTutorialPanel() {
      super(true);
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(null);
      this.innerPanel = new JPanel();
      this.innerPanel.setOpaque(false);
      this.innerPanel.setBounds(27, 77, 800, 375);
      this.innerPanel.setLayout(null);
      this.add(this.innerPanel);
      JButton anuluj = new JButton("Anuluj");
      anuluj.setBounds(37, 433, 100, 28);
      anuluj.setFont(Minecraft.getInstance().getFont(12));
      anuluj.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            if (ModPackTutorialPanel.this.firstTime) {
               Minecraft.switchPanel(ModPackTutorialPanel.this, LoginPanel.getInstance());
            } else {
               Minecraft.switchPanel(ModPackTutorialPanel.this, MultiPanel.getInstance());
            }

         }
      });
      this.add(anuluj);
      JButton dalej = new JButton("Dalej");
      dalej.setBounds(717, 433, 100, 28);
      dalej.setFont(Minecraft.getInstance().getFont(12));
      dalej.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            LongEventResponse.execute(new Runnable() {
               public void run() {
                  ModPackTutorialPanel.this.currentScreen.onDalejClicked();
               }
            });
         }
      });
      this.add(dalej);
      this.switchScreen(new WelcomeTutorialScreen());
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               ModPackTutorialPanel.this.update(ModPackTutorialPanel.this.getGraphics());
            } catch (NullPointerException var2) {
               Minecraft.log.warning("Bład: " + var2.toString());
            }

         }
      });
   }

   protected void switchScreen(final TutorialScreen tutorialScreen) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            if (ModPackTutorialPanel.this.currentScreen != null) {
               ModPackTutorialPanel.this.currentScreen.removeControls();
            }

            tutorialScreen.setPanel(ModPackTutorialPanel.this);
            tutorialScreen.createControls();
            ModPackTutorialPanel.this.currentScreen = tutorialScreen;
            ModPackTutorialPanel.this.update();
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

   protected JPanel getInnerPanel() {
      return this.innerPanel;
   }

   public static ModPackTutorialPanel getInstance() {
      if (instance == null) {
         instance = new ModPackTutorialPanel();
      }

      return instance;
   }

   public void setFirstTime(boolean b) {
      this.firstTime = b;
   }
}
