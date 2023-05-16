package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3D;

public class VibrationParticleOption implements ParticleParam {
   public static final Codec<VibrationParticleOption> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               PositionSource.b.fieldOf("destination").forGetter(var0x -> var0x.c), Codec.INT.fieldOf("arrival_in_ticks").forGetter(var0x -> var0x.d)
            )
            .apply(var0, VibrationParticleOption::new)
   );
   public static final ParticleParam.a<VibrationParticleOption> b = new ParticleParam.a<VibrationParticleOption>() {
      public VibrationParticleOption a(Particle<VibrationParticleOption> var0, StringReader var1) throws CommandSyntaxException {
         var1.expect(' ');
         float var2 = (float)var1.readDouble();
         var1.expect(' ');
         float var3 = (float)var1.readDouble();
         var1.expect(' ');
         float var4 = (float)var1.readDouble();
         var1.expect(' ');
         int var5 = var1.readInt();
         BlockPosition var6 = BlockPosition.a((double)var2, (double)var3, (double)var4);
         return new VibrationParticleOption(new BlockPositionSource(var6), var5);
      }

      public VibrationParticleOption a(Particle<VibrationParticleOption> var0, PacketDataSerializer var1) {
         PositionSource var2 = PositionSourceType.c(var1);
         int var3 = var1.m();
         return new VibrationParticleOption(var2, var3);
      }
   };
   private final PositionSource c;
   private final int d;

   public VibrationParticleOption(PositionSource var0, int var1) {
      this.c = var0;
      this.d = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      PositionSourceType.a(this.c, var0);
      var0.d(this.d);
   }

   @Override
   public String a() {
      Vec3D var0 = this.c.a(null).get();
      double var1 = var0.a();
      double var3 = var0.b();
      double var5 = var0.c();
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %d", BuiltInRegistries.k.b(this.b()), var1, var3, var5, this.d);
   }

   @Override
   public Particle<VibrationParticleOption> b() {
      return Particles.R;
   }

   public PositionSource c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }
}
