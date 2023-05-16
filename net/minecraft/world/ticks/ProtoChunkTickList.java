package net.minecraft.world.ticks;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.level.ChunkCoordIntPair;

public class ProtoChunkTickList<T> implements SerializableTickContainer<T>, TickContainerAccess<T> {
   private final List<TickListChunk<T>> a = Lists.newArrayList();
   private final Set<TickListChunk<?>> b = new ObjectOpenCustomHashSet(TickListChunk.a);

   @Override
   public void a(NextTickListEntry<T> var0) {
      TickListChunk<T> var1 = new TickListChunk<>(var0.a(), var0.b(), 0, var0.d());
      this.a(var1);
   }

   private void a(TickListChunk<T> var0) {
      if (this.b.add(var0)) {
         this.a.add(var0);
      }
   }

   @Override
   public boolean a(BlockPosition var0, T var1) {
      return this.b.contains(TickListChunk.a(var1, var0));
   }

   @Override
   public int a() {
      return this.a.size();
   }

   @Override
   public NBTBase b(long var0, Function<T, String> var2) {
      NBTTagList var3 = new NBTTagList();

      for(TickListChunk<T> var5 : this.a) {
         var3.add(var5.a(var2));
      }

      return var3;
   }

   public List<TickListChunk<T>> b() {
      return List.copyOf(this.a);
   }

   public static <T> ProtoChunkTickList<T> a(NBTTagList var0, Function<String, Optional<T>> var1, ChunkCoordIntPair var2) {
      ProtoChunkTickList<T> var3 = new ProtoChunkTickList<>();
      TickListChunk.a(var0, var1, var2, var3::a);
      return var3;
   }
}
