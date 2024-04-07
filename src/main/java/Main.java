import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // Make Output Colorful
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m"; // error
    public static final String ANSI_GREEN = "\u001B[32m"; // successfully
    public static final String ANSI_YELLOW = "\u001B[33m"; // input
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static void main(String[] args) { // main Function
        runMenu();
    }

    public static void runMenu() { // Function for running the program and showing menus
        DataBase hopeReddit = new DataBase();
        Scanner scanner = new Scanner(System.in);
        String userChoiceForFirstMenu;

        while (true) {
            System.out.println(ANSI_PURPLE);
            System.out.println("================= Reddit =================" + ANSI_RESET);
            showFirstMenu("Main Menu");
            userChoiceForFirstMenu = scanner.nextLine();

            switch (userChoiceForFirstMenu) {
                case "1": // user wants to signUp
                    System.out.println(ANSI_CYAN);
                    System.out.println("Lets Create an Account");

                    System.out.println(ANSI_YELLOW);
                    System.out.print("Enter Your username(use 4 digits at least) %$#--->: ");
                    String inputUsername = scanner.nextLine();
                    if(hopeReddit.isUsernameInDataBase(inputUsername)) {
                        System.out.println(ANSI_CYAN);
                        System.out.println("Username Used Before Please Choice another!");
                        break;
                    }
                    boolean itShouldBackToMenuForUsername = false;
                    while (!isUserNameValid(inputUsername)) {
                        displayInvalidOrderError();
                        System.out.println(ANSI_YELLOW);
                        System.out.print("Enter Your username(use 4 digits at least)(0 For Ending Program) %$#--->: ");
                        inputUsername = scanner.nextLine();
                        if(hopeReddit.isUsernameInDataBase(inputUsername)) {
                            System.out.println(ANSI_CYAN);
                            System.out.println("Username Used Before Please Choice another!");
                            itShouldBackToMenuForUsername = true;
                            break;
                        }
                        if (inputUsername.equals("0")) { exit(); }
                    }
                    if (itShouldBackToMenuForUsername) {
                        break;
                    }

                    System.out.println(ANSI_YELLOW);
                    System.out.print("Enter Your password(use 8 digits at least) %$#--->: ");
                    String inputPassword = scanner.nextLine();
                    while (!isPasswordValid(inputPassword)) {
                        displayInvalidOrderError();
                        System.out.println(ANSI_YELLOW);
                        System.out.print("Enter Your password(use 8 digits at least)(0 For Ending Program) %$#--->: ");
                        inputPassword = scanner.nextLine();
                        if (inputPassword.equals("0")) { exit(); }
                    }

                    System.out.println(ANSI_YELLOW);
                    System.out.print("Enter Your Password Again %$#--->: ");
                    String inputPasswordAgain = scanner.nextLine();
                    while (!inputPasswordAgain.equals(inputPassword)) {
                        System.out.println(ANSI_RED);
                        System.out.println("Passwords Should be The Same!!!" + ANSI_YELLOW);
                        System.out.print("Enter Your Password Again(0 For Ending Program) %$#--->: ");
                        inputPasswordAgain = scanner.nextLine();
                        if (inputPasswordAgain.equals("0")) { exit(); }
                    }

                    boolean itShouldBackToMenuForEmail = false;
                    System.out.println();
                    System.out.print("Enter Your Email %$#--->: ");
                    String inputEmail = scanner.nextLine();
                    if(hopeReddit.isEmailInDataBase(inputEmail)) {
                        System.out.println(ANSI_CYAN);
                        System.out.println("Email Used Before Please Choice another!");
                        break;
                    }
                    while (!isEmailValid(inputEmail)) {
                        displayInvalidOrderError();
                        System.out.println(ANSI_YELLOW);
                        System.out.print("Enter Your Email(0 For Ending Program) %$#--->: ");
                        inputEmail = scanner.nextLine();
                        if(hopeReddit.isEmailInDataBase(inputEmail)) {
                            System.out.println(ANSI_CYAN);
                            System.out.println("Email Used Before Please Choice another!");
                            itShouldBackToMenuForEmail = true;
                            break;
                        }
                        if (inputEmail.equals("0")) { exit(); }
                    }
                    if (itShouldBackToMenuForEmail) {
                        break;
                    }

                    System.out.print("Do You Want to Show Your Email in Your Profile: 1) Yes - 2) No %$#--->: ");
                    String inputShowEmailInProfile = scanner.nextLine();
                    boolean shoeEmailInProfile = false;
                    if (inputShowEmailInProfile.equals("1")) { shoeEmailInProfile = true; }

                    Account newAccount = new Account(inputUsername, inputPassword, inputEmail);
                    newAccount.setEmailDisplayOnProfile(shoeEmailInProfile);
                    hopeReddit.addAccount(newAccount);
                    System.out.println(ANSI_GREEN);
                    System.out.println("You Successfully sign up" + ANSI_RESET);
                    runLoginMenu(newAccount, hopeReddit);
                    break;
                case "2": // user want to signIn
                    System.out.println(ANSI_BLUE);
                    System.out.println("Lets Login To Your Account" + ANSI_YELLOW);
                    System.out.print("Enter You username %$#--->: ");
                    String logUsername = scanner.nextLine();
                    boolean isFound = false;
                    boolean isLoggedIn = false;

                    for (Account account : hopeReddit.getAccounts()) {
                        if (account.getUsername().equals(logUsername)) {
                            isFound = true;
                            System.out.println(ANSI_YELLOW);
                            System.out.print("Enter Your Password " + logUsername + " %$#--->: ");
                            String logPassword = scanner.nextLine();
                            if (account.validatePassword(logPassword)) {
                                System.out.println(ANSI_GREEN);
                                System.out.println("You Successfully Logged in" + ANSI_RESET);
                                runLoginMenu(account, hopeReddit);
                                isLoggedIn = true;
                            }
                            if (!isLoggedIn) {
                                System.out.println(ANSI_RED);
                                System.out.println("Password Was Incorrect!!!");
                            }
                        }
                    }
                    if (!isFound) {
                        System.out.println(ANSI_RED);
                        System.out.println("You Dont Have Any Accounts Please SignUp First!!!");
                    }
                    break;
                case "3": // user wants to Exit the Program
                    exit();
                    break;
                default:
                    displayInvalidOrderError();
                    break;
            }
        }
    }

    public static void runLoginMenu(Account account, DataBase dataBase) { // Function For do tasks after user login
        Scanner scanner = new Scanner(System.in);
        boolean wantToLogOut = false;

        while (!wantToLogOut) {
            account.displayMenuAfterLogIn();
            String userChoice = scanner.nextLine();

            switch (userChoice) {
                case "1": // show All Subreddits / (if user want to Follow a Subreddit mist Choose it)
                    dataBase.viewAllSubreddits();
                    if (dataBase.getSubreddits().isEmpty()) {
                        break;
                    }
                    System.out.print("Enter The Topic Of Subreddit That You Want To Join To It(0 for back to menu) %$#--->: ");
                    String inputJoinTopic = scanner.nextLine();
                    boolean isInputCorrect = false;
                    if (inputJoinTopic.equals("0")) {break;}
                    for (Subreddit subreddit : dataBase.getSubreddits()) {
                        if (subreddit.getTopic().equals(inputJoinTopic)) {
                            isInputCorrect = true;
                            subreddit.addParticipant(account);
                            account.addFollowSubreddit(subreddit);
                            System.out.println(ANSI_GREEN);
                            System.out.println("You Follow " + subreddit.getTopic() + " Now" + ANSI_RESET);
                            break;
                        }
                    }
                    if (!isInputCorrect) {
                        displayInvalidOrderError();
                    }
                    break;
                case "2": // show All Subreddits that this user has created or admin of them / (options for admins)
                    Admin admin2 = new Admin(account.getUsername(), "", account.getEmail());
                    // the password of admins not important because they can not log in (if they want they should log in with their account(account object))
                    admin2.addSubredditsFromAccountToAdmin(account);
                    admin2.showUserSubreddits();
                    if (admin2.getSubreddits().isEmpty()) {
                        break;
                    }
                    admin2.displayDeletingMenu();
                    String deletingType = scanner.nextLine();
                    String deletingInput, subredditName;
                    boolean isIDInDataBase = false;

                    switch (deletingType) {
                        case "1": // delete subreddit
                            System.out.print("Enter ID Of Subreddit That You Want To Delete %$#--->: ");
                            deletingInput = scanner.nextLine();
                            for (Subreddit subreddit : dataBase.getSubreddits()) {
                                if (subreddit.getSubredditStringUUID().equals(deletingInput)) {
                                    dataBase.getSubreddits().remove(subreddit);
                                    isIDInDataBase = true;
                                    System.out.println(ANSI_GREEN);
                                    System.out.println(subreddit.getTopic() + " Successfully Deleted" + ANSI_RESET);
                                    break;
                                }
                            }
                            if (!isIDInDataBase) {
                                System.out.println(ANSI_RED);
                                System.out.println("Not Found" + ANSI_RESET);
                            }
                            break;
                        case "2": // delete post
                            boolean isSubredditFound = false;
                            System.out.print("Enter Subreddit Topic %$#--->: ");
                            subredditName = scanner.nextLine();
                            for (Subreddit subreddit : dataBase.getSubreddits()) {
                                if (subreddit.getTopic().equals(subredditName)) {
                                    isSubredditFound = true;
                                    System.out.print("Enter ID Of Post That You Want To Delete %$#--->: ");
                                    deletingInput = scanner.nextLine();
                                    for (Post post : subreddit.getPosts()) {
                                        if (post.getPostStringUUID().equals(deletingInput)) {
                                            isIDInDataBase = true;
                                            subreddit.getPosts().remove(post);
                                            System.out.println(ANSI_GREEN);
                                            System.out.println(post.getTitle() + " Successfully Deleted" + ANSI_RESET);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!isSubredditFound) {
                                isIDInDataBase = true;
                                System.out.println(ANSI_RED);
                                System.out.println("Subreddit Not Found" + ANSI_RESET);
                            }
                            if (!isIDInDataBase) {
                                System.out.println(ANSI_RED);
                                System.out.println("Not Found" + ANSI_RESET);
                            }
                            break;
                        case "3": // delete comment
                            boolean isSubredditInDataBase = false;
                            boolean isPostInSubreddit = false;
                            System.out.print("Enter Subreddit Topic %$#--->: ");
                            subredditName = scanner.nextLine();
                            for (Subreddit subreddit : dataBase.getSubreddits()) {
                                if (subreddit.getTopic().equals(subredditName)) {
                                    isSubredditInDataBase = true;
                                    System.out.print("Enter Post Title %$#--->: ");
                                    String postTitle = scanner.nextLine();
                                    for (Post post : subreddit.getPosts()) {
                                        if (post.getTitle().equals(postTitle)) {
                                            isPostInSubreddit = true;
                                            System.out.print("Enter ID Of Comment That You Want To Delete %$#--->: ");
                                            deletingInput = scanner.nextLine();
                                            for (Comment comment : post.getComments()) {
                                                if (comment.getCommentStringUUID().equals(deletingInput)) {
                                                    isIDInDataBase = true;
                                                    post.getComments().remove(comment);
                                                    System.out.println(ANSI_GREEN);
                                                    System.out.println("Comment Successfully Deleted" + ANSI_RESET);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!isSubredditInDataBase) {
                                isIDInDataBase = true;
                                isPostInSubreddit = true;
                                System.out.println(ANSI_RED);
                                System.out.println("Subreddit Not Found" + ANSI_RESET);
                            }
                            if (!isPostInSubreddit) {
                                isIDInDataBase = true;
                                System.out.println(ANSI_RED);
                                System.out.println("Post Not Found" + ANSI_RESET);
                            }
                            if (!isIDInDataBase) {
                                System.out.println(ANSI_RED);
                                System.out.println("Not Found" + ANSI_RESET);
                            }
                            break;
                        case "4": // delete participant
                            boolean isSubreddit = false;
                            System.out.print("Enter Subreddit Topic %$#--->: ");
                            subredditName = scanner.nextLine();
                            for (Subreddit subreddit : dataBase.getSubreddits()) {
                                if (subreddit.getTopic().equals(subredditName)) {
                                    isSubreddit = true;
                                    System.out.print("Enter ID Of Participant That You Want To Delete %$#--->: ");
                                    deletingInput = scanner.nextLine();
                                    for (Account account1 : subreddit.getParticipants()) {
                                        if (account1.getStringUUID().equals(deletingInput)) {
                                            isIDInDataBase = true;
                                            subreddit.getParticipants().remove(account1);
                                            account1.getFollowSubreddits().remove(subreddit);
                                            account1.getSubreddits().remove(subreddit);
                                            System.out.println(ANSI_GREEN);
                                            System.out.println(account1.getUsername() + " Successfully Deleted" + ANSI_RESET);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!isSubreddit) {
                                isIDInDataBase = true;
                                System.out.println(ANSI_RED);
                                System.out.println("Subreddit Not Found" + ANSI_RESET);
                            }
                            if (!isIDInDataBase) {
                                System.out.println(ANSI_RED);
                                System.out.println("Not Found" + ANSI_RESET);
                            }
                            break;
                        case "0":
                            break;
                        default:
                            displayInvalidOrderError();
                            break;
                    }
                    break;
                case "3": // show All Subreddits to Leaving Comments
                    dataBase.viewAllSubreddits();
                    if (dataBase.getSubreddits().isEmpty()) {
                        break;
                    }
                    showAllSubredditsSubmenu();
                    String inputSubmenuChoice = scanner.nextLine();

                    switch (inputSubmenuChoice) {
                        case "1": // add comment
                            getAndAddComment(account, dataBase, 3);
                            break;
                        case "2": // saving post
                            getAndSavePost(account, dataBase, 3);
                            break;
                        case "3": // karma
                            getAndAddKarma(account, dataBase, 3);
                            break;
                        case "4": // back to menu
                            break;
                        default:
                            displayInvalidOrderError();
                            break;
                    }
                    break;
                case "4": // show All Subreddits that this user follow them (TimeLine) / (Show and Leaving Comments)
                    account.showUserFollowSubreddits();
                    if (account.getFollowSubreddits().isEmpty()) {
                        break;
                    }
                    showAllSubredditsSubmenu();
                    String inputSubmenuChoice2 = scanner.nextLine();

                    switch (inputSubmenuChoice2) {
                        case "1": // add comment
                            getAndAddComment(account, dataBase, 4);
                            break;
                        case "2": // saving post
                            getAndSavePost(account, dataBase, 4);
                            break;
                        case "3": // karma
                            getAndAddKarma(account, dataBase, 4);
                            break;
                        case "4": // back to menu
                            break;
                        default:
                            displayInvalidOrderError();
                            break;
                    }
                    break;
                case "5": // show all posts user save them
                    account.showAllSavedPosts();
                    break;
                case "6": // Create a new Subreddit
                    Admin admin = new Admin(account.getUsername(), "", account.getEmail());
                    // the password of admins not important because they can not log in (if they want they should log in with their account(account object))
                    System.out.print("Enter The Topic Of Subreddit %$#--->: ");
                    String inputTopic = scanner.nextLine();
                    Subreddit subreddit = new Subreddit(inputTopic, admin);
                    dataBase.addSubreddit(subreddit);
                    account.addSubreddit(subreddit);
                    account.addFollowSubreddit(subreddit);
                    subreddit.addParticipant(account);
                    System.out.println(ANSI_GREEN);
                    System.out.println("Subreddit " + subreddit.getTopic() + " Successfully Created" + ANSI_RESET);
                    break;
                case "7": // add admin to a Subreddit
                    if (account.getSubreddits().isEmpty()) {
                        System.out.println(ANSI_RED);
                        System.out.println("You are not Admin of any subreddits So You Can not add any Admins To any Subreddits!!!" + ANSI_RESET);
                    }
                    else {
                        System.out.println("List Of Your Subreddits: ");
                        account.showUserSubredditsTopics();
                        System.out.print("Enter the Chosen Subreddit Topic %$#--->: ");
                        String inputTopicAddAdmin = scanner.nextLine();
                        boolean isSubredditFound = false;

                        for (Subreddit subreddit1 : account.getSubreddits()) {
                            if (subreddit1.getTopic().equals(inputTopicAddAdmin)){
                                isSubredditFound = true;
                                System.out.print("Enter Username of new Admin %$#--->: ");
                                String newAdminName = scanner.nextLine();
                                if (dataBase.isUsernameInDataBase(newAdminName)) {
                                    Account adminAccount = dataBase.getAccountByUsername(newAdminName);
                                    Admin admin1 = new Admin(adminAccount.getUsername(), "", adminAccount.getEmail());
                                    subreddit1.addAdmin(admin1);
                                    subreddit1.addParticipant(adminAccount);
                                    adminAccount.addSubreddit(subreddit1); // admin of it
                                    adminAccount.addFollowSubreddit(subreddit1); // follow it
                                    System.out.println(ANSI_GREEN);
                                    System.out.println(adminAccount.getUsername() + " Successfully add to admins of " + subreddit1.getTopic() + ANSI_RESET);
                                    break;
                                }
                                else {
                                    System.out.println(ANSI_RED);
                                    System.out.println("User does not Exist!!!" + ANSI_RESET);
                                    break;
                                }
                            }
                        }
                        if (!isSubredditFound) {
                            System.out.println(ANSI_RED);
                            System.out.println("Subreddit Not Founded!!!" + ANSI_RESET);
                        }
                    }
                    break;
                case "8": // Creating a Post
                    dataBase.viewAllSubredditsTopics();
                    if (dataBase.getSubreddits().isEmpty()) {
                        break;
                    }
                    System.out.print("Enter Topic of Subreddit that you want Create Post in it (0 for back to menu) %$#--->: ");
                    String inputSubredditName = scanner.nextLine();
                    boolean isInputTrue = false;
                    if (inputSubredditName.equals("0")) {break;}
                    for (Subreddit subreddit1 : dataBase.getSubreddits()) {
                        if (subreddit1.getTopic().equals(inputSubredditName)) {
                            isInputTrue = true;
                            String newPostTitle;
                            String newPostContent;
                            System.out.print("Enter The Title Of New Post %$#--->: ");
                            newPostTitle = scanner.nextLine();
                            System.out.print("Enter The Content Of New Post %$#--->: ");
                            newPostContent = scanner.nextLine();
                            Post post = new Post(account, newPostTitle, newPostContent);
                            System.out.print("If You Want Enter a Tag (0 for back to menu) %$#--->: ");
                            String inputTags = scanner.nextLine();
                            if (!inputTags.equals("0")) { post.addTag(inputTags); }
                            while (!inputTags.equals("0")) {
                                System.out.print("If You Want Enter a Tag (0 for back to menu) %$#--->: ");
                                inputTags = scanner.nextLine();
                                if (!inputTags.equals("0")) { post.addTag(inputTags); }
                            }
                            subreddit1.addPost(post);
                            account.addPost(post);
                            post.setSubreddit(subreddit1);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Post " + post.getTitle() + " in Subreddit " + subreddit1.getTopic() + " Successfully Created" + ANSI_RESET);
                        }
                    }
                    if (!isInputTrue) {
                        displayInvalidOrderError();
                    }
                    break;
                case "9": // Search
                    System.out.print("Enter Name of a User or a Subreddit %$#--->: ");
                    String inputName = scanner.nextLine();
                    int foundUserAndSubreddit = dataBase.searchByName(inputName);
                    if (foundUserAndSubreddit == 2) { // subreddit found
                        Subreddit searchedSubreddit = dataBase.getSubredditByName(inputName);
                        searchedSubreddit.showAllPosts();
                    }
                    if (foundUserAndSubreddit == 3) { // user found
                        Account searchedAccount = dataBase.getAccountByUsername(inputName);
                        searchedAccount.showUserProfile();
                    }
                    if (foundUserAndSubreddit == 1) { // both found
                        System.out.print("What Do You Want : 1) user - 2) subreddit %$#--->: ");
                        String inputWhat = scanner.nextLine();
                        if (inputWhat.equals("1")) {
                            Account searchedAccount = dataBase.getAccountByUsername(inputName);
                            searchedAccount.showUserProfile();
                        }
                        else if (inputWhat.equals("2")) {
                            Subreddit searchedSubreddit = dataBase.getSubredditByName(inputName);
                            searchedSubreddit.showAllPosts();
                        }
                        else {
                            displayInvalidOrderError();
                        }
                    }
                    break;
                case "10": // Change Personal Information
                    account.displayChangeInformationMenu();
                    String changeInput = scanner.nextLine();
                    switch (changeInput) {
                        case "1": // change username
                            System.out.print("Enter Your New Username(4 digits at least) %$#--->: ");
                            String inputNewUsername = scanner.nextLine();
                            if(dataBase.isUsernameInDataBase(inputNewUsername)) {
                                System.out.println(ANSI_CYAN);
                                System.out.println("Username Used Before Please Choice another!" + ANSI_RESET);
                                break;
                            }
                            if (!isUserNameValid(inputNewUsername)) {
                                displayInvalidOrderError();
                                break;
                            }
                            account.changeUsername(inputNewUsername);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Your Username Successfully changed" + ANSI_RESET);
                            break;
                        case "2": // change password
                            System.out.print("Enter Your Password First %$#--->: ");
                            String exPassword = scanner.nextLine();
                            boolean canChangePassword = account.validatePassword(exPassword);
                            if (!canChangePassword) {
                                System.out.println(ANSI_RED);
                                System.out.println("Your Old Password is Incorrect" + ANSI_RESET);
                                break;
                            }
                            System.out.print("Enter Your New Password(8 digits at least) %$#--->: ");
                            String inputNewPassword = scanner.nextLine();
                            if (!isPasswordValid(inputNewPassword)) {
                                displayInvalidOrderError();
                                break;
                            }
                            account.changePassword(inputNewPassword);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Your Password Successfully changed" + ANSI_RESET);
                            break;
                        case "3": // change email
                            System.out.print("Enter Your New Email %$#--->: ");
                            String inputNewEmail = scanner.nextLine();
                            if(dataBase.isEmailInDataBase(inputNewEmail)) {
                                System.out.println(ANSI_CYAN);
                                System.out.println("Email Used Before Please Choice another!" + ANSI_RESET);
                                break;
                            }
                            if (!isEmailValid(inputNewEmail)) {
                                displayInvalidOrderError();
                                break;
                            }
                            account.changeEmail(inputNewEmail);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Your Email Successfully changed" + ANSI_RESET);
                            break;
                        case "4": // change email show
                            System.out.print("Do You Want to Show Your Email in Your Profile: 1) Yes - 2) No %$#--->: ");
                            String inputShowEmailInProfile = scanner.nextLine();
                            boolean shoeEmailInProfile = false;
                            if (inputShowEmailInProfile.equals("1")) { shoeEmailInProfile = true; }
                            account.setEmailDisplayOnProfile(shoeEmailInProfile);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Successfully Changed" + ANSI_RESET);
                            break;
                        default:
                            displayInvalidOrderError();
                            break;
                    }
                    break;
                case "11": // Show This User Profile
                    account.showUserProfile();
                    break;
                case "12": // Show All Votes
                    account.showAllVotes();
                    changeVote(account);
                    break;
                case "13": // Logout
                    System.out.println(ANSI_GREEN);
                    System.out.println("Log out successfully");
                    wantToLogOut = true;
                    break;
                case "14": // Exit Program
                    exit();
                    break;
                default:
                    displayInvalidOrderError();
                    break;
            }
        }
    }

    public static void getAndAddComment(Account account, DataBase dataBase, int numOfOption) {
        Scanner scanner = new Scanner(System.in);
        boolean isAnyPostInDataBase = false;

        if (numOfOption == 3) { // for every subreddits
            for (Subreddit subreddit : dataBase.getSubreddits()) {
                if (!subreddit.getPosts().isEmpty()) {
                    isAnyPostInDataBase = true;
                    break;
                }
            }
        }

        if (numOfOption == 4) { // for timeline of each user
            for (Subreddit subreddit : account.getFollowSubreddits()) {
                if (!subreddit.getPosts().isEmpty()) {
                    isAnyPostInDataBase = true;
                    break;
                }
            }
        }
        if (isAnyPostInDataBase) {
            System.out.print("Enter The Title Of Post That You Want To Leaving Comment For It (0 For Back to menu) %$#--->: ");
            String inputTitle = scanner.nextLine();

            if (!inputTitle.equals("0")) {
                boolean isPostInDataBase = false;

                for (Subreddit subreddit : dataBase.getSubreddits()) {
                    for (Post post : subreddit.getPosts()) {
                        if (post.getTitle().equals(inputTitle)) {
                            isPostInDataBase = true;
                            System.out.print("Enter Your Comment %$#--->: ");
                            String inputComment = scanner.nextLine();
                            Comment comment = new Comment(inputComment, account, post);
                            post.addComment(comment);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Your Comment Successfully Added" + ANSI_RESET);
                            break;
                        }
                    }
                }

                if (!isPostInDataBase) {
                    displayInvalidOrderError();
                }
            }
        }
        else {
            System.out.println("There is no Posts in Any Subreddits so you Can not Leave any Comments");
        }
    }

    public static void getAndSavePost(Account account, DataBase dataBase, int numOfOption) {
        Scanner scanner = new Scanner(System.in);
        boolean isAnyPostInDataBase = false;

        if (numOfOption == 3) { // for every subreddits
            for (Subreddit subreddit : dataBase.getSubreddits()) {
                if (!subreddit.getPosts().isEmpty()) {
                    isAnyPostInDataBase = true;
                    break;
                }
            }
        }

        if (numOfOption == 4) { // for timeline of each user
            for (Subreddit subreddit : account.getFollowSubreddits()) {
                if (!subreddit.getPosts().isEmpty()) {
                    isAnyPostInDataBase = true;
                    break;
                }
            }
        }

        if (isAnyPostInDataBase) {
            System.out.print("Enter The Title of Post You Want to Save that (0 for back to Menu) %$#--->: ");
            String inoutPost = scanner.nextLine();
            boolean postFound = false;

            if (!inoutPost.equals("0")) {
                for (Subreddit subreddit : dataBase.getSubreddits()) {
                    for (Post post : subreddit.getPosts()) {
                        if (post.getTitle().equals(inoutPost)) {
                            postFound = true;
                            account.addSavedPost(post);
                            System.out.println(ANSI_GREEN);
                            System.out.println("Post Successfully Saved" + ANSI_RESET);
                            break;
                        }
                    }
                }

                if (!postFound) {
                    System.out.println(ANSI_RED);
                    System.out.println("Post Not Found" + ANSI_RESET);
                }
            }
        }

        else {
            System.out.println("There is no Posts in Any Subreddits so you Can not Save any Posts");
        }
    }

    public static void getAndAddKarma(Account account, DataBase dataBase, int numOfOption) {
        Scanner scanner = new Scanner(System.in);
        boolean isAnyPostInDataBase = false;

        if (numOfOption == 3) { // for every subreddits
            for (Subreddit subreddit : dataBase.getSubreddits()) {
                if (!subreddit.getPosts().isEmpty()) {
                    isAnyPostInDataBase = true;
                    break;
                }
            }
        }

        if (numOfOption == 4) { // for timeline of each user
            for (Subreddit subreddit : account.getFollowSubreddits()) {
                if (!subreddit.getPosts().isEmpty()) {
                    isAnyPostInDataBase = true;
                    break;
                }
            }
        }

        if (isAnyPostInDataBase) {
            System.out.print("Enter The Title Of Post You Want To Vote That (0 for Back to Menu) %$#--->: ");
            String inputTitle = scanner.nextLine();
            boolean isPostFound = false;

            if (!inputTitle.equals("0")) {
                for (Subreddit subreddit : dataBase.getSubreddits()) {
                    for (Post post : subreddit.getPosts()) {
                        if (post.getTitle().equals(inputTitle)) {
                            isPostFound = true;
                            System.out.print("Vote To Post or Comments of it 1) Post - 2) one of Comments %$#--->: ");
                            String voteSituation = scanner.nextLine();
                            boolean isVoteSituationValid = (voteSituation.equals("1") || voteSituation.equals("2"));

                            if (!isVoteSituationValid) {
                                displayInvalidOrderError();
                                break;
                            }

                            if (voteSituation.equals("1")) {
                                if (post.getKarma().isKarmaSetterNew(account)) {
                                    System.out.print("+) UpVote / -) DownVote %$#--->: ");
                                    String userVote = scanner.nextLine();
                                    boolean isVoteValid = (userVote.equals("+") || userVote.equals("-"));

                                    if (!isVoteValid) {
                                        displayInvalidOrderError();
                                        break;
                                    }

                                    if (userVote.equals("+")) {
                                        PostVote postVote = new PostVote("+", account, post.getAuthor(), post);
                                        account.addPostVote(postVote);
                                        post.getKarma().addKarma();
                                        post.getKarma().addKarmaSetter(account);
                                        post.getAuthor().getPostsKarma().addKarma();
                                        System.out.println(ANSI_GREEN);
                                        System.out.println("Your Vote Successfully Added" + ANSI_RESET);
                                    }
                                    else if (userVote.equals("-")) {
                                        PostVote postVote = new PostVote("-", account, post.getAuthor(), post);
                                        account.addPostVote(postVote);
                                        post.getKarma().deductKarma();
                                        post.getKarma().addKarmaSetter(account);
                                        post.getAuthor().getPostsKarma().deductKarma();
                                        System.out.println(ANSI_GREEN);
                                        System.out.println("Your Vote Successfully Added" + ANSI_RESET);
                                    }
                                    break;
                                }
                                else {
                                    System.out.println(ANSI_RED);
                                    System.out.println("You Added Vote To This Post Before!!!" + ANSI_RESET);
                                    break;
                                }
                            }

                            if (voteSituation.equals("2")) {
                                if (!post.getComments().isEmpty()) {
                                    System.out.print("Enter Content of The Comment %$#--->: ");
                                    String content = scanner.nextLine();
                                    boolean iscontentFind = false;

                                    for (Comment comment : post.getComments()) {
                                        if (comment.getText().equals(content)) {
                                            iscontentFind = true;
                                            if (comment.getKarma().isKarmaSetterNew(account)) {
                                                System.out.print("+) UpVote / -) DownVote %$#--->: ");
                                                String userVote = scanner.nextLine();
                                                boolean isVoteValid = (userVote.equals("+") || userVote.equals("-"));

                                                if (!isVoteValid) {
                                                    displayInvalidOrderError();
                                                    break;
                                                }

                                                if (userVote.equals("+")) {
                                                    CommentVote commentVote = new CommentVote("+", account, comment.getAuthor(), comment);
                                                    account.addCommentVote(commentVote);
                                                    comment.getKarma().addKarma();
                                                    comment.getKarma().addKarmaSetter(account);
                                                    comment.getAuthor().getCommentsKarma().addKarma();
                                                    System.out.println(ANSI_GREEN);
                                                    System.out.println("Your Vote Successfully Added" + ANSI_RESET);
                                                }
                                                else if (userVote.equals("-")) {
                                                    CommentVote commentVote = new CommentVote("-", account, comment.getAuthor(), comment);
                                                    account.addCommentVote(commentVote);
                                                    comment.getKarma().deductKarma();
                                                    comment.getKarma().addKarmaSetter(account);
                                                    comment.getAuthor().getCommentsKarma().deductKarma();
                                                    System.out.println(ANSI_GREEN);
                                                    System.out.println("Your Vote Successfully Added" + ANSI_RESET);
                                                }
                                                break;
                                            }

                                            else {
                                                System.out.println(ANSI_RED);
                                                System.out.println("You Added Vote To This Post Before!!!" + ANSI_RESET);
                                                break;
                                            }
                                        }
                                    }

                                    if (!iscontentFind) {
                                        System.out.println(ANSI_RED);
                                        System.out.println("Comment Not Found!!!" + ANSI_RESET);
                                        break;
                                    }
                                }

                                else {
                                    System.out.println(ANSI_RED);
                                    System.out.println("This Post Has No Comments Yet!!!" + ANSI_RESET);
                                    break;
                                }
                            }

                            break;
                        }
                    }
                }

                if (!isPostFound) {
                    System.out.println(ANSI_RED);
                    System.out.println("Post Not Found" + ANSI_RESET);
                }
            }
        }

        else {
            System.out.println("There is no Posts in Any Subreddits so you Can not Vote to any posts");
        }
    }

    public static void changeVote(Account account) {
        Scanner scanner = new Scanner(System.in);
        if (!account.getPostVotes().isEmpty() || !account.getCommentVotes().isEmpty()) {
            System.out.print("What Do You Want To Change 1) Vote Of a Post - 2) Vote Of a Comment (0 For Back To Menu) %$#--->: ");
            String situation = scanner.nextLine();
            switch (situation) {
                case "1": // Post
                    if (!account.getPostVotes().isEmpty()) {
                        System.out.print("Enter The Title of Post %$#--->: ");
                        String title = scanner.nextLine();
                        boolean isPostFound = false;
                        for (PostVote postVote : account.getPostVotes()) {
                            if (postVote.getPost().getTitle().equals(title)) {
                                isPostFound = true;
                                System.out.println("Your Vote = " + postVote.getKarmaVote());
                                System.out.print("1) Change Vote - 2) retract Vote (0 For Back To Menu) %$#--->: ");
                                String order = scanner.nextLine();
                                switch (order) {
                                    case "1": // change
                                        if (postVote.getKarmaVote().equals("+")) {
                                            postVote.getPost().getKarma().deductKarma();
                                            postVote.getPost().getKarma().deductKarma();
                                            postVote.getGetter().getPostsKarma().deductKarma();
                                            postVote.getGetter().getPostsKarma().deductKarma();
                                            postVote.setKarmaVote("-");
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Change Was Successfully Applied" + ANSI_RESET);
                                        }
                                        else if (postVote.getKarmaVote().equals("-")) {
                                            postVote.getPost().getKarma().addKarma();
                                            postVote.getPost().getKarma().addKarma();
                                            postVote.getGetter().getPostsKarma().addKarma();
                                            postVote.getGetter().getPostsKarma().addKarma();
                                            postVote.setKarmaVote("+");
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Change Was Successfully Applied" + ANSI_RESET);
                                        }
                                        break;
                                    case "2": // Retract
                                        if (postVote.getKarmaVote().equals("+")) {
                                            postVote.getPost().getKarma().deductKarma();
                                            postVote.getGetter().getPostsKarma().deductKarma();
                                            postVote.getPost().getKarma().getKarmaSetters().remove(account);
                                            account.getPostVotes().remove(postVote);
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Retract Was Successfully Applied You Can Vote To This Post Later" + ANSI_RESET);
                                        }
                                        else if (postVote.getKarmaVote().equals("-")) {
                                            postVote.getPost().getKarma().addKarma();
                                            postVote.getGetter().getPostsKarma().addKarma();
                                            postVote.getPost().getKarma().getKarmaSetters().remove(account);
                                            account.getPostVotes().remove(postVote);
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Retract Was Successfully Applied You Can Vote To This Post Later" + ANSI_RESET);
                                        }
                                        break;
                                    case "0": // Back To M
                                        break;
                                    default:
                                        displayInvalidOrderError();
                                        break;
                                }
                                break;
                            }
                        }
                        if (!isPostFound) {
                            System.out.println(ANSI_RED);
                            System.out.println("Post Not Found" + ANSI_RESET);
                        }
                    }
                    break;
                case "2": // Comment
                    if (!account.getCommentVotes().isEmpty()) {
                        boolean isCommentFounded = false;
                        System.out.print("Enter Content Of The Comment %$#--->: ");
                        String inputComment = scanner.nextLine();
                        for (CommentVote commentVote : account.getCommentVotes()) {
                            if (commentVote.getComment().getText().equals(inputComment) && commentVote.getStatus() != 1) {
                                isCommentFounded = true;
                                System.out.println("Your Vote = " + commentVote.getKarmaVote());
                                System.out.print("1) Change Vote - 2) retract Vote (0 For Back To Menu) %$#--->: ");
                                String order = scanner.nextLine();
                                switch (order) {
                                    case "1": // Change
                                        if (commentVote.getKarmaVote().equals("+")) {
                                            commentVote.getComment().getKarma().deductKarma();
                                            commentVote.getComment().getKarma().deductKarma();
                                            commentVote.getGetter().getCommentsKarma().deductKarma();
                                            commentVote.getGetter().getCommentsKarma().deductKarma();
                                            commentVote.setKarmaVote("-");
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Change Was Successfully Applied" + ANSI_RESET);
                                        }
                                        else if (commentVote.getKarmaVote().equals("-")) {
                                            commentVote.getComment().getKarma().addKarma();
                                            commentVote.getComment().getKarma().addKarma();
                                            commentVote.getGetter().getCommentsKarma().addKarma();
                                            commentVote.getGetter().getCommentsKarma().addKarma();
                                            commentVote.setKarmaVote("+");
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Change Was Successfully Applied" + ANSI_RESET);
                                        }
                                        break;
                                    case "2": // Retract
                                        if (commentVote.getKarmaVote().equals("+")) {
                                            commentVote.getComment().getKarma().deductKarma();
                                            commentVote.getGetter().getCommentsKarma().deductKarma();
                                            commentVote.getComment().getKarma().getKarmaSetters().remove(account);
                                            commentVote.setStatus();
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Retract Was Successfully Applied You Can Vote To This Post Later" + ANSI_RESET);
                                        }
                                        else if (commentVote.getKarmaVote().equals("-")) {
                                            commentVote.getComment().getKarma().addKarma();
                                            commentVote.getGetter().getCommentsKarma().addKarma();
                                            commentVote.getComment().getKarma().getKarmaSetters().remove(account);
                                            commentVote.setStatus();
                                            System.out.println(ANSI_GREEN);
                                            System.out.println("Retract Was Successfully Applied You Can Vote To This Post Later" + ANSI_RESET);
                                        }
                                        break;
                                    case "0": // Back To Menu
                                        break;
                                    default:
                                        displayInvalidOrderError();
                                        break;
                                }
                            }
                        }
                        if (!isCommentFounded) {
                            System.out.println(ANSI_RED);
                            System.out.println("Comment Not Found" + ANSI_RESET);
                        }
                    }
                    break;
                case "0": // Menu
                    break;
                default:
                    displayInvalidOrderError();
                    break;
            }
        }
    }
    public static void showFirstMenu(String str) { // Function for display Main Menu
        System.out.println(ANSI_BLUE);
        System.out.println("----------------- " + str + " -----------------");
        System.out.println("1) Sign Up");
        System.out.println("2) Login");
        System.out.println("3) Exit");
        System.out.print("Enter Your Order: ");
    }

    public static void showAllSubredditsSubmenu() { // for case 3 and 4 in login menu
        System.out.println("$$$$ What Do You Want $$$$");
        System.out.println("1) Leaving Comment For a Post");
        System.out.println("2) Saving a Post");
        System.out.println("3) Karma Setting");
        System.out.println("4) Back To Menu");
        System.out.print("Your Choice %$#--->: ");
    }

    public static void displayInvalidOrderError() { // Function for show user that his/her order was invalid
        System.out.println(ANSI_RED);
        System.out.println("Your Input Was Invalid!!!" + ANSI_RESET);
    }

    // Validate Functions check user inputs before signingUp and create object

    public static boolean isEmailValid(String email) { // Function for Validate Email
        String regex = "\\w+@\\w+\\..+[mrg]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isPasswordValid(String password) { // Function for Validate Password Length
        return password.length() >= 8;
    }

    public static boolean isUserNameValid(String username) { // Function for Validate Username Length
        return username.length() >= 4;
    }

    public static void exit() { // Function for Ending the Program
        System.out.println(ANSI_BLUE);
        System.out.println("Exiting Program .....");
        System.out.println(ANSI_RESET);
        System.exit(0);
    }
}
