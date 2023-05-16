package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ArgumentPredicateItemStack implements Predicate<ItemStack> {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("arguments.item.overstacked", var0, var1)
   );
   private final Holder<Item> b;
   @Nullable
   private final NBTTagCompound c;

   public ArgumentPredicateItemStack(Holder<Item> var0, @Nullable NBTTagCompound var1) {
      this.b = var0;
      this.c = var1;
   }

   public Item a() {
      return this.b.a();
   }

   public boolean a(ItemStack var0) {
      return var0.a(this.b) && GameProfileSerializer.a(this.c, var0.u(), true);
   }

   public ItemStack a(int var0, boolean var1) throws CommandSyntaxException {
      ItemStack var2 = new ItemStack(this.b, var0);
      if (this.c != null) {
         var2.c(this.c);
      }

      if (var1 && var0 > var2.f()) {
         throw a.create(this.c(), var2.f());
      } else {
         return var2;
      }
   }

   public String b() {
      StringBuilder var0 = new StringBuilder(this.c());
      if (this.c != null) {
         var0.append(this.c);
      }

      return var0.toString();
   }

   private String c() {
      return this.b.e().map(ResourceKey::a).orElseGet(() -> "unknown[" + this.b + "]").toString();
   }
}
