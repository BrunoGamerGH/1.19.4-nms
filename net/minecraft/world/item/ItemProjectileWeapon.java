package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityLiving;

public abstract class ItemProjectileWeapon extends Item {
   public static final Predicate<ItemStack> c = var0 -> var0.a(TagsItem.as);
   public static final Predicate<ItemStack> d = c.or(var0 -> var0.a(Items.tw));

   public ItemProjectileWeapon(Item.Info var0) {
      super(var0);
   }

   public Predicate<ItemStack> e() {
      return this.b();
   }

   public abstract Predicate<ItemStack> b();

   public static ItemStack a(EntityLiving var0, Predicate<ItemStack> var1) {
      if (var1.test(var0.b(EnumHand.b))) {
         return var0.b(EnumHand.b);
      } else {
         return var1.test(var0.b(EnumHand.a)) ? var0.b(EnumHand.a) : ItemStack.b;
      }
   }

   @Override
   public int c() {
      return 1;
   }

   public abstract int d();
}
