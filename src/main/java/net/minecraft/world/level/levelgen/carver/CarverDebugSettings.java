package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class CarverDebugSettings {
   public static final CarverDebugSettings a = new CarverDebugSettings(false, Blocks.gy.o(), Blocks.pH.o(), Blocks.ej.o(), Blocks.aP.o());
   public static final Codec<CarverDebugSettings> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.BOOL.optionalFieldOf("debug_mode", false).forGetter(CarverDebugSettings::a),
               IBlockData.b.optionalFieldOf("air_state", a.b()).forGetter(CarverDebugSettings::b),
               IBlockData.b.optionalFieldOf("water_state", a.b()).forGetter(CarverDebugSettings::c),
               IBlockData.b.optionalFieldOf("lava_state", a.b()).forGetter(CarverDebugSettings::d),
               IBlockData.b.optionalFieldOf("barrier_state", a.b()).forGetter(CarverDebugSettings::e)
            )
            .apply(var0, CarverDebugSettings::new)
   );
   private final boolean c;
   private final IBlockData d;
   private final IBlockData e;
   private final IBlockData f;
   private final IBlockData g;

   public static CarverDebugSettings a(boolean var0, IBlockData var1, IBlockData var2, IBlockData var3, IBlockData var4) {
      return new CarverDebugSettings(var0, var1, var2, var3, var4);
   }

   public static CarverDebugSettings a(IBlockData var0, IBlockData var1, IBlockData var2, IBlockData var3) {
      return new CarverDebugSettings(false, var0, var1, var2, var3);
   }

   public static CarverDebugSettings a(boolean var0, IBlockData var1) {
      return new CarverDebugSettings(var0, var1, a.c(), a.d(), a.e());
   }

   private CarverDebugSettings(boolean var0, IBlockData var1, IBlockData var2, IBlockData var3, IBlockData var4) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
   }

   public boolean a() {
      return this.c;
   }

   public IBlockData b() {
      return this.d;
   }

   public IBlockData c() {
      return this.e;
   }

   public IBlockData d() {
      return this.f;
   }

   public IBlockData e() {
      return this.g;
   }
}
