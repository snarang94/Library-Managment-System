package com.library.routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.additem.AddItemMessagesEnum;
import com.library.additem.IAddBookController;
import com.library.additem.IAddMovieController;
import com.library.additem.IAddMusicController;
import com.library.borrowItem.BookItem;
import com.library.borrowItem.ItemStatus;
import com.library.browsePage.DisplayObjectInitializer;
import com.library.browsePage.IBrowseDisplayObjects;
import com.library.businessModels.Book;
import com.library.businessModels.Cover;
import com.library.businessModels.Display;
import com.library.businessModels.DisplayDetailed;
import com.library.businessModels.LibraryItem;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;
import com.library.businessModels.User;
import com.library.businessModels.UserItem;
import com.library.dbConnection.DatabaseConnection;
import com.library.forgotPassword.ForgotPasswordController;
import com.library.forgotPassword.IForgotPasswordController;
import com.library.forgotPassword.RecoverPassword;
import com.library.itemDetailed.DetailedDisplayFetcher;
import com.library.itemDetailed.IDetailedDisplayFetcher;
import com.library.loanmanagement.ILoanManagementController;
import com.library.loanmanagement.Select;
import com.library.messages.Messages;
import com.library.parsers.JsonStringParser;
import com.library.search.BookSearch;
import com.library.search.IDBSearchController;
import com.library.search.MovieSearch;
import com.library.search.MusicSearch;
import com.library.search.SearchFactory;
import com.library.search.SearchRequest;
import com.library.search.SearchResults;
import com.library.search.SearchTermsAndPage;
import com.library.signIn.AuthenticatedUsers;
import com.library.signIn.ISignInController;
import com.library.signIn.SignInController;
import com.library.signUp.ISignUpController;
import com.library.signUp.SignUpController;
import com.library.welcomePage.IWelcomeController;
import com.library.welcomePage.UserSessionDetail;
import com.library.welcomePage.WelcomePageController;

@Controller
public class LibraryRoutes implements WebMvcConfigurer {
	private List<Map.Entry<String, String>> list = null;
	@Inject
	private IDBSearchController dbSearchController;
	private SearchFactory searchFactory = null;
	private static String securityQuestionValue;
	private AddItemMessagesEnum addItemMessages;
	private String displayMessage, redirectPage;
	private ILibraryFactory factory = null;
	private LibraryFactorySingleton libraryInstance = null;
	private String redirectToWelcome = Messages.WelcomePageRedirect.getMessage();
	private String redirectToSignIn = Messages.SignInPageRedirect.getMessage();
	private String redirectToForgotPwd = Messages.ForgotPassPageRedirect.getMessage();
	private String redirectToErrorPage = Messages.ErrorPageRedirect.getMessage();
	private String gotoSignInPage = Messages.SignInForm.getMessage();
	private String gotoSignUpPage = Messages.SignUpForm.getMessage();
	private String gotoWelcomePage = Messages.Welcome.getMessage();
	private String gotoForgotPwdPage = Messages.ForgotPassword.getMessage();
	private String gotoErrorPwdPage = Messages.ErrorPage.getMessage();
	private String gotoBrowsePageCategoriesPage = Messages.BrowsePageCategory.getMessage();
	private String gotoBrowsePageItemsPage = Messages.BrowsePageItems.getMessage();
	private String gotoItemDetailsPage = Messages.ItemDetail.getMessage();
	private String redirectToItemDetail = Messages.ItemDetailRedirect.getMessage();

	public LibraryRoutes() {
		libraryInstance = LibraryFactorySingleton.instance();
		factory = libraryInstance.getFactory();
		searchFactory = SearchFactory.instance();
	}

	@GetMapping("/")
	public String getIndexPage() {
		return redirectToWelcome;
	}

