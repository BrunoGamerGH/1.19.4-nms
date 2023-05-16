package net.minecraft.util.monitoring.jmx;

import com.mojang.logging.LogUtils;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public final class MinecraftServerBeans implements DynamicMBean {
   private static final Logger a = LogUtils.getLogger();
   private final MinecraftServer b;
   private final MBeanInfo c;
   private final Map<String, MinecraftServerBeans.a> d = Stream.of(
         new MinecraftServerBeans.a("tickTimes", this::b, "Historical tick times (ms)", long[].class),
         new MinecraftServerBeans.a("averageTickTime", this::a, "Current average tick time (ms)", Long.TYPE)
      )
      .collect(Collectors.toMap(var0x -> var0x.a, Function.identity()));

   private MinecraftServerBeans(MinecraftServer var0) {
      this.b = var0;
      MBeanAttributeInfo[] var1 = this.d.values().stream().map(MinecraftServerBeans.a::a).toArray(var0x -> new MBeanAttributeInfo[var0x]);
      this.c = new MBeanInfo(MinecraftServerBeans.class.getSimpleName(), "metrics for dedicated server", var1, null, null, new MBeanNotificationInfo[0]);
   }

   public static void a(MinecraftServer var0) {
      try {
         ManagementFactory.getPlatformMBeanServer().registerMBean(new MinecraftServerBeans(var0), new ObjectName("net.minecraft.server:type=Server"));
      } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException var2) {
         a.warn("Failed to initialise server as JMX bean", var2);
      }
   }

   private float a() {
      return this.b.aN();
   }

   private long[] b() {
      return this.b.k;
   }

   @Nullable
   @Override
   public Object getAttribute(String var0) {
      MinecraftServerBeans.a var1 = this.d.get(var0);
      return var1 == null ? null : var1.b.get();
   }

   @Override
   public void setAttribute(Attribute var0) {
   }

   @Override
   public AttributeList getAttributes(String[] var0) {
      List<Attribute> var1 = Arrays.stream(var0)
         .map(this.d::get)
         .filter(Objects::nonNull)
         .map(var0x -> new Attribute(var0x.a, var0x.b.get()))
         .collect(Collectors.toList());
      return new AttributeList(var1);
   }

   @Override
   public AttributeList setAttributes(AttributeList var0) {
      return new AttributeList();
   }

   @Nullable
   @Override
   public Object invoke(String var0, Object[] var1, String[] var2) {
      return null;
   }

   @Override
   public MBeanInfo getMBeanInfo() {
      return this.c;
   }

   static final class a {
      final String a;
      final Supplier<Object> b;
      private final String c;
      private final Class<?> d;

      a(String var0, Supplier<Object> var1, String var2, Class<?> var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      private MBeanAttributeInfo a() {
         return new MBeanAttributeInfo(this.a, this.d.getSimpleName(), this.c, true, false, false);
      }
   }
}
