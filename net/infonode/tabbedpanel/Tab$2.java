package net.infonode.tabbedpanel;

class Tab$2 implements TabListener {
   Tab$2(Tab var1) {
      super();
      this.this$0 = var1;
   }

   public void tabAdded(TabEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$100(this.this$0);
      }

   }

   public void tabRemoved(TabRemovedEvent var1) {
      if (var1.getTab() == this.this$0) {
         var1.getTabbedPanel().removeTabListener(this);
         Tab.access$200(this.this$0, var1);
      }

   }

   public void tabMoved(TabEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$300(this.this$0);
      }

   }

   public void tabDragged(TabDragEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$400(this.this$0, var1);
      }

   }

   public void tabDropped(TabDragEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$500(this.this$0, var1);
      }

   }

   public void tabDragAborted(TabEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$600(this.this$0);
      }

   }

   public void tabSelected(TabStateChangedEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab var2 = var1.getPreviousTab();
         boolean var3 = var2 != null && var2.getFocusableComponent() != null && var2.getFocusableComponent().hasFocus();
         if (var2 != null && var2.getFocusableComponent() != null) {
            var2.getFocusableComponent().setFocusable(false);
         }

         if (Tab.access$700(this.this$0) != null) {
            Tab.access$700(this.this$0).setFocusable(true);
            if (var3) {
               Tab.access$700(this.this$0).requestFocusInWindow();
            }
         }

         Tab.access$800(this.this$0, var1);
      }

   }

   public void tabDeselected(TabStateChangedEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$900(this.this$0, var1);
      }

   }

   public void tabHighlighted(TabStateChangedEvent var1) {
      if (var1.getTab() == this.this$0) {
         Tab.access$1000(this.this$0, var1);
      }

   }

   public void tabDehighlighted(TabStateChangedEvent var1) {
      if (var1.getPreviousTab() == this.this$0) {
         Tab.access$1100(this.this$0, var1);
      }

   }
}
