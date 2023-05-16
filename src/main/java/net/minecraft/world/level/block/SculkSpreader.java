package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;
import org.slf4j.Logger;

public class SculkSpreader {
   public static final int a = 24;
   public static final int b = 1000;
   public static final float c = 0.5F;
   private static final int e = 32;
   public static final int d = 11;
   final boolean f;
   private final TagKey<Block> g;
   private final int h;
   private final int i;
   private final int j;
   private final int k;
   private List<SculkSpreader.a> l = new ArrayList<>();
   private static final Logger m = LogUtils.getLogger();

   public SculkSpreader(boolean var0, TagKey<Block> var1, int var2, int var3, int var4, int var5) {
      this.f = var0;
      this.g = var1;
      this.h = var2;
      this.i = var3;
      this.j = var4;
      this.k = var5;
   }

   public static SculkSpreader a() {
      return new SculkSpreader(false, TagsBlock.bG, 10, 4, 10, 5);
   }

   public static SculkSpreader b() {
      return new SculkSpreader(true, TagsBlock.bH, 50, 1, 5, 10);
   }

   public TagKey<Block> c() {
      return this.g;
   }

   public int d() {
      return this.h;
   }

   public int e() {
      return this.i;
   }

   public int f() {
      return this.j;
   }

   public int g() {
      return this.k;
   }

   public boolean h() {
      return this.f;
   }

   @VisibleForTesting
   public List<SculkSpreader.a> i() {
      return this.l;
   }

   public void j() {
      this.l.clear();
   }

