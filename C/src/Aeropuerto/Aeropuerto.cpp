#include <pthread.h>
#include <ncurses.h>
#include <stdlib.h>     /* srand, rand */
#include <unistd.h>
#include <signal.h>

using namespace std;

//#define Minix

#ifndef Minix
#include "Aeropuerto.h"
#endif

const int planeWidth = 7, trackUp = 5, trackDown = trackUp + planeWidth + 3;
const double aceleration = 0.75;
bool action;
bool run;
pthread_mutex_t mutexBuffer;
pid_t trackpid,planepid;

pid_t thread(pthread_t *__restrict __newthread, void *(*__start_routine)(void *))
{
	// Minix don´t have threads
#ifdef Minix
	pid_t pid = fork();
	if (pid == -1)
	{
		// When fork() returns -1, an error happened.
		perror("fork failed");
		endwin();
		exit(EXIT_FAILURE);
	}
	else if (pid == 0)
	{
		void* arg;
		(*__start_routine)(arg);

		char str[32];
		sprintf(str, "Finished: %d", getpid());
		mvaddstr(0, 0, str);
		refresh();

		_exit(EXIT_SUCCESS);
	}
	else
	{
		return pid;
	}
#else
	if (pthread_create(__newthread, NULL, __start_routine, NULL))
	{
		perror("Error creating thread");
		endwin();
		exit(EXIT_FAILURE);
	}
	return 0;
#endif
}

void sleep2(double const seconds)
{
	usleep(seconds * 1E6);
}

double vel(double t)
{
	return aceleration * t;
}

void printHeader()
{
	pthread_mutex_lock(&mutexBuffer);
	mvaddstr(1, 64, "Airport Simulation");
	refresh();
	pthread_mutex_unlock(&mutexBuffer);
}

void cleanPlane(int l, int c)
{
	pthread_mutex_lock(&mutexBuffer);
	l -= 1;
	mvaddstr(l + 1, c, "                    ");
	mvaddstr(l + 2, c, "                    ");
	mvaddstr(l + 3, c, "                    ");
	mvaddstr(l + 4, c, "                    ");
	mvaddstr(l + 5, c, "                    ");
	mvaddstr(l + 6, c, "                    ");
	mvaddstr(l + 7, c, "                    ");
	pthread_mutex_unlock(&mutexBuffer);

}

void drawPlane(int l, int c)
{
	pthread_mutex_lock(&mutexBuffer);
	l -= 1;
	mvaddstr(l + 1, c, "              ,-.   ");
	mvaddstr(l + 2, c, "    _        /  /   ");
	mvaddstr(l + 3, c, "   ; \\____,-==-._   ");
	mvaddstr(l + 4, c, "   //_    `----' {+>");
	mvaddstr(l + 5, c, "   `  `'--/  /-'`   ");
	mvaddstr(l + 6, c, "         /  /       ");
	mvaddstr(l + 7, c, "         `='        ");
	refresh();
	pthread_mutex_unlock(&mutexBuffer);
}

void* veloc(void*)
{
	int lin = trackDown + 3;
	int col = 20;
	double tim = 0;
	for (int i = 1; i < 101 + 1; i++)
		tim += 1 / vel(i);
	tim /= 100;
	for (int i = 1; i < 100; i++)
	{
		char str[32];
		if (action)
		{
			sprintf(str, "V= %3.0f m/s     ", vel(tim * (i - 1)) * 100);
		}
		else
		{
			sprintf(str, "V= %3.0f m/s     ", vel(tim * (100 - i - 1)) * 100);
		}

		pthread_mutex_lock(&mutexBuffer);
		mvaddstr(lin, col, str);
		refresh();
		pthread_mutex_unlock(&mutexBuffer);
		sleep2(tim);
	}
	pthread_mutex_lock(&mutexBuffer);
	mvaddstr(lin, col, "                    ");
	refresh();
	pthread_mutex_unlock(&mutexBuffer);
	return NULL;
}

void* plane(void*)
{
	int start = 0, end = 120, l = trackUp + 3;
	pthread_t velocimeter;
	/* initialize random seed: */
	srand(time(NULL));

	while (run)
	{
		if ((rand() % 9) % 2 == 0) // Despega
		{
			action = true;
			thread(&velocimeter, veloc);
			for (int i = start + 20; i < end; i++)
			{
				drawPlane(l, i - 3);
				sleep2(1 / vel(i - 20 + 1));
			}
			cleanPlane(l, end - 1);
		}
		else // Aterriza
		{
			action = false;
			thread(&velocimeter, veloc);
			for (int i = start; i < end - 20 + 1; i++)
			{
				drawPlane(l, i);
				sleep2(1 / vel(100 - i + 1));
			}
			cleanPlane(l, end - 20);
			sleep2(2);
		}
	}
	return NULL;
}

void* drawTrack(void*)
{
	int width = 100, start = 20, mod = 5, current = 0;
	while (run)
	{
		pthread_mutex_lock(&mutexBuffer);
		for (int i = 0; i < width; i++)
		{
			mvaddstr(trackUp, i + start, "-");
			mvaddstr(trackDown, i + start, "-");

			if (i % mod == current)
			{
				mvaddstr(trackUp + 1, i + start, "#");
				mvaddstr(trackDown + 1, i + start, "#");
			}
			else
			{
				mvaddstr(trackUp + 1, i + start, " ");
				mvaddstr(trackDown + 1, i + start, " ");
			}

			mvaddstr(trackUp + 2, i + start, "-");
			mvaddstr(trackDown + 2, i + start, "-");
		}
		current = (current + 1) % mod;
		refresh();

		pthread_mutex_unlock(&mutexBuffer);
		sleep2(0.25);
	}
	return NULL;
}

void main2()
{
	clear();
	printHeader();
	pthread_t track;
	pthread_t planethread;

	trackpid = thread(&track, drawTrack);
	planepid = thread(&planethread, plane);

	while (true)
	{
		char c = getch();
		if (c == 'q')
			break;
	}
	kill(trackpid, 0);
	kill(planepid, 0);
}

bool init()
{
	printHeader();

	pthread_mutex_lock(&mutexBuffer);
	mvaddstr(3, 0, "Press any key to start or q for quit");
	refresh();

	pthread_mutex_unlock(&mutexBuffer);
	if (getch() == 'q') // # Exit
		return false;
	return true;

}

#ifdef Minix
int main(int argc, char **argv)
#else
int Aeropuerto(int argc, char **argv)
#endif
{
	initscr();

	noecho();
	curs_set(0);
	run = true;
	pthread_mutex_init(&mutexBuffer, NULL);
	if (init())
	{
		main2();
	}
	run = false;
	endwin();
	return 0;
}
