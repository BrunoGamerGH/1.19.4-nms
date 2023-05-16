package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.EnumDirection8;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.BlockAccessAir;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.BlockFacingHorizontal;
import net.minecraft.world.level.block.BlockStem;
import net.minecraft.world.level.block.BlockStemmed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyChestType;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.ticks.TickListChunk;
import org.slf4j.Logger;

public class ChunkConverter {
   private static final Logger b = LogUtils.getLogger();
   public static final ChunkConverter a = new ChunkConverter(BlockAccessAir.a);
   private static final String c = "Indices";
   private static final EnumDirection8[] d = EnumDirection8.values();
   private final EnumSet<EnumDirection8> e = EnumSet.noneOf(EnumDirection8.class);
   private final List<TickListChunk<Block>> f = Lists.newArrayList();
   private final List<TickListChunk<FluidType>> g = Lists.newArrayList();
   private final int[][] h;
   static final Map<Block, ChunkConverter.a> i = new IdentityHashMap<>();
   static final Set<ChunkConverter.a> j = Sets.newHashSet();

   private ChunkConverter(LevelHeightAccessor var0) {
      this.h = new int[var0.aj()][];
   }

   public ChunkConverter(NBTTagCompound var0, LevelHeightAccessor var1) {
      this(var1);
      if (var0.b("Indices", 10)) {
         NBTTagCompound var2 = var0.p("Indices");

         for(int var3 = 0; var3 < this.h.length; ++var3) {
            String var4 = String.valueOf(var3);
            if (var2.b(var4, 11)) {
               this.h[var3] = var2.n(var4);
            }
         }
      }

      int var2 = var0.h("Sides");

      for(EnumDirection8 var6 : EnumDirection8.values()) {
         if ((var2 & 1 << var6.ordinal()) != 0) {
            this.e.add(var6);
         }
      }

      a(var0, "neighbor_block_ticks", var0x -> BuiltInRegistries.f.b(MinecraftKey.a(var0x)).or(() -> Optional.of(Blocks.a)), this.f);
      a(var0, "neighbor_fluid_ticks", var0x -> BuiltInRegistries.d.b(MinecraftKey.a(var0x)).or(() -> Optional.of(FluidTypes.a)), this.g);
   }

   private static <T> void a(NBTTagCompound var0, String var1, Function<String, Optional<T>> var2, List<TickListChunk<T>> var3) {
      if (var0.b(var1, 9)) {
         for(NBTBase var6 : var0.c(var1, 10)) {
            TickListChunk.a((NBTTagCompound)var6, var2).ifPresent(var3::add);
         }
      }
   }

   public void a(Chunk var0) {
      this.b(var0);

      for(EnumDirection8 var4 : d) {
         a(var0, var4);
      }

      World var1 = var0.D();
      this.f.forEach(var1x -> {
         Block var2 = var1x.a() == Blocks.a ? var1.a_(var1x.b()).b() : var1x.a();
         var1.a(var1x.b(), var2, var1x.c(), var1x.d());
      });
      this.g.forEach(var1x -> {
         FluidType var2 = var1x.a() == FluidTypes.a ? var1.b_(var1x.b()).a() : var1x.a();
         var1.a(var1x.b(), var2, var1x.c(), var1x.d());
      });
      j.forEach(var1x -> var1x.a(var1));
   }

   private static void a(Chunk var0, EnumDirection8 var1) {
      World var2 = var0.D();
      if (var0.r().e.remove(var1)) {
         Set<EnumDirection> var3 = var1.a();
         int var4 = 0;
         int var5 = 15;
         boolean var6 = var3.contains(EnumDirection.f);
         boolean var7 = var3.contains(EnumDirection.e);
         boolean var8 = var3.contains(EnumDirection.d);
         boolean var9 = var3.contains(EnumDirection.c);
         boolean var10 = var3.size() == 1;
         ChunkCoordIntPair var11 = var0.f();
         int var12 = var11.d() + (!var10 || !var9 && !var8 ? (var7 ? 0 : 15) : 1);
         int var13 = var11.d() + (!var10 || !var9 && !var8 ? (var7 ? 0 : 15) : 14);
         int var14 = var11.e() + (!var10 || !var6 && !var7 ? (var9 ? 0 : 15) : 1);
         int var15 = var11.e() + (!var10 || !var6 && !var7 ? (var9 ? 0 : 15) : 14);
         EnumDirection[] var16 = EnumDirection.values();
         BlockPosition.MutableBlockPosition var17 = new BlockPosition.MutableBlockPosition();

         for(BlockPosition var19 : BlockPosition.b(var12, var2.v_(), var14, var13, var2.ai() - 1, var15)) {
            IBlockData var20 = var2.a_(var19);
            IBlockData var21 = var20;

            for(EnumDirection var25 : var16) {
               var17.a(var19, var25);
               var21 = a(var21, var25, var2, var19, var17);
            }

            Block.a(var20, var21, var2, var19, 18);
         }
      }
   }

