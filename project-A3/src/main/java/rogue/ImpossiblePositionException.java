package rogue;

import java.awt.Point;

public class ImpossiblePositionException extends Exception {

    /**.
     * Impossible position exception
     * @param i
     * @param itemRoom
     */
    public ImpossiblePositionException(Item i, Room itemRoom) {
        int w = itemRoom.getWidth() / 2;
        int h = itemRoom.getHeight() / 2;

        i.setXyLocation(new Point(w, h));
        try {
            itemRoom.addItem(i);
        } catch (ImpossiblePositionException e) {

        } catch (NoSuchItemException e) {

        }
    }
}
