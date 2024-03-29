package com.villo.alkemychallenge.utils;

public class Constants {
    private Constants() {
        // Constructor privado para ocultar el constructor público implícito
    }

    //Paths
    public static final String FILM_PATH = "/films";
    public static final String CHARACTER_PATH = "/characters";
    public static final String AUTH_PATH = "/auth";
    public static final String LOGIN_PATH = "/login";

    //Operations
    public static final String CREATE_A = "Create a ";
    public static final String OBTAIN_A = "Obtain a ";
    public static final String OBTAIN_OR_FILTER = "Obtain or filter ";
    public static final String EDIT_A = "Edit a ";
    public static final String DELETE_A = "Delete a ";
    public final static String FILM = "film.";
    public final static String OBTAIN_A_FILM = OBTAIN_A + FILM;


    //Resources
    public static final String RESOURCE_SUCCESSFULLY_CREATED = "Resource created successfully.";
    public static final String INVALID_RESOURCE_DATA = "Invalid resource data.";
    public static final String RESOURCE_FETCHED_SUCCESSFULLY = "Resources fetched successfully.";
    public static final String RESOURCE_NOT_FOUND = "Resource not found.";
    public static final String RESOURCE_EMPTY = "The requested resource contains no data.";
    public static final String RESOURCE_SUCCESSFULLY_EDITED = "Resource edited successfully.";
    public static final String RESOURCE_SUCCESSFULLY_DELETED = "Resource deleted successfully.";
    public static final String RESOURCE_PARTIAL_CONTENT = "Partial content of the resource.";
    public static final String RESOURCE_SUCCESSFULLY_RELATION_CREATED = "Relationship created successfully.";
    public static final String RESOURCE_SUCCESSFULLY_RELATION_DELETED = "Relationship deleted successfully.";

    //Status codes
    public static final String CREATED = "201";
    public static final String BAD_REQUEST = "400";
    public static final String OK = "200";
    public static final String NOT_FOUND = "404";
    public static final String NO_CONTENT = "204";
    public static final String PARTIAL_CONTENT = "206";

    //Media types
    public static final String APPLICATION_JSON = "application/json";

    //Links
    public static final String FIRST_PREV_NEXT_LAST = "first/prev/next/last";
    public static final String PAGE_NAME = "page";
    public static final String PAGE_EXPRESSION = "0 / page - 1 / page + 1 / total pages - 1";
    public static final String SIZE_NAME = "size";
    public static final String SIZE_EXPRESSION = "size";
    public static final String ID = "id";

    //Variables
    public static final String GENRE = "Genre";
    public static final String CHARACTER = "Character";
    public static final String THE_USERNAME = "The username";
    public static final String THE_PASSWORD = "The password";
    public static final String THE_NAME = "The name";
    public static final String THE_AGE = "The age";
    public static final String THE_TITLE = "The title";
    public static final String THE_WEIGHT = "THe weight";
    public static final String THE_HISTORY = "THe history";
    public static final String THE_CREATION_DATE = "The creation date";
    public static final String THE_SCORE = "The score";
    public static final String THE_SIZE = "The size";
    public static final String THE_NUMBER = "The number";
    public static final String THE_FILM_ID = "The film's id";
    public static final String THE_GENRE_ID = "The genre's id";
    public static final String THE_ORDER_MUST_BE = "The order must be ASC/DESC";

    //Validations
    public static final String NOT_FOUND_MESSAGE = " not found.";
    public static final String CHARACTER_NOT_FOUND_MESSAGE = CHARACTER + NOT_FOUND_MESSAGE;
    public static final String FILM_NOT_FOUND_MESSAGE = "Film" + NOT_FOUND_MESSAGE;
    public static final String GENRE_NOT_FOUND_MESSAGE = GENRE + NOT_FOUND_MESSAGE;
    public static final String NOT_BE_NULL_MESSAGE = " cannot be null.";
    public static final String HAVE_VALID_LENGTH_MESSAGE = " must have a valid length.";
    public static final String MUST_BE_WITHIN_A_VALID_RANGE = " must be within a valid range.";
    public static final String MUST_BE_ZERO_OR_GREATER = " must be 0 or greater.";
    public static final String ALREADY_EXIST = " already exists.";
    public static final String NOT_EXIST = " not exists.";
    public static final String INVALID_CREDENTIALS = "Invalid credentials.";
    public static final String MUST_BE_POSITIVE = " must be positive";
    public static final String MUST_BE_A_PAST_DATE = " must be a past date.";

    //Sizes
    public static final int MIN_SIZE_USERNAME = 5;
    public static final int MAX_SIZE_USERNAME = 18;
    public static final int MIN_SIZE_PASSWORD = 5;
    public static final int MAX_SIZE_PASSWORD = 18;
    public static final int MAX_SIZE_EMAIL = 320;
    public static final int MIN_SIZE_NAME = 1;
    public static final int MAX_SIZE_NAME = 50;
    public static final int MAX_SIZE_HISTORY = 500;
    public static final int MIN_SIZE_TITLE = 1;
    public static final int MAX_SIZE_TITLE = 50;
    public static final int MIN_SIZE_HISTORY = 10;
    public static final int MIN_SCORE = 1;
    public static final int MAX_SCORE = 5;

    //Examples
    public static final String USERNAME_EXAMPLE = "admin";
    public static final String PASSWORD_EXAMPLE = "admin";
    public static final String ID_EXAMPLE = "1";
    public static final String NAME_EXAMPLE = "Anthony Stark";
    public static final String AGE_EXAMPLE = "53";
    public static final String TITLE_EXAMPLE = "Iron Man 1";
    public static final String GENRE_NAME_EXAMPLE = "SuperHeroes";
    public static final String GENRE_IMAGE_EXAMPLE = "https://www.rhpaenews.com/wp-content/uploads/2017/04/superheroe-portada.jpg";
    public static final String CHARACTER_IMAGE_EXAMPLE = "https://static.wikia.nocookie.net/disney/images/9/96/Iron-Man-AOU-Render.png/revision" +
            "/latest/scale-to-width-down/1200?cb=20180410032118&path-prefix=es";
    public static final String WEIGHT_EXAMPLE = "102";
    public static final String HISTORY_EXAMPLE = "The movie tells the story of Tony Stark, a billionaire industrialist and genius " +
            "inventor, who is kidnapped and forced to build a devastating weapon.";
    public static final String FILM_IMAGE_EXAMPLE = "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/b/bf" +
            "/Iron_Man_1_Portada.png/revision/latest?cb=20191029194450&path-prefix=es";
    public static final String CREATION_DATE_EXAMPLE = "2008-04-30";
    public static final String SCORE_EXAMPLE = "5";
    public static final String ORDER_EXAMPLE = "ASC";
    public static final String SIZE_EXAMPLE = "10";
    public static final String PAGE_EXAMPLE = "0";
    public static final String TOTAL_PAGES_EXAMPLE = "1";
    public static final String TOTAL_ELEMENTS_EXAMPLE = "1";

    //Booleans
    public static final String TRUE = "true";
    public static final String FALSE = "false";
}
