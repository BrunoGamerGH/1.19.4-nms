package net.minecraft.nbt.visitors;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagType;
import net.minecraft.nbt.StreamTagVisitor;

public class CollectToTag implements StreamTagVisitor {
   private String a = "";
   @Nullable
   private NBTBase b;
   private final Deque<Consumer<NBTBase>> c = new ArrayDeque<>();

   @Nullable
   public NBTBase d() {
      return this.b;
   }

   protected int e() {
      return this.c.size();
   }

   private void a(NBTBase var0) {
      this.c.getLast().accept(var0);
   }

   @Override
   public StreamTagVisitor.b a() {
      this.a(NBTTagEnd.b);
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(String var0) {
      this.a(NBTTagString.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(byte var0) {
      this.a(NBTTagByte.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(short var0) {
      this.a(NBTTagShort.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(int var0) {
      this.a(NBTTagInt.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(long var0) {
      this.a(NBTTagLong.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(float var0) {
      this.a(NBTTagFloat.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(double var0) {
      this.a(NBTTagDouble.a(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(byte[] var0) {
      this.a(new NBTTagByteArray(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(int[] var0) {
      this.a(new NBTTagIntArray(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(long[] var0) {
      this.a(new NBTTagLongArray(var0));
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b a(NBTTagType<?> var0, int var1) {
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.a b(NBTTagType<?> var0, int var1) {
      this.c(var0);
      return StreamTagVisitor.a.a;
   }

   @Override
   public StreamTagVisitor.a a(NBTTagType<?> var0) {
      return StreamTagVisitor.a.a;
   }

   @Override
   public StreamTagVisitor.a a(NBTTagType<?> var0, String var1) {
      this.a = var1;
      this.c(var0);
      return StreamTagVisitor.a.a;
   }

   private void c(NBTTagType<?> var0) {
      if (var0 == NBTTagList.a) {
         NBTTagList var1 = new NBTTagList();
         this.a(var1);
         this.c.addLast(var1::add);
      } else if (var0 == NBTTagCompound.b) {
         NBTTagCompound var1 = new NBTTagCompound();
         this.a(var1);
         this.c.addLast(var1x -> var1.a(this.a, var1x));
      }
   }

   @Override
   public StreamTagVisitor.b b() {
      this.c.removeLast();
      return StreamTagVisitor.b.a;
   }

   @Override
   public StreamTagVisitor.b b(NBTTagType<?> var0) {
      if (var0 == NBTTagList.a) {
         NBTTagList var1 = new NBTTagList();
         this.b = var1;
         this.c.addLast(var1::add);
      } else if (var0 == NBTTagCompound.b) {
         NBTTagCompound var1 = new NBTTagCompound();
         this.b = var1;
         this.c.addLast(var1x -> var1.a(this.a, var1x));
      } else {
         this.c.addLast(var0x -> this.b = var0x);
      }

      return StreamTagVisitor.b.a;
   }
}
