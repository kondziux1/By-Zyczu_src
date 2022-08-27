package de.javasoft.plaf.synthetica;

import de.javasoft.plaf.synthetica.batik.LinearGradientPaint;
import de.javasoft.plaf.synthetica.painter.CheckBoxPainter;
import de.javasoft.plaf.synthetica.painter.ImagePainter;
import de.javasoft.plaf.synthetica.painter.LabelPainter;
import de.javasoft.plaf.synthetica.painter.MenuPainter;
import de.javasoft.plaf.synthetica.painter.TabbedPanePainter;
import de.javasoft.plaf.synthetica.painter.TreePainter;
import de.javasoft.util.IVersion;
import de.javasoft.util.JavaVersion;
import de.javasoft.util.OS;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.AccessControlException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.ResourceBundle.Control;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.CellRendererPane;
import javax.swing.FocusManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolTip;
import javax.swing.JTree;
import javax.swing.JWindow;
import javax.swing.LookAndFeel;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIDefaults.LazyInputMap;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;
import javax.swing.plaf.synth.SynthStyleFactory;
import sun.swing.DefaultLookup;
import sun.swing.plaf.synth.DefaultSynthStyle;

public abstract class SyntheticaLookAndFeel extends SynthLookAndFeel {
   public static final Logger logger = Logger.getLogger(SyntheticaLookAndFeel.class.getName());
   private static final boolean JAVA5 = JavaVersion.JAVA5;
   private static final boolean JAVA6 = JavaVersion.JAVA6;
   private static final boolean JAVA6_OR_ABOVE = !JAVA5;
   private static final boolean JAVA6U10_OR_ABOVE = JavaVersion.JAVA6U10_OR_ABOVE;
   private static final boolean JAVA7_OR_ABOVE = !JAVA5 && !JAVA6;
   private static final boolean JAVA7U8_OR_ABOVE = JavaVersion.JAVA7U8_OR_ABOVE;
   private static Font defaultFont;
   private static boolean antiAliasEnabled;
   private static Dimension toolbarSeparatorDimension;
   private static boolean decorated = true;
   private static boolean extendedFileChooserEnabled = true;
   private static boolean rememberFileChooserPreferences = true;
   private static boolean useSystemFileIcons = true;
   private static boolean defaultsCompatibilityMode = true;
   private KeyEventPostProcessor altKeyEventProcessor;
   private PropertyChangeListener lafChangeListener;
   private UIDefaults orgDefaults;
   private static boolean debug = isSystemPropertySet("synthetica.debug");
   private static boolean outputVersion = isSystemPropertySet("synthetica.version");
   private static final boolean NOSTYLE = false;
   private static UIDefaults uiDefaults;
   private String lafXMLFile;

   static {
      Thread var0 = new Thread() {
         private boolean dialogOpen;
         private int millis = 15000;

         public void run() {
            while(true) {
               try {
                  sleep((long)this.millis);
                  this.millis = (1800 + new Random().nextInt(3600)) * 1000;
                  if (this.check()) {
                     return;
                  }

                  if (!this.dialogOpen) {
                     EventQueue.invokeLater(new Runnable() {
                        public void run() {
                           showDialog();
                        }
                     });
                  }
               } catch (InterruptedException var2) {
               }
            }
         }

         private boolean check() {
            String var1 = "Synthetica.license.info";
            String var2 = "Synthetica.license.key";

            try {
               String[] var3 = (String[])UIManager.get(var1);
               String var4 = (String)UIManager.get(var2);
               if (var3 == null) {
                  try {
                     Properties var5 = new Properties();
                     var5.load(this.getClass().getResourceAsStream("/de/javasoft/resources/license/synthetica.lic"));
                     var3 = ((String)var5.get(var1)).split(",");
                     var4 = (String)var5.get(var2);
                  } catch (Exception var15) {
                  }

                  if (var3 == null) {
                     try {
                        var3 = System.getProperty(var1).split(",");
                        var4 = System.getProperty(var2);
                     } catch (Exception var14) {
                     }
                  }
               }

               if (var3 != null && var4 != null) {
                  try {
                     TreeMap var18 = new TreeMap();

                     for(String var6 : var3) {
                        String[] var10 = var6.split("=");
                        var18.put(var10[0], var10[1]);
                     }

                     if (var4.equals(this.createKey(var18)) && "Synthetica".equals(var18.get("Product"))) {
                        String var19 = (String)var18.get("ExpireDate");
                        if (!"--.--.----".equals(var19)) {
                           SimpleDateFormat var20 = new SimpleDateFormat("dd.MM.yyyy");
                           Date var22 = var20.parse(var19);
                           if (new Date().after(var22)) {
                              throw new RuntimeException();
                           }
                        }

                        String var21 = (String)var18.get("LicenseRegistrationNumber");
                        HashMap var23 = new HashMap();
                        var23.put("347713735", "01.01.2013");

                        for(Entry var24 : var23.entrySet()) {
                           if (((String)var24.getKey()).equals(var21)) {
                              Date var11 = new Date();
                              Date var12 = new SimpleDateFormat("dd.MM.yyyy").parse((String)var24.getValue());
                              if (var11.after(var12) && var11.getTime() % 3L == 0L) {
                                 throw new RuntimeException();
                              }
                           }
                        }

                        String[] var25 = ((String)var18.get("MaxVersion")).split("\\.");
                        int var27 = Integer.parseInt(var25[0]);
                        int var28 = Integer.parseInt(var25[1]);
                        int var29 = Integer.parseInt(var25[2]);
                        SyntheticaLookAndFeel.Version var13 = new SyntheticaLookAndFeel.Version();
                        if (var13.getMajor() <= var27
                           && (var13.getMajor() != var27 || var13.getMinor() <= var28)
                           && (var13.getMajor() != var27 || var13.getMinor() != var28 || var13.getRevision() <= var29)) {
                           return true;
                        }

                        throw new RuntimeException();
                     }

                     throw new RuntimeException();
                  } catch (Exception var16) {
                  }
               }
            } catch (Exception var17) {
            }

            return false;
         }

         private String createKey(TreeMap<String, String> var1) throws Exception {
            StringBuilder var2 = new StringBuilder();

            for(Entry var3 : var1.entrySet()) {
               var2.append(var3).append("\n");
            }

            MessageDigest var8 = MessageDigest.getInstance("SHA-1");
            var8.reset();
            var8.update(var2.toString().getBytes("UTF-8"));
            byte[] var9 = var8.digest();
            StringBuilder var5 = new StringBuilder(var9.length * 2);

            for(int var6 = 0; var6 < var9.length; ++var6) {
               int var7 = var9[var6] & 255;
               if (var7 < 16) {
                  var5.append('0');
               }

               var5.append(Integer.toHexString(var7));
               if (var6 % 4 == 3 && var6 < var9.length - 1) {
                  var5.append("-");
               }
            }

            return var5.toString().toUpperCase();
         }

         private void showDialog() {
            this.dialogOpen = true;
            String var1 = "<html>It looks like you are using an invalid or outdated <b>Synthetica</b> license.<br>Please post your License Registration Number to the <b>Support Center</b><br> and request a valid license key.";
            Window var2 = FocusManager.getCurrentManager().getActiveWindow();
            JOptionPane.showMessageDialog(var2, var1, "Synthetica License Warning", 2);
            this.dialogOpen = false;
         }
      };
      var0.setDaemon(true);
      if (!SyntheticaRootPaneUI.isEvalDupa()) {
         var0.start();
      }

   }

   public SyntheticaLookAndFeel() throws ParseException {
      super();
   }

   public SyntheticaLookAndFeel(String var1) throws ParseException {
      super();
      this.lafXMLFile = var1;
      this.loadXMLConfig(var1);
      if (var1.endsWith(".xml")) {
         String var2 = var1.substring(0, var1.lastIndexOf("/"));
         this.loadAddonsXMLConfig(var2);
      }

      this.loadCustomXML();
   }

   public String getConfigFileName() {
      return this.lafXMLFile;
   }

   protected void loadCustomXML() throws ParseException {
   }

   protected void loadAddonsXMLConfig(String var1) throws ParseException {
      Class<SyntheticaLookAndFeel> var2 = SyntheticaLookAndFeel.class;
      String var3 = var1.replace("/", ".");
      if (var3.startsWith(".")) {
         var3 = var3.substring(1);
      } else {
         var3 = var2.getPackage().getName() + "." + var1.replace("/", ".");
      }

      try {
         Class var4 = Class.forName(var3 + ".XMLProvider");
         AddonsXMLProvider var5 = (AddonsXMLProvider)var4.newInstance();

         String[] var9;
         for(String var6 : var9 = var5.getXMLFileNames()) {
            this.load(var2.getResourceAsStream(var1 + "/" + var6), var2);
         }
      } catch (ClassNotFoundException var10) {
      } catch (InstantiationException var11) {
         throw new RuntimeException(var11);
      } catch (IllegalAccessException var12) {
         throw new RuntimeException(var12);
      }

   }

