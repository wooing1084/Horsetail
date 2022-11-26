package netTeamProject;

public class Operator {
	Database db = null;
	LoginFrame lf = null;
	public String ID;
	
	public static void main(String[] args) {
		Operator opt = new Operator();
		opt.db = new Database();
		opt.lf = new LoginFrame(opt);
		
	}
}