package ses;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Jatin Taneja 557
 * @author Bhupender Khedar 706
 * @author Rajinder Singh Pathania 238
 * @author Kaushal Singh 583
 */
public class Main {
	
	// Global Program list to access all the programs in different functions
	
	static List<Program> globalProgramList = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		
		// Creating a list of program list to add every program details
		List<Program> listOfPrograms = new ArrayList<Program>();
		
		// Main Menu
		int optionChoosen = 0;
		
		boolean wantToContinue = true;
		
		// These two calls will create some basic instances of program for graduate as well undergraduate
		initilizePrograms("graduatePrograms",listOfPrograms);
		initilizePrograms("underGraduatePrograms",listOfPrograms);
		
		// Add some students in under different programs
		initilizeStudents("students");
		
		// Welcome String for our program
		
		System.out.println("Welcome to Student Enrollment System:");
		printSlash();
		
		// Menu for user to see different option
		while(wantToContinue) {
			System.out.println("Choose one of the options below to get started\n"
					+ "1.Add a program\n"
					+ "2.Enroll for program\n"
					+ "3.Checkout list of programs available\n"
					+ "4.Check students enrolled in a program\n"
					+ "5.For exit");
			
			Scanner sc = new Scanner(System.in);
			/*
			 * Incase user enter another type ask for integer option only
			 */
			try {
				optionChoosen = sc.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Please only use options given above:");
				sc.next();
				printSlash(); // A function which will print slashes
			}
			switch(optionChoosen) {
				case 1:
					addNewProgram(); // Call to create a new program
					break;
				case 2:
					enrollStudent(); // Call to enroll a new student
					break;
				case 3:
					listOfPrograms(); // Call for checking the list of programs available
					printSlash();
					break;
				case 4:
					checkStudentsInAProgram(); // Check the students available in any program
					break;
				case 5:
					System.out.println("Good Bye! Have a great day!");
					wantToContinue = false;
					break;
				default:
					continue;
			}
		}

	}
	/**
	 * This function will help in initializing some of the existing student available in csv file
	 * @param fileName
	 */
	private static void initilizeStudents(String fileName) {
		String line = "";  
		String splitBy = ",";
		try   
		{  
			// Add the Programs to global variable using fetch function
			globalProgramList = fetchProgramList();
			
			//parsing a CSV file into BufferedReader class constructor  
			BufferedReader br = new BufferedReader(new FileReader(fileName+".csv"));  
	
			while ((line = br.readLine()) != null)   //returns a Boolean value  
			{  
				String[] student = line.split(splitBy);    // use comma as separator
				
				// Create instance of post graduate & undergraduate students
				for(Program p:globalProgramList) {
					// Create an instance of postgraduate class if it is a graduate program
					if(p.isGraduateProgram()) {
						PostGraduateStudent ps = new PostGraduateStudent(student[0], student[1], Integer.parseInt(student[2]), student[3],"Computer");
						p.enrollStudent(ps);
					}
					// Create an instance of undergraduate if it's not graduate
					else {
						UnderGraduateStudent us = new UnderGraduateStudent(student[0], student[1], Integer.parseInt(student[2]), student[3]);
						p.enrollStudent(us);
					}
				}
		
			}
			
		}
		// Catch the exception
		catch (IOException e)   
		{  
			e.printStackTrace();  
		}  
	}
	/**
	 * A function which will initialize some of the programs to see the data
	 * @param fileName
	 */
	private static void initilizePrograms(String fileName,List<Program> listOfPrograms) {
		String line = "";  
		String splitBy = ",";
		boolean isGraduate = false;
		// If the file name contains under then add false in isGraduate else true
		if(!fileName.contains("under")) {
			isGraduate = true;
		}
		try   
		{  
			//parsing a CSV file into BufferedReader class constructor  
			BufferedReader br = new BufferedReader(new FileReader(fileName+".csv"));  
			
			// stream all the objects into file using Serializable
			
			//Creating stream and writing the object    
			FileOutputStream fileOut=new FileOutputStream("programs.txt");
						
			ObjectOutputStream out=new ObjectOutputStream(fileOut);
			
			while ((line = br.readLine()) != null)   //returns a Boolean value  
			{  
				String[] program = line.split(splitBy);    // use comma as separator
				
				//Create the instance of program either graduate or undergraduate
				Program p = new Program(program[0],program[1],isGraduate);
				
				
				// Add each program to the list of Programs
				listOfPrograms.add(p);
				
			}
			
			// Write object to file so it can be used after deserialization
			out.writeObject(listOfPrograms);
			
			// Flush the writer for the current data
			out.flush();
			// Close the output stream
			out.close();
			fileOut.close();
		} 
		// Catch the exception
		catch (IOException e)   
		{  
			e.printStackTrace();  
		}  
		
	}
	/**
	 * A method just in case we want to add some slashes to make console look good
	 */
	private static void printSlash() {
		System.out.println("------------------------------------------------");
		
	}
	/**
	 * @throws IOException 
	 * 
	 */
	private static void checkStudentsInAProgram() throws IOException {
		// Display the list of programs available 
		String programChoosen = null;
		listOfPrograms(); // it will show the list of programs
		Scanner sc = new Scanner(System.in);
		System.out.print("For which program you want to see students: ");	
		programChoosen = sc.nextLine();
		// Loop through the program available to fetch the program instance
		for(Program p:globalProgramList) {
			// Match if program matches any of the existing program
			if(p.getProgramTitle().equalsIgnoreCase(programChoosen)) {
				// Check if it's a graduate program so create the instance to access
				// Specific students of that program
				if(p.isGraduateProgram()) {
					// Create a list of students which will be returned from get PGStudents method in program class
					System.out.println(p.pgEnrolledStudents);
					List<PostGraduateStudent> listOfStudents =  p.getPGStudents();
					// If students are unavailable inform the user
					if(listOfStudents.size() == 0) {
						System.out.println("No student has been enrolled yet, choose enroll feature");
						printSlash();
						break;
					}
					else {
						// Print the students details in case it is available
						System.out.println("Students enrolled for "+ p.getProgramTitle()+" are:");
						printSlash();
						System.out.println("Student Id\tName\t\t\tAge");
						for(PostGraduateStudent s:listOfStudents) {
							// Child instance is accessing the parent class getters here
							System.out.println(s.getId()+"\t\t"+s.getFirstName()+" "+s.getLastName()+"\t\t"+s.getAge());
						}
					printSlash();
					break;
					}
				}
				else {
					// Create a list of students which will be returned from get UGStudents method in program class
					List<UnderGraduateStudent> listOfStudents =  p.getUGStudents();
					// If students are unavailable inform the user
					if(listOfStudents.size() == 0) {
						System.out.println("No student has been enrolled yet, choose enroll feature");
						printSlash();
						break;
					}
					else {
						// Print the undergraduate student details here
						System.out.println("Students enrolled for "+ p.getProgramTitle()+" are:\n"
								+ "Student Id\tName\t\t\tAge");
						for(UnderGraduateStudent s:listOfStudents) {
							// Child instance is accessing the parent class getters here
							System.out.println(s.getId()+"\t\t"+s.getFirstName()+" "+s.getLastName()+"\t\t"+s.getAge());
						}
					printSlash();
					break;
					}
					
				}
			}
		}
	}
	/**
	 * @throws IOException 
	 * A function which will help to enroll a student into existing programs
	 */
	private static void enrollStudent() throws IOException {
		
		// Display the list of programs available 
		String programChoosen = null;
		boolean programGiven = false; // Check if the program is provided by user
		boolean programFound = false; // CHeck if program is found
		
		//listOfPrograms();
		System.out.println("Which program do you want to enroll for?");
		Scanner sc = new Scanner(System.in);
		programChoosen = sc.nextLine();
		programGiven = true; // As program was given above
		
		// Create a loop until the program is found
		while(true) {
			if(programGiven) {
				for(Program p:globalProgramList) {
					// Match if program matches any of the existing program
					if(p.getProgramTitle().equalsIgnoreCase(programChoosen)) {
						askQuestionsForStudentEnrollment(p); // Ask questions related to adding a student
						programFound = true; // Once the program has been found make it true
						break;
					}
				}
				// Once the program has been found break out of the loop
				if(programFound) {
					break;
				}
				// If program isn't found ask for program name again until it is given
				else {
					programGiven = false;
				}
			}
			else {
				listOfPrograms();
				System.out.println("Please select from one these programs title only:");
				programChoosen = sc.nextLine();
				programGiven = true;
			}
		}
	}
	/**
	 * A program which will ask the questions related to enrolling a student like their first name
	 * last name, age and address
	 * @param p
	 */
	private static void askQuestionsForStudentEnrollment(Program p) {
		Scanner sc = new Scanner(System.in);
		printSlash();
		// Take user input for questions
		System.out.print("Please provide your first name: ");
		String firstName = sc.nextLine();
		
		System.out.print("Please provide your last name: ");
		String lastName = sc.nextLine();
		
		System.out.print("Please provide your age: ");
		int age;
		while(true) {
			try {
				age = sc.nextInt();
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Please provide age in number");
				sc.nextLine();
			}
		}
		sc.nextLine(); // An extra next line is used to make sure it doesn't skip the address question
		System.out.print("Please provide your address: ");
		String address = sc.nextLine();
		// If selected program was a graduate program then one additional question
		if(p.isGraduateProgram()) {
			System.out.print("You want to do major's in? ");
			String majorsIn = sc.nextLine();
			
			// Create postgraduate instance and call enroll student method of postgraduate class
			PostGraduateStudent ps =  new PostGraduateStudent(firstName, lastName, age, address, majorsIn);
			p.enrollStudent(ps);
			
			// Give user information
			System.out.println("Student "+ps.getFirstName() +" "+ps.getLastName()+" is enrolled");
			printSlash();
		}
		else {
			// Create undergraduate instance and enroll the student
			UnderGraduateStudent us = new UnderGraduateStudent(firstName, lastName, age, address);
			p.enrollStudent(us);
			
			// Print the student information
			System.out.println("Student "+us.getFirstName() +" "+us.getLastName()+" is enrolled");
			printSlash();
		}
		
	}
	/**
	 * @throws IOException 
	 * This function is used fetch the program which will deserialize the file and
	 * add all the objects into listOfPrograms also display in tabular format
	 */
	private static void listOfPrograms() throws IOException {
		List<Program> listOfPrograms= fetchProgramList();
		
		if(listOfPrograms.size() == 0) {
			System.out.println("Sorry, there aren't any programs. Please add some!");
		}
		else 
		{
			// Display the data in tabular format
			System.out.println("Okay! List of programs available are:\n"
					+ "Program Id\tProgramTitle\tIsGraduate");
			for(Program p: listOfPrograms) {
				System.out.println(p.getProgramId()+"\t\t"+p.getProgramTitle()+"\t\t"+p.isGraduateProgram());
			}
		}
	}
	
	/**
	 * A function which will deserialize the program.txt file to return the list of programs
	 * @return
	 */
	private static List<Program> fetchProgramList() {
		ArrayList<Program> listOfPrograms = new ArrayList<>();
		try 
		{
			FileInputStream fis = new FileInputStream("programs.txt");
			try (ObjectInputStream input = new ObjectInputStream(fis)) {
				listOfPrograms = (ArrayList<Program>) input.readObject();
			}
		}
		// Catch incase of input output exception
		catch (IOException ioe) 
        {
            ioe.printStackTrace();
        } 
		// Catch the class not found exception
        catch (ClassNotFoundException c) 
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
		// Returnt the list of programs
		return listOfPrograms;
		
	}
	/**
	 * A program which will create the instance of program and also would add program
	 * provided by user into the csv file so later if program ran again it would 
	 * create the instance of newly program added again
	 * @throws IOException 
	 */
	private static void addNewProgram() throws IOException {
		String programTitle = null;
		String graduateProgram;
		String programName;
		boolean isGraduateProgram = false;
		
		
		printSlash();
		Scanner sc = new Scanner(System.in);
		
		// Take user input for program details
		System.out.print("Please provide program title:");
		programTitle = sc.nextLine();
		
		System.out.print("Please provide program Name:");
		programName = sc.nextLine();
		
		System.out.print("Is a graduate program, yes or no");
		graduateProgram = sc.nextLine();
		
		// Set to true if user answers yes
		if(graduateProgram.equalsIgnoreCase("yes") || graduateProgram == "1" ) {
			isGraduateProgram = true;
		}
		
		// Create file writer for adding the new program in different file as per the condition
		// if the program is graduate or not
		FileWriter pw;
		if(isGraduateProgram) {
			// for writing it to graduate programs csv
			pw =  new FileWriter("graduatePrograms.csv",true);
		}
		else {
			// For writing it to undergradaute programs csv
			pw =  new FileWriter("underGraduatePrograms.csv",true);
		}
		
		// Append the title and program with , delimiter
		pw.append(programTitle);
		pw.append(",");
		pw.append(programName);
		
		// Add the instance to globalprogramslist so it can be used to add student
		globalProgramList.add(new Program(programTitle,programName,isGraduateProgram));
		
		// close the printwriter
		pw.close();
		
		System.out.println("Program Added");
		printSlash();
			
	}

}
