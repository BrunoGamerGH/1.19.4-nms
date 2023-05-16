package net.minecraft.world.entity.npc;

import com.google.common.collect.ImmutableSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record VillagerProfession(
   String name,
   Predicate<Holder<VillagePlaceType>> heldJobSite,
   Predicate<Holder<VillagePlaceType>> acquirableJobSite,
   ImmutableSet<Item> requestedItems,
   ImmutableSet<Block> secondaryPoi,
   @Nullable SoundEffect workSound
) {
   private final String q;
   private final Predicate<Holder<VillagePlaceType>> r;
   private final Predicate<Holder<VillagePlaceType>> s;
   private final ImmutableSet<Item> t;
   private final ImmutableSet<Block> u;
   @Nullable
   private final SoundEffect v;
   public static final Predicate<Holder<VillagePlaceType>> a = var0 -> var0.a(PoiTypeTags.a);
   public static final VillagerProfession b = a("none", VillagePlaceType.a, a, null);
   public static final VillagerProfession c = a("armorer", PoiTypes.a, SoundEffects.yp);
   public static final VillagerProfession d = a("butcher", PoiTypes.b, SoundEffects.yq);
   public static final VillagerProfession e = a("cartographer", PoiTypes.c, SoundEffects.yr);
   public static final VillagerProfession f = a("cleric", PoiTypes.d, SoundEffects.ys);
   public static final VillagerProfession g = a(
      "farmer", PoiTypes.e, ImmutableSet.of(Items.oE, Items.oD, Items.um, Items.qG), ImmutableSet.of(Blocks.cB), SoundEffects.yt
   );
   public static final VillagerProfession h = a("fisherman", PoiTypes.f, SoundEffects.yu);
   public static final VillagerProfession i = a("fletcher", PoiTypes.g, SoundEffects.yv);
   public static final VillagerProfession j = a("leatherworker", PoiTypes.h, SoundEffects.yw);
   public static final VillagerProfession k = a("librarian", PoiTypes.i, SoundEffects.yx);
   public static final VillagerProfession l = a("mason", PoiTypes.j, SoundEffects.yy);
   public static final VillagerProfession m = a("nitwit", VillagePlaceType.a, VillagePlaceType.a, null);
   public static final VillagerProfession n = a("shepherd", PoiTypes.k, SoundEffects.yz);
   public static final VillagerProfession o = a("toolsmith", PoiTypes.l, SoundEffects.yA);
   public static final VillagerProfession p = a("weaponsmith", PoiTypes.m, SoundEffects.yB);

   public VillagerProfession(
      String var0,
      Predicate<Holder<VillagePlaceType>> var1,
      Predicate<Holder<VillagePlaceType>> var2,
      ImmutableSet<Item> var3,
      ImmutableSet<Block> var4,
      @Nullable SoundEffect var5
   ) {
      this.q = var0;
      this.r = var1;
      this.s = var2;
      this.t = var3;
      this.u = var4;
      this.v = var5;
   }

   @Override
   public String toString() {
      return this.q;
   }

   private static VillagerProfession a(String var0, ResourceKey<VillagePlaceType> var1, @Nullable SoundEffect var2) {
      return a(var0, var1x -> var1x.a(var1), var1x -> var1x.a(var1), var2);
   }

   private static VillagerProfession a(
      String var0, Predicate<Holder<VillagePlaceType>> var1, Predicate<Holder<VillagePlaceType>> var2, @Nullable SoundEffect var3
   ) {
      return a(var0, var1, var2, ImmutableSet.of(), ImmutableSet.of(), var3);
   }

   private static VillagerProfession a(
      String var0, ResourceKey<VillagePlaceType> var1, ImmutableSet<Item> var2, ImmutableSet<Block> var3, @Nullable SoundEffect var4
   ) {
      return a(var0, var1x -> var1x.a(var1), var1x -> var1x.a(var1), var2, var3, var4);
   }

   private static VillagerProfession a(
      String var0,
      Predicate<Holder<VillagePlaceType>> var1,
      Predicate<Holder<VillagePlaceType>> var2,
      ImmutableSet<Item> var3,
      ImmutableSet<Block> var4,
      @Nullable SoundEffect var5
   ) {
      return IRegistry.a(BuiltInRegistries.z, new MinecraftKey(var0), new VillagerProfession(var0, var1, var2, var3, var4, var5));
   }

   public String a() {
      return this.q;
   }

   public Predicate<Holder<VillagePlaceType>> b() {
      return this.r;
   }

   public Predicate<Holder<VillagePlaceType>> c() {
      return this.s;
   }

   public ImmutableSet<Item> d() {
      return this.t;
   }

   public ImmutableSet<Block> e() {
      return this.u;
   }

   @Nullable
   public SoundEffect f() {
      return this.v;
   }
}
