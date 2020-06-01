package com.library.businessModelSetter;

import java.sql.ResultSet;
import java.util.List;

import com.library.businessModels.Movie;

public interface IMovieSetter {
	public List<Movie> mapMovie(ResultSet resultSet);
}
