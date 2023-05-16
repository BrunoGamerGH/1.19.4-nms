package net.minecraft.world.level.levelgen.structure;

import com.mojang.logging.LogUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import org.slf4j.Logger;

public final class StructureStart {
   public static final String a = "INVALID";
   public static final StructureStart b = new StructureStart(null, new ChunkCoordIntPair(0, 0), 0, new PiecesContainer(List.of()));
   private static final Logger c = LogUtils.getLogger();
   private final Structure d;
   private final PiecesContainer e;
   private final ChunkCoordIntPair f;
   private int g;
   @Nullable
   private volatile StructureBoundingBox h;

   public StructureStart(Structure var0, ChunkCoordIntPair var1, int var2, PiecesContainer var3) {
      this.d = var0;
      this.f = var1;
      this.g = var2;
      this.e = var3;
   }

   @Nullable
   public static StructureStart a(StructurePieceSerializationContext var0, NBTTagCompound var1, long var2) {
      String var4 = var1.l("id");
      if ("INVALID".equals(var4)) {
         return b;
      } else {
         IRegistry<Structure> var5 = var0.b().d(Registries.ax);
         Structure var6 = var5.a(new MinecraftKey(var4));
         if (var6 == null) {
            c.error("Unknown stucture id: {}", var4);
            return null;
         } else {
            ChunkCoordIntPair var7 = new ChunkCoordIntPair(var1.h("ChunkX"), var1.h("ChunkZ"));
            int var8 = var1.h("references");
            NBTTagList var9 = var1.c("Children", 10);

            try {
               PiecesContainer var10 = PiecesContainer.a(var9, var0);
               if (var6 instanceof OceanMonumentStructure) {
                  var10 = OceanMonumentStructure.a(var7, var2, var10);
               }

               return new StructureStart(var6, var7, var8, var10);
            } catch (Exception var11) {
               c.error("Failed Start with id {}", var4, var11);
               return null;
            }
         }
      }
   }

   public StructureBoundingBox a() {
      StructureBoundingBox var0 = this.h;
      if (var0 == null) {
         var0 = this.d.a(this.e.b());
         this.h = var0;
      }

      return var0;
   }

   public void a(GeneratorAccessSeed var0, StructureManager var1, ChunkGenerator var2, RandomSource var3, StructureBoundingBox var4, ChunkCoordIntPair var5) {
      List<StructurePiece> var6 = this.e.c();
      if (!var6.isEmpty()) {
         StructureBoundingBox var7 = var6.get(0).f;
         BlockPosition var8 = var7.f();
         BlockPosition var9 = new BlockPosition(var8.u(), var7.h(), var8.w());

         for(StructurePiece var11 : var6) {
            if (var11.f().a(var4)) {
               var11.a(var0, var1, var2, var3, var4, var5, var9);
            }
         }

         this.d.a(var0, var1, var2, var3, var4, var5, this.e);
      }
   }

   public NBTTagCompound a(StructurePieceSerializationContext var0, ChunkCoordIntPair var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      if (this.b()) {
         var2.a("id", var0.b().d(Registries.ax).b(this.d).toString());
         var2.a("ChunkX", var1.e);
         var2.a("ChunkZ", var1.f);
         var2.a("references", this.g);
         var2.a("Children", this.e.a(var0));
         return var2;
      } else {
         var2.a("id", "INVALID");
         return var2;
      }
   }

   public boolean b() {
      return !this.e.a();
   }

   public ChunkCoordIntPair c() {
      return this.f;
   }

   public boolean d() {
      return this.g < this.g();
   }

   public void e() {
      ++this.g;
   }

   public int f() {
      return this.g;
   }

   protected int g() {
      return 1;
   }

   public Structure h() {
      return this.d;
   }

   public List<StructurePiece> i() {
      return this.e.c();
   }
}
