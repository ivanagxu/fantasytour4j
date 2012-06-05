<!-- PHP Inculde -->
<?php require_once('../../helpers/helpers.php');
        //error_reporting(E_ERROR | E_PARSE);
        global $dbhost, $dbuser, $dbpass, $dbname;
        global $conn;
        
        $dbhost = '127.0.0.1';
        $dbuser = 'root';
        $dbpass = 'password';
        $dbname = 'servicedb';
        
        $conn = mysql_connect($dbhost, $dbuser, $dbpass);
        if($conn)
        {
                mysql_close($conn);
        }
        else
        {
                die ('System in maintenance');
                exit;
        }
?>

<!-- CSS Include -->
<link rel="stylesheet" href="../../res/css/default.css" type="text/css">
<link rel="stylesheet" href="../../res/css/main.css" type="text/css">

<!-- Javascript Include -->
<script type="text/javascript" src="../../res/js/jquery-1.7.2.min.js" ></script> 
<script type="text/javascript" src="../../res/js/jquery-bp.js" ></script> 
<script type="text/javascript" src="../../res/js/navigation.js" ></script> 