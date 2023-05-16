package net.minecraft.world.level.levelgen.structure.pieces;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.slf4j.Logger;

public record PiecesContainer(List<StructurePiece> pieces) {
   private final List<StructurePiece> a;
   private static final Logger b = LogUtils.getLogger();
   private static final MinecraftKey c = new MinecraftKey("jigsaw");
   private static final Map<MinecraftKey, MinecraftKey> d = ImmutableMap.builder()
      .put(new MinecraftKey("nvi"), c)
      .put(new MinecraftKey("pcp"), c)
      .put(new MinecraftKey("bastionremnant"), c)
      .put(new MinecraftKey("runtime"), c)
      .build();

   public PiecesContainer(List<StructurePiece> var0) {
      this.a = List.copyOf(var0);
   }

   public boolean a() {
      return this.a.isEmpty();
   }

   public boolean a(BlockPosition var0) {
      for(StructurePiece var2 : this.a) {
         if (var2.f().b(var0)) {
            return true;
         }
      }

      return false;
   }

   public NBTBase a(StructurePieceSerializationContext var0) {
      NBTTagList var1 = new NBTTagList();

      for(StructurePiece var3 : this.a) {
         var1.add(var3.a(var0));
      }

      return var1;
   }

   public static PiecesContainer a(NBTTagList var0, StructurePieceSerializationContext var1) {
      List<StructurePiece> var2 = Lists.newArrayList();

      for(int var3 = 0; var3 < var0.size(); ++var3) {
         NBTTagCompound var4 = var0.a(var3);
         String var5 = var4.l("id").toLowerCase(Locale.ROOT);
         MinecraftKey var6 = new MinecraftKey(var5);
         MinecraftKey var7 = d.getOrDefault(var6, var6);
         WorldGenFeatureStructurePieceType var8 = BuiltInRegistries.S.a(var7);
         if (var8 == null) {
            b.error("Unknown structure piece id: {}", var7);
         } else {
            try {
               StructurePiece var9 = var8.load(var1, var4);
               var2.add(var9);
            } catch (Exception var10) {
               b.error("Exception loading structure piece with id {}", var7, var10);
            }
         }
      }

      return new PiecesContainer(var2);
   }

   public StructureBoundingBox b() {
      return StructurePiece.a(this.a.stream());
   }

   public List<StructurePiece> c() {
      return this.a;
   }
}