	@PostMapping("/signUp")
	public String processSignUpForm(ModelMap model, User user, HttpSession httpSession,
			RedirectAttributes redirectAttr) {
		Logger logger = LogManager.getLogger(SignUpController.class);
		try {
			ISignUpController signUpCreate = factory.signUp(user, httpSession);
			list = signUpCreate.validateSignUp();
			for (int i = 0; i < list.size(); i++) {
				model.addAttribute(list.get(i).getKey(), list.get(i).getValue());
			}
			// model object has by default two indexes; anytime it gets more than that
			// signifies a validation violation
			if (model.size() > 2) {
				return gotoSignUpPage;
			} else {
				return redirectToWelcome;
			}
		} catch (Exception e) {
			logger.log(Level.ALL, Messages.SignUpErrorStatement.getMessage(), e);
			redirectAttr.addAttribute("error", e);
			return redirectToErrorPage;
		}
	}

	@GetMapping("/signUp")
	public String getSignUpForm(User user) {
		return gotoSignUpPage;
	}

	@GetMapping("/advancedSearch")
	public String getAdvancedSearchPage(HttpSession httpSession, ModelMap model) {
		if (AuthenticatedUsers.instance().userIsAuthenticated(httpSession)) {
			dbSearchController.clearSearch(httpSession);			
			model.addAttribute("searchTermsAndPage", searchFactory.makeSearchTermsAndPage());
			model.addAttribute("bookSearch", searchFactory.makeBookSearch());
			model.addAttribute("musicSearch", searchFactory.makeMusicSearch());
			model.addAttribute("moviesSearch", searchFactory.makeMovieSearech());
			addEmailAndLoginLogout(model);	
			return "AdvancedSearchPage";
		}
		return redirectToWelcome;
	}

	@PostMapping("/advancedSearch")
	public String executeAdvancedSearch(HttpSession httpSession, ModelMap model, SearchTermsAndPage termsAndPage,
			BookSearch bookSearch, MusicSearch musicSearch, MovieSearch moviesSearch) {
		if (AuthenticatedUsers.instance().userIsAuthenticated(httpSession)) {
			SearchResults searchResults = executeSearch(httpSession, termsAndPage, bookSearch, musicSearch,
					moviesSearch);		
			model.addAttribute("searchResults", searchResults);
			model.addAttribute("userEmail", AuthenticatedUsers.instance().getUserEmail(httpSession));
			addEmailAndLoginLogout(model);	
			return "AdvancedSearchResultsPage";
		}
		return redirectToWelcome;
	}

	@PostMapping("/basicSearch")
	public String executeBasicSearch(HttpSession httpSession, ModelMap model, SearchTermsAndPage termsAndPage,
			BookSearch bookSearch, MusicSearch musicSearch, MovieSearch moviesSearch) {
		SearchResults searchResults = executeSearch(httpSession, termsAndPage, bookSearch, musicSearch, moviesSearch);
		model.addAttribute("searchResults", searchResults);
		addEmailAndLoginLogout(model);
		return "BasicSearchResultsPage";
	}

	private SearchResults executeSearch(HttpSession httpSession, SearchTermsAndPage termsAndPage, BookSearch bookSearch,
			MusicSearch musicSearch, MovieSearch moviesSearch) {
		SearchRequest sr = new SearchRequest();
		sr.setTermsAndPage(termsAndPage);
		sr.addCategoryToSearchIn(bookSearch);
		sr.addCategoryToSearchIn(musicSearch);
		sr.addCategoryToSearchIn(moviesSearch);
		return dbSearchController.search(sr, httpSession);
	}

	private void addEmailAndLoginLogout(ModelMap model) {
		String loggingStatus = UserSessionDetail.getClientActiveStatus();
		String sessionClient = UserSessionDetail.getAvailableUserID();
		model.addAttribute("loggingStatus", loggingStatus);
		model.addAttribute("sessionClient", sessionClient);
	}

	@GetMapping("/signIn")
	public String getSignInForm(User user) {
		return gotoSignInPage;
	}
	
