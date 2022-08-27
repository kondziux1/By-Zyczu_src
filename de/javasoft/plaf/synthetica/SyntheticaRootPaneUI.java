package de.javasoft.plaf.synthetica;

import de.javasoft.plaf.synthetica.painter.SyntheticaPainter;
import de.javasoft.util.OS;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRootPaneUI;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;

public class SyntheticaRootPaneUI extends BasicRootPaneUI {
   private Window window;
   private JRootPane rootPane;
   private LayoutManager layoutManager;
   private LayoutManager oldLayoutManager;
   private MouseInputListener mouseInputListener;
   private WindowListener windowListener;
   private ComponentListener windowResizeListener;
   private JComponent titlePane;
   private Insets resizeInsets;
   public static final boolean EVAL_COPY = false;
   public static final int EVAL_HEIGHT = 0;
   public static final String EVAL_TEXT = "";

   public SyntheticaRootPaneUI() {
      super();
   }

   public static ComponentUI createUI(JComponent c) {
      return new SyntheticaRootPaneUI();
   }

   public static final boolean isEvalCopy() {
      return false;
   }

   public static final boolean isEvalDupa() {
      return true;
   }

   public void installUI(JComponent c) {
      super.installUI(c);
      this.rootPane = (JRootPane)c;
      if (this.isDecorated(this.rootPane)) {
         this.installClientDecorations(this.rootPane);
      }

   }

   public void uninstallUI(JComponent c) {
      super.uninstallUI(c);
      this.uninstallClientDecorations(this.rootPane);
      this.rootPane = null;
   }

   private void installClientDecorations(JRootPane root) {
      JComponent titlePane = new SyntheticaTitlePane(root, this);
      this.setTitlePane(root, titlePane);
      this.installBorder(root);
      this.installWindowListeners(root, root.getParent());
      this.installLayout(root);
   }

   private void uninstallClientDecorations(JRootPane root) {
      if (this.titlePane != null && this.titlePane instanceof SyntheticaTitlePane) {
         ((SyntheticaTitlePane)this.titlePane).uninstallListeners(root);
      }

      this.setTitlePane(root, null);
      this.uninstallBorder(root);
      this.uninstallWindowListeners(root);
      this.uninstallLayout(root);
   }

   public JComponent getTitlePane() {
      return this.titlePane;
   }

