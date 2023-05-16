package net.minecraft.world.level.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import org.apache.commons.lang3.StringUtils;

public class BlockSkullPlayer extends BlockSkull {
   protected BlockSkullPlayer(BlockBase.Info var0) {
      super(BlockSkull.Type.c, var0);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, @Nullable EntityLiving var3, ItemStack var4) {
      super.a(var0, var1, var2, var3, var4);
      TileEntity var5 = var0.c_(var1);
      if (var5 instanceof TileEntitySkull var6) {
         GameProfile var7 = null;
         if (var4.t()) {
            NBTTagCompound var8 = var4.u();
            if (var8.b("SkullOwner", 10)) {
               var7 = GameProfileSerializer.a(var8.p("SkullOwner"));
            } else if (var8.b("SkullOwner", 8) && !StringUtils.isBlank(var8.l("SkullOwner"))) {
               var7 = new GameProfile(null, var8.l("SkullOwner"));
            }
         }

         var6.a(var7);
      }
   }
}