	@PostMapping("/signIn")
	public String processSignInForm(HttpSession httpSession, ModelMap model, User user,
			RedirectAttributes redirectAttr) {
		Logger logger = LogManager.getLogger(SignInController.class);
		try {
			ISignInController signIn = factory.signIn(user, httpSession);
			list = signIn.validateSignIn();
			for (int index = 0; index < list.size(); index++) {
				model.addAttribute(list.get(index).getKey(), list.get(index).getValue());
			}
			// ModelMap by default has two values and anytime it gets more than that
			// signifies validation violation
			if (model.size() > 2) {
				return gotoSignInPage;
			}
			return signIn.checkUserCredential();
		} catch (Exception e) {
			logger.log(Level.ALL, Messages.SignInErrorStatement.getMessage(), e);
			redirectAttr.addAttribute("error", e);
			return redirectToErrorPage;
		}
	}

	@GetMapping("/addBook")
	public String mappingsForAddItem(ModelMap model) {

		String sessionClient = UserSessionDetail.getAvailableUserID();
		List<String> bookCategories = new ArrayList<String>();
		List<String> movieCategories = new ArrayList<String>();
		List<String> musicCategories = new ArrayList<String>();
		IAddBookController iAddBookController = factory.makeAddBookController();
		IAddMovieController iAddMovieController = factory.makeAddMovieController();
		IAddMusicController iAddMusicController = factory.makeAddMusicController();
		bookCategories = iAddBookController.getBookCategories();
		movieCategories = iAddMovieController.getMovieCategories();
		musicCategories = iAddMusicController.getMusicCategories();

		model.addAttribute("book", new Book());
		model.addAttribute("movie", new Movie());
		model.addAttribute("music", new Music());
		model.addAttribute("coverBook", new Cover());
		model.addAttribute("coverMovie", new Cover());
		model.addAttribute("coverMusic", new Cover());
		model.addAttribute("sessionClient", sessionClient);
		model.addAttribute("bookCategories", bookCategories);
		model.addAttribute("movieCategories", movieCategories);
		model.addAttribute("musicCategories", musicCategories);

		return "AddItemPage";
	}

	@GetMapping("/addMovie")
	public String mappingsForAddMovie(ModelMap model) {

		return mappingsForAddItem(model);
	}

	@GetMapping("/addMusic")
	public String mappingsForAddMusic(ModelMap model) {
		return mappingsForAddItem(model);
	}

	@PostMapping("/addBook")
	public String addBookToDatabase(ModelMap model, Book book, Cover coverBook) {

		IAddBookController iAddBookController = factory.makeAddBookController();
		addItemMessages = iAddBookController.addBookRecordInDatabase(book, coverBook.getCoverImage());
		displayMessage = addItemMessages.getMessage();
		model.addAttribute("message", displayMessage);
		redirectPage = mappingsForAddItem(model);
		return redirectPage;
	}

	@PostMapping("/addMovie")
	public String addMovieToDatabase(ModelMap model, Movie movie, Cover coverMovie) {

		IAddMovieController iAddMovieController = factory.makeAddMovieController();
		addItemMessages = iAddMovieController.addMovieRecordInDatabase(movie, coverMovie.getCoverImage());
		displayMessage = addItemMessages.getMessage();
		model.addAttribute("message", displayMessage);
		redirectPage = mappingsForAddItem(model);
		return redirectPage;

	}

	@PostMapping("/addMusic")
	public String addMusicToDatabase(ModelMap model, Music music, Cover coverMusic) {

		IAddMusicController iAddMusicController = factory.makeAddMusicController();
		addItemMessages = iAddMusicController.addMusicRecordInDatabase(music, coverMusic.getCoverImage());
		displayMessage = addItemMessages.getMessage();
		model.addAttribute("message", displayMessage);
		redirectPage = mappingsForAddItem(model);
		return redirectPage;
	}

	@GetMapping("/loan")
	public String mappingsForLoanManagement(ModelMap model) {

		String sessionClient = UserSessionDetail.getAvailableUserID();
		model.addAttribute("item", new UserItem());
		ILoanManagementController iLoanManagementController = factory.makeLoanManagementController();
		List<UserItem> items = iLoanManagementController.getAllBorrowedItems();
		model.addAttribute("items", items);
		model.addAttribute("select", new Select());
		model.addAttribute("sessionClient", sessionClient);
		return "LoanManagement";
	}

