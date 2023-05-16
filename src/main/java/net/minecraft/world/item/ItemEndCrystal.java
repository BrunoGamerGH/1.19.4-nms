package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.end.EnderDragonBattle;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class ItemEndCrystal extends Item {
   public ItemEndCrystal(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (!iblockdata.a(Blocks.cn) && !iblockdata.a(Blocks.F)) {
         return EnumInteractionResult.e;
      } else {
         BlockPosition blockposition1 = blockposition.c();
         if (!world.w(blockposition1)) {
            return EnumInteractionResult.e;
         } else {
            double d0 = (double)blockposition1.u();
            double d1 = (double)blockposition1.v();
            double d2 = (double)blockposition1.w();
            List<Entity> list = world.a_(null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0, d1 + 2.0, d2 + 1.0));
            if (!list.isEmpty()) {
               return EnumInteractionResult.e;
            } else {
               if (world instanceof WorldServer) {
                  EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(world, d0 + 0.5, d1, d2 + 0.5);
                  entityendercrystal.a(false);
                  if (CraftEventFactory.callEntityPlaceEvent(itemactioncontext, entityendercrystal).isCancelled()) {
                     return EnumInteractionResult.e;
                  }

                  world.b(entityendercrystal);
                  world.a(itemactioncontext.o(), GameEvent.u, blockposition1);
                  EnderDragonBattle enderdragonbattle = ((WorldServer)world).B();
                  if (enderdragonbattle != null) {
                     enderdragonbattle.e();
                  }
               }

               itemactioncontext.n().h(1);
               return EnumInteractionResult.a(world.B);
            }
         }
      }
   }

   @Override
   public boolean i(ItemStack itemstack) {
      return true;
   }
}
