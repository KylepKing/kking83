

package prog01_aorderedlist;

/**
 *
 * @author Kyle King
 * Student ID: 895320938
 * Turn in date: March 17th 2024
 */
/**
* 
*
* CSC 1351 Programming Project No <1>

* Section <2>
*
* @author <Kyle King>
* @since <March 17th 2024>
*
*/
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

//Main class for the ordered list program

public class Prog01_aOrderedList 
{

    
    public static void main(String[] args) throws FileNotFoundException
    {
       // Get input file from user
       Scanner input = GetInputFile("Enter input filename:  ");
       // Create an instance of ordered list
       aOrderedList orderedList = new aOrderedList();
       // Loop through each line of input
       
       while(input.hasNextLine())
       {
           // Read a line from input
           String line = input.nextLine();
           // Split the line into an array of strings
           String[] carParam = line.split(",");
           String command = carParam[0].trim();
           // Check if the line has required parameters
           if (command.equals("A")) // If it's an "A" (add) operation
           {
                // Extract parameters for car creation
                String make = carParam[1].trim();
                int year = Integer.parseInt(carParam[2].trim());
                int price = Integer.parseInt(carParam[3].trim());
                // Create a new car instance
                   
                // Add the new car to the ordered list
                orderedList.add(new Car(make, year, price));
           }
           else if (command.equals("D")) // If it's a "D" (delete) operation
           {
                // Extract parameters for car identification
                   
                String makedelete = carParam[1].trim();
                int yeardelete = Integer.parseInt(carParam[2].trim());
                int index = -1;
                // Loop through the ordered list 
                for ( int i =0; i<orderedList.size(); i++)
                {
                    // Get the car at current index
                    Car car = (Car)orderedList.get(i);
                    // If found the matching car, remove it
                    if(car.getMake().equals(makedelete)&& car.getYear() == yeardelete)
                    {
                        index= i;
                        break;
                    }
                  
                }
                if (index !=-1)
                {
                    orderedList.remove(index);
                } 
                   
            }
              
       }
    
      
       // Get output file from user
       try (PrintWriter outputFile = GetOutputFile("Enter output filename:  ")) 
       {
           // Write number of cars in the list to output file
           outputFile.println("Number of cars:     " + orderedList.size());
           System.out.println();
           // Loop through each car in the ordered list
           for(int i = 0; i < orderedList.size(); i++)
           {
                
               Car car;
               // Check if the object at index is a Car instance
               if(orderedList.get(i) instanceof Car car1)
               {
                   car = car1;
                   // Write car details to output file
                   outputFile.printf("Make:      %10s%n", car.getMake());
                   outputFile.printf("Year:      %10d%n", car.getYear());
                   outputFile.printf("Price:    $%,10d%n", car.getPrice());
                   outputFile.println(); 
               }
               else
               {
                   // Write error message if object is not a Car
                   outputFile.println("Object at index " + i + " is not a Car.");
                   outputFile.println(); // Empty line to separate entries
               }
               
            }
        }
       
       
    }
    // Method to get input file from user
    public static Scanner GetInputFile(String UserPrompt)throws FileNotFoundException
    {
        // Print user prompt message
        
        Scanner userPrompt = new Scanner(System.in);
        System.out.print("Enter input filename: ");
        // Read filename from user input
        String fileName = userPrompt.nextLine();
        File carInfo = new File(fileName);
        // Loop until valid file name is entered
        while(!carInfo.exists())
        {
            System.out.print("File specified <"+userPrompt+"> does not exist.\n Would you like to continue? <Y/N>");
            if(userPrompt.nextLine().equalsIgnoreCase("Y"))
            {
                System.out.print("Enter input filename: ");
                fileName = userPrompt.nextLine();
                carInfo = new File(fileName);
                
            }
            else if(userPrompt.nextLine().equalsIgnoreCase("N"))
            {
                throw new FileNotFoundException("File not found or operation canceled.");
            }
            else
            {
                System.out.println("Please enter Y/N");
            }
            
        }
        return new Scanner(carInfo);
        
        
    }
    // Method to get output file from user
    public static PrintWriter GetOutputFile(String UserPrompt) throws FileNotFoundException
    {
        // Print user prompt message
        System.out.print("Enter output filename: ");
        Scanner userPrompt = new Scanner(System.in);
        String fileName = userPrompt.nextLine();
        PrintWriter newFile = new PrintWriter ("./src/output.txt");
       
     
        
        // Loop until valid file is entered
        while(!fileName.equals("./src/output.txt"))
        {
            
            System.out.print("File specified <"+userPrompt+"> does not exist.\n Would you like to continue? <Y/N>");
            if(userPrompt.nextLine().equalsIgnoreCase("Y"))
            {
                System.out.print("Enter output filename: ");
        
                fileName = userPrompt.nextLine();
            }
            else if(userPrompt.nextLine().equalsIgnoreCase("N"))
            {
                System.out.println("Please enter Y/N");
            }
            else
            {
                throw new FileNotFoundException("File not found or operation canceled.");
                
            }
        }
        userPrompt.close();
        
        return newFile;
        
        
    }
}
    // Class representing a Car object
    class Car implements Comparable<Car> 
    {
        private String make;
        private int year;
        private int price;
        
        // Constructor for Car object
        public Car(String make, int year, int price)
        {
            this.make = make;
            this.year = year;
            this.price = price;
        }
        // Method for getting car make
        public String getMake()
        {
            return make;
        }
        // Getter method for car year
        public int getYear()
        {
            return year;
        }
        // Getter method for car price
        public int getPrice()
        {
            return price;
        }
        // CompareTo method to compare two Car objects
        public int compareTo(Car other)
        {
            if(!this.make.equals(other.make))
            {
                return this.make.compareTo(other.make);
            }
            else
            {
                return Integer.compare(this.year, other.getYear());
            }
            
        }
        // Override toString method to represent Car object as string
        @Override
        public String toString()
        {
            return "Make: " + make + ", Year : " + year + ", Price: " + price + ";";
        }
        
    }
