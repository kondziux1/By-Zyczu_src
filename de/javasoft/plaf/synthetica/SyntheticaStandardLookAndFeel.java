package de.javasoft.plaf.synthetica;

import java.text.ParseException;

public class SyntheticaStandardLookAndFeel extends SyntheticaLookAndFeel {
   public SyntheticaStandardLookAndFeel() throws ParseException {
      super("standard/xml/synth.xml");
   }

   @Override
   public String getID() {
      return "SyntheticaStandardLookAndFeel";
   }

   @Override
   public String getName() {
      return "Synthetica Standard Look and Feel";
   }
}
