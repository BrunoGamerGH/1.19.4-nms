package net.minecraft.commands;

import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.IChatBaseComponent;

public class CommandExceptionProvider implements BuiltInExceptionProvider {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.double.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.double.big", var1, var0)
   );
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.float.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.float.big", var1, var0)
   );
   private static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.integer.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.integer.big", var1, var0)
   );
   private static final Dynamic2CommandExceptionType g = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.long.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType h = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.long.big", var1, var0)
   );
   private static final DynamicCommandExceptionType i = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.literal.incorrect", var0));
   private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.quote.expected.start"));
   private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.quote.expected.end"));
   private static final DynamicCommandExceptionType l = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.quote.escape", var0));
   private static final DynamicCommandExceptionType m = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.bool.invalid", var0));
   private static final DynamicCommandExceptionType n = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.int.invalid", var0));
   private static final SimpleCommandExceptionType o = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.int.expected"));
   private static final DynamicCommandExceptionType p = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.long.invalid", var0));
   private static final SimpleCommandExceptionType q = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.long.expected"));
   private static final DynamicCommandExceptionType r = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.double.invalid", var0));
   private static final SimpleCommandExceptionType s = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.double.expected"));
   private static final DynamicCommandExceptionType t = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.float.invalid", var0));
   private static final SimpleCommandExceptionType u = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.float.expected"));
   private static final SimpleCommandExceptionType v = new SimpleCommandExceptionType(IChatBaseComponent.c("parsing.bool.expected"));
   private static final DynamicCommandExceptionType w = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("parsing.expected", var0));
   private static final SimpleCommandExceptionType x = new SimpleCommandExceptionType(IChatBaseComponent.c("command.unknown.command"));
   private static final SimpleCommandExceptionType y = new SimpleCommandExceptionType(IChatBaseComponent.c("command.unknown.argument"));
   private static final SimpleCommandExceptionType z = new SimpleCommandExceptionType(IChatBaseComponent.c("command.expected.separator"));
   private static final DynamicCommandExceptionType A = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("command.exception", var0));

   public Dynamic2CommandExceptionType doubleTooLow() {
      return a;
   }

   public Dynamic2CommandExceptionType doubleTooHigh() {
      return b;
   }

   public Dynamic2CommandExceptionType floatTooLow() {
      return c;
   }

   public Dynamic2CommandExceptionType floatTooHigh() {
      return d;
   }

   public Dynamic2CommandExceptionType integerTooLow() {
      return e;
   }

   public Dynamic2CommandExceptionType integerTooHigh() {
      return f;
   }

   public Dynamic2CommandExceptionType longTooLow() {
      return g;
   }

   public Dynamic2CommandExceptionType longTooHigh() {
      return h;
   }

   public DynamicCommandExceptionType literalIncorrect() {
      return i;
   }

   public SimpleCommandExceptionType readerExpectedStartOfQuote() {
      return j;
   }

   public SimpleCommandExceptionType readerExpectedEndOfQuote() {
      return k;
   }

   public DynamicCommandExceptionType readerInvalidEscape() {
      return l;
   }

   public DynamicCommandExceptionType readerInvalidBool() {
      return m;
   }

   public DynamicCommandExceptionType readerInvalidInt() {
      return n;
   }

   public SimpleCommandExceptionType readerExpectedInt() {
      return o;
   }

   public DynamicCommandExceptionType readerInvalidLong() {
      return p;
   }

   public SimpleCommandExceptionType readerExpectedLong() {
      return q;
   }

   public DynamicCommandExceptionType readerInvalidDouble() {
      return r;
   }

   public SimpleCommandExceptionType readerExpectedDouble() {
      return s;
   }

   public DynamicCommandExceptionType readerInvalidFloat() {
      return t;
   }

   public SimpleCommandExceptionType readerExpectedFloat() {
      return u;
   }

   public SimpleCommandExceptionType readerExpectedBool() {
      return v;
   }

   public DynamicCommandExceptionType readerExpectedSymbol() {
      return w;
   }

   public SimpleCommandExceptionType dispatcherUnknownCommand() {
      return x;
   }

   public SimpleCommandExceptionType dispatcherUnknownArgument() {
      return y;
   }

   public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
      return z;
   }

   public DynamicCommandExceptionType dispatcherParseException() {
      return A;
   }
}