   protected void loadXMLConfig(String var1) throws ParseException {
      long var2 = System.currentTimeMillis();
      Class<SyntheticaLookAndFeel> var4 = SyntheticaLookAndFeel.class;
      if (var1.endsWith(".xml")) {
         this.load(var4.getResourceAsStream(var1), var4);
      } else {
         String[] var5 = new String[]{var1};
         String var6 = var4.getPackage().getName().replace(".", "/");

         for(String var7 : var5) {
            try {
               String var11 = var7.startsWith("/") ? var7.substring(1) : var6 + "/" + var7;
               Enumeration var12 = var4.getClassLoader().getResources(var11);

               while(var12.hasMoreElements()) {
                  URL var13 = (URL)var12.nextElement();
                  if (var13.getProtocol().equalsIgnoreCase("jar")) {
                     JarURLConnection var29 = (JarURLConnection)var13.openConnection();
                     JarFile var30 = var29.getJarFile();

                     for(JarEntry var31 : Collections.list(var30.entries())) {
                        if (var31.getName().endsWith(".xml") && var31.getName().startsWith(var11)) {
                           this.load(var4.getResourceAsStream("/" + var31.getName()), var4);
                        }
                     }
                  } else {
                     URI var14 = new URI(var13.toString());
                     File[] var15 = new File(var14.getPath()).listFiles(new FileFilter() {
                        public boolean accept(File var1) {
                           return var1.getName().endsWith(".xml");
                        }
                     });

                     for(File var16 : var15) {
                        this.load(var16.toURI().toURL().openStream(), var4);
                     }
                  }
               }
            } catch (Exception var24) {
               throw new RuntimeException(var24);
            }
         }
      }

      if (isSystemPropertySet("synthetica.enhancedXMLLookup")) {
         try {
            String var25 = "Synthetica.xml";
            this.load(var4.getResourceAsStream("/" + var25), var4);
            if (debug) {
               System.out.println("[Info] Found '" + var25 + "' configuration file.");
            }
         } catch (IllegalArgumentException var22) {
         } catch (Exception var23) {
            var23.printStackTrace();
         }

         String var26 = this.getClass().getName();

         try {
            String var28 = var26.substring(var26.lastIndexOf(".") + 1) + ".xml";
            this.load(var4.getResourceAsStream("/" + var28), var4);
            if (debug) {
               System.out.println("[Info] Found '" + var28 + "' configuration file.");
            }
         } catch (IllegalArgumentException var20) {
         } catch (Exception var21) {
            var21.printStackTrace();
         }
      }

      long var27 = System.currentTimeMillis();
      if (isSystemPropertySet("synthetica.loadTime")) {
         System.out.println("Time for loading LAF: " + (var27 - var2) + "ms");
      }

      if (isSystemPropertySet("synthetica.blockLAFChange")) {
         System.out.println("LAF switchings will be blocked!");
         this.blockLAFChange();
      }

      if (debug) {
         System.out.println("Synthetica debug mode is enabled!");
      }

      if (outputVersion) {
         System.out.format("Synthetica V%s\n%s V%s\n", this.getSyntheticaVersion().toString(), this.getName(), this.getVersion().toString());
      }

   }

   public abstract String getID();

   public abstract String getName();

   public String getDescription() {
      return "Synthetica - the extended Synth Look and Feel.";
   }

   public IVersion getVersion() {
      ResourceBundle var1 = getResourceBundle(this.getID() + "Version", new Locale("", ""));
      final int var2 = Integer.parseInt(var1.getString("major"));
      final int var3 = Integer.parseInt(var1.getString("minor"));
      final int var4 = Integer.parseInt(var1.getString("revision"));
      final int var5 = Integer.parseInt(var1.getString("build"));
      return new IVersion() {
         @Override
         public int getMajor() {
            return var2;
         }

         @Override
         public int getMinor() {
            return var3;
         }

         @Override
         public int getRevision() {
            return var4;
         }

         @Override
         public int getBuild() {
            return var5;
         }

         @Override
         public String toString() {
            return var2 + "." + var3 + "." + var4 + " Build " + var5;
         }
      };
   }

   static boolean shouldUpdateStyle(PropertyChangeEvent var0) {
      LookAndFeel var1 = UIManager.getLookAndFeel();
      return var1 instanceof SyntheticaLookAndFeel && ((SyntheticaLookAndFeel)var1).shouldUpdateStyleOnEvent(var0);
   }

   public boolean getSupportsWindowDecorations() {
      return true;
   }

   public UIDefaults getDefaults() {
      return super.getDefaults();
   }

   private void addResourceBundleToDefaults(String var1, Map<Object, Object> var2) {
      ResourceBundle var3 = getResourceBundle(var1, Locale.getDefault());
      Enumeration var4 = var3.getKeys();

      while(var4.hasMoreElements()) {
         String var5 = (String)var4.nextElement();
         String var6 = var3.getString(var5);
         var2.put(var5, var6);
      }

   }

   public void initialize() {
      super.initialize();
      this.orgDefaults = (UIDefaults)UIManager.getDefaults().clone();
      if (getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         try {
            Class var1 = Class.forName("sun.swing.DefaultLookup");
            Method var2 = var1.getMethod("setDefaultLookup", DefaultLookup.class);
            var2.invoke(var1, new SyntheticaDefaultLookup());
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }

      StyleFactory var8 = new StyleFactory(getStyleFactory());
      SynthLookAndFeel.setStyleFactory(var8);
      PopupFactory.install();
      this.lafChangeListener = new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            SyntheticaLookAndFeel.setFont(null);
            SyntheticaLookAndFeel.this.reinit();
            SyntheticaLookAndFeel.this.installSyntheticaDefaults();
            if (SyntheticaLookAndFeel.defaultsCompatibilityMode) {
               SyntheticaLookAndFeel.this.installCompatibilityDefaults();
            }

            String var2 = System.getProperty("synthetica.font");
            if (var2 != null) {
               SyntheticaLookAndFeel.setFont(new Font(var2, SyntheticaLookAndFeel.getFont().getStyle(), SyntheticaLookAndFeel.getFont().getSize()));
            }

            String var3 = System.getProperty("synthetica.fontsize");
            if (var3 != null) {
               SyntheticaLookAndFeel.setFont(SyntheticaLookAndFeel.getFont().deriveFont(Float.parseFloat(var3)));
            }

            SyntheticaLookAndFeel.updateAllWindowShapes();
         }
      };
      PropertyChangeListener[] var9 = UIManager.getPropertyChangeListeners();

      for(PropertyChangeListener var3 : var9) {
         UIManager.removePropertyChangeListener(var3);
      }

      UIManager.addPropertyChangeListener(this.lafChangeListener);

