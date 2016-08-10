import thread
import threading
import time


def proc(n, lock):
    while True:
        lock.acquire()
        print 'Process #', n, '- working'
        # work
        lock.release()
        time.sleep(0.1)

def main():
    lock = threading.Lock()

    thread.start_new_thread(proc, (1, lock))
    thread.start_new_thread(proc, (2, lock))
    thread.start_new_thread(proc, (3, lock))
    while True:
        time.sleep(1)

main()
