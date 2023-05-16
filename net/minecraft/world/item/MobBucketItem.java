package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.EntityTropicalFish;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidType;

public class MobBucketItem extends ItemBucket {
   private final EntityTypes<?> a;
   private final SoundEffect b;

   public MobBucketItem(EntityTypes<?> var0, FluidType var1, SoundEffect var2, Item.Info var3) {
      super(var1, var3);
      this.a = var0;
      this.b = var2;
   }

   @Override
   public void a(@Nullable EntityHuman var0, World var1, ItemStack var2, BlockPosition var3) {
      if (var1 instanceof WorldServer) {
         this.a((WorldServer)var1, var2, var3);
         var1.a(var0, GameEvent.u, var3);
      }
   }

   @Override
   protected void a(@Nullable EntityHuman var0, GeneratorAccess var1, BlockPosition var2) {
      var1.a(var0, var2, this.b, SoundCategory.g, 1.0F, 1.0F);
   }

   private void a(WorldServer var0, ItemStack var1, BlockPosition var2) {
      Entity var3 = this.a.a(var0, var1, null, var2, EnumMobSpawn.l, true, false);
      if (var3 instanceof Bucketable var4) {
         var4.c(var1.v());
         var4.w(true);
      }
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      if (this.a == EntityTypes.bc) {
         NBTTagCompound var4 = var0.u();
         if (var4 != null && var4.b("BucketVariantTag", 3)) {
            int var5 = var4.h("BucketVariantTag");
            EnumChatFormat[] var6 = new EnumChatFormat[]{EnumChatFormat.u, EnumChatFormat.h};
            String var7 = "color.minecraft." + EntityTropicalFish.r(var5);
            String var8 = "color.minecraft." + EntityTropicalFish.s(var5);

            for(int var9 = 0; var9 < EntityTropicalFish.c.size(); ++var9) {
               if (var5 == EntityTropicalFish.c.get(var9).a()) {
                  var2.add(IChatBaseComponent.c(EntityTropicalFish.c(var9)).a(var6));
                  return;
               }
            }

            var2.add(EntityTropicalFish.t(var5).d().d().a(var6));
            IChatMutableComponent var9 = IChatBaseComponent.c(var7);
            if (!var7.equals(var8)) {
               var9.f(", ").b(IChatBaseComponent.c(var8));
            }

            var9.a(var6);
            var2.add(var9);
         }
      }
   }
}
