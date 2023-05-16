package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftNoteBlock extends CraftBlockData implements NoteBlock {
   private static final BlockStateEnum<?> INSTRUMENT = getEnum("instrument");
   private static final BlockStateInteger NOTE = getInteger("note");

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
}
