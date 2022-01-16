<?php

// Reads the variables sent via POST
$sessionId   = $_POST["sessionId"];
$serviceCode = $_POST["serviceCode"];
$text = $_POST["text"];

//This is the first menu screen
if ( $text == "" ) {
$response  = "CON Hi welcome to DigiClean, I can help you find your next cleaning gig\n";
$response .= "1. Enter 1 to continue";
}

// Menu for a user who selects '1' from the first menu
// Will be brought to this second menu screen
else if ($text == "1") {
$response  = "CON  Pick an option below \n";
$response .= "1. SignUp \n";
$response .= "2. Login \n";
}
//Menu for a user who selects '1' from the second menu above
// Will be brought to this third menu screen
else if ($text == "1*1") {
$response = "CON You are about to SignUp\n";
$response .= "Please Enter 1 to confirm \n";

else if ($text == "1*1*1") {
$response = "CON Enter your full name  \n";
$response .= "Enter 0 to cancel";
}
else if ($text == "1*1*1*0") {
$response = "END Your registration has been canceled";
}

//signup logic

// Menu for a user who selects "2" from the second menu above
// Will be brought to this fourth menu screen
else if ($text == "1*2") {
$response = "CON You are about to Login \n";
$response .= "Please Enter 1 to confirm \n";
}
// Menu for a user who selects "1" from the fourth menu screen
else if ($text == "1*2*1") {
$response = "CON Enter your id number \n";
$response .= "Enter 0 to cancel";
}

//Login logic

else if ($text == "1*2*1*0") {
$response = "END See you soon :)";
}


//echo response
header('Content-type: text/plain');
echo $response
?>