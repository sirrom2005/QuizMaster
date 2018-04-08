package info.rohanmorris.quizmaster;

import java.util.ArrayList;

/**
 * Created by iceman
 * date 3/24/18...
 */

class Common {

    static ArrayList<Category> getGameLevelList(){
        ArrayList<Category> level2 = new ArrayList<>();
        level2.add(new Category(0,"Any Level"));
        level2.add(new Category(1,"Easy"));
        level2.add(new Category(2,"Medium"));
        level2.add(new Category(3,"Hard"));

        return level2;
    }

    static ArrayList<Category> getGameCategoryList(){
        ArrayList<Category> cat = new ArrayList<>();

        cat.add(new Category(0,"All Categories"));
        cat.add(new Category(9,"General Knowledge"));
        cat.add(new Category(10, "Entertainment: Books"));
        cat.add(new Category(11, "Entertainment: Film"));
        cat.add(new Category(12, "Entertainment: Music"));
        cat.add(new Category(13, "Entertainment: Musicals &amp; Theatres"));
        cat.add(new Category(14, "Entertainment: Television"));
        cat.add(new Category(15, "Entertainment: Video Games"));
        cat.add(new Category(16, "Entertainment: Board Games"));
        cat.add(new Category(17, "Science &amp; Nature"));
        cat.add(new Category(18, "Science: Computers"));
        cat.add(new Category(19, "Science: Mathematics"));
        cat.add(new Category(20, "Mythology"));
        cat.add(new Category(21, "Sports"));
        cat.add(new Category(22, "Geography"));
        cat.add(new Category(23, "History"));
        cat.add(new Category(24, "Politics"));
        cat.add(new Category(25, "Art"));
        cat.add(new Category(26, "Celebrities"));
        cat.add(new Category(27, "Animals"));
        cat.add(new Category(28, "Vehicles"));
        cat.add(new Category(29, "Entertainment: Comics"));
        cat.add(new Category(30, "Science: Gadgets"));
        cat.add(new Category(31, "Entertainment: Japanese Anime &amp; Manga"));
        cat.add(new Category(32, "Entertainment: Cartoon &amp; Animations  "));

        return cat;
    }

    public static class Category {
        private int id;
        private String title;

        private Category(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
