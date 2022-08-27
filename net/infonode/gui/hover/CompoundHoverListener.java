package net.infonode.gui.hover;

public class CompoundHoverListener implements HoverListener {
   private HoverListener firstListener;
   private HoverListener secondListener;

   public CompoundHoverListener(HoverListener var1, HoverListener var2) {
      super();
      this.firstListener = var1;
      this.secondListener = var2;
   }

   public HoverListener getFirstListener() {
      return this.firstListener;
   }

   public HoverListener getSecondListener() {
      return this.secondListener;
   }

   public void mouseEntered(HoverEvent var1) {
      this.firstListener.mouseEntered(var1);
      this.secondListener.mouseEntered(var1);
   }

   public void mouseExited(HoverEvent var1) {
      this.secondListener.mouseExited(var1);
      this.firstListener.mouseExited(var1);
   }
}
