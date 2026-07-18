import com.sumen.car.CarService;
import com.sumen.presentation.MenuHandler;
import com.sumen.user.UserService;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        MenuHandler menuHandler = new MenuHandler();
        try {
            CarService carService = new CarService();
            UserService userService = new UserService();
            carService.loadCarInventory();
            userService.addUsers();
            menuHandler.runMenu();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
