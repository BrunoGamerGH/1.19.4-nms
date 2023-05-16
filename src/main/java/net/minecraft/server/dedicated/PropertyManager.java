package net.minecraft.server.dedicated;

import com.google.common.base.MoreObjects;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import joptsimple.OptionSet;
import net.minecraft.core.IRegistryCustom;
import org.slf4j.Logger;

public abstract class PropertyManager<T extends PropertyManager<T>> {
   private static final Logger a = LogUtils.getLogger();
   public final Properties Y;
   private OptionSet options = null;

   public PropertyManager(Properties properties, OptionSet options) {
      this.Y = properties;
      this.options = options;
   }

   private String getOverride(String name, String value) {
      return this.options != null && this.options.has(name) && !name.equals("online-mode") ? String.valueOf(this.options.valueOf(name)) : value;
   }

   public static Properties b(Path path) {
      Properties properties = new Properties();

      try (InputStream inputstream = Files.newInputStream(path)) {
         properties.load(inputstream);
      } catch (IOException var7) {
         a.error("Failed to load properties from file: {}", path);
      }

      return properties;
   }

   public void c(Path path) {
      try {
         if (path.toFile().exists() && !path.toFile().canWrite()) {
            return;
         }

         try (OutputStream outputstream = Files.newOutputStream(path)) {
            this.Y.store(outputstream, "Minecraft server properties");
         }
      } catch (IOException var7) {
         a.error("Failed to store properties to file: {}", path);
      }
   }

   private static <V extends Number> Function<String, V> a(Function<String, V> function) {
      return s -> {
         try {
            return function.apply(s);
         } catch (NumberFormatException var3) {
            return null;
         }
      };
   }

   protected static <V> Function<String, V> a(IntFunction<V> intfunction, Function<String, V> function) {
      return s -> {
         try {
            return intfunction.apply(Integer.parseInt(s));
         } catch (NumberFormatException var4) {
            return function.apply(s);
         }
      };
   }

   @Nullable
   private String c(String s) {
      return this.getOverride(s, this.Y.getProperty(s));
   }

   @Nullable
   protected <V> V a(String s, Function<String, V> function) {
      String s1 = this.c(s);
      if (s1 == null) {
         return null;
      } else {
         this.Y.remove(s);
         return function.apply(s1);
      }
   }

   protected <V> V a(String s, Function<String, V> function, Function<V, String> function1, V v0) {
      String s1 = this.c(s);
      V v1 = (V)MoreObjects.firstNonNull(s1 != null ? function.apply(s1) : null, v0);
      this.Y.put(s, function1.apply(v1));
      return v1;
   }

   protected <V> PropertyManager<T>.EditableProperty<V> b(String s, Function<String, V> function, Function<V, String> function1, V v0) {
      String s1 = this.c(s);
      V v1 = (V)MoreObjects.firstNonNull(s1 != null ? function.apply(s1) : null, v0);
      this.Y.put(s, function1.apply(v1));
      return new PropertyManager.EditableProperty<>(s, v1, function1);
   }

   protected <V> V a(String s, Function<String, V> function, UnaryOperator<V> unaryoperator, Function<V, String> function1, V v0) {
      return this.a(s, s1 -> {
         V v1 = function.apply(s1);
         return v1 != null ? unaryoperator.apply(v1) : null;
      }, function1, v0);
   }

   protected <V> V a(String s, Function<String, V> function, V v0) {
      return this.a(s, function, Objects::toString, v0);
   }

   protected <V> PropertyManager<T>.EditableProperty<V> b(String s, Function<String, V> function, V v0) {
      return this.b(s, function, Objects::toString, v0);
   }

   protected String a(String s, String s1) {
      return this.a(s, Function.identity(), Function.identity(), s1);
   }

   @Nullable
   protected String a(String s) {
      return this.a(s, Function.identity());
   }

   protected int a(String s, int i) {
      return this.a(s, a(Integer::parseInt), Integer.valueOf(i));
   }

   protected PropertyManager<T>.EditableProperty<Integer> b(String s, int i) {
      return this.b(s, a(Integer::parseInt), i);
   }

   protected int a(String s, UnaryOperator<Integer> unaryoperator, int i) {
      return this.a(s, a(Integer::parseInt), unaryoperator, Objects::toString, i);
   }

   protected long a(String s, long i) {
      return this.a(s, a(Long::parseLong), i);
   }

   protected boolean a(String s, boolean flag) {
      return this.a(s, Boolean::valueOf, flag);
   }

   protected PropertyManager<T>.EditableProperty<Boolean> b(String s, boolean flag) {
      return this.b(s, Boolean::valueOf, flag);
   }

   @Nullable
   protected Boolean b(String s) {
      return this.a(s, Boolean::valueOf);
   }

   protected Properties a() {
      Properties properties = new Properties();
      properties.putAll(this.Y);
      return properties;
   }

   protected abstract T reload(IRegistryCustom var1, Properties var2, OptionSet var3);

   public class EditableProperty<V> implements Supplier<V> {
      private final String b;
      private final V c;
      private final Function<V, String> d;

      EditableProperty(String s, V object, Function function) {
         this.b = s;
         this.c = object;
         this.d = function;
      }

      @Override
      public V get() {
         return this.c;
      }

      public T a(IRegistryCustom iregistrycustom, V v0) {
         Properties properties = PropertyManager.this.a();
         properties.put(this.b, this.d.apply(v0));
         return PropertyManager.this.reload(iregistrycustom, properties, PropertyManager.this.options);
      }
   }
}
