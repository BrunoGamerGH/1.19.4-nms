package net.minecraft.world.level.levelgen.structure;

import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.visitors.CollectFields;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.chunk.storage.IChunkLoader;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class StructureCheck {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = -1;
   private final ChunkScanAccess c;
   private final IRegistryCustom d;
   private final IRegistry<BiomeBase> e;
   private final IRegistry<Structure> f;
   private final StructureTemplateManager g;
   private final ResourceKey<World> h;
   private final ChunkGenerator i;
   private final RandomState j;
   private final LevelHeightAccessor k;
   private final WorldChunkManager l;
   private final long m;
   private final DataFixer n;
   private final Long2ObjectMap<Object2IntMap<Structure>> o = new Long2ObjectOpenHashMap();
   private final Map<Structure, Long2BooleanMap> p = new HashMap<>();

   public StructureCheck(
      ChunkScanAccess var0,
      IRegistryCustom var1,
      StructureTemplateManager var2,
      ResourceKey<World> var3,
      ChunkGenerator var4,
      RandomState var5,
      LevelHeightAccessor var6,
      WorldChunkManager var7,
      long var8,
      DataFixer var10
   ) {
      this.c = var0;
      this.d = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
      this.j = var5;
      this.k = var6;
      this.l = var7;
      this.m = var8;
      this.n = var10;
      this.e = var1.d(Registries.an);
      this.f = var1.d(Registries.ax);
   }

   public StructureCheckResult a(ChunkCoordIntPair var0, Structure var1, boolean var2) {
      long var3 = var0.a();
      Object2IntMap<Structure> var5 = (Object2IntMap)this.o.get(var3);
      if (var5 != null) {
         return this.a(var5, var1, var2);
      } else {
         StructureCheckResult var6 = this.a(var0, var1, var2, var3);
         if (var6 != null) {
            return var6;
         } else {
            boolean var7 = ((Long2BooleanMap)this.p.computeIfAbsent(var1, var0x -> new Long2BooleanOpenHashMap()))
               .computeIfAbsent(var3, var2x -> this.b(var0, var1));
            return !var7 ? StructureCheckResult.b : StructureCheckResult.c;
         }
      }
   }

   private boolean b(ChunkCoordIntPair var0, Structure var1) {
      return var1.b(new Structure.a(this.d, this.i, this.l, this.j, this.g, this.m, var0, this.k, var1.a()::a)).isPresent();
   }

   @Nullable
   private StructureCheckResult a(ChunkCoordIntPair var0, Structure var1, boolean var2, long var3) {
      CollectFields var5 = new CollectFields(
         new FieldSelector(NBTTagInt.a, "DataVersion"),
         new FieldSelector("Level", "Structures", NBTTagCompound.b, "Starts"),
         new FieldSelector("structures", NBTTagCompound.b, "starts")
      );

      try {
         this.c.a(var0, var5).join();
      } catch (Exception var13) {
         a.warn("Failed to read chunk {}", var0, var13);
         return StructureCheckResult.c;
      }

      NBTBase var6 = var5.d();
      if (!(var6 instanceof NBTTagCompound)) {
         return null;
      } else {
         NBTTagCompound var7 = (NBTTagCompound)var6;
         int var8 = IChunkLoader.a(var7);
         if (var8 <= 1493) {
            return StructureCheckResult.c;
         } else {
            IChunkLoader.a(var7, this.h, this.i.b());

            NBTTagCompound var9;
            try {
               var9 = DataFixTypes.c.a(this.n, var7, var8);
            } catch (Exception var12) {
               a.warn("Failed to partially datafix chunk {}", var0, var12);
               return StructureCheckResult.c;
            }

            Object2IntMap<Structure> var10 = this.a(var9);
            if (var10 == null) {
               return null;
            } else {
               this.a(var3, var10);
               return this.a(var10, var1, var2);
            }
         }
      }
   }

   @Nullable
   private Object2IntMap<Structure> a(NBTTagCompound var0) {
      if (!var0.b("structures", 10)) {
         return null;
      } else {
         NBTTagCompound var1 = var0.p("structures");
         if (!var1.b("starts", 10)) {
            return null;
         } else {
            NBTTagCompound var2 = var1.p("starts");
            if (var2.g()) {
               return Object2IntMaps.emptyMap();
            } else {
               Object2IntMap<Structure> var3 = new Object2IntOpenHashMap();
               IRegistry<Structure> var4 = this.d.d(Registries.ax);

               for(String var6 : var2.e()) {
                  MinecraftKey var7 = MinecraftKey.a(var6);
                  if (var7 != null) {
                     Structure var8 = var4.a(var7);
                     if (var8 != null) {
                        NBTTagCompound var9 = var2.p(var6);
                        if (!var9.g()) {
                           String var10 = var9.l("id");
                           if (!"INVALID".equals(var10)) {
                              int var11 = var9.h("references");
                              var3.put(var8, var11);
                           }
                        }
                     }
                  }
               }

               return var3;
            }
         }
      }
   }

   private static Object2IntMap<Structure> a(Object2IntMap<Structure> var0) {
      return var0.isEmpty() ? Object2IntMaps.emptyMap() : var0;
   }

   private StructureCheckResult a(Object2IntMap<Structure> var0, Structure var1, boolean var2) {
      int var3 = var0.getOrDefault(var1, -1);
      return var3 == -1 || var2 && var3 != 0 ? StructureCheckResult.b : StructureCheckResult.a;
   }

   public void a(ChunkCoordIntPair var0, Map<Structure, StructureStart> var1) {
      long var2 = var0.a();
      Object2IntMap<Structure> var4 = new Object2IntOpenHashMap();
      var1.forEach((var1x, var2x) -> {
         if (var2x.b()) {
            var4.put(var1x, var2x.f());
         }
      });
      this.a(var2, var4);
   }

   private void a(long var0, Object2IntMap<Structure> var2) {
      this.o.put(var0, a(var2));
      this.p.values().forEach(var2x -> var2x.remove(var0));
   }

   public void a(ChunkCoordIntPair var0, Structure var1) {
      this.o.compute(var0.a(), (var1x, var2x) -> {
         if (var2x == null || var2x.isEmpty()) {
            var2x = new Object2IntOpenHashMap();
         }

         var2x.computeInt(var1, (var0xx, var1xx) -> var1xx == null ? 1 : var1xx + 1);
         return var2x;
      });
   }
}
