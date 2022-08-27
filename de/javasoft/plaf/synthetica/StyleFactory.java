package de.javasoft.plaf.synthetica;

import de.javasoft.plaf.synthetica.styles.CheckBoxStyle;
import de.javasoft.plaf.synthetica.styles.ComboBoxStyle;
import de.javasoft.plaf.synthetica.styles.FormattedTextFieldStyle;
import de.javasoft.plaf.synthetica.styles.LabelStyle;
import de.javasoft.plaf.synthetica.styles.ListStyle;
import de.javasoft.plaf.synthetica.styles.MenuItemStyle;
import de.javasoft.plaf.synthetica.styles.MenuStyle;
import de.javasoft.plaf.synthetica.styles.PopupMenuStyle;
import de.javasoft.plaf.synthetica.styles.RadioButtonStyle;
import de.javasoft.plaf.synthetica.styles.ScrollPaneStyle;
import de.javasoft.plaf.synthetica.styles.SpinnerStyle;
import de.javasoft.plaf.synthetica.styles.TabbedPaneStyle;
import de.javasoft.plaf.synthetica.styles.TableStyle;
import de.javasoft.plaf.synthetica.styles.TextFieldStyle;
import de.javasoft.plaf.synthetica.styles.ToolBarButtonStyle;
import de.javasoft.plaf.synthetica.styles.ToolBarSeparatorStyle;
import de.javasoft.plaf.synthetica.styles.ToolBarStyle;
import de.javasoft.plaf.synthetica.styles.ToolTipStyle;
import de.javasoft.plaf.synthetica.styles.ViewportStyle;
import de.javasoft.util.JavaVersion;
import de.javasoft.util.OS;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.AccessControlException;
import java.util.Date;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DesktopManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.JInternalFrame.JDesktopIcon;
import javax.swing.JToolBar.Separator;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;
import javax.swing.plaf.synth.SynthStyleFactory;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreeCellRenderer;
import sun.swing.DefaultLookup;
import sun.swing.plaf.synth.DefaultSynthStyle;

public class StyleFactory extends SynthStyleFactory {
   private SynthStyleFactory synthStyleFactory;
   private SyntheticaStyleFactory syntheticaStyleFactory;
   private PropertyChangeListener styleUpdater;
   private Boolean styleNameSupportEnabled;
   private StyleFactory.ComponentPropertyStore componentPropertyStore;
   private boolean prepareMetalLAFSwitch = false;
   private StyleFactory.CellRendererHoverListener cellRendererHoverListener;
   private static boolean menuToolTipEnabled;
   private static boolean menuItemToolTipEnabled;
   private static boolean componentPropertyStoreEnabled;
   private static int cleanerThreadDelay;

   static {
      try {
         String var0 = System.getProperty("synthetica.componentPropertyStoreEnabled");
         componentPropertyStoreEnabled = var0 == null ? true : Boolean.parseBoolean(var0);
      } catch (AccessControlException var2) {
         componentPropertyStoreEnabled = true;
      }

      try {
         String var3 = System.getProperty("synthetica.cleanerThreadDelay");
         cleanerThreadDelay = var3 == null ? 30000 : Integer.parseInt(var3);
      } catch (AccessControlException var1) {
         cleanerThreadDelay = 30000;
      }

   }

   public StyleFactory(SynthStyleFactory var1) {
      super();
      this.synthStyleFactory = var1;
      this.componentPropertyStore = new StyleFactory.ComponentPropertyStore();
   }

   void uninitialize() {
      this.componentPropertyStore.enabled = false;
      this.restoreAllComponentProperties();
      this.componentPropertyStore.stop();
   }

   public StyleFactory.ComponentPropertyStore getComponentPropertyStore() {
      return this.componentPropertyStore;
   }

   public SynthStyle getStyle(JComponent var1, Region var2) {
      String var3 = SyntheticaLookAndFeel.getStyleName(var1);
      this.installStyleUpdater(var1);
      Object var4 = null;
      SynthStyle var10;
      if (var3 != null
         && (
            var2.equals(Region.TABBED_PANE_CONTENT)
               || var2.equals(Region.TABBED_PANE_TAB)
               || var2.equals(Region.TABBED_PANE_TAB_AREA)
               || var2.equals(Region.INTERNAL_FRAME_TITLE_PANE)
         )) {
         PropertyChangeListener[] var5 = var1.getPropertyChangeListeners();

         for(PropertyChangeListener var6 : var5) {
            var1.removePropertyChangeListener(var6);
         }

         var1.setName(var2.getName() + "." + var3);
         if (var1.getClientProperty("Synthetica.style") != null) {
            var1.putClientProperty("Synthetica.style", var2.getName() + "." + var3);
         }

         var10 = this.getStyle(var1, var2, this.getSynthStyle(var1, var2));
         var1.setName(var3);
         if (var1.getClientProperty("Synthetica.style") != null) {
            var1.putClientProperty("Synthetica.style", var3);
         }

         for(PropertyChangeListener var11 : var5) {
            var1.addPropertyChangeListener(var11);
         }
      } else {
         var10 = this.getStyle(var1, var2, this.getSynthStyle(var1, var2));
      }

      return var10;
   }

