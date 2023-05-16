package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.Random;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.FireworkMeta;

public class CraftFirework extends CraftProjectile implements Firework {
   private final Random random = new Random();
   private final CraftItemStack item;

   public CraftFirework(CraftServer server, EntityFireworks entity) {
      super(server, entity);
      ItemStack item = this.getHandle().aj().a(EntityFireworks.b);
      if (item.b()) {
         item = new ItemStack(Items.tw);
         this.getHandle().aj().b(EntityFireworks.b, item);
      }

      this.item = CraftItemStack.asCraftMirror(item);
      if (this.item.getType() != Material.FIREWORK_ROCKET) {
         this.item.setType(Material.FIREWORK_ROCKET);
      }
   }

   public EntityFireworks getHandle() {
      return (EntityFireworks)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFirework";
   }

   public EntityType getType() {
      return EntityType.FIREWORK;
   }

   public FireworkMeta getFireworkMeta() {
      return (FireworkMeta)this.item.getItemMeta();
   }

   public void setFireworkMeta(FireworkMeta meta) {
      this.item.setItemMeta(meta);
      this.getHandle().f = 10 * (1 + meta.getPower()) + this.random.nextInt(6) + this.random.nextInt(7);
      this.getHandle().aj().markDirty(EntityFireworks.b);
   }

   public boolean setAttachedTo(LivingEntity entity) {
      if (this.isDetonated()) {
         return false;
      } else {
         this.getHandle().g = entity != null ? ((CraftLivingEntity)entity).getHandle() : null;
         return true;
      }
   }

   public LivingEntity getAttachedTo() {
      EntityLiving entity = this.getHandle().g;
      return entity != null ? (LivingEntity)entity.getBukkitEntity() : null;
   }

   public boolean setLife(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "ticks must be greater than or equal to 0");
      if (this.isDetonated()) {
         return false;
      } else {
         this.getHandle().e = ticks;
         return true;
      }
   }

   public int getLife() {
      return this.getHandle().e;
   }

   public boolean setMaxLife(int ticks) {
      Preconditions.checkArgument(ticks > 0, "ticks must be greater than 0");
      if (this.isDetonated()) {
         return false;
      } else {
         this.getHandle().f = ticks;
         return true;
      }
   }

   public int getMaxLife() {
      return this.getHandle().f;
   }

   public void detonate() {
      this.setLife(this.getMaxLife() + 1);
   }

   public boolean isDetonated() {
      return this.getHandle().e > this.getHandle().f;
   }

   public boolean isShotAtAngle() {
      return this.getHandle().j();
   }

   public void setShotAtAngle(boolean shotAtAngle) {
      this.getHandle().aj().b(EntityFireworks.d, shotAtAngle);
   }
}
