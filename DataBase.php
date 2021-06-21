<?php
require "DataBaseConfig.php";
	
class DataBase
{


    public $connect;
    public $data;
    private $sql;
    protected $servername;

    public  $username;
    public $password;
    protected $databasename;

	


    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }
	

	
    function signUp($table, $fullname, $email, $username, $password)
    { 		

		
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);

		
		$ImageData = $_POST['image_path'];
		$ImagePath = "files/$username.png";
		$ServerURL = "http://192.168.0.101/Loginregister/$ImagePath";
		
        $email = $this->prepareData($email);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email, image_path) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "','" . $ServerURL . "')";
        if (mysqli_query($this->connect, $this->sql)) {
			file_put_contents($ImagePath,base64_decode($ImageData));
            return true;
        } else return false;
    }
	
	
	
	
	    function addProduct($table, $Title, $Price, $Rating, $Image, $Category)
    { 		


        $Title = $this->prepareData($Title);
        $Price = $this->prepareData($Price);
        $Rating = $this->prepareData($Rating);
        $Image = $this->prepareData($Image);
        $Category = $this->prepareData($Category);
		
	
        $this->sql =
            "INSERT INTO " . $table . " (Title, Price, Rating, Image, Category) VALUES ('" . $Title . "','" . $Price . "','" . $Rating . "','" . $Image . "','" . $Category . "')";
        if (mysqli_query($this->connect, $this->sql)) {
			file_put_contents($ImagePath,base64_decode($ImageData));
            return true;
        } else return false;
    }
	
	    function deleteProduct($table, $Title, $Price, $Rating, $Image, $Category)
    { 		


        $Title = $this->prepareData($Title);
        $Price = $this->prepareData($Price);
        $Rating = $this->prepareData($Rating);
        $Image = $this->prepareData($Image);
        $Category = $this->prepareData($Category);
		

        $this->sql =
            "DELETE FROM " . $table . " WHERE Title = '" . $Title . "';";
        if (mysqli_query($this->connect, $this->sql)) {
			file_put_contents($ImagePath,base64_decode($ImageData));
            return true;
        } else return false;
    }
}
?>
