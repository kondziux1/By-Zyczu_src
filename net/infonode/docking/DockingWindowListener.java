package net.infonode.docking;

public interface DockingWindowListener {
   void windowAdded(DockingWindow var1, DockingWindow var2);

   void windowRemoved(DockingWindow var1, DockingWindow var2);

   void windowShown(DockingWindow var1);

   void windowHidden(DockingWindow var1);

   void viewFocusChanged(View var1, View var2);

   void windowClosing(DockingWindow var1) throws OperationAbortedException;

   void windowClosed(DockingWindow var1);

   void windowUndocking(DockingWindow var1) throws OperationAbortedException;

   void windowUndocked(DockingWindow var1);

   void windowDocking(DockingWindow var1) throws OperationAbortedException;

   void windowDocked(DockingWindow var1);

   void windowMinimizing(DockingWindow var1) throws OperationAbortedException;

   void windowMinimized(DockingWindow var1);

   void windowMaximizing(DockingWindow var1) throws OperationAbortedException;

   void windowMaximized(DockingWindow var1);

   void windowRestoring(DockingWindow var1) throws OperationAbortedException;

   void windowRestored(DockingWindow var1);
}
