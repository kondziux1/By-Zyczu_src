package net.infonode.docking;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.util.DockingUtil;

class DockingWindow$10 extends DropAction {
   DockingWindow$10(DockingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      if (this.this$0.getWindowProperties().getUndockEnabled() && this.this$0.getWindowProperties().getUndockOnDropEnabled()) {
         Point var3 = var2.getPoint();
         Point var4 = SwingUtilities.convertPoint((Component)var2.getSource(), var3, this.this$0.getRootWindow());
         Point var5 = SwingUtilities.convertPoint((Component)var2.getSource(), var3, this.this$0.getRootWindow().getRootPane());
         if (!this.this$0.getRootWindow().contains(var4) && !this.this$0.getRootWindow().floatingWindowsContainPoint(var5)) {
            FloatingWindow var6 = DockingUtil.getFloatingWindowFor(var1);
            if (var6 == null || var6.getChildWindowCount() > 0 && var6.getChildWindow(0).getChildWindowCount() > 1) {
               SwingUtilities.convertPointToScreen(var3, (Component)var2.getSource());
               var3.x -= var1.getWidth() / 2;
               var3.y -= Math.min(DockingWindow.access$600(), var1.getHeight() / 2);

               try {
                  var1.undockWithAbort(var3);
               } catch (OperationAbortedException var8) {
               }
            }
         }
      }

   }
}
