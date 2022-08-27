package net.infonode.gui.laf;

import javax.swing.UIDefaults;
import javax.swing.UIDefaults.ActiveValue;

class InfoNodeLookAndFeel$3 implements ActiveValue {
   InfoNodeLookAndFeel$3(InfoNodeLookAndFeel var1) {
      super();
      this.this$0 = var1;
   }

   public Object createValue(UIDefaults var1) {
      return new InfoNodeLookAndFeel.MyListCellRenderer.UIResource(
         InfoNodeLookAndFeel.access$000(this.this$0).getListItemBorder(), InfoNodeLookAndFeel.access$000(this.this$0).getListFocusedItemBorder()
      );
   }
}
