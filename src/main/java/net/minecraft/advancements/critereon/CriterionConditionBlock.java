package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;

public class CriterionConditionBlock {
   public static final CriterionConditionBlock a = new CriterionConditionBlock(null, null, CriterionTriggerProperties.a, CriterionConditionNBT.a);
   @Nullable
   private final TagKey<Block> b;
   @Nullable
   private final Set<Block> c;
   private final CriterionTriggerProperties d;
   private final CriterionConditionNBT e;

   public CriterionConditionBlock(@Nullable TagKey<Block> var0, @Nullable Set<Block> var1, CriterionTriggerProperties var2, CriterionConditionNBT var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public boolean a(WorldServer var0, BlockPosition var1) {
      if (this == a) {
         return true;
      } else if (!var0.o(var1)) {
         return false;
      } else {
         IBlockData var2 = var0.a_(var1);
         if (this.b != null && !var2.a(this.b)) {
            return false;
         } else if (this.c != null && !this.c.contains(var2.b())) {
            return false;
         } else if (!this.d.a(var2)) {
            return false;
         } else {
            if (this.e != CriterionConditionNBT.a) {
               TileEntity var3 = var0.c_(var1);
               if (var3 == null || !this.e.a(var3.m())) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static CriterionConditionBlock a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "block");
         CriterionConditionNBT var2 = CriterionConditionNBT.a(var1.get("nbt"));
         Set<Block> var3 = null;
         JsonArray var4 = ChatDeserializer.a(var1, "blocks", null);
         if (var4 != null) {
            Builder<Block> var5 = ImmutableSet.builder();

            for(JsonElement var7 : var4) {
               MinecraftKey var8 = new MinecraftKey(ChatDeserializer.a(var7, "block"));
               var5.add(BuiltInRegistries.f.b(var8).orElseThrow(() -> new JsonSyntaxException("Unknown block id '" + var8 + "'")));
            }

            var3 = var5.build();
         }

         TagKey<Block> var5 = null;
         if (var1.has("tag")) {
            MinecraftKey var6 = new MinecraftKey(ChatDeserializer.h(var1, "tag"));
            var5 = TagKey.a(Registries.e, var6);
         }

         CriterionTriggerProperties var6 = CriterionTriggerProperties.a(var1.get("state"));
         return new CriterionConditionBlock(var5, var3, var6, var2);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (this.c != null) {
            JsonArray var1 = new JsonArray();

            for(Block var3 : this.c) {
               var1.add(BuiltInRegistries.f.b(var3).toString());
            }

            var0.add("blocks", var1);
         }

         if (this.b != null) {
            var0.addProperty("tag", this.b.b().toString());
         }

         var0.add("nbt", this.e.a());
         var0.add("state", this.d.a());
         return var0;
      }
   }

   public static class a {
      @Nullable
      private Set<Block> a;
      @Nullable
      private TagKey<Block> b;
      private CriterionTriggerProperties c = CriterionTriggerProperties.a;
      private CriterionConditionNBT d = CriterionConditionNBT.a;

      private a() {
      }

      public static CriterionConditionBlock.a a() {
         return new CriterionConditionBlock.a();
      }

      public CriterionConditionBlock.a a(Block... var0) {
         this.a = ImmutableSet.copyOf(var0);
         return this;
      }

      public CriterionConditionBlock.a a(Iterable<Block> var0) {
         this.a = ImmutableSet.copyOf(var0);
         return this;
      }

      public CriterionConditionBlock.a a(TagKey<Block> var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionBlock.a a(NBTTagCompound var0) {
         this.d = new CriterionConditionNBT(var0);
         return this;
      }

      public CriterionConditionBlock.a a(CriterionTriggerProperties var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionBlock b() {
         return new CriterionConditionBlock(this.b, this.a, this.c, this.d);
      }
   }
}
