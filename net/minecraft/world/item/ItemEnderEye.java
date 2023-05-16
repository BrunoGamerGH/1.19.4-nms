package net.minecraft.world.item;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityEnderSignal;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockEnderPortalFrame;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetector;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class ItemEnderEye extends Item {
   public ItemEnderEye(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (!iblockdata.a(Blocks.fx) || iblockdata.c(BlockEnderPortalFrame.b)) {
         return EnumInteractionResult.d;
      } else if (world.B) {
         return EnumInteractionResult.a;
      } else {
         IBlockData iblockdata1 = iblockdata.a(BlockEnderPortalFrame.b, Boolean.valueOf(true));
         Block.a(iblockdata, iblockdata1, world, blockposition);
         world.a(blockposition, iblockdata1, 2);
         world.c(blockposition, Blocks.fx);
         itemactioncontext.n().h(1);
         world.c(1503, blockposition, 0);
         ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = BlockEnderPortalFrame.b().a(world, blockposition);
         if (shapedetector_shapedetectorcollection != null) {
            BlockPosition blockposition1 = shapedetector_shapedetectorcollection.a().b(-3, 0, -3);

            for(int i = 0; i < 3; ++i) {
               for(int j = 0; j < 3; ++j) {
                  world.a(blockposition1.b(i, 0, j), Blocks.fw.o(), 2);
               }
            }

            int viewDistance = world.getCraftServer().getViewDistance() * 16;
            BlockPosition soundPos = blockposition1.b(1, 0, 1);

            for(EntityPlayer player : world.n().ac().k) {
               double deltaX = (double)soundPos.u() - player.dl();
               double deltaZ = (double)soundPos.w() - player.dr();
               double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
               if (world.spigotConfig.endPortalSoundRadius <= 0
                  || !(distanceSquared > (double)(world.spigotConfig.endPortalSoundRadius * world.spigotConfig.endPortalSoundRadius))) {
                  if (distanceSquared > (double)(viewDistance * viewDistance)) {
                     double deltaLength = Math.sqrt(distanceSquared);
                     double relativeX = player.dl() + deltaX / deltaLength * (double)viewDistance;
                     double relativeZ = player.dr() + deltaZ / deltaLength * (double)viewDistance;
                     player.b.a(new PacketPlayOutWorldEvent(1038, new BlockPosition((int)relativeX, soundPos.v(), (int)relativeZ), 0, true));
                  } else {
                     player.b.a(new PacketPlayOutWorldEvent(1038, soundPos, 0, true));
                  }
               }
            }
         }

         return EnumInteractionResult.b;
      }
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      MovingObjectPositionBlock movingobjectpositionblock = a(world, entityhuman, RayTrace.FluidCollisionOption.a);
      if (movingobjectpositionblock.c() == MovingObjectPosition.EnumMovingObjectType.b && world.a_(movingobjectpositionblock.a()).a(Blocks.fx)) {
         return InteractionResultWrapper.c(itemstack);
      } else {
         entityhuman.c(enumhand);
         if (world instanceof WorldServer worldserver) {
            BlockPosition blockposition = worldserver.a(StructureTags.a, entityhuman.dg(), 100, false);
            if (blockposition != null) {
               EntityEnderSignal entityendersignal = new EntityEnderSignal(world, entityhuman.dl(), entityhuman.e(0.5), entityhuman.dr());
               entityendersignal.a(itemstack);
               entityendersignal.a(blockposition);
               world.a(GameEvent.O, entityendersignal.de(), GameEvent.a.a(entityhuman));
               if (!world.b(entityendersignal)) {
                  return new InteractionResultWrapper<>(EnumInteractionResult.e, itemstack);
               }

               if (entityhuman instanceof EntityPlayer) {
                  CriterionTriggers.m.a((EntityPlayer)entityhuman, blockposition);
               }

               world.a(
                  null, entityhuman.dl(), entityhuman.dn(), entityhuman.dr(), SoundEffects.gX, SoundCategory.g, 0.5F, 0.4F / (world.r_().i() * 0.4F + 0.8F)
               );
               world.a(null, 1003, entityhuman.dg(), 0);
               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }

               entityhuman.b(StatisticList.c.b(this));
               entityhuman.a(enumhand, true);
               return InteractionResultWrapper.a(itemstack);
            }
         }

         return InteractionResultWrapper.b(itemstack);
      }
   }
}
