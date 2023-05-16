package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.state.IBlockData;

public class BehaviorSleep extends Behavior<EntityLiving> {
   public static final int c = 100;
   private long d;

   public BehaviorSleep() {
      super(ImmutableMap.of(MemoryModuleType.b, MemoryStatus.a, MemoryModuleType.H, MemoryStatus.c));
   }

   @Override
   protected boolean a(WorldServer var0, EntityLiving var1) {
      if (var1.bL()) {
         return false;
      } else {
         BehaviorController<?> var2 = var1.dH();
         GlobalPos var3 = var2.c(MemoryModuleType.b).get();
         if (var0.ab() != var3.a()) {
            return false;
         } else {
            Optional<Long> var4 = var2.c(MemoryModuleType.H);
            if (var4.isPresent()) {
               long var5 = var0.U() - var4.get();
               if (var5 > 0L && var5 < 100L) {
                  return false;
               }
            }

            IBlockData var5 = var0.a_(var3.b());
            return var3.b().a(var1.de(), 2.0) && var5.a(TagsBlock.Q) && !var5.c(BlockBed.b);
         }
      }
   }

   @Override
   protected boolean a(WorldServer var0, EntityLiving var1, long var2) {
      Optional<GlobalPos> var4 = var1.dH().c(MemoryModuleType.b);
      if (!var4.isPresent()) {
         return false;
      } else {
         BlockPosition var5 = var4.get().b();
         return var1.dH().c(Activity.e) && var1.dn() > (double)var5.v() + 0.4 && var5.a(var1.de(), 1.14);
      }
   }

   @Override
   protected void d(WorldServer var0, EntityLiving var1, long var2) {
      if (var2 > this.d) {
         BehaviorController<?> var4 = var1.dH();
         if (var4.a(MemoryModuleType.v)) {
            Set<GlobalPos> var5 = var4.c(MemoryModuleType.v).get();
            Optional<List<EntityLiving>> var6;
            if (var4.a(MemoryModuleType.g)) {
               var6 = var4.c(MemoryModuleType.g);
            } else {
               var6 = Optional.empty();
            }

            BehaviorInteractDoor.a(var0, var1, null, null, var5, var6);
         }

         var1.b(var1.dH().c(MemoryModuleType.b).get().b());
      }
   }

   @Override
   protected boolean a(long var0) {
      return false;
   }

   @Override
   protected void b(WorldServer var0, EntityLiving var1, long var2) {
      if (var1.fu()) {
         var1.fv();
         this.d = var2 + 40L;
      }
   }
}
