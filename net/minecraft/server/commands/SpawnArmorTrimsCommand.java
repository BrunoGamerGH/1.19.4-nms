package net.minecraft.server.commands;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.EnumArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraft.world.level.World;

public class SpawnArmorTrimsCommand {
   private static final Map<Pair<ArmorMaterial, EnumItemSlot>, Item> a = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put(Pair.of(EnumArmorMaterial.b, EnumItemSlot.f), Items.oK);
      var0.put(Pair.of(EnumArmorMaterial.b, EnumItemSlot.e), Items.oL);
      var0.put(Pair.of(EnumArmorMaterial.b, EnumItemSlot.d), Items.oM);
      var0.put(Pair.of(EnumArmorMaterial.b, EnumItemSlot.c), Items.oN);
      var0.put(Pair.of(EnumArmorMaterial.c, EnumItemSlot.f), Items.oO);
      var0.put(Pair.of(EnumArmorMaterial.c, EnumItemSlot.e), Items.oP);
      var0.put(Pair.of(EnumArmorMaterial.c, EnumItemSlot.d), Items.oQ);
      var0.put(Pair.of(EnumArmorMaterial.c, EnumItemSlot.c), Items.oR);
      var0.put(Pair.of(EnumArmorMaterial.d, EnumItemSlot.f), Items.oW);
      var0.put(Pair.of(EnumArmorMaterial.d, EnumItemSlot.e), Items.oX);
      var0.put(Pair.of(EnumArmorMaterial.d, EnumItemSlot.d), Items.oY);
      var0.put(Pair.of(EnumArmorMaterial.d, EnumItemSlot.c), Items.oZ);
      var0.put(Pair.of(EnumArmorMaterial.g, EnumItemSlot.f), Items.pa);
      var0.put(Pair.of(EnumArmorMaterial.g, EnumItemSlot.e), Items.pb);
      var0.put(Pair.of(EnumArmorMaterial.g, EnumItemSlot.d), Items.pc);
      var0.put(Pair.of(EnumArmorMaterial.g, EnumItemSlot.c), Items.pd);
      var0.put(Pair.of(EnumArmorMaterial.e, EnumItemSlot.f), Items.oS);
      var0.put(Pair.of(EnumArmorMaterial.e, EnumItemSlot.e), Items.oT);
      var0.put(Pair.of(EnumArmorMaterial.e, EnumItemSlot.d), Items.oU);
      var0.put(Pair.of(EnumArmorMaterial.e, EnumItemSlot.c), Items.oV);
      var0.put(Pair.of(EnumArmorMaterial.f, EnumItemSlot.f), Items.ny);
   });
   private static final List<ResourceKey<TrimPattern>> b = List.of(
      TrimPatterns.a,
      TrimPatterns.b,
      TrimPatterns.c,
      TrimPatterns.d,
      TrimPatterns.e,
      TrimPatterns.f,
      TrimPatterns.g,
      TrimPatterns.h,
      TrimPatterns.i,
      TrimPatterns.j,
      TrimPatterns.k
   );
   private static final List<ResourceKey<TrimMaterial>> c = List.of(
      TrimMaterials.a,
      TrimMaterials.b,
      TrimMaterials.c,
      TrimMaterials.d,
      TrimMaterials.e,
      TrimMaterials.f,
      TrimMaterials.g,
      TrimMaterials.h,
      TrimMaterials.i,
      TrimMaterials.j
   );
   private static final ToIntFunction<ResourceKey<TrimPattern>> d = SystemUtils.e(b);
   private static final ToIntFunction<ResourceKey<TrimMaterial>> e = SystemUtils.e(c);

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("spawn_armor_trims")
               .requires(var0x -> var0x.c(2) && var0x.e().G().b(FeatureFlags.c)))
            .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ((CommandListenerWrapper)var0x.getSource()).h()))
      );
   }

   private static int a(CommandListenerWrapper var0, EntityHuman var1) {
      World var2 = var1.Y();
      NonNullList<ArmorTrim> var3 = NonNullList.a();
      IRegistry<TrimPattern> var4 = var2.u_().d(Registries.aC);
      IRegistry<TrimMaterial> var5 = var2.u_().d(Registries.aB);
      var4.s()
         .sorted(Comparator.comparing(var1x -> d.applyAsInt(var4.c(var1x).orElse(null))))
         .forEachOrdered(
            var3x -> var5.s()
                  .sorted(Comparator.comparing(var1xx -> e.applyAsInt(var5.c(var1xx).orElse(null))))
                  .forEachOrdered(var4x -> var3.add(new ArmorTrim(var5.d(var4x), var4.d(var3x))))
         );
      BlockPosition var6 = var1.dg().a(var1.cA(), 5);
      int var7 = EnumArmorMaterial.values().length - 1;
      double var8 = 3.0;
      int var10 = 0;
      int var11 = 0;

      for(ArmorTrim var13 : var3) {
         for(ArmorMaterial var17 : EnumArmorMaterial.values()) {
            if (var17 != EnumArmorMaterial.a) {
               double var18 = (double)var6.u() + 0.5 - (double)(var10 % var5.b()) * 3.0;
               double var20 = (double)var6.v() + 0.5 + (double)(var11 % var7) * 3.0;
               double var22 = (double)var6.w() + 0.5 + (double)(var10 / var5.b() * 10);
               EntityArmorStand var24 = new EntityArmorStand(var2, var18, var20, var22);
               var24.f(180.0F);
               var24.e(true);

               for(EnumItemSlot var28 : EnumItemSlot.values()) {
                  Item var29 = a.get(Pair.of(var17, var28));
                  if (var29 != null) {
                     ItemStack var30 = new ItemStack(var29);
                     ArmorTrim.a(var2.u_(), var30, var13);
                     var24.a(var28, var30);
                     if (var29 instanceof ItemArmor var31 && var31.d() == EnumArmorMaterial.f) {
                        var24.b(var13.a().a().a(var13.b()).e().f(" ").b(var13.b().a().e()));
                        var24.n(true);
                        continue;
                     }

                     var24.j(true);
                  }
               }

               var2.b(var24);
               ++var11;
            }
         }

         ++var10;
      }

      var0.a(IChatBaseComponent.b("Armorstands with trimmed armor spawned around you"), true);
      return 1;
   }
}
