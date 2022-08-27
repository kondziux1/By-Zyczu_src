package de.javasoft.plaf.synthetica;

import java.text.ParseException;

public class SyntheticaAluOxideLookAndFeel extends SyntheticaLookAndFeel {
   public SyntheticaAluOxideLookAndFeel() throws ParseException {
      super("aluoxide/xml/synth.xml");
   }

   @Override
   public String getID() {
      return "SyntheticaAluOxideLookAndFeel";
   }

   @Override
   public String getName() {
      return "Synthetica AluOxide Look and Feel";
   }
}
