package net.infonode.tabbedpanel.theme.internal.laftheme;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.infonode.gui.DynamicUIManager;
import net.infonode.gui.DynamicUIManagerListener;
import net.infonode.util.Direction;

public class PaneHandler {
   private JFrame frame;
   private PanePainter[] panePainters;
   private PaneHandlerListener listener;
   private DynamicUIManagerListener uiListener = new PaneHandler$1(this);

   PaneHandler(PaneHandlerListener var1) {
      super();
      this.listener = var1;
      DynamicUIManager.getInstance().addPrioritizedListener(this.uiListener);
      Direction[] var2 = Direction.getDirections();
      this.panePainters = new PanePainter[var2.length];
      JPanel var3 = new JPanel(null);

      for(int var4 = 0; var4 < var2.length; ++var4) {
         this.panePainters[var4] = new PanePainter(var2[var4]);
         var3.add(this.panePainters[var4]);
         this.panePainters[var4].setBounds(0, 0, 600, 600);
      }

      this.frame = new JFrame();
      this.frame.getContentPane().add(var3, "Center");
      this.frame.pack();
   }

   void dispose() {
      if (this.frame != null) {
         DynamicUIManager.getInstance().removePrioritizedListener(this.uiListener);
         this.frame.removeAll();
         this.frame.dispose();
         this.frame = null;
      }

   }

   PanePainter getPainter(Direction var1) {
      for(int var2 = 0; var2 < this.panePainters.length; ++var2) {
         if (this.panePainters[var2].getDirection() == var1) {
            return this.panePainters[var2];
         }
      }

      return null;
   }

   JFrame getFrame() {
      return this.frame;
   }

   void update() {
      this.listener.updating();
      this.doUpdate();
   }

   private void doUpdate() {
      SwingUtilities.updateComponentTreeUI(this.frame);
      this.listener.updated();
   }
}
