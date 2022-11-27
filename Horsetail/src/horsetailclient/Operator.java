package horsetailclient;

public class Operator {
	Database db = null;
	LoginFrame lf = null;
	public String ID; //서버의 데이터베이스에서 얻어온 ID를 먼저 여기에 저장한다. 
	
	public static void main(String[] args) {
		Operator opt = new Operator();
		opt.db = new Database();
		opt.lf = new LoginFrame(opt);
		
	}
}