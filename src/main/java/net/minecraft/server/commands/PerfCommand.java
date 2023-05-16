package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.FileUtils;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FileZipper;
import net.minecraft.util.TimeRange;
import net.minecraft.util.profiling.MethodProfilerResults;
import net.minecraft.util.profiling.MethodProfilerResultsEmpty;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import org.slf4j.Logger;

public class PerfCommand {
   private static final Logger a = LogUtils.getLogger();
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.perf.notRunning"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.perf.alreadyRunning"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("perf")
                  .requires(var0x -> var0x.c(4)))
               .then(net.minecraft.commands.CommandDispatcher.a("start").executes(var0x -> a((CommandListenerWrapper)var0x.getSource()))))
            .then(net.minecraft.commands.CommandDispatcher.a("stop").executes(var0x -> b((CommandListenerWrapper)var0x.getSource())))
      );
   }

   private static int a(CommandListenerWrapper var0) throws CommandSyntaxException {
      MinecraftServer var1 = var0.l();
      if (var1.aQ()) {
         throw c.create();
      } else {
         Consumer<MethodProfilerResults> var2 = var1x -> a(var0, var1x);
         Consumer<Path> var3 = var2x -> a(var0, var2x, var1);
         var1.a(var2, var3);
         var0.a(IChatBaseComponent.c("commands.perf.started"), false);
         return 0;
      }
   }

   private static int b(CommandListenerWrapper var0) throws CommandSyntaxException {
      MinecraftServer var1 = var0.l();
      if (!var1.aQ()) {
         throw b.create();
      } else {
         var1.aS();
         return 0;
      }
   }

   private static void a(CommandListenerWrapper var0, Path var1, MinecraftServer var2) {
      String var3 = String.format(Locale.ROOT, "%s-%s-%s", SystemUtils.e(), var2.aW().g(), SharedConstants.b().b());

      String var4;
      try {
         var4 = FileUtils.a(MetricsPersister.a, var3, ".zip");
      } catch (IOException var11) {
         var0.b(IChatBaseComponent.c("commands.perf.reportFailed"));
         a.error("Failed to create report name", var11);
         return;
      }

      try (FileZipper var5 = new FileZipper(MetricsPersister.a.resolve(var4))) {
         var5.a(Paths.get("system.txt"), var2.b(new SystemReport()).a());
         var5.a(var1);
      }

      try {
         org.apache.commons.io.FileUtils.forceDelete(var1.toFile());
      } catch (IOException var9) {
         a.warn("Failed to delete temporary profiling file {}", var1, var9);
      }

      var0.a(IChatBaseComponent.a("commands.perf.reportSaved", var4), false);
   }

   private static void a(CommandListenerWrapper var0, MethodProfilerResults var1) {
      if (var1 != MethodProfilerResultsEmpty.a) {
         int var2 = var1.f();
         double var3 = (double)var1.g() / (double)TimeRange.a;
         var0.a(
            IChatBaseComponent.a(
               "commands.perf.stopped", String.format(Locale.ROOT, "%.2f", var3), var2, String.format(Locale.ROOT, "%.2f", (double)var2 / var3)
            ),
            false
         );
      }
   }
}
