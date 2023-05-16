package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureProcessorPredicates {
   public static final Codec<DefinedStructureProcessorPredicates> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               DefinedStructureRuleTest.c.fieldOf("input_predicate").forGetter(var0x -> var0x.b),
               DefinedStructureRuleTest.c.fieldOf("location_predicate").forGetter(var0x -> var0x.c),
               PosRuleTest.c.optionalFieldOf("position_predicate", PosRuleTestTrue.b).forGetter(var0x -> var0x.d),
               IBlockData.b.fieldOf("output_state").forGetter(var0x -> var0x.e),
               NBTTagCompound.a.optionalFieldOf("output_nbt").forGetter(var0x -> Optional.ofNullable(var0x.f))
            )
            .apply(var0, DefinedStructureProcessorPredicates::new)
   );
   private final DefinedStructureRuleTest b;
   private final DefinedStructureRuleTest c;
   private final PosRuleTest d;
   private final IBlockData e;
   @Nullable
   private final NBTTagCompound f;

   public DefinedStructureProcessorPredicates(DefinedStructureRuleTest var0, DefinedStructureRuleTest var1, IBlockData var2) {
      this(var0, var1, PosRuleTestTrue.b, var2, Optional.empty());
   }

   public DefinedStructureProcessorPredicates(DefinedStructureRuleTest var0, DefinedStructureRuleTest var1, PosRuleTest var2, IBlockData var3) {
      this(var0, var1, var2, var3, Optional.empty());
   }

   public DefinedStructureProcessorPredicates(
      DefinedStructureRuleTest var0, DefinedStructureRuleTest var1, PosRuleTest var2, IBlockData var3, Optional<NBTTagCompound> var4
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4.orElse(null);
   }

   public boolean a(IBlockData var0, IBlockData var1, BlockPosition var2, BlockPosition var3, BlockPosition var4, RandomSource var5) {
      return this.b.a(var0, var5) && this.c.a(var1, var5) && this.d.a(var2, var3, var4, var5);
   }

   public IBlockData a() {
      return this.e;
   }

   @Nullable
   public NBTTagCompound b() {
      return this.f;
   }
}
