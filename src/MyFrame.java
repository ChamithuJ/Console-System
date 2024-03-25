

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MyFrame extends JFrame implements ActionListener {
    private JButton myButton;
    private JComboBox<String> comboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private WestminsterShoppingManager shoppingManager;
    private JTextArea detailsArea;
    private JButton shoppingCartButton;
    private JButton addToCartButton;
    private JLabel label;
    private JLabel productIdLabel;
    private JLabel categoryLabel;
    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JLabel colorLabel;
    private JLabel warrantyLabel;
    private JLabel brandLabel;
    private Product selectedProduct;
    private ShoppingCartPanel cartPanel;

    MyFrame(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        String[] items = new String[]{"All", "Clothes", "Electronics"};
        this.cartPanel = new ShoppingCartPanel(shoppingManager);
        this.comboBox = new JComboBox(items);
        this.comboBox.setPreferredSize(new Dimension(90, this.comboBox.getPreferredSize().height));
        this.comboBox.addActionListener(this);
        this.shoppingCartButton = new JButton("Shopping Cart");
        this.shoppingCartButton.setFocusable(false);
        this.shoppingCartButton.addActionListener(this);
        this.addToCartButton = new JButton("Add to Cart");
        this.addToCartButton.setFocusable(false);
        this.addToCartButton.addActionListener(this);
        JPanel topNorthPanel = new JPanel(new FlowLayout(1));
        topNorthPanel.add(this.comboBox);
        JLabel selectCategoryLabel = new JLabel("Select Product Category");
        topNorthPanel.add(selectCategoryLabel);
        topNorthPanel.add(Box.createHorizontalStrut(40));
        topNorthPanel.add(this.shoppingCartButton);
        JPanel bottomSouthPanel = new JPanel(new BorderLayout());
        bottomSouthPanel.add(this.addToCartButton, "Center");
        JPanel topEastPanel = new JPanel(new BorderLayout());
        topEastPanel.add(this.addToCartButton, "South");
        JPanel labelsPanel = new JPanel(new GridLayout(7, 1));
        this.productIdLabel = new JLabel("Product Id: ");
        this.categoryLabel = new JLabel("Category: ");
        this.nameLabel = new JLabel("Name: ");
        this.sizeLabel = new JLabel("Size: ");
        this.colorLabel = new JLabel("Color: ");
        this.warrantyLabel = new JLabel("Warranty: ");
        this.brandLabel = new JLabel("Brand: ");
        labelsPanel.add(this.productIdLabel);
        labelsPanel.add(this.categoryLabel);
        labelsPanel.add(this.nameLabel);
        labelsPanel.add(this.sizeLabel);
        labelsPanel.add(this.colorLabel);
        labelsPanel.add(this.warrantyLabel);
        labelsPanel.add(this.brandLabel);
        topEastPanel.add(labelsPanel, "Center");
        this.hideLabels();
        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Product ID");
        this.tableModel.addColumn("Name");
        this.tableModel.addColumn("Category");
        this.tableModel.addColumn("Price");
        this.tableModel.addColumn("Info");
        this.productTable = new JTable(this.tableModel);
        this.productTable.setRowHeight(20);
        this.updateTableData();
        JScrollPane scrollPane = new JScrollPane(this.productTable);
        this.detailsArea = new JTextArea();
        this.detailsArea.setEditable(false);
        this.detailsArea.setBackground(Color.lightGray);
        this.productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = MyFrame.this.productTable.getSelectedRow();
                if (selectedRow != -1) {
                    MyFrame.this.selectedProduct = MyFrame.this.getProductFromTable(selectedRow);
                    MyFrame.this.updateLabels(MyFrame.this.selectedProduct);
                    MyFrame.this.detailsArea.setText(MyFrame.this.getProductDetails(MyFrame.this.selectedProduct));
                    MyFrame.this.cartPanel.setSelectedProduct(MyFrame.this.selectedProduct);
                }

            }
        });
        this.setTitle("Westminster Shopping Center");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        this.add(topNorthPanel, "North");
        this.add(scrollPane, "Center");
        this.add(topEastPanel, "East");
        this.add(bottomSouthPanel, "South");
        this.add(this.detailsArea, "South");
        this.pack();
        this.setVisible(true);
    }

    private void hideLabels() {
        this.productIdLabel.setVisible(false);
        this.categoryLabel.setVisible(false);
        this.nameLabel.setVisible(false);
        this.sizeLabel.setVisible(false);
        this.colorLabel.setVisible(false);
        this.warrantyLabel.setVisible(false);
        this.brandLabel.setVisible(false);
    }

    private void openShoppingCartPanel() {
        this.cartPanel.updateCart();
        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setDefaultCloseOperation(2);
        cartFrame.getContentPane().add(this.cartPanel);
        cartFrame.pack();
        cartFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.myButton) {
        }

        if (e.getSource() == this.comboBox) {
            this.updateTableData();
        }

        if (e.getSource() == this.shoppingCartButton) {
            this.openShoppingCartPanel();
        }

        if (e.getSource() == this.addToCartButton && this.selectedProduct != null) {
            this.cartPanel.setSelectedProduct(this.selectedProduct);
            this.cartPanel.updateCart();
        }

    }

    private void updateTableData() {
        this.tableModel.setRowCount(0);
        Iterator var1 = this.shoppingManager.getProductList().iterator();

        while(true) {
            Product product;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                product = (Product)var1.next();
            } while(!this.comboBox.getSelectedItem().equals("All") && (!this.comboBox.getSelectedItem().equals("Clothes") || !(product instanceof Clothing)) && (!this.comboBox.getSelectedItem().equals("Electronics") || !(product instanceof Electronics)));

            Object[] rowData = new Object[]{product.getProductId(), product.getProductName(), product instanceof Electronics ? "Electronics" : "Clothing", product.getPrice(), this.getProductInfo(product)};
            this.tableModel.addRow(rowData);
        }
    }

    private String getProductInfo(Product product) {
        String var10000;
        if (product instanceof Electronics) {
            var10000 = ((Electronics)product).getBrand();
            return "Brand: " + var10000 + ", Warranty Period: " + ((Electronics)product).getWarrantyPeriod();
        } else if (product instanceof Clothing) {
            var10000 = ((Clothing)product).getSize();
            return "Size: " + var10000 + ", Color: " + ((Clothing)product).getColor();
        } else {
            return "N/A";
        }
    }

    private String getProductDetails(Product product) {
        StringBuilder details = new StringBuilder("Selected Product - Details\n");
        details.append("Product ID: ").append(product.getProductId()).append("\n");
        details.append("Category: ").append(product instanceof Electronics ? "Electronics" : "Clothing").append("\n");
        details.append("Name: ").append(product.getProductName()).append("\n");
        details.append("Price: ").append(product.getPrice()).append("\n");
        if (product instanceof Electronics electronics) {
            details.append("Brand: ").append(electronics.getBrand()).append("\n");
            details.append("Warranty Period: ").append(electronics.getWarrantyPeriod()).append("\n");
        } else if (product instanceof Clothing clothing) {
            details.append("Size: ").append(clothing.getSize()).append("\n");
            details.append("Color: ").append(clothing.getColor()).append("\n");
        }

        details.append("Items Available: ").append(product.getAvailableItems()).append("\n");
        return details.toString();
    }

    private Product getProductFromTable(int row) {
        String productId = (String)this.tableModel.getValueAt(row, 0);
        Iterator var3 = this.shoppingManager.getProductList().iterator();

        Product product;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            product = (Product)var3.next();
        } while(!product.getProductId().equals(productId));

        return product;
    }

    private void updateLabels(Product product) {
        JLabel var10000 = this.productIdLabel;
        String var10001 = product.getProductId();
        var10000.setText("Product Id: " + var10001);
        this.categoryLabel.setText("Category: " + (product instanceof Electronics ? "Electronics" : "Clothing"));
        this.nameLabel.setText("Name: " + product.getProductName());
        var10001 = product instanceof Clothing ? ((Clothing)product).getSize() : "N/A";
        this.sizeLabel.setText("Size: " + var10001);
        var10001 = product instanceof Clothing ? ((Clothing)product).getColor() : "N/A";
        this.colorLabel.setText("Color: " + var10001);
        Object var2 = product instanceof Electronics ? ((Electronics)product).getWarrantyPeriod() : "N/A";
        this.warrantyLabel.setText("Warranty: " + String.valueOf(var2));
        var10001 = product instanceof Electronics ? ((Electronics)product).getBrand() : "N/A";
        this.brandLabel.setText("Brand: " + var10001);
    }
}
