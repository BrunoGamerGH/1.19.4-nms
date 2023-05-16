package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class ArgumentBlockPredicate implements ArgumentType<ArgumentBlockPredicate.b> {
   private static final Collection<String> a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
   private final HolderLookup<Block> b;

   public ArgumentBlockPredicate(CommandBuildContext var0) {
      this.b = var0.a(Registries.e);
   }

   public static ArgumentBlockPredicate a(CommandBuildContext var0) {
      return new ArgumentBlockPredicate(var0);
   }

   public ArgumentBlockPredicate.b a(StringReader var0) throws CommandSyntaxException {
      return a(this.b, var0);
   }

   public static ArgumentBlockPredicate.b a(HolderLookup<Block> var0, StringReader var1) throws CommandSyntaxException {
      return (ArgumentBlockPredicate.b)ArgumentBlock.b(var0, var1, true)
         .map(
            var0x -> new ArgumentBlockPredicate.a(var0x.a(), var0x.b().keySet(), var0x.c()),
            var0x -> new ArgumentBlockPredicate.c(var0x.a(), var0x.b(), var0x.c())
         );
   }

   public static Predicate<ShapeDetectorBlock> a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return (Predicate<ShapeDetectorBlock>)var0.getArgument(var1, ArgumentBlockPredicate.b.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ArgumentBlock.a(this.b, var1, true, true);
   }

   public Collection<String> getExamples() {
      return a;
   }

   static class a implements ArgumentBlockPredicate.b {
      private final IBlockData a;
      private final Set<IBlockState<?>> b;
      @Nullable
      private final NBTTagCompound c;

      public a(IBlockData var0, Set<IBlockState<?>> var1, @Nullable NBTTagCompound var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public boolean a(ShapeDetectorBlock var0) {
         IBlockData var1 = var0.a();
         if (!var1.a(this.a.b())) {
            return false;
         } else {
            for(IBlockState<?> var3 : this.b) {
               if (var1.c(var3) != this.a.c(var3)) {
                  return false;
               }
            }

            if (this.c == null) {
               return true;
            } else {
               TileEntity var2 = var0.b();
               return var2 != null && GameProfileSerializer.a(this.c, var2.m(), true);
            }
         }
      }

      @Override
      public boolean a() {
         return this.c != null;
      }
   }

   public interface b extends Predicate<ShapeDetectorBlock> {
      boolean a();
   }

   static class c implements ArgumentBlockPredicate.b {
      private final HolderSet<Block> a;
      @Nullable
      private final NBTTagCompound b;
      private final Map<String, String> c;

      c(HolderSet<Block> var0, Map<String, String> var1, @Nullable NBTTagCompound var2) {
         this.a = var0;
         this.c = var1;
         this.b = var2;
      }

      public boolean a(ShapeDetectorBlock var0) {
         IBlockData var1 = var0.a();
         if (!var1.a(this.a)) {
            return false;
         } else {
            for(Entry<String, String> var3 : this.c.entrySet()) {
               IBlockState<?> var4 = var1.b().n().a(var3.getKey());
               if (var4 == null) {
                  return false;
               }

               Comparable<?> var5 = (Comparable)var4.b(var3.getValue()).orElse(null);
               if (var5 == null) {
                  return false;
               }

               if (var1.c(var4) != var5) {
                  return false;
               }
            }

            if (this.b == null) {
               return true;
            } else {
               TileEntity var2 = var0.b();
               return var2 != null && GameProfileSerializer.a(this.b, var2.m(), true);
            }
         }
      }

      @Override
      public boolean a() {
         return this.b != null;
      }
   }
}
