package net.minecraft.world.entity.ai.village.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyBedPart;

public class PoiTypes {
   public static final ResourceKey<VillagePlaceType> a = a("armorer");
   public static final ResourceKey<VillagePlaceType> b = a("butcher");
   public static final ResourceKey<VillagePlaceType> c = a("cartographer");
   public static final ResourceKey<VillagePlaceType> d = a("cleric");
   public static final ResourceKey<VillagePlaceType> e = a("farmer");
   public static final ResourceKey<VillagePlaceType> f = a("fisherman");
   public static final ResourceKey<VillagePlaceType> g = a("fletcher");
   public static final ResourceKey<VillagePlaceType> h = a("leatherworker");
   public static final ResourceKey<VillagePlaceType> i = a("librarian");
   public static final ResourceKey<VillagePlaceType> j = a("mason");
   public static final ResourceKey<VillagePlaceType> k = a("shepherd");
   public static final ResourceKey<VillagePlaceType> l = a("toolsmith");
   public static final ResourceKey<VillagePlaceType> m = a("weaponsmith");
   public static final ResourceKey<VillagePlaceType> n = a("home");
   public static final ResourceKey<VillagePlaceType> o = a("meeting");
   public static final ResourceKey<VillagePlaceType> p = a("beehive");
   public static final ResourceKey<VillagePlaceType> q = a("bee_nest");
   public static final ResourceKey<VillagePlaceType> r = a("nether_portal");
   public static final ResourceKey<VillagePlaceType> s = a("lodestone");
   public static final ResourceKey<VillagePlaceType> t = a("lightning_rod");
   private static final Set<IBlockData> u = ImmutableList.of(
         Blocks.bm,
         Blocks.bn,
         Blocks.bj,
         Blocks.bk,
         Blocks.bh,
         Blocks.bf,
         Blocks.bl,
         Blocks.bb,
         Blocks.bg,
         Blocks.bd,
         Blocks.ba,
         Blocks.aZ,
         new Block[]{Blocks.be, Blocks.bi, Blocks.aY, Blocks.bc}
      )
      .stream()
      .flatMap(var0 -> var0.n().a().stream())
      .filter(var0 -> var0.c(BlockBed.a) == BlockPropertyBedPart.a)
      .collect(ImmutableSet.toImmutableSet());
   private static final Set<IBlockData> v = ImmutableList.of(Blocks.fs, Blocks.fu, Blocks.ft, Blocks.fv)
      .stream()
      .flatMap(var0 -> var0.n().a().stream())
      .collect(ImmutableSet.toImmutableSet());
   private static final Map<IBlockData, Holder<VillagePlaceType>> w = Maps.newHashMap();

   private static Set<IBlockData> a(Block var0) {
      return ImmutableSet.copyOf(var0.n().a());
   }

   private static ResourceKey<VillagePlaceType> a(String var0) {
      return ResourceKey.a(Registries.R, new MinecraftKey(var0));
   }

   private static VillagePlaceType a(IRegistry<VillagePlaceType> var0, ResourceKey<VillagePlaceType> var1, Set<IBlockData> var2, int var3, int var4) {
      VillagePlaceType var5 = new VillagePlaceType(var2, var3, var4);
      IRegistry.a(var0, var1, var5);
      a(var0.f(var1), var2);
      return var5;
   }

   private static void a(Holder<VillagePlaceType> var0, Set<IBlockData> var1) {
      var1.forEach(var1x -> {
         Holder<VillagePlaceType> var2 = w.put(var1x, var0);
         if (var2 != null) {
            throw (IllegalStateException)SystemUtils.b(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", var1x)));
         }
      });
   }

   public static Optional<Holder<VillagePlaceType>> a(IBlockData var0) {
      return Optional.ofNullable(w.get(var0));
   }

   public static boolean b(IBlockData var0) {
      return w.containsKey(var0);
   }

   public static VillagePlaceType a(IRegistry<VillagePlaceType> var0) {
      a(var0, a, a(Blocks.nS), 1, 1);
      a(var0, b, a(Blocks.nR), 1, 1);
      a(var0, c, a(Blocks.nT), 1, 1);
      a(var0, d, a(Blocks.fr), 1, 1);
      a(var0, e, a(Blocks.oY), 1, 1);
      a(var0, f, a(Blocks.nQ), 1, 1);
      a(var0, g, a(Blocks.nU), 1, 1);
      a(var0, h, v, 1, 1);
      a(var0, i, a(Blocks.nW), 1, 1);
      a(var0, j, a(Blocks.nY), 1, 1);
      a(var0, k, a(Blocks.nP), 1, 1);
      a(var0, l, a(Blocks.nX), 1, 1);
      a(var0, m, a(Blocks.nV), 1, 1);
      a(var0, n, u, 1, 1);
      a(var0, o, a(Blocks.nZ), 32, 6);
      a(var0, p, a(Blocks.pb), 0, 1);
      a(var0, q, a(Blocks.pa), 0, 1);
      a(var0, r, a(Blocks.ed), 0, 1);
      a(var0, s, a(Blocks.pm), 0, 1);
      return a(var0, t, a(Blocks.rm), 0, 1);
   }
}
