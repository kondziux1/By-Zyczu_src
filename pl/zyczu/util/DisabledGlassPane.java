package pl.zyczu.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class DisabledGlassPane extends JComponent implements KeyListener {
   private static final Border MESSAGE_BORDER = new EmptyBorder(10, 10, 10, 10);
   private JLabel message = new JLabel();

   public DisabledGlassPane() {
      super();
      this.setOpaque(false);
      Color base = UIManager.getColor("inactiveCaptionBorder");
      Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);
      this.setBackground(background);
      this.setLayout(new GridBagLayout());
      this.add(this.message, new GridBagConstraints());
      this.message.setOpaque(true);
      this.message.setBorder(MESSAGE_BORDER);
      this.addMouseListener(new MouseAdapter() {
      });
      this.addMouseMotionListener(new MouseMotionAdapter() {
      });
      this.addKeyListener(this);
      this.setFocusTraversalKeysEnabled(false);
   }

   protected void paintComponent(Graphics g) {
      g.setColor(this.getBackground());
      g.fillRect(0, 0, this.getSize().width, this.getSize().height);
   }

   public void setBackground(Color background) {
      super.setBackground(background);
      Color messageBackground = new Color(background.getRGB());
      this.message.setBackground(messageBackground);
   }

   public void keyPressed(KeyEvent e) {
      e.consume();
   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyReleased(KeyEvent e) {
      e.consume();
   }

   public void activate(String text) {
      if (text != null && text.length() > 0) {
         this.message.setVisible(true);
         this.message.setText(text);
         this.message.setForeground(this.getForeground());
      } else {
         this.message.setVisible(false);
      }

      this.setVisible(true);
      this.setCursor(Cursor.getPredefinedCursor(3));
      this.requestFocusInWindow();
   }

   public void deactivate() {
      this.setCursor(null);
      this.setVisible(false);
   }
}
