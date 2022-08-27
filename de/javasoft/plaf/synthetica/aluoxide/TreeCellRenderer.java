package de.javasoft.plaf.synthetica.aluoxide;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeCellRenderer extends DefaultTreeCellRenderer {
   private Border rendererBorder = new EmptyBorder(2, 2, 2, 2);

   public TreeCellRenderer() {
      super();
   }

   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      JComponent c = (JComponent)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      c.setBorder(this.rendererBorder);
      return c;
   }
}
