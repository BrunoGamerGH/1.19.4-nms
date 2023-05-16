package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.EnumBlockRotation;

public class GameTestHarnessRegistry {
   private static final Collection<GameTestHarnessTestFunction> a = Lists.newArrayList();
   private static final Set<String> b = Sets.newHashSet();
   private static final Map<String, Consumer<WorldServer>> c = Maps.newHashMap();
   private static final Map<String, Consumer<WorldServer>> d = Maps.newHashMap();
   private static final Collection<GameTestHarnessTestFunction> e = Sets.newHashSet();

   public static void a(Class<?> var0) {
      Arrays.stream(var0.getDeclaredMethods()).forEach(GameTestHarnessRegistry::a);
   }

   public static void a(Method var0) {
      String var1 = var0.getDeclaringClass().getSimpleName();
      GameTest var2 = var0.getAnnotation(GameTest.class);
      if (var2 != null) {
         a.add(c(var0));
         b.add(var1);
      }

      GameTestGenerator var3 = var0.getAnnotation(GameTestGenerator.class);
      if (var3 != null) {
         a.addAll(b(var0));
         b.add(var1);
      }

      a(var0, BeforeBatch.class, BeforeBatch::a, c);
      a(var0, AfterBatch.class, AfterBatch::a, d);
   }

   private static <T extends Annotation> void a(Method var0, Class<T> var1, Function<T, String> var2, Map<String, Consumer<WorldServer>> var3) {
      T var4 = var0.getAnnotation(var1);
      if (var4 != null) {
         String var5 = var2.apply(var4);
         Consumer<WorldServer> var6 = var3.putIfAbsent(var5, d(var0));
         if (var6 != null) {
            throw new RuntimeException("Hey, there should only be one " + var1 + " method per batch. Batch '" + var5 + "' has more than one!");
         }
      }
   }

   public static Collection<GameTestHarnessTestFunction> a(String var0) {
      return a.stream().filter(var1 -> a(var1, var0)).collect(Collectors.toList());
   }

   public static Collection<GameTestHarnessTestFunction> a() {
      return a;
   }

   public static Collection<String> b() {
      return b;
   }

   public static boolean b(String var0) {
      return b.contains(var0);
   }

   @Nullable
   public static Consumer<WorldServer> c(String var0) {
      return c.get(var0);
   }

   @Nullable
   public static Consumer<WorldServer> d(String var0) {
      return d.get(var0);
   }

   public static Optional<GameTestHarnessTestFunction> e(String var0) {
      return a().stream().filter(var1 -> var1.a().equalsIgnoreCase(var0)).findFirst();
   }

   public static GameTestHarnessTestFunction f(String var0) {
      Optional<GameTestHarnessTestFunction> var1 = e(var0);
      if (!var1.isPresent()) {
         throw new IllegalArgumentException("Can't find the test function for " + var0);
      } else {
         return var1.get();
      }
   }

   private static Collection<GameTestHarnessTestFunction> b(Method var0) {
      try {
         Object var1 = var0.getDeclaringClass().newInstance();
         return (Collection<GameTestHarnessTestFunction>)var0.invoke(var1);
      } catch (ReflectiveOperationException var2) {
         throw new RuntimeException(var2);
      }
   }

   private static GameTestHarnessTestFunction c(Method var0) {
      GameTest var1 = var0.getAnnotation(GameTest.class);
      String var2 = var0.getDeclaringClass().getSimpleName();
      String var3 = var2.toLowerCase();
      String var4 = var3 + "." + var0.getName().toLowerCase();
      String var5 = var1.e().isEmpty() ? var4 : var3 + "." + var1.e();
      String var6 = var1.b();
      EnumBlockRotation var7 = GameTestHarnessStructures.a(var1.c());
      return new GameTestHarnessTestFunction(var6, var4, var5, var7, var1.a(), var1.f(), var1.d(), var1.h(), var1.g(), d(var0));
   }

   private static Consumer<?> d(Method var0) {
      return var1 -> {
         try {
            Object var2 = var0.getDeclaringClass().newInstance();
            var0.invoke(var2, var1);
         } catch (InvocationTargetException var3) {
            if (var3.getCause() instanceof RuntimeException) {
               throw (RuntimeException)var3.getCause();
            } else {
               throw new RuntimeException(var3.getCause());
            }
         } catch (ReflectiveOperationException var4) {
            throw new RuntimeException(var4);
         }
      };
   }

   private static boolean a(GameTestHarnessTestFunction var0, String var1) {
      return var0.a().toLowerCase().startsWith(var1.toLowerCase() + ".");
   }

   public static Collection<GameTestHarnessTestFunction> c() {
      return e;
   }

   public static void a(GameTestHarnessTestFunction var0) {
      e.add(var0);
   }

   public static void d() {
      e.clear();
   }
}
