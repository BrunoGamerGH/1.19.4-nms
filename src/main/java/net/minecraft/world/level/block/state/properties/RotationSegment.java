package net.minecraft.world.level.block.state.properties;

import java.util.Optional;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.SegmentedAnglePrecision;

public class RotationSegment {
   private static final SegmentedAnglePrecision a = new SegmentedAnglePrecision(4);
   private static final int b = a.a();
   private static final int c = 0;
   private static final int d = 4;
   private static final int e = 8;
   private static final int f = 12;

   public static int a() {
      return b;
   }

   public static int a(EnumDirection var0) {
      return a.a(var0);
   }

   public static int a(float var0) {
      return a.b(var0);
   }

   public static Optional<EnumDirection> a(int var0) {
      EnumDirection var1 = switch(var0) {
         case 0 -> EnumDirection.c;
         case 4 -> EnumDirection.f;
         case 8 -> EnumDirection.d;
         case 12 -> EnumDirection.e;
         default -> null;
      };
      return Optional.ofNullable(var1);
   }

   public static float b(int var0) {
      return a.b(var0);
   }
}