      for(PropertyChangeListener var10 : var9) {
         UIManager.addPropertyChangeListener(var10);
      }

   }

   private void reinit() {
      LabelPainter.reinitialize();
      CheckBoxPainter.reinitialize();
      MenuPainter.reinitialize();
      TreePainter.reinitialize();
      TabbedPanePainter.reinitialize();
      ImagePainter.clearImageCache();
      StyleFactory.reinitialize();
   }

   protected void installSyntheticaDefaults() {
      uiDefaults = UIManager.getDefaults();
      uiDefaults.put("HyperlinkUI", "de.javasoft.plaf.synthetica.HyperlinkUI");
      uiDefaults.put("StatusBarUI", "de.javasoft.plaf.synthetica.StatusBarUI");
      uiDefaults.put("LoginPanelUI", "de.javasoft.plaf.synthetica.LoginPanelUI");
      uiDefaults.put("LoginPaneUI", "de.javasoft.plaf.synthetica.LoginPanelUI");
      uiDefaults.put("MonthViewUI", "de.javasoft.plaf.synthetica.MonthViewUI");
      uiDefaults.put("TitledPanelUI", "de.javasoft.plaf.synthetica.TitledPanelUI");
      uiDefaults.put("HeaderUI", "de.javasoft.plaf.synthetica.HeaderUI");
      uiDefaults.put("swingx/GroupableTableHeaderUI", "de.javasoft.plaf.synthetica.GroupableTableHeaderUI");
      uiDefaults.put("swingx/TaskPaneUI", "de.javasoft.plaf.synthetica.TaskPaneUI");
      uiDefaults.put("swingx/TaskPaneContainerUI", "de.javasoft.plaf.synthetica.TaskPaneContainerUI");
      uiDefaults.put("swingx/TipOfTheDayUI", "de.javasoft.plaf.synthetica.TipOfTheDayUI");
      uiDefaults.put("Flexdock.view", "de.javasoft.plaf.synthetica.flexdock.ViewUI");
      uiDefaults.put("Flexdock.titlebar", "de.javasoft.plaf.synthetica.flexdock.TitlebarUI");
      uiDefaults.put("Flexdock.titlebar.button", "de.javasoft.plaf.synthetica.flexdock.ButtonUI");
      this.addResourceBundleToDefaults("synthetica", uiDefaults);
      if (UIManager.getBoolean("Synthetica.window.decoration")) {
         uiDefaults.put("RootPaneUI", "de.javasoft.plaf.synthetica.SyntheticaRootPaneUI");
      } else {
         decorated = false;
      }

      JFrame.setDefaultLookAndFeelDecorated(decorated);
      JDialog.setDefaultLookAndFeelDecorated(decorated);
      if (!UIManager.getBoolean("Synthetica.menuItem.useSynthUIDelegates")) {
         uiDefaults.put("MenuUI", "de.javasoft.plaf.synthetica.SyntheticaMenuUI");
         uiDefaults.put("MenuItemUI", "de.javasoft.plaf.synthetica.SyntheticaMenuItemUI");
         uiDefaults.put("CheckBoxMenuItemUI", "de.javasoft.plaf.synthetica.SyntheticaCheckBoxMenuItemUI");
         uiDefaults.put("RadioButtonMenuItemUI", "de.javasoft.plaf.synthetica.SyntheticaRadioButtonMenuItemUI");
      }

      extendedFileChooserEnabled = UIManager.getBoolean("Synthetica.extendedFileChooser.enabled");
      setExtendedFileChooserEnabled(extendedFileChooserEnabled);
      rememberFileChooserPreferences = UIManager.getBoolean("Synthetica.extendedFileChooser.rememberPreferences");
      useSystemFileIcons = UIManager.getBoolean("Synthetica.extendedFileChooser.useSystemFileIcons");
      UIDefaults var1 = UIManager.getLookAndFeelDefaults();

      for(Entry var2 : var1.entrySet()) {
         if (!uiDefaults.containsKey(var2.getKey())) {
            uiDefaults.put(var2.getKey(), var2.getValue());
         }
      }

      if (uiDefaults.get("Synthetica.activateMenuByAltKey") == null || uiDefaults.get("Synthetica.activateMenuByAltKey")) {
         this.altKeyEventProcessor = new AltKeyEventProcessor();
         KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this.altKeyEventProcessor);
         if (AltKeyEventProcessor.showMnemonicsOnAltKeyOnly()) {
            MenuSelectionManager.defaultManager().addChangeListener(new ChangeListener() {
               boolean repaint = true;

               public void stateChanged(ChangeEvent var1) {
                  Window var2 = FocusManager.getCurrentManager().getFocusedWindow();
                  JMenuBar var3 = null;
                  if (var2 instanceof JFrame) {
                     var3 = ((JFrame)var2).getJMenuBar();
                  } else if (var2 instanceof JDialog) {
                     var3 = ((JDialog)var2).getJMenuBar();
                  }

                  boolean var4 = MenuSelectionManager.defaultManager().getSelectedPath().length == 0;
                  if (var4) {
                     AltKeyEventProcessor.setMenuActive(false);
                  }

                  if (var3 != null && (this.repaint || var4)) {
                     var3.repaint();
                     this.repaint = var4;
                  }

               }
            });
         }
      }

   }

   protected void installCompatibilityDefaults() {
      UIDefaults var1 = UIManager.getDefaults();
      this.initSystemColorDefaults(UIManager.getDefaults());
      Object[] var2 = new Object[]{
         "OptionPane.errorSound",
         "OptionPane.informationSound",
         "OptionPane.questionSound",
         "OptionPane.warningSound",
         "InternalFrame.closeSound",
         "InternalFrame.maximizeSound",
         "InternalFrame.minimizeSound",
         "InternalFrame.restoreDownSound",
         "InternalFrame.restoreUpSound",
         "PopupMenu.popupSound",
         "MenuItem.commandSound",
         "CheckBoxMenuItem.commandSound",
         "RadioButtonMenuItem.commandSound"
      };
      Object[] var3 = new Object[]{
         "FileChooser.usesSingleFilePane",
         true,
         "FileChooser.ancestorInputMap",
         new LazyInputMap(new Object[]{"ESCAPE", "cancelSelection", "F2", "editFileName", "F5", "refresh", "ENTER", "approveSelection", "BACK_SPACE", "Go Up"}),
         "List.selectionForeground",
         new ColorUIResource(Color.white),
         "SplitPane.dividerSize",
         8,
         "List.focusCellHighlightBorder",
         new LineBorderUIResource(var1.getColor("Synthetica.list.focusCellHighlightBorder.color")),
         "Table.focusCellHighlightBorder",
         new LineBorderUIResource(var1.getColor("Synthetica.table.focusCellHighlightBorder.color")),
         "TitledBorder.border",
         new BorderUIResource(new SyntheticaTitledBorder()),
         "RootPane.defaultButtonWindowKeyBindings",
         new Object[]{"ENTER", "press", "released ENTER", "release", "ctrl ENTER", "press", "ctrl released ENTER", "release"},
         "controlLtHighlight",
         new ColorUIResource(Color.WHITE),
         "controlHighlight",
         new ColorUIResource(Color.LIGHT_GRAY),
         "controlShadow",
         new ColorUIResource(Color.DARK_GRAY),
         "controlDkShadow",
         new ColorUIResource(Color.BLACK),
         "ScrollBar.minimumThumbSize",
         new DimensionUIResource(8, 8),
         "ScrollBar.maximumThumbSize",
         new DimensionUIResource(4096, 4096),
         "AuditoryCues.cueList",
         var2,
         "AuditoryCues.defaultCueList",
         new Object[]{"OptionPane.informationSound", "OptionPane.warningSound", "OptionPane.questionSound", "OptionPane.errorSound"},
         "AuditoryCues.allAuditoryCues",
         var2,
         "AuditoryCues.noAuditoryCues",
         new Object[]{"mute"},
         "OptionPane.informationSound",
         "/javax/swing/plaf/metal/sounds/OptionPaneInformation.wav",
         "OptionPane.warningSound",
         "/javax/swing/plaf/metal/sounds/OptionPaneWarning.wav",
         "OptionPane.errorSound",
         "/javax/swing/plaf/metal/sounds/OptionPaneError.wav",
         "OptionPane.questionSound",
         "/javax/swing/plaf/metal/sounds/OptionPaneQuestion.wav",
         "InternalFrame.closeSound",
         "/javax/swing/plaf/metal/sounds/FrameClose.wav",
         "InternalFrame.maximizeSound",
         "/javax/swing/plaf/metal/sounds/FrameMaximize.wav",
         "InternalFrame.minimizeSound",
         "/javax/swing/plaf/metal/sounds/FrameMinimize.wav",
         "InternalFrame.restoreDownSound",
         "/javax/swing/plaf/metal/sounds/FrameRestoreDown.wav",
         "InternalFrame.restoreUpSound",
         "/javax/swing/plaf/metal/sounds/FrameRestoreUp.wav",
         "MenuItem.commandSound",
         "/javax/swing/plaf/metal/sounds/MenuItemCommand.wav",
         "PopupMenu.popupSound",
         "/javax/swing/plaf/metal/sounds/PopupMenuPopup.wav",
         "CheckBoxMenuItem.commandSound",
         "/javax/swing/plaf/metal/sounds/MenuItemCommand.wav",
         "RadioButtonMenuItem.commandSound",
         "/javax/swing/plaf/metal/sounds/MenuItemCommand.wav"
      };
      var1.putDefaults(var3);
      var1.putDefaults(SyntheticaInputDefaults.getInputMapDefaults());
      Object var4 = null;
      Object var5 = null;
      SynthStyleFactory var6 = getStyleFactory();
      String[] var7 = (String[])null;
      String[] var8 = (String[])null;
      Object var9 = null;
      JButton var10 = new JButton();
      SynthStyle var46 = var6.getStyle(var10, Region.BUTTON);
      SynthContext var69 = new SynthContext(var10, Region.BUTTON, var46, 1);
      Font var108 = var46.getFont(var69);
      var1.put("Button.font", var108);
      var1.put("Button.textShiftOffset", var46.getInt(var69, "Button.textShiftOffset", 0));
      var1.put("Button.foreground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      var69 = new SynthContext(var10, Region.BUTTON, var46, 8);
      var1.put("Button.disabledForeground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      JComboBox var11 = new JComboBox();
      var46 = var6.getStyle(var11, Region.COMBO_BOX);
      var69 = new SynthContext(var11, Region.COMBO_BOX, var46, 1024);
      Color var12 = var46.getColor(var69, ColorType.BACKGROUND);
      var1.put("ComboBox.background", var12);
      Color var13 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("ComboBox.foreground", var13);
      var1.put("ComboBox.font", var46.getFont(var69));
      var46 = var6.getStyle(var11, Region.LIST);
      var69 = new SynthContext(var11, Region.LIST, var46, 512);
      var1.put("ComboBox.selectionForeground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      var1.put("ComboBox.selectionBackground", var46.getColor(var69, ColorType.TEXT_BACKGROUND));
      JLabel var14 = new JLabel();
      var46 = var6.getStyle(var14, Region.LABEL);
      var69 = new SynthContext(var14, Region.LABEL, var46, 1024);
      var108 = var46.getFont(var69);
      var1.put("Label.font", var108);
      var1.put("JXMonthView.font", var108);
      var1.put("JXTitledPanel.titleFont", var108.deriveFont(1));
      Color var15 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("Label.foreground", var15);
      var69 = new SynthContext(var14, Region.LABEL, var46, 8);
      var15 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("Label.disabledForeground", var15);
      Insets var16 = var14.getInsets();
      var14.setName("Table.cellRenderer");
      if (!var16.equals(var14.getInsets())) {
         Border var17 = var14.getBorder();
         Insets var18 = var17.getBorderInsets(var14);
         var1.put("Table.cellNoFocusBorder", var17);
         Border var19 = var1.getBorder("Table.focusCellHighlightBorder");
         Insets var20 = var19.getBorderInsets(var14);
         Insets var21 = new Insets(var18.top - var20.top, var18.left - var20.left, var18.bottom - var20.bottom, var18.right - var20.right);
         BorderUIResource var117 = new BorderUIResource(new CompoundBorder(var19, new EmptyBorder(var21)));
         var1.put("Table.focusCellHighlightBorder", var117);
      }

      JPanel var115 = new JPanel();
      var46 = var6.getStyle(var115, Region.PANEL);
      var69 = new SynthContext(var115, Region.PANEL, var46, 1024);
      Color var116 = var46.getColor(var69, ColorType.BACKGROUND);
      var1.put("Panel.background", var116);
      var1.put("SplitPane.background", var116);
      var1.put("Label.background", var116);
      var1.put("ColorChooser.swatchesDefaultRecentColor", var116);
      var1.put("control", var116);
      Color var118 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("Panel.foreground", var118);
      var108 = var46.getFont(var69);
      var1.put("Panel.font", var108);
      var1.put("TitledBorder.font", this.getTitledBorderFont(var108));
      JList var119 = new JList();
      var46 = var6.getStyle(var119, Region.LIST);
      var69 = new SynthContext(var119, Region.LIST, var46, 1024);
      Color var120 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("List.background", var120);
      Color var22 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("List.foreground", var22);
      var69 = new SynthContext(var119, Region.LIST, var46, 512);
      var120 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("List.selectionBackground", var120);
      var22 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("List.selectionForeground", var22);
      if (var1.get("List.dropLineColor") == null) {
         var1.put("List.dropLineColor", new ColorUIResource(Color.RED));
      }

      JTable var23 = new JTable();
      var46 = var6.getStyle(var23, Region.TABLE_HEADER);
      var69 = new SynthContext(var23, Region.TABLE_HEADER, var46, 1024);
      Color var24 = var46.getColor(var69, ColorType.BACKGROUND);
      var1.put("TableHeader.background", var24);
      Color var25 = var46.getColor(var69, ColorType.FOREGROUND);
      var1.put("TableHeader.foreground", var25);
      var1.put("TableHeader.font", var46.getFont(var69));
      var46 = var6.getStyle(var23, Region.TABLE);
      var69 = new SynthContext(var23, Region.TABLE, var46, 1024);
      var1.put("Table.gridColor", var46.get(var69, "Table.gridColor"));
      Color var26 = var46.getColor(var69, ColorType.BACKGROUND);
      var1.put("Table.background", var26);
      Color var27 = var46.getColor(var69, ColorType.FOREGROUND);
      var1.put("Table.foreground", var27);
      var1.put("Table.font", var46.getFont(var69));
      var69 = new SynthContext(var23, Region.TABLE, var46, 512);
      var26 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("Table.selectionBackground", var26);
      var27 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("Table.selectionForeground", var27);
      Object var28 = null;
      if (var1.get("Synthetica.table.scrollPaneBorder.className") != null) {
         try {
            var28 = (Border)Class.forName(var1.getString("Synthetica.table.scrollPaneBorder.className")).newInstance();
         } catch (Exception var45) {
            throw new RuntimeException(var45);
         }
      } else if (var1.getBoolean("Synthetica.table.useScrollPaneBorder")) {
         var28 = new JScrollPane().getBorder();
      } else {
         var28 = new LineBorderUIResource(var1.getColor("Synthetica.table.scrollPane.border.color"));
      }

      var1.put("Table.scrollPaneBorder", var28);
      if (var1.getBoolean("Synthetica.scrollPane.cornerPainter.enabled")) {
         var1.put("Table.scrollPaneCornerComponent", SyntheticaScrollPaneCorner.TableUpperTrailingCorner.class);
      }

      if (var1.get("JXTable.rowHeight") == null) {
         Integer var29 = (Integer)var46.get(var69, "Table.rowHeight");
         if (var29 != null) {
            var1.put("JXTable.rowHeight", var29);
         }
      }

      var1.put("ColumnHeaderRenderer.upIcon", loadIcon("Synthetica.arrow.up"));
      var1.put("ColumnHeaderRenderer.downIcon", loadIcon("Synthetica.arrow.down"));
      Icon var126 = loadIcon("Synthetica.tableHeader.ascendingSort.icon", null);
      var1.put("Table.ascendingSortIcon", var126 == null ? loadIcon("Synthetica.arrow.up") : var126);
      var126 = loadIcon("Synthetica.tableHeader.descendingSort.icon", null);
      var1.put("Table.descendingSortIcon", var126 == null ? loadIcon("Synthetica.arrow.down") : var126);
      String var30 = var1.getString("Synthetica.tableHeader.cellBorder.className");
      if (var1.get("TableHeader.cellBorder") == null && var30 != null) {
         try {
            var1.put("TableHeader.cellBorder", Class.forName(var30).newInstance());
         } catch (Exception var44) {
            throw new RuntimeException(var44);
         }
      }

      if (var1.get("Table.dropLineColor") == null) {
         var1.put("Table.dropLineColor", var1.get("Table.foreground"));
      }

      if (var1.get("Table.dropLineShortColor") == null) {
         var1.put("Table.dropLineShortColor", new ColorUIResource(Color.RED));
      }

      JTree var31 = new JTree();
      var46 = var6.getStyle(var31, Region.TREE);
      if (getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         var108 = ((DefaultSynthStyle)var46).getFont(var31, Region.TREE, 1);
         var1.put("Tree.font", var108);
      }

      var69 = new SynthContext(var31, Region.TREE, var46, 1024);
      var1.put("Tree.foreground", var46.getColor(var69, ColorType.FOREGROUND));
      var1.put("Tree.background", var46.getColor(var69, ColorType.BACKGROUND));
      var8 = new String[]{"Tree.expandedIcon", "Tree.collapsedIcon"};
      this.putIcons2Defaults(var1, var8, var8, var46, var69);
      var1.put("Tree.rowHeight", var46.get(var69, "Tree.rowHeight"));
      var1.put("Tree.leftChildIndent", var46.get(var69, "Tree.leftChildIndent"));
      var1.put("Tree.rightChildIndent", var46.get(var69, "Tree.rightChildIndent"));
      var46 = var6.getStyle(var31, Region.TREE_CELL);
      var69 = new SynthContext(var31, Region.TREE_CELL, var46, 1024);
      var1.put("Tree.textForeground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      var1.put("Tree.textBackground", var46.getColor(var69, ColorType.TEXT_BACKGROUND));
      var69 = new SynthContext(var31, Region.TREE_CELL, var46, 512);
      var1.put("Tree.selectionForeground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      var1.put("Tree.selectionBackground", var46.getColor(var69, ColorType.TEXT_BACKGROUND));
      var1.put("Tree.hash", var1.get("Synthetica.tree.line.color.vertical"));
      if (var1.get("Tree.dropLineColor") == null) {
         var1.put("Tree.dropLineColor", new ColorUIResource(Color.RED));
      }

      JInternalFrame var32 = new JInternalFrame();
      var46 = var6.getStyle(var32, Region.INTERNAL_FRAME_TITLE_PANE);
      if (getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         Color var33 = ((DefaultSynthStyle)var46).getColor(var32, Region.INTERNAL_FRAME_TITLE_PANE, 512, ColorType.FOREGROUND);
         var1.put("InternalFrame.activeTitleForeground", var33);
         var33 = ((DefaultSynthStyle)var46).getColor(var32, Region.INTERNAL_FRAME_TITLE_PANE, 1024, ColorType.FOREGROUND);
         var1.put("InternalFrame.inactiveTitleForeground", var33);
         Color var34 = ((DefaultSynthStyle)var46).getColor(var32, Region.INTERNAL_FRAME_TITLE_PANE, 512, ColorType.BACKGROUND);
         var1.put("InternalFrame.activeTitleBackground", var34);
         var1.put("activeCaption", var34);
         var34 = ((DefaultSynthStyle)var46).getColor(var32, Region.INTERNAL_FRAME_TITLE_PANE, 1024, ColorType.BACKGROUND);
         var1.put("InternalFrame.inactiveTitleBackground", var34);
         var1.put("inactiveCaption", var34);
      }

      var69 = new SynthContext(var32, Region.INTERNAL_FRAME_TITLE_PANE, var46, 1024);
      var7 = new String[]{
         "InternalFrameTitlePane.closeIcon",
         "InternalFrameTitlePane.maximizeIcon",
         "InternalFrameTitlePane.minimizeIcon",
         "InternalFrameTitlePane.iconifyIcon"
      };
      var8 = new String[]{"InternalFrame.closeIcon", "InternalFrame.maximizeIcon", "InternalFrame.minimizeIcon", "InternalFrame.iconifyIcon"};
      this.putIcons2Defaults(var1, var7, var8, var46, var69);
      var46 = var6.getStyle(var32, Region.INTERNAL_FRAME);
      var69 = new SynthContext(var32, Region.INTERNAL_FRAME, var46, 1024);
      var8 = new String[]{"InternalFrame.icon"};
      this.putIcons2Defaults(var1, var8, var8, var46, var69);
      JMenu var129 = new JMenu();
      var46 = var6.getStyle(var129, Region.MENU);
      var69 = new SynthContext(var129, Region.MENU, var46, 1024);
      var1.put("MenuItem.background", var46.getColor(var69, ColorType.BACKGROUND));
      Color var131 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("Menu.foreground", var131);
      var1.put("MenuItem.foreground", var131);
      JOptionPane var35 = new JOptionPane();
      var46 = var6.getStyle(var35, Region.OPTION_PANE);
      var69 = new SynthContext(var35, Region.OPTION_PANE, var46, 1024);
      var8 = new String[]{"OptionPane.informationIcon", "OptionPane.questionIcon", "OptionPane.warningIcon", "OptionPane.errorIcon"};
      this.putIcons2Defaults(var1, var8, var8, var46, var69);
      JCheckBox var36 = new JCheckBox();
      var46 = var6.getStyle(var36, Region.CHECK_BOX);
      var69 = new SynthContext(var36, Region.CHECK_BOX, var46, 1024);
      var8 = new String[]{"CheckBox.icon"};
      this.putIcons2Defaults(var1, var8, var8, var46, var69);
      JRadioButton var37 = new JRadioButton();
      var46 = var6.getStyle(var37, Region.RADIO_BUTTON);
      var69 = new SynthContext(var37, Region.RADIO_BUTTON, var46, 1024);
      var8 = new String[]{"RadioButton.icon"};
      this.putIcons2Defaults(var1, var8, var8, var46, var69);
      JTabbedPane var38 = new JTabbedPane();
      var46 = var6.getStyle(var38, Region.TABBED_PANE_TAB_AREA);
      var69 = new SynthContext(var38, Region.TABBED_PANE_TAB_AREA, var46, 1024);
      var1.put("TabbedPane.tabAreaInsets", var46.getInsets(var69, null));
      var46 = var6.getStyle(var38, Region.TABBED_PANE_TAB);
      var69 = new SynthContext(var38, Region.TABBED_PANE_TAB, var46, 1024);
      var1.put("TabbedPane.tabInsets", var46.getInsets(var69, null));
      var46 = var6.getStyle(var38, Region.TABBED_PANE_TAB);
      var69 = new SynthContext(var38, Region.TABBED_PANE_TAB, var46, 512);
      var1.put("TabbedPane.selectedTabPadInsets", var46.getInsets(var69, null));
      var46 = var6.getStyle(var38, Region.TABBED_PANE_CONTENT);
      var69 = new SynthContext(var38, Region.TABBED_PANE_CONTENT, var46, 1024);
      var1.put("TabbedPane.contentBorderInsets", var46.getInsets(var69, null));
      var1.put("TabbedPane.foreground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      var1.put("TabbedPane.shadow", Color.GRAY);
      JTextField var39 = new JTextField();
      var1.put("TextField.border", var39.getBorder());
      var1.put("FormattedTextField.border", var39.getBorder());
      var46 = var6.getStyle(var39, Region.TEXT_FIELD);
      var69 = new SynthContext(var39, Region.TEXT_FIELD, var46, 1024);
      var108 = var46.getFont(var69);
      var1.put("TextField.font", var108);
      var1.put("FormattedTextField.font", var108);
      var1.put("PasswordField.font", var108);
      var1.put("TextArea.font", var108);
      Color var40 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("TextField.foreground", var40);
      var1.put("FormattedTextField.foreground", var40);
      var1.put("PasswordField.foreground", var40);
      var1.put("TextArea.foreground", var40);
      var40 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("TextField.background", var40);
      var1.put("FormattedTextField.background", var40);
      var1.put("PasswordField.background", var40);
      var1.put("TextArea.background", var40);
      var69 = new SynthContext(var39, Region.TEXT_FIELD, var46, 8);
      var40 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("TextField.inactiveForeground", var40);
      var1.put("FormattedTextField.inactiveForeground", var40);
      var1.put("PasswordField.inactiveForeground", var40);
      var1.put("TextArea.inactiveForeground", var40);
      var40 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("TextField.inactiveBackground", var40);
      var1.put("FormattedTextField.inactiveBackground", var40);
      var1.put("PasswordField.inactiveBackground", var40);
      var1.put("TextArea.inactiveBackground", var40);
      var69 = new SynthContext(var39, Region.TEXT_FIELD, var46, 512);
      var40 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("TextField.selectionForeground", var40);
      var1.put("FormattedTextField.selectionForeground", var40);
      var1.put("PasswordField.selectionForeground", var40);
      var1.put("TextArea.selectionForeground", var40);
      var40 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("TextField.selectionBackground", var40);
      var1.put("FormattedTextField.selectionBackground", var40);
      var1.put("PasswordField.selectionBackground", var40);
      var1.put("TextArea.selectionBackground", var40);
      var1.put("textHighlight", var40);
      JTextPane var41 = new JTextPane();
      var46 = var6.getStyle(var39, Region.TEXT_PANE);
      var69 = new SynthContext(var41, Region.TEXT_PANE, var46, 1024);
      var108 = var46.getFont(var69);
      var1.put("TextPane.font", var108);
      var1.put("EditorPane.font", var108);
      var40 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("TextPane.foreground", var40);
      var1.put("EditorPane.foreground", var40);
      var40 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("TextPane.background", var40);
      var1.put("EditorPane.background", var40);
      var69 = new SynthContext(var41, Region.TEXT_PANE, var46, 8);
      var40 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("TextPane.inactiveForeground", var40);
      var1.put("EditorPane.inactiveForeground", var40);
      var40 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("TextPane.inactiveBackground", var40);
      var1.put("EditorPane.inactiveBackground", var40);
      var69 = new SynthContext(var41, Region.TEXT_PANE, var46, 512);
      var40 = var46.getColor(var69, ColorType.TEXT_FOREGROUND);
      var1.put("TextPane.selectionForeground", var40);
      var1.put("EditorPane.selectionForeground", var40);
      var40 = var46.getColor(var69, ColorType.TEXT_BACKGROUND);
      var1.put("TextPane.selectionBackground", var40);
      var1.put("EditorPane.selectionBackground", var40);
      JToolTip var42 = new JToolTip();
      var69 = new SynthContext(var42, Region.TOOL_TIP, var46, 1024);
      var46 = var6.getStyle(var42, Region.TOOL_TIP);
      var1.put("ToolTip.font", var46.getFont(var69));
      var1.put("ToolTip.foreground", var46.getColor(var69, ColorType.TEXT_FOREGROUND));
      Color var43 = var46.getColor(var69, ColorType.BACKGROUND);
      var1.put("ToolTip.background", var43 == null ? null : new Color(var43.getRGB()));
   }

   private void putIcons2Defaults(UIDefaults var1, String[] var2, String[] var3, SynthStyle var4, SynthContext var5) {
      for(int var6 = 0; var6 < var2.length; ++var6) {
         Icon var7 = var4.getIcon(var5, var2[var6]);
         var1.put(var3[var6], var7);
      }

   }

   private Font getTitledBorderFont(Font var1) {
      if (UIManager.get("Synthetica.titledBorder.title.fontName") != null) {
         var1 = new FontUIResource(new Font(UIManager.getString("Synthetica.titledBorder.title.fontName"), ((Font)var1).getStyle(), ((Font)var1).getSize()));
      }

      if (UIManager.get("Synthetica.titledBorder.title.fontSize") != null) {
         float var2 = scaleFontSize((float)UIManager.getInt("Synthetica.titledBorder.title.fontSize"));
         var1 = new FontUIResource(((Font)var1).deriveFont(var2));
      }

      if (UIManager.get("Synthetica.titledBorder.title.fontStyle") != null) {
         var1 = new FontUIResource(((Font)var1).deriveFont(UIManager.getInt("Synthetica.titledBorder.title.fontStyle")));
      }

      return (Font)var1;
   }

   public void uninitialize() {
      KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this.altKeyEventProcessor);
      ((StyleFactory)SynthLookAndFeel.getStyleFactory()).uninitialize();
      UIDefaults var1 = UIManager.getDefaults();
      Object var2 = UIManager.get("Synthetica.license.info");
      Object var3 = UIManager.get("Synthetica.license.key");
      Object var4 = UIManager.get("SyntheticaAddons.license.info");
      Object var5 = UIManager.get("SyntheticaAddons.license.key");
      var1.clear();
      var1.putAll(this.orgDefaults);
      if (var2 != null) {
         UIManager.put("Synthetica.license.info", var2);
         UIManager.put("Synthetica.license.key", var3);
      }

      if (var4 != null) {
         var1.put("SyntheticaAddons.license.info", var4);
         var1.put("SyntheticaAddons.license.key", var5);
      }

      UIManager.removePropertyChangeListener(this.lafChangeListener);
      super.uninitialize();
   }

   protected boolean shouldUpdateStyleOnEvent(PropertyChangeEvent var1) {
      String var2 = var1.getPropertyName();
      if ("name".equals(var2) || "componentOrientation".equals(var2) || "Synthetica.style".equals(var2)) {
         return true;
      } else {
         return "ancestor".equals(var2) && var1.getNewValue() != null ? this.shouldUpdateStyleOnAncestorChanged() : false;
      }
   }

   private void blockLAFChange() {
      UIManager.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            Object var2 = var1.getNewValue();
            if (!(var2 instanceof SyntheticaLookAndFeel)) {
               try {
                  UIManager.setLookAndFeel(SyntheticaLookAndFeel.this);
               } catch (Exception var4) {
                  var4.printStackTrace();
               }
            }

         }
      });
   }

   public static SynthContext createContext(JComponent var0, Region var1, int var2) {
      SynthStyle var3 = getStyle(var0, var1);
      return new SynthContext(var0, var1, var3, var2);
   }

   public static void setFont(String var0, int var1) {
      setFont(new FontUIResource(var0, 0, var1));
   }

   public static void setFont(Font var0) {
      setFont(var0, true);
   }

   public static void setFont(Font var0, boolean var1) {
      if (var0 == null) {
         defaultFont = null;
      } else {
         if (var1) {
            float var2 = scaleFontSize(var0.getSize2D());
            defaultFont = var0.deriveFont(var2);
         } else {
            defaultFont = var0;
         }

         LookAndFeel var3 = UIManager.getLookAndFeel();
         if (defaultsCompatibilityMode && var3 instanceof SyntheticaLookAndFeel) {
            ((SyntheticaLookAndFeel)var3).installCompatibilityDefaults();
         }

      }
   }

   public static Font getFont() {
      return defaultFont;
   }

   public static String getFontName() {
      return defaultFont == null ? null : defaultFont.getFontName();
   }

   public static int getFontSize() {
      return defaultFont == null ? 0 : defaultFont.getSize();
   }

   public static boolean getAntiAliasEnabled() {
      return antiAliasEnabled;
   }

   public static void setAntiAliasEnabled(boolean var0) {
      antiAliasEnabled = var0;
      System.setProperty("awt.useSystemAAFontSettings", "false");
   }

   public static void setWindowsDecorated(boolean var0) {
      decorated = var0;
   }

   public static boolean getExtendedFileChooserEnabled() {
      return extendedFileChooserEnabled;
   }

   public static void setExtendedFileChooserEnabled(boolean var0) {
      extendedFileChooserEnabled = var0;
      if (extendedFileChooserEnabled) {
         UIDefaults var1 = UIManager.getDefaults();
         String var2 = null;
         if (OS.getCurrentOS() == OS.Mac) {
            String var3 = "ch.randelshofer.quaqua.panther.QuaquaPantherFileChooserUI";
            var2 = var1.get("Synthetica.fileChooserUI") == null ? var3 : var1.getString("Synthetica.fileChooserUI");

            try {
               Class.forName(var2);
               SwingUtilities.invokeLater(new Runnable() {
                  public void run() {
                     SyntheticaLookAndFeel.initQuaquaFileChooser(SyntheticaLookAndFeel.uiDefaults);
                  }
               });
            } catch (ClassNotFoundException var5) {
               var2 = null;
            }
         }

         var2 = var2 == null ? "de.javasoft.plaf.synthetica.filechooser.SyntheticaFileChooserUI" : var2;
         var1.put("FileChooserUI", var2);
      } else {
         UIManager.getDefaults().put("FileChooserUI", "javax.swing.plaf.metal.MetalFileChooserUI");
      }

   }

   private static void initQuaquaFileChooser(UIDefaults var0) {
      final Color var1 = var0.getColor("Label.foreground");
      var0.addResourceBundle("ch.randelshofer.quaqua.Labels");
      var0.put("Browser.expandedIcon", loadIcon("Synthetica.arrow.right"));
      var0.put("Browser.sizeHandleIcon", new Icon() {
         public void paintIcon(Component var1x, Graphics var2, int var3, int var4) {
            var2.setColor(var1);
            var2.drawLine(5, 4, 5, 10);
            var2.drawLine(8, 4, 8, 10);
         }

         public int getIconWidth() {
            return 15;
         }

         public int getIconHeight() {
            return 15;
         }
      });
      var0.put("Browser.selectionBackground", new ColorUIResource(56, 117, 215));
      var0.put("Browser.selectionForeground", new ColorUIResource(255, 255, 255));
      var0.put("Browser.inactiveSelectionBackground", new ColorUIResource(208, 208, 208));
      var0.put("Browser.inactiveSelectionForeground", new ColorUIResource(0, 0, 0));
      var0.put("FileChooser.previewLabelForeground", var1);
      var0.put("FileChooser.previewValueForeground", var1);
      FontUIResource var2 = new FontUIResource("Lucida Grande", 0, 11);
      var0.put("FileChooser.previewLabelFont", var2);
      var0.put("FileChooser.previewValueFont", var2);
      var0.put("FileChooser.previewLabelInsets", new InsetsUIResource(0, 0, 0, 4));
      var0.put("FileChooser.cellTipOrigin", new Point(18, 1));
      var0.put("FileChooser.autovalidate", Boolean.TRUE);
      var0.put("Sheet.showAsSheet", Boolean.TRUE);
   }

   public Icon getDisabledIcon(JComponent var1, Icon var2) {
      if (var2 != null && getBoolean("Synthetica.translucency4DisabledIcons.enabled", var1)) {
         int var3 = getInt("Synthetica.translucency4DisabledIcons.alpha", var1, 50);
         BufferedImage var4 = new BufferedImage(var2.getIconWidth(), var2.getIconHeight(), 2);
         Graphics2D var5 = (Graphics2D)var4.getGraphics();
         AlphaComposite var6 = AlphaComposite.getInstance(3, (float)var3 / 100.0F);
         var5.setComposite(var6);
         var2.paintIcon(var1, var5, 0, 0);
         var5.dispose();
         return new ImageIcon(var4);
      } else {
         return super.getDisabledIcon(var1, var2);
      }
   }

   public static boolean getRememberFileChooserPreferences() {
      return rememberFileChooserPreferences;
   }

   public static void setRememberFileChooserPreferences(boolean var0) {
      rememberFileChooserPreferences = var0;
   }

   public static boolean getUseSystemFileIcons() {
      return useSystemFileIcons;
   }

   public static void setUseSystemFileIcons(boolean var0) {
      useSystemFileIcons = var0;
   }

   public static void setDefaultsCompatibilityMode(boolean var0) {
      defaultsCompatibilityMode = var0;
   }

   public static boolean getDefaultsCompatibilityMode() {
      return defaultsCompatibilityMode;
   }

   public static void setToolbarSeparatorDimension(Dimension var0) {
      toolbarSeparatorDimension = var0;
   }

   public static Dimension getToolbarSeparatorDimension() {
      return toolbarSeparatorDimension;
   }

   private static ResourceBundle getResourceBundle(String var0, Locale var1) {
      if (var1 == null) {
         var1 = Locale.getDefault();
      }

      String var2 = "de/javasoft/plaf/synthetica/resourceBundles/" + var0;
      return JAVA6_OR_ABOVE ? ResourceBundle.getBundle(var2, var1, Control.getControl(Control.FORMAT_PROPERTIES)) : ResourceBundle.getBundle(var2, var1);
   }

   public IVersion getSyntheticaVersion() {
      return new SyntheticaLookAndFeel.Version();
   }

   public static String getStyleName(Component var0) {
      String var1 = ((Component)var0).getName();
      SynthStyleFactory var2 = getStyleFactory();
      if (!(var2 instanceof StyleFactory)) {
         return var1;
      } else {
         StyleFactory var3 = (StyleFactory)var2;
         if (var3 != null && var3.isSyntheticaStyleNameSupportEnabled()) {
            if (var0 instanceof Window) {
               if (var0 instanceof JFrame) {
                  var0 = ((JFrame)var0).getRootPane();
               } else if (var0 instanceof JDialog) {
                  var0 = ((JDialog)var0).getRootPane();
               } else if (var0 instanceof JWindow) {
                  var0 = ((JWindow)var0).getRootPane();
               }
            }

            if (var0 instanceof JComponent) {
               Object var4 = ((JComponent)var0).getClientProperty("Synthetica.style");
               if (var4 != null) {
                  var1 = (String)var4;
               }
            }
         }

         return var1;
      }
   }

   public static SyntheticaLookAndFeel.JVMCompatibilityMode getJVMCompatibilityMode() {
      String var0 = UIManager.getString("Synthetica.JVMCompatibilityMode");
      return var0 == null ? SyntheticaLookAndFeel.JVMCompatibilityMode.SUN : SyntheticaLookAndFeel.JVMCompatibilityMode.valueOf(var0);
   }

   public static <T> T getClientProperty(String var0, JComponent var1, T var2) {
      Object var3 = var1.getClientProperty(var0);
      return (T)(var3 != null ? var3 : var2);
   }

   public static Icon loadIcon(String var0) {
      return loadIcon(var0, null);
   }

   public static Icon loadIcon(String var0, Component var1) {
      IconUIResource var2 = null;
      String var3 = getString(var0, var1);
      if (var3 != null) {
         String[] var4 = var3.split(",");
         URL var5 = SyntheticaLookAndFeel.class.getResource(var4[var4.length - 1]);
         Object var8;
         if (var5 == null) {
            try {
               var8 = (Icon)Class.forName(var3).newInstance();
            } catch (Exception var7) {
               throw new RuntimeException(var7);
            }
         } else {
            var8 = new ImageIcon(var5);
         }

         var2 = new IconUIResource((Icon)var8);
      }

      return var2;
   }

   public static Object get(String var0, String var1, String var2, boolean var3) {
      String var4 = var0;
      char var5 = '.';
      int var6 = var0.length();

      while(var6 > -1) {
         StringBuilder var7 = new StringBuilder("Synthetica.");
         var0 = var0.substring(0, var6);
         var7.append(var0);
         if (var1 != null) {
            var7.append(var5);
            var7.append(var1);
         }

         if (var2 != null) {
            var7.append(var5);
            var7.append(var2);
         }

         if (lookup(var7.toString()) != null || !var3) {
            return lookup(var7.toString());
         }

         var6 = var0.lastIndexOf(var5);
         if (var6 == -1 && var2 != null) {
            var2 = null;
            var0 = var4;
            var6 = var4.length();
         }
      }

      return null;
   }

   public static String getString(String var0, String var1, String var2, boolean var3) {
      return (String)get(var0, var1, var2, var3);
   }

   public static Insets getInsets(String var0, String var1, String var2, boolean var3) {
      return (Insets)get(var0, var1, var2, var3);
   }

   public static int getInt(String var0, String var1, String var2, boolean var3, int var4) {
      Object var5 = get(var0, var1, var2, var3);
      return var5 != null ? (Integer)var5 : var4;
   }

   public static Object get(String var0, Component var1) {
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "UI-propertyKey: " + var0);
      }

      return var1 != null && !(var1.getParent() instanceof CellRendererPane) ? get(var0, getStyleName(var1)) : lookup(var0);
   }

   public static Object get(String var0, String var1) {
      if (var1 == null) {
         return lookup(var0);
      } else {
         Object var2 = lookup(var0 + "." + var1);
         return var2 != null ? var2 : lookup(var0);
      }
   }

   private static Object lookup(String var0) {
      return uiDefaults != null ? uiDefaults.get(var0) : UIManager.get(var0);
   }

   public static boolean getBoolean(String var0, Component var1) {
      return getBoolean(var0, var1, false);
   }

   public static boolean getBoolean(String var0, Component var1, boolean var2) {
      Object var3 = get(var0, var1);
      return var3 != null ? (Boolean)var3 : var2;
   }

   public static int getInt(String var0, Component var1) {
      return getInt(var0, var1, 0);
   }

   public static int getInt(String var0, Component var1, int var2) {
      Object var3 = get(var0, var1);
      return var3 != null ? (Integer)var3 : var2;
   }

   public static Insets getInsets(String var0, Component var1) {
      return getInsets(var0, var1, true);
   }

   public static Insets getInsets(String var0, Component var1, boolean var2) {
      Object var3 = get(var0, var1);
      return (Insets)(var3 == null && !var2 ? new InsetsUIResource(0, 0, 0, 0) : (Insets)var3);
   }

   public static Insets getInsets(String var0, Component var1, Insets var2) {
      Object var3 = get(var0, var1);
      return var3 != null ? (Insets)var3 : var2;
   }

   public static String getString(String var0, Component var1) {
      return (String)get(var0, var1);
   }

   public static Color getColor(String var0, Component var1) {
      return (Color)get(var0, var1);
   }

   public static Color getColor(String var0, Component var1, Color var2) {
      Object var3 = get(var0, var1);
      return var3 != null ? (Color)var3 : var2;
   }

   public static Icon getIcon(String var0, Component var1) {
      return (Icon)get(var0, var1);
   }

   public static boolean isToplevelPopupMenu(JPopupMenu var0) {
      Component var1 = var0.getInvoker();
      boolean var2 = getBoolean("Synthetica.popupMenu.toplevel.enabled", var0, false);
      boolean var3 = getBoolean("Synthetica.menu.toplevel.popupMenu.toplevel.enabled", var0, var2);
      if (var1 instanceof JMenu && var3) {
         return ((JMenu)var1).isTopLevelMenu();
      } else {
         return var2 && getClientProperty("Synthetica.popupMenu.toplevel", var0, false);
      }
   }

   public static <T extends JComponent> T findOpaqueParentOfClass(Class<T> var0, Container var1, boolean var2) {
      if (var1 == null) {
         return null;
      } else if (!(var1 instanceof JComponent) || !var0.isInstance(var1) || (!var2 || !var1.isOpaque()) && !isOpaque((JComponent)var1)) {
         return var1.isOpaque() ? null : findOpaqueParentOfClass(var0, var1.getParent(), var2);
      } else {
         return (T)var1;
      }
   }

   public static boolean isOpaque(JComponent var0) {
      if (var0 == null) {
         return true;
      } else {
         boolean var1 = var0.getBackground() == null || var0.getBackground().getAlpha() != 0;
         var1 = var0.getClientProperty("Synthetica.opaque") == null ? var1 : var0.getClientProperty("Synthetica.opaque");
         if (getBoolean("Synthetica.textComponents.useSwingOpaqueness", var0)) {
            var1 = var0.isOpaque();
         }

         return var1;
      }
   }

   public static void setChildrenOpaque(Container var0, boolean var1) {
      Component[] var5;
      for(Component var2 : var5 = var0.getComponents()) {
         if (var2 instanceof JComponent) {
            JComponent var6 = (JComponent)var2;
            var6.setOpaque(var1);
            var6.putClientProperty("Synthetica.opaque", var1);
            setChildrenOpaque(var6, var1);
         }
      }

   }

   public static Rectangle validateWindowBounds(Rectangle var0) {
      Rectangle var1 = new Rectangle(var0);
      if (getBoolean("Synthetica.windowBoundsValidation.enabled", null, true)) {
         Rectangle var2 = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
         int var3 = var2.x;
         int var4 = var2.x + var2.width - var1.width;
         int var5 = var2.y;
         int var6 = var2.y + var2.height - var1.height;
         if (!isInScreen(var1.x, var1.y)) {
            if (var1.x < var3) {
               var1.x = var3;
            } else if (var1.x > var4) {
               var1.x = var4;
            }

            if (var1.y < var5) {
               var1.y = var5;
            } else if (var1.y > var6) {
               var1.y = var6;
            }
         }
      }

      return var1;
   }

   private static boolean isInScreen(int var0, int var1) {
      GraphicsDevice[] var5;
      for(GraphicsDevice var2 : var5 = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
         Rectangle var6 = var2.getDefaultConfiguration().getBounds();
         if (var6.contains(var0, var1)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isWindowOpacityEnabled(Window var0) {
      boolean var1 = !getBoolean("Synthetica.window.opaque", var0, true);
      return !JAVA6U10_OR_ABOVE && OS.getCurrentOS() != OS.Mac || !var1;
   }

   public static void setWindowOpaque(Window var0, boolean var1) {
      if (JAVA7_OR_ABOVE) {
         var0.setBackground(var1 ? null : new Color(0, 0, 0, 0));
      } else if (OS.getCurrentOS() == OS.Mac && !var1) {
         Boolean var5 = getBoolean("Synthetica.window.forceRecognizeMacEvents", var0);
         var0.setBackground(var5 ? new Color(192, 192, 192, 13) : new Color(0, 0, 0, 0));
         if (var0 instanceof JFrame) {
            ((JFrame)var0).getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
            if (!isWindowOpacityEnabled(var0)) {
               ((JComponent)((JFrame)var0).getContentPane()).setOpaque(false);
            }
         } else if (var0 instanceof JDialog) {
            ((JDialog)var0).getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
            if (!isWindowOpacityEnabled(var0)) {
               ((JComponent)((JDialog)var0).getContentPane()).setOpaque(false);
            }
         } else if (var0 instanceof JWindow) {
            ((JWindow)var0).getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
            if (!isWindowOpacityEnabled(var0)) {
               ((JComponent)((JWindow)var0).getContentPane()).setOpaque(false);
            }
         }

      } else if (getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         try {
            Class var2 = Class.forName("com.sun.awt.AWTUtilities");
            Method var3 = var2.getMethod("setWindowOpaque", Window.class, Boolean.TYPE);
            if (!var1 && "###overrideRedirect###".equals(var0.getName())) {
               var3.invoke(null, var0, true);
            }

            var3.invoke(null, var0, var1);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   public static boolean isWindowShapeEnabled(Window var0) {
      String var1 = getString("Synthetica.window.shape", var0);
      return var1 != null && !var1.equals("NONE");
   }

   public static boolean isWindowShapeSupported(Window var0) {
      boolean var1 = JAVA6U10_OR_ABOVE;
      if (OS.getCurrentOS() == OS.Mac) {
         var1 &= JAVA7U8_OR_ABOVE ? getBoolean("Synthetica.window.shapeSupportOnMac", var0, false) : false;
      }

      return var1;
   }

   public static void updateWindowShape(Window var0) {
      if (OS.getCurrentOS() == OS.Mac && !isWindowShapeSupported(var0)) {
         setWindowOpaque(var0, false);
      } else if ((!(var0 instanceof JFrame) || ((JFrame)var0).isUndecorated())
         && (!(var0 instanceof JDialog) || ((JDialog)var0).isUndecorated())
         && getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         boolean var1 = var0 instanceof Frame && (((Frame)var0).getExtendedState() & 6) == 6;

         try {
            Class<Window> var2 = JAVA7_OR_ABOVE ? Window.class : Class.forName("com.sun.awt.AWTUtilities");
            Method var3 = JAVA7_OR_ABOVE ? var2.getMethod("setShape", Shape.class) : var2.getMethod("setWindowShape", Window.class, Shape.class);
            String var4 = getString("Synthetica.window.shape", var0);
            Object var5 = null;
            if (var1 || !isWindowShapeEnabled(var0)) {
               var5 = null;
            } else if ("ROUND_RECT".equals(var4)) {
               int var6 = getInt("Synthetica.window.arcW", var0, 18);
               int var7 = getInt("Synthetica.window.arcH", var0, 18);
               var5 = new java.awt.geom.RoundRectangle2D.Float(0.0F, 0.0F, (float)var0.getWidth(), (float)var0.getHeight(), (float)var6, (float)var7);
            } else {
               try {
                  var5 = ((SyntheticaWindowShape)Class.forName(var4).newInstance()).getShape(var0.getWidth(), var0.getHeight());
               } catch (Exception var8) {
                  throw new RuntimeException(var8);
               }
            }

            if (JAVA7_OR_ABOVE) {
               var3.invoke(var0, var5);
            } else {
               var3.invoke(null, var0, var5);
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }

      }
   }

   private static void updateAllWindowShapes() {
      if (JAVA6U10_OR_ABOVE) {
         Window[] var3;
         for(Window var0 : var3 = Window.getWindows()) {
            boolean var4 = !(var0 instanceof JWindow) && !(var0 instanceof JFrame) && !(var0 instanceof JDialog);
            if (!var0.getClass().getName().contains("Popup$HeavyWeightWindow") && !var4) {
               updateWindowShape(var0);
            }
         }
      }

   }

   public static boolean isSystemPropertySet(String var0) {
      try {
         String var1 = System.getProperty(var0);
         if (var1 instanceof Boolean) {
            return (Boolean)var1;
         } else {
            return var1 != null;
         }
      } catch (AccessControlException var2) {
         return false;
      }
   }

   public static float scaleFontSize(float var0) {
      float var1 = var0;
      if (getBoolean("Synthetica.font.respectSystemDPI", null, true)) {
         Toolkit var2 = Toolkit.getDefaultToolkit();
         int var3 = 96;
         if (OS.getCurrentOS() == OS.Windows) {
            var3 = var2.getScreenResolution();
         } else if (OS.getCurrentOS() == OS.Linux) {
            Object var4 = var2.getDesktopProperty("gnome.Xft/DPI");
            if (var4 instanceof Integer) {
               var3 = (Integer)var4 / 1024;
            }
         }

         var1 = (float)var3 * var0 / 96.0F;
      }

      int var5 = getInt("Synthetica.font.scaleFactor", null, 100);
      return (float)var5 * var1 / 100.0F;
   }

   public static void setChildrenName(Container var0, String var1, String var2) {
      Component[] var6;
      for(Component var3 : var6 = var0.getComponents()) {
         if (var1.equals(getStyleName(var3))) {
            if (var3 instanceof JComponent && ((JComponent)var3).getClientProperty("Synthetica.style") != null) {
               ((JComponent)var3).putClientProperty("Synthetica.style", var2);
            } else {
               var3.setName(var2);
            }
         }

         if (var3 instanceof Container) {
            setChildrenName((Container)var3, var1, var2);
         }
      }

   }

   public static Component findComponent(String var0, Container var1) {
      Component[] var5;
      for(Component var2 : var5 = var1.getComponents()) {
         if (var0.equals(getStyleName(var2))) {
            return var2;
         }

         if (var2 instanceof Container) {
            Component var6 = findComponent(var0, (Container)var2);
            if (var6 != null) {
               return var6;
            }
         }
      }

      return null;
   }

   public static Component findComponent(Class<?> var0, Container var1) {
      Component[] var5;
      for(Component var2 : var5 = var1.getComponents()) {
         if (var0.isInstance(var2)) {
            return var2;
         }

         if (var2 instanceof Container) {
            Component var6 = findComponent(var0, (Container)var2);
            if (var6 != null) {
               return var6;
            }
         }
      }

      return null;
   }

   public static <T extends Component> void findComponents(String var0, Container var1, List<T> var2) {
      Component[] var6;
      for(Component var3 : var6 = var1.getComponents()) {
         if (var0.equals(getStyleName(var3))) {
            var2.add(var3);
         }

         if (var3 instanceof Container) {
            findComponents(var0, (Container)var3, var2);
         }
      }

   }

   public static <T> void findComponents(Class<T> var0, Container var1, List<T> var2) {
      Component[] var6;
      for(Component var3 : var6 = var1.getComponents()) {
         if (var0.isInstance(var3)) {
            var2.add(var3);
         }

         if (var3 instanceof Container) {
            findComponents(var0, (Container)var3, var2);
         }
      }

   }

   public static Border findDefaultBorder(Border var0) {
      if (var0 instanceof UIResource) {
         return var0;
      } else {
         if (var0 instanceof CompoundBorder) {
            Border var1 = findDefaultBorder(((CompoundBorder)var0).getOutsideBorder());
            if (var1 != null) {
               return var1;
            }

            Border var2 = findDefaultBorder(((CompoundBorder)var0).getInsideBorder());
            if (var2 != null) {
               return var2;
            }
         }

         return null;
      }
   }

   public static boolean preservePopupIconSpace(JPopupMenu var0) {
      return getBoolean("Synthetica.popupMenu.forceIconSpace", var0, false) ? true : popupHasIcons(var0);
   }

   public static boolean popupHasIcons(JPopupMenu var0) {
      ArrayList var1 = new ArrayList();
      findComponents(JMenuItem.class, var0, var1);
      if (var1.size() == 0) {
         return false;
      } else {
         for(JMenuItem var2 : var1) {
            if (var2.getIcon() != null) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean popupHasCheckRadio(JPopupMenu var0) {
      ArrayList var1 = new ArrayList();
      findComponents(JMenuItem.class, var0, var1);
      if (var1.size() == 0) {
         return false;
      } else {
         for(JMenuItem var2 : var1) {
            if (var2 instanceof JCheckBoxMenuItem || var2 instanceof JRadioButtonMenuItem) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean popupHasCheckRadioWithIcon(JPopupMenu var0) {
      ArrayList var1 = new ArrayList();
      findComponents(JMenuItem.class, var0, var1);

      for(JMenuItem var2 : var1) {
         if ((var2 instanceof JCheckBoxMenuItem || var2 instanceof JRadioButtonMenuItem) && var2.getIcon() != null) {
            return true;
         }
      }

      return false;
   }

   public static Paint createLinearGradientPaint(float var0, float var1, float var2, float var3, float[] var4, Color[] var5) {
      return (Paint)(JAVA5
         ? new LinearGradientPaint(var0, var1, var2, var3, var4, var5)
         : new java.awt.LinearGradientPaint(var0, var1, var2, var3, var4, var5));
   }

   public static void setLookAndFeel(String var0) {
      setLookAndFeel(var0, true, true);
   }

   public static void setLookAndFeel(String var0, boolean var1, boolean var2) {
      try {
         if (var1) {
            if (OS.getCurrentOS() == OS.Mac) {
               System.setProperty("apple.awt.textantialiasing", "on");
            } else {
               System.setProperty("swing.aatext", String.valueOf(var1));
            }
         }

         if (var2 && OS.getCurrentOS() == OS.Mac && !OS.getVersion().contains("10.4")) {
            System.setProperty("apple.laf.useScreenMenuBar", String.valueOf(var2));
            setLookAndFeelWorkaround(UIManager.getSystemLookAndFeelClassName());
            String var3 = UIManager.getString("MenuBarUI");
            Font var4 = UIManager.getFont("MenuItem.acceleratorFont");
            Boolean var5 = UIManager.getBoolean("Menu.borderPainted");
            Boolean var6 = UIManager.getBoolean("MenuItem.borderPainted");
            Boolean var7 = UIManager.getBoolean("RadioButtonMenuItem.borderPainted");
            Boolean var8 = UIManager.getBoolean("CheckBoxButtonMenuItem.borderPainted");
            UIManager.put("Menu.borderPainted", var5);
            UIManager.put("MenuItem.acceleratorFont", var4);
            UIManager.put("MenuItem.borderPainted", var6);
            UIManager.put("RadioButtonMenuItem.borderPainted", var7);
            UIManager.put("CheckBoxMenuItem.borderPainted", var8);
            UIManager.put("MenuBarUI", var3);
         }

         setLookAndFeelWorkaround(var0);
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   private static void setLookAndFeelWorkaround(String var0) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
      Locale var1 = Locale.getDefault();
      boolean var2 = "tr".equalsIgnoreCase(var1.getLanguage());
      if (var2) {
         Locale.setDefault(Locale.US);
      }

      UIManager.setLookAndFeel(var0);
      if (var2) {
         Locale.setDefault(var1);
      }

   }

   public static enum JVMCompatibilityMode {
      COMMON,
      SUN;

      private JVMCompatibilityMode() {
      }
   }

   private static class Version implements IVersion {
      private int major;
      private int minor;
      private int revision;
      private int build;

      public Version() {
         super();
         ResourceBundle var1 = SyntheticaLookAndFeel.getResourceBundle("SyntheticaStandardLookAndFeelVersion", new Locale("", ""));
         this.major = Integer.parseInt(var1.getString("major"));
         this.minor = Integer.parseInt(var1.getString("minor"));
         this.revision = Integer.parseInt(var1.getString("revision"));
         this.build = Integer.parseInt(var1.getString("build"));
      }

      @Override
      public int getMajor() {
         return this.major;
      }

      @Override
      public int getMinor() {
         return this.minor;
      }

      @Override
      public int getRevision() {
         return this.revision;
      }

      @Override
      public int getBuild() {
         return this.build;
      }

      @Override
      public String toString() {
         return this.major + "." + this.minor + "." + this.revision + " Build " + this.build;
      }
   }
}
