package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.thread.ThreadedMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import org.slf4j.Logger;

public class EntityStorage implements EntityPersistentStorage<Entity> {
   private static final Logger b = LogUtils.getLogger();
   private static final String c = "Entities";
   private static final String d = "Position";
   public final WorldServer e;
   private final IOWorker f;
   private final LongSet g = new LongOpenHashSet();
   public final ThreadedMailbox<Runnable> h;
   protected final DataFixer a;

   public EntityStorage(WorldServer var0, Path var1, DataFixer var2, boolean var3, Executor var4) {
      this.e = var0;
      this.a = var2;
      this.h = ThreadedMailbox.a(var4, "entity-deserializer");
      this.f = new IOWorker(var1, var3, "entities");
   }

   @Override
   public CompletableFuture<ChunkEntities<Entity>> a(ChunkCoordIntPair var0) {
      return this.g.contains(var0.a()) ? CompletableFuture.completedFuture(b(var0)) : this.f.a(var0).thenApplyAsync(var1x -> {
         if (var1x.isEmpty()) {
            this.g.add(var0.a());
            return b(var0);
         } else {
            try {
               ChunkCoordIntPair var2 = a(var1x.get());
               if (!Objects.equals(var0, var2)) {
                  b.error("Chunk file at {} is in the wrong location. (Expected {}, got {})", new Object[]{var0, var0, var2});
               }
            } catch (Exception var6) {
               b.warn("Failed to parse chunk {} position info", var0, var6);
            }

            NBTTagCompound var2 = this.b(var1x.get());
            NBTTagList var3 = var2.c("Entities", 10);
            List<Entity> var4 = EntityTypes.a(var3, this.e).collect(ImmutableList.toImmutableList());
            return new ChunkEntities<>(var0, var4);
         }
      }, this.h::a);
   }

   private static ChunkCoordIntPair a(NBTTagCompound var0) {
      int[] var1 = var0.n("Position");
      return new ChunkCoordIntPair(var1[0], var1[1]);
   }

   private static void a(NBTTagCompound var0, ChunkCoordIntPair var1) {
      var0.a("Position", new NBTTagIntArray(new int[]{var1.e, var1.f}));
   }

   private static ChunkEntities<Entity> b(ChunkCoordIntPair var0) {
      return new ChunkEntities<>(var0, ImmutableList.of());
   }

   @Override
   public void a(ChunkEntities<Entity> var0) {
      ChunkCoordIntPair var1 = var0.a();
      if (var0.c()) {
         if (this.g.add(var1.a())) {
            this.f.a(var1, null);
         }
      } else {
         NBTTagList var2 = new NBTTagList();
         var0.b().forEach(var1x -> {
            NBTTagCompound var2x = new NBTTagCompound();
            if (var1x.e(var2x)) {
               var2.add(var2x);
            }
         });
         NBTTagCompound var3 = GameProfileSerializer.g(new NBTTagCompound());
         var3.a("Entities", var2);
         a(var3, var1);
         this.f.a(var1, var3).exceptionally(var1x -> {
            b.error("Failed to store chunk {}", var1, var1x);
            return null;
         });
         this.g.remove(var1.a());
      }
   }

   @Override
   public void a(boolean var0) {
      this.f.a(var0).join();
      this.h.a();
   }

   private NBTTagCompound b(NBTTagCompound var0) {
      int var1 = GameProfileSerializer.b(var0, -1);
      return DataFixTypes.l.a(this.a, var0, var1);
   }

   @Override
   public void close() throws IOException {
      this.f.close();
   }
}
