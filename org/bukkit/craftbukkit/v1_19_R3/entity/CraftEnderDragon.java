package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerPhase;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftDragonBattle;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EnderDragon.Phase;

public class CraftEnderDragon extends CraftMob implements EnderDragon, CraftEnemy {
   public CraftEnderDragon(CraftServer server, EntityEnderDragon entity) {
      super(server, entity);
   }

   public Set<ComplexEntityPart> getParts() {
      Builder<ComplexEntityPart> builder = ImmutableSet.builder();

      EntityComplexPart[] var5;
      for(EntityComplexPart part : var5 = this.getHandle().ce) {
         builder.add((ComplexEntityPart)part.getBukkitEntity());
      }

      return builder.build();
   }

   public EntityEnderDragon getHandle() {
      return (EntityEnderDragon)this.entity;
   }

   @Override
   public String toString() {
      return "CraftEnderDragon";
   }

   @Override
   public EntityType getType() {
      return EntityType.ENDER_DRAGON;
   }

   public Phase getPhase() {
      return Phase.values()[this.getHandle().aj().a(EntityEnderDragon.b)];
   }

   public void setPhase(Phase phase) {
      this.getHandle().fP().a(getMinecraftPhase(phase));
   }

   public static Phase getBukkitPhase(DragonControllerPhase phase) {
      return Phase.values()[phase.b()];
   }

   public static DragonControllerPhase getMinecraftPhase(Phase phase) {
      return DragonControllerPhase.a(phase.ordinal());
   }

   public BossBar getBossBar() {
      DragonBattle battle = this.getDragonBattle();
      return battle != null ? battle.getBossBar() : null;
   }

   public DragonBattle getDragonBattle() {
      return this.getHandle().fQ() != null ? new CraftDragonBattle(this.getHandle().fQ()) : null;
   }

   public int getDeathAnimationTicks() {
      return this.getHandle().bU;
   }
}
