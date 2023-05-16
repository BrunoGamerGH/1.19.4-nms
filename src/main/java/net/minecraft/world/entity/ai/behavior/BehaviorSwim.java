package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityInsentient;

public class BehaviorSwim extends Behavior<EntityInsentient> {
   private final float c;

   public BehaviorSwim(float var0) {
      super(ImmutableMap.of());
      this.c = var0;
   }

   protected boolean a(WorldServer var0, EntityInsentient var1) {
      return var1.aT() && var1.b(TagsFluid.a) > var1.db() || var1.bg();
   }

   protected boolean a(WorldServer var0, EntityInsentient var1, long var2) {
      return this.a(var0, var1);
   }

   protected void b(WorldServer var0, EntityInsentient var1, long var2) {
      if (var1.dZ().i() < this.c) {
         var1.E().a();
      }
   }
}
