package net.minecraft.world.level.gameevent;

import net.minecraft.world.phys.Vec3D;

public interface GameEventListenerRegistry {
   GameEventListenerRegistry a = new GameEventListenerRegistry() {
      @Override
      public boolean a() {
         return true;
      }

      @Override
      public void a(GameEventListener var0) {
      }

      @Override
      public void b(GameEventListener var0) {
      }

      @Override
      public boolean a(GameEvent var0, Vec3D var1, GameEvent.a var2, GameEventListenerRegistry.a var3) {
         return false;
      }
   };

   boolean a();

   void a(GameEventListener var1);

   void b(GameEventListener var1);

   boolean a(GameEvent var1, Vec3D var2, GameEvent.a var3, GameEventListenerRegistry.a var4);

   @FunctionalInterface
   public interface a {
      void visit(GameEventListener var1, Vec3D var2);
   }
}