   private void installStyleUpdater(JComponent var1) {
      if (this.isSyntheticaStyleNameSupportEnabled() && JavaVersion.JAVA6 && !this.eventListenerExists(this.styleUpdater, var1.getPropertyChangeListeners())) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
         var1.addPropertyChangeListener(this.styleUpdater);
      }

   }

   private SynthStyle getSynthStyle(JComponent var1, Region var2) {
      if (this.isSyntheticaStyleNameSupportEnabled()) {
         if (this.syntheticaStyleFactory == null) {
            this.syntheticaStyleFactory = new SyntheticaStyleFactory(this.synthStyleFactory);
         }

         return this.syntheticaStyleFactory.getStyle(var1, var2);
      } else {
         return this.synthStyleFactory.getStyle(var1, var2);
      }
   }

   boolean isSyntheticaStyleNameSupportEnabled() {
      if (this.styleNameSupportEnabled == null) {
         this.styleNameSupportEnabled = false;
         if (JavaVersion.JAVA6U10_OR_ABOVE && SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
            this.styleNameSupportEnabled = SyntheticaLookAndFeel.getBoolean("Synthetica.styleNameSupportEnabled", null, true);
            if (JavaVersion.JAVA6) {
               this.styleUpdater = new PropertyChangeListener() {
                  public void propertyChange(PropertyChangeEvent var1) {
                     if ("Synthetica.style".equals(var1.getPropertyName())) {
                        JComponent var2 = (JComponent)var1.getSource();
                        var2.putClientProperty("Nimbus.Overrides", "" + var1.getNewValue());
                     }

                  }
               };
            }
         }
      }

      return this.styleNameSupportEnabled;
   }

   private SynthStyle getStyle(JComponent var1, Region var2, SynthStyle var3) {
      String var4 = var1.getName();
      if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         if (SyntheticaLookAndFeel.getFont() != null) {
            boolean var5 = !SyntheticaLookAndFeel.getBoolean("Synthetica.font.disabled", var1)
               & SyntheticaLookAndFeel.getBoolean("Synthetica.font.enabled", var1, true);
            if (var5) {
               Font var6 = ((DefaultSynthStyle)var3).getStateInfo(0).getFont();
               Font var7 = SyntheticaLookAndFeel.getFont();
               FontUIResource var40 = new FontUIResource(var7.deriveFont(var6 == null ? 0 : var6.getStyle()));
               ((DefaultSynthStyle)var3).getStateInfo(0).setFont(var40);
               if (var1.getBorder() instanceof TitledBorder) {
                  ((TitledBorder)var1.getBorder()).setTitleFont(var40);
               }
            }
         } else if (SyntheticaLookAndFeel.getFont() == null) {
            Font var10 = ((DefaultSynthStyle)var3).getStateInfo(0).getFont();
            if (var10 == null) {
               var10 = new FontUIResource("Tahoma", 0, 11);
            } else {
               var10 = var10.deriveFont(0);
            }

            String var29 = UIManager.getString("Synthetica.font.resource");
            if (var29 != null) {
               try {
                  float var41 = (float)var10.getSize();
                  var10 = Font.createFont(0, this.getClass().getResourceAsStream(var29));
                  var10 = var10.deriveFont(var41);
               } catch (Exception var9) {
                  throw new RuntimeException(var9);
               }
            }

            SyntheticaLookAndFeel.setFont(var10, true);
         }
      }

      if (var4 != null
         && (var4.startsWith("ComboBox.arrowButton") || var4.startsWith("ComboBox.textField"))
         && SyntheticaLookAndFeel.getBoolean("Synthetica.comboBox.hoverAndPressed.enabled", var1)
         && !this.eventListenerExists(var1.getMouseListeners())) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
         var1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent var1) {
               Component var2 = (Component)var1.getSource();
               JComponent var3 = (JComponent)var2.getParent();
               var3.putClientProperty("Synthetica.MOUSE_OVER", true);
               var3.dispatchEvent(var1);
            }

            public void mouseExited(MouseEvent var1) {
               Component var2 = (Component)var1.getSource();
               JComponent var3 = (JComponent)var2.getParent();
               var3.putClientProperty("Synthetica.MOUSE_OVER", false);
               var3.dispatchEvent(var1);
            }

            public void mousePressed(MouseEvent var1) {
               Component var2 = (Component)var1.getSource();
               JComponent var3 = (JComponent)var2.getParent();
               if (SyntheticaLookAndFeel.getBoolean("Synthetica.comboBox.hoverAndPressed.enabled", var3)) {
                  var3.repaint();
               }

            }

            public void mouseReleased(MouseEvent var1) {
               Component var2 = (Component)var1.getSource();
               JComponent var3 = (JComponent)var2.getParent();
               if (SyntheticaLookAndFeel.getBoolean("Synthetica.comboBox.hoverAndPressed.enabled", var3)) {
                  var3.repaint();
               }

            }
         });
      }

      if (var4 != null
         && (var4.startsWith("Spinner.nextButton") || var4.startsWith("Spinner.previousButton") || var4.startsWith("Spinner.formattedTextField"))
         && SyntheticaLookAndFeel.getBoolean("Synthetica.spinner.hoverAndPressed.enabled", var1)
         && !this.eventListenerExists(var1.getMouseListeners())) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
         var1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent var1) {
               JSpinner var2 = this.getSpinner(var1);
               var2.putClientProperty("Synthetica.MOUSE_OVER", true);
               var2.dispatchEvent(var1);
            }

            public void mouseExited(MouseEvent var1) {
               JSpinner var2 = this.getSpinner(var1);
               var2.putClientProperty("Synthetica.MOUSE_OVER", false);
               var2.dispatchEvent(var1);
            }

            public void mousePressed(MouseEvent var1) {
            }

            public void mouseReleased(MouseEvent var1) {
            }

            private JSpinner getSpinner(MouseEvent var1) {
               Component var2 = (Component)var1.getSource();
               String var3 = var2.getName();
               return var3 != null && var3.startsWith("Spinner.formattedTextField") ? (JSpinner)var2.getParent().getParent() : (JSpinner)var2.getParent();
            }
         });
      }

      if (var2 == Region.ARROW_BUTTON) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         if (var4 != null && var4.startsWith("Spinner.")) {
            int var13 = SyntheticaLookAndFeel.getInt("Synthetica.spinner.arrowButton.width", var1);
            if (var13 > 0 && var1.getPreferredSize() != null) {
               var1.setPreferredSize(new Dimension(var13, var1.getPreferredSize().height));
            }
         } else if (var4 != null
            && var4.startsWith("ScrollBar.button")
            && SyntheticaLookAndFeel.getBoolean("Synthetica.scrollBarTrack.hoverOnButtons.enabled", var1)
            && !this.eventListenerExists(var1.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            var1.addMouseListener(new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  JButton var2 = (JButton)var1.getSource();
                  var2.getParent().repaint();
               }

               public void mouseExited(MouseEvent var1) {
                  JButton var2 = (JButton)var1.getSource();
                  var2.getParent().repaint();
               }
            });
         }
      } else if (var2 == Region.BUTTON) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_BUTTON_MARGIN");
         if (var1.getParent() instanceof JToolBar) {
            var3 = ToolBarButtonStyle.getStyle(var3, var1, var2);
         }

         LookAndFeel.installProperty(var1, "iconTextGap", SyntheticaLookAndFeel.getInt("Synthetica.button.iconTextGap", var1, 4));
         if (var4 != null && var4.startsWith("InternalFrameTitlePane.") && OS.getCurrentOS() == OS.Mac) {
            var1.putClientProperty("Synthetica.popupType", 2);
         }
      } else if (var2 == Region.INTERNAL_FRAME_TITLE_PANE) {
         if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(
               new PropertyChangeListener() {
                  public void propertyChange(PropertyChangeEvent var1) {
                     if ("ancestor".equals(var1.getPropertyName())) {
                        final JComponent var2 = (JComponent)var1.getSource();
                        if (JavaVersion.JAVA5 && var2 instanceof BasicInternalFrameTitlePane) {
                           boolean var3 = SyntheticaLookAndFeel.getBoolean("Synthetica.internalFrameTitlePane.java5LayoutManager.enabled", var2, true);
                           BasicInternalFrameTitlePane var4 = (BasicInternalFrameTitlePane)var2;
                           if (!(var4.getLayout() instanceof SyntheticaInternalFrameTitlePaneLayout5) && var3) {
                              var4.setLayout(new SyntheticaInternalFrameTitlePaneLayout5(var4));
                           }
                        }
   
                        Component[] var10 = var2.getComponents();
   
                        for(int var11 = 0; var11 < var10.length; ++var11) {
                           Component var5 = var10[var11];
                           if (var5 instanceof JButton) {
                              final String var6 = SyntheticaLookAndFeel.getStyleName(var5);
                              boolean var7 = true;
                              MouseListener[] var8 = var5.getMouseListeners();
   
                              for(int var9 = 0; var9 < var8.length; ++var9) {
                                 if (var8[var9].getClass().getName().contains("synthetica")) {
                                    var7 = false;
                                    break;
                                 }
                              }
   
                              if (var7) {
                                 StyleFactory.this.componentPropertyStore.storeComponentProperty(var5, "SYCP_MOUSE_LISTENERS");
                                 var5.addMouseListener(
                                    new MouseAdapter() {
                                       public void mouseEntered(MouseEvent var1) {
                                          var2.putClientProperty("Synthetica.MOUSE_OVER", var6);
                                       }
      
                                       public void mouseExited(MouseEvent var1) {
                                          var2.putClientProperty("Synthetica.MOUSE_OVER", null);
                                       }
      
                                       public void mousePressed(MouseEvent var1) {
                                          var2.putClientProperty("Synthetica.MOUSE_PRESSED", var6);
                                       }
      
                                       public void mouseReleased(final MouseEvent var1) {
                                          var2.putClientProperty("Synthetica.MOUSE_PRESSED", null);
                                          if (var2.getClientProperty("Synthetica.MOUSE_OVER") != null) {
                                             (new Thread() {
                                                   public void run() {
                                                      try {
                                                         sleep(100L);
                                                      } catch (InterruptedException var2x) {
                                                      }
         
                                                      EventQueue.invokeLater(
                                                         new Runnable() {
                                                            public void run() {
                                                               JButton var1x = (JButton)var1.getSource();
                                                               var1x.dispatchEvent(
                                                                  new MouseEvent(
                                                                     var1x,
                                                                     505,
                                                                     var1.getWhen(),
                                                                     var1.getModifiers(),
                                                                     var1.getX(),
                                                                     var1.getY(),
                                                                     var1.getClickCount(),
                                                                     var1.isPopupTrigger()
                                                                  )
                                                               );
                                                            }
                                                         }
                                                      );
                                                   }
                                                })
                                                .start();
                                          }
                                       }
                                    }
                                 );
                              }
                           }
                        }
                     }
   
                  }
               }
            );
         }
      } else if (var2 == Region.CHECK_BOX) {
         if (!this.eventListenerExists(var1.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            final JCheckBox var14 = (JCheckBox)var1;
            var1.addMouseListener(new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  var14.putClientProperty("Synthetica.MOUSE_OVER", true);
               }

               public void mouseExited(MouseEvent var1) {
                  var14.putClientProperty("Synthetica.MOUSE_OVER", false);
               }
            });
         }

         LookAndFeel.installProperty(var1, "iconTextGap", SyntheticaLookAndFeel.getInt("Synthetica.checkBox.iconTextGap", var1, 4));
         var3 = CheckBoxStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.COMBO_BOX) {
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.comboBox.hoverAndPressed.enabled", var1) && !this.eventListenerExists(var1.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            final JComboBox var15 = (JComboBox)var1;
            var1.addMouseListener(new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  var15.putClientProperty("Synthetica.MOUSE_OVER", true);
                  var15.repaint();
               }

               public void mouseExited(MouseEvent var1) {
                  var15.putClientProperty("Synthetica.MOUSE_OVER", false);
                  var15.repaint();
               }

               public void mousePressed(MouseEvent var1) {
                  var15.putClientProperty("Synthetica.MOUSE_PRESSED", true);
                  var15.repaint();
               }

               public void mouseReleased(MouseEvent var1) {
                  var15.putClientProperty("Synthetica.MOUSE_PRESSED", false);
                  var15.repaint();
               }
            });
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.comboBox.openedStateSupport.enabled", var1)
            && !this.eventListenerExists(((JComboBox)var1).getPopupMenuListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_COMBOBOX_POPUPMENU_LISTENERS");
            final JComboBox var16 = (JComboBox)var1;
            var16.addPopupMenuListener(new PopupMenuListener() {
               public void popupMenuWillBecomeVisible(PopupMenuEvent var1) {
                  var16.repaint();
               }

               public void popupMenuWillBecomeInvisible(PopupMenuEvent var1) {
                  var16.repaint();
               }

               public void popupMenuCanceled(PopupMenuEvent var1) {
               }
            });
         }

         if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent var1) {
                  JComboBox var2 = (JComboBox)var1.getSource();
                  String var3 = var1.getPropertyName();
                  if ("renderer".equals(var3) || "name".equals(var3)) {
                     String var8 = (String)SyntheticaLookAndFeel.get("Synthetica.comboBox.defaultRenderer.className", var2);
                     if (StyleFactory.this.replaceDefaultComboRenderer(var2.getRenderer(), var8)) {
                        StyleFactory.this.componentPropertyStore.storeComponentProperty(var2, "SYCP_COMBOBOX_DEFAULT_RENDERER");

                        try {
                           var2.setRenderer((DefaultListCellRenderer)Class.forName(var8).newInstance());
                        } catch (Exception var7) {
                           throw new RuntimeException(var7);
                        }
                     }
                  } else if ("UI".equals(var3)) {
                     String var4 = SyntheticaLookAndFeel.getString("Synthetica.comboBox.layoutManager.className", var2);
                     if (var4 != null) {
                        StyleFactory.this.componentPropertyStore.storeComponentProperty(var2, "SYCP_COMBOBOX_DEFAULT_LAYOUT");

                        try {
                           var2.setLayout((LayoutManager)Class.forName(var4).newInstance());
                        } catch (Exception var6) {
                           throw new RuntimeException(var6);
                        }
                     }
                  } else if ("editable".equals(var3)) {
                     var2.repaint();
                  }

               }
            });
         }

         var3 = ComboBoxStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.EDITOR_PANE) {
         this.installFocusListener(var1);
         this.installTextComponentPropertyChangeListener(var1);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }
      } else if (var2 == Region.FORMATTED_TEXT_FIELD) {
         this.installFocusListener(var1);
         var3 = FormattedTextFieldStyle.getStyle(var3, var1, var2);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }
      } else if (var2 == Region.INTERNAL_FRAME) {
         SynthContext var17 = new SynthContext(var1, Region.INTERNAL_FRAME, var3, 1);
         if (!var3.isOpaque(var17)) {
            this.setOpaque(var1, false);
         }

         if (SyntheticaLookAndFeel.get("Synthetica.internalFrame.opaque", var1) != null) {
            this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.internalFrame.opaque", var1, true));
         }

         if (JavaVersion.JAVA5 && !this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent var1) {
                  if (var1.getPropertyName().equals("frameIcon")) {
                     JInternalFrame var2 = (JInternalFrame)var1.getSource();
                     if (SyntheticaLookAndFeel.findComponent("InternalFrame.northPane", var2) != null) {
                        ((JButton)SyntheticaLookAndFeel.findComponent("InternalFrameTitlePane.menuButton", var2)).setIcon(var2.getFrameIcon());
                     }
                  }

               }
            });
         }
      } else if (var2 == Region.DESKTOP_ICON) {
         if (JavaVersion.JAVA5 && !this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent var1) {
                  if (var1.getPropertyName().equals("ancestor")) {
                     JDesktopIcon var2 = (JDesktopIcon)var1.getSource();
                     ((JButton)SyntheticaLookAndFeel.findComponent("InternalFrameTitlePane.menuButton", var2)).setIcon(var2.getInternalFrame().getFrameIcon());
                  }

               }
            });
         }
      } else if (var2 == Region.DESKTOP_PANE) {
         String var18 = SyntheticaLookAndFeel.getString("Synthetica.desktopPane.desktopManager.className", var1);
         if (var18 != null) {
            JDesktopPane var30 = (JDesktopPane)var1;
            this.componentPropertyStore.storeComponentProperty(var30, "SYCP_DESKTOP_MANAGER");

            try {
               var30.setDesktopManager((DesktopManager)Class.forName(var18).newInstance());
            } catch (Exception var8) {
               throw new RuntimeException(var8);
            }
         }
      } else if (var2 == Region.LABEL) {
         var3 = LabelStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.LIST) {
         this.installFocusListener(var1);
         var3 = ListStyle.getStyle(var3, var1, var2);
         if (SyntheticaLookAndFeel.get("Synthetica.list.opaque", var1) != null) {
            this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.list.opaque", var1, true));
         }

         if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent var1) {
                  if (var1.getSource() instanceof JList) {
                     JList var2 = (JList)var1.getSource();
                     ListCellRenderer var3 = var2.getCellRenderer();
                     String var4 = SyntheticaLookAndFeel.getString("Synthetica.list.defaultCellRenderer.className", var2);
                     if (var4 != null && var3 != null && var3.getClass().getName().startsWith("javax.swing.plaf.synth.SynthListUI")) {
                        try {
                           ListCellRenderer var5 = (ListCellRenderer)Class.forName(var4).newInstance();
                           var2.setCellRenderer(var5);
                        } catch (Exception var6) {
                           throw new RuntimeException(var6);
                        }
                     }
                  }

               }
            });
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.cellRenderer.hoverSupport.enabled", var1, false)
            && !this.eventListenerExists(var1.getMouseListeners())) {
            this.installCellRendererHoverListener(var1);
         }

         if (OS.getCurrentOS() == OS.Mac
            && !this.eventListenerExists(var1.getMouseListeners())
            && SyntheticaLookAndFeel.getBoolean("Synthetica.metaKeySupportOnMacEnabled", var1, true)
            && SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
            this.installMetaKeySupport(var1);
         }
      } else if (var2 == Region.PASSWORD_FIELD) {
         this.installFocusListener(var1);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }
      } else if (var2 == Region.ROOT_PANE) {
         Color var19 = UIManager.getColor("control");
         Color var31 = var1.getBackground();
         if (var31 instanceof SystemColor && !var31.equals(var19) && SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.replaceSystemColor", var1)) {
            var1.setBackground(var19);
         }
      } else if (var2 == Region.SPINNER) {
         var3 = SpinnerStyle.getStyle(var3, var1, var2);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }

         if (JavaVersion.JAVA5 && !this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent var1) {
                  if ("ToolTipText".equals(var1.getPropertyName())) {
                     StyleFactory.this.updateToolTipTextForChildren((JComponent)var1.getSource());
                  }

               }
            });
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.spinner.hoverAndPressed.enabled", var1) && !this.eventListenerExists(var1.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            final JSpinner var20 = (JSpinner)var1;
            var1.addMouseListener(new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  var20.putClientProperty("Synthetica.MOUSE_OVER", true);
                  var20.repaint();
               }

               public void mouseExited(MouseEvent var1) {
                  var20.putClientProperty("Synthetica.MOUSE_OVER", false);
                  var20.repaint();
               }

               public void mousePressed(MouseEvent var1) {
                  var20.putClientProperty("Synthetica.MOUSE_PRESSED", true);
                  var20.repaint();
               }

               public void mouseReleased(MouseEvent var1) {
                  var20.putClientProperty("Synthetica.MOUSE_PRESSED", false);
                  var20.repaint();
               }
            });
         }
      } else if (var2 == Region.SCROLL_BAR) {
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.scrollBarTrack.hoverAndPressed.enabled", var1)
            && !this.eventListenerExists(var1.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            var1.addMouseListener(new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  JScrollBar var2 = (JScrollBar)var1.getSource();
                  Boolean var3 = (Boolean)var2.getClientProperty("Synthetica.MOUSE_OVER");
                  var2.putClientProperty("Synthetica.MOUSE_OVER", true);
                  if (var3 == null || !var3) {
                     var2.repaint();
                  }

               }

               public void mouseExited(MouseEvent var1) {
                  JScrollBar var2 = (JScrollBar)var1.getSource();
                  Boolean var3 = (Boolean)var2.getClientProperty("Synthetica.MOUSE_OVER");
                  var2.putClientProperty("Synthetica.MOUSE_OVER", false);
                  if (var3 == null || var3) {
                     var2.repaint();
                  }

               }

               public void mousePressed(MouseEvent var1) {
                  JScrollBar var2 = (JScrollBar)var1.getSource();
                  Boolean var3 = (Boolean)var2.getClientProperty("Synthetica.MOUSE_PRESSED");
                  var2.putClientProperty("Synthetica.MOUSE_PRESSED", true);
                  if (var3 == null || !var3) {
                     var2.repaint();
                  }

               }

               public void mouseReleased(MouseEvent var1) {
                  JScrollBar var2 = (JScrollBar)var1.getSource();
                  Boolean var3 = (Boolean)var2.getClientProperty("Synthetica.MOUSE_PRESSED");
                  var2.putClientProperty("Synthetica.MOUSE_PRESSED", false);
                  if (var3 == null || var3) {
                     var2.repaint();
                  }

               }
            });
         }
      } else if (var2 == Region.SCROLL_PANE) {
         var3 = ScrollPaneStyle.getStyle(var3, var1, var2);
         JViewport var21 = ((JScrollPane)var1).getViewport();
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)
            && (var21 != null && var21.getView() instanceof JTextComponent || SyntheticaLookAndFeel.getBoolean("Synthetica.scrollPane.nonOpaque", var1))) {
            this.setOpaque(var1, false);
         }

         this.applyTitledBorderStyle(var1);
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.scrollPane.cornerPainter.enabled", var1)) {
            JScrollPane var32 = (JScrollPane)var1;
            this.installScrollPaneCorner(var32, "LOWER_RIGHT_CORNER");
            this.installScrollPaneCorner(var32, "LOWER_LEFT_CORNER");
            this.installScrollPaneCorner(var32, "UPPER_LEFT_CORNER");
            this.installScrollPaneCorner(var32, "UPPER_RIGHT_CORNER");
         }
      } else if (var2 == Region.SLIDER
         && SyntheticaLookAndFeel.getBoolean("Synthetica.slider.hoverAndPressed.enabled", var1)
         && !this.eventListenerExists(var1.getMouseListeners())) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
         var1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent var1) {
               JComponent var2 = (JComponent)var1.getSource();
               var2.putClientProperty("Synthetica.MOUSE_OVER", true);
               var2.repaint();
            }

            public void mouseExited(MouseEvent var1) {
               JComponent var2 = (JComponent)var1.getSource();
               var2.putClientProperty("Synthetica.MOUSE_OVER", false);
               var2.repaint();
            }

            public void mousePressed(MouseEvent var1) {
               JComponent var2 = (JComponent)var1.getSource();
               var2.putClientProperty("Synthetica.MOUSE_PRESSED", true);
               var2.repaint();
            }

            public void mouseReleased(MouseEvent var1) {
               JComponent var2 = (JComponent)var1.getSource();
               var2.putClientProperty("Synthetica.MOUSE_PRESSED", false);
               var2.repaint();
            }
         });
      } else if (var2 == Region.SPLIT_PANE_DIVIDER) {
         if (SyntheticaLookAndFeel.getBoolean("Syntetica.splitPane.centerOneTouchButtons", var1)) {
            if (!this.eventListenerExists(var1.getComponentListeners())) {
               this.componentPropertyStore.storeComponentProperty(var1, "SYCP_COMPONENT_LISTENERS");
               var1.addComponentListener(new ComponentAdapter() {
                  public void componentResized(ComponentEvent var1) {
                     StyleFactory.this.updateSplitDivider((JSplitPane)var1.getComponent());
                  }
               });
            }

            if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
               this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
               var1.addPropertyChangeListener(new PropertyChangeListener() {
                  public void propertyChange(PropertyChangeEvent var1) {
                     String var2 = var1.getPropertyName();
                     if ("orientation".equals(var2) || "dividerSize".equals(var2)) {
                        StyleFactory.this.updateSplitDivider((JSplitPane)var1.getSource());
                     }

                  }
               });
            }
         }
      } else if (var2 == Region.MENU) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         final JMenu var22 = (JMenu)var1;
         if (!menuToolTipEnabled && var1.getToolTipText() != null) {
            ToolTipManager.sharedInstance().unregisterComponent(var1);
         }

         final MouseAdapter var33 = null;
         if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
            final DefaultSynthStyle var42 = (DefaultSynthStyle)var3;
            var33 = new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  JMenu var2 = (JMenu)var1.getSource();
                  if (var2.isTopLevelMenu()) {
                     var2.putClientProperty("Synthetica.MOUSE_OVER", Boolean.TRUE);
                     Color var3 = var42.getColor(var2, Region.MENU, 512, ColorType.TEXT_FOREGROUND);
                     var2.setForeground(new Color(var3.getRGB()));
                     var2.repaint();
                  }

               }

               public void mouseExited(MouseEvent var1) {
                  JMenu var2 = (JMenu)var1.getSource();
                  if (var2.isTopLevelMenu()) {
                     var2.putClientProperty("Synthetica.MOUSE_OVER", Boolean.FALSE);
                     Color var3 = var42.getColor(var2, Region.MENU, 1024, ColorType.TEXT_FOREGROUND);
                     var2.setForeground(var3);
                     var2.repaint();
                  }

               }
            };
         }

         if (var22.isEnabled() && !this.eventListenerExists(var22.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
               var22.addMouseListener(var33);
            }
         } else if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent var1) {
                  if (var1.getPropertyName().equals("ancestor")) {
                     if (!var22.isEnabled() || !var22.isTopLevelMenu()) {
                        return;
                     }

                     StyleFactory.this.componentPropertyStore.storeComponentProperty(var22, "SYCP_MOUSE_LISTENERS");
                     if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
                        var22.addMouseListener(var33);
                     }
                  }

               }
            });
         }

         var3 = MenuStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.MENU_ITEM) {
         var3 = MenuItemStyle.getStyle(var3, var1, var2);
         if (!menuItemToolTipEnabled && var1.getToolTipText() != null) {
            ToolTipManager.sharedInstance().unregisterComponent(var1);
         }
      } else if (var2 == Region.RADIO_BUTTON_MENU_ITEM) {
         var3 = MenuItemStyle.getStyle(var3, var1, var2);
         if (!menuItemToolTipEnabled && var1.getToolTipText() != null) {
            ToolTipManager.sharedInstance().unregisterComponent(var1);
         }
      } else if (var2 == Region.CHECK_BOX_MENU_ITEM) {
         var3 = MenuItemStyle.getStyle(var3, var1, var2);
         if (!menuItemToolTipEnabled && var1.getToolTipText() != null) {
            ToolTipManager.sharedInstance().unregisterComponent(var1);
         }
      } else if (var2 == Region.TABLE) {
         this.installFocusListener(var1);
         JTable var23 = (JTable)var1;
         if (SyntheticaLookAndFeel.get("Synthetica.table.opaque", var1) != null) {
            this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.table.opaque", var1, true));
         }

         if (SyntheticaLookAndFeel.get("Synthetica.table.columnMargin", var23) != null) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TABLE_COLUMN_MARGIN");
            int var34 = SyntheticaLookAndFeel.getInt("Synthetica.table.columnMargin", var23);
            if (var34 != var23.getColumnModel().getColumnMargin()) {
               var23.getColumnModel().setColumnMargin(var34);
            }
         }

         if (SyntheticaLookAndFeel.get("Synthetica.table.rowMargin", var23) != null) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TABLE_ROW_MARGIN");
            int var35 = SyntheticaLookAndFeel.getInt("Synthetica.table.rowMargin", var23);
            if (var35 != var23.getRowMargin()) {
               var23.setRowMargin(var35);
            }
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.table.installCellRenderers", var1, true)) {
            this.installDefaultTableRenderers(var23);
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.table.installCellEditors", var1, true)) {
            this.installDefaultTableEditors(var23);
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.cellRenderer.hoverSupport.enabled", var1, false)
            && !this.eventListenerExists(var1.getMouseListeners())) {
            this.installCellRendererHoverListener(var1);
         }

         if (OS.getCurrentOS() == OS.Mac
            && !this.eventListenerExists(var1.getMouseListeners())
            && SyntheticaLookAndFeel.getBoolean("Synthetica.metaKeySupportOnMacEnabled", var1, true)
            && SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
            this.installMetaKeySupport(var1);
         }

         if (var23.getDefaultRenderer(Icon.class) == null) {
            var23.setDefaultRenderer(Icon.class, var23.getDefaultRenderer(ImageIcon.class));
         }

         var3 = TableStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.TABLE_HEADER) {
         if (var1 instanceof JTableHeader) {
            JTableHeader var24 = (JTableHeader)var1;
            if (SyntheticaLookAndFeel.get("Synthetica.tableHeader.opaque", var1) != null) {
               this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.tableHeader.opaque", var1, true));
            }

            TableCellRenderer var36 = var24.getDefaultRenderer();
            if (var36 instanceof UIResource
               && !(var36 instanceof SyntheticaHeaderRenderer5)
               && !var36.getClass().getName().equals("de.javasoft.plaf.synthetica.SyntheticaHeaderRenderer")
               && !var36.getClass().getName().equals("org.netbeans.modules.autoupdate.ui.SortColumnHeaderRenderer")
               && !SyntheticaLookAndFeel.getBoolean("Synthetica.table.useSynthHeaderRenderer", var1)) {
               if (JavaVersion.JAVA5) {
                  var24.setDefaultRenderer(new SyntheticaHeaderRenderer5());
               } else if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
                  var24.setDefaultRenderer(new SyntheticaHeaderRenderer());
               }
            }
         }
      } else if (var2 == Region.TABBED_PANE_TAB) {
         var3 = TabbedPaneStyle.getStyle(var3, var1, var2);
         if (!this.eventListenerExists(var1.getMouseMotionListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_MOTION_LISTENERS");
            var1.addMouseMotionListener(new MouseMotionAdapter() {
               public void mouseMoved(MouseEvent var1) {
                  StyleFactory.this.tabHoverSupport(var1);
               }
            });
         }

         if (!this.eventListenerExists(var1.getMouseListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
            var1.addMouseListener(new MouseAdapter() {
               public void mouseEntered(MouseEvent var1) {
                  StyleFactory.this.tabHoverSupport(var1);
               }

               public void mouseExited(MouseEvent var1) {
                  JTabbedPane var2 = (JTabbedPane)var1.getSource();
                  Integer var3 = (Integer)var2.getClientProperty("Synthetica.MOUSE_OVER");
                  int var4 = var3 == null ? -1 : var3;
                  if (var4 != -1) {
                     var2.putClientProperty("Synthetica.MOUSE_OVER", -1);
                     var2.repaint();
                  }

               }
            });
         }

         JTabbedPane var25 = (JTabbedPane)var1;
         if (!this.eventListenerExists(var25.getContainerListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_CONTAINER_LISTENERS");
            this.storeContainerComponentProperties(var25, "SYCP_OPAQUE");
            var25.addContainerListener(new ContainerListener() {
               public void componentAdded(ContainerEvent var1) {
                  StyleFactory.this.storeContainerComponentProperties(var1.getContainer(), "SYCP_OPAQUE");
                  ((JTabbedPane)var1.getContainer()).putClientProperty("Synthetica.childsAreTranslucent", false);
               }

               public void componentRemoved(ContainerEvent var1) {
               }
            });
         }
      } else if (var2 == Region.TEXT_AREA) {
         this.installFocusListener(var1);
         this.installTextComponentPropertyChangeListener(var1);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }
      } else if (var2 == Region.TEXT_FIELD) {
         this.installFocusListener(var1);
         var3 = TextFieldStyle.getStyle(var3, var1, var2);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }
      } else if (var2 == Region.TEXT_PANE) {
         this.installFocusListener(var1);
         this.installTextComponentPropertyChangeListener(var1);
         if (!SyntheticaLookAndFeel.getBoolean("Synthetica.textComponents.useSwingOpaqueness", var1)) {
            this.setOpaque(var1, false);
         }
      } else if (var2 == Region.TOGGLE_BUTTON) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_BUTTON_MARGIN");
         if (var1.getParent() != null && var1.getParent() instanceof JToolBar) {
            var3 = ToolBarButtonStyle.getStyle(var3, var1, var2);
         }

         LookAndFeel.installProperty(var1, "iconTextGap", SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.iconTextGap", var1, 4));
      } else if (var2 == Region.TOOL_BAR) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         var3 = ToolBarStyle.getStyle(var3, var1, var2);
         this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.opaque", var1, true));
         if (!this.eventListenerExists(var1.getContainerListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_CONTAINER_LISTENERS");
            var1.addContainerListener(
               new ContainerAdapter() {
                  public void componentAdded(ContainerEvent var1) {
                     Component var2 = var1.getChild();
                     Font var3 = var2.getFont();
                     if (var2 instanceof JButton
                        && var3 instanceof UIResource
                        && SyntheticaLookAndFeel.get("Synthetica.toolBar.button.font.style", var2) != null) {
                        AbstractButton var6 = (AbstractButton)var2;
                        String var7 = SyntheticaLookAndFeel.getStyleName(var6);
                        if (var6.getClientProperty("Synthetica.style") != null) {
                           var6.putClientProperty("Synthetica.style", null);
                           var6.putClientProperty("Synthetica.style", var7);
                        } else {
                           var2.setName(null);
                           var2.setName(var7);
                        }
                     } else if (var2 instanceof JToggleButton
                        && var3 instanceof UIResource
                        && SyntheticaLookAndFeel.get("Synthetica.toolBar.toggleButton.font.style", var2) != null) {
                        AbstractButton var4 = (AbstractButton)var2;
                        String var5 = SyntheticaLookAndFeel.getStyleName(var2);
                        if (var4.getClientProperty("Synthetica.style") != null) {
                           var4.putClientProperty("Synthetica.style", null);
                           var4.putClientProperty("Synthetica.style", var5);
                        } else {
                           var2.setName(null);
                           var2.setName(var5);
                        }
                     }
   
                  }
               }
            );
         }
      } else if (var2 == Region.TOOL_BAR_SEPARATOR) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TOOLBAR_SEPARATOR_SIZE");
         var3 = ToolBarSeparatorStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.TOOL_TIP) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         var3 = ToolTipStyle.getStyle(var3, var1, var2);
      } else if (var2 == Region.TREE) {
         this.installFocusListener(var1);
         if (SyntheticaLookAndFeel.get("Synthetica.tree.opaque", var1) != null) {
            this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.tree.opaque", var1, true));
         }

         if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
            var1.addPropertyChangeListener(
               new PropertyChangeListener() {
                  public void propertyChange(PropertyChangeEvent var1) {
                     String var2 = var1.getPropertyName();
                     JTree var3 = (JTree)var1.getSource();
                     TreeCellRenderer var4 = var3.getCellRenderer();
                     boolean var5 = SyntheticaLookAndFeel.getBoolean("Synthetica.tree.defaultCellRenderer.enabled", var3, true);
                     String var6 = SyntheticaLookAndFeel.getString("Synthetica.tree.defaultCellRenderer.className", var3);
                     if (var6 == null) {
                        var6 = SyntheticaDefaultTreeCellRenderer.class.getName();
                     }
   
                     if ((var2.equals("cellRenderer") || var2.equals("name"))
                        && var5
                        && var4 != null
                        && (var4.getClass().getName().startsWith("javax.swing.plaf.synth.SynthTreeUI") || var4 instanceof SyntheticaDefaultTreeCellRenderer)) {
                        try {
                           if (!var6.equals(var4.getClass().getName())) {
                              TreeCellRenderer var7 = (TreeCellRenderer)Class.forName(var6).newInstance();
                              var3.setCellRenderer(var7);
                           }
                        } catch (Exception var8) {
                           throw new RuntimeException(var8);
                        }
                     }
   
                     if (JavaVersion.JAVA5 && "componentOrientation".equals(var2) && !((ComponentOrientation)var1.getNewValue()).isLeftToRight()) {
                        ((JTree)var1.getSource()).updateUI();
                     }
   
                  }
               }
            );
         }

         if (OS.getCurrentOS() == OS.Mac
            && !this.eventListenerExists(var1.getMouseListeners())
            && SyntheticaLookAndFeel.getBoolean("Synthetica.metaKeySupportOnMacEnabled", var1, true)
            && SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
            this.installMetaKeySupport(var1);
         }
      } else if (var2 == Region.PANEL) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         this.applyTitledBorderStyle(var1);
      } else if (var2 == Region.POPUP_MENU) {
         if ("ComboPopup.popup".equals(var4)
            && UIManager.get("Synthetica.comboPopup.insets") != null
            && !(var1.getBorder() instanceof StyleFactory.SyntheticaComboPopupBorder)
            && SyntheticaLookAndFeel.findComponent(JScrollPane.class, var1) != null) {
            var1.setBorder(new StyleFactory.SyntheticaComboPopupBorder(null));
            JScrollPane var26 = (JScrollPane)SyntheticaLookAndFeel.findComponent(JScrollPane.class, var1);
            Dimension var37 = var26.getPreferredSize();
            var37.width -= var1.getInsets().left + var1.getInsets().right - 2;
            var26.setPreferredSize(var37);
            var26.setMinimumSize(var37);
            var26.setMaximumSize(var37);
         }

         JComponent var27 = (JComponent)SyntheticaLookAndFeel.findComponent("ComboBox.scrollPane", var1);
         if (var27 != null) {
            var27.setBorder(new EmptyBorder(0, 0, 0, 0));
            Insets var38 = SyntheticaLookAndFeel.getInsets("Synthetica.comboPopup.list.insets", var1);
            JComponent var43 = (JComponent)SyntheticaLookAndFeel.findComponent("ComboBox.list", var1);
            if (var38 != null && var43 != null) {
               var43.setBorder(new EmptyBorder(var38));
            }
         }

         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
         var3 = PopupMenuStyle.getStyle(var3, var1, var2);
         Insets var39 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.toplevel.insets", var1);
         if (var39 != null && !this.eventListenerExists(((JPopupMenu)var1).getPopupMenuListeners())) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_POPUPMENU_LISTENERS");
            ((JPopupMenu)var1)
               .addPopupMenuListener(
                  new PopupMenuListener() {
                     public void popupMenuWillBecomeVisible(PopupMenuEvent var1) {
                        JPopupMenu var2 = (JPopupMenu)var1.getSource();
                        if (SyntheticaLookAndFeel.isToplevelPopupMenu(var2)) {
                           StyleFactory.this.componentPropertyStore.storeComponentProperty(var2, "SYCP_BORDER");
                           BorderUIResource var3 = new BorderUIResource(
                              new EmptyBorder(SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.toplevel.insets", var2, false))
                           );
                           var2.setBorder(var3);
                        }
      
                     }
      
                     public void popupMenuWillBecomeInvisible(PopupMenuEvent var1) {
                     }
      
                     public void popupMenuCanceled(PopupMenuEvent var1) {
                     }
                  }
               );
         }
      } else if (var2 != Region.POPUP_MENU_SEPARATOR) {
         if (var2 == Region.RADIO_BUTTON) {
            if (!this.eventListenerExists(var1.getMouseListeners())) {
               this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
               final JRadioButton var28 = (JRadioButton)var1;
               var1.addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent var1) {
                     var28.putClientProperty("Synthetica.MOUSE_OVER", true);
                  }

                  public void mouseExited(MouseEvent var1) {
                     var28.putClientProperty("Synthetica.MOUSE_OVER", false);
                  }
               });
            }

            LookAndFeel.installProperty(var1, "iconTextGap", SyntheticaLookAndFeel.getInt("Synthetica.radioButton.iconTextGap", var1, 4));
            var3 = RadioButtonStyle.getStyle(var3, var1, var2);
         } else if (var2 == Region.VIEWPORT) {
            var3 = ViewportStyle.getStyle(var3, var1, Region.VIEWPORT);
            if (SyntheticaLookAndFeel.get("Synthetica.viewport.opaque", var1) != null) {
               this.setOpaque(var1, SyntheticaLookAndFeel.getBoolean("Synthetica.viewport.opaque", var1, true));
            }

            if (!this.eventListenerExists(var1.getContainerListeners())) {
               this.componentPropertyStore.storeComponentProperty(var1, "SYCP_CONTAINER_LISTENERS");
               var1.addContainerListener(
                  new ContainerAdapter() {
                     public void componentAdded(ContainerEvent var1) {
                        Component var2 = var1.getChild();
                        boolean var3 = var2.getClass().getName().endsWith("JHEditorPane")
                           && SyntheticaLookAndFeel.getBoolean("Synthetica.javaHelpEditorViewport.opaque", null, true);
                        if (var2 instanceof JTextComponent && !var3) {
                           StyleFactory.this.setOpaque((JViewport)var1.getContainer(), false);
                        }
   
                     }
                  }
               );
            }
         }
      }

      return var3;
   }

   private boolean replaceDefaultComboRenderer(ListCellRenderer var1, String var2) {
      String var3 = var1 == null ? "NULL" : var1.getClass().getName();
      return var2 != null && (var1 == null || var1 instanceof UIResource) && !var3.equals(var2) && !var3.startsWith("org.netbeans.");
   }

   private void tabHoverSupport(MouseEvent var1) {
      JTabbedPane var2 = (JTabbedPane)var1.getSource();
      Integer var3 = (Integer)var2.getClientProperty("Synthetica.MOUSE_OVER");
      int var4 = var3 == null ? -1 : var3;
      int var5 = -1;
      int var6 = var2.getTabCount();

      for(int var7 = 0; var7 < var6; ++var7) {
         if (var2.getBoundsAt(var7).contains(var1.getPoint())) {
            var5 = var7;
            break;
         }
      }

      if (var4 != var5) {
         var2.putClientProperty("Synthetica.MOUSE_OVER", var5);
         if (var4 >= 0 && var2.getTabCount() > var4) {
            var2.repaint(var2.getBoundsAt(var4));
         }

         if (var5 >= 0) {
            var2.repaint(var2.getBoundsAt(var5));
         }
      }

   }

   private void installScrollPaneCorner(JScrollPane var1, String var2) {
      Component var3 = var1.getCorner(var2);
      if ((var3 == null || var3 instanceof UIResource) && !(var3 instanceof SyntheticaScrollPaneCorner)) {
         var1.setCorner(var2, new SyntheticaScrollPaneCorner.UIResource(var2));
      }

   }

   private void applyTitledBorderStyle(JComponent var1) {
      if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
         var1.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent var1) {
               if ("border".equals(var1.getPropertyName())) {
                  JComponent var2 = (JComponent)var1.getSource();
                  TitledBorder var3 = StyleFactory.this.getTitledBorder(var2.getBorder());
                  if (var3 != null && SyntheticaLookAndFeel.get("Synthetica.titledBorder.title.position", var2) != null) {
                     StyleFactory.this.setTitledBorderTitlePosition(var2, var3);
                  }

               }
            }
         });
      }

      TitledBorder var2 = this.getTitledBorder(var1.getBorder());
      if (var2 != null && SyntheticaLookAndFeel.get("Synthetica.titledBorder.title.position", var1) != null) {
         this.setTitledBorderTitlePosition(var1, var2);
      }

   }

   private void setOpaque(JComponent var1, boolean var2) {
      this.componentPropertyStore.storeComponentProperty(var1, "SYCP_OPAQUE");
      var1.setOpaque(var2);
   }

   private boolean eventListenerExists(EventListener[] var1) {
      for(EventListener var2 : var1) {
         if (var2 != this.styleUpdater && var2.getClass().getName().startsWith(this.getClass().getName())) {
            return true;
         }
      }

      return false;
   }

   private boolean eventListenerExists(EventListener var1, EventListener[] var2) {
      for(EventListener var3 : var2) {
         if (var3 == var1) {
            return true;
         }
      }

      return false;
   }

   private void updateToolTipTextForChildren(JComponent var1) {
      Component[] var5;
      for(Component var2 : var5 = var1.getComponents()) {
         ((JComponent)var2).setToolTipText(var1.getToolTipText());
         if (var2 instanceof JComponent) {
            this.updateToolTipTextForChildren((JComponent)var2);
         }
      }

   }

   private TitledBorder getTitledBorder(Border var1) {
      if (var1 instanceof TitledBorder) {
         return (TitledBorder)var1;
      } else {
         if (var1 instanceof CompoundBorder) {
            TitledBorder var2 = this.getTitledBorder(((CompoundBorder)var1).getOutsideBorder());
            if (var2 != null) {
               return var2;
            }

            TitledBorder var3 = this.getTitledBorder(((CompoundBorder)var1).getInsideBorder());
            if (var3 != null) {
               return var3;
            }
         }

         return null;
      }
   }

   private void setTitledBorderTitlePosition(JComponent var1, TitledBorder var2) {
      this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TITLEDBORDER_TITLEPOSITION");
      var2.setTitlePosition(SyntheticaLookAndFeel.getInt("Synthetica.titledBorder.title.position", var1));
   }

   private void installMetaKeySupport(JComponent var1) {
      this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
      var1.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            int var2 = var1.getModifiers();

            try {
               if (var1.isMetaDown()) {
                  int var3 = var2 | 2;
                  Class var4 = Class.forName("java.awt.event.InputEvent");
                  Field var5 = var4.getDeclaredField("modifiers");
                  var5.setAccessible(true);
                  var5.set(var1, var3);
               }

            } catch (Exception var6) {
               throw new RuntimeException(var6);
            }
         }
      });
   }

   private void storeContainerComponentProperties(Container var1, String var2) {
      if (var1 instanceof JComponent) {
         this.componentPropertyStore.storeComponentProperty(var1, var2);
      }

      Component[] var3 = var1.getComponents();

      for(Component var4 : var3) {
         if (var4 instanceof Container && !(var4 instanceof Window) && !(var4 instanceof JRootPane)) {
            this.storeContainerComponentProperties((Container)var4, var2);
         }
      }

   }

   void restoreAllComponentProperties() {
      if (this.componentPropertyStore.cleanerThread != null) {
         this.componentPropertyStore.restoreAllComponentProperties();
         this.componentPropertyStore.cleanerThread.interrupt();
      }

   }

   public void prepareMetalLAFSwitch() {
      this.prepareMetalLAFSwitch = true;
   }

   static void reinitialize() {
      menuToolTipEnabled = UIManager.getBoolean("Synthetica.menu.toolTipEnabled");
      menuItemToolTipEnabled = UIManager.getBoolean("Synthetica.menuItem.toolTipEnabled");
   }

   private void setOpaqueDefault4Metal(JComponent var1) {
      if (!(var1 instanceof JLabel)
         && !(var1 instanceof JInternalFrame)
         && !(var1 instanceof JTabbedPane)
         && !(var1 instanceof JSeparator)
         && !(var1 instanceof JMenu)
         && !(var1 instanceof JSlider)
         && (!(var1 instanceof JPanel) || !this.isGlassPane((JPanel)var1))) {
         if (var1 instanceof JPanel) {
            return;
         }

         var1.setOpaque(true);
      } else {
         var1.setOpaque(false);
      }

   }

   private boolean isGlassPane(JPanel var1) {
      return var1.getParent() instanceof JRootPane && ((JRootPane)var1.getParent()).getGlassPane() == var1;
   }

   private void updateSplitDivider(JSplitPane var1) {
      if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         BasicSplitPaneUI var2 = (BasicSplitPaneUI)var1.getUI();
         BasicSplitPaneDivider var3 = var2.getDivider();
         int var4 = DefaultLookup.getInt(var1, var2, "SplitPane.oneTouchButtonOffset", 2);

         try {
            Class var5 = Class.forName("javax.swing.plaf.basic.BasicSplitPaneDivider");
            Field var6 = var5.getDeclaredField("oneTouchOffset");
            var6.setAccessible(true);
            if (var3 != null) {
               var6.set(var3, var4);
            }
         } catch (Exception var7) {
            throw new RuntimeException(var7);
         }

         if (var3 != null) {
            var3.getLayout().layoutContainer(var3);
         }

      }
   }

   private void installDefaultTableRenderers(JTable var1) {
      TableCellRenderer var2 = var1.getDefaultRenderer(Object.class);
      String var3 = "Synthetica.table.defaultRenderer.className";
      boolean var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultRenderer", var1, true);
      if ((SyntheticaLookAndFeel.get(var3, var1) != null || var4) && (var2 == null || var2.getClass().getName().contains("$SynthTableCellRenderer"))) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TABLE_OBJECT_DEFAULT_RENDERER");
         this.installDefaultTableCellRenderer(var1, Object.class, var3, var2);
         var2 = var1.getDefaultRenderer(Object.class);
      }

      var3 = "Synthetica.table.defaultBooleanRenderer.className";
      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultBooleanRenderer", var1, true);
      if (SyntheticaLookAndFeel.get(var3, var1) != null || var4) {
         TableCellRenderer var5 = var1.getDefaultRenderer(Boolean.class);
         if (var5 == null || var5.getClass().getName().contains("$SynthBooleanTableCellRenderer")) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TABLE_BOOLEAN_DEFAULT_RENDERER");
            this.installDefaultTableCellRenderer(var1, Boolean.class, var3, var5);
         }
      }

      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultFloatRenderer", var1);
      if (SyntheticaLookAndFeel.get("Synthetica.table.defaultFloatRenderer.className", var1) != null || var4) {
         TableCellRenderer var14 = var1.getDefaultRenderer(Float.class);
         if (var14 == null || var14 instanceof UIResource && var14 == var2) {
            this.installDefaultTableCellRenderer(var1, Float.class, var3, var2);
         }
      }

      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultDoubleRenderer", var1);
      if (SyntheticaLookAndFeel.get("Synthetica.table.defaultDoubleRenderer.className", var1) != null || var4) {
         TableCellRenderer var15 = var1.getDefaultRenderer(Double.class);
         if (var15 == null || var15 instanceof UIResource && var15 == var2) {
            this.installDefaultTableCellRenderer(var1, Double.class, var3, var2);
         }
      }

      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultNumberRenderer", var1);
      if (SyntheticaLookAndFeel.get("Synthetica.table.defaultNumberRenderer.className", var1) != null || var4) {
         TableCellRenderer var16 = var1.getDefaultRenderer(Number.class);
         if (var16 == null || var16 instanceof UIResource && var16 == var2) {
            this.installDefaultTableCellRenderer(var1, Number.class, var3, var2);
         }
      }

      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultDateRenderer", var1);
      if (SyntheticaLookAndFeel.get("Synthetica.table.defaultDateRenderer.className", var1) != null || var4) {
         TableCellRenderer var17 = var1.getDefaultRenderer(Date.class);
         if (var17 == null || var17 instanceof UIResource && var17 == var2) {
            this.installDefaultTableCellRenderer(var1, Date.class, var3, var2);
         }
      }

      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultIconRenderer", var1);
      if (SyntheticaLookAndFeel.get("Synthetica.table.defaultIconRenderer.className", var1) != null || var4) {
         TableCellRenderer var18 = var1.getDefaultRenderer(Icon.class);
         if (var18 == null || var18 instanceof UIResource && var18 == var2) {
            this.installDefaultTableCellRenderer(var1, Icon.class, var3, var2);
         }
      }

      var4 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultImageIconRenderer", var1);
      if (SyntheticaLookAndFeel.get("Synthetica.table.defaultImageIconRenderer.className", var1) != null || var4) {
         TableCellRenderer var19 = var1.getDefaultRenderer(ImageIcon.class);
         if (var19 == null || var19 instanceof UIResource && var19 == var2) {
            this.installDefaultTableCellRenderer(var1, ImageIcon.class, var3, var2);
         }
      }

   }

   private void installDefaultTableCellRenderer(JTable var1, Class<?> var2, String var3, TableCellRenderer var4) {
      try {
         String var5 = (String)SyntheticaLookAndFeel.get(var3, var1);
         if (var5 == null) {
            var5 = var2 == Boolean.class ? SyntheticaDefaultBooleanTableCellRenderer.class.getName() : SyntheticaDefaultTableCellRenderer.class.getName();
         }

         TableCellRenderer var6 = (TableCellRenderer)Class.forName(var5).getConstructor(TableCellRenderer.class).newInstance(var4);
         var1.setDefaultRenderer(var2, var6);
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }
   }

   private void installDefaultTableEditors(JTable var1) {
      String var2 = "Synthetica.table.defaultEditor.className";
      boolean var3 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultEditor", var1, true);
      if (SyntheticaLookAndFeel.get(var2, var1) != null || var3) {
         TableCellEditor var4 = var1.getDefaultEditor(Object.class);
         if (var4 == null || var4.getClass().getName().contains(".JTable") || var4.getClass().getName().contains(".swingx.JXTable")) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TABLE_OBJECT_DEFAULT_EDITOR");
            this.installDefaultTableCellEditor(var1, Object.class, var2, var4);
         }
      }

      var2 = "Synthetica.table.defaultEditor.className";
      var3 = SyntheticaLookAndFeel.getBoolean("Synthetica.table.installDefaultNumberEditor", var1, true);
      if (SyntheticaLookAndFeel.get(var2, var1) != null || var3) {
         TableCellEditor var7 = var1.getDefaultEditor(Number.class);
         if (var7 == null || var7.getClass().getName().contains(".JTable") || var7.getClass().getName().contains(".swingx.table.NumberEditorExt")) {
            this.componentPropertyStore.storeComponentProperty(var1, "SYCP_TABLE_NUMBER_DEFAULT_EDITOR");
            this.installDefaultTableCellEditor(var1, Number.class, var2, var7);
         }
      }

   }

   private void installDefaultTableCellEditor(JTable var1, Class<?> var2, String var3, TableCellEditor var4) {
      try {
         String var5 = (String)SyntheticaLookAndFeel.get(var3, var1);
         if (var5 == null) {
            var5 = SyntheticaDefaultTableCellEditor.class.getName();
         }

         if (var2 == Number.class) {
            var5 = var5 + "$NumberEditor";
         }

         var1.setDefaultEditor(var2, (TableCellEditor)Class.forName(var5).newInstance());
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   private void installCellRendererHoverListener(JComponent var1) {
      this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_LISTENERS");
      this.componentPropertyStore.storeComponentProperty(var1, "SYCP_MOUSE_MOTION_LISTENERS");
      if (this.cellRendererHoverListener == null) {
         this.cellRendererHoverListener = new StyleFactory.CellRendererHoverListener(null);
      }

      var1.addMouseListener(this.cellRendererHoverListener);
      var1.addMouseMotionListener(this.cellRendererHoverListener);
   }

   private void installFocusListener(Component var1) {
      if (var1.isEnabled()) {
         FocusListener[] var5;
         for(FocusListener var2 : var5 = var1.getFocusListeners()) {
            if (var2 instanceof StyleFactory.RepaintFocusListener) {
               return;
            }
         }

         this.getComponentPropertyStore().storeComponentProperty(var1, "SYCP_FOCUS_LISTENERS");
         var1.addFocusListener(new StyleFactory.RepaintFocusListener(null));
      }
   }

   private void installTextComponentPropertyChangeListener(Component var1) {
      if (!this.eventListenerExists(var1.getPropertyChangeListeners())) {
         this.componentPropertyStore.storeComponentProperty(var1, "SYCP_PROPERTY_CHANGE_LISTENERS");
         var1.addPropertyChangeListener(new StyleFactory.RepaintTextComponentPropertyChangeListener(null));
      }

   }

   private static class CellRendererHoverListener extends MouseAdapter {
      private final String HOVER = "Synthetica.hoverIndex";

      private CellRendererHoverListener() {
         super();
      }

      public void mouseExited(MouseEvent var1) {
         JComponent var2 = (JComponent)var1.getSource();
         var2.putClientProperty("Synthetica.hoverIndex", -1);
         var2.repaint();
      }

      public void mouseMoved(MouseEvent var1) {
         this.doHover(var1);
      }

      private void doHover(MouseEvent var1) {
         Object var2 = var1.getSource();
         if (var2 instanceof JList) {
            JList var3 = (JList)var1.getSource();
            int var4 = SyntheticaLookAndFeel.getClientProperty("Synthetica.hoverIndex", var3, -1);
            if (var4 >= 0 && var4 < var3.getModel().getSize()) {
               Rectangle var5 = var3.getCellBounds(var4, var4);
               var3.repaint(var5);
            }

            int var10 = var3.locationToIndex(var1.getPoint());
            var3.putClientProperty("Synthetica.hoverIndex", var10);
            Rectangle var6 = var3.getCellBounds(var10, var10);
            if (var6 != null) {
               var3.repaint(var6);
            }
         } else if (var2 instanceof JTable) {
            JTable var8 = (JTable)var1.getSource();
            if (var8.getColumnCount() <= 0) {
               return;
            }

            int var9 = SyntheticaLookAndFeel.getClientProperty("Synthetica.hoverIndex", var8, -1);
            if (var9 >= 0 && var9 < var8.getRowCount()) {
               Rectangle var11 = var8.getCellRect(var9, 0, true);
               Rectangle var13 = var8.getCellRect(var9, var8.getColumnCount() - 1, true);
               if (var11 != null && var13 != null) {
                  var11.width = var13.x + var13.width - var11.x;
                  var8.repaint(var11);
               }
            }

            int var12 = var8.rowAtPoint(var1.getPoint());
            var8.putClientProperty("Synthetica.hoverIndex", var12);
            Rectangle var14 = var8.getCellRect(var12, 0, true);
            Rectangle var7 = var8.getCellRect(var12, var8.getColumnCount() - 1, true);
            if (var14 != null && var7 != null) {
               var14.width = var7.x + var7.width - var14.x;
               var8.repaint(var14);
            }
         } else {
            boolean var10000 = var2 instanceof JTree;
         }

      }
   }

   private class ComponentProperty {
      static final String OPAQUE = "SYCP_OPAQUE";
      static final String INSETS = "SYCP_INSETS";
      static final String BORDER = "SYCP_BORDER";
      static final String BUTTON_MARGIN = "SYCP_BUTTON_MARGIN";
      static final String TOOLBAR_SEPARATOR_SIZE = "SYCP_TOOLBAR_SEPARATOR_SIZE";
      static final String PROPERTY_CHANGE_LISTENERS = "SYCP_PROPERTY_CHANGE_LISTENERS";
      static final String COMPONENT_LISTENERS = "SYCP_COMPONENT_LISTENERS";
      static final String MOUSE_LISTENERS = "SYCP_MOUSE_LISTENERS";
      static final String MOUSE_MOTION_LISTENERS = "SYCP_MOUSE_MOTION_LISTENERS";
      static final String CONTAINER_LISTENERS = "SYCP_CONTAINER_LISTENERS";
      static final String POPUPMENU_LISTENERS = "SYCP_POPUPMENU_LISTENERS";
      static final String COMBOBOX_POPUPMENU_LISTENERS = "SYCP_COMBOBOX_POPUPMENU_LISTENERS";
      static final String TREE_CELL_RENDERER = "SYCP_TREE_CELL_RENDERER";
      static final String FOCUS_LISTENERS = "SYCP_FOCUS_LISTENERS";
      static final String LAYOUT_MANAGER = "SYCP_LAYOUT_MANAGER";
      static final String DESKTOP_MANAGER = "SYCP_DESKTOP_MANAGER";
      static final String TITLEDBORDER_TITLEPOSITION = "SYCP_TITLEDBORDER_TITLEPOSITION";
      static final String COMBOBOX_DEFAULT_RENDERER = "SYCP_COMBOBOX_DEFAULT_RENDERER";
      static final String COMBOBOX_DEFAULT_LAYOUT = "SYCP_COMBOBOX_DEFAULT_LAYOUT";
      static final String TABLE_OBJECT_DEFAULT_RENDERER = "SYCP_TABLE_OBJECT_DEFAULT_RENDERER";
      static final String TABLE_BOOLEAN_DEFAULT_RENDERER = "SYCP_TABLE_BOOLEAN_DEFAULT_RENDERER";
      static final String TABLE_OBJECT_DEFAULT_EDITOR = "SYCP_TABLE_OBJECT_DEFAULT_EDITOR";
      static final String TABLE_NUMBER_DEFAULT_EDITOR = "SYCP_TABLE_NUMBER_DEFAULT_EDITOR";
      static final String TABLE_COLUMN_MARGIN = "SYCP_TABLE_COLUMN_MARGIN";
      static final String TABLE_ROW_MARGIN = "SYCP_TABLE_ROW_MARGIN";
      private WeakReference<Component> component;
      private String propertyName;
      private WeakReference<Object> value;
      private int componentHashCode;

      ComponentProperty(Component var2, String var3, Object var4) {
         super();
         this.component = new WeakReference(var2);
         this.propertyName = var3;
         if (var2 instanceof JComponent) {
            ((JComponent)var2).putClientProperty(var3, var4);
         } else {
            this.value = new WeakReference(var4);
         }

         this.componentHashCode = (var2.hashCode() + var3).hashCode();
      }

      public boolean equals(Object var1) {
         return this.componentHashCode == var1.hashCode();
      }

      public int hashCode() {
         return this.componentHashCode;
      }
   }

   public class ComponentPropertyStore {
      private HashSet<StyleFactory.ComponentProperty> componentProperties;
      private boolean enabled = SyntheticaLookAndFeel.getBoolean("Synthetica.propertyStore.enabled", null, StyleFactory.componentPropertyStoreEnabled);
      private Thread cleanerThread;

      ComponentPropertyStore() {
         super();
         if (this.enabled) {
            this.reinit();
         }

      }

      void reinit() {
         this.stop();
         this.componentProperties = new HashSet(500);
         this.cleanerThread = new Thread() {
            public void run() {
               while(!this.isInterrupted()) {
                  synchronized(ComponentPropertyStore.this.componentProperties) {
                     Iterator var2 = ComponentPropertyStore.this.componentProperties.iterator();

                     while(var2.hasNext()) {
                        StyleFactory.ComponentProperty var3 = (StyleFactory.ComponentProperty)var2.next();
                        if (var3.component.get() == null) {
                           var2.remove();
                        }
                     }
                  }

                  if (this.isInterrupted()) {
                     break;
                  }

                  try {
                     sleep((long)StyleFactory.cleanerThreadDelay);
                  } catch (InterruptedException var4) {
                     this.interrupt();
                  }
               }

               ComponentPropertyStore.this.componentProperties.clear();
            }
         };
         this.cleanerThread.setName("SyntheticaCleanerThread");
         this.cleanerThread.setDaemon(true);
         this.cleanerThread.start();
      }

      void stop() {
         if (this.cleanerThread != null) {
            this.cleanerThread.interrupt();
         }

      }

      boolean removeComponentProperty(Component var1, String var2, Object var3) {
         StyleFactory.ComponentProperty var4 = StyleFactory.this.new ComponentProperty(var1, var2, var3);
         return this.componentProperties.remove(var4);
      }

      public void storeComponentProperty(Component var1, String var2) {
         if (this.enabled) {
            Object var3 = null;
            if (var2.equals("SYCP_OPAQUE")) {
               var3 = var1.isOpaque();
            } else if (var2.equals("SYCP_INSETS")) {
               var3 = ((JComponent)var1).getInsets();
            } else if (var2.equals("SYCP_BORDER")) {
               var3 = ((JComponent)var1).getBorder();
            } else if (var2.equals("SYCP_BUTTON_MARGIN")) {
               var3 = ((AbstractButton)var1).getMargin();
            } else if (var2.equals("SYCP_TOOLBAR_SEPARATOR_SIZE")) {
               var3 = ((Separator)var1).getSeparatorSize();
            } else if (var2.equals("SYCP_TREE_CELL_RENDERER")) {
               var3 = ((JTree)var1).getCellRenderer();
            } else if (var2.equals("SYCP_LAYOUT_MANAGER")) {
               var3 = ((JComponent)var1).getLayout();
            } else if (var2.equals("SYCP_DESKTOP_MANAGER")) {
               var3 = ((JDesktopPane)var1).getDesktopManager();
            } else if (var2.equals("SYCP_TITLEDBORDER_TITLEPOSITION")) {
               var3 = StyleFactory.this.getTitledBorder(((JComponent)var1).getBorder()).getTitlePosition();
            } else if (var2.equals("SYCP_TABLE_OBJECT_DEFAULT_RENDERER")) {
               var3 = ((JTable)var1).getDefaultRenderer(Object.class);
            } else if (var2.equals("SYCP_TABLE_BOOLEAN_DEFAULT_RENDERER")) {
               var3 = ((JTable)var1).getDefaultRenderer(Boolean.class);
            } else if (var2.equals("SYCP_TABLE_OBJECT_DEFAULT_EDITOR")) {
               var3 = ((JTable)var1).getDefaultEditor(Object.class);
            } else if (var2.equals("SYCP_TABLE_NUMBER_DEFAULT_EDITOR")) {
               var3 = ((JTable)var1).getDefaultEditor(Number.class);
            } else if (var2.equals("SYCP_TABLE_COLUMN_MARGIN")) {
               var3 = ((JTable)var1).getColumnModel().getColumnMargin();
            } else if (var2.equals("SYCP_TABLE_ROW_MARGIN")) {
               var3 = ((JTable)var1).getRowMargin();
            }

            StyleFactory.ComponentProperty var4 = StyleFactory.this.new ComponentProperty(var1, var2, var3);
            synchronized(this.componentProperties) {
               if (!this.componentProperties.contains(var4)) {
                  this.componentProperties.add(var4);
               }

            }
         }
      }

      void restoreAllComponentProperties() {
         synchronized(this.componentProperties) {
            for(StyleFactory.ComponentProperty var2 : this.componentProperties) {
               this.restoreComponentProperty(var2);
            }

            this.componentProperties.clear();
         }

         StyleFactory.this.prepareMetalLAFSwitch = false;
      }

      private void restoreComponentProperty(StyleFactory.ComponentProperty var1) {
         Component var2 = (Component)var1.component.get();
         if (var2 != null) {
            String var3 = var1.propertyName;
            Object var4 = null;
            if (var2 instanceof JComponent) {
               var4 = ((JComponent)var2).getClientProperty(var1.propertyName);
            } else {
               var4 = var1.value.get();
            }

            if (var3.equals("SYCP_OPAQUE") && StyleFactory.this.prepareMetalLAFSwitch) {
               StyleFactory.this.setOpaqueDefault4Metal((JComponent)var2);
            } else if (var3.equals("SYCP_INSETS")) {
               Insets var5 = (Insets)var4;
               ((JComponent)var2).setBorder(new EmptyBorder(var5));
            } else if (var3.equals("SYCP_BUTTON_MARGIN")) {
               Insets var8 = (Insets)var4;
               ((AbstractButton)var2).setMargin(var8);
            } else if (var3.equals("SYCP_TOOLBAR_SEPARATOR_SIZE")) {
               Dimension var9 = (Dimension)var4;
               if (var9 == null) {
                  var9 = new Dimension(10, 10);
               }

               ((Separator)var2).setSeparatorSize(var9);
            } else if (var3.equals("SYCP_TREE_CELL_RENDERER")) {
               TreeCellRenderer var10 = (TreeCellRenderer)var4;
               ((JTree)var2).setCellRenderer(var10);
            } else if (var3.equals("SYCP_PROPERTY_CHANGE_LISTENERS")) {
               PropertyChangeListener[] var11 = var2.getPropertyChangeListeners();

               for(int var6 = 0; var6 < var11.length; ++var6) {
                  if (var11[var6].getClass().getName().contains("synthetica")) {
                     var2.removePropertyChangeListener(var11[var6]);
                  }
               }
            } else if (var3.equals("SYCP_COMPONENT_LISTENERS")) {
               ComponentListener[] var12 = var2.getComponentListeners();

               for(int var30 = 0; var30 < var12.length; ++var30) {
                  if (var12[var30].getClass().getName().contains("synthetica")) {
                     var2.removeComponentListener(var12[var30]);
                  }
               }
            } else if (var3.equals("SYCP_CONTAINER_LISTENERS")) {
               ContainerListener[] var13 = ((JComponent)var2).getContainerListeners();

               for(int var31 = 0; var31 < var13.length; ++var31) {
                  if (var13[var31].getClass().getName().contains("synthetica")) {
                     ((JComponent)var2).removeContainerListener(var13[var31]);
                  }
               }
            } else if (var3.equals("SYCP_MOUSE_LISTENERS")) {
               MouseListener[] var14 = var2.getMouseListeners();

               for(int var32 = 0; var32 < var14.length; ++var32) {
                  if (var14[var32].getClass().getName().contains("synthetica")) {
                     var2.removeMouseListener(var14[var32]);
                  }
               }
            } else if (var3.equals("SYCP_MOUSE_MOTION_LISTENERS")) {
               MouseMotionListener[] var15 = var2.getMouseMotionListeners();

               for(int var33 = 0; var33 < var15.length; ++var33) {
                  if (var15[var33].getClass().getName().contains("synthetica")) {
                     var2.removeMouseMotionListener(var15[var33]);
                  }
               }
            } else if (var3.equals("SYCP_FOCUS_LISTENERS")) {
               FocusListener[] var16 = var2.getFocusListeners();

               for(int var34 = 0; var34 < var16.length; ++var34) {
                  if (var16[var34].getClass().getName().contains("synthetica")) {
                     var2.removeFocusListener(var16[var34]);
                  }
               }
            } else if (var3.equals("SYCP_POPUPMENU_LISTENERS")) {
               PopupMenuListener[] var17 = ((JPopupMenu)var2).getPopupMenuListeners();

               for(int var35 = 0; var35 < var17.length; ++var35) {
                  if (var17[var35].getClass().getName().contains("synthetica")) {
                     ((JPopupMenu)var2).removePopupMenuListener(var17[var35]);
                  }
               }
            } else if (var3.equals("SYCP_COMBOBOX_POPUPMENU_LISTENERS")) {
               PopupMenuListener[] var18 = ((JComboBox)var2).getPopupMenuListeners();

               for(int var36 = 0; var36 < var18.length; ++var36) {
                  if (var18[var36].getClass().getName().contains("synthetica")) {
                     ((JComboBox)var2).removePopupMenuListener(var18[var36]);
                  }
               }
            } else if (var3.equals("SYCP_LAYOUT_MANAGER")) {
               LayoutManager var19 = (LayoutManager)var4;
               ((JComponent)var2).setLayout(var19);
            } else if (var3.equals("SYCP_DESKTOP_MANAGER")) {
               DesktopManager var20 = (DesktopManager)var4;
               ((JDesktopPane)var2).setDesktopManager(var20);
            } else if (var3.equals("SYCP_TITLEDBORDER_TITLEPOSITION")) {
               int var21 = (Integer)var4;
               StyleFactory.this.getTitledBorder(((JComponent)var2).getBorder()).setTitlePosition(var21);
            } else if (var3.equals("SYCP_COMBOBOX_DEFAULT_RENDERER")) {
               ListCellRenderer var22 = (ListCellRenderer)var4;
               ((JComboBox)var2).setRenderer(var22);
            } else if (var3.equals("SYCP_COMBOBOX_DEFAULT_LAYOUT")) {
               LayoutManager var23 = (LayoutManager)var4;
               ((JComboBox)var2).setLayout(var23);
            } else if (var3.equals("SYCP_TABLE_OBJECT_DEFAULT_RENDERER")) {
               TableCellRenderer var24 = (TableCellRenderer)var4;
               ((JTable)var2).setDefaultRenderer(Object.class, var24);
            } else if (var3.equals("SYCP_TABLE_BOOLEAN_DEFAULT_RENDERER")) {
               TableCellRenderer var25 = (TableCellRenderer)var4;
               ((JTable)var2).setDefaultRenderer(Boolean.class, var25);
            } else if (var3.equals("SYCP_TABLE_OBJECT_DEFAULT_EDITOR")) {
               TableCellEditor var26 = (TableCellEditor)var4;
               ((JTable)var2).setDefaultEditor(Object.class, var26);
            } else if (var3.equals("SYCP_TABLE_NUMBER_DEFAULT_EDITOR")) {
               TableCellEditor var27 = (TableCellEditor)var4;
               ((JTable)var2).setDefaultEditor(Number.class, var27);
            } else if (var3.equals("SYCP_TABLE_COLUMN_MARGIN")) {
               int var28 = (Integer)var4;
               ((JTable)var2).getColumnModel().setColumnMargin(var28);
            } else if (var3.equals("SYCP_TABLE_ROW_MARGIN")) {
               int var29 = (Integer)var4;
               ((JTable)var2).setRowMargin(var29);
            }

         }
      }
   }

   private static class RepaintFocusListener implements FocusListener {
      private RepaintFocusListener() {
         super();
      }

      public void focusGained(FocusEvent var1) {
         this.repaintComponent(var1.getComponent());
      }

      public void focusLost(FocusEvent var1) {
         this.repaintComponent(var1.getComponent());
      }

      private void repaintComponent(Component var1) {
         String var2 = var1.getName();
         if ("ComboBox.textField".equals(var2) && var1.getParent() != null) {
            var1.getParent().repaint();
         } else if ("Spinner.formattedTextField".equals(var2) && var1.getParent() != null && var1.getParent().getParent() != null) {
            var1.getParent().getParent().repaint();
         } else if (var1.getParent() != null && var1.getParent().getParent() != null && var1.getParent().getParent() instanceof JScrollPane) {
            JScrollPane var3 = (JScrollPane)var1.getParent().getParent();
            var3.repaint();
         } else {
            var1.repaint();
         }

      }
   }

   private static class RepaintTextComponentPropertyChangeListener implements PropertyChangeListener {
      private RepaintTextComponentPropertyChangeListener() {
         super();
      }

      public void propertyChange(PropertyChangeEvent var1) {
         JComponent var2 = (JComponent)var1.getSource();
         if (var2.getParent() != null
            && var2.getParent() instanceof JViewport
            && ("enabled".equals(var1.getPropertyName()) || "background".equals(var1.getPropertyName()))) {
            var2.getParent().getParent().repaint();
         }

      }
   }

   private static class SyntheticaComboPopupBorder implements Border {
      private SyntheticaComboPopupBorder() {
         super();
      }

      public Insets getBorderInsets(Component var1) {
         return UIManager.getInsets("Synthetica.comboPopup.insets");
      }

      public boolean isBorderOpaque() {
         return false;
      }

      public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
         Color var7 = UIManager.getColor("Synthetica.comboPopup.border.color");
         if (var7 != null && this.getBorderInsets(var1).equals(new Insets(1, 1, 1, 1))) {
            var2.setColor(var7);
            var2.drawRect(var3, var4, var5 - 1, var6 - 1);
         }

      }
   }
}
