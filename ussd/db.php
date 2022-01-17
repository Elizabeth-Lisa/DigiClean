<?php
   $host        = "host = 127.0.0.1";
   $port        = "port = 5432";
   $dbname      = "dbname = digiclean";
   $credentials = "user = <dbname> password= <db-password>";

   $db = pg_connect( "$host $port $dbname $credentials"  );
   if(!$db) {
      //print "Error : Unable to open database\n";
   } else {
      //print "Opened database successfully\n";
   }
?>