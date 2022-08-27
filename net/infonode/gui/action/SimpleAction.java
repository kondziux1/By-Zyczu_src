package net.infonode.gui.action;

import javax.swing.Action;
import net.infonode.gui.icon.IconProvider;

public abstract class SimpleAction implements IconProvider {
   public abstract String getName();

   public abstract void perform();

   public abstract boolean isEnabled();

   protected SimpleAction() {
      super();
   }

   public Action toSwingAction() {
      SimpleAction$1 var1 = new SimpleAction$1(this, this.getName(), this.getIcon());
      var1.setEnabled(this.isEnabled());
      return var1;
   }
}
