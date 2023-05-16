package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ItemBow extends ItemProjectileWeapon implements ItemVanishable {
   public static final int a = 20;
   public static final int b = 15;

   public ItemBow(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public void a(ItemStack itemstack, World world, EntityLiving entityliving, int i) {
      if (entityliving instanceof EntityHuman entityhuman) {
         boolean flag = entityhuman.fK().d || EnchantmentManager.a(Enchantments.B, itemstack) > 0;
         ItemStack itemstack1 = entityhuman.g(itemstack);
         if (!itemstack1.b() || flag) {
            if (itemstack1.b()) {
               itemstack1 = new ItemStack(Items.nD);
            }

            int j = this.b(itemstack) - i;
            float f = a(j);
            if ((double)f >= 0.1) {
               boolean flag1 = flag && itemstack1.a(Items.nD);
               if (!world.B) {
                  ItemArrow itemarrow = (ItemArrow)(itemstack1.c() instanceof ItemArrow ? itemstack1.c() : Items.nD);
                  EntityArrow entityarrow = itemarrow.a(world, itemstack1, entityhuman);
                  entityarrow.a(entityhuman, entityhuman.dy(), entityhuman.dw(), 0.0F, f * 3.0F, 1.0F);
                  if (f == 1.0F) {
                     entityarrow.a(true);
                  }

                  int k = EnchantmentManager.a(Enchantments.y, itemstack);
                  if (k > 0) {
                     entityarrow.h(entityarrow.p() + (double)k * 0.5 + 0.5);
                  }

                  int l = EnchantmentManager.a(Enchantments.z, itemstack);
                  if (l > 0) {
                     entityarrow.b(l);
                  }

                  if (EnchantmentManager.a(Enchantments.A, itemstack) > 0) {
                     entityarrow.f(100);
                  }

                  EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(
                     entityhuman, itemstack, itemstack1, entityarrow, entityhuman.ff(), f, !flag1
                  );
                  if (event.isCancelled()) {
                     event.getProjectile().remove();
                     return;
                  }

                  flag1 = !event.shouldConsumeItem();
                  itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(entityhuman.ff()));
                  if (flag1 || entityhuman.fK().d && (itemstack1.a(Items.uq) || itemstack1.a(Items.ur))) {
                     entityarrow.d = EntityArrow.PickupStatus.c;
                  }

                  if (event.getProjectile() == entityarrow.getBukkitEntity() && !world.b(entityarrow)) {
                     if (entityhuman instanceof EntityPlayer) {
                        ((EntityPlayer)entityhuman).getBukkitEntity().updateInventory();
                     }

                     return;
                  }
               }

               world.a(
                  null,
                  entityhuman.dl(),
                  entityhuman.dn(),
                  entityhuman.dr(),
                  SoundEffects.aq,
                  SoundCategory.h,
                  1.0F,
                  1.0F / (world.r_().i() * 0.4F + 1.2F) + f * 0.5F
               );
               if (!flag1 && !entityhuman.fK().d) {
                  itemstack1.h(1);
                  if (itemstack1.b()) {
                     entityhuman.fJ().g(itemstack1);
                  }
               }

               entityhuman.b(StatisticList.c.b(this));
            }
         }
      }
   }

   public static float a(int i) {
      float f = (float)i / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   @Override
   public int b(ItemStack itemstack) {
      return 72000;
   }

   @Override
   public EnumAnimation c(ItemStack itemstack) {
      return EnumAnimation.e;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      boolean flag = !entityhuman.g(itemstack).b();
      if (!entityhuman.fK().d && !flag) {
         return InteractionResultWrapper.d(itemstack);
      } else {
         entityhuman.c(enumhand);
         return InteractionResultWrapper.b(itemstack);
      }
   }

   @Override
   public Predicate<ItemStack> b() {
      return c;
   }

   @Override
   public int d() {
      return 15;
   }
}
