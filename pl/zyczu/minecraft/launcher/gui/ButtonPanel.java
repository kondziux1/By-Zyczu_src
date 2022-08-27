package pl.zyczu.minecraft.launcher.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
   private static final long serialVersionUID = 6872371043931666577L;

   public ButtonPanel() {
      super(true);
      this.setOpaque(false);
      this.setLayout(new BoxLayout(this, 1));
   }
}
