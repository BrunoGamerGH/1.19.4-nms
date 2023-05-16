package net.minecraft.world.item;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.ISourceBlock;
import net.minecraft.core.dispenser.DispenseBehaviorItem;
import net.minecraft.core.dispenser.IDispenseBehavior;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.BlockMinecartTrackAbstract;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public class ItemMinecart extends Item {
   private static final IDispenseBehavior a = new DispenseBehaviorItem() {
      private final DispenseBehaviorItem c = new DispenseBehaviorItem();

      @Override
      public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
         EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
         WorldServer worldserver = isourceblock.g();
         double d0 = isourceblock.a() + (double)enumdirection.j() * 1.125;
         double d1 = Math.floor(isourceblock.b()) + (double)enumdirection.k();
         double d2 = isourceblock.c() + (double)enumdirection.l() * 1.125;
         BlockPosition blockposition = isourceblock.d().a(enumdirection);
         IBlockData iblockdata = worldserver.a_(blockposition);
         BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.b() instanceof BlockMinecartTrackAbstract
            ? iblockdata.c(((BlockMinecartTrackAbstract)iblockdata.b()).c())
            : BlockPropertyTrackPosition.a;
         double d3;
         if (iblockdata.a(TagsBlock.M)) {
            if (blockpropertytrackposition.b()) {
               d3 = 0.6;
            } else {
               d3 = 0.1;
            }
         } else {
            if (!iblockdata.h() || !worldserver.a_(blockposition.d()).a(TagsBlock.M)) {
               return this.c.dispense(isourceblock, itemstack);
            }

            IBlockData iblockdata1 = worldserver.a_(blockposition.d());
            BlockPropertyTrackPosition blockpropertytrackposition1 = iblockdata1.b() instanceof BlockMinecartTrackAbstract
               ? iblockdata1.c(((BlockMinecartTrackAbstract)iblockdata1.b()).c())
               : BlockPropertyTrackPosition.a;
            if (enumdirection != EnumDirection.a && blockpropertytrackposition1.b()) {
               d3 = -0.4;
            } else {
               d3 = -0.9;
            }
         }

         ItemStack itemstack1 = itemstack.a(1);
         Block block2 = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
         BlockDispenseEvent event = new BlockDispenseEvent(block2, craftItem.clone(), new Vector(d0, d1 + d3, d2));
         if (!BlockDispenser.eventFired) {
            worldserver.getCraftServer().getPluginManager().callEvent(event);
         }

         if (event.isCancelled()) {
            itemstack.g(1);
            return itemstack;
         } else {
            if (!event.getItem().equals(craftItem)) {
               itemstack.g(1);
               ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
               IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
               if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                  idispensebehavior.dispense(isourceblock, eventStack);
                  return itemstack;
               }
            }

            itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
            EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(
               worldserver, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), ((ItemMinecart)itemstack1.c()).b
            );
            if (itemstack.z()) {
               entityminecartabstract.b(itemstack.x());
            }

            if (!worldserver.b(entityminecartabstract)) {
               itemstack.g(1);
            }

            return itemstack;
         }
      }

      @Override
      protected void a(ISourceBlock isourceblock) {
         isourceblock.g().c(1000, isourceblock.d(), 0);
      }
   };
   final EntityMinecartAbstract.EnumMinecartType b;

   public ItemMinecart(EntityMinecartAbstract.EnumMinecartType entityminecartabstract_enumminecarttype, Item.Info item_info) {
      super(item_info);
      this.b = entityminecartabstract_enumminecarttype;
      BlockDispenser.a(this, a);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (!iblockdata.a(TagsBlock.M)) {
         return EnumInteractionResult.e;
      } else {
         ItemStack itemstack = itemactioncontext.n();
         if (!world.B) {
            BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.b() instanceof BlockMinecartTrackAbstract
               ? iblockdata.c(((BlockMinecartTrackAbstract)iblockdata.b()).c())
               : BlockPropertyTrackPosition.a;
            double d0 = 0.0;
            if (blockpropertytrackposition.b()) {
               d0 = 0.5;
            }

            EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(
               world, (double)blockposition.u() + 0.5, (double)blockposition.v() + 0.0625 + d0, (double)blockposition.w() + 0.5, this.b
            );
            if (itemstack.z()) {
               entityminecartabstract.b(itemstack.x());
            }

            if (CraftEventFactory.callEntityPlaceEvent(itemactioncontext, entityminecartabstract).isCancelled()) {
               return EnumInteractionResult.e;
            }

            if (!world.b(entityminecartabstract)) {
               return EnumInteractionResult.d;
            }

            world.a(GameEvent.u, blockposition, GameEvent.a.a(itemactioncontext.o(), world.a_(blockposition.d())));
         }

         itemstack.h(1);
         return EnumInteractionResult.a(world.B);
      }
   }
}
