package ses;
/**
 * 
 * @author Jatin Taneja 557
 * @author Bhupender Khedar 706
 * @author Rajinder Singh Pathania 238
 * @author Kaushal Singh 583
 */
public class Student {
	/*
	 * A student class which is a base class and used for creating postgraduate and undergraduate
	 * instance
	 */
	private String firstName;
	private String lastName;
	private int age;
	private String address;
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param address
	 */
	public Student(String firstName, String lastName, int age, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", address=" + address
				+ "]";
	}
	
	
}
