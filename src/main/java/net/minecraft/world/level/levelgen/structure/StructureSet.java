package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public record StructureSet(List<StructureSet.a> structures, StructurePlacement placement) {
   private final List<StructureSet.a> c;
   private final StructurePlacement d;
   public static final Codec<StructureSet> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               StructureSet.a.a.listOf().fieldOf("structures").forGetter(StructureSet::a),
               StructurePlacement.b.fieldOf("placement").forGetter(StructureSet::b)
            )
            .apply(var0, StructureSet::new)
   );
   public static final Codec<Holder<StructureSet>> b = RegistryFileCodec.a(Registries.az, a);

   public StructureSet(Holder<Structure> var0, StructurePlacement var1) {
      this(List.of(new StructureSet.a(var0, 1)), var1);
   }

   public StructureSet(List<StructureSet.a> var0, StructurePlacement var1) {
      this.c = var0;
      this.d = var1;
   }

   public static StructureSet.a a(Holder<Structure> var0, int var1) {
      return new StructureSet.a(var0, var1);
   }

   public static StructureSet.a a(Holder<Structure> var0) {
      return new StructureSet.a(var0, 1);
   }

   public List<StructureSet.a> a() {
      return this.c;
   }

   public StructurePlacement b() {
      return this.d;
   }

   public static record a(Holder<Structure> structure, int weight) {
      private final Holder<Structure> b;
      private final int c;
      public static final Codec<StructureSet.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(Structure.b.fieldOf("structure").forGetter(StructureSet.a::a), ExtraCodecs.i.fieldOf("weight").forGetter(StructureSet.a::b))
               .apply(var0, StructureSet.a::new)
      );

      public a(Holder<Structure> var0, int var1) {
         this.b = var0;
         this.c = var1;
      }

      public Holder<Structure> a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }
   }
}
