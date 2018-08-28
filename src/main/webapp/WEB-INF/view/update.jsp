<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Update Language</title>
	
	<!-- main style -->
	<link href="../../css/update.css" rel="stylesheet" />

	<!-- components styles -->
	<link href="../../css/components/header.css" rel="stylesheet">
	<link href="../../css/components/footer.css" rel="stylesheet">
	
	<!-- utilities style -->
	<link href="../../css/util.css" rel="stylesheet">
	
</head>
<body>

	<!-- Header component -->
	<jsp:include page="../components/header.jsp" />
	
	<main>
	
		<section class="content">
			
			<form class="content__form" action="/languages/update/${language.getName()}" method="POST">
			
				<c:if test="${findByIdException != null}"> 	
					<%
						response.sendRedirect("http://localhost:8080/languages?error=1");
					%>
				</c:if>
				
				<c:if test="${updateException != null}"> 	
					<div class="error">
						<p>
							<c:out value="${updateException}" />
						</p>
					</div>
				</c:if>
				
				<fieldset class="form__container">
				
					<legend class="form__caption">Update Language</legend>
					
					<input type="hidden" name="id" value="${language.getId()}">
					
					<div class="form__field">
						<label for="name">Name</label>
						<input type="text" maxlength="30" id="name" name="name" value="${language.getName()}">
					</div>
					
					<input class="form__submit" type="submit" value="Update" >
				
				</fieldset>
				
				<a href="/languages" class="form__cancel">Cancel</a>
			
			</form>
			
		</section>
		
	</main>
	
	<!-- Footer component -->
	<jsp:include page="../components/footer.jsp" />
	
	
</body>
</html>