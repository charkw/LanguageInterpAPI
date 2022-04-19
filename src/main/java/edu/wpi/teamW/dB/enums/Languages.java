package edu.wpi.teamW.dB.enums;

import java.util.HashMap;
import java.util.Map;

public enum Languages {
  Arabic("Arabic"),
  Bengali("Bengali"),
  Cantonese("Cantonese"),
  English("English"),
  French("French"),
  German("German"),
  Hindi("Hindi"),
  Indonesian("Indonesian"),
  Mandarin("Mandarin"),
  Portuguese("Portuguese"),
  Russian("Russian"),
  Spanish("Spanish"),
  Swedish("Swedish");

  public final String string;

  private static Map map = new HashMap<>();

  private Languages(String string) {
    this.string = string;
  }

  static {
    for (Languages type : Languages.values()) {
      map.put(type.string, type);
    }
  }

  public String getString() {
    return this.string;
  }
}
