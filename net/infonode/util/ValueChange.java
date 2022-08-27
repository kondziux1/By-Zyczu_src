package net.infonode.util;

public class ValueChange {
   private Object oldValue;
   private Object newValue;

   public ValueChange(Object var1, Object var2) {
      super();
      this.oldValue = var1;
      this.newValue = var2;
   }

   public Object getOldValue() {
      return this.oldValue;
   }

   public Object getNewValue() {
      return this.newValue;
   }
}
