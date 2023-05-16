package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.world.level.World;

public class ItemFireworksCharge extends Item {
   public ItemFireworksCharge(Item.Info var0) {
      super(var0);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      NBTTagCompound var4 = var0.b("Explosion");
      if (var4 != null) {
         a(var4, var2);
      }
   }

   public static void a(NBTTagCompound var0, List<IChatBaseComponent> var1) {
      ItemFireworks.EffectType var2 = ItemFireworks.EffectType.a(var0.f("Type"));
      var1.add(IChatBaseComponent.c("item.minecraft.firework_star.shape." + var2.b()).a(EnumChatFormat.h));
      int[] var3 = var0.n("Colors");
      if (var3.length > 0) {
         var1.add(a(IChatBaseComponent.h().a(EnumChatFormat.h), var3));
      }

      int[] var4 = var0.n("FadeColors");
      if (var4.length > 0) {
         var1.add(a(IChatBaseComponent.c("item.minecraft.firework_star.fade_to").b(CommonComponents.q).a(EnumChatFormat.h), var4));
      }

      if (var0.q("Trail")) {
         var1.add(IChatBaseComponent.c("item.minecraft.firework_star.trail").a(EnumChatFormat.h));
      }

      if (var0.q("Flicker")) {
         var1.add(IChatBaseComponent.c("item.minecraft.firework_star.flicker").a(EnumChatFormat.h));
      }
   }

   private static IChatBaseComponent a(IChatMutableComponent var0, int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var2 > 0) {
            var0.f(", ");
         }

         var0.b(a(var1[var2]));
      }

      return var0;
   }

   private static IChatBaseComponent a(int var0) {
      EnumColor var1 = EnumColor.b(var0);
      return var1 == null
         ? IChatBaseComponent.c("item.minecraft.firework_star.custom_color")
         : IChatBaseComponent.c("item.minecraft.firework_star." + var1.b());
   }
}
