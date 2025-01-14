package lab.android.chartersapp.charters.presentation.loginPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginPageViewModel extends ViewModel {

    private final MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        if ("user".equals(username) && "password".equals(password)) {
            loginResult.setValue(true);
        } else {
            loginResult.setValue(false);
        }
    }
}