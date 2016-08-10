import thread
import threading
import time


pieces = ['A1', 'A2', 'B', 'C']


def create(original, piece, inventory, printLock):
    if original != None and inventory[original] < 1:
        return False

    printLock.acquire() # Synchronized
    if original != None:
        inventory[original] -= 1
    inventory[piece] += 1
    print 'Created:', piece, 'Inventory:', inventory
    printLock.release()
    time.sleep(0.1)
    return True


def MA(inventory, sA1, sC, sA2, printLock):
    while True:
        if sC.acquire(False):
            create('C', 'A2', inventory, printLock)
            sA2.release()
        else:
            create(None, 'A1', inventory, printLock)
            sA1.release()

def MB(inventory, sA1, sB, printLock):
    while True:
        sA1.acquire()
        create('A1', 'B', inventory, printLock)
        sB.release()

def MC(inventory, sB, sC, printLock):
    while True:
        sB.acquire()
        create('B', 'C', inventory, printLock)
        sC.release()

def run():
    inventory = dict.fromkeys(pieces, 0)
    sA1 = threading.Semaphore()
    sA2 = threading.Semaphore()
    sB = threading.Semaphore()
    sC = threading.Semaphore()
    printLock = threading.Lock()

    thread.start_new_thread(MA, (inventory, sA1, sC, sA2, printLock))
    thread.start_new_thread(MB, (inventory, sA1, sB, printLock))
    thread.start_new_thread(MC, (inventory, sB, sC, printLock))
    while True:
        time.sleep(1)

run()
