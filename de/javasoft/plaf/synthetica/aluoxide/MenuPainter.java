package de.javasoft.plaf.synthetica.aluoxide;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import de.javasoft.plaf.synthetica.painter.ImagePainter;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class MenuPainter extends de.javasoft.plaf.synthetica.painter.MenuPainter {
   public MenuPainter() {
      super();
   }

   @Override
   public void paintPopupMenuBackground(JPopupMenu popup, boolean toplevelPopup, Graphics g, int x, int y, int w, int h) {
      super.paintPopupMenuBackground(popup, toplevelPopup, g, x, y, w, h);
      if (toplevelPopup) {
         y += 13;
         h -= 26;
      }

      Insets insets = new Insets(5, 5, 5, 5);
      String imagePath = "/de/javasoft/plaf/synthetica/aluoxide/images/popupMenuNoise.png";
      ImagePainter imagePainter = new ImagePainter(g, x, y, w, h, imagePath, insets, insets, 1, 1);
      imagePainter.drawCenter();
   }

   @Override
   public void paintMenuBarBackground(JComponent c, SyntheticaState state, Graphics g, int x, int y, int w, int h) {
      Window window = SwingUtilities.getWindowAncestor(c);
      JRootPane rootPane = window instanceof JFrame ? ((JFrame)window).getRootPane() : (window instanceof JDialog ? ((JDialog)window).getRootPane() : null);
      if (rootPane != null && rootPane.getWindowDecorationStyle() == 0) {
         String imagePath = "Synthetica.rootPane.border.selected";
         imagePath = SyntheticaLookAndFeel.getString(imagePath, window);
         Insets sInsets = (Insets)SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", window).clone();
         Insets insets = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.insets", window, false);
         sInsets.left = sInsets.left < insets.left ? insets.left : sInsets.left;
         sInsets.right = sInsets.right < insets.right ? insets.right : sInsets.right;
         Insets dInsets = new Insets(0, 0, 0, 0);
         ImagePainter imagePainter = new ImagePainter(g, x, y, w, h, imagePath, sInsets, dInsets, 1, 1);
         imagePainter.drawCenter();
      }

      super.paintMenuBarBackground(c, state, g, x, y, w, h);
   }
}
