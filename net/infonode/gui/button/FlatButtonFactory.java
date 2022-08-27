package net.infonode.gui.button;

import java.io.Serializable;
import javax.swing.AbstractButton;

public class FlatButtonFactory implements ButtonFactory, Serializable {
   private static final long serialVersionUID = 1L;

   public FlatButtonFactory() {
      super();
   }

   public AbstractButton createButton(Object var1) {
      return net.infonode.gui.ButtonFactory.createFlatHighlightButton(null, null, 0, null);
   }
}
