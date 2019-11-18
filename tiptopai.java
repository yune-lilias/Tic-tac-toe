package justplaying;

public class tiptopai {
public static sandbox[][] sb = new sandbox[10][10];           
public static sandbox sb1 = new sandbox();                    
public static sandbox[][] st1= new sandbox[3][3];
public static sandbox[][][][] st2= new sandbox[3][3][3][3];
private String value;
private Boolean pickedornot;

public static int[] topai(sandbox[][] sb,sandbox sb1)
{ 
	int row,col,inrow,incol;
	/*all of the possible chance for computer, 
	 *e.g: the first row is empty([0][0],[0][1],[0][2])
      *computer place its chess at [0][0] first, 
      *stored the new chess board in [0][0][0][0] to [0][0][2][2], 
      *then it place its chess at [0][1],store as[0][1][][]
      *and so on
      *if the computer place its chess at[i][j]
      *it will save in [i][j][][]
      *p.s:if the chessbox [i][j]is already taken, 
      *computer will still store this new chess board(by replace the value)
      *but the value canihere of [i][j][][] will be false
	*/
	for(row=0;row<=2;row++)
		for (col=0;col<=2;col++)
			for(inrow=0;inrow<=2;inrow++)
			for (incol=0;incol<=2;incol++)   
		{
			st2[row][col][inrow][incol] = new sandbox();       //Idk why this is need, but without this it will tell me :java.lang.NullPointException
			st2[row][col][inrow][incol].value = "-";           //default value
			st2[row][col][inrow][incol].pickedornot = false;   
		}
	int[] ret = new int[2];
	int[] k = new int[9];
	int a1,a2,a3,a4,a5,a6,knum,kmax;
	a5 = 0;
	a6 = 0;
	for(a1=0;a1<=2;a1++)
		for(a2=0;a2<=2;a2++)
			for(a3=0;a3<3;a3++)
				for(a4=0;a4<3;a4++)
			   {
					st2[a3][a4][a1][a2].value = sb[a1][a2].value;
					st2[a3][a4][a1][a2].pickedornot = sb[a1][a2].pickedornot;
					st2[a3][a4][0][0].cc1 = sb1.cc1;
					st2[a3][a4][0][0].pc1 = sb1.pc1;
					st2[a3][a4][a1][a2].canihere = true;
				}
	stimulatechess(st2, sb1);
	//let computer place its chess at each the boxes
	for(a3=0;a3<3;a3++)
	for(a4=0;a4<3;a4++)
	   {
	k[a3+3*a4] = transform(st2[a3][a4],sb1);
	//get the value of "when computer place its chess here"
	
	//System.out.println(k[a3+3*a4]);
	//sandbox.printgame(st2[a3][a4],2);
	//System.out.println("\n");
	   }
	kmax = k[0];
	for(a3=0;a3<3;a3++)
	for(a4=0;a4<3;a4++)   
	   {
	if(a3+3*a4>=1)
	if(k[a3+3*a4]>=	kmax&&st2[a3][a4][0][0].canihere)
	{
		kmax = k[a3+3*a4];
		a5 = (a3+3*a4)%3;
		a6 = (a3+3*a4)/3;
		//System.out.println(a3);
		//System.out.println(a4);
	}
	//st1[a4][][] is the best step
}
/*	for(a1=0;a1<=2;a1++)
		for(a2=0;a2<=2;a2++)
			for(a3=0;a3<=2;a3++)
				for(a4=0;a4<=2;a4++)
				{
					st2[a3][a4][a1][a2].value = sb[a1][a2].value;
				}*/
	ret[0]=a5;
	ret[1]=a6;
	return ret;
}

//sandbox[][] sb,sandbox[][][] st1,sandbox[][][][] st2

public static int transform(sandbox[][] str,sandbox sb1)
{
	int i,j;
	int result;
	String[][] t = new String[8][3];
	int[] k = new int[8];
	for(i=0;i<3;i++)
		for(j=0;j<8;j++)
		{
			t[j][i]="-";
		}
	for(i=0;i<3;i++)
	{
		t[0][i]= str[0][i].value;      //first row
		t[1][i]= str[1][i].value;      //second row
		t[2][i]= str[2][i].value;      //third row
	    t[3][i]= str[i][0].value;	   //first column
	    t[4][i]= str[i][1].value;      //second column
	    t[5][i]= str[i][2].value;      //third column
	    t[6][i]= str[i][i].value;      //diagonal  "\"
	    t[7][i]= str[i][2-i].value;    //diagonal  "/"
	}
	for(j=0;j<8;j++)
	{
	k[j]=getvalue(t[j],str[0][0].cc1,str[0][0].pc1,sb1);  //get the value of <strings> in each row,column and diagonal
	}
	result = judgevalue(k);      //get the value of <priority> of this chess board
	return result;
}

public static void stimulatechess(sandbox[][][][] str, sandbox sb1)
{
int i,j;
for(i=0;i<3;i++)
	for(j=0;j<3;j++)
	{
		if(str[i][j][i][j].pickedornot == true)
		{str[i][j][0][0].canihere = false;}     //this box is taken, you cannot place chess here!
		else
		{
			str[i][j][i][j].pickedornot = true;
			str[i][j][i][j].value = str[i][j][0][0].cc1;
		}		
	}
for(i=0;i<3;i++)
	for(j=0;j<3;j++)
	{
if(str[i][j][0][0].canihere)
{

}
	}
//System.out.println(str[0][0][0][0].cc1);
}

public static int getvalue(String[] inbox,String cc,String pc,sandbox sb1)
{
	int n,p;
	p=0;
	for(n=0;n<3;n++)
	{
	if(inbox[n].equals(sb1.cc1))
	{
		p = p+72;                  //each computer's chess will worth 72 point
	}else if(inbox[n].equals(sb1.pc1))
	{
		p = p+71;                  //each player's chess will worth 71 point
	}else if(inbox[n].equals("-"))
	{
		p = p-7;                   //each player's chess will worth -7 point
	}
	}
	return p;
}
//72 71 and -7 can be many other numbers, unless each two equations
//(listed in the next function)
//have the different value

//let a=72(computer),b=71(player),c=-7(empty)
public static int judgevalue(int[] arrya)
{
	int val,n;
	val = 0;
	for(n=0;n<8;n++)
	{
	switch(arrya[n])
	{
	case 216:       //3a:computer win:the most important
		val = val + 10000;
	break;
	case 214:      //2b+a:two player's chess but computer pick the rest:stop the player from winning
		val = val + 1000;
	break;
	case 137:      //2a+c:two computer's chess and one empty box
		val = val + 100;
	break;
	case 136:      //a+b+c:one computer,one player, one empty
		val = val + 10;
	break;
	case 58:       //a+2c:one computer and two empty
		val = val + 5;
	break;
	case 57:       //b+2c:one player and two empty
		val = val + 3;
	break;
	case -21:     //3c: three empty
		val = val + 1;
	break;
	case 215:      //two computer and one player:try to avoid
		val = val - 10;
	break;
	case 135:      //two player and one empty:try its best to avoid
		val = val -1000;
	break;
	}
	}
	return val;
}


//nothing left below: just some old codes (I used to use many if and find
//it really annoying, thus I use the val(see above) instead)



/*public int value1(sandbox[][][][] st2,sandbox sb1, String pc,String cc)
{
	sb1.value1 = 0;
	int size = 3;
	int i,j,k;
	Boolean overall;
			for(k=0;k<=8;k++)
		    for(j=0;j<=8;j++)
		    {
			if(sandbox.win(st2[k][j],sb1,size,pc,cc))
			{
				if(sb1.pickedbywhom == 'P')
				{
				sb1.value1 = sb1.value1 + 10000;
				}
				else
				{
				//value1 = -10000;
				}
			}
			else if(twochess(st2[k][j],sb1,size))
			{
				
			}
			else if
		}
			return value1;
}

public static Boolean twochess(sandbox[][] sb,sandbox sb1,int size,String cc,String pc)
{
	Boolean winornot = false;
	sb1.pc1 = pc;
	sb1.cc1 = cc;
	twoinrow(sb,sb1,size);   //:when three x or three o placed like -
	twoincol(sb,sb1,size);   //:when three x or three o placed like |
	if(!sb[1][1].value.equals(sb1.pc1))
	{
	twoinslash(sb,sb1,size); //:when three x or three o placed like /
	twoinbacksl(sb,sb1,size);//:when three x or three o placed like \
	}
	if(sb1.win)
	{winornot = true;}
	return winornot;
}


private static String twoinrow(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(row=0;row<=size;row++)
	{
		for(col=0;col<=size-2;col++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row][col+1].value)&&!sb[row][col+2].value.equals(sb[row][col+1].value))
				{
					sandbox.whowin(sb[row][col].value,sb1);
					if(sb1.pickedbywhom == 'P')
					{
						if(sb[row][col+2].value.equals(sb1.cc1))
						{
							return "breaksuccess";
						}
						else if(sb[row][col+2].value.equals("-"))
						{
							return "breakfail";
						}
					}else if(sb1.pickedbywhom == 'C') {
						if(sb[row][col+2].value.equals(sb1.pc1))
						{
							return "beingbreak";
						}
						else if(sb[row][col+2].value.equals("-"))
						{
							return "goingtowin m";
						}
					}
					return "No";
				}
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row][col+2].value)&&sb[row][col].value.equals(sb[row][col+1].value))
				{
					sandbox.whowin(sb[row][col].value,sb1);
				}
				if(!sb[row][col+1].value.equals("-")&&sb[row][col+1].value.equals(sb[row][col+2].value)&&sb[row][col].value.equals(sb[row][col+2].value))
				{
					sandbox.whowin(sb[row][col+1].value,sb1);
				}
		}
	}
	return "No";	
}


private static char twoincol(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(col=0;col<=size;col++)
	{
		for(row=0;row<=size-2;row++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row+1][col].value)&&!sb[row+2][col].value.equals(sb[row][col].value));
				{
				sandbox.whowin(sb[row][col].value,sb1);
				}
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row+2][col].value)&&!sb[row+1][col].value.equals(sb[row][col].value));
				{
				sandbox.whowin(sb[row][col].value,sb1);
				}
				if(!sb[row+1][col].value.equals("-")&&sb[row+1][col].value.equals(sb[row+2][col].value)&&!sb[row+2][col].value.equals(sb[row][col].value))
				{
				sandbox.whowin(sb[row+1][col].value,sb1);
				}
		}
	}
	
}

private static void twoinslash(sandbox[][] sb,sandbox sb1,int size)
{
		if(!sb[2][0].value.equals("-")&&sb[2][0].value.equals(sb[1][1].value)&&!sb[0][2].value.equals(sb[2][0].value))
				{
					sandbox.whowin(sb[2][0].value,sb1);
					}
	
	}


private static void twoinbacksl(sandbox[][] sb,sandbox sb1,int size)
{
	int row,col;
	for(col=0;col<=size-2;col++)
	{
		for(row=2;row<=size;row++)
		{
				if(!sb[row][col].value.equals("-")&&sb[row][col].value.equals(sb[row-1][col+1].value)&&sb[row][col].value.equals(sb[row-2][col+2].value))
				{
					sandbox.whowin(sb[row][col].value,sb1);
					}
		}
	}
}*/

}
