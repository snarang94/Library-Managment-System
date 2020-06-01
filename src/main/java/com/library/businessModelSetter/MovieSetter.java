package com.library.businessModelSetter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.Movie;

public class MovieSetter implements IMovieSetter {

	@Override
	public List<Movie> mapMovie(ResultSet resultSet) {

		List<Movie> movies = new ArrayList<Movie>();
		try {
			while (resultSet.next()) {
				Movie movie = new Movie();
				movie.setCategory(resultSet.getString("Category"));
				movie.setDescription(resultSet.getString("Description"));
				movie.setDirector(resultSet.getString("Director"));
				movie.setAvailability(resultSet.getInt("Availability"));
				movie.setTitle(resultSet.getString("Title"));
				movie.setItemID(resultSet.getInt("Item_ID"));
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}

}
