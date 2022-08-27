package net.infonode.properties.propertymap.value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.properties.base.Property;
import net.infonode.properties.base.exception.InvalidPropertyTypeException;
import net.infonode.properties.propertymap.PropertyMapImpl;
import net.infonode.properties.propertymap.ref.PropertyMapRef;
import net.infonode.properties.propertymap.ref.PropertyMapRefDecoder;
import net.infonode.util.Printer;
import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalListener;

public class PropertyRefValue implements PropertyValue, SignalListener {
   private PropertyMapImpl map;
   private Property property;
   private PropertyMapRef propertyObjectRef;
   private Property propertyRef;
   private PropertyRefValue parentRef;

   public PropertyRefValue(PropertyMapImpl var1, Property var2, PropertyMapRef var3, Property var4, PropertyRefValue var5) {
      super();
      if (!var2.getType().isAssignableFrom(var4.getType())) {
         throw new InvalidPropertyTypeException(
            var2, var4, "Can't create reference from Property '" + var2 + "' to property '" + var4 + "' because they are of incompatible types!"
         );
      } else {
         this.map = var1;
         this.property = var2;
         this.propertyObjectRef = var3;
         this.propertyRef = var4;
         this.parentRef = var5;
      }
   }

   public Property getProperty() {
      return this.property;
   }

   public PropertyMapImpl getMap() {
      return this.map;
   }

   public void updateListener(boolean var1) {
      if (var1) {
         this.propertyObjectRef.getMap(this.map).getMap().getChangeSignal().add(this);
      } else {
         this.propertyObjectRef.getMap(this.map).getMap().getChangeSignal().remove(this);
      }

   }

   public PropertyValue getParent() {
      return this.parentRef;
   }

   public Object get(PropertyMapImpl var1) {
      PropertyMapImpl var2 = this.propertyObjectRef.getMap(var1);
      PropertyValue var3 = (var2 == null ? this.propertyObjectRef.getMap(this.map) : var2).getValue(this.propertyRef);
      return var3 == null ? null : var3.get(var2);
   }

   public Object getWithDefault(PropertyMapImpl var1) {
      PropertyMapImpl var2 = this.propertyObjectRef.getMap(var1);
      PropertyValue var3 = (var2 == null ? this.propertyObjectRef.getMap(this.map) : var2).getValueWithDefault(this.propertyRef);
      return var3 == null ? null : var3.getWithDefault(var2);
   }

   public PropertyValue getSubValue(PropertyMapImpl var1) {
      PropertyMapImpl var2 = this.propertyObjectRef.getMap(var1);
      if (var2 == null) {
         return null;
      } else {
         return !var2.getPropertyGroup().hasProperty(this.propertyRef)
            ? null
            : new PropertyRefValue(var1, this.property, this.propertyObjectRef, this.propertyRef, this);
      }
   }

   public void unset() {
      this.propertyObjectRef.getMap(this.map).getMap().getChangeSignal().remove(this);
   }

   public void signalEmitted(Signal var1, Object var2) {
      ConstMap var3 = (ConstMap)var2;
      ValueChange var4 = (ValueChange)var3.get(this.propertyRef);
      if (var4 != null) {
         this.map.firePropertyValueChanged(this.property, new ValueChange(var4.getOldValue(), this));
      }

   }

   public String toString() {
      return "ref -> " + this.propertyObjectRef + '.' + this.propertyRef;
   }

   public void dump(Printer var1) {
      var1.println(this.toString());
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(1);
      this.propertyObjectRef.write(var1);
      var1.writeUTF(this.propertyRef.getName());
   }

   public boolean isSerializable() {
      return true;
   }

   public static PropertyValue decode(ObjectInputStream var0, PropertyMapImpl var1, Property var2) throws IOException {
      PropertyMapRef var3 = PropertyMapRefDecoder.decode(var0);
      String var4 = var0.readUTF();
      if (var2 != null && var3 != null) {
         Property var5 = var3.getMap(var1).getPropertyGroup().getProperty(var4);
         return var5 == null ? null : new PropertyRefValue(var1, var2, var3, var5, null);
      } else {
         return null;
      }
   }

   public static void skip(ObjectInputStream var0) throws IOException {
      PropertyMapRefDecoder.decode(var0);
      var0.readUTF();
   }

   public PropertyValue copyTo(PropertyMapImpl var1) {
      return new PropertyRefValue(var1, this.property, this.propertyObjectRef, this.propertyRef, null);
   }
}
