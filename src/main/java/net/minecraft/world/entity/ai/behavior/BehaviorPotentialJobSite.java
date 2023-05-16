package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.schedule.Activity;

public class BehaviorPotentialJobSite extends Behavior<EntityVillager> {
   private static final int d = 1200;
   final float c;

   public BehaviorPotentialJobSite(float var0) {
      super(ImmutableMap.of(MemoryModuleType.d, MemoryStatus.a), 1200);
      this.c = var0;
   }

   protected boolean a(WorldServer var0, EntityVillager var1) {
      return var1.dH().g().map(var0x -> var0x == Activity.b || var0x == Activity.c || var0x == Activity.d).orElse(true);
   }

   protected boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return var1.dH().a(MemoryModuleType.d);
   }

   protected void b(WorldServer var0, EntityVillager var1, long var2) {
      BehaviorUtil.a(var1, var1.dH().c(MemoryModuleType.d).get().b(), this.c, 1);
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      Optional<GlobalPos> var4 = var1.dH().c(MemoryModuleType.d);
      var4.ifPresent(var1x -> {
         BlockPosition var2x = var1x.b();
         WorldServer var3x = var0.n().a(var1x.a());
         if (var3x != null) {
            VillagePlace var4x = var3x.w();
            if (var4x.a(var2x, var0xx -> true)) {
               var4x.b(var2x);
            }

            PacketDebug.c(var0, var2x);
         }
      });
      var1.dH().b(MemoryModuleType.d);
   }
}
