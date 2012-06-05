<!DOCTYPE HTML>
<html>
<head>
<?php
include '../shared/include.php';
if($_POST)
{
	if($_POST['action']=="add")
	{
		$conn = mysql_connect($dbhost, $dbuser, $dbpass);
		mysql_select_db($dbname);
		$name = $_POST['name'];
		$email = $_POST['email'];
		$message = $_POST['message'];

		mysql_query("INSERT INTO message (id, name, email, message) VALUES (null, '$name', '$email', '$message')");
		mysql_close($conn);
		echo 'insert successull';
	}
	else if($_POST['action']=="delete")
	{
		$id = $_POST['id'];
		$conn = mysql_connect($dbhost, $dbuser, $dbpass);
		mysql_select_db($dbname);
		mysql_query("delete from message where id='$id'");
		mysql_close($conn);
		echo 'delete successull';
	}
}
?>
</head>
<body>
	<div id="header">
		<?php include '../shared/navigation.php';?>
	</div>
	<section>
		<div align="center">
		<?php if(!$_POST) {?>
			<form class="messageform" method="post" action="index.php"
				autocomplete="off">
				<label>Your Name:</label> <input name="name" placeholder="Goes Here"
					required /> <label>Your Email:</label> <input name="email" type="email"
					placeholder="Goes Here" required /> <label>Your Message:</label>
				<textarea name="message" placeholder="Goes Here"></textarea>
				<input class="submitbtn" type="submit" value="Submit" /> <input
					type="hidden" name="action" value="add" />
			</form>
			<?php }?>
		</div>
	</section>
	<section class="emailsection">
	<?php if($_POST) { //Show the message table after submit ?>
		<table class="emailtable">
			<tr>
				<td width="10%" class="emailtableth">Edit</td>
				<td width="20%" class="emailtableth">Name</td>
				<td width="20%" class="emailtableth">Email</td>
				<td width="50%" class="emailtableth">Message</td>
			</tr>
			<?php
			$conn = mysql_connect($dbhost, $dbuser, $dbpass);
			mysql_select_db($dbname);
			$emailSet = mysql_query("SELECT * FROM message");
			while($row = mysql_fetch_array($emailSet))
			{
				?>
			<tr class="emailtabletr">
				<td class="emailtabletd">
					<form action="index.php" method="post">
						<input class="deletebtn" type="submit" value="" /> <input
							type="hidden" name="action" value="delete"> <input type="hidden"
							name="id" value="<?php echo $row['id'] ?>">
					</form>
				</td>
				<td class="emailtabletd"><?php echo $row['name'] ?>
				</td>
				<td class="emailtabletd"><?php echo $row['email'] ?>
				</td>
				<td class="emailtabletd"><?php echo $row['message'] ?>
				</td>
			</tr>
			<?php
			}
			?>
		</table>
		<?php }?>
	</section>
	<section>
	<?php if($_POST) { //Show the back button after submit ?>
		<a href="index.php"><img src="../../res/images/back.gif" /> </a>
		<?php }?>
	</section>
	<div id="footer">
		<?php include '../shared/footer.php';?>
	</div>
</body>
</html>
