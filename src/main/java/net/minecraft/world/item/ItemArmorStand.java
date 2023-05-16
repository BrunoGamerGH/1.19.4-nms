package net.minecraft.world.item;

import java.util.function.Consumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class ItemArmorStand extends Item {
   public ItemArmorStand(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      EnumDirection enumdirection = itemactioncontext.k();
      if (enumdirection == EnumDirection.a) {
         return EnumInteractionResult.e;
      } else {
         World world = itemactioncontext.q();
         BlockActionContext blockactioncontext = new BlockActionContext(itemactioncontext);
         BlockPosition blockposition = blockactioncontext.a();
         ItemStack itemstack = itemactioncontext.n();
         Vec3D vec3d = Vec3D.c(blockposition);
         AxisAlignedBB axisalignedbb = EntityTypes.d.n().a(vec3d.a(), vec3d.b(), vec3d.c());
         if (world.a(null, axisalignedbb) && world.a_(null, axisalignedbb).isEmpty()) {
            if (world instanceof WorldServer worldserver) {
               Consumer<EntityArmorStand> consumer = EntityTypes.a(worldserver, itemstack, itemactioncontext.o());
               EntityArmorStand entityarmorstand = EntityTypes.d.b(worldserver, itemstack.u(), consumer, blockposition, EnumMobSpawn.m, true, true);
               if (entityarmorstand == null) {
                  return EnumInteractionResult.e;
               }

               float f = (float)MathHelper.d((MathHelper.g(itemactioncontext.i() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
               entityarmorstand.b(entityarmorstand.dl(), entityarmorstand.dn(), entityarmorstand.dr(), f, 0.0F);
               if (CraftEventFactory.callEntityPlaceEvent(itemactioncontext, entityarmorstand).isCancelled()) {
                  return EnumInteractionResult.e;
               }

               worldserver.a_(entityarmorstand);
               world.a(null, entityarmorstand.dl(), entityarmorstand.dn(), entityarmorstand.dr(), SoundEffects.an, SoundCategory.e, 0.75F, 0.8F);
               entityarmorstand.a(GameEvent.u, itemactioncontext.o());
            }

            itemstack.h(1);
            return EnumInteractionResult.a(world.B);
         } else {
            return EnumInteractionResult.e;
         }
      }
   }
}
