/**
 * List all thread groups and threads in each group in the JVM.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

public class ThreadLister
{
	ThreadGroup rootThreadGroup = null;
 
	/* Getting the root thread group */
	ThreadGroup getRootThreadGroup( ) {
    		if ( rootThreadGroup != null )
        		return rootThreadGroup;
    		ThreadGroup currentThreadGroup = Thread.currentThread( ).getThreadGroup( );
    		ThreadGroup parentThreadGroup;
    		while ( (parentThreadGroup = currentThreadGroup.getParent( )) != null )
        		currentThreadGroup = parentThreadGroup;

		rootThreadGroup = currentThreadGroup;

    		return rootThreadGroup;
	}

	/* Getting a list of all thread groups */
	ThreadGroup[] getAllThreadGroups( ) {
    		final ThreadGroup root = getRootThreadGroup( );

		/* Show the number of active groups in this thread group */
   		int alloc = root.activeGroupCount( );

		/* Repeating over the thread groups */
    		int n = 0;
    		ThreadGroup[] groups;
    		do {
			/* Creating a new group by doubling the active groups */
        		alloc *= 2;
        		groups = new ThreadGroup[alloc];

			/* Copying the root to every active subgroup groups in thread group
                        If it is true, It will return the number of the thread groups in to the array root
                        until we get the number of the desired groups in thread group*/
        		n = root.enumerate(groups, true);
    		} while (n == alloc);

		   /* Create new thread group, it is one larger compared to root */ 
    		ThreadGroup[] allGroups = new ThreadGroup[n+1];

		   /* Storing root at an index 0 */
    		allGroups[0] = root;

		   /* copying other groups from groups variable to all groups from index 1 for count of n groups */
    		System.arraycopy( groups, 0, allGroups, 1, n );

    		return allGroups;
	}

	public static void main(String[] args) {
		
		new CreateThreadGroups();
		
		ThreadLister groups = new ThreadLister();

		/* Getting a thread group by name */
		ThreadGroup[] groupList = groups.getAllThreadGroups();

		/* Repeating all groups to the group list length*/
		for (int i = 0; i < groupList.length; i++) {
			/* creating a thread array twice the active threads in the group
                    It will copy all the active threads from group to the array list*/
			Thread list[] = new Thread[groupList[i].activeCount() * 2];
			groupList[i].enumerate(list, false);
		
			/* Print group name, printing the list of threads and statuses in the group */
			System.out.println(groupList[i].getName());
			for (int j = 0; j < list.length; j++) {
				if (list[j] != null)
					System.out.println("\t"+list[j].getName()+":"+list[j].getId()+":"+list[j].getState()+":"+list[j].isDaemon());
			}
		}
	}
}
