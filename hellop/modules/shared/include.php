<?php require_once('../../helpers/helpers.php');
	//error_reporting(E_ERROR | E_PARSE);
	global $dbhost, $dbuser, $dbpass, $dbname;
	global $conn;
	$dbhost = 'localhost';
	$dbuser = 'root';
	$dbpass = 'password';
	$dbname = 'soladb';
	
	$conn = mysql_connect($dbhost, $dbuser, $dbpass);
	if($conn)
	{
		mysql_close($conn);
	}
	else
	{
		die ('Connect database failed');
		exit;
	}
?>

<link rel="stylesheet" href="../../res/css/style.css?v=1">

<script type="text/javascript" src="../../res/js/content.js"></script>
<script type="text/javascript" src="../../res/js/jquery-1.7.2.min.js"></script>