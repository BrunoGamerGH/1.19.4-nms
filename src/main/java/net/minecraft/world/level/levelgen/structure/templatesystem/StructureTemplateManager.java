package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.FileUtils;
import net.minecraft.ResourceKeyInvalidException;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.SavedFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class StructureTemplateManager {
   private static final Logger a = LogUtils.getLogger();
   private static final String b = "structures";
   private static final String c = "gameteststructures";
   private static final String d = ".nbt";
   private static final String e = ".snbt";
   public final Map<MinecraftKey, Optional<DefinedStructure>> f = Maps.newConcurrentMap();
   private final DataFixer g;
   private IResourceManager h;
   private final Path i;
   private final List<StructureTemplateManager.b> j;
   private final HolderGetter<Block> k;
   private static final FileToIdConverter l = new FileToIdConverter("structures", ".nbt");

   public StructureTemplateManager(IResourceManager var0, Convertable.ConversionSession var1, DataFixer var2, HolderGetter<Block> var3) {
      this.h = var0;
      this.g = var2;
      this.i = var1.a(SavedFile.i).normalize();
      this.k = var3;
      Builder<StructureTemplateManager.b> var4 = ImmutableList.builder();
      var4.add(new StructureTemplateManager.b(this::h, this::d));
      if (SharedConstants.aO) {
         var4.add(new StructureTemplateManager.b(this::g, this::c));
      }

      var4.add(new StructureTemplateManager.b(this::f, this::b));
      this.j = var4.build();
   }

   public DefinedStructure a(MinecraftKey var0) {
      Optional<DefinedStructure> var1 = this.b(var0);
      if (var1.isPresent()) {
         return var1.get();
      } else {
         DefinedStructure var2 = new DefinedStructure();
         this.f.put(var0, Optional.of(var2));
         return var2;
      }
   }

   public Optional<DefinedStructure> b(MinecraftKey var0) {
      return this.f.computeIfAbsent(var0, this::e);
   }

   public Stream<MinecraftKey> a() {
      return this.j.stream().flatMap(var0 -> var0.b().get()).distinct();
   }

   private Optional<DefinedStructure> e(MinecraftKey var0) {
      for(StructureTemplateManager.b var2 : this.j) {
         try {
            Optional<DefinedStructure> var3 = var2.a().apply(var0);
            if (var3.isPresent()) {
               return var3;
            }
         } catch (Exception var5) {
         }
      }

      return Optional.empty();
   }

   public void a(IResourceManager var0) {
      this.h = var0;
      this.f.clear();
   }

   public Optional<DefinedStructure> f(MinecraftKey var0) {
      MinecraftKey var1 = l.a(var0);
      return this.a(() -> this.h.open(var1), var1x -> a.error("Couldn't load structure {}", var0, var1x));
   }

   private Stream<MinecraftKey> b() {
      return l.a(this.h).keySet().stream().map(l::b);
   }

   private Optional<DefinedStructure> g(MinecraftKey var0) {
      return this.a(var0, Paths.get("gameteststructures"));
   }

   private Stream<MinecraftKey> c() {
      return this.a(Paths.get("gameteststructures"), "minecraft", ".snbt");
   }

   public Optional<DefinedStructure> h(MinecraftKey var0) {
      if (!Files.isDirectory(this.i)) {
         return Optional.empty();
      } else {
         Path var1 = b(this.i, var0, ".nbt");
         return this.a(() -> new FileInputStream(var1.toFile()), var1x -> a.error("Couldn't load structure from {}", var1, var1x));
      }
   }

   private Stream<MinecraftKey> d() {
      if (!Files.isDirectory(this.i)) {
         return Stream.empty();
      } else {
         try {
            return Files.list(this.i).filter(var0 -> Files.isDirectory(var0)).flatMap(var0 -> this.a(var0));
         } catch (IOException var2) {
            return Stream.empty();
         }
      }
   }

   private Stream<MinecraftKey> a(Path var0) {
      Path var1 = var0.resolve("structures");
      return this.a(var1, var0.getFileName().toString(), ".nbt");
   }

   private Stream<MinecraftKey> a(Path var0, String var1, String var2) {
      if (!Files.isDirectory(var0)) {
         return Stream.empty();
      } else {
         int var3 = var2.length();
         Function<String, String> var4 = var1x -> var1x.substring(0, var1x.length() - var3);

         try {
            return Files.walk(var0).filter(var1x -> var1x.toString().endsWith(var2)).mapMulti((var3x, var4x) -> {
               try {
                  var4x.accept(new MinecraftKey(var1, var4.apply(this.a(var0, var3x))));
               } catch (ResourceKeyInvalidException var7x) {
                  a.error("Invalid location while listing pack contents", var7x);
               }
            });
         } catch (IOException var7) {
            a.error("Failed to list folder contents", var7);
            return Stream.empty();
         }
      }
   }

   private String a(Path var0, Path var1) {
      return var0.relativize(var1).toString().replace(File.separator, "/");
   }

   private Optional<DefinedStructure> a(MinecraftKey var0, Path var1) {
      if (!Files.isDirectory(var1)) {
         return Optional.empty();
      } else {
         Path var2 = FileUtils.b(var1, var0.a(), ".snbt");

         try {
            Optional var6;
            try (BufferedReader var3 = Files.newBufferedReader(var2)) {
               String var4 = IOUtils.toString(var3);
               var6 = Optional.of(this.a(GameProfileSerializer.a(var4)));
            }

            return var6;
         } catch (NoSuchFileException var9) {
            return Optional.empty();
         } catch (CommandSyntaxException | IOException var10) {
            a.error("Couldn't load structure from {}", var2, var10);
            return Optional.empty();
         }
      }
   }

   private Optional<DefinedStructure> a(StructureTemplateManager.a var0, Consumer<Throwable> var1) {
      try {
         Optional var4;
         try (InputStream var2 = var0.open()) {
            var4 = Optional.of(this.a(var2));
         }

         return var4;
      } catch (FileNotFoundException var8) {
         return Optional.empty();
      } catch (Throwable var9) {
         var1.accept(var9);
         return Optional.empty();
      }
   }

   public DefinedStructure a(InputStream var0) throws IOException {
      NBTTagCompound var1 = NBTCompressedStreamTools.a(var0);
      return this.a(var1);
   }

   public DefinedStructure a(NBTTagCompound var0) {
      DefinedStructure var1 = new DefinedStructure();
      int var2 = GameProfileSerializer.b(var0, 500);
      var1.a(this.k, DataFixTypes.f.a(this.g, var0, var2));
      return var1;
   }

   public boolean c(MinecraftKey var0) {
      Optional<DefinedStructure> var1 = this.f.get(var0);
      if (!var1.isPresent()) {
         return false;
      } else {
         DefinedStructure var2 = var1.get();
         Path var3 = b(this.i, var0, ".nbt");
         Path var4 = var3.getParent();
         if (var4 == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(var4) ? var4.toRealPath() : var4);
            } catch (IOException var13) {
               a.error("Failed to create parent directory: {}", var4);
               return false;
            }

            NBTTagCompound var5 = var2.a(new NBTTagCompound());

            try {
               try (OutputStream var6 = new FileOutputStream(var3.toFile())) {
                  NBTCompressedStreamTools.a(var5, var6);
               }

               return true;
            } catch (Throwable var12) {
               return false;
            }
         }
      }
   }

   public Path a(MinecraftKey var0, String var1) {
      return a(this.i, var0, var1);
   }

   public static Path a(Path var0, MinecraftKey var1, String var2) {
      try {
         Path var3 = var0.resolve(var1.b());
         Path var4 = var3.resolve("structures");
         return FileUtils.b(var4, var1.a(), var2);
      } catch (InvalidPathException var5) {
         throw new ResourceKeyInvalidException("Invalid resource path: " + var1, var5);
      }
   }

   public static Path b(Path var0, MinecraftKey var1, String var2) {
      if (var1.a().contains("//")) {
         throw new ResourceKeyInvalidException("Invalid resource path: " + var1);
      } else {
         Path var3 = a(var0, var1, var2);
         if (var3.startsWith(var0) && FileUtils.a(var3) && FileUtils.b(var3)) {
            return var3;
         } else {
            throw new ResourceKeyInvalidException("Invalid resource path: " + var3);
         }
      }
   }

   public void d(MinecraftKey var0) {
      this.f.remove(var0);
   }

   @FunctionalInterface
   interface a {
      InputStream open() throws IOException;
   }

   static record b(Function<MinecraftKey, Optional<DefinedStructure>> loader, Supplier<Stream<MinecraftKey>> lister) {
      private final Function<MinecraftKey, Optional<DefinedStructure>> a;
      private final Supplier<Stream<MinecraftKey>> b;

      b(Function<MinecraftKey, Optional<DefinedStructure>> var0, Supplier<Stream<MinecraftKey>> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
