package Controller;

import DAOImplementation.AddressDAOImpl;
import DAOImplementation.CityDAOImpl;
import DAOImplementation.CountryDAOImpl;
import DAOImplementation.CustomerDAOImpl;
import DAOImplementation.CustomersTableRowDAOImpl;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.CustomersTableRow;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.DBConnection;

/**
 *
 * @author upont
 */
public class CustomerController implements Initializable {

    private static Connection connection;
    private static User user; //make user static from login to pass values through
    private static Customer customer = new Customer();
    private static Address addr = new Address();
    private static City addrCity = new City();
    private static Country addrCountry = new Country();
    private static int addrCityId, addrId, addrCountryId;
    private ObservableList<Customer> Customers = FXCollections.observableArrayList();
    private ObservableList<Address> Addresses = FXCollections.observableArrayList();
    private ObservableList<City> Cities = FXCollections.observableArrayList();
    private ObservableList<Country> Countries = FXCollections.observableArrayList();
    private ObservableList<CustomersTableRow> TableRows = FXCollections.observableArrayList();
    private CustomersTableRow tableRow;
    private Tooltip createTip = new Tooltip("Create Customer");
    private Tooltip updateTip = new Tooltip("Update Customer");
    private Tooltip deleteTip = new Tooltip("Delete Customer");

    @FXML
    private AnchorPane createCustomerPane;
    @FXML
    private AnchorPane customerListPane;
    @FXML
    private AnchorPane updateCustomerPane;
    @FXML
    private Button createCustomerButton;
    @FXML
    private Button updateCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelDeleteButton;
    @FXML
    private TextField createCustomerNameField;
    @FXML
    private TextField createCityField;
    @FXML
    private TextField createAddressField;
    @FXML
    private TextField createAddress2Field;
    @FXML
    private TextField createPostalCodeField;
    @FXML
    private TextField createPhoneNumberField;
    @FXML
    private TextField createCountryField;
    @FXML
    private TextField updateCustomerNameField;
    @FXML
    private TextField updateCityField;
    @FXML
    private TextField updateAddressField;
    @FXML
    private TextField updateAddress2Field;
    @FXML
    private TextField updatePostalCodeField;
    @FXML
    private TextField updatePhoneNumberField;
    @FXML
    private TextField updateCountryField;
    @FXML
    private TableView<CustomersTableRow> customersTable;
    @FXML
    private TableColumn<CustomersTableRow, String> customerName;
    @FXML
    private TableColumn<CustomersTableRow, String> address;
    @FXML
    private TableColumn<CustomersTableRow, String> postalCode;
    @FXML
    private TableColumn<CustomersTableRow, String> phoneNumber;
    @FXML
    private TableColumn<CustomersTableRow, String> city;
    @FXML
    private TableColumn<CustomersTableRow, String> country;

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void minimizeButtonAction(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void returnButtonAction(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        mainScreenScene.setCursor(new ImageCursor(image));
        mainScreenStage.setScene(mainScreenScene);
        mainScreenStage.show();
    }

    @FXML
    private void createCustomerHandler(ActionEvent event) throws IOException {
        customerListPane.setVisible(false);
        createCustomerPane.setVisible(true);
        updateCustomerButton.setDisable(true);
        deleteCustomerButton.setDisable(true);
    }

    @FXML
    private void updateCustomerHandler(ActionEvent event) throws IOException, SQLException, Exception {
        tableRow = customersTable.getSelectionModel().getSelectedItem();
        if (tableRow.getCustomerName() == null) {
            System.out.println("select a customer to update...");
            tableRow = customersTable.getSelectionModel().getSelectedItem();
        }
        initUpdate();
        populateUpdateFields();
        customerListPane.setVisible(false);
        createCustomerPane.setVisible(false);
        updateCustomerPane.setVisible(true);
        createCustomerButton.setDisable(true);
        deleteCustomerButton.setDisable(true);
    }

    @FXML
    private void deleteCustomerHandler(ActionEvent event) throws IOException, SQLException, Exception {
        tableRow = customersTable.getSelectionModel().getSelectedItem();
        if (tableRow.getCustomerName() == null) {
            System.out.println("select a customer to update...");
            tableRow = customersTable.getSelectionModel().getSelectedItem();
        }
        initUpdate();
        createCustomerButton.setDisable(true);
        updateCustomerButton.setDisable(true);
        deleteButton.setDisable(false);
        cancelDeleteButton.setDisable(false);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        createCustomerPane.setVisible(false);
        updateCustomerPane.setVisible(false);
        customerListPane.setVisible(true);
        deleteButton.setDisable(true);
        cancelDeleteButton.setDisable(true);
        updateCustomerButton.setDisable(false);
        deleteCustomerButton.setDisable(false);
        createCustomerButton.setDisable(false);
        clearCreateFields();
        clearUpdateFields();

    }

    @FXML
    private void createButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        if (validateCreateFields()) {
            createCustomerPane.setVisible(false);
            customerListPane.setVisible(true);
            updateCustomerButton.setDisable(false);
            deleteCustomerButton.setDisable(false);
            createCountry();
            createCity();
            createAddress();
            createCustomer();
            clearCreateFields();
        }
    }

