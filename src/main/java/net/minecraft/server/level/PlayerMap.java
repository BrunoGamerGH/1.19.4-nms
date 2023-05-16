package net.minecraft.server.level;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.Set;

public final class PlayerMap {
   private final Object2BooleanMap<EntityPlayer> a = new Object2BooleanOpenHashMap();

   public Set<EntityPlayer> a(long var0) {
      return this.a.keySet();
   }

   public void a(long var0, EntityPlayer var2, boolean var3) {
      this.a.put(var2, var3);
   }

   public void a(long var0, EntityPlayer var2) {
      this.a.removeBoolean(var2);
   }

   public void a(EntityPlayer var0) {
      this.a.replace(var0, true);
   }

   public void b(EntityPlayer var0) {
      this.a.replace(var0, false);
   }

   public boolean c(EntityPlayer var0) {
      return this.a.getOrDefault(var0, true);
   }

   public boolean d(EntityPlayer var0) {
      return this.a.getBoolean(var0);
   }

   public void a(long var0, long var2, EntityPlayer var4) {
   }
}
