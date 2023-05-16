package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.FileUtils;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.nbt.visitors.SkipFields;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.SessionLock;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.GeneratorSettings;
import net.minecraft.world.level.levelgen.WorldDimensions;
import org.slf4j.Logger;

public class Convertable {
   static final Logger a = LogUtils.getLogger();
   static final DateTimeFormatter b = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .appendLiteral('_')
      .appendValue(ChronoField.HOUR_OF_DAY, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
      .toFormatter();
   private static final ImmutableList<String> c = ImmutableList.of(
      "RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest"
   );
   private static final String d = "Data";
   public final Path e;
   private final Path f;
   final DataFixer g;

   public Convertable(Path path, Path path1, DataFixer datafixer) {
      this.g = datafixer;

      try {
         FileUtils.c(path);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.e = path;
      this.f = path1;
   }

   public static Convertable a(Path path) {
      return new Convertable(path, path.resolve("../backups"), DataConverterRegistry.a());
   }

   private static <T> DataResult<GeneratorSettings> a(Dynamic<T> dynamic, DataFixer datafixer, int i) {
      Dynamic<T> dynamic1 = dynamic.get("WorldGenSettings").orElseEmptyMap();
      UnmodifiableIterator unmodifiableiterator = c.iterator();

      while(unmodifiableiterator.hasNext()) {
         String s = (String)unmodifiableiterator.next();
         Optional<Dynamic<T>> optional = dynamic.get(s).result();
         if (optional.isPresent()) {
            dynamic1 = dynamic1.set(s, (Dynamic)optional.get());
         }
      }

      Dynamic<T> dynamic2 = DataFixTypes.k.a(datafixer, dynamic1, i);
      return GeneratorSettings.a.parse(dynamic2);
   }

   private static WorldDataConfiguration a(Dynamic<?> dynamic) {
      DataResult<WorldDataConfiguration> dataresult = WorldDataConfiguration.b.parse(dynamic);
      Logger logger = a;
      return dataresult.resultOrPartial(logger::error).orElse(WorldDataConfiguration.c);
   }

   public String a() {
      return "Anvil";
   }

   public Convertable.a b() throws LevelStorageException {
      if (!Files.isDirectory(this.e)) {
         throw new LevelStorageException(IChatBaseComponent.c("selectWorld.load_folder_access"));
      } else {
         try {
            List<Convertable.b> list = Files.list(this.e)
               .filter(path -> Files.isDirectory(path))
               .map(Convertable.b::new)
               .filter(convertable_b -> Files.isRegularFile(convertable_b.b()) || Files.isRegularFile(convertable_b.c()))
               .toList();
            return new Convertable.a(list);
         } catch (IOException var2) {
            throw new LevelStorageException(IChatBaseComponent.c("selectWorld.load_folder_access"));
         }
      }
   }

   public CompletableFuture<List<WorldInfo>> a(Convertable.a convertable_a) {
      List<CompletableFuture<WorldInfo>> list = new ArrayList<>(convertable_a.a.size());

      for(Convertable.b convertable_b : convertable_a.a) {
         list.add(
            CompletableFuture.supplyAsync(
               () -> {
                  boolean flag;
                  try {
                     flag = SessionLock.b(convertable_b.f());
                  } catch (Exception var6) {
                     a.warn("Failed to read {} lock", convertable_b.f(), var6);
                     return null;
                  }
      
                  try {
                     WorldInfo worldinfo = this.a(convertable_b, this.a(convertable_b, flag));
                     return worldinfo != null ? worldinfo : null;
                  } catch (OutOfMemoryError var4x) {
                     MemoryReserve.b();
                     System.gc();
                     a.error(LogUtils.FATAL_MARKER, "Ran out of memory trying to read summary of {}", convertable_b.a());
                     throw var4x;
                  } catch (StackOverflowError var5) {
                     a.error(
                        LogUtils.FATAL_MARKER,
                        "Ran out of stack trying to read summary of {}. Assuming corruption; attempting to restore from from level.dat_old.",
                        convertable_b.a()
                     );
                     SystemUtils.a(convertable_b.b(), convertable_b.c(), convertable_b.a(LocalDateTime.now()), true);
                     throw var5;
                  }
               },
               SystemUtils.f()
            )
         );
      }

      return SystemUtils.d(list).thenApply(list1 -> list1.stream().filter(Objects::nonNull).sorted().toList());
   }

   private int e() {
      return 19133;
   }

   @Nullable
   <T> T a(Convertable.b convertable_b, BiFunction<Path, DataFixer, T> bifunction) {
      if (!Files.exists(convertable_b.f())) {
         return null;
      } else {
         Path path = convertable_b.b();
         if (Files.exists(path)) {
            T t0 = bifunction.apply(path, this.g);
            if (t0 != null) {
               return t0;
            }
         }

         path = convertable_b.c();
         return Files.exists(path) ? bifunction.apply(path, this.g) : null;
      }
   }

   @Nullable
   private static WorldDataConfiguration a(Path path, DataFixer datafixer) {
      try {
         NBTBase nbtbase = b(path);
         if (nbtbase instanceof NBTTagCompound nbttagcompound) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.p("Data");
            int i = GameProfileSerializer.b(nbttagcompound1, -1);
            Dynamic<?> dynamic = DataFixTypes.a.a(datafixer, new Dynamic(DynamicOpsNBT.a, nbttagcompound1), i);
            return a(dynamic);
         }
      } catch (Exception var7) {
         a.error("Exception reading {}", path, var7);
      }

      return null;
   }

   static BiFunction<Path, DataFixer, Pair<SaveData, WorldDimensions.b>> a(
      DynamicOps<NBTBase> dynamicops, WorldDataConfiguration worlddataconfiguration, IRegistry<WorldDimension> iregistry, Lifecycle lifecycle
   ) {
      return (path, datafixer) -> {
         NBTTagCompound nbttagcompound;
         try {
            nbttagcompound = NBTCompressedStreamTools.a(path.toFile());
         } catch (IOException var20) {
            throw new UncheckedIOException(var20);
         }

         NBTTagCompound nbttagcompound1 = nbttagcompound.p("Data");
         NBTTagCompound nbttagcompound2 = nbttagcompound1.b("Player", 10) ? nbttagcompound1.p("Player") : null;
         nbttagcompound1.r("Player");
         int i = GameProfileSerializer.b(nbttagcompound1, -1);
         Dynamic<?> dynamic = DataFixTypes.a.a(datafixer, new Dynamic(dynamicops, nbttagcompound1), i);
         DataResult dataresult = a(dynamic, datafixer, i);
         Logger logger = a;
         GeneratorSettings generatorsettings = (GeneratorSettings)dataresult.getOrThrow(false, SystemUtils.a("WorldGenSettings: ", logger::error));
         LevelVersion levelversion = LevelVersion.a(dynamic);
         WorldSettings worldsettings = WorldSettings.a(dynamic, worlddataconfiguration);
         WorldDimensions.b worlddimensions_b = generatorsettings.b().a(iregistry);
         Lifecycle lifecycle1 = worlddimensions_b.a().add(lifecycle);
         WorldDataServer worlddataserver = WorldDataServer.a(
            dynamic, datafixer, i, nbttagcompound2, worldsettings, levelversion, worlddimensions_b.d(), generatorsettings.a(), lifecycle1
         );
         worlddataserver.pdc = nbttagcompound1.c("BukkitValues");
         return Pair.of(worlddataserver, worlddimensions_b);
      };
   }

   BiFunction<Path, DataFixer, WorldInfo> a(Convertable.b convertable_b, boolean flag) {
      return (path, datafixer) -> {
         try {
            NBTBase nbtbase = b(path);
            if (nbtbase instanceof NBTTagCompound nbttagcompound) {
               NBTTagCompound nbttagcompound1 = nbttagcompound.p("Data");
               int i = GameProfileSerializer.b(nbttagcompound1, -1);
               Dynamic<?> dynamic = DataFixTypes.a.a(datafixer, new Dynamic(DynamicOpsNBT.a, nbttagcompound1), i);
               LevelVersion levelversion = LevelVersion.a(dynamic);
               int j = levelversion.a();
               if (j == 19132 || j == 19133) {
                  boolean flag1 = j != this.e();
                  Path path1 = convertable_b.d();
                  WorldDataConfiguration worlddataconfiguration = a(dynamic);
                  WorldSettings worldsettings = WorldSettings.a(dynamic, worlddataconfiguration);
                  FeatureFlagSet featureflagset = b(dynamic);
                  boolean flag2 = FeatureFlags.a(featureflagset);
                  return new WorldInfo(worldsettings, levelversion, convertable_b.a(), flag1, flag, flag2, path1);
               }
            } else {
               a.warn("Invalid root tag in {}", path);
            }

            return null;
         } catch (Exception var18) {
            a.error("Exception reading {}", path, var18);
            return null;
         }
      };
   }

   private static FeatureFlagSet b(Dynamic<?> dynamic) {
      Set<MinecraftKey> set = dynamic.get("enabled_features")
         .asStream()
         .flatMap(dynamic1 -> dynamic1.asString().result().map(MinecraftKey::a).stream())
         .collect(Collectors.toSet());
      return FeatureFlags.d.a(set, minecraftkey -> {
      });
   }

   @Nullable
   private static NBTBase b(Path path) throws IOException {
      SkipFields skipfields = new SkipFields(
         new FieldSelector("Data", NBTTagCompound.b, "Player"), new FieldSelector("Data", NBTTagCompound.b, "WorldGenSettings")
      );
      NBTCompressedStreamTools.a(path.toFile(), skipfields);
      return skipfields.d();
   }

   public boolean a(String s) {
      try {
         Path path = this.e.resolve(s);
         Files.createDirectory(path);
         Files.deleteIfExists(path);
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public boolean b(String s) {
      return Files.isDirectory(this.e.resolve(s));
   }

   public Path c() {
      return this.e;
   }

   public Path d() {
      return this.f;
   }

   public Convertable.ConversionSession createAccess(String s, ResourceKey<WorldDimension> dimensionType) throws IOException {
      return new Convertable.ConversionSession(s, dimensionType);
   }

   public static Path getStorageFolder(Path path, ResourceKey<WorldDimension> dimensionType) {
      if (dimensionType == WorldDimension.b) {
         return path;
      } else if (dimensionType == WorldDimension.c) {
         return path.resolve("DIM-1");
      } else {
         return dimensionType == WorldDimension.d
            ? path.resolve("DIM1")
            : path.resolve("dimensions").resolve(dimensionType.a().b()).resolve(dimensionType.a().a());
      }
   }

   public class ConversionSession implements AutoCloseable {
      final SessionLock b;
      public final Convertable.b c;
      private final String d;
      private final Map<SavedFile, Path> e = Maps.newHashMap();
      public final ResourceKey<WorldDimension> dimensionType;

      public ConversionSession(String s, ResourceKey<WorldDimension> dimensionType) throws IOException {
         this.dimensionType = dimensionType;
         this.d = s;
         this.c = new Convertable.b(Convertable.this.e.resolve(s));
         this.b = SessionLock.a(this.c.f());
      }

      public String a() {
         return this.d;
      }

      public Path a(SavedFile savedfile) {
         Map<SavedFile, Path> map = this.e;
         Convertable.b convertable_b = this.c;
         return map.computeIfAbsent(savedfile, convertable_b::a);
      }

      public Path a(ResourceKey<World> resourcekey) {
         return Convertable.getStorageFolder(this.c.f(), this.dimensionType);
      }

      private void h() {
         if (!this.b.a()) {
            throw new IllegalStateException("Lock is no longer valid");
         }
      }

      public WorldNBTStorage b() {
         this.h();
         return new WorldNBTStorage(this, Convertable.this.g);
      }

      @Nullable
      public WorldInfo c() {
         this.h();
         return Convertable.this.a(this.c, Convertable.this.a(this.c, false));
      }

      @Nullable
      public Pair<SaveData, WorldDimensions.b> a(
         DynamicOps<NBTBase> dynamicops, WorldDataConfiguration worlddataconfiguration, IRegistry<WorldDimension> iregistry, Lifecycle lifecycle
      ) {
         this.h();
         return Convertable.this.a(this.c, Convertable.a(dynamicops, worlddataconfiguration, iregistry, lifecycle));
      }

      @Nullable
      public WorldDataConfiguration d() {
         this.h();
         return Convertable.this.a(this.c, Convertable::access$0);
      }

      public void a(IRegistryCustom iregistrycustom, SaveData savedata) {
         this.a(iregistrycustom, savedata, null);
      }

      public void a(IRegistryCustom iregistrycustom, SaveData savedata, @Nullable NBTTagCompound nbttagcompound) {
         File file = this.c.f().toFile();
         NBTTagCompound nbttagcompound1 = savedata.a(iregistrycustom, nbttagcompound);
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         nbttagcompound2.a("Data", nbttagcompound1);

         try {
            File file1 = File.createTempFile("level", ".dat", file);
            NBTCompressedStreamTools.a(nbttagcompound2, file1);
            File file2 = this.c.c().toFile();
            File file3 = this.c.b().toFile();
            SystemUtils.a(file3, file1, file2);
         } catch (Exception var10) {
            Convertable.a.error("Failed to save level {}", file, var10);
         }
      }

      public Optional<Path> e() {
         return !this.b.a() ? Optional.empty() : Optional.of(this.c.d());
      }

      public void f() throws IOException {
         this.h();
         final Path path = this.c.e();
         Convertable.a.info("Deleting level {}", this.d);

         for(int i = 1; i <= 5; ++i) {
            Convertable.a.info("Attempt {}...", i);

            try {
               Files.walkFileTree(this.c.f(), new SimpleFileVisitor<Path>() {
                  public FileVisitResult a(Path path1, BasicFileAttributes basicfileattributes) throws IOException {
                     if (!path1.equals(path)) {
                        Convertable.a.debug("Deleting {}", path1);
                        Files.delete(path1);
                     }

                     return FileVisitResult.CONTINUE;
                  }

                  public FileVisitResult a(Path path1, IOException ioexception) throws IOException {
                     if (ioexception != null) {
                        throw ioexception;
                     } else {
                        if (path1.equals(ConversionSession.this.c.f())) {
                           ConversionSession.this.b.close();
                           Files.deleteIfExists(path);
                        }

                        Files.delete(path1);
                        return FileVisitResult.CONTINUE;
                     }
                  }
               });
               break;
            } catch (IOException var6) {
               if (i >= 5) {
                  throw var6;
               }

               Convertable.a.warn("Failed to delete {}", this.c.f(), var6);

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var5) {
               }
            }
         }
      }

      public void a(String s) throws IOException {
         this.h();
         Path path = this.c.b();
         if (Files.exists(path)) {
            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(path.toFile());
            NBTTagCompound nbttagcompound1 = nbttagcompound.p("Data");
            nbttagcompound1.a("LevelName", s);
            NBTCompressedStreamTools.a(nbttagcompound, path.toFile());
         }
      }

      public long g() throws IOException {
         this.h();
         String s = LocalDateTime.now().format(Convertable.b);
         String s1 = s + "_" + this.d;
         Path path = Convertable.this.d();

         try {
            FileUtils.c(path);
         } catch (IOException var10) {
            throw new RuntimeException(var10);
         }

         Path path1 = path.resolve(FileUtils.a(path, s1, ".zip"));

         try (final ZipOutputStream zipoutputstream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path1)))) {
            final Path path2 = Paths.get(this.d);
            Files.walkFileTree(this.c.f(), new SimpleFileVisitor<Path>() {
               public FileVisitResult a(Path path3, BasicFileAttributes basicfileattributes) throws IOException {
                  if (path3.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String s2 = path2.resolve(ConversionSession.this.c.f().relativize(path3)).toString().replace('\\', '/');
                     ZipEntry zipentry = new ZipEntry(s2);
                     zipoutputstream.putNextEntry(zipentry);
                     com.google.common.io.Files.asByteSource(path3.toFile()).copyTo(zipoutputstream);
                     zipoutputstream.closeEntry();
                     return FileVisitResult.CONTINUE;
                  }
               }
            });
         }

         return Files.size(path1);
      }

