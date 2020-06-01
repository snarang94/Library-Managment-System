package com.library.businessModelSetter;

import java.sql.ResultSet;
import com.library.businessModels.Cover;

public interface ICoverMapper {
	public Cover setCover(ResultSet resultSet);
}