   void installBorder(JRootPane root) {
      if (this.isDecorated(root)) {
         root.setBorder(new Border() {
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
               SynthContext sc = SyntheticaLookAndFeel.createContext((JComponent)c, Region.ROOT_PANE, 0);
               SyntheticaPainter.getInstance().paintRootPaneBorder(sc, g, x, y, w, h);
            }

            public Insets getBorderInsets(Component c) {
               if (SyntheticaRootPaneUI.this.window instanceof Frame && (((Frame)SyntheticaRootPaneUI.this.window).getExtendedState() & 6) == 6) {
                  return new Insets(0, 0, 0, 0);
               } else {
                  Insets insets = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", SyntheticaRootPaneUI.this.window);
                  if (insets == null) {
                     insets = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.insets", SyntheticaRootPaneUI.this.window);
                  }

                  insets = (Insets)insets.clone();
                  insets.bottom += 16;
                  return insets;
               }
            }

            public boolean isBorderOpaque() {
               return false;
            }
         });
      }

   }

   private void uninstallBorder(JRootPane root) {
      root.setBorder(null);
   }

   private void installWindowListeners(JRootPane root, Component parent) {
      this.window = parent instanceof Window ? (Window)parent : SwingUtilities.getWindowAncestor(parent);
      this.resizeInsets = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.resizeInsets", this.window, root.getBorder().getBorderInsets(root));
      if (this.window != null) {
         if (this.mouseInputListener == null) {
            this.mouseInputListener = new SyntheticaRootPaneUI.MouseInputHandler(null);
         }

         this.window.addMouseListener(this.mouseInputListener);
         this.window.addMouseMotionListener(this.mouseInputListener);
         if (this.windowListener == null) {
            if (!SyntheticaLookAndFeel.isWindowOpacityEnabled(this.window)) {
               if (OS.getCurrentOS() == OS.Mac) {
                  SyntheticaLookAndFeel.setWindowOpaque(this.window, false);
               } else {
                  try {
                     SyntheticaLookAndFeel.setWindowOpaque(this.window, false);
                  } catch (Exception var4) {
                  }
               }
            }

            boolean opaque = SyntheticaLookAndFeel.getBoolean("Synthetica.window.contentPane.opaque", this.window, true);
            this.setContentPaneOpaque(this.window, opaque);
            this.windowListener = new WindowAdapter() {
               public void windowOpened(WindowEvent evt) {
                  Window w = evt.getWindow();
                  if (!SyntheticaLookAndFeel.isWindowOpacityEnabled(SyntheticaRootPaneUI.this.window)) {
                     SyntheticaLookAndFeel.setWindowOpaque(w, false);
                     if (SyntheticaLookAndFeel.getBoolean("Synthetica.window.contentPane.opaque", SyntheticaRootPaneUI.this.window, true)) {
                        SyntheticaRootPaneUI.this.setContentPaneOpaque(w, true);
                     }
                  }

               }
            };
            this.window.addWindowListener(this.windowListener);
         }

         if (SyntheticaLookAndFeel.isWindowShapeEnabled(this.window)) {
            if (this.windowResizeListener == null && SyntheticaLookAndFeel.isWindowShapeSupported(this.window)) {
               this.windowResizeListener = new ComponentAdapter() {
                  public void componentResized(ComponentEvent evt) {
                     Window w = (Window)evt.getComponent();
                     SyntheticaLookAndFeel.updateWindowShape(w);
                  }
               };
               this.window.addComponentListener(this.windowResizeListener);
            } else if (OS.getCurrentOS() == OS.Mac) {
               SyntheticaLookAndFeel.updateWindowShape(this.window);
            }
         }
      }

   }

   private void setContentPaneOpaque(Window w, boolean opaque) {
      if (w instanceof JDialog && ((JDialog)w).getContentPane() instanceof JComponent) {
         ((JComponent)((JDialog)w).getContentPane()).setOpaque(opaque);
      } else if (w instanceof JFrame && ((JFrame)w).getContentPane() instanceof JComponent) {
         ((JComponent)((JFrame)w).getContentPane()).setOpaque(opaque);
      } else if (w instanceof JWindow && ((JWindow)w).getContentPane() instanceof JComponent) {
         ((JComponent)((JWindow)w).getContentPane()).setOpaque(opaque);
      }

   }

   private void uninstallWindowListeners(JRootPane root) {
      if (this.window != null) {
         this.window.removeMouseListener(this.mouseInputListener);
         this.window.removeMouseMotionListener(this.mouseInputListener);
         this.window.removeWindowListener(this.windowListener);
         this.window.removeComponentListener(this.windowResizeListener);
      }

      this.mouseInputListener = null;
      this.windowListener = null;
      this.windowResizeListener = null;
      this.window = null;
   }

   private void installLayout(JRootPane root) {
      if (this.layoutManager == null) {
         this.layoutManager = new SyntheticaRootPaneUI.SyntheticaRootLayout(null);
      }

      this.oldLayoutManager = root.getLayout();
      root.setLayout(this.layoutManager);
   }

   private void uninstallLayout(JRootPane root) {
      if (this.oldLayoutManager != null) {
         root.setLayout(this.oldLayoutManager);
      }

      this.oldLayoutManager = null;
      this.layoutManager = null;
   }

   private void setTitlePane(JRootPane root, JComponent titlePane) {
      JLayeredPane layeredPane = root.getLayeredPane();
      if (this.titlePane != null) {
         this.titlePane.setVisible(false);
         layeredPane.remove(this.titlePane);
      }

      if (titlePane != null) {
         layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
         titlePane.setVisible(true);
      }

      this.titlePane = titlePane;
   }

   public void propertyChange(PropertyChangeEvent e) {
      super.propertyChange(e);
      String propertyName = e.getPropertyName();
      if (propertyName != null) {
         if (propertyName.equals("windowDecorationStyle")) {
            this.uninstallClientDecorations(this.rootPane);
            if (this.isDecorated(this.rootPane)) {
               this.installClientDecorations(this.rootPane);
            }
         } else if (propertyName.equals("ancestor")) {
            this.uninstallWindowListeners(this.rootPane);
            if (this.isDecorated(this.rootPane)) {
               this.installWindowListeners(this.rootPane, this.rootPane.getParent());
            }
         }

      }
   }

   public void setMaximizedBounds(Frame frame) {
      if (!SyntheticaLookAndFeel.isSystemPropertySet("synthetica.frame.fullscreen")) {
         GraphicsConfiguration gc = frame.getGraphicsConfiguration();
         Rectangle screenBounds = gc.getBounds();
         if (!SyntheticaLookAndFeel.isSystemPropertySet("synthetica.frame.respectScreenBoundsX")) {
            screenBounds.x = 0;
         }

         if (!SyntheticaLookAndFeel.isSystemPropertySet("synthetica.frame.respectScreenBoundsY")) {
            screenBounds.y = 0;
         }

         Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
         if (SyntheticaLookAndFeel.isSystemPropertySet("synthetica.frame.ignoreScreenInsets")) {
            frame.setMaximizedBounds(screenBounds);
         } else {
            if (!SyntheticaLookAndFeel.isSystemPropertySet("synthetica.frame.disableAutoHideTaskBarCorrection") && screenInsets.bottom == 0) {
               ++screenInsets.bottom;
            }

            Rectangle maxBounds = new Rectangle(
               screenBounds.x + screenInsets.left,
               screenBounds.y + screenInsets.top,
               screenBounds.width - (screenInsets.left + screenInsets.right),
               screenBounds.height - (screenInsets.top + screenInsets.bottom)
            );
            frame.setMaximizedBounds(maxBounds);
         }
      }
   }

   private boolean isDecorated(JRootPane root) {
      return root.getWindowDecorationStyle() != 0;
   }

   MouseInputListener getMouseInputListener() {
      return this.mouseInputListener;
   }

   private class MouseInputHandler implements MouseInputListener {
      private static final int WINDOW_MOVE = 1;
      private static final int WINDOW_RESIZE = 2;
      private int windowAction;
      private int dragXOffset;
      private int dragYOffset;
      private Dimension dragDimension;
      private int resizeType;
      private int minimumYPos;
      private int maximumYPos;
      private final PrivilegedExceptionAction<?> locationAction = new PrivilegedExceptionAction() {
         public Object run() throws HeadlessException {
            return MouseInfo.getPointerInfo().getLocation();
         }
      };
      private Frame frame = null;
      private Dialog dialog = null;

      private MouseInputHandler() {
         super();
         if (SyntheticaRootPaneUI.this.window instanceof Frame) {
            this.frame = (Frame)SyntheticaRootPaneUI.this.window;
         } else if (SyntheticaRootPaneUI.this.window instanceof Dialog) {
            this.dialog = (Dialog)SyntheticaRootPaneUI.this.window;
         }

      }

      private Point getPoint(MouseEvent evt) {
         return SwingUtilities.convertPoint(evt.getComponent(), evt.getPoint(), SyntheticaRootPaneUI.this.window);
      }

      public void mousePressed(MouseEvent evt) {
         if (SyntheticaRootPaneUI.this.isDecorated(SyntheticaRootPaneUI.this.rootPane)) {
            SyntheticaRootPaneUI.this.window.toFront();
            Rectangle bounds = SyntheticaRootPaneUI.this.window.getGraphicsConfiguration().getBounds();
            this.minimumYPos = bounds.y;
            this.maximumYPos = bounds.y + bounds.height;
            Point windowPoint = this.getPoint(evt);
            Point titlePanePoint = SwingUtilities.convertPoint(SyntheticaRootPaneUI.this.window, windowPoint, SyntheticaRootPaneUI.this.titlePane);
            int cursor = this.position2Cursor(SyntheticaRootPaneUI.this.window, windowPoint.x, windowPoint.y);
            if (cursor != 0
               || SyntheticaRootPaneUI.this.titlePane == null
               || !SyntheticaRootPaneUI.this.titlePane.contains(titlePanePoint)
               || this.dialog == null && (this.frame == null || this.frame.getExtendedState() == 6)) {
               if (this.isWindowResizable()) {
                  this.windowAction = 2;
                  this.dragXOffset = windowPoint.x;
                  this.dragYOffset = windowPoint.y;
                  this.dragDimension = new Dimension(SyntheticaRootPaneUI.this.window.getWidth(), SyntheticaRootPaneUI.this.window.getHeight());
                  this.resizeType = this.position2Cursor(SyntheticaRootPaneUI.this.window, windowPoint.x, windowPoint.y);
               }
            } else {
               this.windowAction = 1;
               this.dragXOffset = windowPoint.x;
               this.dragYOffset = windowPoint.y;
            }

         }
      }

      public void mouseReleased(MouseEvent evt) {
         if (this.windowAction == 2 && !SyntheticaRootPaneUI.this.window.isValid()) {
            SyntheticaRootPaneUI.this.window.validate();
            SyntheticaRootPaneUI.this.rootPane.repaint();
         }

         this.windowAction = -1;
         SyntheticaRootPaneUI.this.window.setCursor(Cursor.getDefaultCursor());
      }

      public void mouseMoved(MouseEvent evt) {
         if (SyntheticaRootPaneUI.this.isDecorated(SyntheticaRootPaneUI.this.rootPane)) {
            Point p = this.getPoint(evt);
            int cursor = this.position2Cursor(SyntheticaRootPaneUI.this.window, p.x, p.y);
            if (cursor != 0 && this.isWindowResizable()) {
               SyntheticaRootPaneUI.this.window.setCursor(Cursor.getPredefinedCursor(cursor));
            } else {
               SyntheticaRootPaneUI.this.window.setCursor(Cursor.getDefaultCursor());
            }

         }
      }

      public void mouseEntered(MouseEvent evt) {
         this.mouseMoved(evt);
      }

      public void mouseExited(MouseEvent evt) {
         SyntheticaRootPaneUI.this.window.setCursor(Cursor.getDefaultCursor());
      }

      public void mouseDragged(MouseEvent evt) {
         GraphicsConfiguration gc = SyntheticaRootPaneUI.this.window.getGraphicsConfiguration();
         Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
         this.minimumYPos = gc.getBounds().y + screenInsets.top;
         this.maximumYPos = gc.getBounds().y
            + gc.getBounds().height
            - screenInsets.bottom
            - SyntheticaRootPaneUI.this.titlePane.getHeight()
            - SyntheticaRootPaneUI.this.rootPane.getInsets().top;
         boolean respectMinimumYPos = SyntheticaLookAndFeel.isSystemPropertySet("synthetica.window.respectMinimumYPos");
         boolean respectMaximumYPos = SyntheticaLookAndFeel.isSystemPropertySet("synthetica.window.respectMaximumYPos");
         if (this.windowAction == 1) {
            try {
               Point p = (Point)AccessController.doPrivileged(this.locationAction);
               p.x -= this.dragXOffset;
               p.y -= this.dragYOffset;
               if (respectMinimumYPos && p.y < this.minimumYPos) {
                  p.y = this.minimumYPos;
               }

               if (respectMaximumYPos && p.y > this.maximumYPos) {
                  p.y = this.maximumYPos;
               }

               SyntheticaRootPaneUI.this.window.setLocation(p);
            } catch (PrivilegedActionException var11) {
            }
         } else if (this.windowAction == 2) {
            Point pt = this.getPoint(evt);
            Dimension min = (Dimension)SyntheticaLookAndFeel.get("Synthetica.rootPane.minimumWindowSize", SyntheticaRootPaneUI.this.window);
            if (min == null) {
               min = SyntheticaRootPaneUI.this.window.getMinimumSize();
            }

            Rectangle bounds = SyntheticaRootPaneUI.this.window.getBounds();
            Rectangle startBounds = new Rectangle(bounds);
            if (this.resizeType == 11 || this.resizeType == 7 || this.resizeType == 5) {
               bounds.width = Math.max(min.width, this.dragDimension.width + pt.x - this.dragXOffset);
            }

            if (this.resizeType == 9 || this.resizeType == 4 || this.resizeType == 5) {
               bounds.height = Math.max(min.height, this.dragDimension.height + pt.y - this.dragYOffset);
            }

            if (this.resizeType == 8 || this.resizeType == 6 || this.resizeType == 7) {
               int dy = pt.y - this.dragYOffset;
               bounds.y += dy;
               bounds.height -= dy;
               if (bounds.height < min.height) {
                  bounds.y += bounds.height - min.height;
                  bounds.height = min.height;
               }
            }

            if (this.resizeType == 10 || this.resizeType == 6 || this.resizeType == 4) {
               int dx = pt.x - this.dragXOffset;
               bounds.x += dx;
               bounds.width -= dx;
               if (bounds.width < min.width) {
                  bounds.x += bounds.width - min.width;
                  bounds.width = min.width;
               }
            }

            if (respectMinimumYPos && bounds.y < this.minimumYPos) {
               bounds.y = this.minimumYPos;
            }

            if (!bounds.equals(startBounds)) {
               SyntheticaRootPaneUI.this.window.setBounds(bounds);
            }
         }

      }

      public void mouseClicked(MouseEvent evt) {
         if (this.frame != null) {
            Point convertedPoint = SwingUtilities.convertPoint(SyntheticaRootPaneUI.this.window, this.getPoint(evt), SyntheticaRootPaneUI.this.titlePane);
            if (SyntheticaRootPaneUI.this.titlePane != null
               && SyntheticaRootPaneUI.this.titlePane.contains(convertedPoint)
               && evt.getClickCount() == 2
               && (evt.getModifiers() & 16) == 16) {
               if (this.frame.isResizable() && this.isFrameResizable()) {
                  ((SyntheticaTitlePane)SyntheticaRootPaneUI.this.titlePane).maximize();
               } else if (this.frame.isResizable() && !this.isFrameResizable()) {
                  ((SyntheticaTitlePane)SyntheticaRootPaneUI.this.titlePane).restore();
               }
            }

         }
      }

      private int position2Cursor(Window w, int x, int y) {
         int ww = w.getWidth();
         int wh = w.getHeight();
         if (x < SyntheticaRootPaneUI.this.resizeInsets.left && y < SyntheticaRootPaneUI.this.resizeInsets.top) {
            return 6;
         } else if (x > ww - SyntheticaRootPaneUI.this.resizeInsets.right && y < SyntheticaRootPaneUI.this.resizeInsets.top) {
            return 7;
         } else if (x < SyntheticaRootPaneUI.this.resizeInsets.left && y > wh - SyntheticaRootPaneUI.this.resizeInsets.bottom) {
            return 4;
         } else if (x > ww - SyntheticaRootPaneUI.this.resizeInsets.right && y > wh - SyntheticaRootPaneUI.this.resizeInsets.bottom) {
            return 5;
         } else if (x < SyntheticaRootPaneUI.this.resizeInsets.left) {
            return 10;
         } else if (x > ww - SyntheticaRootPaneUI.this.resizeInsets.right) {
            return 11;
         } else if (y < SyntheticaRootPaneUI.this.resizeInsets.top) {
            return 8;
         } else {
            return y > wh - SyntheticaRootPaneUI.this.resizeInsets.bottom ? 9 : 0;
         }
      }

      private boolean isFrameResizable() {
         return this.frame != null && this.frame.isResizable() && (this.frame.getExtendedState() & 6) == 0;
      }

      private boolean isDialogResizable() {
         return this.dialog != null && this.dialog.isResizable();
      }

      private boolean isWindowResizable() {
         return this.isFrameResizable() || this.isDialogResizable();
      }
   }

   private static class SyntheticaRootLayout implements LayoutManager2 {
      private SyntheticaRootLayout() {
         super();
      }

      public void addLayoutComponent(String name, Component comp) {
      }

      public void removeLayoutComponent(Component comp) {
      }

      public void addLayoutComponent(Component comp, Object constraints) {
      }

      public float getLayoutAlignmentX(Container target) {
         return 0.0F;
      }

      public float getLayoutAlignmentY(Container target) {
         return 0.0F;
      }

      public void invalidateLayout(Container target) {
      }

      public Dimension preferredLayoutSize(Container parent) {
         Insets insets = parent.getInsets();
         JRootPane root = (JRootPane)parent;
         JComponent titlePane = ((SyntheticaRootPaneUI)root.getUI()).titlePane;
         new Dimension(0, 0);
         Dimension dimC;
         if (root.getContentPane() != null) {
            dimC = root.getContentPane().getPreferredSize();
         } else {
            dimC = root.getSize();
         }

         dimC = dimC == null ? new Dimension(0, 0) : dimC;
         Dimension dimM = new Dimension(0, 0);
         if (root.getJMenuBar() != null) {
            dimM = root.getJMenuBar().getPreferredSize();
         }

         dimM = dimM == null ? new Dimension(0, 0) : dimM;
         Dimension dimT = titlePane.getPreferredSize();
         dimT = dimT == null ? new Dimension(0, 0) : dimT;
         int width = Math.max(dimC.width, Math.max(dimM.width, dimT.width)) + insets.left + insets.right;
         int height = dimC.height + dimM.height + dimT.height + insets.top + insets.bottom;
         return new Dimension(width, height);
      }

      public Dimension minimumLayoutSize(Container parent) {
         Insets insets = parent.getInsets();
         JRootPane root = (JRootPane)parent;
         JComponent titlePane = ((SyntheticaRootPaneUI)root.getUI()).titlePane;
         new Dimension(0, 0);
         Dimension dimC;
         if (root.getContentPane() != null) {
            dimC = root.getContentPane().getMinimumSize();
         } else {
            dimC = root.getSize();
         }

         dimC = dimC == null ? new Dimension(0, 0) : dimC;
         Dimension dimM = new Dimension(0, 0);
         if (root.getJMenuBar() != null) {
            dimM = root.getJMenuBar().getMinimumSize();
         }

         dimM = dimM == null ? new Dimension(0, 0) : dimM;
         Dimension dimT = titlePane.getMinimumSize();
         dimT = dimT == null ? new Dimension(0, 0) : dimT;
         int width = Math.max(dimC.width, Math.max(dimM.width, dimT.width)) + insets.left + insets.right;
         int height = dimC.height + dimM.height + dimT.height + insets.top + insets.bottom;
         return new Dimension(width, height);
      }

      public Dimension maximumLayoutSize(Container target) {
         Insets insets = target.getInsets();
         JRootPane root = (JRootPane)target;
         JComponent titlePane = ((SyntheticaRootPaneUI)root.getUI()).titlePane;
         new Dimension(0, 0);
         Dimension dimC;
         if (root.getContentPane() != null) {
            dimC = root.getContentPane().getMaximumSize();
         } else {
            dimC = root.getSize();
         }

         dimC = dimC == null ? new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE) : dimC;
         Dimension dimM = new Dimension(0, 0);
         if (root.getJMenuBar() != null) {
            dimM = root.getJMenuBar().getMaximumSize();
         }

         dimM = dimM == null ? new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE) : dimM;
         Dimension dimT = titlePane.getMaximumSize();
         dimT = dimT == null ? new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE) : dimT;
         int width = Math.max(dimC.width, Math.max(dimM.width, dimT.width));
         if (width != Integer.MAX_VALUE) {
            width += insets.left + insets.right;
         }

         int height = Math.max(dimC.height, Math.max(dimM.height, dimT.height));
         if (height != Integer.MAX_VALUE) {
            height += insets.top + insets.bottom;
         }

         return new Dimension(width, height);
      }

      public void layoutContainer(Container parent) {
         JRootPane rootPane = (JRootPane)parent;
         Rectangle bounds = rootPane.getBounds();
         Insets insets = rootPane.getInsets() != null ? rootPane.getInsets() : new Insets(0, 0, 0, 0);
         int width = bounds.width - insets.right - insets.left;
         int height = bounds.height - insets.top - insets.bottom;
         int nextY = 0;
         if (rootPane.getLayeredPane() != null) {
            rootPane.getLayeredPane().setBounds(insets.left, insets.top, width, height);
         }

         if (rootPane.getGlassPane() != null) {
            rootPane.getGlassPane().setBounds(insets.left, insets.top, width, height);
         }

         SyntheticaTitlePane titlePane = (SyntheticaTitlePane)((SyntheticaRootPaneUI)rootPane.getUI()).titlePane;
         if (titlePane.isEnabled()) {
            Dimension dimT = titlePane.getPreferredSize();
            if (dimT != null) {
               titlePane.setBounds(0, 0, width, dimT.height);
               nextY += dimT.height;
            }
         }

         JMenuBar mBar = rootPane.getJMenuBar();
         if (mBar != null) {
            Dimension dimM = mBar.getPreferredSize();
            if (titlePane.showMenuBarInTitlePane()) {
               boolean ltr = mBar.getComponentOrientation().isLeftToRight();
               JComponent mb = (JComponent)SyntheticaLookAndFeel.findComponent("RootPane.menuBar", titlePane);
               Insets mbInsets = mb.getInsets();
               nextY = mb.getY() + mbInsets.top;
               int mBarX = ltr ? mb.getX() : (titlePane.clipMenuBarWidth() ? mb.getX() : 0);
               int mBarWidth = ltr
                  ? width - mb.getX()
                  : (titlePane.clipMenuBarWidth() ? mb.getWidth() - mbInsets.left - mbInsets.right : mb.getX() + mb.getWidth());
               mBar.setBounds(mBarX + mbInsets.left, nextY, mBarWidth, dimM.height);
               nextY += mbInsets.top + mbInsets.bottom + dimM.height;
               JComponent userComponent = titlePane.getUserComponent();
               if (userComponent != null && titlePane.getLayoutStyle() == SyntheticaTitlePane.LayoutStyle.SECONDARYMENU) {
                  nextY += userComponent.getPreferredSize().height;
               }
            } else {
               mBar.setBounds(0, nextY, width, dimM.height);
               nextY += dimM.height;
            }
         }

         Container cPane = rootPane.getContentPane();
         if (cPane != null) {
            cPane.setBounds(0, nextY, width, height < nextY ? 0 : height - nextY);
         }

      }
   }
}
