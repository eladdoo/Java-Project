package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import Messages.MessageType;
import algorithms.demo.MazeDomain;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Action;
import algorithms.search.ActuallState;
import algorithms.search.Solution;
import compression_algorithms.Bits;
import compression_algorithms.HuffmanAlg;
import config.HandelProperties;
import config.Properties;

/**
 * The Class MyClient.
 */
public class MyClient extends Observable implements Model
{
	
	/** The port. */
	private int port;
	
	/** The ip. */
	private String ip;
	
	/** The my server. */
	private Socket myServer;
	
	/** The in from server. */
	private ObjectInputStream inFromServer;
	
	/** The out to server. */
	private ObjectOutputStream outToServer;
	
	/** The solution. */
	private SolveProblem solv;
	
	/** The Command. */
	private String Command = "Default";
	
	/** The name. */
	private String name;
	
	/** The maze. */
	private Maze maze;
	
	/** The User name. */
	private String UserName;
	
	/**
	 * Instantiates a new my client.
	 */
	public MyClient()
	{
		HandelProperties h = new HandelProperties();
		Properties p = h.ReadProperties();
		this.port = p.getPort();
		this.ip = p.getIp();
	}
	
	/**
	 * connect to the server method
	 * 
	 * @param user name
	 */
	@Override
	public void ConnectClient(String userName)
	{
		try 
		{
			this.UserName = userName;
			myServer = new Socket(ip,port);
			inFromServer = new ObjectInputStream(myServer.getInputStream());
			outToServer = new ObjectOutputStream(myServer.getOutputStream());
			this.outToServer.writeUTF(userName);
			this.outToServer.flush();
			String ans = this.inFromServer.readUTF(); //getting ack on sent user name
			if (ans.equals("Error")) //if couldent connect
			{
				this.setChanged();
				this.notifyObservers(MessageType.ConErr);
			}
			else //if connection succeed
			{
				this.setChanged();
				this.notifyObservers(MessageType.Success);
			}
		} 
		catch (UnknownHostException e) //connection problems
		{
			this.setChanged();
			this.notifyObservers(MessageType.CONNECT);
		} 
		catch (IOException e) 
		{
			this.setChanged();
			this.notifyObservers(MessageType.CONNECT);
		}
	}
	
