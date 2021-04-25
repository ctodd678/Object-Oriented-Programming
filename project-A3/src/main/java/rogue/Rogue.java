package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.awt.Point;


public class Rogue {

    //CLASS VARIABLES

    private Player          roguePlayer;

    private ArrayList<Room> rogueRooms = new ArrayList<>();
    private ArrayList<Item> rogueItems = new ArrayList<>();
    private ArrayList<Item> rogueLoot = new ArrayList<>();
    private HashMap<String, Character> rogueSymbols = new HashMap<>();


    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    private String currentDisplay;

    //private JSONParser parser = new JSONParser();

    private RogueParser parser = new RogueParser();

    /**
     * Default constructor.
     */
    public Rogue() {
        setPlayer(new Player());
    }

     /**
     * Rogue constructor.
     * @param theDungeonInfo parser
     */
    public Rogue(RogueParser theDungeonInfo) {

        this.parser = theDungeonInfo;
        this.rogueSymbols = theDungeonInfo.getItemSymbols();
    }

    /**.
     * Gets Rooms array
     * @return rooms array list
     */
    public ArrayList<Room> getRooms() {

        return rogueRooms;
    }

    /**.
     * Get Items array
     * @return items array list
     */
    public ArrayList<Item> getItems() {

        return this.rogueItems;
    }

    /**.
     * Player setter
     * @param thePlayer
     */
    public void setPlayer(Player thePlayer) {

        this.roguePlayer = thePlayer;
    }

    /**.
     * Get Player object
     * @return player object
     */
    public Player getPlayer() {

        return this.roguePlayer;
    }

    /**.
     * Set symbols
     * @param sParser parser
     */
    public void setSymbols(RogueParser sParser) {

        rogueSymbols = sParser.getItemSymbols();
    }

    /**
     * Get the character for a symbol.
     * @param symbolName (String) Symbol Name
     * @return (Character) Display character for the symbol
     */
    public Character getSymbol(String symbolName) {

        if (rogueSymbols.containsKey(symbolName)) {
            return rogueSymbols.get(symbolName);
        }

        // Does not contain the key
        return null;
    }

    /**.
     * Set loot
     * @param sParser parser
     */
    public void setLoot(RogueParser sParser) {

        Map tempMap = sParser.nextLoot();

        while (tempMap != null) {
            addLoot(tempMap);
            tempMap = sParser.nextLoot();
        }
    }

    /**.
     * Add loot item
     * @param toAdd
     */
    public void addLoot(Map toAdd) {

        Item tempItem = new Item();
        int iX = Integer.parseInt(toAdd.get("x").toString());
        int iY = Integer.parseInt(toAdd.get("y").toString());

        tempItem.setRoomId(Integer.parseInt(toAdd.get("room").toString()));
        tempItem.setId(Integer.parseInt(toAdd.get("id").toString()));
        tempItem.setXyLocation(new Point(iX, iY));

        rogueLoot.add(tempItem);   //add loot to array list
    }

    /**.
     * Set items
     * @param sParser
     */
    public void setItems(RogueParser sParser) {

        Map tempMap = sParser.nextItem();

        while (tempMap != null) {
            addItem(tempMap);
            tempMap = sParser.nextItem();
        }
    }

    /**.
     * Add item
     * @param toAdd
     */
    public void addItem(Map toAdd) {

        Item tempItem = new Item();

        tempItem.setId(Integer.parseInt(toAdd.get("id").toString()));
        tempItem.setName(toAdd.get("name").toString());
        tempItem.setType(toAdd.get("type").toString());
        tempItem.setDescription(toAdd.get("description").toString());

        rogueItems.add(tempItem);   //add Item to array list
    }

    /**.
     * Set room items
     * @param iRoom
     */
    public void setRoomLoot(Room iRoom) {

        Item tempItem = new Item();

        for (int i = 0; i < rogueLoot.size(); i++) {
            tempItem = rogueLoot.get(i);
            if (tempItem.getRoomId() == iRoom.getId()) {
                try {
                    iRoom.addItem(tempItem);
                } catch (ImpossiblePositionException e) {

                } catch (NoSuchItemException e) {

                }
            }
        }
    }

