package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;

public class ArgumentNBTTag implements ArgumentType<NBTTagCompound> {
   private static final Collection<String> a = Arrays.asList("{}", "{foo=bar}");

   private ArgumentNBTTag() {
   }

   public static ArgumentNBTTag a() {
      return new ArgumentNBTTag();
   }

   public static <S> NBTTagCompound a(CommandContext<S> var0, String var1) {
      return (NBTTagCompound)var0.getArgument(var1, NBTTagCompound.class);
   }

   public NBTTagCompound a(StringReader var0) throws CommandSyntaxException {
      return new MojangsonParser(var0).f();
   }

   public Collection<String> getExamples() {
      return a;
   }
}
