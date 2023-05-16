package net.minecraft.world.level.gameevent;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.phys.Vec3D;

public interface GameEventListener {
   PositionSource a();

   int b();

   boolean a(WorldServer var1, GameEvent var2, GameEvent.a var3, Vec3D var4);

   default GameEventListener.a c() {
      return GameEventListener.a.a;
   }

   public static enum a {
      a,
      b;
   }
}
