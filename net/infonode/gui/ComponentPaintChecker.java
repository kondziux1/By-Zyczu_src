package net.infonode.gui;

import java.awt.Component;

public class ComponentPaintChecker {
   private boolean okToPaint = false;

   public ComponentPaintChecker(Component var1) {
      super();
      var1.addHierarchyListener(new ComponentPaintChecker$1(this, var1));
   }

   public boolean isPaintingOk() {
      return this.okToPaint;
   }
}
