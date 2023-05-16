package net.minecraft.world.entity;

public interface IJumpable extends PlayerRideable {
   void b(int var1);

   boolean a();

   void c(int var1);

   void b();

   default int V_() {
      return 0;
   }
}
