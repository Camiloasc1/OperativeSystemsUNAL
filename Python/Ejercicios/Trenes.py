import random
import thread
import threading
import time


def tren(i, route, pL):
    cStation = None # In garage
    while True:
        for nStation in route:
            nStation[1].acquire()
            if cStation == None: # Depart == None
                pL.acquire()
                print "Train", i, "from", "-garage-", "to", nStation[0]
                pL.release()
            else:
                cStation[1].release()
                pL.acquire()
                print "Train", i, "from", cStation[0], "to", nStation[0]
                pL.release()
            cStation = nStation
            time.sleep(5 + random.randint(1, 3)) # Travel + Stop
            # Ready to next

def main():
    route = [("'" + str(i) + "'", threading.BoundedSemaphore(2)) for i in xrange(5)]
    pL = threading.Lock()
    for i in xrange(8):
        thread.start_new_thread(tren, (i, route, pL))
    while True:
        time.sleep(5)

main()
