import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

class Game{
    /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
July 5, 2026
This class is use to hold the data for the Game nodes and contains functions that directly affect specific game nodes

 ---------------------------------------------------------------------------------------------------------------------*/
    String name;
    String director;
    String genre;
    LocalDate release;
    float origPrice;
    float currPrice;
    int discount;
    Game next;
    Game prev;

    Game(String name, String director, String genre, LocalDate release,  float origPrice, float currPrice, int discount){

        this.name = name;
        this.director = director;
        this.genre = genre;
        this.release = release;
        this.origPrice = origPrice;
        this.currPrice = currPrice;
        this.discount = discount;
        this.next = null;
        this.prev = null;
    }
    //deInitialize
    //Deletes all data within game
    //Returns true when deinitialized

    boolean deInitialize(){
        this.name = null;
        this.director = null;
        this.genre = null;
        this.release = null;
        this.origPrice = 0;
        this.currPrice = 0;
        this.discount = 0;
        this.next = null;
        this.prev = null;
        return true;
    }

    //editGame
    //Edits any data found within the Game node
    //Takes in String for new name of game, String for new director, String for the new genre, LocalDate for the new release date, float for the new original price, float for new current price, and int for new discount
    //Returns edited game

    Game editGame(String name, String director, String genre, LocalDate release, float origPrice, float currPrice, int discount){
        this.name = name;
        this.director = director;
        this.genre = genre;
        this.release = release;
        this.origPrice = origPrice;
        this.currPrice = currPrice;
        this.discount = discount;

        printGame();
        return this;
    }

    //applyDiscount
    //applies discount to current price and saves discount to object for reversal later
    //Takes in integer for new discount
    //returns new current price

    float applyDiscount(int Discount){

        if(this.discount > 0){

            IO.println("Please delete current discount before applying new discount");

            return this.currPrice;

        }
        this.discount = Discount;


        this.currPrice = this.currPrice * (1 - ((float) this.discount /100));

        printGame();

        return this.currPrice;

    }

    //deleteDiscount
    //mathematically undoes discount effect on current price and returns discount variable to 0
    //returns new current price

    float deleteDiscount(){
        if(this.discount == 0){
            IO.println("No discount applied");
            return this.currPrice;
        }

        currPrice = currPrice / (1-((float) discount / 100));
        this.discount = 0;

        printGame();
        return currPrice;

    }

    //printGame
    //Prints information for specific game
    //returns the game being printed

    Game printGame(){
        IO.println("\nGame Name: " + this.name);
        IO.println("Director: " + this.director);
        IO.println("Genre: " + this.genre);
        IO.println("Release Date: " + this.release);
        IO.println("Original Price: " + this.origPrice);
        IO.println("Current Price: " + this.currPrice);
        IO.println("Discount: " + this.discount + "\n");
        return this;
    }
}

class GameList{

        /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
July 5, 2026
This class is used to define the game list, track of the head and tail, as well as contain all functions pertaining to game list

 ---------------------------------------------------------------------------------------------------------------------*/

    Game head;
    Game tail;
    Tools tool = new Tools();
    InputHandler IM = new InputHandler();


    //addGame
    //checks if queue is empty and adds game to the tail. Makes new game the head if it is. Separate from manualAdd so it can be reused for reading file.
    //Takes in a Game object to add to list
    //Returns the game object added or null if addition fails

    Game addGame(Game game){

        //Make sure the name is not a duplicate

        if(tool.isDuplicate(this, game.name)){

            return null;

        }
        IO.println("\n" + game.name + " Added to list\n");

        if(this.head == null){

            this.head = game;

            this.tail = game;

        } else {

            this.tail.next = game;

            game.prev = this.tail;

            this.tail = game;

        }

        return game;

    }

    //printAll
    //Moves through game list and prints each object
    //returns game list being printed

    GameList printAll(){

        //Checks to make sure queue is not empty
        if(this.head == null){

            IO.println("Games list is empty");

            return this;
        }

        IO.println("\nPrinting Games\n--------------------------------------------------------------------------------------------------------------");

        Game cursor = this.head;

        while(cursor!=null){

            cursor.printGame();

            cursor = cursor.next;

        }

        IO.println("---------------------------------------------------------------------------------------------------------------\n");

        return this;
    }




    //removeGame
    //Removes game from game list and stitches the list back together
    //Takes game object
    //returns true if successful and false if not

