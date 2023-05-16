package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemInteractWithBlockTrigger extends CriterionTriggerAbstract<ItemInteractWithBlockTrigger.a> {
   final MinecraftKey a;

   public ItemInteractWithBlockTrigger(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public MinecraftKey a() {
      return this.a;
   }

   public ItemInteractWithBlockTrigger.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionLocation var3 = CriterionConditionLocation.a(var0.get("location"));
      CriterionConditionItem var4 = CriterionConditionItem.a(var0.get("item"));
      return new ItemInteractWithBlockTrigger.a(this.a, var1, var3, var4);
   }

   public void a(EntityPlayer var0, BlockPosition var1, ItemStack var2) {
      IBlockData var3 = var0.x().a_(var1);
      this.a(var0, var4x -> var4x.a(var3, var0.x(), var1, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionLocation a;
      private final CriterionConditionItem b;

      public a(MinecraftKey var0, CriterionConditionEntity.b var1, CriterionConditionLocation var2, CriterionConditionItem var3) {
         super(var0, var1);
         this.a = var2;
         this.b = var3;
      }

      public static ItemInteractWithBlockTrigger.a a(CriterionConditionLocation.a var0, CriterionConditionItem.a var1) {
         return new ItemInteractWithBlockTrigger.a(CriterionTriggers.M.a, CriterionConditionEntity.b.a, var0.b(), var1.b());
      }

      public static ItemInteractWithBlockTrigger.a b(CriterionConditionLocation.a var0, CriterionConditionItem.a var1) {
         return new ItemInteractWithBlockTrigger.a(CriterionTriggers.X.a, CriterionConditionEntity.b.a, var0.b(), var1.b());
      }

      public boolean a(IBlockData var0, WorldServer var1, BlockPosition var2, ItemStack var3) {
         return !this.a.a(var1, (double)var2.u() + 0.5, (double)var2.v() + 0.5, (double)var2.w() + 0.5) ? false : this.b.a(var3);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("location", this.a.a());
         var1.add("item", this.b.a());
         return var1;
      }
   }
}
