package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;

public class CriterionConditionNBT {
   public static final CriterionConditionNBT a = new CriterionConditionNBT(null);
   @Nullable
   private final NBTTagCompound b;

   public CriterionConditionNBT(@Nullable NBTTagCompound var0) {
      this.b = var0;
   }

   public boolean a(ItemStack var0) {
      return this == a ? true : this.a(var0.u());
   }

   public boolean a(Entity var0) {
      return this == a ? true : this.a(b(var0));
   }

   public boolean a(@Nullable NBTBase var0) {
      if (var0 == null) {
         return this == a;
      } else {
         return this.b == null || GameProfileSerializer.a(this.b, var0, true);
      }
   }

   public JsonElement a() {
      return (JsonElement)(this != a && this.b != null ? new JsonPrimitive(this.b.toString()) : JsonNull.INSTANCE);
   }

   public static CriterionConditionNBT a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         NBTTagCompound var1;
         try {
            var1 = MojangsonParser.a(ChatDeserializer.a(var0, "nbt"));
         } catch (CommandSyntaxException var3) {
            throw new JsonSyntaxException("Invalid nbt tag: " + var3.getMessage());
         }

         return new CriterionConditionNBT(var1);
      } else {
         return a;
      }
   }

   public static NBTTagCompound b(Entity var0) {
      NBTTagCompound var1 = var0.f(new NBTTagCompound());
      if (var0 instanceof EntityHuman) {
         ItemStack var2 = ((EntityHuman)var0).fJ().f();
         if (!var2.b()) {
            var1.a("SelectedItem", var2.b(new NBTTagCompound()));
         }
      }

      return var1;
   }
}
