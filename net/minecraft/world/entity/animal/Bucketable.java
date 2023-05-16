package net.minecraft.world.entity.animal;

import java.util.Optional;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemLiquidUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketEntityEvent;

public interface Bucketable {
   boolean r();

   void w(boolean var1);

   void l(ItemStack var1);

   void c(NBTTagCompound var1);

   ItemStack b();

   SoundEffect w();

   @Deprecated
   static void a(EntityInsentient entityinsentient, ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.v();
      if (entityinsentient.aa()) {
         itemstack.a(entityinsentient.ab());
      }

      if (entityinsentient.fK()) {
         nbttagcompound.a("NoAI", entityinsentient.fK());
      }

      if (entityinsentient.aO()) {
         nbttagcompound.a("Silent", entityinsentient.aO());
      }

      if (entityinsentient.aP()) {
         nbttagcompound.a("NoGravity", entityinsentient.aP());
      }

      if (entityinsentient.bY()) {
         nbttagcompound.a("Glowing", entityinsentient.bY());
      }

      if (entityinsentient.cm()) {
         nbttagcompound.a("Invulnerable", entityinsentient.cm());
      }

      nbttagcompound.a("Health", entityinsentient.eo());
   }

   @Deprecated
   static void a(EntityInsentient entityinsentient, NBTTagCompound nbttagcompound) {
      if (nbttagcompound.e("NoAI")) {
         entityinsentient.t(nbttagcompound.q("NoAI"));
      }

      if (nbttagcompound.e("Silent")) {
         entityinsentient.d(nbttagcompound.q("Silent"));
      }

      if (nbttagcompound.e("NoGravity")) {
         entityinsentient.e(nbttagcompound.q("NoGravity"));
      }

      if (nbttagcompound.e("Glowing")) {
         entityinsentient.i(nbttagcompound.q("Glowing"));
      }

      if (nbttagcompound.e("Invulnerable")) {
         entityinsentient.m(nbttagcompound.q("Invulnerable"));
      }

      if (nbttagcompound.b("Health", 99)) {
         entityinsentient.c(nbttagcompound.j("Health"));
      }
   }

   static <T extends EntityLiving & Bucketable> Optional<EnumInteractionResult> a(EntityHuman entityhuman, EnumHand enumhand, T t0) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.c() == Items.pH && t0.bq()) {
         ItemStack itemstack1 = t0.b();
         t0.l(itemstack1);
         PlayerBucketEntityEvent playerBucketFishEvent = CraftEventFactory.callPlayerFishBucketEvent(t0, entityhuman, itemstack, itemstack1, enumhand);
         itemstack1 = CraftItemStack.asNMSCopy(playerBucketFishEvent.getEntityBucket());
         if (playerBucketFishEvent.isCancelled()) {
            ((EntityPlayer)entityhuman).bP.b();
            ((EntityPlayer)entityhuman).b.a(new PacketPlayOutSpawnEntity(t0));
            t0.aj().refresh((EntityPlayer)entityhuman);
            return Optional.of(EnumInteractionResult.e);
         } else {
            t0.a(t0.w(), 1.0F, 1.0F);
            ItemStack itemstack2 = ItemLiquidUtil.a(itemstack, entityhuman, itemstack1, false);
            entityhuman.a(enumhand, itemstack2);
            World world = t0.H;
            if (!world.B) {
               CriterionTriggers.j.a((EntityPlayer)entityhuman, itemstack1);
            }

            t0.ai();
            return Optional.of(EnumInteractionResult.a(world.B));
         }
      } else {
         return Optional.empty();
      }
   }
}
