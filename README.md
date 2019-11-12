# cpuscheduler

By Kathryn Porter and Danny Fayaud

CPUScheduler simulates the various algorithms a CPU uses to schedule a process queue.  

The program prompts the user to select one of five algorithms: First Come First Serve, Shortest Job First, Shortest Remaining Time First, Priority (Non-Preemptive), or Round Robin.
The user can input their own arrival and burst times or use randomly generated values.  The results are then displayed in an Alert and a text-based
Gantt chart is displayed.  

For the purposes of this project, the job queue is limited to eight processes.  The maximum time frame for the CPU execution is set to 200 units.
Processes that won't finish in the time frame can still be added, but their waiting and turnaround times will be set to zero.  These values 
will not be used when calculating average waiting and turnaround times.

Round Robin is the only algorithm that does not support dynamic arrival times.  

The model package was written by Danny Fayaud.

The view package was written by Kathryn Porter.