	/**
	 * method for asking from the server to generate maze
	 * 
	 * @param name of the maze the user chose
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void AskGenerateM(String name)
	{
		Problem p = new Problem();
		p.setCommand("generateMaze");
		p.setName(name);
		p.setUser(UserName);
		p.setFname(null);
		p.setM(null);
		ByteArrayInputStream b = this.coadHuff(p);
		try 
		{
			ObjectInputStream getting = new ObjectInputStream(b);
			Bits bysend;
			try 
			{
				HashMap<Bits, Character> dictSend = (HashMap<Bits, Character>) getting.readObject(); 
				bysend = (Bits) getting.readObject();
				this.outToServer.writeObject(dictSend); 
				this.outToServer.flush();
				this.outToServer.writeObject(bysend);
				this.outToServer.flush();
				HashMap<Bits, Character> dict = (HashMap<Bits, Character>) this.inFromServer.readObject(); //reading server answer
				Bits bf = (Bits) this.inFromServer.readObject();
				this.solv = this.decoadHuff(dict,bf);
				this.setChanged();
				this.notifyObservers(MessageType.GenreatedMaze);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}

		} 
		catch (IOException e1) 
		{
			setChanged();
			notifyObservers(MessageType.servDisc); //if the user disconnected from server without without knowing
		}
	}
	
	/**
	 * asking the server to solve maze
	 * 
	 * @param maze to solve and his name
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void AskForSol(Maze m,String name)
	{
		Problem p = new Problem();
		p.setCommand("solveMaze");
		p.setName(name);
		p.setUser(UserName);
		p.setFname(null);
		p.setM(m);
		ByteArrayInputStream b = this.coadHuff(p);
		try 
		{
			ObjectInputStream getting = new ObjectInputStream(b);
			Bits bysend;
			try 
			{
				HashMap<Bits, Character> dictSend = (HashMap<Bits, Character>) getting.readObject(); 
				bysend = (Bits) getting.readObject();
				this.outToServer.writeObject(dictSend); //sending the problem to the server
				this.outToServer.flush();
				this.outToServer.writeObject(bysend);
				this.outToServer.flush();
				HashMap<Bits, Character> dict = (HashMap<Bits, Character>) this.inFromServer.readObject();
				Bits bf = (Bits) this.inFromServer.readObject();
				this.solv = this.decoadHuff(dict,bf); //decoad the solution object
				this.setChanged();
				this.notifyObservers(MessageType.MazeSolved);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}

		} 
		catch (IOException e1) 
		{
			setChanged();
			notifyObservers(MessageType.servDiscGA); 
		}
	}
	
	/**
	 * asking the server to display maze
	 * 
	 * @param maze name
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void AskToDisplay(String name)
	{
		Problem p = new Problem();
		p.setCommand("displayMaze");
		p.setName(name);
		p.setUser(UserName);
		p.setFname(null);
		p.setM(null);
		ByteArrayInputStream b = this.coadHuff(p);
		try 
		{
			ObjectInputStream getting = new ObjectInputStream(b);
			Bits bysend;
			try 
			{
				HashMap<Bits, Character> dictSend = (HashMap<Bits, Character>) getting.readObject(); 
				bysend = (Bits) getting.readObject();
				this.outToServer.writeObject(dictSend);
				this.outToServer.flush();
				this.outToServer.writeObject(bysend);
				this.outToServer.flush();
				HashMap<Bits, Character> dict = (HashMap<Bits, Character>) this.inFromServer.readObject();
				Bits bf = (Bits) this.inFromServer.readObject();
				this.solv = this.decoadHuff(dict,bf);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}

		} 
		catch (IOException e1) 
		{
			setChanged();
			notifyObservers(MessageType.servDisc);
		}
	}
	
	/**
	 * ask from the server to load properties
	 * 
	 * @param path to the file
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void AskToLoad(String path)
	{
		Problem p = new Problem();
		p.setCommand("file");
		p.setName(null);
		p.setFname(path);
		p.setUser(UserName);
		p.setM(null);
		ByteArrayInputStream b = this.coadHuff(p);
		try 
		{
			ObjectInputStream getting = new ObjectInputStream(b);
			Bits bysend;
			try 
			{
				HashMap<Bits, Character> dictSend = (HashMap<Bits, Character>) getting.readObject(); 
				bysend = (Bits) getting.readObject();
				this.outToServer.writeObject(dictSend);
				this.outToServer.flush();
				this.outToServer.writeObject(bysend);
				this.outToServer.flush();
				HashMap<Bits, Character> dict = (HashMap<Bits, Character>) this.inFromServer.readObject();
				Bits bf = (Bits) this.inFromServer.readObject();
				this.solv = this.decoadHuff(dict,bf);
				this.setChanged();
				this.notifyObservers(MessageType.FOpened);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}

		} 
		catch (IOException e1) 
		{
			setChanged();
			notifyObservers(MessageType.servDisc);
		}
	}
	
	/**
	 * Decoad huff.
	 *
	 * @param dict the dict
	 * @param bf the bf
	 * @return the solve problem
	 */
	public SolveProblem decoadHuff (HashMap<Bits, Character> dict,Bits bf)
	{
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		HuffmanAlg hff = new HuffmanAlg();
		try 
		{
			ObjectOutputStream out = new ObjectOutputStream(bs);
			out.writeObject(dict); //writing the dictionary to bytearray to send to the decompress algorithem
			out.writeObject(bf); //writing the code to bytearray to send to the decompress algorithem
			out.close();
			byte[] bi = hff.decompress(bs.toByteArray());
			ByteArrayInputStream fg = new ByteArrayInputStream(bi);
			ObjectInputStream hg = new ObjectInputStream(fg);
			String text = hg.readUTF();
			hg.close();
			String []res = text.split("!");
			SolveProblem sv = new SolveProblem();
			sv.setMessage(res[0]);
			if (res.length==1)
			{
				return sv;
			}
			else if (res[1].equals("M")) //its maze to decoad
			{
				MazeDomain md = new MazeDomain();
				if (res[2].equals("Y"))
				{
					md.setDiagonal(true);
				}
				else
				{
					md.setDiagonal(false);
				}
				Maze temp = new Maze();
				String []ForM = res[3].split(" "); //split in the maze itself to know its variables
				temp.setRows(Integer.parseInt(ForM[0])); 
				temp.setColls(Integer.parseInt(ForM[1]));
				temp.setStart(new ActuallState(Integer.parseInt(ForM[2]),Integer.parseInt(ForM[3])));
				temp.setGoal(new ActuallState(Integer.parseInt(ForM[4]),Integer.parseInt(ForM[5])));
				temp.setMaze(new Cell[temp.getRows()][temp.getColls()]);
				for (int x=0;x<temp.getRows();x++) //initilize matrix
				{
					for (int y=0;y<temp.getColls();y++)
					{
						temp.getMaze()[x][y] = new Cell(x,y);
					}
				}
				int r=0,c=0;
				for (int j=6;j<ForM.length-1;j+=2) //setting the walls
				{
					if (ForM[j].equals("true"))
					{
						temp.getMaze()[r][c].setHasDownWall(true);
					}
					else
					{
						temp.getMaze()[r][c].setHasDownWall(false);
					}
					if (ForM[j+1].equals("true"))
					{
						temp.getMaze()[r][c].setHasRightWall(true);
					}
					else
					{
						temp.getMaze()[r][c].setHasRightWall(false);
					}
					c++;
					if (c==temp.getColls())
					{
						r++;
						c=0;
					}
				}
				md.setM(temp);
				sv.setM(md);
				}
				else //its solution to decoad
				{
					String []ForS = res[2].split("/");
					Solution temp2 = new Solution();
					List<Action> moves = new ArrayList<Action>();
					for (int j=0;j<ForS.length-1;j++) //creating the actions
					{
						String []ForA = ForS[j].split("_");
						double P = Double.parseDouble(ForA[0]);
						String TheMove = ForA[1];
						Action a = new Action(TheMove);
						a.setPrice(P);
						moves.add(a);
					}
					temp2.setMoves(moves);
					sv.setSol(temp2);
				}
				return sv;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Coad huff.
	 *
	 * @param p the p
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream coadHuff(Problem p)
	{
		HuffmanAlg hff = new HuffmanAlg();
		String res = p.toString();
		try 
		{
			byte[] b = hff.compress(res.getBytes());
			ByteArrayInputStream by = new ByteArrayInputStream(b);
			return by;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get the solution object
	 */
	@Override
	public SolveProblem getSolv() 
	{
		return solv;
	}
	
	/**
	 * get the command
	 */
	@Override
	public String getCommand() 
	{
		return Command;
	}
	
	/**
	 * set the command
	 */
	@Override
	public void setCommand(String command) 
	{
		Command = command;
	}
	
	/**
	 * get the name
	 */
	@Override
	public String getName() 
	{
		return name;
	}
	
	/**
	 * setting the name
	 */
	@Override
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * get the maze
	 */
	@Override
	public Maze getMaze() 
	{
		return maze;
	}
	
	/**
	 * set the maze
	 */
	@Override
	public void setMaze(Maze maze)
	{
		this.maze = maze;
	}
	
	/**
	 * stop model method
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void StopModel()
	{
		if (this.myServer!=null) //if the client made a connection to server
		{
			try 
			{
				Problem p = new Problem();
				p.setCommand("exit");
				ByteArrayInputStream b = this.coadHuff(p);
				ObjectInputStream getting = new ObjectInputStream(b);
				Bits bysend;
				try 
				{
					/**
					 * letting the server know the client is disconnecting
					 */
					HashMap<Bits, Character> dictSend = (HashMap<Bits, Character>) getting.readObject(); 
					bysend = (Bits) getting.readObject();
					this.outToServer.writeObject(dictSend);
					this.outToServer.flush();
					this.outToServer.writeObject(bysend);
					this.outToServer.flush();
				} 
				catch (ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
				this.outToServer.close();
				this.inFromServer.close();
				this.myServer.close();
			
			} 
			catch (IOException e) 
			{
			}
		}
	}
	
	/**
	 * load client properties
	 * 
	 * @param path to the file
	 */
	@Override
	public boolean loadCliProp(String path)
	{
		HandelProperties h = new HandelProperties(path);
		Properties p = h.ReadProperties();
		if (p!=null)
		{
			this.ip = p.getIp();
			this.port = p.getPort();
			return true;
		}
		return false;
	}
	
	/**
	 * get connection to server
	 */
	@Override
	public boolean getConnection()
	{
		if (this.myServer!=null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * set the user name
	 */
	@Override
	public void SetUName(String name)
	{
		this.UserName = name;
	}
	
	/**
	 * stop model if the attempt to connect the server failed
	 */
	@Override
	public void StopModelUN()
	{
		try 
		{
			this.outToServer.close();
			this.inFromServer.close();
			this.myServer.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
