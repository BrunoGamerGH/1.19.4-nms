package net.minecraft.world.level;

import java.util.function.Predicate;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class ClipBlockStateContext {
   private final Vec3D a;
   private final Vec3D b;
   private final Predicate<IBlockData> c;

   public ClipBlockStateContext(Vec3D var0, Vec3D var1, Predicate<IBlockData> var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public Vec3D a() {
      return this.b;
   }

   public Vec3D b() {
      return this.a;
   }

   public Predicate<IBlockData> c() {
      return this.c;
   }
}
