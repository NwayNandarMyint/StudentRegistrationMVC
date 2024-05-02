package StudentRegistrationMvc.Persistant;

public class IdGenerator {
	public static String generateStudentId(int lastId) {
		 lastId++;
		 String studentid = "AI-" + String.format("%03d", lastId);
		 return studentid;
	}

}
