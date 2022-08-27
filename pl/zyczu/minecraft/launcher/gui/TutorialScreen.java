package pl.zyczu.minecraft.launcher.gui;

public interface TutorialScreen {
   void createControls();

   void removeControls();

   void setPanel(ModPackTutorialPanel var1);

   void onDalejClicked();
}
