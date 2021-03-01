import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.ArrayList;

public class ElectronicStoreView extends Group {
    private final Scene scene = new Scene(this, 800, 400);
    private ElectronicStore store = ElectronicStore.createStore();

    private ArrayList<String> storeComponents() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (theProduct p : store.stock) {
            if (p != null) {
                arrayList.add(p.toString());
            }
        }
        return arrayList;
    }

    private final ListViewContainer stock = new ListViewContainer(165, 30, 300, 300, "                                  Store stock:", getChildren());
    private final TextFieldContainer revenue = new TextFieldContainer(store.revenue + "", "Revenue:", 20, 50, 80, 20, getChildren());
    private final TextFieldContainer sales = new TextFieldContainer("0", "# Sales:", 20, 20, 80, 20, getChildren());
    private final TextFieldContainer sale = new TextFieldContainer(store.revenue + "", "$ / Sale:", 20, 80, 80, 20, getChildren());
    private final ListViewContainer currentCart = new ListViewContainer(475, 30, 300, 300, "                                Current Cart:", getChildren());
    private final ListViewContainer popularItems = new ListViewContainer(10, 140, 150, 190, "     Most Popular Items:", getChildren());
    private final Buttons buttons = new Buttons(getChildren(), currentCart, stock, sales, store, revenue, sale,popularItems);

    public ElectronicStoreView() {
        stock.addAllWords(storeComponents());
        stock.turnOnButton(buttons.getAddCart());
        currentCart.turnOnButton(buttons.getRemoveCart());


    }
}

