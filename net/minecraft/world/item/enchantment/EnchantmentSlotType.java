package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemBow;
import net.minecraft.world.item.ItemCrossbow;
import net.minecraft.world.item.ItemFishingRod;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.ItemTool;
import net.minecraft.world.item.ItemTrident;
import net.minecraft.world.item.ItemVanishable;
import net.minecraft.world.level.block.Block;

public enum EnchantmentSlotType {
   a {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemArmor;
      }
   },
   b {
      @Override
      public boolean a(Item var0) {
         if (var0 instanceof ItemArmor var1 && var1.g() == EnumItemSlot.c) {
            return true;
         }

         return false;
      }
   },
   c {
      @Override
      public boolean a(Item var0) {
         if (var0 instanceof ItemArmor var1 && var1.g() == EnumItemSlot.d) {
            return true;
         }

         return false;
      }
   },
   d {
      @Override
      public boolean a(Item var0) {
         if (var0 instanceof ItemArmor var1 && var1.g() == EnumItemSlot.e) {
            return true;
         }

         return false;
      }
   },
   e {
      @Override
      public boolean a(Item var0) {
         if (var0 instanceof ItemArmor var1 && var1.g() == EnumItemSlot.f) {
            return true;
         }

         return false;
      }
   },
   f {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemSword;
      }
   },
   g {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemTool;
      }
   },
   h {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemFishingRod;
      }
   },
   i {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemTrident;
      }
   },
   j {
      @Override
      public boolean a(Item var0) {
         return var0.o();
      }
   },
   k {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemBow;
      }
   },
   l {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof Equipable || Block.a(var0) instanceof Equipable;
      }
   },
   m {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemCrossbow;
      }
   },
   n {
      @Override
      public boolean a(Item var0) {
         return var0 instanceof ItemVanishable || Block.a(var0) instanceof ItemVanishable || j.a(var0);
      }
   };

   public abstract boolean a(Item var1);
}
