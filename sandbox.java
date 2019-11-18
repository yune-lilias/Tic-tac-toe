package justplaying;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class sandbox {
public static sandbox[][] sb = new sandbox[10][10];          //to store some value for each box in the chess board,like value
public static sandbox[] secondlayer = new sandbox[8];       //ignore, no use
public static sandbox[][] thirdlayer = new sandbox[9][8];   //ignore, no use
public static sandbox sb1 = new sandbox();                   //to store some value for the whole chess board,like win, step 
public String value;               //what's in the box
public Boolean pickedornot;        //sb.this = has this box been occupied, sb1.this = has player/computer successfully choose their box
public Boolean canihere;           //use in tiptopai.stimulatechess: tell the computer whether he can take the box
public int step;                 
public char pickedbywhom;          //who pick this box?
public Boolean win;                //anyone win?
public String cc1;                 //computer choose which string to be its chess?
public String pc1;                 //player choose which string to be its chess?
public int value1;                 //use in tiptopai, is equal to "if computer place the chess here", it will benefit computer how much
public int numb;                   //who r u bro? Have I used u?
public int sized;                  //the size of box, if sized = 3 then start the ai
public String whodofirst;          // who first?

private static void setupsb(sandbox[][] sb,int n)       //no use
{
	int row,col;
	for(row=0;row<=n;row++)
	{
		for (col=0;col<=n;col++)
		{   
			sb[row][col].pickedornot = false;
		}
	}
}

public static Boolean win(sandbox[][] sb,sandbox sb1,int size,String cc,String pc)  //anyone win?
{
	Boolean winornot = false;
	//sb1.pc1 = pc;
	//sb1.cc1 = cc;
	wininrow(sb,sb1,size);   //:when three x or three o placed like -
	winincol(sb,sb1,size);   //:when three x or three o placed like |
	wininslash(sb,sb1,size); //:when three x or three o placed like /
	wininbacksl(sb,sb1,size);//:when three x or three o placed like \
	if(sb1.win)
	{winornot = true;}
	return winornot;
}

public static void whowin(String k,sandbox sb1)
{
	if(k.equals(sb1.cc1))
	{
		sb1.pickedbywhom = 'C';
	}
	if(k.equals(sb1.pc1))
	{
		sb1.pickedbywhom = 'P';
	}
}

private static void wininrow(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(row=0;row<=size;row++)
	{
		for(col=0;col<=size-2;col++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row][col+1].value)&&sb[row][col].value.equals(sb[row][col+2].value))
				{
					sb1.win = true;
					whowin(sb[row][col].value,sb1);
				}
		}
	}
	
}

private static void winincol(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(col=0;col<=size;col++)
	{
		for(row=0;row<=size-2;row++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row+1][col].value)&&sb[row][col].value.equals(sb[row+2][col].value))
				{
					sb1.win = true;
					whowin(sb[row][col].value,sb1);
					}
		}
	}
	
}

private static void wininbacksl(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(col=0;col<=size-2;col++)
	{
		for(row=0;row<=size-2;row++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row+1][col+1].value)&&sb[row][col].value.equals(sb[row+2][col+2].value))
				{
					sb1.win = true;
					whowin(sb[row][col].value,sb1);
					}
		}
	}
}

private static void wininslash(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(row=2;row<=size;row++)
	{
		for(col=0;col<=size-2;col++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row-1][col+1].value)&&sb[row][col].value.equals(sb[row-2][col+2].value))
				{
					sb1.win = true;
					whowin(sb[row][col].value,sb1);
					}
		}
	}
}

private static void playerpick(sandbox[][] sb,sandbox sb1,int size, String xx)   //dear player,plz place your chess
{
	System.out.println("It's your turn, choose a number from 1 to n^2");
	sb1.pickedornot = false;
	Scanner sc= new Scanner(System.in);
	int n,i,j;
	n= sc.nextInt();
	size = size+1;
	n=n-1;
	i=n/size;
	j=n-i*size;
	if(!sb[i][j].pickedornot)
	{
	//sb[i][j].value = xx; 
	sb[i][j].value = xx; 
	sb[i][j].pickedornot = true;   //whether this box is picked
	sb1.pickedornot = true;        //whether the pick step complete
	}
}

