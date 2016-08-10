import curses
import math
import random
import thread
import threading
import time


colorTrack = 1
colorVel = 3
planeWidth = 7
trackUp = 5
trackDown = trackUp + planeWidth + 3

aceleration = 0.75

vel = lambda t: aceleration * t

def printHeader(stdscr, lock):
    lock.acquire()
    stdscr.addstr(1, 64, "Airport Simulation")
    stdscr.refresh()
    lock.release()

def cleanPlane(stdscr, l, c, lock):
    lock.acquire()

    l -= 1
    stdscr.addstr(l + 1, c, "                    ")
    stdscr.addstr(l + 2, c, "                    ")
    stdscr.addstr(l + 3, c, "                    ")
    stdscr.addstr(l + 4, c, "                    ")
    stdscr.addstr(l + 5, c, "                    ")
    stdscr.addstr(l + 6, c, "                    ")
    stdscr.addstr(l + 7, c, "                    ")

    lock.release()

def drawPlane(stdscr, l, c, lock):
    lock.acquire()

    l -= 1
    stdscr.addstr(l + 1, c, "              ,-.   ")
    stdscr.addstr(l + 2, c, "    _        /  /   ")
    stdscr.addstr(l + 3, c, "   ; \____,-==-._   ")
    stdscr.addstr(l + 4, c, "   //_    `----' {+>")
    stdscr.addstr(l + 5, c, "   `  `'--/  /-'`   ")
    stdscr.addstr(l + 6, c, "         /  /       ")
    stdscr.addstr(l + 7, c, "         `='        ")
    stdscr.refresh()

    lock.release()

def veloc(stdscr, action, lock):
    lin = trackDown + 3
    col = 20
    curses.init_pair(colorVel, curses.COLOR_YELLOW, curses.COLOR_BLACK)

    tim = sum([1 / (vel(i)) for i in xrange(1, 101 + 1)]) / 100
    for i in xrange(100):
        lock.acquire()

        if action:
            stdscr.addstr(lin, col, "V= " + str(int(vel(tim * (i - 1)) * 100)) + " m/s     ", curses.color_pair(colorVel))
        else:
            stdscr.addstr(lin, col, "V= " + str(int(vel(tim * (100 - i - 1)) * 100)) + " m/s     ", curses.color_pair(colorVel))

        stdscr.refresh()

        lock.release()
        time.sleep(tim)

    lock.acquire()

    stdscr.addstr(lin, col, "                    ")
    stdscr.refresh()

    lock.release()

def plane(stdscr, lock):
    start = 0
    end = 120
    l = trackUp + 3
    while True:
        if random.randint(0, 9) % 2 == 0: # Despega
            thread.start_new_thread(veloc, (stdscr, True, lock))
            for i in xrange(start + 20, end + 1):
                drawPlane(stdscr, l, i - 3, lock)
                time.sleep(1 / vel(i - 20 + 1))
            cleanPlane(stdscr, l, end - 1, lock)
        else: # Aterriza
            thread.start_new_thread(veloc, (stdscr, False, lock))
            for i in xrange(start, end - 20 + 1):
                drawPlane(stdscr, l, i, lock)
                time.sleep(1 / vel(100 - i + 1))
            cleanPlane(stdscr, l, end - 20, lock)
        time.sleep(2)

def drawTrack(stdscr, lock):
    width = 100
    start = 20
    mod = 5
    current = 0
    curses.init_pair(colorTrack, curses.COLOR_RED, curses.COLOR_BLACK)
    while True:
        lock.acquire()
        for i in xrange(width):
            stdscr.addstr(trackUp, i + start, "-")
            stdscr.addstr(trackDown, i + start, "-")

            if i % mod == current:
                stdscr.addstr(trackUp + 1, i + start, "#", curses.color_pair(colorTrack))
                stdscr.addstr(trackDown + 1, i + start, "#", curses.color_pair(colorTrack))
            else:
                stdscr.addstr(trackUp + 1, i + start, " ", curses.color_pair(colorTrack))
                stdscr.addstr(trackDown + 1, i + start, " ", curses.color_pair(colorTrack))

            stdscr.addstr(trackUp + 2, i + start, "-")
            stdscr.addstr(trackDown + 2, i + start, "-")
        current = (current + 1) % mod
        stdscr.refresh()

        lock.release()
        time.sleep(0.25)

def main(stdscr, lock):
    stdscr.clear()
    printHeader(stdscr, lock)

    thread.start_new_thread(drawTrack, (stdscr, lock))
    thread.start_new_thread(plane, (stdscr, lock))

    while True:
        c = stdscr.getch()
        if c == ord('q'): # Exit
            break

def init(stdscr, lock):
    printHeader(stdscr, lock)

    lock.acquire()
    stdscr.addstr(3, 0, "Press any key to start ", curses.A_REVERSE)
    stdscr.addstr(3, 23, "or q for quit", curses.A_REVERSE) # curses.A_BOLD
    stdscr.refresh()
    lock.release()

    if stdscr.getch() == ord('q'): # Exit
        return False
    return True

if __name__ == '__main__':
    stdscr = curses.initscr()
    curses.start_color()

    curses.noecho()
    curses.curs_set(0)
    # curses.cbreak()
    stdscr.keypad(1)

    lock = threading.RLock()
    if init(stdscr, lock):
        main(stdscr, lock)

    curses.endwin()
