package net.minecraft.world.item;

import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.decoration.EntityLeash;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ItemLeash extends Item {
   public ItemLeash(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (iblockdata.a(TagsBlock.R)) {
         EntityHuman entityhuman = itemactioncontext.o();
         if (!world.B && entityhuman != null) {
            bindPlayerMobs(entityhuman, world, blockposition, itemactioncontext.p());
         }

         return EnumInteractionResult.a(world.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   public static EnumInteractionResult bindPlayerMobs(EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand) {
      EntityLeash entityleash = null;
      boolean flag = false;
      double d0 = 7.0;
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();

      for(EntityInsentient entityinsentient : world.a(
         EntityInsentient.class, new AxisAlignedBB((double)i - 7.0, (double)j - 7.0, (double)k - 7.0, (double)i + 7.0, (double)j + 7.0, (double)k + 7.0)
      )) {
         if (entityinsentient.fJ() == entityhuman) {
            if (entityleash == null) {
               entityleash = EntityLeash.b(world, blockposition);
               EquipmentSlot hand = CraftEquipmentSlot.getHand(enumhand);
               HangingPlaceEvent event = new HangingPlaceEvent(
                  (Hanging)entityleash.getBukkitEntity(),
                  entityhuman != null ? (Player)entityhuman.getBukkitEntity() : null,
                  world.getWorld().getBlockAt(i, j, k),
                  BlockFace.SELF,
                  hand
               );
               world.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  entityleash.ai();
                  return EnumInteractionResult.d;
               }

               entityleash.w();
            }

            if (!CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, entityleash, entityhuman, enumhand).isCancelled()) {
               entityinsentient.b(entityleash, true);
               flag = true;
            }
         }
      }

      if (flag) {
         world.a(GameEvent.b, blockposition, GameEvent.a.a(entityhuman));
      }

      return flag ? EnumInteractionResult.a : EnumInteractionResult.d;
   }

   public static EnumInteractionResult a(EntityHuman entityhuman, World world, BlockPosition blockposition) {
      return bindPlayerMobs(entityhuman, world, blockposition, EnumHand.a);
   }
}
