package net.minecraft.world.level.material;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FluidTypes {
   public static final FluidType a = a("empty", new FluidTypeEmpty());
   public static final FluidTypeFlowing b = a("flowing_water", new FluidTypeWater.a());
   public static final FluidTypeFlowing c = a("water", new FluidTypeWater.b());
   public static final FluidTypeFlowing d = a("flowing_lava", new FluidTypeLava.a());
   public static final FluidTypeFlowing e = a("lava", new FluidTypeLava.b());

   private static <T extends FluidType> T a(String var0, T var1) {
      return IRegistry.a(BuiltInRegistries.d, var0, var1);
   }

   static {
      for(FluidType var1 : BuiltInRegistries.d) {
         UnmodifiableIterator var2 = var1.f().a().iterator();

         while(var2.hasNext()) {
            Fluid var3 = (Fluid)var2.next();
            FluidType.c.b(var3);
         }
      }
   }
}
