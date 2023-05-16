package net.minecraft;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

public class SystemReport {
   public static final long a = 1048576L;
   private static final long b = 1000000000L;
   private static final Logger c = LogUtils.getLogger();
   private static final String d = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
   private static final String e = System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
   private static final String f = System.getProperty("java.vm.name")
      + " ("
      + System.getProperty("java.vm.info")
      + "), "
      + System.getProperty("java.vm.vendor");
   private final Map<String, String> g = Maps.newLinkedHashMap();

   public SystemReport() {
      this.a("Minecraft Version", SharedConstants.b().c());
      this.a("Minecraft Version ID", SharedConstants.b().b());
      this.a("Operating System", d);
      this.a("Java Version", e);
      this.a("Java VM Version", f);
      this.a("Memory", () -> {
         Runtime var0 = Runtime.getRuntime();
         long var1 = var0.maxMemory();
         long var3 = var0.totalMemory();
         long var5 = var0.freeMemory();
         long var7 = var1 / 1048576L;
         long var9 = var3 / 1048576L;
         long var11 = var5 / 1048576L;
         return var5 + " bytes (" + var11 + " MiB) / " + var3 + " bytes (" + var9 + " MiB) up to " + var1 + " bytes (" + var7 + " MiB)";
      });
      this.a("CPUs", () -> String.valueOf(Runtime.getRuntime().availableProcessors()));
      this.a("hardware", () -> this.a(new SystemInfo()));
      this.a("JVM Flags", () -> {
         List<String> var0 = SystemUtils.j().collect(Collectors.toList());
         return String.format(Locale.ROOT, "%d total; %s", var0.size(), String.join(" ", var0));
      });
   }

   public void a(String var0, String var1) {
      this.g.put(var0, var1);
   }

   public void a(String var0, Supplier<String> var1) {
      try {
         this.a(var0, var1.get());
      } catch (Exception var4) {
         c.warn("Failed to get system info for {}", var0, var4);
         this.a(var0, "ERR");
      }
   }

   private void a(SystemInfo var0) {
      HardwareAbstractionLayer var1 = var0.getHardware();
      this.a("processor", () -> this.a(var1.getProcessor()));
      this.a("graphics", () -> this.b(var1.getGraphicsCards()));
      this.a("memory", () -> this.a(var1.getMemory()));
   }

   private void a(String var0, Runnable var1) {
      try {
         var1.run();
      } catch (Throwable var4) {
         c.warn("Failed retrieving info for group {}", var0, var4);
      }
   }

   private void a(List<PhysicalMemory> var0) {
      int var1 = 0;

      for(PhysicalMemory var3 : var0) {
         String var4 = String.format(Locale.ROOT, "Memory slot #%d ", var1++);
         this.a(var4 + "capacity (MB)", () -> String.format(Locale.ROOT, "%.2f", (float)var3.getCapacity() / 1048576.0F));
         this.a(var4 + "clockSpeed (GHz)", () -> String.format(Locale.ROOT, "%.2f", (float)var3.getClockSpeed() / 1.0E9F));
         this.a(var4 + "type", var3::getMemoryType);
      }
   }

   private void a(VirtualMemory var0) {
      this.a("Virtual memory max (MB)", () -> String.format(Locale.ROOT, "%.2f", (float)var0.getVirtualMax() / 1048576.0F));
      this.a("Virtual memory used (MB)", () -> String.format(Locale.ROOT, "%.2f", (float)var0.getVirtualInUse() / 1048576.0F));
      this.a("Swap memory total (MB)", () -> String.format(Locale.ROOT, "%.2f", (float)var0.getSwapTotal() / 1048576.0F));
      this.a("Swap memory used (MB)", () -> String.format(Locale.ROOT, "%.2f", (float)var0.getSwapUsed() / 1048576.0F));
   }

   private void a(GlobalMemory var0) {
      this.a("physical memory", () -> this.a(var0.getPhysicalMemory()));
      this.a("virtual memory", () -> this.a(var0.getVirtualMemory()));
   }

   private void b(List<GraphicsCard> var0) {
      int var1 = 0;

      for(GraphicsCard var3 : var0) {
         String var4 = String.format(Locale.ROOT, "Graphics card #%d ", var1++);
         this.a(var4 + "name", var3::getName);
         this.a(var4 + "vendor", var3::getVendor);
         this.a(var4 + "VRAM (MB)", () -> String.format(Locale.ROOT, "%.2f", (float)var3.getVRam() / 1048576.0F));
         this.a(var4 + "deviceId", var3::getDeviceId);
         this.a(var4 + "versionInfo", var3::getVersionInfo);
      }
   }

   private void a(CentralProcessor var0) {
      ProcessorIdentifier var1 = var0.getProcessorIdentifier();
      this.a("Processor Vendor", var1::getVendor);
      this.a("Processor Name", var1::getName);
      this.a("Identifier", var1::getIdentifier);
      this.a("Microarchitecture", var1::getMicroarchitecture);
      this.a("Frequency (GHz)", () -> String.format(Locale.ROOT, "%.2f", (float)var1.getVendorFreq() / 1.0E9F));
      this.a("Number of physical packages", () -> String.valueOf(var0.getPhysicalPackageCount()));
      this.a("Number of physical CPUs", () -> String.valueOf(var0.getPhysicalProcessorCount()));
      this.a("Number of logical CPUs", () -> String.valueOf(var0.getLogicalProcessorCount()));
   }

   public void a(StringBuilder var0) {
      var0.append("-- ").append("System Details").append(" --\n");
      var0.append("Details:");
      this.g.forEach((var1x, var2) -> {
         var0.append("\n\t");
         var0.append(var1x);
         var0.append(": ");
         var0.append(var2);
      });
   }

   public String a() {
      return this.g
         .entrySet()
         .stream()
         .map(var0 -> (String)var0.getKey() + ": " + (String)var0.getValue())
         .collect(Collectors.joining(System.lineSeparator()));
   }
}
