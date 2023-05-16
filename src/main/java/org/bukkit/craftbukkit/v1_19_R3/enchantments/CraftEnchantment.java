package org.bukkit.craftbukkit.v1_19_R3.enchantments;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.EnchantmentBinding;
import net.minecraft.world.item.enchantment.EnchantmentVanishing;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class CraftEnchantment extends Enchantment {
   private final net.minecraft.world.item.enchantment.Enchantment target;

   public CraftEnchantment(net.minecraft.world.item.enchantment.Enchantment target) {
      super(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.g.b(target)));
      this.target = target;
   }

   public int getMaxLevel() {
      return this.target.a();
   }

   public int getStartLevel() {
      return this.target.e();
   }

   public EnchantmentTarget getItemTarget() {
      switch(this.target.e) {
         case a:
            return EnchantmentTarget.ARMOR;
         case b:
            return EnchantmentTarget.ARMOR_FEET;
         case c:
            return EnchantmentTarget.ARMOR_LEGS;
         case d:
            return EnchantmentTarget.ARMOR_TORSO;
         case e:
            return EnchantmentTarget.ARMOR_HEAD;
         case f:
            return EnchantmentTarget.WEAPON;
         case g:
            return EnchantmentTarget.TOOL;
         case h:
            return EnchantmentTarget.FISHING_ROD;
         case i:
            return EnchantmentTarget.TRIDENT;
         case j:
            return EnchantmentTarget.BREAKABLE;
         case k:
            return EnchantmentTarget.BOW;
         case l:
            return EnchantmentTarget.WEARABLE;
         case m:
            return EnchantmentTarget.CROSSBOW;
         case n:
            return EnchantmentTarget.VANISHABLE;
         default:
            return null;
      }
   }

   public boolean isTreasure() {
      return this.target.b();
   }

   public boolean isCursed() {
      return this.target instanceof EnchantmentBinding || this.target instanceof EnchantmentVanishing;
   }

   public boolean canEnchantItem(ItemStack item) {
      return this.target.a(CraftItemStack.asNMSCopy(item));
   }

   public String getName() {
      switch(BuiltInRegistries.g.a(this.target)) {
         case 0:
            return "PROTECTION_ENVIRONMENTAL";
         case 1:
            return "PROTECTION_FIRE";
         case 2:
            return "PROTECTION_FALL";
         case 3:
            return "PROTECTION_EXPLOSIONS";
         case 4:
            return "PROTECTION_PROJECTILE";
         case 5:
            return "OXYGEN";
         case 6:
            return "WATER_WORKER";
         case 7:
            return "THORNS";
         case 8:
            return "DEPTH_STRIDER";
         case 9:
            return "FROST_WALKER";
         case 10:
            return "BINDING_CURSE";
         case 11:
            return "SOUL_SPEED";
         case 12:
            return "SWIFT_SNEAK";
         case 13:
            return "DAMAGE_ALL";
         case 14:
            return "DAMAGE_UNDEAD";
         case 15:
            return "DAMAGE_ARTHROPODS";
         case 16:
            return "KNOCKBACK";
         case 17:
            return "FIRE_ASPECT";
         case 18:
            return "LOOT_BONUS_MOBS";
         case 19:
            return "SWEEPING_EDGE";
         case 20:
            return "DIG_SPEED";
         case 21:
            return "SILK_TOUCH";
         case 22:
            return "DURABILITY";
         case 23:
            return "LOOT_BONUS_BLOCKS";
         case 24:
            return "ARROW_DAMAGE";
         case 25:
            return "ARROW_KNOCKBACK";
         case 26:
            return "ARROW_FIRE";
         case 27:
            return "ARROW_INFINITE";
         case 28:
            return "LUCK";
         case 29:
            return "LURE";
         case 30:
            return "LOYALTY";
         case 31:
            return "IMPALING";
         case 32:
            return "RIPTIDE";
         case 33:
            return "CHANNELING";
         case 34:
            return "MULTISHOT";
         case 35:
            return "QUICK_CHARGE";
         case 36:
            return "PIERCING";
         case 37:
            return "MENDING";
         case 38:
            return "VANISHING_CURSE";
         default:
            return "UNKNOWN_ENCHANT_" + BuiltInRegistries.g.a(this.target);
      }
   }

   public static net.minecraft.world.item.enchantment.Enchantment getRaw(Enchantment enchantment) {
      if (enchantment instanceof EnchantmentWrapper) {
         enchantment = ((EnchantmentWrapper)enchantment).getEnchantment();
      }

      return enchantment instanceof CraftEnchantment ? ((CraftEnchantment)enchantment).target : null;
   }

   public boolean conflictsWith(Enchantment other) {
      if (other instanceof EnchantmentWrapper) {
         other = ((EnchantmentWrapper)other).getEnchantment();
      }

      if (!(other instanceof CraftEnchantment)) {
         return false;
      } else {
         CraftEnchantment ench = (CraftEnchantment)other;
         return !this.target.b(ench.target);
      }
   }

   public net.minecraft.world.item.enchantment.Enchantment getHandle() {
      return this.target;
   }
}
