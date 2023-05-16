package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeItemStack;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class TemptingSensor extends Sensor<EntityCreature> {
   public static final int a = 10;
   private static final PathfinderTargetCondition c = PathfinderTargetCondition.b().a(10.0).d();
   private final RecipeItemStack d;

   public TemptingSensor(RecipeItemStack recipeitemstack) {
      this.d = recipeitemstack;
   }

   protected void a(WorldServer worldserver, EntityCreature entitycreature) {
      BehaviorController<?> behaviorcontroller = entitycreature.dH();
      Stream<EntityPlayer> stream = worldserver.v()
         .stream()
         .filter(IEntitySelector.f)
         .filter(entityplayer -> c.a(entitycreature, entityplayer))
         .filter(entityplayer -> entitycreature.a(entityplayer, 10.0))
         .filter(this::a)
         .filter(entityplayer -> !entitycreature.u(entityplayer));
      List<EntityHuman> list = stream.sorted(Comparator.comparingDouble(entitycreature::f)).collect(Collectors.toList());
      if (!list.isEmpty()) {
         EntityHuman entityhuman = list.get(0);
         EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(entitycreature, entityhuman, TargetReason.TEMPT);
         if (event.isCancelled()) {
            return;
         }

         if (event.getTarget() instanceof HumanEntity) {
            behaviorcontroller.a(MemoryModuleType.N, ((CraftHumanEntity)event.getTarget()).getHandle());
         } else {
            behaviorcontroller.b(MemoryModuleType.N);
         }
      } else {
         behaviorcontroller.b(MemoryModuleType.N);
      }
   }

   private boolean a(EntityHuman entityhuman) {
      return this.a(entityhuman.eK()) || this.a(entityhuman.eL());
   }

   private boolean a(ItemStack itemstack) {
      return this.d.a(itemstack);
   }

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.N);
   }
}
