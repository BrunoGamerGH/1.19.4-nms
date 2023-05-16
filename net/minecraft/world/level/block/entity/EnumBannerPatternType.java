package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EnumColor;

public class EnumBannerPatternType {
   final String a;

   public EnumBannerPatternType(String var0) {
      this.a = var0;
   }

   public static MinecraftKey a(ResourceKey<EnumBannerPatternType> var0, boolean var1) {
      String var2 = var1 ? "banner" : "shield";
      return var0.a().d("entity/" + var2 + "/");
   }

   public String a() {
      return this.a;
   }

   @Nullable
   public static Holder<EnumBannerPatternType> a(String var0) {
      return BuiltInRegistries.ak.h().filter(var1 -> var1.a().a.equals(var0)).findAny().orElse(null);
   }

   public static class a {
      private final List<Pair<Holder<EnumBannerPatternType>, EnumColor>> a = Lists.newArrayList();

      public EnumBannerPatternType.a a(ResourceKey<EnumBannerPatternType> var0, EnumColor var1) {
         return this.a(BuiltInRegistries.ak.f(var0), var1);
      }

      public EnumBannerPatternType.a a(Holder<EnumBannerPatternType> var0, EnumColor var1) {
         return this.a(Pair.of(var0, var1));
      }

      public EnumBannerPatternType.a a(Pair<Holder<EnumBannerPatternType>, EnumColor> var0) {
         this.a.add(var0);
         return this;
      }

      public NBTTagList a() {
         NBTTagList var0 = new NBTTagList();

         for(Pair<Holder<EnumBannerPatternType>, EnumColor> var2 : this.a) {
            NBTTagCompound var3 = new NBTTagCompound();
            var3.a("Pattern", ((EnumBannerPatternType)((Holder)var2.getFirst()).a()).a);
            var3.a("Color", ((EnumColor)var2.getSecond()).a());
            var0.add(var3);
         }

         return var0;
      }
   }
}
