package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockNote;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftNote extends CraftBlockData implements NoteBlock, Powerable {
   private static final BlockStateEnum<?> INSTRUMENT = getEnum(BlockNote.class, "instrument");
   private static final BlockStateInteger NOTE = getInteger(BlockNote.class, "note");
   private static final BlockStateBoolean POWERED = getBoolean(BlockNote.class, "powered");

   public CraftNote() {
   }

   public CraftNote(IBlockData state) {
      super(state);
   }

   public Instrument getInstrument() {
      return this.get(INSTRUMENT, Instrument.class);
   }

   public void setInstrument(Instrument instrument) {
      this.set(INSTRUMENT, instrument);
   }

   public Note getNote() {
      return new Note(this.get(NOTE));
   }

   public void setNote(Note note) {
      this.set(NOTE, Integer.valueOf(note.getId()));
   }

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }
}