// Class representing an ordered list   
class aOrderedList
{
    final int SIZEINCREMENTS = 20; //size of increments for
                                   //increasing ordered list
    private Comparable[] oList; //the ordered list
    private int listSize; //the size of the ordered list
    private int numObjects; //the number of objects in
                            // the ordered list
    private int curr; //index of current element accessed via
                          //iterator methods    
    // Constructor for ordered list
    public aOrderedList()
    {
        this.numObjects = 0;
        this.listSize = SIZEINCREMENTS;
        oList = new Comparable[SIZEINCREMENTS];
    }
    // Method to add object to ordered list   
    public void add(Comparable newObject)
    {
          
        if(numObjects == listSize)
        {
            listSize += SIZEINCREMENTS;
            oList = Arrays.copyOf(oList, listSize);
        }
        int i = numObjects - 1;
        while(i >= 0 && oList[i] != null && oList[i].compareTo(newObject) > 0)
        {
            oList[i+1] = oList[i];
            i--;
        }
            
        oList[i+1] = newObject;
        numObjects++;
        }
    // Override toString method to represent ordered list as string   
    @Override
    public String toString()
    {
        String output = "[";
        for(int i = 0; i < numObjects; i++)
        {
            output+= oList[i].toString();
            if(i < numObjects - 1)
            {
                output+= ", ";
            }
        }
        output += "]";
        return output;
    }
    // Method to get size of ordered list
    public int size()
    {
        return numObjects;
    }
    // Method to get object at specified index
    public Comparable get(int index)
    {
       
        if(index < 0 || index >= numObjects)
        {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + numObjects);
        }
        return oList[index];
    }
    // Method to check if ordered list is empty
    public boolean isEmpty()
    {
        boolean arrayCheck = false;
        if(oList.length != 0)
        {
            arrayCheck = true;
          
        }
        return arrayCheck;
    }
    // Method to remove object at specified index
    public void remove(int index)
    {
        if(curr < 0 || curr >= numObjects)
        {
            throw new IndexOutOfBoundsException("Index: " + curr + ", Size: " + numObjects);
        }
        for(int i = curr; i < numObjects - 1; i++)
        {
            oList[i] = oList[i+1];
        }
            
            numObjects--;    
    }
    // Method to reset iterator
    public void reset()
    {
        curr = -1;
    }
     // Method to get next element using iterator
    public Comparable next()
    {
        if(hasNext())
        {
            curr++; // might go after line below
            Comparable next = oList[curr];
            
            return next;
        }
        else
        {
            throw new NoSuchElementException("No more elements are in the list");
        }
        
    }
    // Method to check if there is next element using iterator
    public boolean hasNext()
    {
        return curr< numObjects;
    }

    // Method to remove element using iterator
    public void remove()
    {
        if(curr > 0 && curr <= numObjects)
        {
            remove(curr-1);
        }
    }
        
}
    

