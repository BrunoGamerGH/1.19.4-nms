package net.minecraft.world.level.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.util.CSVWriter;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.storage.EntityStorage;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.slf4j.Logger;

public class PersistentEntitySectionManager<T extends EntityAccess> implements AutoCloseable {
   static final Logger a = LogUtils.getLogger();
   final Set<UUID> b = Sets.newHashSet();
   final LevelCallback<T> c;
   public final EntityPersistentStorage<T> d;
   private final EntityLookup<T> e = new EntityLookup<>();
   final EntitySectionStorage<T> f;
   private final LevelEntityGetter<T> g;
   private final Long2ObjectMap<Visibility> h = new Long2ObjectOpenHashMap();
   private final Long2ObjectMap<PersistentEntitySectionManager.b> i = new Long2ObjectOpenHashMap();
   private final LongSet j = new LongOpenHashSet();
   private final Queue<ChunkEntities<T>> k = Queues.newConcurrentLinkedQueue();

   public PersistentEntitySectionManager(Class<T> oclass, LevelCallback<T> levelcallback, EntityPersistentStorage<T> entitypersistentstorage) {
      this.f = new EntitySectionStorage<>(oclass, this.h);
      this.h.defaultReturnValue(Visibility.a);
      this.i.defaultReturnValue(PersistentEntitySectionManager.b.a);
      this.c = levelcallback;
      this.d = entitypersistentstorage;
      this.g = new LevelEntityGetterAdapter<>(this.e, this.f);
   }

   public List<Entity> getEntities(ChunkCoordIntPair chunkCoordIntPair) {
      return this.f.b(chunkCoordIntPair.a()).flatMap(EntitySection::b).map(entity -> (Entity)entity).collect(Collectors.toList());
   }

   public boolean isPending(long pair) {
      return this.i.get(pair) == PersistentEntitySectionManager.b.b;
   }

   void a(long i, EntitySection<T> entitysection) {
      if (entitysection.a()) {
         this.f.e(i);
      }
   }

   private boolean b(T t0) {
      if (!this.b.add(t0.cs())) {
         a.warn("UUID of added entity already exists: {}", t0);
         return false;
      } else {
         return true;
      }
   }

   public boolean a(T t0) {
      return this.a(t0, false);
   }

   private boolean a(T t0, boolean flag) {
      if (!this.b(t0)) {
         return false;
      } else {
         long i = SectionPosition.c(t0.dg());
         EntitySection<T> entitysection = this.f.c(i);
         entitysection.a(t0);
         t0.a(new PersistentEntitySectionManager.a(t0, i, entitysection));
         if (!flag) {
            this.c.g(t0);
         }

         Visibility visibility = a(t0, entitysection.c());
         if (visibility.b()) {
            this.e(t0);
         }

         if (visibility.a()) {
            this.c(t0);
         }

         return true;
      }
   }

   static <T extends EntityAccess> Visibility a(T t0, Visibility visibility) {
      return t0.dF() ? Visibility.c : visibility;
   }

   public void a(Stream<T> stream) {
      stream.forEach(entityaccess -> this.a(entityaccess, true));
   }

   public void b(Stream<T> stream) {
      stream.forEach(entityaccess -> this.a(entityaccess, false));
   }

   void c(T t0) {
      this.c.e(t0);
   }

   void d(T t0) {
      this.c.d(t0);
   }

   void e(T t0) {
      this.e.a(t0);
      this.c.c(t0);
   }

   void f(T t0) {
      this.c.b(t0);
      this.e.b(t0);
   }

   public void a(ChunkCoordIntPair chunkcoordintpair, PlayerChunk.State playerchunk_state) {
      Visibility visibility = Visibility.a(playerchunk_state);
      this.a(chunkcoordintpair, visibility);
   }

   public void a(ChunkCoordIntPair chunkcoordintpair, Visibility visibility) {
      long i = chunkcoordintpair.a();
      if (visibility == Visibility.a) {
         this.h.remove(i);
         this.j.add(i);
      } else {
         this.h.put(i, visibility);
         this.j.remove(i);
         this.b(i);
      }

      this.f.b(i).forEach(entitysection -> {
         Visibility visibility1 = entitysection.a(visibility);
         boolean flag = visibility1.b();
         boolean flag1 = visibility.b();
         boolean flag2 = visibility1.a();
         boolean flag3 = visibility.a();
         if (flag2 && !flag3) {
            entitysection.b().filter(entityaccess -> !entityaccess.dF()).forEach(this::d);
         }

         if (flag && !flag1) {
            entitysection.b().filter(entityaccess -> !entityaccess.dF()).forEach(this::f);
         } else if (!flag && flag1) {
            entitysection.b().filter(entityaccess -> !entityaccess.dF()).forEach(this::e);
         }

         if (!flag2 && flag3) {
            entitysection.b().filter(entityaccess -> !entityaccess.dF()).forEach(this::c);
         }
      });
   }