private static void computerpick(sandbox[][] sb,sandbox sb1,int n,String cc)   //it's computer's term!
{
	sb1.pickedornot = false;
	int[] num = new int[2];
	if(sb1.sized == 3)          //for the chess board with the size 3*3,start ai
	{
	num = tiptopai.topai(sb,sb1);                                   
	sb[num[0]][num[1]].value = cc; 
	sb[num[0]][num[1]].pickedornot = true;
	System.out.println("My next step...well, I think the "+num[0]+" "+num[1]+" is the best choice\n");
	//System.out.println(num[1]);
	sb1.pickedornot = true;        //whether the pick step complete
	}
	else                         //for other chess board, randomly place the chess
	{
		num[0]=(int)(Math.random()*n);	
		num[1]=(int)(Math.random()*n);	
		if(!sb[num[0]][num[1]].pickedornot)
		{  
	/*	num = tiptopai.topai(sb,sb1);      */                             
		sb[num[0]][num[1]].value = cc; 
		sb[num[0]][num[1]].pickedornot = true; 
		//System.out.println(num[0]);
		//System.out.println(num[1]);
		sb1.pickedornot = true;      
	}
	}
}

public static void printgame(sandbox[][] sb,int n)       //print the chess board
{
	int row,col;
	for(row=0;row<=n;row++)
	{
		for (col=0;col<=n;col++)
		{
			System.out.print(sb[row][col].value);
			System.out.print("  ");
		}
		System.out.print("\n\n");
	}
	System.out.print("\n");
}

