package net.minecraft.world.entity.npc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;

public class VillagerData {
   public static final int a = 1;
   public static final int b = 5;
   private static final int[] d = new int[]{0, 10, 70, 150, 250};
   public static final Codec<VillagerData> c = RecordCodecBuilder.create(
      var0 -> var0.group(
               BuiltInRegistries.y.q().fieldOf("type").orElseGet(() -> VillagerType.c).forGetter(var0x -> var0x.e),
               BuiltInRegistries.z.q().fieldOf("profession").orElseGet(() -> VillagerProfession.b).forGetter(var0x -> var0x.f),
               Codec.INT.fieldOf("level").orElse(1).forGetter(var0x -> var0x.g)
            )
            .apply(var0, VillagerData::new)
   );
   private final VillagerType e;
   private final VillagerProfession f;
   private final int g;

   public VillagerData(VillagerType var0, VillagerProfession var1, int var2) {
      this.e = var0;
      this.f = var1;
      this.g = Math.max(1, var2);
   }

   public VillagerType a() {
      return this.e;
   }

   public VillagerProfession b() {
      return this.f;
   }

   public int c() {
      return this.g;
   }

   public VillagerData a(VillagerType var0) {
      return new VillagerData(var0, this.f, this.g);
   }

   public VillagerData a(VillagerProfession var0) {
      return new VillagerData(this.e, var0, this.g);
   }

   public VillagerData a(int var0) {
      return new VillagerData(this.e, this.f, var0);
   }

   public static int b(int var0) {
      return d(var0) ? d[var0 - 1] : 0;
   }

   public static int c(int var0) {
      return d(var0) ? d[var0] : 0;
   }

   public static boolean d(int var0) {
      return var0 >= 1 && var0 < 5;
   }
}
