package net.minecraft.world.food;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityExhaustionEvent.ExhaustionReason;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class FoodMetaData {
   public int a = 20;
   public float b = 5.0F;
   public float c;
   private int d;
   private EntityHuman entityhuman;
   public int saturatedRegenRate = 10;
   public int unsaturatedRegenRate = 80;
   public int starvationRate = 80;
   private int e = 20;

   public FoodMetaData() {
      throw new AssertionError("Whoopsie, we missed the bukkit.");
   }

   public FoodMetaData(EntityHuman entityhuman) {
      Validate.notNull(entityhuman);
      this.entityhuman = entityhuman;
   }

   public void a(int i, float f) {
      this.a = Math.min(i + this.a, 20);
      this.b = Math.min(this.b + (float)i * f * 2.0F, (float)this.a);
   }

   public void a(Item item, ItemStack itemstack) {
      if (item.u()) {
         FoodInfo foodinfo = item.v();
         int oldFoodLevel = this.a;
         FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(this.entityhuman, foodinfo.a() + oldFoodLevel, itemstack);
         if (!event.isCancelled()) {
            this.a(event.getFoodLevel() - oldFoodLevel, foodinfo.b());
         }

         ((EntityPlayer)this.entityhuman).getBukkitEntity().sendHealthUpdate();
      }
   }

   public void a(EntityHuman entityhuman) {
      EnumDifficulty enumdifficulty = entityhuman.H.ah();
      this.e = this.a;
      if (this.c > 4.0F) {
         this.c -= 4.0F;
         if (this.b > 0.0F) {
            this.b = Math.max(this.b - 1.0F, 0.0F);
         } else if (enumdifficulty != EnumDifficulty.a) {
            FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, Math.max(this.a - 1, 0));
            if (!event.isCancelled()) {
               this.a = event.getFoodLevel();
            }

            ((EntityPlayer)entityhuman).b.a(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), this.a, this.b));
         }
      }

      boolean flag = entityhuman.H.W().b(GameRules.j);
      if (flag && this.b > 0.0F && entityhuman.fU() && this.a >= 20) {
         ++this.d;
         if (this.d >= this.saturatedRegenRate) {
            float f = Math.min(this.b, 6.0F);
            entityhuman.heal(f / 6.0F, RegainReason.SATIATED);
            entityhuman.causeFoodExhaustion(f, ExhaustionReason.REGEN);
            this.d = 0;
         }
      } else if (flag && this.a >= 18 && entityhuman.fU()) {
         ++this.d;
         if (this.d >= this.unsaturatedRegenRate) {
            entityhuman.heal(1.0F, RegainReason.SATIATED);
            entityhuman.causeFoodExhaustion(entityhuman.H.spigotConfig.regenExhaustion, ExhaustionReason.REGEN);
            this.d = 0;
         }
      } else if (this.a <= 0) {
         ++this.d;
         if (this.d >= this.starvationRate) {
            if (entityhuman.eo() > 10.0F || enumdifficulty == EnumDifficulty.d || entityhuman.eo() > 1.0F && enumdifficulty == EnumDifficulty.c) {
               entityhuman.a(entityhuman.dG().i(), 1.0F);
            }

            this.d = 0;
         }
      } else {
         this.d = 0;
      }
   }

   public void a(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.b("foodLevel", 99)) {
         this.a = nbttagcompound.h("foodLevel");
         this.d = nbttagcompound.h("foodTickTimer");
         this.b = nbttagcompound.j("foodSaturationLevel");
         this.c = nbttagcompound.j("foodExhaustionLevel");
      }
   }

   public void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("foodLevel", this.a);
      nbttagcompound.a("foodTickTimer", this.d);
      nbttagcompound.a("foodSaturationLevel", this.b);
      nbttagcompound.a("foodExhaustionLevel", this.c);
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.e;
   }

   public boolean c() {
      return this.a < 20;
   }

   public void a(float f) {
      this.c = Math.min(this.c + f, 40.0F);
   }

   public float d() {
      return this.c;
   }

   public float e() {
      return this.b;
   }

   public void a(int i) {
      this.a = i;
   }

   public void b(float f) {
      this.b = f;
   }

   public void c(float f) {
      this.c = f;
   }
}
