package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.level.World;
import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.Raid.RaidStatus;
import org.bukkit.entity.Raider;

public final class CraftRaid implements Raid {
   private final net.minecraft.world.entity.raid.Raid handle;

   public CraftRaid(net.minecraft.world.entity.raid.Raid handle) {
      this.handle = handle;
   }

   public boolean isStarted() {
      return this.handle.j();
   }

   public long getActiveTicks() {
      return this.handle.D;
   }

   public int getBadOmenLevel() {
      return this.handle.J;
   }

   public void setBadOmenLevel(int badOmenLevel) {
      int max = this.handle.l();
      Preconditions.checkArgument(badOmenLevel >= 0 && badOmenLevel <= max, "Bad Omen level must be between 0 and %s", max);
      this.handle.J = badOmenLevel;
   }

   public Location getLocation() {
      BlockPosition pos = this.handle.t();
      World world = this.handle.i();
      return new Location(world.getWorld(), (double)pos.u(), (double)pos.v(), (double)pos.w());
   }

   public RaidStatus getStatus() {
      if (this.handle.d()) {
         return RaidStatus.STOPPED;
      } else if (this.handle.e()) {
         return RaidStatus.VICTORY;
      } else {
         return this.handle.f() ? RaidStatus.LOSS : RaidStatus.ONGOING;
      }
   }

   public int getSpawnedGroups() {
      return this.handle.k();
   }

   public int getTotalGroups() {
      return this.handle.Q + (this.handle.J > 1 ? 1 : 0);
   }

   public int getTotalWaves() {
      return this.handle.Q;
   }

   public float getTotalHealth() {
      return this.handle.q();
   }

   public Set<UUID> getHeroes() {
      return Collections.unmodifiableSet(this.handle.C);
   }

   public List<Raider> getRaiders() {
      return this.handle.getRaiders().stream().map(new Function<EntityRaider, Raider>() {
         public Raider apply(EntityRaider entityRaider) {
            return (Raider)entityRaider.getBukkitEntity();
         }
      }).collect(ImmutableList.toImmutableList());
   }
}
