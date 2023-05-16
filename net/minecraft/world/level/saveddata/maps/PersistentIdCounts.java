package net.minecraft.world.level.saveddata.maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.saveddata.PersistentBase;

public class PersistentIdCounts extends PersistentBase {
   public static final String a = "idcounts";
   private final Object2IntMap<String> b = new Object2IntOpenHashMap();

   public PersistentIdCounts() {
      this.b.defaultReturnValue(-1);
   }

   public static PersistentIdCounts b(NBTTagCompound var0) {
      PersistentIdCounts var1 = new PersistentIdCounts();

      for(String var3 : var0.e()) {
         if (var0.b(var3, 99)) {
            var1.b.put(var3, var0.h(var3));
         }
      }

      return var1;
   }

   @Override
   public NBTTagCompound a(NBTTagCompound var0) {
      ObjectIterator var2 = this.b.object2IntEntrySet().iterator();

      while(var2.hasNext()) {
         Entry<String> var2x = (Entry)var2.next();
         var0.a((String)var2x.getKey(), var2x.getIntValue());
      }

      return var0;
   }

   public int a() {
      int var0 = this.b.getInt("map") + 1;
      this.b.put("map", var0);
      this.b();
      return var0;
   }
}