   public void a(NBTTagCompound var0) {
      if (var0.b("cursors", 9)) {
         this.l.clear();
         List<SculkSpreader.a> var1 = (List)SculkSpreader.a.b
            .listOf()
            .parse(new Dynamic(DynamicOpsNBT.a, var0.c("cursors", 10)))
            .resultOrPartial(m::error)
            .orElseGet(ArrayList::new);
         int var2 = Math.min(var1.size(), 32);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.a(var1.get(var3));
         }
      }
   }

   public void b(NBTTagCompound var0) {
      SculkSpreader.a.b.listOf().encodeStart(DynamicOpsNBT.a, this.l).resultOrPartial(m::error).ifPresent(var1x -> var0.a("cursors", var1x));
   }

   public void a(BlockPosition var0, int var1) {
      while(var1 > 0) {
         int var2 = Math.min(var1, 1000);
         this.a(new SculkSpreader.a(var0, var2));
         var1 -= var2;
      }
   }

   private void a(SculkSpreader.a var0) {
      if (this.l.size() < 32) {
         this.l.add(var0);
      }
   }

   public void a(GeneratorAccess var0, BlockPosition var1, RandomSource var2, boolean var3) {
      if (!this.l.isEmpty()) {
         List<SculkSpreader.a> var4 = new ArrayList<>();
         Map<BlockPosition, SculkSpreader.a> var5 = new HashMap<>();
         Object2IntMap<BlockPosition> var6 = new Object2IntOpenHashMap();

         for(SculkSpreader.a var8 : this.l) {
            var8.a(var0, var1, var2, this, var3);
            if (var8.e <= 0) {
               var0.c(3006, var8.a(), 0);
            } else {
               BlockPosition var9 = var8.a();
               var6.computeInt(var9, (var1x, var2x) -> (var2x == null ? 0 : var2x) + var8.e);
               SculkSpreader.a var10 = var5.get(var9);
               if (var10 == null) {
                  var5.put(var9, var8);
                  var4.add(var8);
               } else if (!this.h() && var8.e + var10.e <= 1000) {
                  var10.a(var8);
               } else {
                  var4.add(var8);
                  if (var8.e < var10.e) {
                     var5.put(var9, var8);
                  }
               }
            }
         }

         ObjectIterator var16 = var6.object2IntEntrySet().iterator();

         while(var16.hasNext()) {
            Entry<BlockPosition> var8 = (Entry)var16.next();
            BlockPosition var9 = (BlockPosition)var8.getKey();
            int var10 = var8.getIntValue();
            SculkSpreader.a var11 = var5.get(var9);
            Collection<EnumDirection> var12 = var11 == null ? null : var11.d();
            if (var10 > 0 && var12 != null) {
               int var13 = (int)(Math.log1p((double)var10) / 2.3F) + 1;
               int var14 = (var13 << 6) + MultifaceBlock.a(var12);
               var0.c(3006, var9, var14);
            }
         }

         this.l = var4;
      }
   }

   public static class a {
      private static final ObjectArrayList<BaseBlockPosition> c = SystemUtils.a(
         new ObjectArrayList(18),
         var0 -> BlockPosition.b(new BlockPosition(-1, -1, -1), new BlockPosition(1, 1, 1))
               .filter(var0x -> (var0x.u() == 0 || var0x.v() == 0 || var0x.w() == 0) && !var0x.equals(BlockPosition.b))
               .map(BlockPosition::i)
               .forEach(var0::add)
      );
      public static final int a = 1;
      private BlockPosition d;
      int e;
      private int f;
      private int g;
      @Nullable
      private Set<EnumDirection> h;
      private static final Codec<Set<EnumDirection>> i = EnumDirection.g
         .listOf()
         .xmap(var0 -> Sets.newEnumSet(var0, EnumDirection.class), Lists::newArrayList);
      public static final Codec<SculkSpreader.a> b = RecordCodecBuilder.create(
         var0 -> var0.group(
                  BlockPosition.a.fieldOf("pos").forGetter(SculkSpreader.a::a),
                  Codec.intRange(0, 1000).fieldOf("charge").orElse(0).forGetter(SculkSpreader.a::b),
                  Codec.intRange(0, 1).fieldOf("decay_delay").orElse(1).forGetter(SculkSpreader.a::c),
                  Codec.intRange(0, Integer.MAX_VALUE).fieldOf("update_delay").orElse(0).forGetter(var0x -> var0x.f),
                  i.optionalFieldOf("facings").forGetter(var0x -> Optional.ofNullable(var0x.d()))
               )
               .apply(var0, SculkSpreader.a::new)
      );

      private a(BlockPosition var0, int var1, int var2, int var3, Optional<Set<EnumDirection>> var4) {
         this.d = var0;
         this.e = var1;
         this.g = var2;
         this.f = var3;
         this.h = var4.orElse(null);
      }

      public a(BlockPosition var0, int var1) {
         this(var0, var1, 1, 0, Optional.empty());
      }

      public BlockPosition a() {
         return this.d;
      }

      public int b() {
         return this.e;
      }

      public int c() {
         return this.g;
      }

      @Nullable
      public Set<EnumDirection> d() {
         return this.h;
      }

      private boolean a(GeneratorAccess var0, BlockPosition var1, boolean var2) {
         if (this.e <= 0) {
            return false;
         } else if (var2) {
            return true;
         } else {
            return var0 instanceof WorldServer var3 ? var3.m(var1) : false;
         }
      }

      public void a(GeneratorAccess var0, BlockPosition var1, RandomSource var2, SculkSpreader var3, boolean var4) {
         if (this.a(var0, var1, var3.f)) {
            if (this.f > 0) {
               --this.f;
            } else {
               IBlockData var5 = var0.a_(this.d);
               SculkBehaviour var6 = a(var5);
               if (var4 && var6.a(var0, this.d, var5, this.h, var3.h())) {
                  if (var6.b()) {
                     var5 = var0.a_(this.d);
                     var6 = a(var5);
                  }

                  var0.a(null, this.d, SoundEffects.tQ, SoundCategory.e, 1.0F, 1.0F);
               }

               this.e = var6.a(this, var0, var1, var2, var3, var4);
               if (this.e <= 0) {
                  var6.a(var0, var5, this.d, var2);
               } else {
                  BlockPosition var7 = a(var0, this.d, var2);
                  if (var7 != null) {
                     var6.a(var0, var5, this.d, var2);
                     this.d = var7.i();
                     if (var3.h() && !this.d.a(new BaseBlockPosition(var1.u(), this.d.v(), var1.w()), 15.0)) {
                        this.e = 0;
                        return;
                     }

                     var5 = var0.a_(var7);
                  }

                  if (var5.b() instanceof SculkBehaviour) {
                     this.h = MultifaceBlock.h(var5);
                  }

                  this.g = var6.i_(this.g);
                  this.f = var6.a();
               }
            }
         }
      }

      void a(SculkSpreader.a var0) {
         this.e += var0.e;
         var0.e = 0;
         this.f = Math.min(this.f, var0.f);
      }

      private static SculkBehaviour a(IBlockData var0) {
         Block var2 = var0.b();
         return var2 instanceof SculkBehaviour var1 ? var1 : SculkBehaviour.r_;
      }

      private static List<BaseBlockPosition> a(RandomSource var0) {
         return SystemUtils.a(c, var0);
      }

      @Nullable
      private static BlockPosition a(GeneratorAccess var0, BlockPosition var1, RandomSource var2) {
         BlockPosition.MutableBlockPosition var3 = var1.j();
         BlockPosition.MutableBlockPosition var4 = var1.j();

         for(BaseBlockPosition var6 : a(var2)) {
            var4.a(var1, var6);
            IBlockData var7 = var0.a_(var4);
            if (var7.b() instanceof SculkBehaviour && a(var0, var1, var4)) {
               var3.g(var4);
               if (SculkVeinBlock.a(var0, var7, var4)) {
                  break;
               }
            }
         }

         return var3.equals(var1) ? null : var3;
      }

      private static boolean a(GeneratorAccess var0, BlockPosition var1, BlockPosition var2) {
         if (var1.k(var2) == 1) {
            return true;
         } else {
            BlockPosition var3 = var2.b(var1);
            EnumDirection var4 = EnumDirection.a(
               EnumDirection.EnumAxis.a, var3.u() < 0 ? EnumDirection.EnumAxisDirection.b : EnumDirection.EnumAxisDirection.a
            );
            EnumDirection var5 = EnumDirection.a(
               EnumDirection.EnumAxis.b, var3.v() < 0 ? EnumDirection.EnumAxisDirection.b : EnumDirection.EnumAxisDirection.a
            );
            EnumDirection var6 = EnumDirection.a(
               EnumDirection.EnumAxis.c, var3.w() < 0 ? EnumDirection.EnumAxisDirection.b : EnumDirection.EnumAxisDirection.a
            );
            if (var3.u() == 0) {
               return a(var0, var1, var5) || a(var0, var1, var6);
            } else if (var3.v() == 0) {
               return a(var0, var1, var4) || a(var0, var1, var6);
            } else {
               return a(var0, var1, var4) || a(var0, var1, var5);
            }
         }
      }

      private static boolean a(GeneratorAccess var0, BlockPosition var1, EnumDirection var2) {
         BlockPosition var3 = var1.a(var2);
         return !var0.a_(var3).d(var0, var3, var2.g());
      }
   }
}
