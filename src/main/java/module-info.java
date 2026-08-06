module com.example.summer26.section1.group1.diamondworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.summer26.section1.group1.diamondworld to javafx.fxml;
    exports com.example.summer26.section1.group1.diamondworld;

    // Richi
    opens com.example.summer26.section1.group1.diamondworld.Richi to javafx.fxml;
    exports com.example.summer26.section1.group1.diamondworld.Richi;

    // Nupur
    opens com.example.summer26.section1.group1.diamondworld.Nupur to javafx.fxml;
    exports com.example.summer26.section1.group1.diamondworld.Nupur;

    // Turjo
    opens com.example.summer26.section1.group1.diamondworld.Turjo to javafx.fxml, com.google.gson;
    exports com.example.summer26.section1.group1.diamondworld.Turjo;
}