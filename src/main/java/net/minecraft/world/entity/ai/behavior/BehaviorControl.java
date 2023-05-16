package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;

public interface BehaviorControl<E extends EntityLiving> {
   Behavior.Status a();

   boolean e(WorldServer var1, E var2, long var3);

   void f(WorldServer var1, E var2, long var3);

   void g(WorldServer var1, E var2, long var3);

   String b();
}
