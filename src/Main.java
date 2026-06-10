import com.amigos.car.CarService;
import com.amigos.menu.MenuHandler;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        MenuHandler menuHandler = new MenuHandler();
        try {
            CarService carService = new CarService();
            carService.updateCarInventory();
            menuHandler.runMenu();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