   public void b(long i) {
      PersistentEntitySectionManager.b persistententitysectionmanager_b = (PersistentEntitySectionManager.b)this.i.get(i);
      if (persistententitysectionmanager_b == PersistentEntitySectionManager.b.a) {
         this.c(i);
      }
   }

   private boolean a(long i, Consumer<T> consumer) {
      return this.storeChunkSections(i, consumer, false);
   }

   private boolean storeChunkSections(long i, Consumer<T> consumer, boolean callEvent) {
      PersistentEntitySectionManager.b persistententitysectionmanager_b = (PersistentEntitySectionManager.b)this.i.get(i);
      if (persistententitysectionmanager_b == PersistentEntitySectionManager.b.b) {
         return false;
      } else {
         List<T> list = this.f.b(i).flatMap(entitysection -> entitysection.b().filter(EntityAccess::dE)).collect(Collectors.toList());
         if (list.isEmpty()) {
            if (persistententitysectionmanager_b == PersistentEntitySectionManager.b.c) {
               if (callEvent) {
                  CraftEventFactory.callEntitiesUnloadEvent(((EntityStorage)this.d).e, new ChunkCoordIntPair(i), ImmutableList.of());
               }

               this.d.a(new ChunkEntities<>(new ChunkCoordIntPair(i), ImmutableList.of()));
            }

            return true;
         } else if (persistententitysectionmanager_b == PersistentEntitySectionManager.b.a) {
            this.c(i);
            return false;
         } else {
            if (callEvent) {
               CraftEventFactory.callEntitiesUnloadEvent(
                  ((EntityStorage)this.d).e, new ChunkCoordIntPair(i), list.stream().map(entity -> (Entity)entity).collect(Collectors.toList())
               );
            }

            this.d.a(new ChunkEntities<>(new ChunkCoordIntPair(i), list));
            list.forEach(consumer);
            return true;
         }
      }
   }

