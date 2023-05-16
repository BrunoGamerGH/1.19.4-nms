package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.ISourceBlock;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.IShearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BlockBeehive;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public class DispenseBehaviorShears extends DispenseBehaviorMaybe {
   @Override
   protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
      WorldServer worldserver = isourceblock.g();
      Block bukkitBlock = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
      CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
      BlockDispenseEvent event = new BlockDispenseEvent(bukkitBlock, craftItem.clone(), new Vector(0, 0, 0));
      if (!BlockDispenser.eventFired) {
         worldserver.getCraftServer().getPluginManager().callEvent(event);
      }

      if (event.isCancelled()) {
         return itemstack;
      } else {
         if (!event.getItem().equals(craftItem)) {
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
            if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
               idispensebehavior.dispense(isourceblock, eventStack);
               return itemstack;
            }
         }

         if (!worldserver.k_()) {
            BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
            this.a(a(worldserver, blockposition) || tryShearLivingEntity(worldserver, blockposition, bukkitBlock, craftItem));
            if (this.a() && itemstack.a(1, worldserver.r_(), null)) {
               itemstack.f(0);
            }
         }

         return itemstack;
      }
   }

   private static boolean a(WorldServer worldserver, BlockPosition blockposition) {
      IBlockData iblockdata = worldserver.a_(blockposition);
      if (iblockdata.a(TagsBlock.aD, blockbase_blockdata -> blockbase_blockdata.b(BlockBeehive.b) && blockbase_blockdata.b() instanceof BlockBeehive)) {
         int i = iblockdata.c(BlockBeehive.b);
         if (i >= 5) {
            worldserver.a(null, blockposition, SoundEffects.bH, SoundCategory.e, 1.0F, 1.0F);
            BlockBeehive.a(worldserver, blockposition);
            ((BlockBeehive)iblockdata.b()).a(worldserver, iblockdata, blockposition, null, TileEntityBeehive.ReleaseStatus.b);
            worldserver.a(null, GameEvent.Q, blockposition);
            return true;
         }
      }

      return false;
   }

   private static boolean tryShearLivingEntity(WorldServer worldserver, BlockPosition blockposition, Block bukkitBlock, CraftItemStack craftItem) {
      for(EntityLiving entityliving : worldserver.a(EntityLiving.class, new AxisAlignedBB(blockposition), IEntitySelector.f)) {
         if (entityliving instanceof IShearable ishearable
            && ishearable.a()
            && !CraftEventFactory.callBlockShearEntityEvent(entityliving, bukkitBlock, craftItem).isCancelled()) {
            ishearable.a(SoundCategory.e);
            worldserver.a(null, GameEvent.Q, blockposition);
            return true;
         }
      }

      return false;
   }
}
