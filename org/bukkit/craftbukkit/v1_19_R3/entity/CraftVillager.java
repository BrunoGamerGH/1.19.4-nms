package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.Locale;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.EntityZombieVillager;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.state.IBlockData;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class CraftVillager extends CraftAbstractVillager implements Villager {
   public CraftVillager(CraftServer server, EntityVillager entity) {
      super(server, entity);
   }

   public EntityVillager getHandle() {
      return (EntityVillager)this.entity;
   }

   @Override
   public String toString() {
      return "CraftVillager";
   }

   @Override
   public EntityType getType() {
      return EntityType.VILLAGER;
   }

   @Override
   public void remove() {
      this.getHandle().gv();
      super.remove();
   }

   public Profession getProfession() {
      return nmsToBukkitProfession(this.getHandle().gd().b());
   }

   public void setProfession(Profession profession) {
      Validate.notNull(profession);
      this.getHandle().a(this.getHandle().gd().a(bukkitToNmsProfession(profession)));
   }

   public Type getVillagerType() {
      return Type.valueOf(BuiltInRegistries.y.b(this.getHandle().gd().a()).a().toUpperCase(Locale.ROOT));
   }

   public void setVillagerType(Type type) {
      Validate.notNull(type);
      this.getHandle().a(this.getHandle().gd().a(BuiltInRegistries.y.a(CraftNamespacedKey.toMinecraft(type.getKey()))));
   }

   public int getVillagerLevel() {
      return this.getHandle().gd().c();
   }

   public void setVillagerLevel(int level) {
      Preconditions.checkArgument(1 <= level && level <= 5, "level must be between [1, 5]");
      this.getHandle().a(this.getHandle().gd().a(level));
   }

   public int getVillagerExperience() {
      return this.getHandle().r();
   }

   public void setVillagerExperience(int experience) {
      Preconditions.checkArgument(experience >= 0, "Experience must be positive");
      this.getHandle().t(experience);
   }

   public boolean sleep(Location location) {
      Preconditions.checkArgument(location != null, "Location cannot be null");
      Preconditions.checkArgument(location.getWorld() != null, "Location needs to be in a world");
      Preconditions.checkArgument(location.getWorld().equals(this.getWorld()), "Cannot sleep across worlds");
      Preconditions.checkState(!this.getHandle().generation, "Cannot sleep during world generation");
      BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      IBlockData iblockdata = this.getHandle().H.a_(position);
      if (!(iblockdata.b() instanceof BlockBed)) {
         return false;
      } else {
         this.getHandle().b(position);
         return true;
      }
   }

   public void wakeup() {
      Preconditions.checkState(this.isSleeping(), "Cannot wakeup if not sleeping");
      Preconditions.checkState(!this.getHandle().generation, "Cannot wakeup during world generation");
      this.getHandle().fv();
   }

   public void shakeHead() {
      this.getHandle().go();
   }

   public ZombieVillager zombify() {
      EntityZombieVillager entityzombievillager = EntityZombie.zombifyVillager(
         this.getHandle().H.getMinecraftWorld(), this.getHandle(), this.getHandle().dg(), this.isSilent(), SpawnReason.CUSTOM
      );
      return entityzombievillager != null ? (ZombieVillager)entityzombievillager.getBukkitEntity() : null;
   }

   public static Profession nmsToBukkitProfession(VillagerProfession nms) {
      return Profession.valueOf(BuiltInRegistries.z.b(nms).a().toUpperCase(Locale.ROOT));
   }

   public static VillagerProfession bukkitToNmsProfession(Profession bukkit) {
      return BuiltInRegistries.z.a(CraftNamespacedKey.toMinecraft(bukkit.getKey()));
   }
}
