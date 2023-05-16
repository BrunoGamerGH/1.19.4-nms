package net.minecraft.world.item;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityHanging;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ItemHanging extends Item {
   private static final IChatBaseComponent a = IChatBaseComponent.c("painting.random").a(EnumChatFormat.h);
   private final EntityTypes<? extends EntityHanging> b;

   public ItemHanging(EntityTypes<? extends EntityHanging> entitytypes, Item.Info item_info) {
      super(item_info);
      this.b = entitytypes;
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      BlockPosition blockposition = itemactioncontext.a();
      EnumDirection enumdirection = itemactioncontext.k();
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      EntityHuman entityhuman = itemactioncontext.o();
      ItemStack itemstack = itemactioncontext.n();
      if (entityhuman != null && !this.a(entityhuman, enumdirection, itemstack, blockposition1)) {
         return EnumInteractionResult.e;
      } else {
         World world = itemactioncontext.q();
         Object object;
         if (this.b == EntityTypes.ar) {
            Optional<EntityPainting> optional = EntityPainting.a(world, blockposition1, enumdirection);
            if (optional.isEmpty()) {
               return EnumInteractionResult.b;
            }

            object = optional.get();
         } else if (this.b == EntityTypes.af) {
            object = new EntityItemFrame(world, blockposition1, enumdirection);
         } else {
            if (this.b != EntityTypes.S) {
               return EnumInteractionResult.a(world.B);
            }

            object = new GlowItemFrame(world, blockposition1, enumdirection);
         }

         NBTTagCompound nbttagcompound = itemstack.u();
         if (nbttagcompound != null) {
            EntityTypes.a(world, entityhuman, (Entity)object, nbttagcompound);
         }

         if (((EntityHanging)object).s()) {
            if (!world.B) {
               Player who = itemactioncontext.o() == null ? null : (Player)itemactioncontext.o().getBukkitEntity();
               Block blockClicked = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
               BlockFace blockFace = CraftBlock.notchToBlockFace(enumdirection);
               EquipmentSlot hand = CraftEquipmentSlot.getHand(itemactioncontext.p());
               HangingPlaceEvent event = new HangingPlaceEvent(
                  (Hanging)((EntityHanging)object).getBukkitEntity(), who, blockClicked, blockFace, hand, CraftItemStack.asBukkitCopy(itemstack)
               );
               world.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  return EnumInteractionResult.e;
               }

               ((EntityHanging)object).w();
               world.a(entityhuman, GameEvent.u, ((EntityHanging)object).de());
               world.b((Entity)object);
            }

            itemstack.h(1);
            return EnumInteractionResult.a(world.B);
         } else {
            return EnumInteractionResult.b;
         }
      }
   }

   protected boolean a(EntityHuman entityhuman, EnumDirection enumdirection, ItemStack itemstack, BlockPosition blockposition) {
      return !enumdirection.o().b() && entityhuman.a(blockposition, enumdirection, itemstack);
   }

   @Override
   public void a(ItemStack itemstack, @Nullable World world, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      super.a(itemstack, world, list, tooltipflag);
      if (this.b == EntityTypes.ar) {
         NBTTagCompound nbttagcompound = itemstack.u();
         if (nbttagcompound != null && nbttagcompound.b("EntityTag", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.p("EntityTag");
            EntityPainting.c(nbttagcompound1).ifPresentOrElse(holder -> {
               holder.e().ifPresent(resourcekey -> {
                  list.add(IChatBaseComponent.c(resourcekey.a().b("painting", "title")).a(EnumChatFormat.o));
                  list.add(IChatBaseComponent.c(resourcekey.a().b("painting", "author")).a(EnumChatFormat.h));
               });
               list.add(IChatBaseComponent.a("painting.dimensions", MathHelper.e(holder.a().a(), 16), MathHelper.e(holder.a().b(), 16)));
            }, () -> list.add(a));
         } else if (tooltipflag.b()) {
            list.add(a);
         }
      }
   }
}
