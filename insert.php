<?php
$db_name ="restaurants";
$mysql_username="root";
$mysql_password="1";
$servername="localhost";
$conn = mysqli_connect($servername,$mysql_username,$mysql_password,$db_name);
$summary=$_GET["summary"];
$sql="select count(*) from try;";
$res = mysqli_query($conn,$sql);
$row = mysqli_fetch_row($res);
echo $row[0];
$k=$row[0]+1;
$sql = "insert into try values($k,'$summary');";

$res = mysqli_query($conn,$sql);


mysqli_close($conn);

?>
