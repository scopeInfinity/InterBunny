<?php
if(isset($_POST['files']) && isset($_POST['myname'])) {
	$files = scandir("data/");
foreach ( $files as $filename)
if(endsWith($filename,$_POST['myname'])) {
	
	$filename = substr($filename, 0,strpos($filename,'_'));
    echo $filename . "\n";
}
}
else
if(isset($_POST['id']) && isset($_POST['myname']) && isset($_POST['oname']))
{
	if(isset($_POST['data'])) {
		 file_put_contents("data/".$_POST['id']."_".$_POST['oname'], $_POST['data'],FILE_APPEND );
	}
	$data = "";
	if(file_exists("data/".$_POST['id']."_".$_POST['myname'])) {
	$data = file_get_contents("data/".$_POST['id']."_".$_POST['myname']);
             unlink("data/".$_POST['id']."_".$_POST['myname']);
    }
    echo $data;
}
function endsWith($haystack, $needle) {
    // search forward starting from end minus needle length characters
    return $needle === "" || (($temp = strlen($haystack) - strlen($needle)) >= 0 && strpos($haystack, $needle, $temp) !== false);
}
?>