Steps to Execute the Code
1. Save the Code
Save the program into a file, e.g., sjfnp.c.

2. Compile the Program
Use the GCC compiler to compile the program:

bash
Copy code
gcc -o sjfnp sjfnp.c
If the compilation is successful, an executable named sjfnp will be generated.

3. Run the Program
Execute the program:

bash
Copy code
./sjfnp
4. Provide Input
The program will prompt you to enter the number of processes and their details.

Example Input:

plaintext
Copy code
Enter no.of process: 4
Enter process name: P1
Enter arrival time: 0
Enter first CPU burst time: 6
Enter process name: P2
Enter arrival time: 2
Enter first CPU burst time: 8
Enter process name: P3
Enter arrival time: 4
Enter first CPU burst time: 7
Enter process name: P4
Enter arrival time: 6
Enter first CPU burst time: 3
Expected Output
1. Process Execution Table
After sorting and scheduling:

plaintext
Copy code
pname	at	bt
P1	    0	6
P4	    6	3
P3	    4	7
P2	    2	8
2. Completion Table
plaintext
Copy code
pname	at	bt	ct	tat	wt
P1	    0	6	6	6	0
P4	    6	3	9	3	0
P3	    4	7	16	12	5
P2	    2	8	24	22	14
Avg TAT=10.750000	Avg WT=4.750000
3. Gantt Chart
plaintext
Copy code
0----P1----6-P4-9----P3----16----P2----24


#include<stdio.h>

#include<stdlib.h>

#include<string.h>

typedef struct process_info

{

	char pname[20];

	int at,bt,ct,bt1;

	struct process_info *next;

}NODE;



int n;

NODE *first,*last;



void accept_info()

{

	NODE *p;

	int i;

	printf("Enter no.of process:");

	scanf("%d",&n);

	for(i=0;i<n;i++)

	{

		p = (NODE*)malloc(sizeof(NODE));

		printf("Enter process name:");

		scanf("%s",p->pname);

		printf("Enter arrival time:");

		scanf("%d",&p->at);

		printf("Enter first CPU burst time:");

		scanf("%d",&p->bt);		

		p->bt1 = p->bt;

		p->next = NULL;

		if(first==NULL)

			first=p;

		else

			last->next=p;

		last = p;

	}

}



void print_output()

{

	NODE *p;

	float avg_tat=0,avg_wt=0;

	printf("pname\tat\tbt\tct\ttat\twt\n");

	p = first;

	while(p!=NULL)

	{

		int tat = p->ct-p->at;

		int wt = tat-p->bt;		

		avg_tat+=tat;

		avg_wt+=wt;

		printf("%s\t%d\t%d\t%d\t%d\t%d\n",p->pname,p->at,p->bt,p->ct,tat,wt);	

		p=p->next;

	}

	printf("Avg TAT=%f\tAvg WT=%f\n",avg_tat/n,avg_wt/n);

}



void print_input()

{

	NODE *p;

	p = first;	

	printf("pname\tat\tbt\n");

	while(p!=NULL)

	{

		printf("%s\t%d\t%d\n",

			p->pname,p->at,p->bt1);

		p = p->next;

	}

}



void sort()

{

	NODE *p,*q;

	int t;

	char name[20];

	p = first;

	while(p->next!=NULL)

	{

		q=p->next;

		while(q!=NULL)

		{

			if(p->at > q->at)

			{

				strcpy(name,p->pname);

				strcpy(p->pname,q->pname);

				strcpy(q->pname,name);



				t = p->at;

				p->at = q->at;

				q->at = t;

				

				t = p->bt;

				p->bt = q->bt;

				q->bt = t;



				t = p->ct;

				p->ct = q->ct;

				q->ct = t;



				t = p->bt1;

				p->bt1 = q->bt1;

				q->bt1 = t;		

			}

			q=q->next;

		}

		p=p->next;

	}

}

int time;



NODE * get_sjf()

{

	NODE *p,*min_p=NULL;

	int min=9999;

	p = first;

	while(p!=NULL)

	{

		if(p->at<=time && p->bt1!=0 && 

			p->bt1<min)

		{

			min = p->bt1;

			min_p = p;

		}

		p=p->next;

	}

	return min_p;

}



struct gantt_chart

{

	int start;

	char pname[30];

	int end;

}s[100],s1[100];

int k;



void sjfnp()

{

	int prev=0,n1=0;

	NODE *p;

	while(n1!=n)

	{

		p = get_sjf();

		if(p==NULL)

		{

			time++;

			s[k].start = prev;

			strcpy(s[k].pname,"*");

			s[k].end = time;

			prev = time;

			k++;

		}

		else

		{

			time+=p->bt1;

			s[k].start = prev;

			strcpy(s[k].pname, p->pname);

			s[k].end = time;

			prev = time;

			k++;

			p->ct = time;

			p->bt1 = 0;

			n1++;

		}

		print_input();	

		sort();

	}

}

void print_gantt_chart()

{

	int i,j,m;

	s1[0] = s[0];

	for(i=1,j=0;i<k;i++)

	{

		if(strcmp(s[i].pname,s1[j].pname)==0)

			s1[j].end = s[i].end;

		else

			s1[++j] = s[i];

	}

	printf("%d",s1[0].start);

	for(i=0;i<=j;i++)

	{

		m = (s1[i].end - s1[i].start);

		for(k=0;k<m/2;k++)

			printf("-");

		printf("%s",s1[i].pname);

		for(k=0;k<(m+1)/2;k++)

			printf("-");

		printf("%d",s1[i].end);

	}

}

int main()

{

	accept_info();

	sort();

	sjfnp();

	print_output();

	print_gantt_chart();

	return 0;

}