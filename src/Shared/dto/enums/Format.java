package Shared.dto.enums;

public enum Format
{
  PAPERBACK,
  HARDCOVER,
  EBOOK;

  public static Format fromString(String formatString)
  {
    switch (formatString.trim().toUpperCase())//added trim() to account for spaces or unexpected formatting
    {
      case "PAPERBACK":
        return PAPERBACK;
      case "HARDCOVER":
        return HARDCOVER;
      case "EBOOK":
        return EBOOK;
      default:
        throw new IllegalArgumentException("Unknown format: " + formatString);
    }
  }


}
