package pl.zyczu.minecraft.launcher;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {
   private static final long serialVersionUID = 1L;

   public LoadingPanel(String v) {
      super();
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new GridBagLayout());
      JLabel text = new JLabel(
         "<html><center><p>Minecraft Launcher by Zyczu v. "
            + v
            + "</p><p><a href=\"http://minecraft.zyczu.pl\">minecraft.zyczu.pl</a></p><p>Trwa ładowanie...</p></center></html>"
      );
      text.setVerticalAlignment(0);
      text.setVisible(true);
      this.add(text, new GridBagConstraints());
   }
}
