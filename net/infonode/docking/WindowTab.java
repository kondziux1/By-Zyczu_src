package net.infonode.docking;

import java.awt.Component;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;
import net.infonode.docking.internalutil.ButtonInfo;
import net.infonode.docking.internalutil.CloseButtonInfo;
import net.infonode.docking.internalutil.DockButtonInfo;
import net.infonode.docking.internalutil.InternalDockingUtil;
import net.infonode.docking.internalutil.MinimizeButtonInfo;
import net.infonode.docking.internalutil.RestoreButtonInfo;
import net.infonode.docking.internalutil.UndockButtonInfo;
import net.infonode.docking.properties.WindowTabProperties;
import net.infonode.docking.properties.WindowTabStateProperties;
import net.infonode.gui.ContainerList;
import net.infonode.gui.panel.DirectionPanel;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.properties.propertymap.PropertyMapListener;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabStateProperties;
import net.infonode.util.Direction;

class WindowTab extends TitledTab {
   private static final TitledTabStateProperties EMPTY_PROPERTIES = new TitledTabStateProperties();
   private static final WindowTabProperties EMPTY_TAB_PROPERTIES = new WindowTabProperties();
   private static final ButtonInfo[] buttonInfos = new ButtonInfo[]{
      new UndockButtonInfo(WindowTabStateProperties.UNDOCK_BUTTON_PROPERTIES),
      new DockButtonInfo(WindowTabStateProperties.DOCK_BUTTON_PROPERTIES),
      new MinimizeButtonInfo(WindowTabStateProperties.MINIMIZE_BUTTON_PROPERTIES),
      new RestoreButtonInfo(WindowTabStateProperties.RESTORE_BUTTON_PROPERTIES),
      new CloseButtonInfo(WindowTabStateProperties.CLOSE_BUTTON_PROPERTIES)
   };
   private final DockingWindow window;
   private final AbstractButton[][] buttons = new AbstractButton[WindowTabState.getStateCount()][];
   private final DirectionPanel[] buttonBoxes = new DirectionPanel[WindowTabState.getStateCount()];
   private final DirectionPanel customComponents = new DirectionPanel();
   private final DirectionPanel highlightedFocusedPanel = new WindowTab$1(this);
   private final WindowTabProperties windowTabProperties = new WindowTabProperties(EMPTY_TAB_PROPERTIES);
   private ContainerList tabComponentsList;
   private boolean isFocused;
   private final PropertyMapListener windowPropertiesListener = new WindowTab$2(this);
   private final PropertyMapTreeListener windowTabPropertiesListener = new WindowTab$3(this);

   WindowTab(DockingWindow var1, boolean var2) {
      super(var1.getTitle(), var1.getIcon(), var2 ? null : new SimplePanel(var1), null);
      this.window = var1;

      for(int var3 = 0; var3 < WindowTabState.getStateCount(); ++var3) {
         this.buttonBoxes[var3] = new WindowTab$4(this);
         this.buttons[var3] = new AbstractButton[buttonInfos.length];
      }

      this.highlightedFocusedPanel.add(this.customComponents);
      this.highlightedFocusedPanel.add(this.buttonBoxes[WindowTabState.HIGHLIGHTED.getValue()]);
      this.setHighlightedStateTitleComponent(this.highlightedFocusedPanel);
      this.setNormalStateTitleComponent(this.buttonBoxes[WindowTabState.NORMAL.getValue()]);
      this.addMouseListener(new WindowTab$5(this));
      this.getProperties().addSuperObject(this.windowTabProperties.getTitledTabProperties());
      PropertyMapWeakListenerManager.addWeakTreeListener(this.windowTabProperties.getMap(), this.windowTabPropertiesListener);
      PropertyMapWeakListenerManager.addWeakListener(this.window.getWindowProperties().getMap(), this.windowPropertiesListener);
      this.windowTabProperties.getTitledTabProperties().getHighlightedProperties().addSuperObject(EMPTY_PROPERTIES);
   }

   public void updateUI() {
      super.updateUI();
      if (this.buttonBoxes != null) {
         for(int var1 = 0; var1 < WindowTabState.getStateCount(); ++var1) {
            if (this.buttonBoxes[var1] != null) {
               SwingUtilities.updateComponentTreeUI(this.buttonBoxes[var1]);
            }
         }
      }

   }

   void setFocused(boolean var1) {
      if (this.isFocused != var1) {
         this.isFocused = var1;
         TitledTabStateProperties var2 = var1 ? this.windowTabProperties.getFocusedProperties() : EMPTY_PROPERTIES;
         this.windowTabProperties
            .getTitledTabProperties()
            .getHighlightedProperties()
            .getMap()
            .replaceSuperMap(this.windowTabProperties.getTitledTabProperties().getHighlightedProperties().getMap().getSuperMap(), var2.getMap());
         this.highlightedFocusedPanel.remove(1);
         this.highlightedFocusedPanel.add(this.buttonBoxes[var1 ? WindowTabState.FOCUSED.getValue() : WindowTabState.HIGHLIGHTED.getValue()]);
         this.highlightedFocusedPanel.revalidate();
      }

   }

   void setProperties(WindowTabProperties var1) {
      this.windowTabProperties.getMap().replaceSuperMap(this.windowTabProperties.getMap().getSuperMap(), var1.getMap());
   }

   void unsetProperties() {
      this.setProperties(EMPTY_TAB_PROPERTIES);
   }

   void updateTabButtons(Map var1) {
      WindowTabState[] var2 = WindowTabState.getStates();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         WindowTabState var4 = var2[var3];
         WindowTabStateProperties var5 = var4 == WindowTabState.FOCUSED
            ? this.windowTabProperties.getFocusedButtonProperties()
            : (
               var4 == WindowTabState.HIGHLIGHTED
                  ? this.windowTabProperties.getHighlightedButtonProperties()
                  : this.windowTabProperties.getNormalButtonProperties()
            );
         InternalDockingUtil.updateButtons(buttonInfos, this.buttons[var3], this.buttonBoxes[var3], this.window, var5.getMap(), var1);
         this.buttonBoxes[var3]
            .setDirection(
               (var4 == WindowTabState.NORMAL ? this.getProperties().getNormalProperties() : this.getProperties().getHighlightedProperties()).getDirection()
            );
      }

      Direction var6 = this.getProperties().getHighlightedProperties().getDirection();
      this.highlightedFocusedPanel.setDirection(var6);
      this.customComponents.setDirection(var6);
   }

   DockingWindow getWindow() {
      return this.window;
   }

   void windowTitleChanged() {
      this.setText(this.getWindow().getTitle());
      this.setIcon(this.getWindow().getIcon());
   }

   public String toString() {
      return this.window != null ? this.window.toString() : null;
   }

   void setContentComponent(Component var1) {
      ((SimplePanel)this.getContentComponent()).setComponent(var1);
   }

   List getCustomTabComponentsList() {
      if (this.tabComponentsList == null) {
         this.tabComponentsList = new ContainerList(this.customComponents);
      }

      return this.tabComponentsList;
   }
}
