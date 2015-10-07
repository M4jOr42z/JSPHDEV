/**
 * @author zhexinq
 * Interface provides contracts for updating Auto object's OptionSet name,
 * and Option price.
 */

package adapter;

public interface UpdateAuto {
	public void updateOptionSetName(String modelname, String setName, String newName);
	public void updateOptionPrice(String modelname, String setName, String optionName, int newprice);
	public void makeOptionChoice(String modelname, String setName, String optName);
}
