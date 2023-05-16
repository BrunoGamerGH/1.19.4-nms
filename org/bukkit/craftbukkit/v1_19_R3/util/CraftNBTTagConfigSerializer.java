package org.bukkit.craftbukkit.v1_19_R3.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class CraftNBTTagConfigSerializer {
   private static final Pattern ARRAY = Pattern.compile("^\\[.*]");
   private static final Pattern INTEGER = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)?i", 2);
   private static final Pattern DOUBLE = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final MojangsonParser MOJANGSON_PARSER = new MojangsonParser(new StringReader(""));

   public static Object serialize(NBTBase base) {
      if (base instanceof NBTTagCompound) {
         Map<String, Object> innerMap = new HashMap<>();

         for(String key : ((NBTTagCompound)base).e()) {
            innerMap.put(key, serialize(((NBTTagCompound)base).c(key)));
         }

         return innerMap;
      } else if (!(base instanceof NBTTagList)) {
         if (base instanceof NBTTagString) {
            return base.f_();
         } else {
            return base instanceof NBTTagInt ? base.toString() + "i" : base.toString();
         }
      } else {
         List<Object> baseList = new ArrayList<>();

         for(int i = 0; i < ((NBTList)base).size(); ++i) {
            baseList.add(serialize((NBTBase)((NBTList)base).get(i)));
         }

         return baseList;
      }
   }

   public static NBTBase deserialize(Object object) {
      if (object instanceof Map) {
         NBTTagCompound compound = new NBTTagCompound();

         for(Entry<String, Object> entry : ((Map)object).entrySet()) {
            compound.a(entry.getKey(), deserialize(entry.getValue()));
         }

         return compound;
      } else if (!(object instanceof List)) {
         if (object instanceof String string) {
            if (ARRAY.matcher(string).matches()) {
               try {
                  return new MojangsonParser(new StringReader(string)).h();
               } catch (CommandSyntaxException var5) {
                  throw new RuntimeException("Could not deserialize found list ", var5);
               }
            } else if (INTEGER.matcher(string).matches()) {
               return NBTTagInt.a(Integer.parseInt(string.substring(0, string.length() - 1)));
            } else if (DOUBLE.matcher(string).matches()) {
               return NBTTagDouble.a(Double.parseDouble(string.substring(0, string.length() - 1)));
            } else {
               NBTBase nbtBase = MOJANGSON_PARSER.b(string);
               if (nbtBase instanceof NBTTagInt) {
                  return NBTTagString.a(nbtBase.f_());
               } else {
                  return (NBTBase)(nbtBase instanceof NBTTagDouble ? NBTTagString.a(String.valueOf(((NBTTagDouble)nbtBase).j())) : nbtBase);
               }
            }
         } else {
            throw new RuntimeException("Could not deserialize NBTBase");
         }
      } else {
         List<Object> list = (List)object;
         if (list.isEmpty()) {
            return new NBTTagList();
         } else {
            NBTTagList tagList = new NBTTagList();

            for(Object tag : list) {
               tagList.add(deserialize(tag));
            }

            return tagList;
         }
      }
   }
}
