package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IFluidContainer;
import net.minecraft.world.level.block.IFluidSource;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypeFlowing;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.DummyGeneratorAccess;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class ItemBucket extends Item implements DispensibleContainerItem {
   public final FluidType a;

   public ItemBucket(FluidType fluidtype, Item.Info item_info) {
      super(item_info);
      this.a = fluidtype;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      MovingObjectPositionBlock movingobjectpositionblock = a(
         world, entityhuman, this.a == FluidTypes.a ? RayTrace.FluidCollisionOption.b : RayTrace.FluidCollisionOption.a
      );
      if (movingobjectpositionblock.c() == MovingObjectPosition.EnumMovingObjectType.a) {
         return InteractionResultWrapper.c(itemstack);
      } else if (movingobjectpositionblock.c() != MovingObjectPosition.EnumMovingObjectType.b) {
         return InteractionResultWrapper.c(itemstack);
      } else {
         BlockPosition blockposition = movingobjectpositionblock.a();
         EnumDirection enumdirection = movingobjectpositionblock.b();
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (!world.a(entityhuman, blockposition) || !entityhuman.a(blockposition1, enumdirection, itemstack)) {
            return InteractionResultWrapper.d(itemstack);
         } else if (this.a == FluidTypes.a) {
            IBlockData iblockdata = world.a_(blockposition);
            if (iblockdata.b() instanceof IFluidSource ifluidsource) {
               ItemStack dummyFluid = ifluidsource.c(DummyGeneratorAccess.INSTANCE, blockposition, iblockdata);
               if (dummyFluid.b()) {
                  return InteractionResultWrapper.d(itemstack);
               }

               PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(
                  (WorldServer)world, entityhuman, blockposition, blockposition, movingobjectpositionblock.b(), itemstack, dummyFluid.c(), enumhand
               );
               if (event.isCancelled()) {
                  ((EntityPlayer)entityhuman).b.a(new PacketPlayOutBlockChange(world, blockposition));
                  ((EntityPlayer)entityhuman).getBukkitEntity().updateInventory();
                  return InteractionResultWrapper.d(itemstack);
               }

               ItemStack itemstack1 = ifluidsource.c(world, blockposition, iblockdata);
               if (!itemstack1.b()) {
                  entityhuman.b(StatisticList.c.b(this));
                  ifluidsource.an_().ifPresent(soundeffect -> entityhuman.a(soundeffect, 1.0F, 1.0F));
                  world.a(entityhuman, GameEvent.A, blockposition);
                  ItemStack itemstack2 = ItemLiquidUtil.a(itemstack, entityhuman, CraftItemStack.asNMSCopy(event.getItemStack()));
                  if (!world.B) {
                     CriterionTriggers.j.a((EntityPlayer)entityhuman, itemstack1);
                  }

                  return InteractionResultWrapper.a(itemstack2, world.k_());
               }
            }

            return InteractionResultWrapper.d(itemstack);
         } else {
            IBlockData iblockdata = world.a_(blockposition);
            BlockPosition blockposition2 = iblockdata.b() instanceof IFluidContainer && this.a == FluidTypes.c ? blockposition : blockposition1;
            if (this.emptyContents(
               entityhuman, world, blockposition2, movingobjectpositionblock, movingobjectpositionblock.b(), blockposition, itemstack, enumhand
            )) {
               this.a(entityhuman, world, itemstack, blockposition2);
               if (entityhuman instanceof EntityPlayer) {
                  CriterionTriggers.y.a((EntityPlayer)entityhuman, blockposition2, itemstack);
               }

               entityhuman.b(StatisticList.c.b(this));
               return InteractionResultWrapper.a(a(itemstack, entityhuman), world.k_());
            } else {
               return InteractionResultWrapper.d(itemstack);
            }
         }
      }
   }

   public static ItemStack a(ItemStack itemstack, EntityHuman entityhuman) {
      return !entityhuman.fK().d ? new ItemStack(Items.pG) : itemstack;
   }

   @Override
   public void a(@Nullable EntityHuman entityhuman, World world, ItemStack itemstack, BlockPosition blockposition) {
   }

   @Override
   public boolean a(@Nullable EntityHuman entityhuman, World world, BlockPosition blockposition, @Nullable MovingObjectPositionBlock movingobjectpositionblock) {
      return this.emptyContents(entityhuman, world, blockposition, movingobjectpositionblock, null, null, null, EnumHand.a);
   }

   public boolean emptyContents(
      EntityHuman entityhuman,
      World world,
      BlockPosition blockposition,
      @Nullable MovingObjectPositionBlock movingobjectpositionblock,
      EnumDirection enumdirection,
      BlockPosition clicked,
      ItemStack itemstack,
      EnumHand enumhand
   ) {
      if (!(this.a instanceof FluidTypeFlowing)) {
         return false;
      } else {
         IBlockData iblockdata = world.a_(blockposition);
         Block block = iblockdata.b();
         Material material = iblockdata.d();
         boolean flag = iblockdata.a(this.a);
         boolean flag1 = iblockdata.h() || flag || block instanceof IFluidContainer && ((IFluidContainer)block).a(world, blockposition, iblockdata, this.a);
         if (flag1 && entityhuman != null) {
            PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(
               (WorldServer)world, entityhuman, blockposition, clicked, enumdirection, itemstack, enumhand
            );
            if (event.isCancelled()) {
               ((EntityPlayer)entityhuman).b.a(new PacketPlayOutBlockChange(world, blockposition));
               ((EntityPlayer)entityhuman).getBukkitEntity().updateInventory();
               return false;
            }
         }

         if (!flag1) {
            return movingobjectpositionblock != null
               && this.emptyContents(
                  entityhuman, world, movingobjectpositionblock.a().a(movingobjectpositionblock.b()), null, enumdirection, clicked, itemstack, enumhand
               );
         } else if (world.q_().i() && this.a.a(TagsFluid.a)) {
            int i = blockposition.u();
            int j = blockposition.v();
            int k = blockposition.w();
            world.a(entityhuman, blockposition, SoundEffects.hJ, SoundCategory.e, 0.5F, 2.6F + (world.z.i() - world.z.i()) * 0.8F);

            for(int l = 0; l < 8; ++l) {
               world.a(Particles.U, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
            }

            return true;
         } else if (block instanceof IFluidContainer && this.a == FluidTypes.c) {
            ((IFluidContainer)block).a(world, blockposition, iblockdata, ((FluidTypeFlowing)this.a).a(false));
            this.a(entityhuman, world, blockposition);
            return true;
         } else {
            if (!world.B && flag && !material.a()) {
               world.b(blockposition, true);
            }

            if (!world.a(blockposition, this.a.g().g(), 11) && !iblockdata.r().b()) {
               return false;
            } else {
               this.a(entityhuman, world, blockposition);
               return true;
            }
         }
      }
   }

   protected void a(@Nullable EntityHuman entityhuman, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      SoundEffect soundeffect = this.a.a(TagsFluid.b) ? SoundEffects.cu : SoundEffects.cr;
      generatoraccess.a(entityhuman, blockposition, soundeffect, SoundCategory.e, 1.0F, 1.0F);
      generatoraccess.a(entityhuman, GameEvent.B, blockposition);
   }
}
