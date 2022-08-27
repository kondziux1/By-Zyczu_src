package net.infonode.gui.hover.action;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.Timer;
import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;

public class DelayedHoverExitAction implements HoverListener {
   private HashMap timers = new HashMap();
   private HashMap exitEvents = new HashMap();
   private HoverListener action;
   private int delay;

   public DelayedHoverExitAction(HoverListener var1, int var2) {
      super();
      this.action = var1;
      this.delay = var2;
   }

   public int getDelay() {
      return this.delay;
   }

   public void setDelay(int var1) {
      this.delay = var1;
   }

   public HoverListener getHoverAction() {
      return this.action;
   }

   public void forceExit(Component var1) {
      if (this.timers.containsKey(var1)) {
         ((Timer)this.timers.get(var1)).stop();
         this.timers.remove(var1);
         HoverEvent var2 = (HoverEvent)this.exitEvents.get(var1);
         this.exitEvents.remove(var1);
         this.action.mouseExited(var2);
      }

   }

   public void mouseEntered(HoverEvent var1) {
      Component var2 = var1.getSource();
      if (this.timers.containsKey(var2)) {
         ((Timer)this.timers.get(var2)).stop();
      } else {
         Timer var3 = new Timer(this.delay, new DelayedHoverExitAction$1(this, var2));
         var3.setRepeats(false);
         this.timers.put(var2, var3);
         this.action.mouseEntered(var1);
      }

   }

   public void mouseExited(HoverEvent var1) {
      this.exitEvents.put(var1.getSource(), var1);
      ((Timer)this.timers.get(var1.getSource())).restart();
   }
}
