package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.EntityVillager;

public class BehaviorWork extends Behavior<EntityVillager> {
   private static final int c = 300;
   private static final double d = 1.73;
   private long e;

   public BehaviorWork() {
      super(ImmutableMap.of(MemoryModuleType.c, MemoryStatus.a, MemoryModuleType.n, MemoryStatus.c));
   }

   protected boolean b(WorldServer var0, EntityVillager var1) {
      if (var0.U() - this.e < 300L) {
         return false;
      } else if (var0.z.a(2) != 0) {
         return false;
      } else {
         this.e = var0.U();
         GlobalPos var2 = var1.dH().c(MemoryModuleType.c).get();
         return var2.a() == var0.ab() && var2.b().a(var1.de(), 1.73);
      }
   }

   protected void a(WorldServer var0, EntityVillager var1, long var2) {
      BehaviorController<EntityVillager> var4 = var1.dH();
      var4.a(MemoryModuleType.I, var2);
      var4.c(MemoryModuleType.c).ifPresent(var1x -> var4.a(MemoryModuleType.n, new BehaviorTarget(var1x.b())));
      var1.gh();
      this.a(var0, var1);
      if (var1.gg()) {
         var1.gf();
      }
   }

   protected void a(WorldServer var0, EntityVillager var1) {
   }

   protected boolean b(WorldServer var0, EntityVillager var1, long var2) {
      Optional<GlobalPos> var4 = var1.dH().c(MemoryModuleType.c);
      if (!var4.isPresent()) {
         return false;
      } else {
         GlobalPos var5 = var4.get();
         return var5.a() == var0.ab() && var5.b().a(var1.de(), 1.73);
      }
   }
}
