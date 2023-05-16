package net.minecraft.world.item.trading;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.item.ItemStack;

public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
   public MerchantRecipeList() {
   }

   private MerchantRecipeList(int var0) {
      super(var0);
   }

   public MerchantRecipeList(NBTTagCompound var0) {
      NBTTagList var1 = var0.c("Recipes", 10);

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         this.add(new MerchantRecipe(var1.a(var2)));
      }
   }

   @Nullable
   public MerchantRecipe a(ItemStack var0, ItemStack var1, int var2) {
      if (var2 > 0 && var2 < this.size()) {
         MerchantRecipe var3 = this.get(var2);
         return var3.a(var0, var1) ? var3 : null;
      } else {
         for(int var3 = 0; var3 < this.size(); ++var3) {
            MerchantRecipe var4 = this.get(var3);
            if (var4.a(var0, var1)) {
               return var4;
            }
         }

         return null;
      }
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this, (var0x, var1x) -> {
         var0x.a(var1x.a());
         var0x.a(var1x.d());
         var0x.a(var1x.c());
         var0x.writeBoolean(var1x.p());
         var0x.writeInt(var1x.g());
         var0x.writeInt(var1x.i());
         var0x.writeInt(var1x.o());
         var0x.writeInt(var1x.m());
         var0x.writeFloat(var1x.n());
         var0x.writeInt(var1x.k());
      });
   }

   public static MerchantRecipeList b(PacketDataSerializer var0) {
      return var0.a(MerchantRecipeList::new, var0x -> {
         ItemStack var1 = var0x.r();
         ItemStack var2 = var0x.r();
         ItemStack var3 = var0x.r();
         boolean var4 = var0x.readBoolean();
         int var5 = var0x.readInt();
         int var6 = var0x.readInt();
         int var7 = var0x.readInt();
         int var8 = var0x.readInt();
         float var9 = var0x.readFloat();
         int var10 = var0x.readInt();
         MerchantRecipe var11 = new MerchantRecipe(var1, var3, var2, var5, var6, var7, var9, var10);
         if (var4) {
            var11.q();
         }

         var11.b(var8);
         return var11;
      });
   }

   public NBTTagCompound a() {
      NBTTagCompound var0 = new NBTTagCompound();
      NBTTagList var1 = new NBTTagList();

      for(int var2 = 0; var2 < this.size(); ++var2) {
         MerchantRecipe var3 = this.get(var2);
         var1.add(var3.t());
      }

      var0.a("Recipes", var1);
      return var0;
   }
}
