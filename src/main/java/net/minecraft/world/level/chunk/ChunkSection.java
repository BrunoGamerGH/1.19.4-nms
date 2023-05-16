package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.QuartPos;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;

public class ChunkSection {
   public static final int a = 16;
   public static final int b = 16;
   public static final int c = 4096;
   public static final int d = 2;
   private final int e;
   private short f;
   private short g;
   private short h;
   private final DataPaletteBlock<IBlockData> i;
   private DataPaletteBlock<Holder<BiomeBase>> j;

   public ChunkSection(int i, DataPaletteBlock<IBlockData> datapaletteblock, DataPaletteBlock<Holder<BiomeBase>> palettedcontainerro) {
      this.e = a(i);
      this.i = datapaletteblock;
      this.j = palettedcontainerro;
      this.h();
   }

   public ChunkSection(int i, IRegistry<BiomeBase> iregistry) {
      this.e = a(i);
      this.i = new DataPaletteBlock<>(Block.o, Blocks.a.o(), DataPaletteBlock.d.d);
      this.j = new DataPaletteBlock<>(iregistry.t(), iregistry.f(Biomes.b), DataPaletteBlock.d.e);
   }

   public static int a(int i) {
      return i << 4;
   }

   public IBlockData a(int i, int j, int k) {
      return this.i.a(i, j, k);
   }

   public Fluid b(int i, int j, int k) {
      return this.i.a(i, j, k).r();
   }

   public void a() {
      this.i.a();
   }

   public void b() {
      this.i.b();
   }

   public IBlockData a(int i, int j, int k, IBlockData iblockdata) {
      return this.a(i, j, k, iblockdata, true);
   }

   public IBlockData a(int i, int j, int k, IBlockData iblockdata, boolean flag) {
      IBlockData iblockdata1;
      if (flag) {
         iblockdata1 = this.i.a(i, j, k, iblockdata);
      } else {
         iblockdata1 = this.i.b(i, j, k, iblockdata);
      }

      Fluid fluid = iblockdata1.r();
      Fluid fluid1 = iblockdata.r();
      if (!iblockdata1.h()) {
         --this.f;
         if (iblockdata1.s()) {
            --this.g;
         }
      }

      if (!fluid.c()) {
         --this.h;
      }

      if (!iblockdata.h()) {
         ++this.f;
         if (iblockdata.s()) {
            ++this.g;
         }
      }

      if (!fluid1.c()) {
         ++this.h;
      }

      return iblockdata1;
   }

   public boolean c() {
      return this.f == 0;
   }

   public boolean d() {
      return this.e() || this.f();
   }

   public boolean e() {
      return this.g > 0;
   }

   public boolean f() {
      return this.h > 0;
   }

   public int g() {
      return this.e;
   }

   public void h() {
      class 1a implements DataPaletteBlock.b<IBlockData> {
         public int nonEmptyBlockCount;
         public int tickingBlockCount;
         public int tickingFluidCount;

         public void accept(IBlockData iblockdata, int i) {
            Fluid fluid = iblockdata.r();
            if (!iblockdata.h()) {
               this.nonEmptyBlockCount += i;
               if (iblockdata.s()) {
                  this.tickingBlockCount += i;
               }
            }

            if (!fluid.c()) {
               this.nonEmptyBlockCount += i;
               if (fluid.f()) {
                  this.tickingFluidCount += i;
               }
            }
         }
      }

      1a a0 = new 1a();
      this.i.a(a0);
      this.f = (short)a0.nonEmptyBlockCount;
      this.g = (short)a0.tickingBlockCount;
      this.h = (short)a0.tickingFluidCount;
   }

   public DataPaletteBlock<IBlockData> i() {
      return this.i;
   }

   public PalettedContainerRO<Holder<BiomeBase>> j() {
      return this.j;
   }

   public void a(PacketDataSerializer packetdataserializer) {
      this.f = packetdataserializer.readShort();
      this.i.a(packetdataserializer);
      DataPaletteBlock<Holder<BiomeBase>> datapaletteblock = this.j.e();
      datapaletteblock.a(packetdataserializer);
      this.j = datapaletteblock;
   }

   public void b(PacketDataSerializer packetdataserializer) {
      DataPaletteBlock<Holder<BiomeBase>> datapaletteblock = this.j.e();
      datapaletteblock.a(packetdataserializer);
      this.j = datapaletteblock;
   }

   public void c(PacketDataSerializer packetdataserializer) {
      packetdataserializer.writeShort(this.f);
      this.i.b(packetdataserializer);
      this.j.b(packetdataserializer);
   }

   public int k() {
      return 2 + this.i.c() + this.j.c();
   }

   public boolean a(Predicate<IBlockData> predicate) {
      return this.i.a(predicate);
   }

   public Holder<BiomeBase> c(int i, int j, int k) {
      return this.j.a(i, j, k);
   }

   public void setBiome(int i, int j, int k, Holder<BiomeBase> biome) {
      this.j.c(i, j, k, biome);
   }

   public void a(BiomeResolver biomeresolver, Climate.Sampler climate_sampler, int i, int j) {
      DataPaletteBlock<Holder<BiomeBase>> datapaletteblock = this.j.e();
      int k = QuartPos.a(this.g());
      boolean flag = true;

      for(int l = 0; l < 4; ++l) {
         for(int i1 = 0; i1 < 4; ++i1) {
            for(int j1 = 0; j1 < 4; ++j1) {
               datapaletteblock.b(l, i1, j1, biomeresolver.getNoiseBiome(i + l, k + i1, j + j1, climate_sampler));
            }
         }
      }

      this.j = datapaletteblock;
   }
}
