package net.minecraft.nbt;

public interface TagVisitor {
   void a(NBTTagString var1);

   void a(NBTTagByte var1);

   void a(NBTTagShort var1);

   void a(NBTTagInt var1);

   void a(NBTTagLong var1);

   void a(NBTTagFloat var1);

   void a(NBTTagDouble var1);

   void a(NBTTagByteArray var1);

   void a(NBTTagIntArray var1);

   void a(NBTTagLongArray var1);

   void a(NBTTagList var1);

   void a(NBTTagCompound var1);

   void a(NBTTagEnd var1);
}
