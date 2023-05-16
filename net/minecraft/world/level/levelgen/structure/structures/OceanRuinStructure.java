package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.INamable;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class OceanRuinStructure extends Structure {
   public static final Codec<OceanRuinStructure> d = RecordCodecBuilder.create(
      var0 -> var0.group(
               a(var0),
               OceanRuinStructure.a.c.fieldOf("biome_temp").forGetter(var0x -> var0x.e),
               Codec.floatRange(0.0F, 1.0F).fieldOf("large_probability").forGetter(var0x -> var0x.f),
               Codec.floatRange(0.0F, 1.0F).fieldOf("cluster_probability").forGetter(var0x -> var0x.g)
            )
            .apply(var0, OceanRuinStructure::new)
   );
   public final OceanRuinStructure.a e;
   public final float f;
   public final float g;

   public OceanRuinStructure(Structure.c var0, OceanRuinStructure.a var1, float var2, float var3) {
      super(var0);
      this.e = var1;
      this.f = var2;
      this.g = var3;
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      return a(var0, HeightMap.Type.c, var1x -> this.a(var1x, var0));
   }

   private void a(StructurePiecesBuilder var0, Structure.a var1) {
      BlockPosition var2 = new BlockPosition(var1.h().d(), 90, var1.h().e());
      EnumBlockRotation var3 = EnumBlockRotation.a(var1.f());
      OceanRuinPieces.a(var1.e(), var2, var3, var0, var1.f(), this);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.k;
   }

   public static enum a implements INamable {
      a("warm"),
      b("cold");

      public static final Codec<OceanRuinStructure.a> c = INamable.a(OceanRuinStructure.a::values);
      private final String d;

      private a(String var2) {
         this.d = var2;
      }

      public String a() {
         return this.d;
      }

      @Override
      public String c() {
         return this.d;
      }
   }
}
