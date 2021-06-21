<?php
include 'DataBase.php';

$conn = mysqli_connect("localhost","root" , "", "loginregister");
@session_start();

$table = "users";
$username = "JanKowalski13";
//$sql = "SELECT *FROM users WHERE username='" . $user . "'";
$sql = "select * from " . $table . " where username = '" . $username . "'";

$result = $conn->query($sql);

if ($result->num_rows >0) {
	while($row[] = $result->fetch_assoc()) {
	 $tem = $row;
 $json = json_encode($tem);
 }
 } else {
	echo "0 results";
	}
	echo $json;
	$conn->close();
	
?>