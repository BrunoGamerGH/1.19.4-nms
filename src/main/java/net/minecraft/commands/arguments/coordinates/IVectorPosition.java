package net.minecraft.commands.arguments.coordinates;

import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;

public interface IVectorPosition {
   Vec3D a(CommandListenerWrapper var1);

   Vec2F b(CommandListenerWrapper var1);

   default BlockPosition c(CommandListenerWrapper var0) {
      return BlockPosition.a(this.a(var0));
   }

   boolean a();

   boolean b();

   boolean c();
}
