package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.EnumDirection;

public class BlockStateDirection extends BlockStateEnum<EnumDirection> {
   protected BlockStateDirection(String var0, Collection<EnumDirection> var1) {
      super(var0, EnumDirection.class, var1);
   }

   public static BlockStateDirection a(String var0) {
      return a(var0, var0x -> true);
   }

   public static BlockStateDirection a(String var0, Predicate<EnumDirection> var1) {
      return a(var0, Arrays.stream(EnumDirection.values()).filter(var1).collect(Collectors.toList()));
   }

   public static BlockStateDirection a(String var0, EnumDirection... var1) {
      return a(var0, Lists.newArrayList(var1));
   }

   public static BlockStateDirection a(String var0, Collection<EnumDirection> var1) {
      return new BlockStateDirection(var0, var1);
   }
}
