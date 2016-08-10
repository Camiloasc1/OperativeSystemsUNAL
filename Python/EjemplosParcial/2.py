import time
import thread
import threading

N = 2
R = 5
eatTime = 10
cookTime = 2

def llamarCocinero(mesa, printLock):
    if mesa['marmita'] <= 0:
        printLock.acquire()
        print 'Cocinero:', 'Inicio'
        printLock.release()
        
        time.sleep(cookTime)
        mesa['marmita'] = R
        
        printLock.acquire()
        print 'Cocinero:', 'Fin'
        printLock.release()
        


def student(mesa, lock, printLock, marmitaLock, n):
    printLock.acquire()
    print 'Estudiante', n, ': Llega'
    printLock.release()
    
    lock.acquire()
    
    printLock.acquire()
    print 'Estudiante', n, ': Se Sienta'
    printLock.release()
    
    mesa['sillas'] -= 1
    
    marmitaLock.acquire()
    if mesa['marmita'] <= 0:
        printLock.acquire()
        print 'Estudiante', n, ': Llama al Cocinero'
        printLock.release()
        
        llamarCocinero(mesa, printLock)
    
    printLock.acquire()
    print 'Estudiante', n, ': Come'
    printLock.release()
    
    mesa['marmita'] -= 1
    marmitaLock.release()
    time.sleep(eatTime)
    
    printLock.acquire()
    print 'Estudiante', n, ': Se Va'
    printLock.release()
    
    lock.release()
    mesa['sillas'] += 1

def run():
    count = 15
    mesa = {'sillas':N, 'marmita':R}
    # lock = [thread.allocate_lock() for _ in xrange(N)]
    lock = threading.Semaphore(N)
    printLock = threading.Lock()
    marmitaLock = threading.Lock()
    for n in xrange(count):
        thread.start_new_thread(student, (mesa, lock, printLock, marmitaLock, n))
    while True:
        time.sleep(1)
run()