   private void c(long i) {
      this.i.put(i, PersistentEntitySectionManager.b.b);
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i);
      CompletableFuture completablefuture = this.d.a(chunkcoordintpair);
      Queue queue = this.k;
      completablefuture.thenAccept(queue::add).exceptionally(throwable -> {
         a.error("Failed to read chunk {}", chunkcoordintpair, throwable);
         return null;
      });
   }

   private boolean d(long i) {
      boolean flag = this.storeChunkSections(i, entityaccess -> entityaccess.cP().forEach(this::g), true);
      if (!flag) {
         return false;
      } else {
         this.i.remove(i);
         return true;
      }
   }

   private void g(EntityAccess entityaccess) {
      entityaccess.b(Entity.RemovalReason.c);
      entityaccess.a(EntityInLevelCallback.a);
   }

   private void f() {
      this.j.removeIf(i -> this.h.get(i) != Visibility.a ? true : this.d(i));
   }

   private void g() {
      ChunkEntities<T> chunkentities;
      while((chunkentities = this.k.poll()) != null) {
         chunkentities.b().forEach(entityaccess -> this.a(entityaccess, true));
         this.i.put(chunkentities.a().a(), PersistentEntitySectionManager.b.c);
         List<Entity> entities = this.getEntities(chunkentities.a());
         CraftEventFactory.callEntitiesLoadEvent(((EntityStorage)this.d).e, chunkentities.a(), entities);
      }
   }

   public void a() {
      this.g();
      this.f();
   }

   private LongSet h() {
      LongSet longset = this.f.a();
      ObjectIterator objectiterator = Long2ObjectMaps.fastIterable(this.i).iterator();

      while(objectiterator.hasNext()) {
         Entry<PersistentEntitySectionManager.b> entry = (Entry)objectiterator.next();
         if (entry.getValue() == PersistentEntitySectionManager.b.c) {
            longset.add(entry.getLongKey());
         }
      }

      return longset;
   }

   public void b() {
      this.h().forEach(i -> {
         boolean flag = this.h.get(i) == Visibility.a;
         if (flag) {
            this.d(i);
         } else {
            this.a(i, entityaccess -> {
            });
         }
      });
   }

   public void c() {
      LongSet longset = this.h();

      while(!longset.isEmpty()) {
         this.d.a(false);
         this.g();
         longset.removeIf(i -> {
            boolean flag = this.h.get(i) == Visibility.a;
            return flag ? this.d(i) : this.a(i, entityaccess -> {
            });
         });
      }

      this.d.a(true);
   }

   @Override
   public void close() throws IOException {
      this.close(true);
   }

   public void close(boolean save) throws IOException {
      if (save) {
         this.c();
      }

      this.d.close();
   }

   public boolean a(UUID uuid) {
      return this.b.contains(uuid);
   }

   public LevelEntityGetter<T> d() {
      return this.g;
   }

   public boolean a(BlockPosition blockposition) {
      return ((Visibility)this.h.get(ChunkCoordIntPair.a(blockposition))).a();
   }

   public boolean a(ChunkCoordIntPair chunkcoordintpair) {
      return ((Visibility)this.h.get(chunkcoordintpair.a())).a();
   }

   public boolean a(long i) {
      return this.i.get(i) == PersistentEntitySectionManager.b.c;
   }

   public void a(Writer writer) throws IOException {
      CSVWriter csvwriter = CSVWriter.a().a("x").a("y").a("z").a("visibility").a("load_status").a("entity_count").a(writer);
      this.f
         .a()
         .forEach(
            i -> {
               PersistentEntitySectionManager.b persistententitysectionmanager_b = (PersistentEntitySectionManager.b)this.i.get(i);
               this.f
                  .a(i)
                  .forEach(
                     j -> {
                        EntitySection<T> entitysection = this.f.d(j);
                        if (entitysection != null) {
                           try {
                              csvwriter.a(
                                 SectionPosition.b(j),
                                 SectionPosition.c(j),
                                 SectionPosition.d(j),
                                 entitysection.c(),
                                 persistententitysectionmanager_b,
                                 entitysection.d()
                              );
                           } catch (IOException var7) {
                              throw new UncheckedIOException(var7);
                           }
                        }
                     }
                  );
            }
         );
   }

   @VisibleForDebug
   public String e() {
      int i = this.b.size();
      return i + "," + this.e.b() + "," + this.f.b() + "," + this.i.size() + "," + this.h.size() + "," + this.k.size() + "," + this.j.size();
   }

   private class a implements EntityInLevelCallback {
      private final T c;
      private long d;
      private EntitySection<T> e;

      a(EntityAccess entityaccess, long i, EntitySection entitysection) {
         this.c = entityaccess;
         this.d = i;
         this.e = entitysection;
      }

      @Override
      public void a() {
         BlockPosition blockposition = this.c.dg();
         long i = SectionPosition.c(blockposition);
         if (i != this.d) {
            Visibility visibility = this.e.c();
            if (!this.e.b(this.c)) {
               PersistentEntitySectionManager.a
                  .warn("Entity {} wasn't found in section {} (moving to {})", new Object[]{this.c, SectionPosition.a(this.d), i});
            }

            PersistentEntitySectionManager.this.a(this.d, this.e);
            EntitySection<T> entitysection = PersistentEntitySectionManager.this.f.c(i);
            entitysection.a(this.c);
            this.e = entitysection;
            this.d = i;
            this.a(visibility, entitysection.c());
         }
      }

      private void a(Visibility visibility, Visibility visibility1) {
         Visibility visibility2 = PersistentEntitySectionManager.a(this.c, visibility);
         Visibility visibility3 = PersistentEntitySectionManager.a(this.c, visibility1);
         if (visibility2 == visibility3) {
            if (visibility3.b()) {
               PersistentEntitySectionManager.this.c.a(this.c);
            }
         } else {
            boolean flag = visibility2.b();
            boolean flag1 = visibility3.b();
            if (flag && !flag1) {
               PersistentEntitySectionManager.this.f(this.c);
            } else if (!flag && flag1) {
               PersistentEntitySectionManager.this.e(this.c);
            }

            boolean flag2 = visibility2.a();
            boolean flag3 = visibility3.a();
            if (flag2 && !flag3) {
               PersistentEntitySectionManager.this.d(this.c);
            } else if (!flag2 && flag3) {
               PersistentEntitySectionManager.this.c(this.c);
            }

            if (flag1) {
               PersistentEntitySectionManager.this.c.a(this.c);
            }
         }
      }

      @Override
      public void a(Entity.RemovalReason entity_removalreason) {
         if (!this.e.b(this.c)) {
            PersistentEntitySectionManager.a
               .warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[]{this.c, SectionPosition.a(this.d), entity_removalreason});
         }

         Visibility visibility = PersistentEntitySectionManager.a(this.c, this.e.c());
         if (visibility.a()) {
            PersistentEntitySectionManager.this.d(this.c);
         }

         if (visibility.b()) {
            PersistentEntitySectionManager.this.f(this.c);
         }

         if (entity_removalreason.a()) {
            PersistentEntitySectionManager.this.c.f(this.c);
         }

         PersistentEntitySectionManager.this.b.remove(this.c.cs());
         this.c.a(a);
         PersistentEntitySectionManager.this.a(this.d, this.e);
      }
   }

   private static enum b {
      a,
      b,
      c;
   }
}
