package net.minecraft.network.protocol.game;

import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Queue;
import java.util.function.BiPredicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketPlayOutCommands implements Packet<PacketListenerPlayOut> {
   private static final byte a = 3;
   private static final byte b = 4;
   private static final byte c = 8;
   private static final byte d = 16;
   private static final byte e = 0;
   private static final byte f = 1;
   private static final byte g = 2;
   private final int h;
   private final List<PacketPlayOutCommands.b> i;

   public PacketPlayOutCommands(RootCommandNode<ICompletionProvider> var0) {
      Object2IntMap<CommandNode<ICompletionProvider>> var1 = a(var0);
      this.i = a(var1);
      this.h = var1.getInt(var0);
   }

   public PacketPlayOutCommands(PacketDataSerializer var0) {
      this.i = var0.a(PacketPlayOutCommands::b);
      this.h = var0.m();
      a(this.i);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.i, (var0x, var1x) -> var1x.a(var0x));
      var0.d(this.h);
   }

   private static void a(List<PacketPlayOutCommands.b> var0, BiPredicate<PacketPlayOutCommands.b, IntSet> var1) {
      IntSet var2 = new IntOpenHashSet(IntSets.fromTo(0, var0.size()));

      while(!var2.isEmpty()) {
         boolean var3 = var2.removeIf(var3x -> var1.test(var0.get(var3x), var2));
         if (!var3) {
            throw new IllegalStateException("Server sent an impossible command tree");
         }
      }
   }

   private static void a(List<PacketPlayOutCommands.b> var0) {
      a(var0, PacketPlayOutCommands.b::a);
      a(var0, PacketPlayOutCommands.b::b);
   }

   private static Object2IntMap<CommandNode<ICompletionProvider>> a(RootCommandNode<ICompletionProvider> var0) {
      Object2IntMap<CommandNode<ICompletionProvider>> var1 = new Object2IntOpenHashMap();
      Queue<CommandNode<ICompletionProvider>> var2 = Queues.newArrayDeque();
      var2.add(var0);

      CommandNode<ICompletionProvider> var3;
      while((var3 = var2.poll()) != null) {
         if (!var1.containsKey(var3)) {
            int var4 = var1.size();
            var1.put(var3, var4);
            var2.addAll(var3.getChildren());
            if (var3.getRedirect() != null) {
               var2.add(var3.getRedirect());
            }
         }
      }

      return var1;
   }

   private static List<PacketPlayOutCommands.b> a(Object2IntMap<CommandNode<ICompletionProvider>> var0) {
      ObjectArrayList<PacketPlayOutCommands.b> var1 = new ObjectArrayList(var0.size());
      var1.size(var0.size());
      ObjectIterator var2 = Object2IntMaps.fastIterable(var0).iterator();

      while(var2.hasNext()) {
         Entry<CommandNode<ICompletionProvider>> var3 = (Entry)var2.next();
         var1.set(var3.getIntValue(), a((CommandNode<ICompletionProvider>)var3.getKey(), var0));
      }

      return var1;
   }

   private static PacketPlayOutCommands.b b(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      int[] var2 = var0.c();
      int var3 = (var1 & 8) != 0 ? var0.m() : 0;
      PacketPlayOutCommands.e var4 = a(var0, var1);
      return new PacketPlayOutCommands.b(var4, var1, var3, var2);
   }

   @Nullable
   private static PacketPlayOutCommands.e a(PacketDataSerializer var0, byte var1) {
      int var2 = var1 & 3;
      if (var2 == 2) {
         String var3 = var0.s();
         int var4 = var0.m();
         ArgumentTypeInfo<?, ?> var5 = BuiltInRegistries.w.a(var4);
         if (var5 == null) {
            return null;
         } else {
            ArgumentTypeInfo.a<?> var6 = var5.b(var0);
            MinecraftKey var7 = (var1 & 16) != 0 ? var0.t() : null;
            return new PacketPlayOutCommands.a(var3, var6, var7);
         }
      } else if (var2 == 1) {
         String var3 = var0.s();
         return new PacketPlayOutCommands.c(var3);
      } else {
         return null;
      }
   }

   private static PacketPlayOutCommands.b a(CommandNode<ICompletionProvider> var0, Object2IntMap<CommandNode<ICompletionProvider>> var1) {
      int var2 = 0;
      int var3;
      if (var0.getRedirect() != null) {
         var2 |= 8;
         var3 = var1.getInt(var0.getRedirect());
      } else {
         var3 = 0;
      }

      if (var0.getCommand() != null) {
         var2 |= 4;
      }

      PacketPlayOutCommands.e var4;
      if (var0 instanceof RootCommandNode) {
         var2 |= 0;
         var4 = null;
      } else if (var0 instanceof ArgumentCommandNode var6) {
         var4 = new PacketPlayOutCommands.a(var6);
         var2 |= 2;
         if (var6.getCustomSuggestions() != null) {
            var2 |= 16;
         }
      } else {
         if (!(var0 instanceof LiteralCommandNode)) {
            throw new UnsupportedOperationException("Unknown node type " + var0);
         }

         LiteralCommandNode var5 = (LiteralCommandNode)var0;
         var4 = new PacketPlayOutCommands.c(var5.getLiteral());
         var2 |= 1;
      }

      int[] var6 = var0.getChildren().stream().mapToInt(var1::getInt).toArray();
      return new PacketPlayOutCommands.b(var4, var2, var3, var6);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public RootCommandNode<ICompletionProvider> a(CommandBuildContext var0) {
      return (RootCommandNode<ICompletionProvider>)new PacketPlayOutCommands.d(var0, this.i).a(this.h);
   }

   static class a implements PacketPlayOutCommands.e {
      private final String a;
      private final ArgumentTypeInfo.a<?> b;
      @Nullable
      private final MinecraftKey c;

      @Nullable
      private static MinecraftKey a(@Nullable SuggestionProvider<ICompletionProvider> var0) {
         return var0 != null ? CompletionProviders.a(var0) : null;
      }

      a(String var0, ArgumentTypeInfo.a<?> var1, @Nullable MinecraftKey var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public a(ArgumentCommandNode<ICompletionProvider, ?> var0) {
         this(var0.getName(), ArgumentTypeInfos.b(var0.getType()), a(var0.getCustomSuggestions()));
      }

      @Override
      public ArgumentBuilder<ICompletionProvider, ?> a(CommandBuildContext var0) {
         ArgumentType<?> var1 = this.b.b(var0);
         RequiredArgumentBuilder<ICompletionProvider, ?> var2 = RequiredArgumentBuilder.argument(this.a, var1);
         if (this.c != null) {
            var2.suggests(CompletionProviders.a(this.c));
         }

         return var2;
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
         a(var0, this.b);
         if (this.c != null) {
            var0.a(this.c);
         }
      }

      private static <A extends ArgumentType<?>> void a(PacketDataSerializer var0, ArgumentTypeInfo.a<A> var1) {
         a(var0, var1.a(), var1);
      }

      private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.a<A>> void a(
         PacketDataSerializer var0, ArgumentTypeInfo<A, T> var1, ArgumentTypeInfo.a<A> var2
      ) {
         var0.d(BuiltInRegistries.w.a(var1));
         var1.a((T)var2, var0);
      }
   }

   static class b {
      @Nullable
      final PacketPlayOutCommands.e a;
      final int b;
      final int c;
      final int[] d;

      b(@Nullable PacketPlayOutCommands.e var0, int var1, int var2, int[] var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      public void a(PacketDataSerializer var0) {
         var0.writeByte(this.b);
         var0.a(this.d);
         if ((this.b & 8) != 0) {
            var0.d(this.c);
         }

         if (this.a != null) {
            this.a.a(var0);
         }
      }

      public boolean a(IntSet var0) {
         if ((this.b & 8) != 0) {
            return !var0.contains(this.c);
         } else {
            return true;
         }
      }

      public boolean b(IntSet var0) {
         for(int var4 : this.d) {
            if (var0.contains(var4)) {
               return false;
            }
         }

         return true;
      }
   }

   static class c implements PacketPlayOutCommands.e {
      private final String a;

      c(String var0) {
         this.a = var0;
      }

      @Override
      public ArgumentBuilder<ICompletionProvider, ?> a(CommandBuildContext var0) {
         return LiteralArgumentBuilder.literal(this.a);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
      }
   }

   static class d {
      private final CommandBuildContext a;
      private final List<PacketPlayOutCommands.b> b;
      private final List<CommandNode<ICompletionProvider>> c;

      d(CommandBuildContext var0, List<PacketPlayOutCommands.b> var1) {
         this.a = var0;
         this.b = var1;
         ObjectArrayList<CommandNode<ICompletionProvider>> var2 = new ObjectArrayList();
         var2.size(var1.size());
         this.c = var2;
      }

      public CommandNode<ICompletionProvider> a(int var0) {
         CommandNode<ICompletionProvider> var1 = this.c.get(var0);
         if (var1 != null) {
            return var1;
         } else {
            PacketPlayOutCommands.b var2 = this.b.get(var0);
            CommandNode<ICompletionProvider> var3;
            if (var2.a == null) {
               var3 = new RootCommandNode();
            } else {
               ArgumentBuilder<ICompletionProvider, ?> var4 = var2.a.a(this.a);
               if ((var2.b & 8) != 0) {
                  var4.redirect(this.a(var2.c));
               }

               if ((var2.b & 4) != 0) {
                  var4.executes(var0x -> 0);
               }

               var3 = var4.build();
            }

            this.c.set(var0, var3);

            for(int var7 : var2.d) {
               CommandNode<ICompletionProvider> var8 = this.a(var7);
               if (!(var8 instanceof RootCommandNode)) {
                  var3.addChild(var8);
               }
            }

            return var3;
         }
      }
   }

   interface e {
      ArgumentBuilder<ICompletionProvider, ?> a(CommandBuildContext var1);

      void a(PacketDataSerializer var1);
   }
}