    boolean removeGame(Game game){

        if(this.head == null){

            IO.println("Game list is empty");

            return false;

        }



        if(game == this.head){

            this.head = game.next;

        } else if(game == this.tail){

            this.tail = game.prev;

            this.tail.next = null;
        } else {

            game.prev.next = game.next;

            game.next.prev = game.prev;

        }
        IO.println("\nRemoving " + game.name + " From list\n");
        printAll();
        game.deInitialize();

        return true;
    }


    //saveAll
    //Saves all entries in game list to file entered by user
    //returns true if successful and false if not

    boolean saveAll(){

        if(this.head == null){

            IO.println("Game list is empty");

            return false;
        }

        String file = IO.readln("Please enter file to save to: ");

        try {

            Files.write(Paths.get(file), "".getBytes());

            Game cursor = this.head;

            while(cursor != null){

                String data = cursor.name + "|" + cursor.director + "|" + cursor.genre + "|" + cursor.release + "|" + cursor.origPrice + "|" + cursor.currPrice + "|" + cursor.discount + "\n";

                Files.write(Paths.get(file), data.getBytes(), java.nio.file.StandardOpenOption.APPEND);

                cursor = cursor.next;
            }

            IO.println("Game list saved to " + file);

            return true;

        } catch (IOException e) {

            IO.println("Could not write to file " + file + ": " + e.getMessage());

            return false;

        }
    }
    //readAll
    //Reads file line by line and passes parsed line variables to addgame()
    //Returns true if file is read correctly and false if not

    boolean readAll(String file) {


        try {

            List<String> entries = Files.readAllLines(Paths.get(file));

            //for each line in the file, split the data and pass it to addGame, counter used to inform user which line failed to import

            int counter = 0;

            for (String line : entries) {

                counter++;

                String[] data = line.split("\\|");


                //Error check to make sure there are six pieces of data
                if (data.length != 7) {

                    IO.println("\nCould not add Game at line " + counter + " because information is either missing or there are too many variables");

                    continue;
                }


                String name = data[0];

                String director = data[1];

                String genre = data[2];


                if(tool.isVariable(data[3], "LocalDate") == false){

                    IO.println("\nCould not add Game at line " + counter + " because release date variable is not LocalDate");

                    continue;

                }

                LocalDate releaseDate = LocalDate.parse(data[3]);


                if(tool.isVariable(data[4], "float") == false){

                    IO.println("\nCould not add Game at line " + counter + " because original cost variable is not float");

                    continue;

                }
                float origCost = Float.parseFloat(data[4]);

                if(tool.isVariable(data[5], "float") == false){

                    IO.println("\nCould not add Game at line " + counter + " because current cost variable is not float");

                    continue;

                }

                float currCost = Float.parseFloat(data[5]);

                if(tool.isVariable(data[6], "int") == false){

                    IO.println("\nCould not add Game at line " + counter + " because discount variable is not integer");

                    continue;

                }

                int discount = Integer.parseInt(data[6]);

                addGame(new Game(name, director, genre, releaseDate, origCost, currCost, discount));


            }

        } catch (IOException e) {

            IO.println("Could not read file " + file + ": " + e.getMessage());

            return false;

        }

        if(printAll() == null){

            IO.println("GameList no good");

        }

        return true;
    }


    //searchGame
    //Looks through game list using string as name and returns game found
    //takes in String for game name being searched for
    //returns game if found and null if not

    Game searchGame(String input){


        if(this.head == null){

            IO.println("Game list is empty");

            return null;

        }
        String name = input;
        Game cursor = this.head;


        while(!cursor.name.equalsIgnoreCase(name)){

            cursor = cursor.next;

            if(cursor == null){

                IO.println("Could not find game");

                return null;

            }
        }
        return cursor;



    }
}


class InputHandler{

    /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
July 5,2026
Input Handler Class handles all major menu changes for UI and passes user input to functions in other classes

---------------------------------------------------------------------------------------------------------------------*/

    Tools tool = new Tools();

    //mainMenu
    //Gives the user a list of options to use and calls on the function necessary to complete each one
    //Takes in game list to interact with
    //returns game list when done

