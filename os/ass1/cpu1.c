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

		printf("%s\t%d\t%d\t%d\t%d\t%d\n",

			p->pname,p->at,p->bt,p->ct,tat,wt);		

		p=p->next;

	}

	printf("Avg TAT=%f\tAvg WT=%f\n",

			avg_tat/n,avg_wt/n);

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

NODE * get_fcfs()

{

	NODE *p;

	p = first;

	while(p!=NULL)

	{

		if(p->at<=time && p->bt1!=0)

			return p;

		p=p->next;

	}

	return NULL;

}



struct gantt_chart

{

	int start;

	char pname[30];

	int end;

}s[100],s1[100];

int k;

void fcfs()

{

	int prev=0,n1=0;

	NODE *p;

	while(n1!=n)

	{

		p = get_fcfs();

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

	fcfs();

	print_output();

	print_gantt_chart();

	return 0;

}






Key Functions
accept_info():

Takes input for the number of processes and their details (name, arrival time, burst time).
Adds processes to a linked list.
print_input():

Prints the process information as entered by the user.
print_output():

Calculates and displays the turnaround time (TAT) and waiting time (WT) for each process.
Computes and displays the average TAT and WT.
sort():

Sorts the linked list of processes based on their arrival times.
get_fcfs():

Selects the next process to execute based on the FCFS scheduling rule.
fcfs():

Simulates the FCFS scheduling algorithm, tracking the current time and process completion.
Updates the Gantt chart structure (s[]).
print_gantt_chart():

Constructs and prints the Gantt chart, merging consecutive entries for the same process.
How to Compile and Run the Program
Save the Program
Save the code to a file, for example, fcfs.c.

Compile the Program
Use the following command to compile:

bash
Copy code
gcc -o fcfs fcfs.c
Run the Program
Execute the compiled program:

bash
Copy code
./fcfs