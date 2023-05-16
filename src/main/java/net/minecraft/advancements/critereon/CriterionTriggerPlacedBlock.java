package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class CriterionTriggerPlacedBlock extends CriterionTriggerAbstract<CriterionTriggerPlacedBlock.a> {
   static final MinecraftKey a = new MinecraftKey("placed_block");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerPlacedBlock.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      Block var3 = a(var0);
      CriterionTriggerProperties var4 = CriterionTriggerProperties.a(var0.get("state"));
      if (var3 != null) {
         var4.a(var3.n(), var1x -> {
            throw new JsonSyntaxException("Block " + var3 + " has no property " + var1x + ":");
         });
      }

      CriterionConditionLocation var5 = CriterionConditionLocation.a(var0.get("location"));
      CriterionConditionItem var6 = CriterionConditionItem.a(var0.get("item"));
      return new CriterionTriggerPlacedBlock.a(var1, var3, var4, var5, var6);
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

   public void a(EntityPlayer var0, BlockPosition var1, ItemStack var2) {
      IBlockData var3 = var0.x().a_(var1);
      this.a(var0, var4x -> var4x.a(var3, var1, var0.x(), var2));
   }

   public static class a extends CriterionInstanceAbstract {
      @Nullable
      private final Block a;
      private final CriterionTriggerProperties b;
      private final CriterionConditionLocation c;
      private final CriterionConditionItem d;

      public a(
         CriterionConditionEntity.b var0, @Nullable Block var1, CriterionTriggerProperties var2, CriterionConditionLocation var3, CriterionConditionItem var4
      ) {
         super(CriterionTriggerPlacedBlock.a, var0);
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }

      public static CriterionTriggerPlacedBlock.a a(Block var0) {
         return new CriterionTriggerPlacedBlock.a(
            CriterionConditionEntity.b.a, var0, CriterionTriggerProperties.a, CriterionConditionLocation.a, CriterionConditionItem.a
         );
      }

      public boolean a(IBlockData var0, BlockPosition var1, WorldServer var2, ItemStack var3) {
         if (this.a != null && !var0.a(this.a)) {
            return false;
         } else if (!this.b.a(var0)) {
            return false;
         } else if (!this.c.a(var2, (double)var1.u(), (double)var1.v(), (double)var1.w())) {
            return false;
         } else {
            return this.d.a(var3);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         if (this.a != null) {
            var1.addProperty("block", BuiltInRegistries.f.b(this.a).toString());
         }

         var1.add("state", this.b.a());
         var1.add("location", this.c.a());
         var1.add("item", this.d.a());
         return var1;
      }
   }
}
