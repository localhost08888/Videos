#include <sys/types.h>

#include <sys/stat.h>

#include <fcntl.h>

#include <stdio.h>

#include <stdlib.h>

#include <string.h>

void make_toks(char *s, char *tok[])

{

	int i=0;

	char *p;

	p = strtok(s," ");

	while(p!=NULL)

	{

		tok[i++]=p;

		p=strtok(NULL," ");

	}

	tok[i]=NULL;

}

void count(char *fn, char op)

{

	int fh,cc=0,wc=0,lc=0;

	char c;

	fh = open(fn,O_RDONLY);

	if(fh==-1)

	{

		printf("File %s not found.\n",fn);

		return;

	}

	while(read(fh,&c,1)>0)

	{

		if(c==' ') wc++;

		else if(c=='\n')

		{

			wc++;

			lc++;

		}

		cc++;

	}

	close(fh);

	switch(op)

	{

	case 'c':

		printf("No.of characters:%d\n",cc);

		break;

	case 'w':

		printf("No.of words:%d\n",wc);

		break;

	case 'l':

		printf("No.of lines:%d\n",lc);

		break;

	}

}

int main()

{

	char buff[80],*args[10];

	int pid;

	while(1)

	{

		printf("myshell$");

		fflush(stdin);

		fgets(buff,80,stdin);

		buff[strlen(buff)-1]='\0';

		make_toks(buff,args);

		if(strcmp(args[0],"count")==0)

			count(args[2],args[1][0]);

		else

		{

			pid = fork();

			if(pid>0)

				wait();

			else

			{

				if(execvp(args[0],args)==-1)

					printf("Bad command.\n");

			}

		}

	}

	return 0;

}
// Use a C compiler (e.g., gcc) to compile the program. Open a terminal and run:

// bash
// Copy code
// gcc myshell.c -o myshell
// This will create an executable named myshell.
// If there are any errors or warnings, they will appear during this step. Fix them if needed.
// 3. Run the Program
// Execute the compiled program:

// bash
// Copy code
// ./myshell
// You will see a prompt like:

// ruby
// Copy code
// myshell$
// 4. Test Built-in Commands
// You can test the count command with different options:

// Create a text file, e.g., test.txt, with some content.
// Run commands like:
// bash
// Copy code
// count c test.txt  # Counts the number of characters
// count w test.txt  # Counts the number of words
// count l test.txt  # Counts the number of lines
// If the file test.txt does not exist, you'll see an error message:

// arduino
// Copy code
// File test.txt not found.
// 5. Run External Commands
// For other commands, the shell behaves like a normal terminal. For example:

// List files:
// bash
// Copy code
// ls
// Check the current directory:
// bash
// Copy code
// pwd
// Compile a file:
// bash
// Copy code
// gcc -o program program.c
// 6. Exit the Shell
// To exit the shell, you can use Ctrl+C or terminate the process manually.

// Example Session
// Here's an example of running the program:

// bash
// Copy code
// $ ./myshell
// myshell$ count c test.txt
// No.of characters:42
// myshell$ count w test.txt
// No.of words:8
// myshell$ ls
// myshell.c  myshell  test.txt
// myshell$ gcc -o program program.c
// myshell$ ./program
// Hello, World!
// myshell$