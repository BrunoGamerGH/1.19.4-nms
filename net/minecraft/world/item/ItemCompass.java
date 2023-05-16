package net.minecraft.world.item;

import com.mojang.logging.LogUtils;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

public class ItemCompass extends Item implements ItemVanishable {
   private static final Logger d = LogUtils.getLogger();
   public static final String a = "LodestonePos";
   public static final String b = "LodestoneDimension";
   public static final String c = "LodestoneTracked";

   public ItemCompass(Item.Info var0) {
      super(var0);
   }

   public static boolean d(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      return var1 != null && (var1.e("LodestoneDimension") || var1.e("LodestonePos"));
   }

   private static Optional<ResourceKey<World>> c(NBTTagCompound var0) {
      return World.g.parse(DynamicOpsNBT.a, var0.c("LodestoneDimension")).result();
   }

   @Nullable
   public static GlobalPos a(NBTTagCompound var0) {
      boolean var1 = var0.e("LodestonePos");
      boolean var2 = var0.e("LodestoneDimension");
      if (var1 && var2) {
         Optional<ResourceKey<World>> var3 = c(var0);
         if (var3.isPresent()) {
            BlockPosition var4 = GameProfileSerializer.b(var0.p("LodestonePos"));
            return GlobalPos.a(var3.get(), var4);
         }
      }

      return null;
   }

   @Nullable
   public static GlobalPos a(World var0) {
      return var0.q_().j() ? GlobalPos.a(var0.ab(), var0.Q()) : null;
   }

   @Override
   public boolean i(ItemStack var0) {
      return d(var0) || super.i(var0);
   }

   @Override
   public void a(ItemStack var0, World var1, Entity var2, int var3, boolean var4) {
      if (!var1.B) {
         if (d(var0)) {
            NBTTagCompound var5 = var0.v();
            if (var5.e("LodestoneTracked") && !var5.q("LodestoneTracked")) {
               return;
            }

            Optional<ResourceKey<World>> var6 = c(var5);
            if (var6.isPresent() && var6.get() == var1.ab() && var5.e("LodestonePos")) {
               BlockPosition var7 = GameProfileSerializer.b(var5.p("LodestonePos"));
               if (!var1.j(var7) || !((WorldServer)var1).w().a(PoiTypes.s, var7)) {
                  var5.r("LodestonePos");
               }
            }
         }
      }
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      BlockPosition var1 = var0.a();
      World var2 = var0.q();
      if (!var2.a_(var1).a(Blocks.pm)) {
         return super.a(var0);
      } else {
         var2.a(null, var1, SoundEffects.mC, SoundCategory.h, 1.0F, 1.0F);
         EntityHuman var3 = var0.o();
         ItemStack var4 = var0.n();
         boolean var5 = !var3.fK().d && var4.K() == 1;
         if (var5) {
            this.a(var2.ab(), var1, var4.v());
         } else {
            ItemStack var6 = new ItemStack(Items.qa, 1);
            NBTTagCompound var7 = var4.t() ? var4.u().h() : new NBTTagCompound();
            var6.c(var7);
            if (!var3.fK().d) {
               var4.h(1);
            }

            this.a(var2.ab(), var1, var7);
            if (!var3.fJ().e(var6)) {
               var3.a(var6, false);
            }
         }

         return EnumInteractionResult.a(var2.B);
      }
   }

   private void a(ResourceKey<World> var0, BlockPosition var1, NBTTagCompound var2) {
      var2.a("LodestonePos", GameProfileSerializer.a(var1));
      World.g.encodeStart(DynamicOpsNBT.a, var0).resultOrPartial(d::error).ifPresent(var1x -> var2.a("LodestoneDimension", var1x));
      var2.a("LodestoneTracked", true);
   }

   @Override
   public String j(ItemStack var0) {
      return d(var0) ? "item.minecraft.lodestone_compass" : super.j(var0);
   }
}
