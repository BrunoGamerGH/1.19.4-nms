package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;

public class LightEngine implements ILightEngine {
   public static final int a = 15;
   public static final int b = 1;
   protected final LevelHeightAccessor c;
   @Nullable
   private final LightEngineLayer<?, ?> d;
   @Nullable
   private final LightEngineLayer<?, ?> e;

   public LightEngine(ILightAccess var0, boolean var1, boolean var2) {
      this.c = var0.q();
      this.d = var1 ? new LightEngineBlock(var0) : null;
      this.e = var2 ? new LightEngineSky(var0) : null;
   }

   @Override
   public void a(BlockPosition var0) {
      if (this.d != null) {
         this.d.a(var0);
      }

      if (this.e != null) {
         this.e.a(var0);
      }
   }

   @Override
   public void a(BlockPosition var0, int var1) {
      if (this.d != null) {
         this.d.a(var0, var1);
      }
   }

   @Override
   public boolean D_() {
      if (this.e != null && this.e.D_()) {
         return true;
      } else {
         return this.d != null && this.d.D_();
      }
   }

   @Override
   public int a(int var0, boolean var1, boolean var2) {
      if (this.d != null && this.e != null) {
         int var3 = var0 / 2;
         int var4 = this.d.a(var3, var1, var2);
         int var5 = var0 - var3 + var4;
         int var6 = this.e.a(var5, var1, var2);
         return var4 == 0 && var6 > 0 ? this.d.a(var6, var1, var2) : var6;
      } else if (this.d != null) {
         return this.d.a(var0, var1, var2);
      } else {
         return this.e != null ? this.e.a(var0, var1, var2) : var0;
      }
   }

   @Override
   public void a(SectionPosition var0, boolean var1) {
      if (this.d != null) {
         this.d.a(var0, var1);
      }

      if (this.e != null) {
         this.e.a(var0, var1);
      }
   }

   @Override
   public void a(ChunkCoordIntPair var0, boolean var1) {
      if (this.d != null) {
         this.d.a(var0, var1);
      }

      if (this.e != null) {
         this.e.a(var0, var1);
      }
   }

   public LightEngineLayerEventListener a(EnumSkyBlock var0) {
      if (var0 == EnumSkyBlock.b) {
         return (LightEngineLayerEventListener)(this.d == null ? LightEngineLayerEventListener.Void.a : this.d);
      } else {
         return (LightEngineLayerEventListener)(this.e == null ? LightEngineLayerEventListener.Void.a : this.e);
      }
   }

   public String a(EnumSkyBlock var0, SectionPosition var1) {
      if (var0 == EnumSkyBlock.b) {
         if (this.d != null) {
            return this.d.b(var1.s());
         }
      } else if (this.e != null) {
         return this.e.b(var1.s());
      }

      return "n/a";
   }

   public void a(EnumSkyBlock var0, SectionPosition var1, @Nullable NibbleArray var2, boolean var3) {
      if (var0 == EnumSkyBlock.b) {
         if (this.d != null) {
            this.d.a(var1.s(), var2, var3);
         }
      } else if (this.e != null) {
         this.e.a(var1.s(), var2, var3);
      }
   }

   public void b(ChunkCoordIntPair var0, boolean var1) {
      if (this.d != null) {
         this.d.b(var0, var1);
      }

      if (this.e != null) {
         this.e.b(var0, var1);
      }
   }

   public int b(BlockPosition var0, int var1) {
      int var2 = this.e == null ? 0 : this.e.b(var0) - var1;
      int var3 = this.d == null ? 0 : this.d.b(var0);
      return Math.max(var3, var2);
   }

   public int b() {
      return this.c.aj() + 2;
   }

   public int c() {
      return this.c.ak() - 1;
   }

   public int d() {
      return this.c() + this.b();
   }
}
