import com.amigos.data.DataStore;
import com.amigos.menu.MenuHandler;
import com.amigos.model.CarBooking;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        try {
            DataStore.loadCars();
            MenuHandler.runMenu();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
