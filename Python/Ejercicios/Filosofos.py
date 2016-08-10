import thread
import threading
import time

N = 5
deadTime = 30
thinkTime = 15
eatTime = 5
waitTime = 0.25

def filosofo(i, r, l, pL):
    pL.acquire()
    print "#", i, "Thinking..."
    pL.release()

    time.sleep(thinkTime)

    pL.acquire()
    print "#", i, "Hungry!!!"
    pL.release()
    Ht = time.time()

    while True:
        if Ht < (time.time() - deadTime):
            pL.acquire()
            print "#", i, "***DEAD***"
            pL.release()
            return

        if r.acquire(False):
            if l.acquire(False):
                pL.acquire()
                print "#", i, "Eating..."
                pL.release()

                time.sleep(eatTime)

                r.release()
                l.release()

                pL.acquire()
                print "#", i, "Thinking..."
                pL.release()

                time.sleep(thinkTime)

                pL.acquire()
                print "#", i, "Hungry!!!"
                pL.release()
                Ht = time.time()
            else:
                r.release()
        time.sleep(waitTime)


def main():
    sem = [threading.Lock() for _ in xrange(N)]
    pL = threading.Lock()
    for i in xrange(N):
        thread.start_new_thread(filosofo, (i, sem[i], sem[(i + 1) % N], pL))
    while True:
        time.sleep(5)

main()
