package com.adnan;
import java.util.*;
import java.util.Map.Entry;
import java.lang.*;

import static java.lang.System.out;

import java.io.IOException;

import javax.servlet.http.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.adnan.Stream;
import com.adnan.ConnexusImage;
import com.adnan.Subscriber;

// Backs up CreateStream.html form submission. Trivial since there's no image uploaded, just a URL
@SuppressWarnings("serial")
public class AnalyzeStreamTrendsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		HashMap<Long, Integer> recentStreamsMap= new HashMap<Long, Integer>();
		Date currentDate = new Date();
		List<StreamViews> allStreamViews = OfyService.ofy().load().type(StreamViews.class).list();
	    
	
		Comparator<StreamViews> comparator = new Comparator<StreamViews>() {
		    public int compare(StreamViews c1, StreamViews c2) {
		        return c1.compareTo(c2); // use your logic
		    }
		};
		Collections.sort(allStreamViews, comparator); 
		
		if(!allStreamViews.isEmpty())
		{
			int i;
			Integer freq ;
			Long sId;
			out.println(allStreamViews.size());
			for(i=0; i<allStreamViews.size(); i++){
				long current = currentDate.getTime();
				long past = ((allStreamViews.get(i)).getviewDate()).getTime();
				out.println("current time"+ current);
				out.println("pastime"+ past);
				long MilliSec = current - past;
		//		long MilliSec = currentDate.getTime() - ((allStreamViews.get(i)).getviewDate()).getTime();
				out.println("diff" +MilliSec);
				if (MilliSec > 3600000)
				break;
				else{
					sId = (allStreamViews.get(i)).getStreamId();
					freq = recentStreamsMap.get(sId);
					freq = (freq == null) ? 1 : freq + 1;					
				    recentStreamsMap.put(sId,freq) ;
				}
			}
		}
		if (recentStreamsMap.size() > 2) {
		 HashMap<Long, Integer> sortedMap = sortByComparator(recentStreamsMap, false);
		 Iterator it = sortedMap.entrySet().iterator();
		 int count = 0;
	     Long Stream1,Stream2, Stream3;
	     Integer count1,count2,count3;
	     List<Long> topstreams = new ArrayList<Long>();
	     List<Integer> viewcounts = new ArrayList<Integer>();
	     while (it.hasNext() && count <=2){
	            Map.Entry pairs = (Map.Entry)it.next();
	            topstreams.add((Long) pairs.getKey());
	            viewcounts.add(Integer.parseInt(pairs.getValue().toString()));
	            count++;
	      }
	     Stream1 = topstreams.get(0);
	     Stream2 = topstreams.get(1);
	     Stream3 = topstreams.get(2);
	     count1 = viewcounts.get(0);
	     count2 = viewcounts.get(1);
	     count3 = viewcounts.get(2);
	       LeaderBoard LB = new LeaderBoard(Stream1, count1, Stream2, count2, Stream3, count3);
	       OfyService.ofy().save().entity(LB).now();
		}
	}
	
    private static HashMap<Long, Integer> sortByComparator(Map<Long, Integer> unsortMap, final boolean order)
    {

        List<Entry<Long, Integer>> list = new LinkedList<Entry<Long, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Long, Integer>>()
        {
            public int compare(Entry<Long, Integer> o1,
                    Entry<Long, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<Long, Integer> sortedMap = new LinkedHashMap<Long, Integer>();
        for (Entry<Long, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
