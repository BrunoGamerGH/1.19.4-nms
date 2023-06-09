package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public interface StructureAccess {
   @Nullable
   StructureStart a(Structure var1);

   void a(Structure var1, StructureStart var2);

   LongSet b(Structure var1);

   void a(Structure var1, long var2);

   Map<Structure, LongSet> h();

   void b(Map<Structure, LongSet> var1);
}
