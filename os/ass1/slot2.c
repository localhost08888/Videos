#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <unistd.h>
#include <errno.h>

void make_toks(char *s, char *tok[])
{
  int i = 0;
  char *p;
  p = strtok(s, " ");
  while (p != NULL)
  {
    tok[i++] = p;
    p = strtok(NULL, " ");
  }
  tok[i] = NULL;
}

void search(char mode, char *filename, char *pattern)
{
  FILE *fp = fopen(filename, "r");
  if (!fp)
  {
    printf("File %s not found.\n", filename);
    return;
  }
  char line[1024];
  int line_num = 0, count = 0, found = 0;

  while (fgets(line, sizeof(line), fp))
  {
    line_num++;
    char *pos = strstr(line, pattern);
    if (pos)
    {
      if (mode == 'f')
      {
        printf("First occurrence at line %d: %s", line_num, line);
        found = 1;
        break;
      }
      else if (mode == 'a')
      {
        printf("Occurrence at line %d: %s", line_num, line);
      }
      else if (mode == 'c')
      {
        count++;
      }
    }
  }

  if (mode == 'c')
  {
    printf("Pattern found %d times.\n", count);
  }
  else if (mode == 'f' && !found)
  {
    printf("Pattern not found.\n");
  }
  fclose(fp);
}

void typeline(char mode, int n, char *filename)
{
  FILE *fp = fopen(filename, "r");
  if (!fp)
  {
    printf("File %s not found.\n", filename);
    return;
  }

  char lines[1024][1024];
  int line_count = 0;

  while (fgets(lines[line_count], sizeof(lines[line_count]), fp))
  {
    line_count++;
  }

  if (mode == 'a')
  {
    for (int i = 0; i < line_count; i++)
    {
      printf("%s", lines[i]);
    }
  }
  else if (mode == 'n')
  {
    for (int i = 0; i < n && i < line_count; i++)
    {
      printf("%s", lines[i]);
    }
  }
  else if (mode == '-')
  {
    for (int i = (line_count - n < 0 ? 0 : line_count - n); i < line_count; i++)
    {
      printf("%s", lines[i]);
    }
  }
  fclose(fp);
}

void list(char mode, char *dirname)
{
  DIR *dir = opendir(dirname);
  if (!dir)
  {
    printf("Directory %s not found.\n", dirname);
    return;
  }

  struct dirent *entry;
  int count = 0;

  while ((entry = readdir(dir)) != NULL)
  {
    count++;
    if (mode == 'n')
    {
      printf("%s\n", entry->d_name);
    }
    else if (mode == 'i')
    {
      printf("Filename: %s, Inode: %ld\n", entry->d_name, entry->d_ino);
    }
  }

  if (mode == 'n' && count == 0)
  {
    printf("No entries found.\n");
  }
  else if (mode == 'c')
  {
    printf("Total entries: %d\n", count);
  }
  closedir(dir);
}

int main()
{
  char buff[80], *args[10];
  int pid;

  while (1)
  {
    printf("myshell$ ");
    fflush(stdin);
    fgets(buff, sizeof(buff), stdin);
    buff[strlen(buff) - 1] = '\0';
    make_toks(buff, args);

    if (strcmp(args[0], "count") == 0)
    {
      count(args[2], args[1][0]);
    }
    else if (strcmp(args[0], "search") == 0)
    {
      search(args[1][0], args[2], args[3]);
    }
    else if (strcmp(args[0], "typeline") == 0)
    {
      typeline(args[1][0], atoi(args[2]), args[3]);
    }
    else if (strcmp(args[0], "list") == 0)
    {
      list(args[1][0], args[2]);
    }
    else
    {
      pid = fork();
      if (pid > 0)
      {
        wait(NULL);
      }
      else
      {
        if (execvp(args[0], args) == -1)
        {
          printf("Bad command.\n");
        }
      }
    }
  }

  return 0;
}
Features Implemented:
search:
f: Finds the first occurrence of a pattern in the file.
a: Lists all occurrences of the pattern in the file.
c: Counts the occurrences of the pattern in the file.
typeline:
n: Displays the first n lines of the file.
-n: Displays the last n lines of the file.
a: Displays all lines of the file.
list:
n: Displays filenames in a directory.
c: Counts the number of entries in a directory.
i: Displays filenames and their inode numbers.





1. Save the Program
Save the program to a file named, for example, myshell.c.

2. Compile the Program
Compile the program using gcc:

bash
Copy code
gcc -o myshell myshell.c
If there are no errors, an executable file named myshell will be created.

3. Run the Program
Run the compiled shell:

bash
Copy code
./myshell
The prompt myshell$ will appear, and you can start entering commands.

4. Commands to Test
Here’s how to test the new commands:

Search Command
bash
Copy code
myshell$ search f filename pattern
Finds the first occurrence of pattern in filename.
bash
Copy code
myshell$ search a filename pattern
Lists all occurrences of pattern in filename.
bash
Copy code
myshell$ search c filename pattern
Counts occurrences of pattern in filename.
Typeline Command
bash
Copy code
myshell$ typeline n 5 filename
Displays the first 5 lines of filename.
bash
Copy code
myshell$ typeline - 3 filename
Displays the last 3 lines of filename.
bash
Copy code
myshell$ typeline a filename
Displays all lines of filename.
List Command
bash
Copy code
myshell$ list n dirname
Displays filenames in the directory dirname.
bash
Copy code
myshell$ list c dirname
Counts the number of entries in the directory dirname.
bash
Copy code
myshell$ list i dirname
Displays filenames and their inode numbers in the directory dirname.
Example Workflow
Create a text file example.txt with some sample text:

bash
Copy code
echo -e "Line 1\nLine 2\nLine 3\nLine 4\nLine 5" > example.txt
Test the typeline command:

bash
Copy code
myshell$ typeline n 3 example.txt
Output:

mathematica
Copy code
Line 1
Line 2
Line 3
Test the search command:

bash
Copy code
myshell$ search c example.txt Line
Output:

bash
Copy code
Pattern found 5 times.
Test the list command:

bash
Copy code
myshell$ list n .
Output:

Copy code
myshell
example.txt
myshell.c
5. Exit the Shell
To exit the shell, use Ctrl+C or terminate the program from another terminal