package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.animal.EntityRabbit;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Rabbit.Type;

public class CraftRabbit extends CraftAnimals implements Rabbit {
   public CraftRabbit(CraftServer server, EntityRabbit entity) {
      super(server, entity);
   }

   public EntityRabbit getHandle() {
      return (EntityRabbit)this.entity;
   }

   @Override
   public String toString() {
      return "CraftRabbit{RabbitType=" + this.getRabbitType() + "}";
   }

   @Override
   public EntityType getType() {
      return EntityType.RABBIT;
   }

   public Type getRabbitType() {
      return Type.values()[this.getHandle().fS().ordinal()];
   }

   public void setRabbitType(Type type) {
      EntityRabbit entity = this.getHandle();
      if (this.getRabbitType() == Type.THE_KILLER_BUNNY) {
         World world = ((CraftWorld)this.getWorld()).getHandle();
         entity.bN = new PathfinderGoalSelector(world.ad());
         entity.bO = new PathfinderGoalSelector(world.ad());
         entity.x();
         entity.initializePathFinderGoals();
      }

      entity.a(EntityRabbit.Variant.values()[type.ordinal()]);
   }
}
