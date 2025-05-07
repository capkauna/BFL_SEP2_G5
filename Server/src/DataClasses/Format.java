package DataClasses;

public enum Format
{
  PAPERBACK,
  HARDCOVER,
  EBOOKPDF,
  EBOOKEPUB,
  EBOOKMOBI;

  public static Format fromString(String formatString)
  {
    switch (formatString.toUpperCase())
    {
      case "PAPERBACK":
        return PAPERBACK;
      case "HARDCOVER":
        return HARDCOVER;
      case "EBOOKPDF":
        return EBOOKPDF;
      case "EBOOKEPUB":
        return EBOOKEPUB;
      case "EBOOKMOBI":
        return EBOOKMOBI;
      default:
        throw new IllegalArgumentException("Unknown format: " + formatString);
    }
  }

}
