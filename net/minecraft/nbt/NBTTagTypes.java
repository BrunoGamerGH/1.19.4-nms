package net.minecraft.nbt;

public class NBTTagTypes {
   private static final NBTTagType<?>[] a = new NBTTagType[]{
      NBTTagEnd.a,
      NBTTagByte.a,
      NBTTagShort.a,
      NBTTagInt.a,
      NBTTagLong.a,
      NBTTagFloat.b,
      NBTTagDouble.b,
      NBTTagByteArray.a,
      NBTTagString.a,
      NBTTagList.a,
      NBTTagCompound.b,
      NBTTagIntArray.a,
      NBTTagLongArray.a
   };

   public static NBTTagType<?> a(int var0) {
      return var0 >= 0 && var0 < a.length ? a[var0] : NBTTagType.a(var0);
   }
}
