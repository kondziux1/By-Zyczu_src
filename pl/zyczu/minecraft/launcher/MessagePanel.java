package pl.zyczu.minecraft.launcher;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel {
   private final int szerokosc = 650;
   private final int wysokosc = 100;
   private final int odstep = 0;
   private final int margines = 10;
   private final int lewo = 107;
   private final int gora = 195;
   private JLabel line1 = null;
   private JLabel line2 = null;
   private JLabel line3 = null;
   private JButton przycisk = null;
   private short postep = -1;
   private static MessagePanel instance = null;

   public MessagePanel() {
      super(true);
      Properties p = Properties.getInstance();
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(null);
      this.line1 = new JLabel("", 0);
      this.line1.setBounds(107, 185, 630, 25);
      this.line1.setFont(Minecraft.getInstance().getFont(18));
      this.line1.setVerticalAlignment(0);
      this.line2 = new JLabel("", 0);
      this.line2.setBounds(107, 215, 630, 20);
      this.line2.setFont(Minecraft.getInstance().getFont(12));
      this.line2.setVerticalAlignment(0);
      this.line3 = new JLabel("", 0);
      this.line3.setBounds(107, 235, 630, 20);
      this.line3.setFont(Minecraft.getInstance().getFont(12));
      this.line3.setVerticalAlignment(0);
      this.add(this.line1);
      this.add(this.line2);
      this.add(this.line3);
   }

   public void setTitle(String t) {
      this.line1.setText(t);
   }

   public void setFirst(String t) {
      this.line2.setText(t);
   }

   public void setSecond(String t) {
      this.line3.setText(t);
   }

   public void cleanup() {
      this.line1.setText("");
      this.line2.setText("");
      this.line3.setText("");
      if (this.przycisk != null) {
         this.remove(this.przycisk);
         this.przycisk = null;
      }

      this.postep = -1;
      this.update();
   }

   public void removeButton() {
      this.remove(this.przycisk);
      this.update();
   }

   public void setButton(String t, ActionListener callback) {
      this.przycisk = new JButton(t);
      this.przycisk.setFont(Minecraft.getInstance().getFont(12));
      this.przycisk.setBounds(297, 255, 250, 25);
      this.przycisk.addActionListener(callback);
      this.add(this.przycisk);
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            MessagePanel.this.przycisk.update(MessagePanel.this.przycisk.getGraphics());
         }
      });
   }

   public void updateProgress(short nowy) {
      this.postep = nowy;
      this.update();
   }

   public void update() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               MessagePanel.this.update(MessagePanel.this.getGraphics());
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
      g2.fillRect(97, 185, 650, 100);
      g2.setComposite(originalComposite);
   }

   public static MessagePanel getInstance() {
      if (instance == null) {
         instance = new MessagePanel();
      }

      return instance;
   }

   public static void shutdownInstance() {
      instance = null;
   }
}
