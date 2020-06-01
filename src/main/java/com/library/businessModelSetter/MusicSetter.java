package com.library.businessModelSetter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.Music;

public class MusicSetter implements IMusicSetter {

	@Override
	public List<Music> mapMusic(ResultSet resultSet) {
		List<Music> musics = new ArrayList<Music>();
		try {
			while (resultSet.next()) {
				Music music = new Music();
				music.setTitle(resultSet.getString("Title"));
				music.setCategory(resultSet.getString("Category"));
				music.setRecordLabel(resultSet.getString("Record_Label"));
				music.setArtist(resultSet.getString("Artist"));
				music.setAvailability(resultSet.getInt("Availability"));
				music.setItemID(resultSet.getInt("Item_ID"));
				musics.add(music);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return musics;
	}
}
