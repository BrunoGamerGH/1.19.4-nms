package net.minecraft;

import com.google.common.base.Ticker;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SingleKeyCache;
import net.minecraft.util.TimeSource;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.world.level.block.state.properties.IBlockState;
import org.slf4j.Logger;

public class SystemUtils {
   static final Logger e = LogUtils.getLogger();
   private static final int f = 255;
   private static final String g = "max.bg.threads";
   private static final AtomicInteger h = new AtomicInteger(1);
   private static final ExecutorService i = b("Main");
   private static final ExecutorService j = n();
   private static final DateTimeFormatter k = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
   public static TimeSource.a a = System::nanoTime;
   public static final Ticker b = new Ticker() {
      public long read() {
         return SystemUtils.a.getAsLong();
      }
   };
   public static final UUID c = new UUID(0L, 0L);
   public static final FileSystemProvider d = FileSystemProvider.installedProviders()
      .stream()
      .filter(var0 -> var0.getScheme().equalsIgnoreCase("jar"))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("No jar file system provider found"));
   private static Consumer<String> l = var0 -> {
   };

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> a() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T extends Comparable<T>> String a(IBlockState<T> var0, Object var1) {
      return var0.a((T)var1);
   }

   public static String a(String var0, @Nullable MinecraftKey var1) {
      return var1 == null ? var0 + ".unregistered_sadface" : var0 + "." + var1.b() + "." + var1.a().replace('/', '.');
   }

   public static long b() {
      return c() / 1000000L;
   }

   public static long c() {
      return a.getAsLong();
   }

   public static long d() {
      return Instant.now().toEpochMilli();
   }

   public static String e() {
      return k.format(ZonedDateTime.now());
   }

   private static ExecutorService b(String var0) {
      int var1 = MathHelper.a(Runtime.getRuntime().availableProcessors() - 1, 1, m());
      ExecutorService var2;
      if (var1 <= 0) {
         var2 = MoreExecutors.newDirectExecutorService();
      } else {
         var2 = new ForkJoinPool(var1, var1x -> {
            ForkJoinWorkerThread var2x = new ForkJoinWorkerThread(var1x) {
               @Override
               protected void onTermination(Throwable var0) {
                  if (var0 != null) {
                     SystemUtils.e.warn("{} died", this.getName(), var0);
                  } else {
                     SystemUtils.e.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(var0);
               }
            };
            var2x.setName("Worker-" + var0 + "-" + h.getAndIncrement());
            return var2x;
         }, SystemUtils::a, true);
      }

      return var2;
   }

   private static int m() {
      String var0 = System.getProperty("max.bg.threads");
      if (var0 != null) {
         try {
            int var1 = Integer.parseInt(var0);
            if (var1 >= 1 && var1 <= 255) {
               return var1;
            }

            e.error("Wrong {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{"max.bg.threads", var0, 255});
         } catch (NumberFormatException var2) {
            e.error("Could not parse {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{"max.bg.threads", var0, 255});
         }
      }

      return 255;
   }

   public static ExecutorService f() {
      return i;
   }

   public static ExecutorService g() {
      return j;
   }

   public static void h() {
      a(i);
      a(j);
   }

   private static void a(ExecutorService var0) {
      var0.shutdown();

      boolean var1;
      try {
         var1 = var0.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var3) {
         var1 = false;
      }

      if (!var1) {
         var0.shutdownNow();
      }
   }

   private static ExecutorService n() {
      return Executors.newCachedThreadPool(var0 -> {
         Thread var1 = new Thread(var0);
         var1.setName("IO-Worker-" + h.getAndIncrement());
         var1.setUncaughtExceptionHandler(SystemUtils::a);
         return var1;
      });
   }

   public static void a(Throwable var0) {
      throw var0 instanceof RuntimeException ? (RuntimeException)var0 : new RuntimeException(var0);
   }

   private static void a(Thread var0, Throwable var1) {
      b(var1);
      if (var1 instanceof CompletionException) {
         var1 = var1.getCause();
      }

      if (var1 instanceof ReportedException) {
         DispenserRegistry.a(((ReportedException)var1).a().e());
         System.exit(-1);
      }

      e.error(String.format(Locale.ROOT, "Caught exception in thread %s", var0), var1);
   }

   @Nullable
   public static Type<?> a(TypeReference var0, String var1) {
      return !SharedConstants.aN ? null : b(var0, var1);
   }

   @Nullable
   private static Type<?> b(TypeReference var0, String var1) {
      Type<?> var2 = null;

      try {
         var2 = DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(SharedConstants.b().d().c())).getChoiceType(var0, var1);
      } catch (IllegalArgumentException var4) {
         e.error("No data fixer registered for {}", var1);
         if (SharedConstants.aO) {
            throw var4;
         }
      }

      return var2;
   }

   public static Runnable a(String var0, Runnable var1) {
      return SharedConstants.aO ? () -> {
         Thread var2 = Thread.currentThread();
         String var3 = var2.getName();
         var2.setName(var0);

         try {
            var1.run();
         } finally {
            var2.setName(var3);
         }
      } : var1;
   }

   public static <V> Supplier<V> a(String var0, Supplier<V> var1) {
      return SharedConstants.aO ? () -> {
         Thread var2 = Thread.currentThread();
         String var3 = var2.getName();
         var2.setName(var0);

         Object var4;
         try {
            var4 = var1.get();
         } finally {
            var2.setName(var3);
         }

         return (V)var4;
      } : var1;
   }

   public static SystemUtils.OS i() {
      String var0 = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (var0.contains("win")) {
         return SystemUtils.OS.c;
      } else if (var0.contains("mac")) {
         return SystemUtils.OS.d;
      } else if (var0.contains("solaris")) {
         return SystemUtils.OS.b;
      } else if (var0.contains("sunos")) {
         return SystemUtils.OS.b;
      } else if (var0.contains("linux")) {
         return SystemUtils.OS.a;
      } else {
         return var0.contains("unix") ? SystemUtils.OS.a : SystemUtils.OS.e;
      }
   }

   public static Stream<String> j() {
      RuntimeMXBean var0 = ManagementFactory.getRuntimeMXBean();
      return var0.getInputArguments().stream().filter(var0x -> var0x.startsWith("-X"));
   }

   public static <T> T a(List<T> var0) {
      return var0.get(var0.size() - 1);
   }

   public static <T> T a(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> var2 = var0.iterator();
      T var3 = var2.next();
      if (var1 != null) {
         T var4 = var3;

         while(var4 != var1) {
            if (var2.hasNext()) {
               var4 = var2.next();
            }
         }

         if (var2.hasNext()) {
            return var2.next();
         }
      }

      return var3;
   }

   public static <T> T b(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> var2 = var0.iterator();

      T var3;
      T var4;
      for(var3 = null; var2.hasNext(); var3 = var4) {
         var4 = var2.next();
         if (var4 == var1) {
            if (var3 == null) {
               var3 = (T)(var2.hasNext() ? Iterators.getLast(var2) : var1);
            }
            break;
         }
      }

      return var3;
   }

   public static <T> T a(Supplier<T> var0) {
      return var0.get();
   }

   public static <T> T a(T var0, Consumer<T> var1) {
      var1.accept(var0);
      return var0;
   }

   public static <K> Strategy<K> k() {
      return SystemUtils.IdentityHashingStrategy.a;
   }

   public static <V> CompletableFuture<List<V>> b(List<? extends CompletableFuture<V>> var0) {
      if (var0.isEmpty()) {
         return CompletableFuture.completedFuture(List.of());
      } else if (var0.size() == 1) {
         return var0.get(0).thenApply(List::of);
      } else {
         CompletableFuture<Void> var1 = CompletableFuture.allOf(var0.toArray(new CompletableFuture[0]));
         return var1.thenApply(var1x -> var0.stream().map(CompletableFuture::join).toList());
      }
   }

   public static <V> CompletableFuture<List<V>> c(List<? extends CompletableFuture<? extends V>> var0) {
      CompletableFuture<List<V>> var1 = new CompletableFuture<>();
      return a(var0, var1::completeExceptionally).applyToEither(var1, Function.identity());
   }

   public static <V> CompletableFuture<List<V>> d(List<? extends CompletableFuture<? extends V>> var0) {
      CompletableFuture<List<V>> var1 = new CompletableFuture<>();
      return a(var0, var2 -> {
         if (var1.completeExceptionally(var2)) {
            for(CompletableFuture<? extends V> var4 : var0) {
               var4.cancel(true);
            }
         }
      }).applyToEither(var1, Function.identity());
   }

   private static <V> CompletableFuture<List<V>> a(List<? extends CompletableFuture<? extends V>> var0, Consumer<Throwable> var1) {
      List<V> var2 = Lists.newArrayListWithCapacity(var0.size());
      CompletableFuture<?>[] var3 = new CompletableFuture[var0.size()];
      var0.forEach(var3x -> {
         int var4 = var2.size();
         var2.add((V)null);
         var3[var4] = var3x.whenComplete((var3xx, var4x) -> {
            if (var4x != null) {
               var1.accept(var4x);
            } else {
               var2.set(var4, var3xx);
            }
         });
      });
      return CompletableFuture.allOf(var3).thenApply(var1x -> var2);
   }

   public static <T> Optional<T> a(Optional<T> var0, Consumer<T> var1, Runnable var2) {
      if (var0.isPresent()) {
         var1.accept(var0.get());
      } else {
         var2.run();
      }

      return var0;
   }

   public static <T> Supplier<T> a(Supplier<T> var0, Supplier<String> var1) {
      return var0;
   }

   public static Runnable a(Runnable var0, Supplier<String> var1) {
      return var0;
   }

   public static void a(String var0) {
      e.error(var0);
      if (SharedConstants.aO) {
         c(var0);
      }
   }

   public static void a(String var0, Throwable var1) {
      e.error(var0, var1);
      if (SharedConstants.aO) {
         c(var0);
      }
   }

   public static <T extends Throwable> T b(T var0) {
      if (SharedConstants.aO) {
         e.error("Trying to throw a fatal exception, pausing in IDE", var0);
         c(var0.getMessage());
      }

      return var0;
   }

   public static void a(Consumer<String> var0) {
      l = var0;
   }

   private static void c(String var0) {
      Instant var1 = Instant.now();
      e.warn("Did you remember to set a breakpoint here?");
      boolean var2 = Duration.between(var1, Instant.now()).toMillis() > 500L;
      if (!var2) {
         l.accept(var0);
      }
   }

   public static String c(Throwable var0) {
      if (var0.getCause() != null) {
         return c(var0.getCause());
      } else {
         return var0.getMessage() != null ? var0.getMessage() : var0.toString();
      }
   }

   public static <T> T a(T[] var0, RandomSource var1) {
      return var0[var1.a(var0.length)];
   }

   public static int a(int[] var0, RandomSource var1) {
      return var0[var1.a(var0.length)];
   }

   public static <T> T a(List<T> var0, RandomSource var1) {
      return var0.get(var1.a(var0.size()));
   }

   public static <T> Optional<T> b(List<T> var0, RandomSource var1) {
      return var0.isEmpty() ? Optional.empty() : Optional.of(a(var0, var1));
   }

   private static BooleanSupplier a(final Path var0, final Path var1) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            try {
               Files.move(var0, var1);
               return true;
            } catch (IOException var2) {
               SystemUtils.e.error("Failed to rename", var2);
               return false;
            }
         }

         @Override
         public String toString() {
            return "rename " + var0 + " to " + var1;
         }
      };
   }

   private static BooleanSupplier a(final Path var0) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(var0);
               return true;
            } catch (IOException var2) {
               SystemUtils.e.warn("Failed to delete", var2);
               return false;
            }
         }

         @Override
         public String toString() {
            return "delete old " + var0;
         }
      };
   }

   private static BooleanSupplier b(final Path var0) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return !Files.exists(var0);
         }

         @Override
         public String toString() {
            return "verify that " + var0 + " is deleted";
         }
      };
   }

   private static BooleanSupplier c(final Path var0) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return Files.isRegularFile(var0);
         }

         @Override
         public String toString() {
            return "verify that " + var0 + " is present";
         }
      };
   }

   private static boolean a(BooleanSupplier... var0) {
      for(BooleanSupplier var4 : var0) {
         if (!var4.getAsBoolean()) {
            e.warn("Failed to execute {}", var4);
            return false;
         }
      }

      return true;
   }

   private static boolean a(int var0, String var1, BooleanSupplier... var2) {
      for(int var3 = 0; var3 < var0; ++var3) {
         if (a(var2)) {
            return true;
         }

         e.error("Failed to {}, retrying {}/{}", new Object[]{var1, var3, var0});
      }

      e.error("Failed to {}, aborting, progress might be lost", var1);
      return false;
   }

   public static void a(File var0, File var1, File var2) {
      a(var0.toPath(), var1.toPath(), var2.toPath());
   }

   public static void a(Path var0, Path var1, Path var2) {
      a(var0, var1, var2, false);
   }

   public static void a(File var0, File var1, File var2, boolean var3) {
      a(var0.toPath(), var1.toPath(), var2.toPath(), var3);
   }

   public static void a(Path var0, Path var1, Path var2, boolean var3) {
      int var4 = 10;
      if (!Files.exists(var0) || a(10, "create backup " + var2, a(var2), a(var0, var2), c(var2))) {
         if (a(10, "remove old " + var0, a(var0), b(var0))) {
            if (!a(10, "replace " + var0 + " with " + var1, a(var1, var0), c(var0)) && !var3) {
               a(10, "restore " + var0 + " from " + var2, a(var2, var0), c(var0));
            }
         }
      }
   }

   public static int a(String var0, int var1, int var2) {
      int var3 = var0.length();
      if (var2 >= 0) {
         for(int var4 = 0; var1 < var3 && var4 < var2; ++var4) {
            if (Character.isHighSurrogate(var0.charAt(var1++)) && var1 < var3 && Character.isLowSurrogate(var0.charAt(var1))) {
               ++var1;
            }
         }
      } else {
         for(int var4 = var2; var1 > 0 && var4 < 0; ++var4) {
            --var1;
            if (Character.isLowSurrogate(var0.charAt(var1)) && var1 > 0 && Character.isHighSurrogate(var0.charAt(var1 - 1))) {
               --var1;
            }
         }
      }

      return var1;
   }

   public static Consumer<String> a(String var0, Consumer<String> var1) {
      return var2 -> var1.accept(var0 + var2);
   }

   public static DataResult<int[]> a(IntStream var0, int var1) {
      int[] var2 = var0.limit((long)(var1 + 1)).toArray();
      if (var2.length != var1) {
         Supplier<String> var3 = () -> "Input is not a list of " + var1 + " ints";
         return var2.length >= var1 ? DataResult.error(var3, Arrays.copyOf(var2, var1)) : DataResult.error(var3);
      } else {
         return DataResult.success(var2);
      }
   }

   public static <T> DataResult<List<T>> a(List<T> var0, int var1) {
      if (var0.size() != var1) {
         Supplier<String> var2 = () -> "Input is not a list of " + var1 + " elements";
         return var0.size() >= var1 ? DataResult.error(var2, var0.subList(0, var1)) : DataResult.error(var2);
      } else {
         return DataResult.success(var0);
      }
   }

   public static void l() {
      Thread var0 = new Thread("Timer hack thread") {
         @Override
         public void run() {
            while(true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  SystemUtils.e.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      var0.setDaemon(true);
      var0.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(e));
      var0.start();
   }

   public static void b(Path var0, Path var1, Path var2) throws IOException {
      Path var3 = var0.relativize(var2);
      Path var4 = var1.resolve(var3);
      Files.copy(var2, var4);
   }

   public static String a(String var0, CharPredicate var1) {
      return var0.toLowerCase(Locale.ROOT)
         .chars()
         .mapToObj(var1x -> var1.test((char)var1x) ? Character.toString((char)var1x) : "_")
         .collect(Collectors.joining());
   }

   public static <K, V> SingleKeyCache<K, V> a(Function<K, V> var0) {
      return new SingleKeyCache<>(var0);
   }

   public static <T, R> Function<T, R> b(final Function<T, R> var0) {
      return new Function<T, R>() {
         private final Map<T, R> b = new ConcurrentHashMap<>();

         @Override
         public R apply(T var0x) {
            return this.b.computeIfAbsent(var0, var0);
         }

         @Override
         public String toString() {
            return "memoize/1[function=" + var0 + ", size=" + this.b.size() + "]";
         }
      };
   }

   public static <T, U, R> BiFunction<T, U, R> a(final BiFunction<T, U, R> var0) {
      return new BiFunction<T, U, R>() {
         private final Map<Pair<T, U>, R> b = new ConcurrentHashMap<>();

         @Override
         public R apply(T var0x, U var1) {
            return this.b.computeIfAbsent(Pair.of(var0, var1), var1x -> var0.apply((T)var1x.getFirst(), (U)var1x.getSecond()));
         }

         @Override
         public String toString() {
            return "memoize/2[function=" + var0 + ", size=" + this.b.size() + "]";
         }
      };
   }

   public static <T> List<T> a(Stream<T> var0, RandomSource var1) {
      ObjectArrayList<T> var2 = var0.collect(ObjectArrayList.toList());
      b(var2, var1);
      return var2;
   }

   public static IntArrayList a(IntStream var0, RandomSource var1) {
      IntArrayList var2 = IntArrayList.wrap(var0.toArray());
      int var3 = var2.size();

      for(int var4 = var3; var4 > 1; --var4) {
         int var5 = var1.a(var4);
         var2.set(var4 - 1, var2.set(var5, var2.getInt(var4 - 1)));
      }

      return var2;
   }

   public static <T> List<T> b(T[] var0, RandomSource var1) {
      ObjectArrayList<T> var2 = new ObjectArrayList(var0);
      b(var2, var1);
      return var2;
   }

   public static <T> List<T> a(ObjectArrayList<T> var0, RandomSource var1) {
      ObjectArrayList<T> var2 = new ObjectArrayList(var0);
      b(var2, var1);
      return var2;
   }

   public static <T> void b(ObjectArrayList<T> var0, RandomSource var1) {
      int var2 = var0.size();

      for(int var3 = var2; var3 > 1; --var3) {
         int var4 = var1.a(var3);
         var0.set(var3 - 1, var0.set(var4, var0.get(var3 - 1)));
      }
   }

   public static <T> CompletableFuture<T> c(Function<Executor, CompletableFuture<T>> var0) {
      return a(var0, CompletableFuture::isDone);
   }

   public static <T> T a(Function<Executor, T> var0, Predicate<T> var1) {
      BlockingQueue<Runnable> var2 = new LinkedBlockingQueue<>();
      T var3 = var0.apply(var2::add);

      while(!var1.test(var3)) {
         try {
            Runnable var4 = var2.poll(100L, TimeUnit.MILLISECONDS);
            if (var4 != null) {
               var4.run();
            }
         } catch (InterruptedException var5) {
            e.warn("Interrupted wait");
            break;
         }
      }

      int var4 = var2.size();
      if (var4 > 0) {
         e.warn("Tasks left in queue: {}", var4);
      }

      return var3;
   }

   public static <T> ToIntFunction<T> e(List<T> var0) {
      return a(var0, Object2IntOpenHashMap::new);
   }

   public static <T> ToIntFunction<T> a(List<T> var0, IntFunction<Object2IntMap<T>> var1) {
      Object2IntMap<T> var2 = (Object2IntMap)var1.apply(var0.size());

      for(int var3 = 0; var3 < var0.size(); ++var3) {
         var2.put(var0.get(var3), var3);
      }

      return var2;
   }

   public static <T, E extends Exception> T a(DataResult<T> var0, Function<String, E> var1) throws E {
      Optional<PartialResult<T>> var2 = var0.error();
      if (var2.isPresent()) {
         throw var1.apply(((PartialResult)var2.get()).message());
      } else {
         return (T)var0.result().orElseThrow();
      }
   }

   static enum IdentityHashingStrategy implements Strategy<Object> {
      a;

      public int hashCode(Object var0) {
         return System.identityHashCode(var0);
      }

      public boolean equals(Object var0, Object var1) {
         return var0 == var1;
      }
   }

   public static enum OS {
      a("linux"),
      b("solaris"),
      c("windows") {
         @Override
         protected String[] b(URL var0) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", var0.toString()};
         }
      },
      d("mac") {
         @Override
         protected String[] b(URL var0) {
            return new String[]{"open", var0.toString()};
         }
      },
      e("unknown");

      private final String f;

      OS(String var2) {
         this.f = var2;
      }

      public void a(URL var0) {
         try {
            Process var1 = AccessController.doPrivileged((PrivilegedExceptionAction<Process>)(() -> Runtime.getRuntime().exec(this.b(var0))));
            var1.getInputStream().close();
            var1.getErrorStream().close();
            var1.getOutputStream().close();
         } catch (IOException | PrivilegedActionException var3) {
            SystemUtils.e.error("Couldn't open url '{}'", var0, var3);
         }
      }

      public void a(URI var0) {
         try {
            this.a(var0.toURL());
         } catch (MalformedURLException var3) {
            SystemUtils.e.error("Couldn't open uri '{}'", var0, var3);
         }
      }

      public void a(File var0) {
         try {
            this.a(var0.toURI().toURL());
         } catch (MalformedURLException var3) {
            SystemUtils.e.error("Couldn't open file '{}'", var0, var3);
         }
      }

      protected String[] b(URL var0) {
         String var1 = var0.toString();
         if ("file".equals(var0.getProtocol())) {
            var1 = var1.replace("file:", "file://");
         }

         return new String[]{"xdg-open", var1};
      }

      public void a(String var0) {
         try {
            this.a(new URI(var0).toURL());
         } catch (MalformedURLException | IllegalArgumentException | URISyntaxException var3) {
            SystemUtils.e.error("Couldn't open uri '{}'", var0, var3);
         }
      }

      public String a() {
         return this.f;
      }
   }
}
