package pl.zyczu.minecraft.launcher.gui;

import javax.swing.JPanel;
import pl.zyczu.minecraft.launcher.repo.ModPack;

public abstract class Loadable extends JPanel {
   private static final long serialVersionUID = -3711468665319973388L;

   public Loadable(boolean b) {
      super(b);
   }

   public abstract void onLoad(MultiPanelStub var1);

   public abstract void onModPackChanged();

   protected void openView(Loadable other) {
      MultiPanelStub.getInstance().openView(other);
   }

   protected ModPack getCurrentModPack() {
      return MultiPanelStub.getInstance().getCurrentModPack();
   }

   protected void updateComboBox() {
      MultiPanel.getInstance().updateComboBox();
   }
}
