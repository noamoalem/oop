/*
* you are welcome to change, fix and update the tester as you see fit, just document the changes:
* 6.5 22:00 - Asa Front - create.
*
* */

public class testOpen {

	public static void main(String[] args) {
		float upperCapacityForGiven = 0.8f;
		float lowerCapacityForGiven = 0.4f;
		String elementToAdd = "13";
		//it is possible to change the stringArray but don't put the value of elementToAdd.
		String[] stringArray = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		OpenHashSet openArrayGiven = new OpenHashSet(stringArray);
		OpenHashSet openValueGiven = new OpenHashSet(upperCapacityForGiven,lowerCapacityForGiven);
		OpenHashSet openDefault = new OpenHashSet();

		/* -- Check initial size --*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check initial size -- ");
		System.out.println("");
		System.out.println("1. "+(openArrayGiven.size() == stringArray.length));
		System.out.println("2. "+(openValueGiven.size() == 0));
		System.out.println("3. "+(openDefault.size() == 0));

		/* -- Check initial Capacity -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check initial Capacity -- ");
		System.out.println("");
		System.out.println("4. "+(openArrayGiven.capacity() == 16));
		System.out.println("5. "+(openValueGiven.capacity() == 16));
		System.out.println("6. "+(openDefault.capacity() == 16));

		/* -- Check initial load factors -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check initial load factors -- ");
		System.out.println("");
		System.out.println("7. "+(openArrayGiven.getLowerLoadFactor() == 0.25));
		System.out.println("8. "+(openArrayGiven.getUpperLoadFactor() == 0.75));
		System.out.println("9. "+(openValueGiven.getLowerLoadFactor() == lowerCapacityForGiven));
		System.out.println("10. "+(openValueGiven.getUpperLoadFactor() == upperCapacityForGiven));
		System.out.println("11. "+(openDefault.getLowerLoadFactor() == 0.25));
		System.out.println("12. "+(openDefault.getUpperLoadFactor() == 0.75));


		/* -- Add One --*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Add One -- ");
		System.out.println("");
		System.out.println("13. "+(openArrayGiven.add(elementToAdd)));
		System.out.println("14. "+(openValueGiven.add(elementToAdd)));
		System.out.println("15. "+(openDefault.add(elementToAdd)));

		/* -- check size increase --*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check size increase -- ");
		System.out.println("");
		System.out.println("16. "+(openArrayGiven.size() == stringArray.length+1));
		System.out.println("17. "+(openValueGiven.size() == 1));
		System.out.println("18. "+(openDefault.size() == 1));

		/* -- Capacity check increase -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Capacity check increase -- ");
		System.out.println("");
		System.out.println("19. "+(openArrayGiven.capacity() == 32));
		System.out.println("20. "+(openValueGiven.capacity() == 16));
		System.out.println("21. "+(openDefault.capacity() == 16));

		/* -- add one that already exist -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- add one that already exist -- ");
		System.out.println("");
		System.out.println("22. "+(!openArrayGiven.add(elementToAdd)));
		System.out.println("23. "+(!openValueGiven.add(elementToAdd)));
		System.out.println("24. "+(!openDefault.add(elementToAdd)));

		/* -- delete one -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- delete one -- ");
		System.out.println("");
		System.out.println("25. "+(openArrayGiven.delete(elementToAdd)));
		System.out.println("26. "+(openValueGiven.delete(elementToAdd)));
		System.out.println("27. "+(openDefault.delete(elementToAdd)));

		/* -- check size reduce--*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check size reduce -- ");
		System.out.println("");
		System.out.println("28. "+(openArrayGiven.size() == stringArray.length));
		System.out.println("29. "+(openValueGiven.size() == 0));
		System.out.println("30. "+(openDefault.size() == 0));

		/* -- Check capacity reduce -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check capacity reduce -- ");
		System.out.println("");
		System.out.println("31. "+(openArrayGiven.capacity() == 32));
		System.out.println("32. "+(openValueGiven.capacity() == 8));
		System.out.println("33. "+(openDefault.capacity() == 8));

		/* -- delete one that doesn't exist -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- delete one that doesn't exist -- ");
		System.out.println("");
		System.out.println("34. "+(!openArrayGiven.delete(elementToAdd)));
		System.out.println("35. "+(!openValueGiven.delete(elementToAdd)));
		System.out.println("36. "+(!openDefault.delete(elementToAdd)));

		/* -- Creating an array with more elements then initial capacity -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Creating an array with more elements then initial capacity -- ");
		System.out.println("");
		String[] stringArrayLong = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
				"17","18","19","20"};
		OpenHashSet longSet = new OpenHashSet(stringArrayLong);
		System.out.println("37. "+(longSet.size() == stringArrayLong.length));

		/* -- check if capacity can get down to 0 (it shouldn't go below 1)-- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check if capacity can get down to 0 (it shouldn't go below 1) -- ");
		System.out.println("");
		OpenHashSet openValueGivenForZero = new OpenHashSet(1,1);
		while(openValueGivenForZero.capacity()>2) {
			openValueGivenForZero.add(elementToAdd+elementToAdd);
			openValueGivenForZero.add(elementToAdd);
			openValueGivenForZero.delete(elementToAdd+elementToAdd);
		}
		openValueGivenForZero.delete(elementToAdd);
		openValueGivenForZero.add(elementToAdd);
		openValueGivenForZero.delete(elementToAdd);
		if(openValueGivenForZero.capacity() == 1){
			System.out.println("38. true");
		}else{
			System.out.println("38. false:");
			System.out.println("	Size:" + openValueGivenForZero.size() + " <-- even if this is 0");
			System.out.println("	Capacity: " + openValueGivenForZero.capacity() + " <-- this should be 1");
		}
	}
}
