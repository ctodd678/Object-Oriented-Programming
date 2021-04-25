package rogue;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Map;

//import org.json.simple.ItemList;

import java.awt.Point;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room  {

   //CLASS VARIABLES

   private int width;
   private int height;
   private int roomID;
   private boolean roomStart;

   private ArrayList<Item> roomItems = new ArrayList<>();
   private ArrayList<Door> roomDoors = new ArrayList<>();
   private ArrayList<String> doorDirections = new ArrayList<>();
   private ArrayList<Integer> doorLocations = new ArrayList<>();
   private HashMap<String, Integer> doors = new HashMap<>();

   private Player roomPlayer;
   private Rogue  roomRogue;

   /**.
    * Default Constructor
    */
   public Room() {
      setWidth(0);
      setHeight(0);
      setId(0);
      setStart(false);
   }

   /**.
    * Constructor
    * @param rWidth
    * @param rHeight
    * @param rID
    * @param rStart
    * @param rRogue
    */
   public Room(int rWidth, int rHeight, int rID, boolean rStart, Rogue rRogue) {

      this.width = rWidth;
      this.height = rHeight;
      this.roomID = rID;
      this.roomStart = rStart;
      this.roomRogue = rRogue;
   }

   // Required getter and setters below

   /**.
    * Width getter
    * @return room width
    */
   public Rogue getRogue() {

      return this.roomRogue;
   }

   /**.
    * Width setter
    * @param newRogue
    */
   public void setRogue(Rogue newRogue) {

      this.roomRogue = newRogue;
   }

   /**.
    * Width getter
    * @return room width
    */
   public int getWidth() {

      return this.width;
   }

   /**.
    * Width setter
    * @param newWidth
    */
   public void setWidth(int newWidth) {

      this.width = newWidth;
   }

   /**.
    * Height getter
    * @return room height
    */
   public int getHeight() {

      return this.height;
   }

   /**.
    * Height setter
    * @param newHeight
    */
   public void setHeight(int newHeight) {

      this.height = newHeight;
   }

   /**.
    * Room id getter
    * @return room id
    */
   public int getId() {

      return this.roomID;
   }

   /**.
    * Room id setter
    * @param newId
    */
   public void setId(int newId) {

      this.roomID = newId;
   }

   /**.
    * Get room start value
    * @return room id
    */
   public boolean getStart() {

      return this.roomStart;
   }

   /**.
    * Sets room start value
    * @param isStart
    */
   public void setStart(boolean isStart) {
      this.roomStart = isStart;
   }

   /**.
    * Get room doors
    * @return room doors array list
    */
   public ArrayList<Door> getRoomDoors() {

      return this.roomDoors;
   }

   /**.
    * Set room doors
    * @param newRoomDoors
    */
   public void setRoomDoors(ArrayList<Door> newRoomDoors) {

      this.roomDoors = newRoomDoors;
   }

   /**.
    * Get room items
    * @return room items array list
    */
   public ArrayList<Item> getRoomItems() {

      return this.roomItems;
   }

   /**.
    * Set room items
    * @param newRoomItems
    */
   public void setRoomItems(ArrayList<Item> newRoomItems) {

      this.roomItems = newRoomItems;
   }

   /**.
     * Check if there is already item in pos
     * @param itemPos
     * @return true or false
     */
   public boolean checkItemPos(Point itemPos) {
      for (int i = 0; i < roomItems.size(); i++) {
         if (itemPos.equals(roomItems.get(i).getXyLocation())) {
            return true;
         }
      }
      return false;
   }

   /**.
     * Check if item exists in the list of items
     * @param id
     * @return true or false
     */
    public boolean checkItemID(int id) {

      for (int i = 0; i < roomRogue.getItems().size(); i++) {
         if (id == roomRogue.getItems().get(i).getId()) {
            return true;
         }
      }
      return false;
   }

   /**.
     * Add item
     * @param toAdd
     * @throws ImpossiblePositionException
     * @throws NoSuchItemException
     */
   public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException {
      int itemX = toAdd.getXyLocation().x;
      int itemY = toAdd.getXyLocation().y;
      Point itemPos = toAdd.getXyLocation();

      if (itemX == getWidth() - 1 || itemY == getHeight() - 1 || itemX == 0 || itemY == 0) {
         throw new ImpossiblePositionException(toAdd, this);
      } else if (itemPos.equals(getPlayer().getXyLocation())) {
         throw new ImpossiblePositionException(toAdd, this);
      } else if (checkItemPos(itemPos)) {
         throw new ImpossiblePositionException(toAdd, this);
      } else if (!checkItemID(toAdd.getId())) {
         throw new NoSuchItemException(toAdd, this);
      }
      roomItems.add(toAdd);
   }

   /**.
    * Verifies if correct room
    * @return true or false
    * @throws NotEnoughDoorsException
    */
   public boolean verifyRoom() throws NotEnoughDoorsException {
      int pX = getPlayer().getXyLocation().x;
      int pY = getPlayer().getXyLocation().y;

      //CHECK VALID ITEM POS
      for (Item temp : roomItems) {
         int iX = temp.getXyLocation().x;
         int iY = temp.getXyLocation().y;

         if ((iX == getWidth() -  1 || iX == 0) || (iY == getHeight() -  1 || iY == 0)) {
            return false;
         }
      }
      //CHECK VALID PLAYER POS
      if (isPlayerInRoom()) {
         if ((pX == getWidth() -  1 || pX == 0) || (pY == getHeight() -  1 || pY == 0)) {
            return false;
         }
      }
      if (getRoomDoors().size() < 1) {
         throw new NotEnoughDoorsException(this.roomRogue, this);
      }
      return true;
   }

   /**.
    * Player getter
    * @return roomPlayer object
    */
   public Player getPlayer() {

      return this.roomPlayer;
   }

   /**.
    * Player setter
    * @param newPlayer
    */
   public void setPlayer(Player newPlayer) {

      this.roomPlayer = newPlayer;
   }

   /**.
    * Get room door
    * @param direction
    * @return door distance
    */
   public int getDoor(String direction) {

      int i = 0;
      int doorDistance = 0;
      int numDoors = doorDirections.size();

      while (i < numDoors) {
         if (doorDirections.get(i).equals(direction)) {
            doorDistance = doorLocations.get(i);
         }
         i++;
      }
      return doorDistance;
   }

/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

   /**.
    * Set room door
    * @param direction
    * @param location
    */
   public void setDoor(String direction, int location) {

      doorDirections.add(direction);
      doorLocations.add(location);
   }
     /**.
     * Get item name
     * @param id for item
     * @return item type string
     */
    public String getItemType(int id) {
      for (Item temp : roomRogue.getItems()) {
          if (temp.getId() == id) {
             return temp.getName().toUpperCase();
          }
      }
      return "4";
  }

   /**.
    * Checks if player is in room
    * @return false if no player, true if player or false for else case
    */
   public boolean isPlayerInRoom() {

      if (getPlayer() == null) {
         return false;
      } else {
         return getPlayer().getCurrentRoom().roomID == this.roomID;
      }
   }

   /**.
    * Checks if door is in room
    * @param door
    * @return true or false
    */
   public boolean doorCheck(int door) {
      for (int i = 0; i < doorDirections.size(); i++) {
         if (doorDirections.get(i).equals("N") && door == 1) {
            return true;
         } else if (doorDirections.get(i).equals("S") && door == 2) {
            return true;
         } else if (doorDirections.get(i).equals("E") && door == 2 + 1) {
            return true;
         } else if (doorDirections.get(i).equals("W") && door == 2 + 2) {
            return true;
         }
      }
      return false;
   }

   /**.
    * Gets string for top of room
    * @return string
    */
   public String topOfRoom() {
      String top = "";
      //TOP OF ROOM
      for (int i = 0; i < getWidth(); i++) {
         if (doorCheck(1)) {
            if ((i) == getDoor("N")) {
               top = top +  this.roomRogue.getSymbol("DOOR");
               i++;
            }
         }
         top = top + this.roomRogue.getSymbol("NS_WALL");
      }
      top = top + "\n";
      return top;
   }

   /**.
    * Gets string for bottom of room
    * @return string
    */
   public String bottomOfRoom() {
      String bottom = "";
      //BOTTOM OF ROOM
      for (int i = 0; i < getWidth(); i++) {
         if (doorCheck(2)) {
            if ((i) == getDoor("S")) {
               bottom = bottom +  this.roomRogue.getSymbol("DOOR");
               i++;
            }
         }
         bottom = bottom + this.roomRogue.getSymbol("NS_WALL");
      }
      bottom = bottom + "\n";
      return bottom;
   }

   /**.
    * Determines whether to set item in position
    * @param iPos
    * @return true or false
    */
   public Item getItemInPos(Point iPos) {
      Item tempItem = new Item();
      //SET ITEMS
      for (int k = 0; k < roomItems.size(); k++) {
         tempItem = roomItems.get(k);
         if (tempItem.getXyLocation().equals(iPos)) {
            break;
         }
      }
      return tempItem;
   }

   /**.
    * Removes item if player on item
    */
   public void checkItemPlayerPos() {
      Point p = getPlayer().getXyLocation();
      p = (new Point(getPlayer().getXyLocation().x - 1, getPlayer().getXyLocation().y));
      for (int i = 0; i < roomItems.size(); i++) {
         if (p.equals(roomItems.get(i).getXyLocation())) {
            roomItems.remove(i);
         }
      }
   }

   /**.
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
    * @return (String) String representation of how the room looks
    */
   public String displayRoom() {
      String roomPicture = "";
      roomPicture = roomPicture + topOfRoom();   //set top of room
      //MIDDLE OF ROOM
      for (int i = 0; i < getHeight() - 2; i++) {
         for (int j = 0; j < getWidth(); j++) {
            Point itemPos = new Point((j), (i + 1));
            Point playerPos = new Point((j + 1), (i + 1));
            //SET WEST WALLS/DOORS
            if (j == 0) {
               if (doorCheck(2 + 2)) {
                  if ((j) == getDoor("W")) {
                     roomPicture = roomPicture +  this.roomRogue.getSymbol("DOOR");
                     j++;
                  }
               }
               roomPicture = roomPicture + this.roomRogue.getSymbol("EW_WALL");
               j++;
            }
            //SET PLAYER
            if (isPlayerInRoom()) {
               if (getPlayer().getXyLocation().equals(playerPos)) {
                  roomPicture = roomPicture + this.roomRogue.getSymbol("PLAYER");
                  j++;
               }
            }
            //SET ITEMS
            if (checkItemPos(itemPos)) {
               roomPicture = roomPicture + this.roomRogue.getSymbol(getItemType(getItemInPos(itemPos).getId()));
               j++;
            }
            checkItemPlayerPos();
            //SET EAST WALLS/DOORS
            if (j == getWidth() - 1) {
               if (doorCheck(2 + 1)) {
                  if ((j) == getDoor("E")) {
                     roomPicture = roomPicture +  this.roomRogue.getSymbol("DOOR");
                     break;
                  }
               }
               roomPicture = roomPicture + this.roomRogue.getSymbol("EW_WALL");
               break;
            }
            roomPicture = roomPicture + this.roomRogue.getSymbol("FLOOR");
         }
         roomPicture = roomPicture + "\n";
      }
      roomPicture = roomPicture + bottomOfRoom();
      return roomPicture;
   }
}
