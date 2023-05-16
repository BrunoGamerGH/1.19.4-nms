package net.minecraft.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.level.World;

public class ItemSuspiciousStew extends Item {
   public static final String a = "Effects";
   public static final String b = "EffectId";
   public static final String c = "EffectDuration";
   public static final int d = 160;

   public ItemSuspiciousStew(Item.Info var0) {
      super(var0);
   }

   public static void a(ItemStack var0, MobEffectList var1, int var2) {
      NBTTagCompound var3 = var0.v();
      NBTTagList var4 = var3.c("Effects", 9);
      NBTTagCompound var5 = new NBTTagCompound();
      var5.a("EffectId", MobEffectList.a(var1));
      var5.a("EffectDuration", var2);
      var4.add(var5);
      var3.a("Effects", var4);
   }

   private static void a(ItemStack var0, Consumer<MobEffect> var1) {
      NBTTagCompound var2 = var0.u();
      if (var2 != null && var2.b("Effects", 9)) {
         NBTTagList var3 = var2.c("Effects", 10);

         for(int var4 = 0; var4 < var3.size(); ++var4) {
            NBTTagCompound var5 = var3.a(var4);
            int var6;
            if (var5.b("EffectDuration", 3)) {
               var6 = var5.h("EffectDuration");
            } else {
               var6 = 160;
            }

            MobEffectList var7 = MobEffectList.a(var5.h("EffectId"));
            if (var7 != null) {
               var1.accept(new MobEffect(var7, var6));
            }
         }
      }
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      super.a(var0, var1, var2, var3);
      if (var3.b()) {
         List<MobEffect> var4 = new ArrayList<>();
         a(var0, var4::add);
         PotionUtil.a(var4, var2, 1.0F);
      }
   }

   @Override
   public ItemStack a(ItemStack var0, World var1, EntityLiving var2) {
      ItemStack var3 = super.a(var0, var1, var2);
      a(var3, var2::b);
      return var2 instanceof EntityHuman && ((EntityHuman)var2).fK().d ? var3 : new ItemStack(Items.oy);
   }
}
