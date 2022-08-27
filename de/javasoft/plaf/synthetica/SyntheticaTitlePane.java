package de.javasoft.plaf.synthetica;

import de.javasoft.plaf.synthetica.painter.SyntheticaPainter;
import de.javasoft.util.JavaVersion;
import de.javasoft.util.OS;
import de.javasoft.util.java2d.DropShadow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicRootPaneUI;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;

public class SyntheticaTitlePane extends JPanel {
   private static final long serialVersionUID = -6164225719742333375L;
   private static final String BUTTON_ID = "Synthetica.buttonID";
   private JRootPane rootPane;
   private SyntheticaRootPaneUI rootPaneUI;
   private Window window;
   private Frame frame;
   private Dialog dialog;
   private JButton menuButton;
   private JLabel titleLabel;
   private JLabel menuBarLabel;
   private Container userComponentContainer;
   private JButton toggleButton;
   private JButton iconifyButton;
   private JButton closeButton;
   private JPopupMenu systemMenu;
   private Action closeAction;
   private Action iconifyAction;
   private Action restoreAction;
   private Action maximizeAction;
   private WindowListener windowListener;
   private PropertyChangeListener propertyChangeListener;
   private PropertyChangeListener rootPanePropertyChangeListener;
   private ContainerListener layeredPaneContainerListener;
   private ActionListener menuActionListener;
   private boolean selected = true;
   private boolean useMACStyle = false;
   private SyntheticaTitlePane.LayoutStyle layoutStyle;
   private JInternalFrame iFrameDelegate = new JInternalFrame();

