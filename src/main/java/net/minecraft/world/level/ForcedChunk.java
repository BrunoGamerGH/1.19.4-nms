package net.minecraft.world.level;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.saveddata.PersistentBase;

public class ForcedChunk extends PersistentBase {
   public static final String a = "chunks";
   private static final String b = "Forced";
   private final LongSet c;

   private ForcedChunk(LongSet var0) {
      this.c = var0;
   }

   public ForcedChunk() {
      this(new LongOpenHashSet());
   }

   public static ForcedChunk b(NBTTagCompound var0) {
      return new ForcedChunk(new LongOpenHashSet(var0.o("Forced")));
   }

   @Override
   public NBTTagCompound a(NBTTagCompound var0) {
      var0.a("Forced", this.c.toLongArray());
      return var0;
   }

   public LongSet a() {
      return this.c;
   }
}
