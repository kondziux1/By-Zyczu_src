package net.infonode.docking;

import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabDragEvent;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabbedPanel;

class TabWindowMover extends TabAdapter {
   private AbstractTabWindow window;
   private TabbedPanel tabbedPanel;
   private WindowDragger dragger;

   TabWindowMover(AbstractTabWindow var1, TabbedPanel var2) {
      super();
      this.window = var1;
      this.tabbedPanel = var2;
   }

   public void tabDragged(TabDragEvent var1) {
      if (this.dragger == null) {
         DockingWindow var2 = ((WindowTab)var1.getTab()).getWindow();
         if (!var2.getWindowProperties().getDragEnabled()) {
            return;
         }

         this.dragger = new WindowDragger(var2);
         this.window.setDraggedTabIndex(this.tabbedPanel.getTabIndex(var1.getTab()));
      }

      this.dragger.dragWindow(var1.getMouseEvent());
   }

   public void tabDropped(TabDragEvent var1) {
      if (this.dragger != null) {
         this.dragger.dropWindow(var1.getMouseEvent());
         this.dragger = null;
      }

   }

   public void tabDragAborted(TabEvent var1) {
      if (this.dragger != null) {
         this.dragger.abortDrag();
         this.dragger = null;
      }

   }
}
