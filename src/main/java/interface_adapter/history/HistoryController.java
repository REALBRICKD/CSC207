//package interface_adapter.history;
//import use_case.SignupInputBoundary;
//import use_case.SignupInputData;
//
//public class HistoryController {
//    final SignupInputBoundary userSignupUseCaseInteractor;
//    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
//        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
//    }
//
//    public void execute(String username, String password1, String password2) {
//        SignupInputData signupInputData = new SignupInputData(
//                username, password1, password2);
//
//        userSignupUseCaseInteractor.execute(signupInputData);
//    }
//}