   private static IBlockData a(IBlockData var0, EnumDirection var1, GeneratorAccess var2, BlockPosition var3, BlockPosition var4) {
      return i.getOrDefault(var0.b(), ChunkConverter.Type.b).a(var0, var1, var2.a_(var4), var2, var3, var4);
   }

   private void b(Chunk var0) {
      BlockPosition.MutableBlockPosition var1 = new BlockPosition.MutableBlockPosition();
      BlockPosition.MutableBlockPosition var2 = new BlockPosition.MutableBlockPosition();
      ChunkCoordIntPair var3 = var0.f();
      GeneratorAccess var4 = var0.D();

      for(int var5 = 0; var5 < this.h.length; ++var5) {
         ChunkSection var6 = var0.b(var5);
         int[] var7 = this.h[var5];
         this.h[var5] = null;
         if (var7 != null && var7.length > 0) {
            EnumDirection[] var8 = EnumDirection.values();
            DataPaletteBlock<IBlockData> var9 = var6.i();

            for(int var13 : var7) {
               int var14 = var13 & 15;
               int var15 = var13 >> 8 & 15;
               int var16 = var13 >> 4 & 15;
               var1.d(var3.d() + var14, var6.g() + var15, var3.e() + var16);
               IBlockData var17 = var9.a(var13);
               IBlockData var18 = var17;

               for(EnumDirection var22 : var8) {
                  var2.a(var1, var22);
                  if (SectionPosition.a(var1.u()) == var3.e && SectionPosition.a(var1.w()) == var3.f) {
                     var18 = a(var18, var22, var4, var1, var2);
                  }
               }

               Block.a(var17, var18, var4, var1, 18);
            }
         }
      }

      for(int var5 = 0; var5 < this.h.length; ++var5) {
         if (this.h[var5] != null) {
            b.warn("Discarding update data for section {} for chunk ({} {})", new Object[]{var4.g(var5), var3.e, var3.f});
         }

         this.h[var5] = null;
      }
   }

   public boolean a() {
      for(int[] var3 : this.h) {
         if (var3 != null) {
            return false;
         }
      }

      return this.e.isEmpty();
   }

   public NBTTagCompound b() {
      NBTTagCompound var0 = new NBTTagCompound();
      NBTTagCompound var1 = new NBTTagCompound();

      for(int var2 = 0; var2 < this.h.length; ++var2) {
         String var3 = String.valueOf(var2);
         if (this.h[var2] != null && this.h[var2].length != 0) {
            var1.a(var3, this.h[var2]);
         }
      }

      if (!var1.g()) {
         var0.a("Indices", var1);
      }

      int var2 = 0;

      for(EnumDirection8 var4 : this.e) {
         var2 |= 1 << var4.ordinal();
      }

      var0.a("Sides", (byte)var2);
      if (!this.f.isEmpty()) {
         NBTTagList var3 = new NBTTagList();
         this.f.forEach(var1x -> var3.add(var1x.a(var0xx -> BuiltInRegistries.f.b(var0xx).toString())));
         var0.a("neighbor_block_ticks", var3);
      }

      if (!this.g.isEmpty()) {
         NBTTagList var3 = new NBTTagList();
         this.g.forEach(var1x -> var3.add(var1x.a(var0xx -> BuiltInRegistries.d.b(var0xx).toString())));
         var0.a("neighbor_fluid_ticks", var3);
      }

      return var0;
   }

