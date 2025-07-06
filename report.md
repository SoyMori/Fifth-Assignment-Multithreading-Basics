1. start() vs run()

The output of this code snippet is as follows:

Calling run()
Running in: main
Calling start()
Running in: Thread-2

Calling a thread directly using the run() method causes the code to execute like a regular method and the multithreading system won't be activated. That's why the output first shows the name of the main method.
However, calling a thread using the start() method creates a multithreading system, and the target code executes in parallel with other threads. As a result, the output displays the name of the second thread.

2. Daemon Threads

The output of this code is something like this:

Daemon thread running...
Daemon thread running...
. ━━━┓
.    ┣━ May be less than the number defined inside the loop
. ━━━┛
Daemon thread running...
Main thread ends.

Because when we set a thread as a daemon, the entire thread ends when the main ends, so the number of times the command is executed within the thread may not reach a certain value.
This type of threads has many uses:
For example, automatic data saving in an IDE program, cache clearing in a web application, an automatic timer in a game, notification display in a chat application, and background Android programs such as music playback or downloads.

3. A shorter way to create threads

The output of this code is this:

Thread is running using a ...!

Lambda expression

It provides a cleaner output and has a smaller footprint, but it may not be very useful for large-scale applications and expansions.