	@PostMapping("/loan")
	public String returnItems(ModelMap model, Select select) {

		String selections = select.getSelections();
		JsonStringParser jsonStringParser = new JsonStringParser();
		List<UserItem> userItems = new ArrayList<UserItem>();
		userItems = jsonStringParser.parseSelections(selections);
		ILoanManagementController iLoanManagementController = factory.makeLoanManagementController();
		iLoanManagementController.removeUserItems(userItems);
		List<UserItem> items = iLoanManagementController.getAllBorrowedItems();
		model.addAttribute("select", new Select());
		model.addAttribute("items", items);
		return "LoanManagement";
	}

	@GetMapping("/welcome")
	public String welcomeBody(ModelMap model, LibraryItem libraryItem, HttpServletRequest request,
			HttpSession httpSession, RedirectAttributes redirectAttr) {
		List<Book> book, favBooks;
		List<Movie> movie, favMovies;
		List<Music> music, favMusic;
		Logger logger = LogManager.getLogger(WelcomePageController.class);
		dbSearchController.clearSearch(httpSession);
		String loggingStatus = UserSessionDetail.getClientActiveStatus();
		String sessionClient = UserSessionDetail.getAvailableUserID();
		model.addAttribute("searchTermsAndPage", searchFactory.makeSearchTermsAndPage());
		IWelcomeController welcomeCtrl = factory.welcomePage();
		
		if (welcomeCtrl.getValFromRequestParam(request)) {
			loggingStatus = Messages.RegisterLogin.getMessage();
			sessionClient = "";
		}
		try {
			book = welcomeCtrl.getBookItems();
			movie = welcomeCtrl.getMovieItems();
			music = welcomeCtrl.getMusicItems();
			favBooks = welcomeCtrl.getFavouriteBooks();
			favMovies = welcomeCtrl.getFavouriteMovies();
			favMusic = welcomeCtrl.getFavouriteMusic();
		} catch (SQLException e) {
			logger.log(Level.ALL, Messages.WelcomeSQlErrorStatement.getMessage(), e);
			redirectAttr.addAttribute("error", e);
			return redirectToErrorPage;
		} catch (Exception e) {
			logger.log(Level.ALL, Messages.WelcomeErrorStatement.getMessage(), e);
			redirectAttr.addAttribute("error", e);
			return redirectToErrorPage;
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		
		model.addAttribute("book", book);
		model.addAttribute("favBooks", favBooks);
		model.addAttribute("movie", movie);
		model.addAttribute("favMovies", favMovies);
		model.addAttribute("music", music);
		model.addAttribute("favMusic", favMusic);
		model.addAttribute("isAdminAval", welcomeCtrl.isAdminAvailable());
		model.addAttribute("loggingStatus", loggingStatus);
		model.addAttribute("sessionClient", sessionClient);
		return gotoWelcomePage;
	}

	@GetMapping("/ErrorPage")
	public String errorPage() {
		return gotoErrorPwdPage;
	}

	@GetMapping("/logOut")
	public String processLogOut(HttpSession httpSession, ModelMap model, RedirectAttributes redirectAttributes) {
		if (AuthenticatedUsers.instance().userIsAuthenticated(httpSession)) {
			AuthenticatedUsers.instance().removeAuthenticatedUser(httpSession);
			redirectAttributes.addAttribute("LoggedOut", true);
			UserSessionDetail.setAvailableAdmin(false);
			UserSessionDetail.setAvailableUserID("");
			UserSessionDetail.setClientActiveStatus(Messages.RegisterLogin.getMessage());
		}
		return redirectToWelcome;
	}

	@GetMapping(value = "/forgotPassword")
	public String getForgotPasswordForm(ModelMap model, RecoverPassword recoverPassword) {
		Logger logger = LogManager.getLogger(ForgotPasswordController.class);
		try {
			IForgotPasswordController fPwdCntrl = factory.forgotPassword(recoverPassword);
			securityQuestionValue = fPwdCntrl.setQuestion();
			model.addAttribute("securityQuestion", securityQuestionValue);
		} catch (Exception e) {
			logger.log(Level.ALL, Messages.ForgotPwdErrorStatement.getMessage(), e);
		}
		return gotoForgotPwdPage;
	}

	@PostMapping(value = "/forgotPassword")
	public String processForgotPasswordUserForm(RecoverPassword recoverPassword, RedirectAttributes redirectAttr) {
		Logger logger = LogManager.getLogger(ForgotPasswordController.class);
		try {
			recoverPassword.setSecurityQuestion(securityQuestionValue);
			IForgotPasswordController fPwdCntrl = factory.forgotPassword(recoverPassword);
			if (fPwdCntrl.recoverPassword()) {
				return redirectToSignIn;
			} else {
				return redirectToForgotPwd;
			}
		} catch (MessagingException | IOException em) {
			logger.log(Level.ALL, Messages.ForgotPwdEmailErrorStatement.getMessage(), em);
			redirectAttr.addAttribute("error", em);
			return redirectToErrorPage;
		} catch (Exception e) {
			logger.log(Level.ALL, Messages.ForgotPwdErrorStatement.getMessage(), e);
			redirectAttr.addAttribute("error", e);
			return redirectToErrorPage;
		}
	}

	@GetMapping("/BrowsePage/{itemType}")
	public String BrowsePageCategory(@PathVariable String itemType, ModelMap model) {
		DisplayObjectInitializer displayObjectInitializer = new DisplayObjectInitializer();
		IBrowseDisplayObjects browseDisplayObjects = null;
		List<String> categories;
		browseDisplayObjects = displayObjectInitializer.getDisplayObject(itemType);
		categories = browseDisplayObjects.getCategories();
		addEmailAndLoginLogout(model);
		model.addAttribute("categories", categories);
		model.addAttribute("itemType", itemType);
		return gotoBrowsePageCategoriesPage;
	}

	@GetMapping("/BrowsePage/{itemType}/{category}")
	public String BrowsePageItems(@PathVariable(value = "itemType") String itemType,
			@PathVariable(value = "category") String category, ModelMap model) {

		DisplayObjectInitializer displayObjectInitializer = new DisplayObjectInitializer();
		IBrowseDisplayObjects browseDisplayObjects = null;
		List<Display> displayItems;
		browseDisplayObjects = displayObjectInitializer.getDisplayObject(itemType);
		displayItems = browseDisplayObjects.itemsByCategory(category);
		addEmailAndLoginLogout(model);
		model.addAttribute("displayItems", displayItems);
		model.addAttribute(itemType);
		model.addAttribute(category);
		return gotoBrowsePageItemsPage;
	}

	@GetMapping("/itemDetail/{itemType}/{itemID}")
	public String itemDetailed(@PathVariable(value = "itemType") String itemType,
			@PathVariable(value = "itemID") int itemID, ModelMap model, HttpSession httpSession) {
		IDetailedDisplayFetcher displayFetcher = new DetailedDisplayFetcher();
		DisplayDetailed displayDetailed = displayFetcher.fetchDetailedDisplay(itemType, itemID);
		AuthenticatedUsers user = AuthenticatedUsers.instance();
		String emailAddress = user.getUserEmail(httpSession);
		ItemStatus statusFetcher = new ItemStatus(displayDetailed, emailAddress);
		String status = statusFetcher.getItemStatus();
		addEmailAndLoginLogout(model);
		model.addAttribute("status", status);
		model.addAttribute("displayDetailed", displayDetailed);
		return gotoItemDetailsPage;
	}

	@PostMapping("/borrowItem/{status}")
	public String borrowItems(@PathVariable(value = "status") String status, HttpSession httpSession, ModelMap model,
			DisplayDetailed displayDetailed) {
		AuthenticatedUsers user = AuthenticatedUsers.instance();
		String emailAddress = user.getUserEmail(httpSession);
		BookItem bookItem = new BookItem(displayDetailed, emailAddress);
		Boolean isItemBooked = bookItem.bookItem(status);
		if (!isItemBooked) {
			return redirectToErrorPage;
		}
		String itemType = displayDetailed.getItemType();
		int itemID = displayDetailed.getItemID();
		return redirectToItemDetail + itemType + "/" + itemID;
	}
}
