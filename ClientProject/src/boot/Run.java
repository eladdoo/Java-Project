package boot;

import presenter.Presenter;
import view.MyMazeWindow;
import model.MyClient;

public class Run 
{

	public static void main(String[] args) 
	{//aas
		MyClient c = new MyClient();
		MyMazeWindow win = new MyMazeWindow("my maze",500,500);
		Presenter p = new Presenter(c,win);
		c.addObserver(p);
		win.addObserver(p);
		win.start();
	}

}
