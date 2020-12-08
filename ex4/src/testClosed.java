/*
 * you are welcome to change, fix and update the tester as you see fit, just document the changes:
 * 6.5 22:00 - Asa Front - create.
 *
 * */

public class testClosed {

	public static void main(String[] args) {
		float upperCapacityForGiven = 0.8f;
		float lowerCapacityForGiven = 0.4f;
		String elementToAdd = "13";
		//it is possible to change the stringArray but don't put the value of elementToAdd.
		String[] stringArray = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		ClosedHashSet ClosedArrayGiven = new ClosedHashSet(stringArray);
		ClosedHashSet ClosedValueGiven = new ClosedHashSet(upperCapacityForGiven,lowerCapacityForGiven);
		ClosedHashSet ClosedDefault = new ClosedHashSet();

		/* -- Check initial size --*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check initial size -- ");
		System.out.println("");
		System.out.println("1. "+(ClosedArrayGiven.size() == stringArray.length));
		System.out.println("2. "+(ClosedValueGiven.size() == 0));
		System.out.println("3. "+(ClosedDefault.size() == 0));

		/* -- Check initial Capacity -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check initial Capacity -- ");
		System.out.println("");
		System.out.println("4. "+(ClosedArrayGiven.capacity() == 16));
		System.out.println("5. "+(ClosedValueGiven.capacity() == 16));
		System.out.println("6. "+(ClosedDefault.capacity() == 16));

		/* -- Check initial load factors -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check initial load factors -- ");
		System.out.println("");
		System.out.println("7. "+(ClosedArrayGiven.getLowerLoadFactor() == 0.25));
		System.out.println("8. "+(ClosedArrayGiven.getUpperLoadFactor() == 0.75));
		float mistake = ClosedValueGiven.getLowerLoadFactor();
		System.out.println(mistake);
		System.out.println("9. "+(mistake == lowerCapacityForGiven));
		System.out.println("10. "+(ClosedValueGiven.getUpperLoadFactor() == upperCapacityForGiven));
		System.out.println("11. "+(ClosedDefault.getLowerLoadFactor() == 0.25));
		System.out.println("12. "+(ClosedDefault.getUpperLoadFactor() == 0.75));


		/* -- Add One --*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Add One -- ");
		System.out.println("");
		System.out.println("13. "+(ClosedArrayGiven.add(elementToAdd)));
		System.out.println("14. "+(ClosedValueGiven.add(elementToAdd)));
		System.out.println("15. "+(ClosedDefault.add(elementToAdd)));

		/* -- check size increase --*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check size increase -- ");
		System.out.println("");
		System.out.println("16. "+(ClosedArrayGiven.size() == stringArray.length+1));
		System.out.println("17. "+(ClosedValueGiven.size() == 1));
		System.out.println("18. "+(ClosedDefault.size() == 1));

		/* -- Capacity check increase -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Capacity check increase -- ");
		System.out.println("");
		System.out.println("19. "+(ClosedArrayGiven.capacity() == 32));
		System.out.println("20. "+(ClosedValueGiven.capacity() == 16));
		System.out.println("21. "+(ClosedDefault.capacity() == 16));

		/* -- add one that already exist -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- add one that already exist -- ");
		System.out.println("");
		System.out.println("22. "+(!ClosedArrayGiven.add(elementToAdd)));
		System.out.println("23. "+(!ClosedValueGiven.add(elementToAdd)));
		System.out.println("24. "+(!ClosedDefault.add(elementToAdd)));

		/* -- delete one -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- delete one -- ");
		System.out.println("");
		System.out.println("25. "+(ClosedArrayGiven.delete(elementToAdd)));
		System.out.println("26. "+(ClosedValueGiven.delete(elementToAdd)));
		System.out.println("27. "+(ClosedDefault.delete(elementToAdd)));

		/* -- check size reduce--*/
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check size reduce -- ");
		System.out.println("");
		System.out.println("28. "+(ClosedArrayGiven.size() == stringArray.length));
		System.out.println("29. "+(ClosedValueGiven.size() == 0));
		System.out.println("30. "+(ClosedDefault.size() == 0));

		/* -- Check capacity reduce -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Check capacity reduce -- ");
		System.out.println("");
		System.out.println("31. "+(ClosedArrayGiven.capacity() == 32));
		System.out.println("32. "+(ClosedValueGiven.capacity() == 8));
		System.out.println("33. "+(ClosedDefault.capacity() == 8));

		/* -- delete one that doesn't exist -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- delete one that doesn't exist -- ");
		System.out.println("");
		System.out.println("34. "+(!ClosedArrayGiven.delete(elementToAdd)));
		System.out.println("35. "+(!ClosedValueGiven.delete(elementToAdd)));
		System.out.println("36. "+(!ClosedDefault.delete(elementToAdd)));

		/* -- Creating an array with more elements then initial capacity -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- Creating an array with more elements then initial capacity -- ");
		System.out.println("");
		String[] stringArrayLong = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
				"17","18","19","20"};
		ClosedHashSet longSet = new ClosedHashSet(stringArrayLong);
		System.out.println("37. "+(longSet.size() == stringArrayLong.length));

		/* -- check if capacity can get down to 0 (it shouldn't go below 1)-- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check if capacity can get down to 0 (it shouldn't go below 1) -- ");
		System.out.println("");
		ClosedHashSet ClosedValueGivenForZero = new ClosedHashSet(1,1);
		while(ClosedValueGivenForZero.capacity()>2) {
			ClosedValueGivenForZero.add(elementToAdd+elementToAdd);
			ClosedValueGivenForZero.delete(elementToAdd+elementToAdd);
		}
		ClosedValueGivenForZero.delete(elementToAdd);
		ClosedValueGivenForZero.add(elementToAdd);
		ClosedValueGivenForZero.delete(elementToAdd);
		if(ClosedValueGivenForZero.capacity() == 1){
			System.out.println("38. true");
		}else{
			System.out.println("38. false:");
			System.out.println("	Size:" + ClosedValueGivenForZero.size() + " <-- even if this is 0");
			System.out.println("	Capacity: " + ClosedValueGivenForZero.capacity() + " <-- this should be 1");
		}

		/* -- check the closed hash when completely full -- */
		System.out.println("");
		System.out.println("");
		System.out.println(" -- check the closed hash when completely full -- ");
		System.out.println("");
		ClosedHashSet fullHouse = new ClosedHashSet(1,1);
		String[] sixteenArray = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
		for(String string:sixteenArray){
			fullHouse.add(string);
		}
		System.out.println("39. size: "+(fullHouse.size() == 16));
		System.out.println("40. capacity: "+(fullHouse.capacity() == 16));
		System.out.println("41. "+(!fullHouse.contains("17")));
		System.out.println("42. "+(!fullHouse.delete("17")));
		System.out.println("43. "+(fullHouse.add("17")));
		System.out.println("44. size after adding: "+(fullHouse.size() == 17));
		System.out.println("45. capacity after adding: "+(fullHouse.capacity() == 32));
		System.out.println("46. "+(fullHouse.delete("17")));
		System.out.println("47. size after deleting: "+(fullHouse.size() == 16));
		System.out.println("48. capacity after deleting: "+(fullHouse.capacity() == 16));

	}
}
