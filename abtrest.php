<?php
$db_name ="restaurants";
$mysql_username="root";
$mysql_password="1";
$servername="localhost";
$conn = mysqli_connect($servername,$mysql_username,$mysql_password,$db_name);
// $city=$_GET["location"];
$restname = $_GET["name"];
$sql = "select * from aboutrestaurant where name = '$restname';";

$res = mysqli_query($conn,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
array_push($result,
array('name'=>$row[0],
'describe'=>$row[1],
'phone'=>$row[2],
'menu'=>$row[3],
'addr'=>$row[4]
));
}

echo json_encode(array("result"=>$result));

mysqli_close($conn);

?>
