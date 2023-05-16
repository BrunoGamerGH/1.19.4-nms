package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.World;

public class SmithingTemplateItem extends Item {
   private static final EnumChatFormat a = EnumChatFormat.h;
   private static final EnumChatFormat b = EnumChatFormat.j;
   private static final String c = SystemUtils.a("item", new MinecraftKey("smithing_template"));
   private static final IChatBaseComponent d = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.ingredients"))).a(a);
   private static final IChatBaseComponent e = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.applies_to"))).a(a);
   private static final IChatBaseComponent f = IChatBaseComponent.c(SystemUtils.a("upgrade", new MinecraftKey("netherite_upgrade"))).a(a);
   private static final IChatBaseComponent g = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.armor_trim.applies_to"))).a(b);
   private static final IChatBaseComponent h = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.armor_trim.ingredients"))).a(b);
   private static final IChatBaseComponent i = IChatBaseComponent.c(
      SystemUtils.a("item", new MinecraftKey("smithing_template.armor_trim.base_slot_description"))
   );
   private static final IChatBaseComponent j = IChatBaseComponent.c(
      SystemUtils.a("item", new MinecraftKey("smithing_template.armor_trim.additions_slot_description"))
   );
   private static final IChatBaseComponent k = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.netherite_upgrade.applies_to")))
      .a(b);
   private static final IChatBaseComponent r = IChatBaseComponent.c(SystemUtils.a("item", new MinecraftKey("smithing_template.netherite_upgrade.ingredients")))
      .a(b);
   private static final IChatBaseComponent s = IChatBaseComponent.c(
      SystemUtils.a("item", new MinecraftKey("smithing_template.netherite_upgrade.base_slot_description"))
   );
   private static final IChatBaseComponent t = IChatBaseComponent.c(
      SystemUtils.a("item", new MinecraftKey("smithing_template.netherite_upgrade.additions_slot_description"))
   );
   private static final MinecraftKey u = new MinecraftKey("item/empty_armor_slot_helmet");
   private static final MinecraftKey v = new MinecraftKey("item/empty_armor_slot_chestplate");
   private static final MinecraftKey w = new MinecraftKey("item/empty_armor_slot_leggings");
   private static final MinecraftKey x = new MinecraftKey("item/empty_armor_slot_boots");
   private static final MinecraftKey y = new MinecraftKey("item/empty_slot_hoe");
   private static final MinecraftKey z = new MinecraftKey("item/empty_slot_axe");
   private static final MinecraftKey A = new MinecraftKey("item/empty_slot_sword");
   private static final MinecraftKey B = new MinecraftKey("item/empty_slot_shovel");
   private static final MinecraftKey C = new MinecraftKey("item/empty_slot_pickaxe");
   private static final MinecraftKey D = new MinecraftKey("item/empty_slot_ingot");
   private static final MinecraftKey E = new MinecraftKey("item/empty_slot_redstone_dust");
   private static final MinecraftKey F = new MinecraftKey("item/empty_slot_quartz");
   private static final MinecraftKey G = new MinecraftKey("item/empty_slot_emerald");
   private static final MinecraftKey H = new MinecraftKey("item/empty_slot_diamond");
   private static final MinecraftKey I = new MinecraftKey("item/empty_slot_lapis_lazuli");
   private static final MinecraftKey J = new MinecraftKey("item/empty_slot_amethyst_shard");
   private final IChatBaseComponent K;
   private final IChatBaseComponent L;
   private final IChatBaseComponent M;
   private final IChatBaseComponent N;
   private final IChatBaseComponent O;
   private final List<MinecraftKey> P;
   private final List<MinecraftKey> Q;

   public SmithingTemplateItem(
      IChatBaseComponent var0,
      IChatBaseComponent var1,
      IChatBaseComponent var2,
      IChatBaseComponent var3,
      IChatBaseComponent var4,
      List<MinecraftKey> var5,
      List<MinecraftKey> var6
   ) {
      super(new Item.Info().a(FeatureFlags.c));
      this.K = var0;
      this.L = var1;
      this.M = var2;
      this.N = var3;
      this.O = var4;
      this.P = var5;
      this.Q = var6;
   }

   public static SmithingTemplateItem a(ResourceKey<TrimPattern> var0) {
      return a(var0.a());
   }

   public static SmithingTemplateItem a(MinecraftKey var0) {
      return new SmithingTemplateItem(g, h, IChatBaseComponent.c(SystemUtils.a("trim_pattern", var0)).a(a), i, j, A(), B());
   }

   public static SmithingTemplateItem h() {
      return new SmithingTemplateItem(k, r, f, s, t, C(), D());
   }

   private static List<MinecraftKey> A() {
      return List.of(u, v, w, x);
   }

   private static List<MinecraftKey> B() {
      return List.of(D, E, I, F, H, G, J);
   }

   private static List<MinecraftKey> C() {
      return List.of(u, A, v, C, w, z, x, y, B);
   }

   private static List<MinecraftKey> D() {
      return List.of(D);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      super.a(var0, var1, var2, var3);
      var2.add(this.M);
      var2.add(CommonComponents.a);
      var2.add(e);
      var2.add(CommonComponents.a().b(this.K));
      var2.add(d);
      var2.add(CommonComponents.a().b(this.L));
   }

   public IChatBaseComponent i() {
      return this.N;
   }

   public IChatBaseComponent x() {
      return this.O;
   }

   public List<MinecraftKey> y() {
      return this.P;
   }

   public List<MinecraftKey> z() {
      return this.Q;
   }

   @Override
   public String a() {
      return c;
   }
}
