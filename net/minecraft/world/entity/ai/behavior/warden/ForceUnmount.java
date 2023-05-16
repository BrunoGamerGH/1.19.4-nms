package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.Behavior;

public class ForceUnmount extends Behavior<EntityLiving> {
   public ForceUnmount() {
      super(ImmutableMap.of());
   }

   @Override
   protected boolean a(WorldServer var0, EntityLiving var1) {
      return var1.bL();
   }

   @Override
   protected void d(WorldServer var0, EntityLiving var1, long var2) {
      var1.ac();
   }
}
