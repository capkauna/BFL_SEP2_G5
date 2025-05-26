package Shared.dto.enums;

public enum Genre
{
  FICTION("Fiction"),
  NON_FICTION("Non_Fiction"),
  MYSTERY("Mystery"),
  FANTASY("Fantasy"),
  SCIENCE_FICTION("Science_Fiction"),
  BIOGRAPHY("Biography"),
  HISTORY("History"),
  ROMANCE("Romance"),
  THRILLER("Thriller"),
  HORROR("Horror"),
  DYSTOPIA("Dystopia"),
  PSYCHOLOGY("Psychology"),
  MANGA("Manga"),//can this be also another category? :?
  COMICS("Comics");



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
