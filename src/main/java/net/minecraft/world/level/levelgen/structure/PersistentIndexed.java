package net.minecraft.world.level.levelgen.structure;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.saveddata.PersistentBase;

public class PersistentIndexed extends PersistentBase {
   private static final String a = "Remaining";
   private static final String b = "All";
   private final LongSet c;
   private final LongSet d;

   private PersistentIndexed(LongSet var0, LongSet var1) {
      this.c = var0;
      this.d = var1;
   }

   public PersistentIndexed() {
      this(new LongOpenHashSet(), new LongOpenHashSet());
   }

   public static PersistentIndexed b(NBTTagCompound var0) {
      return new PersistentIndexed(new LongOpenHashSet(var0.o("All")), new LongOpenHashSet(var0.o("Remaining")));
   }

   @Override
   public NBTTagCompound a(NBTTagCompound var0) {
      var0.a("All", this.c.toLongArray());
      var0.a("Remaining", this.d.toLongArray());
      return var0;
   }

   public void a(long var0) {
      this.c.add(var0);
      this.d.add(var0);
   }

   public boolean b(long var0) {
      return this.c.contains(var0);
   }

   public boolean c(long var0) {
      return this.d.contains(var0);
   }

   public void d(long var0) {
      this.d.remove(var0);
   }

   public LongSet a() {
      return this.c;
   }
}