      @Override
      public void close() throws IOException {
         this.b.close();
      }
   }

   public static record a(List<Convertable.b> levels) implements Iterable<Convertable.b> {
      private final List<Convertable.b> a;

      public a(List<Convertable.b> levels) {
         this.a = levels;
      }

      public boolean a() {
         return this.a.isEmpty();
      }

      @Override
      public Iterator<Convertable.b> iterator() {
         return this.a.iterator();
      }

      public List<Convertable.b> b() {
         return this.a;
      }
   }

   public static record b(Path path) {
      private final Path a;

      public b(Path path) {
         this.a = path;
      }

      public String a() {
         return this.a.getFileName().toString();
      }

      public Path b() {
         return this.a(SavedFile.e);
      }

      public Path c() {
         return this.a(SavedFile.f);
      }

      public Path a(LocalDateTime localdatetime) {
         Path path = this.a;
         String s = SavedFile.e.a();
         return path.resolve(s + "_corrupted_" + localdatetime.format(Convertable.b));
      }

      public Path d() {
         return this.a(SavedFile.g);
      }

      public Path e() {
         return this.a(SavedFile.h);
      }

      public Path a(SavedFile savedfile) {
         return this.a.resolve(savedfile.a());
      }

      public Path f() {
         return this.a;
      }
   }
}
