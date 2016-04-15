package myPackage;

public class MainMethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model(4,4);
		Control control = new Control(model);
		View view = new View(model,control);
		model.addObserver(view);
		
		(new Thread(model)).start();
	}

}
