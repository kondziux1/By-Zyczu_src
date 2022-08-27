package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.SplitWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

public class SplitWindowItem extends WindowItem {
   public static final SplitWindowProperties emptyProperties = new SplitWindowProperties();
   private boolean isHorizontal;
   private float dividerLocation;
   private SplitWindowProperties splitWindowProperties;
   private SplitWindowProperties parentProperties = emptyProperties;

   public SplitWindowItem() {
      super();
      this.splitWindowProperties = new SplitWindowProperties(emptyProperties);
   }

   public SplitWindowItem(SplitWindowItem var1) {
      super(var1);
      this.splitWindowProperties = new SplitWindowProperties(var1.getSplitWindowProperties().getMap().copy(true, true));
      this.splitWindowProperties.getMap().replaceSuperMap(var1.getParentSplitWindowProperties().getMap(), emptyProperties.getMap());
   }

   public SplitWindowItem(WindowItem var1, WindowItem var2, boolean var3, float var4) {
      super();
      this.addWindow(var1);
      this.addWindow(var2);
      this.isHorizontal = var3;
      this.dividerLocation = var4;
   }

   protected DockingWindow createWindow(ViewReader var1, ArrayList var2) {
      return (DockingWindow)(var2.size() == 0
         ? null
         : (var2.size() == 1 ? (DockingWindow)var2.get(0) : var1.createSplitWindow((DockingWindow)var2.get(0), (DockingWindow)var2.get(1), this)));
   }

   public boolean isHorizontal() {
      return this.isHorizontal;
   }

   public float getDividerLocation() {
      return this.dividerLocation;
   }

   public void setHorizontal(boolean var1) {
      this.isHorizontal = var1;
   }

   public void setDividerLocation(float var1) {
      this.dividerLocation = var1;
   }

   public SplitWindowProperties getSplitWindowProperties() {
      return this.splitWindowProperties;
   }

   public SplitWindowProperties getParentSplitWindowProperties() {
      return this.parentProperties;
   }

   public void setParentSplitWindowProperties(SplitWindowProperties var1) {
      this.splitWindowProperties.getMap().replaceSuperMap(this.parentProperties.getMap(), var1.getMap());
      this.parentProperties = var1;
   }

   public WindowItem copy() {
      return new SplitWindowItem(this);
   }

   public void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(0);
      super.write(var1, var2, var3);
   }

   public void writeSettings(ObjectOutputStream var1, WriteContext var2) throws IOException {
      var1.writeBoolean(this.isHorizontal);
      var1.writeFloat(this.dividerLocation);
      super.writeSettings(var1, var2);
   }

   public void readSettings(ObjectInputStream var1, ReadContext var2) throws IOException {
      if (var2.getVersion() >= 3) {
         this.isHorizontal = var1.readBoolean();
         this.dividerLocation = var1.readFloat();
      }

      super.readSettings(var1, var2);
   }

   protected PropertyMap getPropertyObject() {
      return this.getSplitWindowProperties().getMap();
   }

   public String toString() {
      return "SplitWindow: " + super.toString();
   }
}
