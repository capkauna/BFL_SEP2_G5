package DTO.enums;

public enum Genre
{
  FICTION("Fiction"),
  NON_FICTION("Non-Fiction"),
  MYSTERY("Mystery"),
  FANTASY("Fantasy"),
  SCIENCE_FICTION("Science Fiction"),
  BIOGRAPHY("Biography"),
  HISTORY("History"),
  ROMANCE("Romance"),
  THRILLER("Thriller"),
  HORROR("Horror"),
  DYSTOPIA("Dystopia"),
  PSYCHOLOGY("Psychology"),
  MANGA("Manga"); //can this be also another category? :?



  private final String genreName;

  Genre(String genreName)
  {
    this.genreName = genreName;
  }

  public String getGenreName()
  {
    return genreName;
  }
}
