package net.minecraft.world.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import org.apache.commons.lang3.StringUtils;

public class ItemSkullPlayer extends ItemBlockWallable {
   public static final String c = "SkullOwner";

   public ItemSkullPlayer(Block block, Block block1, Item.Info item_info) {
      super(block, block1, item_info, EnumDirection.a);
   }

   @Override
   public IChatBaseComponent m(ItemStack itemstack) {
      if (itemstack.a(Items.tp) && itemstack.t()) {
         String s = null;
         NBTTagCompound nbttagcompound = itemstack.u();
         if (nbttagcompound.b("SkullOwner", 8)) {
            s = nbttagcompound.l("SkullOwner");
         } else if (nbttagcompound.b("SkullOwner", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.p("SkullOwner");
            if (nbttagcompound1.b("Name", 8)) {
               s = nbttagcompound1.l("Name");
            }
         }

         if (s != null) {
            return IChatBaseComponent.a(this.a() + ".named", s);
         }
      }

      return super.m(itemstack);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (nbttagcompound.b("SkullOwner", 8) && !StringUtils.isBlank(nbttagcompound.l("SkullOwner"))) {
         GameProfile gameprofile = new GameProfile(null, nbttagcompound.l("SkullOwner"));
         TileEntitySkull.a(gameprofile, gameprofile1 -> nbttagcompound.a("SkullOwner", GameProfileSerializer.a(new NBTTagCompound(), gameprofile1)));
      } else {
         NBTTagList textures = nbttagcompound.p("SkullOwner").p("Properties").c("textures", 10);

         for(int i = 0; i < textures.size(); ++i) {
            if (textures.k(i) instanceof NBTTagCompound
               && !((NBTTagCompound)textures.k(i)).b("Signature", 8)
               && ((NBTTagCompound)textures.k(i)).l("Value").trim().isEmpty()) {
               nbttagcompound.r("SkullOwner");
               break;
            }
         }
      }
   }
}
