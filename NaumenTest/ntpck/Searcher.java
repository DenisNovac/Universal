package ntpck;

import ntpck.sorter.QuickSort;

public class Searcher implements ISearcher{

	
	@Override
	public void refresh(String[] classNames, long[] modificationDates) {
		QuickSort.sort(classNames, modificationDates);
	}
	
	

	@Override
	public String[] guess(String start) {
		String[] r = new String[12];
		
		
		
		
		
		
		
		return null;
	}

}
