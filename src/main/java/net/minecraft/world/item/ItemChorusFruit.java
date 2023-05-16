package net.minecraft.world.item;

import java.util.Optional;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class ItemChorusFruit extends Item {
   public ItemChorusFruit(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
      ItemStack itemstack1 = super.a(itemstack, world, entityliving);
      if (!world.B) {
         double d0 = entityliving.dl();
         double d1 = entityliving.dn();
         double d2 = entityliving.dr();

         for(int i = 0; i < 16; ++i) {
            double d3 = entityliving.dl() + (entityliving.dZ().j() - 0.5) * 16.0;
            double d4 = MathHelper.a(
               entityliving.dn() + (double)(entityliving.dZ().a(16) - 8), (double)world.v_(), (double)(world.v_() + ((WorldServer)world).j() - 1)
            );
            double d5 = entityliving.dr() + (entityliving.dZ().j() - 0.5) * 16.0;
            if (entityliving.bL()) {
               entityliving.bz();
            }

            Vec3D vec3d = entityliving.de();
            Optional<Boolean> status = entityliving.randomTeleport(d3, d4, d5, true, TeleportCause.CHORUS_FRUIT);
            if (!status.isPresent()) {
               break;
            }

            if (status.get()) {
               world.a(GameEvent.V, vec3d, GameEvent.a.a(entityliving));
               SoundEffect soundeffect = entityliving instanceof EntityFox ? SoundEffects.ie : SoundEffects.ew;
               world.a(null, d0, d1, d2, soundeffect, SoundCategory.h, 1.0F, 1.0F);
               entityliving.a(soundeffect, 1.0F, 1.0F);
               break;
            }
         }

         if (entityliving instanceof EntityHuman) {
            ((EntityHuman)entityliving).ge().a(this, 20);
         }
      }

      return itemstack1;
   }
}
