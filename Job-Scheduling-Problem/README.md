**Job Scheduling Problem**
----------------------------------------------
The script contains a greedy algorithm for minimizing the weighted sum
of completion times. This file describes a set of jobs with positive and
integral weights and lengths. It has the format

[number_of_jobs]

[job_1_weight] [job_1_length]

[job_2_weight] [job_2_length]

...

For example, the third line of the file is "74 59", indicating that the
second job has weight 74 and length 59. The edge weights or lenggths may
not be distinct.

**Goal:** Run a greedy algorithm that scedhiles jobs in *decreasing
  orderof the **difference (weight - line)** or decreasing order of the **ratio(weight/length)**. And if two jobs have equal
  difference(weight - length), we schedule the job with higher weight first.

**Output:**: sum of weighted completion times of the resulting schedule.

Note that you can choose different metric by uncommenting either line 63
or line 64.
