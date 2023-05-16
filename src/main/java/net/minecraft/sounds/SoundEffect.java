package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryFileCodec;

public class SoundEffect {
   public static final Codec<SoundEffect> a = RecordCodecBuilder.create(
      var0 -> var0.group(MinecraftKey.a.fieldOf("sound_id").forGetter(SoundEffect::a), Codec.FLOAT.optionalFieldOf("range").forGetter(SoundEffect::b))
            .apply(var0, SoundEffect::a)
   );
   public static final Codec<Holder<SoundEffect>> b = RegistryFileCodec.a(Registries.ab, a);
   private static final float c = 16.0F;
   private final MinecraftKey d;
   private final float e;
   private final boolean f;

   private static SoundEffect a(MinecraftKey var0, Optional<Float> var1) {
      return var1.<SoundEffect>map(var1x -> a(var0, var1x.floatValue())).orElseGet(() -> a(var0));
   }

   public static SoundEffect a(MinecraftKey var0) {
      return new SoundEffect(var0, 16.0F, false);
   }

   public static SoundEffect a(MinecraftKey var0, float var1) {
      return new SoundEffect(var0, var1, true);
   }

   private SoundEffect(MinecraftKey var0, float var1, boolean var2) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
   }

   public MinecraftKey a() {
      return this.d;
   }

   public float a(float var0) {
      if (this.f) {
         return this.e;
      } else {
         return var0 > 1.0F ? 16.0F * var0 : 16.0F;
      }
   }

   private Optional<Float> b() {
      return this.f ? Optional.of(this.e) : Optional.empty();
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this.d);
      var0.a(this.b(), PacketDataSerializer::writeFloat);
   }

   public static SoundEffect b(PacketDataSerializer var0) {
      MinecraftKey var1 = var0.t();
      Optional<Float> var2 = var0.b(PacketDataSerializer::readFloat);
      return a(var1, var2);
   }
}
