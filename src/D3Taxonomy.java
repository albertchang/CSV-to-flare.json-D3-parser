import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

public class D3Taxonomy {
	
	public static void main(String[] args) {
		try {
			//proper usage pattern: java <filename> <delimiter>
			String inputFile = args[0];
			String delimiter = args[1];
			
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String catName;
			
			//main data structure for storage
			Map<String, Object> flareMap = new LinkedHashMap<String, Object>();
			flareMap.put("name", "flare");
			flareMap.put("children", new ArrayList<Object>());
			
			String[] curString;
			Map<String, Object> curMap, tempMap;

			while ((catName = br.readLine()) != null) {
				catName = catName.trim();
				//ignore header lines in file
				if (catName.startsWith("#"))
					continue;

				curString = catName.split(delimiter);

				//if the category is not found in map, create a new path
				if ((tempMap = findName(flareMap, curString[0])) == null) {
					int i = 0;
					tempMap = flareMap;
					//create nodes for each subcategory in string
					while (i < curString.length)
						tempMap = createNode(tempMap, curString[i++]);
				}
				else {
					//otherwise, it will be an existing category name in path,
					//iterate until last location and create nodes for subcategories
					int i = 0;
					tempMap = flareMap;
					while (i < curString.length && 
							(curMap = findName(tempMap, curString[i])) != null){
						i++;
						tempMap = curMap;
					}
					while (i < curString.length)
						tempMap = createNode(tempMap, curString[i++]);
				}
			}
			
			br.close();
			String jsonString = JSONValue.toJSONString(flareMap);
			
			//default output filename: flare.json -- modify if necessary
			FileWriter writer = new FileWriter("flare.json");
			writer.write(jsonString);
			writer.close();
			
			System.out.println(jsonString);
		} 
		catch (Exception e) {
			System.out.println("Error! Correct usage: <filename> <delimiter>");
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new node for a category not already in the map. 
	 *
	 * @param  current 	the current map to add to
	 * @param  in 		the new category name to be added
	 * @return newmap	the new node created
	 */
	public static Map<String, Object> createNode (Map<String, Object> current, 
			String in) {
		Map<String, Object> newMap = new LinkedHashMap<String, Object>();
		newMap.put("name", in);

		if (current.containsKey("children"))
			((List<Map<String, Object>>) current.get("children")).add(newMap);
		else {
			//add new subcategory if it does not already exist
			current.put("children", new ArrayList<Object>());
			((List<Map<String, Object>>) current.get("children")).add(newMap);
		}

		return newMap;
	}
	
	/**
	 * Sequentially searches through the current map for input string. 
	 *
	 * @param  current 	the current map to search through
	 * @param  in 		the search string
	 * @return 			the node requested if found; null otherwise
	 */
	public static Map<String, Object> findName (Map<String, Object> current, 
			String in) {
		if (current.containsKey("children")) {
			List<Object> temp = ((List<Object>) current.get("children"));

			for (int i = 0; i < temp.size(); i++) {
				if (((Map<Object, Object>) temp.get(i)).get("name").equals(in))
					return ((Map<String, Object>) temp.get(i));
			}
		}
		return null;
	}
}