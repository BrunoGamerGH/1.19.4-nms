package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class CriterionTriggerBeeNestDestroyed extends CriterionTriggerAbstract<CriterionTriggerBeeNestDestroyed.a> {
   static final MinecraftKey a = new MinecraftKey("bee_nest_destroyed");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerBeeNestDestroyed.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      Block var3 = a(var0);
      CriterionConditionItem var4 = CriterionConditionItem.a(var0.get("item"));
      CriterionConditionValue.IntegerRange var5 = CriterionConditionValue.IntegerRange.a(var0.get("num_bees_inside"));
      return new CriterionTriggerBeeNestDestroyed.a(var1, var3, var4, var5);
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

   public void a(EntityPlayer var0, IBlockData var1, ItemStack var2, int var3) {
      this.a(var0, var3x -> var3x.a(var1, var2, var3));
   }

   public static class a extends CriterionInstanceAbstract {
      @Nullable
      private final Block a;
      private final CriterionConditionItem b;
      private final CriterionConditionValue.IntegerRange c;

      public a(CriterionConditionEntity.b var0, @Nullable Block var1, CriterionConditionItem var2, CriterionConditionValue.IntegerRange var3) {
         super(CriterionTriggerBeeNestDestroyed.a, var0);
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public static CriterionTriggerBeeNestDestroyed.a a(Block var0, CriterionConditionItem.a var1, CriterionConditionValue.IntegerRange var2) {
         return new CriterionTriggerBeeNestDestroyed.a(CriterionConditionEntity.b.a, var0, var1.b(), var2);
      }

      public boolean a(IBlockData var0, ItemStack var1, int var2) {
         if (this.a != null && !var0.a(this.a)) {
            return false;
         } else {
            return !this.b.a(var1) ? false : this.c.d(var2);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         if (this.a != null) {
            var1.addProperty("block", BuiltInRegistries.f.b(this.a).toString());
         }

         var1.add("item", this.b.a());
         var1.add("num_bees_inside", this.c.d());
         return var1;
      }
   }
}