    @FXML
    private void updateButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        if (validateUpdateFields()) {
            updateCustomerPane.setVisible(false);
            createCustomerPane.setVisible(false);
            customerListPane.setVisible(true);
            updateCustomerButton.setDisable(false);
            deleteCustomerButton.setDisable(false);
            createCustomerButton.setDisable(false);
            updateCountry();
            updateCity();
            updateAddress();
            updateCustomer();
            clearUpdateFields();
        }
    }

    @FXML
    private void deleteButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        updateCustomerButton.setDisable(false);
        deleteCustomerButton.setDisable(false);
        createCustomerButton.setDisable(false);
        cancelDeleteButton.setDisable(true);
        deleteButton.setDisable(true);
        deleteCustomer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnectionReference();
        customersTable.getSelectionModel().clearSelection();
        initToolTips();
        user = LoginController.getUser();
        String newCustomerName = "customer4";
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        try {
            customer = CustomerDAOImpl.getCustomerByName(newCustomerName, connection);
            Customers.addAll(CustomerDAOImpl.getAllCustomers(connection));
            Addresses.addAll(AddressDAOImpl.getAllAddresses(connection));
            Cities.addAll(CityDAOImpl.getAllCities(connection));
            Countries.addAll(CountryDAOImpl.getAllCountries(connection));
            TableRows.addAll(CustomersTableRowDAOImpl.getAllTableRows(connection));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        customersTable.setItems(TableRows);
        customersTable.refresh();
    }

    private void initToolTips() {
        Tooltip.install(createCustomerButton, createTip);
        Tooltip.install(updateCustomerButton, updateTip);
        Tooltip.install(deleteCustomerButton, deleteTip);
    }

    private void createCustomer() throws SQLException, Exception {
        customer = new Customer();
        customer.setCustomerName(createCustomerNameField.getText());
        customer.setAddressId(addrId);
        if (duplicateCustomerCheck(createCustomerNameField.getText(), addrId)) {
            System.out.println("Duplicate Customer Name Detected: Customer not saved...");
        } else {
            CustomerDAOImpl.createCustomer(customer, user, connection);
        }
        TableRows.clear();
        TableRows.addAll(CustomersTableRowDAOImpl.getAllTableRows(connection));
        customersTable.setItems(TableRows);
        customersTable.refresh();
    }

    private void createAddress() throws SQLException, Exception {
        addr = new Address();
        addr.setAddress(createAddressField.getText());
        addr.setAddress2(createAddress2Field.getText());
        addr.setPostalCode(createPostalCodeField.getText());
        addr.setPhone(createPhoneNumberField.getText());
        addr.setCityId(addrCityId);
        if (duplicateAddressCheck(createAddressField.getText(), createAddress2Field.getText(), createPostalCodeField.getText(), createPhoneNumberField.getText(), addrCityId) == true) {
            System.out.println("Duplicate Address Detected...");
        } else {
            AddressDAOImpl.createAddress(addr, user, connection);
        }

        addr = AddressDAOImpl.getAddressByAddressPostalPhone(addr.getAddress(), addr.getPostalCode(), addr.getPhone(), connection);
        addrId = addr.getAddressId();
    }

    private void createCity() throws SQLException, Exception {
        String addCity = createCityField.getText();
        addrCity = new City();
        addrCity.setCity(addCity);
        addrCity.setCountryId(addrCountryId);
        if (duplicateCityCheck(addCity, addrCountryId)) {
            System.out.println("Duplicate City Detected...");
        } else {
            CityDAOImpl.createCity(addrCity, user, connection);
        }
        addrCity = CityDAOImpl.getCityByName(addCity, connection);
        addrCityId = addrCity.getCityId();

    }

    private void createCountry() throws SQLException, Exception {
        String addCountry = createCountryField.getText();
        addrCountry = new Country();
        addrCountry.setCountry(addCountry);
        if (duplicateCountryCheck(addCountry)) {
            System.out.println("Duplicate Country Detected...");
        } else {
            CountryDAOImpl.createCountry(addrCountry, user, connection);
        }

        addrCountry = CountryDAOImpl.getCountryByName(addCountry, connection);
        addrCountryId = addrCountry.getCountryId();

    }

    private void updateCustomer() throws SQLException, Exception {
        String selectedCustomerName;
        selectedCustomerName = customer.getCustomerName();
        customer = new Customer();
        customer = CustomerDAOImpl.getCustomerByName(selectedCustomerName, connection);
        addr = new Address();
        addr = AddressDAOImpl.getAddressByAddressId(customer.getAddressId(), connection);

        if (duplicateCustomerCheck(updateCustomerNameField.getText(), addrId)) {
            System.out.println("Duplicate Customer Name Detected: Customer not updated...");
        } else {
            customer.setCustomerName(updateCustomerNameField.getText());
            customer.setAddressId(addrId);
            CustomerDAOImpl.updateCustomer(customer, user, connection);
        }

        if (duplicateAddressCheck(updateAddressField.getText(), updateAddress2Field.getText(), updatePostalCodeField.getText(), updatePhoneNumberField.getText(), addr.getCityId())) {
            System.out.println("Duplicate Address Detected: Customer Address not updated....");
        } else {
            addr.setAddress(updateAddressField.getText());
            addr.setAddress2(updateAddress2Field.getText());
            addr.setPostalCode(updatePostalCodeField.getText());
            addr.setPhone(updatePhoneNumberField.getText());
            AddressDAOImpl.updateAddress(addr, user, connection);
        }

        TableRows.clear();
        TableRows.addAll(CustomersTableRowDAOImpl.getAllTableRows(connection));
        customersTable.setItems(TableRows);
        customersTable.refresh();
    }

    private void updateAddress() throws SQLException, Exception {
        String add, post, phone;
        add = addr.getAddress();
        post = addr.getPostalCode();
        phone = addr.getPhone();
        addr = new Address();
        addr = AddressDAOImpl.getAddressByAddressPostalPhone(add, post, phone, connection);

        if (newAddressCheck(updateAddressField.getText())) {
            addr.setAddress(updateAddressField.getText());
            addr.setAddress2(updateAddress2Field.getText());
            addr.setPostalCode(updatePostalCodeField.getText());
            addr.setPhone(updatePhoneNumberField.getText());
            addr.setCityId(addrCityId);
            AddressDAOImpl.createAddress(addr, user, connection);
        } else {
            addr.setAddress(updateAddressField.getText());
            addr.setAddress2(updateAddress2Field.getText());
            addr.setPostalCode(updatePostalCodeField.getText());
            addr.setPhone(updatePhoneNumberField.getText());
            addr.setCityId(addrCityId);
            AddressDAOImpl.updateAddress(addr, user, connection);
        }

        addr = AddressDAOImpl.getAddressByAddressPostalPhone(addr.getAddress(), addr.getPostalCode(), addr.getPhone(), connection);
        addrId = addr.getAddressId();
    }

    private void updateCity() throws SQLException, Exception {
        String addCity = addrCity.getCity();
        addrCity = new City();
        addrCity = CityDAOImpl.getCityByName(addCity, connection);

        if (duplicateCityCheck(updateCityField.getText(), addrCountryId)) {
            System.out.println("Duplicate City Detected...");
        } else {
            addrCity.setCity(updateCityField.getText());
            addrCity.setCountryId(addrCountryId);
            CityDAOImpl.createCity(addrCity, user, connection);
        }
        addrCity = CityDAOImpl.getCityByName(updateCityField.getText(), connection);
        addrCityId = addrCity.getCityId();
    }

    private void updateCountry() throws SQLException, Exception {
        String addCountry;
        addCountry = addrCountry.getCountry();
        addrCountry = new Country();
        addrCountry = CountryDAOImpl.getCountryByName(addCountry, connection);
        if (duplicateCountryCheck(updateCountryField.getText())) {
            System.out.println("Duplicate Country Detected...");
        } else {
            addrCountry.setCountry(updateCountryField.getText());
            CountryDAOImpl.createCountry(addrCountry, user, connection);
        }

        addrCountry = CountryDAOImpl.getCountryByName(updateCountryField.getText(), connection);
        addrCountryId = addrCountry.getCountryId();
    }

    private void deleteCustomer() throws SQLException, Exception {
        String selectedCustomerName = customer.getCustomerName();
        customer = new Customer();
        customer = CustomerDAOImpl.getCustomerByName(selectedCustomerName, connection);
        CustomerDAOImpl.deleteCustomer(customer, connection);
        TableRows.clear();
        TableRows.addAll(CustomersTableRowDAOImpl.getAllTableRows(connection));
        customersTable.setItems(TableRows);
        customersTable.refresh();
    }

    private boolean duplicateCustomerCheck(String customerName, int addrId) {
        String name;
        int addr;
        for (Customer cus : Customers) {
            name = cus.getCustomerName();
            addr = cus.getAddressId();
            if (name.equals(customerName)) {
                if (Integer.compare(addr, addrId) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean duplicateAddressCheck(String address, String address2, String postalCode, String phoneNumber, int cityId) {
        String addrStr, addr2Str, phone, postCode;
        int city;
        for (Address addr : Addresses) {
            addrStr = addr.getAddress();
            addr2Str = addr.getAddress2();
            city = addr.getCityId();
            phone = addr.getPhone();
            postCode = addr.getPostalCode();

            if (addrStr.equals(address)) {
                if (Integer.compare(city, cityId) == 0) {
                    if (phone.equals(phoneNumber)) {
                        if (postCode.equals(postalCode)) {
                            if (addr2Str.equals(address2)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean duplicateCityCheck(String cityName, int countryId) {
        String cityStr;
        int country;
        for (City city : Cities) {
            cityStr = city.getCity();
            country = city.getCountryId();
            if (cityStr.equals(cityName)) {
                if (Integer.compare(country, countryId) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean duplicateCountryCheck(String countryName) {
        String country;
        for (Country coun : Countries) {
            country = coun.getCountry();
            if (country.equals(countryName)) {
                return true;
            }
        }
        return false;
    }

    private boolean newAddressCheck(String adr) {
        String add1;
        for (Address add : Addresses) {
            add1 = add.getAddress();

            if (add1.equals(adr)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean validateCreateFields() {
        int dialogueCode = 10;
        String addrStr, phone, postCode, customerName, country, city;
        try {
            customerName = createCustomerNameField.getText();
            addrStr = createAddressField.getText();
            phone = createPhoneNumberField.getText();
            postCode = createPostalCodeField.getText();
            city = createCityField.getText();
            country = createCountryField.getText();

            try {
                if (customerName.isEmpty() || customerName == null) {
                    dialogueCode = 0;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (addrStr.isEmpty() || addrStr == null) {
                    dialogueCode = 1;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (phone.isEmpty() || phone == null) {
                    dialogueCode = 2;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (postCode.isEmpty() || postCode == null) {
                    dialogueCode = 3;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (city.isEmpty() || city == null) {
                    dialogueCode = 4;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (country.isEmpty() || country == null) {
                    dialogueCode = 5;
                    throw new Exception("INVALID_USER_INPUT");
                }
            } catch (Exception e) {
                System.out.println(e);
                dialogueHandler(dialogueCode);
                return false;
            }

        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }
    
    private boolean validateUpdateFields() {
        int dialogueCode = 10;
        String addrStr, phone, postCode, customerName, country, city;
        try {
            customerName = updateCustomerNameField.getText();
            addrStr = updateAddressField.getText();
            phone = updatePhoneNumberField.getText();
            postCode = updatePostalCodeField.getText();
            city = updateCityField.getText();
            country = updateCountryField.getText();

            try {
                if (customerName.isEmpty() || customerName == null) {
                    dialogueCode = 0;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (addrStr.isEmpty() || addrStr == null) {
                    dialogueCode = 1;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (phone.isEmpty() || phone == null) {
                    dialogueCode = 2;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (postCode.isEmpty() || postCode == null) {
                    dialogueCode = 3;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (city.isEmpty() || city == null) {
                    dialogueCode = 4;
                    throw new Exception("INVALID_USER_INPUT");
                }
                if (country.isEmpty() || country == null) {
                    dialogueCode = 5;
                    throw new Exception("INVALID_USER_INPUT");
                }
            } catch (Exception e) {
                System.out.println(e);
                dialogueHandler(dialogueCode);
                return false;
            }

        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }

    private void clearCreateFields() {
        createCustomerNameField.clear();
        createCityField.clear();
        createAddressField.clear();
        createAddress2Field.clear();
        createPostalCodeField.clear();
        createPhoneNumberField.clear();
        createCountryField.clear();
    }

    private void clearUpdateFields() {
        updateCustomerNameField.clear();
        updateAddressField.clear();
        updateAddress2Field.clear();
        updatePostalCodeField.clear();
        updatePhoneNumberField.clear();
    }

    private void initUpdate() throws SQLException, Exception {
        tableRow = customersTable.getSelectionModel().getSelectedItem();
        String name = tableRow.getCustomerName();
        int addId, cityId, countryId;
        customer = CustomerDAOImpl.getCustomerByName(name, connection);
        addId = customer.getAddressId();
        addr = AddressDAOImpl.getAddressByAddressId(addId, connection);
        cityId = addr.getCityId();
        addrCity = CityDAOImpl.getCityByCityId(cityId, connection);
        countryId = addrCity.getCountryId();
        addrCountry = CountryDAOImpl.getCountryByCountryId(countryId, connection);
    }

    private void populateUpdateFields() {
        updateCustomerNameField.setText(customer.getCustomerName());
        updateAddressField.setText(addr.getAddress());
        updateAddress2Field.setText(addr.getAddress2());
        updatePostalCodeField.setText(addr.getPostalCode());
        updatePhoneNumberField.setText(addr.getPhone());
        updateCityField.setText(addrCity.getCity());
        updateCountryField.setText(addrCountry.getCountry());
    }
    
    private boolean dialogueHandler(int code) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("User Input Error");
        alert.setContentText("Click Ok to Confirm");
        Optional<ButtonType> result;
        switch (code) {
            case 0: //
                alert.setHeaderText("Please Enter A Customer Name");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 1: //Update Appointment Conflict Message
                alert.setHeaderText("Please Enter An Address");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 2: //
                alert.setHeaderText("Please Enter A Phone Number");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 3: //
                alert.setHeaderText("Please Enter A Postal Code");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 4: //
                alert.setHeaderText("Please Enter A City");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 5: //
                alert.setHeaderText("Please Enter A Country");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
        }
        return true;
    }

}
