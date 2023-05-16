package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.LightEngineGraphSection;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;

public abstract class LightEngineStorage<M extends LightEngineStorageArray<M>> extends LightEngineGraphSection {
   protected static final int a = 0;
   protected static final int b = 1;
   protected static final int c = 2;
   protected static final NibbleArray d = new NibbleArray();
   private static final EnumDirection[] n = EnumDirection.values();
   private final EnumSkyBlock o;
   private final ILightAccess p;
   protected final LongSet e = new LongOpenHashSet();
   protected final LongSet f = new LongOpenHashSet();
   protected final LongSet g = new LongOpenHashSet();
   protected volatile M h;
   protected final M i;
   protected final LongSet j = new LongOpenHashSet();
   protected final LongSet k = new LongOpenHashSet();
   protected final Long2ObjectMap<NibbleArray> l = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());
   private final LongSet q = new LongOpenHashSet();
   private final LongSet r = new LongOpenHashSet();
   private final LongSet s = new LongOpenHashSet();
   protected volatile boolean m;

   protected LightEngineStorage(EnumSkyBlock var0, ILightAccess var1, M var2) {
      super(3, 16, 256);
      this.o = var0;
      this.p = var1;
      this.i = var2;
      this.h = var2.b();
      this.h.d();
   }

   protected boolean g(long var0) {
      return this.a(var0, true) != null;
   }

   @Nullable
   protected NibbleArray a(long var0, boolean var2) {
      return this.a((M)(var2 ? this.i : this.h), var0);
   }

   @Nullable
   protected NibbleArray a(M var0, long var1) {
      return var0.c(var1);
   }

   @Nullable
   public NibbleArray h(long var0) {
      NibbleArray var2 = (NibbleArray)this.l.get(var0);
      return var2 != null ? var2 : this.a(var0, false);
   }

   protected abstract int d(long var1);

   protected int i(long var0) {
      long var2 = SectionPosition.e(var0);
      NibbleArray var4 = this.a(var2, true);
      return var4.a(SectionPosition.b(BlockPosition.a(var0)), SectionPosition.b(BlockPosition.b(var0)), SectionPosition.b(BlockPosition.c(var0)));
   }

   protected void b(long var0, int var2) {
      long var3 = SectionPosition.e(var0);
      if (this.j.add(var3)) {
         this.i.a(var3);
      }

      NibbleArray var5 = this.a(var3, true);
      var5.a(SectionPosition.b(BlockPosition.a(var0)), SectionPosition.b(BlockPosition.b(var0)), SectionPosition.b(BlockPosition.c(var0)), var2);
      SectionPosition.a(var0, this.k::add);
   }

   @Override
   protected int c(long var0) {
      if (var0 == Long.MAX_VALUE) {
         return 2;
      } else if (this.e.contains(var0)) {
         return 0;
      } else {
         return !this.s.contains(var0) && this.i.b(var0) ? 1 : 2;
      }
   }

   @Override
   protected int b(long var0) {
      if (this.f.contains(var0)) {
         return 2;
      } else {
         return !this.e.contains(var0) && !this.g.contains(var0) ? 2 : 0;
      }
   }

   @Override
   protected void a(long var0, int var2) {
      int var3 = this.c(var0);
      if (var3 != 0 && var2 == 0) {
         this.e.add(var0);
         this.g.remove(var0);
      }

      if (var3 == 0 && var2 != 0) {
         this.e.remove(var0);
         this.f.remove(var0);
      }

      if (var3 >= 2 && var2 != 2) {
         if (this.s.contains(var0)) {
            this.s.remove(var0);
         } else {
            this.i.a(var0, this.j(var0));
            this.j.add(var0);
            this.k(var0);
            int var4 = SectionPosition.b(var0);
            int var5 = SectionPosition.c(var0);
            int var6 = SectionPosition.d(var0);

            for(int var7 = -1; var7 <= 1; ++var7) {
               for(int var8 = -1; var8 <= 1; ++var8) {
                  for(int var9 = -1; var9 <= 1; ++var9) {
                     this.k.add(SectionPosition.b(var4 + var8, var5 + var9, var6 + var7));
                  }
               }
            }
         }
      }

      if (var3 != 2 && var2 >= 2) {
         this.s.add(var0);
      }

      this.m = !this.s.isEmpty();
   }

   protected NibbleArray j(long var0) {
      NibbleArray var2 = (NibbleArray)this.l.get(var0);
      return var2 != null ? var2 : new NibbleArray();
   }

   protected void a(LightEngineLayer<?, ?> var0, long var1) {
      if (var0.c() != 0) {
         if (var0.c() < 8192) {
            var0.a(var2x -> SectionPosition.e(var2x) == var1);
         } else {
            int var3 = SectionPosition.c(SectionPosition.b(var1));
            int var4 = SectionPosition.c(SectionPosition.c(var1));
            int var5 = SectionPosition.c(SectionPosition.d(var1));

            for(int var6 = 0; var6 < 16; ++var6) {
               for(int var7 = 0; var7 < 16; ++var7) {
                  for(int var8 = 0; var8 < 16; ++var8) {
                     long var9 = BlockPosition.a(var3 + var6, var4 + var7, var5 + var8);
                     var0.e(var9);
                  }
               }
            }
         }
      }
   }

   protected boolean a() {
      return this.m;
   }

   protected void a(LightEngineLayer<M, ?> var0, boolean var1, boolean var2) {
      if (this.a() || !this.l.isEmpty()) {
         LongIterator var3 = this.s.iterator();

         while(var3.hasNext()) {
            long var4x = var3.next();
            this.a(var0, var4x);
            NibbleArray var6 = (NibbleArray)this.l.remove(var4x);
            NibbleArray var7 = this.i.d(var4x);
            if (this.r.contains(SectionPosition.f(var4x))) {
               if (var6 != null) {
                  this.l.put(var4x, var6);
               } else if (var7 != null) {
                  this.l.put(var4x, var7);
               }
            }
         }

         this.i.c();
         var3 = this.s.iterator();

         while(var3.hasNext()) {
            long var4x = var3.next();
            this.l(var4x);
         }

         this.s.clear();
         this.m = false;
         ObjectIterator var10 = this.l.long2ObjectEntrySet().iterator();

         while(var10.hasNext()) {
            Entry<NibbleArray> var4x = (Entry)var10.next();
            long var5 = var4x.getLongKey();
            if (this.g(var5)) {
               NibbleArray var7 = (NibbleArray)var4x.getValue();
               if (this.i.c(var5) != var7) {
                  this.a(var0, var5);
                  this.i.a(var5, var7);
                  this.j.add(var5);
               }
            }
         }

         this.i.c();
         if (!var2) {
            var3 = this.l.keySet().iterator();

            while(var3.hasNext()) {
               long var4x = var3.next();
               this.b(var0, var4x);
            }
         } else {
            var3 = this.q.iterator();

            while(var3.hasNext()) {
               long var4x = var3.next();
               this.b(var0, var4x);
            }
         }

         this.q.clear();
         ObjectIterator<Entry<NibbleArray>> var3x = this.l.long2ObjectEntrySet().iterator();

         while(var3x.hasNext()) {
            Entry<NibbleArray> var4x = (Entry)var3x.next();
            long var5 = var4x.getLongKey();
            if (this.g(var5)) {
               var3x.remove();
            }
         }
      }
   }

   private void b(LightEngineLayer<M, ?> var0, long var1) {
      if (this.g(var1)) {
         int var3 = SectionPosition.c(SectionPosition.b(var1));
         int var4 = SectionPosition.c(SectionPosition.c(var1));
         int var5 = SectionPosition.c(SectionPosition.d(var1));

         for(EnumDirection var9 : n) {
            long var10 = SectionPosition.a(var1, var9);
            if (!this.l.containsKey(var10) && this.g(var10)) {
               for(int var12 = 0; var12 < 16; ++var12) {
                  for(int var13 = 0; var13 < 16; ++var13) {
                     long var14;
                     long var16;
                     switch(var9) {
                        case a:
                           var14 = BlockPosition.a(var3 + var13, var4, var5 + var12);
                           var16 = BlockPosition.a(var3 + var13, var4 - 1, var5 + var12);
                           break;
                        case b:
                           var14 = BlockPosition.a(var3 + var13, var4 + 16 - 1, var5 + var12);
                           var16 = BlockPosition.a(var3 + var13, var4 + 16, var5 + var12);
                           break;
                        case c:
                           var14 = BlockPosition.a(var3 + var12, var4 + var13, var5);
                           var16 = BlockPosition.a(var3 + var12, var4 + var13, var5 - 1);
                           break;
                        case d:
                           var14 = BlockPosition.a(var3 + var12, var4 + var13, var5 + 16 - 1);
                           var16 = BlockPosition.a(var3 + var12, var4 + var13, var5 + 16);
                           break;
                        case e:
                           var14 = BlockPosition.a(var3, var4 + var12, var5 + var13);
                           var16 = BlockPosition.a(var3 - 1, var4 + var12, var5 + var13);
                           break;
                        default:
                           var14 = BlockPosition.a(var3 + 16 - 1, var4 + var12, var5 + var13);
                           var16 = BlockPosition.a(var3 + 16, var4 + var12, var5 + var13);
                     }

                     var0.a(var14, var16, var0.b(var14, var16, var0.c(var14)), false);
                     var0.a(var16, var14, var0.b(var16, var14, var0.c(var16)), false);
                  }
               }
            }
         }
      }
   }

   protected void k(long var0) {
   }

   protected void l(long var0) {
   }

   protected void b(long var0, boolean var2) {
   }

   public void c(long var0, boolean var2) {
      if (var2) {
         this.r.add(var0);
      } else {
         this.r.remove(var0);
      }
   }

   protected void a(long var0, @Nullable NibbleArray var2, boolean var3) {
      if (var2 != null) {
         this.l.put(var0, var2);
         if (!var3) {
            this.q.add(var0);
         }
      } else {
         this.l.remove(var0);
      }
   }

   protected void d(long var0, boolean var2) {
      boolean var3 = this.e.contains(var0);
      if (!var3 && !var2) {
         this.g.add(var0);
         this.a(Long.MAX_VALUE, var0, 0, true);
      }

      if (var3 && var2) {
         this.f.add(var0);
         this.a(Long.MAX_VALUE, var0, 2, false);
      }
   }

   protected void d() {
      if (this.b()) {
         this.b(Integer.MAX_VALUE);
      }
   }

   protected void e() {
      if (!this.j.isEmpty()) {
         M var0 = this.i.b();
         var0.d();
         this.h = var0;
         this.j.clear();
      }

      if (!this.k.isEmpty()) {
         LongIterator var0 = this.k.iterator();

         while(var0.hasNext()) {
            long var1 = var0.nextLong();
            this.p.a(this.o, SectionPosition.a(var1));
         }

         this.k.clear();
      }
   }
}
