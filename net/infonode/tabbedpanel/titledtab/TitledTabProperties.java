package net.infonode.tabbedpanel.titledtab;

import net.infonode.gui.DimensionProvider;
import net.infonode.gui.DynamicUIManager;
import net.infonode.gui.hover.HoverListener;
import net.infonode.properties.base.Property;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.DimensionProviderProperty;
import net.infonode.properties.types.HoverListenerProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.util.Alignment;
import net.infonode.util.Direction;

public class TitledTabProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Titled Tab Properties", "Properties for the TitledTab class.");
   public static final BooleanProperty FOCUSABLE = new BooleanProperty(PROPERTIES, "Focusable", "Tab focusable", PropertyMapValueHandler.INSTANCE);
   public static final BooleanProperty FOCUS_MARKER_ENABLED = new BooleanProperty(
      PROPERTIES, "Focus Marker Enabled", "Enables or disables the focus marker when the tab has focus.", PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty NORMAL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Normal Properties", "Normal tab properties.", TitledTabStateProperties.PROPERTIES
   );
   public static final PropertyMapProperty HIGHLIGHTED_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Highlighted Properties", "Highlighted tab properties.", TitledTabStateProperties.PROPERTIES
   );
   public static final PropertyMapProperty DISABLED_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Disabled Properties", "Disabled tab properties.", TitledTabStateProperties.PROPERTIES
   );
   public static final TitledTabSizePolicyProperty SIZE_POLICY = new TitledTabSizePolicyProperty(
      PROPERTIES, "Size Policy", "Tab size policy", PropertyMapValueHandler.INSTANCE
   );
   public static final TitledTabBorderSizePolicyProperty BORDER_SIZE_POLICY = new TitledTabBorderSizePolicyProperty(
      PROPERTIES, "Border Size Policy", "Border size policy.", PropertyMapValueHandler.INSTANCE
   );
   public static final DimensionProviderProperty MINIMUM_SIZE_PROVIDER = new DimensionProviderProperty(
      PROPERTIES, "Minimum Size", "Tab minimum size.", PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty HIGHLIGHTED_RAISED_AMOUNT = IntegerProperty.createPositive(
      PROPERTIES, "Highlighted Raised", "Number of raised pixels for highlighted tab.", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final HoverListenerProperty HOVER_LISTENER = new HoverListenerProperty(
      PROPERTIES, "Hover Listener", "Hover Listener to be used for tracking mouse hovering over the tab.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty ENABLED = new BooleanProperty(PROPERTIES, "Enabled", "TitledTab enabled or disabled", PropertyMapValueHandler.INSTANCE);
   private static final TitledTabProperties DEFAULT_VALUES = new TitledTabProperties(PROPERTIES.getDefaultMap());

   public TitledTabProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public TitledTabProperties(PropertyMap var1) {
      super(var1);
   }

   public TitledTabProperties(TitledTabProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TitledTabProperties addSuperObject(TitledTabProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public TitledTabProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TitledTabProperties removeSuperObject(TitledTabProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public TitledTabProperties replaceSuperObject(TitledTabProperties var1, TitledTabProperties var2) {
      this.getMap().replaceSuperMap(var1.getMap(), var2.getMap());
      return this;
   }

   public static TitledTabProperties getDefaultProperties() {
      return new TitledTabProperties(DEFAULT_VALUES);
   }

   public TitledTabStateProperties getNormalProperties() {
      return new TitledTabStateProperties(NORMAL_PROPERTIES.get(this.getMap()));
   }

   public TitledTabStateProperties getHighlightedProperties() {
      return new TitledTabStateProperties(HIGHLIGHTED_PROPERTIES.get(this.getMap()));
   }

   public TitledTabStateProperties getDisabledProperties() {
      return new TitledTabStateProperties(DISABLED_PROPERTIES.get(this.getMap()));
   }

   public TitledTabProperties setFocusable(boolean var1) {
      FOCUSABLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getFocusable() {
      return FOCUSABLE.get(this.getMap());
   }

   public TitledTabProperties setFocusMarkerEnabled(boolean var1) {
      FOCUS_MARKER_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getFocusMarkerEnabled() {
      return FOCUS_MARKER_ENABLED.get(this.getMap());
   }

   public TitledTabProperties setSizePolicy(TitledTabSizePolicy var1) {
      SIZE_POLICY.set(this.getMap(), var1);
      return this;
   }

   public TitledTabSizePolicy getSizePolicy() {
      return SIZE_POLICY.get(this.getMap());
   }

   public TitledTabProperties setBorderSizePolicy(TitledTabBorderSizePolicy var1) {
      BORDER_SIZE_POLICY.set(this.getMap(), var1);
      return this;
   }

   public TitledTabBorderSizePolicy getBorderSizePolicy() {
      return BORDER_SIZE_POLICY.get(this.getMap());
   }

   public TitledTabProperties setMinimumSizeProvider(DimensionProvider var1) {
      MINIMUM_SIZE_PROVIDER.set(this.getMap(), var1);
      return this;
   }

   public DimensionProvider getMinimumSizeProvider() {
      return MINIMUM_SIZE_PROVIDER.get(this.getMap());
   }

   public TitledTabProperties setHighlightedRaised(int var1) {
      HIGHLIGHTED_RAISED_AMOUNT.set(this.getMap(), var1);
      return this;
   }

   public int getHighlightedRaised() {
      return HIGHLIGHTED_RAISED_AMOUNT.get(this.getMap());
   }

   public TitledTabProperties setEnabled(boolean var1) {
      ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getEnabled() {
      return ENABLED.get(this.getMap());
   }

   public TitledTabProperties setHoverListener(HoverListener var1) {
      HOVER_LISTENER.set(this.getMap(), var1);
      return this;
   }

   public HoverListener getHoverListener() {
      return HOVER_LISTENER.get(this.getMap());
   }

   private static void updateVisualProperties() {
      PropertyMapManager.runBatch(new TitledTabProperties$2());
   }

   private static void updateFunctionalProperties() {
      DEFAULT_VALUES.setEnabled(true)
         .setFocusable(true)
         .setFocusMarkerEnabled(true)
         .setSizePolicy(TitledTabSizePolicy.EQUAL_SIZE)
         .setBorderSizePolicy(TitledTabBorderSizePolicy.EQUAL_SIZE)
         .setHighlightedRaised(2);
      DEFAULT_VALUES.getNormalProperties()
         .setHorizontalAlignment(Alignment.LEFT)
         .setVerticalAlignment(Alignment.CENTER)
         .setIconTextRelativeAlignment(Alignment.LEFT)
         .setTitleComponentTextRelativeAlignment(Alignment.RIGHT)
         .setDirection(Direction.RIGHT);
   }

   static {
      DynamicUIManager.getInstance().addListener(new TitledTabProperties$1());
      DEFAULT_VALUES.getNormalProperties()
         .getMap()
         .createRelativeRef(TitledTabStateProperties.TOOL_TIP_TEXT, DEFAULT_VALUES.getNormalProperties().getMap(), TitledTabStateProperties.TEXT);
      DEFAULT_VALUES.getHighlightedProperties().getMap().addSuperMap(DEFAULT_VALUES.getNormalProperties().getMap());
      DEFAULT_VALUES.getDisabledProperties().getMap().addSuperMap(DEFAULT_VALUES.getNormalProperties().getMap());
      Property[] var0 = TitledTabStateProperties.PROPERTIES.getProperties();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         DEFAULT_VALUES.getHighlightedProperties().getMap().createRelativeRef(var0[var1], DEFAULT_VALUES.getNormalProperties().getMap(), var0[var1]);
         DEFAULT_VALUES.getDisabledProperties().getMap().createRelativeRef(var0[var1], DEFAULT_VALUES.getNormalProperties().getMap(), var0[var1]);
      }

      var0 = ComponentProperties.PROPERTIES.getProperties();

      for(int var4 = 0; var4 < var0.length; ++var4) {
         DEFAULT_VALUES.getHighlightedProperties()
            .getComponentProperties()
            .getMap()
            .createRelativeRef(var0[var4], DEFAULT_VALUES.getNormalProperties().getComponentProperties().getMap(), var0[var4]);
         DEFAULT_VALUES.getDisabledProperties()
            .getComponentProperties()
            .getMap()
            .createRelativeRef(var0[var4], DEFAULT_VALUES.getNormalProperties().getComponentProperties().getMap(), var0[var4]);
      }

      var0 = ShapedPanelProperties.PROPERTIES.getProperties();

      for(int var5 = 0; var5 < var0.length; ++var5) {
         DEFAULT_VALUES.getHighlightedProperties()
            .getShapedPanelProperties()
            .getMap()
            .createRelativeRef(var0[var5], DEFAULT_VALUES.getNormalProperties().getShapedPanelProperties().getMap(), var0[var5]);
         DEFAULT_VALUES.getDisabledProperties()
            .getShapedPanelProperties()
            .getMap()
            .createRelativeRef(var0[var5], DEFAULT_VALUES.getNormalProperties().getShapedPanelProperties().getMap(), var0[var5]);
      }

      updateVisualProperties();
      updateFunctionalProperties();
   }
}
