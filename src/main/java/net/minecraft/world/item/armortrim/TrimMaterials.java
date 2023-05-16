package net.minecraft.world.item.armortrim;

import java.util.Map;
import java.util.Optional;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EnumArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TrimMaterials {
   public static final ResourceKey<TrimMaterial> a = a("quartz");
   public static final ResourceKey<TrimMaterial> b = a("iron");
   public static final ResourceKey<TrimMaterial> c = a("netherite");
   public static final ResourceKey<TrimMaterial> d = a("redstone");
   public static final ResourceKey<TrimMaterial> e = a("copper");
   public static final ResourceKey<TrimMaterial> f = a("gold");
   public static final ResourceKey<TrimMaterial> g = a("emerald");
   public static final ResourceKey<TrimMaterial> h = a("diamond");
   public static final ResourceKey<TrimMaterial> i = a("lapis");
   public static final ResourceKey<TrimMaterial> j = a("amethyst");

   public static void a(BootstapContext<TrimMaterial> var0) {
   }

   public static void b(BootstapContext<TrimMaterial> var0) {
      a(var0, a, Items.nJ, ChatModifier.a.a(14931140), 0.1F);
      a(var0, b, Items.nM, ChatModifier.a.a(15527148), 0.2F, Map.of(EnumArmorMaterial.c, "iron_darker"));
      a(var0, c, Items.nR, ChatModifier.a.a(6445145), 0.3F, Map.of(EnumArmorMaterial.g, "netherite_darker"));
      a(var0, d, Items.li, ChatModifier.a.a(9901575), 0.4F);
      a(var0, e, Items.nO, ChatModifier.a.a(11823181), 0.5F);
      a(var0, f, Items.nQ, ChatModifier.a.a(14594349), 0.6F, Map.of(EnumArmorMaterial.d, "gold_darker"));
      a(var0, g, Items.nH, ChatModifier.a.a(1155126), 0.7F);
      a(var0, h, Items.nG, ChatModifier.a.a(7269586), 0.8F, Map.of(EnumArmorMaterial.e, "diamond_darker"));
      a(var0, i, Items.nI, ChatModifier.a.a(4288151), 0.9F);
      a(var0, j, Items.nK, ChatModifier.a.a(10116294), 1.0F);
   }

   public static Optional<Holder.c<TrimMaterial>> a(IRegistryCustom var0, ItemStack var1) {
      return var0.d(Registries.aB).h().filter(var1x -> var1.a(var1x.a().b())).findFirst();
   }

   private static void a(BootstapContext<TrimMaterial> var0, ResourceKey<TrimMaterial> var1, Item var2, ChatModifier var3, float var4) {
      a(var0, var1, var2, var3, var4, Map.of());
   }

   private static void a(
      BootstapContext<TrimMaterial> var0, ResourceKey<TrimMaterial> var1, Item var2, ChatModifier var3, float var4, Map<EnumArmorMaterial, String> var5
   ) {
      TrimMaterial var6 = TrimMaterial.a(var1.a().a(), var2, var4, IChatBaseComponent.c(SystemUtils.a("trim_material", var1.a())).c(var3), var5);
      var0.a(var1, var6);
   }

   private static ResourceKey<TrimMaterial> a(String var0) {
      return ResourceKey.a(Registries.aB, new MinecraftKey(var0));
   }
}
