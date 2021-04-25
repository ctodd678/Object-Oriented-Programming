package rogue;


public class NoSuchItemException extends Exception {

    /**.
     * No such item exception
     * @param i
     * @param itemRoom
     */
    public NoSuchItemException(Item i, Room itemRoom) {

        if (itemRoom.getRoomItems().contains(i)) {
            itemRoom.getRoomItems().remove(i);
        }
    }
}
