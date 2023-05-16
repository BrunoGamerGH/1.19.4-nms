package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;

public class LightEngineStorageSky extends LightEngineStorage<LightEngineStorageSky.a> {
   private static final EnumDirection[] n = new EnumDirection[]{EnumDirection.c, EnumDirection.d, EnumDirection.e, EnumDirection.f};
   private final LongSet o = new LongOpenHashSet();
   private final LongSet p = new LongOpenHashSet();
   private final LongSet q = new LongOpenHashSet();
   private final LongSet r = new LongOpenHashSet();
   private volatile boolean s;

   protected LightEngineStorageSky(ILightAccess var0) {
      super(EnumSkyBlock.a, var0, new LightEngineStorageSky.a(new Long2ObjectOpenHashMap(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
   }

   @Override
   protected int d(long var0) {
      return this.e(var0, false);
   }

   protected int e(long var0, boolean var2) {
      long var3 = SectionPosition.e(var0);
      int var5 = SectionPosition.c(var3);
      LightEngineStorageSky.a var6 = var2 ? this.i : this.h;
      int var7 = var6.c.get(SectionPosition.f(var3));
      if (var7 != var6.b && var5 < var7) {
         NibbleArray var8 = this.a(var6, var3);
         if (var8 == null) {
            for(var0 = BlockPosition.e(var0); var8 == null; var8 = this.a(var6, var3)) {
               if (++var5 >= var7) {
                  return 15;
               }

               var0 = BlockPosition.a(var0, 0, 16, 0);
               var3 = SectionPosition.a(var3, EnumDirection.b);
            }
         }

         return var8.a(SectionPosition.b(BlockPosition.a(var0)), SectionPosition.b(BlockPosition.b(var0)), SectionPosition.b(BlockPosition.c(var0)));
      } else {
         return var2 && !this.n(var3) ? 0 : 15;
      }
   }

   @Override
   protected void k(long var0) {
      int var2 = SectionPosition.c(var0);
      if (this.i.b > var2) {
         this.i.b = var2;
         this.i.c.defaultReturnValue(this.i.b);
      }

      long var3 = SectionPosition.f(var0);
      int var5 = this.i.c.get(var3);
      if (var5 < var2 + 1) {
         this.i.c.put(var3, var2 + 1);
         if (this.r.contains(var3)) {
            this.p(var0);
            if (var5 > this.i.b) {
               long var6 = SectionPosition.b(SectionPosition.b(var0), var5 - 1, SectionPosition.d(var0));
               this.o(var6);
            }

            this.f();
         }
      }
   }

   private void o(long var0) {
      this.q.add(var0);
      this.p.remove(var0);
   }

   private void p(long var0) {
      this.p.add(var0);
      this.q.remove(var0);
   }

   private void f() {
      this.s = !this.p.isEmpty() || !this.q.isEmpty();
   }

   @Override
   protected void l(long var0) {
      long var2 = SectionPosition.f(var0);
      boolean var4 = this.r.contains(var2);
      if (var4) {
         this.o(var0);
      }

      int var5 = SectionPosition.c(var0);
      if (this.i.c.get(var2) == var5 + 1) {
         long var6;
         for(var6 = var0; !this.g(var6) && this.a(var5); var6 = SectionPosition.a(var6, EnumDirection.a)) {
            --var5;
         }

         if (this.g(var6)) {
            this.i.c.put(var2, var5 + 1);
            if (var4) {
               this.p(var6);
            }
         } else {
            this.i.c.remove(var2);
         }
      }

      if (var4) {
         this.f();
      }
   }

   @Override
   protected void b(long var0, boolean var2) {
      this.d();
      if (var2 && this.r.add(var0)) {
         int var3 = this.i.c.get(var0);
         if (var3 != this.i.b) {
            long var4 = SectionPosition.b(SectionPosition.b(var0), var3 - 1, SectionPosition.d(var0));
            this.p(var4);
            this.f();
         }
      } else if (!var2) {
         this.r.remove(var0);
      }
   }

   @Override
   protected boolean a() {
      return super.a() || this.s;
   }

   @Override
   protected NibbleArray j(long var0) {
      NibbleArray var2 = (NibbleArray)this.l.get(var0);
      if (var2 != null) {
         return var2;
      } else {
         long var3 = SectionPosition.a(var0, EnumDirection.b);
         int var5 = this.i.c.get(SectionPosition.f(var0));
         if (var5 != this.i.b && SectionPosition.c(var3) < var5) {
            NibbleArray var6;
            while((var6 = this.a(var3, true)) == null) {
               var3 = SectionPosition.a(var3, EnumDirection.b);
            }

            return a(var6);
         } else {
            return new NibbleArray();
         }
      }
   }

   private static NibbleArray a(NibbleArray var0) {
      if (var0.c()) {
         return new NibbleArray();
      } else {
         byte[] var1 = var0.a();
         byte[] var2 = new byte[2048];

         for(int var3 = 0; var3 < 16; ++var3) {
            System.arraycopy(var1, 0, var2, var3 * 128, 128);
         }

         return new NibbleArray(var2);
      }
   }

   @Override
   protected void a(LightEngineLayer<LightEngineStorageSky.a, ?> var0, boolean var1, boolean var2) {
      super.a(var0, var1, var2);
      if (var1) {
         if (!this.p.isEmpty()) {
            LongIterator var4 = this.p.iterator();

            while(var4.hasNext()) {
               long var4x = var4.next();
               int var6 = this.c(var4x);
               if (var6 != 2 && !this.q.contains(var4x) && this.o.add(var4x)) {
                  if (var6 == 1) {
                     this.a(var0, var4x);
                     if (this.j.add(var4x)) {
                        this.i.a(var4x);
                     }

                     Arrays.fill(this.a(var4x, true).a(), (byte)-1);
                     int var7 = SectionPosition.c(SectionPosition.b(var4x));
                     int var8 = SectionPosition.c(SectionPosition.c(var4x));
                     int var9 = SectionPosition.c(SectionPosition.d(var4x));

                     for(EnumDirection var13 : n) {
                        long var14 = SectionPosition.a(var4x, var13);
                        if ((this.q.contains(var14) || !this.o.contains(var14) && !this.p.contains(var14)) && this.g(var14)) {
                           for(int var16 = 0; var16 < 16; ++var16) {
                              for(int var17 = 0; var17 < 16; ++var17) {
                                 long var18;
                                 long var20;
                                 switch(var13) {
                                    case c:
                                       var18 = BlockPosition.a(var7 + var16, var8 + var17, var9);
                                       var20 = BlockPosition.a(var7 + var16, var8 + var17, var9 - 1);
                                       break;
                                    case d:
                                       var18 = BlockPosition.a(var7 + var16, var8 + var17, var9 + 16 - 1);
                                       var20 = BlockPosition.a(var7 + var16, var8 + var17, var9 + 16);
                                       break;
                                    case e:
                                       var18 = BlockPosition.a(var7, var8 + var16, var9 + var17);
                                       var20 = BlockPosition.a(var7 - 1, var8 + var16, var9 + var17);
                                       break;
                                    default:
                                       var18 = BlockPosition.a(var7 + 16 - 1, var8 + var16, var9 + var17);
                                       var20 = BlockPosition.a(var7 + 16, var8 + var16, var9 + var17);
                                 }

                                 var0.a(var18, var20, var0.b(var18, var20, 0), true);
                              }
                           }
                        }
                     }

                     for(int var10 = 0; var10 < 16; ++var10) {
                        for(int var11 = 0; var11 < 16; ++var11) {
                           long var12 = BlockPosition.a(
                              SectionPosition.a(SectionPosition.b(var4x), var10),
                              SectionPosition.c(SectionPosition.c(var4x)),
                              SectionPosition.a(SectionPosition.d(var4x), var11)
                           );
                           long var14 = BlockPosition.a(
                              SectionPosition.a(SectionPosition.b(var4x), var10),
                              SectionPosition.c(SectionPosition.c(var4x)) - 1,
                              SectionPosition.a(SectionPosition.d(var4x), var11)
                           );
                           var0.a(var12, var14, var0.b(var12, var14, 0), true);
                        }
                     }
                  } else {
                     for(int var7 = 0; var7 < 16; ++var7) {
                        for(int var8 = 0; var8 < 16; ++var8) {
                           long var9 = BlockPosition.a(
                              SectionPosition.a(SectionPosition.b(var4x), var7),
                              SectionPosition.a(SectionPosition.c(var4x), 15),
                              SectionPosition.a(SectionPosition.d(var4x), var8)
                           );
                           var0.a(Long.MAX_VALUE, var9, 0, true);
                        }
                     }
                  }
               }
            }
         }

         this.p.clear();
         if (!this.q.isEmpty()) {
            LongIterator var23 = this.q.iterator();

            while(var23.hasNext()) {
               long var4 = var23.next();
               if (this.o.remove(var4) && this.g(var4)) {
                  for(int var6 = 0; var6 < 16; ++var6) {
                     for(int var7 = 0; var7 < 16; ++var7) {
                        long var8 = BlockPosition.a(
                           SectionPosition.a(SectionPosition.b(var4), var6),
                           SectionPosition.a(SectionPosition.c(var4), 15),
                           SectionPosition.a(SectionPosition.d(var4), var7)
                        );
                        var0.a(Long.MAX_VALUE, var8, 15, false);
                     }
                  }
               }
            }
         }

         this.q.clear();
         this.s = false;
      }
   }

   protected boolean a(int var0) {
      return var0 >= this.i.b;
   }

   protected boolean m(long var0) {
      long var2 = SectionPosition.f(var0);
      int var4 = this.i.c.get(var2);
      return var4 == this.i.b || SectionPosition.c(var0) >= var4;
   }

   protected boolean n(long var0) {
      long var2 = SectionPosition.f(var0);
      return this.r.contains(var2);
   }

   protected static final class a extends LightEngineStorageArray<LightEngineStorageSky.a> {
      int b;
      final Long2IntOpenHashMap c;

      public a(Long2ObjectOpenHashMap<NibbleArray> var0, Long2IntOpenHashMap var1, int var2) {
         super(var0);
         this.c = var1;
         var1.defaultReturnValue(var2);
         this.b = var2;
      }

      public LightEngineStorageSky.a a() {
         return new LightEngineStorageSky.a(this.a.clone(), this.c.clone(), this.b);
      }
   }
}
