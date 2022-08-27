package net.infonode.gui.button;

import java.io.Serializable;
import javax.swing.AbstractButton;

public interface ButtonFactory extends Serializable {
   AbstractButton createButton(Object var1);
}
