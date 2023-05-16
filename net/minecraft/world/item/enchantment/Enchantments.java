package net.minecraft.world.item.enchantment;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EnumItemSlot;
import org.bukkit.craftbukkit.v1_19_R3.enchantments.CraftEnchantment;

public class Enchantments {
   private static final EnumItemSlot[] N = new EnumItemSlot[]{EnumItemSlot.f, EnumItemSlot.e, EnumItemSlot.d, EnumItemSlot.c};
   public static final Enchantment a = a("protection", new EnchantmentProtection(Enchantment.Rarity.a, EnchantmentProtection.DamageType.a, N));
   public static final Enchantment b = a("fire_protection", new EnchantmentProtection(Enchantment.Rarity.b, EnchantmentProtection.DamageType.b, N));
   public static final Enchantment c = a("feather_falling", new EnchantmentProtection(Enchantment.Rarity.b, EnchantmentProtection.DamageType.c, N));
   public static final Enchantment d = a("blast_protection", new EnchantmentProtection(Enchantment.Rarity.c, EnchantmentProtection.DamageType.d, N));
   public static final Enchantment e = a("projectile_protection", new EnchantmentProtection(Enchantment.Rarity.b, EnchantmentProtection.DamageType.e, N));
   public static final Enchantment f = a("respiration", new EnchantmentOxygen(Enchantment.Rarity.c, N));
   public static final Enchantment g = a("aqua_affinity", new EnchantmentWaterWorker(Enchantment.Rarity.c, N));
   public static final Enchantment h = a("thorns", new EnchantmentThorns(Enchantment.Rarity.d, N));
   public static final Enchantment i = a("depth_strider", new EnchantmentDepthStrider(Enchantment.Rarity.c, N));
   public static final Enchantment j = a("frost_walker", new EnchantmentFrostWalker(Enchantment.Rarity.c, EnumItemSlot.c));
   public static final Enchantment k = a("binding_curse", new EnchantmentBinding(Enchantment.Rarity.d, N));
   public static final Enchantment l = a("soul_speed", new EnchantmentSoulSpeed(Enchantment.Rarity.d, EnumItemSlot.c));
   public static final Enchantment m = a("swift_sneak", new SwiftSneakEnchantment(Enchantment.Rarity.d, EnumItemSlot.d));
   public static final Enchantment n = a("sharpness", new EnchantmentWeaponDamage(Enchantment.Rarity.a, 0, EnumItemSlot.a));
   public static final Enchantment o = a("smite", new EnchantmentWeaponDamage(Enchantment.Rarity.b, 1, EnumItemSlot.a));
   public static final Enchantment p = a("bane_of_arthropods", new EnchantmentWeaponDamage(Enchantment.Rarity.b, 2, EnumItemSlot.a));
   public static final Enchantment q = a("knockback", new EnchantmentKnockback(Enchantment.Rarity.b, EnumItemSlot.a));
   public static final Enchantment r = a("fire_aspect", new EnchantmentFire(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment s = a("looting", new EnchantmentLootBonus(Enchantment.Rarity.c, EnchantmentSlotType.f, EnumItemSlot.a));
   public static final Enchantment t = a("sweeping", new EnchantmentSweeping(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment u = a("efficiency", new EnchantmentDigging(Enchantment.Rarity.a, EnumItemSlot.a));
   public static final Enchantment v = a("silk_touch", new EnchantmentSilkTouch(Enchantment.Rarity.d, EnumItemSlot.a));
   public static final Enchantment w = a("unbreaking", new EnchantmentDurability(Enchantment.Rarity.b, EnumItemSlot.a));
   public static final Enchantment x = a("fortune", new EnchantmentLootBonus(Enchantment.Rarity.c, EnchantmentSlotType.g, EnumItemSlot.a));
   public static final Enchantment y = a("power", new EnchantmentArrowDamage(Enchantment.Rarity.a, EnumItemSlot.a));
   public static final Enchantment z = a("punch", new EnchantmentArrowKnockback(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment A = a("flame", new EnchantmentFlameArrows(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment B = a("infinity", new EnchantmentInfiniteArrows(Enchantment.Rarity.d, EnumItemSlot.a));
   public static final Enchantment C = a("luck_of_the_sea", new EnchantmentLootBonus(Enchantment.Rarity.c, EnchantmentSlotType.h, EnumItemSlot.a));
   public static final Enchantment D = a("lure", new EnchantmentLure(Enchantment.Rarity.c, EnchantmentSlotType.h, EnumItemSlot.a));
   public static final Enchantment E = a("loyalty", new EnchantmentTridentLoyalty(Enchantment.Rarity.b, EnumItemSlot.a));
   public static final Enchantment F = a("impaling", new EnchantmentTridentImpaling(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment G = a("riptide", new EnchantmentTridentRiptide(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment H = a("channeling", new EnchantmentTridentChanneling(Enchantment.Rarity.d, EnumItemSlot.a));
   public static final Enchantment I = a("multishot", new EnchantmentMultishot(Enchantment.Rarity.c, EnumItemSlot.a));
   public static final Enchantment J = a("quick_charge", new EnchantmentQuickCharge(Enchantment.Rarity.b, EnumItemSlot.a));
   public static final Enchantment K = a("piercing", new EnchantmentPiercing(Enchantment.Rarity.a, EnumItemSlot.a));
   public static final Enchantment L = a("mending", new EnchantmentMending(Enchantment.Rarity.c, EnumItemSlot.values()));
   public static final Enchantment M = a("vanishing_curse", new EnchantmentVanishing(Enchantment.Rarity.d, EnumItemSlot.values()));

   private static Enchantment a(String s, Enchantment enchantment) {
      enchantment = IRegistry.a(BuiltInRegistries.g, s, enchantment);
      org.bukkit.enchantments.Enchantment.registerEnchantment(new CraftEnchantment(enchantment));
      return enchantment;
   }
}
