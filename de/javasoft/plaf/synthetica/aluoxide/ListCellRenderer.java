package de.javasoft.plaf.synthetica.aluoxide;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer.UIResource;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ListCellRenderer extends UIResource {
   private Border rendererBorder = new EmptyBorder(2, 6, 2, 6);

   public ListCellRenderer() {
      super();
   }

   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      JComponent c = (JComponent)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      c.setBorder(this.rendererBorder);
      c.setOpaque(isSelected);
      return c;
   }
}