   static enum Type implements ChunkConverter.a {
      a(
         Blocks.kL,
         Blocks.ed,
         Blocks.lJ,
         Blocks.lK,
         Blocks.lL,
         Blocks.lM,
         Blocks.lN,
         Blocks.lO,
         Blocks.lP,
         Blocks.lQ,
         Blocks.lR,
         Blocks.lS,
         Blocks.lT,
         Blocks.lU,
         Blocks.lV,
         Blocks.lW,
         Blocks.lX,
         Blocks.lY,
         Blocks.gR,
         Blocks.gS,
         Blocks.gT,
         Blocks.fz,
         Blocks.L,
         Blocks.I,
         Blocks.K,
         Blocks.cD,
         Blocks.cE,
         Blocks.cF,
         Blocks.cG,
         Blocks.cH,
         Blocks.cI,
         Blocks.cJ,
         Blocks.cQ,
         Blocks.cR,
         Blocks.cS,
         Blocks.cT,
         Blocks.cV,
         Blocks.cW,
         Blocks.cZ,
         Blocks.da,
         Blocks.db,
         Blocks.dc,
         Blocks.de,
         Blocks.df,
         Blocks.dk,
         Blocks.dl,
         Blocks.dm,
         Blocks.dn,
         Blocks.dp,
         Blocks.dq
      ) {
         @Override
         public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
            return var0;
         }
      },
      b {
         @Override
         public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
            return var0.a(var1, var3.a_(var5), var3, var4, var5);
         }
      },
      c(Blocks.cu, Blocks.gU) {
         @Override
         public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
            if (var2.a(var0.b()) && var1.o().d() && var0.c(BlockChest.c) == BlockPropertyChestType.a && var2.c(BlockChest.c) == BlockPropertyChestType.a) {
               EnumDirection var6 = var0.c(BlockChest.b);
               if (var1.o() != var6.o() && var6 == var2.c(BlockChest.b)) {
                  BlockPropertyChestType var7 = var1 == var6.h() ? BlockPropertyChestType.b : BlockPropertyChestType.c;
                  var3.a(var5, var2.a(BlockChest.c, var7.a()), 18);
                  if (var6 == EnumDirection.c || var6 == EnumDirection.f) {
                     TileEntity var8 = var3.c_(var4);
                     TileEntity var9 = var3.c_(var5);
                     if (var8 instanceof TileEntityChest && var9 instanceof TileEntityChest) {
                        TileEntityChest.a((TileEntityChest)var8, (TileEntityChest)var9);
                     }
                  }

                  return var0.a(BlockChest.c, var7);
               }
            }

            return var0;
         }
      },
      d(true, Blocks.aH, Blocks.aI, Blocks.aF, Blocks.aJ, Blocks.aG, Blocks.aD, Blocks.aE) {
         private final ThreadLocal<List<ObjectSet<BlockPosition>>> g = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));

         @Override
         public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
            IBlockData var6 = var0.a(var1, var3.a_(var5), var3, var4, var5);
            if (var0 != var6) {
               int var7 = var6.c(BlockProperties.aC);
               List<ObjectSet<BlockPosition>> var8 = this.g.get();
               if (var8.isEmpty()) {
                  for(int var9 = 0; var9 < 7; ++var9) {
                     var8.add(new ObjectOpenHashSet());
                  }
               }

               ((ObjectSet)var8.get(var7)).add(var4.i());
            }

            return var0;
         }

         @Override
         public void a(GeneratorAccess var0) {
            BlockPosition.MutableBlockPosition var1 = new BlockPosition.MutableBlockPosition();
            List<ObjectSet<BlockPosition>> var2 = this.g.get();

            for(int var3 = 2; var3 < var2.size(); ++var3) {
               int var4 = var3 - 1;
               ObjectSet<BlockPosition> var5 = (ObjectSet)var2.get(var4);
               ObjectSet<BlockPosition> var6 = (ObjectSet)var2.get(var3);
               ObjectIterator var8x = var5.iterator();

               while(var8x.hasNext()) {
                  BlockPosition var8x = (BlockPosition)var8x.next();
                  IBlockData var9 = var0.a_(var8x);
                  if (var9.c(BlockProperties.aC) >= var4) {
                     var0.a(var8x, var9.a(BlockProperties.aC, Integer.valueOf(var4)), 18);
                     if (var3 != 7) {
                        for(EnumDirection var13 : f) {
                           var1.a(var8x, var13);
                           IBlockData var14 = var0.a_(var1);
                           if (var14.b(BlockProperties.aC) && var9.c(BlockProperties.aC) > var3) {
                              var6.add(var1.i());
                           }
                        }
                     }
                  }
               }
            }

            var2.clear();
         }
      },
      e(Blocks.fd, Blocks.fc) {
         @Override
         public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
            if (var0.c(BlockStem.b) == 7) {
               BlockStemmed var6 = ((BlockStem)var0.b()).b();
               if (var2.a(var6)) {
                  return var6.c().o().a(BlockFacingHorizontal.aD, var1);
               }
            }

            return var0;
         }
      };

      public static final EnumDirection[] f = EnumDirection.values();

      Type(Block... var2) {
         this(false, var2);
      }

      Type(boolean var2, Block... var3) {
         for(Block var7 : var3) {
            ChunkConverter.i.put(var7, this);
         }

         if (var2) {
            ChunkConverter.j.add(this);
         }
      }
   }

   public interface a {
      IBlockData a(IBlockData var1, EnumDirection var2, IBlockData var3, GeneratorAccess var4, BlockPosition var5, BlockPosition var6);

      default void a(GeneratorAccess var0) {
      }
   }
}
