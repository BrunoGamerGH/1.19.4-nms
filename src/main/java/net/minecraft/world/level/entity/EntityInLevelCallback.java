package net.minecraft.world.level.entity;

import net.minecraft.world.entity.Entity;

public interface EntityInLevelCallback {
   EntityInLevelCallback a = new EntityInLevelCallback() {
      @Override
      public void a() {
      }

      @Override
      public void a(Entity.RemovalReason var0) {
      }
   };

   void a();

   void a(Entity.RemovalReason var1);
}
