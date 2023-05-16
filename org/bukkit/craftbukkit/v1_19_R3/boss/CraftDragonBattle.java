package org.bukkit.craftbukkit.v1_19_R3.boss;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.dimension.end.EnderDragonBattle;
import net.minecraft.world.level.dimension.end.EnumDragonRespawn;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.boss.DragonBattle.RespawnPhase;
import org.bukkit.entity.EnderDragon;

public class CraftDragonBattle implements DragonBattle {
   private final EnderDragonBattle handle;

   public CraftDragonBattle(EnderDragonBattle handle) {
      this.handle = handle;
   }

   public EnderDragon getEnderDragon() {
      Entity entity = this.handle.l.a(this.handle.u);
      return entity != null ? (EnderDragon)entity.getBukkitEntity() : null;
   }

   public BossBar getBossBar() {
      return new CraftBossBar(this.handle.k);
   }

   public Location getEndPortalLocation() {
      return this.handle.w == null
         ? null
         : new Location(this.handle.l.getWorld(), (double)this.handle.w.u(), (double)this.handle.w.v(), (double)this.handle.w.w());
   }

   public boolean generateEndPortal(boolean withPortals) {
      if (this.handle.w == null && this.handle.j() == null) {
         this.handle.a(withPortals);
         return true;
      } else {
         return false;
      }
   }

   public boolean hasBeenPreviouslyKilled() {
      return this.handle.d();
   }

   public void initiateRespawn() {
      this.handle.e();
   }

   public RespawnPhase getRespawnPhase() {
      return this.toBukkitRespawnPhase(this.handle.x);
   }

   public boolean setRespawnPhase(RespawnPhase phase) {
      Preconditions.checkArgument(phase != null && phase != RespawnPhase.NONE, "Invalid respawn phase provided: %s", phase);
      if (this.handle.x == null) {
         return false;
      } else {
         this.handle.a(this.toNMSRespawnPhase(phase));
         return true;
      }
   }

   public void resetCrystals() {
      this.handle.f();
   }

   @Override
   public int hashCode() {
      return this.handle.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof CraftDragonBattle && ((CraftDragonBattle)obj).handle == this.handle;
   }

   private RespawnPhase toBukkitRespawnPhase(EnumDragonRespawn phase) {
      return phase != null ? RespawnPhase.values()[phase.ordinal()] : RespawnPhase.NONE;
   }

   private EnumDragonRespawn toNMSRespawnPhase(RespawnPhase phase) {
      return phase != RespawnPhase.NONE ? EnumDragonRespawn.values()[phase.ordinal()] : null;
   }
}
