package net.infonode.util;

import java.util.ArrayList;

public class ArrayUtil {
   private ArrayUtil() {
      super();
   }

   public static final Object[] add(Object[] var0, Object var1, Object[] var2) {
      System.arraycopy(var0, 0, var2, 0, var0.length);
      var2[var0.length] = var1;
      return var2;
   }

   public static final byte[] part(byte[] var0, int var1, int var2) {
      byte[] var3 = new byte[var2];
      System.arraycopy(var0, var1, var3, 0, var2);
      return var3;
   }

   public static final int countNotNull(Object[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2] != null) {
            ++var1;
         }
      }

      return var1;
   }

   public static final int findSmallest(double[] var0) {
      int var1 = 0;

      for(int var2 = 1; var2 < var0.length; ++var2) {
         if (var0[var2] < var0[var1]) {
            var1 = var2;
         }
      }

      return var1;
   }

   public static final int findSmallest(int[] var0) {
      int var1 = 0;

      for(int var2 = 1; var2 < var0.length; ++var2) {
         if (var0[var2] < var0[var1]) {
            var1 = var2;
         }
      }

      return var1;
   }

   public static final int findLargest(float[] var0) {
      int var1 = 0;

      for(int var2 = 1; var2 < var0.length; ++var2) {
         if (var0[var2] > var0[var1]) {
            var1 = var2;
         }
      }

      return var1;
   }

   public static float[] toFloatArray(int[] var0) {
      float[] var1 = new float[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (float)var0[var2];
      }

      return var1;
   }

   public static final int indexOf(int[] var0, int var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public static final int indexOf(byte[] var0, byte var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public static final String[] append(String[] var0, String[] var1) {
      String[] var2 = new String[var0.length + var1.length];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      System.arraycopy(var1, 0, var2, var0.length, var1.length);
      return var2;
   }

   public static final Object[] append(Object[] var0, Object[] var1, Object[] var2) {
      System.arraycopy(var0, 0, var2, 0, var0.length);
      System.arraycopy(var1, 0, var2, var0.length, var1.length);
      return var2;
   }

   public static boolean equal(int[] var0, int var1, int[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         if (var0[var1 + var5] != var2[var3 + var5]) {
            return false;
         }
      }

      return true;
   }

   public static boolean equal(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         if (var0[var1 + var5] != var2[var3 + var5]) {
            return false;
         }
      }

      return true;
   }

   public static boolean contains(short[] var0, short var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2] == var1) {
            return true;
         }
      }

      return false;
   }

   public static int[] range(int var0, int var1, int var2) {
      int[] var3 = new int[var1];

      for(int var4 = 0; var4 < var1; ++var4) {
         var3[var4] = var0 + var2 * var4;
      }

      return var3;
   }

   public static boolean containsEqual(Object[] var0, Object var1) {
      return indexOfEqual(var0, var1) != -1;
   }

   public static boolean contains(Object[] var0, Object var1) {
      return indexOf(var0, var1) != -1;
   }

   public static int indexOf(Object[] var0, Object var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public static int indexOf(Object[] var0, Object var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3; ++var4) {
         if (var0[var4] == var1) {
            return var4;
         }
      }

      return -1;
   }

   public static int indexOfEqual(Object[] var0, Object var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2].equals(var1)) {
            return var2;
         }
      }

      return -1;
   }

   public static Object[] remove(Object[] var0, Object var1, Object[] var2) {
      int var3 = indexOf(var0, var1);
      if (var3 == -1) {
         var3 = var0.length;
      }

      System.arraycopy(var0, 0, var2, 0, var3);
      System.arraycopy(var0, var3 + 1, var2, var3, var2.length - var3);
      return var2;
   }

   public static String toString(int[] var0) {
      StringBuffer var1 = new StringBuffer(var0.length * 4);

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var2 != 0) {
            var1.append(", ");
         }

         var1.append(var0[var2]);
      }

      return var1.toString();
   }

   public static int[] part(int[] var0, int var1, int var2) {
      int[] var3 = new int[var2];
      System.arraycopy(var0, var1, var3, 0, var2);
      return var3;
   }

   public static int sum(int[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1 += var0[var2];
      }

      return var1;
   }

   public static int count(int[] var0, int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var0[var3] == var1) {
            ++var2;
         }
      }

      return var2;
   }

   public static int count(boolean[] var0, boolean var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var0[var3] == var1) {
            ++var2;
         }
      }

      return var2;
   }

   public static int findLargest(int[] var0) {
      int var1 = 0;

      for(int var2 = 1; var2 < var0.length; ++var2) {
         if (var0[var2] > var0[var1]) {
            var1 = var2;
         }
      }

      return var1;
   }

   public static int[] toIntArray(ArrayList var0) {
      int[] var1 = new int[var0.size()];

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         var1[var2] = ((Number)var0.get(var2)).intValue();
      }

      return var1;
   }

   public static boolean[] toBooleanArray(ArrayList var0) {
      boolean[] var1 = new boolean[var0.size()];

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         var1[var2] = var0.get(var2);
      }

      return var1;
   }
}
