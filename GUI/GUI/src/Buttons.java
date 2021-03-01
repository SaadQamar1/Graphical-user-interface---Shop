import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.LinkedList;
import java.util.List;

public class Buttons {
    private Button resetStore;
    private Button addCart;
    private Button removeCart;
    private Button completeSale;

    public Button getAddCart() {
        return addCart;
    }
    public Button getRemoveCart() {
        return removeCart;
    }

    public Buttons(ObservableList<Node> children, ListViewContainer cart, ListViewContainer stock, TextFieldContainer sales, ElectronicStore store, TextFieldContainer revenue, TextFieldContainer sale, ListViewContainer topThree) {
        resetStore = new Button("Reset Store");
        addCart = new Button("Add to Cart");
        removeCart = new Button("Remove from Cart");
        completeSale = new Button("Complete Sale");
        setAddCartAction(stock, cart,store);
        setResetStoreAction();
        setRemoveCart(stock, cart, revenue, store, sale);
        setCompleteSale(cart, sales,store,revenue,sale,topThree);
        setButtonPosition();
        addCart.setDisable(true);
        removeCart.setDisable(true);
        completeSale.setDisable(true);
        children.add(resetStore);
        children.add(addCart);
        children.add(removeCart);
        children.add(completeSale);
        topThree.flush();
        topThree.addAllWords(topThree(store.stock));
    }

    private void setResetStoreAction() {
        resetStore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ElectronicStoreApp.theStage.setScene(new ElectronicStoreView().getScene());

            }
        });
    }

    private void setAddCartAction(ListViewContainer stock, ListViewContainer cart, ElectronicStore store) {
        addCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isValid(stock,cart,store.stock)) {
                    cart.addToView(stock.getSelectedItem());
                    if (!isValid(stock,cart,store.stock)) {
                        stock.removeItem(stock.getIndex());
                    }
                } else stock.removeItem(stock.getIndex());
                if (cart.items().size() > 0) {
                    completeSale.setDisable(false);
                }

                updateTheCartPrice(cart, store);
                if (stock.getIndex() == -1) {
                    addCart.setDisable(true);;
                }
            }
        });
    }

    private void setRemoveCart(ListViewContainer stock, ListViewContainer cart, TextFieldContainer revenue, ElectronicStore store, TextFieldContainer saleTextField) {
        removeCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = cart.getIndex();
                cart.removeItem(x);
                revenue.updateContent(store.getRevenue()+"");
                saleTextField.updateContent(store.getRevenue()+"");
                if (!stock.items().contains(cart.getSelectedItem()) && cart.getIndex()!=-1) {
                    stock.addToView(cart.getSelectedItem());
                }
                if (cart.getIndex() == -1) {
                    removeCart.setDisable(true);
                    completeSale.setDisable(true);
                }
                updateTheCartPrice(cart, store);
            }
        });
    }

    private boolean isValid(ListViewContainer stock, ListViewContainer cart, theProduct[] products) {
        theProduct product = products[0];
        int theTimes = 0;
        for (theProduct p:products) {
            if (p != null&&stock.getSelectedItem().equals(p.toString())) {
                product = p; }
        }
        for (String content:cart.items()) {
            if (content.equals (product.toString())) {
                theTimes += 1; }
        }
        return theTimes < product.getStockQuantity();
    }

    private void setCompleteSale(ListViewContainer cart, TextFieldContainer sales, ElectronicStore store, TextFieldContainer revenue, TextFieldContainer saleTextField, ListViewContainer topThree) {
        completeSale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int sale = Integer.parseInt(sales.getContent());
                sale++;
                sales.updateContent(sale + "");

                for (int i=0;i<store.stock.length;i++) {
                    theProduct p = store.stock[i];
                    for (String s:cart.items()) {
                        if (p!=null && s.equals(p.toString())) {
                            store.sellProducts(i,1);
                        }
                    }
                }
                cart.flush();

                revenue.updateContent(store.getRevenue()+"");
                saleTextField.updateContent(store.getRevenue()+"");
                completeSale.setDisable(true);
                removeCart.setDisable(true);
                topThree.flush();
                topThree.addAllWords(topThree(store.stock));
                updateTheCartPrice(cart, store);
            }

        });
    }

    private void setButtonPosition() {
        resetStore.relocate(10, 335);
        addCart.relocate(230, 335);
        removeCart.relocate(475, 335);
        completeSale.relocate(625, 335);
        completeSale.setPrefSize(150, 45);
        removeCart.setPrefSize(150, 45);
        addCart.setPrefSize(150, 45);
        resetStore.setPrefSize(150, 45);
    }

    private List<String> topThree(theProduct[] products) {
        List<theProduct> productsList = new LinkedList<>();
        for (theProduct p:products) {
            if (p!=null) {
                productsList.add(p);
            }
        }
        List<String> finalResult = new LinkedList<>();
        for (int i=0;i<3;i++) {
            theProduct product = products[i];
            while (finalResult.contains(product.toString())) {
                product = products[i+1];
            }
            for (int j=0;j<productsList.size();j++) {
                if (product.getSoldQuantity()<productsList.get(j).getSoldQuantity()&&!finalResult.contains(productsList.get(j).toString())) {
                    product = productsList.get(j);
                }
            }
            finalResult.add(product.toString());
        }
        return finalResult;
    }
    private void updateTheCartPrice(ListViewContainer cart, ElectronicStore store) {
        double priceCounter = 0;
        for (theProduct p:store.stock) {
            for (String s:cart.items()) {
                if (p!=null&&s.equals(p.toString())) {
                    priceCounter+=p.getPrice();
                }
            }
        }
        cart.updateLabel("Current Cart($"+priceCounter+")");
    }


}
