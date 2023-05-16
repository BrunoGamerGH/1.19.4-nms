package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.CriterionConditionNBT;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.Entity;

public record EntityDataSource(String selectorPattern, @Nullable EntitySelector compiledSelector) implements DataSource {
   private final String a;
   @Nullable
   private final EntitySelector b;

   public EntityDataSource(String var0) {
      this(var0, a(var0));
   }

   public EntityDataSource(String var0, @Nullable EntitySelector var1) {
      this.a = var0;
      this.b = var1;
   }

   @Nullable
   private static EntitySelector a(String var0) {
      try {
         ArgumentParserSelector var1 = new ArgumentParserSelector(new StringReader(var0));
         return var1.t();
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   @Override
   public Stream<NBTTagCompound> getData(CommandListenerWrapper var0) throws CommandSyntaxException {
      if (this.b != null) {
         List<? extends Entity> var1 = this.b.b(var0);
         return var1.stream().map(CriterionConditionNBT::b);
      } else {
         return Stream.empty();
      }
   }

   @Override
   public String toString() {
      return "entity=" + this.a;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof EntityDataSource var1 && this.a.equals(var1.a)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }
}