    GameList mainMenu(GameList queue){

        while(true){

            String choice = IO.readln("What would you like to do? (1.add game/ 2.print list/ 3.read file/ 4.save to file/ 5.search game/ 0.exit program) ");

            if(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("add")){

                addMenu(queue);

            } else if(choice.equalsIgnoreCase("2") || choice.equalsIgnoreCase("print")){

                queue.printAll();

            } else if(choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("read")){

                readMenu(queue);

            }else if (choice.equalsIgnoreCase("4") || choice.equalsIgnoreCase("save")){

                queue.saveAll();

            } else if(choice.equalsIgnoreCase("5") || choice.equalsIgnoreCase("search")){

                searchMenu(queue);

            }
            else if(choice.equalsIgnoreCase("0") || choice.equalsIgnoreCase("exit")){

                break;

            } else {

                IO.println("Invalid choice. Please try again.");
            }

        }

        return queue;
    }

    boolean readMenu(GameList queue){
        String file = IO.readln("Please enter the file: ");
        return queue.readAll(file);
    }

    //editMenu
    //Gives list of specific game object variables for users to edit
    //Takes in game object to edit
    //Returns game object edited
    Game editMenu(Game game){


        String name = game.name;
        String director = game.director;
        String genre = game.genre;
        LocalDate release = game.release;
        float origPrice = game.origPrice;
        float currPrice = game.currPrice;
        int discount = game.discount;

        while(true){

            String input = IO.readln("What would you like to edit? ( 1.name/ 2.director/ 3.genre/ 4.release/ 5.origPrice/ 6.currPrice/ 0.exit to search menu) ");

            if(input.equalsIgnoreCase("name")||input.equalsIgnoreCase("1")){

                name = IO.readln("Enter new name: ");

            } else if(input.equalsIgnoreCase("director")||input.equalsIgnoreCase("2")){

                director = IO.readln("Enter new director: ");

            } else if(input.equalsIgnoreCase("genre")||input.equalsIgnoreCase("3")){

                genre = IO.readln("Enter new genre: ");

            } else if(input.equalsIgnoreCase("release")||input.equalsIgnoreCase("4")){

                input = IO.readln("Enter new release date (YYYY-MM-DD): ");

                if(tool.isVariable(input, "LocalDate") == false){

                    continue;

                }

                release = LocalDate.parse(input);

            }
            else if(input.equalsIgnoreCase("origPrice")||input.equalsIgnoreCase("5")){

                input = IO.readln("Enter new original price: ");

                if(tool.isVariable(input, "float") == false){
                    continue;
                }
                origPrice = Float.parseFloat(input);

            } else if(input.equalsIgnoreCase("currPrice")||input.equalsIgnoreCase("6")){

                input = IO.readln("Enter new current price: ");

                if(tool.isVariable(input, "float") == false){
                    continue;
                }
                currPrice = Float.parseFloat(input);

            } else if (input.equalsIgnoreCase("exit") ||input.equalsIgnoreCase("0")){

                IO.println("\nReturning to menu\n");

                return game;

            } else {

                IO.println("Invalid input. Please enter 1.name, 2.director, 3.genre, 4.release, 5.origPrice, 6.currPrice or 0.exit.");
                continue;
            }

            game.editGame(name, director, genre, release, origPrice, currPrice, discount);
        }


    }

    //searchMenu
    //Searches for a specific game and gives the user a list of ways they can interact with the game object
    //takes in game list to search through
    //returns game object found

    Game searchMenu(GameList queue){
        String input = IO.readln("Please enter game name: ");

        Game game;

        if(queue.searchGame(input)!=null){
            IO.println("\n" + input + " Found in database\n");
            game = queue.searchGame(input);
            game.printGame();

        } else {

            return null;
        }
        while(true){
            if(game.discount>0){
                input = IO.readln("Would you like to 1.remove, 2.edit, 3.delete discount or 0.Return to menu ? \n");
            } else{
                input = IO.readln("Would you like to 1.remove, 2.edit, 3.apply discount or 0.Return to menu ? \n");
            }

            if(input.equalsIgnoreCase("1")){
                if(tool.Confirmation() == false){
                    continue;
                }
                queue.removeGame(game);
                return null;

            } else if (input.equalsIgnoreCase("2")){

                editMenu(game);

            } else if (input.equalsIgnoreCase("3") && game.discount == 0){

                input = IO.readln("What discount would you like to apply?\n");

                if(tool.isVariable(input, "int")){

                    game.applyDiscount(Integer.parseInt(input));

                } else{

                    continue;

                }
            }
            else if(input.equalsIgnoreCase("3") && game.discount != 0){

                game.deleteDiscount();

            } else if (input.equalsIgnoreCase("0")){

                return game;

            } else  {

                continue;

            }

        }
    }

