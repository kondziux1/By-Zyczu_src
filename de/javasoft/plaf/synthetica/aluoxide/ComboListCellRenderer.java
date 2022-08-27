package de.javasoft.plaf.synthetica.aluoxide;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ComboListCellRenderer extends DefaultListCellRenderer {
   private Border listLabelBorder = new EmptyBorder(2, 4, 2, 4);
   private Border comboLabelBorder = new EmptyBorder(1, 0, 1, 0);

   public ComboListCellRenderer() {
      super();
      this.setName("ComboBox.listRenderer");
   }

   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      JComponent c = (JComponent)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      c.setBorder(index >= 0 ? this.listLabelBorder : this.comboLabelBorder);
      if (index >= 0) {
         c.setOpaque(isSelected);
      } else {
         c.setOpaque(false);
      }

      return c;
   }
}
