package org.bukkit.craftbukkit.v1_19_R3.block;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.world.ChestLock;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeacon;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftBeacon extends CraftBlockEntityState<TileEntityBeacon> implements Beacon {
   public CraftBeacon(World world, TileEntityBeacon tileEntity) {
      super(world, tileEntity);
   }

   public Collection<LivingEntity> getEntitiesInRange() {
      this.ensureNoWorldGeneration();
      TileEntity tileEntity = this.getTileEntityFromWorld();
      if (!(tileEntity instanceof TileEntityBeacon)) {
         return new ArrayList();
      } else {
         TileEntityBeacon beacon = (TileEntityBeacon)tileEntity;
         Collection<EntityHuman> nms = TileEntityBeacon.getHumansInRange(beacon.k(), beacon.p(), beacon.l);
         Collection<LivingEntity> bukkit = new ArrayList(nms.size());

         for(EntityHuman human : nms) {
            bukkit.add(human.getBukkitEntity());
         }

         return bukkit;
      }
   }

   public int getTier() {
      return this.getSnapshot().l;
   }

   public PotionEffect getPrimaryEffect() {
      return this.getSnapshot().getPrimaryEffect();
   }

   public void setPrimaryEffect(PotionEffectType effect) {
      this.getSnapshot().n = effect != null ? MobEffectList.a(effect.getId()) : null;
   }

   public PotionEffect getSecondaryEffect() {
      return this.getSnapshot().getSecondaryEffect();
   }

   public void setSecondaryEffect(PotionEffectType effect) {
      this.getSnapshot().r = effect != null ? MobEffectList.a(effect.getId()) : null;
   }

   public String getCustomName() {
      TileEntityBeacon beacon = this.getSnapshot();
      return beacon.s != null ? CraftChatMessage.fromComponent(beacon.s) : null;
   }

   public void setCustomName(String name) {
      this.getSnapshot().a(CraftChatMessage.fromStringOrNull(name));
   }

   public boolean isLocked() {
      return !this.getSnapshot().t.c.isEmpty();
   }

   public String getLock() {
      return this.getSnapshot().t.c;
   }

   public void setLock(String key) {
      this.getSnapshot().t = key == null ? ChestLock.a : new ChestLock(key);
   }
}
