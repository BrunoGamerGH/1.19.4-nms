package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Frog.Variant;

public class CraftFrog extends CraftAnimals implements Frog {
   public CraftFrog(CraftServer server, net.minecraft.world.entity.animal.frog.Frog entity) {
      super(server, entity);
   }

   public net.minecraft.world.entity.animal.frog.Frog getHandle() {
      return (net.minecraft.world.entity.animal.frog.Frog)this.entity;
   }

   @Override
   public String toString() {
      return "CraftFrog";
   }

   @Override
   public EntityType getType() {
      return EntityType.FROG;
   }

   public Entity getTongueTarget() {
      return this.getHandle().r().map(net.minecraft.world.entity.Entity::getBukkitEntity).orElse(null);
   }

   public void setTongueTarget(Entity target) {
      if (target == null) {
         this.getHandle().q();
      } else {
         this.getHandle().a(((CraftEntity)target).getHandle());
      }
   }

   public Variant getVariant() {
      return (Variant)Registry.FROG_VARIANT.get(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.aj.b(this.getHandle().w())));
   }

   public void setVariant(Variant variant) {
      Preconditions.checkArgument(variant != null, "variant");
      this.getHandle().a(BuiltInRegistries.aj.a(CraftNamespacedKey.toMinecraft(variant.getKey())));
   }
}
