package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Image;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

class SyntheticaSoftCache {
   private final int maxCacheSize;
   private final int maxImageSize;
   private final LinkedHashMap<Integer, SyntheticaSoftCache.ImageReference> cache = new LinkedHashMap();
   private ReferenceQueue<Image> referenceQueue = new ReferenceQueue();
   private int pixelCount = 0;
   private static final SyntheticaSoftCache instance = new SyntheticaSoftCache();

   private SyntheticaSoftCache() {
      super();
      int var1 = SyntheticaLookAndFeel.getInt("Synthetica.cache.maxSize", null, 4);
      this.maxCacheSize = var1 * 1024 * 1024;
      this.maxImageSize = SyntheticaLookAndFeel.getInt("Synthetica.cache.maxImageSize", null, 200000);
   }

   static SyntheticaSoftCache getInstance() {
      return instance;
   }

   public boolean isCacheable(int var1, int var2) {
      return var1 * var2 < this.maxImageSize && var1 > 0 && var2 > 0;
   }

   public Image getImage(int var1) {
      SyntheticaSoftCache.ImageReference var2 = (SyntheticaSoftCache.ImageReference)this.cache.get(var1);
      return var2 != null ? (Image)var2.get() : null;
   }

   public boolean setImage(Image var1, int var2) {
      SyntheticaSoftCache.ImageReference var3 = (SyntheticaSoftCache.ImageReference)this.cache.get(var2);
      if (var3 != null && var3.get() != null) {
         return true;
      } else {
         int var4 = var1.getWidth(null) * var1.getHeight(null);

         for(this.pixelCount += var4; (var3 = (SyntheticaSoftCache.ImageReference)this.referenceQueue.poll()) != null; this.pixelCount -= var3.pixelCount) {
            this.cache.remove(var3.cacheHash);
         }

         SyntheticaSoftCache.ImageReference var7;
         if (this.pixelCount > this.maxCacheSize) {
            for(Iterator var5 = this.cache.entrySet().iterator();
               this.pixelCount > this.maxCacheSize / 2 && var5.hasNext();
               this.pixelCount -= var7.pixelCount
            ) {
               Entry var6 = (Entry)var5.next();
               var7 = (SyntheticaSoftCache.ImageReference)var6.getValue();
               Image var8 = (Image)var7.get();
               if (var8 != null) {
                  var8.flush();
               }

               var5.remove();
            }
         }

         this.cache.put(var2, new SyntheticaSoftCache.ImageReference(var1, var2, this.referenceQueue));
         return true;
      }
   }

   public void clear() {
      this.pixelCount = 0;
      this.cache.clear();
   }

   private static class ImageReference extends SoftReference<Image> {
      private int cacheHash;
      private int pixelCount;

      public ImageReference(Image var1, int var2, ReferenceQueue<Image> var3) {
         super(var1, var3);
         this.cacheHash = var2;
         this.pixelCount = var1.getWidth(null) * var1.getHeight(null);
      }
   }
}
