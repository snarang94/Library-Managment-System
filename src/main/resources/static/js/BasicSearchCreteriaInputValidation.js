function validateSearchForm() {
	if(document.getElementById("searchTerms").value == "") {
	  alert("Search terms field cannot be empty");
	  return false;
	}
	return true;
}
