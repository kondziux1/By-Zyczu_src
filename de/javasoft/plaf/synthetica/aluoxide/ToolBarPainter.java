package de.javasoft.plaf.synthetica.aluoxide;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import de.javasoft.plaf.synthetica.painter.ImagePainter;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class ToolBarPainter extends de.javasoft.plaf.synthetica.painter.ToolBarPainter {
   public ToolBarPainter() {
      super();
   }

   @Override
   public void paintToolBarBackground(JComponent c, SyntheticaState state, Graphics g, int x, int y, int w, int h) {
      if (SyntheticaLookAndFeel.isOpaque(c)) {
         Window window = this.getWindow(c.getRootPane());
         Rectangle r = SwingUtilities.convertRectangle(c, c.getBounds(), c.getRootPane());
         Insets sInsets = (Insets)SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", c, false).clone();
         if (c instanceof JToolBar && ((JToolBar)c).getOrientation() != 0) {
            sInsets.top = 112;
            sInsets.bottom = 62;
         } else {
            sInsets.top = r.y + h >= 122 ? sInsets.top : r.y;
         }

         Insets dInsets = new Insets(0, 0, 0, 0);
         String imagePath = SyntheticaLookAndFeel.getString("Synthetica.rootPane.border" + (window.isActive() ? ".selected" : ""), c);
         ImagePainter imagePainter = new ImagePainter(g, x, y, w, h, imagePath, sInsets, dInsets, 1, 1);
         imagePainter.drawCenter();
      }

   }

   private Window getWindow(JRootPane root) {
      Container parent = root.getParent();
      return parent instanceof Window ? (Window)parent : SwingUtilities.getWindowAncestor(parent);
   }
}