   public SyntheticaTitlePane(JRootPane var1, BasicRootPaneUI var2) {
      super();
      this.rootPane = var1;
      this.rootPaneUI = (SyntheticaRootPaneUI)var2;
      Container var3 = this.rootPane.getParent();
      this.window = var3 instanceof Window ? (Window)var3 : SwingUtilities.getWindowAncestor(var3);
      if (this.window instanceof Frame) {
         this.frame = (Frame)this.window;
      } else if (this.window instanceof Dialog) {
         this.dialog = (Dialog)this.window;
      }

      String var4 = SyntheticaLookAndFeel.getString("Synthetica.rootPane.titlePane.layoutStyle", this.window);
      this.layoutStyle = var4 == null ? SyntheticaTitlePane.LayoutStyle.REGULAR : SyntheticaTitlePane.LayoutStyle.valueOf(var4);
      this.useMACStyle = SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.useMACStyleOnMAC", this.window, true);
      this.setOpaque(SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.opaque", this.window, true));
      if (!this.isOpaque() && !SyntheticaLookAndFeel.isWindowOpacityEnabled(this.window)) {
         this.setDoubleBuffered(false);
      }

      this.setLayout(new GridBagLayout());
      this.setName("RootPane.titlePane");
      this.closeAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            SyntheticaTitlePane.this.close();
         }
      };
      int var5 = this.rootPane.getWindowDecorationStyle();
      boolean var6 = SyntheticaLookAndFeel.getBoolean("Synthetica.dialog.icon.enabled", this.window);
      if (var5 == 1 || var6) {
         this.iconifyAction = new AbstractAction() {
            public void actionPerformed(ActionEvent var1) {
               SyntheticaTitlePane.this.iconify();
            }
         };
         this.restoreAction = new AbstractAction() {
            public void actionPerformed(ActionEvent var1) {
               SyntheticaTitlePane.this.restore();
            }
         };
         this.maximizeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent var1) {
               SyntheticaTitlePane.this.maximize();
            }
         };
         this.menuButton = this.createTitlePaneButton("RootPane.titlePane.menuButton");
         this.menuButton.putClientProperty("doNotCancelPopup", new JComboBox().getClientProperty("doNotCancelPopup"));
         this.menuButton.setIcon(this.getIcon(this.rootPane));
         this.menuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent var1) {
               String var2 = System.getProperty("os.name").toLowerCase();
               if (var1.getClickCount() == 2 && var2.contains("windows")) {
                  SyntheticaTitlePane.this.closeAction.actionPerformed(new ActionEvent(var1.getSource(), var1.getID(), "doubleClick"));
               }

            }
         });
         this.systemMenu = new JPopupMenu();
         this.systemMenu.putClientProperty("Synthetica.popupMenu.toplevel", false);
         this.addMenuItems(this.systemMenu);
      }

      this.titleLabel = new JLabel(" ");
      this.titleLabel.setName("RootPane.title");
      this.titleLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.titleLabel.setFont(this.titleLabel.getFont().deriveFont(1));
      this.menuBarLabel = new JLabel(" ");
      this.menuBarLabel.setName("RootPane.menuBar");
      this.menuBarLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
      boolean var7 = this.showMenuBarInTitlePane();
      this.menuBarLabel.setVisible(var7);
      if (var7) {
         EventQueue.invokeLater(new Runnable() {
            public void run() {
               SyntheticaTitlePane.this.menuBarLabel.setPreferredSize(SyntheticaTitlePane.this.rootPane.getJMenuBar().getPreferredSize());
            }
         });
      }

      this.createButtons();
      this.updateLayoutStyle(var5, var6);
      this.installListeners(this.rootPane);
      this.setComponentsActiveState(this.window.isActive());
      this.updateState();
   }

   public void updateLayoutStyle() {
      int var1 = this.rootPane.getWindowDecorationStyle();
      boolean var2 = SyntheticaLookAndFeel.getBoolean("Synthetica.dialog.icon.enabled", this.window);
      this.updateLayoutStyle(var1, var2);
   }

   private void updateLayoutStyle(int var1, boolean var2) {
      if (this.layoutStyle == SyntheticaTitlePane.LayoutStyle.REGULAR) {
         this.addControls(var1, var2);
      } else if (this.layoutStyle == SyntheticaTitlePane.LayoutStyle.SECONDARYMENU) {
         this.menuBarLabel.setVisible(true);
         if (this.userComponentContainer == null) {
            this.userComponentContainer = new SyntheticaTitlePane.UserComponentContainer();
         }

         this.addControls_secondaryMenuLayout(var1, var2);
      }

   }

   private void addControls(int var1, boolean var2) {
      int var3 = this.getXGap();
      if (this.useMACStyle && OS.getCurrentOS() == OS.Mac) {
         this.add(this.closeButton, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, this.getCloseButtonAlignment(), 0, this.getCloseButtonInsets(var3), 0, 0));
         if (var1 == 1 && this.dialog == null) {
            this.add(
               this.iconifyButton, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0, this.getIconifyButtonAlignment(), 0, this.getIconifyButtonInsets(var3), 0, 0)
            );
            this.add(
               this.toggleButton, new GridBagConstraints(2, 0, 1, 2, 0.0, 0.0, this.getToggleButtonAlignment(), 0, this.getToggleButtonInsets(var3), 0, 0)
            );
         }

         this.add(this.titleLabel, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, this.getTitleLabelAlignment(), 2, this.getTitleLabelInsets(var3), 0, 0));
         this.add(this.menuBarLabel, new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0, 10, 2, this.getMenuBarLabelInsets(var3), 0, 0));
         if (var1 == 1 || var2) {
            this.add(this.menuButton, new GridBagConstraints(4, 0, 1, 2, 0.0, 0.0, this.getMenuButtonAlignment(), 0, this.getMenuButtonInsets(var3), 0, 0));
         }
      } else {
         if (var1 == 1 || var2) {
            this.add(this.menuButton, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, this.getMenuButtonAlignment(), 0, this.getMenuButtonInsets(var3), 0, 0));
         }

         this.add(this.titleLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, this.getTitleLabelAlignment(), 2, this.getTitleLabelInsets(var3), 0, 0));
         this.add(this.menuBarLabel, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, 10, 2, this.getMenuBarLabelInsets(var3), 0, 0));
         if (var1 == 1 && this.dialog == null) {
            this.add(
               this.iconifyButton, new GridBagConstraints(2, 0, 1, 2, 0.0, 0.0, this.getIconifyButtonAlignment(), 0, this.getIconifyButtonInsets(var3), 0, 0)
            );
            this.add(
               this.toggleButton, new GridBagConstraints(3, 0, 1, 2, 0.0, 0.0, this.getToggleButtonAlignment(), 0, this.getToggleButtonInsets(var3), 0, 0)
            );
         }

         this.add(this.closeButton, new GridBagConstraints(4, 0, 1, 2, 0.0, 0.0, this.getCloseButtonAlignment(), 0, this.getCloseButtonInsets(var3), 0, 0));
      }

   }

   private void addControls_secondaryMenuLayout(int var1, boolean var2) {
      int var3 = this.getXGap();
      if (this.useMACStyle && OS.getCurrentOS() == OS.Mac) {
         this.add(this.closeButton, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, this.getCloseButtonAlignment(), 0, this.getCloseButtonInsets(var3), 0, 0));
         if (var1 == 1 && this.dialog == null) {
            this.add(
               this.iconifyButton, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0, this.getIconifyButtonAlignment(), 0, this.getIconifyButtonInsets(var3), 0, 0)
            );
            this.add(
               this.toggleButton, new GridBagConstraints(2, 0, 1, 2, 0.0, 0.0, this.getToggleButtonAlignment(), 0, this.getToggleButtonInsets(var3), 0, 0)
            );
         }

         this.add(this.menuBarLabel, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, 10, 2, this.getMenuBarLabelInsets(var3), 0, 0));
         this.add(this.userComponentContainer, new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0, 10, 2, this.getUserComponentInsets(var3), 0, 0));
         if (var1 == 1 || var2) {
            this.add(this.menuButton, new GridBagConstraints(4, 0, 1, 2, 0.0, 0.0, this.getMenuButtonAlignment(), 0, this.getMenuButtonInsets(var3), 0, 0));
         }
      } else {
         if (var1 == 1 || var2) {
            this.add(this.menuButton, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, this.getMenuButtonAlignment(), 0, this.getMenuButtonInsets(var3), 0, 0));
         }

         this.add(this.menuBarLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, 10, 2, this.getMenuBarLabelInsets(var3), 0, 0));
         boolean var4 = var1 == 1 && this.dialog == null;
         this.add(this.userComponentContainer, new GridBagConstraints(1, 1, var4 ? 4 : 2, 1, 1.0, 1.0, 10, 2, this.getUserComponentInsets(var3), 0, 0));
         if (var4) {
            this.add(
               this.iconifyButton, new GridBagConstraints(2, 0, 1, 2, 0.0, 0.0, this.getIconifyButtonAlignment(), 0, this.getIconifyButtonInsets(var3), 0, 0)
            );
            this.add(
               this.toggleButton, new GridBagConstraints(3, 0, 1, 2, 0.0, 0.0, this.getToggleButtonAlignment(), 0, this.getToggleButtonInsets(var3), 0, 0)
            );
         }

         this.add(this.closeButton, new GridBagConstraints(4, 0, 1, 2, 0.0, 0.0, this.getCloseButtonAlignment(), 0, this.getCloseButtonInsets(var3), 0, 0));
      }

   }

   public void setUserComponent(JComponent var1) {
      if (this.userComponentContainer != null) {
         this.userComponentContainer.removeAll();
         this.userComponentContainer.add(var1);
      }

   }

   public JComponent getUserComponent() {
      return this.userComponentContainer != null && this.userComponentContainer.getComponentCount() != 0
         ? (JComponent)this.userComponentContainer.getComponent(0)
         : null;
   }

   public SyntheticaTitlePane.LayoutStyle getLayoutStyle() {
      return this.layoutStyle;
   }

   private void installListeners(JRootPane var1) {
      this.windowListener = new WindowAdapter() {
         public void windowOpened(WindowEvent var1) {
            if (SyntheticaTitlePane.this.rootPane.getWindowDecorationStyle() == 1 && SyntheticaTitlePane.this.dialog == null) {
               SyntheticaTitlePane.this.updateToggleButton();
               SyntheticaTitlePane.this.updateState();
            }

         }

         public void windowStateChanged(WindowEvent var1) {
            SyntheticaTitlePane.this.updateToggleButton();
            SyntheticaTitlePane.this.updateState();
         }

         public void windowActivated(WindowEvent var1) {
            SyntheticaTitlePane.this.setActive(true);
            SyntheticaTitlePane.this.selected = true;
         }

         public void windowDeactivated(WindowEvent var1) {
            SyntheticaTitlePane.this.setActive(false);
            SyntheticaTitlePane.this.selected = false;
         }
      };
      this.window.addWindowListener(this.windowListener);
      this.window.addWindowStateListener((WindowStateListener)this.windowListener);
      this.propertyChangeListener = new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            String var2 = var1.getPropertyName();
            if ("resizable".equals(var2)) {
               boolean var3 = var1.getNewValue();
               SyntheticaTitlePane.this.toggleButton.setEnabled(var3);
               SyntheticaTitlePane.this.systemMenu.removeAll();
               SyntheticaTitlePane.this.addMenuItems(SyntheticaTitlePane.this.systemMenu);
            } else if ("iconImage".equals(var2) && SyntheticaTitlePane.this.menuButton != null) {
               SyntheticaTitlePane.this.menuButton.setIcon(SyntheticaTitlePane.this.getIcon(SyntheticaTitlePane.this.rootPane));
            } else if ("title".equals(var2)) {
               SyntheticaTitlePane.this.repaint();
            } else if ("name".equals(var2)) {
               SyntheticaTitlePane.this.updateDefaults();
            }

         }
      };
      this.window.addPropertyChangeListener(this.propertyChangeListener);
      this.rootPanePropertyChangeListener = new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            if ("Synthetica.dialog.iconImage".equals(var1.getPropertyName()) && SyntheticaTitlePane.this.menuButton != null) {
               SyntheticaTitlePane.this.menuButton.setIcon(SyntheticaTitlePane.this.getIcon((JRootPane)var1.getSource()));
            }

         }
      };
      this.rootPane.addPropertyChangeListener(this.rootPanePropertyChangeListener);
      if (this.menuButton != null) {
         this.menuActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               if (SyntheticaTitlePane.this.systemMenu.isVisible()) {
                  SyntheticaTitlePane.this.systemMenu.setVisible(false);
               } else {
                  int var2 = SyntheticaTitlePane.this.getXGap();
                  int var3 = SyntheticaTitlePane.this.menuButton.getY()
                     + SyntheticaTitlePane.this.menuButton.getHeight()
                     + SyntheticaTitlePane.this.getTitleLabelInsets(var2).bottom;
                  boolean var4 = SyntheticaTitlePane.this.useMACStyle && OS.getCurrentOS() == OS.Mac;
                  if ((!SyntheticaTitlePane.this.isRTL() || var4) && (SyntheticaTitlePane.this.isRTL() || !var4)) {
                     SyntheticaTitlePane.this.systemMenu.show(SyntheticaTitlePane.this, SyntheticaTitlePane.this.menuButton.getX() - var2, var3);
                  } else {
                     SyntheticaTitlePane.this.systemMenu
                        .show(
                           SyntheticaTitlePane.this,
                           SyntheticaTitlePane.this.menuButton.getX()
                              + SyntheticaTitlePane.this.menuButton.getWidth()
                              - SyntheticaTitlePane.this.systemMenu.getPreferredSize().width
                              + var2,
                           var3
                        );
                  }

               }
            }
         };
         this.menuButton.addActionListener(this.menuActionListener);
      }

      this.layeredPaneContainerListener = new ContainerListener() {
         public void componentAdded(ContainerEvent var1) {
            if (var1.getChild() instanceof JMenuBar && SyntheticaTitlePane.this.showMenuBarInTitlePane()) {
               JMenuBar var2 = (JMenuBar)var1.getChild();
               SyntheticaTitlePane.this.menuBarLabel.setPreferredSize(var2.getPreferredSize());
               SyntheticaTitlePane.this.menuBarLabel.setMinimumSize(var2.getPreferredSize());
               SyntheticaTitlePane.this.menuBarLabel.setVisible(true);
               ((JLayeredPane)var1.getComponent()).setComponentZOrder(var2, 1);
               MouseInputListener var3 = SyntheticaTitlePane.this.rootPaneUI.getMouseInputListener();
               var2.addMouseListener(var3);
               var2.addMouseMotionListener(var3);
            }

         }

         public void componentRemoved(ContainerEvent var1) {
            if (var1.getChild() instanceof JMenuBar) {
               JMenuBar var2 = (JMenuBar)var1.getChild();
               MouseInputListener var3 = SyntheticaTitlePane.this.rootPaneUI.getMouseInputListener();
               var2.removeMouseListener(var3);
               var2.removeMouseMotionListener(var3);
               SyntheticaTitlePane.this.menuBarLabel.setVisible(false);
            }

         }
      };
      this.rootPane.getLayeredPane().addContainerListener(this.layeredPaneContainerListener);
   }

   void uninstallListeners(JRootPane var1) {
      this.window.removeWindowListener(this.windowListener);
      this.window.removeWindowStateListener((WindowStateListener)this.windowListener);
      this.window.removePropertyChangeListener(this.propertyChangeListener);
      var1.removePropertyChangeListener(this.rootPanePropertyChangeListener);
      var1.getLayeredPane().removeContainerListener(this.layeredPaneContainerListener);
      if (this.menuButton != null) {
         this.menuButton.removeActionListener(this.menuActionListener);
      }

   }

   private void updateDefaults() {
      this.setOpaque(SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.opaque", this.window, true));
      int var1 = this.getXGap();
      GridBagLayout var2 = (GridBagLayout)this.getLayout();
      GridBagConstraints var3 = var2.getConstraints(this.titleLabel);
      var3.insets = this.getTitleLabelInsets(var1);
      var2.setConstraints(this.titleLabel, var3);
      this.titleLabel.revalidate();
      var3 = var2.getConstraints(this.menuBarLabel);
      var3.insets = this.getMenuBarLabelInsets(var1);
      var2.setConstraints(this.menuBarLabel, var3);
      this.menuBarLabel.revalidate();
      if (this.menuButton != null) {
         this.menuButton.setIcon(this.getIcon(this.rootPane));
         var3 = var2.getConstraints(this.menuButton);
         var3.insets = this.getMenuButtonInsets(var1);
         var3.anchor = this.getMenuButtonAlignment();
         var2.setConstraints(this.menuButton, var3);
         this.menuButton.revalidate();
      }

      if (this.iconifyButton != null) {
         var3 = var2.getConstraints(this.iconifyButton);
         var3.insets = this.getIconifyButtonInsets(var1);
         var3.anchor = this.getIconifyButtonAlignment();
         var2.setConstraints(this.iconifyButton, var3);
         this.iconifyButton.revalidate();
      }

      if (this.toggleButton != null) {
         var3 = var2.getConstraints(this.toggleButton);
         var3.insets = this.getToggleButtonInsets(var1);
         var3.anchor = this.getToggleButtonAlignment();
         var2.setConstraints(this.toggleButton, var3);
         this.toggleButton.revalidate();
      }

      var3 = var2.getConstraints(this.closeButton);
      var3.insets = this.getCloseButtonInsets(var1);
      var3.anchor = this.getCloseButtonAlignment();
      var2.setConstraints(this.closeButton, var3);
      this.closeButton.revalidate();
   }

   boolean showMenuBarInTitlePane() {
      return this.rootPane.getJMenuBar() != null
         && SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.showMenuBarInTitlePane", this.window, false);
   }

   boolean clipMenuBarWidth() {
      return this.rootPane.getJMenuBar() != null && SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.clipMenuBarWidth", this.window, false);
   }

   private int getXGap() {
      return SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.gap", this.window, 4);
   }

   private Insets getTitleLabelInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.title.insets", this.window, new Insets(3, 0, 4, 0));
      return new Insets(var2.top, var2.left + var1, var2.bottom, var2.right + var1);
   }

   private Insets getMenuBarLabelInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.menuBar.insets", this.window, false);
      return new Insets(var2.top, var2.left + var1, var2.bottom, var2.right + var1);
   }

   private Insets getUserComponentInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.userComponent.insets", this.window, false);
      return new Insets(var2.top, var2.left + var1, var2.bottom, var2.right + var1);
   }

   private Insets getMenuButtonInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.menuButton.insets", this.window, false);
      return this.useMACStyle && OS.getCurrentOS() == OS.Mac
         ? new Insets(var2.top, var2.right, var2.bottom, var2.left + var1)
         : new Insets(var2.top, var2.left + var1, var2.bottom, var2.right);
   }

   private Insets getIconifyButtonInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.iconifyButton.insets", this.window, false);
      if (this.useMACStyle && OS.getCurrentOS() == OS.Mac) {
         int var4 = SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.iconifyButton.gap", this.window, 0);
         return new Insets(var2.top, var2.left + var4, var2.bottom, var2.right);
      } else {
         int var3 = SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.iconifyButton.gap", this.window, 0);
         return new Insets(var2.top, var2.left, var2.bottom, var2.right + var3);
      }
   }

   private Insets getToggleButtonInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.toggleButton.insets", this.window, false);
      if (this.useMACStyle && OS.getCurrentOS() == OS.Mac) {
         int var4 = SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.toggleButton.gap", this.window, 0);
         return new Insets(var2.top, var2.left + var4, var2.bottom, var2.right);
      } else {
         int var3 = SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.toggleButton.gap", this.window, var1);
         return new Insets(var2.top, var2.left, var2.bottom, var2.right + var3);
      }
   }

   private Insets getCloseButtonInsets(int var1) {
      Insets var2 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.closeButton.insets", this.window, false);
      return this.useMACStyle && OS.getCurrentOS() == OS.Mac
         ? new Insets(var2.top, var2.right + var1, var2.bottom, var2.left)
         : new Insets(var2.top, var2.left, var2.bottom, var2.right + var1);
   }

   private int getMenuButtonAlignment() {
      return SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.menuButton.alignment", this.window, 10);
   }

   private int getTitleLabelAlignment() {
      return SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.title.alignment", this.window, 10);
   }

   private int getIconifyButtonAlignment() {
      return SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.iconifyButton.alignment", this.window, 10);
   }

   private int getToggleButtonAlignment() {
      return SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.toggleButton.alignment", this.window, 10);
   }

   private int getCloseButtonAlignment() {
      return SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.closeButton.alignment", this.window, 10);
   }

   public JRootPane getRootPane() {
      return this.rootPane;
   }

   public Dimension getPreferredSize() {
      return this.isTitlePaneEnabled() ? super.getPreferredSize() : new Dimension(0, 0);
   }

   public Dimension getMinimumSize() {
      return this.isTitlePaneEnabled() ? super.getMinimumSize() : new Dimension(0, 0);
   }

   private boolean isTitlePaneEnabled() {
      boolean var1 = this.rootPane.getClientProperty("Synthetica.titlePane.enabled") == null
         ? false
         : !this.rootPane.getClientProperty("Synthetica.titlePane.enabled");
      String var2 = this.rootPane.getParent().getClass().getName() + ".titlePane.enabled";
      return (UIManager.get(var2) == null || UIManager.getBoolean(var2)) && !var1;
   }

   private String getTitle() {
      if (this.frame != null) {
         return this.frame.getTitle();
      } else {
         return this.dialog != null ? this.dialog.getTitle() : null;
      }
   }

   private Icon getFrameIcon() {
      Image var1 = this.frame != null ? this.frame.getIconImage() : null;
      Object var2 = null;
      Icon var5;
      if (var1 != null) {
         var5 = this.image2Icon(var1);
      } else {
         SynthStyle var3 = SynthLookAndFeel.getStyle(this.rootPane, Region.ROOT_PANE);
         SynthContext var4 = new SynthContext(this.rootPane, Region.ROOT_PANE, var3, 1024);
         var5 = var3.getIcon(var4, "RootPane.icon");
      }

      return var5;
   }

   private Icon getIcon(JRootPane var1) {
      Icon var2 = this.getFrameIcon();
      if (var1.getWindowDecorationStyle() == 1 || var1 == null) {
         return var2;
      } else if (var1.getClientProperty("Synthetica.dialog.iconImage") != null) {
         Image var5 = (Image)var1.getClientProperty("Synthetica.dialog.iconImage");
         return this.image2Icon(var5);
      } else if (this.window instanceof JDialog && !JavaVersion.JAVA5 && ((JDialog)this.window).getIconImages().size() > 0) {
         return this.image2Icon((Image)((JDialog)var1.getParent()).getIconImages().get(0));
      } else {
         if (this.window != null && this.window.getOwner() != null) {
            Window var3 = this.window.getOwner();
            if (var3 instanceof JFrame) {
               var1 = ((JFrame)var3).getRootPane();
            } else if (var3 instanceof JDialog) {
               var1 = ((JDialog)var3).getRootPane();
            } else {
               var1 = null;
            }

            if (var1 != null && var1.getUI() instanceof SyntheticaRootPaneUI && ((SyntheticaRootPaneUI)var1.getUI()).getTitlePane() != null) {
               return ((SyntheticaTitlePane)((SyntheticaRootPaneUI)var1.getUI()).getTitlePane()).getIcon(var1);
            }
         }

         return var2;
      }
   }

   private Icon image2Icon(Image var1) {
      return SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.menuButton.useOriginalImageSize", this.window)
         ? new ImageIcon(var1)
         : new ImageIcon(var1.getScaledInstance(16, 16, 4));
   }

   private void setActive(boolean var1) {
      this.setComponentsActiveState(var1);
      this.getRootPane().repaint();
   }

   private void setComponentsActiveState(boolean var1) {
      JInternalFrame var2 = this.iFrameDelegate;
      var2.setName(this.window.getName());
      SynthStyle var3 = SynthLookAndFeel.getStyleFactory().getStyle(var2, Region.INTERNAL_FRAME_TITLE_PANE);
      short var4 = 1024;
      if (var1) {
         var4 = 512;
      }

      SynthContext var5 = new SynthContext(var2, Region.INTERNAL_FRAME_TITLE_PANE, var3, var4);
      Font var6 = this.getFont();
      if (var6 == null || var6 instanceof UIResource) {
         var6 = var3.getFont(var5);
      }

      float var7 = (float)SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.title.fontSize", this.window);
      if (var7 == 0.0F) {
         var7 = (float)var6.getSize();
      } else {
         var7 = SyntheticaLookAndFeel.scaleFontSize(var7);
      }

      var6 = var6.deriveFont(var6.getStyle(), var7);
      this.titleLabel.setFont(var6);
      Color var8 = this.getForeground();
      if (var8 == null || var8 instanceof UIResource) {
         var8 = var3.getColor(var5, ColorType.FOREGROUND);
      }

      var8 = new Color(var8.getRGB());
      this.titleLabel.setForeground(var8);
      this.closeButton.putClientProperty("paintActive", var1);
      if (this.rootPane.getWindowDecorationStyle() == 1 && this.dialog == null) {
         this.iconifyButton.putClientProperty("paintActive", var1);
         this.toggleButton.putClientProperty("paintActive", var1);
      }

      this.updateToggleButton();
      if (this.iconifyButton != null) {
         this.iconifyButton
            .setIcon(
               var1
                  ? (Icon)this.iconifyButton.getClientProperty("Synthetica.iconifyIcon")
                  : (Icon)this.iconifyButton.getClientProperty("Synthetica.iconifyIcon.inactive")
            );
      }

      if (this.closeButton != null) {
         this.closeButton
            .setIcon(
               var1
                  ? (Icon)this.closeButton.getClientProperty("Synthetica.closeIcon")
                  : (Icon)this.closeButton.getClientProperty("Synthetica.closeIcon.inactive")
            );
      }

   }

   private boolean isFrameResizable() {
      return this.frame != null && this.frame.isResizable();
   }

   private boolean isFrameMaximized() {
      return this.frame != null && (this.frame.getExtendedState() & 6) == 6;
   }

   private void addMenuItems(JPopupMenu var1) {
      JMenuItem var2 = var1.add(this.restoreAction);
      var2.setText(SyntheticaLookAndFeel.getString("InternalFrameTitlePane.restoreButtonText", this.window));
      var2.setMnemonic('R');
      var2.setEnabled(this.isFrameResizable());
      var2 = var1.add(this.iconifyAction);
      var2.setText(SyntheticaLookAndFeel.getString("InternalFrameTitlePane.minimizeButtonText", this.window));
      var2.setMnemonic('n');
      var2.setEnabled(this.frame != null);
      if (Toolkit.getDefaultToolkit().isFrameStateSupported(6)) {
         var2 = var1.add(this.maximizeAction);
         var2.setText(SyntheticaLookAndFeel.getString("InternalFrameTitlePane.maximizeButtonText", this.window));
         var2.setMnemonic('x');
         var2.setEnabled(this.isFrameResizable());
      }

      var1.addSeparator();
      var2 = var1.add(this.closeAction);
      var2.setText(SyntheticaLookAndFeel.getString("InternalFrameTitlePane.closeButtonText", this.window));
      var2.setMnemonic('C');
      if (this.closeButton != null) {
         var2.setEnabled(this.closeButton.isVisible() & this.closeButton.isEnabled());
      }

   }

   private JButton createTitlePaneButton(String var1) {
      JButton var2 = new JButton();
      var2.setName(var1);
      var2.setFocusPainted(false);
      var2.setFocusable(false);
      var2.setOpaque(false);
      var2.setBorder(BorderFactory.createEmptyBorder());
      return var2;
   }

   private void createButtons() {
      MouseAdapter var1 = new MouseAdapter() {
         public void mouseEntered(MouseEvent var1) {
            JButton var2 = (JButton)var1.getSource();
            String var3 = "Synthetica." + var2.getClientProperty("Synthetica.buttonID") + "Icon.hover";
            var2.setIcon((Icon)var2.getClientProperty(var3));
         }

         public void mouseExited(MouseEvent var1) {
            JButton var2 = (JButton)var1.getSource();
            String var3 = "Synthetica." + var2.getClientProperty("Synthetica.buttonID") + "Icon";
            if (!SyntheticaTitlePane.this.window.isActive()) {
               var3 = var3 + ".inactive";
            }

            var2.setIcon((Icon)var2.getClientProperty(var3));
         }
      };
      SynthStyle var2 = SynthLookAndFeel.getStyle(this.rootPane, Region.ROOT_PANE);
      SynthContext var3 = new SynthContext(this.rootPane, Region.ROOT_PANE, var2, 1024);
      Icon var4 = var2.getIcon(var3, "RootPane.closeIcon");
      Icon var5 = var2.getIcon(var3, "RootPane.iconifyIcon");
      Icon var6 = var2.getIcon(var3, "RootPane.maximizeIcon");
      Icon var7 = var2.getIcon(var3, "RootPane.minimizeIcon");
      Icon var8 = var2.getIcon(var3, "RootPane.closeIcon.inactive") == null ? var4 : var2.getIcon(var3, "RootPane.closeIcon.inactive");
      Icon var9 = var2.getIcon(var3, "RootPane.iconifyIcon.inactive") == null ? var5 : var2.getIcon(var3, "RootPane.iconifyIcon.inactive");
      Icon var10 = var2.getIcon(var3, "RootPane.maximizeIcon.inactive") == null ? var6 : var2.getIcon(var3, "RootPane.maximizeIcon.inactive");
      Icon var11 = var2.getIcon(var3, "RootPane.minimizeIcon.inactive") == null ? var7 : var2.getIcon(var3, "RootPane.minimizeIcon.inactive");
      var3 = new SynthContext(this.rootPane, Region.ROOT_PANE, var2, 2);
      Icon var12 = var2.getIcon(var3, "RootPane.closeIcon");
      Icon var13 = var2.getIcon(var3, "RootPane.iconifyIcon");
      Icon var14 = var2.getIcon(var3, "RootPane.maximizeIcon");
      Icon var15 = var2.getIcon(var3, "RootPane.minimizeIcon");
      var3 = new SynthContext(this.rootPane, Region.ROOT_PANE, var2, 4);
      Icon var16 = var2.getIcon(var3, "RootPane.closeIcon") == var4 ? null : var2.getIcon(var3, "RootPane.closeIcon");
      Icon var17 = var2.getIcon(var3, "RootPane.iconifyIcon") == var5 ? null : var2.getIcon(var3, "RootPane.iconifyIcon");
      Icon var18 = var2.getIcon(var3, "RootPane.maximizeIcon") == var6 ? null : var2.getIcon(var3, "RootPane.maximizeIcon");
      Icon var19 = var2.getIcon(var3, "RootPane.minimizeIcon") == var7 ? null : var2.getIcon(var3, "RootPane.minimizeIcon");
      this.closeButton = this.createTitlePaneButton("RootPane.titlePane.closeButton");
      this.closeButton.putClientProperty("Synthetica.buttonID", "close");
      this.closeButton.putClientProperty("Synthetica.closeIcon", var4);
      this.closeButton.putClientProperty("Synthetica.closeIcon.hover", var12);
      this.closeButton.putClientProperty("Synthetica.closeIcon.inactive", var8);
      this.closeButton.setAction(this.closeAction);
      this.closeButton.getAccessibleContext().setAccessibleName("Close");
      this.closeButton.setIcon(var4);
      this.closeButton.setPressedIcon(var16);
      this.closeButton.addMouseListener(var1);
      SyntheticaTitlePane.CloseButtonStateListener var20 = new SyntheticaTitlePane.CloseButtonStateListener(null);
      this.closeButton.addPropertyChangeListener(var20);
      this.closeButton.addComponentListener(var20);
      if (this.rootPane.getWindowDecorationStyle() == 1 && this.dialog == null) {
         this.iconifyButton = this.createTitlePaneButton("RootPane.titlePane.iconifyButton");
         this.iconifyButton.putClientProperty("Synthetica.buttonID", "iconify");
         this.iconifyButton.putClientProperty("Synthetica.iconifyIcon", var5);
         this.iconifyButton.putClientProperty("Synthetica.iconifyIcon.hover", var13);
         this.iconifyButton.putClientProperty("Synthetica.iconifyIcon.inactive", var9);
         this.iconifyButton.setAction(this.iconifyAction);
         this.iconifyButton.getAccessibleContext().setAccessibleName("Iconify");
         this.iconifyButton.setIcon(var5);
         this.iconifyButton.setPressedIcon(var17);
         this.iconifyButton.addMouseListener(var1);
         this.toggleButton = this.createTitlePaneButton("RootPane.titlePane.toggleButton");
         this.toggleButton.putClientProperty("Synthetica.maximizeIcon", var6);
         this.toggleButton.putClientProperty("Synthetica.maximizeIcon.hover", var14);
         this.toggleButton.putClientProperty("Synthetica.maximizeIcon.pressed", var18);
         this.toggleButton.putClientProperty("Synthetica.maximizeIcon.inactive", var10);
         this.toggleButton.putClientProperty("Synthetica.minimizeIcon", var7);
         this.toggleButton.putClientProperty("Synthetica.minimizeIcon.hover", var15);
         this.toggleButton.putClientProperty("Synthetica.minimizeIcon.pressed", var19);
         this.toggleButton.putClientProperty("Synthetica.minimizeIcon.inactive", var11);
         this.updateToggleButton();
         this.toggleButton.addMouseListener(var1);
      }

   }

   private void updateToggleButton() {
      if (this.toggleButton != null) {
         boolean var1 = this.window.isActive();
         Object var2 = null;
         Object var3 = null;
         Icon var4;
         Icon var5;
         if (!this.isFrameMaximized()) {
            this.toggleButton.setAction(this.maximizeAction);
            this.toggleButton.getAccessibleContext().setAccessibleName("Maximize");
            this.toggleButton.putClientProperty("Synthetica.buttonID", "maximize");
            if (!var1) {
               var4 = (Icon)this.toggleButton.getClientProperty("Synthetica.maximizeIcon.inactive");
            } else {
               var4 = (Icon)this.toggleButton.getClientProperty("Synthetica.maximizeIcon");
            }

            var5 = (Icon)this.toggleButton.getClientProperty("Synthetica.maximizeIcon.pressed");
         } else {
            this.toggleButton.setAction(this.restoreAction);
            this.toggleButton.getAccessibleContext().setAccessibleName("Restore");
            this.toggleButton.putClientProperty("Synthetica.buttonID", "minimize");
            if (!var1) {
               var4 = (Icon)this.toggleButton.getClientProperty("Synthetica.minimizeIcon.inactive");
            } else {
               var4 = (Icon)this.toggleButton.getClientProperty("Synthetica.minimizeIcon");
            }

            var5 = (Icon)this.toggleButton.getClientProperty("Synthetica.minimizeIcon.pressed");
         }

         this.toggleButton.setIcon(var4);
         this.toggleButton.setPressedIcon(var5);
         this.toggleButton.setEnabled(this.isFrameResizable());
      }
   }

   public void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      SynthContext var2 = SyntheticaLookAndFeel.createContext(this.rootPane, Region.ROOT_PANE, this.selected ? 512 : 0);
      ((SyntheticaPainter)SyntheticaPainter.getInstance()).paintRootPaneTitlePaneBackground(var2, var1, 0, 0, this.getWidth(), this.getHeight());
      Rectangle var3 = this.getControlButtonsBounds();
      if (var3.width > 0 && var3.height > 0) {
         ((SyntheticaPainter)SyntheticaPainter.getInstance()).paintRootPaneButtonAreaBackground(var2, var1, var3.x, var3.y, var3.width, var3.height);
      }

      String var4 = this.getTitle();
      if (var4 != null && var4.length() != 0) {
         GridBagLayout var5 = (GridBagLayout)this.getLayout();
         Insets var6 = var5.getConstraints(this.titleLabel).insets;
         FontMetrics var7 = this.getFontMetrics(this.titleLabel.getFont());
         boolean var8 = OS.getCurrentOS() == OS.Mac && this.useMACStyle;
         boolean var9 = SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.title.center", this.window) || var8;
         int var10 = this.getXGap();
         Rectangle var11 = this.getBounds();
         Rectangle var12 = this.getMenuButtonBounds();
         if (this.menuButton != null) {
            var12.width += var5.getConstraints(this.menuButton).insets.right;
         }

         Rectangle var13 = this.getControlButtonsBounds();
         SynthStyle var14 = SynthLookAndFeel.getStyle(this.iFrameDelegate, Region.INTERNAL_FRAME_TITLE_PANE);
         short var15 = 1024;
         if (this.selected) {
            var15 = 512;
         }

         var2 = new SynthContext(this.iFrameDelegate, Region.INTERNAL_FRAME_TITLE_PANE, var14, var15);
         var11.width -= var12.width + var13.width + var10 * 2 + var6.left + var6.right;
         String var16 = var14.getGraphicsUtils(var2).layoutText(var2, var7, var4, null, 0, 0, 0, 0, var11, new Rectangle(0, 0), new Rectangle(0, 0), 0);
         boolean var17 = this.isRTL();
         int var18 = var7.stringWidth(var4);
         int var19 = var7.getHeight();
         int var20 = this.getTitleLabelAlignment() == 10 ? (this.getHeight() - var19 + var6.top - var6.bottom) / 2 : this.titleLabel.getY();
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.showMenuBarInTitlePane", this.window, false)) {
            var20 = this.titleLabel.getY() + this.titleLabel.getInsets().top;
         }

         int var21 = !var8 && !var17 ? var12.x + var12.width + var6.left : var13.x + var13.width + var6.right;
         int var22 = !var8 && !var17 ? var13.x - var18 - var6.right : var12.x - var18 - var6.left;
         var22 = Math.max(var21, var22);
         int var23 = var17 ? var22 : var21;
         var23 = var9
            ? (var17 ? Math.min(var23, this.getWidth() / 2 - var7.stringWidth(var4) / 2) : Math.max(var23, this.getWidth() / 2 - var7.stringWidth(var4) / 2))
            : var23;
         if (!var8) {
            var23 = var17 ? Math.min(var23, var22) : Math.max(var23, var21);
            var23 = var17 ? Math.max(var23, var21) : Math.min(var23, var22);
         }

         boolean var24 = SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.title.visible", this.window, true);
         boolean var25 = this.rootPane.getClientProperty("Synthetica.logoRenderer") != null;
         if (var24
            && (
               !var25
                  || (!var25 || this.rootPane.getClientProperty("Synthetica.paintTitle") != null) && this.rootPane.getClientProperty("Synthetica.paintTitle")
            )) {
            if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.dropShadow", this.window) && this.selected) {
               BufferedImage var26 = new BufferedImage(var18, var19, 2);
               Graphics2D var27 = var26.createGraphics();
               var27.setFont(this.titleLabel.getFont());
               var27.drawString(var16, 0, var7.getAscent());
               var27.dispose();
               DropShadow var28 = new DropShadow(var26);
               var28.setDistance(SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.dropShadow.distance", this.window, -5));
               var28.setShadowColor(SyntheticaLookAndFeel.getColor("Synthetica.rootPane.titlePane.dropShadow.color", this.window, var28.getShadowColor()));
               var28.setQuality(SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.dropShadow.highQuality", this.window, var28.getHighQuality()));
               var28.setShadowOpacity(
                  (float)SyntheticaLookAndFeel.getInt(
                        "Synthetica.rootPane.titlePane.dropShadow.opacity", this.window, (int)(var28.getShadowOpacity() * 100.0F)
                     )
                     / 100.0F
               );
               var28.setShadowSize(SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.dropShadow.size", this.window, var28.getShadowSize()));
               int var29 = SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.dropShadow.xOffset", this.window, 0);
               int var30 = SyntheticaLookAndFeel.getInt("Synthetica.rootPane.titlePane.dropShadow.yOffset", this.window, 0);
               var28.paintShadow(var1, var23 + var29, var20 + var30);
            }

            var1.setFont(this.titleLabel.getFont());
            if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.title.etchedTop", this.window)) {
               var1.setColor(Color.BLACK);
               var14.getGraphicsUtils(var2).paintText(var2, var1, var16, var23, var20 - 1, -2);
            }

            if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.title.etchedBottom", this.window)) {
               var1.setColor(Color.WHITE);
               var14.getGraphicsUtils(var2).paintText(var2, var1, var16, var23, var20 + 1, -2);
            }

            var1.setColor(var14.getColor(var2, ColorType.FOREGROUND));
            var14.getGraphicsUtils(var2).paintText(var2, var1, var16, var23, var20, -2);
         }

      }
   }

   public Rectangle getControlButtonsBounds() {
      Rectangle var1 = this.closeButton.getBounds();
      if (this.useMACStyle && OS.getCurrentOS() == OS.Mac) {
         if (this.toggleButton != null) {
            var1.width = this.toggleButton.getBounds().x + this.toggleButton.getBounds().width - var1.x;
         }
      } else if (this.iconifyButton != null) {
         Rectangle var2 = this.iconifyButton.getBounds();
         if (this.isRTL()) {
            var1.width = var2.x + var2.width - var1.x;
         } else {
            var1.width = var1.x + var1.width - var2.x;
            var1.x = var2.x;
         }
      }

      return var1;
   }

   public Rectangle getMenuButtonBounds() {
      Rectangle var1 = this.menuButton == null ? new Rectangle(0, 0, 0, 0) : this.menuButton.getBounds();
      if (this.menuButton == null) {
         var1.x = this.isRTL() ? this.getBounds().width - 1 : 0;
         var1.y = this.getBounds().y;
      }

      return var1;
   }

   private void close() {
      this.window.dispatchEvent(new WindowEvent(this.window, 201));
   }

   private void iconify() {
      int var1 = this.frame.getExtendedState();
      this.frame.setExtendedState(var1 | 1);
      this.updateState();
   }

   void maximize() {
      int var1 = this.frame.getExtendedState();
      this.rootPaneUI.setMaximizedBounds(this.frame);
      this.frame.setExtendedState(var1 | 6);
      this.updateState();
   }

   void restore() {
      int var1 = this.frame.getExtendedState();
      if ((var1 & 1) == 1) {
         this.frame.setExtendedState(var1 ^ 1);
      } else if ((var1 & 6) == 6) {
         this.frame.setExtendedState(var1 ^ 6);
      }

      this.updateState();
   }

   private void updateState() {
      if (this.frame != null) {
         if (!this.isFrameResizable()) {
            this.restoreAction.setEnabled(false);
            this.maximizeAction.setEnabled(false);
            this.iconifyAction.setEnabled(true);
         } else {
            switch(this.frame.getExtendedState()) {
               case 1:
                  this.restoreAction.setEnabled(true);
                  this.maximizeAction.setEnabled(true);
                  this.iconifyAction.setEnabled(false);
                  break;
               case 6:
                  this.restoreAction.setEnabled(true);
                  this.maximizeAction.setEnabled(false);
                  this.iconifyAction.setEnabled(true);
                  break;
               default:
                  this.restoreAction.setEnabled(false);
                  this.maximizeAction.setEnabled(true);
                  this.iconifyAction.setEnabled(true);
            }

         }
      }
   }

   public ComponentOrientation getComponentOrientation() {
      return this.window == null ? this.getRootPane().getComponentOrientation() : this.window.getComponentOrientation();
   }

   private boolean isRTL() {
      return !this.getComponentOrientation().isLeftToRight();
   }

   private class CloseButtonStateListener implements PropertyChangeListener, ComponentListener {
      private CloseButtonStateListener() {
         super();
      }

      public void componentResized(ComponentEvent var1) {
      }

      public void componentMoved(ComponentEvent var1) {
      }

      public void componentShown(ComponentEvent var1) {
         this.updateSystemMenu();
      }

      public void componentHidden(ComponentEvent var1) {
         this.updateSystemMenu();
      }

      public void propertyChange(PropertyChangeEvent var1) {
         String var2 = var1.getPropertyName();
         if ("enabled".equals(var2)) {
            this.updateSystemMenu();
         }

      }

      private void updateSystemMenu() {
         if (SyntheticaTitlePane.this.systemMenu != null) {
            SyntheticaTitlePane.this.systemMenu.removeAll();
            SyntheticaTitlePane.this.addMenuItems(SyntheticaTitlePane.this.systemMenu);
         }

      }
   }

   public static enum LayoutStyle {
      REGULAR,
      SECONDARYMENU;

      private LayoutStyle() {
      }
   }

   private static class UserComponentContainer extends JComponent {
      public UserComponentContainer() {
         super();
         this.setLayout(new BorderLayout());
      }
   }
}
