<?php
require "DataBase.php"; 
$db = new DataBase();
if (isset($_POST['Title']) && isset($_POST['Price']) && isset($_POST['Rating']) && isset($_POST['Image'])&& isset($_POST['Category'])) {
    if ($db->dbConnect()) {
        if ($db->deleteProduct("orders", $_POST['Title'], $_POST['Price'], $_POST['Rating'], $_POST['Image'], $_POST['Category'])) {
            echo "Your Image Has Been Uploaded.";
        } else echo "Dodawanie nie powiodło się";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>