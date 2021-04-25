package rogue;

import java.util.ArrayList;


public class Door {

    private int room;
    private int connectedRoom;
    private int wallPos;
    private String direction;

    private Room doorRoom;
    private ArrayList<Room> conRooms = new ArrayList<>();

    /**.
     * Default Constructor
     */
    public Door() {

    }

    /**.
     * Door constructor
     * @param dRoom id int
     */
    public Door(int dRoom) {
        this.room = dRoom;
    }

     /**.
     * Door room setter
     * @param dRoomID id int
     */
    public void setDoorRoom(int dRoomID) {
        this.room = dRoomID;
    }

    /**.
     * Door room getter
     * @return room id int
     */
    public int getDoorRoom() {
        return this.room;
    }

    /**.
     * Sets connected rooms of door
     * @param conRoomID connected room int
     */
    public void setConnectedRoom(int conRoomID) {
        this.connectedRoom = conRoomID;
    }

    /**.
     * Gets connected rooms of door
     * @return connected room int
     */
    public int getConnectedRoom() {
        return this.connectedRoom;
    }

    /**.
     * Sets door direction
     * @param dDir
     */
    public void setDoorDirection(String dDir) {
        this.direction = dDir;
    }

    /**.
     * Gets door direction
     * @return door direction string
     */
    public String getDoorDirection() {
        return this.direction;
    }

    /**.
     * Connects door to room
     * @param r
     */
    public void connectRoom(Room r) {
        if (r.getId() == this.room) {
            setConnectedRoom(r.getId());
        }
    }

    /**.
     * Gets connected rooms
     * @return array list of connected rooms
     */
    public ArrayList getConnectedRooms() {

        return null;
    }

    /**.
     * Gets other rooms
     * @param currrentRoom to get
     * @return other room
     */
    public Room getOtherRoom(Room currrentRoom) {

        return currrentRoom;
    }
}
