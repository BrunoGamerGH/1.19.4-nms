package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collection;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.item.alchemy.PotionUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class CraftThrownPotion extends CraftThrowableProjectile implements ThrownPotion {
   public CraftThrownPotion(CraftServer server, EntityPotion entity) {
      super(server, entity);
   }

   public Collection<PotionEffect> getEffects() {
      Builder<PotionEffect> builder = ImmutableList.builder();

      for(MobEffect effect : PotionUtil.a(this.getHandle().k())) {
         builder.add(CraftPotionUtil.toBukkit(effect));
      }

      return builder.build();
   }

   @Override
   public ItemStack getItem() {
      return CraftItemStack.asBukkitCopy(this.getHandle().k());
   }

   @Override
   public void setItem(ItemStack item) {
      Validate.notNull(item, "ItemStack cannot be null.");
      Validate.isTrue(
         item.getType() == Material.LINGERING_POTION || item.getType() == Material.SPLASH_POTION,
         "ItemStack must be a lingering or splash potion. This item stack was " + item.getType() + "."
      );
      this.getHandle().a(CraftItemStack.asNMSCopy(item));
   }

   public EntityPotion getHandle() {
      return (EntityPotion)this.entity;
   }

   public EntityType getType() {
      return EntityType.SPLASH_POTION;
   }
}