    /**.
     * Add room to rouge
     * @param toAdd
     */
    public void addRoom(Map toAdd) {
        Room tempRoom = new Room();

        tempRoom.setId(Integer.parseInt(toAdd.get("id").toString()));
        tempRoom.setStart(toAdd.get("start").toString().equals("true"));
        tempRoom.setHeight(Integer.parseInt(toAdd.get("height").toString()));
        tempRoom.setWidth(Integer.parseInt(toAdd.get("width").toString()));

        if (tempRoom.getStart()) {
            getPlayer().setCurrentRoom(tempRoom);
        }
        getPlayer().setXyLocation(new Point(2 + 2 + 1, 2 + 2 + 1));
        tempRoom.setPlayer(getPlayer());
        tempRoom.setRogue(this);
        setRoomLoot(tempRoom);
        rogueRooms.add(tempRoom);   //add room to array list
    }

    /**.
     * Create all game rooms
     * @param sParser
     */
    public void createRooms(RogueParser sParser) {

        setLoot(sParser);
        setItems(sParser);
        Map tempMap = sParser.nextRoom();

        while (tempMap != null) {
            addRoom(tempMap);
            tempMap = sParser.nextRoom();
        }
    }

    /**.
     * Returns the makeMove message
     * @param input key
     * @throws InvalidMoveException
     * @return message
     */
    public String makeMove(char input) throws InvalidMoveException {

        String afterMove = "";
        boolean valDisplay = false;

        afterMove = getNextDisplay();

        if (!setNewPlayerPos(input)) {
            throw new InvalidMoveException();
        }
        try {
            valDisplay = getPlayer().getCurrentRoom().verifyRoom();
        } catch (NotEnoughDoorsException e) {

        }

        if (valDisplay) {
            this.currentDisplay = afterMove;

        }
        return "Key Pressed: " + String.valueOf(input);
    }

    /**.
     * Sets the new player position when moved
     * @param move key char
     * @return true or false
     */
    public boolean setNewPlayerPos(char move) {

        Point currentPos = getPlayer().getXyLocation();
        int rW = getPlayer().getCurrentRoom().getWidth();
        int rH = getPlayer().getCurrentRoom().getHeight();

        if (move == UP && (currentPos.y - 1 != 0)) {
            currentPos = (new Point(currentPos.x, currentPos.y - 1));
            getPlayer().setXyLocation(currentPos);
        } else if (move == DOWN && (currentPos.y + 1 != rH - 1)) {
            currentPos = (new Point(currentPos.x, currentPos.y + 1));
            getPlayer().setXyLocation(currentPos);
        } else if (move == LEFT && (currentPos.x - 1 != 0)) {
            currentPos = (new Point(currentPos.x - 1, currentPos.y));
            getPlayer().setXyLocation(currentPos);
        } else if (move == RIGHT && (currentPos.x - 1 != rW - 2)) {
            currentPos = (new Point(currentPos.x + 1, currentPos.y));
            getPlayer().setXyLocation(currentPos);
        } else {
            return false;
        }
        return true;
    }

    /**.
     * Sets the current room display string
     * @param roomDisplay
     */
    public void setCurrentDisplay(String roomDisplay) {
        this.currentDisplay = roomDisplay;
    }

    /**.
     * Returns the current room display string
     * @return room display
     */
    public String getCurrentDisplay() {
        return this.currentDisplay;
    }

    /**.
     * Returns the string of room after move
     * @return room string
     */
    public String getNextDisplay() {

        return getPlayer().getCurrentRoom().displayRoom();
    }

    /**.
     * Creates a string that displays all rooms
     * @return allRooms string
     */
    public String displayAll() {

        String allRooms = "";

        for (int i = 0; i < rogueRooms.size(); i++) {
            allRooms = allRooms + rogueRooms.get(i).displayRoom();
        }

        return allRooms;
    }
}
