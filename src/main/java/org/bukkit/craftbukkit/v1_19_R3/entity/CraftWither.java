package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.EntityWither;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftBossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wither.Head;

public class CraftWither extends CraftMonster implements Wither {
   private BossBar bossBar;

   public CraftWither(CraftServer server, EntityWither entity) {
      super(server, entity);
      if (entity.cb != null) {
         this.bossBar = new CraftBossBar(entity.cb);
      }
   }

   public EntityWither getHandle() {
      return (EntityWither)this.entity;
   }

   @Override
   public String toString() {
      return "CraftWither";
   }

   @Override
   public EntityType getType() {
      return EntityType.WITHER;
   }

   public BossBar getBossBar() {
      return this.bossBar;
   }

   public void setTarget(Head head, LivingEntity livingEntity) {
      Preconditions.checkArgument(head != null, "head cannot be null");
      int entityId = livingEntity != null ? livingEntity.getEntityId() : 0;
      this.getHandle().a(head.ordinal(), entityId);
   }

   public LivingEntity getTarget(Head head) {
      Preconditions.checkArgument(head != null, "head cannot be null");
      int entityId = this.getHandle().s(head.ordinal());
      if (entityId == 0) {
         return null;
      } else {
         Entity target = this.getHandle().Y().a(entityId);
         return target != null ? (LivingEntity)target.getBukkitEntity() : null;
      }
   }
}
