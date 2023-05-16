package org.bukkit.craftbukkit.v1_19_R3.persistence;

import com.google.common.primitives.Primitives;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.persistence.PersistentDataContainer;

public final class CraftPersistentDataTypeRegistry {
   private final Function<Class, CraftPersistentDataTypeRegistry.TagAdapter> CREATE_ADAPTER = this::createAdapter;
   private final Map<Class, CraftPersistentDataTypeRegistry.TagAdapter> adapters = new HashMap<>();

   private <T> CraftPersistentDataTypeRegistry.TagAdapter createAdapter(Class<T> type) {
      if (!Primitives.isWrapperType(type)) {
         type = Primitives.wrap(type);
      }

      if (Objects.equals(Byte.class, type)) {
         return this.createAdapter(Byte.class, NBTTagByte.class, NBTTagByte::a, NBTTagByte::i);
      } else if (Objects.equals(Short.class, type)) {
         return this.createAdapter(Short.class, NBTTagShort.class, NBTTagShort::a, NBTTagShort::h);
      } else if (Objects.equals(Integer.class, type)) {
         return this.createAdapter(Integer.class, NBTTagInt.class, NBTTagInt::a, NBTTagInt::g);
      } else if (Objects.equals(Long.class, type)) {
         return this.createAdapter(Long.class, NBTTagLong.class, NBTTagLong::a, NBTTagLong::f);
      } else if (Objects.equals(Float.class, type)) {
         return this.createAdapter(Float.class, NBTTagFloat.class, NBTTagFloat::a, NBTTagFloat::k);
      } else if (Objects.equals(Double.class, type)) {
         return this.createAdapter(Double.class, NBTTagDouble.class, NBTTagDouble::a, NBTTagDouble::j);
      } else if (Objects.equals(String.class, type)) {
         return this.createAdapter(String.class, NBTTagString.class, NBTTagString::a, NBTTagString::f_);
      } else if (Objects.equals(byte[].class, type)) {
         return this.createAdapter(
            byte[].class, NBTTagByteArray.class, array -> new NBTTagByteArray(Arrays.copyOf(array, array.length)), n -> Arrays.copyOf(n.e(), n.size())
         );
      } else if (Objects.equals(int[].class, type)) {
         return this.createAdapter(
            int[].class, NBTTagIntArray.class, array -> new NBTTagIntArray(Arrays.copyOf(array, array.length)), n -> Arrays.copyOf(n.g(), n.size())
         );
      } else if (Objects.equals(long[].class, type)) {
         return this.createAdapter(
            long[].class, NBTTagLongArray.class, array -> new NBTTagLongArray(Arrays.copyOf(array, array.length)), n -> Arrays.copyOf(n.g(), n.size())
         );
      } else if (Objects.equals(PersistentDataContainer[].class, type)) {
         return this.createAdapter(PersistentDataContainer[].class, NBTTagList.class, containerArray -> {
            NBTTagList list = new NBTTagList();

            for(int i = 0; i < ((Object[])containerArray).length; ++i) {
               list.add(((CraftPersistentDataContainer)((Object[])containerArray)[i]).toTagCompound());
            }

            return list;
         }, tag -> {
            PersistentDataContainer[] containerArray = new CraftPersistentDataContainer[tag.size()];

            for(int i = 0; i < tag.size(); ++i) {
               CraftPersistentDataContainer container = new CraftPersistentDataContainer(this);
               NBTTagCompound compound = tag.a(i);

               for(String key : compound.e()) {
                  container.put(key, compound.c(key));
               }

               containerArray[i] = container;
            }

            return (T)containerArray;
         });
      } else if (Objects.equals(PersistentDataContainer.class, type)) {
         return this.createAdapter(CraftPersistentDataContainer.class, NBTTagCompound.class, CraftPersistentDataContainer::toTagCompound, tag -> {
            CraftPersistentDataContainer container = new CraftPersistentDataContainer(this);

            for(String key : tag.e()) {
               container.put(key, tag.c(key));
            }

            return container;
         });
      } else {
         throw new IllegalArgumentException("Could not find a valid TagAdapter implementation for the requested type " + type.getSimpleName());
      }
   }

   private <T, Z extends NBTBase> CraftPersistentDataTypeRegistry.TagAdapter<T, Z> createAdapter(
      Class<T> primitiveType, Class<Z> nbtBaseType, Function<T, Z> builder, Function<Z, T> extractor
   ) {
      return new CraftPersistentDataTypeRegistry.TagAdapter<>(primitiveType, nbtBaseType, builder, extractor);
   }

   public <T> NBTBase wrap(Class<T> type, T value) {
      return this.adapters.computeIfAbsent(type, this.CREATE_ADAPTER).build(value);
   }

   public <T> boolean isInstanceOf(Class<T> type, NBTBase base) {
      return this.adapters.computeIfAbsent(type, this.CREATE_ADAPTER).isInstance(base);
   }

   public <T> T extract(Class<T> type, NBTBase tag) throws ClassCastException, IllegalArgumentException {
      CraftPersistentDataTypeRegistry.TagAdapter adapter = this.adapters.computeIfAbsent(type, this.CREATE_ADAPTER);
      if (!adapter.isInstance(tag)) {
         throw new IllegalArgumentException(
            String.format("`The found tag instance cannot store %s as it is a %s", type.getSimpleName(), tag.getClass().getSimpleName())
         );
      } else {
         Object foundValue = adapter.extract(tag);
         if (!type.isInstance(foundValue)) {
            throw new IllegalArgumentException(
               String.format("The found object is of the type %s. Expected type %s", foundValue.getClass().getSimpleName(), type.getSimpleName())
            );
         } else {
            return type.cast(foundValue);
         }
      }
   }

   private class TagAdapter<T, Z extends NBTBase> {
      private final Function<T, Z> builder;
      private final Function<Z, T> extractor;
      private final Class<T> primitiveType;
      private final Class<Z> nbtBaseType;

      public TagAdapter(Class<T> primitiveType, Class<Z> nbtBaseType, Function<T, Z> builder, Function<Z, T> extractor) {
         this.primitiveType = primitiveType;
         this.nbtBaseType = nbtBaseType;
         this.builder = builder;
         this.extractor = extractor;
      }

      T extract(NBTBase base) {
         if (!this.nbtBaseType.isInstance(base)) {
            throw new IllegalArgumentException(
               String.format("The provided NBTBase was of the type %s. Expected type %s", base.getClass().getSimpleName(), this.nbtBaseType.getSimpleName())
            );
         } else {
            return this.extractor.apply(this.nbtBaseType.cast(base));
         }
      }

      Z build(Object value) {
         if (!this.primitiveType.isInstance(value)) {
            throw new IllegalArgumentException(
               String.format("The provided value was of the type %s. Expected type %s", value.getClass().getSimpleName(), this.primitiveType.getSimpleName())
            );
         } else {
            return this.builder.apply(this.primitiveType.cast(value));
         }
      }

      boolean isInstance(NBTBase base) {
         return this.nbtBaseType.isInstance(base);
      }
   }
}
