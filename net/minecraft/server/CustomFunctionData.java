package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CustomFunction;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.level.GameRules;

public class CustomFunctionData {
   private static final IChatBaseComponent a = IChatBaseComponent.c("commands.debug.function.noRecursion");
   private static final MinecraftKey b = new MinecraftKey("tick");
   private static final MinecraftKey c = new MinecraftKey("load");
   final MinecraftServer d;
   @Nullable
   private CustomFunctionData.a e;
   private List<CustomFunction> f = ImmutableList.of();
   private boolean g;
   private CustomFunctionManager h;

   public CustomFunctionData(MinecraftServer minecraftserver, CustomFunctionManager customfunctionmanager) {
      this.d = minecraftserver;
      this.h = customfunctionmanager;
      this.b(customfunctionmanager);
   }

   public int a() {
      return this.d.aK().c(GameRules.w);
   }

   public CommandDispatcher<CommandListenerWrapper> b() {
      return this.d.vanillaCommandDispatcher.a();
   }

   public void c() {
      if (this.g) {
         this.g = false;
         Collection<CustomFunction> collection = this.h.b(c);
         this.a(collection, c);
      }

      this.a(this.f, b);
   }

   private void a(Collection<CustomFunction> collection, MinecraftKey minecraftkey) {
      GameProfilerFiller gameprofilerfiller = this.d.aP();
      gameprofilerfiller.a(minecraftkey::toString);

      for(CustomFunction customfunction : collection) {
         this.a(customfunction, this.d());
      }

      this.d.aP().c();
   }

   public int a(CustomFunction customfunction, CommandListenerWrapper commandlistenerwrapper) {
      return this.a(customfunction, commandlistenerwrapper, null);
   }

   public int a(CustomFunction customfunction, CommandListenerWrapper commandlistenerwrapper, @Nullable CustomFunctionData.c customfunctiondata_c) {
      if (this.e != null) {
         if (customfunctiondata_c != null) {
            this.e.a(a.getString());
            return 0;
         } else {
            this.e.a(customfunction, commandlistenerwrapper);
            return 0;
         }
      } else {
         int i;
         try {
            this.e = new CustomFunctionData.a(customfunctiondata_c);
            i = this.e.b(customfunction, commandlistenerwrapper);
         } finally {
            this.e = null;
         }

         return i;
      }
   }

   public void a(CustomFunctionManager customfunctionmanager) {
      this.h = customfunctionmanager;
      this.b(customfunctionmanager);
   }

   private void b(CustomFunctionManager customfunctionmanager) {
      this.f = ImmutableList.copyOf(customfunctionmanager.b(b));
      this.g = true;
   }

   public CommandListenerWrapper d() {
      return this.d.aD().a(2).a();
   }

   public Optional<CustomFunction> a(MinecraftKey minecraftkey) {
      return this.h.a(minecraftkey);
   }

   public Collection<CustomFunction> b(MinecraftKey minecraftkey) {
      return this.h.b(minecraftkey);
   }

   public Iterable<MinecraftKey> e() {
      return this.h.a().keySet();
   }

   public Iterable<MinecraftKey> f() {
      return this.h.b();
   }

   private class a {
      private int b;
      @Nullable
      private final CustomFunctionData.c c;
      private final Deque<CustomFunctionData.b> d = Queues.newArrayDeque();
      private final List<CustomFunctionData.b> e = Lists.newArrayList();

      a(@Nullable CustomFunctionData.c customfunctiondata_c) {
         this.c = customfunctiondata_c;
      }

      void a(CustomFunction customfunction, CommandListenerWrapper commandlistenerwrapper) {
         int i = CustomFunctionData.this.a();
         if (this.d.size() + this.e.size() < i) {
            this.e.add(new CustomFunctionData.b(commandlistenerwrapper, this.b, new CustomFunction.d(customfunction)));
         }
      }

      int b(CustomFunction customfunction, CommandListenerWrapper commandlistenerwrapper) {
         int i = CustomFunctionData.this.a();
         int j = 0;
         CustomFunction.c[] acustomfunction_c = customfunction.b();

         for(int k = acustomfunction_c.length - 1; k >= 0; --k) {
            this.d.push(new CustomFunctionData.b(commandlistenerwrapper, 0, acustomfunction_c[k]));
         }

         while(!this.d.isEmpty()) {
            try {
               CustomFunctionData.b customfunctiondata_b = this.d.removeFirst();
               GameProfilerFiller gameprofilerfiller = CustomFunctionData.this.d.aP();
               gameprofilerfiller.a(customfunctiondata_b::toString);
               this.b = customfunctiondata_b.b;
               customfunctiondata_b.a(CustomFunctionData.this, this.d, i, this.c);
               if (!this.e.isEmpty()) {
                  List list = Lists.reverse(this.e);
                  Deque deque = this.d;
                  list.forEach(deque::addFirst);
                  this.e.clear();
               }
            } finally {
               CustomFunctionData.this.d.aP().c();
            }

            if (++j >= i) {
               return j;
            }
         }

         return j;
      }

      public void a(String s) {
         if (this.c != null) {
            this.c.b(this.b, s);
         }
      }
   }

   public static class b {
      private final CommandListenerWrapper a;
      final int b;
      private final CustomFunction.c c;

      public b(CommandListenerWrapper commandlistenerwrapper, int i, CustomFunction.c customfunction_c) {
         this.a = commandlistenerwrapper;
         this.b = i;
         this.c = customfunction_c;
      }

      public void a(CustomFunctionData customfunctiondata, Deque<CustomFunctionData.b> deque, int i, @Nullable CustomFunctionData.c customfunctiondata_c) {
         try {
            this.c.execute(customfunctiondata, this.a, deque, i, this.b, customfunctiondata_c);
         } catch (CommandSyntaxException var6) {
            if (customfunctiondata_c != null) {
               customfunctiondata_c.b(this.b, var6.getRawMessage().getString());
            }
         } catch (Exception var7) {
            if (customfunctiondata_c != null) {
               customfunctiondata_c.b(this.b, var7.getMessage());
            }
         }
      }

      @Override
      public String toString() {
         return this.c.toString();
      }
   }

   public interface c {
      void a(int var1, String var2);

      void a(int var1, String var2, int var3);

      void b(int var1, String var2);

      void a(int var1, MinecraftKey var2, int var3);
   }
}
