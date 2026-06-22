module com.example.summer26.section1.group1.diamondworld {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.summer26.section1.group1.diamondworld to javafx.fxml;
    exports com.example.summer26.section1.group1.diamondworld;
}