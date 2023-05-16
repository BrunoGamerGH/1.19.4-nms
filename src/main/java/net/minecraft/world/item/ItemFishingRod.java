package net.minecraft.world.item;

import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

public class ItemFishingRod extends Item implements ItemVanishable {
   public ItemFishingRod(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (entityhuman.ch != null) {
         if (!world.B) {
            int i = entityhuman.ch.a(itemstack);
            itemstack.a(i, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
         }

         world.a(null, entityhuman.dl(), entityhuman.dn(), entityhuman.dr(), SoundEffects.hL, SoundCategory.g, 1.0F, 0.4F / (world.r_().i() * 0.4F + 0.8F));
         entityhuman.a(GameEvent.E);
      } else {
         if (!world.B) {
            int i = EnchantmentManager.c(itemstack);
            int j = EnchantmentManager.b(itemstack);
            EntityFishingHook entityfishinghook = new EntityFishingHook(entityhuman, world, j, i);
            PlayerFishEvent playerFishEvent = new PlayerFishEvent(
               (Player)entityhuman.getBukkitEntity(), null, (FishHook)entityfishinghook.getBukkitEntity(), CraftEquipmentSlot.getHand(enumhand), State.FISHING
            );
            world.getCraftServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
               entityhuman.ch = null;
               return InteractionResultWrapper.c(itemstack);
            }

            world.a(null, entityhuman.dl(), entityhuman.dn(), entityhuman.dr(), SoundEffects.hN, SoundCategory.g, 0.5F, 0.4F / (world.r_().i() * 0.4F + 0.8F));
            world.b(entityfishinghook);
         }

         entityhuman.b(StatisticList.c.b(this));
         entityhuman.a(GameEvent.F);
      }

      return InteractionResultWrapper.a(itemstack, world.k_());
   }

   @Override
   public int c() {
      return 1;
   }
}
