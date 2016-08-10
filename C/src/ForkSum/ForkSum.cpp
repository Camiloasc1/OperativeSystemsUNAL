/*
 * ForkSum.cpp
 *
 *  Created on: 10/09/2014
 */

#include <sys/types.h> /* pid_t */
#include <sys/wait.h>  /* waitpid */
#include <stdio.h>     /* printf, perror */
#include <stdlib.h>    /* exit */
#include <unistd.h>    /* _exit, fork */
#include <iostream>

#define PIPE_READ  0
#define PIPE_WRITE 1

using namespace std;

void createProc();
void makeTree(pid_t);

/*
 * 		  P
 *	l1			r1
 *			l2		r2
 *					l3
 */

struct Tree
{
		int l1;
		int r1;
		int l2;
		int r2;
		int l3;
		Tree()
		{
			l1 = -1;
			r1 = -1;
			l2 = -1;
			r2 = -1;
			l3 = -1;
		}
} SumTree;

void makeTree(pid_t chpid)
{
	if (SumTree.l1 == -1)
	{
		SumTree.l1 = chpid;
		cout << "l1: (PID=" << SumTree.l1 << ") (sum=" << SumTree.l1 % 10 << ")" << endl;
	}
	else if (SumTree.r1 == -1)
	{
		SumTree.r1 = chpid;
		createProc(); //l2
		createProc(); //r2
		int sum = 0;
		sum += SumTree.r1 % 10;
		sum += SumTree.l2 % 10;
		sum += SumTree.r2 % 10;
		sum += SumTree.l3 % 10;
		cout << "r1: (PID=" << SumTree.r1 << ") (sum=" << sum << ")" << endl;
	}
	else if (SumTree.l2 == -1)
	{
		SumTree.l2 = chpid;
		cout << "l2: (PID=" << SumTree.l2 << ") (sum=" << SumTree.l2 % 10 << ")" << endl;
	}
	else if (SumTree.r2 == -1)
	{
		SumTree.r2 = chpid;
		createProc(); //r3
		int sum = 0;
		sum += SumTree.r2 % 10;
		sum += SumTree.l3 % 10;
		cout << "r2: (PID=" << SumTree.r2 << ") (sum=" << sum << ")" << endl;
	}
	else if (SumTree.l3 == -1)
	{
		SumTree.l3 = chpid;
		cout << "l3: (PID=" << SumTree.l3 << ") (sum=" << SumTree.l3 % 10 << ")" << endl;
	}
}

void createProc()
{
	int pidpipe[2];
	int stpipe[2];

	if (pipe(pidpipe) == -1 || pipe(stpipe) == -1)
	{
		perror("pipe");
		exit(EXIT_FAILURE);
	}
	pid_t pid = fork();
	if (pid == -1)
	{
		// When fork() returns -1, an error happened.
		perror("fork failed");
		exit(EXIT_FAILURE);
	}
	else if (pid == 0)
	{
		// When fork() returns 0, we are in the child process.
		pid_t chpid = 0;
		read(pidpipe[PIPE_READ], &chpid, sizeof(chpid));
		makeTree(chpid);
		write(stpipe[PIPE_WRITE], &SumTree, sizeof(SumTree));
		_exit(EXIT_SUCCESS);  // exit() is unreliable here, so _exit must be used
	}
	else
	{
		// When fork() returns a positive number, we are in the parent process
		// and the return value is the PID of the newly created child process.

		write(pidpipe[PIPE_WRITE], &pid, sizeof(pid));
		read(stpipe[PIPE_READ], &SumTree, sizeof(SumTree));

		int status;
		(void) waitpid(pid, &status, 0);
		// wait(&status);
	}
}

void printHierarchy()
{
	/*
	 * 		  P
	 *	l1			r1
	 *			l2		r2
	 *					l3
	 */
	cout << "HIERARCHY:" << endl;
	cout << endl;
	cout << " 		  P" << endl;
	cout << "	l1			r1" << endl;
	cout << "			l2		r2" << endl;
	cout << "					l3" << endl;
	cout << endl;
}

int ForkSum(int argc, char **argv)
{
	printHierarchy();
	pid_t ppid = getpid();
	createProc(); // l1
	createProc(); // r1
	int sum = 0;
	sum += ppid % 10;
	sum += SumTree.l1 % 10;
	sum += SumTree.r1 % 10;
	sum += SumTree.l2 % 10;
	sum += SumTree.r2 % 10;
	sum += SumTree.l3 % 10;
	cout << "Parent: (PID=" << ppid << ") (TotalSum=" << sum << ")" << endl;
	return EXIT_SUCCESS;
}
