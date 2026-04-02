1. AppController class is the entry point to the system, which allows user to (1) enter or edit current job details by enterCurrentJob(), (2) enter job offers by enterJobOffer(), (3) adjust the comparison settings by adjustComparisonSettings(), or (4) compare job offers by compareJobs(), which is further handled by JobComparator class. Compare feature is only available when the boolean variable canCompare is true when there at least two job offers if no current job or at least one job offer if there is current job.

2. a. The CurrentJob class extend the Job class, which contains attributes storing all the details of a job, derived results and methods used for derivation calculation. User is able to enter or details of the current job.  
   b. Current job detail will be saved in AppController as currentJob attribute once user chose to save, or it wont be save if choose to cancel.

3. a. Similarly to CurrentJob, JobOffer extends Job class and store all the details of a job offer and user is able to enter.  
   b. Similarly to current job, job offer detail will be saved in AppController or cancel.  
   c. These actions are coordinated by AppController. JobComparator handle the compare request if the current job is present, meaning the boolean variable hasCurrentJob is true.

4. ComparasonSettings class stores all the integer weights to each factor with default value of 1\. It contains getTotalWeight() method for score calculation. Once user choose to save, it would be save in AppController as settings attribute.

5. a. JobRankingService class handles the ranking logic. It uses JobScorer to compute job score, then rankJobs() returns list of RankedJob which including current job if present and all job offers. It includes current job if hasCurrentJob is true. RankedJob includes job and the score.  
   b. compareJobs() coordinates comparison of two chosen jobs and triggers the comparison in jobComparator.  
   c. The comparison result is displayed via ComparisonResult returned by JobComparator, which contains job details with adjusted yearly salary, adjusted yearly bonus, calculated life insurance and job score.  
   d. It offers to perform another comparison by AppControllers and return to the main menu by GUI.

6. The score calculation is implemented in JobScorer, with the method of computeScore(job, settings). The calculation uses the job details and derived adjusted details stored in Job and comparison settings. Methods from Job and ComparisonSettings help do the calculation. Separating score calculation into JobScores isolated policy logic from domain date and main single responsibility for the Job class.

7. This is not represented in my score as it will be handled entirely within the GUI implementation.

8. The entire system operates within a single instance of AppController. No communication or saving between devices are considered.

