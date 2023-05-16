package net.minecraft.world.inventory;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public class Containers<T extends Container> implements FeatureElement {
   public static final Containers<ContainerChest> a = a("generic_9x1", ContainerChest::a);
   public static final Containers<ContainerChest> b = a("generic_9x2", ContainerChest::b);
   public static final Containers<ContainerChest> c = a("generic_9x3", ContainerChest::c);
   public static final Containers<ContainerChest> d = a("generic_9x4", ContainerChest::d);
   public static final Containers<ContainerChest> e = a("generic_9x5", ContainerChest::e);
   public static final Containers<ContainerChest> f = a("generic_9x6", ContainerChest::f);
   public static final Containers<ContainerDispenser> g = a("generic_3x3", ContainerDispenser::new);
   public static final Containers<ContainerAnvil> h = a("anvil", ContainerAnvil::new);
   public static final Containers<ContainerBeacon> i = a("beacon", ContainerBeacon::new);
   public static final Containers<ContainerBlastFurnace> j = a("blast_furnace", ContainerBlastFurnace::new);
   public static final Containers<ContainerBrewingStand> k = a("brewing_stand", ContainerBrewingStand::new);
   public static final Containers<ContainerWorkbench> l = a("crafting", ContainerWorkbench::new);
   public static final Containers<ContainerEnchantTable> m = a("enchantment", ContainerEnchantTable::new);
   public static final Containers<ContainerFurnaceFurnace> n = a("furnace", ContainerFurnaceFurnace::new);
   public static final Containers<ContainerGrindstone> o = a("grindstone", ContainerGrindstone::new);
   public static final Containers<ContainerHopper> p = a("hopper", ContainerHopper::new);
   public static final Containers<ContainerLectern> q = a("lectern", (i, playerinventory) -> new ContainerLectern(i, playerinventory));
   public static final Containers<ContainerLoom> r = a("loom", ContainerLoom::new);
   public static final Containers<ContainerMerchant> s = a("merchant", ContainerMerchant::new);
   public static final Containers<ContainerShulkerBox> t = a("shulker_box", ContainerShulkerBox::new);
   public static final Containers<LegacySmithingMenu> u = a("legacy_smithing", LegacySmithingMenu::new);
   public static final Containers<ContainerSmithing> v = a("smithing", ContainerSmithing::new, FeatureFlags.c);
   public static final Containers<ContainerSmoker> w = a("smoker", ContainerSmoker::new);
   public static final Containers<ContainerCartography> x = a("cartography_table", ContainerCartography::new);
   public static final Containers<ContainerStonecutter> y = a("stonecutter", ContainerStonecutter::new);
   private final FeatureFlagSet z;
   private final Containers.Supplier<T> A;

   private static <T extends Container> Containers<T> a(String s, Containers.Supplier<T> containers_supplier) {
      return IRegistry.a(BuiltInRegistries.r, s, new Containers<>(containers_supplier, FeatureFlags.f));
   }

   private static <T extends Container> Containers<T> a(String s, Containers.Supplier<T> containers_supplier, FeatureFlag... afeatureflag) {
      return IRegistry.a(BuiltInRegistries.r, s, new Containers<>(containers_supplier, FeatureFlags.d.a(afeatureflag)));
   }

   private Containers(Containers.Supplier<T> containers_supplier, FeatureFlagSet featureflagset) {
      this.A = containers_supplier;
      this.z = featureflagset;
   }

   public T a(int i, PlayerInventory playerinventory) {
      return this.A.create(i, playerinventory);
   }

   @Override
   public FeatureFlagSet m() {
      return this.z;
   }

   private interface Supplier<T extends Container> {
      T create(int var1, PlayerInventory var2);
   }
}
