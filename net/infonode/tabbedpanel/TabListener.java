package net.infonode.tabbedpanel;

public interface TabListener {
   void tabAdded(TabEvent var1);

   void tabRemoved(TabRemovedEvent var1);

   void tabDragged(TabDragEvent var1);

   void tabDropped(TabDragEvent var1);

   void tabDragAborted(TabEvent var1);

   void tabSelected(TabStateChangedEvent var1);

   void tabDeselected(TabStateChangedEvent var1);

   void tabHighlighted(TabStateChangedEvent var1);

   void tabDehighlighted(TabStateChangedEvent var1);

   void tabMoved(TabEvent var1);
}
