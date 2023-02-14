package ses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jatin Taneja 557
 * @author Bhupender Khedar 706
 * @author Rajinder Singh Pathania 238
 * @author Kaushal Singh 583
 */
public class Program implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * A Program class which would have auto generated id, name of program passed and initialized in constructor
	 * and it will have the boolean flag if program is graduate program or not, also it would have the number of students
	 * which can be enrolled into it.
	 */
	
	
	// Auto Generated Id would be used to generate new id for each program Instance created
	private static int autoGeneratedId = 0;
	private int programId;
	private String programTitle;
	private String programName;
	private boolean isGraduateProgram;
	public List<PostGraduateStudent> pgEnrolledStudents = new ArrayList<>();
	public List<UnderGraduateStudent> ugEnrolledStudents = new ArrayList<>();
	
	/**
	 * Parameterized constructor to initialize a program
	 * @param programTitle
	 * @param programName
	 * @param isGraduateProgram
	 */
	public Program(String programTitle,String programName, boolean isGraduateProgram) {
		autoGeneratedId += 1;
		this.programId = autoGeneratedId;
		this.programTitle = programTitle;
		this.programName = programName;
		this.isGraduateProgram = isGraduateProgram;
	}
	
	/**
	 * Overridden method to add post graduate student
	 * @param postGraduateStudent
	 */
	public void enrollStudent(PostGraduateStudent postGraduateStudent) {
		pgEnrolledStudents.add(postGraduateStudent);
	}

	/**
	 * Overridden method to add under graduate student
	 * @param underGraduateStudent
	 */
	public void enrollStudent(UnderGraduateStudent underGraduateStudent) {
		//System.out.println("Before:"+ugEnrolledStudents);
		ugEnrolledStudents.add(underGraduateStudent);
		//System.out.println("After:"+ugEnrolledStudents);
	}
	
	/**
	 * 
	 * @return list of enrolled students
	 */
	public List<PostGraduateStudent> getPGStudents(){
		System.out.println("Here it is"+pgEnrolledStudents);
		return pgEnrolledStudents;
	}

	/**
	 * 
	 * @return list of enrolled students
	 */
	public List<UnderGraduateStudent> getUGStudents(){
		return ugEnrolledStudents;
	}
	/**
	 * @return the programTitle
	 */
	public String getProgramTitle() {
		return programTitle;
	}


	/**
	 * @param programTitle the programTitle to set
	 */
	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}


	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}


	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}


	/**
	 * @return the programId
	 */
	public int getProgramId() {
		return programId;
	}


	/**
	 * @return the isGraduateProgram
	 */
	public boolean isGraduateProgram() {
		return isGraduateProgram;
	}
	
	@Override
	public String toString() {
		return programTitle;
	}
	
}