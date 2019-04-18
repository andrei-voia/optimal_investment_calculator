interface Risky
{
	public double getRisk();
}


class Member
{
	private String nume;
	private int varsta;
	
	public Member(int varsta, String nume)
	{
		this.nume=nume;
		this.varsta=varsta;
	}
}


//PROJECT
//
//
//
abstract class Project implements Risky
{
	private String manager, titlu, obiectiv;
	protected long fonduri;
	protected Member[] membru;
	protected int numarMembru=1;
	
	public Project(String titlu, String manager, String obiectiv, long fonduri)
	{
		this.titlu=titlu;
		this.obiectiv=obiectiv;
		this.fonduri=fonduri;
		this.manager=manager;
	}
	
	public abstract void addMember(Member m);
}


abstract class DeadLine extends Project
{
	private String deadLine;
	private static final int MAX_MEMBRU = 15;
	
	public DeadLine(String titlu, String manager, String obiectiv, long fonduri, String deadLine)
	{
		super(titlu, manager, obiectiv, fonduri);
		this.deadLine=deadLine;
		membru = new Member[MAX_MEMBRU];
	}
	
	public void addMember(Member m)
	{
		if(numarMembru>MAX_MEMBRU)return;
		membru[numarMembru]=m;
		numarMembru++;
	}
}


//OPEN SOURCE
//
//
//
class OpenSource extends Project
{
	private String mailingList;
	private static final int UNLIMITED = 10; 
	
	public OpenSource(String titlu, String manager, String obiectiv, long fonduri, String mailingList)
	{
		super(titlu, manager, obiectiv, fonduri);
		this.mailingList=mailingList;
		membru = new Member[UNLIMITED];
	}
	
	public void addMember(Member m)
	{
		if(membru.length==numarMembru)
		{
			Member[] change = new Member[membru.length+UNLIMITED];
			for(int i=0;i<membru.length;i++) change[i]=membru[i];
			
			membru = change;
		}
			
		membru[numarMembru]=m;
		numarMembru++;
	}
	
	public double getRisk()
	{
		return numarMembru/fonduri;
	}
}


//MILITARE
//
//
//
class Militare extends DeadLine
{
	private String parola;
	
	public Militare(String titlu, String manager, String obiectiv, long fonduri, String deadLine, String parola)
	{
		super(titlu, manager, obiectiv, fonduri, deadLine);
		this.parola=parola;
	}
	
	public double getRisk()
	{
		return numarMembru/parola.length()/fonduri;
	}
}


//COMERCIALE
//
//
//
class Comerciale extends DeadLine
{
	private long fonduriMarketing;
	private int numarEchipe;

	public Comerciale(String titlu, String manager, String obiectiv, long fonduri, String deadLine, int numarEchipe)
	{
		super(titlu, manager, obiectiv, fonduri, deadLine);
		fonduriMarketing=fonduri/2;
		this.numarEchipe=numarEchipe;
	}
	
	public double getRisk()
	{
		return numarEchipe*3/numarMembru/fonduri - fonduriMarketing;
	}
}


class InvestmentCompany
{
	private static final int UNLIMITED = 10;
	private Project[] proiect = new Project[UNLIMITED];
	private int numarProiect=0;

	public void addProject(Project p)
	{
		if(proiect.length==numarProiect)
		{
			Project[] change = new Project[proiect.length+UNLIMITED];
			for(int i=0;i<proiect.length;i++) change[i]=proiect[i];
			
			proiect = change;
		}
		
		proiect[numarProiect]=p;
		numarProiect++;
	}
	
	public Project getBestInvestment()
	{
		if(numarProiect==0)
		{
			System.out.println("ERROR >>> there are no projects added <<< \n");
			return null;
		}
		if(numarProiect==1) return proiect[0];
		
		Project investitieBuna;
		investitieBuna=proiect[0];
		
		for(int i=1;i<numarProiect;i++)
		{
			if(proiect[i].getRisk()<investitieBuna.getRisk())
				investitieBuna=proiect[i];
		}
		
		return investitieBuna;
	}
	
	public static void main(String args[])
	{
		InvestmentCompany x = new InvestmentCompany();
		
		OpenSource p1 = new OpenSource("Ciocolata", "George", "facem bani", 1, "rada@yahoo.com");
		Militare p2 = new Militare("pizza","Silapboss", "vindem", 1, "pana maine", "rada");
		Comerciale p3 = new Comerciale("scoala Vietzii","huboGoss", "face bani", 10, "vrea bossu", 300);

		p1.addMember(new Member(20, "Ted"));
		//p1.addMember(new Member(22, "Ralu"));
		
		p2.addMember(new Member(32, "Cris"));
		p2.addMember(new Member(33, "Cis"));
		//p2.addMember(new Member(34, "Crs"));
		
		p3.addMember(new Member(36, "Cris"));
		p3.addMember(new Member(40, "Gugugaga"));
		//p3.addMember(new Member(8, "Germania"));
		
		x.getBestInvestment();
		x.addProject(p1);
		x.addProject(p2);
		x.addProject(p3);
		
		System.out.println(p1.getRisk());
		System.out.println(p2.getRisk());
		System.out.println(p3.getRisk());
		
		System.out.println(x.getBestInvestment());
	}
}