    //addMenu
    //Allows the user to input variables for game being added to game list. Repeats until user has added all games they would like
    //takes in game list to add on to
    //returns game list that was added on to

    GameList addMenu(GameList queue){

        boolean question = false;

        String input;

        while(true) {

            if(question) {

                //Checks if user would like to continue to add games so that they can add multiple without returning to menu

                input = IO.readln("Continue adding game to list manually? (1.yes/2.no)\n");

                if (input.equalsIgnoreCase("2")) {

                    queue.printAll();

                    IO.println("Exiting to menu");

                    return queue;
                }

                if (input.equalsIgnoreCase("1") == false) {

                    IO.println("Invalid input, please enter yes or no.");

                    continue;
                }

            }
            question = true;

            input = IO.readln("Enter game name: ");

            if(tool.isDuplicate(queue, input) == true){

                continue;

            }

            String name = input;

            String director = IO.readln("Enter game director: ");


            String genre = IO.readln("Enter game genre: ");

            input = IO.readln("Enter game release date (YYYY-MM-DD): ");

            if(!tool.isVariable(input, "LocalDate")){

                continue;

            }

            LocalDate release = LocalDate.parse(input);

            input = IO.readln("Enter game original price: ");

            if(!tool.isVariable(input, "float")){

                continue;

            }
            float origPrice = Float.parseFloat(input);

            input = IO.readln("Enter game current price: ");

            if(!tool.isVariable(input, "float")){

                continue;
            }
            float currPrice = Float.parseFloat(input);

            queue.addGame(new Game(name, director, genre, release, origPrice, currPrice, 0));
        }


    }

}

class Tools {

    /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
July 5,2026
This is the Tools class. The purpose is to create a selection of general use tools for other methods to use.

---------------------------------------------------------------------------------------------------------------------*/


    // Checks if game is already in list by going through list and comparing the name
    // Takes in GameList and String name for searching
    //returns true if found and false if not

    boolean isDuplicate(GameList gameList, String name) {


        Game cursor = gameList.head;

        while (cursor != null) {

            if (cursor.name.equalsIgnoreCase(name)) {

                IO.println("\nCould not add " + cursor.name + " because name already exists\n");

                return true;

            }

            cursor = cursor.next;
        }
        return false;
    }


    //isVariable
    //Ensures that the given String can be parsed to int or float. Used to error check previous methods in a cleaner fashion.
    //Takes in String for input and String for the variable type the program is checking for (int or float)
    //returns true if it can be parsed to that variable type and false if not

    boolean isVariable(String input, String variableType) {

        if (variableType.equalsIgnoreCase("int")) {
            try {

                Integer.parseInt(input);

                return true;

            } catch (NumberFormatException e) {

                IO.println("Invalid input. Please enter an integer.");

                return false;

            }

        } else if (variableType.equalsIgnoreCase("float")) {

            try {

                Float.parseFloat(input);

                return true;

            } catch (NumberFormatException e) {

                IO.println("Invalid input. Please enter a float.");

                return false;
            }
        } else if (variableType.equalsIgnoreCase("LocalDate")) {

            try {

                LocalDate.parse(input);

                return true;

            } catch (Exception e) {

                IO.println("Invalid input. Please enter a date (YYYY-MM-DD).");

                return false;
            }
        }

        return false;
    }


    //isVariable
    //Ensures that the user would like to continue. Used to ensure user error is limited.
    //returns true if it can be parsed to that variable type and false if not

    boolean Confirmation() {

        while (true) {

            String input = IO.readln("Are you sure? 1.No / 2.Yes \n");

            if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("no")) {

                return false;

            } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("yes")) {

                return true;


            } else {

                IO.println("Invalid input\n");

                continue;

            }
        }


    }
}



class Main {

    /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
July 5,2026
This is the Main class. The purpose of the Main is to create a game list and input handler and then pass the program off to them.

 ---------------------------------------------------------------------------------------------------------------------*/



    //main
    //used to initialize game list and begin input handler
    //no arguments
    //no return
    public void main() {

        IO.println("Search for game to edit, remove, or apply discount\n");

        GameList queue = new GameList();

        InputHandler menu = new InputHandler();

        menu.mainMenu(queue);

    }

}