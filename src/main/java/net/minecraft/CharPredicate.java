package net.minecraft;

import java.util.Objects;

@FunctionalInterface
public interface CharPredicate {
   boolean test(char var1);

   default CharPredicate and(CharPredicate var0) {
      Objects.requireNonNull(var0);
      return var1x -> this.test(var1x) && var0.test(var1x);
   }

   default CharPredicate negate() {
      return var0 -> !this.test(var0);
   }

   default CharPredicate or(CharPredicate var0) {
      Objects.requireNonNull(var0);
      return var1x -> this.test(var1x) || var0.test(var1x);
   }
}
