package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class CriterionSlideDownBlock extends CriterionTriggerAbstract<CriterionSlideDownBlock.a> {
   static final MinecraftKey a = new MinecraftKey("slide_down_block");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionSlideDownBlock.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      Block var3 = a(var0);
      CriterionTriggerProperties var4 = CriterionTriggerProperties.a(var0.get("state"));
      if (var3 != null) {
         var4.a(var3.n(), var1x -> {
            throw new JsonSyntaxException("Block " + var3 + " has no property " + var1x);
         });
      }

      return new CriterionSlideDownBlock.a(var1, var3, var4);
   }

   @Nullable
   private static Block a(JsonObject var0) {
      if (var0.has("block")) {
         MinecraftKey var1 = new MinecraftKey(ChatDeserializer.h(var0, "block"));
         return BuiltInRegistries.f.b(var1).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + var1 + "'"));
      } else {
         return null;
      }
   }

   public void a(EntityPlayer var0, IBlockData var1) {
      this.a(var0, var1x -> var1x.a(var1));
   }

   public static class a extends CriterionInstanceAbstract {
      @Nullable
      private final Block a;
      private final CriterionTriggerProperties b;

      public a(CriterionConditionEntity.b var0, @Nullable Block var1, CriterionTriggerProperties var2) {
         super(CriterionSlideDownBlock.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionSlideDownBlock.a a(Block var0) {
         return new CriterionSlideDownBlock.a(CriterionConditionEntity.b.a, var0, CriterionTriggerProperties.a);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         if (this.a != null) {
            var1.addProperty("block", BuiltInRegistries.f.b(this.a).toString());
         }

         var1.add("state", this.b.a());
         return var1;
      }

      public boolean a(IBlockData var0) {
         if (this.a != null && !var0.a(this.a)) {
            return false;
         } else {
            return this.b.a(var0);
         }
      }
   }
}
