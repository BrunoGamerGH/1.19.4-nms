package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Locale;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CustomFunction;
import net.minecraft.commands.ICommandListener;
import net.minecraft.commands.arguments.item.ArgumentTag;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.CustomFunctionData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TimeRange;
import net.minecraft.util.profiling.MethodProfilerResults;
import org.slf4j.Logger;

public class CommandDebug {
   private static final Logger a = LogUtils.getLogger();
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.debug.notRunning"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.debug.alreadyRunning"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "debug"
                     )
                     .requires(var0x -> var0x.c(3)))
                  .then(net.minecraft.commands.CommandDispatcher.a("start").executes(var0x -> a((CommandListenerWrapper)var0x.getSource()))))
               .then(net.minecraft.commands.CommandDispatcher.a("stop").executes(var0x -> b((CommandListenerWrapper)var0x.getSource()))))
            .then(
               ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("function").requires(var0x -> var0x.c(3)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("name", ArgumentTag.a())
                        .suggests(CommandFunction.a)
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentTag.a(var0x, "name")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0) throws CommandSyntaxException {
      MinecraftServer var1 = var0.l();
      if (var1.bc()) {
         throw c.create();
      } else {
         var1.bd();
         var0.a(IChatBaseComponent.c("commands.debug.started"), true);
         return 0;
      }
   }

   private static int b(CommandListenerWrapper var0) throws CommandSyntaxException {
      MinecraftServer var1 = var0.l();
      if (!var1.bc()) {
         throw b.create();
      } else {
         MethodProfilerResults var2 = var1.be();
         double var3 = (double)var2.g() / (double)TimeRange.a;
         double var5 = (double)var2.f() / var3;
         var0.a(
            IChatBaseComponent.a("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", var3), var2.f(), String.format(Locale.ROOT, "%.2f", var5)), true
         );
         return (int)var5;
      }
   }

   private static int a(CommandListenerWrapper var0, Collection<CustomFunction> var1) {
      int var2 = 0;
      MinecraftServer var3 = var0.l();
      String var4 = "debug-trace-" + SystemUtils.e() + ".txt";

      try {
         Path var5 = var3.c("debug").toPath();
         Files.createDirectories(var5);

         try (Writer var6 = Files.newBufferedWriter(var5.resolve(var4), StandardCharsets.UTF_8)) {
            PrintWriter var7 = new PrintWriter(var6);

            for(CustomFunction var9 : var1) {
               var7.println(var9.a());
               CommandDebug.a var10 = new CommandDebug.a(var7);
               var2 += var0.l().aA().a(var9, var0.a(var10).b(2), var10);
            }
         }
      } catch (IOException | UncheckedIOException var13) {
         a.warn("Tracing failed", var13);
         var0.b(IChatBaseComponent.c("commands.debug.function.traceFailed"));
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.debug.function.success.single", var2, var1.iterator().next().a(), var4), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.debug.function.success.multiple", var2, var1.size(), var4), true);
      }

      return var2;
   }

   static class a implements CustomFunctionData.c, ICommandListener {
      public static final int b = 1;
      private final PrintWriter c;
      private int d;
      private boolean e;

      a(PrintWriter var0) {
         this.c = var0;
      }

      private void a(int var0) {
         this.b(var0);
         this.d = var0;
      }

      private void b(int var0) {
         for(int var1 = 0; var1 < var0 + 1; ++var1) {
            this.c.write("    ");
         }
      }

      private void e() {
         if (this.e) {
            this.c.println();
            this.e = false;
         }
      }

      @Override
      public void a(int var0, String var1) {
         this.e();
         this.a(var0);
         this.c.print("[C] ");
         this.c.print(var1);
         this.e = true;
      }

      @Override
      public void a(int var0, String var1, int var2) {
         if (this.e) {
            this.c.print(" -> ");
            this.c.println(var2);
            this.e = false;
         } else {
            this.a(var0);
            this.c.print("[R = ");
            this.c.print(var2);
            this.c.print("] ");
            this.c.println(var1);
         }
      }

      @Override
      public void a(int var0, MinecraftKey var1, int var2) {
         this.e();
         this.a(var0);
         this.c.print("[F] ");
         this.c.print(var1);
         this.c.print(" size=");
         this.c.println(var2);
      }

      @Override
      public void b(int var0, String var1) {
         this.e();
         this.a(var0 + 1);
         this.c.print("[E] ");
         this.c.print(var1);
      }

      @Override
      public void a(IChatBaseComponent var0) {
         this.e();
         this.b(this.d + 1);
         this.c.print("[M] ");
         this.c.println(var0.getString());
      }

      @Override
      public boolean d_() {
         return true;
      }

      @Override
      public boolean j_() {
         return true;
      }

      @Override
      public boolean M_() {
         return false;
      }

      @Override
      public boolean e_() {
         return true;
      }
   }
}
