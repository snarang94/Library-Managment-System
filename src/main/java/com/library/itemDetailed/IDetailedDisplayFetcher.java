package com.library.itemDetailed;

import com.library.businessModels.DisplayDetailed;

public interface IDetailedDisplayFetcher {
	public DisplayDetailed fetchDetailedDisplay(String itemType, int itemID);
}
