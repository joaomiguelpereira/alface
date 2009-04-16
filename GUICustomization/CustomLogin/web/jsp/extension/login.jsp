<%--
* Copyright (C) 2005-2007 Alfresco Software Limited.

* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.

* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.

* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

* As a special exception to the terms and conditions of version 2.0 of
* the GPL, you may redistribute this Program in connection with Free/Libre
* and Open Source Software ("FLOSS") applications as described in Alfresco's
* FLOSS exception.  You should have recieved a copy of the text describing
* the FLOSS exception, and it is also available here:
* http://www.alfresco.com/legal/licensing"
--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>

<%@ page import="org.alfresco.web.app.servlet.AuthenticationHelper"%>
<%@ page import="org.alfresco.web.ui.common.PanelGenerator"%>
<%@ page import="javax.servlet.http.Cookie"%>

<%@ page buffer="16kb" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>

<%
	Cookie authCookie = AuthenticationHelper.getAuthCookie(request);

	// remove the username cookie value if explicit logout was requested by the user
	if (session.getAttribute(AuthenticationHelper.SESSION_INVALIDATED) != null) {
		if (authCookie != null) {
			authCookie.setMaxAge(0);
			response.addCookie(authCookie);
		}
	} else {
		// setup value used by JSF bean state ready for login page if we find the cookie
		if (authCookie != null && authCookie.getValue() != null) {
			session.setAttribute(AuthenticationHelper.SESSION_USERNAME,
					authCookie.getValue());
		}
	}
%>

<body bgcolor="#ffffff">

<r:page titleId="title_login">
	<!-- Change this style snippet to the proper location -->
	<!-- Begin style -->
	<style>
#errors {
	background: red;
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
}

#login {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
	position: absolute;
	top: 50%;
	left: 50%;
	width: 440px;
	height: 200px;
	margin-top: -100px; /*set to a negative number 1/2 of your height*/
	margin-left: -220px; /*set to a negative number 1/2 of your width*/
	border: 1px solid #ccc;
	background-color: #f3f3f3;
	-moz-border-radius: 5px;
	padding: 10px;
	padding-top: 20px;
	background-image: url(/alfresco/images/login_logo_nidea.png);
	background-repeat: no-repeat;
	background-position: right top;
}

#login h1 {
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: 1.4em;
}

#login input,select {
	border: 1px solid #ddd;
	font-size: 16px;
	padding: 1px;
	width: 150px;
	margin: 0;
	-moz-border-radius: 3px;
	-webkit-border-radius: 5px;
}

#login input:focus,select:focus {
	background: #CCCCCC;
}
</style>
	<!-- End Style -->

	<f:view>
		<%-- load a bundle of properties I18N strings here --%>
		<f:loadBundle basename="alfresco.messages.webclient" var="msg" />

		<h:form acceptcharset="UTF-8" id="loginForm">

			<!-- Errors are shown at top of the page -->
			<div id="errors"><h:messages /></div>
			<!-- Start -->
			<div id="login">
			<h1><h:outputText value="#{msg.login_details}" /></h1>
			<table border=0 cellspacing=4 cellpadding=2>
				
				<tr>
					<td><h:outputText value="#{msg.username}" />:</td>
					<td><%-- input text field, with an example of a nested validator tag --%>
					<h:inputText id="user-name" value="#{LoginBean.username}"
						validator="#{LoginBean.validateUsername}" style="width:150px" />
					</td>
				</tr>

				<tr>
					<td><h:outputText value="#{msg.password}" />:</td>
					<td><%-- password text field, with an example of a validation bean method --%>
					<%-- the validation method adds a faces message to be displayed by a message tag --%>
					<h:inputSecret id="user-password" value="#{LoginBean.password}"
						validator="#{LoginBean.validatePassword}" style="width:150px" />
					</td>
				</tr>
				<tr>
					<td><h:outputText value="#{msg.language}" />:</td>
					<td><%-- language selection drop-down --%> <h:selectOneMenu
						id="language" value="#{UserPreferencesBean.language}"
						style="width:150px"
						onchange="document.forms['loginForm'].submit(); return true;">
						<f:selectItems value="#{UserPreferencesBean.languages}" />
					</h:selectOneMenu></td>
				</tr>

				<tr>
					<td colspan=2 align=right><h:commandButton id="submit"
						action="#{LoginBean.login}" value="#{msg.login}" /></td>
				</tr>


			</table>

			</div>
			<!-- End -->
		</h:form>
	</f:view>

	<script>
	document.cookie = "_alfTest=_alfTest"
	var cookieEnabled = (document.cookie.indexOf("_alfTest") != -1);
	if (cookieEnabled == false) {
		document.getElementById("no-cookies").style.display = 'inline';
	}

	if (document.getElementById("loginForm:user-name").value.length == 0) {
		document.getElementById("loginForm:user-name").focus();
	} else {
		document.getElementById("loginForm:user-password").focus();
	}
</script>

</r:page>

</body>