public static void main(String arg[])
{
	Scanner sc= new Scanner(System.in);
	Boolean gameend = false;
while(!gameend)                   //whether the game end
{
	int n,nsize;
	Boolean s = true;
	Boolean continueornot = true;      //continue:after one of the player win, he can still continue until all the boxs are occupied
	System.out.println("Please enter the size of the box(while enter n ,then the box would be n*n )");
	System.out.println("P.S:the number of box would be in row major order");
	System.out.println("e.g:  1  2  3");
	System.out.println("      4  5  6");
	System.out.println("      7  8  9");
	n = sc.nextInt()-1;               //for the array of size n is from 0 to n-1
	nsize = (n+1)*(n+1);              
	int row,col;
	for(row=0;row<=n;row++)
	{
		for (col=0;col<=n;col++)
		{   
			sb[row][col] = new sandbox();        //Idk why this is need, but without this it will tell me :java.lang.NullPointException
			sb[row][col].value = "-";            //default value
			sb[row][col].pickedornot = false;    //no one pick this
		}
	}
	sandbox sb1 = new sandbox();                 //Idk why this is need, but without this it will tell me :java.lang.NullPointException 
	sb1.sized = n+1;                             //this size is use in tiptopai.java
	sb1.step = 0;                                
	sb1.win = false;
	String pc,cc;
	System.out.println("Please choose your icon (o) or (x)");
	System.out.println("If you choose icons (o),we would give conputer the icon (x), otherwise we would give it (o)\n");
	//System.out.println("For computer is not as smart as u, please let it place its chess first");
	Object[] opt = {"o","x","*","+"};               //optional icons
	Object[] whofirst = {"Computer","Me"};          //who first?
	pc = (String)JOptionPane.showInputDialog(null,"Please Choose one of the Followings:  ","Sancbox",JOptionPane.INFORMATION_MESSAGE,null,opt,opt[1]); 
	sb1.whodofirst = (String)JOptionPane.showInputDialog(null,"Please Choose one of the Followings:  ","Sancbox",JOptionPane.INFORMATION_MESSAGE,null,whofirst,whofirst[0]); 
	//setupsb(sb, n);
	if(pc == null)                    //if the OptionPane is canceled without choose any icon
		{pc = "x";}
	if (pc.equals("o"))
	{
		cc = "x";
	}
	else 
	{
		cc="o";
	}
	sb1.cc1 = cc;                     //icon of computer
	sb1.pc1 = pc;                    //icon of player
	while(continueornot)             
	{
		int u = 0;                   
	continueornot = false;
	Boolean win1;
	win1=false;
	while(sb1.step <= nsize)          //step<size means there're still some empty box
	{
		switch(sb1.whodofirst)          
		{
		case "Computer":                 //computer first
		sb1.pickedornot = true;
		sb1.step ++;
		if(!win1||continueornot&&sb1.step<=nsize) {     //if all boxes are chosen and, someone win or player choose continue
		while(s)               //break until a new box has been chosen
		{
			computerpick(sb,sb1,n,cc);
			//playerpick(sb,sb1,n,pc);
			if(sb1.pickedornot) {break;}
			if(sb1.step >= nsize) {s = false;}
			//System.out.println("Hey,this box has been selected, please choose another one\n");
		}
		printgame(sb,n);
		}
		sb1.pickedornot = true;
		sb1.step ++;
		win1 = win(sb,sb1,n,pc,cc);
		//if(!win1||continueornot&&sb1.step<=nsize) {    //this will make you lose one step if computer win and u choose continue
	    if(sb1.step<=nsize) {                            //this will make the "u lose " shows after you place your chess
		while(s)
		{
			playerpick(sb,sb1,n,pc);
			//computerpick(sb,sb1,n,cc);
			if(sb1.pickedornot) {break;}
			//if(sb1.step >= nsize) {s = false;}
			System.out.println("Hey,this box has been selected, please choose another one\n");
		}
		printgame(sb,n);
		}
		break;
		
		
		case "Me":
			sb1.pickedornot = true;
			sb1.step ++;
			win1 = win(sb,sb1,n,pc,cc);
			if(!win1||continueornot&&sb1.step<=nsize) {
			while(s)
			{
				playerpick(sb,sb1,n,pc);
				//computerpick(sb,sb1,n,cc);
				if(sb1.pickedornot) {break;}
				//if(sb1.step >= nsize) {s = false;}
				System.out.println("Hey,this box has been selected, please choose another one\n");
			}
			printgame(sb,n);
			}
			sb1.pickedornot = true;
			sb1.step ++;
				//if(!win1||continueornot&&sb1.step<=nsize) {    //this will make computer lose one step if computer win and u choose continue
			    if(sb1.step<=nsize) {                            //this will make the "u win " shows after computer place your chess
			while(s)
			{
				computerpick(sb,sb1,n,cc);
				//playerpick(sb,sb1,n,pc);
				if(sb1.pickedornot) {break;}
				if(sb1.step >= nsize) {s = false;}
				//System.out.println("Hey,this box has been selected, please choose another one\n");
			}
			printgame(sb,n);
			}	
			break;
		}
		
	
		win1 = win(sb,sb1,n,pc,cc);          
		if(win1&&!continueornot)
		{
			String option = "Quit the game";
			Object[] winopt = {"Quit the game","Continue","Start over"}; 
			if(sb1.pickedbywhom == 'C')
			{
             option = (String)JOptionPane.showInputDialog(null,"You lose! ","notify",JOptionPane.INFORMATION_MESSAGE,null,winopt,winopt[0]); 
			}
			else if(sb1.pickedbywhom == 'P')
			{
			 option = (String)JOptionPane.showInputDialog(null,"You win! ","notify",JOptionPane.INFORMATION_MESSAGE,null,winopt,winopt[0]); 
			}
			else 
			{
				option = (String)JOptionPane.showInputDialog(null,"Draw! ","notify",JOptionPane.INFORMATION_MESSAGE,null,winopt,winopt[0]); 
			}
            if(option == null)
    			{option = "Quit the game";}         	
            switch(option)
    			{
                case "Quit the game":
        			sb1.step = sb1.step +100;
        			gameend = true;           //end the game
        			System.out.println("Thank you for playing");
                break;
                case "Continue":
                	continueornot = true;     //continue this game
                break;
                case "Start over":
                	sb1.step = sb1.step +100;     //gameend = false, do not break the big loop
                break;
    			}
		}
	}
		if(sb1.step >= nsize&&!gameend)      //if all boxes are chosen, end the game
		{
			int endnotify;
		endnotify = JOptionPane.showConfirmDialog(null, "the game is going to end now, start a new game?","new game",JOptionPane.YES_NO_OPTION);
		if(endnotify == 0)
		{}
		else if (endnotify == 1)
		{
			gameend = true;
		}
		}
	}
	}
System.out.println("end");
}
	
}


