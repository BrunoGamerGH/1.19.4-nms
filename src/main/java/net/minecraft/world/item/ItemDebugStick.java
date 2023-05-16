package net.minecraft.world.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class ItemDebugStick extends Item {
   public ItemDebugStick(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public boolean i(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
      if (!world.B) {
         this.a(entityhuman, iblockdata, world, blockposition, false, entityhuman.b(EnumHand.a));
      }

      return false;
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      EntityHuman entityhuman = itemactioncontext.o();
      World world = itemactioncontext.q();
      if (!world.B && entityhuman != null) {
         BlockPosition blockposition = itemactioncontext.a();
         if (!this.a(entityhuman, world.a_(blockposition), world, blockposition, true, itemactioncontext.n())) {
            return EnumInteractionResult.e;
         }
      }

      return EnumInteractionResult.a(world.B);
   }

   public boolean a(
      EntityHuman entityhuman, IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, boolean flag, ItemStack itemstack
   ) {
      if (!entityhuman.gg()
         && (!entityhuman.fK().d || !entityhuman.getBukkitEntity().hasPermission("minecraft.debugstick"))
         && !entityhuman.getBukkitEntity().hasPermission("minecraft.debugstick.always")) {
         return false;
      } else {
         Block block = iblockdata.b();
         BlockStateList<Block, IBlockData> blockstatelist = block.n();
         Collection<IBlockState<?>> collection = blockstatelist.d();
         String s = BuiltInRegistries.f.b(block).toString();
         if (collection.isEmpty()) {
            a(entityhuman, IChatBaseComponent.a(this.a() + ".empty", s));
            return false;
         } else {
            NBTTagCompound nbttagcompound = itemstack.a("DebugProperty");
            String s1 = nbttagcompound.l(s);
            IBlockState<?> iblockstate = blockstatelist.a(s1);
            if (flag) {
               if (iblockstate == null) {
                  iblockstate = collection.iterator().next();
               }

               IBlockData iblockdata1 = a(iblockdata, iblockstate, entityhuman.fz());
               generatoraccess.a(blockposition, iblockdata1, 18);
               a(entityhuman, IChatBaseComponent.a(this.a() + ".update", iblockstate.f(), a(iblockdata1, iblockstate)));
            } else {
               iblockstate = a(collection, iblockstate, entityhuman.fz());
               String s2 = iblockstate.f();
               nbttagcompound.a(s, s2);
               a(entityhuman, IChatBaseComponent.a(this.a() + ".select", s2, a(iblockdata, iblockstate)));
            }

            return true;
         }
      }
   }

   private static <T extends Comparable<T>> IBlockData a(IBlockData iblockdata, IBlockState<T> iblockstate, boolean flag) {
      return iblockdata.a(iblockstate, a(iblockstate.a(), iblockdata.c(iblockstate), flag));
   }

   private static <T> T a(Iterable<T> iterable, @Nullable T t0, boolean flag) {
      return (T)(flag ? SystemUtils.b(iterable, t0) : SystemUtils.a(iterable, t0));
   }

   private static void a(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
      ((EntityPlayer)entityhuman).b(ichatbasecomponent, true);
   }

   private static <T extends Comparable<T>> String a(IBlockData iblockdata, IBlockState<T> iblockstate) {
      return iblockstate.a(iblockdata.c(iblockstate));
   }
}
