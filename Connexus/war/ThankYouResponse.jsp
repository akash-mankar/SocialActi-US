<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en" >
 <head>
  	 <meta charset="UTF-8">
    <title>Thank you!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0 maximum-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">
<style>
 html, body{
 	height:100%;
    background: url("images/light-background.jpg");
	background-size: cover;
    font: 13px/18px "Helvetica Neue", Helvetica, Arial, sans-serif;
 }
</style>
  </head>
<body>
	<div class="container-fluid fill-image">
		<div class="row row-main">
    		<h1 class="home"><b><i>Thank You!</i></b></h1>
    	<div class="row">
    		<div class="col-xs-1">
    		</div>
    		<div class="col-xs-10">
    			<h3 class = "homewhitesmall"><p>We appreciate the time you took to submit the feedback. 
    			We apologize for this inconvenience. You could return to MTURK by clicking on the following link.<br>
    			<a href ="https://www.mturk.com/mturk/findhits?match=false"> BACK TO MTURK</a>
    			</p></h3>
    		</div>
    		<div class="col-xs-1"></div>
    	</div>
    	</div>
	 </div>
    <script src="//code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
