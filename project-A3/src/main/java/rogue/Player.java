package rogue;
import java.awt.Point;

/**.
 * The player character.
 */
public class Player {

    //CLASS VARIABLES
    private String playerName;
    private Point playerLocation;
    private Room playerRoom;

    /**.
     * Default constructor
     */
    public Player() {
        setName("Player");
        setXyLocation(new Point(0, 0));
        setCurrentRoom(playerRoom);
    }

    /**
     * @param name
     */
    public Player(String name) {

        this.playerName = name;
    }

    /**.
     * Player name getter
     * @return player name string
     */
    public String getName() {

        return this.playerName;
    }

    /**.
     * Player name setter
     * @param newName
     */
    public void setName(String newName) {

        this.playerName = newName;
    }

    /**.
     * Player location getter
     * @return player location point
     */
    public Point getXyLocation() {

        return this.playerLocation;
    }


    /**.
     * Player location setter
     * @param newXyLocation
     */
    public void setXyLocation(Point newXyLocation) {

        this.playerLocation = newXyLocation;
    }

    /**.
     * Gets current player room
     * @return player room object
     */
    public Room getCurrentRoom() {

        return this.playerRoom;
    }

    /**.
     * Sets current player room
     * @param newRoom
     */
    public void setCurrentRoom(Room newRoom) {

        this.playerRoom = newRoom;
    }
}
