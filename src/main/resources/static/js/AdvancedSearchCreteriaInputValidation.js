function validateSearchForm() {
	if(document.getElementById("searchTerms").value == "") {
	  alert("Search terms field cannot be empty");
	  return false;
	}
	var atLeastOneContentTypeIsSelected;
	
	atLeastOneContentTypeIsSelected = (document.getElementById("searchInBooks").checked == true  || 
			document.getElementById("searchInMovies").checked == true  ||
			document.getElementById("searchInMusic").checked == true );
	if(!atLeastOneContentTypeIsSelected) {
		alert("Select at least one content type to search");
		return false;		  
	}
	
	var atLeastOneFiledIsSelectedForBooks = document.getElementById("bookTitle").checked == true ||
	document.getElementById("bookAuthor").checked == true  ||
	document.getElementById("bookCategory").checked == true  ||
	document.getElementById("bookPublisher").checked == true  ||
	document.getElementById("bookDescription").checked == true  ||
	document.getElementById("bookISBN").checked == true ;
	var atLeastOneFiledIsSelectedForMusic = document.getElementById("musicAlbumName").checked == true  ||
	document.getElementById("musicArtist").checked == true  ||
	document.getElementById("musicRecordLabel").checked == true ;
	var atLeastOneFiledIsSelectedForMovies = document.getElementById("movieTitle").checked == true  ||
	document.getElementById("movieDirector").checked == true  ||
	document.getElementById("movieDescription").checked == true ;
	
	var oneOfFieldsNotSelectedWhereasShouldBe = ((document.getElementById("searchInBooks").checked == true)  && !atLeastOneFiledIsSelectedForBooks) ||
												((document.getElementById("searchInMusic").checked == true)  && !atLeastOneFiledIsSelectedForMusic) ||
												((document.getElementById("searchInMovies").checked == true)  && !atLeastOneFiledIsSelectedForMovies);
	if(oneOfFieldsNotSelectedWhereasShouldBe) {
		alert("Select at least one field for each content type selected above");
		return false;		  
	}
	
	return true;
}
