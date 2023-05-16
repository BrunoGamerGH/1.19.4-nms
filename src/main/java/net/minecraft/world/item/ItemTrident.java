package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRiptideEvent;

public class ItemTrident extends Item implements ItemVanishable {
   public static final int a = 10;
   public static final float b = 8.0F;
   public static final float c = 2.5F;
   private final Multimap<AttributeBase, AttributeModifier> d;

   public ItemTrident(Item.Info item_info) {
      super(item_info);
      Builder<AttributeBase, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(GenericAttributes.f, new AttributeModifier(m, "Tool modifier", 8.0, AttributeModifier.Operation.a));
      builder.put(GenericAttributes.h, new AttributeModifier(n, "Tool modifier", -2.9F, AttributeModifier.Operation.a));
      this.d = builder.build();
   }

   @Override
   public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
      return !entityhuman.f();
   }

   @Override
   public EnumAnimation c(ItemStack itemstack) {
      return EnumAnimation.f;
   }

   @Override
   public int b(ItemStack itemstack) {
      return 72000;
   }

   @Override
   public void a(ItemStack itemstack, World world, EntityLiving entityliving, int i) {
      if (entityliving instanceof EntityHuman entityhuman) {
         int j = this.b(itemstack) - i;
         if (j >= 10) {
            int k = EnchantmentManager.h(itemstack);
            if (k <= 0 || entityhuman.aU()) {
               if (!world.B) {
                  if (k == 0) {
                     EntityThrownTrident entitythrowntrident = new EntityThrownTrident(world, entityhuman, itemstack);
                     entitythrowntrident.a(entityhuman, entityhuman.dy(), entityhuman.dw(), 0.0F, 2.5F + (float)k * 0.5F, 1.0F);
                     if (entityhuman.fK().d) {
                        entitythrowntrident.d = EntityArrow.PickupStatus.c;
                     }

                     if (!world.b(entitythrowntrident)) {
                        if (entityhuman instanceof EntityPlayer) {
                           ((EntityPlayer)entityhuman).getBukkitEntity().updateInventory();
                        }

                        return;
                     }

                     itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(entityliving.ff()));
                     entitythrowntrident.i = itemstack.o();
                     world.a(null, entitythrowntrident, SoundEffects.xu, SoundCategory.h, 1.0F, 1.0F);
                     if (!entityhuman.fK().d) {
                        entityhuman.fJ().g(itemstack);
                     }
                  } else {
                     itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(entityliving.ff()));
                  }
               }

               entityhuman.b(StatisticList.c.b(this));
               if (k > 0) {
                  PlayerRiptideEvent event = new PlayerRiptideEvent((Player)entityhuman.getBukkitEntity(), CraftItemStack.asCraftMirror(itemstack));
                  event.getPlayer().getServer().getPluginManager().callEvent(event);
                  float f = entityhuman.dw();
                  float f1 = entityhuman.dy();
                  float f2 = -MathHelper.a(f * (float) (Math.PI / 180.0)) * MathHelper.b(f1 * (float) (Math.PI / 180.0));
                  float f3 = -MathHelper.a(f1 * (float) (Math.PI / 180.0));
                  float f4 = MathHelper.b(f * (float) (Math.PI / 180.0)) * MathHelper.b(f1 * (float) (Math.PI / 180.0));
                  float f5 = MathHelper.c(f2 * f2 + f3 * f3 + f4 * f4);
                  float f6 = 3.0F * ((1.0F + (float)k) / 4.0F);
                  f2 *= f6 / f5;
                  f3 *= f6 / f5;
                  f4 *= f6 / f5;
                  entityhuman.j((double)f2, (double)f3, (double)f4);
                  entityhuman.s(20);
                  if (entityhuman.ax()) {
                     float f7 = 1.1999999F;
                     entityhuman.a(EnumMoveType.a, new Vec3D(0.0, 1.1999999F, 0.0));
                  }

                  SoundEffect soundeffect;
                  if (k >= 3) {
                     soundeffect = SoundEffects.xt;
                  } else if (k == 2) {
                     soundeffect = SoundEffects.xs;
                  } else {
                     soundeffect = SoundEffects.xr;
                  }

                  world.a(null, entityhuman, soundeffect, SoundCategory.h, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.j() >= itemstack.k() - 1) {
         return InteractionResultWrapper.d(itemstack);
      } else if (EnchantmentManager.h(itemstack) > 0 && !entityhuman.aU()) {
         return InteractionResultWrapper.d(itemstack);
      } else {
         entityhuman.c(enumhand);
         return InteractionResultWrapper.b(itemstack);
      }
   }

   @Override
   public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
      itemstack.a(1, entityliving1, entityliving2 -> entityliving2.d(EnumItemSlot.a));
      return true;
   }

   @Override
   public boolean a(ItemStack itemstack, World world, IBlockData iblockdata, BlockPosition blockposition, EntityLiving entityliving) {
      if ((double)iblockdata.h(world, blockposition) != 0.0) {
         itemstack.a(2, entityliving, entityliving1 -> entityliving1.d(EnumItemSlot.a));
      }

      return true;
   }

   @Override
   public Multimap<AttributeBase, AttributeModifier> a(EnumItemSlot enumitemslot) {
      return enumitemslot == EnumItemSlot.a ? this.d : super.a(enumitemslot);
   }

   @Override
   public int c() {
      return 1;
   }
}
