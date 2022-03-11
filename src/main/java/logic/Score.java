package logic;


public class Score {
    private int points;
    private static int cap1 = 5;
    private static int cap2 = 25;

    public Score() {
        this.points = 0;
    }

    public int getPoints() {
        return this.points;
    }


    /**
     * Increases the amount of points we have.
     *
     * @param food the food the snake eats and from which we get the points to add.
     */
    public void increaseScore(Food food) {
        //check how much the new food should be worth
        this.points += food.getPoints();
        if (this.points >= cap1 && this.points < cap2) {
            food.setPoints(5);
        } else if (this.points >= cap2) {
            food.setPoints(25);
        }
    }
}
