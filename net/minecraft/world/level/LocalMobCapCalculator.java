package net.minecraft.world.level;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerChunkMap;
import net.minecraft.world.entity.EnumCreatureType;

public class LocalMobCapCalculator {
   private final Long2ObjectMap<List<EntityPlayer>> a = new Long2ObjectOpenHashMap();
   private final Map<EntityPlayer, LocalMobCapCalculator.a> b = Maps.newHashMap();
   private final PlayerChunkMap c;

   public LocalMobCapCalculator(PlayerChunkMap var0) {
      this.c = var0;
   }

   private List<EntityPlayer> a(ChunkCoordIntPair var0) {
      return (List<EntityPlayer>)this.a.computeIfAbsent(var0.a(), var1x -> this.c.e(var0));
   }

   public void a(ChunkCoordIntPair var0, EnumCreatureType var1) {
      for(EntityPlayer var3 : this.a(var0)) {
         this.b.computeIfAbsent(var3, var0x -> new LocalMobCapCalculator.a()).a(var1);
      }
   }

   public boolean a(EnumCreatureType var0, ChunkCoordIntPair var1) {
      for(EntityPlayer var3 : this.a(var1)) {
         LocalMobCapCalculator.a var4 = this.b.get(var3);
         if (var4 == null || var4.b(var0)) {
            return true;
         }
      }

      return false;
   }

   static class a {
      private final Object2IntMap<EnumCreatureType> a = new Object2IntOpenHashMap(EnumCreatureType.values().length);

      public void a(EnumCreatureType var0) {
         this.a.computeInt(var0, (var0x, var1x) -> var1x == null ? 1 : var1x + 1);
      }

      public boolean b(EnumCreatureType var0) {
         return this.a.getOrDefault(var0, 0) < var0.b();
      }
   }
}
