package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.OptionalBox.Mu;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.entity.EntityInteractEvent;

public class BehaviorInteractDoor {
   private static final int a = 20;
   private static final double b = 3.0;
   private static final double c = 2.0;

   public static BehaviorControl<EntityLiving> a() {
      MutableObject<PathPoint> mutableobject = new MutableObject(null);
      MutableInt mutableint = new MutableInt(0);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(behaviorbuilder_b -> behaviorbuilder_b.group(
                  behaviorbuilder_b.b(MemoryModuleType.t), behaviorbuilder_b.a(MemoryModuleType.v), behaviorbuilder_b.a(MemoryModuleType.g)
               )
               .apply(behaviorbuilder_b, (memoryaccessor, memoryaccessor1, memoryaccessor2) -> (worldserver, entityliving, i) -> {
                     PathEntity pathentity = behaviorbuilder_b.b(memoryaccessor);
                     Optional<Set<GlobalPos>> optional = behaviorbuilder_b.a(memoryaccessor1);
                     if (!pathentity.b() && !pathentity.c()) {
                        if (Objects.equals(mutableobject.getValue(), pathentity.h())) {
                           mutableint.setValue(20);
                        } else if (mutableint.decrementAndGet() > 0) {
                           return false;
                        }
      
                        mutableobject.setValue(pathentity.h());
                        PathPoint pathpoint = pathentity.i();
                        PathPoint pathpoint1 = pathentity.h();
                        BlockPosition blockposition = pathpoint.a();
                        IBlockData iblockdata = worldserver.a_(blockposition);
                        if (iblockdata.a(TagsBlock.g, blockbase_blockdata -> blockbase_blockdata.b() instanceof BlockDoor)) {
                           BlockDoor blockdoor = (BlockDoor)iblockdata.b();
                           if (!blockdoor.h(iblockdata)) {
                              EntityInteractEvent event = new EntityInteractEvent(entityliving.getBukkitEntity(), CraftBlock.at(entityliving.H, blockposition));
                              entityliving.H.getCraftServer().getPluginManager().callEvent(event);
                              if (event.isCancelled()) {
                                 return false;
                              }
      
                              blockdoor.a(entityliving, worldserver, iblockdata, blockposition, true);
                           }
      
                           optional = a(memoryaccessor1, optional, worldserver, blockposition);
                        }
      
                        BlockPosition blockposition1 = pathpoint1.a();
                        IBlockData iblockdata1 = worldserver.a_(blockposition1);
                        if (iblockdata1.a(TagsBlock.g, blockbase_blockdata -> blockbase_blockdata.b() instanceof BlockDoor)) {
                           BlockDoor blockdoor1 = (BlockDoor)iblockdata1.b();
                           if (!blockdoor1.h(iblockdata1)) {
                              EntityInteractEvent event = new EntityInteractEvent(
                                 entityliving.getBukkitEntity(), CraftBlock.at(entityliving.H, blockposition1)
                              );
                              entityliving.H.getCraftServer().getPluginManager().callEvent(event);
                              if (event.isCancelled()) {
                                 return false;
                              }
      
                              blockdoor1.a(entityliving, worldserver, iblockdata1, blockposition1, true);
                              optional = a(memoryaccessor1, optional, worldserver, blockposition1);
                           }
                        }
      
                        optional.ifPresent(set -> a(worldserver, entityliving, pathpoint, pathpoint1, set, behaviorbuilder_b.a(memoryaccessor2)));
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }

   public static void a(
      WorldServer worldserver,
      EntityLiving entityliving,
      @Nullable PathPoint pathpoint,
      @Nullable PathPoint pathpoint1,
      Set<GlobalPos> set,
      Optional<List<EntityLiving>> optional
   ) {
      Iterator iterator = set.iterator();

      while(iterator.hasNext()) {
         GlobalPos globalpos = (GlobalPos)iterator.next();
         BlockPosition blockposition = globalpos.b();
         if ((pathpoint == null || !pathpoint.a().equals(blockposition)) && (pathpoint1 == null || !pathpoint1.a().equals(blockposition))) {
            if (a(worldserver, entityliving, globalpos)) {
               iterator.remove();
            } else {
               IBlockData iblockdata = worldserver.a_(blockposition);
               if (!iblockdata.a(TagsBlock.g, blockbase_blockdata -> blockbase_blockdata.b() instanceof BlockDoor)) {
                  iterator.remove();
               } else {
                  BlockDoor blockdoor = (BlockDoor)iblockdata.b();
                  if (!blockdoor.h(iblockdata)) {
                     iterator.remove();
                  } else if (a(entityliving, blockposition, optional)) {
                     iterator.remove();
                  } else {
                     blockdoor.a(entityliving, worldserver, iblockdata, blockposition, false);
                     iterator.remove();
                  }
               }
            }
         }
      }
   }

   private static boolean a(EntityLiving entityliving, BlockPosition blockposition, Optional<List<EntityLiving>> optional) {
      return optional.isEmpty()
         ? false
         : optional.get()
            .stream()
            .filter(entityliving1 -> entityliving1.ae() == entityliving.ae())
            .filter(entityliving1 -> blockposition.a(entityliving1.de(), 2.0))
            .anyMatch(entityliving1 -> a(entityliving1.dH(), blockposition));
   }

   private static boolean a(BehaviorController<?> behaviorcontroller, BlockPosition blockposition) {
      if (!behaviorcontroller.a(MemoryModuleType.t)) {
         return false;
      } else {
         PathEntity pathentity = behaviorcontroller.c(MemoryModuleType.t).get();
         if (pathentity.c()) {
            return false;
         } else {
            PathPoint pathpoint = pathentity.i();
            if (pathpoint == null) {
               return false;
            } else {
               PathPoint pathpoint1 = pathentity.h();
               return blockposition.equals(pathpoint.a()) || blockposition.equals(pathpoint1.a());
            }
         }
      }
   }

   private static boolean a(WorldServer worldserver, EntityLiving entityliving, GlobalPos globalpos) {
      return globalpos.a() != worldserver.ab() || !globalpos.b().a(entityliving.de(), 3.0);
   }

   private static Optional<Set<GlobalPos>> a(
      MemoryAccessor<Mu, Set<GlobalPos>> memoryaccessor, Optional<Set<GlobalPos>> optional, WorldServer worldserver, BlockPosition blockposition
   ) {
      GlobalPos globalpos = GlobalPos.a(worldserver.ab(), blockposition);
      return Optional.of(optional.map((Function<? super Set<GlobalPos>, ? extends Set<GlobalPos>>)(set -> {
         set.add(globalpos);
         return set;
      })).orElseGet(() -> {
         Set<GlobalPos> set = Sets.newHashSet(new GlobalPos[]{globalpos});
         memoryaccessor.a(set);
         return set;
      }));
   }
}
