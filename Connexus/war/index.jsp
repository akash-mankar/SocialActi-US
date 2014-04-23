<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.net.MalformedURLException"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en" >
<script type="text/javascript">
function validateForm() {
with (document.info) {
var alertMsg = "ATTENTION:\n";
if(answer.checked==false || Message.value == "" )alertMsg += "\n Please complete all the sections";
if (alertMsg != "ATTENTION:\n") {
alert(alertMsg);
return false;
} else {
return true;
} } }
</script>

  <head>
  	 <meta charset="UTF-8">
    <title>Task Restricted - Feedback</title>
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
    		<h1 class="home"><b><i>Welcome</i></b></h1>
    	<div class="row">
    		<div class="col-xs-1">
    		</div>
    		<div class="col-xs-10">
    		       <% 
        String ip     = request.getRemoteAddr();		
		String freeGeoipUrl =  "https://freegeoip.net/json/" + ip;

	    String[] Splitpairs = null;
		try {
		    URL url = new URL(freeGeoipUrl);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		       //System.out.println("LINE:" + line);
		    	Splitpairs = line.split(",");
		    }
		    reader.close();

		} catch (MalformedURLException e) {
		    // ...
		}

	    String[] country = Splitpairs[2].split(":");  // index 2 is country
	    String[] state   = Splitpairs[4].split(":");  // index 4 is state
	    String[] city    = Splitpairs[5].split(":");   // index 5 is city
	    
	    String finCountry = country[1].replace("\"","");
	    String finState = state[1].replace("\"","");
	    String finCity = city[1].replace("\"","");
	    
	  %>
    			<h3 class = "homewhitesmall"><p>We see that you are located in <%=" "+ finCity +","+finState +","+ finCountry+"."%> The minimum local wage in your country is $7.25 per hour (source: 
    			<a href="http://en.wikipedia.org/wiki/List_of_minimum_wages_by_country">Wikipedia</a>) 
    			and unfortunately the expected completion time for this task would mean you would be paid 
    			less than minimum wage.  We are not comfortable with that, and cannot afford to pay more, 
    			and so are not allowing this task to be performed in your region. We apologize for the 
    			inconvenience and welcome your feedback. </p></h3>
    		</div>
    		<div class="col-xs-1"></div>
    	</div>
       <div class="row">
       <div class="col-xs-2">
       </div>
       <div class="col-xs-7">
					<legend><h4 class="homewhite">Feedback form</h4></legend>
						<form action="handleFormServlet" method="post" name="info" onSubmit="return validateForm(this);" >
			 
			        		<fieldset style="border: none">
					         <p><span class="homeawaysmall"><label for="answer">Do you think this is a good policy that we do 
			    			not allow our Mechanical Turk workers to work for less than minimum wage,even though that 
			    			means some tasks we post will be unavailable to workers in certain regions?<br><br>
			    			For Example:<br>
			    			On a crowdsourcing platform,  Task A pays you $5 per hour. Task B pays you $8 per hour.
							The minimum wage in your location is $7 per hour.Hence, you are only allowed to work on 
							Task B but blocked from working on Task A.<br>
			                Please provide your opinion about this policy in at least 3 sentences. <br></label><br>
			                <label class="trail"><span class="h2omeawaysmall"><input type="radio" id="answer" name="answer"  value="Good">Good policy</label>
			 				&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			    			<label class="trail"><span class="homeawaysmall"><input type="radio" id="answer" name="answer" value="Bad">Bad policy</label>
			           		&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			           		<label class="trail"><span class="homeawaysmall"><input type="radio" id="answer" name="answer" value="Neutral">Neutral Opinion</label>
			            	</p>
							 <textarea name="Message" id="Message" cols="110" rows="5"></textarea><br><br>
							<span class="homeaway">Thank you!</I><br>
			
							<p><input class="trail" name="Reset Message" type="reset" id="Reset Message" value="Clear Form">&nbsp;<input class="trail" name="Submit Form" type="submit" value="Submit Form" onClick="check()"></p>
				  		</form>			
					</div>	
					<div class="col-xs-3">
					</div>
    		</div>
    	</div>
	 </div>
    <script src="//code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
